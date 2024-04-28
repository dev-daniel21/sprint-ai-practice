package com.dw.SPRINGAIPRACTICE.service;

import com.dw.SPRINGAIPRACTICE.model.Answer;
import com.dw.SPRINGAIPRACTICE.model.Question;
import com.dw.SPRINGAIPRACTICE.model.StarWarsRequestModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OpenAiServiceImpl implements OpenAIService {

    @Value("classpath:templates/get-starwars.st")
    private Resource getStarWars;

    @Value("classpath:templates/get-starwars-JSON-response.st")
    private Resource getStarWarsDirector;

    @Value("classpath:templates/get-starwars-with-info.st")
    private Resource getStarWarsWithInfo;

    private final ChatClient chatClient;

    @Autowired
    ObjectMapper objectMapper;

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

        Answer result = new Answer(response.getResult().getOutput().getContent());

        System.out.println(result.answer());
        return result;
    }

    @Override
    public Answer getStarWarsDirector(StarWarsRequestModel starWarsRequest) {
        System.out.println("##\nNew Star Wars question received:");
        PromptTemplate promptTemplate = new PromptTemplate(getStarWarsDirector);
        Prompt prompt = promptTemplate.create(Map.of("movieName", starWarsRequest.movieName()));
        System.out.println(prompt.getContents() + "\n##");
        ChatResponse response = chatClient.call(prompt);
        String responseString;

        try {
            JsonNode jsonNode = objectMapper.readTree(response.getResult().getOutput().getContent());
            responseString = jsonNode.get("director").asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Answer result = new Answer(responseString);

        System.out.println(response.getResult().getOutput().getContent());
        System.out.println(responseString);
        System.out.println(result.answer());
        return result;
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
