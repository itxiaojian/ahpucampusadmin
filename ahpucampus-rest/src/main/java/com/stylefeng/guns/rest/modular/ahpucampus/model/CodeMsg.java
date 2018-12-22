package com.stylefeng.guns.rest.modular.ahpucampus.model;

public class CodeMsg {
    private boolean success;
    private int code;
    private String message;
    // 按照模块定义CodeMsg
    // 通用异常
    public static CodeMsg SUCCESS = new CodeMsg(true,200,"success");
    public static CodeMsg SERVER_EXCEPTION = new CodeMsg(false,500100,"服务端异常");
    public static CodeMsg PARAMETER_ISNULL = new CodeMsg(false,500101,"输入参数为空");
    // 业务异常
    public static CodeMsg USER_NOT_EXSIST = new CodeMsg(false,500102,"用户不存在");
    public static CodeMsg ONLINE_USER_OVER = new CodeMsg(false,500103,"在线用户数超出允许登录的最大用户限制。");
    public static CodeMsg SESSION_NOT_EXSIST =  new CodeMsg(false,500104,"不存在离线session数据");
    public static CodeMsg NOT_FIND_DATA = new CodeMsg(false,500105,"查找不到对应数据");


    private CodeMsg(boolean success,int code, String message) {
        this.success =success;
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}