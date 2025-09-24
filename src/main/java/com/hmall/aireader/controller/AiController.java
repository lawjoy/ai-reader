package com.hmall.aireader.controller;

import com.hmall.aireader.ai.AiReaderService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


@Slf4j
@RestController
@RequestMapping("/ai")
public class AiController {

@Resource
private AiReaderService aiReaderService;

@GetMapping("/chat")
public Flux<ServerSentEvent<String>> chat(@RequestParam("memoryId") int memoryId,@RequestParam("message") String message) {

    log.info("1. 刚接收到的参数: {}", message);

    return aiReaderService.chatStream(memoryId, message)
            .map(chunk -> ServerSentEvent.<String>builder()
                    .data(chunk)
                    .build());
 }
}