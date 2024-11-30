package com.sparta.msa_exam.product.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    //    @Id
//    @GeneratedValue
    private Long id;

    private String name;

    private long price;


}
