package com.trms.controllers;

import com.trms.beans.EventType;
import com.trms.beans.GradingForm;
import com.trms.beans.ReimbursementRequest;
import com.trms.beans.User;
import com.trms.services.ReimbursementService;
import com.trms.services.ReimbursementServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.javalin.http.Context;

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
		log.debug("Request created");
		ctx.status(201);
		ctx.json(request);
	}

	@Override
	public void approveRequest(Context ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cancelRequest(Context ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateRequest(Context ctx) {
		User loggedUser = ctx.sessionAttribute("loggedUser");
		String username = ctx.pathParam("username");

		// Check if user has logged in
		if (loggedUser == null) {
			ctx.status(401);
			return;
		}
		
		UUID requestId = UUID.fromString(ctx.pathParam("id"));
		ReimbursementRequest req = rs.getRequest(requestId);
		
		req.setAmountRequested(350);
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

		ctx.json(req);
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
		// TODO Auto-generated method stub

	}

	@Override
	public void getMsg(Context ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void uploadMsg(Context ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void uploadPPT(Context ctx) {
		// TODO Auto-generated method stub

	}

}
