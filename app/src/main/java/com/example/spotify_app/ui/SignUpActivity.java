package com.example.spotify_app.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.spotify_app.R;

public class SignUpActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button signUpButton;
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
        passwordConfirm = findViewById(R.id.passwordConfirm);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String confirmPassword = passwordConfirm.getText().toString();

                if (user.equals("user") && pass.equals("pass") && confirmPassword.equals("pass")) {
                    Toast.makeText(SignUpActivity.this, "Sign Up Successful!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignUpActivity.this, "Sign Up Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}