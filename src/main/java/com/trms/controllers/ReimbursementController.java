package com.trms.controllers;

public interface ReimbursementController {
	
	/*
	 * POST requests
	 */
	
	public void createReimbursement();
	
	/*
	 * PUT requests
	 */
	
	public void approveRequest();
	
	public void cancelRequest();
	
	public void updateRequest();
	
	/*
	 * GET requests
	 */
	public void getReimbursement();
	
	public void getStatus();
	
	public void reviewRequest();
	
	//Getting the source for downloading files needed for approval
	public void getPresentation();
	
	public void getMsg();
	
	
}
