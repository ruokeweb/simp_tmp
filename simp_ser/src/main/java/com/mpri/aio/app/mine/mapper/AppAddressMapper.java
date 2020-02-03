package com.mpri.aio.app.mine.mapper;

import com.github.pagehelper.Page;
import com.mpri.aio.schoolmate.model.SmAddress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AppAddressMapper {

    SmAddress get(SmAddress smAddress);

    Page<SmAddress> loadByPage (@Param("entity") SmAddress smAddress);

    void delete(SmAddress smAddress);

    void insert(SmAddress smAddress);

    void update(SmAddress smAddress);

    void setDefault(SmAddress smAddress);

    void clearDefault(SmAddress smAddress);
}
