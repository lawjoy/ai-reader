package com.hmall.aireader.controller;

import com.hmall.aireader.translate.TranslateService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping
public class TranslateController {

    @Resource
    private TranslateService translateService;

    @GetMapping("/trans")
    public String trans(@RequestParam String english) {

        log.info(english);
        // 调用翻译并输出结果
        return translateService.translateToChinese(english);
    }
}
