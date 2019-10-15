package example.com.t1708m.service;

import example.com.t1708m.entity.Employees;
import example.com.t1708m.util.HibernateUtil;

import javax.jws.WebService;
import javax.mail.Session;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

@WebService
public class EmployeeService {
    Transaction transaction = null;

    public Employees store(Employees employees) {
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(employees);
            transaction.commit();
            return employees;
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
            return null;
        }
    }

    public List<Employees> getAll() {
        try (Session session = HibernateUtil.getSession()) {
            List<Employees> employees = session.createQuery("from Employees", Employees.class).list();
            return employees;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Employees getById(long id) {
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            Employees employees = session.get(Employees.class, id);
            return employees;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Employees update(Employees employees) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSession()) {
            transaction = session.beginTransaction();
            session.update(employees);
            transaction.commit();
            return employees;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }
}
