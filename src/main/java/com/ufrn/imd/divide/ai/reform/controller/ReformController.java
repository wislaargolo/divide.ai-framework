package com.ufrn.imd.divide.ai.reform.controller;

import com.ufrn.imd.divide.ai.framework.controller.GroupController;
import com.ufrn.imd.divide.ai.framework.service.GroupService;
import com.ufrn.imd.divide.ai.reform.dto.request.ReformCreateRequestDTO;
import com.ufrn.imd.divide.ai.reform.dto.request.ReformUpdateRequestDTO;
import com.ufrn.imd.divide.ai.reform.dto.response.ReformResponseDTO;
import com.ufrn.imd.divide.ai.reform.model.Reform;
import com.ufrn.imd.divide.ai.reform.service.ReformService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("reform")
@RestController
@RequestMapping("/reforms")
public class ReformController extends GroupController<Reform, ReformCreateRequestDTO, ReformUpdateRequestDTO, ReformResponseDTO> {
    public ReformController(ReformService reformService) {
        super(reformService);
    }
}
