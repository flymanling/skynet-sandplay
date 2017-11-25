package com.skynet.sandplay.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.skynet.sandplay.dao.interfaces.IBaseDao;
import com.skynet.sandplay.dao.interfaces.IRoundStartDao;
import com.skynet.sandplay.model.RoundStart;
import com.skynet.sandplay.service.interfaces.IRoundStartService;


@Service("roundStartService")
public class RoundStartService extends BaseServiceImpl<RoundStart, Integer> implements IRoundStartService{

	@Resource(name="roundStartDao")
	private IRoundStartDao roundStartDao;
	
	@Resource(name="roundStartDao")
    public void setBaseDao(IBaseDao<RoundStart, Integer> baseDao) {  
        super.setBaseDao(baseDao);
    }
	
	@Override
	public RoundStart getRoundStartByRound(int userId, int round) {
		return roundStartDao.getRoundStartByRound(userId, round);
	}
}
