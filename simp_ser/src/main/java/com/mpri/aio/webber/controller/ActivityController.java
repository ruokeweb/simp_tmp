package com.mpri.aio.webber.controller;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.act.model.ActActivity;
import com.mpri.aio.act.service.ActActivityService;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.init.InitCache;
import com.mpri.aio.system.model.SysArea;
import com.mpri.aio.system.model.SysDict;
import com.mpri.aio.webber.vo.ActivityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("webber/open/activity")
public class ActivityController  extends BaseController {

    @Autowired
    private ActActivityService actActivityService;
    
    @Autowired
    private RedisCacheService redisCacheService;

    private Map<String,List<SysDict>> dictCache;
    
    private void InitMaps() {
        dictCache= (Map<String,List<SysDict>>)redisCacheService.getCache(new SysDict(), InitCache.DICT_CACHE);
    }
    /**
     * 获取活动列表
     * <p>Title: list</p>
     * <p>Description: </p>
     * @param pageNo
     * @param pageSize
     * @param actActivity
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/list")
    public PageInfo<ActActivity> list(int pageNo, int pageSize, ActActivity actActivity) {
        this.InitMaps();
        PageIo<ActActivity> pageInfo =  actActivityService.loadByPage(pageNo,pageSize,actActivity);
        List<ActActivity> list=pageInfo.getList();
        if(list!=null&&!list.isEmpty()){
            for (ActActivity act:list) {
                act.setStatusName(setFormatDictValue(act.getStatus(),GlobalStr.ACT_STATUS));
            }

        }
        pageInfo.setList(list);
        return pageInfo;
    }
    /**
     * 获取活动列表
     * <p>Title: list</p>
     * <p>Description: </p>
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/listNew")
    public PageInfo<ActActivity> listNew(@RequestBody ActivityVo activityVo) {
        this.InitMaps();
        ActActivity actActivity = activityVo.getActActivity();
        if(actActivity==null){
            actActivity = new ActActivity();
        }
        int pageNo = activityVo.getPageNo();
        int pageSize = activityVo.getPageSize();
        PageIo<ActActivity> pageInfo =  actActivityService.loadByPage(pageNo,pageSize,actActivity);
        List<ActActivity> list=pageInfo.getList();
        if(list!=null&&!list.isEmpty()){
            for (ActActivity act:list) {
                act.setStatusName(setFormatDictValue(act.getStatus(),GlobalStr.ACT_STATUS));
            }

        }
        pageInfo.setList(list);
        return pageInfo;
    }

    /**
     * 获取活动
     * <p>Title: get</p>
     * <p>Description: </p>
     * @param actActivity
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/get")
    public RestResponse<ActActivity> get(ActActivity actActivity) {
        ActActivity act = actActivityService.get(actActivity);
        if(act!=null){
            act.setStatusName(setFormatDictValue(act.getStatus(),GlobalStr.ACT_STATUS));
        }

        return new RestResponse<ActActivity>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
                act);
    }
    /**
     * 获取活动
     * <p>Title: get</p>
     * <p>Description: </p>
     * @param actActivity
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/getNew")
    public RestResponse<ActActivity> getNew(@RequestBody ActActivity actActivity) {
        ActActivity act = actActivityService.get(actActivity);
        if(act!=null){
            act.setStatusName(setFormatDictValue(act.getStatus(),GlobalStr.ACT_STATUS));
        }

        return new RestResponse<ActActivity>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
                act);
    }
    private String setFormatDictValue(String value,String typeCode) {
        if(!StringUtil.isEmpty(value)) {
            //key: redis内key的规则：type:value
            List<SysDict> sds=(List<SysDict>)dictCache.get(typeCode);
            if(!sds.isEmpty()){
                for(SysDict dict : sds) {
                    if(value.equals(dict.getValue())) {
                        return dict.getLabel();
                    }
                }
            }
        }
        return "";
    }
}
