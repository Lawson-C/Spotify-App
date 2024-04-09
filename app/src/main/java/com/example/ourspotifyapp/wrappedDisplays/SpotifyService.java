package com.example.ourspotifyapp.wrappedDisplays;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SpotifyService {

    private static final String CLIENT_ID = "631dbb12d2b642bebb1e019886c20525";
    private static final String REDIRECT_URI = "com.example.Group4Spotify-App://auth";
    private static final int AUTH_TOKEN_REQUEST_CODE = 0;
    private static final int AUTH_CODE_REQUEST_CODE = 1;

    private final Activity activity;
    private final OkHttpClient okHttpClient = new OkHttpClient();
    private Call call;
    private String accessToken, accessCode;

    public SpotifyService(Activity activity) {
        this.activity = activity;
    }

    public void getToken() {
        AuthorizationRequest request = getAuthenticationRequest(AuthorizationResponse.Type.TOKEN);
        AuthorizationClient.openLoginActivity(activity, AUTH_TOKEN_REQUEST_CODE, request);
    }

    public void getCode() {
        AuthorizationRequest request = getAuthenticationRequest(AuthorizationResponse.Type.CODE);
        AuthorizationClient.openLoginActivity(activity, AUTH_CODE_REQUEST_CODE, request);
    }

    public void getTokenAndCode(TextView token, TextView code) {
        getToken();
        getCode();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data, TextView tokenTextView, TextView codeTextView) {
        AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, data);

        if (AUTH_TOKEN_REQUEST_CODE == requestCode) {
            accessToken = response.getAccessToken();
            setTextAsync(accessToken, tokenTextView);
        } else if (AUTH_CODE_REQUEST_CODE == requestCode) {
            accessCode = response.getCode();
            setTextAsync(accessCode, codeTextView);
        }
    }

//    public void onGetUserProfileClicked(TextView tokenTextView, TextView identifierTextView, String identifier) {
//        if (accessToken == null) {
//            Toast.makeText(activity, "You need to get an access token first!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        Request request = new Request.Builder()
//                .url(endpoint) // for example this could be .url("https://api.spotify.com/v1/me").
//                .addHeader("Authorization", "Bearer " + accessToken)
//                .build();
//
//        cancelCall();
//        call = okHttpClient.newCall(request);
//
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("HTTP", "Failed to fetch data: " + e);
//                activity.runOnUiThread(() -> Toast.makeText(activity, "Failed to fetch data, watch Logcat for more details",
//                        Toast.LENGTH_SHORT).show());
//            }
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (!response.isSuccessful()) {
//                    Log.d("HTTP", "Server error: " + response);
//                    activity.runOnUiThread(() -> Toast.makeText(activity, "Server error, check Logcat for more details",
//                            Toast.LENGTH_SHORT).show());
//                    return;
//                }
//                try {
//                    final String responseData = response.body().string(); // added this to try to parse
//                    JSONObject jsonObject = new JSONObject(responseData); // keeping these, splits the next commented out line into 2
//                    JSONArray items = jsonObject.getJSONArray("items");
//                    ArrayList<String> names = new ArrayList<>();
//                    for (int i = 0; i < items.length(); i++) {
//                        JSONObject item = items.getJSONObject(i);
//                        String name = item.getString("name");
//                        names.add("name: " + name + "\n");
//                    }
//                    activity.runOnUiThread(() -> {
//                        setTextAsync(names.toString(), identifierTextView);
//                    });
//                } catch (JSONException e) {
//                    Log.d("JSON", "Failed to parse data: " + e);
//                    activity.runOnUiThread(() -> Toast.makeText(activity, "Failed to parse data, watch Logcat for more details",
//                            Toast.LENGTH_SHORT).show());
//                }
//            }
//        });
//    }

    public void getTopArtists(TextView substitutingTextView) {
        if (accessToken == null) {
            Toast.makeText(activity, "You need to get an access token first!", Toast.LENGTH_SHORT).show();
            return;
        }
        String endpoint = "https://api.spotify.com/v1/me/top/artists";
        Request request = new Request.Builder()
                .url(endpoint)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        cancelCall();
        call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "Failed to fetch data: " + e);
                activity.runOnUiThread(() -> Toast.makeText(activity, "Failed to fetch data, watch Logcat for more details",
                        Toast.LENGTH_SHORT).show());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.d("HTTP", "Server error: " + response);
                    activity.runOnUiThread(() -> Toast.makeText(activity, "Server error, check Logcat for more details",
                            Toast.LENGTH_SHORT).show());
                    return;
                }
                try {
                    final String responseData = response.body().string(); // added this to try to parse
                    JSONObject jsonObject = new JSONObject(responseData); // keeping these, splits the next commented out line into 2
                    JSONArray items = jsonObject.getJSONArray("items");
                    ArrayList<String> artistNames = new ArrayList<>();
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject item = items.getJSONObject(i);
                        String artistName = item.getString("name");
                        artistNames.add("Name: " + artistName + "\n");
                    }
                    setTextAsync(artistNames.toString(), substitutingTextView);
//                    activity.runOnUiThread(() -> {
//                        setTextAsync(names.toString(), substitutingTextView);
//                    });
                } catch (JSONException e) {
                    Log.d("JSON", "Failed to parse data: " + e);
                    activity.runOnUiThread(() -> Toast.makeText(activity, "Failed to parse data, watch Logcat for more details",
                            Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

//    public void getTopTracks(TextView subtitutingTextView) {
//        Map<String, String> songToID = new HashMap<>(); // this is going to contain the song and its list of artists
//        if (accessToken == null) {
//            Toast.makeText(activity, "You need to get an access token first!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        String endpoint = "https://api.spotify.com/v1/me/top/tracks";
//        Request request = new Request.Builder()
//                .url(endpoint)
//                .addHeader("Authorization", "Bearer " + accessToken)
//                .build();
//
//        cancelCall();
//        call = okHttpClient.newCall(request);
//
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("HTTP", "Failed to fetch data: " + e);
//                activity.runOnUiThread(() -> Toast.makeText(activity, "Failed to fetch data, watch Logcat for more details",
//                        Toast.LENGTH_SHORT).show());
//            }
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (!response.isSuccessful()) {
//                    Log.d("HTTP", "Server error: " + response);
//                    activity.runOnUiThread(() -> Toast.makeText(activity, "Server error, check Logcat for more details",
//                            Toast.LENGTH_SHORT).show());
//                    return;
//                }
//                try {
//                    final String responseData = response.body().string(); // added this to try to parse
//                    JSONObject jsonObject = new JSONObject(responseData);
//
//                    JSONArray items = jsonObject.getJSONArray("items");
//                    ArrayList<String> names = new ArrayList<>();
//                    for (int i = 0; i < items.length(); i++) {
//                        JSONObject item = items.getJSONObject(i);
//                        String name = item.getString("name");
//                        String id = item.getString("id"); // might need this in order to get the artists for the song
//                        songToID.put(name, id);
//                        names.add(name + "\n"); // ex. Kanye West + newline
//                    }
//                    activity.runOnUiThread(() -> {
//                        setTextAsync(names.toString(), subtitutingTextView);
//                    });
//                } catch (JSONException e) {
//                    Log.d("JSON", "Failed to parse data: " + e);
//                    activity.runOnUiThread(() -> Toast.makeText(activity, "Failed to parse data, watch Logcat for more details",
//                            Toast.LENGTH_SHORT).show());
//                }
//            }
//        });
//    }


    private void setTextAsync(final String text, TextView textView) {
        activity.runOnUiThread(() -> textView.setText(text));
    }

    private AuthorizationRequest getAuthenticationRequest(AuthorizationResponse.Type type) {
        return new AuthorizationRequest.Builder(CLIENT_ID, type, getRedirectUri().toString())
                .setShowDialog(false)
                .setScopes(new String[]{"user-read-email, playlist-read-private, user-top-read"})
                .setCampaign("your-campaign-token") // this line doesn't matter
                .build();
    }
    public Uri getRedirectUri() {
        return Uri.parse(REDIRECT_URI);
    }

    public void cancelCall() {
        if (call != null) {
            call.cancel();
        }
    }
}

