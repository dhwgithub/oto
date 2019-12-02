package com.henu.oto.service.impl;

import com.henu.oto.cache.JedisUtil;
import com.henu.oto.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CacheServiceImpl implements CacheService {
    @Autowired
    private JedisUtil.Keys jedisKeys;

    @Override
    public void removeFromCache(String keyPreFix) {
        Set<String> keySet = jedisKeys.keys(keyPreFix + "*");
        for (String key : keySet){
            jedisKeys.del(key);
        }
    }
}
