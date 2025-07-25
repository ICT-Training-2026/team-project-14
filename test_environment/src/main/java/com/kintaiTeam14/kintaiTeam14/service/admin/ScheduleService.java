package com.kintaiTeam14.kintaiTeam14.service.admin;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.kintaiTeam14.kintaiTeam14.form.UserEditForm;
import com.kintaiTeam14.kintaiTeam14.repository.admin.UserEditRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final UserEditRepository r;

    public void scheduleTask(LocalDateTime executeDateTime,UserEditForm f) {
        LocalDateTime now = LocalDateTime.now();
        long delay = Duration.between(now, executeDateTime).toMillis();

        executor.schedule(() -> {
            System.out.println("指定日時になりました！" + executeDateTime);
            r.userEditExe(f);
        }, delay, TimeUnit.MILLISECONDS);
    }
}