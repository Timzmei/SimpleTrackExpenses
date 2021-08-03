package timzmei.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import timzmei.entity.Transact;
import timzmei.service.ServiceTransact;
import timzmei.util.HibernateUtil;

import java.util.List;

public class TransactRepository implements ServiceTransact {
    private static SessionFactory sessionFactory;
    private Transaction transaction;
    private Session session;

    public TransactRepository(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public List getTransact(){
        session = sessionFactory.openSession();
        try{
            String hql = "From Person";

            return session.createQuery(hql).list();
        }
        finally {
            session.close();
        }
    }
    @Override
    public void saveTransact(Transact person){
        session = sessionFactory.openSession();

        try{
            transaction = session.beginTransaction();

            session.save(person);

            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            if(transaction != null){
                transaction.rollback();
            }
        }
        finally {
            session.close();
        }

    }
    @Override
    public void updateTransact(Transact person){
        session = sessionFactory.openSession();

        try{
            transaction = session.beginTransaction();

            session.update(person);

            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            if(transaction != null){
                transaction.rollback();
            }
        }
        finally {
            session.close();
        }
    }

    @Override
    public void deleteTransact(Transact id){
        session = sessionFactory.openSession();

        try{
            transaction = session.beginTransaction();

            session.delete(id);

            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            if(transaction != null){
                transaction.rollback();
            }
        }
        finally {
            session.close();
        }
    }

}
