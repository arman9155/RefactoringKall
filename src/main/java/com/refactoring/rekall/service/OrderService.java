package com.refactoring.rekall.service;

import com.refactoring.rekall.dto.OrderDTO;
import com.refactoring.rekall.dto.OrderDetailDTO;
import com.refactoring.rekall.entity.OrderDetailEntity;
import com.refactoring.rekall.entity.OrderEntity;
import com.refactoring.rekall.repository.OrderDetailRepository;
import com.refactoring.rekall.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

//  ------------------------------------- odetailDTO --------------------------------------------------------------------
//  ------------------------------------- ★ orderDTO 개별★ --------------------------------------------------
//  ------------------------------------- ★ loginId별 / sort 별 List ★ --------------------------------------------------
    public List<OrderDTO> getOrderList(String loginId, String status) {
        List<OrderEntity> orderEntities = orderRepository.findByUserEntityUserIdOrderByOrderIdDesc(loginId);
        List<OrderDTO> orderDTOList = getSotrList(orderEntities, status);

        return orderDTOList;
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
        } else if(status.equals(status)) {
            for (OrderEntity orderEntity : orderList) {
                if (orderEntity != null && orderEntity.getStatus().equals(status)) {
                    orderDTOList.add(OrderDTO.toOrderDTO(orderEntity));
                }
            }
        }

        return orderDTOList;
    }

//  ------------------------------------- ★ 전체 / sort List ★ --------------------------------------------------
    public List<OrderDTO> getAllOrderList(String status) {
        List<OrderEntity> orderEntities = orderRepository.findAll();
        List<OrderDTO> orderDTOList = getSotrList(orderEntities, status);

        return orderDTOList;
    }
    
//  ------------------------------------- ★ 주문 상세 정보 전달★ --------------------------------------------------

    public OrderDetailDTO getOrderDetail(Integer odetailId) {
        Optional<OrderDetailEntity> optionalOrderDetailEntity = orderDetailRepository.findById(odetailId);
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        if(optionalOrderDetailEntity.isPresent()) {
            orderDetailDTO = OrderDetailDTO.toOrderDeatilDTO(optionalOrderDetailEntity.get());
        }
        return orderDetailDTO;
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
            orderDetailDTOS.add(OrderDetailDTO.toOrderDeatilDTO(orderDetailEntity));
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

    //  ------------------------------------- ★ odetailId로 orderDTO 찾기 ★ --------------------------------------------------
    public OrderDTO getOrderDTO(Integer odetailId) {
        Optional<OrderEntity> orderEntity = orderRepository.findByOrderDetailEntityOdetailId(odetailId);
        OrderDTO orderDTO = new OrderDTO();

        if(orderEntity.isPresent()) orderDTO = OrderDTO.toOrderDTO(orderEntity.get());

        return orderDTO;
    }
}



