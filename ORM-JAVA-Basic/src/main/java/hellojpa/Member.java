package hellojpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Entity             // JPA 로딩될 때 JPA 사용하는 애구나 관리해야겠다 라고 인식
//@Table(name = "USER") // Table을 안 적으면 관례상 테이블을 클래스 이름으로 설정해서 쿼리를 보내게 된다.
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // DB에 따라 생성
    private Long id;

    @Column(name = "name", nullable = false)
    private String username;

    /* JPA는 기본적으로 내부적으로 reflection 을 쓰기 때문에 기본 생성자 필요 */
    public Member() {

    }


}
