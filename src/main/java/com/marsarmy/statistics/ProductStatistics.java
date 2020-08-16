package com.marsarmy.statistics;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProductStatistics {

    private Long upc;
    private String name;
    private String color;
    private String brand;
    private String category;
    private Integer price;
    private Long quantitySold;
}