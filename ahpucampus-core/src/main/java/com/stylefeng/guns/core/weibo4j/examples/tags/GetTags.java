package com.stylefeng.guns.core.weibo4j.examples.tags;

import com.stylefeng.guns.core.weibo4j.Tags;
import com.stylefeng.guns.core.weibo4j.examples.oauth2.Log;
import com.stylefeng.guns.core.weibo4j.model.Tag;
import com.stylefeng.guns.core.weibo4j.model.WeiboException;

import java.util.List;

public class GetTags {

	public static void main(String[] args) {
		String access_token = args[0];
		String uid = args[1];
		Tags tm  = new Tags(access_token);
		try {
			List<Tag> tags = tm.getTags(uid);
			for(Tag tag:tags ){				
				Log.logInfo(tag.toString());
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
