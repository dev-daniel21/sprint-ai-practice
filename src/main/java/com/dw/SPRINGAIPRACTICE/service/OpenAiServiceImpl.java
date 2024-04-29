package com.dw.SPRINGAIPRACTICE.service;

import com.dw.SPRINGAIPRACTICE.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.parser.BeanOutputParser;
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

    @Value("classpath:templates/get-JSON-format.st")
    private Resource getJSONFormat;

    @Value("classpath:templates/get-movie-info.st")
    private Resource getMovieInfo;

    private final ChatClient chatClient;

    @Autowired
    ObjectMapper objectMapper;

    public OpenAiServiceImpl(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

//  TESTS only
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
    public GeneralResponse getStarWarsDirector(StarWarsRequestModel starWarsRequest) {
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
        GeneralResponse result = new GeneralResponse(responseString);

        System.out.println(response.getResult().getOutput().getContent());
        System.out.println(responseString);
        System.out.println(result.response());
        return result;
    }

    @Override
    public Answer getStarWarsWithInfo(StarTrekRequest starWarsRequest) {
        System.out.println("##\nNew Star Wars question received:");
        PromptTemplate promptTemplate = new PromptTemplate(getStarWarsWithInfo);
        Prompt prompt = promptTemplate.create(Map.of("movieName", starWarsRequest.movieName()));
        System.out.println(prompt.getContents() + "\n##");
        ChatResponse response = chatClient.call(prompt);

        return new Answer(response.getResult().getOutput().getContent());
    }

    @Override
    public GeneralResponse getStartrekJSONFormat(StarTrekRequest starWarsRequest) {
        BeanOutputParser<GeneralResponse> parser = new BeanOutputParser<>(GeneralResponse.class);
        String format = parser.getFormat();
        System.out.printf("Parser format: %s%n", format);

        System.out.println("##\nNew Star Trek question received:");

        PromptTemplate promptTemplate = new PromptTemplate(getJSONFormat);
        Prompt prompt = promptTemplate.create(Map.of("movieName", starWarsRequest.movieName(), "format", format));

        System.out.println(prompt.getContents() + "\n##");

        ChatResponse response = chatClient.call(prompt);

        System.out.println(response.getResult().getOutput().getContent());

        return parser.parse(response.getResult().getOutput().getContent());
    }

    @Override
    public GeneralInfoResponse getMovieInfo(GeneralInfoRequest request) {
        BeanOutputParser<GeneralInfoResponse> parser = new BeanOutputParser<>(GeneralInfoResponse.class);
        String format = parser.getFormat();

        PromptTemplate promptTemplate = new PromptTemplate(getMovieInfo);
        Prompt prompt = promptTemplate.create(Map.of("movieName", request.movieName(), "format", format));

        ChatResponse response = chatClient.call(prompt);

        return parser.parse(response.getResult().getOutput().getContent());
    }
}
