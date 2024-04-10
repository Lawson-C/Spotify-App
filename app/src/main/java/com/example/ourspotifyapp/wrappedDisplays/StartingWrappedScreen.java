package com.example.ourspotifyapp.wrappedDisplays;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.DialogFragmentNavigatorDestinationBuilder;

import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.ourspotifyapp.R;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StartingWrappedScreen extends AppCompatActivity {

    public static final String CLIENT_ID = "631dbb12d2b642bebb1e019886c20525";
    public static final String REDIRECT_URI = "com.example.ourspotifyapp://auth";

    public static final int AUTH_TOKEN_REQUEST_CODE = 0;
    public static final int AUTH_CODE_REQUEST_CODE = 1;
    String dataBaseToken;
    String dataBaseCode;

    static Map<String, String> genreCount = new HashMap<>();

    private final OkHttpClient mOkHttpClient = new OkHttpClient();
    private static boolean responseReceived = false;
    private String mAccessToken, mAccessCode;
    private Call mCall;
    boolean time_frame_selected = false;
    static String desired_time_frame;

    static Map<String, String> artistToId = new HashMap<>();


    static List<String> artistsToDisplay = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("created", "app has started onCreate method -------------------------");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_wrapped_screen);

        // Initialize the buttons
        Button acceptBtn = (Button) findViewById(R.id.accept_button);
        Button getWrappedBtn = (Button) findViewById(R.id.get_wrapped_button);
        RadioButton time_frame_button1 = findViewById(R.id.short_time_frame);
        RadioButton time_frame_button2 = findViewById(R.id.medium_time_frame);
        RadioButton time_frame_button3 = findViewById(R.id.long_time_frame);

        Button confirmedTimeFrameButton = findViewById(R.id.submitted_time_frame);

        confirmedTimeFrameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (time_frame_button1.isChecked()) {
                    desired_time_frame = "short_term";
                } else if (time_frame_button2.isChecked()) {
                    desired_time_frame = "medium_term";
                } else {
                    desired_time_frame = "long_term";
                }
                time_frame_selected = true;
                getWrappedBtn.setVisibility(View.VISIBLE);
            }
        });

        acceptBtn.setOnClickListener((v) -> {
            getToken();
        });

        time_frame_button1.setOnClickListener((v) -> {

        });

        getWrappedBtn.setOnClickListener((v) -> {
            if (!time_frame_selected) {
                desired_time_frame = "long_term"; // defaults to ~ 1 year timeframe if none is selected
                artistToId = getTopArtists(desired_time_frame);
            } else {
                artistToId = getTopArtists(desired_time_frame);
            }
            // by the time the function gets to here, the map of artists to their ids should be saved and we should be ready to move over and display them
            // so in this part of the method, I should edit the screen to move over to TopArtists.java screen, whereI will display this
//            Log.d("%%%%%%%%%%&&&&&&&&&&&&&%%%%%%%%%%%", "map of the items" + String.artistToId);
            startActivity(new Intent(StartingWrappedScreen.this, TopArtists.class));
        });

    }

    /**
     * Get token from Spotify
     * This method will open the Spotify login activity and get the token
     * What is token?
     * https://developer.spotify.com/documentation/general/guides/authorization-guide/
     */
    public void getToken() {
        try {
            Log.d("98***********************", "Entered GetToken Method");
            final AuthorizationRequest request = getAuthenticationRequest(AuthorizationResponse.Type.TOKEN);
            Log.d("This is the request made", String.valueOf(request));
            AuthorizationClient.openLoginActivity(StartingWrappedScreen.this, AUTH_TOKEN_REQUEST_CODE, request); // What is happening to AUTH_REQUEST_CODE in this??
            Log.d("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&", "believe this is where the error comes");
        } catch (NullPointerException e) {
            Log.e("MainActivity", "getToken failed", e);
        }
    }


    /**
     * When the app leaves this activity to momentarily get a token/code, this function
     * fetches the result of that external activity to get the response from Spotify
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("126 ---------------------------", "onActivity started");
        super.onActivityResult(requestCode, resultCode, data);
        final AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, data);

        // Check which request code is present (if any)
        if (AUTH_TOKEN_REQUEST_CODE == requestCode) {
            mAccessToken = response.getAccessToken();
            dataBaseToken = mAccessToken;
//            setTextAsync(mAccessToken, tokenTextView); // might actually need these to run for some reason?
            Log.d("IMPORTANT", "dataBaseTokenSet ");
            Log.d("IMPORTANT", "dataBaseTokenSet ");
        } else if (AUTH_CODE_REQUEST_CODE == requestCode) {
            mAccessCode = response.getCode();
            dataBaseCode = mAccessCode;
            Log.d("IMPORTANT", "dataBaseCodeSet ");
            Log.d("IMPORTANT", "dataBaseCodeSet ");

        }
    }


    public Map<String, String> getTopArtists(String specified_time_range) {

        Log.d("156: started getTopArtist", "getTopArtists: ");
        if (mAccessToken == null) {
            Toast.makeText(this, "You need to get an access token first!", Toast.LENGTH_SHORT).show();
            return new HashMap<>(); // keeps it from continuing after this is printed
        }

        final Request request = new Request.Builder()
                .url(String.format("https://api.spotify.com/v1/me/top/artists?limit=5&time_range=%s", specified_time_range)) // CHANGE THE LIMIT IF WE WANT MORE THAN 5 PIECES OF DATA
                .addHeader("Authorization", "Bearer " + mAccessToken)
                .build();

        cancelCall();
        mCall = mOkHttpClient.newCall(request);

        responseReceived = false;

        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "255, Failed to fetch data: " + e);
                Toast.makeText(StartingWrappedScreen.this, "Line 256, Failed to fetch data, watch Logcat for more details",
                        Toast.LENGTH_SHORT).show();
            }
            //This is where the data from the userProfile is added to the textview and displayed
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("FurthestDown", "made it all the way to onResponse");
                Log.d("FurthestDown", "made it all the way to onResponse");

                try {
                    final String responseData = response.body().string(); // added this to try to parse
                    JSONObject jsonObject = new JSONObject(responseData);
                    JSONArray items = jsonObject.getJSONArray("items");
                    ArrayList<String> artistNames = new ArrayList<>();

                    for (int i = 0; i < items.length(); i++) {
                        JSONObject item = items.getJSONObject(i);
                        String artistName = item.getString("name");
                        String artistId = item.getString("id");

                        String genres = item.getString("genres");
                        Type listType = new TypeToken<List<String>>() {}.getType();
                        List<String> genreList = new Gson().fromJson(genres, listType);
                        String[] genreArray = Arrays.copyOf(genreList.toArray(), genreList.toArray().length, String[].class);
                        Log.d("-----------------%$%$%%$", Arrays.toString(genreArray));

                        artistToId.put(artistName, artistId); // adds an artist name mapped to their ids to put in database
//                        artistNames.add(artistName + "\n");
                        artistsToDisplay.add(artistName + "\n");
                        // This is where I calculate how how often the genres are found
                        // if the genre is found in the hashmap, then the key is incremented by 1 (will change weight later, if not then it is set to 1)
                        for (int x = 0; x < genreArray.length; x++) {
                            if (genreCount.containsKey(genreArray[x])) {
                                Log.d("tag---------", genreArray[x]);
                                int currCount = Integer.parseInt(genreCount.get(genreArray[x]));
                                currCount++; // This will be the place that we would change the weight of the genres when we add them
                                genreCount.put(genreArray[x], String.valueOf(currCount)); // restores the value as an int
                            } else {
                                genreCount.put(genreArray[x], "1"); // where the initial value of a genre is created if not found before
                            }
                        }
                    }
                    Map artistDataBaseMap = new HashMap<String, String>();
                    artistDataBaseMap = artistToId;

                    Log.d("WHAT I WANT TO DISPLAY", artistNames.toString());
                    responseReceived = true;

                } catch (JSONException e) {
                    Log.d("JSON", "Failed to parse data: " + e);
                    Toast.makeText(StartingWrappedScreen.this, "Failed to parse data, watch Logcat for more details",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        while (!responseReceived) {
            continue;
        }
        return artistToId; // returns map of artists to their ids to be used in database
    }

    /**
     * Creates a UI thread to update a TextView in the background
     * Reduces UI latency and makes the system perform more consistently
     *
     * @param text the text to set
     * @param textView TextView object to update
     */

    /**
     * Get authentication request
     *
     * @param type the type of the request
     * @return the authentication request
     */
    private AuthorizationRequest getAuthenticationRequest(AuthorizationResponse.Type type) {
        AuthorizationRequest request = new AuthorizationRequest.Builder(CLIENT_ID, type, getRedirectUri().toString())
                .setShowDialog(false)
                .setScopes(new String[] { "user-read-email, user-library-read, user-top-read", "user-read-recently-played" }) // user-read-email user-library-read <--- Change the scope of your requested token here/ was user read-email "user-read-email"
                .setCampaign("your-campaign-token")
                .build();
        return request;
    }


    /**
     * Gets the redirect Uri for Spotify
     *
     * @return redirect Uri object
     */
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

    @Override
    protected void onDestroy() {
        cancelCall();
        super.onDestroy();
    }
    public static Map<String, String> getArtistsToId() {
        return artistToId;
    }
    public static String getDesiredTimeFrame() {
        return desired_time_frame;
    }
    public static Map<String, String> getGenreCount() {
        return genreCount;
    }
    public static List<String> getArtistsToDisplay() {
        return artistsToDisplay;
    }
    public static void setArtistsToDisplay(List<String> artistsToDisplay) {
        StartingWrappedScreen.artistsToDisplay = artistsToDisplay;
    }
    public static void setArtistToId(Map<String, String> artistToId) {
        StartingWrappedScreen.artistToId = artistToId;
    }
}