package com.ufrn.imd.divide.ai.sporting.controller;

import com.ufrn.imd.divide.ai.framework.controller.OpenAIController;
import com.ufrn.imd.divide.ai.framework.service.OpenAIService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("sporting")
@RestController
@RequestMapping("/sporting/chat-completion")
public class SportingOpenAIController extends OpenAIController {
    public SportingOpenAIController(OpenAIService openAIService) {
        super(openAIService);
    }
}
