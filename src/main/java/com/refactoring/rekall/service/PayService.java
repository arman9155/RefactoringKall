package com.refactoring.rekall.service;

import com.refactoring.rekall.Auth;
import com.refactoring.rekall.dto.KakaoApproveDTO;
import com.refactoring.rekall.dto.KakaoReadyDTO;
import com.refactoring.rekall.dto.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
@RequiredArgsConstructor
public class PayService {
    static final String cid = "TC0ONETIME"; // 가맹점 테스트 코드
    static final String admin_Key = "65376ffe789c8864970ce9302f21a74f";
    private KakaoReadyDTO kakaoReady;
    @Autowired
    OrderService orderService;

    public KakaoReadyDTO kakaoPayReady(String loginId, String product, String quantity, String totalP, String orderId, String productId) {

        totalP = totalP.replace("원","").trim();

        // 카카오페이 요청 양식
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("partner_order_id", orderId);
        parameters.add("partner_user_id", loginId);
        parameters.add("item_name", product);
        parameters.add("item_code", productId);
        parameters.add("quantity", quantity);
        parameters.add("total_amount", totalP);
        parameters.add("tax_free_amount", "0");
        parameters.add("approval_url", "http://localhost:8080/kakao/success"); // 성공 시 redirect url
        parameters.add("cancel_url", "http://localhost:8080/kakao/cancel"); // 취소 시 redirect url
        parameters.add("fail_url", "http://localhost:8080/kakao/fail"); // 실패 시 redirect url

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        // 외부에 보낼 url
        kakaoReady = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/ready",
                requestEntity,
                KakaoReadyDTO.class);
        System.out.println(kakaoReady);
        return kakaoReady;
    }

    // 결제 완료 승인

    public KakaoApproveDTO approveResponse(String pgToken, String orderId, String loginId) {

        // 카카오 요청
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("tid", kakaoReady.getTid());
        parameters.add("partner_order_id", orderId);
        parameters.add("partner_user_id", loginId);
        parameters.add("pg_token", pgToken);

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        KakaoApproveDTO approveResponse = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/approve",
                requestEntity,
                KakaoApproveDTO.class);

        return approveResponse;
    }


    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();

        String auth = "KakaoAK " + admin_Key;

        httpHeaders.set("Authorization", auth);
        httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return httpHeaders;
    }


}
