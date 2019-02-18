package com.stylefeng.guns.core.weibo4j.examples.location;

import com.stylefeng.guns.core.weibo4j.Location;
import com.stylefeng.guns.core.weibo4j.examples.oauth2.Log;
import com.stylefeng.guns.core.weibo4j.model.Geos;
import com.stylefeng.guns.core.weibo4j.model.WeiboException;

import java.util.List;

public class IpToGeo {

	public static void main(String[] args) {
		String access_token = args[0];
		String ip = args[1];
		Location l = new Location(access_token);
		try {
			List<Geos> list = l.ipToGeo(ip);
			for (Geos g : list) {
				Log.logInfo(g.toString());
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}

}
