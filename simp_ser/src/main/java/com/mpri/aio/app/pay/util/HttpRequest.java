package com.mpri.aio.app.pay.util;

import com.alibaba.fastjson.JSONObject;
import com.mpri.aio.common.utils.DateUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

/**
 * desc
 * 
 * @author lzq
 * @date 2018年9月5日 - 下午3:35:21
 */
public class HttpRequest {

	/**
	 * 向指定URL发送GET方法的请求
	 *
	 * @param url   发送请求的URL
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			//// ex.printStackTrace();;
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (null != in) {
					in.close();
				}
			} catch (Exception e2) {
				// e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 *
	 * @param url   发送请求的 URL
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, JSONObject param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type", "application/json");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();

			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}

		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			//// ex.printStackTrace();;
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (null != out) {
					out.close();
				}
				if (null != in) {
					in.close();
				}
			} catch (IOException ex) {
				// ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 *
	 * @param url   发送请求的 URL
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static void sendPost1(String url, JSONObject param, HttpServletResponse response) {
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type", "application/json");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();

			ServletOutputStream outputStream = response.getOutputStream();

			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

//            int ch;
//                  while ((ch = in.read()) != -1) {   
//                      outputStream.write(ch);   
//                 }
			BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
			BufferedImage image = ImageIO.read(bis);

			ImageIO.write(image, "jpeg", outputStream);

		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			//// ex.printStackTrace();;
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (null != out) {
					out.close();
				}
				if (null != in) {
					in.close();
				}
			} catch (IOException ex) {
				// ex.printStackTrace();
			}
		}
//        return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 *
	 * @param url   发送请求的 URL
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String getCordUrl(String url, String weixinFolder, String staticAccessPath, JSONObject param,
			HttpServletResponse response) {
		PrintWriter out = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type", "application/json");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();

			String newFilName = String.valueOf(new Date().getTime()) + ".png"; /* 更改文件名 */
			String resfillPath = "/weixin/" + DateUtils.getDate();
			result = weixinFolder + resfillPath + "/" + newFilName;

			File file = new File(result);
			result = staticAccessPath.replaceAll("\\*", "") + resfillPath + "/" + newFilName;
			BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
			BufferedImage image = ImageIO.read(bis);

			if (!file.exists()) {
				file.mkdirs();
			}

			ImageIO.write(image, "jpeg", file);

		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			//// ex.printStackTrace();;
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			if (null != out) {
				out.close();
			}
		}
		return result;
	}
}
