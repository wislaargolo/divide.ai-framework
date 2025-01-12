package com.ufrn.imd.divide.ai.trip.controller;

import com.ufrn.imd.divide.ai.framework.controller.OpenAIController;
import com.ufrn.imd.divide.ai.framework.service.OpenAIService;
import com.ufrn.imd.divide.ai.trip.service.TripOpenAIService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("trip")
@RestController
@RequestMapping("/trip/chat-completion")
public class TripOpenAIController extends OpenAIController {
    public TripOpenAIController(TripOpenAIService openAIService) {
        super(openAIService);
    }
}
