package jpabook.jpashop.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookResponseDto {

    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

}
