package com.stylefeng.guns.rest.modular.auth.validator.impl;

import com.stylefeng.guns.core.wechat.model.WechatOauth2Token;
import com.stylefeng.guns.core.wechat.utils.WechatUtils;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthRequest;
import com.stylefeng.guns.rest.modular.auth.validator.IReqValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 直接验证账号密码是不是admin
 *
 * @author fengshuonan
 * @date 2017-08-23 12:34
 */
@Service
public class SimpleValidator implements IReqValidator {

    private static String USER_NAME = "admin";

    private static String PASSWORD = "admin";

    @Override
    public boolean validate(AuthRequest credence) {

        String code = credence.getCode();

        WechatOauth2Token weiXinOauth2Token = WechatUtils.getOauth2AccessToken(code);// 根据code获取 页面授权信息类
        String openId ="";
        if(weiXinOauth2Token != null){
            openId = weiXinOauth2Token.getOpenId();
        }
        if(StringUtils.isNotEmpty(openId)){
            credence.setOpenId(openId);
            return true;
        } else {
            return false;
        }
    }
}
