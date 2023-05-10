package com.refactoring.rekall.controller;

import com.refactoring.rekall.dto.*;
import com.refactoring.rekall.service.CategoryService;
import com.refactoring.rekall.service.ProductService;
import com.refactoring.rekall.service.WishListService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    WishListService wishListService;


//  ------------------------------------- ★ Product List ★ ------------------------------------------------------------
//  ★ 주문케이크 리스트 ★ ------------------------------------------------------------
    @GetMapping("custom") // 상품리스트 _ custom
    public ModelAndView customList(@SessionAttribute(name ="loginId", required = false) String loginId,
                                   @SessionAttribute(name ="userRole", required = false) String userRole) {

        ModelAndView modelAndView = new ModelAndView();
        List<ProductDTO> productDTOList = productService.findById("custom");
        String value = categoryService.findByCategoryId("custom");
        List<String> tags = productService.findtaglist(productDTOList);
        List<WishListDTO> wishListDTOList = wishListService.findWishList(loginId);

        modelAndView.addObject("productList", productDTOList);
        modelAndView.addObject("category", value);
        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("userRole", userRole);
        modelAndView.addObject("wishList", wishListDTOList);
        modelAndView.addObject("tags", tags); // 태그들
        modelAndView.setViewName("pages/product/productList.html");

        return modelAndView;
    }

//  ★ 디자인케이크 리스트 ★ ------------------------------------------------------------
    @GetMapping("design") // 상품리스트 _ design
    public ModelAndView designList(@SessionAttribute(name ="loginId", required = false) String loginId,
                                   @SessionAttribute(name ="userRole", required = false) String userRole) {
        ModelAndView modelAndView = new ModelAndView();
        List<ProductDTO> productDTOList = productService.findById("design");
        String value = categoryService.findByCategoryId("design");
        List<String> tags = productService.findtaglist(productDTOList);
        List<WishListDTO> wishListDTOList = wishListService.findWishList(loginId);

        modelAndView.addObject("productList", productDTOList);
        modelAndView.addObject("category", value);
        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("userRole", userRole);
        modelAndView.addObject("wishList", wishListDTOList);
        modelAndView.addObject("tags", tags); // 태그들
        modelAndView.setViewName("pages/product/productList.html");

        return modelAndView;
    }

//  ★ 기타 리스트 ★ ------------------------------------------------------------
    @GetMapping("etc") // 상품리스트 _ etc
    public ModelAndView etcList(@SessionAttribute(name ="loginId", required = false) String loginId,
                                @SessionAttribute(name ="userRole", required = false) String userRole) {
        ModelAndView modelAndView = new ModelAndView();
        List<ProductDTO> productDTOList = productService.findById("etc");
        String value = categoryService.findByCategoryId("etc");
        List<String> tags = productService.findtaglist(productDTOList);
        List<WishListDTO> wishListDTOList = wishListService.findWishList(loginId);

        modelAndView.addObject("productList", productDTOList);
        modelAndView.addObject("category", value);
        modelAndView.addObject("loginId", loginId);
        modelAndView.addObject("userRole", userRole);
        modelAndView.addObject("wishList", wishListDTOList);
        modelAndView.addObject("tags", tags); // 태그들
        modelAndView.setViewName("pages/product/productList.html");

        return modelAndView;
    }

//  ------------------------------------- ★ 상세페이지 연결 ★ ------------------------------------------------------------
    @GetMapping("product/{id}") // 상품 상세페이지
        public ModelAndView product(@SessionAttribute(name ="loginId", required = false) String loginId,
                                    @SessionAttribute(name ="userRole", required = false) String userRole,
                                    @PathVariable("id") Integer productId) {

        ModelAndView modelAndView = new ModelAndView();
        ProductDTO productDTO = productService.findByProductId(productId); // 상품정보
        List<ProductImgDTO> productImgDTO = productService.productImgList(productId); //상세사진
        List<ReviewDTO> reviewDTOList = productService.findReview(productId); // 리뷰리스트
        List<ProductQDTO> productQList = productService.findproductQ(productId); // 문의리스트
        /*List< ReviewCmtDTO> reviewCmtDTOList = productService.reviewCmt()*/
        List<ProductDTO> randomItem = productService.randomProduct(productId); // 랜덤리스트
        List<WishListDTO> wishListDTOList = wishListService.findWishList(loginId); // loginId의 wishList

        boolean flag = false;
        if(wishListDTOList != null ) {
            for(WishListDTO wishListDTO : wishListDTOList) {
                if(wishListDTO.getProductDTO().getProductId() == productId)  {
                    flag = true; // 있으면 true
                    break;
                }
            }
        }

        String[] tags = productService.findtags(productDTO); // 태그 넣기
        String value = productDTO.getName(); //상품명

        if(flag)
            modelAndView.addObject("wish", true);
        modelAndView.addObject("loginId", loginId);  //세션
        modelAndView.addObject("userRole", userRole);  //세션

        modelAndView.addObject("productDTO", productDTO); // 상품정보
        modelAndView.addObject("detailImg", productImgDTO); // 상세사진
        modelAndView.addObject("review", reviewDTOList); // 리뷰리스트
        modelAndView.addObject("product_q", productQList); // 문의리스트
        modelAndView.addObject("tags", tags); // 태그들
        modelAndView.addObject("productName", value);  // 상품명
        modelAndView.addObject("randomList", randomItem); //랜덤상품추천

        modelAndView.addObject("productQDTO",new ProductQDTO()); // 문의글DTO 보내기

        System.out.println("productDTO.getCategoryDTO().getCategoryId()"+productDTO.getCategoryDTO().getCategoryId());
        if(productDTO.getCategoryDTO().getCategoryId() == "custom")
            modelAndView.setViewName("pages/product/customDetail.html");
        else if(productDTO.getCategoryDTO().getCategoryId() == "design")
            modelAndView.setViewName("pages/product/productDetail.html");
        else
            modelAndView.setViewName("pages/product/customDetail.html");
//        허허..안되넹..

        return modelAndView;
    }


//  ------------------------------------- ★ 관리자페이지 ★ -----------------------------------------------------------------
//   ★ 상품관리 _ 리스트  ★ -----------------------------------------------------------------
    @GetMapping("a_product")
    public ModelAndView userList(@RequestParam(value="sort", defaultValue = "all") String categoryId) {
        ModelAndView modelAndView = new ModelAndView();
        List<ProductDTO> productDTOList = productService.getList(categoryId);

        modelAndView.addObject("productList", productDTOList);
        modelAndView.addObject("status", categoryId);
        modelAndView.setViewName("admin/product/productList.html");

        return modelAndView;
    }

//   ★ 상품관리 _ 디테일  ★ -----------------------------------------------------------------------
    @GetMapping("a_product_detail")
    public ModelAndView productDetail(@RequestParam("productId") Integer productId) {
        ModelAndView modelAndView = new ModelAndView();
        ProductDTO productDTO = productService.findByProductId(productId);

        modelAndView.addObject("productDTO", productDTO);
        modelAndView.setViewName("admin/product/productDetail.html");
        return modelAndView;
    }

// ★ 상품 관리 _ 상품 추가★ ---------------------------------------------------------------
    @GetMapping("a_product_add")
    public ModelAndView productAddF() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("productDTO", new ProductDTO());
        modelAndView.setViewName("admin/product/productAdd.html");
        return modelAndView;
    }

    @PostMapping("a_product_add")
    public ModelAndView productAdd(@ModelAttribute ProductDTO productDTO, MultipartFile imgFile) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        String sort = "a";
        Integer productId = productService.productAdd(productDTO, imgFile, sort);

        modelAndView.addObject("data", new Message("추가되었습니다.", "/a_product_detail?productId="+productId));
        modelAndView.setViewName("common/fragments/message.html");
        return modelAndView;
    }

//  상품 관리_ 수정 ★ ---------------------------------------------------------------
    @GetMapping("a_product_change")
    public ModelAndView productChangeF(@RequestParam("productId") Integer productId) {
        ModelAndView modelAndView = new ModelAndView();
        ProductDTO productDTO = productService.findByProductId(productId);

        modelAndView.addObject("productDTO", productDTO);
        modelAndView.setViewName("admin/product/productDetailChange.html");
        return modelAndView;
    }
//  상품 관리 _ 수정 완료★ ---------------------------------------------------------------
    @PostMapping("a_product_change")
    public ModelAndView productChange(@ModelAttribute ProductDTO productDTO, MultipartFile imgFile) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        String sort = "c";

        if(imgFile != null && !imgFile.isEmpty()) {
            productService.productAdd(productDTO, imgFile, sort);
        } else {
            productService.productAdd(productDTO, null, sort);
        }

        modelAndView.addObject("data", new Message("수정되었습니다.", "/a_product_detail?productId="+productDTO.getProductId()));
        modelAndView.setViewName("common/fragments/message.html");
        return modelAndView;
    }

//  ★ 상품 관리 _ 삭제★ ---------------------------------------------------------------
    @GetMapping("a_product_del") //오..고민해봐야겠네..?
    public ModelAndView productDel(@RequestParam(required = false, name="productId") Integer productId) {
        ModelAndView modelAndView = new ModelAndView();

        List<Integer> productIds = new ArrayList<>();
        productService.productDel(productIds, productId);

        modelAndView.addObject("data", new Message("삭제되었습니다.", "close"));
        modelAndView.setViewName("common/fragments/message.html");

        return modelAndView;
    }
    @PostMapping("a_product_del")
    public ModelAndView addressDel(@RequestParam(required = false, name="productIds") List<Integer> productIds) {
        ModelAndView modelAndView = new ModelAndView();

        Integer productId = 0;
        productService.productDel(productIds, productId);

        modelAndView.addObject("data", new Message("삭제되었습니다.", "a_product"));
        modelAndView.setViewName("common/fragments/message.html");

        return modelAndView;
    }
}
