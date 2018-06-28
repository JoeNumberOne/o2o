package com.joe.dao;

import com.joe.entity.Area;

import java.util.List;

public interface AreaDao{
    /**
     * 查询区域列表
     * @return areaList
     */
    List<Area> queryArea();
}

