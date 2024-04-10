package com.example.ourspotifyapp.wrappedDisplays;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

public class TopGenres extends AppCompatActivity {

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
        setContentView(R.layout.activity_top_genres);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        TextView topGenresTextView = findViewById(R.id.top_genres_text_view);
        Button returnToStart = (Button) findViewById(R.id.returnToStart);

        Map<String, String> trackToId = StartingWrappedScreen.getTrackToId();
        int count = 1;
        String trackIdToPlay = "";
        Log.d("test", trackToId.toString());
        for (Map.Entry<String, String> x : trackToId.entrySet()) {
            Log.d("hmmmm", String.valueOf(x));
            if (count == 3) {
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

        List<String> genresToDisplay = StartingWrappedScreen.getTopGenres();
        setTextAsync(genresToDisplay.toString().replace("[", " ").replace("]", "").replace(",", "\n"), topGenresTextView);

        returnToStart.setOnClickListener((v) -> {

            mediaPlayer.stop();

            StartingWrappedScreen.setArtistsToDisplay(new ArrayList<>());
            StartingWrappedScreen.setArtistToId(new HashMap<>());

            StartingWrappedScreen.setTopGenres(new ArrayList<>());

            StartingWrappedScreen.setTopTracksToDisplay(new ArrayList<>());
            StartingWrappedScreen.setTrackToId(new HashMap<>());



            startActivity(new Intent(TopGenres.this, StartingWrappedScreen.class));
        });
    }


    private void setTextAsync(final String text, TextView textView) {
        runOnUiThread(() -> textView.setText(text));
    }

}