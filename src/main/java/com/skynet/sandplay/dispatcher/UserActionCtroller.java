package com.skynet.sandplay.dispatcher;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.skynet.sandplay.annotation.ActionId;
import com.skynet.sandplay.annotation.ServiceId;
import com.skynet.sandplay.form.BaseMsg;
import com.skynet.sandplay.form.RoundForm;
import com.skynet.sandplay.model.RoundEnd;
import com.skynet.sandplay.model.RoundPlay;
import com.skynet.sandplay.model.RoundSet;
import com.skynet.sandplay.model.RoundStart;
import com.skynet.sandplay.model.User;
import com.skynet.sandplay.service.RoundModel;
import com.skynet.sandplay.service.interfaces.ILoanService;
import com.skynet.sandplay.service.interfaces.IRoundEndService;
import com.skynet.sandplay.service.interfaces.IRoundSetService;
import com.skynet.sandplay.service.interfaces.IRoundStartService;
import com.skynet.sandplay.service.interfaces.IUserService;
import com.skynet.sandplay.util.StringUtil;

@ServiceId(1)
@Component("userActionCtroller")
public class UserActionCtroller extends ActionCtroller{

	@Resource(name="userService")
	private IUserService userService;
	@Resource(name="roundEndService")
	private IRoundEndService roundEndService;
	@Resource(name="roundStartService")
	private IRoundStartService roundStartService;
	@Resource(name="roundSetService")
	private IRoundSetService roundSetService;
	@Resource(name="loanService")
	private ILoanService loanService;
	
	@ActionId(1)
	public String getUser(BaseMsg msg) {
//		String id = "402881e65de12d3f015de12d41a00000";
		Integer id = msg.getAsInt("id", 0);
		if(id == 0) {
			return "id not found";
		}
		User user = userService.get(id);
		return retSuccess(user);
	}
	
	@ActionId(2)
	public String getCurrentStep(BaseMsg msg) {
		int round = msg.getAsInt("round", 0);
		if(round == 0) {
			return retFail("参数错误");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		RoundSet roundSet = roundSetService.getRoundSetByRound(round);
		RoundSet oldRoundSet = roundSetService.getRoundSetByRound(round-1);
		RoundStart roundStart = roundStartService.getRoundStartByRound(msg.userId, round);
		if(roundStart == null) {
			roundStart = new RoundStart();
		}
		RoundEnd roundEnd = roundEndService.getRoundEndByRound(msg.userId, round);
		if(roundEnd == null) {
			//roundEnd = new RoundEnd();
			//roundEnd.setCash(roundStart.getCash() + roundStart.getSurplus());
		}
		map.put("roundStart", roundStart);
		map.put("roundEnd", roundEnd);
		map.put("roundSet", roundSet);
		map.put("oldRoundSet", oldRoundSet);
		map.put("currentRound", RoundPlay.currentRound);
		return retSuccess(map);
	}
	
	@ActionId(3)
	public String saveUser(BaseMsg msg) {
		String name = msg.getAsString("name");
		User user = userService.getUserByName(name);
		if(user != null) {
			msg.req.getSession().setAttribute("user", user);
			return retSuccess("用户名已存在");
		} 
		
		user = new User();
		user.setGrade(RoundPlay.currentGrade);
		user.setName(name);
		Integer id = userService.save(user);
		if(id != null && id>0) {
			msg.req.getSession().setAttribute("user", user);
			return retSuccess();
		}
		return retFail("操作失败");
	}
	
	@ActionId(4)
	public String getUserBySession(BaseMsg msg) {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = (User) msg.req.getSession().getAttribute("user");
		return retSuccess(user);
	}
	
	@ActionId(5)
	public String logoutUser(BaseMsg msg) {
		msg.req.getSession().removeAttribute("user");
		return retSuccess();
	}
	
	@ActionId(6)
	public String saveRound(BaseMsg msg) {
		RoundForm req = gson.fromJson(msg.content, RoundForm.class);
		RoundStart roundStart = new RoundStart();
		roundStart.setUserId(msg.userId);
		roundStart.setRound(1);
		roundStart.setUserName(msg.userName);
		if(req.getRound() > 1) {
			roundStart = roundStartService.getRoundStartByRound(msg.userId, req.getRound());
			if(roundStart == null) {
				return retFail("无法获取本轮期初数据");
			}
			RoundEnd roundEnd = roundEndService.getRoundEndByRound(msg.userId, req.getRound());
			if(roundEnd != null && roundEnd.getStatus() == 1) {
				return retFail("当前轮已被锁定，无法更改");
			}
		} 
		
		RoundSet roundSet = roundSetService.getRoundSetByRound(req.getRound());
		if(roundSet == null) {
			return retFail("获取本轮单价数据失败");
		}
		
		RoundSet nextRoundSet = roundSetService.getRoundSetByRound(req.getRound()+1);
		RoundEnd roundEnd = null;
		if(req.getId() != null && req.getId() > 0) {
			//解锁后的提交
			roundEnd = roundEndService.get(req.getId());
			if(roundEnd == null) {
				return retFail("根据id找不到本轮数据");
			}
		} else {
			roundEnd = new RoundEnd();
		}
		
		RoundStart nextRoundStart = roundStartService.getRoundStartByRound(msg.userId, req.getRound() + 1);
		if(nextRoundStart == null) {
			nextRoundStart = new RoundStart();
			nextRoundStart.setUserId(msg.userId);
			nextRoundStart.setRound(req.getRound()+1);
			nextRoundStart.setGrade(RoundPlay.currentGrade);
		}
		
		String rs = saveRound(msg, req, roundStart, roundEnd, nextRoundStart, roundSet, nextRoundSet);
		
		return rs==null?retSuccess(roundEnd):retFail(rs);
	}
	
	public String saveRound(BaseMsg msg, RoundForm req, RoundStart roundStart,RoundEnd roundEnd, RoundStart nextRoundStart,
			 RoundSet roundSet, RoundSet nextRoundSet) {

		//计算模型
		String errMsg = new RoundModel(roundStart, roundEnd, nextRoundStart,roundSet, nextRoundSet, req, msg, loanService).process();
		if(errMsg != null) {
			return errMsg;
		}
		if(roundEnd.getCash() <0) {
			return "你的现金余额不足，现金余额：" + roundEnd.getCash();
		}
		
		if(req.getView() > 0) {//预览
			return null;
		}
		
		if(req.getId() != null && req.getId() > 0) {
			boolean rs = roundEndService.update(roundEnd);
			boolean rs1 = roundStartService.update(nextRoundStart);
			return rs && rs1 ? null:"操作失败";
		} else {
			Integer id = roundEndService.save(roundEnd);
			Integer id1 = roundStartService.save(nextRoundStart);
			if(id != null && id>0 && id1 != null && id1 > 0) {
				return null;
			} else{
				return "操作失败";
			}
		}
	}  

	
	@ActionId(7)
	public String unlock(BaseMsg msg) {
		String pwd = msg.getAsString("pwd", null);
		if(!StringUtil.isNotEmpty(pwd) || !pwd.equals(RoundPlay.lockPwd)) {
			return retFail("密码错误");
		}
		
		int round = msg.getAsInt("round", 0);
		if(round < 1) {
			return retFail("获取轮次失败");
		}
		
		RoundEnd roundEnd = roundEndService.getRoundEndByRound(msg.userId, round);
		if(roundEnd != null) {
			roundEnd.setStatus(0);
			boolean rs = roundEndService.update(roundEnd);
			return retByRs(rs);
		} else {
			return retFail("找不到本轮提交数据");
		}
		
	}
	
	
}
