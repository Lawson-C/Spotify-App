package com.example.ourspotifyapp.loginScreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.ourspotifyapp.R;
import com.example.ourspotifyapp.database.LocalAccountEntry;
import com.example.ourspotifyapp.wrappedDisplays.StartingWrappedScreen;
import com.example.ourspotifyapp.MainActivity;
import com.example.ourspotifyapp.database.StorageSystem;
import com.example.ourspotifyapp.homeScreen.HomeActivity;

public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button loginButton;
    Button backButton;
    CardView card;

    public static int currentUserHash = -1;

    Button temporaryBtn; // get rid of this later

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        username = findViewById(R.id.usernameDialog);
        password = findViewById(R.id.passwordDialog);
        loginButton = findViewById(R.id.loginButton);
        backButton = findViewById(R.id.backButton);
        card = findViewById(R.id.loginCard);
        card.setBackgroundResource(R.drawable.card_background);

        loginButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String user = username.getText().toString();
               String pass = password.getText().toString();

               String checkPass = "";
               try {
                   checkPass = StorageSystem.readLocalAccountValue(LocalAccountEntry.COLUMN_NAME, user, LocalAccountEntry.COLUMN_PASSWORD);
               } catch (Exception e) {
                   Log.d("login exception", "something happened? " + e);
               }

               if (pass.equals(checkPass)) {
                   currentUserHash = Math.abs( (short) user.hashCode());
                   Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                   startActivity(intent);
               } else {
                   Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
               }
           }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
