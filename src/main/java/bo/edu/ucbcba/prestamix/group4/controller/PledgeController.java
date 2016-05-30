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
            throw new ValidationException("Error el campo código está vacío");
        }
        else
        {
            if(cod.length()>20)
            {
                throw new ValidationException("Error el código supera los 20 caracteres");
            }
            else
            {
                List<Pledge> response = searchPledgeByCode(cod);
                if(response.isEmpty())
                {
                    pledge.setCod(cod);
                }
                else
                {
                    throw new ValidationException("Error el código de la prenda ya existe");
                }
            }
        }

        if ((name == null) || (name.isEmpty())){
            throw new ValidationException("Error el campo nombre está vacío");
        }
        else
        {
            if(name.length()>25)
            {
                throw new ValidationException("Error el nombre de la prenda supera los 25 caracteres");
            }
            else{
                pledge.setName(name);
            }
        }

        if ((type == null) || (type.isEmpty())){
            throw new ValidationException("Error el campo tipo está vacío");
        }
        else
        {
            pledge.setType(type);
        }
        if ((description == null) || (description.isEmpty())) {
            throw new ValidationException("Error el descripción tipo está vacío");
        }
        else
        {
            if(description.length()>90)
            {
                throw  new ValidationException("La descripción debe ser de máximo 90 caracteres");
            }
            else
            {
                pledge.setDescription(description);
            }
        }

        if ((location == null) || (location.isEmpty()) || (location.equals("null")))
        {
            throw new ValidationException("Error el ubicación está vacío o no tiene depositos registrados");
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

    public List<Pledge> searchPledgeByLocation(String q)
    {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        TypedQuery<Pledge> query = entityManager.createQuery("select p from Pledge p WHERE lower(p.location) like :location", Pledge.class);
        query.setParameter("location", "%" + q.toLowerCase() + "%");
        List<Pledge> response = query.getResultList();
        entityManager.close();
        return  response;
    }

    public List<Pledge> getAllPledges()
    {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        TypedQuery<Pledge> query = entityManager.createQuery("select p from Pledge p", Pledge.class);
        List<Pledge> response = query.getResultList();
        entityManager.close();
        return response;
    }


}
