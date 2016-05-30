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
}
