package com.example.ourspotifyapp.wrappedDisplays;


import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.ourspotifyapp.databinding.ActivityTopTracksBinding;


import com.example.ourspotifyapp.R;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TopTracks extends AppCompatActivity {

    public static final String CLIENT_ID = "631dbb12d2b642bebb1e019886c20525";
    public static final String REDIRECT_URI = "com.example.ourspotifyapp://auth";
    private AppBarConfiguration appBarConfiguration;
    private ActivityTopTracksBinding binding;

    static List<String> topGenres = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_tracks);
        Map<String, String> trackToId = TopArtists.getTrackToId();
        List<String> artistNameList = new ArrayList<>();
        int count = 1;
        for (Map.Entry<String, String> artist_id : trackToId.entrySet()) {
            String trackName = artist_id.getKey();
            artistNameList.add(count + ": " + trackName);
            count++;
        }
        Button getGenresButton = (Button) findViewById(R.id.get_top_genres);

        TextView topTracksTextView = findViewById(R.id.top_tracks_text_view);
        String formatted = TopArtists.getTopTracksToDisplay().toString().replace("[", "").replace("]", "").replace(",", "\n");
        setTextAsync(formatted, topTracksTextView);

        getGenresButton.setOnClickListener((v) -> {
            calculateTopGenres();
            startActivity(new Intent(TopTracks.this, TopGenres.class));
        });

    }

    private void calculateTopGenres() {
        Map<String, String> myMap = new HashMap<>();
        myMap = StartingWrappedScreen.getGenreCount();
        String key1 = "Default, means genres haven't been collected";
        String key2 = "Default, means genres haven't been collected";
        String key3 = "Default, means genres haven't been collected";
        String key4 = "Default, means genres haven't been collected";
        String key5 = "Default, means genres haven't been collected";
        int max1 = 0;
        int max2 = 0;
        int max3 = 0;
        int max4 = 0;
        int max5 = 0;
        for (Map.Entry<String, String> entry : myMap.entrySet()) {
            String currKey = entry.getKey();
            int currVal = Integer.parseInt(entry.getValue());
            if (currVal > max1) {
                max1 = currVal;
                key1 = currKey;
            } else if (currVal > max2) {
                max2 = currVal;
                key2 = currKey;
            } else if (currVal > max3) {
                max3 = currVal;
                key3 = currKey;
            } else if (currVal > max4) {
                max4 = currVal;
                key4 = currKey;
            } else if (currVal >= max5) {
                max5 = currVal;
                key5 = currKey;
            }
        }

        topGenres.add(key1);
        topGenres.add(key2);
        topGenres.add(key3);
        topGenres.add(key4);
        topGenres.add(key5);

    }

    private void setTextAsync(final String text, TextView textView) {
        runOnUiThread(() -> textView.setText(text));
    }

    public static List<String> getTopGenres() {
        return topGenres;
    }
    public static void setTopGenres(List<String> topGenres) {
        TopTracks.topGenres = topGenres;
    }
}



