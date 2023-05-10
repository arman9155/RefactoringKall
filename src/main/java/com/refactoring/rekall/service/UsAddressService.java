package com.refactoring.rekall.service;

import com.refactoring.rekall.dto.UsAddressDTO;
import com.refactoring.rekall.dto.UserDTO;
import com.refactoring.rekall.entity.UsAddressEntity;
import com.refactoring.rekall.repository.UsAddressRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UsAddressService {
    @Autowired
    UsAddressRepository usAddressRepository;
    @Autowired
    UserService userService;

//  ------------------------------------- ★ 전체 주소 리스트 전달★ ---------------------------------------------------------------
    public List<UsAddressDTO> getAddressList(String loginId) {
        List<UsAddressEntity> usAddressEntityList = usAddressRepository.findByUserEntityUserId(loginId);
        List<UsAddressDTO> usAddressDTOList = new ArrayList<>();

        for(UsAddressEntity usAddressEntity : usAddressEntityList) {
            if(usAddressEntity != null) {
                usAddressDTOList.add(UsAddressDTO.toUsAddressDTO(usAddressEntity));
            }
        }
        return usAddressDTOList;
    }

//  ------------------------------------- ★ 기본 배송지 설정★ ---------------------------------------------------------------
    public void findAddressId(Integer addressId, String loginId) {
        List<UsAddressDTO> usAddressDTOList = getAddressList(loginId);
        for(UsAddressDTO usAddressDTO : usAddressDTOList) {
            usAddressDTO.setStatus(null);
            usAddressRepository.save(UsAddressEntity.toUsAddressEntity(usAddressDTO));
        }

        // 선택 값을 기본배송지로 status 변경
        UsAddressDTO usAddressDTO = getAddress(addressId);
        usAddressDTO.setStatus("기본배송지");
        usAddressRepository.save(UsAddressEntity.toUsAddressEntity(usAddressDTO));
    }

//  ------------------------------------- ★ addressId에 맞는 배송지★ ---------------------------------------------------------------
    public UsAddressDTO getAddress(Integer addressId) {
        Optional<UsAddressEntity> optionalUsAddressEntity = usAddressRepository.findById(addressId);
        if(optionalUsAddressEntity.isPresent()) {
            UsAddressEntity usAddressEntity = optionalUsAddressEntity.get();
            UsAddressDTO usAddressDTO= UsAddressDTO.toUsAddressDTO(usAddressEntity);
            return usAddressDTO;
        }
        return null;
    }

//  ------------------------------------- ★ 배송지 저장★ ---------------------------------------------------------------
    public void saveAddress(UsAddressDTO usAddressDTO, String loginId) {
        UserDTO userDTO = userService.findByUserID(loginId);
        usAddressDTO.setUserDTO(userDTO);

        usAddressRepository.save(UsAddressEntity.toUsAddressEntity(usAddressDTO));
    }

//  ------------------------------------- ★ 배송지 삭제★ ---------------------------------------------------------------
    public void addressDel(Integer addressId) {
        usAddressRepository.deleteById(addressId);
    }


}
