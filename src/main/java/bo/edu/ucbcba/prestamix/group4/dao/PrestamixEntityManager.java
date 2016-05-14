package bo.edu.ucbcba.prestamix.group4.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PrestamixEntityManager
{
    private static PrestamixEntityManager entityManager;
   private EntityManagerFactory entityManagerFactory;

    private PrestamixEntityManager() {
        entityManagerFactory = Persistence.createEntityManagerFactory("Prestamix");
    }

    private static PrestamixEntityManager getInstance() {
        if (entityManager == null)
            entityManager = new PrestamixEntityManager();
        return entityManager;
    }

    public static EntityManager createEntityManager() {
        return getInstance().entityManagerFactory.createEntityManager();
    }
}
