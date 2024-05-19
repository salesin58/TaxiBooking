package com.taxi.backend.utils;

import org.springframework.stereotype.Component;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import okhttp3.*;

import java.io.IOException;

@Component
public class ChatGptUtils {

        private static final String API_KEY = "sk-proj-DiT8K8srqAq66zS506R0T3BlbkFJVEYQHZSUWnXVUrW68PAL";
        private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new Gson();

    public static String sendMessage(String messageContent) throws IOException {
        JsonObject message = new JsonObject();
        message.addProperty("role", "user");
        message.addProperty("content", messageContent);

        JsonArray messages = new JsonArray();
        messages.add(message);

        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("model", "gpt-3.5-turbo");
        requestBody.add("messages", messages);

        String jsonRequestBody = requestBody.toString();
        System.out.println("Request Body: " + jsonRequestBody);

        Request request = new Request.Builder()
                .url(API_URL)
                .post(RequestBody.create(jsonRequestBody, MediaType.get("application/json; charset=utf-8")))
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body() != null ? response.body().string() : null;
            System.out.println("Response Code: " + response.code());
            System.out.println("Response Body: " + responseBody);

            if (response.isSuccessful() && responseBody != null) {
                JsonObject jsonResponse = gson.fromJson(responseBody, JsonObject.class);
                return jsonResponse.toString();
            } else {
                throw new IOException("API request failed: " + response.message());
            }
        }


}}