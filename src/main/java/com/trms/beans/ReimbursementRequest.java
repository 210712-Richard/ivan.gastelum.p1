package com.trms.beans;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
		this.needsRequestorReview = false;
		
		this.requestDate = LocalDate.now();
		this.startTime = LocalTime.now();
		this.msgURI = null;
		this.presentation = null;
		
		supervisorApproval = new Approval();
		headDeptApproval = new Approval();
		benCoApproval = new Approval();
		
		this.finalGrade = null;
		this.reimbursementComment = null;
		this.finalAmountReimbursed = 0.0d;
	}

	public ReimbursementRequest(UUID id, String username, String fname, String lname, String deptName,
			LocalDateTime deadline, double amountRequested, EventType event, String description,
			GradingForm gradingForm) {
		this();
		this.id = id;
		this.username = username;
		this.fname = fname;
		this.lname = lname;
		this.deptName = deptName;
		this.deadline = deadline;
		this.amountRequested = amountRequested;
		this.event = event;
		this.description = description;
		this.gradingForm = gradingForm;
	}
	
	/*
	 * GETTERS AND SETTERS
	 */

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public LocalDate getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(LocalDate requestDate) {
		this.requestDate = requestDate;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}

	public double getAmountRequested() {
		return amountRequested;
	}

	public void setAmountRequested(double amountRequested) {
		this.amountRequested = amountRequested;
	}

	public EventType getEvent() {
		return event;
	}

	public void setEvent(EventType event) {
		this.event = event;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsUrgent() {
		return isUrgent;
	}

	public void setIsUrgent(Boolean isUrgent) {
		this.isUrgent = isUrgent;
	}

	public Boolean getNeedsRequestorReview() {
		return needsRequestorReview;
	}

	public void setNeedsRequestorReview(Boolean needsRequestorReview) {
		this.needsRequestorReview = needsRequestorReview;
	}

	public String getMsgURI() {
		return msgURI;
	}

	public void setMsgURI(String msgURI) {
		this.msgURI = msgURI;
	}

	public String getPresentation() {
		return presentation;
	}

	public void setPresentation(String presentation) {
		this.presentation = presentation;
	}

	public Approval getSupervisorApproval() {
		return supervisorApproval;
	}

	public void setSupervisorApproval(Approval supervisorApproval) {
		this.supervisorApproval = supervisorApproval;
	}

	public Approval getHeadDeptApproval() {
		return headDeptApproval;
	}

	public void setHeadDeptApproval(Approval headDeptApproval) {
		this.headDeptApproval = headDeptApproval;
	}

	public Approval getBenCoApproval() {
		return benCoApproval;
	}

	public void setBenCoApproval(Approval benCoApproval) {
		this.benCoApproval = benCoApproval;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getFinalGrade() {
		return finalGrade;
	}

	public void setFinalGrade(String finalGrade) {
		this.finalGrade = finalGrade;
	}

	public String getReimbursementComment() {
		return reimbursementComment;
	}

	public void setReimbursementComment(String reimbursementComment) {
		this.reimbursementComment = reimbursementComment;
	}

	public GradingForm getGradingForm() {
		return gradingForm;
	}

	public void setGradingForm(GradingForm gradingForm) {
		this.gradingForm = gradingForm;
	}

	public double getFinalAmountReimbursed() {
		return finalAmountReimbursed;
	}

	public void setFinalAmountReimbursed(double finalAmountReimbursed) {
		this.finalAmountReimbursed = finalAmountReimbursed;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amountRequested, benCoApproval, deadline, deptName, description, event,
				finalAmountReimbursed, finalGrade, fname, gradingForm, headDeptApproval, id, isUrgent, lname, msgURI,
				needsRequestorReview, presentation, reimbursementComment, requestDate, startTime, status,
				supervisorApproval, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReimbursementRequest other = (ReimbursementRequest) obj;
		return Double.doubleToLongBits(amountRequested) == Double.doubleToLongBits(other.amountRequested)
				&& Objects.equals(benCoApproval, other.benCoApproval) && Objects.equals(deadline, other.deadline)
				&& Objects.equals(deptName, other.deptName) && Objects.equals(description, other.description)
				&& event == other.event
				&& Double.doubleToLongBits(finalAmountReimbursed) == Double
						.doubleToLongBits(other.finalAmountReimbursed)
				&& Objects.equals(finalGrade, other.finalGrade) && Objects.equals(fname, other.fname)
				&& Objects.equals(gradingForm, other.gradingForm)
				&& Objects.equals(headDeptApproval, other.headDeptApproval) && Objects.equals(id, other.id)
				&& Objects.equals(isUrgent, other.isUrgent) && Objects.equals(lname, other.lname)
				&& Objects.equals(msgURI, other.msgURI)
				&& Objects.equals(needsRequestorReview, other.needsRequestorReview)
				&& Objects.equals(presentation, other.presentation)
				&& Objects.equals(reimbursementComment, other.reimbursementComment)
				&& Objects.equals(requestDate, other.requestDate) && Objects.equals(startTime, other.startTime)
				&& status == other.status && Objects.equals(supervisorApproval, other.supervisorApproval)
				&& Objects.equals(username, other.username);
	}
}