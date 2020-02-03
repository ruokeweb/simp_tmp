package com.mpri.aio.donation.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.donation.model.DonProjectTogether;


 /**   
 *  
 * @Description:  一起捐记录——DAO
 * @Author:       lzq
 * @project 	  simp 
 * @CreateDate:   Mon May 27 17:54:56 CST 2019
 * @Version:      v_1.2
 *    
 */
@Mapper
public interface DonProjectTogetherMapper extends CrudMapper<DonProjectTogether>{

  /**
   * 根据订单编号修改订单支付状态
   * <p>Title: updateBycustomid</p>
   * <p>Description: </p>
   * @param donProjdectTogether
   */
  void updateStatus (DonProjectTogether donProjdectTogether);
}