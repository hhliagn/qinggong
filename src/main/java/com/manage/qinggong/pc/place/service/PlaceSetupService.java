package com.manage.qinggong.pc.place.service;

import com.manage.qinggong.pc.place.mapper.PlaceSetupMapper;
import com.manage.qinggong.pc.place.pojo.PlaceSetup;
import com.manage.qinggong.pc.place.pojo.PlaceSetupExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceSetupService {
    @Autowired
    private PlaceSetupMapper placeSetupMapper;
    public boolean setup(PlaceSetup placeSetup) {
        PlaceSetupExample example = new PlaceSetupExample();
        List<PlaceSetup> placeSetups = placeSetupMapper.selectByExample(example);
        if (placeSetups == null || placeSetups.size() == 0) placeSetupMapper.insert(placeSetup);
        placeSetup.setId(1);
        int i = placeSetupMapper.updateByPrimaryKeySelective(placeSetup);
        return i > 0;
    }
}
