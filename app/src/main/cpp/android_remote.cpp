// Write C++ code here.
//
// Do not forget to dynamically load the C++ library into your application.
//
// For instance,
//
// In MainActivity.java:
//    static {
//       System.loadLibrary("android_remote");
//    }
//
// Or, in MainActivity.kt:
//    companion object {
//      init {
//         System.loadLibrary("android_remote")
//      }
//    }

#include "include/OVR_Platform.h"
#include <stdio.h>
#include <stdlib.h>
#include <jni.h>

extern "C"
JNIEXPORT void JNICALL
Java_org_cagnulein_android_1remote_ovr_1lib_init(JNIEnv *env, jclass clazz, jobject activityObject) {
    //jobject globalObj = env->NewGlobalRef(activityObject);
    ovr_PlatformInitializeAndroid("7605700462831986", activityObject, env);

}