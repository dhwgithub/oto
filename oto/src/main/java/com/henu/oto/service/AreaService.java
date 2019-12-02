package com.henu.oto.service;

import com.henu.oto.entity.Area;

import java.util.List;

public interface AreaService {
    // 添加一行即可
    public static final String AREALISTKEY = "arealist";

    List<Area> getAreaList();
}
