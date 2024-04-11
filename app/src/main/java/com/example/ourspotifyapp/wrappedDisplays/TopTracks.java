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
import android.widget.Toast;


import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.ourspotifyapp.databinding.ActivityTopTracksBinding;


import com.example.ourspotifyapp.R;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class TopTracks extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_tracks);
        Map<String, String> trackToId = StartingWrappedScreen.getTrackToId();
        List<String> artistNameList = new ArrayList<>();
        int count = 1;
        for (Map.Entry<String, String> artist_id : trackToId.entrySet()) {
            String trackName = artist_id.getKey();
            artistNameList.add(count + ": " + trackName);
            count++;
        }
        Button getGameButton = (Button) findViewById(R.id.embedded_game);

        count = 1;
        String trackIdToPlay = "";
        Log.d("test", trackToId.toString());
        for (Map.Entry<String, String> x : trackToId.entrySet()) {
            Log.d("hmmmm", String.valueOf(x));
            if (count == 2) {
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

        TextView firstTopTracksTextView = findViewById(R.id.top1_tracks_text_view);
        TextView secondTopTracksTextView = findViewById(R.id.top2_tracks_text_view);
        TextView thirdTopTracksTextView = findViewById(R.id.top3_tracks_text_view);
        TextView fourthTopTracksTextView = findViewById(R.id.top4_tracks_text_view);
        TextView fifthTopTracksTextView = findViewById(R.id.top5_tracks_text_view);
        List<String> tracksToDisplay = StartingWrappedScreen.getTopTracksToDisplay();
//        String formatted = StartingWrappedScreen.getTopTracksToDisplay().toString().replace("[", "").replace("]", "").replace(",", "\n");
//        setTextAsync(formatted, topTracksTextView);
        setTextAsync(tracksToDisplay.get(0), firstTopTracksTextView);
        setTextAsync(tracksToDisplay.get(1), secondTopTracksTextView);
        setTextAsync(tracksToDisplay.get(2), thirdTopTracksTextView);
        setTextAsync(tracksToDisplay.get(3), fourthTopTracksTextView);
        setTextAsync(tracksToDisplay.get(4), fifthTopTracksTextView);

        getGameButton.setOnClickListener((v) -> {
            mediaPlayer.stop();
            startActivity(new Intent(TopTracks.this, EmbeddedGame.class));
        });

    }

    private void setTextAsync(final String text, TextView textView) {
        runOnUiThread(() -> textView.setText(text));
    }

}



