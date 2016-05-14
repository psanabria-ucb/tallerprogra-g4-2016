package bo.edu.ucbcba.prestamix.group4.controller;

import bo.edu.ucbcba.prestamix.group4.dao.PrestamixEntityManager;
import bo.edu.ucbcba.prestamix.group4.model.Pledge;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class PledgeController
{
    public void create(String cod, String name, String type, String description, String location)
    {
        Pledge pledge = new Pledge();
        pledge.setCod(cod);
        pledge.setName(name);
        pledge.setType(type);
        pledge.setDescription(description);
        pledge.setLocation(location);

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
}
