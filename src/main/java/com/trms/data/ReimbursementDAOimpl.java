package com.trms.data;

import java.util.List;
import java.util.UUID;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.datastax.oss.driver.api.core.data.TupleValue;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.core.type.TupleType;
import com.trms.beans.ReimbursementRequest;
import com.trms.util.CassandraUtil;

public class ReimbursementDAOimpl implements ReimbursementDAO {
	
	private CqlSession session = CassandraUtil.getInstance().getSession();
	private static final TupleType TUPLE_TEMPLATE_1 = DataTypes.tupleOf(DataTypes.TEXT, DataTypes.TEXT);
	private static final TupleType TUPLE_TEMPLATE_2 = DataTypes.tupleOf(DataTypes.TEXT, DataTypes.TEXT, DataTypes.TEXT);

	@Override
	public void addReimbursement(ReimbursementRequest r) {
		
		StringBuilder query = new StringBuilder("Insert into reimbursement (")
				.append("id, username , fname, lname, deptName, requestDate, deadline, ")
				.append("amountRequested, event, startTime, ")
				.append("description, isUrgent, gradingForm, ")
				.append("needsRequestorReview, msgURI, presentation, status, ")
				.append("supervisorApproval, headDeptApproval, ")
				.append("benCoApproval, finalGrade, reimbursementComment, finalAmountReimbursed ")
				.append(") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?);");
		SimpleStatement s = new SimpleStatementBuilder(query.toString())
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		
		/*
		 * SETTING MY TUPLES TO BE COMPATIBLE WITH DATABASE
		 */
		
		//GradingForm conversion into text tuples
		TupleValue grade = TUPLE_TEMPLATE_1.newValue(r.getGradingForm().getGradeFormat().toString(),
				r.getGradingForm().getGrade());
		
		//Supervisor Approval Tuple
		TupleValue supervisorApproval = TUPLE_TEMPLATE_2.newValue(r.getSupervisorApproval().getStatus().toString(),
				r.getSupervisorApproval().getReason(), r.getSupervisorApproval().getReviewUsername());
		
		//Head Department Approval Tuple
		TupleValue DeptHeadApproval = TUPLE_TEMPLATE_2.newValue(r.getHeadDeptApproval().getStatus().toString(),
				r.getHeadDeptApproval().getReason(), r.getHeadDeptApproval().getReviewUsername());
		
		//BenCo Approval Tuple
		TupleValue benCoApproval = TUPLE_TEMPLATE_2.newValue(r.getBenCoApproval().getStatus().toString(),
				r.getBenCoApproval().getReason(), r.getBenCoApproval().getReviewUsername());
		
		BoundStatement bound = session.prepare(s).bind(r.getId(), r.getUsername(), r.getFname(), r.getLname(), r.getDeptName(), r.getRequestDate(), r.getDeadline(),
				r.getAmountRequested(), r.getEvent().toString(), r.getStartTime(), r.getDescription(), r.getIsUrgent(), grade,
				r.getNeedsRequestorReview(), r.getMsgURI(), r.getPresentation(), r.getStatus(), supervisorApproval,
				DeptHeadApproval, benCoApproval, r.getFinalGrade(), r.getReimbursementComment(), r.getFinalAmountReimbursed());
		
		session.execute(bound);
	}

	@Override
	public void getReimbursementById(UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ReimbursementRequest> getRequests() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateReimbursement(ReimbursementRequest r) {
		// TODO Auto-generated method stub
		
	}

}
