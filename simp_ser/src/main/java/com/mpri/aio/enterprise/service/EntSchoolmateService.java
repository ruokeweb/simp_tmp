package com.mpri.aio.enterprise.service;

import com.github.pagehelper.PageHelper;
import com.mpri.aio.common.page.PageIo;
import org.springframework.stereotype.Service;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.enterprise.model.EntSchoolmate;
import com.mpri.aio.enterprise.mapper.EntSchoolmateMapper;

import java.util.List;

/**
 * 
 * @Description: 校友企业校友任职表——Service
 * @Author: lzq
 * @project simp
 * @CreateDate: Thu Feb 14 15:59:00 CST 2019
 * @Version: v_1.2
 * 
 */
@Service
public class EntSchoolmateService extends CrudService<EntSchoolmateMapper, EntSchoolmate> {

	public PageIo<EntSchoolmate> loadSchoolByPage(int pageNo, int pageSize, EntSchoolmate entSchoolmate) {
		PageHelper.startPage(pageNo, pageSize);
		List<EntSchoolmate> pageList = this.mapper.loadByPageSchool(entSchoolmate);
		PageIo<EntSchoolmate> pageInfo = new PageIo<EntSchoolmate>(pageList);
		return pageInfo;
	}

}