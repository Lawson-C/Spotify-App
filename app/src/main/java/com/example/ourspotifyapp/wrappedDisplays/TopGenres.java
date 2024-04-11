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
import com.example.ourspotifyapp.database.LocalAccountEntry;
import com.example.ourspotifyapp.database.StorageSystem;
import com.example.ourspotifyapp.database.WrappedSongEntry;
import com.example.ourspotifyapp.loginScreen.LoginActivity;
import com.example.ourspotifyapp.ui.SignUpActivity;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
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

        TextView firstTopGenresTextView = findViewById(R.id.top1_genres_text_view);
        TextView secondTopGenresTextView = findViewById(R.id.top2_genres_text_view);
        TextView thirdTopGenresTextView = findViewById(R.id.top3_genres_text_view);
        TextView fourthTopGenresTextView = findViewById(R.id.top4_genres_text_view);
        TextView fifthTopGenresTextView = findViewById(R.id.top5_genres_text_view);




        Button returnToStart = (Button) findViewById(R.id.returnToStart);
        Button saveData = (Button) findViewById(R.id.saveWrappedData);

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
//        setTextAsync(genresToDisplay.toString().replace("[", " ").replace("]", "").replace(",", "\n"), topGenresTextView);
        setTextAsync(genresToDisplay.get(0), firstTopGenresTextView);
        setTextAsync(genresToDisplay.get(1), secondTopGenresTextView);
        setTextAsync(genresToDisplay.get(2), thirdTopGenresTextView);
        setTextAsync(genresToDisplay.get(3), fourthTopGenresTextView);
        setTextAsync(genresToDisplay.get(4), fifthTopGenresTextView);

        returnToStart.setOnClickListener((v) -> {

            mediaPlayer.stop();

            StartingWrappedScreen.setArtistsToDisplay(new ArrayList<>());
            StartingWrappedScreen.setArtistToId(new HashMap<>());

            StartingWrappedScreen.setTopGenres(new ArrayList<>());

            StartingWrappedScreen.setTopTracksToDisplay(new ArrayList<>());
            StartingWrappedScreen.setTrackToId(new HashMap<>());



            startActivity(new Intent(TopGenres.this, StartingWrappedScreen.class));
        });

        saveData.setOnClickListener((v) -> {

            String[] songNames = new String[5];
            Map<String, String> trackIdMap = StartingWrappedScreen.getTrackToId();

            Log.d("track id map", trackIdMap.toString());
            String[] audioUrls = new String[5];

            int tracker = 0;
            for (Map.Entry<String, String> x : trackIdMap.entrySet()) {
                Log.d("hmmmm", String.valueOf(x));
                Log.d("key", x.getKey());
                Log.d("value", x.getValue());
                String songId = x.getValue();
                songNames[tracker] = x.getKey();
                String urlTest = StartingWrappedScreen.getTrackAudios().get(songId);
                Log.d("audio url test", urlTest);
                audioUrls[tracker] = urlTest;
                tracker++;
            }

            Log.d("songs", String.join(", ", songNames));
            Log.d("urls", String.join(", ", audioUrls));

            String[] artistNames = StartingWrappedScreen.getArtistsToDisplay().toArray(new String[0]);
            String[] genreNames = StartingWrappedScreen.getTopGenres().toArray(new String[0]);

            Log.d("artists", String.join(", ", artistNames));
            Log.d("genres", String.join(", ", genreNames));

            Calendar cal = Calendar.getInstance();
            int date = cal.get(Calendar.DAY_OF_YEAR);
            String time_frame = "";
            String months_wrapped = StartingWrappedScreen.desired_time_frame;
            if (months_wrapped.equals("short_term")) {
                time_frame = "4";
            } else if (months_wrapped.equals("medium_term")){
                time_frame = "6";
            } else if (months_wrapped.equals("long_term")) {
                time_frame = "12";
            }

            int id = Integer.valueOf(String.valueOf(date) + time_frame + String.valueOf(LoginActivity.currentUserHash) );

            Log.d("date", String.valueOf(date));
            Log.d("time frame", String.valueOf(time_frame));
            Log.d("id", String.valueOf(id));

            try {
                String[] idCheck = StorageSystem.readWrappedEntryValue(WrappedSongEntry.TABLE_NAME, "id", String.valueOf(id), WrappedSongEntry.COLUMN_ID);
                if (idCheck.length != 0) {
                    Log.d("duplicate?", String.join(", ", idCheck));
                    Toast.makeText(TopGenres.this, "Wrapped for today already exists!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Log.d("no duplicate", "yay?");
                }
            } catch (Exception e) {
                Log.d("no duplicate", "something happened? " + e);
            }

            StorageSystem.writeWrappedSong(id, songNames, audioUrls, LoginActivity.currentUserHash);
            StorageSystem.writeWrappedArtist(id, artistNames, LoginActivity.currentUserHash);
            StorageSystem.writeWrappedGenre(id, genreNames, LoginActivity.currentUserHash);

            Log.d("everything worked fine", "yayy!!");


            // StorageSystem.writeWrappedSong()
        });
    }


    private void setTextAsync(final String text, TextView textView) {
        runOnUiThread(() -> textView.setText(text));
    }

}