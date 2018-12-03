package com.stylefeng.guns.core.wechat.model;

/**
 * JSON 数据请求返回内容
 *
 * @datetime 2010-8-12 下午01:45:25
 * @author libinsong1204@gmail.com
 */
public class AuthorizeResponse {
	//~ Static fields ==================================================================================================
	public static final AuthorizeResponse SUCCESS_NO_DATA = new AuthorizeResponse(true,"操作成功");
	public static final AuthorizeResponse FAILED_NO_DATA = new AuthorizeResponse(false);
	public static final AuthorizeResponse FAILED_TEST_ADCONNET = new AuthorizeResponse(false,"连接ad域服务器失败");
	public static final AuthorizeResponse FAILED_TEST_FTPCONNET = new AuthorizeResponse(false,"连接ftp服务器失败");
	public static final AuthorizeResponse FAILED_DEL_OWNROLE = new AuthorizeResponse(false,"当前用户不能删除自己被授于的角色");
	public static final AuthorizeResponse FAILED_QD_ACTIVITI = new AuthorizeResponse(false,"启动失败，原因可能是用户没有对应上级或用户角色有问题");
	
	//~ Instance fields ================================================================================================
	private boolean success = true;
	private String type;
	private Object message;
	private String requestURI;
	private String execptionTrace;
	private Object data;
	
	//~ Constructors ===================================================================================================
	public AuthorizeResponse(boolean success) {
		this(success, null, null);
	}
	
	public AuthorizeResponse(boolean success, Object message) {
		this(success, null, message);
	}

	public AuthorizeResponse(boolean success, String type, Object message) {
		this.success = success;
		this.type = type;
		this.message = message;
	}

	public AuthorizeResponse(){

	}

	//~ Methods ========================================================================================================
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRequestURI() {
		return requestURI;
	}

	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}

	public String getExecptionTrace() {
		return execptionTrace;
	}

	public void setExecptionTrace(String execptionTrace) {
		this.execptionTrace = execptionTrace;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
