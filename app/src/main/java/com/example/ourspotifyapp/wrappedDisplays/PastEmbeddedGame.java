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

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class PastEmbeddedGame extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private int[] neededVals;
    private int correctVal = 0;

    private int points;
    private TextView pointCounter;

    private TextView firstSongText, secondSongText, thirdSongText, fourthSongText, fifthSongText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.embeddedgame);

        Button getGenresButton = (Button) findViewById(R.id.get_top_genres);


        String[] tracksList = PastHome.getSongNames();
        String[] urlList = PastHome.getUrlNames();

        neededVals = ThreadLocalRandom.current().ints(0, 50).distinct().limit(5).toArray();
        correctVal = ThreadLocalRandom.current().nextInt(0, 5);

        String previewUrl = PastHome.getUrlNames()[neededVals[correctVal]];

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

        firstSongText = findViewById(R.id.song_guess_one_text);
        secondSongText = findViewById(R.id.song_guess_two_text);
        thirdSongText = findViewById(R.id.song_guess_three_text);
        fourthSongText = findViewById(R.id.song_guess_four_text);
        fifthSongText = findViewById(R.id.song_guess_five_text);
        pointCounter = findViewById(R.id.point_counter);

        String[] tracksToDisplay = PastHome.getSongNames();
//        String formatted = StartingWrappedScreen.getTopTracksToDisplay().toString().replace("[", "").replace("]", "").replace(",", "\n");
//        setTextAsync(formatted, topTracksTextView);

        setTextAsync(tracksToDisplay[neededVals[0]], firstSongText);
        setTextAsync(tracksToDisplay[neededVals[1]], secondSongText);
        setTextAsync(tracksToDisplay[neededVals[2]], thirdSongText);
        setTextAsync(tracksToDisplay[neededVals[3]], fourthSongText);
        setTextAsync(tracksToDisplay[neededVals[4]], fifthSongText);

        getGenresButton.setOnClickListener((v) -> {
            mediaPlayer.stop();
            startActivity(new Intent(PastEmbeddedGame.this, PastTopGenres.class));
        });

        firstButton.setOnClickListener((v) -> {
            if (correctVal == 0) {
                Toast.makeText(PastEmbeddedGame.this, "Right answer!", Toast.LENGTH_SHORT).show();
                points++;
            } else {
                Toast.makeText(PastEmbeddedGame.this, "Wrong answer!", Toast.LENGTH_SHORT).show();
            }
            restartPlayer();

        });
        secondButton.setOnClickListener((v) -> {
            if (correctVal == 1) {
                Toast.makeText(PastEmbeddedGame.this, "Right answer!", Toast.LENGTH_SHORT).show();
                points++;
            } else {
                Toast.makeText(PastEmbeddedGame.this, "Wrong answer!", Toast.LENGTH_SHORT).show();
            }
            restartPlayer();
        });
        thirdButton.setOnClickListener((v) -> {
            if (correctVal == 2) {
                Toast.makeText(PastEmbeddedGame.this, "Right answer!", Toast.LENGTH_SHORT).show();
                points++;
            } else {
                Toast.makeText(PastEmbeddedGame.this, "Wrong answer!", Toast.LENGTH_SHORT).show();
            }
            restartPlayer();
        });
        fourthButton.setOnClickListener((v) -> {
            if (correctVal == 3) {
                Toast.makeText(PastEmbeddedGame.this, "Right answer!", Toast.LENGTH_SHORT).show();
                points++;
            } else {
                Toast.makeText(PastEmbeddedGame.this, "Wrong answer!", Toast.LENGTH_SHORT).show();
            }
            restartPlayer();
        });
        fifthButton.setOnClickListener((v) -> {
            if (correctVal == 4) {
                Toast.makeText(PastEmbeddedGame.this, "Right answer!", Toast.LENGTH_SHORT).show();
                points++;
            } else {
                Toast.makeText(PastEmbeddedGame.this, "Wrong answer!", Toast.LENGTH_SHORT).show();
            }
            restartPlayer();
        });

    }

    private void restartPlayer() {
        mediaPlayer.stop();

        neededVals = ThreadLocalRandom.current().ints(0, 50).distinct().limit(5).toArray();
        correctVal = ThreadLocalRandom.current().nextInt(0, 5);

        String previewUrl = PastHome.getUrlNames()[neededVals[correctVal]];
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
        String displayPoints = "Points: " + String.valueOf(points);

        setTextAsync(tracksToDisplay[neededVals[0]], firstSongText);
        setTextAsync(tracksToDisplay[neededVals[1]], secondSongText);
        setTextAsync(tracksToDisplay[neededVals[2]], thirdSongText);
        setTextAsync(tracksToDisplay[neededVals[3]], fourthSongText);
        setTextAsync(tracksToDisplay[neededVals[4]], fifthSongText);
        setTextAsync(displayPoints, pointCounter);
    }

    private void setTextAsync(final String text, TextView textView) {
        runOnUiThread(() -> textView.setText(text));
    }
}



