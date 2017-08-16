package com.skynet.sandplay.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.skynet.sandplay.dao.IBaseDao;
import com.skynet.sandplay.model.RoundPlay;

@Service("roundPlayService")
public class RoundPlayService  extends BaseServiceImpl<RoundPlay, String>{

	@Resource(name="roundPlayDao")
    public void setBaseDao(IBaseDao<RoundPlay, String> baseDao) {  
        super.setBaseDao(baseDao);
    }  

}
