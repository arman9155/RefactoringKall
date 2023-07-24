package com.refactoring.rekall.controller;

import com.refactoring.rekall.dto.CartDTO;
import com.refactoring.rekall.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {

    @Autowired
    CartService cartService;
    @Autowired
    OrderController orderController;

    //  ------------------------------------- ★ Cart / Order 리스트★ -----------------------------------------------------------------

    @GetMapping("") // 장바구니 => userid에 맞는 리스트 뽑
    public ModelAndView cart(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        List<CartDTO> cartDTOList = cartService.findUserId(session.getAttribute("loginId").toString());

        // id 전달 받아서 id 넣으면 id 에 맞게 search
        modelAndView.addObject("page", "cart");
        modelAndView.addObject("cartList", cartDTOList);
        modelAndView.setViewName("pages/order/cart.html");
        return modelAndView;
    }

    @ResponseBody
    @PostMapping("/{id}") // 처음 저장
    public void saveCart(@PathVariable("id") String loginId,
                         @RequestParam(name = "amount", required = false, defaultValue = "0") Integer amount,
                         @RequestParam(name="dto", required = false) String[] strings,
                         @RequestParam("productId") Integer productId) throws Exception {

        CartDTO cartDTO = new CartDTO();
        if(strings != null)  cartDTO = cartService.convertDTO(strings);

        cartService.saveCart(loginId, cartDTO, productId, amount);
    }

    @ResponseBody
    @PostMapping("/image/{id}") // 장바구니 => userid에 맞는 리스트 뽑
    public void saveCart(@PathVariable("id") String loginId,
                         @RequestPart("fileData") MultipartFile file) throws Exception {

        ModelAndView modelAndView = new ModelAndView();

        CartDTO cartDTO = cartService.findCart(loginId); // 제일 마지막에 저장된 dto 가져오기
        cartService.saveImag(cartDTO, file);

        List<CartDTO> cartList = cartService.findUserId(loginId);
    }

    // 중간 저장 _ 수량 변경 등
    @ResponseBody
    @PostMapping("/change/{id}") // 처음 저장
    public void changeCart(@PathVariable("id") Integer cartId,
                           @RequestParam(name = "amount", required = false, defaultValue = "0") Integer amount) {

        cartService.changeCart(cartId, amount);
    }

    @ResponseBody
    @PostMapping("/del") // 장바구니 => userid에 맞는 리스트 뽑
    public void delCart(@RequestParam(name = "cartIds", required = false) String[] cartIds) {
        cartService.delCartIds(cartIds);
    }

    @ResponseBody
    @GetMapping("/toOrder")
    public void toOrder (@RequestParam("cartIds") String[] cartIds,
                         @RequestParam("cartP") String cartP,
                         @RequestParam("totalP") String totalP,
                           HttpSession session){

        session.setAttribute("cartP", cartP);
        session.setAttribute("totalP", totalP);
        session.setAttribute("cartIds", cartIds);
    }

}
