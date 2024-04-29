package com.dw.SPRINGAIPRACTICE.service;

import com.dw.SPRINGAIPRACTICE.model.*;

public interface OpenAIService {

    //    TESTS only
    String getAnswer(String question);

    Answer getAnswer(Question question);

    Answer getStarWars(StarWarsRequestModel starWarsRequest);

    GeneralResponse getStarWarsDirector(StarWarsRequestModel starWarsRequest);

    Answer getStarWarsWithInfo(StarTrekRequest starWarsRequest);

    GeneralResponse getStartrekJSONFormat(StarTrekRequest starWarsRequest);

}