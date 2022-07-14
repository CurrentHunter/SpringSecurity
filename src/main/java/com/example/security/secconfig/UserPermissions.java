package com.example.security.secconfig;

public enum UserPermissions {
	STUDENT_READ("student:read"),
	STUDENT_WRITE("student:write"),
	COURSE_READ("course:read"),
	COURSE_WRITE("course:write");
	
	public String getPermission() {
		return persmission;
	}

	private final String persmission;

	UserPermissions(String persmission) {
		this.persmission = persmission;
	}
	
}
