package com.hmall.aireader.ai;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServiceContext;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiReaderServiceFactory {

    @Resource
    private ChatModel qwenChatModel;

    @Bean
    public AiReaderService aiReaderService(){
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);
        AiReaderService aiReaderService=AiServices.builder(AiReaderService.class)
                .chatModel(qwenChatModel)
                .chatMemory(chatMemory)
                .build();
        return aiReaderService;
    }
}
