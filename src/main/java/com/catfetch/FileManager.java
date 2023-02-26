package com.catfetch;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileManager {
    private static final String DATA_DIR = "data";

    public FileManager() {

    }

    public void createDataDirIfNotExist() {
        Path dataDirPath = Paths.get(DATA_DIR);
        if (!Files.exists(dataDirPath)) {
            try {
                Files.createDirectories(dataDirPath);
            } catch (IOException e) {
                System.err.println("Error creating data directory: " + e.getMessage());
                System.exit(1);
            }
        }
    }

    public void saveCatImageLocally(String imageUrl) {
        // Generate unique filename for image
        String extension = imageUrl.substring(imageUrl.lastIndexOf('.'));
        String filename = UUID.randomUUID().toString() + extension;

        // Download and save image to data directory
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(imageUrl)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    File file = new File(DATA_DIR + "/" + filename);
                    if (!file.exists()) {
                        try (FileOutputStream fos = new FileOutputStream(file)) {
                            fos.write(responseBody.bytes());
                            System.out.println("Cat image saved: " + filename);
                        }
                    } else {
                        System.out.println("Cat image already exists: " + filename);
                    }
                } else {
                    System.err.println("Empty response body from image URL");
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving cat image: " + e.getMessage());
        }
    }
}
