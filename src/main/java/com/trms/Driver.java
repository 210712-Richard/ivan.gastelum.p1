package com.trms;

import com.trms.controllers.ReimbursementController;
import com.trms.controllers.ReimbursementControllerImpl;
import com.trms.controllers.UserController;
import com.trms.controllers.UserControllerImpl;
import com.trms.util.CassandraUtil;
import com.trms.util.DatabaseCreator;

import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// dbtest();
		runJavalin();
		//instantiateDatabase();
	}

	private static void runJavalin() {
		// Set up Jackson to serialize LocalDates and LocalDateTimes
		ObjectMapper jackson = new ObjectMapper();
		jackson.registerModule(new JavaTimeModule());
		jackson.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		JavalinJackson.configure(jackson);

		// Starts the Javalin framework
		Javalin app = Javalin.create().start(8080);

		UserController uc = new UserControllerImpl();
		ReimbursementController rc = new ReimbursementControllerImpl();

		// As a user, I can log in
		app.post("/users", uc::login);

		// As a user, I can register for an account
		app.put("/users/:username", uc::register);

		// As a user, I can log out
		app.delete("/users", uc::logout);
		
		//As a user, I can submit a Reimbursement Request
		app.post("/users/:username/reimbursement", rc::createReimbursement);
		
		//As a user, I can get a Reimbursement Request by id
		app.get("/users/:username/reimbursement/:id", rc::getReimbursement);
		
		//As a user, I can upload a presentation
		app.put("/users/:username/reimbursement/ppt", rc::uploadPPT);
		
		//As a user, I can upload an email message
		app.put("/users/:username/reimbursement/:id/msg", rc::uploadMsg);
		
		//As a supervisor, I can approve a Reimbursement request from one of my employees
		//app.put("/users/:username/reimbursement/:id/approval", rc::uploadMsg);
		
		//As a Head of Department, I can approve a Reimbursement request after the correct supervisor has approved it
		//app.put("/users/:username/reimbursement/:id/approval", rc::uploadMsg);
		
		//As a Benefits Coordinator, I can approve a Reimbursement request
		//app.put("/users/:username/reimbursement/:id/approval", rc::uploadMsg);
	}

	public static void dbtest() {
		CassandraUtil.getInstance().getSession();
	}

	public static void instantiateDatabase() {
		DatabaseCreator.dropTables();
		try {
			Thread.sleep(30000); // wait 30 seconds
		} catch (Exception e) {
			e.printStackTrace();
		}
		DatabaseCreator.createTables();
		try {
			Thread.sleep(70000); // wait 20 seconds
		} catch (Exception e) {
			e.printStackTrace();
		}
		DatabaseCreator.populateUserTable();
		DatabaseCreator.populateReimbursementTable();
		System.exit(0);
	}

}
