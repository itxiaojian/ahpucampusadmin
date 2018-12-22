package com.stylefeng.guns.rest.modular.ahpucampus.controller;

import com.stylefeng.guns.core.util.FileUtil;
import com.stylefeng.guns.rest.modular.ahpucampus.model.User;
import com.stylefeng.guns.rest.modular.ahpucampus.service.IMessageFileService;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

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


    @PostMapping("/fileUpload")
    public String uploadMusicFile(HttpServletRequest request, @RequestParam("files") MultipartFile[] files){
        String uploadPath="E:/pic/";//存放到本地路径（示例）
        if(files!=null && files.length>=1) {
            BufferedOutputStream bw = null;
            try {
                String fileName = files[0].getOriginalFilename();
                //判断是否有文件
                if(StringUtils.isNoneBlank(fileName)) {
                    //输出到本地路径
                    File outFile = new File(
                            uploadPath + UUID.randomUUID().toString()+ FileUtil.getFileType(fileName));
//                    FileUtils.copyInputStreamToFile(files[0].getInputStream(), outFile);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if(bw!=null) {bw.close();}
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "success";
    }
}
