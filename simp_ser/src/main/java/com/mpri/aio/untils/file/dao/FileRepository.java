package com.mpri.aio.untils.file.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mpri.aio.untils.file.model.File;

public interface FileRepository extends MongoRepository<File, String>{

}
