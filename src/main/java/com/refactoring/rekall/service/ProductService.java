package com.refactoring.rekall.service;

import com.refactoring.rekall.dto.*;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


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
    public  List<String> findtaglist(List<ProductDTO> productDTOList) {
        List<String> tagList = new ArrayList<>();
        int i = 0;
        for(ProductDTO productDTO : productDTOList) { //각 상품카테고리별 상품 돌리고
            String[] tags = findtags(productDTO); // detail에 상품 각 태그 뽑을 때 만든 method
            for(String tag : tagList) { // 넘기려는 tag List
                for(String tagItem : tags) { //받아온 tags
                    if (!tagItem.equals(tag)) { // 받아온 값이 tagList에 없을 때 저장
                        tagList.add(tagItem);
                    }
                }
            }
        }
        return tagList;
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

//  ---------------- ★ 제품 상세 보기 _ productId로 productDTO 보내기 ★ ------------------------------------------------------------
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
        List<ProductImgEntity> productImgEntityList = productImgRepository.findByProductEntityProductId(productId);
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
        List<ReviewEntity> reviewEntityList = reviewRepository.findByProductEntityProductIdOrderByReviewIdDesc(productId);
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
        List<ProductQEntity> productQEntityList = productQRepository.findByProductEntityProductIdOrderByProductQIdDesc(productId);
        List<ProductQDTO> productQDTOList = new ArrayList<>();
        for(ProductQEntity productQEntity : productQEntityList) {
            if(productQEntity != null) {
                productQDTOList.add(ProductQDTO.toProductQDTO(productQEntity));

            }
        }
        return productQDTOList;
    }

//  ★ 랜덤 제품 추천 ★ ------------------------------------------------------------
    public List<ProductDTO> randomProduct(Integer productId) {
        List<ProductDTO> productDTOList = findAll();
        List<ProductDTO> productDTOS = new ArrayList<>(5);

        for(int i = 0, j = 0, p = 0; ; i++) {
            if(p ==5) break;
            j = (int)(Math.random()*productDTOList.size());
            if(productDTOList.get(j).getProductId() != productId) {
                productDTOS.add(productDTOList.get(j));
                p++;
            }
        }
        return productDTOS;
    }


//  ------------------------------------- ★ 관리자페이지 ★ -----------------------------------------------------------------
//   ★ 상품관리 _ 리스트  ★ -----------------------------------------------------------------
    public List<ProductDTO> getList(String categoryId) {
        if(categoryId.equals("all"))
            return findAll();
        else
            return findById(categoryId);
    }

//   ★ 상품관리 _ 저장 / 수정 기능 ★ -----------------------------------------------------------------
    public void saveDTO(ProductDTO productDTO) {
        if(productDTO != null)
            productRepository.save(ProductEntity.toProductEntity(productDTO));
    }

//   ★ 상품관리 _ 추가 기능 ★ -----------------------------------------------------------------
    public Integer productAdd(ProductDTO productDTO, MultipartFile imgFile, String sort) throws Exception {

        if(imgFile != null && !imgFile.isEmpty()) {
            String imgPath = saveImg(productDTO.getCategoryDTO().getCategoryId(), productDTO.getName(), productDTO.getProductId(), imgFile);
            productDTO.setImage(imgPath);
        }
        saveDTO(productDTO);
        if(sort == "a") {
            return findProductId(productDTO);
        }

        return productDTO.getProductId();
    }
//   ★ 상품관리 _ 상품 날짜로 productId 찾기 ★ -----------------------------------------------------------------
    public Integer findProductId(ProductDTO productDTO) {
        return productRepository.findByNameAndDate(productDTO.getName());
    }

// 사진저장 기능 -> 사진 여러개면 이거 for문으로 돌려서 받아라..
    public String saveImg (String categoryId, String name, Integer Id, MultipartFile imgFile) throws Exception {

         if(imgFile != null && !imgFile.isEmpty()) {
             String oriImgName = imgFile.getOriginalFilename(); // 원본파일 이름

             String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/upImg/"+categoryId+"/"+Id+"/"; //경로
                // UUID 를 이용하여 파일명 새로 생성
                // UUID - 서로 다른 객체들을 구별하기 위한 클래스

             UUID uuid = UUID.randomUUID(); // 난수 생성
             String savedFileName = uuid + "_" + name + "_" + oriImgName;  //저장할 이름 완성

             File saveFile = new File(projectPath, savedFileName);
             if(!saveFile.exists())
                 saveFile.mkdirs();

             imgFile.transferTo(saveFile);

             String saveName = "/upImg/"+categoryId+"/"+Id+"/"+savedFileName;
             return saveName;
         }
         return "";
    }

//   ★ 상품관리 _ 삭제 기능 ★ -----------------------------------------------------------------

    public void productDel(List<Integer> productIds, Integer productId) {
        if(productId == 0) {
            for(Integer Id : productIds) {
                productRepository.deleteById(Id);
            }
        } else {
            productRepository.deleteById(productId);
        }
    }
}
