package com.mpri.aio.settings;

import com.mpri.aio.ApplicationTests;
import com.mpri.aio.settings.model.SettingCardElement;
import com.mpri.aio.settings.model.SettingPage;
import com.mpri.aio.settings.service.SettingCardElementService;
import com.mpri.aio.settings.service.SettingPageService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class PageTest extends ApplicationTests {

    @Autowired
    private SettingPageService settingPageService;
    @Test
    public void test() {
        SettingPage settingPage = new SettingPage();
        settingPage.setName("值年返校简介页");
        settingPage.setCode("backSchool");
        settingPage.setContent("<p>亲爱的校友：&nbsp;<br>&nbsp; &nbsp; &nbsp;弹指间岁月变迁，风雨兼程的您是否还记得曾经的青青校园？是否还惦念旧时朝夕相处的的恩师同窗？是否总有一段马家花园的记忆拨动您的心弦？秀园、五谷园的景色是否常悄然进入您的梦乡？您可曾梦想再次踏入校园，留下美好的身影和健硕的步伐？母校这方土地蕴育过您的梦想，挥洒过您的汗水，记载着您那段青葱岁月里的友情、爱情，喜怒哀乐，相聚、别离。不论身在何方，您永远是母校的牵挂。近两年，每年暑期都有近千名校友回到校园逐梦青春，也请您邀约班级、年级同学一同“回家”吧！&nbsp;<br>&nbsp;&nbsp;</p>");
        settingPageService.save(settingPage);
    }

}