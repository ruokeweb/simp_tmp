package com.mpri.aio.app.reg.utils;

import java.util.ArrayList;
import java.util.List;

import com.mpri.aio.app.reg.vo.MultipleLinkageVo;

/**
 * @Desc 数据转换工具类
 * @author syp
 *
 */
public class CovertDataUtils {

	/**
	 * 转换成formSelect 所需数据(籍贯)
	* <p>Title: setRootFormSelectData</p>  
	* <p>Description: </p>  
	* @param formSelectDatas
	* @param root
	* @return
	 */
	public static MultipleLinkageVo setMultipleLinkageData(List<MultipleLinkageVo> multipleLinkageVos,String root) {
		MultipleLinkageVo multipleLinkageVo = new MultipleLinkageVo();
		multipleLinkageVo.setId(root);
		for(MultipleLinkageVo ml : multipleLinkageVos) {
			if(ml.getParentId().equalsIgnoreCase(root)) {
				multipleLinkageVo.setChilds(covertMultipleLinkageDatas(multipleLinkageVos,ml.getId()));
				break;
			}
		}
		return multipleLinkageVo;
	}
	
	/**
	 * 数据处理
	* <p>Title: covertFormSelectDatas</p>  
	* <p>Description: </p>  
	* @param formSelectDatas
	* @param parentId
	* @return
	 */
	private static List<MultipleLinkageVo> covertMultipleLinkageDatas(List<MultipleLinkageVo> multipleLinkageVos,String parentId){
		List<MultipleLinkageVo> mls =  new ArrayList<MultipleLinkageVo>();
		for(MultipleLinkageVo ml : multipleLinkageVos) {
			if(ml.getParentId().equals(parentId)) {
				ml.setChilds(covertMultipleLinkageDatas(multipleLinkageVos,ml.getId()));
				mls.add(ml);
			}
		}
		return mls;
	}
}
