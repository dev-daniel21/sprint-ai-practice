package com.dw.SPRINGAIPRACTICE.service;

import com.dw.SPRINGAIPRACTICE.model.Answer;
import com.dw.SPRINGAIPRACTICE.model.Question;
import com.dw.SPRINGAIPRACTICE.model.StarWarsRequestModel;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OpenAiServiceImpl implements OpenAIService {

    @Value("classpath:templates/get-starwars.st")
    private Resource getStarWars;

    @Value("classpath:templates/get-starwars-with-info.st")
    private Resource getStarWarsWithInfo;

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
        System.out.println("##\nNew Star Wars question received:");
        PromptTemplate promptTemplate = new PromptTemplate(getStarWars);
        Prompt prompt = promptTemplate.create(Map.of("movieName", starWarsRequest.movieName()));
        System.out.println(prompt.getContents() + "\n##");
        ChatResponse response = chatClient.call(prompt);

        return new Answer(response.getResult().getOutput().getContent());
    }

    @Override
    public Answer getStarWarsWithInfo(StarWarsRequestModel starWarsRequest) {
        System.out.println("##\nNew Star Wars question received:");
        PromptTemplate promptTemplate = new PromptTemplate(getStarWarsWithInfo);
        Prompt prompt = promptTemplate.create(Map.of("movieName", starWarsRequest.movieName()));
        System.out.println(prompt.getContents() + "\n##");
        ChatResponse response = chatClient.call(prompt);

        return new Answer(response.getResult().getOutput().getContent());
    }
}
