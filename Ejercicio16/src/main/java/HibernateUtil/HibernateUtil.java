package HibernateUtil;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HibernateUtil {
    private static final Logger logger = LogManager.getLogger(HibernateUtil.class);
    private static SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
            logger.info("SessionFactory creada con éxito.");
        } catch (Exception e) {
            logger.error("Error en la conexión Hibernate: " + e.getMessage());
        }
    }
    public static SessionFactory getSessionFactory() { return sessionFactory; }
}