package com.example.ourspotifyapp.accountSettingScreen;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.ourspotifyapp.R;
import com.example.ourspotifyapp.homeScreen.HomeActivity;
import com.example.ourspotifyapp.loginScreen.LoginActivity;

public class AccountSettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountsettings);
        ConstraintLayout chngUser = findViewById(R.id.accountChangeUsernameLayout);
        ConstraintLayout chngPW = findViewById(R.id.accountChangePasswordLayout);
        ConstraintLayout deleteAcc = findViewById(R.id.deleteAccount);
        chngUser.setOnClickListener(v -> changeUser());

    }

    private void changeUser() {
        Dialog popUp = new Dialog(this);
        popUp.setContentView(R.layout.changeuserprompt);
        popUp.show();
        Button confirm = popUp.findViewById(R.id.confirmdButtonUserChange);
        Button cancel =  popUp.findViewById(R.id.cancelButtonUserChange);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText oldUser = findViewById(R.id.oldusernameDialog);
                EditText newUser = findViewById(R.id.newusernameDialog);
                String oldUserText = oldUser.getText().toString();
                String newUserText = newUser.getText().toString();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUp.dismiss();
            }
        });
    }

    private void changePW() {
    }

    private void deleteAcc() {
    }
}

