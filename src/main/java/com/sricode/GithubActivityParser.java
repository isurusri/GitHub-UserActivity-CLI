package com.sricode;

import com.google.gson.*;

public class GithubActivityParser {
    public static void formatJson(String json) {
        JsonArray events = JsonParser.parseString(json).getAsJsonArray();

        for (int i = 0; i < events.size(); i++) {
            JsonObject event = events.get(i).getAsJsonObject();
            String eventType = event.get("type").getAsString();
            String repoName = event.getAsJsonObject("repo").get("name").getAsString();

            switch (eventType) {
                case "PushEvent":
                    int commitCount = event.getAsJsonObject("payload").getAsJsonArray("commits").size();
                    System.out.println("- Pushed " + commitCount + " commit(s) to " + repoName);
                    break;

                case "CreateEvent":
                    String refType = event.getAsJsonObject("payload").get("ref_type").getAsString();
                    if ("repository".equals(refType)) {
                        System.out.println("- Created a new repository " + repoName);
                    } else if ("branch".equals(refType)) {
                        String branchName = event.getAsJsonObject("payload").get("ref").getAsString();
                        System.out.println("- Created a new branch " + branchName + " in " + repoName);
                    }
                    break;

                // Add cases for other event types if needed
                default:
                    System.out.println("- " + eventType + " in " + repoName);
                    break;
            }
        }
    }
}
