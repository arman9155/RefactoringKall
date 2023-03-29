package com.refactoring.rekall.service;

import com.refactoring.rekall.dto.UsQDTO;
import com.refactoring.rekall.entity.UsQEntity;
import com.refactoring.rekall.repository.UsQRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UsQService {

    @Autowired
    UsQRepository usQRepository;

//  ---------------------------------- ★ 1:1 문의 저장★ ---------------------------------------------------------------
    public void sendQuestion(UsQDTO usQDTO) {
        usQRepository.save(UsQEntity.toUsQEntity(usQDTO));
    }

}
