package com.refactoring.rekall.service;

import com.refactoring.rekall.Auth;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.refactoring.rekall.Auth.Role.ADMIN;
import static com.refactoring.rekall.Auth.Role.USER;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserDelRepository userDelRepository;

    //  ---------------------------- ★ 회원정보 저장 ★ -------------------------------------------------------------------------
    public String signUp(UserDTO userDTO) {
        if (userDTO.getPassword().contains("admin123")) {
            userDTO.setRole(ADMIN);
        } else userDTO.setRole(USER);
        if (userDTO.getBirthday() == null || userDTO.getBirthday().equals("")) {
            userDTO.setBirthday(null);
        } else {
            try {
                userDTO.setBirthday((userDTO.getBirthday()));
            } catch (DateTimeParseException e) {
                // 유효한 날짜 형식이 아닌 경우 처리할 내용
                System.out.println("exception");
            }
        }
/*        System.out.println("비밀번호 암호화");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));*/

        // password를 암호화 한 뒤 table에 저장

//        System.out.println(userDTO.getRole());
        return userRepository.save(UserEntity.toUserEntity(userDTO)).getUserId();
    }

    //  ---------------------------- ★ id/pw 기반 회원 정보 찾기 ★ -------------------------------------------------------------------------
    public UserDTO findID(String userId, String password) {
        System.out.println("findId 들어옴");
        UserEntity userEntity = userRepository.findByUserIdAndPassword(userId, password);
        if (userEntity == null) {
            return null;
        } else return UserDTO.toUserDTO(userEntity);
    }

    //  ---------------------------- ★ id 기반 회원 정보 찾기 ★ -------------------------------------------------------------------------
    public UserDTO findByUserID(String userId) {
        System.out.println("여기?");
        Optional<UserEntity> optionaluserEntity = userRepository.findByUserId(userId);
        if (optionaluserEntity.isPresent()) {
            UserEntity userEntity = optionaluserEntity.get();
            return UserDTO.toUserDTO(userEntity);
        }
        return null;
    }


//  ---------------------------- ★ 전체 유저 리스트 ★ -------------------------------------------------------------------------
    public List<UserDTO> getList(String status) {
        List<UserEntity> userEntityList;
        if (status.equals("all")) {
            userEntityList = userRepository.findAll();
        } else {
            userEntityList = userRepository.findByStatus(status);
        }
        List<UserDTO> userDTOList = new ArrayList<>();
        for (UserEntity user : userEntityList) {
            if (user != null) {
                userDTOList.add(UserDTO.toUserDTO(user));
            }
        }
        return userDTOList;
    }

//  ---------------------------- ★ 회원 탈퇴★ -------------------------------------------------------------------------
//  ---------------------------- ★ 탈퇴 - 상태 변경 / us_del 이동 ★ -------------------------------------------------------------------------
    public void deleteUser(UserDelDTO userDelDTO, String userId) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUserId(userId);
        if(optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            userEntity.setStatus("탈퇴계정");
            userRepository.save(userEntity);
            // userEntity에 status 변경

            userDelDTO.setUserDTO(UserDTO.toUserDTO(userEntity));
        }

        userDelRepository.save(UserDelEntity.toUserDelEntity(userDelDTO));
    }

//  ---------------------------- ★ 관리자 탈퇴처리 ★ -------------------------------------------------------------------------
    public void a_deleteUser(List<String> userIds) {
        for(String user : userIds) {
            UserDelDTO userDelDTO = new UserDelDTO();
            userDelDTO.setText("관리자가 탈퇴처리함");
            deleteUser(userDelDTO , user);
        }
    }

    public String idDupC(String id) {
        Optional<UserEntity> userEntity = userRepository.findByUserId(id);
        if(userEntity.isEmpty()) return "T";
        else return "F";
    }
}