package com.dw.SPRINGAIPRACTICE.service;

import com.dw.SPRINGAIPRACTICE.model.Answer;
import com.dw.SPRINGAIPRACTICE.model.Question;
import com.dw.SPRINGAIPRACTICE.model.StarWarsRequestModel;

public interface OpenAIService {

    String getAnswer(String question);

    Answer getAnswer(Question question);

    Answer getStarWars(StarWarsRequestModel starWarsRequest);

    Answer getStarWarsDirector(StarWarsRequestModel starWarsRequest);

    Answer getStarWarsWithInfo(StarWarsRequestModel starWarsRequestModel);
}
