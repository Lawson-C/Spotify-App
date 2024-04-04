package com.example.spotify_app;

        import androidx.appcompat.app.AppCompatActivity;
        import android.os.Bundle;
//        import account_login.xml;
        import android.content.Intent;
        import android.net.Uri;
        import android.util.Log;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.io.IOException;

        import okhttp3.Call;
        import okhttp3.Callback;
        import okhttp3.OkHttpClient;
        import okhttp3.Request;
        import okhttp3.Response;

public class AccountLogin extends AppCompatActivity {

    public static final String CLIENT_ID = "631dbb12d2b642bebb1e019886c20525"; // Client ID from spotify registered app
    public static final String REDIRECT_URI = "com.example.Group4Spotify-App://auth"; // Redirect URI from spotify registered app



    public static final int AUTH_TOKEN_REQUEST_CODE = 0;
    public static final int AUTH_CODE_REQUEST_CODE = 1;

    private final OkHttpClient mOkHttpClient = new OkHttpClient();

    private TextView tokenTextView, codeTextView, profileTextView, topTracksTextView, topArtistsTextView;


    SpotifyService spotifyService;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.xml.account_login);

        spotifyService = new SpotifyService(this); // Initialize SpotifyService

        // Initialize the views
        tokenTextView = findViewById(R.id.token_text_view);
        codeTextView = findViewById(R.id.code_text_view);
        profileTextView = findViewById(R.id.response_text_view);
        topTracksTextView = findViewById(R.id.top_tracks_text_view);
//        topArtistsTextView = findViewById(R.id_top_artists_text_view);

        // Initialize the buttons
        Button tokenBtn = findViewById(R.id.token_btn);
        Button codeBtn = findViewById(R.id.code_btn);
        Button profileBtn = findViewById(R.id.profile_btn);
        Button tracksBtn = findViewById(R.id.top_tracks_btn);
//        Button artistsBtn = findViewById(R.id.playlistsBtn);

        // Set the click listeners for the buttons
        tokenBtn.setOnClickListener(v -> spotifyService.getToken());
        codeBtn.setOnClickListener(v -> spotifyService.getCode());
        profileBtn.setOnClickListener(v -> spotifyService.onGetUserProfileInfoClicked(tokenTextView, profileTextView, "fall through to else statement"));
        tracksBtn.setOnClickListener(v -> spotifyService.onGetUserProfileInfoClicked(tokenTextView, profileTextView, "top_tracks"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        spotifyService.onActivityResult(requestCode, resultCode, data, tokenTextView, codeTextView);
    }

    @Override
    protected void onDestroy() {
        spotifyService.cancelCall();
        super.onDestroy();
    }
}
