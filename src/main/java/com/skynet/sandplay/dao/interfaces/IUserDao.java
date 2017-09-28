package com.skynet.sandplay.dao.interfaces;

import com.skynet.sandplay.model.User;

public interface IUserDao extends IBaseDao<User, Integer>{

	public User getUserByName(String name);
}
