package com.stylefeng.guns.core.weibo4j.examples.account;

import com.stylefeng.guns.core.weibo4j.Account;
import com.stylefeng.guns.core.weibo4j.examples.oauth2.Log;
import com.stylefeng.guns.core.weibo4j.model.User;
import com.stylefeng.guns.core.weibo4j.model.WeiboException;

public class EndSession {

	public static void main(String[] args) {
		String access_token = args[0];
		Account am = new Account(access_token);
		try {
			User user = am.endSession();
			Log.logInfo(user.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}

	}

}
