package com.mpri.aio.system.service;

import org.springframework.stereotype.Service;

import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.system.mapper.SysLogsMapper;
import com.mpri.aio.system.model.SysLogs;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 
 * @author Cary
 * @date 2018年8月6日
 */
@Service
public class SysLogsService extends CrudService<SysLogsMapper, SysLogs>  {

    @Resource(name="")
    private DataSource dataSource;

    /**
     * 清理多余数据
     */
    public void deleteAll(){
        mapper.deleteAll();
    }
}