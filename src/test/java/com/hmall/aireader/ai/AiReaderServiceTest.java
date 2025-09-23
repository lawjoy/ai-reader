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

    @Test
    void chatForReport() {
        AiReaderService.Report report = aiReaderService.chatForReport("你好，我是五虎酱，请帮我生成学习报告");
        System.out.println(report);
    }

    @Test
    void chatWithRag() {
        String result = aiReaderService.chat("怎么学习 Java？有哪些常见面试题？");
        System.out.println(result);
    }

    @Test
    void chatWithTools() {
        String result = aiReaderService.chat("有哪些常见的计算机网络面试题？,请用我设置的工具解答");
        System.out.println(result);
    }

    @Test
    void chatWithMcp() {
        String result = aiReaderService.chat("给我推荐一些程序员必备网站？");
        System.out.println(result);
    }

    @Test
    void chatWithGuardrail() {
        String result = aiReaderService.chat("kill the game");
        System.out.println(result);
    }
}