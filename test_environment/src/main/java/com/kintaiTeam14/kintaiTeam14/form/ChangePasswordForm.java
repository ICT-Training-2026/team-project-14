package com.kintaiTeam14.kintaiTeam14.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ChangePasswordForm {
	 	@NotBlank(message = "現在のパスワードを入力してください")
	    private String currentPass;

	 	 @NotBlank(message = "新しいパスワードを入力してください")
	     @Size(min = 8, message = "8文字以上で入力してください")
	 	@Pattern(
	 		    regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$",
	 		    message = "英字、数字、記号をそれぞれ1文字以上含む必要があります"
	 		)
	    private String newPass;

	    @NotBlank(message = "確認用パスワードを入力してください")
	    private String newPassRev;


}
