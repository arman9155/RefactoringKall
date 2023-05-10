package com.refactoring.rekall.service;

import com.refactoring.rekall.repository.UsQRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserQService {

    UsQRepository usQRepository;
}
