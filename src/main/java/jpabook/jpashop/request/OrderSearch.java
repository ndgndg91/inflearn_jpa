package jpabook.jpashop.request;

import jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;
}
