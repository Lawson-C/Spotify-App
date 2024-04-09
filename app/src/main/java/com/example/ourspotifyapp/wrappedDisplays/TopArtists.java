package com.example.ourspotifyapp.wrappedDisplays;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class TopArtists extends AppCompatActivity {

    public static final String CLIENT_ID = "631dbb12d2b642bebb1e019886c20525";
    public static final String REDIRECT_URI = "com.example.ourspotifyapp://auth";
    public static final int AUTH_TOKEN_REQUEST_CODE = 0;
    public static final int AUTH_CODE_REQUEST_CODE = 1;
    private final OkHttpClient mOkHttpClient = new OkHttpClient();
    private String mAccessToken, mAccessCode;
    private static boolean responseReceived = false;
    private Call mCall;
    public TextView artistsTextView;

    private static Map<String, String> trackToId = new HashMap<>();


    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_artists_display);

        TextView artistsTextView = (TextView) findViewById(R.id.top_artists_text_view);

        // initialize and write the top 5 artists in the textView
        Map<String, String> artistToId = StartingWrappedScreen.getArtistsToId(); // fix this to get the map constructed in startingWrappedScreen to this variable
        List<String> artistNameList = new ArrayList<>();
        int count = 1;
        for (Map.Entry<String, String> artist_id : artistToId.entrySet()) {
            String artist = artist_id.getKey();
            artistNameList.add(count + ": " + artist);
            count++;
        }

        getToken();

        String formatted = artistNameList.toString().replace("[", " ").replace("]", "").replace(",", "\n" + "\n");
        setTextAsync(formatted, artistsTextView); // this is where the lists of artit previously calculated are finally displayed
        Button getTracksButton = (Button) findViewById(R.id.get_top_tracks);
        getTracksButton.setOnClickListener((v) -> {
            getTopTracks(StartingWrappedScreen.getDesiredTimeFrame());

            startActivity(new Intent(TopArtists.this, TopTracks.class));
        });
    }

    public void getToken() {
        try {
            final AuthorizationRequest request = getAuthenticationRequest(AuthorizationResponse.Type.TOKEN);
            AuthorizationClient.openLoginActivity(TopArtists.this, AUTH_TOKEN_REQUEST_CODE, request);
        } catch (NullPointerException e) {
            Log.e("MainActivity", "getToken failed", e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, data);

        // Check which request code is present (if any)
        if (AUTH_TOKEN_REQUEST_CODE == requestCode) {
            mAccessToken = response.getAccessToken();
        } else if (AUTH_CODE_REQUEST_CODE == requestCode) {
            mAccessCode = response.getCode();
        }
    }


    public Map<String, String> getTopTracks(String specified_time_range) {
        if (mAccessToken == null) {
            Toast.makeText(this, "You need to get an access token first!", Toast.LENGTH_SHORT).show();
            return new HashMap<>(); // keeps it from continuing after this is printed
        }
        Map<String, String> trackToId = new HashMap<>();
        // Create a request to get the user profile
        final Request request = new Request.Builder()
                .url(String.format("https://api.spotify.com/v1/me/top/tracks?limit=5&time_range=%s", specified_time_range))
                .addHeader("Authorization", "Bearer " + mAccessToken)
                .build();

        cancelCall();
        mCall = mOkHttpClient.newCall(request);

        responseReceived = false;
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "124, Failed to fetch data: " + e);
                Toast.makeText(TopArtists.this, "Line 256, Failed to fetch data, watch Logcat for more details",
                        Toast.LENGTH_SHORT).show();
            }
            //This is where the data from the userProfile is added to the textview and displayed
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    final String responseData = response.body().string(); // added this to try to parse
                    JSONObject jsonObject = new JSONObject(responseData);
                    JSONArray items = jsonObject.getJSONArray("items");
                    ArrayList<String> trackNames = new ArrayList<>();
                    int count = 0;
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject item = items.getJSONObject(i);
                        String trackName = item.getString("name");
                        String trackId = item.getString("id");
                        trackToId.put(trackName, trackId); // adds an artist name mapped to their ids to put in database
                        trackNames.add(trackName + "\n");
                        Log.d("LINE 149-154" + count, trackName + trackId);
//                        artistDataBaseList.add(artistName);
                    }
                    responseReceived = true;

//                    setTextAsync(artistNames.toString(), profileTextView); // use this for testing but th
                } catch (JSONException e) {
                    Log.d("JSON", "Failed to parse data: " + e);
                    Toast.makeText(TopArtists.this, "Failed to parse data, watch Logcat for more details",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        while (!responseReceived) {
            continue;
        }
        return trackToId; // returns map of artists to their ids to be used in database?
    }

    private AuthorizationRequest getAuthenticationRequest(AuthorizationResponse.Type type) {
        AuthorizationRequest request = new AuthorizationRequest.Builder(CLIENT_ID, type, getRedirectUri().toString())
                .setShowDialog(false)
                .setScopes(new String[] { "user-read-email, user-library-read, user-top-read", "user-read-recently-played" }) // user-read-email user-library-read <--- Change the scope of your requested token here/ was user read-email "user-read-email"
                .setCampaign("your-campaign-token")
                .build();
        return request;
    }

    private Uri getRedirectUri() {
        return Uri.parse(REDIRECT_URI);
    }


    private void setTextAsync(final String text, TextView textView) {
        runOnUiThread(() -> textView.setText(text));
    }

    private void cancelCall() {
        if (mCall != null) {
            mCall.cancel();
        }
    }

    public static Map<String, String> getTrackToId() {
        return trackToId;
    }

}
