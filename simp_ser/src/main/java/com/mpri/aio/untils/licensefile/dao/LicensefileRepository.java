package com.mpri.aio.untils.licensefile.dao;

import com.mpri.aio.untils.licensefile.model.LicenseFile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LicensefileRepository extends MongoRepository<LicenseFile, String> {

}