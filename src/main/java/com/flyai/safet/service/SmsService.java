package com.flyai.safet.service;

import com.flyai.safet.entity.User;
import com.flyai.safet.exception.NotFoundException;
import com.flyai.safet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
@RequiredArgsConstructor
public class SmsService {

    private final UserRepository userRepository;
    private DefaultMessageService messageService;
    private final GeoCodingService geoCodingService;

    @Value("${coolsms.api-key}")
    private String apiKey;

    @Value("${coolsms.api-secret}")
    private String apiSecret;

    @Value("${coolsms.sender-number}")
    private String senderNumber;

    private final String emergencyNumber = "01037046722";

    @PostConstruct
    private void init() {
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
    }

    public String sendEmergencySms(double latitude, double longitude, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));
        // 위도, 경도를 주소로 변환
        String locationInfo = geoCodingService.getAddressFromCoordinate(latitude, longitude);
        log.info("locationInfo: {}", locationInfo);

        Message message = new Message();
        message.setFrom(senderNumber);  // 발신자 번호
        message.setTo(emergencyNumber); // 수신자 번호 (112)
        message.setText(
                "급발진 의심 상황 발생\n" +
                        "위치: " + locationInfo + "\n" +
                        "위도: " + latitude + ", 경도: " + longitude + "\n" +
                        "차량 정보: " + user.getVehicleType() + "/" + user.getVehicleNumber() + "\n" +
                        "사용자 정보: " + user.getAge() + "세 "+ user.getGender() + user.getUsername() + "\n" +
                        "속도가 비정상적으로 올라가고 있습니다.\n" +
                        "긴급 조치가 필요합니다."
        );


        try {
            SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
            return "긴급 문자 전송 성공: " + response.getMessageId();
        } catch (Exception e) {
            e.printStackTrace();
            return "긴급 문자 전송 실패: " + e.getMessage();
        }
    }
}