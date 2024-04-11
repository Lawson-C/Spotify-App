package com.example.ourspotifyapp.wrappedSettingScreen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ourspotifyapp.R;
import com.example.ourspotifyapp.settingScreen.SettingsActivity;

public class WrappedSettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wrappedsettings);
        ImageView backArrow = findViewById(R.id.wrappedBackButtonSettings);
        backArrow.setOnClickListener(v -> backArrowMethod());
    }


    private void backArrowMethod() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
