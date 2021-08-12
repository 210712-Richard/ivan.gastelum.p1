package com.trms.data;

import java.util.List;
import java.util.UUID;

import com.trms.beans.ReimbursementRequest;

public interface ReimbursementDAO {
	
	//Creating a new Reimbursement request
	void addReimbursement(ReimbursementRequest r);
	
	//Getting a reimbursement by id
	ReimbursementRequest getReimbursementById(UUID id);
	
	//Getting all reimbursement requests
	List<ReimbursementRequest> getRequests();
	
	//Changing status of a reimbursement request
	void updateReimbursement(ReimbursementRequest r);
}
