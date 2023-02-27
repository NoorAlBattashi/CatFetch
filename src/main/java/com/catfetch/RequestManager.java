package com.catfetch;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

/**
 * RequestManager class is responsible for fetching cat images from thecatapi.com.
 */
public class RequestManager {
    /**
     * The base URL of the cat API.
     */
    private static final String CAT_API_URL = "https://api.thecatapi.com/v1/images/search";

    /**
     * Constructs a new RequestManager object.
     */
    public RequestManager() {

    }

    /**
     * Fetches a cat image URL from the cat API.
     *
     * @return The URL of a cat image as a string.
     * @throws IOException If there is an error connecting to the API or parsing the response.
     */
    public String fetchCatImageUrl() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(CAT_API_URL)
                .build();

        try (Response response = client.newCall(request).execute()) {
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                String json = responseBody.string();
                // Parse JSON response to get image URL
                int startIndex = json.indexOf("\"url\":") + 7;
                int endIndex = json.indexOf("\",", startIndex);
                String imageUrl = json.substring(startIndex, endIndex);
                return imageUrl;
            } else {
                System.err.println("Empty response body from API");
                System.exit(1);
            }
        } catch (IOException e) {
            System.err.println("Error fetching cat image URL: " + e.getMessage());
            System.exit(1);
        }

        return null; // unreachable code
    }
}
