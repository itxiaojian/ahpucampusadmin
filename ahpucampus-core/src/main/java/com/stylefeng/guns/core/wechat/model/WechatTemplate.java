package com.stylefeng.guns.core.wechat.model;

import java.util.Map;

/** 
 * @author:zhangyi 
 * @version 创建时间：2016年4月15日 上午11:59:19 
 * 微信模板消息类 
 */

public class WechatTemplate implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

    /**
     * 模板消息id
     */
    private String template_id;
    /**
     * 用户openId
     */
    private String touser;
    /**
     * URL置空，则在发送后，点击模板消息会进入一个空白页面（ios），或无法点击（android）
     */
    private String page;
    /**
     * 标题颜色
     */
    private String form_id;

    /**
     * 需要放大内容
     */
    private String emphasis_keyword;

    /**
     * 模板字体颜色，默认黑色
     */
    private String color;


    /**
     * 详细内容
     */
    private Map<String,TemplateDataView> data;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getEmphasis_keyword() {
        return emphasis_keyword;
    }

    public void setEmphasis_keyword(String emphasis_keyword) {
        this.emphasis_keyword = emphasis_keyword;
    }

    public String getForm_id() {
        return form_id;
    }

    public void setForm_id(String form_id) {
        this.form_id = form_id;
    }

    public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getTouser() {
        return touser;
    }
    public void setTouser(String touser) {
        this.touser = touser;
    }
    public Map<String, TemplateDataView> getData() {
        return data;
    }
    public void setData(Map<String, TemplateDataView> data) {
        this.data = data;
    }
}