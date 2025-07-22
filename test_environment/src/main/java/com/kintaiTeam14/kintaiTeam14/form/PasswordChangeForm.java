package com.kintaiTeam14.kintaiTeam14.form;

import jakarta.validation.constraints.NotNull;

import lombok.Data;


@Data

public class PasswordChangeForm {

	@NotNull(message = "現在のパスワードを入力してください")
	private String currentPass;
	
	@NotNull(message = "新しいパスワードを入力してください")
	private String newPass;
	
	@NotNull(message = "新しいパスワードをもう一度入力してください")
	private String newPass_rev;
}
