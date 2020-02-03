package com.mpri.aio.settings.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.settings.mapper.SettingDepartmentMapper;
import com.mpri.aio.settings.model.SettingDepartment;
import com.mpri.aio.settings.vo.DepartmentMerge;

/**
 *
 * @Description: 院系专业——Service
 * @Author: zdl
 * @project smmp
 * @CreateDate: Thu Jan 31 11:33:46 CST 2019
 * @Version: v_1.2
 *
 */
@Service
public class SettingDepartmentService extends CrudService<SettingDepartmentMapper, SettingDepartment> {

	/**
	 * 根据子节点的id,删除包括该节点的所有子节点
	 * 
	 * @param settingDepartment
	 */
	@Transactional(readOnly = false)
	public void deleteAllChildrenByParentId(SettingDepartment settingDepartment) {
		realDelete(settingDepartment);
	}

	private void realDelete(SettingDepartment settingDepartment) {
		SettingDepartment setting = new SettingDepartment();
		setting.setParentId(settingDepartment.getId());
		List<SettingDepartment> children = getChildren(setting);
		boolean b = ifChildren(children);
		if (b) {
			for (SettingDepartment settingDepartment1 : children) {
				realDelete(settingDepartment1);

			}
		}
		mapper.delete(settingDepartment);
		// System.out.println("需要删除=============="+settingDepartment.getId());
	}

	/**
	 * 根据id获取子集
	 * 
	 * @param settingDepartment
	 * @return
	 */
	private List<SettingDepartment> getChildren(SettingDepartment settingDepartment) {
		return mapper.loadAllListBy(settingDepartment);
	}

	/**
	 * 是否存在字集
	 * 
	 * @param settingDepartments
	 * @return
	 */
	private boolean ifChildren(List<SettingDepartment> settingDepartments) {
		if (settingDepartments != null && !settingDepartments.isEmpty()) {
			return true;
		}
		return false;
	}

	public PageIo<DepartmentMerge> loadAllTempSeries(int pageNo, int pageSize, DepartmentMerge departmentMerge) {
		PageHelper.startPage(pageNo, pageSize);
		Page<DepartmentMerge> pageList = this.mapper.loadAllTempSeries(departmentMerge);
		PageIo<DepartmentMerge> pageInfo = new PageIo<DepartmentMerge>(pageList);
		return pageInfo;
	}

	public PageIo<DepartmentMerge> loadAllTempSpecialty(int pageNo, int pageSize, DepartmentMerge departmentMerge) {
		PageHelper.startPage(pageNo, pageSize);
		Page<DepartmentMerge> pageList = this.mapper.loadAllTempSpecialty(departmentMerge);
		PageIo<DepartmentMerge> pageInfo = new PageIo<DepartmentMerge>(pageList);
		return pageInfo;
	}

	@Transactional(readOnly = false)
	public void mergeSeries(String school, String college, String series, DepartmentMerge departmentMerge) {
		List<DepartmentMerge> merges = this.mapper.loadByMerge(departmentMerge);
		if (!merges.isEmpty()) {
			int num = 1000;
			while (!merges.isEmpty()) {
				List<DepartmentMerge> tmp = new ArrayList<>();
				for (int i = 0; i < num; i++) {
					if (merges.isEmpty()) {
						break;
					}
					tmp.add(merges.get(0));
					merges.remove(0);
				}
				this.mapper.mergeSeries(school, college, series, tmp);
			}
		}

	}

	@Transactional(readOnly = false)
	public void mergeSpecialty(String school, String college, String series, String specialty,
			DepartmentMerge departmentMerge) {
		List<DepartmentMerge> merges = this.mapper.loadBySpecialtyMerge(departmentMerge);
		if (!merges.isEmpty()) {
			int num = 1000;
			while (!merges.isEmpty()) {
				List<DepartmentMerge> tmp = new ArrayList<>();
				for (int i = 0; i < num; i++) {
					if (merges.isEmpty()) {
						break;
					}
					tmp.add(merges.get(0));
					merges.remove(0);
				}
				this.mapper.mergeSpecialty(school, college, series, specialty, tmp);
			}
		}

	}
}