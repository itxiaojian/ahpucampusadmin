package com.stylefeng.guns.core.weibo4j.examples.comment;

import com.stylefeng.guns.core.weibo4j.Comments;
import com.stylefeng.guns.core.weibo4j.examples.oauth2.Log;
import com.stylefeng.guns.core.weibo4j.model.CommentWapper;
import com.stylefeng.guns.core.weibo4j.model.WeiboException;

public class GetCommentToMe {

	public static void main(String[] args) {
		String access_token = args[0];
		Comments cm = new Comments(access_token);
		try {
			CommentWapper comment = cm.getCommentToMe();
			Log.logInfo(comment.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
