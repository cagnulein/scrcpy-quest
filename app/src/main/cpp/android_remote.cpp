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


int init() {
    if (ovr_PlatformInitializeWindows("7605700462831986") != ovrPlatformInitialize_Success) {
    return 1;
    }
}