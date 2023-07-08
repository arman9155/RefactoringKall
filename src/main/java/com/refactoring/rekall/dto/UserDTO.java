package com.refactoring.rekall.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.refactoring.rekall.Auth;
import com.refactoring.rekall.entity.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
/*import org.springframework.security.crypto.password.PasswordEncoder;*/

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.refactoring.rekall.Auth.Role.USER;

@Getter @Setter
@ToString
@NoArgsConstructor
public class UserDTO {


    @NotBlank(message = "아이디를 입력해주세요.")
    private String userId; //  ▷▶ 유저ID
    @NotBlank(message = "이름을 입력해주세요.")
    private String name; //  ▷▶  이름
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Length(min=7, max=16, message = "비밀번호는 7자 이상, 16자 이하로 입력해주세요.")
    private String password; //  ▷▶  비밀번호
    @NotBlank(message = "핸드폰번호를 입력해주세요.")
    private String phoneNb; //  ▷▶  핸드폰번호
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email; //  ▷▶ 이메일
    private String birthday; //  ▷▶ 생일
    private String root; //  ▷▶ 방문경로
    private String eventagree; //  ▷▶ 광고성 수신 여부 -> Y / Nsql delete 문
    private Auth.Role role = USER; //  ▷▶ ID 역할
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



//// -------------- ▷▶ set --------------------------------------------------------
//    public static UserDTO setUserDTO(UserDTO userDTO) {
//        if(userDTO == null) return null;
//        UserDTO userDTO1 = new UserDTO();
//
//        userDTO1.setUserId(userDTO.getUserId());
//        userDTO1.setName(userDTO.getName());
//        userDTO1.setPassword(userDTO.getPassword());
//        userDTO1.setPhoneNb(userDTO.getPhoneNb());
//        userDTO1.setEmail(userDTO.getEmail());
//        userDTO1.setBirthday(userDTO.getBirthday());
//        userDTO1.setRoot(userDTO.getRoot());
//        userDTO1.setEventagree(userDTO.getEventagree());
//        userDTO1.setRole(userDTO.getRole());
//        userDTO1.setMileage(userDTO.getMileage());
//        userDTO1.setDate(userDTO.getDate());
//        userDTO1.setStatus(userDTO.getStatus());
//
//        return userDTO1;
//    }

// -------------- ▷▶ 비밀번호 암호화 / role 변경 ----------------------------------------=--------
/*    public static UserDTO createUser(UserDTO userDTO, PasswordEncoder passwordEncoder) {
        if(userDTO == null) return null;

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        /// 암호화 처리
        if(userDTO.getDate().equals("2023-02-03")) {
            userDTO.setRole("admin");
        }

        return userDTO;
    }*/
//// -------------- ▷▶ 암호화 2번----------------------------------------=--------
//    @Builder
//    public UserDTO(String userId, String name, String password) {
//        this.userId = userId;
//        this.name = name;
//        this.password = password;
//    }

}

