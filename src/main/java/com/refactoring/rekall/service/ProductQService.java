package com.refactoring.rekall.service;

import com.refactoring.rekall.dto.ProductDTO;
import com.refactoring.rekall.dto.ProductQDTO;
import com.refactoring.rekall.dto.UserDTO;
import com.refactoring.rekall.entity.ProductQEntity;
import com.refactoring.rekall.repository.ProductQRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductQService {
    @Autowired
    ProductQRepository productQRepository;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;

//  ------------------------------------- ★ 상품 문의 저장 ★ ------------------------------------------------------------
    
    public void saveProductQ(ProductQDTO productQDTO, Integer productId, String loginId) {
        ProductDTO productDTO = productService.findByProductId(productId);
        UserDTO userDTO = userService.findByUserID(loginId);
        productQDTO.setProductDTO(productDTO);
        productQDTO.setUserDTO(userDTO);

        productQRepository.save(ProductQEntity.toProductQEntity(productQDTO));
    }
//  ------------------------------------- ★ 상품 문의 답변 저장 ★ ------------------------------------------------------------

    public void saveDTO(ProductQDTO productQDTO) {
        ProductDTO productDTO = productService.findByName(productQDTO.getProductDTO().getName());
        productQDTO.setProductDTO(productDTO);
        productQRepository.save(ProductQEntity.toProductQEntity(productQDTO));
    }
//  ------------------------------------- ★ 상품 문의 리스트 ★ ------------------------------------------------------------
    //-- 전체 뽑기
    public List<ProductQDTO> findAll() {
        List<ProductQEntity> productQEntityList = productQRepository.findAll(Sort.by(Sort.Direction.DESC,"productqId"));
        List<ProductQDTO> productQDTOList = new ArrayList<>();
        for(ProductQEntity productQEntity : productQEntityList) {
            if(productQEntity != null) {
                productQDTOList.add(ProductQDTO.toProductQDTO(productQEntity));
            }
        }
        return productQDTOList;
    }
    //  ID 에 해당하는 리스트
    public List<ProductQDTO> findList(String loginId) {
        List<ProductQEntity> productQEntityList = productQRepository.findByUserEntityUserIdOrderByProductqIdDesc(loginId);
        List<ProductQDTO> productQDTOList = new ArrayList<>();

        for(ProductQEntity Q : productQEntityList) {
            if(Q != null) {
                productQDTOList.add(ProductQDTO.toProductQDTO(Q));
            }
        }

        return productQDTOList;
    }

    // productqId 별
    public ProductQDTO findId(Integer qId) {
        Optional<ProductQEntity> optionalProductQEntity = productQRepository.findById(qId);
        if(optionalProductQEntity.isPresent()) {
           return ProductQDTO.toProductQDTO(optionalProductQEntity.get());
        }
        return null;
    }

//  ------------------------------------- ★ 상품 문의 삭제 ★ ------------------------------------------------------------
    public void productQDel(List<Integer> ids) {
        for(Integer id : ids) {
            productQIdDel(id);
        }
    }

    public void productQIdDel(Integer id) {
        if(id != null) {
            productQRepository.deleteById(id);
        }
    }



}
