package com.hmall.aireader.ai;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AiReaderServiceTest {

    @Resource
    private AiReaderService aiReaderService;

    @Test
    void chat() {
        String reslut = aiReaderService.chat("你好我是五虎将");
        System.out.println(reslut);
    }

    @Test
    void chatWithMemory() {
        String result = aiReaderService.chat("你好我是五虎酱");
        System.out.println(result);
        result = aiReaderService.chat("你好我是谁来着？");
        System.out.println(result);
    }
}