package com.mpri.aio.untils.file.service;

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

import com.mpri.aio.untils.file.dao.FileRepository;
import com.mpri.aio.untils.file.model.File;

@Service
public class FileServiceImpl implements FileService{
	@Autowired
	public FileRepository fileRepository;

	@Override
	public File saveFile(File file) {
		return fileRepository.save(file);
	}

	@Override
	public void removeFile(String id) {
		fileRepository.deleteById(id);
	}

	@Override
	public Optional<File> getFileById(String id) {
		return fileRepository.findById(id);
	}

	@Override
	public List<File> listFilesByPage(int pageIndex, int pageSize) {
		Page<File> page = null;
		List<File> list = null;
		String []pr = {"uploadDate"};
		Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Direction.DESC, pr));
		
		page = fileRepository.findAll(pageable);
		list = page.getContent();
		return list;
	}
}
