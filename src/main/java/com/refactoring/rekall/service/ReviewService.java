package com.refactoring.rekall.service;

import com.refactoring.rekall.controller.Review;
import com.refactoring.rekall.dto.ProductDTO;
import com.refactoring.rekall.dto.ReviewDTO;
import com.refactoring.rekall.entity.ProductEntity;
import com.refactoring.rekall.entity.ReviewEntity;
import com.refactoring.rekall.repository.ProductRepository;
import com.refactoring.rekall.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


@Service
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

//  ----------------- ★ community Review ★ ---------------------------------------------------------------


}
