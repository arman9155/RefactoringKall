package com.refactoring.rekall.dto;

import com.refactoring.rekall.entity.OrderDetailEntity;
import com.refactoring.rekall.entity.RefundEntity;
import com.refactoring.rekall.entity.UserEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RefundDTO {

    private Integer refundId; // ▷▶ id _ 자동 count

    // -------- ▷▶  refund 가 외래키로 가져오는 DTO ----------------------------------------------
    private OrderDetailDTO orderDetailDTO; // ▷▶ 주문상세번호
    private UserDTO userDTO; // ▷▶ 유저Id
// -------------------------------------------------------------------------------------------

    private String title; // ▷▶ 제목
    private String content; // ▷▶ 환불사유
    private String image_1; // ▷▶ 환불 이미지1
    private String image_2; // ▷▶ 환불 이미지2
    private String status = "반품 요청";   // ▷▶ 환불 처리내역
    private LocalDateTime date = LocalDateTime.now();  // ▷▶ 날짜

// -------------- ▷▶ Entity -> DTO ---------------------------------------------------------
    public static RefundDTO toRefundDTO(RefundEntity refundEntity) {
        if(refundEntity == null) return null;
        RefundDTO refundDTO = new RefundDTO();

        refundDTO.setRefundId(refundEntity.getRefundId());
        refundDTO.setOrderDetailDTO(OrderDetailDTO.toOrderDeatilDTO(refundEntity.getOrderDetailEntity()));
        refundDTO.setUserDTO(UserDTO.toUserDTO(refundEntity.getUserEntity()));
        refundDTO.setTitle(refundEntity.getTitle());
        refundDTO.setContent(refundEntity.getContent());
        refundDTO.setImage_1(refundEntity.getImage_1());
        refundDTO.setImage_2(refundEntity.getImage_2());
        refundDTO.setStatus(refundEntity.getStatus());
        refundDTO.setDate(refundEntity.getDate());

        return refundDTO;
    }
}
