package com.flyai.safet.handler;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override // 웹 소켓 연결시
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(session.getId(), session);
        log.info("WebSocket connection established: " + session.getId());
    }

    @Override // 데이터 통신시
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("Received message from session {}: {}", session.getId(), payload);

        try {
            JSONObject obdData = new JSONObject(payload);
            String speed = obdData.getString("speed");
            String rpm = obdData.getString("rpm");

            log.info("Speed: {}, RPM: {}", speed, rpm);

            String response = "Data received and processed";
            session.sendMessage(new TextMessage(response));
        } catch (Exception e) {
            log.error("Error processing message: ", e);
            session.sendMessage(new TextMessage("Error processing data"));
        }
    }

    @Override // 웹소켓 통신 에러시
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("WebSocket error in session " + session.getId(), exception);
        session.close(CloseStatus.SERVER_ERROR);
    }

    @Override // 웹 소켓 연결 종료시
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session.getId());
        log.info("WebSocket connection closed: " + session.getId());
    }
}
