package com.skynet.sandplay.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.skynet.sandplay.dao.interfaces.IBaseDao;
import com.skynet.sandplay.model.RoundPlay;

@Service("roundPlayService")
public class RoundPlayService  extends BaseServiceImpl<RoundPlay, Integer>{

	@Resource(name="roundPlayDao")
    public void setBaseDao(IBaseDao<RoundPlay, Integer> baseDao) {  
        super.setBaseDao(baseDao);
    }  

}
