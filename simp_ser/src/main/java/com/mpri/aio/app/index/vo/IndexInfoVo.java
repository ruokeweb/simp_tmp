package com.mpri.aio.app.index.vo;


public class IndexInfoVo {
    private String id;
    private String userId;
    private String name;
    private IndexEduVo indexEduVo;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IndexEduVo getIndexEduVo() {
        return indexEduVo;
    }

    public void setIndexEduVo(IndexEduVo indexEduVo) {
        this.indexEduVo = indexEduVo;
    }
}
