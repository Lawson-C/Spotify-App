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

import com.example.ourspotifyapp.wrappedDisplays.StartingWrappedScreen;
import com.example.ourspotifyapp.R;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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
import okhttp3.Response;
import okhttp3.RequestBody;
import okhttp3.FormBody;



public class TopArtists extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_artists_display);

        TextView firstArtistTextView = (TextView) findViewById(R.id.top1_artists_text_view);
        TextView secondArtistTextView = (TextView) findViewById(R.id.top2_artists_text_view);
        TextView thirdArtistTextView = (TextView) findViewById(R.id.top3_artists_text_view);
        TextView fourthArtistTextView = (TextView) findViewById(R.id.top4_artists_text_view);
        TextView fifthArtistTextView = (TextView) findViewById(R.id.top5_artists_text_view);

        Map<String, String> trackToId = StartingWrappedScreen.getTrackToId();
        int count = 1;
        String trackIdToPlay = "";
        Log.d("test", trackToId.toString());
        for (Map.Entry<String, String> x : trackToId.entrySet()) {
            Log.d("hmmmm", String.valueOf(x));
            if (count == 1) {
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
        ArrayList<String> artistsListToDisplay = (ArrayList) StartingWrappedScreen.getArtistsToDisplay();
//        String formatted = StartingWrappedScreen.getArtistsToDisplay().toString().replace("[", " ").replace("]", "").replace(",", "\n");
        setTextAsync(artistsListToDisplay.get(0), firstArtistTextView);
        setTextAsync(artistsListToDisplay.get(1), secondArtistTextView);
        setTextAsync(artistsListToDisplay.get(2), thirdArtistTextView);
        setTextAsync(artistsListToDisplay.get(3), fourthArtistTextView);
        setTextAsync(artistsListToDisplay.get(4), fifthArtistTextView);


        Button getTracksButton = (Button) findViewById(R.id.get_top_tracks);
        getTracksButton.setOnClickListener((v) -> {
            mediaPlayer.stop();
            startActivity(new Intent(TopArtists.this, TopTracks.class));
        });
    }

    private void setTextAsync(final String text, TextView textView) {
        runOnUiThread(() -> textView.setText(text));
    }

}
