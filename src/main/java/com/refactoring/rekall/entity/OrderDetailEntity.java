package com.refactoring.rekall.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.refactoring.rekall.dto.OrderDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "us_odetail")
public class OrderDetailEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer odetailId; // ▷▶ id _ 자동 count  _ 주문상세번호

// -------- ▷▶  odetailId 를 사용하는 Entity ----------------------------------------------
    @OneToMany(mappedBy = "orderDetailEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderEntity> orderEntities = new ArrayList<>();
    @OneToMany(mappedBy = "orderDetailEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<RefundEntity> refundEntities = new ArrayList<>();
    @OneToMany(mappedBy = "orderDetailEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ReviewEntity> reviewEntities = new ArrayList<>();

// -------- ▷▶   odetailId 가 외래키로 가져오는 Entity ----------------------------------------------
    @ManyToOne
    @JoinColumn(name = "productId")
    private ProductEntity productEntity; // ▷▶ 상품번호
// -------------------------------------------------------------------------------------------

    @Column(length = 20)
    private String option_sheet; //  ▷▶  시트 옵션
    @Column(length = 20)
    private String option_shape; //  ▷▶  모양 옵션
    @Column(length = 20)
    private String option_cream; //  ▷▶  크림 색상 옵션
    @Column(length = 30)
    private String option_lettering; // ▷▶ 레터링 옵션
    @Column(length = 20)
    private String option_size; // ▷▶ 사이즈 옵션
    @Column(length = 100)
    private String option_image; // ▷▶ 이미지 옵션
    @Column(nullable = false, length = 3)
    private Integer amount;  // ▷▶ 개수
    @Column(nullable = false, length = 8)
    private Integer price;  // ▷▶ 가격
    @Column(length = 20)
    private String status;  // ▷▶ 상태 (반품안내칸)

// ------------ ▷▶ 예비컬럼----------------------------------------------------------------------------
    @Column(length = 50)
    private String request;

// -------------- ▷▶ DTO -> Entity --------------------------------------------------------
    public static OrderDetailEntity toOrderDetailEntity(OrderDetailDTO orderdetailDTO) {
        if(orderdetailDTO == null) return null;

        OrderDetailEntity orderDetailEntity = new OrderDetailEntity();

        orderDetailEntity.setOdetailId(orderdetailDTO.getOdetailId());
        orderDetailEntity.setProductEntity(ProductEntity.toProductEntity(orderdetailDTO.getProductDTO()));
        orderDetailEntity.setOption_sheet(orderdetailDTO.getOption_sheet());
        orderDetailEntity.setOption_shape(orderdetailDTO.getOption_shape());
        orderDetailEntity.setOption_cream(orderdetailDTO.getOption_cream());
        orderDetailEntity.setOption_lettering(orderdetailDTO.getOption_lettering());
        orderDetailEntity.setOption_size(orderdetailDTO.getOption_size());
        orderDetailEntity.setOption_image(orderdetailDTO.getOption_image());
        orderDetailEntity.setAmount(orderdetailDTO.getAmount());
        orderDetailEntity.setPrice(orderdetailDTO.getPrice());
        orderDetailEntity.setStatus(orderdetailDTO.getStatus());
        orderDetailEntity.setRequest(orderDetailEntity.getRequest());

        return orderDetailEntity;
    }
}
