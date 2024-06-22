package com.example.myapplication.Sistem;

import okhttp3.*;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class OkHttpRequester {

    private final OkHttpClient client;

    public OkHttpRequester() {
        this.client = new OkHttpClient();
    }

    public CompletableFuture<String> performRequest(String url, String query) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        urlBuilder.addQueryParameter("query", query);

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .build();

        CompletableFuture<String> future = new CompletableFuture<>();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                future.completeExceptionally(e);
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                if (!response.isSuccessful()) {
                    future.completeExceptionally(new IOException("Unexpected code " + response));
                } else {
                    future.complete(response.body().string());
                }
            }
        });

        return future;
    }
}
