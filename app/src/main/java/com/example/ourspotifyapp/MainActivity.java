package com.example.ourspotifyapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.ourspotifyapp.accountSettingScreen.AccountSettingActivity;
import com.example.ourspotifyapp.database.StorageSystem;
import com.example.ourspotifyapp.loginScreen.LoginActivity;
import com.example.ourspotifyapp.ui.SignUpActivity;

public class MainActivity extends AppCompatActivity {

    Button loginButton;
    Button signupButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        StorageSystem.init(getApplicationContext());

        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.signupButton);
        loginButton.setOnClickListener(v -> openLoginActivity());
        signupButton.setOnClickListener(v -> openSignUpActivity());

    }

    public void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void openSignUpActivity() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}
