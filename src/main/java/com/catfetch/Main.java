package com.catfetch;

public class Main {

    public static FileManager fileManager = new FileManager();
    public static RequestManager requestManager = new RequestManager();

    public static void main(String[] args) {
        // Create data directory if it doesn't exist
        fileManager.createDataDirIfNotExist();

        // Fetch cat image from API
        String imageUrl = requestManager.fetchCatImageUrl();

        // Download and save cat image locally
        fileManager.saveCatImageLocally(imageUrl);
    }


}
