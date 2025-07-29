package util;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import model.Matches;
import model.Player;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;

@Slf4j
public class HibernateUtil {
    private HibernateUtil() {
    }

    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            configuration.addAnnotatedClass(Player.class);
            configuration.addAnnotatedClass(Matches.class);

            return configuration.buildSessionFactory();
        } catch (Throwable ex) {
            log.warn("SessionFactory build failed", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
