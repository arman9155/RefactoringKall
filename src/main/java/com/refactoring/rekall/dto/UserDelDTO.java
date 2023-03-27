package com.refactoring.rekall.dto;

import com.refactoring.rekall.entity.UserEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDelDTO {

    private Integer usdelId; // ▷▶  ID -> seq

    // -------- ▷▶ usdelId 가 외래키로 사용하는 DTO ----------------------------------------------
    private UserDTO userDTO; // ▷▶ 유저Id
// -------------------------------------------------------------------------------------------

    private String text; // ▷▶  탈퇴사유
    private LocalDateTime date = LocalDateTime.now(); // ▷▶  탈퇴일
}
