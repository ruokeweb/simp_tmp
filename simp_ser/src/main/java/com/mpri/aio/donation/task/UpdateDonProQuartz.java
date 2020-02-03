package com.mpri.aio.donation.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mpri.aio.donation.model.DonProject;
import com.mpri.aio.donation.service.DonProjectService;
import com.mpri.aio.system.common.GlobalStr;

/**
 * 修改捐赠项目的quartz
 * <p>
 * Title: UpdateDonProQuartz
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author syp
 * @date 2018年9月25日
 */
@Component
public class UpdateDonProQuartz {

	@Autowired
	private DonProjectService donProjectService;


	/**
	 * 判断日期
	 * <p>
	 * Title: judgeDate
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 */
	public static String judgeDate(DonProject pro) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = null;
		Date startDate = null;
		Date currtDate = null;
		try {
			currtDate = sdf.parse(sdf.format(new Date()));
			if (null != pro.getEnddate()) {
				endDate = sdf.parse(sdf.format(pro.getEnddate()));
			}
			if(null != pro.getStartdate()) {
				startDate = sdf.parse(sdf.format(pro.getStartdate()));
			}
			if (null == pro.getEnddate()) {
				return GlobalStr.DON_BEDOING;
			} else if (null!=endDate && endDate.before(currtDate)) {
				return GlobalStr.DON_HASDONE;
			} else if (null !=pro.getStartdate()  && currtDate.before(startDate)) {
				return GlobalStr.DON_WILL;
			} else if (null !=pro.getStartdate() && null!=startDate && startDate.before(currtDate) && currtDate.before(endDate)) {
				return GlobalStr.DON_BEDOING;
			}
		} catch (ParseException e) {
			return GlobalStr.DON_BEDOING;
		}
		return GlobalStr.DON_BEDOING;
	}

}
