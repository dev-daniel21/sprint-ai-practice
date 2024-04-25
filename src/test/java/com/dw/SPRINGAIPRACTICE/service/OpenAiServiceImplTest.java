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
        System.out.println("Answer received:\n");
        System.out.println(answer);
    }

    @Test
    void getAnswerForPythonScript() {
        String answer = openAiService.getAnswer("Write a python code to output numbers form 1 to 1000");
        System.out.println("Answer received:\n");
        System.out.println(answer);
    }

    @Test
    void getAnswerForSnakeGame() {
        String answer = openAiService.getAnswer("Write a python code to create a snake game");
        System.out.println("Answer received:\n");
        System.out.println(answer);
    }
}