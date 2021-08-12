package com.trms.data;

import com.trms.beans.User;
import com.trms.beans.UserType;
import com.trms.util.CassandraUtil;


import java.util.UUID;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;

public class UserDAOimpl implements UserDAO {

	private CqlSession session = CassandraUtil.getInstance().getSession();

	@Override
	public User getUser(String username, String password) {
		StringBuilder query = new StringBuilder("Select username , password, email, firstName, ")
				.append("lastName, type, department, supervisorUsername, startDate, ")
				.append("availableReimbursement, totalReimbursementRequested, totalAwardedReimbursement, ")
				.append("projectedReimbursement, reimbursementSent, reimbursementForReview")
				.append("from user where username=? AND password=?");
		
		SimpleStatement s = new SimpleStatementBuilder(query.toString()).build();
		BoundStatement bound = session.prepare(s).bind(username, password);
		
		// ResultSet is the values returned by my query.
		ResultSet rs = session.execute(bound);
		Row row = rs.one();
		if (row == null) {
			// if there is no return values
			return null;
		}
		
		/*
		 * NEEDS TO SET MISSING FIELDS
		 */
		User u = new User();
		u.setUsername(row.getString("username"));
		u.setPassword(row.getString("password"));
		u.setEmail(row.getString("email"));
		u.setFname(row.getString("firstName"));
		u.setLname(row.getString("lastName"));
		u.setType(UserType.valueOf(row.getString("type")));
		u.setDepartment(row.getString("department"));
		u.setSupervisorUsername(row.getString("supervisorUsername"));
		u.setStartDate(row.getLocalDate("startDate"));
		u.setAvailableReimbursement(row.getDouble("availableReimbursement"));
		u.setTotalReimbursementRequested(row.getDouble("totalReimbursementRequested"));
		u.setTotalAwardedReimbursement(row.getDouble("totalAwardedReimbursement"));
		u.setProjectedReimbursement(row.getDouble("projectedReimbursement"));
		u.setReimbursementSent(row.getList("reimbursementSent", UUID.class));
		u.setReimbursementForReview(row.getList("reimbursementForReview", UUID.class));

		return u;
	}

	@Override
	public User getUser(String username) {
		
		if(username==null)
			return null;
		
		StringBuilder query = new StringBuilder("SELECT username , password, email, firstName, ")
				.append("lastName, type, department, supervisorUsername, startDate, ")
				.append("availableReimbursement, totalReimbursementRequested, totalAwardedReimbursement, ")
				.append("projectedReimbursement, reimbursementSent, reimbursementForReview ")
				.append("FROM user where username=?;");
		SimpleStatement s = new SimpleStatementBuilder(query.toString()).build();
		BoundStatement bound = session.prepare(s).bind(username);

		ResultSet rs = session.execute(bound);
		Row row = rs.one();

		if (row == null) {
			// if there is no return values
			return null;
		}
		
		/*
		 * NEEDS TO SET MISSING OF FIELDS
		 */
		User u = new User();
		u.setUsername(row.getString("username"));
		u.setPassword(row.getString("password"));
		u.setEmail(row.getString("email"));
		u.setFname(row.getString("firstName"));
		u.setLname(row.getString("lastName"));
		u.setType(UserType.valueOf(row.getString("type")));
		u.setDepartment(row.getString("department"));
		u.setSupervisorUsername(row.getString("supervisorUsername"));
		u.setStartDate(row.getLocalDate("startDate"));
		u.setAvailableReimbursement(row.getDouble("availableReimbursement"));
		u.setTotalReimbursementRequested(row.getDouble("totalReimbursementRequested"));
		u.setTotalAwardedReimbursement(row.getDouble("totalAwardedReimbursement"));
		u.setProjectedReimbursement(row.getDouble("projectedReimbursement"));
		u.setReimbursementSent(row.getList("reimbursementSent", UUID.class));
		u.setReimbursementForReview(row.getList("reimbursementForReview", UUID.class));

		return u;
	}

	@Override
	public void registerUser(User u) {
		StringBuilder query = new StringBuilder("Insert into user (username , password, email, firstName, ")
				.append("lastName, type, department, supervisorUsername, startDate, ")
				.append("availableReimbursement, totalReimbursementRequested, totalAwardedReimbursement, ")
				.append("projectedReimbursement, reimbursementSent, reimbursementForReview")
				.append(") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
		SimpleStatement s = new SimpleStatementBuilder(query.toString())
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		
		BoundStatement bound = session.prepare(s).bind(u.getUsername(), u.getPassword(), u.getEmail(), u.getFname(),
				u.getLname(), u.getType().toString(), u.getDepartment(), u.getSupervisorUsername(), u.getStartDate(),
				u.getAwardedReimbursement(), u.getTotalReimbursementRequested(), u.getTotalAwardedReimbursement(),
				u.getProjectedReimbursement(), u.getReimbursementSent(), u.getReimbursementForReview());
		session.execute(bound);
	}

	@Override
	public void updateUserInfo(User u) {
		
		StringBuilder query = new StringBuilder("UPDATE user SET email=?, firstName=?, ")
				.append("lastName=?, type=?, department=?, supervisorUsername=?, availableReimbursement=?, ")
				.append("totalReimbursementRequested=?, totalAwardedReimbursement=?, projectedReimbursement=?, reimbursementSent=?, reimbursementForReview=? ")
				.append("WHERE username = ? AND password = ?;");
		
		SimpleStatement s = new SimpleStatementBuilder(query.toString())
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s).bind(u.getEmail(), u.getFname(), u.getLname(),
				u.getType().toString(), u.getDepartment(), u.getSupervisorUsername(), u.getAwardedReimbursement(),
				u.getTotalReimbursementRequested(), u.getTotalAwardedReimbursement(), u.getProjectedReimbursement(), 
				u.getReimbursementSent(), u.getReimbursementForReview(), u.getUsername(), u.getPassword());

		session.execute(bound);
	}

}
