package com.refactoring.rekall.entity;

import com.refactoring.rekall.dto.RefundDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "refund")
public class RefundEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer refundId; // ▷▶ id _ 자동 count

// -------- ▷▶  refund 가 외래키로 가져오는 Entity ----------------------------------------------
    @JoinColumn(name = "odetailId")
    @ManyToOne
    private OrderDetailEntity orderDetailEntity; // ▷▶ 주문상세번호
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity; // ▷▶ 유저Id
// -------------------------------------------------------------------------------------------

    @Column(nullable = false, length = 50)
    private String title; // ▷▶ 제목
    @Column(nullable = false, length = 500)
    private String content; // ▷▶ 환불사유
    @Column(length = 100)
    private String image_1; // ▷▶ 환불 이미지1
    @Column(length = 100)
    private String image_2; // ▷▶ 환불 이미지2
    @Column(length = 30)
    private String status = "반품 요청";   // ▷▶ 환불 처리내역
    @CreationTimestamp // 생성일시
    private LocalDateTime date = LocalDateTime.now();  // ▷▶ 날짜

// ------------ ▷▶ 예비컬럼----------------------------------------------------------------------------
    @Column(length = 50)
    private String tmp_2;

// -------------- ▷▶ DTO -> Entity --------------------------------------------------------
    public static RefundEntity toRefundEntity(RefundDTO refundDTO) {
        if(refundDTO == null) return null;

        RefundEntity refundEntity = new RefundEntity();

        refundEntity.setRefundId(refundDTO.getRefundId());
        refundEntity.setOrderDetailEntity(OrderDetailEntity.toOrderDetailEntity(refundDTO.getOrderDetailDTO()));
        refundEntity.setUserEntity(UserEntity.toUserEntity(refundDTO.getUserDTO()));
        refundEntity.setTitle(refundDTO.getTitle());
        refundEntity.setContent(refundDTO.getContent());
        refundEntity.setImage_1(refundDTO.getImage_1());
        refundEntity.setImage_2(refundDTO.getImage_2());
        refundEntity.setStatus(refundDTO.getStatus());
        refundEntity.setDate(refundDTO.getDate());

        return refundEntity;
    }
}
