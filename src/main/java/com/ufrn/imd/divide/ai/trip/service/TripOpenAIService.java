package com.ufrn.imd.divide.ai.trip.service;

import com.azure.ai.openai.OpenAIClient;
import com.ufrn.imd.divide.ai.framework.dto.request.OpenAIRequestDTO;
import com.ufrn.imd.divide.ai.framework.dto.response.ConfigOpenAIResponseDTO;
import com.ufrn.imd.divide.ai.framework.mapper.ChatMapper;
import com.ufrn.imd.divide.ai.framework.model.Group;
import com.ufrn.imd.divide.ai.framework.repository.OpenAIRepository;
import com.ufrn.imd.divide.ai.framework.service.GenericGroupService;
import com.ufrn.imd.divide.ai.framework.service.OpenAIService;
import com.ufrn.imd.divide.ai.framework.service.UserService;
import com.ufrn.imd.divide.ai.framework.service.UserValidationService;
import com.ufrn.imd.divide.ai.trip.dto.response.TripOpenAIResponseDTO;
import com.ufrn.imd.divide.ai.trip.model.Trip;
import org.springframework.stereotype.Service;
import com.ufrn.imd.divide.ai.framework.util.FileUtils;

import java.time.LocalDate;

@Service
public class TripOpenAIService extends OpenAIService {
    private final TripService tripService;

    public TripOpenAIService(OpenAIClient openAIClient, ChatMapper chatMapper, OpenAIRepository openAIRepository, UserValidationService userValidationService, UserService userService, GenericGroupService<Group> groupService, TripService tripService) {
        super(openAIClient, chatMapper, openAIRepository, userValidationService, userService, groupService);
        this.tripService = tripService;
    }

    @Override
    protected String buildPrompt(OpenAIRequestDTO chatRequestDTO) throws Exception {
        Long groupId = chatRequestDTO.groupId();
        Trip group = tripService.findByIdIfExists(groupId);

        LocalDate startDate = group.getStartDate();
        LocalDate finalOccurrenceDate = group.getFinalOccurrenceDate();

        Integer numberOfParticipants = group.getMembers().size();

        String destination = group.getDestination();

        return FileUtils.readSystemPromptFile("prompt/trip/Prompt.txt")
                .replace("{destination}", destination)
                .replace("{startDate}", startDate.toString())
                .replace("{finalOccurrenceDate}", finalOccurrenceDate.toString())
                .replace("{numberOfParticipants}", numberOfParticipants.toString());
    }

    @Override
    protected ConfigOpenAIResponseDTO getConfig() {
        return new ConfigOpenAIResponseDTO("gpt-3.5-turbo", 0.5, "prompt/trip/TripSystemPrompt.txt");
    }

}
