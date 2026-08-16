package com.demanxier.JavaIA;

import dev.langchain4j.service.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assistant")
public class AssistantController {

    private final AssistentAiService assistentAiService;

    public AssistantController(AssistentAiService assistentAiService) {
        this.assistentAiService = assistentAiService;
    }

    @PostMapping()
    public String askAssistant(@RequestBody String userMessage){
        Result<String> result = assistentAiService.handleRequest(userMessage);
        return result.content();
    }
}
