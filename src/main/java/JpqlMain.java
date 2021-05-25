import jpql.Member;
import jpql.MemberDTO;

import javax.persistence.*;
import java.util.List;

public class JpqlMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            for (int i=0;  i<100; i++) {
                Member member = new Member();
                member.setUsername("member"+i);
                member.setAge(i);
                em.persist(member);

            }


            // ======================= 01 : JPQL 기본문법 =========================

//            // 반환형이 명확하지 않은 경우 Query
//            Query query3 = em.createQuery("select m.username, m.age from Member m");
//
//            // 반환형이 정해진 경우 TypedQuery
//            TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
//            TypedQuery<Member> query2 = em.createQuery("select m from Member m where m.username = :username", Member.class);
//
//            // 01. 쿼리 결과가 하나인 경우
//            // getSingleResult는 정확히 결과가 하나만 나와야 한다.
//            // 결과가 없거나 둘 이상이면 Exception이 터진다.
//            query2.setParameter("username", "member1"); // query2로부터 member1을 찾으려 바인딩 시킴
//                                                             // 바인딩은 무조건 이름 기준으로 할 것 (위치 ㄴ)
//            Member singleResult = query.getSingleResult();
//            System.out.println("singleResult = " + singleResult);
//
//            // 쿼리 결과 여러 개인 경우
//            // 만약 값이 없는 빈 리스트가 반환되어 exception 걱정하지 않아도 된다.
//            List<Member> resultList = query.getResultList();
//            for (Member member1 : resultList) {
//                System.out.println("member1 = " + member1);
//            }
            // ==================================================


            // ======================= 02 : 프로젝션 =========================
//            em.flush();
//            em.clear();
//
//            // 쿼리를 이용하여 가져온 데이터는 영속성 컨텍스트에 등록되어 관리됨
//            // 두 번째 인자는 반환 형태 클래스
//
//            // 01. 프로젝션은 아래와 같이 다 가능
//            // select m from member m => 엔티티 프로젝션
//            // select m.team from member m => 엔티티 프로젝션
//            // select m.address from member m => 임베디드 타입 프로젝션
//            // select m.username, m.age from meber m => 스칼라 타입 프로젝션 ( 막 가져오는 )
//            // => 스칼라 타입은 반환 타입이 없다. ( 두 번째 인자가 없음 )
//            List<Member> result = em.createQuery("select m from Member m", Member.class)
//                    .getResultList();
//
//            Member findMember = result.get(0);
//            findMember.setAge(20);  // 영속성 컨텍스트에 관리되므로 Update 쿼리 날아감
//
//            // 02. 여러 값 조회
//            // 위에는 Query 타입으로 조회
//            // 아래는 new 명령어로 조회 ( 생성자를 통해서 )
//            em.createQuery("select m from Member m", MemberDTO.class);
//            List<MemberDTO> result3  = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
//                    .getResultList();
//
//            MemberDTO memberDTO = result3.get(0);
//            System.out.println("memberDTO = " + memberDTO.getUsername());
//            System.out.println("memberDTO = " + memberDTO.getAge());


            // ======================= 03 : 페이징 API =========================
            // JPA는 페이징을 다음 두 API로 추상화
            // 페이징 : 몇 번째부터 몇 개 가져올래 ?
            // 복잡하게 select from ... nested 3 selecet from을 사용해서 페이징을 하였다면,..(오라클)
            // JPA는 API로 추상화 해주었음

            em.flush();
            em.clear();

            // 페이징은 sorting하면서 가져오기 때문에 제대로 돌아가는거 보려면 정렬부터
            List<Member> result = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(1)  // 98부터 실행
                    .setMaxResults(10)
                    .getResultList();

            System.out.println("result.size = " + result.size());
            for (Member member1 : result) {
                System.out.println("member1 = " + member1);
            }


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }
}
