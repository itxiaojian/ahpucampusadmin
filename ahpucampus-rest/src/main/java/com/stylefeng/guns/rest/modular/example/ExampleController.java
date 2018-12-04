package com.stylefeng.guns.rest.modular.example;

import com.stylefeng.guns.rest.modular.ahpucampus.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @RequestMapping("")
    public ResponseEntity hello(@RequestBody User user) {
        logger.info("hello-User={}",user.toString());
        return ResponseEntity.ok("请求成功!");
    }
}
