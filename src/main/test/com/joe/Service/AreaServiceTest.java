package com.joe.Service;

import com.joe.BaseTest;
import com.joe.entity.Area;
import com.joe.service.AreaService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AreaServiceTest extends BaseTest{

    @Autowired
    private AreaService areaService;

    @Test
    public void testGetAreaList(){
        List<Area> areaList = areaService.getAreaList();
        System.out.println(areaList.get(0).getAreaName());
    }
}
