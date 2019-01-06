package com.stylefeng.guns.rest.modular.ahpucampus.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.core.util.FileUtil;
import com.stylefeng.guns.rest.modular.ahpucampus.dao.MessageFileMapper;
import com.stylefeng.guns.rest.modular.ahpucampus.model.MessageFile;
import com.stylefeng.guns.rest.modular.ahpucampus.service.IMessageFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 消息有关文件表 服务实现类
 * </p>
 *
 * @author sliu123
 * @since 2018-12-10
 */
@Service
public class MessageFileServiceImpl extends ServiceImpl<MessageFileMapper, MessageFile> implements IMessageFileService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    static final List<String> fileTypes = new ArrayList<String>();

    static {
        fileTypes.add("jpg");
        fileTypes.add("jpeg");
        fileTypes.add("gif");
        fileTypes.add("png");
    }

    @Override
    public boolean fileUpload(HttpServletRequest request, MultipartFile[] files) throws FileNotFoundException {
        String messageId = request.getParameter("pid");

        for (MultipartFile attachMentFile : files) {
            MessageFile messageFile = new MessageFile();
            messageFile.setMessageId(Integer.parseInt(messageId));
            String filename = ""; //文件名
            String realPath = "";  //文件路径
            String previewPath = "";  //预览文件路径

            String path = ResourceUtils.getURL("classpath:").getPath();

            //源文件存放路径
            String uploadUrl = new File(new File(new File(path).getParent()).getParent()).getParent() + File.separator + "gunsUploadFile";
            //预览图片路径
            String previewUrl = uploadUrl + File.separator + "preview";


            if (attachMentFile != null) {
                //文件上传
                File file = FileUtil.getFile(attachMentFile, uploadUrl, fileTypes);
                //文件压缩形成预览图片
                File previewFile = FileUtil.scale(file,previewUrl);
                realPath = file.toString();
                filename = file.getName();
                previewPath = previewFile.toString();
            }

            messageFile.setUrl(realPath);
            messageFile.setPreviewUrl(previewPath);
            messageFile.setFileName(filename);

            insert(messageFile);


        }
        return true;

    }

    @Override
    public void getFile(HttpServletRequest request, HttpServletResponse response,
                        String filetype,String fileId) {
        response.setContentType("image/png");
        Wrapper<MessageFile> messageFileWrapper = new EntityWrapper<>();
        messageFileWrapper.eq("id",fileId);
        MessageFile messageFile = selectOne(messageFileWrapper);
        if(null != messageFile){
            String path  = messageFile.getPreviewUrl();
             if("original".equals(filetype)){
                path =messageFile.getUrl();
            }

            if(StringUtils.isEmpty(path)){
                try {
                    path =  ResourceUtils.getURL("classpath:").getPath()
                            + File.separator + "static"+ File.separator +"404.png";
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    logger.info("获取图片失败url{}",path);

                }
            }

            FileUtil.getFile(response,path);
        }
    }


}
