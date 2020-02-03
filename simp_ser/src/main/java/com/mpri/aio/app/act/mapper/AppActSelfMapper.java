package com.mpri.aio.app.act.mapper;

import com.github.pagehelper.Page;
import com.mpri.aio.act.model.ActSelforg;
import com.mpri.aio.act.model.ActSelforgContent;
import com.mpri.aio.app.act.vo.ActDetailVo;
import com.mpri.aio.app.act.vo.ActSelfVo;
import com.mpri.aio.app.act.vo.AttendSmByAct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AppActSelfMapper {

    /**
     *
     * @param entity
     * @return
     */
    Page<ActSelfVo> getActSelfList(@Param("entity") ActSelforg entity);

    /**
     * 新增
     * @param entity
     */
    void insert(ActSelforg entity);

    /**
     * 获取值年返校详情
     * @param entity
     * @return
     */
    ActSelfVo getSelfDetail(ActSelforg entity);

    /**
     * 已报名校友列表
     * @param entity
     * @return
     */
    Page<AttendSmByAct> getAttendSmBySelf(ActSelforg entity);

    /**
     * 通过id 获取
     * @param entity
     * @return
     */
    ActSelforg getById(ActSelforg entity);

    /**
     * 修改
     * @param entity
     */
    void update(ActSelforg entity);

    Integer getMyActSelfNum(@Param("entity")ActSelforg actSelforg);
}
