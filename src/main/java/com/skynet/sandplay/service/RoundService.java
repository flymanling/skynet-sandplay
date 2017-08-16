package com.skynet.sandplay.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.skynet.sandplay.dao.IBaseDao;
import com.skynet.sandplay.model.Round;

@Service("roundService")
public class RoundService extends BaseServiceImpl<Round, String>{

	@Resource(name="roundDao")
    public void setBaseDao(IBaseDao<Round, String> baseDao) {  
        super.setBaseDao(baseDao);
    }  
}
