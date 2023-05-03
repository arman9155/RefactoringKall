package com.refactoring.rekall.service;

import com.refactoring.rekall.dto.ProductDTO;
import com.refactoring.rekall.dto.ProductQDTO;
import com.refactoring.rekall.dto.UserDTO;
import com.refactoring.rekall.entity.ProductQEntity;
import com.refactoring.rekall.repository.ProductQRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

@Service
@Transactional
public class ProductQService {
    @Autowired
    ProductQRepository productQRepository;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;

    public void saveProductQ(ProductQDTO productQDTO, Integer productId, String loginId) {
        ProductDTO productDTO = productService.findByProductId(productId);
        UserDTO userDTO = userService.findByUserID(loginId);
        productQDTO.setProductDTO(productDTO);
        productQDTO.setUserDTO(userDTO);

        productQRepository.save(ProductQEntity.toProductQEntity(productQDTO));
    }
}
