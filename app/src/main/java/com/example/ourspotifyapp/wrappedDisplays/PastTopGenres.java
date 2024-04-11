package com.example.ourspotifyapp.wrappedDisplays;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ourspotifyapp.R;
import com.example.ourspotifyapp.database.StorageSystem;
import com.example.ourspotifyapp.database.WrappedSongEntry;
import com.example.ourspotifyapp.loginScreen.LoginActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;

public class PastTopGenres extends AppCompatActivity {

    public static final String CLIENT_ID = "631dbb12d2b642bebb1e019886c20525";
    public static final String REDIRECT_URI = "com.example.ourspotifyapp://auth";
    public static final int AUTH_TOKEN_REQUEST_CODE = 0;
    public static final int AUTH_CODE_REQUEST_CODE = 1;
    private final OkHttpClient mOkHttpClient = new OkHttpClient();
    private String mAccessToken, mAccessCode;
    private static boolean responseReceived = false;
    private Call mCall;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_top_genres);

        TextView firstTopGenresTextView = findViewById(R.id.top1_genres_text_view);
        TextView secondTopGenresTextView = findViewById(R.id.top2_genres_text_view);
        TextView thirdTopGenresTextView = findViewById(R.id.top3_genres_text_view);
        TextView fourthTopGenresTextView = findViewById(R.id.top4_genres_text_view);
        TextView fifthTopGenresTextView = findViewById(R.id.top5_genres_text_view);

        Button returnToStart = (Button) findViewById(R.id.returnToStart);

        int trackIdToPlay = 2;
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

        String[] genresToDisplay = PastHome.getGenreNames();
//        setTextAsync(genresToDisplay.toString().replace("[", " ").replace("]", "").replace(",", "\n"), topGenresTextView);
        setTextAsync(genresToDisplay[0], firstTopGenresTextView);
        setTextAsync(genresToDisplay[1], secondTopGenresTextView);
        setTextAsync(genresToDisplay[2], thirdTopGenresTextView);
        setTextAsync(genresToDisplay[3], fourthTopGenresTextView);
        setTextAsync(genresToDisplay[4], fifthTopGenresTextView);

        returnToStart.setOnClickListener((v) -> {

            mediaPlayer.stop();

            StartingWrappedScreen.setArtistsToDisplay(new ArrayList<>());
            StartingWrappedScreen.setArtistToId(new HashMap<>());

            StartingWrappedScreen.setTopGenres(new ArrayList<>());

            StartingWrappedScreen.setTopTracksToDisplay(new ArrayList<>());
            StartingWrappedScreen.setTrackToId(new HashMap<>());



            startActivity(new Intent(PastTopGenres.this, PastHome.class));
        });
    }


    private void setTextAsync(final String text, TextView textView) {
        runOnUiThread(() -> textView.setText(text));
    }

}