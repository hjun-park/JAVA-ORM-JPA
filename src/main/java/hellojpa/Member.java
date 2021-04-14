package hellojpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Entity             // JPA 로딩될 때 JPA 사용하는 애구나 관리해야겠다 라고 인식
//@Table(name = "USER") // Table을 안 적으면 관례상 테이블을 클래스 이름으로 설정해서 쿼리를 보내게 된다.
public class Member {

    @Id         // id는 Primary key 라고 알려줌
    private Long id;                    // primary name

    // @Column(unique = true, length = 10) // unique 제약조건 + 최대길이 10
    // @Column(nullable = false) // NOT NULL
    // @Column(precision.scale) // 큰 숫자나 소숫점 사용
    @Column(name = "name", insertable = true, updatable = true) // 등록하고 변경이 가능한지
    private String username;            // ( 컬럼명이 name )

    private Integer age;                // INT

    @Enumerated(EnumType.STRING)        // enum은 ORDINAL 사용 X, 값이 아닌 순서를 저장하기 때문에.
    private RoleType roleType;          // DB에는 enum이 없기 때문에 Enumerated 어노테이션 사용

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;            // 날짜 타입 ( 생성일자 ) (( 날짜, 시간, 타임스탬프(날짜+시간)를 DB에서 사용해서 선택 필요 ))

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;      // 날짜 타입 ( 수정일자 )

    // hibernate 에서 이렇게 써도 인식함 ( 최신 버전에서의 사용 )
    private LocalDate testLocalDate;
    private LocalDateTime testLocalDateTime;

    @Lob
    private String description;         // varchar를 넘어서는 큰 컨텐츠를 넣기 위해서는 Lob을 이용한다.

    @Transient
    private int temp;                   // DB와 관련없이 메모리에만 쓰고 싶다. ( 실제로 퀄리로 전송되지 않음 )


    /* JPA는 기본적으로 내부적으로 reflection 을 쓰기 때문에 기본 생성자 필요 */
    public Member() {
    }


}
