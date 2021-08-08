package com.trms.services;

import java.time.LocalDate;

import com.trms.beans.User;

public interface UserService {
	User login(String name);

	boolean userExists(String username);

	User register(String username, String email, LocalDate birthday);

	User updateUser(User user);

}
