package com.stylefeng.guns.core.weibo4j.examples.timeline;

import com.stylefeng.guns.core.weibo4j.Timeline;
import com.stylefeng.guns.core.weibo4j.examples.oauth2.Log;
import com.stylefeng.guns.core.weibo4j.model.StatusWapper;
import com.stylefeng.guns.core.weibo4j.model.WeiboException;

public class GetUserTimeline {

	public static void main(String[] args) {
		String access_token = "2.001Z_KND0FPbsu1a822dfacc0XtfsZ";
		Timeline tm = new Timeline(access_token);
		try {
			StatusWapper status = tm.getUserTimeline();
			Log.logInfo(status.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
