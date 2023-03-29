package com.refactoring.rekall.service;

import com.refactoring.rekall.dto.ProductDTO;
import com.refactoring.rekall.entity.ProductEntity;
import com.refactoring.rekall.repository.ProductRepository;
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
}