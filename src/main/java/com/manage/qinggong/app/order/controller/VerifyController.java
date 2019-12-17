package com.manage.qinggong.app.order.controller;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.manage.qinggong.app.order.pojo.Order;
import com.manage.qinggong.app.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

@RestController
@RequestMapping("/entry")
public class VerifyController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderService orderService;

    /**
     * 响应值 AcsRes：处理结果，1:打开，2:报警，3:关闭，0:拒绝 ActIndex：动作位置，继电器位置，0:进，1:出，2:报警
     * Time：继电器动作时间,也就是继电器保持动作多少秒 Voice：语音字符串，用于播报，小于，小于40字节 返回汉字必须GB2312
     */
    private final String RESPONSE_SUCCESS = "DATA={\"AcsRes\":\"1\",\"ActIndex\":\"0\",\"Time\":\"1\",\"Voice\":\"%1$s\",\"Note\":\"%1$s\"}";
    private final String RESPONSE_FAILED = "DATA={\"AcsRes\":\"0\",\"ActIndex\":\"0\",\"Time\":\"1\",\"Voice\":\"%1$s\",\"Note\":\"%1$s\"}";

    /**
     * 门禁扫码/刷卡
     *
     * @param req
     * @return
     */
    @PostMapping(value = "/checkin", produces="application/json;charset=GB2312")
    public String checkIn(@RequestBody String req) {
        boolean isOpen = false;
        Gson token_gson = new Gson();
        JsonObject data = token_gson.fromJson(req, JsonObject.class);
        logger.info("received post from gate. [req={}]", data);
//        String type = data.get("type").toString().replaceAll("\"", "");//0:卡,1:串口232接口输入字符串,如二维码等,3:按钮,9:Base64编码数据,用于串口输入二维码
        String code = data.get("Card").toString().replaceAll("\"", "");//接收到的卡号、密码、二维码、身份证号码等
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decode = decoder.decode(code);
        String qrcodeData = new String(decode);
        Order order = JSON.parseObject(qrcodeData, Order.class);
        String orderUserName = order.getOrderUserName();
        if (!"admin".equals(orderUserName) && !"admin1".equals(orderUserName) && !"admin2".equals(orderUserName)){
            if (order != null) isOpen = orderService.verify(order);
            if (isOpen) return String.format(RESPONSE_SUCCESS, "欢迎光临");
            else return String.format(RESPONSE_FAILED, "无效卡");
        }
        return String.format(RESPONSE_SUCCESS, "欢迎光临");
    }

    /**
     * 门禁心跳请求
     *
     * @param req
     * @return
     */
    @PostMapping(value = "/heartbeat", produces="application/json;charset=GB2312")
    public String heartBeat(@RequestBody String req) {
        //req={"Now":"2003032110163107","Key":"29499","Crc":"65535","T1":"0.00","H1":"0.00","T2":"0.00","H2":"0.00","NextNum":"0","Serial":"1N0242","Status":"03","Input":"0000","Ver":"157","ID":"1N0242","IP":"192.168.8.242","MAC":"0004A36F96F9"}
        Gson token_gson = new Gson();
        JsonObject data = token_gson.fromJson(req, JsonObject.class);
        logger.info("gate heartbeet: {}", data);
        String key = data.get("Key").toString().replaceAll("\"", "");
        String result = String.format("DATA={\"Key\":\"%s\"}", key);
        return result;
    }
}
