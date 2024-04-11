package com.example.ourspotifyapp.notifScreen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ourspotifyapp.R;
import com.example.ourspotifyapp.settingScreen.SettingsActivity;

public class NotifSettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifsettings);
        ImageView backArrow = findViewById(R.id.notifsBackButtonSettings);
        backArrow.setOnClickListener(v -> backArrowMethod());
    }


    private void backArrowMethod() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
