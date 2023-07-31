package com.refactoring.rekall.controller;

import com.refactoring.rekall.dto.*;
import com.refactoring.rekall.service.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.codehaus.groovy.transform.SourceURIASTTransformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    @Autowired
    CartService cartService;
    @Autowired
    RefundService refundService;
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

//  ------------------------------------- ★ mypage order List ★ ------------------------------------------------------------
    @GetMapping("mypage/order")
    public ModelAndView orderList (HttpSession session,
                                   @RequestParam(name = "sort", defaultValue = "all", required = false) String status){

        ModelAndView modelAndView = new ModelAndView();

        List<OrderDetailDTO> orderDetailDTOS = orderService.getOrderList(session.getAttribute("loginId").toString(), status);

        modelAndView.addObject("status", status);
        modelAndView.addObject("oDetailList", orderDetailDTOS);
        modelAndView.setViewName("pages/mypage/order/orderList.html");

        return modelAndView;
    }

//  ------------------------------------- ★ mypage mileage List ★ ------------------------------------------------------------
@GetMapping("mypage/mileage")
    public ModelAndView mileageList (HttpSession session,
                                    @RequestParam(name = "sort", defaultValue = "all", required = false) String status){

        ModelAndView modelAndView = new ModelAndView();
        String loginId = session.getAttribute("loginId").toString();
        List<OrderDTO> orderDTOList = orderService.getMileageList(loginId);
        UserDTO userDTO = userService.findByUserID(loginId);

        modelAndView.addObject("user", userDTO);
        modelAndView.addObject("user", userDTO);
        modelAndView.addObject("status", status);
        modelAndView.addObject("orderList",orderDTOList);
        modelAndView.setViewName("pages/mypage/profile/mileage.html");

        return modelAndView;
    }

    //  ------------------------------------- ★ 주문상세정보 ★ ------------------------------------------------------------
    @GetMapping("/mypage/order/detail")
    public ModelAndView userOrderDetail (@RequestParam("odetailId") Integer odetailId){

        ModelAndView modelAndView = new ModelAndView();

        OrderDetailDTO orderDetailDTO = orderService.getOrderDetail(odetailId);
        modelAndView.addObject("odetail",orderDetailDTO);
        modelAndView.setViewName("/pages/mypage/order/orderDetail.html");

        return modelAndView;
    }

    //  ------------------------------------- ★ odetail 취소 요청 ★ ------------------------------------------------------------
    @GetMapping("/mypage/order/cancel")
    public ModelAndView cancelOdetailF(@RequestParam("odetailId") Integer odetailId) {
        ModelAndView modelAndView = new ModelAndView();

        OrderDetailDTO orderDetailDTO = orderService.getOrderDetail(odetailId);
        UserDTO userDTO = userService.findByUserID(orderDetailDTO.getOrderDTO().getUserDTO().getUserId());

        RefundDTO refundDTO = new RefundDTO();
        refundDTO.setUserDTO(userDTO);
        refundDTO.setOrderDetailDTO(orderDetailDTO);

        modelAndView.addObject("refundDTO", refundDTO );
        modelAndView.setViewName("/pages/mypage/order/orderRefund.html");
        return modelAndView;
    }
    @PostMapping("/mypage/order/cancel")
    public ModelAndView cancelOdetail(@ModelAttribute("refundDTO") RefundDTO refundDTO, MultipartFile[] files) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        orderService.cancelOdetail(refundDTO.getOrderDetailDTO().getOdetailId());
        refundService.setRefund(refundDTO, files, "save");

        modelAndView.addObject("data", new Message("반품 요청되었습니다.","/close" ));
        modelAndView.setViewName("/common/message.html");
        return modelAndView;
    }

//  ------------------------------------- ★ 바로 주문 처리 ★ ------------------------------------------------------------
    @ResponseBody
    @PostMapping("order/{id}")
    // 바로 주문 --> 저장할 곳이 없어서 cart에 저장하고 나중에 삭제하기로..! -->원래 order -
    public void directOrder(@PathVariable("id") String loginId,
                            @RequestParam(name = "amount", required = false, defaultValue = "0") Integer amount,
                            @RequestParam(name="dto", required = false) String[] strings,
                            @RequestParam("productId") Integer productId,
                            HttpSession session) throws Exception {

        CartDTO cartDTO = new CartDTO();
        if(strings != null)  cartDTO = cartService.convertDTO(strings);

        cartService.saveCart(loginId, cartDTO, productId, amount);

        CartDTO cart = cartService.findCart(loginId); // 제일 마지막에 저장된 dto 가져오기

        Integer cartP = cart.getPrice() * cart.getAmount();
        Integer totalP = cartP;

        if(session.getAttribute("cartP") != null) {
            cartP += Integer.parseInt(session.getAttribute("cartP").toString());

            String[] tmp = (String[]) session.getAttribute("cartIds");
            String[] cartIds = new String[tmp.length+1];
             for(int i = 0; i < tmp.length; i++) {
                cartIds[i] = tmp[i];
            }
            cartIds[tmp.length] = cart.getCartId().toString();
            session.setAttribute("cartIds", cartIds);
        } else {
            String[] cartIds = new String[1];
            cartIds[0] = cart.getCartId().toString();
            totalP = cartP + 3000;
            session.setAttribute("cartIds", cartIds);
        }
        session.setAttribute("from","direct");
        session.setAttribute("cartP", cartP );
        session.setAttribute("totalP", totalP);
    }

    @ResponseBody
    @PostMapping("order/image/{id}")
    public void saveCart(@PathVariable("id") String loginId,
                         @RequestPart("fileData") MultipartFile file) throws Exception {

        ModelAndView modelAndView = new ModelAndView();

        CartDTO cartDTO = cartService.findCart(loginId); // 제일 마지막에 저장된 dto 가져오기
        cartService.saveImag(cartDTO, file);

        List<CartDTO> cartList = cartService.findUserId(loginId);
    }


//  ------------------------------------- ★ 주문 ★ ------------------------------------------------------------

    @GetMapping("order")
    public ModelAndView order(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();

        String cartP = session.getAttribute("cartP").toString();
        String totalP = session.getAttribute("totalP").toString();
        String[] cartId = (String[]) session.getAttribute("cartIds");

        // 처리 로직 작성
        List<CartDTO> cartList = cartService.cartIdList(cartId);
        UserDTO userDTO = cartList.get(0).getUserDTO();

        if(session.getAttribute("from") != null) {
            modelAndView.addObject("from",session.getAttribute("from").toString());
            session.removeAttribute("from");
            System.out.println("from 들어옴");
        }
        session.removeAttribute("cartP");
        session.removeAttribute("totalP");
        session.removeAttribute("cartIds");

        modelAndView.addObject("page", "order");
        modelAndView.addObject("cartP", cartP);
        modelAndView.addObject("totalP", totalP);
        modelAndView.addObject("userInfo", userDTO);
        modelAndView.addObject("cartList", cartList);
        modelAndView.setViewName("pages/order/orderList.html");
        return modelAndView;
    }

//  ------------------------------------- ★ 주문 저장 ★ ------------------------------------------------------------
    @ResponseBody
    @PostMapping("order/order/save")
    public Integer saveOrder(@RequestParam("order") String[] order,
                             @RequestParam("userId") String userId) {
        OrderDTO orderDTO = orderService.saveOrder(order, userId);

        return orderDTO.getOrderId();
    }

//  ------------------------------------- ★ 주문디테일 저장 / 카트 삭제 ★ ------------------------------------------------------------
    @ResponseBody
    @PostMapping("order/odetail/save")
    public void saveOdetail(@RequestParam("odetailDTO")String[] odetail,
                            @RequestParam("orderId") Integer orderId,
                            @RequestParam("cartId") Integer cartId,
                            HttpSession session) {
        orderService.saveOdetail(odetail, orderId, cartId);
        cartService.delCartId(cartId);
        session.setAttribute("orderId",orderId);

    }
//  ------------------------------------- ★ order 완전 삭제★ ------------------------------------------------------------
    @ResponseBody
    @PostMapping("order/odetail/del")
    public void delOdetail(@RequestParam("orderId") Integer orderId) {
        orderService.delOrderId(orderId);
    }

//  ------------------------------------- ★ 주문 완료 ★ ------------------------------------------------------------
    @GetMapping("order/complete") // 주문 -> 결제완료
    public ModelAndView complete(@RequestParam("orderId") Integer orderId,
                                 @RequestParam("loginId") String loginId) {
        ModelAndView modelAndView = new ModelAndView();
        OrderDTO orderDTO = orderService.getOrderList(loginId);
        List<OrderDetailDTO> orderDetailList = orderService.getOdetailList(orderId);
        Integer length = orderDetailList.size();
        String product = orderDetailList.get(0).getProductDTO().getName();

        userService.calcMileage(orderDTO);
        productService.countAmount(orderDetailList);

        modelAndView.addObject("page", "complete");
        modelAndView.addObject("orderDTO", orderDTO);
        modelAndView.addObject("length", length);
        modelAndView.addObject("product", product);
        modelAndView.setViewName("pages/order/complete.html");
        return modelAndView;
    }

//  ------------------------------------- ★ 관리자 페이지★ ------------------------------------------------------------
//  ------------------------------------- ★ 전체 / sort order List ★ ------------------------------------------------------------

    @GetMapping("admin/order")
    public ModelAndView allOrderList (@RequestParam(name = "sort", defaultValue = "all", required = false) String status){

        ModelAndView modelAndView = new ModelAndView();
        List<OrderDetailDTO> orderDetailDTOS = orderService.getAllOrderDetailList();
        List<OrderDetailDTO> orderDetailDTOList = orderService.getSotrOdetailList(orderDetailDTOS, status);

        modelAndView.addObject("status", status);
        modelAndView.addObject("oDetailList", orderDetailDTOList);
        modelAndView.setViewName("admin/order/orderList.html");

        return modelAndView;
    }
    @GetMapping("/admin/order/detail")
    public ModelAndView adminOrderDetail (@RequestParam("odetailId") Integer odetailId){

        ModelAndView modelAndView = new ModelAndView();
        OrderDetailDTO orderDetailDTO = orderService.getOrderDetail(odetailId);

        modelAndView.addObject("odetail",orderDetailDTO);
        modelAndView.setViewName("admin/order/orderDetail.html");


        return modelAndView;
    }
    @GetMapping("admin/order/status")
    public ModelAndView adminOrderStatusF(@RequestParam("odetailIds") List<Integer> odetailIds) {
        ModelAndView modelAndView = new ModelAndView();
        List<CategoryDTO> categoryDTOS = categoryService.findCategory("order_"); // 주문 정보 가져오기
        categoryDTOS.add(categoryService.findCategoryDTO("rf_03"));

        modelAndView.addObject("odetailIds", odetailIds);
        modelAndView.addObject("category", categoryDTOS );
        modelAndView.setViewName("/admin/order/orderStatus.html");
        return modelAndView;
    }

    @PostMapping("admin/order/status")
    public ModelAndView adminOrderStatus(@RequestParam("status") String status,
                                         @RequestParam("odetailIds") String[] odetailIds,
                                         HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();

        if("반품 확정".equals(status)){
            orderService.adminCancelOrder(odetailIds); //order ,odetail 반품 수정
            refundService.adminSetRefund(odetailIds, session.getAttribute("loginId").toString()); // refund 생성 저장
        } else {
            orderService.changeStatus(status, odetailIds);
        }
        modelAndView.addObject("data", new Message("변경되었습니다.","/close" ));
        modelAndView.setViewName("/common/message.html");
        return modelAndView;
    }

}
