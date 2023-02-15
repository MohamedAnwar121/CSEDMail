package com.example.mailbe.Schedule;

import com.example.mailbe.Service.MailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MidNightJob {

    private final MailService mailService;

    public MidNightJob(MailService mailService) {
        this.mailService = mailService;
    }

    @Scheduled(cron = "@midnight")
    public void trackOverduePayments() {
        mailService.deleteOldMails();
    }
}
