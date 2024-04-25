package com.dw.SPRINGAIPRACTICE.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpenAiServiceImplTest {

    @Autowired
    OpenAIService openAiService;

    @Test
    void getAnswer() {
        String answer = openAiService.getAnswer("Tell a knock-knock joke");
        System.out.println("Answer received");
        System.out.println(answer);
    }
}