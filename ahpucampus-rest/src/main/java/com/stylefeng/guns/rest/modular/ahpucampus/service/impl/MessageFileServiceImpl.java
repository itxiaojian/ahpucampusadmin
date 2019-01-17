package com.stylefeng.guns.rest.modular.ahpucampus.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.core.util.FileUtil;
import com.stylefeng.guns.rest.modular.ahpucampus.dao.MessageFileMapper;
import com.stylefeng.guns.rest.modular.ahpucampus.model.Message;
import com.stylefeng.guns.rest.modular.ahpucampus.model.MessageFile;
import com.stylefeng.guns.rest.modular.ahpucampus.service.IMessageFileService;
import com.stylefeng.guns.rest.modular.ahpucampus.service.IMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    @Autowired
    private IMessageService messageService;

    static final List<String> fileTypes = new ArrayList<String>();

    static {
        fileTypes.add("jpg");
        fileTypes.add("jpeg");
        fileTypes.add("gif");
        fileTypes.add("png");
    }

    //注入配置文件application.yml中设置的图片存放子目录名
    @Value("${upload.path.goodsImg}")
    private String GOODS_IMG_PATH;

    @Value("${upload.path.preview}")
    private String PREVIEW_PATH;

    @Override
    public boolean fileUpload(HttpServletRequest request, MultipartFile[] files)  {
        String messageId = request.getParameter("pid");

        for (MultipartFile attachMentFile : files) {
            MessageFile messageFile = new MessageFile();
            messageFile.setMessageId(Integer.parseInt(messageId));
            String filename = ""; //文件名
            String realPath = "";  //文件路径
            String previewPath = "";  //预览文件路径



            if (attachMentFile != null) {
                //文件上传
                File file = null;
                File previewFile = null;
                try {
                    file = FileUtil.uploadFile(attachMentFile, GOODS_IMG_PATH, fileTypes);
                    //文件压缩形成预览图片
                    previewFile = FileUtil.scale(file,PREVIEW_PATH);
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("文件上传失败异常{}",e);
                    //回滚消息记录
                    Message message = new Message();
                    message.setId(Integer.parseInt(messageId));
                    messageService.deleteById(message);
                    return false;
                }
                realPath = file.getPath();
                filename = file.getName();
                previewPath = previewFile.getPath();
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
            File img = new File(path);

            if(StringUtils.isEmpty(path) || !img.exists()){
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
