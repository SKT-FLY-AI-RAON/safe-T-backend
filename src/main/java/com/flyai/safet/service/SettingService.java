package com.flyai.safet.service;

import com.flyai.safet.dto.SettingDto;
import com.flyai.safet.entity.Setting;
import com.flyai.safet.entity.User;
import com.flyai.safet.exception.NotFoundException;
import com.flyai.safet.repository.SettingRepository;
import com.flyai.safet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SettingService {
    private final UserRepository userRepository;
    private final SettingRepository settingRepository;

    @Transactional
    public void saveSetting(SettingDto.SettingRequestDto requestDto){
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(
                () -> new NotFoundException("존재하지 않은 사용자입니다.")
        );

        Setting setting = Setting.builder().user(user).video_option(requestDto.isVideoOption()).build();
        settingRepository.save(setting);

    }
}
