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
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
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


//  -------------- ★ Notice 전체에서 고르기 : notice/faq/faq세부 ★ ---------------------------------------------------------------
    public List<NoticeDTO> findList(String categoryId){

        List<NoticeDTO> List = findAll();  // 전체 notice 리스트 뽑기
        List<NoticeDTO> noticeDTOList = new ArrayList<>();

        for(NoticeDTO noticeDTO : List) { // 전체 notice 리스트에서 select하여 저장하기
            if (noticeDTO != null) { // list가 있고
                if(categoryId.equals("faq")) { // categoryId가 faq인 경우 noticeDTO에 faq가 포함되면 모두 저장하여 보내기
                    if(noticeDTO.getCategoryDTO().getCategoryId().contains("faq"))
                        noticeDTOList.add(noticeDTO);
                } else { // 아니면 notice / faq  세부이니까 categoryId랑 notice의 category가 같으면 저장하여 전달하기
                    if(noticeDTO.getCategoryDTO().getCategoryId().equals(categoryId))
                        noticeDTOList.add(noticeDTO);
                }
            }
        }
        return noticeDTOList;
    }



//  -------------- ★ 관리자 페이지 ★ ---------------------------------------------------------------
//  ★ 공지 / FAQ 디테일 (noticeId 로 dto 전달)★ ---------------------------------------------------------------
    public NoticeDTO noticeList(Integer noticeId) {
        Optional<NoticeEntity> notice = noticeRepository.findById(noticeId);
        NoticeEntity noticeEntity = new NoticeEntity();
        if(notice.isPresent())
            noticeEntity = notice.get();
        return NoticeDTO.toNoticeDTO(noticeEntity);
    }

//  수정 / 저장★ ---------------------------------------------------------------
    public Integer noticeSave(NoticeDTO noticeDTO) {
        noticeRepository.save(NoticeEntity.toNoticeEntity(noticeDTO));

        return  noticeRepository.findNoticeId();
    }

//  삭제 ★ ---------------------------------------------------------------
    public void delete(Integer noticeId, List<Integer> noticeIds, String page) {
        if(page.equals("list")) {
            for(Integer notice : noticeIds) {
                if(notice != null) noticeRepository.deleteById(notice);
            }
        } else {
            noticeRepository.deleteById(noticeId);
        }
    }



}
