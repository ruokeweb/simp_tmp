package com.mpri.aio.schoolmate.vo;

import cn.afterturn.easypoi.handler.inter.IExcelModel;

/**
 * 返回导出错误
 * @author syp
 *
 */
public class ExcelVerifyEntityOfMode implements IExcelModel {

    private String errorMsg;

    @Override
    public String getErrorMsg() {
    	System.err.println(errorMsg);
        return errorMsg;
    }

    @Override
    public void setErrorMsg(String errorMsg) {
    	System.err.println(errorMsg);
        this.errorMsg = errorMsg;
    }

    
}
