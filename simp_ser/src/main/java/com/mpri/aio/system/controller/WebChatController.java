package com.mpri.aio.system.controller;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 微信相关请求
 * 
 * @author Cary
 * @date 2018年9月14日
 */
@Controller
public class WebChatController {

	// 获取域名
//	@Value("${qrcodeContent}")
	private String qrcodeContent;

	@CrossOrigin
	@RequestMapping(value = "/qrcode")
	public void qrcode(HttpServletResponse response) {

		String content = "http://www.163.com";
		ServletOutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();
			// QRCode.Stream_QRCode(content,
			// "http://life.southmoney.com/tuwen/UploadFiles_6871/201805/20180510090638710.jpg",
			// outputStream);
		} catch (Exception e) {
			//// ex.printStackTrace();;
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					//// ex.printStackTrace();;
				}
			}
		}
	}

	/**
	 * 服务端二维码生成
	 * <p>
	 * Title: managerServerQrcode
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param response
	 */
	@CrossOrigin
	@RequestMapping(value = "/managerServerQrcode")
	public void managerServerQrcode(HttpServletResponse response) {

		ServletOutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();
			// QRCode.Stream_QRCode(qrcodeContent, "static/face.png" ,outputStream);
		} catch (Exception e) {
			//// ex.printStackTrace();;
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					//// ex.printStackTrace();;
				}
			}
		}
	}
}
