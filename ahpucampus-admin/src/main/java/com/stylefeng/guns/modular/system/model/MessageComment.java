package com.stylefeng.guns.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 消息评论表
 * </p>
 *
 * @author sliu123
 * @since 2019-01-25
 */
@TableName("bus_message_comment")
public class MessageComment extends Model<MessageComment> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 消息id
     */
    private Integer messageId;
    /**
     * 发起评论人openId
     */
    private String sendOpenId;
    /**
     * 发起评论人头像
     */
    private String sendAvatar;
    /**
     * 发起消息人昵称
     */
    private String sendNickName;
    /**
     * 收到评论人openId
     */
    private String receiveOpenId;
    /**
     * 收到评论人头像
     */
    private String receiveAvatar;
    /**
     * 收到评论人昵称
     */
    private String receiveNickName;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 发起消息时间，以页面传来为准
     */
    private Integer time;


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

    public String getSendOpenId() {
        return sendOpenId;
    }

    public void setSendOpenId(String sendOpenId) {
        this.sendOpenId = sendOpenId;
    }

    public String getSendAvatar() {
        return sendAvatar;
    }

    public void setSendAvatar(String sendAvatar) {
        this.sendAvatar = sendAvatar;
    }

    public String getSendNickName() {
        return sendNickName;
    }

    public void setSendNickName(String sendNickName) {
        this.sendNickName = sendNickName;
    }

    public String getReceiveOpenId() {
        return receiveOpenId;
    }

    public void setReceiveOpenId(String receiveOpenId) {
        this.receiveOpenId = receiveOpenId;
    }

    public String getReceiveAvatar() {
        return receiveAvatar;
    }

    public void setReceiveAvatar(String receiveAvatar) {
        this.receiveAvatar = receiveAvatar;
    }

    public String getReceiveNickName() {
        return receiveNickName;
    }

    public void setReceiveNickName(String receiveNickName) {
        this.receiveNickName = receiveNickName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "MessageComment{" +
        "id=" + id +
        ", messageId=" + messageId +
        ", sendOpenId=" + sendOpenId +
        ", sendAvatar=" + sendAvatar +
        ", sendNickName=" + sendNickName +
        ", receiveOpenId=" + receiveOpenId +
        ", receiveAvatar=" + receiveAvatar +
        ", receiveNickName=" + receiveNickName +
        ", content=" + content +
        ", time=" + time +
        "}";
    }
}
