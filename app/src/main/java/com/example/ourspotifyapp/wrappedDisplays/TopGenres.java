package com.example.ourspotifyapp.wrappedDisplays;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ourspotifyapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TopGenres extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_top_genres);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        TextView topGenresTextView = findViewById(R.id.top_genres_text_view);
        Button returnToStart = (Button) findViewById(R.id.returnToStart);

        List<String> genresToDisplay = TopTracks.getTopGenres();
        setTextAsync(genresToDisplay.toString().replace("[", " ").replace("]", "").replace(",", "\n"), topGenresTextView);

        returnToStart.setOnClickListener((v) -> {

            StartingWrappedScreen.setArtistsToDisplay(new ArrayList<>());
            StartingWrappedScreen.setArtistToId(new HashMap<>());

            TopTracks.setTopGenres(new ArrayList<>());

            TopArtists.setTopTracksToDisplay(new ArrayList<>());
            TopArtists.setTrackToId(new HashMap<>());



            startActivity(new Intent(TopGenres.this, StartingWrappedScreen.class));
        });
    }


    private void setTextAsync(final String text, TextView textView) {
        runOnUiThread(() -> textView.setText(text));
    }
}