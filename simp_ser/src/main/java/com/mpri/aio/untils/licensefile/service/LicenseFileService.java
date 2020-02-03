package com.mpri.aio.untils.licensefile.service;

import com.mpri.aio.untils.licensefile.model.LicenseFile;
import java.util.List;
import java.util.Optional;

public interface LicenseFileService {

	/**
	 * 保存文件
	 */
	LicenseFile saveFile(LicenseFile file);
	
	/**
	 * 删除文件
	 */
	void removeFile(String id);
	
	/**
	 * 根据id获取文件
	 */
	Optional<LicenseFile> getFileById(String id);

	/**
	 * 分页查询，按上传时间降序
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	List<LicenseFile> listFilesByPage(int pageIndex, int pageSize);
	
}
