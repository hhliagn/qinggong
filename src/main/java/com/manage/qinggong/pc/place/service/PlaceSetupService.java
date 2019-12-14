package com.manage.qinggong.pc.place.service;

import com.manage.qinggong.base.pojo.ErrorCode;
import com.manage.qinggong.base.pojo.Response;
import com.manage.qinggong.pc.place.mapper.PlaceSetupMapper;
import com.manage.qinggong.pc.place.pojo.PlaceSetup;
import com.manage.qinggong.pc.place.pojo.PlaceSetupExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PlaceSetupService {
    @Autowired
    private PlaceSetupMapper placeSetupMapper;
    public boolean setup(PlaceSetup placeSetup) {
        PlaceSetupExample example = new PlaceSetupExample();
        List<PlaceSetup> placeSetups = placeSetupMapper.selectByExample(example);
        if (placeSetups == null || placeSetups.size() == 0) {
            placeSetup.setStatus(0);
            placeSetup.setCreateTime(new Date());
            placeSetupMapper.insert(placeSetup);
        }
        placeSetup.setId(1);
        placeSetup.setUpdateTime(new Date());
        int i = placeSetupMapper.updateByPrimaryKeySelective(placeSetup);
        return i > 0;
    }

    public Response find() {
        PlaceSetup placeSetup = placeSetupMapper.selectByPrimaryKey(1);
        return new Response("默认展馆设置", ErrorCode.SUCCESS, placeSetup);
    }
}
