package com.example.spotify_app.loginScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.spotify_app.database.StorageSystem;
import com.example.spotify_app.homeScreen.HomeActivity;

import com.example.spotify_app.R;

public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button loginButton;
    CardView card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        username = findViewById(R.id.usernameDialog);
        password = findViewById(R.id.passwordDialog);
        loginButton = findViewById(R.id.loginButton);
        card = findViewById(R.id.loginCard);
        card.setBackgroundResource(R.drawable.card_background);

        loginButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String user = username.getText().toString();
               String pass = password.getText().toString();


               if (user.equals("user") && pass.equals("pass")) {
                   Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                   startActivity(intent);
               } else {
                   Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
               }
           }
        });

    }
}
