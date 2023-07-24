package com.refactoring.rekall.dto;

import lombok.*;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KakaoReadyDTO {
    private String tid; // 결제 고유 번호
    private String next_redirect_app_url;
    private String next_redirect_mobile_url;
    private String next_redirect_pc_url; // pc 웹일 경우 받는 결제 페이지
    private String android_app_scheme;
    private String ios_app_scheme;
    private String created_at;
}
