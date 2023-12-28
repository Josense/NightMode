package com.josense.nightmode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.josense.nightmode.databinding.ActivityPrincipleBinding;

public class PrincipleActivity extends AppCompatActivity {

    private ActivityPrincipleBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrincipleBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.closeButton.setOnClickListener(v -> PrincipleActivity.this.finish());
    }
}