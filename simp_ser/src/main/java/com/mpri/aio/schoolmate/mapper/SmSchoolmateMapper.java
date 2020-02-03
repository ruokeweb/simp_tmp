package com.mpri.aio.schoolmate.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.mpri.aio.association.model.AsAssociationUser;
import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.message.vo.MesGroupSchoolmates;
import com.mpri.aio.schoolmate.model.SmContact;
import com.mpri.aio.schoolmate.model.SmMark;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import com.mpri.aio.webber.vo.SchoolmateInfoVo;



 /**   
 *  
 * @Description:  校友基本信息——DAO
 * @Author:       syp
 * @project 	  exchange_datasource 
 * @CreateDate:   Mon Jan 28 15:29:11 CST 2019
 * @Version:      v_1.0
 *    
 */
@Mapper
public interface SmSchoolmateMapper extends CrudMapper<SmSchoolmate>{
	
	/**
	 *通过校友id获取校友基本信息(籍贯和用户名（即联系方式）)
	 */
	SmSchoolmate getSmInfoById (SmSchoolmate schoolmate);
	 /**
	  *通过校友userid获取校友信息
	  */
	 SmSchoolmate getByUserId (SmSchoolmate schoolmate);
	/**
	 * 不带分页获取数据（带多条件）
	 */
	List<SmSchoolmate> loadListBy (@Param("entity") SmSchoolmate schoolmate);
	/**
	 * 不带分页获取群组条件（带多条件）
	 */
	List<MesGroupSchoolmates> loadListByCondition (@Param("entity") SmSchoolmate schoolmate);

	 /**校友信息 单位信息统计*/
	List<SmSchoolmate> loadListByStaticCondition(@Param("entity") SmSchoolmate schoolmate);

	/**
	 * 获取导出的List
	 */
	List<SmSchoolmate> ExportList (@Param("entity") SmSchoolmate schoolmate);

	/**
	 * 多条件以及模糊字段联合查询获取导出的List
	 */
	List<SmSchoolmate> ExportListByCondition(@Param("entity") SmSchoolmate schoolmate,@Param(value = "fuzzySearchFiled") String  fuzzySearchFiled);

	/**
	 * 对比获取重复数据
	 */
	List<SmSchoolmate> getListCompare(SmSchoolmate schoolmate);
	
	/**
	 * 根据标签获取列表
	 */
	List<SmSchoolmate> loadSmsByMark(SmMark smMark);
	
	/**
	 * 删除标签中间表
	 */
	void deleteUserMark(SmSchoolmate smSchoolmate);

	/**
	 * 插入标签中间表
	 */
	void insertUserMark(SmSchoolmate smSchoolmate);
	 /**
	  * 通过name查询
	  * @param smSchoolmate
	  * @return
	  */
	 Page<SmSchoolmate> listByName(@Param("entity") SmSchoolmate smSchoolmate);
	 
	 Page<SmSchoolmate> listOnlyByName(@Param("entity") SmSchoolmate smSchoolmate);
	 
	 /**
	  * 通过生日查询
	 * <p>Title: loadListByBirth</p>  
	 * <p>Description: </p>  
	 * @param smSchoolmate
	 * @return
	  */
	 List<SmSchoolmate> loadListByBirth(@Param("entity") SmSchoolmate smSchoolmate);
	 
	 /**
	  * 通过校友ID 获取校友个人所有信息
	  */
	 SmSchoolmate getUserINfoById(@Param("entity") SmSchoolmate smSchoolmate);
	 
	 /**
	  * 更新校友星级完整度 
	  */
	 void updateComplete(SmSchoolmate smSchoolmate);
	 
	 /**
	  * 可能是他（校友）
	  */
	 List<SmSchoolmate> getListLike(SmSchoolmate schoolmate);
	 
	 /**
	  * 通过user_id 删除校友
	  */
	 void deleteOldSm(SmSchoolmate schoolmate);

	 /**
	  * 校友数据全字段模糊查询
	  * @param selectStr
	  * @return
	  */
	 Page<SmSchoolmate> loadByPageAll(@Param(value = "selectStr") String  selectStr,@Param(value = "type") String  type);

	 /**
	  * 交大老数据查询重复数据
	  * @param nowSchoolmate
	  * @return
	  */
	 List<SmSchoolmate> getListCompareByUserId(SmSchoolmate nowSchoolmate);

     SmSchoolmate getUserEduInfo(@Param("entity") SmSchoolmate smSchoolmate);

	 /**
	  * 获取有身份证的
	  * @param smSchoolmate
	  * @return
	  */
     List<SmSchoolmate> getListByCardNum(SmSchoolmate smSchoolmate);
//	 Page<SmSchoolmate> loadByPageAll(SmSchoolmate schoolmate);
     
     
     /**
      * deleteSysAs
      */
     void deleteSysAs(AsAssociationUser asAssociationUser);

	 /**
	  * 在组件中使用 根据条件获取用户
	  * @param smSchoolmate
	  * @return
	  */
	 Page<SchoolmateInfoVo> getSchoolmateInfo(@Param("entity") SmSchoolmate smSchoolmate);

	 /**
	  *	根据手机号获取用户
	  * @param phone
	  * @return
	  */
	 SchoolmateInfoVo getSchoolmateByPhone( @Param("entity") SmContact smContact);
	 
	 /**
	  * 更新拼音
	  */
	 void updatePinyin(SmSchoolmate smSchoolmate);
	 
	 
	 /**
	  * 导出用户联系方式
	  */
	 List<SmContact> ExportContactListByCondition(@Param("entity") SmSchoolmate schoolmate,@Param(value = "fuzzySearchFiled") String  fuzzySearchFiled);
 }