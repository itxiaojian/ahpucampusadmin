package com.stylefeng.guns.core.weibo4j.examples.suggestions;

import com.stylefeng.guns.core.weibo4j.Suggestion;
import com.stylefeng.guns.core.weibo4j.model.User;
import com.stylefeng.guns.core.weibo4j.model.WeiboException;

public class SuggestionsUsersNot_interested {

	public static void main(String[] args) {
		String access_token = args[0];
		String uid = args[1];
		Suggestion suggestion = new Suggestion(access_token);
		try {
			User user = suggestion.suggestionsUsersNotInterested(uid);
			System.out.println(user.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}

	}

}
