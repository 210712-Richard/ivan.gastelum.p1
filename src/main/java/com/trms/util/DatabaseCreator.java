package com.trms.util;


public class DatabaseCreator {
	public static void dropTables() {
		StringBuilder sb = new StringBuilder("DROP TABLE IF EXISTS User;");
		CassandraUtil.getInstance().getSession().execute(sb.toString());
		
		sb = new StringBuilder("DROP TABLE IF EXISTS Reimbursement;");
		CassandraUtil.getInstance().getSession().execute(sb.toString());
		
	}
	
	public static void createTables() {
		
		//CREATE User Table
		StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS User (")
				.append("username text, password text, email text, firstName text, ")
				.append("lastName text, type text, department text, supervisorUsername text, startDate date, ")
				.append("availableReimbursement double, totalReimbursementRequested double, totalAwardedReimbursement double, ")
				.append("projectedReimbursement double, reimbursementSent list<UUID>, reimbursementForReview list<UUID>, ")
				.append("primary key(username, password));");
		CassandraUtil.getInstance().getSession().execute(query.toString());
		
		//CREATE Reimbursement Table
		query = new StringBuilder("CREATE TABLE IF NOT EXISTS Reimbursement (").append(
				"id uuid, username text, fname text, lname text, deptName text, requestDate date, deadline timestamp, ")
				.append("amountRequested double, event text, startTime time, ")
				.append("description text, isUrgent boolean, gradingForm tuple<text, text>, ")
				.append("needsRequestorReview boolean, msgURI text, presentation text, status text, ")
				.append("supervisorApproval tuple<text, text, text>, headDeptApproval tuple<text, text, text>, ")
				.append("benCoApproval tuple<text, text, text>, ")
				.append("finalGrade text, reimbursementComment text, finalAmountReimbursed double, ")
				.append("primary key(id, username));");
		CassandraUtil.getInstance().getSession().execute(query.toString());
	}
	
	public static void populateUserTable() {
		
	}
	
	public static void populateReimbursementTable() {
		
	}
}
