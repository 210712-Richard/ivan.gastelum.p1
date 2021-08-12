package com.trms.beans;

import java.util.Objects;

public class Approval {
	private Status status;
	private String reason;
	private String reviewUsername;
	
	public Approval() {
		super();
		this.status = Status.PENDING;
		this.reason = null;
	}
	
	public Approval(String username) {
		this();
		this.reviewUsername = username;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReviewUsername() {
		return reviewUsername;
	}

	public void setReviewUsername(String reviewUsername) {
		this.reviewUsername = reviewUsername;
	}

	@Override
	public int hashCode() {
		return Objects.hash(reason, reviewUsername, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Approval other = (Approval) obj;
		return Objects.equals(reason, other.reason) && Objects.equals(reviewUsername, other.reviewUsername)
				&& status == other.status;
	}
}
