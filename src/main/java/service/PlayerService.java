package service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import model.Player;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

@Slf4j

public class PlayerService {
    @Getter
    private static final PlayerService instance = new PlayerService();

    private PlayerService() {
    }

    public Player findOrCreatePlayer(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Player player = session.createQuery("from Player  where name = :name", Player.class)
                    .setParameter("name", name)
                    .uniqueResult();

            if (player == null) {
                player = Player.builder().name(name).build();
                session.persist(player);
                log.info("Created player with name {}", name);
            } else {
                log.info("Found player with name {}", name);
            }
            tx.commit();
            return player;
        } catch (Exception e) {
            log.error("Error in findOrCreate", e);
            throw e;
        }
    }
}
