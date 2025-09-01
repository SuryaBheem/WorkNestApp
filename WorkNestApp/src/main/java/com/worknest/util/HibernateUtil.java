package com.worknest.util;

import com.worknest.model.Comment;
import com.worknest.model.Task;
import com.worknest.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    
    private static final SessionFactory sessionFactory;

    static {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            
            // Add annotated classes to the configuration
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Task.class);
            configuration.addAnnotatedClass(Comment.class);

            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            // Log the exception and rethrow it
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Gets the single instance of the SessionFactory.
     * @return The Hibernate SessionFactory.
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Closes the SessionFactory to release all resources.
     */
    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }
}