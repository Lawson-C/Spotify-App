package com.example.ourspotifyapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.ourspotifyapp.MainActivity;
import com.example.ourspotifyapp.R;
import com.example.ourspotifyapp.database.LocalAccountEntry;
import com.example.ourspotifyapp.database.StorageSystem;
import com.example.ourspotifyapp.homeScreen.HomeActivity;
import com.example.ourspotifyapp.loginScreen.LoginActivity;


public class SignUpActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button signUpButton;

    Button backButton;
    CardView card;

    EditText passwordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.signup);

        username = findViewById(R.id.usernameDialog);
        password = findViewById(R.id.passwordDialog);
        signUpButton = findViewById(R.id.signUpButton);
        backButton = findViewById(R.id.backButton);
        passwordConfirm = findViewById(R.id.passwordConfirm);

        backButton.setOnClickListener(v -> openMainActivity());

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String confirmPassword = passwordConfirm.getText().toString();

                if (!confirmPassword.equals(pass)) {
                    Toast.makeText(SignUpActivity.this, "Passwords don't match!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d("signup", "we got here!");

                String checkExist = "";

                try {
                    checkExist = StorageSystem.readLocalAccountValue(LocalAccountEntry.COLUMN_NAME, user, LocalAccountEntry.COLUMN_NAME);
                    if (checkExist.equals(user)) {
                        Log.d("signup", "exception for duplicate user");
                        Toast.makeText(SignUpActivity.this, "User already exists!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {
                    Log.d("signup", "user that doesn't exist wasn't found");
                }

                StorageSystem.writeLocalAccount(user, pass, 0);

                LoginActivity.currentUserID = Integer.parseInt(StorageSystem.readLocalAccountValue(LocalAccountEntry.COLUMN_NAME, user, LocalAccountEntry.COLUMN_ID));

                Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}