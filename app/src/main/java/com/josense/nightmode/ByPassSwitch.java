package com.josense.nightmode;

import android.annotation.SuppressLint;
import android.app.UiModeManager;
import android.os.Build;

import java.lang.reflect.InvocationTargetException;

public class ByPassSwitch {
    public static void switchUiMode(int mode, UiModeManager systemService) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            switchUiModeForApiLess28(mode, systemService);
        } else {
            switchUiModeForApiGreat29(mode, systemService);
        }
    }

    @SuppressLint("BlockedPrivateApi")
    private static void switchUiModeForApiGreat29(int mode, UiModeManager systemService) {
        try {
            if (mode == UiModeManager.MODE_NIGHT_NO) {
                systemService.getClass().getDeclaredMethod("setNightModeActivated", boolean.class)
                        .invoke(systemService, false);
            } else {
                systemService.getClass().getDeclaredMethod("setNightModeActivated", boolean.class)
                        .invoke(systemService, true);
            }
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException ignored) {
        }
    }

    private static void switchUiModeForApiLess28(int mode, UiModeManager systemService) {
        try {
            systemService.getClass().getDeclaredMethod("setNightMode", int.class)
                    .invoke(systemService, mode);
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException ignored) {
        }
    }
}
