package com.mpri.aio.app.utils.service;

import com.mpri.aio.app.wxdata.model.WxdataTemplateSend;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TemplateUtils {

    public  static WxdataTemplateSend getTemplateSend(String touser, String form_id){

        WxdataTemplateSend wxdataTemplateSend = new WxdataTemplateSend();
        wxdataTemplateSend.setTouser(touser);
        wxdataTemplateSend.setFormId(form_id);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(new Date());
        Date sDate = null;
        try {
            sDate = format.parse(dateString);
        } catch (ParseException e) {
            //e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(sDate);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        Date date=cal.getTime();
        wxdataTemplateSend.setValidPeriod(date);
        return wxdataTemplateSend;

    }
}
