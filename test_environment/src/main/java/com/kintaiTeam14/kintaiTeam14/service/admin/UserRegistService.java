package com.kintaiTeam14.kintaiTeam14.service.admin;

import org.springframework.stereotype.Service;

import com.kintaiTeam14.kintaiTeam14.form.UserRegistForm;
import com.kintaiTeam14.kintaiTeam14.repository.admin.UserRegistRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserRegistService {
	
	private final UserRegistRepository r;
	
	public boolean userRegist(UserRegistForm f) {
		return r.userRegist(f);
	}
}
