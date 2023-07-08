package com.refactoring.rekall.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.refactoring.rekall.Auth;
import com.refactoring.rekall.dto.UserDTO;
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

import static com.refactoring.rekall.Auth.Role.USER;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "user")
public class UserEntity {
    @Id @Column(length = 15)
    private String userId; //  ▷▶ 유저ID

// -------- ▷▶ userId 를 사용하는 Entity ----------------------------------------------
    @OneToMany(mappedBy = "userEntity" , fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JsonIgnore
    private List<UserDelEntity> userDelEntities = new ArrayList<>();
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<UsAddressEntity> usAddressEntities = new ArrayList<>();
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderEntity> orderEntities = new ArrayList<>();
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<RefundEntity> refundEntities = new ArrayList<>();
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProductQEntity> productQEntities = new ArrayList<>();
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ReviewEntity> reviewEntities = new ArrayList<>();
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ReviewCmtEntity> reviewCmtEntities = new ArrayList<>();
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CartEntity> cartEntities = new ArrayList<>();
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<WishListEntity> wishListEntities = new ArrayList<>();
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<UsQEntity> usQEntities = new ArrayList<>();
// -------------------------------------------------------------------------------------------

    @Column(nullable = false, length = 10)
    private String name; //  ▷▶  이름
    @Column(nullable = false, length = 150)
    private String password; //  ▷▶  비밀번호
    @Column(nullable = false, length = 15)
    private String phoneNb; //  ▷▶  핸드폰번호
    @Column(nullable = false, length = 40)
    private String email; //  ▷▶ 이메일
    @Column(length = 20)
    private String birthday; //  ▷▶ 생일
    @Column(length = 30)
    private String root; //  ▷▶ 방문경로
    @Column(length = 1)
    private String eventagree; //  ▷▶ 광고성 수신 여부 -> Y / N
    @Enumerated(EnumType.STRING)
    private Auth.Role role = USER; //  ▷▶ ID 역할
    @Column(length = 8)
    private Integer mileage = 3000; //  ▷▶ 마일리지
    @CreationTimestamp // 생성일시
    private LocalDateTime date= LocalDateTime.now(); // ▷▶ 날짜
    @Column(length = 20)
    private String status= "활동계정"; //  ▷▶ 상태

// ------------ ▷▶ 예비컬럼----------------------------------------------------------------------------
    @Column(length = 50)
    private String tmp_2;

// -------------- ▷▶ DTO -> Entity --------------------------------------------------------
    public static UserEntity toUserEntity(UserDTO userDTO) {
        if(userDTO == null) return null;
        UserEntity userEntity = new UserEntity();

        userEntity.setUserId(userDTO.getUserId());
        userEntity.setName(userDTO.getName());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setPhoneNb(userDTO.getPhoneNb());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setBirthday(userDTO.getBirthday());
        userEntity.setRoot(userDTO.getRoot());
        userEntity.setEventagree(userDTO.getEventagree());
        userEntity.setRole(userDTO.getRole());
        userEntity.setMileage(userDTO.getMileage());
        userEntity.setDate(userDTO.getDate());
        userEntity.setStatus(userDTO.getStatus());

        return userEntity;
    }
/*
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }*/
}
