package com.skynet.sandplay.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.skynet.sandplay.dao.interfaces.IBaseDao;
import com.skynet.sandplay.dao.interfaces.IUserDao;
import com.skynet.sandplay.model.User;
import com.skynet.sandplay.service.interfaces.IUserService;

@Service("userService")
public class UserService extends BaseServiceImpl<User, Integer> implements IUserService {

	@Resource(name="userDao")
	private IUserDao userDao;
	
	@Resource(name="userDao")
    public void setBaseDao(IBaseDao<User, Integer> baseDao) {  
        super.setBaseDao(baseDao);
    }

	@Override
	public User getUserByName(String name) {
		return userDao.getUserByName(name);
	}  
	
	
}
