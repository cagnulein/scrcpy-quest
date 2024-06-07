package org.cagnulein.android_remote;

public class ovr_lib {
    static {
        System.loadLibrary("android_remote");
    }

    public static native void init(Object activityObject);
}
 