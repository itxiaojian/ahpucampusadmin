package com.stylefeng.guns.core.util;

import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.exception.GunsExceptionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

public class FileUtil {

    private static Logger log = LoggerFactory.getLogger(FileUtil.class);

    /**
     * NIO way
     */
    public static byte[] toByteArray(String filename) {

        File f = new File(filename);
        if (!f.exists()) {
            log.error("文件未找到！" + filename);
            throw new GunsException(GunsExceptionEnum.FILE_NOT_FOUND);
        }
        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                // do nothing
                // System.out.println("reading");
            }
            return byteBuffer.array();
        } catch (IOException e) {
            throw new GunsException(GunsExceptionEnum.FILE_READING_ERROR);
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                throw new GunsException(GunsExceptionEnum.FILE_READING_ERROR);
            }
            try {
                fs.close();
            } catch (IOException e) {
                throw new GunsException(GunsExceptionEnum.FILE_READING_ERROR);
            }
        }
    }

    /**
     * 删除目录
     *
     * @author fengshuonan
     * @Date 2017/10/30 下午4:15
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }


    public static File getFile(MultipartFile imgFile, String brandName, List<String> fileTypes) {
        String fileName = imgFile.getOriginalFilename();
        // 获取上传文件类型的扩展名,先得到.的位置，再截取从.的下一个位置到文件的最后，最后得到扩展名
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1,fileName.length());
        // 对扩展名进行小写转换
        ext = ext.toLowerCase();
        File file = null;
        if (fileTypes.contains(ext)) { // 如果扩展名属于允许上传的类型，则创建文件
            file = creatFolder(brandName, fileName);
            try {
                imgFile.transferTo(file); // 保存上传的文件
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            throw new GunsException(GunsExceptionEnum.FILE_TYPE_ERROR);
        }
        return file;
    }

    public static File creatFolder(String brandName, String fileName) {
        File file = null;
        File firstFolder = new File(brandName);
        String suffix = fileName.substring(fileName.lastIndexOf('.'));
        String prefix = System.currentTimeMillis() + "";
        String newfileName = prefix + suffix;
        if (firstFolder.exists()) { // 如果一级文件夹存在，则检测二级文件夹
            file = new File(brandName + "\\" + newfileName);
        } else { // 如果一级不存在，则创建一级文件夹
            firstFolder.mkdir();
            file = new File(brandName + "\\" + newfileName);
        }
        return file;
    }

    public static void downLoadFile(HttpServletRequest request, HttpServletResponse response, String fileUrl, String fileName){
        try {
            if (request.getHeader("User-Agent").toLowerCase()
                    .indexOf("firefox") > 0) {
                fileName = new String(fileName.getBytes("UTF-8"),
                        "ISO8859-1");// firefox浏览器
            } else if (request.getHeader("User-Agent").toUpperCase()
                    .indexOf("MSIE") > 0) {
                fileName = URLEncoder.encode(fileName, "UTF-8");// IE浏览器
            } else {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            }

            FileInputStream fis = new FileInputStream(new File(fileUrl));
            response.setHeader("Content-Disposition", "attachment; filename=\""+ fileName + "\"");
            response.setContentType("application/octet-stream");
            OutputStream os = response.getOutputStream();
            byte[] b = new byte[1024];
            int i = 0;
            while ((i = fis.read(b)) > 0) {
                os.write(b, 0, i);
            }
            fis.close();
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String getFileType(String filename){
        if(filename.endsWith(".jpg") || filename.endsWith(".jepg")){
            return ".jpg";
        }else if(filename.endsWith(".png") || filename.endsWith(".PNG")){
            return ".png";
        } else{
            return "application/octet-stream";
        }
    }
}