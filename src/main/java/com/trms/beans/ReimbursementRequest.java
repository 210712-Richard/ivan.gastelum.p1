package com.trms.beans;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReimbursementRequest {
	
	private UUID id;
	
	//Required fields
	private String username;
	private String fname;
	private String lname;
	private String deptName;
	private LocalDate requestDate;
	private LocalDate deadline;
	private double amountRequested;
	private EventType event;
	private String description;
	private String justification;
	private String workTimeMissed;
	
	//Other fields
	private Boolean isUrgent;
	private Boolean needsRequestorReview;
	private List<String> msgURIs;
	
	//Approval fields
	private Approval supervisorApproval;
	private Approval headDeptApproval;
	private Approval benCoApproval;
	private Status status;
	private String finalGrade;
	private String reimbursementComment;
	private GradingForm gradingForm; 
	
	public ReimbursementRequest() {
		super();
		this.status = Status.PENDING;
		this.isUrgent = false;
		msgURIs = new ArrayList<String>();
		
		supervisorApproval = new Approval();
		headDeptApproval = new Approval();
		benCoApproval = new Approval();
	}
	
}