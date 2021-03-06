package com.stylefeng.guns.core.weibo4j.examples.shorturl;

import com.stylefeng.guns.core.weibo4j.ShortUrl;
import com.stylefeng.guns.core.weibo4j.model.WeiboException;
import com.stylefeng.guns.core.weibo4j.org.json.JSONObject;

public class LongToShortUrl {

	public static void main(String[] args) {
		String access_token = args[0];
		String url = args[1];
		ShortUrl su = new ShortUrl(access_token);
		try {
			JSONObject jo = su.longToShortUrl(url);
			System.out.println(jo.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}

