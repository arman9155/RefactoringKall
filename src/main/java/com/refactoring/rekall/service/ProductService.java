package com.refactoring.rekall.service;

import com.refactoring.rekall.dto.ProductDTO;
import com.refactoring.rekall.dto.ProductImgDTO;
import com.refactoring.rekall.dto.ProductQDTO;
import com.refactoring.rekall.dto.ReviewDTO;
import com.refactoring.rekall.entity.ProductEntity;
import com.refactoring.rekall.entity.ProductImgEntity;
import com.refactoring.rekall.entity.ProductQEntity;
import com.refactoring.rekall.entity.ReviewEntity;
import com.refactoring.rekall.repository.ProductImgRepository;
import com.refactoring.rekall.repository.ProductQRepository;
import com.refactoring.rekall.repository.ProductRepository;
import com.refactoring.rekall.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductImgRepository productImgRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    ProductQRepository productQRepository;

//  ------------------------------------- ★ Product List ★ ------------------------------------------------------------
    public List<ProductDTO> findAll() {
        List<ProductEntity> productEntityList = productRepository.findAll(Sort.by(Sort.Direction.DESC,"productId"));
        List<ProductDTO> productDTOList = new ArrayList<>(6);
        for(ProductEntity productEntity : productEntityList) {
            if(productEntity != null) {
                if(!productEntity.getCategoryEntity().getCategoryId().equals("main")) {
                    productDTOList.add(ProductDTO.toProductDTO(productEntity));
                }
            }
        }
        return productDTOList;
    }

//  ------------------------------------- ★ 제품 리스트 뽑기 기능 ★ ------------------------------------------------------------
    public List<ProductDTO> findById(String categoryId) {
        List<ProductEntity> productEntityList = productRepository.findAllByCategoryEntityCategoryIdOrderByProductIdDesc(categoryId);
        List<ProductDTO> productDTOList = new ArrayList<>();
        for(ProductEntity productEntity : productEntityList) {
            if(productEntity != null) {
                productDTOList.add(ProductDTO.toProductDTO(productEntity));
            }
        }
        return productDTOList;
    }

//  ------------------------------------- ★ 제품 상세 보기 ★ ------------------------------------------------------------
    public ProductDTO findByProductId(Integer productId) {
        ProductEntity productEntity = productRepository.findByProductId(productId);
        ProductDTO productDTO = new ProductDTO();
        if(productEntity != null) productDTO = ProductDTO.toProductDTO(productEntity);
        return productDTO;
    }
//  -------------------------------------★ 태그 리스트 보내기 ★ ------------------------------------------------------------
    public String[] findtags(ProductDTO productDTO) {
        String tag = productDTO.getTag();
        String[] tags = tag.split("#");

        return tags;
    }

//  -----------------------------------★ 상세이미지리스트 ★ ------------------------------------------------------------
//  ★  상품상세사진★ ------------------------------------------------------------
    public List<ProductImgDTO> productImgList(Integer productId) {
        List<ProductImgEntity> productImgEntityList = productImgRepository.findByProdctEntityProductId(productId);
        List<ProductImgDTO> productImgDTOList = new ArrayList<>();
        for(ProductImgEntity productImgEntity : productImgEntityList) {
            if(productImgEntity != null) {
                productImgDTOList.add(ProductImgDTO.toProductImgDTO(productImgEntity));
            }
        }
        return productImgDTOList;
    }

//  -★ 리뷰리스트 ★ ------------------------------------------------------------
    public List<ReviewDTO> findReview(Integer productId) {
        List<ReviewEntity> reviewEntityList = reviewRepository.findByProductEntityProductId(productId);
        List<ReviewDTO> reviewDTOSList = new ArrayList<>();
        for(ReviewEntity reviewEntity : reviewEntityList) {
            if(reviewEntity != null) {
                reviewDTOSList.add(ReviewDTO.toReviewDTO(reviewEntity));
            }
        }
        return reviewDTOSList;
    }

//  ★ 문의리스트 ★ ------------------------------------------------------------
    public List<ProductQDTO> findproductQ(Integer productId) {
        List<ProductQEntity> productQEntityList = productQRepository.findByProductEntityProductId(productId);
        List<ProductQDTO> productQDTOList = new ArrayList<>();
        for(ProductQEntity productQEntity : productQEntityList) {
            if(productQEntity != null) {
                productQDTOList.add(ProductQDTO.toProductQDTO(productQEntity));

            }
        }
        return productQDTOList;
    }



}
