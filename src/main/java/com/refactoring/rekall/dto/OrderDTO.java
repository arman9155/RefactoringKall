package com.refactoring.rekall.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.refactoring.rekall.entity.OrderDetailEntity;
import com.refactoring.rekall.entity.OrderEntity;
import com.refactoring.rekall.entity.UserEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Integer orderId; // ▷▶ id _ 자동 count _ 주문번호

// -------- ▷▶  orderId 가 외래키로 가져오는 DTO ----------------------------------------------
    private UserDTO userDTO; // ▷▶ 유저Id

// -------------------------------------------------------------------------------------------
    private String name; //  ▷▶ 수령자 이름
    private String zipCode; //  ▷▶ 우편번호
    private String address_1; //  ▷▶ 주소1
    private String address_2; //  ▷▶ 주소2
    private String phoneNb; //  ▷▶ 연락처
    private String request; //  ▷▶ 요청사항
    private LocalDateTime date= LocalDateTime.now(); //  ▷▶ 날짜
    private Integer mileage=0; //  ▷▶ 사용 마일리지
    private Integer price; //  ▷▶ 결제총액
    private String payment; //  ▷▶ 결제방식
    private String status = "결제 완료"; //  ▷▶ 주문 처리 현황
    private Integer mileage2=0; //  ▷▶ 적립 마일리지
    private String buyerNb; // ▷▶ 구매자 번호

// -------------- ▷▶ Entity -> DTO ---------------------------------------------------------
    public static OrderDTO toOrderDTO(OrderEntity orderEntity) {
        if(orderEntity == null) return null;
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setOrderId(orderEntity.getOrderId());
        orderDTO.setUserDTO(UserDTO.toUserDTO(orderEntity.getUserEntity()));
        orderDTO.setName(orderEntity.getName());
        orderDTO.setZipCode(orderEntity.getZipCode());
        orderDTO.setAddress_1(orderEntity.getAddress_1());
        orderDTO.setAddress_2(orderEntity.getAddress_2());
        orderDTO.setPhoneNb(orderEntity.getPhoneNb());
        orderDTO.setRequest(orderEntity.getRequest());
        orderDTO.setDate(orderEntity.getDate());
        orderDTO.setMileage(orderEntity.getMileage());
        orderDTO.setPrice(orderEntity.getPrice());
        orderDTO.setPayment(orderEntity.getPayment());
        orderDTO.setStatus(orderEntity.getStatus());
        orderDTO.setMileage2(orderEntity.getMileage2());
        orderDTO.setBuyerNb(orderEntity.getBuyerNb());

        return orderDTO;
    }
}
