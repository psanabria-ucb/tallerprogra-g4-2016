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
        if (ci.matches("[0-9]+")) {
            if (ci.length()>9)
                throw new ValidationException("El campo CI debe contener un número menor a 9 digitos, corrija el dato para continuar");
            else {
                int a = Integer.parseInt(ci);
                EntityManager entityManager = PrestamixEntityManager.createEntityManager();
                TypedQuery<Customer> query = entityManager.createQuery("select c from Customer c WHERE c.Ci =  :a", Customer.class);
                query.setParameter("a", a);
                List<Customer> response = query.getResultList();
                entityManager.close();
                if (response.isEmpty())
                    customer.setCi(Integer.parseInt(ci));
                else {
                    throw new ValidationException("El CI ya fue registrado anteriormente, revise si el cliente ya existe");
                }
            }
        }
        else
            throw new ValidationException("El campo CI debe contener un número, complete el formulario para continuar");
        if(firstName.length()>25)
            throw new ValidationException("El campo Nombre debe contener máximo 25 caracteres");
        else {
            if (firstName.isEmpty() || firstName ==null)
                throw new ValidationException("El campo Nombre está vacío, complete el formulario para continuar");
            else
                customer.setFirtsName(firstName);
        }
        if (lastNameF.length()>25 )
            throw new ValidationException("El campo Apellido Paterno debe contener máximo 25 caracteres");
        else {
            if (lastNameF.isEmpty() || lastNameF ==null){
                throw new ValidationException("El campo Apellido Paterno está vacío, complete el formulario para continuar");
            }
            else
                customer.setLastNameF(lastNameF);
        }
        if (lastNameM.length()>25)
            throw new ValidationException("El campo apellido Materno debe contener máximo 25 caracteres");
        else {
            if (lastNameM.isEmpty() || lastNameM == null)
                throw new ValidationException("El campo Apellido Materno está vacío, complete el formulario para continuar");
            else
                customer.setLastNameM(lastNameM);
        }
        if (address.length()>90) {
            throw new ValidationException("El campo Dirección debe contener máximo 90 caracteres");
        }
        else {
            if (address.isEmpty() || address==null)
                throw new ValidationException("El campo Dirección está vacío, complete el formulario para continuar");
            else
            customer.setAddress(address);
        }
        if((numberPhone.isEmpty()) || (numberPhone==null))
            throw new ValidationException("El campo Teléfono está vacío, complete el formulario para continuar");
        else{
            if (numberPhone.matches("[0-9]+")){
                if (numberPhone.length()>9)
                    throw new ValidationException("El campo Teléfono no debe contener mas de 9 dígitos, corrija el dato para continuar ");
                else
                    customer.setNumberPhone(Integer.parseInt(numberPhone));
            }
            else
                throw new ValidationException("El campo Teléfono debe contener un número, corrija el dato para continuar");

        }



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
        if(ci!=0) {
            EntityManager entityManager = PrestamixEntityManager.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Customer.class, ci));
            entityManager.getTransaction().commit();
            entityManager.close();
        }
        else
        {
            throw new ValidationException("Seleccione el cliente que desea eliminar");
        }
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

    public Customer getCustomer(int ci)
    {
        if(ci!=0)
        {
            EntityManager entityManager = PrestamixEntityManager.createEntityManager();
            entityManager.getTransaction().begin();
            Customer response = entityManager.find(Customer.class,ci);
            entityManager.getTransaction().commit();
            entityManager.close();
            return response;
        }
        else {
            throw new ValidationException("Seleccione el cliente que desea editar");
        }
    }

    public void update(int ci, String firstName, String lastNameF, String lastNameM, String address,
                       String numberPhone)
    {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        entityManager.getTransaction().begin();
        Customer customer = entityManager.find(Customer.class, ci);
        if(firstName.length()>25)
            throw new ValidationException("El campo Nombre debe contener máximo 25 caracteres, corrija el dato para continuar");
        else {
            if (firstName.isEmpty() || firstName ==null)
                throw new ValidationException("El campo Nombre está vacío, complete el formulario para continuar");
            else
                customer.setFirtsName(firstName);
        }
        if (lastNameF.length()>25 )
            throw new ValidationException("El Apellido Paterno debe contener máximo 25 caracteres, corrija el dato para continuar");
        else {
            if (lastNameF.isEmpty() || lastNameF ==null){
                throw new ValidationException("El campo Apellido Paterno está vacío, complete el formulario para continuar");
            }
            else
                customer.setLastNameF(lastNameF);
        }
        if (lastNameM.length()>25)
            throw new ValidationException("El Apellido Materno debe ser de máximo 25 caracteres, corrija el dato para continuar");
        else {
            if (lastNameM.isEmpty() || lastNameM == null)
                throw new ValidationException("El campo Apellido Materno está vacío, complete el formulario para continuar");
            else
                customer.setLastNameM(lastNameM);
        }
        if (address.length()>90) {
            throw new ValidationException("El campo Dirección debe contener máximo 90 caracteres, corrija el dato para continuar");
        }
        else {
            if (address.isEmpty() || address==null)
                throw new ValidationException("El campo Dirección está vacío, complete el formulario para continuar");
            else
                customer.setAddress(address);
        }
        if((numberPhone.isEmpty()) || (numberPhone==null))
            throw new ValidationException("El campo Teléfono está vacío, complete el formulario para continuar");
        else{
            if (numberPhone.matches("[0-9]+")){
                if (numberPhone.length()>9)
                    throw new ValidationException("El Teléfono no debe tener mas de 9 dígitos, corrija el dato para continuar ");
                else
                    customer.setNumberPhone(Integer.parseInt(numberPhone));
            }
            else
                throw new ValidationException("El campo Teléfono debe ser un número, corrija el dato para continuar");

        }

        entityManager.getTransaction().commit();
        entityManager.close();

    }
}
