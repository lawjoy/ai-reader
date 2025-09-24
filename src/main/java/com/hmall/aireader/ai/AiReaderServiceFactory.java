package com.hmall.aireader.ai;

import com.hmall.aireader.ai.model.QwenChatModelConfig;
import com.hmall.aireader.ai.tools.InterviewQuestionTool;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.service.AiServiceContext;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiReaderServiceFactory {

    @Resource
    private ChatModel myQwenChatModel;

//    @Resource
//    private ContentRetriever contentRetriever;

    @Resource
    private McpToolProvider mcpToolProvider;

    @Resource
    private StreamingChatModel streamingChatModel;

    @Bean
    public AiReaderService aiReaderService(){
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);
        AiReaderService aiReaderService=AiServices.builder(AiReaderService.class)
                .chatModel(myQwenChatModel)//模型
                .streamingChatModel(streamingChatModel)//流式输出
                .chatMemory(chatMemory)//会话记忆
                .chatMemoryProvider(memoryId ->
                        MessageWindowChatMemory.withMaxMessages(10)) // 每个会话独立存储
                //.contentRetriever(contentRetriever) // RAG 检索增强生成
                .tools(new InterviewQuestionTool())//工具
                .toolProvider(mcpToolProvider)//MCP
                .build();
        return aiReaderService;
    }
}
