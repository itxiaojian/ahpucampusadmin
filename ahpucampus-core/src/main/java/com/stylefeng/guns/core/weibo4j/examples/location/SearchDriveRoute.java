package com.stylefeng.guns.core.weibo4j.examples.location;

import com.stylefeng.guns.core.weibo4j.Location;
import com.stylefeng.guns.core.weibo4j.examples.oauth2.Log;
import com.stylefeng.guns.core.weibo4j.model.WeiboException;
import com.stylefeng.guns.core.weibo4j.org.json.JSONObject;

public class SearchDriveRoute {

	public static void main(String[] args) {
		String access_token = args[0];
		String beginCoordinate = args[1];
		String endCoordinate = args[2];
		Location l = new Location(access_token);
		try {
			JSONObject json = l.searchDriveRouteByCoordinate(beginCoordinate,
					endCoordinate);
			Log.logInfo(json.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
