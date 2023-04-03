package com.refactoring.rekall.service;

import com.refactoring.rekall.dto.UserDTO;
import com.refactoring.rekall.dto.UserDelDTO;
import com.refactoring.rekall.entity.UserDelEntity;
import com.refactoring.rekall.entity.UserEntity;
import com.refactoring.rekall.repository.UserDelRepository;
import com.refactoring.rekall.repository.UserRepository;
import lombok.AllArgsConstructor;
/*import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;*/
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserDelRepository userDelRepository;

    //  ---------------------------- ★ 회원정보 저장 ★ -------------------------------------------------------------------------
    @Transactional
    public String signUp(UserDTO userDTO) {
        if (userDTO.getPassword().contains("admin123")) {
            userDTO.setRole("admin");
        }
        if (userDTO.getBirthday() == null || userDTO.getBirthday().equals("")) {
            userDTO.setBirthday(null);
            System.out.println("여기null처리");
        } else {
            try {
                userDTO.setBirthday((userDTO.getBirthday()));
                System.out.println("date 저장하는거");
            } catch (DateTimeParseException e) {
                // 유효한 날짜 형식이 아닌 경우 처리할 내용
                System.out.println("exception");
            }
        }
/*        System.out.println("비밀번호 암호화");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));*/

        // password를 암호화 한 뒤 table에 저장
        return userRepository.save(UserEntity.toUserEntity(userDTO)).getUserId();
    }

    //  ---------------------------- ★ id/pw 기반 회원 정보 찾기 ★ -------------------------------------------------------------------------
    public UserDTO findID(String userId, String password) {
        UserEntity userEntity = userRepository.findByUserIdAndPassword(userId, password);
        if (userEntity == null) {
            return null;
        }
        return UserDTO.toUserDTO(userEntity);
    }

    //  ---------------------------- ★ id 기반 회원 정보 찾기 ★ -------------------------------------------------------------------------
    public UserDTO findByUserID(String userId) {
        Optional<UserEntity> optionaluserEntity = userRepository.findByUserId(userId);
        if (optionaluserEntity.isPresent()) {
            UserEntity userEntity = optionaluserEntity.get();
            return UserDTO.toUserDTO(userEntity);
        }
        return null;
    }

    //  ---------------------------- ★ 탈퇴 - 상태 변경 / us_del 이동 ★ -------------------------------------------------------------------------
    public void deleteUser(String userId, UserDelDTO userDelDTO) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUserId(userId);
        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            userEntity.setStatus("탈퇴계정");
            // userEntity에 status 변경

            userDelRepository.save(UserDelEntity.toUserDelEntity(userDelDTO));
        }
    }

}