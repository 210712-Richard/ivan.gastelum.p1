package com.trms.services;

import java.time.LocalDateTime;

import com.trms.beans.EventType;
import com.trms.beans.GradingForm;
import com.trms.beans.ReimbursementRequest;
import com.trms.beans.User;

public interface ReimbursementService {
	
	ReimbursementRequest createRequest(String username, String fname, String lname, String deptName, 
			double amountRequested, EventType event, String description);
	
	ReimbursementRequest updateRequestStatus();
	
}
