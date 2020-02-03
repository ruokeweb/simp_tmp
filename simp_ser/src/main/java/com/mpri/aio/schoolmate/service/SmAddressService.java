package com.mpri.aio.schoolmate.service;

import org.springframework.stereotype.Service;

import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.schoolmate.mapper.SmAddressMapper;
import com.mpri.aio.schoolmate.model.SmAddress;

import java.util.List;

/**
 *
 * @Description: 校友通讯地址——Service
 * @Author: syp
 * @project exchange_datasource
 * @CreateDate: Mon Jan 28 15:31:22 CST 2019
 * @Version: v_1.0
 *
 */

@Service
public class SmAddressService extends CrudService<SmAddressMapper, SmAddress> {

	/**
	 * 查询籍贯
	 */
	public SmAddress getNationBySysUser(SmAddress smAddress) {
		return mapper.getNationBySysUser(smAddress);
	}

	public List<SmAddress> getUserId() {
		return this.mapper.getUserId();
	}
	
	public void deleteByUserId(SmAddress address) {
		mapper.deleteByUserId(address);
	}
}
