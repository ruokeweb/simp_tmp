package com.mpri.aio.untils.task.job;

import com.mpri.aio.schoolmate.model.SmSchoolmate;
import com.mpri.aio.schoolmate.service.SchoolmateMergeService;
import com.mpri.aio.schoolmate.service.SmSchoolmateService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 合并相同校友
 */
@Component(value="MergeSmJob")
public class MergeSmJob implements Job {
    @Autowired
    private SchoolmateMergeService schoolmateMergeService;
    @Autowired
    private SmSchoolmateService schoolmateService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {


        //获取所有校友
		List<SmSchoolmate> smSchoolmates =new ArrayList<>();
		//将第一条取出 判断是否有相同校友
		this.deleteSchoolmate(smSchoolmates,smSchoolmates.size());
    }
    //删除并合并校友
    public  void deleteSchoolmate(List<SmSchoolmate> smSchoolmates , int nowNum ){
        if(nowNum<=1){
            return;
        }
        SmSchoolmate nowSchoolmate = smSchoolmates.get(nowNum-1);
        List<SmSchoolmate> listCompare = schoolmateService.getListCompareByUserId(nowSchoolmate);
        schoolmateMergeService.mergeSchoomates(listCompare,nowSchoolmate);
        for (int j = 0 ;j<listCompare.size() ;j++){
            for(int i=nowNum-2;i>=0;i--)
            {
                //查出和第一条相同的校友
                if(listCompare.get(j).getId().equals(smSchoolmates.get(i).getId())){
                    smSchoolmates.remove(smSchoolmates.get(i));
                }
            }
        }
        smSchoolmates.remove(nowSchoolmate);
        this.deleteSchoolmate(smSchoolmates,smSchoolmates.size());
    }
}
