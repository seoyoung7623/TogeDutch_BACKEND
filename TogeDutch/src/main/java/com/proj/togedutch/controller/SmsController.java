package com.proj.togedutch.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.config.BaseResponseStatus;
import com.proj.togedutch.entity.Message;
import com.proj.togedutch.entity.SmsResponse;
import com.proj.togedutch.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static com.proj.togedutch.utils.ValidationRegex.isRegexPhone;

@RestController
@RequiredArgsConstructor
public class SmsController {
    @Autowired
    SmsService smsService;

    @ResponseBody
    @PostMapping("/sms/send")
    public BaseResponse<SmsResponse> sendSms(@RequestBody Message message) throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        if (!isRegexPhone(message.getTo())) {
            return new BaseResponse<>(BaseResponseStatus.POST_USERS_INVALID_PHONE);
        }
        SmsResponse response = smsService.sendSms(message);
        return new BaseResponse<>(response);
    }
}
