package com.trms.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

import com.trms.beans.User;
import com.trms.beans.UserType;
import com.trms.data.UserDAOimpl;

public class UserServiceImpl implements UserService {
	
	public UserDAOimpl ud = new UserDAOimpl();
	private static Logger log = LogManager.getLogger(UserServiceImpl.class);

	@Override
	public User login(String username, String password) {
		User user = ud.getUser(username, password);
		return user;
	}

	@Override
	public boolean userExists(String username) {
		User u = ud.getUser(username);
		if (u==null) return true;
		else return false;
	}

	@Override
	public User register(String username, String password, String fname, String lname, String email,
			LocalDate startDate, UserType type, String department, String supervisorUsername) {
		User newUser = new User(username, password, fname,lname,email, type,department,supervisorUsername);
		
		return newUser;
	}

	@Override
	public User updateUser(User user) {
		ud.updateUserInfo(user);
		return user;
	}

}
