package com.skynet.sandplay.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.skynet.sandplay.dao.interfaces.IBaseDao;
import com.skynet.sandplay.dao.interfaces.IRoundEndDao;
import com.skynet.sandplay.model.RoundEnd;
import com.skynet.sandplay.service.interfaces.IRoundEndService;

@Service("roundEndService")
public class RoundEndService extends BaseServiceImpl<RoundEnd, Integer> implements IRoundEndService{

	
	@Resource(name="roundEndDao")
	private IRoundEndDao roundEndDao;
	
	@Resource(name="roundEndDao")
    public void setBaseDao(IBaseDao<RoundEnd, Integer> baseDao) {  
        super.setBaseDao(baseDao);
    }

	@Override
	public RoundEnd getRoundEndByRound(int userId, int round) {
		return roundEndDao.getRoundEndByRound(userId, round);
	}

}
