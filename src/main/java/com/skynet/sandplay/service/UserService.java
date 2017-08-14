package com.skynet.sandplay.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.skynet.sandplay.dao.IBaseDao;
import com.skynet.sandplay.model.User;

@Service("userService")
public class UserService extends BaseServiceImpl<User, String> {

	@Resource(name="userDao")
    public void setBaseDao(IBaseDao<User, String> baseDao) {  
        super.setBaseDao(baseDao);
    }  
	
	
}
