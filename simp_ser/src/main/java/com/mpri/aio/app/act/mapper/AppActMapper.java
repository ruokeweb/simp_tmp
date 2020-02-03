package com.mpri.aio.app.act.mapper;

import com.github.pagehelper.Page;
import com.mpri.aio.act.model.ActActivity;
import com.mpri.aio.act.model.ActSetting;
import com.mpri.aio.app.act.vo.ActDetailVo;
import com.mpri.aio.app.act.vo.ActListVo;
import com.mpri.aio.app.act.vo.AttendSmByAct;
import com.mpri.aio.app.act.vo.BannerActsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface AppActMapper {


    /**
     * 活动首页banner图
     * @param entity
     * @return
     */
    Page<BannerActsVo> getBannerActs(@Param("entity") ActActivity entity);

    /**
     * 活动列表分页查询
     * @param entity
     * @return
     */
    Page<ActListVo > ActList (@Param("entity") ActActivity entity);

    /**
     * 活动详情
     * @param entity
     * @return
     */
    ActDetailVo getActDetail(ActActivity entity);

    /**
     * 获取已经报名的校友
     * @param entity
     * @return
     */
    Page<AttendSmByAct> getAttendSmByAct(ActActivity entity);

    /**
     * 获取动态表单
     * @param actActivity
     * @return
     */
    List<ActSetting> getActForm(ActActivity actActivity);

    /**
     * 获取活动
     * @param entity
     * @return
     */
    ActActivity getById(ActActivity entity);

    /**
     * 更新
     * @param actActivity
     */
    void update(ActActivity actActivity);

    /**
     * 热门活动推荐
     * @param entity
     * @return
     */
    Page<ActListVo> hotActList(@Param("entity") ActActivity entity);

    /**
     * 我参与的活动列表
     * @param actActivity
     * @param userId
     * @return
     */
    Page<ActListVo> getPartakeActList(@Param("entity") ActActivity actActivity, @Param("userId") String userId);

    ActListVo getPartakeActOne(@Param("entity") ActActivity actActivity, @Param("userId") String userId);

}
