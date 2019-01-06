package com.stylefeng.guns.rest.modular.ahpucampus.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.core.util.Bean2MapUtil;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.rest.common.persistence.model.RequestParamDto;
import com.stylefeng.guns.rest.modular.ahpucampus.model.ActionResponse;
import com.stylefeng.guns.rest.modular.ahpucampus.model.Message;
import com.stylefeng.guns.rest.modular.ahpucampus.model.MessageFile;
import com.stylefeng.guns.rest.modular.ahpucampus.model.User;
import com.stylefeng.guns.rest.modular.ahpucampus.service.IMessageFileService;
import com.stylefeng.guns.rest.modular.ahpucampus.service.IMessageService;
import com.stylefeng.guns.rest.modular.ahpucampus.service.IUserService;
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

    @RequestMapping("/save")
    @ResponseBody
    public ActionResponse<?> messageSave(@RequestBody Message message) {
        message.setCreatetime(DateUtil.parseTime(DateUtil.getTime()));
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


}
