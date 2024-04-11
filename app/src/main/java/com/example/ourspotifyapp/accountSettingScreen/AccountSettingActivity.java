package com.example.ourspotifyapp.accountSettingScreen;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.ourspotifyapp.MainActivity;
import com.example.ourspotifyapp.R;
import com.example.ourspotifyapp.database.LocalAccountEntry;
import com.example.ourspotifyapp.database.StorageSystem;
import com.example.ourspotifyapp.loginScreen.LoginActivity;
import com.example.ourspotifyapp.settingScreen.SettingsActivity;
import com.example.ourspotifyapp.ui.SignUpActivity;
import com.example.ourspotifyapp.wrappedDisplays.PastHome;
import com.example.ourspotifyapp.wrappedDisplays.PastTopArtists;

import java.util.Objects;

public class AccountSettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountsettings);
        ConstraintLayout chngUser = findViewById(R.id.accountChangeUsernameLayout);
        ConstraintLayout chngPW = findViewById(R.id.accountChangePasswordLayout);
        ConstraintLayout deleteAcc = findViewById(R.id.deleteAccount);
        ImageView backArrow = findViewById(R.id.accountBackButtonSettings);




        chngUser.setOnClickListener(v -> changeUser());
        chngPW.setOnClickListener(v -> changePW());
        deleteAcc.setOnClickListener(v -> deleteAcc());
        backArrow.setOnClickListener(v -> backArrowMethod());

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
                EditText oldUser = popUp.findViewById(R.id.oldUserDialog);
                EditText newUser = popUp.findViewById(R.id.newUserDialog);
                String oldUserText = oldUser.getText().toString();
                String newUserText = newUser.getText().toString();

                if (oldUserText.equals(newUserText)) {
                    Toast.makeText(AccountSettingActivity.this, "Old and new username can't be the same!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String realOldUser = StorageSystem.readLocalAccountValue(LocalAccountEntry.COLUMN_ID, String.valueOf(LoginActivity.currentUserHash), LocalAccountEntry.COLUMN_NAME);
                if (!oldUserText.equals(realOldUser)) {
                    Toast.makeText(AccountSettingActivity.this, "Old username doesn't match!", Toast.LENGTH_SHORT).show();
                    return;
                }

                StorageSystem.setIntLocalAccount(LoginActivity.currentUserHash, LocalAccountEntry.COLUMN_NAME, newUserText);
                Toast.makeText(AccountSettingActivity.this, "Username updated!", Toast.LENGTH_SHORT).show();

                popUp.dismiss();
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
        Dialog popUp = new Dialog(this);
        popUp.setContentView(R.layout.changepasswordprompt);
        popUp.show();
        Button confirm = popUp.findViewById(R.id.confirmdButtonPWChange);
        Button cancel =  popUp.findViewById(R.id.cancelButtonPWChange);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText oldPW = popUp.findViewById(R.id.oldPasswordText);
                EditText newPW = popUp.findViewById(R.id.newPasswordText);
                String oldPWText = oldPW.getText().toString();
                String newPWText = newPW.getText().toString();

                if (oldPWText.equals(newPWText)) {
                    Toast.makeText(AccountSettingActivity.this, "Old and new password can't be the same!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String realOldPW = StorageSystem.readLocalAccountValue(LocalAccountEntry.COLUMN_ID, String.valueOf(LoginActivity.currentUserHash), LocalAccountEntry.COLUMN_PASSWORD);
                if (!oldPWText.equals(realOldPW)) {
                    Toast.makeText(AccountSettingActivity.this, "Old password doesn't match!", Toast.LENGTH_SHORT).show();
                    return;
                }

                StorageSystem.setIntLocalAccount(LoginActivity.currentUserHash, LocalAccountEntry.COLUMN_PASSWORD, newPWText);
                Toast.makeText(AccountSettingActivity.this, "Password updated!", Toast.LENGTH_SHORT).show();

                popUp.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUp.dismiss();
            }
        });
    }

    private void deleteAcc() {
        Dialog popUp = new Dialog(this);
        popUp.setContentView(R.layout.deletedialog);
        popUp.show();
        Button confirm = popUp.findViewById(R.id.confirmButtonDelete);
        Button cancel =  popUp.findViewById(R.id.cancelButtonDelete);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StorageSystem.deleteLocalAccount(LoginActivity.currentUserHash);
                startActivity(new Intent(AccountSettingActivity.this, MainActivity.class));
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUp.dismiss();
            }
        });
    }
    private void backArrowMethod() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}

