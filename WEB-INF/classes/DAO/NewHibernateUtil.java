/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.io.File;
import java.io.FileInputStream;

import java.util.Properties; 
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class NewHibernateUtil {

    //protected static final Logger logger = Logger.getLogger(NHibernateUtil.class);
    public static String appHome = "No";
    private static SessionFactory sessionFactory;
    private static final ThreadLocal threadSession = new ThreadLocal();
    private static final ThreadLocal threadTransaction = new ThreadLocal();

    /**
     * Initialize Hibernate Configuration
     */
    public static void initMonitor() {
        //logger.info("Hibernate configure");
        try {
            //logger.info("appHome" + appHome);
            String path_properties = appHome + File.separatorChar + "hibernate.properties";
            String path_mapping = appHome + File.separatorChar + "mapping_classes.mysql.hbm.xml";
            //String ecache = appHome+File.separatorChar+"ehcache.xml";


            Properties propHibernate = new Properties();
            propHibernate.load(new FileInputStream(path_properties));

            Configuration configuration = new Configuration();
            configuration.addFile(path_mapping);
            configuration.setProperties(propHibernate);

            sessionFactory = configuration.buildSessionFactory();

        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * @return a Session Factory Object
     */
    public static SessionFactory getSessionFactory() {
        try {

            if (sessionFactory == null) {
                initMonitor();
            } else {
            }
        } catch (Exception e) {
        }

        return sessionFactory;
    }

    /**
     * @return Session . Start a Session
     */
    public static Session getSession() {

        Session s = (Session) threadSession.get();
        if (s == null) {

            s = getSessionFactory().openSession();
            threadSession.set(s);
        }
        return s;
    }

    /**
     * Close Session
     */
    public static void closeSession() {

        Session s = (Session) threadSession.get();
        threadSession.set(null);
        if (s != null && s.isOpen()) {
            s.flush();
            s.close();
        }
    }

    /**
     * Start a new database transaction.
     */
    public static void beginTransaction() {
        Transaction tx = null;

        if (tx == null) {
            tx = (Transaction) getSession().beginTransaction();
            threadTransaction.set(tx);
        }
    }

    /**
     * Commit the database transaction.
     */
    public static void commitTransaction() {
        Transaction tx = (Transaction) threadTransaction.get();
        try {
            if (tx != null) {
                    tx.commit();
            }

            threadTransaction.set(null);

        } catch (HibernateException ex) {
            rollbackTransaction();

            throw ex;
        }
    }

    /**
     * Rollback the database transaction.
     */
    public static void rollbackTransaction() {

        Transaction tx = (Transaction) threadTransaction.get();
        try {
            threadTransaction.set(null);
            if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
                try {
                    tx.rollback();
                } catch (IllegalStateException ex) {
                    Logger.getLogger(NewHibernateUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        } finally {
            closeSession();
        }
    }
}
