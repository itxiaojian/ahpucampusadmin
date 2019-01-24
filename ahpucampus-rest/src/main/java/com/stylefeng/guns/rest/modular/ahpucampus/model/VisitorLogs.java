package com.stylefeng.guns.rest.modular.ahpucampus.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 浏览记录
 * </p>
 *
 * @author sliu123
 * @since 2019-01-24
 */
@TableName("bus_visitor_logs")
public class VisitorLogs extends Model<VisitorLogs> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 浏览记录类型
     */
    private Integer logType;
    /**
     * 帖子id
     */
    private Integer messageId;
    /**
     * 浏览人openId
     */
    private String openId;
    /**
     * 浏览时头像链接
     */
    private String avatar;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 创建时间，浏览时间
     */
    private Date createTime;

    @TableField(exist = false)
    private int actionType;

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "VisitorLogs{" +
        "id=" + id +
        ", logType=" + logType +
        ", messageId=" + messageId +
        ", openId=" + openId +
        ", avatar=" + avatar +
        ", nickName=" + nickName +
        ", createTime=" + createTime +
        "}";
    }
}
