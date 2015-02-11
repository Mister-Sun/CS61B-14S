package DAO;


import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class DaoImpl<T> implements Dao<T> {

    private Class<T> classe;
    private Session session;

    @SuppressWarnings("unchecked")
    public DaoImpl() {
        this.classe = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.session = ConnectionUtil.getSession();
    }

    @Override
    public long save(T bean) {
        long id = 0;
        try {
//            if(session.getTransaction().isActive()){
//                session.save(bean);
//                session.getTransaction().commit();
//            }else{
                session.beginTransaction();
                id =  Long.parseLong(session.save(bean).toString());
                session.getTransaction().commit();
//            }
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
        }
        return id;
    }

    @Override
    public List<T> getBeansByQuantity(int maxResult) {
        List<T> list = session.createCriteria(classe).setMaxResults(maxResult).list();
        return list;
    }

    @Override
    public void update(T bean) {
        try {
            session.beginTransaction();
            session.update(bean);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
        }
    }
    
    public void updateList(List<T> beans) {
        try {
            for(T bean:beans){
                session.beginTransaction();
                session.update(bean);
                session.getTransaction().commit();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
        }
    }

    @Override
    public void merge(T bean) {
        try {
            session.beginTransaction();
            session.merge(bean);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
        }
    }
    
    public void mergeList(List<T> beans) {
        try {
            for(T bean:beans){
                session.beginTransaction();
                session.merge(bean);
                session.getTransaction().commit();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
        }
    }
    
    public void close() {
        try {
            session.getSessionFactory().close();
            System.out.println("Sessão: "+ session.isOpen());
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
        }
    }

    @Override
    public void destroy(T bean) {
        try {
            session.beginTransaction();
            session.delete(bean);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
        }
    }

    @Override
    public T getBean(Serializable id) {
        try {
            session.beginTransaction();
            T bean = (T) session.get(classe, id);
            return bean;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
        }
        return null;
    }

    @Override
    public List<T> getBeans() {
        List<T> list = session.createCriteria(classe).list();
        session.clear();
        return list;
    }

    public Class<T> getClasse() {
        return classe;
    }

    public void setClasse(Class<T> classe) {
        this.classe = classe;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
