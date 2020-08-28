package com.marsarmy.dto;

import com.marsarmy.model.ProductsInOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO class of {@link ProductsInOrder} entity
 */
@NoArgsConstructor
@Getter
@Setter
public class ProductsInOrderDto {

    private Long id;
    private ProductDto productDto;
    private Long orderDto;
    private Integer numberOfProducts;
}
