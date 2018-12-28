package com.stylefeng.guns.rest.modular.ahpucampus.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.rest.modular.ahpucampus.model.ActionResponse;
import com.stylefeng.guns.rest.modular.ahpucampus.model.Message;
import com.stylefeng.guns.rest.modular.ahpucampus.service.IMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 消息发布控制器
 *
 * @author fengshuonan
 * @date 2017-08-23 16:02
 */
@Controller
@RequestMapping("/message")
public class MessageController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IMessageService iMessageService;

    @RequestMapping("/save")
    @ResponseBody
    public ActionResponse<?> messageSave(@RequestBody Message message) {
        message.setCreatetime(new Date());
        int messageId = iMessageService.insert4primarykey(message);

        JSONObject responsedata = new JSONObject();
        responsedata.put("messageId",messageId);

        return ActionResponse.success(responsedata);
    }

    @RequestMapping("/queryList")
    @ResponseBody
    public ActionResponse<?> queryMessageList(HttpServletRequest request) {
        String page = request.getParameter("page");
        String pageSize = request.getParameter("pageSize");
        String _object = request.getParameter("_object");
        String sign = request.getParameter("sign");
        Object o = request.getParameterNames();
        Wrapper<Message> messageEntityWrapper = new EntityWrapper<Message>();
        List<Message> message = iMessageService.selectList(messageEntityWrapper);


        JSONObject responsedata = new JSONObject();
        responsedata.put("list",message);

        return ActionResponse.success(responsedata);
    }
}
