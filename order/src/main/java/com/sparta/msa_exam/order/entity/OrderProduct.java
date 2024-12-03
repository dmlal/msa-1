package com.sparta.msa_exam.order.entity;

import com.sparta.msa_exam.order.vo.Price;
import com.sparta.msa_exam.order.vo.Quantity;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    @Embedded
    private Price productPrice;

    @Embedded
    private Quantity quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public OrderProduct(Order order, Long productId, Price productPrice, Quantity quantity) {
        this.order = order;
        this.productId = productId;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
