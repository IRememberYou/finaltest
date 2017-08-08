#include <jni.h>
#include "Max.h"

extern "C" {
jint Java_com_example_finaltest_test3_CJavaActivity_maxFormJNI(JNIEnv *env, jobject object, jint a,
                                                               jint b) {
    return max(a, b);
}

}

