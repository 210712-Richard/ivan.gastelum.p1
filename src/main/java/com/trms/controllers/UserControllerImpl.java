package com.trms.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.javalin.http.Context;

import com.trms.beans.User;
import com.trms.services.UserServiceImpl;


public class UserControllerImpl implements UserController {
	
	private static Logger log = LogManager.getLogger(UserController.class);
	private UserServiceImpl us = new UserServiceImpl();
	
	@Override
	public void login(Context ctx) {
		// TODO Auto-generated method stub
		User u = ctx.bodyAsClass(User.class);
		log.debug(u.getPassword());
		u = us.login(u.getUsername(), u.getPassword());
		log.debug(u);
		
		if(u!=null) {
			ctx.sessionAttribute("loggedUser", u);
			ctx.json(u);
			return;
		}
		ctx.status(401);
	}
	@Override
	public void register(Context ctx) {
		
		User u = ctx.bodyAsClass(User.class);

		if(!us.userExists(u.getUsername())) {
			User newUser = us.register(u.getUsername(), u.getPassword(), u.getFname(), u.getLname(), u.getEmail(), u.getStartDate(), u.getType(), u.getDepartment(), u.getSupervisorUsername());
			ctx.status(201);
			ctx.json(newUser);
		} else {
			ctx.status(409);
			ctx.html("Username already taken.");
		}
		
	}
	@Override
	public void logout(Context ctx) {
		ctx.req.getSession().invalidate();
		ctx.status(204);
	}
	@Override
	public void viewUserInfo(Context ctx) {
		// TODO Auto-generated method stub
		
	}
	

}
