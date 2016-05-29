package bo.edu.ucbcba.prestamix.group4.controller;

import bo.edu.ucbcba.prestamix.group4.dao.PrestamixEntityManager;
import bo.edu.ucbcba.prestamix.group4.exceptions.ValidationException;
import bo.edu.ucbcba.prestamix.group4.model.Customer;
import bo.edu.ucbcba.prestamix.group4.model.Pawn;
import bo.edu.ucbcba.prestamix.group4.model.Pledge;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PawnController
{
    public void create(String nameCustomer, String codPledge,
                       String amount, String type, String date, String status)
    {
        Pawn pawn = new Pawn();
        Date d = new Date(date);
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String convert = dateFormat.format(d);
        pawn.setNameCustomer(nameCustomer);
        pawn.setCodPledge(codPledge);
        if (amount.matches("[0-9]+"))
            pawn.setAmount(Integer.parseInt(amount));
        else
            throw new ValidationException("Monto no es un número");
        pawn.setType(type);
        pawn.setDate(convert);
        pawn.setStatus(status);


        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(pawn);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<Pawn> show()
    {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        TypedQuery<Pawn> query = entityManager.createQuery("select p from Pawn p", Pawn.class);
        List<Pawn> response = query.getResultList();
        entityManager.close();
        return response;
    }

    public void delete(int id)
    {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Pawn.class,id));
        entityManager.getTransaction().commit();
        entityManager.close();
    }


}
