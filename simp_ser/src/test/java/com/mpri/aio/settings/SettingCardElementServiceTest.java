package com.mpri.aio.settings;

import com.mpri.aio.ApplicationTests;
import com.mpri.aio.settings.model.SettingCardElement;
import com.mpri.aio.settings.model.SettingDepartment;
import com.mpri.aio.settings.service.SettingCardElementService;
import com.mpri.aio.settings.service.SettingDepartmentService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class SettingCardElementServiceTest extends ApplicationTests {

    @Autowired
    private SettingCardElementService settingCardElementServiceTest;
    @Test
    public void test() {
        SettingCardElement settingDepartment = new SettingCardElement();
        settingDepartment.setCardId("48b8b4e12d8b4f089f46e8fc833bde90");
        settingDepartment.setFace("FACE");
        settingDepartment.setPosition("下边");
        settingDepartment.setStatus("班级名称");
        settingDepartment.setColor("蓝色");
        settingDepartment.setStyle("margin: 100px");
        settingCardElementServiceTest.save(settingDepartment);

    }

}