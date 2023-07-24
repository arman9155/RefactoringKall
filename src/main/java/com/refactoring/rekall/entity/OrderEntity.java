package com.refactoring.rekall.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.refactoring.rekall.dto.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "us_order")
public class OrderEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId; // ▷▶ id _ 자동 count _ 주문번호


// -------- ▷▶  orderId 가 외래키로 가져오는 Entity ----------------------------------------------
    @JoinColumn(name = "userId")
    @ManyToOne
    private UserEntity userEntity; // ▷▶ 유저Id
// -------- ▷▶  orderId 를 사용하는 Entity ----------------------------------------------
    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderDetailEntity> orderDetailEntities = new ArrayList<>();
// -------------------------------------------------------------------------------------------

    @Column(nullable = false, length = 10)
    private String name; //  ▷▶ 수령자 이름
    @Column(nullable = false, length = 10)
    private String zipCode; //  ▷▶ 우편번호
    @Column(nullable = false, length = 100)
    private String address_1; //  ▷▶ 주소1
    @Column(length = 100)
    private String address_2; //  ▷▶ 주소2
    @Column(nullable = false, length = 15)
    private String phoneNb; //  ▷▶ 연락처
    @Column(length = 100)
    private String request; //  ▷▶ 요청사항
    @CreationTimestamp // 생성일시
    private LocalDateTime date= LocalDateTime.now(); //  ▷▶ 날짜
    @Column(length = 8)
    private Integer mileage=0; //  ▷▶ 사용 마일리지
    @Column(nullable = false, length = 8)
    private Integer price; //  ▷▶ 결제총액
    @Column(nullable = false, length = 40)
    private String payment; //  ▷▶ 결제방식
    @Column(length = 40)
    private String status = "결제 완료"; //  ▷▶ 주문 처리 현황
    @Column(length = 8)
    private Integer mileage2=0; //  ▷▶ 적립 마일리지
    @Column(length=15)
    private String buyerNb; // ▷▶ 구매자 번호

// -------------- ▷▶ DTO -> Entity --------------------------------------------------------
    public static OrderEntity toOrderEntity(OrderDTO orderDTO) {
        if(orderDTO == null) return null;

        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setOrderId(orderDTO.getOrderId());
        orderEntity.setUserEntity(UserEntity.toUserEntity(orderDTO.getUserDTO()));
        orderEntity.setName(orderDTO.getName());
        orderEntity.setZipCode(orderDTO.getZipCode());
        orderEntity.setAddress_1(orderDTO.getAddress_1());
        orderEntity.setAddress_2(orderDTO.getAddress_2());
        orderEntity.setPhoneNb(orderDTO.getPhoneNb());
        orderEntity.setRequest(orderDTO.getRequest());
        orderEntity.setDate(orderDTO.getDate());
        orderEntity.setMileage(orderDTO.getMileage());
        orderEntity.setPrice(orderDTO.getPrice());
        orderEntity.setPayment(orderDTO.getPayment());
        orderEntity.setStatus(orderDTO.getStatus());
        orderEntity.setMileage2(orderDTO.getMileage2());
        orderEntity.setBuyerNb(orderDTO.getBuyerNb());

        return orderEntity;
    }
}