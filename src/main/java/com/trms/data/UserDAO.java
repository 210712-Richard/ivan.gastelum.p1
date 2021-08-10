package com.trms.data;

import com.trms.beans.User;

public interface UserDAO {
	//Used for log in
	User getUser(String username, String password);
	
	//Used to check if username exists
	boolean getUser(String username);
	
	void registerUser(User u);
	
	void updateUserInfo(User u);
}
