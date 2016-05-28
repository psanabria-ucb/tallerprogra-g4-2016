package bo.edu.ucbcba.prestamix.group4.controller;

import bo.edu.ucbcba.prestamix.group4.dao.PrestamixEntityManager;
import bo.edu.ucbcba.prestamix.group4.exceptions.ValidationException;
import bo.edu.ucbcba.prestamix.group4.model.Customer;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CustomerController
{
    public void create(String ci, String firstName, String lastNameF, String lastNameM,
                       String address, String numberPhone)
    {
        Customer customer = new Customer();
        if (ci.matches("[0-9]+"))
            customer.setCi(Integer.parseInt(ci));
        else
            throw new ValidationException("CI no es un número o Formulario vacío");
        customer.setFirtsName(firstName);
        customer.setLastNameF(lastNameF);
        customer.setLastNameM(lastNameM);
        customer.setAddress(address);
        if(numberPhone.matches("[0-9]+"))
            customer.setNumberPhone(Integer.parseInt(numberPhone));
        else
            throw new ValidationException("Teléfono no es un número");

        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(customer);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Customer> show()
    {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        TypedQuery<Customer> query = entityManager.createQuery("select c from Customer c", Customer.class);
        List<Customer> response = query.getResultList();
        entityManager.close();
        return response;
    }

    public void delete(int ci)
    {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Customer.class,ci));
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Customer> searchCustomerByFirstName(String q) {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        TypedQuery<Customer> query = entityManager.createQuery("select c from Customer c WHERE lower(c.firtsName) like :firtsName", Customer.class);
        query.setParameter("firtsName", "%" + q.toLowerCase() + "%");
        List<Customer> response = query.getResultList();
        entityManager.close();
        return response;
    }

    public List<Customer> searchCustomerByFathersLastName(String q) {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        TypedQuery<Customer> query = entityManager.createQuery("select c from Customer c WHERE lower(c.lastNameF) like :lastNameF", Customer.class);
        query.setParameter("lastNameF", "%" + q.toLowerCase() + "%");
        List<Customer> response = query.getResultList();
        entityManager.close();
        return response;
    }


    public List<Customer> searchCustomerByMothersLastName(String q) {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        TypedQuery<Customer> query = entityManager.createQuery("select c from Customer c WHERE lower(c.lastNameM) like :lastNameM", Customer.class);
        query.setParameter("lastNameM", "%" + q.toLowerCase() + "%");
        List<Customer> response = query.getResultList();
        entityManager.close();
        return response;
    }

    public List<Customer> searchCustomerByCI(String q)
    {
        int a;
        if (q.matches("[0-9]+")) {
            if (q.isEmpty()) {
                a = 0;
            } else {
                a = Integer.parseInt(q);
            }
            EntityManager entityManager = PrestamixEntityManager.createEntityManager();
            TypedQuery<Customer> query = entityManager.createQuery("select c from Customer c WHERE c.Ci =  :a", Customer.class);
            query.setParameter("a", a);
            List<Customer> response = query.getResultList();
            entityManager.close();
            return response;
        }
        else {
            throw new ValidationException("El campo debe ser un número para buscar por ese criterio");
        }


    }

    public List<Customer> getAllCustomers() {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        TypedQuery<Customer> query = entityManager.createQuery("select c from Customer c", Customer.class);
        List<Customer> response = query.getResultList();
        entityManager.close();
        return response;
    }

    public Customer find(int id)
    {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        entityManager.getTransaction().begin();
        Customer customer = entityManager.find(Customer.class,id);
        entityManager.getTransaction().commit();
        entityManager.close();
        return customer;
    }

    public void update(int id, int ci, String fn, String lnp, String lnm, String a, int n)
    {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        entityManager.getTransaction().begin();
        Customer customer = entityManager.find(Customer.class,id);
        customer.setCi(ci);
        customer.setFirtsName(fn);
        customer.setLastNameF(lnp);
        customer.setLastNameM(lnm);
        customer.setAddress(a);
        customer.setNumberPhone(n);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
