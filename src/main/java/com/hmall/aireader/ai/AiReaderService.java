package com.hmall.aireader.ai;

import dev.langchain4j.service.SystemMessage;

public interface AiReaderService {

    @SystemMessage(fromResource = "system-prompt.txt")
    String chat(String userMessage);
}
