package com.mpri.aio.app.mine.vo;

import com.mpri.aio.schoolmate.model.SmSchoolmate;

/**
 * @Description TODO
 * @Date 2019/11/12 13:31
 * @Created by lzq  com.mpri.aio.app.mine.vo.CardRankingVo
 */
public class AppCardRankingVo {
    private String cardNo;
    private String status;
    private Integer ranking;
    private String jointCardNo;
    private String cardId;
    private String userId;
    private SmSchoolmate smSchoolmate;
    private Integer perNum;  //领卡前后的人数
    private Integer max;
    private Integer min;
    private int startNum;
    private int endNum;

    public AppCardRankingVo() {
        //默认查询前三名
        this.startNum = 0;
        this.endNum = 3;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getJointCardNo() {
        return jointCardNo;
    }

    public void setJointCardNo(String jointCardNo) {
        this.jointCardNo = jointCardNo;
    }

    public SmSchoolmate getSmSchoolmate() {
        return smSchoolmate;
    }

    public void setSmSchoolmate(SmSchoolmate smSchoolmate) {
        this.smSchoolmate = smSchoolmate;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Integer getPerNum() {
        return perNum;
    }

    public void setPerNum(Integer perNum) {
        this.perNum = perNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
