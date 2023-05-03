package com.refactoring.rekall.dto;

import com.refactoring.rekall.entity.UsAddressEntity;
import com.refactoring.rekall.entity.UserEntity;
import lombok.*;

import javax.persistence.*;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UsAddressDTO {

    private Integer addressId; // ▷▶ id _ 자동 count

    // -------- ▷▶  address 가 외래키로 가져오는 DTO ----------------------------------------------
    private UserDTO userDTO; // ▷▶ 유저Id
// -------------------------------------------------------------------------------------------

    private String status = "false"; // ▷▶  상태 ( 기본배송지 / 다른배송지인지)
    private String name; // ▷▶ 수령자 이름
    private String addressName; // ▷▶ 배송지명 (집 / 회사 등..)
    private String phoneNb; // ▷▶ 수령자 연락처
    private String zipCode; // ▷▶ 우편번호
    private String address_1; // ▷▶ 주소1
    private String address_2; // ▷▶ 주소2

// -------------- ▷▶ Entity -> DTO ---------------------------------------------------------
    public static UsAddressDTO toUsAddressDTO(UsAddressEntity usAddressEntity) {
        if(usAddressEntity == null) return null;
        UsAddressDTO usAddressDTO = new UsAddressDTO();

        usAddressDTO.setAddressId(usAddressEntity.getAddressId());
        usAddressDTO.setUserDTO(UserDTO.toUserDTO(usAddressEntity.getUserEntity()));
        usAddressDTO.setStatus(usAddressEntity.getStatus());
        usAddressDTO.setName(usAddressEntity.getName());
        usAddressDTO.setAddressName(usAddressEntity.getAddressName());
        usAddressDTO.setPhoneNb(usAddressEntity.getPhoneNb());
        usAddressDTO.setZipCode(usAddressEntity.getZipCode());
        usAddressDTO.setAddress_1(usAddressEntity.getAddress_1());
        usAddressDTO.setAddress_2(usAddressEntity.getAddress_2());

        return usAddressDTO;
    }
}
