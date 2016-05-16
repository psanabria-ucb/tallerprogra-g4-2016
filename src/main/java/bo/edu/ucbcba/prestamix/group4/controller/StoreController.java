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
            throw new ValidationException("Error el campo nombre esta vacío");
        }
        else {
            store.setName(name);

        }
        if ((description == null) || (description.isEmpty())) {
                throw new ValidationException("Error el campo descripcion esta vacío");
        }
        else {
            store.setDescription(description);
        }
        if ((status == null) || (status.isEmpty())) {
            throw new ValidationException("Error el campo estado esta vacío");
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

    public void delete(int id)
    {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Store.class,id));
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
