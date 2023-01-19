package com.proj.togedutch.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.proj.togedutch.entity.Message;
import com.proj.togedutch.entity.SmsResponse;
import com.proj.togedutch.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
public class SmsController {
    @Autowired
    SmsService smsService;
    @PostMapping("/sms/send")
    public SmsResponse sendSms(@RequestBody Message message) throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        SmsResponse response = smsService.sendSms(message);
        return response;
    }
}
