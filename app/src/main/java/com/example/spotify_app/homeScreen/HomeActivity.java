package com.example.spotify_app.homeScreen;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.spotify_app.R;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    DrawerLayout homeLayout;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        homeLayout = (DrawerLayout) findViewById(R.id.homeLayout);
        toggle = new ActionBarDrawerToggle(this, homeLayout, R.string.open, R.string.close);
        homeLayout.addDrawerListener(toggle);
        toggle.syncState();

        ActionBar sab = this.getSupportActionBar();
        sab.setDisplayHomeAsUpEnabled(true);

        NavigationView nav  = (NavigationView)findViewById(R.id.homeMenu);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Toast.makeText(getApplicationContext(),"Hello",Toast.LENGTH_SHORT).show();
                return true;
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
