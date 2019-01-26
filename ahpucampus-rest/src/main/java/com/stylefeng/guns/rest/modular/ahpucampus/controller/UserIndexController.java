package com.stylefeng.guns.rest.modular.ahpucampus.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.core.util.Bean2MapUtil;
import com.stylefeng.guns.rest.common.persistence.model.RequestParamDto;
import com.stylefeng.guns.rest.modular.ahpucampus.model.*;
import com.stylefeng.guns.rest.modular.ahpucampus.service.*;
import org.apache.commons.collections.CollectionUtils;
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
import java.util.Map;

/**
 * 消息发布控制器
 *
 * @author fengshuonan
 * @date 2017-08-23 16:02
 */
@Controller
@RequestMapping("/userindex")
public class UserIndexController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private INoticeService noticeService;

    @RequestMapping("/getAboutInfo")
    @ResponseBody
    public ActionResponse<?> getAboutInfo(@RequestBody Notice notice) {

        Map<String,Object> aboutInfo = noticeService.getAboutInfo();

        JSONObject responsedata = new JSONObject();
        responsedata.put("aboutInfo",aboutInfo);

        return ActionResponse.success(responsedata);
    }


}
