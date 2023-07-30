package com.refactoring.rekall.service;

import com.refactoring.rekall.dto.RefundDTO;
import com.refactoring.rekall.entity.RefundEntity;
import com.refactoring.rekall.repository.RefundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RefundService {
    @Autowired
    RefundRepository refundRepository;
    @Autowired
    ImageService imageService;

//  ------------------------------------- ★ loginId별 / sort 별 List ★ --------------------------------------------------
    public List<RefundDTO> getRefundList(String loginId, String status) {
        List<RefundEntity> refundEntities = refundRepository.findByUserEntityUserIdOrderByRefundIdDesc(loginId);
        System.out.println(refundEntities);
        List<RefundDTO> refundDTOList = getSortRefund(refundEntities, status);

        return refundDTOList;
    }
//  ★ sort 별 List ★ --------------------------------------------------
    public List<RefundDTO> getSortRefund(List<RefundEntity> refundEntities, String status) {
        List<RefundDTO> refundDTOList = new ArrayList<>();
        if("all".equals(status)) {
            for (RefundEntity refundEntity : refundEntities) {
                refundDTOList.add(RefundDTO.toRefundDTO(refundEntity));
            }
        } else {
            for (RefundEntity refundEntity : refundEntities) {
                if (refundEntity.getStatus().equals(status)){
                    refundDTOList.add(RefundDTO.toRefundDTO(refundEntity));
                }
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

    //  --------------------- ★ 파일 업로드 처리 / ★ ---------------------------------------------------------------
    public void setRefund(RefundDTO refundDTO, MultipartFile[] files, String status) throws Exception {
        System.out.println("파일업로드 넘어옴");
        String saveName = "";

        if (files.length > 0 && "save".equals(status)) {  // 처음 저장하는 경우
            System.out.println("save");
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    saveName = imageService.saveImg("refund", refundDTO.getUserDTO().getUserId(), refundDTO.getOrderDetailDTO().getOdetailId().toString() , file);
                    if (refundDTO.getImage_1() == null) {
                        refundDTO.setImage_1(saveName);
                    } else {
                        refundDTO.setImage_2(saveName);
                    }
                }
            }
        } else {
            if(files.length>0) {  //처음 저장이 아닌 경우 _ change
                System.out.println("else");

                int i = 0;
                for (MultipartFile file : files) {
                    if (!file.isEmpty()) {
                        saveName = imageService.saveImg("refund", refundDTO.getUserDTO().getUserId(), refundDTO.getOrderDetailDTO().getOdetailId().toString() , file);
                        System.out.println(">>>>>>"+saveName);
                        if (i==0) {
                            refundDTO.setImage_1(saveName);
                            i++;
                        } else {
                            refundDTO.setImage_2(saveName);
                        }
                    }
                }
            }
        }
        refundRepository.save(RefundEntity.toRefundEntity(refundDTO));
    }

    //  --------------------- ★ refund DTO 찾기★ ---------------------------------------------------------------
    public RefundDTO findRefundDTO(Integer refundId) {
        Optional<RefundEntity> optionalRefundEntity = refundRepository.findById(refundId);
        RefundDTO refundDTO = new RefundDTO();
        if(optionalRefundEntity.isPresent()) refundDTO = RefundDTO.toRefundDTO(optionalRefundEntity.get());
        return refundDTO;
    }

    public void saveRefundDTO(RefundDTO refundDTO) {
        if(refundDTO != null) refundRepository.save(RefundEntity.toRefundEntity(refundDTO));
    }
}
