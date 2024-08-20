package com.flyai.safet.controller;


import com.flyai.safet.dto.SettingDto;
import com.flyai.safet.entity.ApiResponse;
import com.flyai.safet.service.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/setting")
@RequiredArgsConstructor
public class SettingController {
    private final SettingService settingService;


    @PatchMapping("")
    public ApiResponse<String> saveSetting(@RequestBody SettingDto.SettingRequestDto requestDto) {
        settingService.saveSetting(requestDto);
        return new ApiResponse<>("설정이 성공적으로 수행되었습니다.");
    }
}
