package com.trms.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.trms.beans.EventType;
import com.trms.beans.GradingForm;
import com.trms.beans.ReimbursementRequest;
import com.trms.beans.User;
import com.trms.data.ReimbursementDAO;
import com.trms.data.ReimbursementDAOimpl;

public class ReimbursementServiceImpl implements ReimbursementService {
	
	private static Logger log = LogManager.getLogger(UserServiceImpl.class);
	public ReimbursementDAO rd = new ReimbursementDAOimpl();

	@Override
	public ReimbursementRequest createRequest(String username, String fname, String lname, String deptName,
			double amountRequested, EventType event, String description) {
		
		GradingForm gf = new GradingForm();
		ReimbursementRequest req = new ReimbursementRequest(UUID.randomUUID(), username, fname, lname, deptName,
				LocalDateTime.now(),amountRequested, event, description, gf);
		
		rd.addReimbursement(req);
		return req;
	}

	@Override
	public ReimbursementRequest updateRequestStatus() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
