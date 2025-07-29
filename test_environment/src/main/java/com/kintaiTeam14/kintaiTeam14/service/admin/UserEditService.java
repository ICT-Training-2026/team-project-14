package com.kintaiTeam14.kintaiTeam14.service.admin;

import org.springframework.stereotype.Service;

import com.kintaiTeam14.kintaiTeam14.form.UserEditForm;
import com.kintaiTeam14.kintaiTeam14.repository.admin.UserEditRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserEditService {
	
	private final UserEditRepository r;

	public int userEdit(UserEditForm f) {
		return r.userEditCheck(f);
	}
	
	public void editSchedule(UserEditForm f) {
		r.editSchedule(f);
	}
}
