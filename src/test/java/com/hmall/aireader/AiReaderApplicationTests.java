package com.hmall.aireader;

import com.hmall.aireader.ai.AiReader;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AiReaderApplicationTests {

	@Resource
	private AiReader aiReader;

	@Test
	void chat() {
		aiReader.chat("我是五虎将");
	}
}
