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
import java.util.*;


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
    @Autowired
    ImageService imageService;

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

        for(ProductDTO productDTO : productDTOList) { //각 상품카테고리별 상품 돌리고
            String[] tags = findtags(productDTO); // detail에 상품 각 태그 뽑을 때 만든 method
            List<String> tempList = new ArrayList<>(); // 임시 컬렉션 생성

            for (String tagItem : tags) {
                if (!tagList.contains(tagItem) && !tempList.contains(tagItem)) {
                    tempList.add(tagItem); // tagList와 tempList에 뽑은 tag가 없을 때 tagList에 저장함
                }
            }
            tagList.addAll(tempList); // 임시 컬렉션의 모든 요소를 tagList에 추가

        }
        return tagList;
    }

//  ★ 제품 리스트 뽑기_tag로★ ------------------------------------------------------------

    public List<ProductDTO> findProductByTagList(String[] tags) {
        List<ProductDTO> productDTOList = new ArrayList<>();

        if(Arrays.stream(tags).count()>0) {
            for(String tag : tags) {
                if(tag != null) {
                    productDTOList.add(findByTag(tag));
                }
            }
        }
        return productDTOList;
    }

//  -★ 제품 리스트 뽑기_tag로★ ------------------------------------------------------------
    public ProductDTO findByTag(String tag) {
        Optional<ProductEntity> OpProductEntity = productRepository.findByTag(tag);
        ProductDTO productDTO = new ProductDTO();
        if(OpProductEntity.isPresent()) productDTO = ProductDTO.toProductDTO(OpProductEntity.get());

        return productDTO;
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
        System.out.println("findtags에 들어옴");
        String tag = productDTO.getTag();
        String[] tags = tag.split("#");
        return tags;
    }

//  -----------------------------------★ 상세이미지리스트 ★ ------------------------------------------------------------
//  ★  상품상세사진★ ------------------------------------------------------------
    public List<ProductImgDTO> productImgList(Integer productId) {
        List<ProductImgEntity> productImgEntityList = productImgRepository.findimg(productId);
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
        List<ProductQEntity> productQEntityList = productQRepository.findByProductEntityProductIdOrderByProductqIdDesc(productId);
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

        if(productDTOList.size() > 5) {
            for (int i = 0, j = 0, p = 0; ; i++) {
                if (p == 5) break;
                j = (int) (Math.random() * productDTOList.size());
                if (productDTOList.get(j).getProductId() != productId) {
                    productDTOS.add(productDTOList.get(j));
                    p++;
                }
            }
            return productDTOS;
        } else {
            return productDTOS;
        }
    }


//  ------------------------------------- ★ 관리자페이지 ★ -----------------------------------------------------------------
//   ★ 상품관리 _ 리스트  ★ -----------------------------------------------------------------
    public List<ProductDTO> getList(String categoryId) {
        if(categoryId.equals("all")) {
            List<ProductEntity> productEntityList = productRepository.findAll();
            List<ProductDTO> productDTOList = new ArrayList<>();
            for (ProductEntity productEntity : productEntityList) {
                if(productEntity != null) productDTOList.add(ProductDTO.toProductDTO(productEntity));
            }
            return productDTOList;
        }
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
        String id = "0";
        if(productRepository.findId() == null) id = "1";
        else id = productRepository.findId() + 1 + "";

        if(imgFile != null && !imgFile.isEmpty()) {
            String imgPath = imageService.saveImg(productDTO.getCategoryDTO().getCategoryId(), productDTO.getName(), id, imgFile);
            productDTO.setImage(imgPath);
        }
        saveDTO(productDTO);

        if(sort == "a") {
            return productRepository.findId();
        }

        return productDTO.getProductId();
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
//   ★ 상품관리 _ 상세이미지 저장 ★ -----------------------------------------------------------------
    public void detailImage(ProductImgDTO productImgDTO, Integer productId, MultipartFile[] files) throws Exception {

        productImgDTO.setProductDTO(findByProductId(productId)); //productDTO 찾아서 넣어주기

        String id = "0";

        if(productImgRepository.findId() == null) id = "1"; //id가 null -> Img 저장 첨인 경우
        else id = productImgRepository.findId()+1+"";  // 기존 img가 있는                                                경우 최종 + 1 로 설정

        String saveName = "";
        String str = productImgDTO.getProductDTO().getName(); // imgId가 숫자인데 integer로 설정(고유번호 전달)

        if(files != null) {
            for(MultipartFile file : files) {
                if(!file.isEmpty()) {
                    saveName = imageService.saveImg(productImgDTO.getProductDTO().getCategoryDTO().getCategoryId(), str, id, file);
                    productImgDTO.setImage(saveName);

                    String name = saveName.split("_")[saveName.split("_").length-1];
                    productImgDTO.setImageName(name);

                    productImgRepository.save(ProductImgEntity.toProductImgEntity(productImgDTO));
                }
                id += 1;  //여러개인 경우 id가 고유번호니까 추가되야함
            }
        }
    }

//   ★ 상품관리 _ 상세이미지 수정 ★ -----------------------------------------------------------------
//   ★ 상품관리 _ 상세이미지 삭제 ★
    public void productDImageDel(Integer productId) {
        List<ProductImgEntity> productImgEntityList = productImgRepository.findByProductEntityProductId(productId);
        if(productImgEntityList != null) {
            for(ProductImgEntity productImgEntity : productImgEntityList) {
                if(productImgEntity != null) {
                    productImgRepository.deleteById(productImgEntity.getProductimgId());
                }
            }
        }
    }
//   ★ 상품관리 _ 상세이미지 삭제 - 저장★
    public void productDImageC(ProductImgDTO productImgDTO, MultipartFile[] files, Integer productId) throws Exception {
        productDImageDel(productId);
        detailImage(productImgDTO, productId, files);
    }

    public ProductDTO getIDFromName(String name) {
        return ProductDTO.toProductDTO(productRepository.findByName(name));
    }
}
