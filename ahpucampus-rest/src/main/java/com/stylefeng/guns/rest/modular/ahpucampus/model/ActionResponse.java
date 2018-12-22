package com.stylefeng.guns.rest.modular.ahpucampus.model;

import java.io.Serializable;

/**
 * 认证的响应结果
 *
 * @author fengshuonan
 * @Date 2017/8/24 13:58
 */
public class ActionResponse<T> {

    private boolean success;
    private int code;
    private String message;
    private T data;
    private ActionResponse(T data) {
        this.success = true;
        this.code = 200;
        this.message = "成功";
        this.data = data;
    }
    private ActionResponse(CodeMsg cm) {
        if(cm == null){
            return;
        }
        this.success = cm.isSuccess();
        this.code = cm.getCode();
        this.message = cm.getMessage();
    }
    /**
     * 成功时候的调用
     * @return
     */
    public static <T> ActionResponse<T> success(T data){
        return new ActionResponse<T>(data);
    }
    /**
     * 成功，不需要传入参数
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> ActionResponse<T> success(){
        return (ActionResponse<T>) success("");
    }
    /**
     * 失败时候的调用
     * @return
     */
    public static <T> ActionResponse<T> error(CodeMsg cm){
        return new ActionResponse<T>(cm);
    }
    /**
     * 失败时候的调用,扩展消息参数
     * @param cm
     * @param msg
     * @return
     */
    public static <T> ActionResponse<T> error(CodeMsg cm,String msg){
        cm.setMessage(cm.getMessage()+"--"+msg);
        return new ActionResponse<T>(cm);
    }

    public boolean isSuccess() {
        return success;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
