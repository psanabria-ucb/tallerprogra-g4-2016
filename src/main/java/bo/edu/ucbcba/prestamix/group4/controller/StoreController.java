package bo.edu.ucbcba.prestamix.group4.controller;

import bo.edu.ucbcba.prestamix.group4.dao.PrestamixEntityManager;
import bo.edu.ucbcba.prestamix.group4.exceptions.ValidationException;
import bo.edu.ucbcba.prestamix.group4.model.Store;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class StoreController
{
    public void create(String name, String description, String status)
    {
        Store store = new Store();
        if ((name == null) || (name.isEmpty())) {
            throw new ValidationException("Error el campo nombre está vacío");
        }
        else {
            if (name.length()>20)
                throw new ValidationException("Error el Nombre no debe tener mas de 20 caracteres");
            store.setName(name);

        }
        if ((description == null) || (description.isEmpty())) {
                throw new ValidationException("Error el campo descripción está vacío");
        }
        else {
            if(description.length()>90){
                throw  new ValidationException("La descripción debe ser de máximo 90 caracteres");
            }
            else{
                store.setDescription(description);
            }

        }
        if ((status == null) || (status.isEmpty())) {
            throw new ValidationException("Error el campo estado está vacío");
        }
        else {
            store.setStatus(status);
        }

        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(store);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Store> show()
    {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        TypedQuery<Store> query = entityManager.createQuery("select s from Store s", Store.class);
        List<Store> response = query.getResultList();
        entityManager.close();
        return response;
    }

    public List<Store> searchStoresByName(String q){
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        TypedQuery<Store> query = entityManager.createQuery("select s from Store s WHERE lower(s.name) like :name", Store.class);
        query.setParameter("name", "%" + q.toLowerCase() + "%");
        List<Store> response = query.getResultList();
        entityManager.close();
        return response;
    }

    public List<Store> searchStoresByDescription(String q){
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        TypedQuery<Store> query = entityManager.createQuery("select s from Store s WHERE lower(s.description) like :description", Store.class);
        query.setParameter("description", "%" + q.toLowerCase() + "%");
        List<Store> response = query.getResultList();
        entityManager.close();
        return response;
    }

    public List<Store> searchStoresByState(String q){
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        TypedQuery<Store> query = entityManager.createQuery("select s from Store s WHERE lower(s.status) like :status", Store.class);
        query.setParameter("status", "%" + q.toLowerCase() + "%");
        List<Store> response = query.getResultList();
        entityManager.close();
        return response;
    }


    public List<Store> searchStoresById(String q)
    {
        int a;
        if (q.matches("[0-9]+") && (q.length()<7)) {
            if (q.isEmpty()) {
                a = 0;
            } else {
                a = Integer.parseInt(q);
            }
            EntityManager entityManager = PrestamixEntityManager.createEntityManager();
            TypedQuery<Store> query = entityManager.createQuery("select s from Store s WHERE s.id =  :a", Store.class);
            query.setParameter("a", a);
            List<Store> response = query.getResultList();
            entityManager.close();
            return response;
        }
        else {
            throw new ValidationException("El campo debe ser un número para buscar por ese criterio, corrija el dato para continuar");
        }
    }




    public void delete(int id)
    {
        if(id!=0)
        {
            EntityManager entityManager = PrestamixEntityManager.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Store.class, id));
            entityManager.getTransaction().commit();
            entityManager.close();
        }
        else
        {
            throw new ValidationException("Error");
        }
    }

    public Store getStore(int id)
    {
        if(id!=0)
        {
            EntityManager entityManager = PrestamixEntityManager.createEntityManager();
            entityManager.getTransaction().begin();
            Store response = entityManager.find(Store.class,id);
            entityManager.getTransaction().commit();
            entityManager.close();
            return response;
        }
        else {
            throw new ValidationException("Seleccione el depósito que desea editar");
        }
    }

    public void update(int id, String name, String description, String status)
    {

        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        entityManager.getTransaction().begin();
        Store store = entityManager.find(Store.class, id);

        if ((name == null) || (name.isEmpty())) {
            throw new ValidationException("Error el campo nombre está vacío");
        }
        else {
            if (name.length()>20)
                throw new ValidationException("Error el Nombre no debe tener mas de 20 caracteres");
            store.setName(name);

        }
        if ((description == null) || (description.isEmpty())) {
            throw new ValidationException("Error el campo descripción está vacío");
        }
        else {
            if(description.length()>90){
                throw  new ValidationException("La descripción debe ser de máximo 90 caracteres");
            }
            else{
                store.setDescription(description);
            }

        }
        if ((status == null) || (status.isEmpty())) {
            throw new ValidationException("Error el campo estado está vacío");
        }
        else {
            store.setStatus(status);
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
