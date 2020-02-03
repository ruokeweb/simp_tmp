package com.mpri.aio.schoolmate;

import org.apache.commons.text.StringEscapeUtils;

import com.mpri.aio.ApplicationTests;

public class StringEscapeTest{

	
	public static void main(String[] args) {
		String str = "{&quot;id&quot;:&quot;0f53c59c7fa24c9d8534411edc9f52c2&quot;,&quot;isNewRecord&quot;"
				+ ":false,&quot;createDate&quot;:null,&quot;remark&quot;:&quot;NORMAL&quot;,&quot;flag&quot;:&quot;NORMAL&quot;"
				+ ",&quot;queryBeginDate&quot;:null,&quot;queryEndDate&quot;:null,&quot;paramA&quot;:null,&quot;paramB&quot;"
				+ ":null,&quot;orgId&quot;:&quot;0&quot;,&quot;username&quot;:&quot;test13&quot;,&quot;password&quot;:null,"
				+ "&quot;safecode&quot;:null,&quot;name&quot;:&quot;&lt;script&gt;11&quot;,&quot;idcard&quot;:&quot;"
				+ "610525199203020202&quot;,&quot;photo&quot;"
				+ ":&quot;http://192.168.140.36:8080//upload/file/2018-09-19/1537342560735.png&quot;,&quot;mobile&quot;"
				+ ":&quot;18058326902&quot;,&quot;email&quot;:&quot;13@123.com&quot;,&quot;type&quot;:&quot;SUPER_ADMIN&quot;"
				+ ",&quot;orgName&quot;:null,&quot;roleList&quot;:[{&quot;id&quot;:&quot;1&quot;,&quot;isNewRecord&quot;"
				+ ":false,&quot;createDate&quot;:null,&quot;remark&quot;:null,&quot;flag&quot;:&quot;NORMAL&quot;"
				+ ",&quot;queryBeginDate&quot;:null,&quot;queryEndDate&quot;:null,&quot;paramA&quot;:null,"
				+ "&quot;paramB&quot;:null,&quot;name&quot;:null,&quot;code&quot;:null,&quot;type&quot;:null,"
				+ "&quot;menuList&quot;:[]}]}\r\n";
		
		String result = StringEscapeUtils.unescapeHtml4(str.trim());
		System.out.println(result);
	}
}
