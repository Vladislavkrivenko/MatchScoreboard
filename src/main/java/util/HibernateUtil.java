package util;

import lombok.extern.slf4j.Slf4j;
import model.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
@Slf4j
public class HibernateUtil {
//   private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure();
        Player player = Player.builder()
                .name("Vova")
                .build();
        log.info("User {}", player);
        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(player);

log.trace("Players is created {}",transaction );
            session.getTransaction().commit();

        }

    }

}
