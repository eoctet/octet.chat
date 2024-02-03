package chat.octet;


import chat.octet.agent.OctetAgent;
import chat.octet.api.CharacterModelBuilder;
import chat.octet.config.CharacterConfig;
import chat.octet.model.Model;
import chat.octet.model.enums.ModelType;
import chat.octet.model.parameters.GenerateParameter;
import chat.octet.model.utils.ColorConsole;
import chat.octet.model.utils.PromptBuilder;
import chat.octet.utils.CommonUtils;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class AppStart {

    private static final String DEFAULT_CHARACTER_NAME = "llama2-chat";
    private static final Options OPTIONS = new Options();

    static {
        //Based parameters
        OPTIONS.addOption("h", "help", false, "Show this help message and exit.");
        OPTIONS.addOption(null, "app", true, "App launch type: cli | api (default: cli).");
        OPTIONS.addOption("c", "completions", false, "Use completions mode.");
        OPTIONS.addOption("ch", "character", true, "Load the specified AI character, default: llama2-chat.");
        OPTIONS.addOption("q", "questions", true, "Load the specified user question list, example: /PATH/questions.txt.");
    }

    private static void execute(Model model, CharacterConfig config, OctetAgent agent, boolean completions, String system, String input) {
        if (!completions) {
            String botInputPrefix = ColorConsole.cyan(config.getGenerateParameter().getAssistant() + ": ");
            System.out.print(botInputPrefix);
            if (config.isAgentMode() && agent != null) {
                System.out.println(ColorConsole.grey("[ I'm thinking, please wait a moment.. ]"));
                agent.chat(input, true);
            } else {
                model.chat(config.getGenerateParameter(), system, input).output();
            }
        } else {
            System.out.print(ColorConsole.green(input));
            model.generate(config.getGenerateParameter(), input).output();
        }
        System.out.print("\n");
        model.metrics();
    }

    private static void automation(CommandLine cmd) {
        String questions = cmd.getOptionValue("questions");
        String characterName = cmd.getOptionValue("character", DEFAULT_CHARACTER_NAME);
        boolean completions = cmd.hasOption("completions");

        try (Model model = CharacterModelBuilder.getInstance().getCharacterModel(characterName)) {
            List<String> lines = CommonUtils.readFileLines(questions);

            CharacterConfig config = CharacterModelBuilder.getInstance().getCharacterConfig();
            GenerateParameter generateParams = config.getGenerateParameter();
            String system = Optional.ofNullable(StringUtils.stripToNull(config.getPrompt())).orElse(PromptBuilder.DEFAULT_COMMON_SYSTEM);
            if (config.isAgentMode() && ModelType.QWEN != ModelType.valueOf(model.getModelType())) {
                throw new IllegalArgumentException("AI Agent only supports Qwen series model");
            }
            OctetAgent agent = config.isAgentMode() ? new OctetAgent(model, config) : null;

            for (String input : lines) {
                String question = StringUtils.trimToEmpty(input);
                if (StringUtils.isNotBlank(question)) {
                    System.out.println("\n" + ColorConsole.green(generateParams.getUser() + ": " + question));
                    execute(model, config, agent, completions, system, input);
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e);
            System.exit(1);
        }
    }

    private static void interaction(CommandLine cmd) {
        String characterName = cmd.getOptionValue("character", DEFAULT_CHARACTER_NAME);
        boolean completions = cmd.hasOption("completions");

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
             Model model = CharacterModelBuilder.getInstance().getCharacterModel(characterName)) {

            CharacterConfig config = CharacterModelBuilder.getInstance().getCharacterConfig();
            GenerateParameter generateParams = config.getGenerateParameter();
            String system = Optional.ofNullable(StringUtils.stripToNull(config.getPrompt())).orElse(PromptBuilder.DEFAULT_COMMON_SYSTEM);
            if (config.isAgentMode() && ModelType.QWEN != ModelType.valueOf(model.getModelType())) {
                throw new IllegalArgumentException("AI Agent only supports Qwen series model");
            }
            OctetAgent agent = config.isAgentMode() ? new OctetAgent(model, config) : null;

            while (true) {
                String userInputPrefix = ColorConsole.green(generateParams.getUser() + ": ");
                System.out.print("\n" + userInputPrefix);
                String input = bufferedReader.readLine();

                if (StringUtils.trimToEmpty(input).equalsIgnoreCase("exit")) {
                    break;
                }
                execute(model, config, agent, completions, system, input);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e);
            System.exit(1);
        }
    }

    public static void main(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(OPTIONS, args, false);

        if (cmd.hasOption("h") || cmd.hasOption("help")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("LLAMA-JAVA-APP", OPTIONS);
            System.exit(0);
        }
        String mode = cmd.getOptionValue("app", "cli");
        if ("api".equalsIgnoreCase(StringUtils.trimToEmpty(mode))) {
            SpringApplication.run(AppStart.class, args);
        } else {
            String questions = cmd.getOptionValue("questions", "");
            if (StringUtils.isBlank(questions)) {
                interaction(cmd);
            } else {
                automation(cmd);
            }
        }
    }

}
