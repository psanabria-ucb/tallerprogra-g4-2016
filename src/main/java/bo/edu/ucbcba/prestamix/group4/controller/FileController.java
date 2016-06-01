package bo.edu.ucbcba.prestamix.group4.controller;

import bo.edu.ucbcba.prestamix.group4.dao.PrestamixEntityManager;
import bo.edu.ucbcba.prestamix.group4.model.File;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class FileController {
    public void create(int id, String nameCustomer, String codPledge,
                       String amount, String type, String date, String status)
    {
        File file = new File();

        file.setId(id);
        file.setNameCustomer(nameCustomer);
        file.setCodPledge(codPledge);
        file.setAmount(Integer.valueOf(amount));
        file.setType(type);
        file.setDate(date);
        file.setStatus(status);

        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(file);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<File> show()
    {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        TypedQuery<File> query = entityManager.createQuery("select f from File f", File.class);
        List<File> response = query.getResultList();
        entityManager.close();
        return response;
    }
}
