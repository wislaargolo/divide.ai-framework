package com.ufrn.imd.divide.ai.trip.controller;

import com.ufrn.imd.divide.ai.framework.controller.OpenAIController;
import com.ufrn.imd.divide.ai.framework.service.OpenAIService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trip/chat-completion")
public class TripOpenAIController extends OpenAIController {
    public TripOpenAIController(OpenAIService openAIService) {
        super(openAIService);
    }
}
