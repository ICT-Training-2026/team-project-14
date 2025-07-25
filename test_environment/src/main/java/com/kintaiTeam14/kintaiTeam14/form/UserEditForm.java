package com.kintaiTeam14.kintaiTeam14.form;

import java.time.LocalDate;

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
	@NotNull
	private LocalDate activateDate;

}
