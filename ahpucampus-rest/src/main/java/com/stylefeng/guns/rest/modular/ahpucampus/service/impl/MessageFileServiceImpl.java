package com.stylefeng.guns.rest.modular.ahpucampus.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.core.util.FileUtil;
import com.stylefeng.guns.rest.modular.ahpucampus.dao.MessageFileMapper;
import com.stylefeng.guns.rest.modular.ahpucampus.model.MessageFile;
import com.stylefeng.guns.rest.modular.ahpucampus.service.IMessageFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
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


    static final List<String> fileTypes = new ArrayList<String>();

    static {
        fileTypes.add("jpg");
        fileTypes.add("jpeg");
        fileTypes.add("bmp");
        fileTypes.add("gif");
        fileTypes.add("png");
        fileTypes.add("pdf");
        fileTypes.add("doc");
        fileTypes.add("docx");
        fileTypes.add("xls");
        fileTypes.add("xlsx");
        fileTypes.add("zip");
        fileTypes.add("rar");
    }

    @Override
    public boolean fileUpload(HttpServletRequest request, MultipartFile[] files) throws FileNotFoundException {
        String messageId = request.getParameter("pid");

        for (MultipartFile attachMentFile : files) {
            MessageFile messageFile = new MessageFile();
            messageFile.setMessageId(Integer.parseInt(messageId));
            String filename = ""; //文件名
            String realPath = null;  //文件路径

            String path = ResourceUtils.getURL("classpath:").getPath();

            String uploadUrl = new File(new File(new File(path).getParent()).getParent()).getParent() + File.separator + "gunsUploadFile";


            if (attachMentFile != null) {
                File file = FileUtil.getFile(attachMentFile, uploadUrl, fileTypes);
                realPath = file.toString();
                filename = file.getName();
            }

            messageFile.setUrl(realPath);
            messageFile.setFileName(filename);

            insert(messageFile);


        }
        return true;

    }
}
