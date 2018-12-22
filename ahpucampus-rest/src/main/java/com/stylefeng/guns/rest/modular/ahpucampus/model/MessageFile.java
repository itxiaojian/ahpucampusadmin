package com.stylefeng.guns.rest.modular.ahpucampus.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 消息有关文件表
 * </p>
 *
 * @author sliu123
 * @since 2018-12-10
 */
@TableName("bus_message_file")
public class MessageFile extends Model<MessageFile> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer messageId;
    /**
     * 文件原名
     */
    private String fileName;
    /**
     * 文件随机名
     */
    private String uuName;
    /**
     * 文件在服务器的绝对完整路径
     */
    private String url;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUuName() {
        return uuName;
    }

    public void setUuName(String uuName) {
        this.uuName = uuName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "MessageFile{" +
        "id=" + id +
        ", messageId=" + messageId +
        ", fileName=" + fileName +
        ", uuName=" + uuName +
        ", url=" + url +
        "}";
    }
}
