package com.flyai.safet.controller;

import com.flyai.safet.entity.ApiResponse;
import com.flyai.safet.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sms")
@RequiredArgsConstructor
public class SmsController {

    private final SmsService smsService;

    @PostMapping("/alert")
    public ApiResponse<String> sendEmergencyAlert(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam Long userId) {

        String result = smsService.sendEmergencySms(latitude, longitude, userId);

        return new ApiResponse<>(result);
    }
}
