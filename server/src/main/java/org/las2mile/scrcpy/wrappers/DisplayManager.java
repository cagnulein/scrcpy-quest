package org.las2mile.scrcpy.wrappers;

import android.hardware.display.VirtualDisplay;
import android.os.IInterface;
import android.view.Surface;

import org.las2mile.scrcpy.DisplayInfo;
import org.las2mile.scrcpy.Size;

import java.lang.reflect.InvocationTargetException;

public final class DisplayManager {
    private final IInterface manager;

    public DisplayManager(IInterface manager) {
        this.manager = manager;
    }

    public static VirtualDisplay createVirtualDisplay(String name, int width, int height,
                                                      int displayIdToMirror, Surface surface)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        java.lang.Class<?> displayManagerClass =
                java.lang.Class.forName("android.hardware.display.DisplayManager");
        return (VirtualDisplay) displayManagerClass.getMethod("createVirtualDisplay",
                        String.class, int.class, int.class, int.class, Surface.class)
                .invoke(null, name, width, height, displayIdToMirror, surface);
    }

    public DisplayInfo getDisplayInfo() {
        try {
            Object displayInfo = manager.getClass().getMethod("getDisplayInfo", int.class).invoke(manager, 0);
            Class<?> cls = displayInfo.getClass();
            // width and height already take the rotation into account
            int width = cls.getDeclaredField("logicalWidth").getInt(displayInfo);
            int height = cls.getDeclaredField("logicalHeight").getInt(displayInfo);
            int rotation = cls.getDeclaredField("rotation").getInt(displayInfo);
            return new DisplayInfo(new Size(width, height), rotation);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}
