package com.dw.SPRINGAIPRACTICE.service;

import com.dw.SPRINGAIPRACTICE.model.Answer;
import com.dw.SPRINGAIPRACTICE.model.Question;
import com.dw.SPRINGAIPRACTICE.model.StarWarsRequestModel;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;


@Service
public class OpenAiServiceImpl implements OpenAIService {

    private final ChatClient chatClient;

    public OpenAiServiceImpl(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public String getAnswer(String question) {
        PromptTemplate promptTemplate = new PromptTemplate(question);
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatClient.call(prompt);


        return response.getResult().getOutput().getContent();
    }

    @Override
    public Answer getAnswer(Question question) {
        System.out.println("new question received");
        PromptTemplate promptTemplate = new PromptTemplate(question.question());
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatClient.call(prompt);

        return new Answer(response.getResult().getOutput().getContent());
    }

    @Override
    public Answer getStarWars(StarWarsRequestModel starWarsRequest) {
        System.out.println("new Star Wars question received");
        PromptTemplate promptTemplate = new PromptTemplate(String.format("Which part of Star Wars franchise is the movie called %s ?",
                starWarsRequest.movieName()));
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatClient.call(prompt);

        return new Answer(response.getResult().getOutput().getContent());
    }
}
