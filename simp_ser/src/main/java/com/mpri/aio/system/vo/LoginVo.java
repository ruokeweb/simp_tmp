package com.mpri.aio.system.vo;

import com.mpri.aio.common.response.RestToken;

public class LoginVo {
    private RestToken restToken;
    private UpdatePasswordVo updatePasswordVo ;

    public RestToken getRestToken() {
        return restToken;
    }

    public void setRestToken(RestToken restToken) {
        this.restToken = restToken;
    }

    public UpdatePasswordVo getUpdatePasswordVo() {
        return updatePasswordVo;
    }

    public void setUpdatePasswordVo(UpdatePasswordVo updatePasswordVo) {
        this.updatePasswordVo = updatePasswordVo;
    }
}
