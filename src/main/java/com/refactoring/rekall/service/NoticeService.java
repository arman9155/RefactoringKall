package com.refactoring.rekall.service;

import com.refactoring.rekall.dto.CategoryDTO;
import com.refactoring.rekall.dto.NoticeDTO;
import com.refactoring.rekall.entity.CategoryEntity;
import com.refactoring.rekall.entity.NoticeEntity;
import com.refactoring.rekall.repository.CategoryRepository;
import com.refactoring.rekall.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {

    @Autowired
    NoticeRepository noticeRepository;
    @Autowired
    CategoryRepository categoryRepository;

//  -------------- ★ Notice 전체 리스트 뽑기 ★ ---------------------------------------------------------------
    public List<NoticeDTO> findAll(){
        List<NoticeEntity> noticeEntityList = noticeRepository.findAllByOrderByNoticeIdDesc();
        List<NoticeDTO> noticeDTOList = new ArrayList<>();

        for(NoticeEntity noticeEntity : noticeEntityList) {
            if(noticeEntity != null) {
                noticeDTOList.add(NoticeDTO.toNoticeDTO(noticeEntity));
            }
        }
        return noticeDTOList;
    }

//  -------------- ★ QnA : category 보내기 ★ ---------------------------------------------------------------
    public List<CategoryDTO> findCategory() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();

        for(CategoryEntity categoryEntity : categoryEntityList) {
            if(categoryEntity != null) {
                if(categoryEntity.getCategoryId().contains("faq")){
                    categoryDTOList.add(CategoryDTO.toCategoryDTO(categoryEntity));
                }
            }
        }
        return  categoryDTOList;
    }

//  -------------- ★ Notice 전체에서 고르기 : notice/faq/faq세부 ★ ---------------------------------------------------------------
    public List<NoticeDTO> findList(String categoryId){

        List<NoticeDTO> List = findAll();
        List<NoticeDTO> noticeDTOList = new ArrayList<>();

        for(NoticeDTO noticeDTO : List) {
            if (noticeDTO != null) {
                if (noticeDTO.getCategoryDTO().getCategoryId().equals(categoryId)){
                    noticeDTOList.add(noticeDTO);
                } else if (categoryId.equals("all")) {
                    if (noticeDTO.getCategoryDTO().getCategoryId().contains("faq")) {
                        noticeDTOList.add(noticeDTO);
                    }
                }
            }
        }
        return noticeDTOList;
    }
}
