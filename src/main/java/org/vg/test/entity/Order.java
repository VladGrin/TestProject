package org.vg.test.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Order {
    private long orderId;
    private String orderName;
    private int price;

}
