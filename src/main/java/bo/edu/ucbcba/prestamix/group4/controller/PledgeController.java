package bo.edu.ucbcba.prestamix.group4.controller;

import bo.edu.ucbcba.prestamix.group4.dao.PrestamixEntityManager;
import bo.edu.ucbcba.prestamix.group4.exceptions.ValidationException;
import bo.edu.ucbcba.prestamix.group4.model.Pledge;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class PledgeController
{
    public void create(String cod, String name, String type, String description, String location)
    {

        Pledge pledge = new Pledge();
        if ((cod == null) || (cod.isEmpty())) {
            throw new ValidationException("Error el campo codigo esta vacío");
        }
        else {
            pledge.setCod(cod);
        }
        if ((name == null) || (name.isEmpty())){
            throw new ValidationException("Error el campo nombre esta vacio");
        }
        else {
            pledge.setName(name);
        }
        if ((type == null) || (type.isEmpty())){
            throw new ValidationException("Error el campo tipo esta vacio");
        }
        else
        {
            pledge.setType(type);
        }
        if ((description == null) || (description.isEmpty())) {
            throw new ValidationException("Error el descripcion tipo esta vacio");
        }
        else {
            pledge.setDescription(description);
        }
        if ((location == null) || (location.isEmpty()))
        {
            throw new ValidationException("Error el ubicacion tipo esta vacio");
        }
        else
        {
            pledge.setLocation(location);
        }

        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(pledge);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Pledge> show()
    {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        TypedQuery<Pledge> query = entityManager.createQuery("select p from Pledge p", Pledge.class);
        List<Pledge> response = query.getResultList();
        entityManager.close();
        return response;
    }

    public void delete(String cod)
    {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Pledge.class,cod));
        entityManager.getTransaction().commit();
        entityManager.close();
    }


    public List<Pledge> searchPledgeByType(String q) {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        TypedQuery<Pledge> query = entityManager.createQuery("select p from Pledge p WHERE lower(p.type) like :type", Pledge.class);
        query.setParameter("type", "%" + q.toLowerCase() + "%");
        List<Pledge> response = query.getResultList();
        entityManager.close();
        return response;
    }

    public List<Pledge> searchPledgeByName(String q) {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        TypedQuery<Pledge> query = entityManager.createQuery("select p from Pledge p WHERE lower(p.name) like :name", Pledge.class);
        query.setParameter("name", "%" + q.toLowerCase() + "%");
        List<Pledge> response = query.getResultList();
        entityManager.close();
        return response;
    }

    public List<Pledge> searchPledgeByCode(String q) {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        TypedQuery<Pledge> query = entityManager.createQuery("select p from Pledge p WHERE lower(p.cod) like :cod", Pledge.class);
        query.setParameter("cod", "%" + q.toLowerCase() + "%");
        List<Pledge> response = query.getResultList();
        entityManager.close();
        return response;
    }


}
