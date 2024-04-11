package com.example.ourspotifyapp.settingScreen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.ourspotifyapp.R;
import com.example.ourspotifyapp.accountSettingScreen.AccountSettingActivity;
import com.example.ourspotifyapp.dataSettingScreen.DataSettingActivity;
import com.example.ourspotifyapp.database.WrappedArtistEntry;
import com.example.ourspotifyapp.displaySettingScreen.DisplaySettingsActivity;
import com.example.ourspotifyapp.loginScreen.LoginActivity;
import com.example.ourspotifyapp.notifScreen.NotifSettingActivity;
import com.example.ourspotifyapp.wrappedSettingScreen.WrappedSettingActivity;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        ConstraintLayout dataScreen = findViewById(R.id.dataconstraintLayout);
        ConstraintLayout accountScreen = findViewById(R.id.accountconstraintLayout);

        ConstraintLayout wrappedScreen = findViewById(R.id.wrappedConstraintLayoutSettings);
        ConstraintLayout notifScreen = findViewById(R.id.notifsconstraintLayout);
        ConstraintLayout displayScreen = findViewById(R.id.displayConstraintLayout);
        ImageView backArrow = findViewById(R.id.backButtonSettings);

        backArrow.setOnClickListener(v -> backArrowMethod());
        dataScreen.setOnClickListener(v -> openDataSettings());
        accountScreen.setOnClickListener(v -> openAccountSettings());

        wrappedScreen.setOnClickListener(v -> openWrappedSettings());
        notifScreen.setOnClickListener(v -> openNotifSettings());
        displayScreen.setOnClickListener(v -> openDisplaySettings());

    }

    private void openDisplaySettings() {
        Intent intent = new Intent(this, DisplaySettingsActivity.class);
        startActivity(intent);
    }

    private void openNotifSettings() {
        Intent intent = new Intent(this, NotifSettingActivity.class);
        startActivity(intent);
    }
    private void openWrappedSettings() {
        Intent intent = new Intent(this, WrappedSettingActivity.class);
        startActivity(intent);
    }

    private void openDataSettings() {
        Intent intent = new Intent(this, DataSettingActivity.class);
        startActivity(intent);
    }

    private void openAccountSettings() {
        Intent intent = new Intent(this, AccountSettingActivity.class);
        startActivity(intent);
    }

    private void backArrowMethod() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
