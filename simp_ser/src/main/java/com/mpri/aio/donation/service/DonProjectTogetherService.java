package com.mpri.aio.donation.service;

import org.springframework.stereotype.Service;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.donation.model.DonProjectTogether;
import com.mpri.aio.donation.mapper.DonProjectTogetherMapper;


/**
 * @Description: 一起捐记录——Service
 * @Author: lzq
 * @project simp
 * @CreateDate: Mon May 27 17:54:56 CST 2019
 * @Version: v_1.2
 */

@Service
public class DonProjectTogetherService extends CrudService<DonProjectTogetherMapper, DonProjectTogether> {

    public void updateStatus(DonProjectTogether donProjectTogether) {
        this.mapper.updateStatus(donProjectTogether);
    }


}