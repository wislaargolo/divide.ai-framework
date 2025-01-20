package com.ufrn.imd.divide.ai.reform.controller;

import com.ufrn.imd.divide.ai.framework.controller.OpenAIController;
import com.ufrn.imd.divide.ai.framework.service.OpenAIService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("reform")
@RestController
@RequestMapping("/reform/chat-completion")
public class ReformOpenAIController extends OpenAIController {
    public ReformOpenAIController(OpenAIService openAIService) {
        super(openAIService);
    }
}
