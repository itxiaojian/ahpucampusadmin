package com.stylefeng.guns.core.util;

import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.exception.GunsExceptionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.*;
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

    /**
     * 获取根目录
     * @param subdirectory
     * @return
     */
    public static String  getPath(String subdirectory){
        //获取跟目录---与jar包同级目录的upload目录下指定的子目录subdirectory
        File upload = null;
        try {
            //本地测试时获取到的是"工程目录/target/upload/subdirectory
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if(!path.exists()) path = new File("");
            upload = new File(path.getAbsolutePath(),subdirectory);
            if(!upload.exists()) upload.mkdirs();//如果不存在则创建目录
            String realPath = upload + File.separator;
            return realPath;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("获取服务器路径发生错误！");
        }
    }

    /**
     * 获取随机文件名
     * @return
     */

    private static String getUUFileName(){
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
                + (new Random().nextInt(9000) % (9000 - 1000 + 1) + 1000);
    }

    /**
     * 流上传文件方法
     * @param file
     * @param subdirectory
     * @param fileTypes
     * @return
     */


    public static File uploadFile(MultipartFile file, String subdirectory, List<String> fileTypes) throws IOException {
        String fileName = file.getOriginalFilename();
        // 获取上传文件类型的扩展名,先得到.的位置，再截取从.的下一个位置到文件的最后，最后得到扩展名
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        // 对扩展名进行小写转换
        ext = ext.toLowerCase();

        if (fileTypes.contains(ext)) { // 如果扩展名属于允许上传的类型，则创建文件

            //上传文件路径
            String path = getPath(subdirectory);
            log.info("上传文件路径==={}",path);
            //重新修改文件名防止重名
            String filename = getUUFileName() + "." + ext;
            File filepath = new File(path, filename);
            //判断路径是否存在，没有就创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            //将上传文件保存到一个目标文档中
            File newFile = new File(path + File.separator + filename);
            file.transferTo(newFile);
            return newFile;

        }else{

            throw new GunsException(GunsExceptionEnum.FILE_TYPE_ERROR);

        }

    }

    /**
     * 裁剪图片上传
     * @param file
     * @param subdirectory
     * @return
     */

    public static File scale(File file,String subdirectory) throws IOException {
        //上传文件路径
        String fileUrl = getPath(subdirectory);
        BufferedImage src = null; // java.awt.image 包下的
        Image image = null; // 抽象类 Image 是表示图形图像的所有类的超类。 java.awt 包下的
        BufferedImage tag = null;
        String type="png";
        String fileName= file.getName();
        log.info("=========2222"+fileName);
        String path = file.getPath();
        String suffix = fileName.substring(fileName.lastIndexOf('.'));
        String newfileName = getUUFileName() + suffix;
        log.info("-----------------12------------->"+path);
        File folder = new File(fileUrl);

        if(!folder.exists()){
            folder.mkdirs();
        }

        File newFile = new File(fileUrl + File.separator +newfileName);


        try {
            src = ImageIO.read(new File(path)); // 读入文件
            if (src != null) {
                int width = src.getWidth(); // 得到源图宽
                int height = src.getHeight(); // 得到源图长
                width = 120; // 设置新图片的宽度
                height = 55; // 设置新图片的长度
                image = src.getScaledInstance(width, height,Image.SCALE_DEFAULT);
                tag = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(image, 0, 0, null); // 绘制缩小后的图
                g.dispose();
                ImageIO.write(tag, type, newFile);// 输出到文件流
            }
        }  finally {
            if (src != null) {
                src.flush();
                image.flush();
                tag.flush();
            }
        }
        return newFile;
    }




    /**
     * 以base64编码格式上传，将照片转成字节流
     * @param imageFile
     * @param subdirectory
     * @return
     */
    public static Map<String,String> getImg(String imageFile,String subdirectory){
        // 通过base64来转化图片
        String type = imageFile.substring(imageFile.indexOf("/")+1,imageFile.indexOf(";"));
        if (type.equals("png")){
            imageFile = imageFile.replaceAll("data:image/png;base64,", "");
        }
        if (type.equals("jpeg")){
            imageFile = imageFile.replaceAll("data:image/jpeg;base64,", "");
        }
        BASE64Decoder decoder = new BASE64Decoder();
        // Base64解码
        byte[] imageByte = null;
        try {
            imageByte = decoder.decodeBuffer(imageFile);
            for (int i = 0; i < imageByte.length; ++i) {
                if (imageByte[i] < 0) {// 调整异常数据
                    imageByte[i] += 256;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        type = "." + type;
        return saveImg(imageByte,subdirectory,type);
    }

    //存储照片到服务器
    private static Map<String,String> saveImg(byte[] imageByte,String subdirectory,String type){
        // 生成文件名及文件类型
        String files = new SimpleDateFormat("yyyyMMddHHmmssSSS")
                .format(new Date())
                + (new Random().nextInt(9000) % (9000 - 1000 + 1) + 1000)
                + type;

        Map<String,String> map = new HashMap<>();
        //获取跟目录---与jar包同级目录并生成文件路径
        String filename = FileUtil.getPath(subdirectory) + files;
        try {
            // 生成文件
            File imageFile = new File(filename);
            imageFile.createNewFile();
            if(!imageFile.exists()){
                imageFile.createNewFile();
            }
            OutputStream imageStream = new FileOutputStream(imageFile);
            imageStream.write(imageByte);
            imageStream.flush();
            imageStream.close();
            map.put("res","success");
            map.put("url",files);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("res","error");
            return map;
        }
    }

    public static void getFile( HttpServletResponse response,String path){
        try {
            FileInputStream in = new FileInputStream(new File(path));
            OutputStream out = response.getOutputStream();
            byte[] b = new byte[512];

            while ((in.read(b)) != -1) {
                out.write(b);
            }

            out.flush();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

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