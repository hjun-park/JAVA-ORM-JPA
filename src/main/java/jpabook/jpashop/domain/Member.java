package jpabook.jpashop.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBER_ID")
    private Long id;
    private String name;
    private String street;
    private String zipcode;

    // Order에서 해준 것 반대로 Member에서는 order를 담은 배열을 선언하고 MappedBy까지 지정한다.
    // 외래 키가 있는 것을 주인으로 정한다. 그렇지 않으면 다른 테이블의 내용을 수정하기 때문에 이해하기가 어렵다.
    // (mappedby 를 사용하게되면 쓰기가 아닌 읽기만 된다, DB에 해당값 쓰려도 안 써진다. )
    // 결론 : 연관관계 주인이 되는 쪽은 외래키를 가지고 있으며 관계차수가 N인 쪽
    @OneToMany(mappedBy = "member") // 연관관계 주인이 아닌 쪽에서는 mappedby를 써서 주인을 지정해준다., 외래키 있는게 주인
    private List<Order> orders = new ArrayList<>(); // 리스트로 따로 만들어 놓은 쪽, (mappedby를 쓰면 양방향 연관관계)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
