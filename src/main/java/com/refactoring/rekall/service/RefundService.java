package com.refactoring.rekall.service;

import com.refactoring.rekall.dto.RefundDTO;
import com.refactoring.rekall.entity.RefundEntity;
import com.refactoring.rekall.repository.RefundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RefundService {
    @Autowired
    RefundRepository refundRepository;

//  ------------------------------------- ★ loginId별 / sort 별 List ★ --------------------------------------------------
    public List<RefundDTO> getRefundList(String loginId, String status) {
        List<RefundEntity> refundEntities = refundRepository.findByUserEntityUserIdOrderByRefundId(loginId);
        List<RefundDTO> refundDTOList = getSortRefund(refundEntities, status);

        return refundDTOList;
    }
//  ★ sort 별 List ★ --------------------------------------------------
    public List<RefundDTO> getSortRefund(List<RefundEntity> refundEntities, String status) {
        List<RefundDTO> refundDTOList = new ArrayList<>();

        for(RefundEntity refundEntity : refundEntities) {
            if(refundEntity != null && refundEntity.getStatus().equals(status)){
                refundDTOList.add(RefundDTO.toRefundDTO(refundEntity));
            }
        }
        return refundDTOList;
    }

//  ★ 전체 / sort 별 List ★ --------------------------------------------------
    public List<RefundDTO> getAllRefund(String status) {
         List<RefundEntity> refundEntities = refundRepository.findAll();
        List<RefundDTO> refundDTOList = getSortRefund(refundEntities, status);

        return refundDTOList;
    }
}
