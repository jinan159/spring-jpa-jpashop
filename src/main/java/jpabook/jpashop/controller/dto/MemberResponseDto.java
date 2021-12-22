package jpabook.jpashop.controller.dto;

import jpabook.jpashop.domain.Address;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberResponseDto {

    private Long id;
    private String name;
    private Address address;

}
