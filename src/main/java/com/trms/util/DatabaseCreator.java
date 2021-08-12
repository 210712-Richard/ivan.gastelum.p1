package com.trms.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.trms.beans.EventType;
import com.trms.beans.GradingForm;
import com.trms.beans.ReimbursementRequest;
import com.trms.beans.User;
import com.trms.beans.UserType;
import com.trms.data.ReimbursementDAO;
import com.trms.data.ReimbursementDAOimpl;
import com.trms.data.UserDAO;
import com.trms.data.UserDAOimpl;

public class DatabaseCreator {
	public static void dropTables() {
		StringBuilder sb = new StringBuilder("DROP TABLE IF EXISTS user;");
		CassandraUtil.getInstance().getSession().execute(sb.toString());

		sb = new StringBuilder("DROP TABLE IF EXISTS reimbursement;");
		CassandraUtil.getInstance().getSession().execute(sb.toString());
	}

	public static void createTables() {

		// CREATE User Table
		StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS user (")
				.append("username text, password text, email text, firstName text, ")
				.append("lastName text, type text, department text, supervisorUsername text, startDate date, ")
				.append("availableReimbursement double, totalReimbursementRequested double, totalAwardedReimbursement double, ")
				.append("projectedReimbursement double, reimbursementSent list<UUID>, reimbursementForReview list<UUID>, ")
				.append("primary key(username, password));");
		CassandraUtil.getInstance().getSession().execute(query.toString());

		// CREATE Reimbursement Table
		query = new StringBuilder("CREATE TABLE IF NOT EXISTS reimbursement (").append(
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

		UserDAO ud = new UserDAOimpl();

		// Instantiating a user 1
		User user = new User("aandres", "123", "Andres", "Gastelum", "agastelum@example.com", UserType.EMPLOYEE, "HR",
				"jjona");
		ud.registerUser(user);

		// Instantiating a user 2
		user = new User("abrahamba", "456", "Abraham", "Barraza", "abarra@example.com", UserType.SUPERVISOR,
				"Technology", "ssteele");
		ud.registerUser(user);

		// Instantiating a user 3
		user = new User("mikkel", "120", "Michael", "Jonas", "mikkel@example.com", UserType.DEPT_HEAD, "Technology",
				"olive");
		ud.registerUser(user);

		// Instantiating a user 4
		user = new User("igastelum", "123", "Ivan", "Gastelum", "igastelum@example.com", UserType.EMPLOYEE,
				"Technology", "abrahamba");
		ud.registerUser(user);
		
		// Instantiating a user 5
		user = new User("kkenia", "123", "Kenia", "Guerrero", "kkenia@example.com", UserType.BENCO,
						"HR", "susan");
		ud.registerUser(user);
				
		// Instantiating a user 6
		user = new User("claudy", "123", "Claudia", "Monroe", "cmon@example.com", UserType.SUPERVISOR,
				"HR", "larry");
		ud.registerUser(user);

	}

	public static void populateReimbursementTable() {
		ReimbursementDAO rd = new ReimbursementDAOimpl();
		
		//SUBMITTING A REIMBURSEMENT REQUEST
		
		GradingForm gf = new GradingForm();
		ReimbursementRequest req = new ReimbursementRequest(UUID.randomUUID(), "aandres", "Andres", "Gastelum", "HR",
				LocalDateTime.now(),200.0, EventType.SEMINAR, "Conference seminar", gf);
		rd.addReimbursement(req);
	}
}
