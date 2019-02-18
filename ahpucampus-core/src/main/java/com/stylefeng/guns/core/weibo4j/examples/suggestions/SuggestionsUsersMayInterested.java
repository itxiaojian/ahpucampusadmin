package com.stylefeng.guns.core.weibo4j.examples.suggestions;

import com.stylefeng.guns.core.weibo4j.Suggestion;
import com.stylefeng.guns.core.weibo4j.model.WeiboException;
import com.stylefeng.guns.core.weibo4j.org.json.JSONArray;

public class SuggestionsUsersMayInterested {

	public static void main(String[] args) {
		String access_token = args[0];
		Suggestion suggestion = new Suggestion(access_token);
		try {
			JSONArray jo = suggestion.suggestionsUsersMayInterested();
			System.out.println(jo.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}

	}

}
