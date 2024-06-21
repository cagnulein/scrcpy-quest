package org.cagnulein.android_remote.wrappers;

import android.hardware.display.VirtualDisplay;
import android.os.IInterface;
import android.view.Surface;

import org.cagnulein.android_remote.DisplayInfo;
import org.cagnulein.android_remote.Size;

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

    public DisplayInfo getDisplayInfo(int displayId) {
        try {
            Object displayInfo = manager.getClass().getMethod("getDisplayInfo", int.class).invoke(manager, displayId);
            if (displayInfo == null) {
                // fallback when displayInfo is null
                return getDisplayInfoFromDumpsysDisplay(displayId);
            }
            Class<?> cls = displayInfo.getClass();
            // width and height already take the rotation into account
            int width = cls.getDeclaredField("logicalWidth").getInt(displayInfo);
            int height = cls.getDeclaredField("logicalHeight").getInt(displayInfo);
            int rotation = cls.getDeclaredField("rotation").getInt(displayInfo);
            int layerStack = cls.getDeclaredField("layerStack").getInt(displayInfo);
            int flags = cls.getDeclaredField("flags").getInt(displayInfo);
            return new DisplayInfo(displayId, new Size(width, height), rotation, layerStack, flags);
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(e);
        }
    }

    public int[] getDisplayIds() {
        try {
            return (int[]) manager.getClass().getMethod("getDisplayIds").invoke(manager);
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(e);
        }
    }    
}
