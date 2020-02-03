package com.mpri.aio.ranking.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mpri.aio.app.reg.mapper.SmSchoolmateProveMapper;
import com.mpri.aio.app.reg.model.SmSchoolmateProve;
import com.mpri.aio.common.utils.DateUtils;
import com.mpri.aio.common.utils.IdGen;
import com.mpri.aio.donation.mapper.DonRecordMapper;
import com.mpri.aio.donation.model.DonRecord;
import com.mpri.aio.friend.mapper.FriShareMapper;
import com.mpri.aio.friend.model.FriShare;
import com.mpri.aio.ranking.mapper.ChaDonMoneyMapper;
import com.mpri.aio.ranking.mapper.ChaDonTimeMapper;
import com.mpri.aio.ranking.mapper.ChaProveMapper;
import com.mpri.aio.ranking.mapper.ChaShareMapper;
import com.mpri.aio.ranking.model.ChaDonMoney;
import com.mpri.aio.ranking.model.ChaDonTime;
import com.mpri.aio.ranking.model.ChaProve;
import com.mpri.aio.ranking.model.ChaShare;
import com.mpri.aio.system.common.GlobalStr;

/**
 * 各个排名收集
 * @author syp
 *
 */
@Component
public class ChartRankingService {

	@Autowired
	private ChaDonMoneyMapper chaDonMoneyMapper;
	
	@Autowired
	private ChaDonTimeMapper chaDonTimeMapper;
	
	@Autowired
	private ChaShareMapper chaShareMapper;
	
	@Autowired
	private ChaProveMapper chaProveMapper;
	
	@Autowired
	private FriShareMapper friShareMapper;
	
	@Autowired
	private DonRecordMapper donRecordMapper;
	
	@Autowired
	private SmSchoolmateProveMapper smSchoolmateProveMapper;
	
    
    /**
     * 捐赠钱数
     */
    @Transactional(readOnly = false)
    public void excuteDonMoney() {
    	DonRecord record  = new DonRecord();
    	record.setState(GlobalStr.NORMAL_DON);
    	//获取今天日期
    	String now = DateUtils.getDate("yyyy-MM-dd");
    	record.setTime(DateUtils.parseDate(now));
    	List<Map> list = donRecordMapper.findCountByDay(record);
    	List<ChaDonMoney> chaDonMoneys = new ArrayList<ChaDonMoney>();
    	//转换为所需的对象
    	for (Map map : list) {
    		ChaDonMoney chaDonMoney = new ChaDonMoney();
    		chaDonMoney.setId(IdGen.uuid());
    		chaDonMoney.setUserId(String.valueOf(map.get("userId")));
    		chaDonMoney.setDonMoney(Double.valueOf(String.valueOf(map.get("donMoney"))));
    		chaDonMoney.setYear(DateUtils.getYear());
    		chaDonMoney.setMonth(DateUtils.getMonth());
    		chaDonMoneys.add(chaDonMoney);
		}
    	if(chaDonMoneys.size() > 0) {
        	chaDonMoneyMapper.insertBatch(chaDonMoneys);
    	}
    }
    
    /**
     * 捐赠次数
     */
    @Transactional(readOnly = false)
    public void excuteChaDonTime() {
    	DonRecord record  = new DonRecord();
    	record.setState(GlobalStr.NORMAL_DON);
    	//获取今天日期
    	String now = DateUtils.getDate("yyyy-MM-dd");
    	record.setTime(DateUtils.parseDate(now));
    	List<Map> list = donRecordMapper.findTimesByDay(record);
    	List<ChaDonTime> chaDonTimes = new ArrayList<ChaDonTime>();
    	//转换为所需的对象
    	for (Map map : list) {
    		ChaDonTime chaDonTime = new ChaDonTime();
    		chaDonTime.setId(IdGen.uuid());
    		chaDonTime.setUserId(String.valueOf(map.get("userId")));
    		chaDonTime.setDonTimes(Integer.valueOf(String.valueOf(map.get("donTimes"))));
    		chaDonTime.setYear(DateUtils.getYear());
    		chaDonTime.setMonth(DateUtils.getMonth());
    		chaDonTimes.add(chaDonTime);
		}
    	if(chaDonTimes.size() > 0) {
        	chaDonTimeMapper.insertBatch(chaDonTimes);
    	}
    }
    
    /**
     * 分享次数
     */
    @Transactional(readOnly = false)
    public void excuteChaShare() {
    	FriShare friShare = new FriShare();
    	//获取今天日期
    	String now = DateUtils.getDate("yyyy-MM-dd");
    	friShare.setCreateDate(DateUtils.parseDate(now));
    	List<Map> list = friShareMapper.findCountByDay(friShare);
    	List<ChaShare> chaShares = new ArrayList<ChaShare>();
    	//转换为所需的对象
    	for (Map map : list) {
    		ChaShare chaShare = new ChaShare();
    		chaShare.setId(IdGen.uuid());
    		chaShare.setUserId(String.valueOf(map.get("userId")));
    		chaShare.setShareTimes(Integer.valueOf(String.valueOf(map.get("shareTimes"))));
    		chaShare.setYear(DateUtils.getYear());
    		chaShare.setMonth(DateUtils.getMonth());
    		chaShares.add(chaShare);
		}
    	if(chaShares.size() > 0) {
        	chaShareMapper.insertBatch(chaShares);
    	}
    }
    
    /**
     * 认证次数
     */
    @Transactional(readOnly = false)
    public void excuteChaProve() {
    	SmSchoolmateProve smSchoolmateProve = new SmSchoolmateProve();
    	//获取今天日期
    	String now = DateUtils.getDate("yyyy-MM-dd");
    	smSchoolmateProve.setCreateDate(DateUtils.parseDate(now));
    	List<Map> list = smSchoolmateProveMapper.findCountByDay(smSchoolmateProve);
    	List<ChaProve> chaProves = new ArrayList<ChaProve>();
    	//转换为所需的对象
    	for (Map map : list) {
    		ChaProve chaProve = new ChaProve();
    		chaProve.setId(IdGen.uuid());
    		chaProve.setUserId(String.valueOf(map.get("userId")));
    		chaProve.setProTimes(Integer.valueOf(String.valueOf(map.get("proTimes"))));
    		chaProve.setYear(DateUtils.getYear());
    		chaProve.setMonth(DateUtils.getMonth());
    		chaProves.add(chaProve);
		}
    	if(chaProves.size() > 0) {
        	chaProveMapper.insertBatch(chaProves);
    	}
    }
    
    
    
    
    
}
