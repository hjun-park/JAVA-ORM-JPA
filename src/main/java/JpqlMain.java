import jpql.Member;
import jpql.MemberDTO;
import jpql.MemberType;
import jpql.Team;

import javax.persistence.*;
import java.util.List;

public class JpqlMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

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

            // ======================= 04 : Inner, Outer, Theta 조인 ============== //
            /*
//                내부 조인 :
//                 SELECT m FROM Member m [INNER] JOIN m.team t
//                  - 멤버는 있고 팀이 없는 경우 데이터가 아예 나오지 않음
//
//                외부 조인
//                 SELECT m FROM Member m LEFT [OUTER] JOIN m.team.t
//                  - 멤버는 있고 팀이 없는 경우 데이터가 팀 쪽은 NULL로 표시
//
//                세타 조인
//                  select count(m) from Member m, Team t where m.username = t.name
//                  - 멤버랑 팀 서로 카티션 곱
//             */
            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            member1.setAge(10);
            member1.setType(MemberType.ADMIN);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            member2.setAge(10);
            member2.setType(MemberType.ADMIN);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);
            member3.setAge(10);
            member3.setType(MemberType.ADMIN);
            em.persist(member3);


//            em.flush();
//            em.clear();
//
//
//            String innerJoinQuery = "select m from Member m inner join m.team t";
//            String outerJoinQuery = "select m from Member m left join m.team t";
//            String thetaJoinQuery = "select m from Member m, Team t where m.username =  t.name";
//
//            List<Member> result = em.createQuery(thetaJoinQuery, Member.class)
//                    .getResultList();
//
//            // 페이징은 sorting 하면서 가져오기 때문에 제대로 돌아가는거 보려면 정렬부터            // 페이징
////            List<Member> result = em.createQuery("select m from Member m order by m.age desc", Member.class)
////                    .setFirstResult(1)  // 98부터 실행
////                    .setMaxResults(10)
////                    .getResultList();
//
//            System.out.println("result.size() = " + result.size());


//            // ======================= 05 : JPQL 타입 ============== //
//            em.flush();
//            em.clear();
//
//            // enum 타입은 jpql에서 패키지명까지 다 적어주어야 한다.
////            String typeQuery = "select m.username, 'HELLO', true from Member m " +
////                    "where m.type = jpql.MemberType.ADMIN";
//
//            //위 대신에 편하게 아래와 같이 쓸 수 있다.
//            String typeQuery = "select m.username, 'HELLO', true from Member m " +
//                    "where m.type = :userType";
//
//            List<Object[]> typeResult = em.createQuery(typeQuery)
//                    .setParameter("userType", MemberType.ADMIN)
//                    .getResultList();
//
//            for (Object[] objects: typeResult) {
//                System.out.println("objects = " + objects[0]);
//                System.out.println("objects = " + objects[1]);
//                System.out.println("objects = " + objects[2]);
//            }
//
//
//
//            // ======================= 06 : CASE 식 ============== //
//            em.flush();
//            em.clear();
//
//            String query =
//                    "select " +
//                            "case when m.age <= 10 then '학생요금' " +
//                            "     when m.age >= 60 then '경로요금' " +
//                            "     else '일반요금' " +
//                            "end " +
//                    "from Member m";
//            List<String> result = em.createQuery(query, String.class)
//                    .getResultList();
//
//            for (String s : result) {
//                System.out.println("s = " + s);
//
//            }
//
//
//            // ======================= 07 : CASE + COALESCE ============== //
//            // COALESCE : 하나씩 조회해서 null이 아니면 반환
//            // NULLIF : 두 값이 같으면 null 반환, 다르면 첫 번째 값 반환
//            em.flush();
//            em.clear();
//
//            String coalesceQuery = "select coalesce(m.username, '이름 없는 회원') as username " +
//                    "from Member m ";
//            String nullifQuery = "select nullif(m.username, '관리자') as username " +
//                    "from Member m ";
//
//            List<String> coalesceResult = em.createQuery(coalesceQuery, String.class)
//                    .getResultList();
//
//            for (String s : coalesceResult) {
//                System.out.println("s = " + s);
//            }


            // ======================= 08 : fetch join ============== //
            // DB SQL의 join 종류는 아니고 JPQL에서 성능 최적화 위해 제공 기능
            // [JPQL] select m from Member m join fetch m.team
            // [SQL] SELECT M.*, T.* FROM MEMBER M
            //       INNER JOIN TEAM T ON M.TEAM_ID=T.ID
            em.flush();
            em.clear();

            String query = "select m From Member m";

            List<Member> result = em.createQuery(query , Member.class)
                    .getResultList();

            // getname 호출하는 시점마다 DB에 쿼리를 날리게 된다. (LAZY로 되어있으니)
            for (Member member : result) {
                System.out.println("member = " + member.getUsername() + ", " + member.getTeam().getName());
                // 회원1이 돌 때 팀A를 SQL 쿼리로 가져오게된다. ( 영속성 컨텍스트에 없기 때문 )
                // ==> 회원1, 팀A(SQL)
                // 회원2도 팀A소속, JPA에게 달라고 하면 그냥 영속성 컨텍스트에서 가져옴
                // ==> 회원2, 팀A(1차캐시)
                // 회원3은 팀B 이기 때문에 영속성 컨텍스트에 없어서 SQL 쿼리로 가져오게 된다.
                // ==> 회원3, 팀B(SQL)
                //// ==> 총 나가는 쿼리 수는 3개 ( 멤버조회, 팀A조회, 팀B조회 )
                //// ==> 팀의 수에 따라서 EAGER 방식의 경우 N+1 문제가 발생할 수 있다.

            }

            em.flush();
            em.clear();

            // 지연로딩을 해도 fetch join이 우선권을 갖는다.
            String normalQuery = "select t From Team t"; // fetch join 없이는 팀은 2개로 나옴 (정상적 결과)
            String fetchJoinQuery = "select t From Team t join fetch t.members"; // 결과가 3개 (join하면서 데이터 뻥튀기, 중복결과)
                                    // 1:다 관계에서는 join 시 데이터 뻥튀기 가능성이 있다.

            List<Team> fetchResult = em.createQuery(fetchJoinQuery, Team.class)
                    .getResultList();

            System.out.println("result.size() = " + result.size());

            // 페치조인을 하게되면 쿼리가 한 번만 나간다.
            // 그 내용을 보면 중복이 된 것을 볼 수 있다.
            for (Team team : fetchResult) {
                System.out.println("team = " + team.getName() + "|members=" + team.getMembers().size());
                for (Member member : team.getMembers() ) {
                    System.out.println("-> member = " + member);
                }
            }

            // 중복된 결과를 제거하는 방법 : distinct 추가
            // SQL은 컬럼들이 100% 중복된 결과여야 제거되지만
            // JPA에서는 컬렉션 중복을 제거해줌으로 아래 쿼리는 결과적으로 중복값을 제거해준다.
            String dtFetchJoinQuery = "select distinct t From Team t join fetch t.members"; // 결과가 3개 (join하면서 데이터 뻥튀기, 중복결과)


            // 그렇다면 패치 조인과 일반 조인의 차이는?
            //   - 일반 조인 실행 시 연관된 엔티티를 함께 조회하지 않음
            //   - 패치 조인을 사용할 때만 연관된 엔티티도 함께 조회 ( 즉시 로딩 )
            //   - 패치 조인은 객체 그래프를 SQL 한 번에 조회하는 개념

            // 대부분의 N+1 문제를 패치 조인으로 해결할 수 있다.
            

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
