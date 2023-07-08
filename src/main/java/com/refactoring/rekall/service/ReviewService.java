package com.refactoring.rekall.service;

import com.refactoring.rekall.dto.ReviewDTO;
import com.refactoring.rekall.entity.ReviewEntity;
import com.refactoring.rekall.repository.ProductRepository;
import com.refactoring.rekall.repository.ReviewRepository;
import groovy.transform.ThreadInterrupt;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    ProductRepository productRepository;

/*    public List<ProductDTO> findproduct() {
        List<ProductEntity> productEntityList = productRepository.findAll();

    }

    public List<ReviewDTO> findReview() {
       List<ReviewEntity> reviewEntityList = productList();
       List<ReviewDTO> reviewDTOList = new ArrayList<>();
        for(ReviewEntity reviewEntity : reviewEntityList) {
            if(reviewEntity != null) {
                ReviewDTO reviewDTO = ReviewDTO.toReviewDTO(reviewEntity);

            }
        }

    }*/
    // 상품 별로 review를 어떻게 뽑지..?

    public  List<ReviewEntity> productList() {
        List<ReviewEntity> reviewEntityList = reviewRepository.findProduct();
        return reviewEntityList;
    }

//  ----------------- ★ mypage Review  ★ ---------------------------------------------------------------
    public List<ReviewDTO> findReviewById(String loginId) {
        List<ReviewEntity> reviewEntityList = reviewRepository.findByUserEntityUserIdOrderByReviewIdDesc(loginId);
        List<ReviewDTO> reviewDTOS = new ArrayList<>();
        for(ReviewEntity reviewEntity : reviewEntityList) {
            if (reviewEntity != null) {
                reviewDTOS.add(ReviewDTO.toReviewDTO(reviewEntity));
            }
        }
        return reviewDTOS;
    }

//  ----------------- ★ community ReviewController ★ ---------------------------------------------------------------


}
