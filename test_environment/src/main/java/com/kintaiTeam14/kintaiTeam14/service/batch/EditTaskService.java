package com.kintaiTeam14.kintaiTeam14.service.batch;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kintaiTeam14.kintaiTeam14.repository.batch.EditTaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EditTaskService {
	
	private final EditTaskRepository r;

	public List<Map<String,Object>> checkExistView(Long empId) {
		return r.checkExistView(empId);
	}
}
