package com.stylefeng.guns.core.weibo4j.examples.place;

import com.stylefeng.guns.core.weibo4j.Place;
import com.stylefeng.guns.core.weibo4j.examples.oauth2.Log;
import com.stylefeng.guns.core.weibo4j.model.PoisitionCategory;
import com.stylefeng.guns.core.weibo4j.model.WeiboException;

import java.util.List;

public class GetPoisCategory {

	public static void main(String[] args) {
		String access_token = args[0];
		Place p = new Place(access_token);
		try {
			List<PoisitionCategory> list = p.poisCategory();
			for (PoisitionCategory pois : list) {
				Log.logInfo(pois.toString());
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
