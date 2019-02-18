package com.stylefeng.guns.core.weibo4j.examples.user;

import com.stylefeng.guns.core.weibo4j.Users;
import com.stylefeng.guns.core.weibo4j.examples.oauth2.Log;
import com.stylefeng.guns.core.weibo4j.model.User;
import com.stylefeng.guns.core.weibo4j.model.WeiboException;

public class ShowUserByDomain {

	public static void main(String[] args) {
		String access_token = args[0];
		String domain = args[1];
		Users um = new Users(access_token);
		try {
			User user = um.showUserByDomain(domain);
			Log.logInfo(user.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
