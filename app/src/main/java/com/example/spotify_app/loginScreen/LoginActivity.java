package com.example.spotify_app.loginScreen;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.spotify_app.database.StorageSystem;

import com.example.spotify_app.R;

public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button loginButton;
    CardView card;

    // StorageSystem db = StorageSystem.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        StorageSystem db = new StorageSystem(getApplicationContext());

        username = findViewById(R.id.usernameDialog);
        password = findViewById(R.id.passwordDialog);
        loginButton = findViewById(R.id.loginButton);
        card = findViewById(R.id.loginCard);
        card.setBackgroundResource(R.drawable.card_background);


        // db.writeLocalAccount(1, "test", "pass_two", 1244);
        // db.deleteLocalAccount(0);

        loginButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String user = username.getText().toString();
               String pass = password.getText().toString();

               // something like check if user exists in db
               // for now i just pass in the user

               if (db.readLocalAccountByUserPass(user, pass, "id") != null) {
                   Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
               } else {
                   Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
               }
//               if (user.equals("user") && pass.equals("pass")) {
//                   Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
//               } else {
//                   Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
//               }
           }
        });

    }
}
