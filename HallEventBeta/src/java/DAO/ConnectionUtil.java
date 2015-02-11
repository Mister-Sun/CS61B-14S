package DAO;

import java.io.Serializable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class ConnectionUtil implements Serializable {

    private static final SessionFactory session;

    static {
        Configuration configuration = new AnnotationConfiguration();
        configuration.configure();
        session = configuration.buildSessionFactory();
    }

    public static Session getSession() {
        //System.out.println("@@ Session openned ...");
        return session.openSession();
    }

    public static SessionFactory getSessionFactory() {
        return session;
    }

    public static void closeSession() {
        getSession().close();
    }
}
