package com.example.order.service;

import com.example.order.dto.OrderDto;
import com.example.order.entity.*;
import com.example.order.jwt.JwtUtil;
import com.example.order.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final UsersRepository usersRepository;
    private final ItemRepository itemRepository;
    private final JwtUtil jwtUtil;

    // 주문 처리 및 결제 정보 저장
    @Transactional
    public void orderItem(String token, OrderDto orderDto) {
        String email = jwtUtil.getEmail(token);
        User user = usersRepository.getReferenceById(email);
        List<CartEntity> cartItems = cartRepository.findByEmail(email);
        if (cartItems.isEmpty()) {
            throw new IllegalStateException("장바구니가 비어있어 주문할 수 없습니다.");
        }

        List<ItemEntity> itemEntities = itemRepository.findListByPopIdAndItemId(orderDto.getPopId(), orderDto.getItemId());



        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        orderDto.setOrderDate(currentDate);

        long totalPrice = 0L;
        String firstItemName = cartItems.get(0).getItemName(); // 첫 번째 아이템 이름 저장

        for (CartEntity cartItem : cartItems) {
            totalPrice += cartItem.getPrice();
        }

        // 잔액 검사
        if (user.getPoint() < totalPrice) {
            throw new IllegalArgumentException("구매자의 잔액이 부족합니다.");
        }

        // 수량 차감
        for (CartEntity cartItem : cartItems) {
            ItemEntity itemEntity = itemRepository.findById(cartItem.getItemId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 아이템을 찾을 수 없습니다."));

            if (itemEntity.getAmount() < cartItem.getAmount()) {
                throw new IllegalStateException("상품의 재고가 부족합니다.");
            }
            // 잔액 차감
            user.setPoint(user.getPoint() - totalPrice);
            System.out.println("유저의 남은 잔액: " + user.getPoint());
            usersRepository.save(user);

            // 재고 차감
            itemEntity.setAmount(itemEntity.getAmount() - cartItem.getAmount());

            itemRepository.save(itemEntity);
        }

        PaymentEntity paymentEntity = PaymentEntity.builder()
                .buyerEmail(email)
                .orderDate(currentDate)
                .totalPrice(totalPrice)
                .orderItem(firstItemName)
                .build();

        paymentEntity = paymentRepository.save(paymentEntity);
        Long paymentId = paymentEntity.getPaymentId();

        for (CartEntity cartItem : cartItems) {
            OrderEntity orderEntity = OrderEntity.builder()
                    .itemId(cartItem.getItemId())
                    .popId(cartItem.getPopId())
                    .email(email)
                    .totalPrice(cartItem.getPrice())
                    .itemName(cartItem.getItemName())
                    .buyerName(orderDto.getBuyerName())
                    .buyerAddress(orderDto.getBuyerAddress())
                    .buyerPhone(orderDto.getBuyerPhone())
                    .totalAmount(cartItem.getAmount())
                    .orderDate(orderDto.getOrderDate())
                    .imageUrl(cartItem.getImageUrl())
                    .paymentId(paymentId)
                    .build();

            orderRepository.save(orderEntity);
        }

        // 장바구니 비우기
        cartRepository.deleteByEmail(email);
    }

    // 주문 상세정보
    public List<OrderDto> getOrderItems(String token, Long paymentId) {
        String email = jwtUtil.getEmail(token);

        // paymentId로 PaymentEntity 조회
        PaymentEntity paymentEntity = paymentRepository.findByPaymentId(paymentId);
        if (paymentEntity == null) {
            throw new RuntimeException("해당 paymentId의 결제 정보를 찾을 수 없습니다: " + paymentId);
        }

        // email과 paymentId로 OrderEntity 조회
        List<OrderEntity> orderEntities = orderRepository.findByEmailAndPaymentId(email, paymentEntity.getPaymentId());

        if (orderEntities == null) {
            throw new RuntimeException("해당 email의 주문을 찾을 수 없습니다.: " + email + "해당 paymentId의 주문을 찾을 수 없습니다.: " + paymentId);
        }

        return orderEntities.stream()
                .map(order -> OrderDto.builder()
                        .orderId(order.getOrderId())
                        .itemId(order.getItemId())
                        .popId(order.getPopId())
                        .email(order.getEmail())
                        .totalPrice(order.getTotalPrice())
                        .itemName(order.getItemName())
                        .buyerName(order.getBuyerName())
                        .buyerAddress(order.getBuyerAddress())
                        .buyerPhone(order.getBuyerPhone())
                        .totalAmount(order.getTotalAmount())
                        .orderDate(order.getOrderDate())
                        .imageUrl(order.getImageUrl())
                        .paymentId(paymentEntity.getPaymentId())
                        .build())
                .toList();
    }
}
