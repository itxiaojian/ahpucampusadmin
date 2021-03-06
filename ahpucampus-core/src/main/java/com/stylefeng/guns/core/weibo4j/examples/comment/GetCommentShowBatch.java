package com.stylefeng.guns.core.weibo4j.examples.comment;

import com.stylefeng.guns.core.weibo4j.Comments;
import com.stylefeng.guns.core.weibo4j.examples.oauth2.Log;
import com.stylefeng.guns.core.weibo4j.model.Comment;
import com.stylefeng.guns.core.weibo4j.model.WeiboException;

import java.util.List;

public class GetCommentShowBatch {

	public static void main(String[] args) {
		String access_token = args[0];
		String cids = args[1];
		Comments cm = new Comments(access_token);
		try {
			List<Comment> comment = cm.getCommentShowBatch(cids);
			for (Comment c : comment) {
				Log.logInfo(c.toString());
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
}
