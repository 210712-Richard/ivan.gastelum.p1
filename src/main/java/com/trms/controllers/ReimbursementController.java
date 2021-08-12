package com.trms.controllers;

import io.javalin.http.Context;

public interface ReimbursementController {
	
	/*
	 * POST requests
	 */
	
	public void createReimbursement(Context ctx);
	
	/*
	 * PUT requests
	 */
	
	public void approveRequest(Context ctx);
	
	public void cancelRequest(Context ctx);
	
	public void updateRequest(Context ctx);
	
	public void uploadMsg(Context ctx);
	
	public void uploadPPT(Context ctx);
	
	/*
	 * GET requests
	 */
	public void getReimbursement(Context ctx);
	
	public void getStatus(Context ctx);
	
	public void reviewRequest(Context ctx);
	
	//Getting the source for downloading files needed for approval
	public void getPresentation(Context ctx);
	
	public void getMsg(Context ctx);
	
	
}
