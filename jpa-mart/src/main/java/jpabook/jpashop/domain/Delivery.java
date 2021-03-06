package jpabook.jpashop.domain;

import javax.persistence.*;

@Entity
public class Delivery extends BaseEntity {

    @Id @GeneratedValue
    private Long id;


    // 아래 대신 사용
    @Embedded
    private Address address;
//    private String street;
//    private String zipcode;

    private DeliveryStatus status;

    // 1대1 연관관계 중에서도 양방향 연관관계
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

}
