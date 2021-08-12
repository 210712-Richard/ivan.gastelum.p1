package com.trms;

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
		//dbtest();
		instantiateDatabase();
		runJavalin();
	}
	
	private static void runJavalin() {
		// Set up Jackson to serialize LocalDates and LocalDateTimes
		ObjectMapper jackson = new ObjectMapper();
		jackson.registerModule(new JavaTimeModule());
		jackson.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		JavalinJackson.configure(jackson);
		
		//Starts the Javalin framework
		Javalin app = Javalin.create().start(8080);
	}

	public static void dbtest() {
		CassandraUtil.getInstance().getSession();
	}
	
	public static void instantiateDatabase() {
		DatabaseCreator.dropTables();
		try {
			Thread.sleep(30000); // wait 30 seconds
		} catch(Exception e) {
			e.printStackTrace();
		}
		DatabaseCreator.createTables();
		try {
			Thread.sleep(20000); // wait 20 seconds
		} catch(Exception e) {
			e.printStackTrace();
		}
		//DatabaseCreator.populateUserTable();
		DatabaseCreator.populateReimbursementTable();
		System.exit(0);
	}

}
