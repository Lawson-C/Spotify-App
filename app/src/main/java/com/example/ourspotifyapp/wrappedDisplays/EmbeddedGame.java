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

public class EmbeddedGame extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private int[] neededVals;
    private int correctVal;

    private int points;
    private TextView pointCounter;
    private TextView firstSongText, secondSongText, thirdSongText, fourthSongText, fifthSongText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.embeddedgame);

        Button getGenresButton = (Button) findViewById(R.id.get_top_genres);

        Map<String, String> trackToId = StartingWrappedScreen.getTrackToId();

        int count = 0;
        neededVals = ThreadLocalRandom.current().ints(0, 50).distinct().limit(5).toArray();
        correctVal = ThreadLocalRandom.current().nextInt(0, 5);
        ArrayList<String> tracksToDisplay = new ArrayList<>();
        String trackIdToPlay = "";
        Log.d("test", trackToId.toString());
        for (Map.Entry<String, String> x : trackToId.entrySet()) {
            Log.d("hmmmm", String.valueOf(x));
            tracksToDisplay.add(x.getKey());
            if (count == neededVals[correctVal]) {
                trackIdToPlay = x.getValue();
            }
            count++;
        }

        Log.d("tag yayyy", trackIdToPlay);

        String previewUrl = StartingWrappedScreen.getTrackAudios().get(trackIdToPlay);
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

        tracksToDisplay = (ArrayList) StartingWrappedScreen.getTopTracksToDisplay();
        Log.d("tracks", tracksToDisplay.toString());
//        String formatted = StartingWrappedScreen.getTopTracksToDisplay().toString().replace("[", "").replace("]", "").replace(",", "\n");
//        setTextAsync(formatted, topTracksTextView);

        setTextAsync(tracksToDisplay.get(neededVals[0]), firstSongText);
        setTextAsync(tracksToDisplay.get(neededVals[1]), secondSongText);
        setTextAsync(tracksToDisplay.get(neededVals[2]), thirdSongText);
        setTextAsync(tracksToDisplay.get(neededVals[3]), fourthSongText);
        setTextAsync(tracksToDisplay.get(neededVals[4]), fifthSongText);

        getGenresButton.setOnClickListener((v) -> {
            mediaPlayer.stop();
            startActivity(new Intent(EmbeddedGame.this, TopGenres.class));
        });

        firstButton.setOnClickListener((v) -> {
            if (correctVal == 0) {
                Toast.makeText(EmbeddedGame.this, "Right answer!", Toast.LENGTH_SHORT).show();
                points++;
            } else {
                Toast.makeText(EmbeddedGame.this, "Wrong answer!", Toast.LENGTH_SHORT).show();
            }
            restartPlayer();

        });
        secondButton.setOnClickListener((v) -> {
            if (correctVal == 1) {
                Toast.makeText(EmbeddedGame.this, "Right answer!", Toast.LENGTH_SHORT).show();
                points++;
            } else {
                Toast.makeText(EmbeddedGame.this, "Wrong answer!", Toast.LENGTH_SHORT).show();
            }
            restartPlayer();
        });
        thirdButton.setOnClickListener((v) -> {
            if (correctVal == 2) {
                Toast.makeText(EmbeddedGame.this, "Right answer!", Toast.LENGTH_SHORT).show();
                points++;
            } else {
                Toast.makeText(EmbeddedGame.this, "Wrong answer!", Toast.LENGTH_SHORT).show();
            }
            restartPlayer();
        });
        fourthButton.setOnClickListener((v) -> {
            if (correctVal == 3) {
                Toast.makeText(EmbeddedGame.this, "Right answer!", Toast.LENGTH_SHORT).show();
                points++;
            } else {
                Toast.makeText(EmbeddedGame.this, "Wrong answer!", Toast.LENGTH_SHORT).show();
            }
            restartPlayer();
        });
        fifthButton.setOnClickListener((v) -> {
            if (correctVal == 4) {
                Toast.makeText(EmbeddedGame.this, "Right answer!", Toast.LENGTH_SHORT).show();
                points++;
            } else {
                Toast.makeText(EmbeddedGame.this, "Wrong answer!", Toast.LENGTH_SHORT).show();
            }
            restartPlayer();
        });

    }

    private void restartPlayer() {
        mediaPlayer.stop();
        Map<String, String> trackToId = StartingWrappedScreen.getTrackToId();
        int count = 0;
        neededVals = ThreadLocalRandom.current().ints(0, 50).distinct().limit(5).toArray();
        correctVal = ThreadLocalRandom.current().nextInt(0, 5);
        String trackIdToPlay = "";
        Log.d("test", trackToId.toString());
        for (Map.Entry<String, String> x : trackToId.entrySet()) {
            Log.d("hmmmm", String.valueOf(x));
            if (count == neededVals[correctVal]) {
                trackIdToPlay = x.getValue();
            }
            count++;
        }

        Log.d("tag yayyy", trackIdToPlay);

        String previewUrl = StartingWrappedScreen.getTrackAudios().get(trackIdToPlay);
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

        ArrayList<String> tracksToDisplay = StartingWrappedScreen.getTopTracksToDisplay();
        String displayPoints = "Points: " + String.valueOf(points);

        setTextAsync(tracksToDisplay.get(neededVals[0]), firstSongText);
        setTextAsync(tracksToDisplay.get(neededVals[1]), secondSongText);
        setTextAsync(tracksToDisplay.get(neededVals[2]), thirdSongText);
        setTextAsync(tracksToDisplay.get(neededVals[3]), fourthSongText);
        setTextAsync(tracksToDisplay.get(neededVals[4]), fifthSongText);
        setTextAsync(displayPoints, pointCounter);
    }

    private void setTextAsync(final String text, TextView textView) {
        runOnUiThread(() -> textView.setText(text));
    }
}



