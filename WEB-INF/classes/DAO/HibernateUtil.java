/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

//import org.hibernate.cfg.AnnotationConfiguration;
//import org.hibernate.SessionFactory;
//
///**
// * Hibernate Utility class with a convenient method to get Session Factory object.
// *
// * @author babusseuil
// */
//public class HibernateUtil {
//
//    private static  SessionFactory sessionFactory = null;
//    
////    static {
////        try {
////            // Create the SessionFactory from standard (hibernate.cfg.xml) 
////            // config file.
////            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
////        } catch (Throwable ex) {
////            // Log the exception. 
////            System.err.println("Initial SessionFactory creation failed." + ex);
////            throw new ExceptionInInitializerError(ex);
////        }
////    }
//    
//    public static SessionFactory getSessionFactory() {
//        
//        if(sessionFactory == null)
//        {
//            try {
//            // Create the SessionFactory from standard (hibernate.cfg.xml) 
//            // config file.
//            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
//            } catch (Throwable ex) {
//                // Log the exception. 
//                System.err.println("Initial SessionFactory creation failed." + ex);
//                throw new ExceptionInInitializerError(ex);
//            }
//        }
//        
//        return sessionFactory;
//    }
//}
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
// load from different directory
            SessionFactory sessionFactory = new Configuration().configure("/hibernate.cfg.xml").buildSessionFactory();

            return sessionFactory;

        } catch (Throwable ex) {
// Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
// Close caches and connection pools
        getSessionFactory().close();
    }
}