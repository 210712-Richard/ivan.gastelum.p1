package com.trms.services;

import java.time.LocalDateTime;
import java.util.UUID;

import com.trms.beans.EventType;
import com.trms.beans.GradingForm;
import com.trms.beans.ReimbursementRequest;
import com.trms.beans.User;

public interface ReimbursementService {
	
	ReimbursementRequest createRequest(String username, String fname, String lname, String deptName, 
			double amountRequested, EventType event, String description);
	
	void updateRequestStatus(ReimbursementRequest req);
	
	ReimbursementRequest getRequest(UUID id);
	
}
