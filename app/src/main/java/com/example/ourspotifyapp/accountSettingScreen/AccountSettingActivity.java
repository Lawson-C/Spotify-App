package com.example.ourspotifyapp.accountSettingScreen;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.ourspotifyapp.R;
import com.example.ourspotifyapp.loginScreen.LoginActivity;
import com.example.ourspotifyapp.settingScreen.SettingsActivity;

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
                EditText oldUser = findViewById(R.id.oldPasswordDialog);
                EditText newUser = findViewById(R.id.newPasswordDialog);
                String oldUserText = oldUser.getText().toString();
                String newUserText = newUser.getText().toString();
                /*
                /////////////////////////////////////////////////////////

                    ADD COMPARISON FOR OLD USER TO CHECK FOR CORRECT USERS
                    WHEN DATABASE IS READY FOR IMPLEMENTATION

                /////////////////////////////////////////////////////////
                 */
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
                EditText oldPW = findViewById(R.id.oldPasswordText);
                EditText newPW = findViewById(R.id.newPasswordText);
                String oldPWText = oldPW.getText().toString();
                String newPWText = newPW.getText().toString();
                /*
                /////////////////////////////////////////////////////////

                    ADD COMPARISON FOR OLD USER TO CHECK FOR CORRECT USERS
                    WHEN DATABASE IS READY FOR IMPLEMENTATION

                /////////////////////////////////////////////////////////
                 */
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
                /*
                /////////////////////////////////////////////////////////

                    ADD DELETE CAPABILITY

                /////////////////////////////////////////////////////////
                 */
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

