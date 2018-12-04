package com.stylefeng.guns.rest.modular.auth.validator.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.wechat.model.WechatOauth2Token;
import com.stylefeng.guns.core.wechat.utils.WechatUtils;
import com.stylefeng.guns.rest.modular.ahpucampus.dao.UserMapper;
import com.stylefeng.guns.rest.modular.ahpucampus.model.User;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthRequest;
import com.stylefeng.guns.rest.modular.auth.validator.IReqValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 账号密码验证
 *
 * @author fengshuonan
 * @date 2017-08-23 12:34
 */
@Service
public class DbValidator implements IReqValidator {

    @Autowired
    UserMapper userMapper;

    @Override
    public boolean validate(AuthRequest credence) {
        String code = credence.getCode();
        String openId = credence.getOpenId();

        if(StringUtils.isNotEmpty(code)&&StringUtils.isEmpty(openId)){
            WechatOauth2Token weiXinOauth2Token = WechatUtils.getOauth2AccessToken(code);// 根据code获取 页面授权信息类
            if(weiXinOauth2Token != null){
                openId = weiXinOauth2Token.getOpenId();
            }
            if(StringUtils.isNotEmpty(openId)){
                credence.setOpenId(openId);
                return true;
            } else {
                return false;
            }
        }else if(StringUtils.isEmpty(code)&&StringUtils.isNotEmpty(openId)){
            List<User> users = userMapper.selectList(new EntityWrapper<User>().eq("openId", credence.getOpenId()));
            if (users != null && users.size() > 0) {
                return true;
            } else {
                return false;
            }
        }else{
            return false;
        }
    }
}
