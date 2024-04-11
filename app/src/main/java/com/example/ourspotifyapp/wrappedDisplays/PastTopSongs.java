package com.example.ourspotifyapp.wrappedDisplays;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ourspotifyapp.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class PastTopSongs extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_tracks);

        Button getGenresButton = (Button) findViewById(R.id.get_top_genres);

        TextView firstTopTracksTextView = findViewById(R.id.top1_tracks_text_view);
        TextView secondTopTracksTextView = findViewById(R.id.top2_tracks_text_view);
        TextView thirdTopTracksTextView = findViewById(R.id.top3_tracks_text_view);
        TextView fourthTopTracksTextView = findViewById(R.id.top4_tracks_text_view);
        TextView fifthTopTracksTextView = findViewById(R.id.top5_tracks_text_view);

        int trackIdToPlay = 1;
        String previewUrl = PastHome.getUrlNames()[trackIdToPlay];

        Log.d("tag yayyy", String.valueOf(trackIdToPlay));
        Log.d("url correct?", previewUrl);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build());

        try {
            mediaPlayer.setDataSource(previewUrl);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] tracksToDisplay = PastHome.getSongNames();
        setTextAsync(tracksToDisplay[0], firstTopTracksTextView);
        setTextAsync(tracksToDisplay[1], secondTopTracksTextView);
        setTextAsync(tracksToDisplay[2], thirdTopTracksTextView);
        setTextAsync(tracksToDisplay[3], fourthTopTracksTextView);
        setTextAsync(tracksToDisplay[4], fifthTopTracksTextView);

        getGenresButton.setOnClickListener((v) -> {
            mediaPlayer.stop();
            startActivity(new Intent(PastTopSongs.this, PastTopGenres.class));
        });

    }

    private void setTextAsync(final String text, TextView textView) {
        runOnUiThread(() -> textView.setText(text));
    }

}



