package com.henu.oto.service;

import com.henu.oto.BaseTest;
import com.henu.oto.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class AreaServiceTest extends BaseTest {
    @Autowired
    private AreaService areaService;
    @Autowired
    private CacheService cacheService;

    @Test
    public void testGetAreaList(){
        List<Area> areaList = areaService.getAreaList();
        assertEquals("华源", areaList.get(0).getAreaName());

        cacheService.removeFromCache(areaService.AREALISTKEY);
        areaList = areaService.getAreaList();
    }

}
