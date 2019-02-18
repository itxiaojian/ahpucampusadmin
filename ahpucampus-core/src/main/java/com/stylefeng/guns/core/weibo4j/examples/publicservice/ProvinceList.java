package com.stylefeng.guns.core.weibo4j.examples.publicservice;

import com.stylefeng.guns.core.weibo4j.PublicService;
import com.stylefeng.guns.core.weibo4j.model.WeiboException;
import com.stylefeng.guns.core.weibo4j.org.json.JSONArray;

public class ProvinceList {

	public static void main(String[] args) {
		String access_token = args[0];
		String country = args[1];
		PublicService ps = new PublicService(access_token);
		try {
			JSONArray jo = ps.provinceList(country);
			System.out.println(jo.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
		
	}

}
