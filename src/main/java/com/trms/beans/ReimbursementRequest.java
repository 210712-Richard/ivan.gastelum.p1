package com.trms.beans;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
	private LocalTime startTime;
	private LocalDateTime deadline;
	private double amountRequested;
	private EventType event;
	private String description;
	
	//Other fields
	private Boolean isUrgent;
	private Boolean needsRequestorReview;
	private String msgURI;
	private String presentation;
	
	//Approval fields
	private Approval supervisorApproval;
	private Approval headDeptApproval;
	private Approval benCoApproval;
	private Status status;
	private String finalGrade;
	private String reimbursementComment;
	private GradingForm gradingForm; 
	private double finalAmountReimbursed;
	
	public ReimbursementRequest() {
		super();
		this.status = Status.PENDING;
		this.isUrgent = false;
		
		supervisorApproval = new Approval();
		headDeptApproval = new Approval();
		benCoApproval = new Approval();
	}
	
}