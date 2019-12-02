package com.henu.oto.dao;

import com.henu.oto.BaseTest;
import com.henu.oto.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class AreaDaoTest extends BaseTest {
    @Autowired
    private AreaDao areaDao;

    @Test
    public void testQueryArea(){
        List<Area> areaList = areaDao.queryArea();
        System.out.println(areaList);
        assertEquals(3, areaList.size());
    }
}
