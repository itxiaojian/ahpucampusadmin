package com.stylefeng.guns.core.wechat.controller;



import com.stylefeng.guns.core.wechat.model.*;
import com.stylefeng.guns.core.wechat.utils.WechatUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求验证的
 *
 * @author fengshuonan
 * @Date 2017/8/24 14:22
 */
@RestController
@RequestMapping("/wechat")
public class AuthorizeController {
    private static Logger logger = LoggerFactory.getLogger(AuthorizeController.class);

    @RequestMapping(value = "/authorize")
    @ResponseBody
    public AuthorizeResponse getWechatAuthorizeToken(@RequestBody AuthorizeRequest authorizeRequest) {
        AuthorizeResponse authorizeResponse = new AuthorizeResponse();
        logger.info("wechat/authorize接收数据：{}",authorizeRequest.toString());
        if(authorizeRequest==null || authorizeRequest.getId() ==null){
            logger.info("wechat/authorize参数有误,参数{}",authorizeRequest);
            authorizeRequest.setId("268");
//            return ;
        }

        WechatOauth2Token weiXinOauth2Token = WechatUtils.getOauth2AccessToken(authorizeRequest.getCode());// 根据code获取 页面授权信息类
        String openId ="";
        if(weiXinOauth2Token != null){
            openId = weiXinOauth2Token.getOpenId();
            authorizeResponse.setData(openId);
        }
//        WechatUtils.sendSMS();
        if(StringUtils.isNotEmpty(openId)){
            String key = authorizeRequest.getId()+"#openId";
//            redisService.set(key,openId);
            logger.info("wechat/authorize redis保存成功，参数key{}，value{}",key,openId);
        }
        return authorizeResponse;
    }

    @RequestMapping(value = "/dealFormIds")
    @ResponseBody
    public void dealFormIds(@RequestBody AuthorizeRequest authorizeRequest) {

        logger.info("wechat/dealFormIds接收数据：{}",authorizeRequest.toString());
        if(authorizeRequest==null ||
                authorizeRequest.getId() ==null ||
                StringUtils.isEmpty(authorizeRequest.getFormId())){
            logger.info("wechat/dealFormIds参数有误");
            authorizeRequest.setId("268");
//            return ;
        }
        String key = authorizeRequest.getId()+"#formId";
        //redisService.set(key,authorizeRequest.getFormId());
        //redisService.expire(key,60*60*24*7);// TODO: 2018/10/1  缓存有效期7天,注意后面需要多个表单id的时候需要调整缓存策略
        logger.info("wechat/dealFormIds redis保存成功，参数key{}，value{}",key,authorizeRequest.getFormId());
    }


    @RequestMapping(value = "/sendTemplateMsg")
    @ResponseBody
    public void sendTemplateMsg(@RequestBody AuthorizeRequest authorizeRequest) {


        WechatTemplate temp = new WechatTemplate();
        temp.setTemplate_id("3SNLXksh4qdXM3RrN0bKDXmMWh-PR6UEbbcEJKYJzyQ");  //公司
        //temp.setTouser(redisService.get("268#openId"));

        //temp.setForm_id(redisService.get("268#formId"));
        temp.setPage("");
        temp.setEmphasis_keyword("这是一条测试呢");
        temp.setColor("#000000");
        Map<String, TemplateDataView> mapdata = new HashMap<String,TemplateDataView>();

        //挂失卡片卡号
        TemplateDataView dataView2 = new TemplateDataView();
        dataView2.setColor("#000000");
        dataView2.setValue(" 测试key1");
        mapdata.put("keyword1", dataView2);

        //拾到人手机号码
        TemplateDataView dataView3 = new TemplateDataView();
        dataView3.setColor("#000000");
        dataView3.setValue("65689271");
        mapdata.put("keyword2", dataView3);
        //登记时间
        TemplateDataView dataView4 = new TemplateDataView();
        dataView4.setColor("#000000");
        dataView4.setValue(" 测试key4");
        mapdata.put("keyword3", dataView4);
        //备注
        TemplateDataView dataView5 = new TemplateDataView();
        dataView5.setColor("#99CC00");
        dataView5.setValue(" 如有疑问，请联系网络中心。");
        mapdata.put("keyword4", dataView5);


        temp.setData(mapdata);


        WechatUtils.sendTemplateMsg(temp);
    }



}

