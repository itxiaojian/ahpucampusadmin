package com.stylefeng.guns.core.weibo4j;

import com.stylefeng.guns.core.weibo4j.model.PostParameter;
import com.stylefeng.guns.core.weibo4j.model.WeiboException;
import com.stylefeng.guns.core.weibo4j.org.json.JSONObject;
import com.stylefeng.guns.core.weibo4j.util.WeiboConfig;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Share extends Weibo {

	private static final long serialVersionUID = -6809545704064413209L;

	public Share(String access_token) {
		this.access_token = access_token;
	}

	/**
	 * 验证昵称是否可用，并给予建议昵称
	 * 
	 * @param status
	 *            需要验证的昵称。4-20个字符，支持中英文、数字、"_"或减号。必须做URLEncode，采用UTF-8编码
	 * @return
	 * @throws WeiboException
	 *             when Weibo service or network is unavailable
	 * @version weibo4j-V2 1.0.2
	 * @see open.weibo.com/wiki/2/register/verify_nickname
	 * @since JDK 1.5
	 */
	public JSONObject Share(String status,String pic,String rip) throws WeiboException, UnsupportedEncodingException {
		return client
				.post(WeiboConfig.baseURL	+ "statuses/share.json",
						new PostParameter[] {
								new PostParameter("status", status),
//								new PostParameter("pic", pic),
								new PostParameter("rip", "132.232.224.83"),
						}, access_token).asJSONObject();
	}
}
