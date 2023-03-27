package com.refactoring.rekall.service;

import com.refactoring.rekall.dto.NoticeDTO;
import com.refactoring.rekall.entity.NoticeEntity;
import com.refactoring.rekall.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeService {

    @Autowired
    NoticeRepository noticeRepository;

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


//  -------------- ★ Notice 전체에서 고르기 ★ ---------------------------------------------------------------
    public List<NoticeDTO> findList(String categoryId){

        List<NoticeDTO> List = findAll();
        List<NoticeDTO> noticeDTOList = new ArrayList<>();

        for(NoticeDTO noticeDTO : List) {
            if (noticeDTO != null) {
                if (noticeDTO.getCategoryDTO().getCategoryId().contains(categoryId)){
                    noticeDTOList.add(noticeDTO);
                }
            }
        }
        return noticeDTOList;
    }
}
