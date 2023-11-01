package com.chatgptapi.test.data;

import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class ChatGptData {
    public static class ChatGptRequest {
        private String model;
        private String prompt;
        private double temperature;
        private int max_tokens;

        public static ChatGptRequest create(String prompt) {
            ChatGptRequest request = new ChatGptRequest();
            request.model = "text-davinci-003";
            request.prompt = prompt;
            request.temperature = 1;
            request.max_tokens = 100;
            return request;
        }
    }

    public static class ChatGptResponse {
        private List<Choice> choices;

        public Optional<String> getText() {
            if (choices == null || choices.isEmpty())
                return Optional.empty();
            return Optional.of(choices.get(0).text);
        }
    }

    public static class Choice {
        private String text;

        public Choice(String text) {
            this.text = text;
        }
    }
}
