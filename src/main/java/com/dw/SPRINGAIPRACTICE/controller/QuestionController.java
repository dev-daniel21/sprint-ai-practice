package com.dw.SPRINGAIPRACTICE.controller;

import com.dw.SPRINGAIPRACTICE.model.*;
import com.dw.SPRINGAIPRACTICE.service.OpenAIService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class QuestionController {

    private final OpenAIService openAIService;


    QuestionController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/getAnswer")
    public Answer getAnswer(@RequestBody Question question) {

        return openAIService.getAnswer(question);
    }

    @PostMapping("/getStarWars")
    public Answer getStarWars(@RequestBody StarWarsRequestModel starWarsRequestModel) {

        return openAIService.getStarWars(starWarsRequestModel);
    }

    @PostMapping("/getStarWarsDirector")
    public GeneralResponse getStarWarsDirector(@RequestBody StarWarsRequestModel starWarsRequestModel) {

        return openAIService.getStarWarsDirector(starWarsRequestModel);
    }

    @PostMapping("/getStarWarsWithInfo")
    public Answer getStarwarsWithInfo(@RequestBody StarTrekRequest starWarsRequestModel) {

        return openAIService.getStarWarsWithInfo(starWarsRequestModel);
    }

    @PostMapping("/getStarWarsJson")
    public GeneralResponse getStarwarsJSON(@RequestBody StarTrekRequest request) {

        return openAIService.getStartrekJSONFormat(request);
    }


    @PostMapping("/getMovieInfo")
    public GeneralInfoResponse getMovieInfo(@RequestBody GeneralInfoRequest request) {

        return openAIService.getMovieInfo(request);
    }
}
