package com.stylefeng.guns.core.weibo4j.examples.friendships;

import com.stylefeng.guns.core.weibo4j.Friendships;
import com.stylefeng.guns.core.weibo4j.model.User;
import com.stylefeng.guns.core.weibo4j.model.UserWapper;
import com.stylefeng.guns.core.weibo4j.model.WeiboException;

public class GetFriendsByName {

	public static void main(String[] args) {
		String access_token = args[0];
		String name = args[1];
		Friendships fm = new Friendships(access_token);
		try {
			UserWapper users = fm.getFriendsByScreenName(name);
			for(User u : users.getUsers()){
				System.out.println(u.toString());
			}
			System.out.println(users.getNextCursor());
			System.out.println(users.getPreviousCursor());
			System.out.println(users.getTotalNumber());
		} catch (WeiboException e) {
			e.printStackTrace();
		}

	}

}
