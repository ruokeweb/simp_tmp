package com.mpri.aio.app.index.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpri.aio.act.model.ActActivity;
import com.mpri.aio.app.act.service.AppActService;
import com.mpri.aio.app.act.vo.BannerActsVo;
import com.mpri.aio.app.don.service.AppDonService;
import com.mpri.aio.app.index.mapper.IndexMapper;
import com.mpri.aio.app.index.vo.BannerVo;
import com.mpri.aio.app.index.vo.IndexInfoVo;
import com.mpri.aio.app.index.vo.News;
import com.mpri.aio.app.index.vo.TreeColumn;
import com.mpri.aio.app.reg.service.RegistService;
import com.mpri.aio.app.wish.mapper.AppWishMapper;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.donation.model.DonProject;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import com.mpri.aio.schoolmate.model.SmWish;
import com.mpri.aio.settings.model.SettingWebvsb;
import com.mpri.aio.settings.service.SettingWebvsbService;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.shiro.JWTUtil;
import com.mpri.aio.untils.activiti.utils.ActivitiUtils;

/**
 * 首页相关service
 * @author Administrator
 *
 */
@Service
public class IndexService {

	@Autowired
	private ActivitiUtils activitiUtils;
	
	@Autowired
	private ProcessEngine processEngine;

	@Autowired
	private IndexMapper indexMapper;

	@Autowired
	private AppWishMapper appWishMapper;
	
    @Autowired
    private AppActService appActService;
    
    @Autowired
    private AppDonService donService;
    
    @Autowired
    private RegistService registService;
    
	@Autowired
	private SettingWebvsbService settingWebvsbService;
	
	@Autowired
	private WebberVsbService webberVsbService;
	
    public static final String BANNER_DON ="DON";
    public static final String BANNER_ACT ="ACT";
    
	/**
	 * @Desc 获取当前用户的卡状态
	 * @param request
	 * @return
	 */
	public Map<String,String> getMyCard(HttpServletRequest request){
		String userId = JWTUtil.getUserId(request.getHeader("Authorization"));
		Map<String,String> map = new HashMap<String,String>();
		SmSchoolmate schoolmate = new SmSchoolmate();
		schoolmate.setUserId(userId);
		schoolmate = registService.getCardStatusByUserId(schoolmate);
		if(GlobalStr.NORMAL_CARD_STATUS.equalsIgnoreCase(schoolmate.getCardStatus())) {
        	map.put("pages", GlobalStr.CARD_STATUS_SUCCES_PAGE);
        	map.put("resultContent","审核校友卡成功");
        	return map;
		}
		TaskService taskService = processEngine.getTaskService();
		List<Task> list = activitiUtils.findWillsByAssignee(GlobalStr.REG_WORKFLOW_NAME, userId);
		if(list.size() != 0) {
			Task task = list.get(0);
        	Object obj = taskService.getVariable(task.getId(), "resultCon");
        	String resultContent = obj == null ? "":(String) obj;
        	map.put("pages", task.getCategory());
        	map.put("resultContent",resultContent);
		}
		return map;
	}

    public Integer getCollarCardNum(SmSchoolmate smSchoolmate) {
		return indexMapper.getCollarCardNum(smSchoolmate);
    }

	public List<IndexInfoVo> getCollarCardList(SmSchoolmate smSchoolmate,Integer pageSize) {
		return indexMapper.getCollarCardList(smSchoolmate,pageSize);
	}

	public List<SmWish> getThankslist(SmWish smWish, Integer pageSize) {
		return appWishMapper.getThankslist(smWish,pageSize);
	}
	
	public List<BannerVo> getIndexBanners(int pageNo,int pageSize){
		DonProject donProject =new DonProject();
		PageIo<DonProject> bannerDonProjects =  donService.donProList(pageNo,pageSize,donProject);
		ActActivity actActivity = new ActActivity();
		PageIo<BannerActsVo> bannerActs = appActService.getBannerActs(pageNo, pageSize, actActivity);
		List<BannerVo> list = new ArrayList<BannerVo>();
		for (DonProject project : bannerDonProjects.getList()) {
			BannerVo vo = new BannerVo();
			vo.setId(project.getId());
			vo.setPic(project.getPic());
			vo.setTittle(project.getName());
			vo.setType(BANNER_DON);
			list.add(vo);
		}
		for (BannerActsVo actVo : bannerActs.getList()) {
			BannerVo vo = new BannerVo();
			vo.setId(actVo.getId());
			vo.setPic(actVo.getImage());
			vo.setTittle(actVo.getName());
			vo.setType(BANNER_ACT);
			list.add(vo);
		}		
		return list;
	}
	
	/**
	 * 获取首页的新闻列表
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public List<News> getIndexNews(int pageNo,int pageSize,SettingWebvsb settingWebvsb) throws ClientProtocolException, IOException{
		List<News> news = new ArrayList<News>();
		List<SettingWebvsb> list = settingWebvsbService.loadAllListBy(settingWebvsb);
		for (SettingWebvsb vsb : list) {
			TreeColumn trColumn = new TreeColumn();
			trColumn.setTreeid(Integer.valueOf(vsb.getTreeId()));
			trColumn.setViewid(Integer.valueOf(vsb.getViewId()));
			news  = webberVsbService.getNewsByTree(trColumn, (pageNo-1)*pageSize, pageSize);
			return news;
		}
		return news;
	}
	
	/**
	 * 获取新闻详情
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public News getNewsDetail(String id) throws ParseException, IOException {
		return webberVsbService.getNewsDetail(id);
	}
	
	/**
	 * 获取推荐文章
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public List<News> getrelationNews(News news,int start,int count) throws ClientProtocolException, IOException{
		return webberVsbService.getrelationNews(news, start, count);
	}
}
