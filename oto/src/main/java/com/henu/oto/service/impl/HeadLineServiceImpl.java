package com.henu.oto.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.henu.oto.cache.JedisUtil;
import com.henu.oto.dao.HeadLineDao;
import com.henu.oto.entity.HeadLine;
import com.henu.oto.service.HeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HeadLineServiceImpl implements HeadLineService {
    @Autowired
    private HeadLineDao headLineDao;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;

    @Override
    @Transactional
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException {
        String key = HEADLINELISTKEY;
        List<HeadLine> headLineList = null;
        ObjectMapper mapper = new ObjectMapper();

        if (headLineCondition != null && headLineCondition.getEnableStatus() != null){
            key = key + "_" + headLineCondition.getEnableStatus();
        }

        if (!jedisKeys.exists(key)){
            headLineList = headLineDao.queryHeadLine(headLineCondition);
            String jsonString = null;
            try {
                jsonString = mapper.writeValueAsString(headLineList);
            }catch (JsonProcessingException e){
                e.printStackTrace();
            }
            jedisStrings.set(key, jsonString);
        }else {
            String jsonString = jedisStrings.get(key);
            JavaType javaType = mapper.getTypeFactory().constructParametricType(
                    ArrayList.class, HeadLine.class
            );
            try {
                headLineList = mapper.readValue(jsonString, javaType);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return headLineList;
    }
}
