package Ejercicio2_HQL.Persistencia;

import Ejercicio2_HQL.Modelo.*;
import Ejercicio2_HQL.Modelo.EntityBeans;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ConfigHibernate {
    private static SessionFactory sessionFactory;

    public static void load(){

        try {
            Configuration config = new Configuration();
            config.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/computadoras?useTimezone=true&serverTimezone=UTC");
            config.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            config.setProperty("hibernate.connection.username", "root");
            config.setProperty("hibernate.connection.password", "");
            config.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            config.setProperty("hibernate.show_sql", "true");
            config.setProperty("hibernate.hbm2ddl.auto", "update" );
            config.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect");


            // Propiedades C3P0 (asegúrate de tener c3p0 en tu classpath)
            config.setProperty("hibernate.c3p0.min_size","0");
            config.setProperty("hibernate.c3p0.max_size","7");
            config.setProperty("hibernate.c3p0.timeout","100");
            config.setProperty("hibernate.c3p0.max_elements","100");
            config.setProperty("hibernate.c3p0.idle_test_period","100");
            config.setProperty("hibernate.c3p0.autoCommitOnClose","true");

            config.addPackage("Modelo");  // Agregar paquete de entidades

            // Agregar clases anotadas
            config.addAnnotatedClass(Componente.class);
            config.addAnnotatedClass(Computadora.class);

            sessionFactory = config.buildSessionFactory();
        } catch (Exception e) {
            System.out.println("Error: HibernateException: " + e.getMessage());
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public synchronized static Session openSession() {
        return getSessionFactory().openSession();
    }

    public static void closeSession(Session session) {
        session.close();
    }

    public synchronized static SessionFactory getSessionFactory() {
        if(sessionFactory == null)
            load();

        return sessionFactory;
    }

    public synchronized static void closeSessionFactory() {
        try {
            if(sessionFactory != null) {
                sessionFactory.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sessionFactory = null;
        }
    }

    public boolean saveEntity(Session sezion, EntityBeans entity) {
        Transaction tx = sezion.getTransaction();
        if (tx == null || !tx.isActive()) {
            tx = sezion.beginTransaction();
        }

        sezion.saveOrUpdate(entity);
        if(tx != null) tx.commit();
        return true;
    }

    public boolean deleteEntity(Session sezion, EntityBeans entity) {
        Transaction tx = sezion.getTransaction();
        if (tx == null || !tx.isActive()) {
            tx = sezion.beginTransaction();
        }

        sezion.delete(entity);
        if(tx != null) tx.commit();
        return true;
    }

    public boolean updateEntity(Session sezion, EntityBeans entity) {
        Transaction tx = sezion.getTransaction();
        if (tx == null || !tx.isActive()) {
            tx = sezion.beginTransaction();
        }

        sezion.update(entity);
        if(tx != null) tx.commit();
        return true;
    }

    public boolean deleteEntities(Session sezion, List entities) {
        for (Object entity : entities) {
            sezion.delete(entity);
        }
        return true;
    }

    public void destroy() {
        closeSessionFactory();
    }
}


