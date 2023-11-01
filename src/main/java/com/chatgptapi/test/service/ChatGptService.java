package com.chatgptapi.test.service;

import com.chatgptapi.test.data.ChatGptData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import java.io.IOException;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
public class ChatGptService {
    ObjectMapper objectMapper = new ObjectMapper();
    HttpClient httpClient = new HttpClient() {
        @Override
        public Optional<CookieHandler> cookieHandler() {
            return Optional.empty();
        }

        @Override
        public Optional<Duration> connectTimeout() {
            return Optional.empty();
        }

        @Override
        public Redirect followRedirects() {
            return null;
        }

        @Override
        public Optional<ProxySelector> proxy() {
            return Optional.empty();
        }

        @Override
        public SSLContext sslContext() {
            return null;
        }

        @Override
        public SSLParameters sslParameters() {
            return null;
        }

        @Override
        public Optional<Authenticator> authenticator() {
            return Optional.empty();
        }

        @Override
        public Version version() {
            return null;
        }

        @Override
        public Optional<Executor> executor() {
            return Optional.empty();
        }

        @Override
        public <T> HttpResponse<T> send(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler) throws IOException, InterruptedException {
            return null;
        }

        @Override
        public <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler) {
            return null;
        }

        @Override
        public <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler, HttpResponse.PushPromiseHandler<T> pushPromiseHandler) {
            return null;
        }
    };
    String apiUrl = "https://api.openai.com/v1/chat/completions";
    String openAIApiKey = "";
    public String chat(String prompt) throws Exception {
        var chatGptRequest = ChatGptData.ChatGptRequest.create(prompt);
        var requestBody = objectMapper.writeValueAsString(chatGptRequest);
        var responseBody = sendRequest(requestBody);
        var chatGptResponseResponse = objectMapper.readValue(responseBody, ChatGptData.ChatGptResponse.class);

        return chatGptResponseResponse.getText().orElseThrow();
    }

    public String sendRequest(String requestBodyAsJson) throws Exception {
        URI url = URI.create(apiUrl);

        var request = HttpRequest.newBuilder().uri(url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer" + openAIApiKey)
                .POST(HttpRequest.BodyPublishers.ofString(requestBodyAsJson)).build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }
}
