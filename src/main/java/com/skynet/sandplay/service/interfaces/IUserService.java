package com.skynet.sandplay.service.interfaces;

import com.skynet.sandplay.model.User;

public interface IUserService extends IBaseService<User, Integer>{

	public User getUserByName(String name);

}
