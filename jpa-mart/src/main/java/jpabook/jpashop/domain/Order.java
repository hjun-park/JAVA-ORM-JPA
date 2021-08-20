package jpabook.jpashop.domain;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "ORDERS")
public class Order extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    //@Column(name = "MEMBER_ID")
    //private Long memberId;      // ## 객체지향스럽지 못한 설계,  ( 관계형 DB에 맞춘 설계 )

    // ## 따라서 아래와 같이 해야한다, 하지만 Member객체에서는 해당 Order가 있는지 모르니까 Member에 리스트를 넣어줘야 한다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    /*
        Delivery 매핑
     */
    // order 생성 시점에 배송 시점도 생성하겠단 의미 ( cascadetype.ALL ) - order 저장하면 delivery도 저장된다는 의미
    @OneToOne(fetch = FetchType.LAZY, cascade = ALL)
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;

    // 연관관계를 만들어줌 ( order Item에 있는 order가 외래키고 연관관계의 주인이 됨 )
    @OneToMany(mappedBy = "order", cascade = ALL)
    private List<OrderItem> orderItems = new ArrayList<>();
    //======================================================

    private LocalDateTime orderDate;    // order_date

    @Enumerated(EnumType.STRING) // enum에 사용
    private OrderStatus status;

    // 편의 메소드 - 양방향 연관관계
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);      // 주문상품 집어넣은 후
        orderItem.setOrder(this);       // 현재 나 자신의 order를 넣어서 양방향 연관관계가 걸리도록 함
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }


}
