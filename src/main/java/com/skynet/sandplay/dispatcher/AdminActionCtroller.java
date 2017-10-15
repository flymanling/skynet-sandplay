package com.skynet.sandplay.dispatcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.skynet.sandplay.annotation.ActionId;
import com.skynet.sandplay.annotation.ServiceId;
import com.skynet.sandplay.form.BaseMsg;
import com.skynet.sandplay.model.RoundPlay;
import com.skynet.sandplay.model.RoundSet;
import com.skynet.sandplay.service.QuerySettable;
import com.skynet.sandplay.service.interfaces.IRoundSetService;
import com.skynet.sandplay.util.NumUtil;

@ServiceId(2)
@Component("adminActionCtroller")
public class AdminActionCtroller extends ActionCtroller {
	
	@Resource(name="roundSetService")
	private IRoundSetService roundSetService;
	
	@ActionId(1)
	public String setCurrentStep(BaseMsg msg) {
		int step = msg.getAsInt("step", -1);
		if(step>=0) {
			RoundPlay.currentRound = step;
		}
		int grade = msg.getAsInt("grade", 0);
		if(grade > 0) {
			RoundPlay.currentGrade = grade;
		}
		String lockPwd = msg.getAsString("lockPwd", null);
		if(lockPwd != null) {
			RoundPlay.lockPwd = lockPwd;
		}
		return retSuccess();
	}
	
	@ActionId(2)
	public String addRoundSet(BaseMsg msg) {
		RoundSet roundSet = gson.fromJson(msg.content, RoundSet.class);
		if(roundSet.getRound() > 1) {
			//计算涨幅
			RoundSet oldRoundSet = roundSetService.getRoundSetByRound(roundSet.getRound()-1);
			if(oldRoundSet != null) {
				doChange(oldRoundSet, roundSet);
			}
		}
		if(roundSet.getId() != 0) {
			boolean rs = roundSetService.update(roundSet);
			if(rs) {
				RoundSet nextRoundSet = roundSetService.getRoundSetByRound(roundSet.getRound() + 1);
				if(nextRoundSet != null) {
					doChange(roundSet, nextRoundSet);
					roundSetService.update(nextRoundSet);
				}
			}
			return retByRs(rs);
		} else {
			Integer id = roundSetService.save(roundSet); 
			if(id != null && id > 0) {
				RoundSet nextRoundSet = roundSetService.getRoundSetByRound(roundSet.getRound() + 1);
				if(nextRoundSet != null) {
					doChange(roundSet, nextRoundSet);
					roundSetService.update(nextRoundSet);
				}
			}
			return retSuccess(id);
		}
	}
	
	/**
	 * 计算价格涨幅
	 * @param oldRoundSet
	 * @param roundSet
	 */
	private void doChange(RoundSet oldRoundSet, RoundSet roundSet) {
		if(oldRoundSet.getInsurePrice() > 0) {
			roundSet.setInsurePriceChange(NumUtil.getDoubleFormat(
					(roundSet.getInsurePrice()-oldRoundSet.getInsurePrice())
					/oldRoundSet.getInsurePrice()));
		}
		if(oldRoundSet.getBankPrice() > 0) {
			roundSet.setBankPriceChange(NumUtil.getDoubleFormat(
					(roundSet.getBankPrice()-oldRoundSet.getBankPrice())
					/oldRoundSet.getBankPrice()));
		}
		if(oldRoundSet.getGoldPrice() > 0) {
			roundSet.setGoldPriceChange(NumUtil.getDoubleFormat(
					(roundSet.getGoldPrice()-oldRoundSet.getGoldPrice())
					/oldRoundSet.getGoldPrice()));
		}
		if(oldRoundSet.getGoldPrice() > 0) {
			roundSet.setGoldPriceChange(NumUtil.getDoubleFormat(
					(roundSet.getGoldPrice()-oldRoundSet.getGoldPrice())
					/oldRoundSet.getGoldPrice()));
		}
		if(oldRoundSet.getHkPrice() > 0) {
			roundSet.setHkPriceChange(NumUtil.getDoubleFormat(
					(roundSet.getHkPrice()-oldRoundSet.getHkPrice())
					/oldRoundSet.getHkPrice()));
		}
		if(oldRoundSet.getEtfPrice() > 0) {
			roundSet.setEtfPriceChange(NumUtil.getDoubleFormat(
					(roundSet.getEtfPrice()-oldRoundSet.getEtfPrice())
					/oldRoundSet.getEtfPrice()));
		}
		if(oldRoundSet.getPufaPrice() > 0) {
			roundSet.setPufaPriceChange(NumUtil.getDoubleFormat(
					(roundSet.getPufaPrice()-oldRoundSet.getPufaPrice())
					/oldRoundSet.getPufaPrice()));
		}
		if(oldRoundSet.getTrustPrice() > 0) {
			roundSet.setTrustPriceChange(NumUtil.getDoubleFormat(
					(roundSet.getTrustPrice()-oldRoundSet.getTrustPrice())
					/oldRoundSet.getTrustPrice()));
		}
		if(oldRoundSet.getHousePrice() > 0) {
			roundSet.setHousePriceChange(NumUtil.getDoubleFormat(
					(roundSet.getHousePrice()-oldRoundSet.getHousePrice())
					/oldRoundSet.getHousePrice()));
		}
		if(oldRoundSet.getLandPrice() > 0) {
			roundSet.setLandPriceChange(NumUtil.getDoubleFormat(
					(roundSet.getLandPrice()-oldRoundSet.getLandPrice())
					/oldRoundSet.getLandPrice()));
		}
		
	}
	
	@ActionId(3)
	public String getRoundSetList(BaseMsg msg) {
		List<RoundSet> list = roundSetService.list("from roundset r order by r.round", null);
		return retSuccess(list);
	}
	
	@ActionId(4)
	public String getCurrentStep(BaseMsg msg) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currentStep", RoundPlay.currentRound);
		map.put("currentGrade", RoundPlay.currentGrade);
		map.put("lockPwd", RoundPlay.lockPwd);
		return retSuccess(map);
	}
	
	@ActionId(5)
	public String deleteRoundSet(BaseMsg msg) {
		Integer id = msg.getAsInt("id", 0);
		if(id > 0) {
			boolean rs = roundSetService.delete(id);
			return retByRs(rs);
		} 
		return retFail("操作失败");
	}
}
