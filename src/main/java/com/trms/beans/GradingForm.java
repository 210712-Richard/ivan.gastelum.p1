package com.trms.beans;

import java.util.Objects;

public class GradingForm {
	private GradeReference gradeFormat;
	private String grade;
	
	public GradingForm() {
		this.gradeFormat = GradeReference.LETTER;
		this.grade = gradeFormat.getDefaultGrade();
	}
	
	public GradingForm(GradeReference gradeFormat) {
		this.gradeFormat = gradeFormat;
		this.grade = gradeFormat.getDefaultGrade();
	}
	
	public GradingForm(GradeReference gradeFormat, String grade) {
		this.gradeFormat = gradeFormat;
		this.grade = grade;
	}

	public GradeReference getGradeFormat() {
		return gradeFormat;
	}

	public void setGradeFormat(GradeReference gradeFormat) {
		this.gradeFormat = gradeFormat;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Override
	public int hashCode() {
		return Objects.hash(grade, gradeFormat);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GradingForm other = (GradingForm) obj;
		return Objects.equals(grade, other.grade) && gradeFormat == other.gradeFormat;
	}
	
	
}
