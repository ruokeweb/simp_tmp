package com.mpri.aio.app.mine.vo;

import com.mpri.aio.ranking.model.ChaDonTime;
import com.mpri.aio.schoolmate.model.SmSchoolmate;

/**
 * @Description TODO
 * @Date 2019/11/12 13:31
 * @Created by lzq  com.mpri.aio.app.mine.vo.CardRankingVo
 */
public class AppChaDonTimeVo extends ChaDonTime{
    private SmSchoolmate smSchoolmate;
    private Integer perNum;  //领卡前后的人数
    private Integer max;
    private Integer min;
    private Integer ranking;
    private int startNum;
    private int endNum;

    public AppChaDonTimeVo() {
        //默认查询前三名
        this.startNum = 0;
        this.endNum = 3;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public int getStartNum() {
        return startNum;
    }

    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }

    public int getEndNum() {
        return endNum;
    }

    public void setEndNum(int endNum) {
        this.endNum = endNum;
    }

    public SmSchoolmate getSmSchoolmate() {
        return smSchoolmate;
    }

    public void setSmSchoolmate(SmSchoolmate smSchoolmate) {
        this.smSchoolmate = smSchoolmate;
    }

    public Integer getPerNum() {
        return perNum;
    }

    public void setPerNum(Integer perNum) {
        this.perNum = perNum;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }
}
