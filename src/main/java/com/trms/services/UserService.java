package com.trms.services;

import java.time.LocalDate;

import com.trms.beans.User;
import com.trms.beans.UserType;

public interface UserService {
	User login(String name, String password);

	boolean userExists(String username);

	User register(String username, String password, String fname, String lname, 
			String email, LocalDate startDate, UserType type, String department, 
			String supervisorUsername);

	User updateUser(User user);

}
