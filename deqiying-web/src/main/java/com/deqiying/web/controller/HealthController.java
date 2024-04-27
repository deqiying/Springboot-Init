package com.deqiying.web.controller;

import com.deqiying.framework.utils.RedisUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/server")
public class HealthController {

    @GetMapping("/health")
    public String health() {
        return "success";
    }

    @GetMapping("/cache/set/{key}/{value}")
    public String setCacheObject(@PathVariable String key, @PathVariable String value) {
        RedisUtils.setCacheObject(key, value);
        return value;
    }
    @GetMapping("/cache/get/{key}")
    public String getCacheObject(@PathVariable String key) {
        return RedisUtils.getCacheObject(key);
    }

}
