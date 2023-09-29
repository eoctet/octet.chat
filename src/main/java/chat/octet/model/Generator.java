package chat.octet.model;

import chat.octet.model.beans.FinishReason;
import chat.octet.model.beans.Token;
import chat.octet.model.parameters.GenerateParameter;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


@Slf4j
public class Generator implements Iterator<Token> {
    private final GenerateParameter generateParams;
    private final List<Token> generateTokens;
    private final int[] inputIds;
    private final byte[] multiByteTokenBuffer;
    private int multiByteTokenLength;
    private int multiByteTokenIndex;
    private boolean finished = false;
    private int maxNewTokensSize;
    private int inputLength;
    private int pastTokensSize;
    private final int contextSize;
    private final int lastTokensSize;

    protected Generator(GenerateParameter generateParams, String text, int lastTokensSize) {
        this.generateParams = generateParams;
        this.contextSize = LlamaService.getContextSize();
        this.inputIds = new int[contextSize];
        this.multiByteTokenBuffer = new byte[8];
        this.lastTokensSize = lastTokensSize;

        int[] tokens = StringUtils.isNotBlank(text) ? LlamaService.tokenize(text, true) : new int[]{LlamaService.getTokenBOS()};
        if (tokens.length >= contextSize) {
            throw new IllegalArgumentException(MessageFormat.format("Requested tokens ({0}) exceed context window of {1}", tokens.length, contextSize));
        }
        if (generateParams.isVerbosePrompt()) {
            log.info(MessageFormat.format("Print prompt text:\n{0}", text));
        }
        System.arraycopy(tokens, 0, inputIds, 0, tokens.length);
        inputLength += tokens.length;

        maxNewTokensSize = (generateParams.getMaxNewTokensSize() <= 0) ? contextSize - tokens.length : generateParams.getMaxNewTokensSize();
        if (maxNewTokensSize + tokens.length > contextSize) {
            maxNewTokensSize = contextSize - tokens.length;
        }

        generateTokens = Lists.newArrayList();
        log.debug(MessageFormat.format("Generate starting, input tokens size: {0}.", tokens.length));
    }

    private boolean breakOrContinue(Token token, float[] logits) {
        if (token.getId() == LlamaService.getTokenEOS()) {
            token.updateFinishReason(FinishReason.FINISHED);
            return true;
        }
        if (generateParams.getStoppingCriteriaList() != null) {
            boolean matched = generateParams.getStoppingCriteriaList().criteria(inputIds, logits);
            if (matched) {
                token.updateFinishReason(FinishReason.STOP);
                return true;
            }
        }
        if (generateTokens.size() > maxNewTokensSize) {
            token.updateFinishReason(FinishReason.LENGTH);
            return true;
        }
        return false;
    }

    private String decode(int token) {
        byte[] buffer = new byte[64];
        int length = LlamaService.getTokenToPiece(token, buffer, buffer.length);
        byte code = buffer[0];

        if (length == 1 && !Character.isValidCodePoint(code)) {
            if (multiByteTokenLength == 0) {
                multiByteTokenLength = TokenDecoder.getUtf8ByteLength(code);
            }
            multiByteTokenBuffer[multiByteTokenIndex] = code;
            ++multiByteTokenIndex;
            if (multiByteTokenIndex == multiByteTokenLength) {
                String text = new String(multiByteTokenBuffer, 0, multiByteTokenLength, StandardCharsets.UTF_8);
                multiByteTokenIndex = 0;
                multiByteTokenLength = 0;
                Arrays.fill(multiByteTokenBuffer, (byte) 0);
                return text;
            }
            return StringUtils.EMPTY;
        }
        return new String(buffer, 0, length, StandardCharsets.UTF_8);
    }

    private void truncate(int keepSize) {
        if (keepSize <= 0 || keepSize >= contextSize) {
            keepSize = contextSize / 2;
        }
        //check multibyte token
        for (int truncateIndex = keepSize; truncateIndex > 0; truncateIndex--) {
            int size = TokenDecoder.isMultiByte(inputIds[truncateIndex]);
            if (size >= 0) {
                keepSize -= size;
                break;
            }
        }
        int[] newTokensBuffer = ArrayUtils.subarray(inputIds, keepSize, inputIds.length);
        Arrays.fill(inputIds, 0);
        System.arraycopy(newTokensBuffer, 0, inputIds, 0, newTokensBuffer.length);

        pastTokensSize = keepSize;
        inputLength = keepSize;
    }

    @Override
    public boolean hasNext() {
        return !finished;
    }

    @Override
    public Token next() {
        //evaluation tokens
        int evaluateTokenSize = LlamaService.evaluate(inputIds, pastTokensSize, inputLength);
        pastTokensSize += evaluateTokenSize;
        float[] logits = LlamaService.getLogits();

        // execute logits processor
        if (generateParams.getLogitsProcessorList() != null) {
            logits = generateParams.getLogitsProcessorList().processor(inputIds, logits);
        }
        //do sampling
        long timestamp = System.currentTimeMillis();
        int tokenId = LlamaService.sampling(generateParams, logits, inputIds, inputLength, lastTokensSize);
        Token token = new Token(tokenId, timestamp, decode(tokenId));
        //Save new token to the list
        generateTokens.add(token);

        if (inputLength + 1 > contextSize) {
            truncate(generateParams.getKeepContextTokensSize());
        }
        inputIds[inputLength] = tokenId;
        ++inputLength;

        if (breakOrContinue(token, logits)) {
            finished = true;
            ++pastTokensSize;
        }
        return token;
    }

    public String getFullGenerateText() {
        StringBuilder builder = new StringBuilder();
        generateTokens.forEach(token -> builder.append(token.getText()));
        return builder.toString();
    }

    public FinishReason getFinishReason() {
        return (generateTokens == null || generateTokens.isEmpty()) ? FinishReason.UNKNOWN : generateTokens.get(generateTokens.size() - 1).getFinishReason();

    }

}
