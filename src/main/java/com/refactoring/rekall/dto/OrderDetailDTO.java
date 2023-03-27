package com.refactoring.rekall.dto;

import com.refactoring.rekall.entity.OrderDetailEntity;
import lombok.*;
import javax.persistence.*;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDTO {
    private Integer odetailId; // ▷▶ id _ 자동 count  _ 주문상세번호

    // -------- ▷▶   odetailId 가 외래키로 가져오는 DTO ----------------------------------------------
    private OrderDTO orderDTO; // ▷▶ 주문번호
    private ProductDTO productDTO; // ▷▶ 상품번호
// -------------------------------------------------------------------------------------------

    private String option_sheet; //  ▷▶  시트 옵션
    private String option_shape; //  ▷▶  모양 옵션
    private String option_cream; //  ▷▶  크림 색상 옵션
    private String option_lettering; // ▷▶ 레터링 옵션
    private String option_size; // ▷▶ 사이즈 옵션
    private String option_image; // ▷▶ 이미지 옵션
    private Integer amount;  // ▷▶ 개수
    private Integer price;  // ▷▶ 가격
    private String status;  // ▷▶ 상태 (반품안내칸)

// -------------- ▷▶ Entity -> DTO ---------------------------------------------------------
    public static OrderDetailDTO toOrderDeatilDTO(OrderDetailEntity orderDetailEntity) {
        if(orderDetailEntity == null) return null;
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();

        orderDetailDTO.setOdetailId(orderDetailEntity.getOdetailId());
        orderDetailDTO.setOrderDTO(OrderDTO.toOrderDTO(orderDetailEntity.getOrderEntity()));
        orderDetailDTO.setProductDTO(ProductDTO.toProductDTO(orderDetailEntity.getProductEntity()));
        orderDetailDTO.setOption_sheet(orderDetailEntity.getOption_sheet());
        orderDetailDTO.setOption_shape(orderDetailEntity.getOption_shape());
        orderDetailDTO.setOption_cream(orderDetailEntity.getOption_cream());
        orderDetailDTO.setOption_lettering(orderDetailEntity.getOption_lettering());
        orderDetailDTO.setOption_size(orderDetailEntity.getOption_size());
        orderDetailDTO.setOption_image(orderDetailEntity.getOption_image());
        orderDetailDTO.setAmount(orderDetailEntity.getAmount());
        orderDetailDTO.setPrice(orderDetailEntity.getPrice());
        orderDetailDTO.setStatus(orderDetailEntity.getStatus());

        return orderDetailDTO;
    }
}
