package com.mpri.aio.untils.licensefile.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mpri.aio.untils.licensefile.dao.LicensefileRepository;
import com.mpri.aio.untils.licensefile.model.LicenseFile;

@Service
public class LicenseFileServiceImpl implements LicenseFileService{
	@Autowired
	public LicensefileRepository fileRepository;

	@Override
	public LicenseFile saveFile(LicenseFile file) {
		return fileRepository.save(file);
	}

	@Override
	public void removeFile(String id) {
		fileRepository.deleteById(id);
	}

	@Override
	public Optional<LicenseFile> getFileById(String id) {
		return fileRepository.findById(id);
	}

	@Override
	public List<LicenseFile> listFilesByPage(int pageIndex, int pageSize) {
		Page<LicenseFile> page = null;
		List<LicenseFile> list = null;
		
		String [] pr= {"uploadDate"};
		Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Direction.DESC, pr));
		
		page = fileRepository.findAll(pageable);
		list = page.getContent();
		return list;
	}
}
