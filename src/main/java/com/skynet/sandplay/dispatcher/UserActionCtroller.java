package com.skynet.sandplay.dispatcher;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.skynet.sandplay.annotation.ActionId;
import com.skynet.sandplay.annotation.ServiceId;
import com.skynet.sandplay.form.BaseMsg;
import com.skynet.sandplay.form.RoundForm;
import com.skynet.sandplay.model.Round;
import com.skynet.sandplay.model.RoundPlay;
import com.skynet.sandplay.model.RoundSet;
import com.skynet.sandplay.model.User;
import com.skynet.sandplay.service.interfaces.IRoundService;
import com.skynet.sandplay.service.interfaces.IRoundSetService;
import com.skynet.sandplay.service.interfaces.IUserService;
import com.skynet.sandplay.util.StringUtil;

@ServiceId(1)
@Component("userActionCtroller")
public class UserActionCtroller extends ActionCtroller{

	@Resource(name="userService")
	private IUserService userService;
	@Resource(name="roundService")
	private IRoundService roundService;
	@Resource(name="roundSetService")
	private IRoundSetService roundSetService;
	
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
		Round roundData = roundService.getRoundByRound(msg.userId, round>1?round-1:0);
		Round roundNow = roundService.getRoundByRound(msg.userId, round);
		map.put("round", roundData == null?new Round():roundData);
		map.put("roundNow", roundNow);
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
		Round oldRound = new Round();
		if(req.getRound() > 1) {
			oldRound = roundService.getRoundByRound(msg.userId, req.getRound()-1);
			if(oldRound == null) {
				return retFail("无法获取上一轮数据");
			}
			Round existRound = roundService.getRoundByRound(msg.userId, req.getRound());
			if(existRound != null && existRound.getStatus() == 1) {
				return retFail("当前轮已被锁定，无法更改");
			}
		} 
		
		RoundSet roundSet = roundSetService.getRoundSetByRound(req.getRound());
		if(roundSet == null) {
			return retFail("获取本轮单价数据失败");
		}
		
		RoundSet nextRoundSet = roundSetService.getRoundSetByRound(req.getRound()+1);
		Round round = null;
		if(req.getId() != null && req.getId() > 0) {
			//解锁后的提交
			round = roundService.get(req.getId());
			if(round == null) {
				return retFail("根据id找不到本轮数据");
			}
		} else {
			round = new Round();
		}
		
		String rs = roundService.saveRound(msg, req, round, oldRound, roundSet, nextRoundSet);
		
		return rs==null?retSuccess(round):retFail(rs);
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
		
		Round roundData = roundService.getRoundByRound(msg.userId, round);
		if(roundData != null) {
			roundData.setStatus(0);
			boolean rs = roundService.update(roundData);
			return retByRs(rs);
		} else {
			return retFail("找不到本轮提交数据");
		}
		
	}
	
}
