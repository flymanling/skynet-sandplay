package com.skynet.sandplay.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.skynet.sandplay.dao.IBaseDao;
import com.skynet.sandplay.model.RoundSet;

@Service("roudSetService")
public class RoundSetService extends BaseServiceImpl<RoundSet, String> {

	@Resource(name="roundSetDao")
    public void setBaseDao(IBaseDao<RoundSet, String> baseDao) {  
        super.setBaseDao(baseDao);
    }  
}
