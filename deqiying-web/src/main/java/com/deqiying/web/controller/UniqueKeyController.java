package com.deqiying.web.controller;


import com.deqiying.common.constant.Constants;
import com.deqiying.common.constant.RedisKey;
import com.deqiying.common.utils.SnowFlakeUtils;
import com.deqiying.common.utils.spring.SpringUtils;
import com.deqiying.framework.utils.RedisUtils;
import org.redisson.api.RLock;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/server/unique")
public class UniqueKeyController {
    @GetMapping("/string")
    public String uniqueString() {
        RLock lock = RedisUtils.getLock(RedisKey.SYSTEM_UNIQUE_LOCK + getLastPath());
        try {
            if (lock.tryLock(1200, 500, TimeUnit.MILLISECONDS)) {
                return SnowFlakeUtils.nextId26();
            }
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            lock.unlock();
        }
    }

    @GetMapping("/nextId")
    public Long nextId() {
        RLock lock = RedisUtils.getLock(RedisKey.SYSTEM_UNIQUE_LOCK + getLastPath());
        try {
            if (lock.tryLock(1200, 500, TimeUnit.MILLISECONDS)) {
                return SnowFlakeUtils.nextId();
            }
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            lock.unlock();
        }
    }

    @GetMapping("/nextId16")
    public String nextId16() {
        RLock lock = RedisUtils.getLock(RedisKey.SYSTEM_UNIQUE_LOCK + getLastPath());
        try {
            if (lock.tryLock(1200, 500, TimeUnit.MILLISECONDS)) {
                return SnowFlakeUtils.nextId16();
            }
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            lock.unlock();
        }
    }

    @GetMapping("/nextId/{radix}")
    public String nextId(@PathVariable Integer radix) {
        RLock lock = RedisUtils.getLock(RedisKey.SYSTEM_UNIQUE_LOCK + getLastPath());
        try {
            if (lock.tryLock(1200, 500, TimeUnit.MILLISECONDS)) {
                return SnowFlakeUtils.nextId(radix);
            }
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            lock.unlock();
        }
    }

    private String getLastPath() {
        String requestURI = SpringUtils.getRequestURI();
        return requestURI.substring(requestURI.lastIndexOf(Constants.SLASH) + 1);
    }
}
