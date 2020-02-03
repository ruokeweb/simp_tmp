package com.mpri.aio.donation.utils;

import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.donation.model.DonRecord;
import com.mpri.aio.settings.model.SettingDepartment;
import com.mpri.aio.settings.model.SettingSubject;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.init.InitCache;
import com.mpri.aio.system.model.SysArea;
import com.mpri.aio.system.model.SysDict;
import com.mpri.aio.system.model.SysIndustry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 捐赠详情信息处理
 */
@Service
public class RecordExportHandler {
    @Autowired
    private RedisCacheService redisCacheService;


    private Map<String, List<SysDict>> dictCache;
    private Map<String, SysArea> areaBaseCache;
    private Map<String, SettingDepartment> departBaseCache;
    private Map<String, SettingSubject> subjectBaseCache;
    private Map<String, SysIndustry> industryBaseCache;


    private void InitMaps() {
        dictCache = (Map<String, List<SysDict>>) redisCacheService.getCache(new SysDict(), InitCache.DICT_CACHE);
        areaBaseCache = (Map<String, SysArea>) redisCacheService.getBaseCache(new SysArea(), InitCache.AREA_BASE_CACHE);
        departBaseCache = (Map<String, SettingDepartment>) redisCacheService.getBaseCache(new SettingDepartment(), InitCache.DEPART_BASE_CACHE);
        subjectBaseCache = (Map<String, SettingSubject>) redisCacheService.getBaseCache(new SettingSubject(), InitCache.SETTING_BASE_SUBJECT);
        industryBaseCache = (Map<String, SysIndustry>) redisCacheService.getBaseCache(new SysIndustry(), InitCache.SYS_BASE_INDUSTRY_CACHE);
    }

    /**
     * 导出数据转换
     * <p>Title: smhander</p>
     * <p>Description: </p>
     *
     * @param list
     * @return
     */
    public List<DonRecord> handlerExport(List<DonRecord> list) {
        this.InitMaps();
        for (DonRecord donRecord : list) {
            //转换校友基本信息
            donRecord.setDonType(setFormatDictValue(donRecord.getDonType(), GlobalStr.DONATION_TYPE));
            donRecord.setMoneyType(setFormatDictValue(donRecord.getMoneyType(), GlobalStr.MONEY_TYPE));
            donRecord.setStyle(setFormatDictValue(donRecord.getStyle(), GlobalStr.PAY_STYLE));
        }
        return list;
    }


    private String setFormatDictValue(String value, String typeCode) {
        if (!StringUtil.isEmpty(value)) {
            //key: redis内key的规则：type:value
            List<SysDict> sds = (List<SysDict>) dictCache.get(typeCode);
            for (SysDict dict : sds) {
                if (value.equals(dict.getValue())) {
                    return dict.getLabel();
                }
            }
        }
        return "";
    }

}
