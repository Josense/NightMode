package com.josense.nightmode;

import android.annotation.SuppressLint;
import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.josense.nightmode.databinding.ActivityMainBinding;

import java.lang.reflect.InvocationTargetException;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private UiModeManager systemService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        systemService = (UiModeManager) getSystemService(Context.UI_MODE_SERVICE);

        binding.principleButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PrincipleActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDayNightButtonState();
        binding.dayNightSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                switchUiMode(UiModeManager.MODE_NIGHT_YES);
            } else {
                switchUiMode(UiModeManager.MODE_NIGHT_NO);
            }
        });
    }

    private void switchUiMode(int mode) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            switchUiModeForApiLess28(mode);
        } else {
            switchUiModeForApiGreat29(mode);
        }
    }

    @SuppressLint("BlockedPrivateApi")
    private void switchUiModeForApiGreat29(int mode) {
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

    private void switchUiModeForApiLess28(int mode) {
        try {
            systemService.getClass().getDeclaredMethod("setNightMode", int.class)
                    .invoke(systemService, mode);
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException ignored) {
        }
    }

    private void setDayNightButtonState() {
        boolean realNightMode = systemService.getNightMode() == UiModeManager.MODE_NIGHT_YES;
        binding.dayNightSwitch.setChecked(realNightMode);
    }
}