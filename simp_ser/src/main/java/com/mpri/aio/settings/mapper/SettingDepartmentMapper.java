package com.mpri.aio.settings.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.settings.model.SettingDepartment;
import com.mpri.aio.settings.vo.DepartmentMerge;

import java.util.List;


/**
 *
 * @Description:  院系专业——DAO
 * @Author:       zdl
 * @project 	  smmp
 * @CreateDate:   Thu Jan 31 11:33:46 CST 2019
 * @Version:      v_1.2
 *
 */
@Mapper
public interface SettingDepartmentMapper extends CrudMapper<SettingDepartment>{
		/**
		 *    获取备用系分组
		 * @param departmentMerge
		 * @return
		 */
	  Page<DepartmentMerge> loadAllTempSeries(@Param("entity") DepartmentMerge departmentMerge);

	 /**
	  *  合并系别名
	  * @param series
	  * @param school
	  * @param college
	  * @param departmentMerge
	  */
	  void mergeSeries( @Param("school") String school, @Param("college") String college,@Param("series")String series, @Param("list") List<DepartmentMerge>  departmentMerge);
	 /**
	  *  合并系别名
	  * @param series
	  * @param school
	  * @param college
	  * @param departmentMerge
	  */
	  void mergeSpecialty( @Param("school") String school, @Param("college") String college,@Param("series")String series,@Param("specialty")String specialty, @Param("list") List<DepartmentMerge>  departmentMerge);

	/**
	 * 根据条件查询需要修改的id
	 * @param departmentMerge
	 * @return
	 */
	 List<DepartmentMerge> loadByMerge(@Param("entity")DepartmentMerge departmentMerge);
	/**
	 * 根据条件查询需要修改的id
	 * @param departmentMerge
	 * @return
	 */
	 List<DepartmentMerge> loadBySpecialtyMerge(@Param("entity")DepartmentMerge departmentMerge);

	Page<DepartmentMerge> loadAllTempSpecialty(@Param("entity")DepartmentMerge departmentMerge);
}