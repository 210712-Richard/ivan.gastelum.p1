package com.trms.beans;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class User {
	//username for authentication by any user
	private String username;
	//password for authentication by any user
	private String password;
	//User First Name
	private String fname;
	//User Last Name
	private String lname;
	//User email
	private String email;
	//The date user started working in the company. We'll be used for Reimbursement reset each time interval
	private LocalDate startDate;
	//User type: EMPLOYEE, SUPERVISOR, DEPT_HEAD or BENCO
	private UserType type;
	//Department the user belongs to
	private String department;
	//Direct supervisor username
	private String supervisorUsername;
	//The current awarded reimbursement available by user
	private double availableReimbursement;
	//The current total reimbursement requested by user
	private double totalReimbursementRequested;
	
	private double totalAwardedReimbursement;
	
	private double projectedReimbursement;
	
	//Lists of reimbursement that user has sent, and needs to review
	//For supervisors, head of dept, and benco: have a list to review 
	//For requestor employees: if any higher level needs more info, the request gets appended to the list for Review
	private List<UUID> reimbursementSent;
	private List<UUID> reimbursementForReview;
	
	/**
	 * Constructors
	 */
	
	public User() {
		this.availableReimbursement = 1000.0d;
		this.reimbursementSent = new ArrayList<>();
		this.reimbursementForReview = new ArrayList<>();
		this.totalReimbursementRequested = 0.0d;
		this.totalAwardedReimbursement = 0.0d;
		this.projectedReimbursement = this.availableReimbursement;
		this.startDate = LocalDate.now();
	}
	
	public User(String username, String password, String fname, String lname, String email,
			UserType type, String department, String supervisorUsername) {
		this();
		this.username = username;
		this.password = password;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.type = type;
		this.department = department;
		this.supervisorUsername = supervisorUsername;
	}
	
	/**
	 * Getters and Setters
	 * 
	 */
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public UserType getType() {
		return type;
	}
	public void setType(UserType type) {
		this.type = type;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getSupervisorUsername() {
		return supervisorUsername;
	}
	public void setSupervisorUsername(String supervisorUserName) {
		this.supervisorUsername = supervisorUserName;
	}
	public double getAwardedReimbursement() {
		return availableReimbursement;
	}
	public void setAwardedReimbursement(long availableReimbursement) {
		this.availableReimbursement = availableReimbursement;
	}
	public List<UUID> getReimbursementSent() {
		return reimbursementSent;
	}
	public void setReimbursementSent(List<UUID> reimbursementSent) {
		this.reimbursementSent = reimbursementSent;
	}
	public List<UUID> getReimbursementForReview() {
		return reimbursementForReview;
	}
	public void setReimbursementForReview(List<UUID> reimbursementForReview) {
		this.reimbursementForReview = reimbursementForReview;
	}
	public double getTotalReimbursementRequested() {
		return totalReimbursementRequested;
	}
	public void setTotalReimbursementRequested(double totalReimbursementRequested) {
		this.totalReimbursementRequested = totalReimbursementRequested;
	}
	public double getTotalAwardedReimbursement() {
		return totalAwardedReimbursement;
	}
	public void setTotalAwardedReimbursement(double totalAwardedReimbursement) {
		this.totalAwardedReimbursement = totalAwardedReimbursement;
	}
	public double getProjectedReimbursement() {
		return projectedReimbursement;
	}
	public void setProjectedReimbursement(double projectedReimbursement) {
		this.projectedReimbursement = projectedReimbursement;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(availableReimbursement, department, email, fname, lname, password, reimbursementForReview,
				reimbursementSent, startDate, supervisorUsername, type, username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return availableReimbursement == other.availableReimbursement && Objects.equals(department, other.department)
				&& Objects.equals(email, other.email) && Objects.equals(fname, other.fname)
				&& Objects.equals(lname, other.lname) && Objects.equals(password, other.password)
				&& Objects.equals(reimbursementForReview, other.reimbursementForReview)
				&& Objects.equals(reimbursementSent, other.reimbursementSent)
				&& Objects.equals(startDate, other.startDate)
				&& Objects.equals(supervisorUsername, other.supervisorUsername) && type == other.type
				&& Objects.equals(username, other.username);
	}
	
}