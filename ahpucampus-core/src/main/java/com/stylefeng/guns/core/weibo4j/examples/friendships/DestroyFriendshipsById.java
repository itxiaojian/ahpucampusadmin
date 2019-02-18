package com.stylefeng.guns.core.weibo4j.examples.friendships;

import com.stylefeng.guns.core.weibo4j.Friendships;
import com.stylefeng.guns.core.weibo4j.examples.oauth2.Log;
import com.stylefeng.guns.core.weibo4j.model.User;
import com.stylefeng.guns.core.weibo4j.model.WeiboException;

public class DestroyFriendshipsById {

	public static void main(String[] args) {
		String access_token = args[0];
		String uid = args[1];
		Friendships fm = new Friendships(access_token);
		try {
			User user = fm.destroyFriendshipsById(uid);
			Log.logInfo(user.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
