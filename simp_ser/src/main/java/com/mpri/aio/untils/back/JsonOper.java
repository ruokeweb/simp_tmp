package com.mpri.aio.untils.back;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Component
public class JsonOper {

	@Value("${json.oper.filename}")
	private String jsonFile;

	@Value("${back.backpath}")
	private String backPath;
	
	@Value("${back.maxsize}")
	private int maxSize;
	
	@Autowired
	private MysqlOper mysqlOper;

	@Autowired
	private MongoOper mongoOper;

	/**
	 * 写入json数据
	 * 
	 * @param backUp
	 * @throws Exception
	 */
	public int saveBack(String type) throws Exception {
		// 存储对象构建
		String id = String.valueOf(System.currentTimeMillis());
		SimpleDateFormat date = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		BackUp back = new BackUp();
		back.setId(id);
		back.setName("BACK_" + id);
		back.setDate(date.format(new Date()));
		back.setType(type);
		back.setPath(backPath + id);
		
		// 存储列表构建
		List<BackUp> list = loadBack();

		if (list == null) {
			list = new ArrayList<BackUp>();
		}
		
		//限制数据条数为15
		if(list.size()>=maxSize) {
			list = removeEldest(list);
		}
		
		
		//构建备份文件夹
		File dir=new File(backPath+id);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		// Mysql 存储
		int my = mysqlOper.dbBackup(id);
		// mongo 存储
//		int mo = mongoOper.dbBackup(id);

		int isBack=my ;
		
		if(isBack==0) {
			list.add(0, back);
		}
		
		// 写入
		writeJson(list);

		return isBack;
	}

	/**
	 * 删除备份
	 * 
	 * @param id
	 * @throws IOException
	 */
	public List<BackUp> delBack(String id) throws IOException {

		// 删除备份数据
		List<BackUp> list = loadBack();
		BackUp delBack = new BackUp();
		boolean isDel = false;
		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);
			BackUp bu = (BackUp) JSON.parseObject(obj.toString(), BackUp.class);

			// 移除json数据
			if (bu.getId().equals(id)) {
				delBack = bu;
				list.remove(i);

				//File file = new File(backPath + "\\" + id);
				// 删除实际备份文件
				try {
					delBackFile(backPath + "\\" + id);
				} catch (Exception e) {
					//// ex.printStackTrace();;
				}
				isDel = true;
				break;
			}
		}

		writeJson(list);
		return list;
	}

	/**
	 * 获取备份对象
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public BackUp getBack(String id) throws IOException {
		JsonOper jp = new JsonOper();

		// 获取备份数据
		List<BackUp> list = jp.loadBack();
		BackUp back = new BackUp();
		for (int i = 0; i < list.size(); i++) {

			Object obj = list.get(i);
			BackUp bu = (BackUp) JSON.parseObject(obj.toString(), BackUp.class);
			// 获取json数据
			if (bu.getId().equals(id)) {
				back = bu;
				break;
			}
		}
		return back;
	}

	/**
	 * 读取json列表
	 * 
	 * @return
	 * @throws IOException
	 */
	public List<BackUp> loadBack() throws IOException {
		// ClassPathResource resource = new ClassPathResource(jsonFile);
		File file = new File(jsonFile);
		String json = FileUtils.readFileToString(file, "UTF-8");
		List<BackUp> backList = null;
		if (json != null && json != "") {
			backList = (List<BackUp>) JSONObject.parseObject(json, List.class);
		}

		// List<BackUp> bkList = JSON.parseArray(JSON.parseObject(json).getString(""),
		// BackUp.class);
		return backList;
	}

	/**
	 * json文件写入
	 * 
	 * @param data
	 * @throws IOException
	 */
	public void writeJson(List<BackUp> list) throws IOException {
		String data = JSONObject.toJSONString(list);

		// ClassPathResource resource = new ClassPathResource(jsonFile);
		File file = new File(jsonFile);
		BufferedWriter writer = null;
		FileOutputStream fileOutputStream = null;
		// 写入
		try {
			fileOutputStream = new FileOutputStream(file, false);
			writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "UTF-8"));

			writer.write(data);
		} catch (IOException e) {
			 e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
				if(fileOutputStream !=null){
					fileOutputStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 删除物理文件
	 * 
	 * @param id
	 */
	public Boolean delBackFile(String path) {
		File f=new File(path); 
		if(f.isDirectory()){//如果是目录，先递归删除 
			String[] list=f.list(); 
			for(int i=0;i<list.length;i++){ 
				delBackFile(path+"//"+list[i]);//先删除目录下的文件 
			} 
		}
		return f.delete(); 
	}
	
	/**
	 * 删除最老的备份
	 * @param list
	 */
	public List<BackUp> removeEldest(List<BackUp> list){
		
		Object obj = list.get(list.size()-1);
		BackUp bu = (BackUp) JSON.parseObject(obj.toString(), BackUp.class);
		try {
			list=delBack(bu.getId());
		} catch (IOException e) {
			//// ex.printStackTrace();;
		}
		
		return list;
	}
	
}
