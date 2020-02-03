package com.mpri.aio.schoolmate.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mpri.aio.schoolmate.mapper.InfoIntegrityMapper;
import com.mpri.aio.schoolmate.vo.StarInfoEnum;
import com.mpri.aio.settings.model.SmStar;
import com.mpri.aio.settings.model.SmStarSminfo;
import com.mpri.aio.settings.service.SmStarService;

import tk.mybatis.mapper.util.StringUtil;

/**
 * 获取信息完整度
 * <p>
 * Title: InfoIntegrityUtils
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @date 2018年9月13日
 */
@Component
public class InfoIntegrityUtils {

	/* 忽略的字段 */
	private static String IGNORESTR = "ADMININFO";

	@Autowired
	private SmStarService smStarService;

	/**
	 * 获取枚举的Code
	 * <p>
	 * Title: getStarInfos
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @return
	 */
	private static List<String> getStarInfos() {
		List<String> enums = new ArrayList<String>();
		for (StarInfoEnum s : StarInfoEnum.values()) {
			if (!s.getCode().equals(IGNORESTR))
				enums.add(s.getCode());
		}
		return enums;
	}

	/**
	 * 根据code 执行对应方法
	 * <p>
	 * Title: executor
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param mapper
	 * @param code
	 * @param sysUserId
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private static Object executor(InfoIntegrityMapper mapper, String code, String sysUserId) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		if (StringUtil.isNotEmpty(sysUserId)) {
			Class clazz = mapper.getClass();
			Method method = clazz.getMethod(code, String.class);
			return method.invoke(mapper, sysUserId);
		}
		return false;
	}

	/**
	 * 遍历枚举查询对应的完善度
	 * <p>
	 * Title: getInfoIntegrity
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param mapper
	 * @param code
	 * @param sysUserId
	 * @return
	 * @throws Exception
	 */
	public static List<String> getInfoIntegrity(InfoIntegrityMapper mapper, String sysUserId) throws Exception {
		List<String> codes = getStarInfos();
		List<String> status = new ArrayList<String>();

		for (String code : codes) {
			Boolean flag = (Boolean) executor(mapper, code, sysUserId);
			if (flag) {
				status.add(code);
			}
		}
		return status;
	}

	/**
	 * 设置信息完整度
	 * <p>
	 * Title: setSmComplete
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param status
	 */
	@SuppressWarnings("unlikely-arg-type")
	public int setSmComplete(List<String> status) {
		//获取星级缓存
		SmStar smStar = new SmStar();
		List<SmStar> smStars = smStarService.loadInfoListBy(smStar);
		if (status.size() < 1) {
			return 0;
		} else {
			//处理星级缓存
			for (SmStar star : smStars) { 
				List<String> starInfo = getStatus(star.getSmStarSminfos());
				if(status.containsAll(starInfo) || compareList(starInfo,status)) {
					return star.getInfoLevel();
				}
				
			}

		}
		return 0;
	}
	
	
	private static List<String> getStatus(List<SmStarSminfo> smStarSminfo){
		List<String> status = new ArrayList<String>();
		for (SmStarSminfo sm : smStarSminfo) {
			status.add(sm.getSmSminfoCode());
		}		
		return status;
	}
	
	/**
	 * 比较集合相等
	* <p>Title: compareList</p>  
	* <p>Description: </p>  
	* @param stars
	* @param status
	* @return
	 */
	private static Boolean compareList(List<String> stars, List<String> status) {
    		if (stars.size() != status.size())
    			return false;
    		for (String object : status) {
    			if (!stars.contains(object))
    				return false;
    		}
    		return true;
	}
	
}
