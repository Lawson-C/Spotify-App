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
import java.util.Arrays;
import java.util.Map;

public class PastTopArtists extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_artists_display);

        TextView artistsTextView = (TextView) findViewById(R.id.top_artists_text_view);

        int trackIdtoPlay = 0;
        String previewUrl = PastHome.getUrlNames()[trackIdtoPlay];

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

        String formatted = Arrays.asList(PastHome.getArtistNames()).toString().replace("[", " ").replace("]", "").replace(",", "\n");
        setTextAsync(formatted, artistsTextView); // this is where the lists of artit previously calculated are finally displayed
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
