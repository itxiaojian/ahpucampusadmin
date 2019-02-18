package com.stylefeng.guns.core.weibo4j.examples.location;

import com.stylefeng.guns.core.weibo4j.Location;
import com.stylefeng.guns.core.weibo4j.examples.oauth2.Log;
import com.stylefeng.guns.core.weibo4j.model.Poisition;
import com.stylefeng.guns.core.weibo4j.model.WeiboException;

import java.util.List;

public class SearchPoisByArea {

	public static void main(String[] args) {
		String access_token = args[0];
		String q = args[1];
		String coordinates = args[2];
		Location l = new Location(access_token);
		try {
			List<Poisition> list = l.searchPoisByAreaByQ(q, coordinates);
			for (Poisition p : list) {
				Log.logInfo(p.toString());
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
