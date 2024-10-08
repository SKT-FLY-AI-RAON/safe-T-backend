package com.flyai.safet.exception;

import com.flyai.safet.entity.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;


@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    //400
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ApiResponse<String>> handleBadRequestException(BadRequestException e) {
        ApiResponse<String> httpRes = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        log.info("BAD_REQUEST 입니다");
        return new ResponseEntity<>(httpRes, HttpStatus.BAD_REQUEST);
    }

    //401
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity<ApiResponse<String>> handleUnauthorizedException(UnauthorizedException e) {
        ApiResponse<String> httpRes = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        log.info("UNAUTHORIZED 입니다");
        return new ResponseEntity<>(httpRes, HttpStatus.UNAUTHORIZED);
    }

    //500
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ServerErrorException.class})
    public ResponseEntity<ApiResponse<String>> handleServerErrorException(ServerErrorException e) {
        ApiResponse<String> httpRes = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        log.info("INTERNAL_SERVER_ERROR 입니다");
        return new ResponseEntity<>(httpRes, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    //MultipartFile 잘못 전달받았을 경우
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MultipartException.class})
    public ResponseEntity<ApiResponse<String>> handleMultipartException(MultipartException e) {
        ApiResponse<String> httpRes = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "잘못된 형식의 파일입니다.");
        return new ResponseEntity<>(httpRes, HttpStatus.BAD_REQUEST);
    }
}
