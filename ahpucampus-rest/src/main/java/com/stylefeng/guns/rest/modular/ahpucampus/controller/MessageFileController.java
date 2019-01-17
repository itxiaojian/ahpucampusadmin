package com.stylefeng.guns.rest.modular.ahpucampus.controller;

import com.stylefeng.guns.rest.modular.ahpucampus.model.ActionResponse;
import com.stylefeng.guns.rest.modular.ahpucampus.model.CodeMsg;
import com.stylefeng.guns.rest.modular.ahpucampus.service.IMessageFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public ActionResponse<?> messageFileUpload(HttpServletRequest request, @RequestParam("files") MultipartFile[] files) {
        ActionResponse response  = null;
        if(files!=null && files.length>=1) {

            boolean success = iMessageFileService.fileUpload(request,files);

            if(success){
                response = ActionResponse.success();
            }else{
                response = ActionResponse.error(CodeMsg.SERVER_EXCEPTION);
            }
        }
        logger.info("/messagefile/upload返回{}",response.toString());
        return response;
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
