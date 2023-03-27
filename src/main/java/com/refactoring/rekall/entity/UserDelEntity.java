package com.refactoring.rekall.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "us_del")
public class UserDelEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer usdelId; // ▷▶  ID -> seq

// -------- ▷▶ usdelId 가 외래키로 사용하는 Entity ----------------------------------------------
    @JoinColumn(name = "userId")
    @ManyToOne
    private UserEntity userEntity; // ▷▶ 유저Id
// -------------------------------------------------------------------------------------------

    @Column(length = 500)
    private String text; // ▷▶  탈퇴사유
    @CreationTimestamp // 생성일시
    private LocalDateTime date = LocalDateTime.now(); // ▷▶  탈퇴일

}
