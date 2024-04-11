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
import com.example.ourspotifyapp.ui.SignUpActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class PastEmbeddedGame extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private int neededVal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.embeddedgame);

        Button getGenresButton = (Button) findViewById(R.id.get_top_genres);


        neededVal = ThreadLocalRandom.current().nextInt(0, 5);
        String previewUrl = PastHome.getUrlNames()[neededVal];

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

        Button firstButton = findViewById(R.id.song_guess_one);
        Button secondButton = findViewById(R.id.song_guess_two);
        Button thirdButton = findViewById(R.id.song_guess_three);
        Button fourthButton = findViewById(R.id.song_guess_four);
        Button fifthButton = findViewById(R.id.song_guess_five);

        TextView firstSongText = findViewById(R.id.song_guess_one_text);
        TextView secondSongText = findViewById(R.id.song_guess_two_text);
        TextView thirdSongText = findViewById(R.id.song_guess_three_text);
        TextView fourthSongText = findViewById(R.id.song_guess_four_text);
        TextView fifthSongText = findViewById(R.id.song_guess_five_text);

        String[] tracksToDisplay = PastHome.getSongNames();
//        String formatted = StartingWrappedScreen.getTopTracksToDisplay().toString().replace("[", "").replace("]", "").replace(",", "\n");
//        setTextAsync(formatted, topTracksTextView);

        setTextAsync(tracksToDisplay[0], firstSongText);
        setTextAsync(tracksToDisplay[1], secondSongText);
        setTextAsync(tracksToDisplay[2], thirdSongText);
        setTextAsync(tracksToDisplay[3], fourthSongText);
        setTextAsync(tracksToDisplay[4], fifthSongText);

        getGenresButton.setOnClickListener((v) -> {
            mediaPlayer.stop();
            startActivity(new Intent(PastEmbeddedGame.this, PastTopGenres.class));
        });

        firstButton.setOnClickListener((v) -> {
            if (neededVal == 0) {
                Toast.makeText(PastEmbeddedGame.this, "Right answer!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(PastEmbeddedGame.this, "Wrong answer!", Toast.LENGTH_SHORT).show();
            }
            restartPlayer();

        });
        secondButton.setOnClickListener((v) -> {
            if (neededVal == 1) {
                Toast.makeText(PastEmbeddedGame.this, "Right answer!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(PastEmbeddedGame.this, "Wrong answer!", Toast.LENGTH_SHORT).show();
            }
            restartPlayer();
        });
        thirdButton.setOnClickListener((v) -> {
            if (neededVal == 2) {
                Toast.makeText(PastEmbeddedGame.this, "Right answer!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(PastEmbeddedGame.this, "Wrong answer!", Toast.LENGTH_SHORT).show();
            }
            restartPlayer();
        });
        fourthButton.setOnClickListener((v) -> {
            if (neededVal == 3) {
                Toast.makeText(PastEmbeddedGame.this, "Right answer!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(PastEmbeddedGame.this, "Wrong answer!", Toast.LENGTH_SHORT).show();
            }
            restartPlayer();
        });
        fifthButton.setOnClickListener((v) -> {
            if (neededVal == 4) {
                Toast.makeText(PastEmbeddedGame.this, "Right answer!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(PastEmbeddedGame.this, "Wrong answer!", Toast.LENGTH_SHORT).show();
            }
            restartPlayer();
        });

    }

    private void restartPlayer() {
        mediaPlayer.stop();

        neededVal = ThreadLocalRandom.current().nextInt(0, 5);
        String previewUrl = PastHome.getUrlNames()[neededVal];
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
    }

    private void setTextAsync(final String text, TextView textView) {
        runOnUiThread(() -> textView.setText(text));
    }
}



