package com.stylefeng.guns.core.weibo4j.examples.place;

import com.stylefeng.guns.core.weibo4j.Place;
import com.stylefeng.guns.core.weibo4j.examples.oauth2.Log;
import com.stylefeng.guns.core.weibo4j.model.Places;
import com.stylefeng.guns.core.weibo4j.model.WeiboException;

import java.util.List;

public class GetPoisSearch {

	public static void main(String[] args) {
		String access_token = args[0];
		String keyword = args[1];
		Place p = new Place(access_token);
		try {
			List<Places> list = p.poisSearch(keyword);
			for (Places pl : list) {
				Log.logInfo(pl.toString());
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}

	}

}