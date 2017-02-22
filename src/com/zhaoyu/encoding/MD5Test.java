package com.zhaoyu.encoding;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * author:大神
 */
public class MD5Test {
	public static String md5(String str) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		byte[] encodedValue = md5.digest();
		int j = encodedValue.length;
		char finalValue[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
			byte encoded = encodedValue[i];
			finalValue[k++] = hexDigits[encoded >> 4 & 0xf];
			finalValue[k++] = hexDigits[encoded & 0xf];
		}

		return new String(finalValue);
	}

	public static void main(String[] args) {
		try {
			System.out.println(getUrl("http://img.dmall.com/2/wx"));
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public static String getUrl(String url) throws UnsupportedEncodingException {
		final String wangsu_name = "dmall";
		final String wangsu_pass = "sd-9898w(*";
		String wangsu_url = "http://ccm.chinanetcenter.com/ccm/servlet/contReceiver?username=USERNAME&passwd=MD5&url=URL1";
		String md5 = md5(wangsu_name + wangsu_pass + url);
		String encodeUrl = java.net.URLEncoder.encode(url, "ISO-8859-1");
		String wangsu_url1 = wangsu_url.replace("USERNAME", wangsu_name).replace("MD5", md5).replace("URL1", encodeUrl);
		return wangsu_url1;
	}

}
