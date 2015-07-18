package com.fqy.fqylibs.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * 网络请求
 * 
 * @Title: HttpUtils.java
 * @Package com.fqy.fqylibs.utils
 * @Description: TODO 欢迎页
 * @author: Fang Qingyou
 * @date 2015年6月26日下午2:30:56
 * @version V1.0
 */
public class UtilsHttp {

	/**
	 * 用 HttpURLConnection发送 get请求
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年6月26日下午3:28:12
	 * @param url
	 * @return
	 */
	public static String sondHttpConnGet(String url) {
		HttpURLConnection conn = null;
		String data = "";
		try {
			URL httpUrl = new URL(url);
			conn = (HttpURLConnection) httpUrl.openConnection();
			conn.setReadTimeout(3 * 1000);
			conn.setConnectTimeout(3 * 1000);
			conn.setRequestMethod("GET");

			InputStream inputStream = conn.getInputStream();

			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");

			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			String flag = "";
			while ((flag = bufferedReader.readLine()) != null) {
				data += flag;
			}

			// 用完记得关闭
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return "error";
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}
		return data;
	}

	public static String sondHttpConnPost(String url,
			HashMap<String, String> param) {
		HttpURLConnection conn = null;
		String data = null;

		try {
			URL httpUrl = new URL(url);

			conn = (HttpURLConnection) httpUrl.openConnection();
			conn.connect();
			// 设置是否向connection输出，因为这个是post请求，参数要放在
			// http正文内，因此需要设为true
			conn.setDoOutput(true);
			// Read from the connection. Default is true.
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			// Post 请求不能使用缓存
			conn.setUseCaches(false);

			// 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
			// 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
			// 进行编码
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			// 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
			// 要注意的是connection.getOutputStream会隐含的进行connect。
			conn.connect();

			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			// The URL-encoded contend
			// 正文，正文内容其实跟get的URL中 '? '后的参数字符串一致
			String content = null;
			boolean isFist = true;
			for (Entry<String, String> entrySet : param.entrySet()) {
				if (isFist) {
					content += entrySet.getKey() + "="
							+ URLEncoder.encode(entrySet.getValue(), "UTF-8");
					isFist = false;
				} else {
					content += "&" + entrySet.getKey() + "="
							+ URLEncoder.encode(entrySet.getValue(), "UTF-8");

				}
			}
			// DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写到流里面
			out.writeBytes(content);

			out.close();

			InputStream inputStream = conn.getInputStream();

			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");

			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			String flag = null;
			while ((flag = bufferedReader.readLine()) != null) {
				data += flag;
			}

			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
			return "error";
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}
		return data;
	}

	public static String sondHttpClientGet(String url) {

		BufferedReader in = null;

		String content = null;
		try {
			// 定义HttpClient
			HttpClient client = new DefaultHttpClient();
			// 实例化HTTP方法
			HttpGet request = new HttpGet();
			request.setURI(new URI(url));
			HttpResponse response = client.execute(request);

			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			content = sb.toString();
		} finally {
			if (in != null) {
				try {
					in.close();// 最后要关闭BufferedReader
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return content;
		}
	}

}
