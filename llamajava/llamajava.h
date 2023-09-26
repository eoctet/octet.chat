/* DO NOT EDIT THIS FILE - it is machine generated */
#include "jni.h"
/* Header for class chat_octet_llama_LlamaLibrary */

#ifndef _Included_chat_octet_llama_LlamaLibrary
#define _Included_chat_octet_llama_LlamaLibrary
#ifdef __cplusplus
extern "C" {
#endif

/*
* Class:     chat_octet_model_LlamaService
* Method:    initLocal
*/
JNIEXPORT void JNICALL Java_chat_octet_model_LlamaService_initLocal
        (JNIEnv *, jclass);

/*
* Class:     chat_octet_model_LlamaService
* Method:    getLlamaContextDefaultParams
*/
JNIEXPORT jobject JNICALL Java_chat_octet_model_LlamaService_getLlamaContextDefaultParams
        (JNIEnv *, jclass);

/*
 * Class:     chat_octet_model_LlamaService
 * Method:    llamaBackendInit
 */
JNIEXPORT void JNICALL Java_chat_octet_model_LlamaService_llamaBackendInit
        (JNIEnv *, jclass, jboolean);

/*
 * Class:     chat_octet_model_LlamaService
 * Method:    llamaBackendFree
 */
JNIEXPORT void JNICALL Java_chat_octet_model_LlamaService_llamaBackendFree
        (JNIEnv *, jclass);

/*
 * Class:     chat_octet_model_LlamaService
 * Method:    loadLlamaModelFromFile
 */
JNIEXPORT void JNICALL Java_chat_octet_model_LlamaService_loadLlamaModelFromFile
        (JNIEnv *, jclass, jstring, jobject);

/*
 * Class:     chat_octet_model_LlamaService
 * Method:    createNewContextWithModel
 */
JNIEXPORT void JNICALL Java_chat_octet_model_LlamaService_createNewContextWithModel
        (JNIEnv *, jclass, jobject);

/*
 * Class:     chat_octet_model_LlamaService
 * Method:    release
 */
JNIEXPORT void JNICALL Java_chat_octet_model_LlamaService_release
        (JNIEnv *, jclass);

/*
 * Class:     chat_octet_model_LlamaService
 * Method:    getMaxDevices
 */
JNIEXPORT jint JNICALL Java_chat_octet_model_LlamaService_getMaxDevices
        (JNIEnv *, jclass);

/*
 * Class:     chat_octet_model_LlamaService
 * Method:    isMmapSupported
 */
JNIEXPORT jboolean JNICALL Java_chat_octet_model_LlamaService_isMmapSupported
        (JNIEnv *, jclass);

/*
 * Class:     chat_octet_model_LlamaService
 * Method:    isMlockSupported
 */
JNIEXPORT jboolean JNICALL Java_chat_octet_model_LlamaService_isMlockSupported
        (JNIEnv *, jclass);

/*
 * Class:     chat_octet_model_LlamaService
 * Method:    getVocabSize
 */
JNIEXPORT jint JNICALL Java_chat_octet_model_LlamaService_getVocabSize
        (JNIEnv *, jclass);

/*
 * Class:     chat_octet_model_LlamaService
 * Method:    getContextSize
 */
JNIEXPORT jint JNICALL Java_chat_octet_model_LlamaService_getContextSize
        (JNIEnv *, jclass);

/*
 * Class:     chat_octet_model_LlamaService
 * Method:    getEmbeddingSize
 */
JNIEXPORT jint JNICALL Java_chat_octet_model_LlamaService_getEmbeddingSize
        (JNIEnv *, jclass);

/*
 * Class:     chat_octet_model_LlamaService
 * Method:    getVocabType
 */
JNIEXPORT jint JNICALL Java_chat_octet_model_LlamaService_getVocabType
        (JNIEnv *, jclass);

/*
 * Class:     chat_octet_model_LlamaService
 * Method:    getModelVocabSize
 */
JNIEXPORT jint JNICALL Java_chat_octet_model_LlamaService_getModelVocabSize
        (JNIEnv *, jclass);

/*
 * Class:     chat_octet_model_LlamaService
 * Method:    getModelContextSize
 */
JNIEXPORT jint JNICALL Java_chat_octet_model_LlamaService_getModelContextSize
        (JNIEnv *, jclass);

/*
 * Class:     chat_octet_model_LlamaService
 * Method:    getModelEmbeddingSize
 */
JNIEXPORT jint JNICALL Java_chat_octet_model_LlamaService_getModelEmbeddingSize
        (JNIEnv *, jclass);

/*
 * Class:     chat_octet_model_LlamaService
 * Method:    loadLoraModelFromFile
 */
JNIEXPORT jint JNICALL Java_chat_octet_model_LlamaService_loadLoraModelFromFile
        (JNIEnv *, jclass, jstring, jstring, jint);

/*
 * Class:     chat_octet_model_LlamaService
 * Method:    evaluate
 */
JNIEXPORT jint JNICALL Java_chat_octet_model_LlamaService_evaluate
        (JNIEnv *, jclass, jintArray, jint, jint, jint);

/*
 * Class:     chat_octet_model_LlamaService
 * Method:    getLogits
 */
JNIEXPORT jfloatArray JNICALL Java_chat_octet_model_LlamaService_getLogits
        (JNIEnv *, jclass);

/*
 * Class:     chat_octet_model_LlamaService
 * Method:    getEmbeddings
 */
JNIEXPORT jfloatArray JNICALL Java_chat_octet_model_LlamaService_getEmbeddings
        (JNIEnv *, jclass);

/*
 * Class:     chat_octet_model_LlamaService
 * Method:    getTokenText
 */
JNIEXPORT jstring JNICALL Java_chat_octet_model_LlamaService_getTokenText
        (JNIEnv *, jclass, jint);

/*
 * Class:     chat_octet_model_LlamaService
 * Method:    getTokenScore
 */
JNIEXPORT jfloat JNICALL Java_chat_octet_model_LlamaService_getTokenScore
        (JNIEnv *, jclass, jint);

/*
 * Class:     chat_octet_model_LlamaService
 * Method:    getTokenType
 */
JNIEXPORT jint JNICALL Java_chat_octet_model_LlamaService_getTokenType
        (JNIEnv *, jclass, jint);

/*
 * Class:     chat_octet_model_LlamaService
 * Method:    getTokenBOS
 */
JNIEXPORT jint JNICALL Java_chat_octet_model_LlamaService_getTokenBOS
        (JNIEnv *, jclass);

/*
 * Class:     chat_octet_model_LlamaService
 * Method:    getTokenEOS
 */
JNIEXPORT jint JNICALL Java_chat_octet_model_LlamaService_getTokenEOS
        (JNIEnv *, jclass);

/*
 * Class:     chat_octet_model_LlamaService
 * Method:    getTokenNL
 */
JNIEXPORT jint JNICALL Java_chat_octet_model_LlamaService_getTokenNL
        (JNIEnv *, jclass);

/*
 * Class:     chat_octet_model_LlamaService
 * Method:    tokenize
 */
JNIEXPORT jint JNICALL Java_chat_octet_model_LlamaService_tokenize
        (JNIEnv *, jclass, jbyteArray, jint, jintArray, jint, jboolean);

/*
 * Class:     chat_octet_model_LlamaService
 * Method:    tokenizeWithModel
 */
JNIEXPORT jint JNICALL Java_chat_octet_model_LlamaService_tokenizeWithModel
        (JNIEnv *, jclass, jbyteArray, jint, jintArray, jint, jboolean);

/*
 * Class:     chat_octet_model_LlamaService
 * Method:    getTokenToPiece
 */
JNIEXPORT jint JNICALL Java_chat_octet_model_LlamaService_getTokenToPiece
        (JNIEnv *, jclass, jint, jbyteArray, jint);

/*
 * Class:     chat_octet_model_LlamaService
 * Method:    getTokenToPieceWithModel
 */
JNIEXPORT jint JNICALL Java_chat_octet_model_LlamaService_getTokenToPieceWithModel
        (JNIEnv *, jclass, jint, jbyteArray, jint);

/*
 * Class:     chat_octet_model_LlamaService
 * Method:    printTimings
 */
JNIEXPORT void JNICALL Java_chat_octet_model_LlamaService_printTimings
        (JNIEnv *, jclass);

/*
 * Class:     chat_octet_model_LlamaService
 * Method:    printSystemInfo
 */
JNIEXPORT jstring JNICALL Java_chat_octet_model_LlamaService_printSystemInfo
        (JNIEnv *, jclass);

/*
 * Class:     chat_octet_model_LlamaService
 * Method:    sampling
 */
JNIEXPORT jint JNICALL Java_chat_octet_model_LlamaService_sampling
        (JNIEnv *, jclass, jfloatArray, jintArray, jint, jfloat, jfloat, jfloat, jboolean, jint, jfloat,
         jfloat, jfloat, jint, jfloat, jfloat, jfloat);

#ifdef __cplusplus
}
#endif
#endif
