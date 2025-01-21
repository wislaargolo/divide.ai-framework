package com.ufrn.imd.divide.ai.sporting.controller;

import com.ufrn.imd.divide.ai.framework.controller.GroupController;
import com.ufrn.imd.divide.ai.sporting.dto.request.SportingCreateRequestDTO;
import com.ufrn.imd.divide.ai.sporting.dto.request.SportingUpdateRequestDTO;
import com.ufrn.imd.divide.ai.sporting.dto.response.SportingResponseDTO;
import com.ufrn.imd.divide.ai.sporting.model.Sporting;
import com.ufrn.imd.divide.ai.sporting.service.SportingService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("sporting")
@RestController
@RequestMapping("/sporting")
public class SportingController extends GroupController<Sporting, SportingCreateRequestDTO, SportingUpdateRequestDTO, SportingResponseDTO> {
    public SportingController(SportingService sportingService) {
        super(sportingService);
    }
}
