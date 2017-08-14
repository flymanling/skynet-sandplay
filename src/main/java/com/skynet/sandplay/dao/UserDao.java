package com.skynet.sandplay.dao;

import org.springframework.stereotype.Repository;

import com.skynet.sandplay.model.User;

@Repository("userDao")
public class UserDao extends BaseDaoImpl<User, String> {

}
