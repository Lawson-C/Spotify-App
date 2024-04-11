package com.example.ourspotifyapp.homeScreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.ourspotifyapp.MainActivity;
import com.example.ourspotifyapp.R;
import com.example.ourspotifyapp.loginScreen.LoginActivity;
import com.example.ourspotifyapp.pastSummaries.PastHome;
import com.example.ourspotifyapp.settingScreen.SettingsActivity;
import com.example.ourspotifyapp.wrappedDisplays.StartingWrappedScreen;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    ActionBarDrawerToggle toggle;
    Button temporaryBtn;
    Button pastBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        DrawerLayout homeLayout = findViewById(R.id.homeLayout);
        toggle = new ActionBarDrawerToggle(this, homeLayout, R.string.open, R.string.close);
        homeLayout.addDrawerListener(toggle);
        toggle.syncState();

        ActionBar sab = this.getSupportActionBar();
        sab.setDisplayHomeAsUpEnabled(true);

        NavigationView nav  = findViewById(R.id.homeMenu);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.accountSettings) {
                    startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
                } else if (item.getItemId() == R.id.logOut) {
                    startActivity(new Intent(HomeActivity.this, MainActivity.class));
                }
                return true;
            }
        });

        temporaryBtn = findViewById(R.id.temp_button);
        temporaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, StartingWrappedScreen.class));
            }
        });

        pastBtn = findViewById(R.id.past_button);
        pastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, PastHome.class));
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
