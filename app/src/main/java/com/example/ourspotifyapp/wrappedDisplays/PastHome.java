package com.example.ourspotifyapp.wrappedDisplays;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ourspotifyapp.MainActivity;
import com.example.ourspotifyapp.R;
import com.example.ourspotifyapp.database.StorageSystem;
import com.example.ourspotifyapp.database.WrappedArtistEntry;
import com.example.ourspotifyapp.database.WrappedGenreEntry;
import com.example.ourspotifyapp.database.WrappedSongEntry;
import com.example.ourspotifyapp.homeScreen.HomeActivity;
import com.example.ourspotifyapp.loginScreen.LoginActivity;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class PastHome extends AppCompatActivity {

    Button generate;
    Button back;

    CheckBox shortTerm, mediumTerm, longTerm;
    EditText day, month, year;

    private static String[] songNames, artistNames, genreNames, urlNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.past_summaries);

        day = findViewById(R.id.dayInput);
        month = findViewById(R.id.monthInput);
        year = findViewById(R.id.yearInput);

        shortTerm = findViewById(R.id.shortTerm);
        mediumTerm = findViewById(R.id.mediumTerm);
        longTerm = findViewById(R.id.longTerm);

        back = findViewById(R.id.backButton);
        generate = findViewById(R.id.generatePastWrapped);

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int timeFrame = 0;

                int dayVal = Integer.parseInt(day.getText().toString());
                int monthVal = Integer.parseInt(month.getText().toString());
                int yearVal = Integer.parseInt(year.getText().toString());

                Calendar wantedCal = new GregorianCalendar();
                wantedCal.set(Calendar.YEAR, yearVal);
                wantedCal.set(Calendar.MONTH, monthVal - 1);
                wantedCal.set(Calendar.DAY_OF_MONTH, dayVal);

                int yearDay = wantedCal.get(Calendar.DAY_OF_YEAR);

                if (shortTerm.isChecked()) {
                    timeFrame = 4;
                } else if (mediumTerm.isChecked()) {
                    timeFrame = 6;
                } else if (longTerm.isChecked()) {
                    timeFrame = 12;
                }

                int idSearch = Integer.valueOf(String.valueOf(yearDay) + String.valueOf(timeFrame) + String.valueOf(LoginActivity.currentUserHash));
                Log.d("our id", String.valueOf(idSearch));
                try {
                    String[][] songDataQuery = StorageSystem.readWrappedEntry(WrappedSongEntry.TABLE_NAME, "id", String.valueOf(idSearch));
                    String[][] artistDataQuery = StorageSystem.readWrappedEntry(WrappedArtistEntry.TABLE_NAME, "id", String.valueOf(idSearch));
                    String[][] genreDataQuery = StorageSystem.readWrappedEntry(WrappedGenreEntry.TABLE_NAME, "id", String.valueOf(idSearch));

                    Log.d("song data", String.join(", ", songDataQuery[0]));
                    Log.d("artist data", String.join(", ", artistDataQuery[0]));
                    Log.d("genre data", String.join(", ", genreDataQuery[0]));
                    String[] songData = songDataQuery[0];
                    songNames = Arrays.copyOfRange(songData, 1, 6);
                    urlNames = Arrays.copyOfRange(songData, 6, 11);
                    String[] artistData = artistDataQuery[0];
                    artistNames = Arrays.copyOfRange(artistData, 1, 6);
                    String[] genreData = genreDataQuery[0];
                    genreNames = Arrays.copyOfRange(genreData, 1, 6);

                    Log.d("song names", String.join(", ", songNames));
                    Log.d("url names", String.join(", ", urlNames));
                    Log.d("artist names", String.join(", ", artistNames));
                    Log.d("genre names", String.join(", ", genreNames));



                } catch (Exception e) {
                    Log.d("nothing found", "i guess?");
                    Toast.makeText(PastHome.this, "No wrapped exists with this date, try again!", Toast.LENGTH_SHORT).show();
                    return;
                }

                startActivity(new Intent(PastHome.this, PastTopArtists.class));

            }
        });

        back.setOnClickListener(v -> openHomeScreen());
    }

    public void openHomeScreen() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public static String[] getSongNames() {
        return songNames;
    }
    public static String[] getUrlNames() {
        return urlNames;
    }
    public static String[] getArtistNames() {
        return artistNames;
    }
    public static String[] getGenreNames() {
        return genreNames;
    }

}
