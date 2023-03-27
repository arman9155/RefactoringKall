package com.refactoring.rekall.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.refactoring.rekall.entity.*;
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
public class UserDTO {

    private String userId; //  ▷▶ 유저ID
    private String name; //  ▷▶  이름
    private String password; //  ▷▶  비밀번호
    private String phoneNb; //  ▷▶  핸드폰번호
    private String email; //  ▷▶ 이메일
    private String birthday; //  ▷▶ 생일
    private String root; //  ▷▶ 방문경로
    private String eventagree; //  ▷▶ 광고성 수신 여부 -> Y / N
    private String role = "user"; //  ▷▶ ID 역할
    private Integer mileage = 3000; //  ▷▶ 마일리지
    private LocalDateTime date= LocalDateTime.now(); // ▷▶ 날짜
    private String status= "활동계정"; //  ▷▶ 상태

// -------------- ▷▶ DTO -> Entity --------------------------------------------------------
    public static UserDTO toUserDTO(UserEntity userEntity) {
        if(userEntity == null) return null;
        UserDTO userDTO = new UserDTO();

        userDTO.setUserId(userEntity.getUserId());
        userDTO.setName(userEntity.getName());
        userDTO.setPassword(userEntity.getPassword());
        userDTO.setPhoneNb(userEntity.getPhoneNb());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setBirthday(userEntity.getBirthday());
        userDTO.setRoot(userEntity.getRoot());
        userDTO.setEventagree(userEntity.getEventagree());
        userDTO.setRole(userEntity.getRole());
        userDTO.setMileage(userEntity.getMileage());
        userDTO.setDate(userEntity.getDate());
        userDTO.setStatus(userEntity.getStatus());

        return userDTO;
    }
}
