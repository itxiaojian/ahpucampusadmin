package com.stylefeng.guns.core.weibo4j.examples.remind;

import com.stylefeng.guns.core.weibo4j.Reminds;
import com.stylefeng.guns.core.weibo4j.model.WeiboException;
import com.stylefeng.guns.core.weibo4j.org.json.JSONObject;

public class Remind {

	public static void main(String[] args) {
		String access_token = args[0];
		Reminds rm = new Reminds(access_token);
		try {
			JSONObject jo = rm.getUnreadCountOfRemind();
			System.out.println(jo.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
		
	}

}
