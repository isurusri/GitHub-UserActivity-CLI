package com.sricode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Arrays;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java -jar GitHub-UserActivity-CLI-1.0-SNAPSHOT.jar <username>");
            return;
        }

        String username = args[0];

        try {
            String apiUrl = "https://api.github.com/users/";
            URI uri = new URI(apiUrl + username +"/events");
            URL url = uri.toURL();

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();

                GithubActivityParser.formatJson(response.toString());
            }
            else {
                System.out.println("GET request failed");
            }

            connection.disconnect();
        }
        catch (MalformedURLException e) {
            Logger logger = Logger.getLogger(Main.class.getName());
            logger.severe("MalformedURLException: " + e.getMessage());
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}