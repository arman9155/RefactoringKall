package com.refactoring.rekall.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.refactoring.rekall.dto.*;
import com.refactoring.rekall.entity.CartEntity;
import com.refactoring.rekall.entity.OrderDetailEntity;
import com.refactoring.rekall.entity.OrderEntity;
import com.refactoring.rekall.repository.CartRepository;
import com.refactoring.rekall.repository.OrderDetailRepository;
import com.refactoring.rekall.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    ImageService imageService;
    @Autowired
    UserService userService;
    @Autowired
    CartService cartService;
    @Autowired
    ProductService productService;


//  ------------------------------------- odetailDTO --------------------------------------------------------------------
//  ------------------------------------- ★ orderDTO 개별★ --------------------------------------------------
//  ------------------------------------- ★ loginId별 / sort 별 List ★ --------------------------------------------------
    public List<OrderDetailDTO> getOrderList(String loginId, String status) {

        List<OrderEntity> orderEntities = orderRepository.findByUserEntityUserIdOrderByOrderIdDesc(loginId);
        List<OrderDetailDTO> orderDetailDTOS = new ArrayList<>();

        if(orderEntities != null)  {
            List<OrderDTO> orderDTOList = getSotrList(orderEntities, status);

            for(OrderDTO orderDTO : orderDTOList) {

                if(orderDTO != null) {
                   List<OrderDetailEntity> orderDetailEntities = orderDetailRepository.findByOrderEntityOrderIdOrderByOdetailIdDesc(orderDTO.getOrderId());
                   for(OrderDetailEntity orderDetail : orderDetailEntities) orderDetailDTOS.add(OrderDetailDTO.toOrderDetailDTO(orderDetail));
                }

            }
        }
        return orderDetailDTOS;
    }
//  ------------------------------- ★ orderDTO loginId로 최근 주문건★ --------------------------------------------------
    public OrderDTO getOrderList(String loginId) {
       Optional<OrderEntity> opOrderEntity = orderRepository.findRecentOrderDTO(loginId);
       OrderDTO orderDTO = new OrderDTO();
        if(opOrderEntity.isPresent()) orderDTO = OrderDTO.toOrderDTO(opOrderEntity.get());

        return orderDTO;
    }
//  ------------------------------- ★ odetailDTO orderId로 주문 찾기★ --------------------------------------------------
    public List<OrderDetailDTO> getOdetailList(Integer orderId) {

        List<OrderDetailEntity> orderDetailEntities = orderDetailRepository.findByOrderEntityOrderIdOrderByOdetailIdDesc(orderId);
        List<OrderDetailDTO> orderDetailDTO = new ArrayList<>();

        if(orderDetailEntities != null)  {
            for(OrderDetailEntity orderDetailEntity : orderDetailEntities) {
                if(orderDetailEntity != null) {
                    orderDetailDTO.add(OrderDetailDTO.toOrderDetailDTO(orderDetailEntity));
                }

            }
        }
        return orderDetailDTO;
    }

    // ★ sort 별 List ★ --------------------------------------------------

    public List<OrderDTO> getSotrList(List<OrderEntity> orderList, String status) {

        List<OrderDTO> orderDTOList = new ArrayList<>();

        if(status.equals("all")) {
            for(OrderEntity orderEntity : orderList) {
                if(orderEntity != null) {
                    orderDTOList.add(OrderDTO.toOrderDTO(orderEntity));
                }
            }
        } else {
            for (OrderEntity orderEntity : orderList) {
                if (orderEntity != null && orderEntity.getStatus().equals(status)) {
                    orderDTOList.add(OrderDTO.toOrderDTO(orderEntity));
                }
            }
        }

        return orderDTOList;
    }

//  ------------------------------------- ★ 전체 / sort order List ★ --------------------------------------------------
    public List<OrderDTO>  getAllOrderList(String status) {
        List<OrderEntity> orderEntities = orderRepository.findAll(Sort.by(Sort.Direction.DESC,"orderId"));
        List<OrderDTO> orderDTOList = getSotrList(orderEntities, status);

        return orderDTOList;
    }
    //  ------------------------------------- ★ 전체  odetail List ★ --------------------------------------------------
    public List<OrderDetailDTO> getAllOrderDetailList() {
        List<OrderDetailEntity> orderDetailEntities = orderDetailRepository.findAll(Sort.by(Sort.Direction.DESC,"orderEntity.orderId"));
        List<OrderDetailDTO> orderDTOList = new ArrayList<>();
        for(OrderDetailEntity orderDetail : orderDetailEntities) {
            if(orderDetail != null) orderDTOList.add(OrderDetailDTO.toOrderDetailDTO(orderDetail));
        }

        return orderDTOList;
    }

    // ★ odetail sort 별 List ★ --------------------------------------------------

    public List<OrderDetailDTO> getSotrOdetailList(List<OrderDetailDTO> orderDetailList, String status) {

        List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();

        if(status.equals("all")) {
            for(OrderDetailDTO orderDetailDTO : orderDetailList) {
                if(orderDetailDTO != null) {
                    orderDetailDTOList.add(orderDetailDTO);
                }
            }
        } else {
            for (OrderDetailDTO orderDetailDTO : orderDetailList) {
                if (orderDetailDTO != null && orderDetailDTO.getOrderDTO().getStatus().equals(status)) {
                    orderDetailDTOList.add(orderDetailDTO);
                }
            }
        }

        return orderDetailDTOList;
    }
//  ------------------------------------- ★ 주문 상세 정보 전달★ --------------------------------------------------

    public OrderDetailDTO getOrderDetail(Integer odetailId) {
        OrderDetailDTO orderDetail = new OrderDetailDTO();

        Optional<OrderDetailEntity> orderDetailEntity = orderDetailRepository.findById(odetailId);
        if(orderDetailEntity != null) orderDetail = OrderDetailDTO.toOrderDetailDTO(orderDetailEntity.get());

        return orderDetail;
    }

    
//  ------------------------------------- ★ 주문 상세 사진 처리★ --------------------------------------------------
    public List<OrderDetailDTO> file(List<OrderDetailDTO> odetailDTOList, MultipartFile[] imgFiles, String loginId) throws Exception {
        String id = "0";
        if(orderDetailRepository.findId() == null) id = "1";
        else id = orderDetailRepository.findId() + 1 + "";
        List<OrderDetailDTO> orderDetailDTOS = new ArrayList<>();

        String saveName = "";

        for(int i = 0 ; i < odetailDTOList.size(); i++) {
            OrderDetailDTO orderDetailDTO = odetailDTOList.get(i);
            if(orderDetailDTO.getOption_image() != "") {
                MultipartFile file = imgFiles[i];
                saveName = imageService.saveImg("주문요청사항",loginId, id, file);
                orderDetailDTO.setOption_image(saveName);
            }
            OrderDetailEntity orderDetailEntity = orderDetailRepository.save(OrderDetailEntity.toOrderDetailEntity(orderDetailDTO));
            orderDetailDTOS.add(OrderDetailDTO.toOrderDetailDTO(orderDetailEntity));
        }
        return orderDetailDTOS;
    }

    //  ------------------------------------- ★ orderDTO List  유저 ★ --------------------------------------------------
    public List<OrderDTO> getMileageList(String loginId) {
        List<OrderEntity> orderEntities = orderRepository.findByUserEntityUserIdOrderByOrderIdDesc(loginId);
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for(OrderEntity orderEntity : orderEntities ) {
            if(orderEntity != null) orderDTOList.add(OrderDTO.toOrderDTO(orderEntity));
        }
        return orderDTOList;
    }

    //  ------------------------------------- ★ orderDTO 저장 ★ --------------------------------------------------
    public OrderDTO saveOrder(String[] order, String userId ) {
        OrderDTO orderDTO = convertOrderDTO(order);
        UserDTO userDTO = userService.findByUserID(userId);
        orderDTO.setUserDTO(userDTO);
        orderRepository.save(OrderEntity.toOrderEntity(orderDTO));
        System.out.println(orderDTO.getUserDTO());
        return findOrderId(userId);
    }
    //  ------------------------------------- ★ loginId로 orderDTO 저장 ★ --------------------------------------------------
    public OrderDTO findOrderId(String loginId) {
        Optional<OrderEntity> opOrderEntity = orderRepository.findRecentOrderDTO(loginId);
        OrderDTO orderDTO = new OrderDTO();
        if(opOrderEntity.isPresent()) orderDTO = OrderDTO.toOrderDTO(opOrderEntity.get());
        return orderDTO;
    }
    //  ------------------------------------- ★ Json -> orderDTO convert ★ --------------------------------------------------
    public OrderDTO convertOrderDTO(String[] strings) {

        JsonObject jsonObject;
        String json = "";
        for(int i = 0 ; i < strings.length; i++) {
            if(i == 0 ) {
                strings[i] = strings[i].replace("[", "{");
                json += strings[i];
            } else if(i == strings.length-1) {
                strings[i] = strings[i].replace("]", "}");
                json += "," + strings[i];
            }else json +=  ","+strings[i];
        }

        Gson gson = new Gson();
        OrderDTO orderDTO = gson.fromJson(json, OrderDTO.class);
        System.out.println(orderDTO);
        return orderDTO;
    }

    //  ------------------------------------- ★ odetailDTO 저장 ★ --------------------------------------------------
    public void saveOdetail(String[] odetail, Integer orderId, Integer cartId) {
        OrderDetailDTO odetailDTO = convertOdetailDTO(odetail);

        CartDTO cartDTO = cartService.getCartDTO(cartId);
        ProductDTO productDTO = productService.findByProductId(cartDTO.getProductDTO().getProductId());
        OrderDTO orderDTO = OrderDTO.toOrderDTO(orderRepository.findById(orderId).get());

        odetailDTO.setOrderDTO(orderDTO);
        odetailDTO.setProductDTO(productDTO);

        orderDetailRepository.save(OrderDetailEntity.toOrderDetailEntity(odetailDTO));
    }

    //  ------------------------------------- ★ Json -> odetailDTO convert ★ --------------------------------------------------
    public OrderDetailDTO convertOdetailDTO(String[] strings) {

        JsonObject jsonObject;
        String json = "";
        for(int i = 0 ; i < strings.length; i++) {
            if(i == 0 ) {
                strings[i] = strings[i].replace("[", "{");
                json += strings[i];
            } else if(i == strings.length-1) {
                strings[i] = strings[i].replace("]", "}");
                json += "," + strings[i];
            }else json +=  ","+strings[i];
        }

        Gson gson = new Gson();
        OrderDetailDTO odetailDTO = gson.fromJson(json, OrderDetailDTO.class);
        System.out.println(odetailDTO);
        return odetailDTO;
    }

    //  ------------------------------------- ★ odetailDTO 삭제 ★ --------------------------------------------------
    public void delOrderId(Integer orderId) {
        orderRepository.deleteById(orderId);
    }

    //  ------------------------------------- ★ odetailDTO 취소 요청 ★ --------------------------------------------------

    public void cancelOdetail(Integer odetailId) {
        Optional<OrderDetailEntity> orderDetailEntity = orderDetailRepository.findById(odetailId);
        OrderDetailEntity orderDetail = new OrderDetailEntity();
        if(orderDetailEntity.isPresent()) orderDetail = orderDetailEntity.get();

        orderDetail.setStatus("반품 요청");

        orderDetailRepository.save(orderDetail);
        // order 수정하기
        cancelOrder(orderDetail.getOrderEntity().getOrderId());
    }
    //  ------------------------------------- ★ order 취소 요청 ★ --------------------------------------------------
    public void cancelOrder(Integer orderId) {
        List<OrderDetailEntity> orderDetailList = orderDetailRepository.findByOrderEntityOrderIdOrderByOdetailIdDesc(orderId); // orderId에 포함된 odetail 찾기
        Optional<OrderEntity> optionalOrderEntity = orderRepository.findById(orderId); //order 가져오기
        OrderEntity orderEntity = new OrderEntity();
        if(optionalOrderEntity.isPresent()) orderEntity = optionalOrderEntity.get();
        int i = 0 ;

            for(OrderDetailEntity orderDetail : orderDetailList) {
                if("반품 요청".equals(orderDetail.getStatus())) i++; // 반품요청 있으면 count i
            }
            if(i == orderDetailList.size()) orderEntity.setStatus("반품 요청"); //i가 list만큼 증가 -> 모두 반품요청 -> order status 변경
            else if (i > 0) orderEntity.setStatus("부분 반품 요청"); // 전체 아니면 부분 반품으로 수정

       orderRepository.save(orderEntity);
    }

//  ------------------------------------- ★ admin ★ --------------------------------------------------
//  ------------------------------------- ★ order에서 주문 상태 변경★ --------------------------------------------------

    public void changeStatus(String status, String[] odetailIds) {
        for(String id : odetailIds) {
            OrderDetailDTO orderDetailDTO = getOrderDetail(Integer.parseInt(id));
            if(!"반품 확정".equals(orderDetailDTO.getStatus())) {
                orderDetailDTO.setStatus(status);
                orderDetailRepository.save(OrderDetailEntity.toOrderDetailEntity(orderDetailDTO));

                Optional<OrderEntity> optionalOrderEntity = orderRepository.findById(orderDetailDTO.getOrderDTO().getOrderId()); //order 가져오기
                OrderEntity orderEntity = new OrderEntity();
                if (optionalOrderEntity.isPresent()) orderEntity = optionalOrderEntity.get();
                orderEntity.setStatus(status);
                orderRepository.save(orderEntity);
            }
        }
    }

//  ------------------------------------- ★ order에서 admin이 반품으로 넘김★ --------------------------------------------------
    public void adminCancelOrder(String[] odetailIds) {
        // 1차 odetailIds 에 있는거 상품 상태 변경
        for(String id : odetailIds) {
            Integer orderId = confirmOdetailCancel(Integer.parseInt(id));
            confirmOrderCancel(orderId);
        }
    }

    //  ------------------------------------- ★ odetailDTO 취소 확정★ --------------------------------------------------
    public Integer confirmOdetailCancel(Integer odetailId) {
        Optional<OrderDetailEntity> orderDetailEntity = orderDetailRepository.findById(odetailId);
        OrderDetailEntity orderDetail = new OrderDetailEntity();
        if(orderDetailEntity.isPresent()) orderDetail = orderDetailEntity.get();

        orderDetail.setStatus("반품 확정");

        orderDetailRepository.save(orderDetail);

        return orderDetail.getOrderEntity().getOrderId();
    }

    //  ------------------------------------- ★ 주문 상태 변경 ★ --------------------------------------------------
    public void confirmOrderCancel(Integer orderId) {
        List<OrderDetailEntity> orderDetailList = orderDetailRepository.findByOrderEntityOrderIdOrderByOdetailIdDesc(orderId); // orderId에 포함된 odetail 찾기
        Optional<OrderEntity> optionalOrderEntity = orderRepository.findById(orderId); //order 가져오기
        OrderEntity orderEntity = new OrderEntity();
        if(optionalOrderEntity.isPresent()) orderEntity = optionalOrderEntity.get();
        int i = 0 ;
        int j = 0;

        for(OrderDetailEntity orderDetail : orderDetailList) {
            if("반품 요청".equals(orderDetail.getStatus())) i++; // 반품요청 있으면 count i
            else if ("반품 확정".equals(orderDetail.getStatus())) j++;
        }
        if(i == orderDetailList.size()) orderEntity.setStatus("반품 요청"); //i가 list만큼 증가 -> 모두 반품요청 -> order status 변경
        else if (j == orderDetailList.size()) orderEntity.setStatus("반품 확정");
        else if (j > 0) orderEntity.setStatus("부분 반품 확정");
        else if (i > 0) orderEntity.setStatus("부분 반품 요청"); // 부분 반품으로 수정

        orderRepository.save(orderEntity);
    }


}



