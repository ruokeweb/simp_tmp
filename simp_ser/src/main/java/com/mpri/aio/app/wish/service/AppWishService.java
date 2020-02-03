package com.mpri.aio.app.wish.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mpri.aio.app.wish.mapper.AppWishMapper;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.schoolmate.model.SmWish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppWishService {

    @Autowired
    AppWishMapper appWishMapper;

    public SmWish insert (SmWish smWish){
        smWish.preInsert();
        appWishMapper.insert(smWish);
        return smWish;
    }

    public PageIo<SmWish> loadByPage(int pageNo, int pageSize,SmWish smWish){
        PageHelper.startPage(pageNo, pageSize);
        Page<SmWish> pageList = appWishMapper.loadByPage(smWish);
        PageIo<SmWish> pageInfo = new PageIo<>(pageList);
        return pageInfo;
    }
}
