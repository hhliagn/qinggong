package com.manage.qinggong.pc.place.controller;

import com.manage.qinggong.base.pojo.ErrorCode;
import com.manage.qinggong.base.pojo.Response;
import com.manage.qinggong.pc.place.pojo.PlaceSetup;
import com.manage.qinggong.pc.place.service.PlaceSetupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/placeSetup")
public class PlaceSetupController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PlaceSetupService placeSetupService;

    @GetMapping(value = "/default")
    public Response defaultSetup(){
        return this.placeSetupService.find();
    }

    @PostMapping(value = "")
    public Response setup(PlaceSetup placeSetup){
        if (placeSetup == null) return new Response("对象为空", ErrorCode.ERROR);
        String openTime = placeSetup.getOpenTime();
        String[] open = openTime.split("-");
        Integer openBegin = Integer.valueOf(open[0]);
        Integer openEnd = Integer.valueOf(open[1]);
        if (openBegin < 1 || openBegin > 7 || openEnd < 1 || openEnd > 7) return new Response("时间设置错误", ErrorCode.ERROR);
        boolean flag = placeSetupService.setup(placeSetup);
        if (flag) return new Response("设置成功", ErrorCode.SUCCESS);
        return new Response("设置失败", ErrorCode.ERROR);
    }
}


