package bo.edu.ucbcba.prestamix.group4.controller;

import bo.edu.ucbcba.prestamix.group4.dao.PrestamixEntityManager;
import bo.edu.ucbcba.prestamix.group4.exceptions.ValidationException;
import bo.edu.ucbcba.prestamix.group4.model.Pawn;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

public class PawnController
{
    public void create(int ciCustomer, String codPledge, String amount, String type,
                       String date, String status)
    {
        Pawn pawn = new Pawn();
        pawn.setCiCustomer(ciCustomer);
        pawn.setCodPledge(codPledge);
        if (amount.matches("[0-9]+"))
            pawn.setAmount(Integer.parseInt(amount));
        else
            throw new ValidationException("Monto no es un numero");
        pawn.setType(type);
        pawn.setDate(new Date(date));
        pawn.setStatus(status);
    }

    public List<Pawn> show()
    {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        TypedQuery<Pawn> query = entityManager.createQuery("select p from Pawn p", Pawn.class);
        List<Pawn> response = query.getResultList();
        entityManager.close();
        return response;
    }
}
