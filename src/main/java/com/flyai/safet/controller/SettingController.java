package com.flyai.safet.controller;


import com.flyai.safet.dto.SettingDto;
import com.flyai.safet.entity.ApiResponse;
import com.flyai.safet.entity.Setting;
import com.flyai.safet.service.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/setting")
@RequiredArgsConstructor
public class SettingController {
    private final SettingService settingService;


    @GetMapping("")
    public ApiResponse<SettingDto.SettingResponseDto> getSetting(@RequestParam Long userId) {
        Setting setting = settingService.getSetting(userId);
        SettingDto.SettingResponseDto settingDto = SettingDto.SettingResponseDto.builder()
                .warningOption(setting.getWarningOption())
                .agreement(setting.isAgreement())
                .build();
        return new ApiResponse<>(settingDto);
    }

    @PatchMapping("")
    public ApiResponse<String> saveSetting(@RequestBody SettingDto.SettingRequestDto requestDto) {
        settingService.saveSetting(requestDto);
        return new ApiResponse<>("설정이 성공적으로 수행되었습니다.");
    }

    @PatchMapping("/agreement")
    public ApiResponse<String> saveAgreement(@RequestBody SettingDto.AgreementRequestDto requestDto) {
        settingService.saveAgreement(requestDto);
        return new ApiResponse<>("개인정보 동의 설정이 완료되었습니다.");
    }
}
