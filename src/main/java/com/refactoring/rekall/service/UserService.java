package com.refactoring.rekall.service;
/*

import com.refactoring.rekall.dto.UserDTO;
import com.refactoring.rekall.entity.UserEntity;
import com.refactoring.rekall.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public UserEntity save(UserDTO userDTO) {
        validationDuplicateUser(userDTO);
        return userRepository.save(UserEntity.toUserEntity(userDTO));
    }

    private void validationDuplicateUser(UserDTO userDTO) {
        List<UserEntity> userEntity = userRepository.findAll();
        for(UserEntity entity : userEntity) {
            if(entity.getUserId() == userDTO.getUserId()) {
                throw new IllegalStateException("이미 사용중인 아이디입니다.");
            }
        }
    }
}
1번 */

import com.refactoring.rekall.dto.UserDTO;
import com.refactoring.rekall.entity.UserEntity;
import com.refactoring.rekall.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private UserRepository userRepository;

    //회원가입
    @Transactional
    public String signUp(UserDTO userDTO) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // password를 암호화 한 뒤 table에 저장

        return userRepository.save(UserEntity.toUserEntity(userDTO)).getUserId();
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // 로그인을 하기 위해 가입된 user정보를 조회하는 메서드

        Optional<UserEntity> userEntityOptional = userRepository.findByUserId(userId);
        if(userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();

            List<GrantedAuthority> authorities = new ArrayList<>();

             if("admin".equals(userEntity.getRole())) {
                    authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
             } else {
                  authorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
             }
             UserDTO userDTO = UserDTO.toUserDTO(userEntity);
            //아이디, 비밀번호, 권한리스트를 매개변수로 UserDTO를 만들어서 반환해줌
        return new User(userDTO.getUserId(), userDTO.getPassword(), authorities);
        } //user는
        else {return null;}
    }
}
