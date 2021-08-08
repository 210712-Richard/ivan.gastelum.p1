package com.trms.beans;

public enum GradeReference {
	LETTER("C"), NUMERIC("70"), PASS_FAIL("PASS"); 
	
	private String defaultGrade;
	
	GradeReference(String s) {
		this.defaultGrade = s;
	}
	
	public String getDefaultGrade() {
		return defaultGrade;
	}
}
