package com.skynet.sandplay.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.skynet.sandplay.dao.interfaces.IBaseDao;
import com.skynet.sandplay.dao.interfaces.IRoundDao;
import com.skynet.sandplay.form.BaseMsg;
import com.skynet.sandplay.form.RoundForm;
import com.skynet.sandplay.model.Round;
import com.skynet.sandplay.model.RoundSet;
import com.skynet.sandplay.service.interfaces.ILoanService;
import com.skynet.sandplay.service.interfaces.IRoundService;

@Service("roundService")
public class RoundService extends BaseServiceImpl<Round, Integer> implements IRoundService{

	@Resource(name="roundDao")
	private IRoundDao roundDao;
	
	@Resource(name="loanService")
	private ILoanService loanService;
	
	@Resource(name="roundDao")
    public void setBaseDao(IBaseDao<Round, Integer> baseDao) {  
        super.setBaseDao(baseDao);
    }

	@Override
	public Round getRoundByRound(int userId, int round) {
		return roundDao.getRoundByRound(userId, round);
	}

	@Override
	public String saveRound(BaseMsg msg, RoundForm req, Round round,
			Round oldRound, RoundSet roundSet, RoundSet nextRoundSet) {

		//计算模型
		String errMsg = new RoundModel(round, oldRound, roundSet, nextRoundSet, req, msg, loanService).process();
		if(errMsg != null) {
			return errMsg;
		}
		if(round.getCash() <0) {
			return "你的现金余额不足";
		}
		
		if(req.getView() > 0) {//预览
			return null;
		}
		
		if(req.getId() != null && req.getId() > 0) {
			boolean rs = update(round);
			return rs ? null:"操作失败";
		} else {
			Integer id = save(round);
			if(id != null && id>0) {
				return null;
			} else{
				return "操作失败";
			}
		}
	}  
	
	
}
