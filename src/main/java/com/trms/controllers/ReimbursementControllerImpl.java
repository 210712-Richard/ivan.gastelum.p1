package com.trms.controllers;

import com.trms.beans.Approval;
import com.trms.beans.EventType;
import com.trms.beans.GradingForm;
import com.trms.beans.ReimbursementRequest;
import com.trms.beans.Status;
import com.trms.beans.User;
import com.trms.beans.UserType;
import com.trms.services.ReimbursementService;
import com.trms.services.ReimbursementServiceImpl;
import com.trms.util.S3Util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.javalin.http.Context;
import java.io.InputStream;

public class ReimbursementControllerImpl implements ReimbursementController {

	private static Logger log = LogManager.getLogger(UserController.class);
	private ReimbursementService rs = new ReimbursementServiceImpl();

	@Override
	public void createReimbursement(Context ctx) {
		ReimbursementRequest req = ctx.bodyAsClass(ReimbursementRequest.class);
		User loggedUser = ctx.sessionAttribute("loggedUser");
		String username = ctx.pathParam("username");
		// Checking if logged in
		if (loggedUser == null || !loggedUser.getUsername().equals(username)) {
			ctx.status(401);
			return;
		}
		ReimbursementRequest request = rs.createRequest(loggedUser.getUsername(), loggedUser.getFname(),
				loggedUser.getLname(), loggedUser.getDepartment(), req.getAmountRequested(), req.getEvent(),
				req.getDescription());
		if (request == null) {
			ctx.status(409);
			ctx.html("Reimbursement not submitted.");
		}
		
		Approval a = new Approval(loggedUser.getSupervisorUsername());
		request.setSupervisorApproval(a);
		rs.updateRequestStatus(request);
		
		log.debug("Request created");
		ctx.status(201);
		ctx.json(request);
	}


	@Override
	public void cancelRequest(Context ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateRequest(Context ctx) {
		User loggedUser = ctx.sessionAttribute("loggedUser");
		String username = ctx.pathParam("username");
		
		String description = ctx.queryParam("description");
		String newAmount = ctx.queryParam("amount");
		Double amount = Double.parseDouble(newAmount);
		
		//Query params
		String approval = ctx.queryParam("status");

		// Check if user has logged in
		if (loggedUser == null) {
			ctx.status(401);
			return;
		}

		UUID requestId = UUID.fromString(ctx.pathParam("id"));
		ReimbursementRequest req = rs.getRequest(requestId);

		//Approval a = new Approval();
		//a.setReviewUsername("Verify");
		//req.setSupervisorApproval(a);
		
		req.setAmountRequested(amount);
		req.setDescription(description);
		rs.updateRequestStatus(req);
	}

	@Override
	public void getReimbursement(Context ctx) {

		User loggedUser = ctx.sessionAttribute("loggedUser");
		String username = ctx.pathParam("username");

		// Check if user has logged in
		if (loggedUser == null) {
			ctx.status(401);
			return;
		}

		UUID requestId = UUID.fromString(ctx.pathParam("id"));
		ReimbursementRequest req = rs.getRequest(requestId);

		if (req == null) {
			ctx.status(404);
			ctx.html("No request found with that ID");
		}

		// Check if correct people are able to access the request
		//If the user's department is the same as the 
		
		if(loggedUser.getType().equals(UserType.EMPLOYEE) && loggedUser.getUsername().equals(username)) {
			ctx.status(201);
			ctx.json(req);
			return;
		}
		
		//Benco are able to see any reimbursement
		if(loggedUser.getType().equals(UserType.BENCO)) {
			ctx.status(201);
			ctx.json(req);
			return;
		}
		
		//Department Head can only see Reimbursement within the given Department
		if(loggedUser.getType().equals(UserType.DEPT_HEAD) && loggedUser.getDepartment().equals(req.getDeptName())) {
			ctx.status(201);
			ctx.json(req);
			return;
		}
		
		if(loggedUser.getType().equals(UserType.SUPERVISOR) && loggedUser.getUsername().equals(req.getSupervisorApproval().getReviewUsername())) {
			ctx.status(201);
			ctx.json(req);
			return;
		}

		ctx.status(404);
	}

	@Override
	public void getStatus(Context ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reviewRequest(Context ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getPresentation(Context ctx) {
		User loggedUser = ctx.sessionAttribute("loggedUser");
		// Checking if logged in
		if (loggedUser == null) {
			ctx.status(401);
			return;
		}
		
		UUID requestId = UUID.fromString(ctx.pathParam("id"));
		ReimbursementRequest request = rs.getRequest(requestId);
		
		
		if(request == null) {
			ctx.status(404);
			return;
		}
		try {
			InputStream presentation = S3Util.getInstance().getObject(request.getPresentation());
			ctx.result(presentation);
		} catch (Exception e) {
			ctx.status(500);
		}
	}

	@Override
	public void getMsg(Context ctx) {
		User loggedUser = ctx.sessionAttribute("loggedUser");
		// Checking if logged in
		if (loggedUser == null) {
			ctx.status(401);
			return;
		}
		
		UUID requestId = UUID.fromString(ctx.pathParam("id"));
		ReimbursementRequest request = rs.getRequest(requestId);
		
		
		if(request == null) {
			ctx.status(404);
			return;
		}
		try {
			InputStream msg = S3Util.getInstance().getObject(request.getMsgURI());
			ctx.result(msg);
		} catch (Exception e) {
			ctx.status(500);
		}
	}

	@Override
	public void uploadMsg(Context ctx) {
		User loggedUser = ctx.sessionAttribute("loggedUser");
		String filetype = ctx.header("filetype");

		if (loggedUser == null) {
			ctx.status(401);
			return;
		}

		// Get the request from the UUID in the path
		UUID requestId = UUID.fromString(ctx.pathParam("id"));
		ReimbursementRequest request = rs.getRequest(requestId);
		log.debug("Request from the requestId" + request);

		// If no request was found with that id
		if (request == null) {
			ctx.status(404);
			ctx.html("No request with that ID");
			return;
		}

		// Generate the key and upload to the bucket
		String key = request.getId() + "-email." + filetype;
		S3Util.getInstance().uploadToBucket(key, ctx.bodyAsBytes());
		request.setMsgURI(key);
		log.debug("Presentation File URI: " + request.getMsgURI());
		log.debug("Final grade on request: " + request.getMsgURI());
		rs.updateRequestStatus(request);
		ctx.json(request);
	}

	@Override
	public void uploadPPT(Context ctx) {
		User loggedUser = ctx.sessionAttribute("loggedUser");
		String filetype = ctx.header("filetype");

		if (loggedUser == null) {
			ctx.status(401);
			return;
		}

		// Get the request from the UUID in the path
		UUID requestId = UUID.fromString(ctx.pathParam("id"));
		ReimbursementRequest request = rs.getRequest(requestId);
		log.debug("Request from the requestId" + request);

		// If no request was found with that id
		if (request == null) {
			ctx.status(404);
			ctx.html("No request with that ID");
			return;
		}

		String key = request.getId() + "-presentation." + filetype;
		S3Util.getInstance().uploadToBucket(key, ctx.bodyAsBytes());
		request.setPresentation(key);
		log.debug("Presentation File URI: " + request.getPresentation());
		log.debug("Final grade on request: " + request.getFinalGrade());
		rs.updateRequestStatus(request);
		ctx.json(request);
	}


	@Override
	public void approveRequest(Context ctx) {
		User loggedUser = ctx.sessionAttribute("loggedUser");

		if (loggedUser == null) {
			ctx.status(401);
			return;
		}
		//Getting the request by id
		UUID requestId = UUID.fromString(ctx.pathParam("id"));
		ReimbursementRequest req = rs.getRequest(requestId);
		
		if(loggedUser.getType().equals(UserType.SUPERVISOR)) {
			if(loggedUser.getUsername().equals(req.getSupervisorApproval().getReviewUsername())) {
				req.getSupervisorApproval().setStatus(Status.APPROVED);
			}else {
				ctx.status(401);
				ctx.html("Approval not permitted");
				return;
			}
		}
		
		if(loggedUser.getType().equals(UserType.DEPT_HEAD)) {
			if(loggedUser.getDepartment().equals(req.getDeptName()) && req.getSupervisorApproval().getStatus().equals(Status.APPROVED)) {
				req.getHeadDeptApproval().setStatus(Status.APPROVED);
			}else {
				ctx.status(401);
				ctx.html("Needs Supervisor approval First.");
			}
		}
		
		if(loggedUser.getType().equals(UserType.BENCO)) {
			if(req.getSupervisorApproval().getStatus().equals(Status.APPROVED) && req.getHeadDeptApproval().getStatus().equals(Status.APPROVED)) {
				req.getBenCoApproval().setStatus(Status.APPROVED);
				req.setFinalGrade("PASS");
				req.setFinalAmountReimbursed(req.getAmountRequested());
			}
		}
	}

}
