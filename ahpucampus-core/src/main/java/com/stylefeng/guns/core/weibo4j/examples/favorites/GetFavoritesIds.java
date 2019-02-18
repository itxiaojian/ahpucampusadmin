package com.stylefeng.guns.core.weibo4j.examples.favorites;

import com.stylefeng.guns.core.weibo4j.Favorite;
import com.stylefeng.guns.core.weibo4j.examples.oauth2.Log;
import com.stylefeng.guns.core.weibo4j.model.FavoritesIds;
import com.stylefeng.guns.core.weibo4j.model.WeiboException;

import java.util.List;

public class GetFavoritesIds {

	public static void main(String[] args) {
		String access_token = args[0];
		Favorite fm = new Favorite(access_token);
		try {
			List<FavoritesIds> ids = fm.getFavoritesIds();
			for(FavoritesIds s : ids){
				Log.logInfo(s.toString());
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}

	}

}
