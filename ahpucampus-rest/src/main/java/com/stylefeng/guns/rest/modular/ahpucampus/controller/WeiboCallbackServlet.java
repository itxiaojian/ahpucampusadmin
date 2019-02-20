package com.stylefeng.guns.rest.modular.ahpucampus.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stylefeng.guns.core.weibo4j.util.WeiboConfig;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/WeiboCallbackServlet")
public class WeiboCallbackServlet  {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//测试回调
	@RequestMapping("/doService")
	public void doService(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String nonce = request.getParameter("nonce");
		String echoStr = request.getParameter("echostr");
		String timestamp = request.getParameter("timestamp");
		String signature = request.getParameter("signature");
		
		String returnContent = ""; //返回的响应结果。默认为空字符串。
		//验证签名
		boolean isValid = ValidateSHA(signature, nonce, timestamp);
		if(isValid){ //签名正确时，如果存在echoStr就返回echoStr，否则接收数据
			if(StringUtils.isNotBlank(echoStr)){
				//存在echoStr，是首次配置时验证url可达性。
				returnContent =  echoStr; //返回内容为echoStr的内容
			}else{
				//正常推送消息时不会存在echoStr参数。
				//接收post过来的消息数据
				StringBuilder sb = new StringBuilder();
				BufferedReader in = request.getReader();
		        String line;
		        while ((line = in.readLine()) != null) {
		            sb.append(line);
		        }
				//TODO 根据业务对消息进行处理。处理完成可以返回空串，也可以返回回复消息。
				logger.info("received message : " + sb.toString());
				if(StringUtils.isNotBlank(sb.toString())){
					Map<String,Object> dataMap = JSON.parseObject(sb.toString());

					String type = MapUtils.getString(dataMap,"type");
					if("text".equals(type)){
						String text = MapUtils.getString(dataMap,"text");
						String receiverId = MapUtils.getString(dataMap,"receiver_id");//回复消息的发送方id。即蓝v自己的uid
						String senderId = MapUtils.getString(dataMap,"sender_id"); //回复消息的接收方id。蓝v粉丝的uid。这个字段需要在接收的推送消息中获取。
						returnContent = generateReplyMsg(textMsg(), "text",receiverId,senderId);//回复text类型消息

					}
				}

//				String senderId = "123";
//				String receiverId = "456";
//				//需要回复消息时，修改returnContent为对应消息内容
//		        returnContent = generateReplyMsg(articleMsg(), "articles", senderId, receiverId); //回复article类型消息
//		        returnContent = generateReplyMsg(positionMsg(), "position", senderId, receiverId);//回复position类型的消息
			}		
		}else{
			//TODO 异常信息
			returnContent = "sign error!";
		}
		logger.info("returnContent message : " + returnContent);
		output(response, returnContent); //输出响应的内容。
		
	}

	/**
	 * 验证sha1签名，验证通过返回true，否则返回false
	 * @param signature
	 * @param nonce
	 * @param timestamp
	 * @return
	 */
	private boolean ValidateSHA(String signature, String nonce,
			String timestamp) {
		if (signature == null || nonce == null || timestamp == null) {
			return false;
		}
		String sign = sha1(getSignContent(nonce, timestamp, WeiboConfig.client_SERCRET));
		if (!signature.equals(sign)) {
			return false;
		}
		return true;
	}
	
	/**
	 * 生产sha1签名
	 * @param strSrc
	 * @return
	 */	
	private static String sha1(String strSrc) {
		MessageDigest md = null;
		String strDes = null;

		byte[] bt = strSrc.getBytes();
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.update(bt);
			strDes = bytes2Hex(md.digest()); // to HexString
		} catch (NoSuchAlgorithmException e) {
			//TODO
			e.printStackTrace();
		}
		return strDes;
	}
	
	private static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;

		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));

			if (tmp.length() == 1) {
				des += "0";
			}

			des += tmp;
		}

		return des;
	}
	
	
	/**
	 * 对非空参数按字典顺序升序构造签名串
	 * 
	 * @param params
	 * @return
	 */

	private static String getSignContent(String... params) {
		List<String> list = new ArrayList(params.length);
		for(String temp : params){
			if(StringUtils.isNotBlank(temp)){
				list.add(temp);
			}	
		}
		Collections.sort(list);
		StringBuilder strBuilder = new StringBuilder();
		for (String element : list) {
			strBuilder.append(element);
		}
		return strBuilder.toString();
	}
	
	/**
	 * 输出返回内容
	 * @param response
	 * @param msg
	 * @throws IOException
	 */
	private void output(HttpServletResponse response, String msg)
			throws IOException {
		if(msg != null){
			response.getOutputStream().write(msg.getBytes());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}
	
	/**
	 * 生成回复的消息。（发送被动响应消息）
	 * @param data  消息的内容。
	 * @param type  消息的类型
	 * @param senderId 回复消息的发送方uid。蓝v用户自己
	 * @param receiverId 回复消息的接收方  蓝v用户的粉丝uid
	 * @return
	 */
	private static String generateReplyMsg(String data, String type, String senderId, String receiverId){
		JSONObject jo = new JSONObject();
		jo.put("result",true);
		jo.put("sender_id", senderId);
		jo.put("receiver_id", receiverId);
		jo.put("type", type);
		try {
			jo.put("data", URLEncoder.encode(data, "utf-8")); //data字段的内容需要进行utf8的urlencode
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jo.toString();
	}
	
	/**
	 * 生成文本类型的消息data字段
	 * @return
	 */
	private static String  textMsg(){
		JSONObject jo = new JSONObject();
		jo.put("text", "你好呀");
		return jo.toString();
	}
	
	/**
	 * 生成文本类型的消息data字段
	 * @return
	 */
	private static String articleMsg(){
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		for(int i = 0; i< 1; i ++){
			JSONObject temp = new JSONObject();
			temp.put("display_name", "两个故事");
			temp.put("summary", "今天讲两个故事，分享给你。谁是公司？谁又是中国人？​");
			temp.put("image", "http://storage.mcp.weibo.cn/0JlIv.jpg");
			temp.put("url", "http://e.weibo.com/mediaprofile/article/detail?uid=1722052204&aid=983319");
			ja.add(temp);
		}
		jo.put("articles", ja);
		return jo.toString();
	}
	
	/**
	 * 生成文本类型的消息data字段
	 * @return
	 */
	private static String positionMsg(){
		JSONObject jo = new JSONObject();
		jo.put("longitude", "344.3344");
		jo.put("latitude", "232.343434");
		return jo.toString();
	}

}
