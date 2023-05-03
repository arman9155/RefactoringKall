package com.refactoring.rekall.entity;

import com.refactoring.rekall.dto.UsAddressDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;


@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "us_address")
public class UsAddressEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId; // ▷▶ id _ 자동 count

// -------- ▷▶  address 가 외래키로 가져오는 Entity ----------------------------------------------
    @JoinColumn(name = "userId")
    @ManyToOne
    private UserEntity userEntity; // ▷▶ 유저Id
// -------------------------------------------------------------------------------------------

    @Column(length = 10)
    private String status = "false"; // ▷▶  상태 ( 기본배송지 / 다른배송지인지)
    @Column(nullable = false, length = 10)
    private String name; // ▷▶ 수령자 이름
    @Column(length = 30)
    private String addressName; // ▷▶ 배송지명 (집 / 회사 등..)
    @Column(nullable = false, length = 15)
    private String phoneNb; // ▷▶ 수령자 연락처
    @Column(nullable = false, length = 10)
    private String zipCode; // ▷▶ 우편번호
    @Column(nullable = false, length = 100)
    private String address_1; // ▷▶ 주소1
    @Column(length = 100)
    private String address_2; // ▷▶ 주소2

// ------------ ▷▶ 예비컬럼----------------------------------------------------------------------------
    @Column(length = 50)
    private String tmp_1;
    @Column(length = 50)
    private String tmp_2;

// -------------- ▷▶ DTO -> Entity --------------------------------------------------------
    public static UsAddressEntity toUsAddressEntity(UsAddressDTO usAddressDTO) {
        if(usAddressDTO == null) return null;
        UsAddressEntity usAddressEntity = new UsAddressEntity();

        usAddressEntity.setAddressId(usAddressDTO.getAddressId());
        usAddressEntity.setUserEntity(UserEntity.toUserEntity(usAddressDTO.getUserDTO()));
        usAddressEntity.setStatus(usAddressDTO.getStatus());
        usAddressEntity.setName(usAddressDTO.getName());
        usAddressEntity.setAddressName(usAddressDTO.getAddressName());
        usAddressEntity.setPhoneNb(usAddressDTO.getPhoneNb());
        usAddressEntity.setZipCode(usAddressDTO.getZipCode());
        usAddressEntity.setAddress_1(usAddressDTO.getAddress_1());
        usAddressEntity.setAddress_2(usAddressDTO.getAddress_2());

        return usAddressEntity;
    }
}
