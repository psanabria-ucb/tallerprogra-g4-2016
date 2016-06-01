package bo.edu.ucbcba.prestamix.group4.controller;

import bo.edu.ucbcba.prestamix.group4.dao.PrestamixEntityManager;
import bo.edu.ucbcba.prestamix.group4.exceptions.ValidationException;
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

    public List<File> searchFilesById(String q)
    {
        int a;
        if (q.matches("[0-9]+") && (q.length()<7)) {
            if (q.isEmpty()) {
                a = 0;
            } else {
                a = Integer.parseInt(q);
            }
            EntityManager entityManager = PrestamixEntityManager.createEntityManager();
            TypedQuery<File> query = entityManager.createQuery("select f from File f WHERE f.id =  :a", File.class);
            query.setParameter("a", a);
            List<File> response = query.getResultList();
            entityManager.close();
            return response;
        }
        else {
            throw new ValidationException("El campo debe ser un número para buscar por ese criterio, corrija el dato para continuar");
        }
    }


    public List<File> searchFilesByClientName(String q)
    {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        TypedQuery<File> query = entityManager.createQuery("select f from File f WHERE lower(f.nameCustomer) like :nameCustomer", File.class);
        query.setParameter("nameCustomer", "%" + q.toLowerCase() + "%");
        List<File> response = query.getResultList();
        entityManager.close();
        return response;
    }

    public List<File> searchFilesByPledge(String q)
    {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        TypedQuery<File> query = entityManager.createQuery("select f from File f WHERE lower(f.codPledge) like :codPledge", File.class);
        query.setParameter("codPledge", "%" + q.toLowerCase() + "%");
        List<File> response = query.getResultList();
        entityManager.close();
        return response;
    }

    public List<File> searchFilesByAmount(String q)
    {
        int a;
        if (q.matches("[0-9]+") && (q.length()<7)) {
            if (q.isEmpty()) {
                a = 0;
            } else {
                a = Integer.parseInt(q);
            }
            EntityManager entityManager = PrestamixEntityManager.createEntityManager();
            TypedQuery<File> query = entityManager.createQuery("select f from File f WHERE f.amount =  :a", File.class);
            query.setParameter("a", a);
            List<File> response = query.getResultList();
            entityManager.close();
            return response;
        }
        else {
            throw new ValidationException("El campo debe ser un número no mayor a 6 dígitos para buscar por ese criterio, corrija el dato para continuar");
        }
    }


    public List<File> searchFilesByType(String q)
    {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        TypedQuery<File> query = entityManager.createQuery("select f from File f WHERE lower(f.type) like :type", File.class);
        query.setParameter("type", "%" + q.toLowerCase() + "%");
        List<File> response = query.getResultList();
        entityManager.close();
        return response;
    }

    public List<File> searchFilesByStatus(String q)
    {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        TypedQuery<File> query = entityManager.createQuery("select f from File f WHERE lower(f.status) like :status", File.class);
        query.setParameter("status", "%" + q.toLowerCase() + "%");
        List<File> response = query.getResultList();
        entityManager.close();
        return response;
    }

    public List<File> searchFilesByDate(String q)
    {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        TypedQuery<File> query = entityManager.createQuery("select f from File f WHERE lower(f.date) like :date", File.class);
        query.setParameter("date", "%" + q.toLowerCase() + "%");
        List<File> response = query.getResultList();
        entityManager.close();
        return response;
    }




}
