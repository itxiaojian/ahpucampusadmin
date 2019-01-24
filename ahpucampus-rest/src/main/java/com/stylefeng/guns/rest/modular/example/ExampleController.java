package com.stylefeng.guns.rest.modular.example;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.core.enums.userInfoEnum;
import com.stylefeng.guns.rest.modular.ahpucampus.dao.UserMapper;
import com.stylefeng.guns.rest.modular.ahpucampus.model.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 常规控制器
 *
 * @author fengshuonan
 * @date 2017-08-23 16:02
 */
@Controller
@RequestMapping("/hello")
public class ExampleController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Resource
    UserMapper userMapper;

    @RequestMapping("")
    public ResponseEntity hello(@RequestBody User user) {
        user.setVersion(userInfoEnum.USER_LOGIN_ROLE_CUSTOMER.getValue());
        user.setStatus(userInfoEnum.USER_STATUS_NORMAL.getValue());
        user.setCreatetime(new Date());
        EntityWrapper<User> wrapper = new EntityWrapper<>();
        wrapper.eq("openId",user.getOpenId());
        if(StringUtils.isNotEmpty(user.getOpenId())){
            if(userMapper.selectList(wrapper).size()==0){
                userMapper.insert(user);

            }else{
                userMapper.update(user,wrapper);
            }
        }
        logger.info("hello-User={}",user.toString());
        return ResponseEntity.ok("请求成功!");
    }
}
