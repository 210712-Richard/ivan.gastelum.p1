package com.trms.controllers;

import io.javalin.http.Context;

public interface UserController {
	
	public void login(Context ctx);
	
	public void register(Context ctx);
	
	public void logout(Context ctx);
	
	public void viewUserInfo(Context ctx);
}
