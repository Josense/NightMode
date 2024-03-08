package com.josense.nightmode;

import static com.josense.nightmode.ByPassSwitch.switchUiMode;

import android.app.AlertDialog;
import android.app.UiModeManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;
import com.josense.nightmode.databinding.ActivityMainBinding;


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
            showDialog(this,
                    getString(R.string.principle),
                    getString(R.string.principle_info),
                    getString(R.string.close));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDayNightButtonState();
        binding.dayNightSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                switchUiMode(UiModeManager.MODE_NIGHT_YES, systemService);
            } else {
                switchUiMode(UiModeManager.MODE_NIGHT_NO, systemService);
            }
        });
    }

    private void setDayNightButtonState() {
        boolean realNightMode = systemService.getNightMode() == UiModeManager.MODE_NIGHT_YES;
        binding.dayNightSwitch.setChecked(realNightMode);
    }

    public static void showDialog(Context context, String title, String message, String buttonText) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(buttonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // 关闭对话框
            }
        });
        builder.show();
    }
}