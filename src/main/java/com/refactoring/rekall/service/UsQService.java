package com.refactoring.rekall.service;

import com.refactoring.rekall.entity.UsQEntity;
import com.refactoring.rekall.repository.UsQRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsQService {

    @Autowired
    UsQRepository usQRepository;

//  ---------------------------------- ★ 1:1 문의 저장★ ---------------------------------------------------------------
    public void sendQuestion(UsQEntity usQEntity) {
        usQRepository.save(usQEntity);
    }

}
