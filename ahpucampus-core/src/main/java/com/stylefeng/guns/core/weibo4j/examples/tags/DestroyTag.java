package com.stylefeng.guns.core.weibo4j.examples.tags;

import com.stylefeng.guns.core.weibo4j.Tags;
import com.stylefeng.guns.core.weibo4j.examples.oauth2.Log;
import com.stylefeng.guns.core.weibo4j.model.WeiboException;
import com.stylefeng.guns.core.weibo4j.org.json.JSONObject;

public class DestroyTag {

	public static void main(String[] args) {
		String access_token = args[0];
		int tag_id = Integer.parseInt(args[1]);
		Tags tm = new Tags(access_token);
		try {
			JSONObject result = tm.destoryTag(tag_id);
			Log.logInfo(String.valueOf(result));
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
