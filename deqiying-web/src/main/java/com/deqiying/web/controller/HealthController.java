package com.deqiying.web.controller;

import com.deqiying.common.utils.spring.SpringUtils;
import com.deqiying.framework.utils.RedisUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> download(@RequestParam("fileName") String fileName) throws IOException {
        Resource resource = SpringUtils.getResource(fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
        headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.contentLength()));
        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(resource.getInputStream()));
    }
}
