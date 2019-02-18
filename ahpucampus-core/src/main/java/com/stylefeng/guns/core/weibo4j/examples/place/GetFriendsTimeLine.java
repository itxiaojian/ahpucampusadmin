package com.stylefeng.guns.core.weibo4j.examples.place;

import com.stylefeng.guns.core.weibo4j.Place;
import com.stylefeng.guns.core.weibo4j.examples.oauth2.Log;
import com.stylefeng.guns.core.weibo4j.model.StatusWapper;
import com.stylefeng.guns.core.weibo4j.model.WeiboException;

public class GetFriendsTimeLine {
	public static void main(String[] args) {
		String access_token = args[0];
		Place p = new Place(access_token);
		try {
			StatusWapper sw = p.friendsTimeLine();
			Log.logInfo(sw.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
}
