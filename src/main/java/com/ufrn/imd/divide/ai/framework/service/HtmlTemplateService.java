package com.ufrn.imd.divide.ai.framework.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.util.Map;

@Service
public class HtmlTemplateService {

    public String loadHtmlTemplate(String templateName, Map<String, String> placeholders) {
        try {

            ClassPathResource resource = new ClassPathResource("templates/" + templateName);
            String htmlContent = Files.readString(resource.getFile().toPath());

            // Substitui os placeholders pelos valores do map
            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                htmlContent = htmlContent.replace("{{" + entry.getKey() + "}}", entry.getValue());
            }

            return htmlContent;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar o template HTML: " + templateName, e);
        }
    }
}
