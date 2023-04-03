/*
package com.refactoring.rekall.service;

import com.refactoring.rekall.entity.UserEntity;
import com.refactoring.rekall.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserPrincipalDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public UserPrincipalDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = this.userRepository.findByUserName(username);
        if(userEntity == null) {
            throw new UsernameNotFoundException("해당하는 사용자가 없습니다.");
        }

        return new org.springframework.security.core.userdetails.User(userEntity.getUserId(), userEntity.getPassword(), getAuthorities(userEntity));
    }

    public Collection<? extends GrantedAuthority> getAuthorities(UserEntity user) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        if(user.getRole() == "admin") {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return authorities;
    }


}
*/
