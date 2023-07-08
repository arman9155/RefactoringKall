package com.refactoring.rekall.service;

import com.refactoring.rekall.dto.UsQDTO;
import com.refactoring.rekall.dto.UserDTO;
import com.refactoring.rekall.entity.UsQEntity;
import com.refactoring.rekall.repository.UsQRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UsQService {

    @Autowired
    UsQRepository usQRepository;
    @Autowired
    UserService userService;
    @Autowired
    ImageService imageService;

    //  ---------------------------------- ★ 1:1 문의 저장★ ---------------------------------------------------------------
    public void saveQuestion(UsQDTO usQDTO) {
        if(usQDTO != null)
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
    public void deleteUsQ(List<Integer> usQIds, Integer usqId, String page) {

        if(page.equals("detail"))
            usQRepository.deleteById(usqId);
        else {
            System.out.println("여기");
            for (Integer usQ : usQIds) {
                if(usQ != null) {
                    System.out.println("usq" + usQ);
                    usQRepository.deleteById(usQ);
                }
            }
        }
    }

//  --------------------- ★ 전체 문의 내역★ ---------------------------------------------------------------
    public List<UsQDTO> usqAllList() {
        List<UsQEntity> usQEntityList = usQRepository.findAll(Sort.by(Sort.Direction.DESC, "usqId"));
        List<UsQDTO> usQDTOList = new ArrayList<>();

        for(UsQEntity usQEntity : usQEntityList) {
            if(usQEntity != null) {
                usQDTOList.add(UsQDTO.toUsQDTO(usQEntity));
            }
        }
        return usQDTOList;
    }
//  --------------------- ★ 개인 문의 뽑기★ ---------------------------------------------------------------
    public UsQDTO findUsQ(Integer usQId) {
        Optional<UsQEntity> OpusQEntity = usQRepository.findById(usQId);
        UsQDTO usQDTO = new UsQDTO();

        if(OpusQEntity.isPresent()) {
            usQDTO = UsQDTO.toUsQDTO(OpusQEntity.get());
        }
        return usQDTO;
    }
//  --------------------- ★ 카테고리별 뽑기★ ---------------------------------------------------------------
    public List<UsQDTO> findCList(String categoryId) {
        List<UsQEntity> UsQEntityList = usQRepository.findByCategoryEntityCategoryIdOrderByUsqIdDesc(categoryId);
        List<UsQDTO> usQDTOList = new ArrayList<>();
        for(UsQEntity usQEntity : UsQEntityList) {
            if(usQEntity != null) {
                usQDTOList.add(UsQDTO.toUsQDTO(usQEntity));
            }
        }
        return usQDTOList;
    }
//  --------------------- ★ 업로드 파일 처리★ ---------------------------------------------------------------

    public UsQDTO file(UsQDTO usQDTO, MultipartFile[] files) throws Exception {
        String id = "0";
        if(usQRepository.findId() == null) id = "1";
        else id = usQRepository.findId() + 1 + "";

        String saveName = "";

        if (files.length > 0) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    saveName = imageService.saveImg(usQDTO.getCategoryDTO().getCategoryId(), usQDTO.getUserDTO().getUserId(), id, file);
                    if (usQDTO.getImage1() == null) {
                        usQDTO.setImage1(saveName);
                    } else {
                        usQDTO.setImage2(saveName);
                    }
                }
            }
        }

        return usQDTO;
    }

}
