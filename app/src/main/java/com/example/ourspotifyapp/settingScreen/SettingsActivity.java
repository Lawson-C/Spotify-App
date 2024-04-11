package com.example.ourspotifyapp.settingScreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.ourspotifyapp.R;
import com.example.ourspotifyapp.accountSettingScreen.AccountSettingActivity;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        ConstraintLayout account = this.findViewById(R.id.accountconstraintLayout);
        account.findViewById(R.id.accountconstraintLayout);
        account.setOnClickListener(v -> openAccountSettings());
    }

    private void openAccountSettings() {
        Intent intent = new Intent(this, AccountSettingActivity.class);
        startActivity(intent);
    }

}
