package com.stylefeng.guns.rest.modular.ahpucampus.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.core.util.Bean2MapUtil;
import com.stylefeng.guns.rest.common.persistence.model.RequestParamDto;
import com.stylefeng.guns.rest.modular.ahpucampus.model.*;
import com.stylefeng.guns.rest.modular.ahpucampus.service.IMessageFileService;
import com.stylefeng.guns.rest.modular.ahpucampus.service.IMessageService;
import com.stylefeng.guns.rest.modular.ahpucampus.service.IUserService;
import com.stylefeng.guns.rest.modular.ahpucampus.service.IVisitorLogsService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @Autowired
    private IMessageFileService messageFileService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IVisitorLogsService visitorLogsService;

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
    public ActionResponse<?> queryMessageList(@RequestBody RequestParamDto requestParamDto) {
        int page = requestParamDto.getPage();
        int pageSize = requestParamDto.getPageSize();
        String activeIndex = requestParamDto.getActiveIndex();

        int begin = page*pageSize;
        int pagesize = pageSize;
        Wrapper<Message> messageEntityWrapper = new EntityWrapper<>();

        messageEntityWrapper.eq("message_type",activeIndex);

        messageEntityWrapper.orderBy("id",false).last(" LIMIT "+begin+","+pagesize);

        List<Message> messageList = iMessageService.selectList(messageEntityWrapper);

        if(!CollectionUtils.isEmpty(messageList)){
            for (Message message:messageList) {

                String openId = message.getOpenId();

                Wrapper<User> userWrapper = new EntityWrapper<>();

                userWrapper.eq("openId",openId);
                User user = userService.selectOne(userWrapper);

                if(user!=null){
                    message.setHeadimgurl(user.getAvatarUrl());
                    message.setNickName(user.getNickName());
                }

                int messageId = message.getId();
                Wrapper<MessageFile>messageFileWrapper = new EntityWrapper<>();
                messageFileWrapper.eq("messageId",messageId);

                List<MessageFile> messageFilesList = messageFileService.selectList(messageFileWrapper);

                if(CollectionUtils.isNotEmpty(messageFilesList)){
                    MessageFile previewFile = messageFilesList.get(0);
                    message.setPhoto(previewFile.getId());
                }

            }
        }


        JSONObject responsedata = new JSONObject();
        responsedata.put("list",messageList);

        return ActionResponse.success(responsedata);
    }


    @RequestMapping("/queryDetail")
    @ResponseBody
    public ActionResponse<?> queryMessageDetail(@RequestBody RequestParamDto requestParamDto){
        int messageId = requestParamDto.getMessageId();
        Wrapper<Message> messageWrapper = new EntityWrapper<>();
        messageWrapper.eq("id",messageId);
        Message message = iMessageService.selectOne(messageWrapper);
        if(message!=null){

            String openId = message.getOpenId();

            Wrapper<User> userWrapper = new EntityWrapper<>();

            userWrapper.eq("openId",openId);
            User user = userService.selectOne(userWrapper);

            if(user!=null){
                message.setHeadimgurl(user.getAvatarUrl());
                message.setNickName(user.getNickName());
                message.setTelephone(user.getPhone());
            }

            Wrapper<MessageFile>messageFileWrapper = new EntityWrapper<>();
            messageFileWrapper.eq("messageId",messageId);

            List<MessageFile> messageFilesList = messageFileService.selectList(messageFileWrapper);

            if(CollectionUtils.isNotEmpty(messageFilesList)){
                MessageFile previewFile = messageFilesList.get(0);
                message.setPhoto(previewFile.getId());
            }
        }

        return ActionResponse.success(Bean2MapUtil.transBean2Map(message));

    }


    @RequestMapping("/saveOrGetVisitorLog")
    @ResponseBody
    public ActionResponse<?> saveOrGetVisitorLog(@RequestBody VisitorLogs visitorLogs){
        JSONObject responsedata = new JSONObject();

        int actionType = visitorLogs.getActionType();
        //类型1保存浏览记录
        if(actionType ==1){
            Wrapper<VisitorLogs> visitorLogsWrapper = new EntityWrapper<>();
            visitorLogsWrapper.eq("openId",visitorLogs.getOpenId()).eq("messageId",visitorLogs.getMessageId());
            if(visitorLogsService.selectCount(visitorLogsWrapper)==0){
                visitorLogs.setLogType(1);
                visitorLogs.setCreateTime(new Date());
                visitorLogsService.insert(visitorLogs);
                int messageId = visitorLogs.getMessageId();
                Wrapper<Message> messageWrapper = new EntityWrapper<>();
                messageWrapper.eq("id",messageId);
                Message message = iMessageService.selectOne(messageWrapper);
                int visitorCount = message.getVisitorCount();
                message.setVisitorCount(++visitorCount);
                iMessageService.update(message,messageWrapper);
            }else{
                VisitorLogs visitorLogshistory = visitorLogsService.selectOne(visitorLogsWrapper);
                visitorLogshistory.setUpdateTime(new Date());
                visitorLogsService.updateById(visitorLogshistory);
            }
        }

        //类型2查询浏览总数
        if(actionType ==2){
            responsedata.put("visitorCount",getVisitorCount(visitorLogs.getMessageId(),1));
        }

        return ActionResponse.success(responsedata);
    }


    private int getVisitorCount(int messageId,int logType){
        Wrapper<VisitorLogs> visitorLogsWrapper = new EntityWrapper<>();
        visitorLogsWrapper.eq("logType",logType).eq("messageId",messageId);
        return visitorLogsService.selectCount(visitorLogsWrapper);

    }


}
