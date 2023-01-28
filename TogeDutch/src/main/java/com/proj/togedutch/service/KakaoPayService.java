package com.proj.togedutch.service;

import com.proj.togedutch.entity.KakaoApproveRes;
import com.proj.togedutch.entity.KakaoCancelRes;
import com.proj.togedutch.entity.KakaoReadyRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Service
public class KakaoPayService {
    private static final String HOST = "https://kapi.kakao.com";
    private KakaoReadyRes kakaoReadyRes;
    private KakaoApproveRes kakaoApproveRes;
    private KakaoCancelRes kakaoCancelRes;
    /**
     * 카카오 요구 헤더값
     */
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();

        String auth = "KakaoAK " + "41b165acaabc5c3ff295dfd5559893e0";
        httpHeaders.add("Authorization", "KakaoAK " + "41b165acaabc5c3ff295dfd5559893e0");
        httpHeaders.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        httpHeaders.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        return httpHeaders;
    }
    public KakaoReadyRes payReady() {

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("partner_order_id", "1001"); // 가맹점 주문번호, 최대 100자
        params.add("partner_user_id", "gorany"); // 가맹점 회원 id, 최대 100자
        params.add("item_name", "가치더치 광고 결제"); // 상품명
        params.add("quantity", "1"); // 상품 수량
        params.add("total_amount", "5000"); // 상품 금액
        params.add("tax_free_amount", "0"); // 상품 비과세 금액
        params.add("approval_url", "http://localhost:9000/payment/success"); // 결제 승인 redirect url
        params.add("cancel_url", "http://localhost:9000/payment/cancel"); // 결제 취소 redirect url
        params.add("fail_url", "http://localhost:9000/payment/fail"); // 결제 실패 redirect url

        log.info("파트너 주문 아이디:"+ params.get("partner_order_id"));
        HttpEntity<MultiValueMap<String, String>> requestBody = new HttpEntity<MultiValueMap<String, String>>(params, this.getHeaders());
        RestTemplate restTemplate = new RestTemplate();
        try {
            kakaoReadyRes = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), requestBody, KakaoReadyRes.class);

            log.info("결제준비 응답객체:" + kakaoReadyRes);
            return kakaoReadyRes;
        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public KakaoApproveRes payApprove(String pg_token) {

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("tid", kakaoReadyRes.getTid());
        params.add("partner_order_id", "1001");
        params.add("partner_user_id", "gorany");
        params.add("pg_token", pg_token);

        log.info("파트너 주문 아이디:"+ params.get("partner_order_id"));
        HttpEntity<MultiValueMap<String, String>> requestBody = new HttpEntity<MultiValueMap<String, String>>(params, this.getHeaders());
        RestTemplate restTemplate = new RestTemplate();
        try {
            kakaoApproveRes = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), requestBody, KakaoApproveRes.class);

            log.info("결제승인 응답객체:" + kakaoApproveRes);
            return kakaoApproveRes;
        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public KakaoCancelRes payCancel(String tid){
        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("tid", tid);
        params.add("cancel_amount", "5000"); // 환불금액
        params.add("cancel_tax_free_amount", "0"); //환불 비과세 금액

        HttpEntity<MultiValueMap<String, String>> requestBody = new HttpEntity<MultiValueMap<String, String>>(params, this.getHeaders());
        RestTemplate restTemplate = new RestTemplate();

        try {
            kakaoCancelRes = restTemplate.postForObject(new URI(HOST + "/v1/payment/cancel"), requestBody, KakaoCancelRes.class);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        log.info("결제취소 응답객체:" + kakaoCancelRes);
        return kakaoCancelRes;
    }
}
