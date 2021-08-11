package com.trms.data;

import com.trms.beans.User;
import com.trms.beans.UserType;
import com.trms.util.CassandraUtil;


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
		String query = "Select username, type, email, currency, birthday, lastCheckIn from user where username=?";
		SimpleStatement s = new SimpleStatementBuilder(query).build();
		BoundStatement bound = session.prepare(s).bind(username);
		// ResultSet is the values returned by my query.
		ResultSet rs = session.execute(bound);
		Row row = rs.one();
		if(row == null) {
			// if there is no return values
			return null;
		}
		User u = new User();
		u.setUsername(row.getString("username"));
		u.setEmail(row.getString("email"));
		u.setType(UserType.valueOf(row.getString("type")));
		
		return u;
	}

	@Override
	public boolean getUser(String username) {
		String query = "Select username from user where username=?";
		SimpleStatement s = new SimpleStatementBuilder(query).build();
		BoundStatement bound = session.prepare(s).bind(username);

		ResultSet rs = session.execute(bound);
		Row row = rs.one();
		if(row == null) return false;
		else return true;
	}

	@Override
	public void registerUser(User u) {
		StringBuilder query = new StringBuilder("Insert into user (username , password, email, firstName, ")
				.append("lastName, type, department, supervisorUsername, startDate, ")
				.append("availableReimbursement, totalReimbursementRequested, totalAwardedReimbursement, ")
				.append("projectedReimbursement, reimbursementSent, reimbursementForReview")
				.append(") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
		SimpleStatement s = new SimpleStatementBuilder(query.toString()).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s)
				.bind(u.getUsername(), u.getPassword(), u.getEmail(), u.getFname(), u.getLname(), u.getType().toString(), 
						u.getDepartment(), u.getSupervisorUsername(), u.getStartDate(), u.getAwardedReimbursement(),
						u.getTotalReimbursementRequested(), u.getTotalAwardedReimbursement(), u.getProjectedReimbursement(),
						u.getReimbursementSent(), u.getReimbursementForReview());
		session.execute(bound);
	}

	@Override
	public void updateUserInfo(User u) {
		// TODO Auto-generated method stub
		
	}

}
