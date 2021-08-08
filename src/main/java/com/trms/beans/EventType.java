package com.trms.beans;

public enum EventType {
	UNIVERSITY_COURSE(0.8d), 
	SEMINAR(0.6d), 
	CERTIFICATION_PREP_CLASS(0.75d), 
	CERTIFICATION(1.0d), 
	TECH_TRAINING(0.9d), 
	OTHER(0.3d);
	
	private double percentFraction;
	
	EventType(double percentFraction){
		this.percentFraction = percentFraction;
	}
	
	public double getpercent() {
		return percentFraction;
	}
}
