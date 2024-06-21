package org.cagnulein.android_remote.wrappers;

import android.annotation.SuppressLint;
import android.os.IBinder;
import android.os.IInterface;

import java.lang.reflect.Method;
import android.hardware.camera2.CameraManager;

@SuppressLint("PrivateApi")
public final class ServiceManager {
    private final Method getServiceMethod;

    private static WindowManager windowManager;
    private static DisplayManager displayManager;
    private static InputManager inputManager;
    private static PowerManager powerManager;
    private static ActivityManager activityManager;
    private static CameraManager cameraManager;

    public ServiceManager() {
        try {
            getServiceMethod = Class.forName("android.os.ServiceManager").getDeclaredMethod("getService", String.class);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    private IInterface getService(String service, String type) {
        try {
            IBinder binder = (IBinder) getServiceMethod.invoke(null, service);
            Method asInterfaceMethod = Class.forName(type + "$Stub").getMethod("asInterface", IBinder.class);
            return (IInterface) asInterfaceMethod.invoke(null, binder);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    public static WindowManager getWindowManager() {
        if (windowManager == null) {
            windowManager = new WindowManager(getService("window", "android.view.IWindowManager"));
        }
        return windowManager;
    }

    public static DisplayManager getDisplayManager() {
        if (displayManager == null) {
            displayManager = new DisplayManager(getService("display", "android.hardware.display.IDisplayManager"));
        }
        return displayManager;
    }

    public static InputManager getInputManager() {
        if (inputManager == null) {
            inputManager = new InputManager(getService("input", "android.hardware.input.IInputManager"));
        }
        return inputManager;
    }

    public static CameraManager getCameraManager() {
        if (cameraManager == null) {
            try {
                Constructor<CameraManager> ctor = CameraManager.class.getDeclaredConstructor(Context.class);
                cameraManager = ctor.newInstance(FakeContext.get());
            } catch (Exception e) {
                throw new AssertionError(e);
            }
        }
        return cameraManager;
    }

    public static PowerManager getPowerManager() {
        if (powerManager == null) {
            powerManager = new PowerManager(getService("power", "android.os.IPowerManager"));
        }
        return powerManager;
    }

    public static ActivityManager getActivityManager() {
        if (activityManager == null) {
            activityManager = ActivityManager.create();
        }
        return activityManager;
    }
}
