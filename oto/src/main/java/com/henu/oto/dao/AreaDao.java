package com.henu.oto.dao;

import com.henu.oto.entity.Area;

import java.util.List;

public interface AreaDao {
    /**
     * 列出区域列表
     * @return List<Area>
     */
    List<Area> queryArea();
}
