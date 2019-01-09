package com.stylefeng.guns.rest.modular.ahpucampus.controller;

import com.stylefeng.guns.rest.modular.ahpucampus.service.IMessageFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 消息发布控制器
 *
 * @author fengshuonan
 * @date 2017-08-23 16:02
 */
@Controller
@RequestMapping("/messagefile")
public class MessageFileController {

    @Autowired
    private IMessageFileService iMessageFileService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/upload")
    public ResponseEntity messageFileUpload(HttpServletRequest request, @RequestParam("files") MultipartFile[] files) {
        if(files!=null && files.length>=1) {
            try {
                iMessageFileService.fileUpload(request,files);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.ok("请求成功!");
    }

    @RequestMapping(value="/getFile/{filetype}/{fileId}")
    public void getFile(HttpServletRequest request, HttpServletResponse response,
                        @PathVariable String filetype,@PathVariable String fileId) {
        try {
            iMessageFileService.getFile(request,response,filetype,fileId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
