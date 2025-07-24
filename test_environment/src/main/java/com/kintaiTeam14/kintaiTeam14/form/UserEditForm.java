package com.kintaiTeam14.kintaiTeam14.form;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserEditForm {
	
	@NotNull
	private String name;
	@NotNull
	private String departmentId;
	@NotNull
	private String employeeId;
	
	private String activateDate;

}
