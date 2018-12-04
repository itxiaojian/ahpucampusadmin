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

        String userName = credence.getUserName();
        String password = credence.getPassword();
        if(USER_NAME.equals(userName)&&PASSWORD.equals(password)){
            return true;
        } else {
            return false;
        }
    }
}
