package com.stylefeng.guns.modular.system.model;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 消息表
 * </p>
 *
 * @author sliu123
 * @since 2018-12-10
 */
@TableName("bus_message")
public class Message extends Model<Message> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 发帖者openId
     */
    private String openId;
    /**
     * 消息类型（0失物，1寻物...）
     */
    @TableField("message_type")
    private String messageType;
    /**
     * 事件描述
     */
    private String content;
    /**
     * 逻辑删除标示（0,显示，1删除）
     */
    private Integer isShow;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 详细地址
     */
    @TableField("detail_address")
    private String detailAddress;
    /**
     * 省份
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区/县
     */
    private String district;
    /**
     * 镇
     */
    private String town;
    /**
     * 创建时间
     */
    private Date createtime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Message{" +
        "id=" + id +
        ", openId=" + openId +
        ", messageType=" + messageType +
        ", content=" + content +
        ", isShow=" + isShow +
        ", longitude=" + longitude +
        ", latitude=" + latitude +
        ", detailAddress=" + detailAddress +
        ", province=" + province +
        ", city=" + city +
        ", district=" + district +
        ", town=" + town +
        ", createtime=" + createtime +
        "}";
    }
}
