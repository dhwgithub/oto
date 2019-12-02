package com.henu.oto.dao;

import com.henu.oto.BaseTest;
import com.henu.oto.entity.HeadLine;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class HeadLineDaoTest extends BaseTest {
    @Autowired
    private HeadLineDao headLineDao;

    @Test
    public void testQueryHeadLine(){
        List<HeadLine> headLineList = headLineDao.queryHeadLine(new HeadLine());
        assertEquals(1, headLineList.size());
    }
}
