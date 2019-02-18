package com.stylefeng.guns.core.weibo4j.examples.trends;

import com.stylefeng.guns.core.weibo4j.Trend;
import com.stylefeng.guns.core.weibo4j.examples.oauth2.Log;
import com.stylefeng.guns.core.weibo4j.model.UserTrend;
import com.stylefeng.guns.core.weibo4j.model.WeiboException;

import java.util.List;

public class GetTrends {

	public static void main(String[] args) {
		String access_token = args[0];
		String uid = args[1];
		Trend tm = new Trend(access_token);
		try {
			List<UserTrend> trends = tm.getTrends(uid);
			for(UserTrend t : trends){
				Log.logInfo(t.toString());
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
