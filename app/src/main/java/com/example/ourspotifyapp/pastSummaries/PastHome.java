package com.example.ourspotifyapp.pastSummaries;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ourspotifyapp.R;
import com.example.ourspotifyapp.homeScreen.HomeActivity;
import com.example.ourspotifyapp.loginScreen.LoginActivity;


public class PastHome extends AppCompatActivity {

    Button weeks4;
    Button months6;
    Button year1;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.past_summaries);

        weeks4 = findViewById(R.id.past4weeks);
        months6 = findViewById(R.id.past6months);
        year1 = findViewById(R.id.past1yr);
        back = findViewById(R.id.past_back);

        back.setOnClickListener(v -> openHomeScreen());
    }

    public void openHomeScreen() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

}
