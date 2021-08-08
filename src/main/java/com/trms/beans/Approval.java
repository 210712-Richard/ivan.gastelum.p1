package com.trms.beans;

public class Approval {
	private Status status;
	private String reason;
	private String reviewUsername;
	
	public Approval() {
		super();
		this.status = Status.PENDING;
	}
	
	public Approval(String username) {
		this();
		this.status = Status.PENDING;
		this.reviewUsername = username;
	}
}
