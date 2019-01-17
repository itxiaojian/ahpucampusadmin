package com.stylefeng.guns.rest.modular.ahpucampus.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.rest.modular.ahpucampus.model.MessageFile;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * <p>
 * 消息有关文件表 服务类
 * </p>
 *
 * @author sliu123
 * @since 2018-12-10
 */
public interface IMessageFileService extends IService<MessageFile> {

    boolean fileUpload(HttpServletRequest request, MultipartFile[] files);


    void getFile(HttpServletRequest request, HttpServletResponse response,String filetype,String fileId) throws IOException;


}
