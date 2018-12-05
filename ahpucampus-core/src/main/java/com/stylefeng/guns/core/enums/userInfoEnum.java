package com.stylefeng.guns.core.enums;

public enum userInfoEnum {

    /**
     * 登录账号类型
     */
    USER_VERSION_MANAGE(1, "管理"),
    USER_LOGIN_ROLE_CUSTOMER(2, "客户"),
    USER_STATUS_NORMAL(1,"启用"),
    USER_STATUS_FREEZE(2,"冻结"),
    USER_STATUS_DELETE(3,"删除");




    /**
     * 角色值
     */
    private Integer value;

    /**
     * 状态描述
     */
    private String desc;

    /**
     * 构造函数
     *
     * @param value
     * @param desc
     */
    userInfoEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getValue() {
        return value;
    }


}
