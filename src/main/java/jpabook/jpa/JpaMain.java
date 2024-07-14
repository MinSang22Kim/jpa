package jpabook.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jpabook.jpa.domain.Member;
import jpabook.jpa.domain.Movie;
import jpabook.jpa.domain.Team;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team1 = new Team();
            team1.setName("teamA");
            em.persist(team1);

            Team team2 = new Team();
            team2.setName("teamB");
            em.persist(team2);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(team1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setTeam(team2);

            em.flush();
            em.clear();

            List<Member> members = em.createQuery("select m from Member m", Member.class)
                    .getResultList();

            // SQL: select * from Member;
            // SQL: select * from Team where TEAM_ID = xxx

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
