package com.kintaiTeam14.kintaiTeam14.form;

import java.time.LocalDateTime;

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
	private LocalDateTime activateDate;

}
