package com.sparta.msa_exam.order.entity;

import com.sparta.msa_exam.order.common.exception.CustomException;
import com.sparta.msa_exam.order.common.exception.ErrorCode;
import com.sparta.msa_exam.order.entity.enums.OrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Integer version;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private long totalPrice;

    private Long userId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProductList = new ArrayList<>();

    @Builder(toBuilder = true)
    public Order(Long userId, List<OrderProduct> orderProductList, OrderStatus orderStatus) {
        this.userId = userId;
        this.orderProductList = orderProductList != null ? orderProductList : new ArrayList<>();
        this.orderStatus = orderStatus;
        calculateTotalPrice();
    }

    private void calculateTotalPrice() {
        this.totalPrice = orderProductList.stream()
                .mapToLong(orderProduct ->
                        orderProduct.getProductPrice().getPrice() * orderProduct.getQuantity().getQuantity())
                .sum();
    }

    public void addOrderProduct(OrderProduct orderProduct) {
        if (!OrderStatus.PENDING.equals(this.orderStatus)) {
            throw new CustomException(ErrorCode.CAN_NOT_ADD_PRODUCT);
        }
        if (this.orderProductList == null) {
            this.orderProductList = new ArrayList<>();
        }
        this.orderProductList.add(orderProduct);
        orderProduct.setOrder(this);
        calculateTotalPrice();
    }

    public void removeOrderProduct(OrderProduct orderProduct) {
        if (!OrderStatus.PENDING.equals(this.orderStatus)) {
            throw new CustomException(ErrorCode.CAN_NOT_ADD_PRODUCT);
        }
        this.orderProductList.remove(orderProduct);
        calculateTotalPrice();
    }

    public void updateOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

}
