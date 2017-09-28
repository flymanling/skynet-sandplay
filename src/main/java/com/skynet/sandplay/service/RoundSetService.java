package com.skynet.sandplay.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.skynet.sandplay.dao.interfaces.IBaseDao;
import com.skynet.sandplay.dao.interfaces.IRoundSetDao;
import com.skynet.sandplay.model.RoundSet;
import com.skynet.sandplay.service.interfaces.IRoundSetService;

@Service("roundSetService")
public class RoundSetService extends BaseServiceImpl<RoundSet, Integer> implements IRoundSetService{

	@Resource(name="roundSetDao")
	public IRoundSetDao roundSetDao;
	
	@Resource(name="roundSetDao")
    public void setBaseDao(IBaseDao<RoundSet, Integer> baseDao) {  
        super.setBaseDao(baseDao);
    }

	@Override
	public RoundSet getRoundSetByRound(int round) {
		return roundSetDao.getRoundSetByRound(round);
	}  
}
