package com.stylefeng.guns.core.wechat.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.stylefeng.guns.core.wechat.model.AccessToken;
import com.stylefeng.guns.core.wechat.model.WechatConfig;
import com.stylefeng.guns.core.wechat.model.WechatOauth2Token;
import com.stylefeng.guns.core.wechat.model.WechatTemplate;
import com.stylefeng.guns.core.wechat.security.MyX509TrustManager;
import com.stylefeng.guns.core.wechat.security.SpringApplicationContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.xml.ws.http.HTTPException;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;

public class WechatUtils {
	private static Logger logger = LoggerFactory.getLogger(WechatUtils.class);

    // 获取access_token的接口地址（GET） 限200（次/天）
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    //获取openid
    public static String authorize_url = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

    //发送消息模板
	public static String send_uniformmessage_url ="https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN";


	//private static RedisService redisService = SpringApplicationContextHolder.getBean(RedisService.class);

	private static String WECHAT_TOKEN_HASH_KEY = WechatConfig.AppID+"#token";
	private static String WECHAT_TOKEN_KEY = "token";
	private static String WECHAT_CREATETIME_KEY = "createTime";

    /**
	 * 发起https请求并获取结果 
	 * 
	 * @param requestUrl   请求地址
	 * @param requestMethod   请求方式（GET、POST） 
	 * @param outputStr     提交的数据
	 * @return      JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		        JSONObject jsonObject = null;
		        StringBuffer buffer = new StringBuffer();  
		        try {  
		            // 创建SSLContext对象，并使用我们指定的信任管理器初始化  
		            TrustManager[] tm = { new MyX509TrustManager() };
		            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");  
		            sslContext.init(null, tm, new java.security.SecureRandom());  
		            // 从上述SSLContext对象中得到SSLSocketFactory对象  
		            SSLSocketFactory ssf = sslContext.getSocketFactory();  
		  
		            URL url = new URL(requestUrl);
		            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();  
		            httpUrlConn.setSSLSocketFactory(ssf);

		            httpUrlConn.setDoOutput(true);
		            httpUrlConn.setDoInput(true);
		            httpUrlConn.setUseCaches(false);
		            // 设置请求方式（GET/POST）  
		            httpUrlConn.setRequestMethod(requestMethod);  
		  
		            if ("GET".equalsIgnoreCase(requestMethod))
		                httpUrlConn.connect();  
		  
		            // 当有数据需要提交时  
		            if (StringUtils.hasText(outputStr)) {
		                OutputStream outputStream = httpUrlConn.getOutputStream();  
		                // 注意编码格式，防止中文乱码  
		                outputStream.write(outputStr.getBytes("UTF-8"));  
		                outputStream.close();  
		            }  
		  
		            // 将返回的输入流转换成字符串  
		            InputStream inputStream = httpUrlConn.getInputStream();  
		            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
		            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
		  
		            String str = null;  
		            while ((str = bufferedReader.readLine()) != null) {  
		                buffer.append(str);  
		            }  
		            bufferedReader.close();  
		            inputStreamReader.close();  
		            // 释放资源  
		            inputStream.close();  
		            inputStream = null;  
		            httpUrlConn.disconnect();  
		            jsonObject = (JSONObject) JSON.parse(buffer.toString());
		        } catch (ConnectException ce) {  
		        	logger.error("Weixin server connection timed out{}",ce);
					jsonObject.put("errmsg","Weixin server connection timed out");
					jsonObject.put("errcode","500");
		        } catch (Exception e) {
		        	logger.error("https request error:{}", e);
					jsonObject.put("errmsg","Weixin server error");
					jsonObject.put("errcode","500");
		        }  
		        return jsonObject;  
		    }  




	/**
	 * 获取WechatOauth2Token对象
	 * @author:sliu
	 * @version 创建时间：2015年6月11日 上午10:28:29
	 * @param
	 * @param
	 * @param code
	 * @return
	 */
	public static WechatOauth2Token getOauth2AccessToken(String code) {
		WechatOauth2Token wat = new WechatOauth2Token();
		String requestUrl = authorize_url.replace("APPID", WechatConfig.AppID)
				                         .replace("SECRET", WechatConfig.AppSecret)
				                         .replace("JSCODE", code);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			try {
				wat = new WechatOauth2Token();
				wat.setOpenId(jsonObject.getString("openid"));
			} catch (Exception e) {
				wat = null;
				String errorCode = jsonObject.getString("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				logger.error("获取网页授权凭证失败 errcode{},errMsg", errorCode, errorMsg);
			}
		}
		return wat;
	}

    /**
     * 发送模板消息
	 *
     */
    public static boolean sendTemplateMsg(WechatTemplate temp) {
        boolean flag = false;
        String token ="";

		AccessToken accessToken = getAccessToken();
		if(accessToken!=null){
			token = accessToken.getToken();
		}

        if(!StringUtils.isEmpty(token)){
			String requestUrl = send_uniformmessage_url.replace("ACCESS_TOKEN", token);
			JSONObject result = httpRequest(requestUrl, "POST", JSON.toJSONString(temp));
			logger.info("模板消息发送,入参==={}，返回==={}", JSON.toJSONString(temp),result);
			if (result != null) {
				int errorCode = result.getInteger("errcode");
				String errorMessage = result.getString("errmsg");
				if (errorCode == 0) {
					flag = true;
				} else {
					logger.info("模板消息发送失败:" + errorCode + "," + errorMessage);
					flag = false;
				}
			}
		}

        return flag;
    }


    /**
     * 获取access_token
     *
     * @return
     */
    public static AccessToken getAccessToken() {
        AccessToken accessToken = null;
		//String token = (String)redisService.getHash(WECHAT_TOKEN_HASH_KEY,WECHAT_TOKEN_KEY);
		String token = "";
		if(StringUtils.isEmpty(token)){
			String requestUrl = access_token_url.replace("APPID", WechatConfig.AppID)
					                            .replace("APPSECRET", WechatConfig.AppSecret);
			JSONObject jsonObject = httpRequest(requestUrl, "GET", null);

			if (null != jsonObject) {
				try {
					accessToken = new AccessToken();
					accessToken.setToken(jsonObject.getString("access_token"));
					accessToken.setExpiresIn(jsonObject.getInteger("expires_in"));

					/*redisService.setHash(WECHAT_TOKEN_HASH_KEY,WECHAT_TOKEN_KEY,accessToken.getToken());
					redisService.setHash(WECHAT_TOKEN_HASH_KEY,WECHAT_CREATETIME_KEY,DateUtil.getDateTime());
					boolean issuccess =redisService.expire(WECHAT_TOKEN_HASH_KEY,accessToken.getExpiresIn());
					if(issuccess){
						logger.info(WechatConfig.AppID+"#token设置时效success:{}",accessToken.getExpiresIn());
					}*/
				} catch (JSONException e) {
					accessToken = null;
					// 获取token失败
					logger.error("获取token失败 errcode:{} errmsg:{}"+ jsonObject.getInteger("errcode")+"-"
							+ jsonObject.getString("errmsg"));
				}
			}
		}else{
			accessToken = new AccessToken();
			accessToken.setToken(token);
		}

        return accessToken;
    }

	/**
	 * 发送短信验证码
	 */
	public static void sendSMS(){

		try {
			String[] params = {"520","5","你好我们测试一下"};//数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
			SmsSingleSender ssender = new SmsSingleSender(WechatConfig.smsAppID, WechatConfig.smsAppKey);
			SmsSingleSenderResult result = ssender.sendWithParam("86", "15956549931",
					221754, params, "灰尘大小饭桌", "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
			System.out.println(result);
		} catch (HTTPException e) {
			// HTTP响应码错误
			e.printStackTrace();
		} catch (JSONException e) {
			// json解析错误
			e.printStackTrace();
		} catch (IOException e) {
			// 网络IO错误
			e.printStackTrace();
		} catch (com.github.qcloudsms.httpclient.HTTPException e) {
			e.printStackTrace();
		} catch (org.json.JSONException e) {
			e.printStackTrace();
		}

	}



}
