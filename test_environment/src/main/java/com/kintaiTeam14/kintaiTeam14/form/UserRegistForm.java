package com.kintaiTeam14.kintaiTeam14.form;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserRegistForm {
	
	@NotNull
	private String name;
	@NotNull
	private String departmentId;
	@NotNull
	private String employeeId;

}
