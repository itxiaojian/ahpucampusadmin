package com.stylefeng.guns.core.weibo4j.examples.trends;

import com.stylefeng.guns.core.weibo4j.Trend;
import com.stylefeng.guns.core.weibo4j.examples.oauth2.Log;
import com.stylefeng.guns.core.weibo4j.model.Trends;
import com.stylefeng.guns.core.weibo4j.model.WeiboException;

import java.util.List;

public class GetTrendsWeekly {

	public static void main(String[] args) {
		String access_token = args[0];
		Trend tm = new Trend(access_token);
		try {
			List<Trends> trends = tm.getTrendsWeekly();
			for(Trends ts : trends){
				Log.logInfo(ts.toString());
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
