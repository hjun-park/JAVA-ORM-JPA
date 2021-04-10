package hellojpa;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");    // persistence.xml 에 있는 hello

        EntityManager em = emf.createEntityManager();   // DB 커넥션 받았다고 생각
                                                        // em은 쓰레드간에 공유하면 안됨 ( 사용하고 버려야 한다 )

        EntityTransaction tx = em.getTransaction();    // 트랜잭션 얻어오기
        tx.begin();     // 트랜잭션 시작


        // 이렇게 try 문을 적지만 사실 spring boot에서는 em.persist(data); 만 해도 알아서 해준다.
        try {

            // 영속
//            Member member1 = new Member(150L, "A");
//            Member member2 = new Member(160L, "B");

//            em.persist(member1);
//            em.persist(member2);       // 영속성 컨텍스트에 넣고 아래에 커밋
                                       // 커밋을 넣을 때 쿼리가 나간다.


            /* dirty checking 원리
               1. 처음 실행 시 1차 캐시에 대해 스냅샷을 찍는다.
               2. commit을 하게 되면 flush()를 보낸다.
               3. 엔티티와 스냅샷을 비교한다.
               4. 변경 부분이 있으면 update 쿼리를 날린다.
               5. flush하고 커밋
             */

            Member member = em.find(Member.class, 150L);
            member.setName("ZZZZZ");    // 수정 시 영속성 컨텍스트에 들어있기 때문에 persist 안 써도 된다.
                                        // 수정이 되면 알아서 update 쿼리를 보내준다. ( 변경감지 : dirty checking )






            // // 001 비영속, 영속
            // // 비영속 상태  ( JPA와 상관없음 )
            // Member member = new Member();
            // member.setId(101L);
            // member.setName("HelloJPA");
            //
            // // 영속 ( 객체를 저장한 상태 :: 엔티티 매니저 통해서 관리가 됨 )
            // // 사실 persist 썼다고 해서 DB에 바로 저장되는 건 아니다. 커밋을 해야 저장이 된다.
            // // 영속성 컨텍스트 안에 있는 1차 캐시에 저장이 되고 커밋을 하게되면 DB에 들어가게 된다.
            // em.persist(member);
            //
            // // 영속성 컨텍스트에 있는 member를 출력하면 아직 커밋하지 않았으므로
            // // 쿼리가 나가지 않고 1차 캐시에 있는 정보를 가지고 출력되는 것을 볼 수 있다.
            // Member findMember = em.find(Member.class, 101L);

            // 002 엔티티 매니저
            // 같은 ID를 여러 번 조회해도 쿼리는 1번만 나가게 된다.
            // DB에서 가져와서 영속성 컨텍스트 1차 캐시에 올리기 때문에 한 번 가져오면 그 이후는 가져올 필요가 없기 때문

//            em.detach(member);  // 영속성 컨텍스트를 다시 지움 ( 준영속 )
//            em.remove(member);  // 실제 DB에서 지우는 것

            // 003 데이터 삽입
            // Member member = new Member();
            // member.setId(2L);
            // member.setName("HelloB");
            // em.persist(member);

            // 004 데이터 수정 ( Entity Manager 통해서 데이터 가져오면 JPA가 관리하게 됨 )
//            Member findMember = em.find(Member.class, 1L);
//            System.out.println("findMember.id = " + findMember.getId());
//            System.out.println("findMember.name = " + findMember.getName());
//            findMember.setName("helloJPA");
            // 수정하고 em.persist 할 필요없이 setName만으로도 값이 변경됨


            // 005 JPQL을 이용한 데이터 수정
            // JPQL은 엔티티 객체 대상으로 쿼리,  SQL은 DB 테이블을 대상으로 쿼리
//            List<Member> result = em.createQuery("select m from Member as m", Member.class)
//                    .setFirstResult(5)
//                    .setMaxResults(8)   // 5번부터 8개까지 가져와
//                    .getResultList();   // 쿼리를 리스트 형식으로 반환
//
//            for (Member member : result) {
//                System.out.println("member = " + member.getName());
//            }

            // 데이터 삭제
            // em.remove(findMember);

            tx.commit();    // 한 트랜잭션이 끝난 후 커밋까지
        } catch (Exception e) {
            tx.rollback();  // 예외처리 발생 시 잡아서 롤백
        } finally {
            em.close();     // 엔티티 매니저는 끝내주는게 좋음
        }

        emf.close();    // 엔티티 매니저 팩토리 종료

    }
}
