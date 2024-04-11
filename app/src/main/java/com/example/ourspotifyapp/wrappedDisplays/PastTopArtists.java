package com.example.ourspotifyapp.wrappedDisplays;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ourspotifyapp.R;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;

public class PastTopArtists extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_artists_display);

        TextView firstArtistTextView = (TextView) findViewById(R.id.top1_artists_text_view);
        TextView secondArtistTextView = (TextView) findViewById(R.id.top2_artists_text_view);
        TextView thirdArtistTextView = (TextView) findViewById(R.id.top3_artists_text_view);
        TextView fourthArtistTextView = (TextView) findViewById(R.id.top4_artists_text_view);
        TextView fifthArtistTextView = (TextView) findViewById(R.id.top5_artists_text_view);

        int trackIdToPlay = 0;
        String previewUrl = PastHome.getUrlNames()[trackIdToPlay];

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

        String[] artistsListToDisplay = PastHome.getSongNames();
//        String formatted = StartingWrappedScreen.getArtistsToDisplay().toString().replace("[", " ").replace("]", "").replace(",", "\n");
        setTextAsync(artistsListToDisplay[0], firstArtistTextView);
        setTextAsync(artistsListToDisplay[1], secondArtistTextView);
        setTextAsync(artistsListToDisplay[2], thirdArtistTextView);
        setTextAsync(artistsListToDisplay[3], fourthArtistTextView);
        setTextAsync(artistsListToDisplay[4], fifthArtistTextView);

        Button getTracksButton = (Button) findViewById(R.id.get_top_tracks);
        getTracksButton.setOnClickListener((v) -> {
            mediaPlayer.stop();
            startActivity(new Intent(PastTopArtists.this, PastTopSongs.class));
        });
    }

    private void setTextAsync(final String text, TextView textView) {
        runOnUiThread(() -> textView.setText(text));
    }

}

