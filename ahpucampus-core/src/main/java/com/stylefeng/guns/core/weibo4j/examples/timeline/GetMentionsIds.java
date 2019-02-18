package com.stylefeng.guns.core.weibo4j.examples.timeline;

import com.stylefeng.guns.core.weibo4j.Timeline;
import com.stylefeng.guns.core.weibo4j.examples.oauth2.Log;
import com.stylefeng.guns.core.weibo4j.model.MentionsIds;
import com.stylefeng.guns.core.weibo4j.model.WeiboException;

public class GetMentionsIds {
	public static void main(String[] args) {
		String access_token = args[0];
		Timeline tm = new Timeline(access_token);
		try {
			MentionsIds ids = tm.getMentionsIds();
			Log.logInfo(ids.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
}
