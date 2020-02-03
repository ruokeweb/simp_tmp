package com.mpri.aio.webber.controller;


import com.github.pagehelper.PageInfo;
import com.mpri.aio.association.model.AsAssociation;
import com.mpri.aio.association.model.AsPost;
import com.mpri.aio.association.service.AsAssociationService;
import com.mpri.aio.association.service.AsPostService;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("webber/open/association")
public class AssociationController
{

    @Autowired
    private AsAssociationService asAssociationService;
    @Autowired
    private AsPostService asPostService;

    /**
     * 分页加载校友会类列表
     * @param pageNo
     * @param pageSize
     * @param asAssociation
     * @return
     */
    @PostMapping(value = "/list")
    public RestResponse< PageIo<AsAssociation>> list(int pageNo, int pageSize, AsAssociation asAssociation){
        PageIo<AsAssociation> pageInfo = asAssociationService.loadByPage(pageNo,pageSize,asAssociation);
        return new RestResponse< PageIo<AsAssociation>>(ExceptionResult.REQUEST_SUCCESS, "获取校友会列表成功", pageInfo);
    }

    /**
     * 获取校友会信息
     * <p>Title: get</p>
     * <p>Description: </p>
     * @return
     */
    @PostMapping(value = "/get")
    public RestResponse<AsAssociation> get(AsAssociation asAssociation) {
        AsAssociation resAsAssociation = asAssociationService.get(asAssociation);
        AsAssociation parentAsAssociation = new AsAssociation();
        parentAsAssociation.setId(resAsAssociation.getParentId());
        resAsAssociation.setParentAsAssociation(asAssociationService.get(parentAsAssociation));
        return new RestResponse<AsAssociation>(ExceptionResult.REQUEST_SUCCESS, "获取校友会信息成功！", resAsAssociation);
    }

    /**
     * 获取校友会任职信息列表
     * <p>Title: list</p>
     * <p>Description: </p>
     * @param pageNo
     * @param pageSize
     * @param asPost
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/postlist")
    public RestResponse< PageIo<AsPost>> list(int pageNo, int pageSize, AsPost asPost) {
        PageIo<AsPost> pageInfo =  asPostService.loadSchoolByPage(pageNo,pageSize,asPost);
        return new RestResponse< PageIo<AsPost>>(ExceptionResult.REQUEST_SUCCESS, "获取校友会任职信息成功！", pageInfo);
    }

}
