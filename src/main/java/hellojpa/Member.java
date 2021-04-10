package hellojpa;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity             // JPA 로딩될 때 JPA 사용하는 애구나 관리해야겠다 라고 인식
//@Table(name = "USER") // Table을 안 적으면 관례상 테이블을 클래스 이름으로 설정해서 쿼리를 보내게 된다.
public class Member {

    @Id         // id는 Primary key 라고 알려줌
    private Long id;
    private String name;

    /* JPA는 기본적으로 내부적으로 reflection 을 쓰기 때문에 기본 생성자 필요 */
    public Member() {

    }
    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
