package com.trms.data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
import com.trms.beans.Approval;
import com.trms.beans.EventType;
import com.trms.beans.GradeReference;
import com.trms.beans.GradingForm;
import com.trms.beans.ReimbursementRequest;
import com.trms.beans.Status;
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
		
		BoundStatement bound = session.prepare(s).bind(r.getId(), r.getUsername(), r.getFname(), r.getLname(), r.getDeptName(), r.getRequestDate(), r.getDeadline().toInstant(ZoneOffset.UTC),
				r.getAmountRequested(), r.getEvent().toString(), r.getStartTime(), r.getDescription(), r.getIsUrgent(), grade,
				r.getNeedsRequestorReview(), r.getMsgURI(), r.getPresentation(), r.getStatus().toString(), supervisorApproval,
				DeptHeadApproval, benCoApproval, r.getFinalGrade(), r.getReimbursementComment(), r.getFinalAmountReimbursed());
		
		session.execute(bound);
	}

	@Override
	public ReimbursementRequest getReimbursementById(UUID id) {
		
		if(id==null) return null;
		
		StringBuilder query = new StringBuilder("SELECT ")
				.append("id, username , fname, lname, deptName, requestDate, deadline, ")
				.append("amountRequested, event, startTime, ")
				.append("description, isUrgent, gradingForm, ")
				.append("needsRequestorReview, msgURI, presentation, status, ")
				.append("supervisorApproval, headDeptApproval, ")
				.append("benCoApproval, finalGrade, reimbursementComment, finalAmountReimbursed ")
				.append("FROM request WHERE id = ?;");
		SimpleStatement s = new SimpleStatementBuilder(query.toString()).build();
		BoundStatement bound = session.prepare(s).bind(id);
		
		ResultSet rs = session.execute(bound);
		Row row = rs.one();
		
		if (row==null) return null;
		
		//instantiate a new Reimbursement object and set all values from values gotten from query
		ReimbursementRequest r = new ReimbursementRequest();
		r.setId(row.getUuid("id"));
		r.setUsername(row.getString("username"));
		r.setFname(row.getString("fname"));
		r.setLname(row.getString("lname"));
		r.setDeptName(row.getString("deptName"));
		r.setRequestDate(row.getLocalDate("requestDate"));
		r.setDeadline(LocalDateTime.ofInstant(row.getInstant("deadline"), ZoneOffset.UTC));
		r.setAmountRequested(row.getDouble("amountRequested"));
		r.setEvent(EventType.valueOf(row.getString("event")));
		r.setStartTime(row.getLocalTime("startTime"));
		r.setDescription(row.getString("description"));
		//Instantiating a gradingForm object based on the values gotten from query
		GradingForm g = new GradingForm(GradeReference.valueOf(row.getTupleValue("gradingForm").get(0,String.class)), row.getTupleValue("gradingForm").get(1,String.class));
		r.setGradingForm(g);
		r.setNeedsRequestorReview(row.getBoolean("needsRequestorReview"));
		r.setMsgURI(row.getString("msgURI"));
		r.setPresentation(row.getString("presentation"));
		r.setStatus(Status.valueOf(row.getString("status")));
		//Instantiating approvals and used values from tuple values
		Approval supervisor = new Approval(Status.valueOf(row.getTupleValue("supervisorApproval").get(0,String.class)), row.getTupleValue("supervisorApproval").get(1,String.class), row.getTupleValue("supervisorApproval").get(2,String.class));
		r.setSupervisorApproval(supervisor);
		Approval head = new Approval(Status.valueOf(row.getTupleValue("headDeptApproval").get(0,String.class)), row.getTupleValue("headDeptApproval").get(1,String.class), row.getTupleValue("headDeptApproval").get(2,String.class));
		r.setSupervisorApproval(head);
		Approval benco = new Approval(Status.valueOf(row.getTupleValue("benCoApproval").get(0,String.class)), row.getTupleValue("benCoApproval").get(1,String.class), row.getTupleValue("benCoApproval").get(2,String.class));
		r.setSupervisorApproval(benco);
		r.setFinalGrade(row.getString("finalGrade"));
		r.setReimbursementComment(row.getString("reimbursementComment"));
		r.setFinalAmountReimbursed(row.getDouble("finalAmountReimbursed"));
		
		return r;
	}

	@Override
	public List<ReimbursementRequest> getRequests() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateReimbursement(ReimbursementRequest r) {
		
		StringBuilder query = new StringBuilder("UPDATE request SET ")
				.append("fname, lname, deptName, requestDate, deadline, ")
				.append("amountRequested, event, startTime, ")
				.append("description, isUrgent, gradingForm, ")
				.append("needsRequestorReview, msgURI, presentation, status, ")
				.append("supervisorApproval, headDeptApproval, ")
				.append("benCoApproval, finalGrade, reimbursementComment, finalAmountReimbursed ")
				.append(" WHERE id = ? AND username = ?;");
		
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
		
		
		//BINDING VALUES
		BoundStatement bound = session.prepare(s).bind(r.getFname(), r.getLname(), r.getDeptName(), r.getRequestDate(), r.getDeadline().toInstant(ZoneOffset.UTC),
				r.getAmountRequested(), r.getEvent().toString(), r.getStartTime(), r.getDescription(), r.getIsUrgent(), grade,
				r.getNeedsRequestorReview(), r.getMsgURI(), r.getPresentation(), r.getStatus().toString(), supervisorApproval,
				DeptHeadApproval, benCoApproval, r.getFinalGrade(), r.getReimbursementComment(), r.getFinalAmountReimbursed(), r.getId(), r.getUsername());
		
		session.execute(bound);
		
	}

}
