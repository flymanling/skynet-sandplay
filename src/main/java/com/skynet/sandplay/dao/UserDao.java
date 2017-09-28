package com.skynet.sandplay.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.skynet.sandplay.dao.interfaces.IUserDao;
import com.skynet.sandplay.model.RoundPlay;
import com.skynet.sandplay.model.User;

@Repository("userDao")
public class UserDao extends BaseDaoImpl<User, Integer> implements IUserDao{

	@Override
	public User getUserByName(String name) {
		String hql = "from user u where u.name=:name and grade=:grade";
		Query query = getSession().createQuery(hql);
		query.setString("name", name);
		query.setInteger("grade", RoundPlay.currentGrade);
		List<User> userList = query.list();
		if(userList != null && userList.size() > 0) {
			return userList.get(0);
		}
		return null;
	}

}
