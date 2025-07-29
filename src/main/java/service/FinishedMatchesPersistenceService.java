package service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import model.Match;
import model.Matches;
import model.Player;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

@Slf4j
public class FinishedMatchesPersistenceService {
    @Getter
    private static final FinishedMatchesPersistenceService instance = new FinishedMatchesPersistenceService();

    private FinishedMatchesPersistenceService() {
    }

    public void saveFinishedMatches(Match match) {
        log.info("Saving finished match to database: {}", match.getUuid());
        Player winner = match.getScore().getSetsPlayer1() > match.getScore().getSetsPlayer2()
                ? match.getPlayer1()
                : match.getPlayer2();

        Matches matches = Matches.builder()
                .firstPlayer(match.getPlayer1())
                .secondPlayer(match.getPlayer2())
                .winner(winner)
                .build();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(matches);
            transaction.commit();
            log.info("Matches saved to database.");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.error("Error saving finished match", e);
        }
    }
    public List<Matches> findFinishedMatches(String playerName, int page, int pageSize){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            String hql = """
                select m from Matches m
                where (:name is null or lower(m.firstPlayer.name) like lower(concat('%', :name, '%'))
                       or lower(m.secondPlayer.name) like lower(concat('%', :name, '%')))
                order by m.id desc
                """;
            return session.createQuery(hql,Matches.class)
                    .setParameter("name", playerName == null || playerName.isBlank() ? null : playerName)
                    .setFirstResult((page -1) * pageSize)
                    .setMaxResults(pageSize)
                    .list();
        }
    }
    public int countFilteredMatches(String playerName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = """
                select count(m) from Matches m
                where (:name is null or lower(m.firstPlayer.name) like lower(concat('%', :name, '%'))
                       or lower(m.secondPlayer.name) like lower(concat('%', :name, '%')))
                """;

            Long count = session.createQuery(hql, Long.class)
                    .setParameter("name", playerName == null || playerName.isBlank() ? null : playerName)
                    .uniqueResult();
            return count.intValue();
        }
    }
}
