package com.refactoring.rekall.service;

import com.refactoring.rekall.dto.UsQDTO;
import com.refactoring.rekall.dto.UserDTO;
import com.refactoring.rekall.entity.UsQEntity;
import com.refactoring.rekall.repository.UsQRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UsQService {

    @Autowired
    UsQRepository usQRepository;
    @Autowired
    UserService userService;

//  ---------------------------------- ★ 1:1 문의 저장★ ---------------------------------------------------------------
    public void sendQuestion(UsQDTO usQDTO) {
        usQRepository.save(UsQEntity.toUsQEntity(usQDTO));
    }

//  ---------------------------------- ★ 1:1 문의 리스트★ ---------------------------------------------------------------
    public List<UsQDTO> usqList(String loginId) {
        List<UsQEntity> usQEntityList = usQRepository.findByUserEntityUserIdOrderByUsqIdDesc(loginId);
        List<UsQDTO> usQDTOList = new ArrayList<>();
        for(UsQEntity usQEntity : usQEntityList) {
            if(usQEntity != null) {
                usQDTOList.add(UsQDTO.toUsQDTO(usQEntity));
            }
        }
        return usQDTOList;
    }

//  --------------------- ★ 1:1 문의 _ email전달★ ---------------------------------------------------------------
    public String findEmail(String loginId) {
        UserDTO userDTO = userService.findByUserID(loginId);
        if(userDTO != null) {
            String email = userDTO.getEmail();
            return email;
        }
        return null;
    }

//  --------------------- ★ 1:1 문의 _ usQId로 삭제하기★ ---------------------------------------------------------------
    public void deleteUsQ(List<Integer> usQIds) {
        for(Integer usQ : usQIds) {
            usQRepository.deleteById(usQ);
        }
    }


}
