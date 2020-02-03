package com.mpri.aio.settings;

import com.mpri.aio.ApplicationTests;
import com.mpri.aio.common.utils.IdGen;
import com.mpri.aio.settings.model.SettingDepartment;
import com.mpri.aio.settings.service.SettingDepartmentService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;



public class SettingDepartmentServiceTest  extends ApplicationTests {
    private String[] dep = {"材料科学与工程学院","电气工程与信息工程学院","法学院","国际教育学院","机电工程学院","计算机与通信学院","继续教育学院项目培训班"
            ,"继续教育学院夜大函授脱产","继续教育学院自考","经济管理学院","理学院","马克思主义学院","能源与动力工程学院","设计艺术学院","生命科学与工程学院","石油化工学院"
            ,"体育教学研究部","土木工程学院","外国语学院","新能源学院"};
   // private String[] xueli ={"本科生","博士生","硕士生","专科生","语言生"};
   // private String[] xueli ={"本科生"};
   // private String [] zhuanye ={"","","","","","","","","","","","","","","","","","","","","","","","",""};
    private String [] zhuanye ={"工业与民用建筑"
           ,"工业与民用建筑工程"
           ,"供用电技术"
           ,"管理工程"
           ,"管理信息系统"
           ,"焊接"
           ,"焊接技术与工程"
           ,"化工工艺"
           ,"化工机械"
           ,"化工设备与机械"
           ,"会计电算化"
           ,"机电工程"
           ,"机电一体化"
           ,"机电一体化技术"
           ,"机械设计"
           ,"机械设计与制造"
           ,"机械设计制造及其自动化"
           ,"机械制造"
           ,"计算机及其应用"
           ,"计算机及应用"
           ,"计算机网络技术"
           ,"计算机信息管理"
           ,"计算机应用"
           ,"计算机应用技术"
           ,"计算机应用与维护"
           ,"计算机与信息管理"
           ,"建筑工程管理"
           ,"建筑工程技术"
           ,"建筑工程项目管理"
           ,"经济管理"
           ,"经济信息管理"
           ,"精细化工"
           ,"精细化工工艺"
           ,"矿产资源开发与管理"
           ,"矿山资源开发与管理"
           ,"旅游管理"
           ,"企业管理"
           ,"汽车检测与维修技术"
           ,"热能动力工程"
           ,"热能与动力工程"
           ,"市场开发与营销"
           ,"市场营销"
           ,"数控技术"
           ,"水利水电工程"
           ,"无机非金属材料工程技术"
           ,"物业管理"
           ,"现代纺织技术"
           ,"冶金技术"
           ,"英语"
           ,"应用电子技术"
           ,"应用化工技术"
           ,"应用英语"
           ,"油气储运工程"
           ,"油气开采技术"
           ,"质量技术监督"

   };

    @Autowired
    private SettingDepartmentService settingDepartmentService;

    public void deleteAllChildrenByParentId() {

        for (int i=0 ;i<zhuanye.length;i++){
            SettingDepartment settingDepartment = new SettingDepartment();
            settingDepartment.setName(zhuanye[i]);
            settingDepartment.setParentId("0404710bd6b54560ba944af42e772ee0");
            settingDepartment.setParentIds("root,0,16b349f764b14c31b5125a29a8c98c7c,f485fe23730b4b1daabf17222319fb18,0404710bd6b54560ba944af42e772ee0");
            settingDepartment.setCode("1008020"+(i+1)+0);
            settingDepartment.setType("MAJOR");
            settingDepartment.setSort(i+1);
            settingDepartmentService.save(settingDepartment);
        }
    }

    /**
     * 青海大学基础数据初始化
     */
    @Test
    public void  qihaiDepartmentCreate(){
        String school ="青海大学";
        String  collage[] =  {"藏医学院","农牧学院","计算机技术与应用系","化工学院","医学院","土木工程学院","新能源光伏中心","财经学院","生态环境工程学院","水利电力学院","地质工程系","机械工程学院"};
        String  series[][] =  {{"藏医学院"},{"农牧学院"},{"计算机技术与应用系"},{"化工学院"},{"医学院"},{"土木工程学院"},{"新能源光伏中心"},{"财经学院"},{"生态环境工程学院"},{"水利电力学院"},{"地质工程系"},{"机械工程学院"}};
        String  specialty[][][] = {
                {{"藏医学","藏医学（专科）"}},
                {{"植物生产类()","植物生产类()","食品科学与工程","动物科学" ,"林学" ,"草业科学" ,"动物医学","园林" ,"乳品工程"}},
                {{"计算机科学与技术"}},
                {{"环境工程" ,"化学工程与工艺" ,"自动化","过程装备与控制工程","制药工程","应用化学"}},
                {{"临床医学" , "医学影像学","麻醉学" , "医学检验技术", "护理（专科）","康复治疗学","中医血泪"}},
                {{"给排水科学与工程" , "土木工程", "城乡规划"}},
                {{"新能源材料与器件"}},
                {{"工商管理" , "金融学" ,"行政管理" , "财务管理" ,"会计学" ,"经济学" , "信息管理与信息系统" ,"国际经济与贸易" , "电子商务" , "旅游管理"}},
                {{"生物技术" ,"生物工程" ,"环境生态工程"}},
                {{"水利水电工程" , "电气工程及其自动化"}},
                {{"地质工程" , "测绘工程" , "资源勘查工程"}},
                {{"材料类" , "机械类"}}
            };
        SettingDepartment rooyDepartment = new SettingDepartment();
        rooyDepartment.setName(school);
        rooyDepartment.setParentId("root");
        rooyDepartment.setParentIds("root");
        rooyDepartment.setCode("10");
        rooyDepartment.setId("0");
        rooyDepartment.setType("ROOT");
        rooyDepartment.setSort(0);
        settingDepartmentService.save(rooyDepartment);
        SettingDepartment schoolDepartment = new SettingDepartment();
        schoolDepartment.setName(school);
        schoolDepartment.setParentId("0");
        schoolDepartment.setParentIds("root,0");
        schoolDepartment.setCode("100");
        schoolDepartment.setType("SCHOOL");
        schoolDepartment.setSort(0);
        settingDepartmentService.save(schoolDepartment);
        for(int i=0 ;i<collage.length;i++){
            SettingDepartment collageDepartment = new SettingDepartment();
            collageDepartment.setName(collage[i]);
            collageDepartment.setParentId(schoolDepartment.getId());
            collageDepartment.setParentIds("root,0,"+schoolDepartment.getId());
            collageDepartment.setCode("100"+("00"+i));
            collageDepartment.setType("COLLEGE");
            collageDepartment.setSort(0);
            settingDepartmentService.save(collageDepartment);
            for(int y=0 ;y<series[i].length;y++){
                SettingDepartment seriesDepartment = new SettingDepartment();
                seriesDepartment.setName(series[i][y]);
                seriesDepartment.setParentId(collageDepartment.getId());
                seriesDepartment.setParentIds("root,0,"+schoolDepartment.getId()+","+collageDepartment.getId());
                seriesDepartment.setCode("100"+("00"+i)+(1000+i));
                seriesDepartment.setType("DEPARTMENT");
                seriesDepartment.setSort(0);
                settingDepartmentService.save(seriesDepartment);
                for(int k=0 ;k<specialty[i][y].length;k++){
                    SettingDepartment specialtyDepartment = new SettingDepartment();
                    specialtyDepartment.setName(specialty[i][y][k]);
                    specialtyDepartment.setParentId(seriesDepartment.getId());
                    specialtyDepartment.setParentIds("root,0,"+schoolDepartment.getId()+","+collageDepartment.getId()+","+seriesDepartment.getId());
                    specialtyDepartment.setCode("100"+("00"+i)+(1000+i)+(100+k));
                    specialtyDepartment.setType("MAJOR");
                    specialtyDepartment.setSort(k+1);
                    settingDepartmentService.save(specialtyDepartment);

                }
            }
        }

    }


}