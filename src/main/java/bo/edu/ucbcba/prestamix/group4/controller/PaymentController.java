package bo.edu.ucbcba.prestamix.group4.controller;

import bo.edu.ucbcba.prestamix.group4.dao.PrestamixEntityManager;
import bo.edu.ucbcba.prestamix.group4.exceptions.ValidationException;
import bo.edu.ucbcba.prestamix.group4.model.Payment;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PaymentController
{
    public void create(String interest, String amountInterest, String date)
    {
        Payment payment = new Payment();

        if (interest.matches("[0-9]+"))
            if(interest.length()>6)
                throw new ValidationException("El monto no puede ser mayor a 6 digitos, corrija el dato para continuar");
            else
                payment.setInterest(Integer.parseInt(interest));
        else
            throw new ValidationException("Monto no es un número, corrija el dato para continuar");

        if (amountInterest.matches("[0-9]+"))
            if(amountInterest.length()>6)
                throw new ValidationException("El monto no puede ser mayor a 6 digitos, corrija el dato para continuar");
            else
                payment.setAmount(Integer.parseInt(amountInterest));
        else
            throw new ValidationException("Monto no es un número, corrija el dato para continuar");

        if (isDateValid(date))
        {
            Date d = new Date(date);
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            String convert = dateFormat.format(d);
            payment.setDate(convert);
        }
        else {
            throw new ValidationException("La fecha debe estar escrita en el formato Mes/Día/Año, corrija el dato para continuar");
        }

        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(payment);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public boolean isDateValid(String date)
    {
        String DATE_FORMAT = "MM/dd/yyyy";
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public List<Payment> getAll()
    {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        TypedQuery<Payment> query = entityManager.createQuery("select p from Payment p", Payment.class);
        List<Payment> response = query.getResultList();
        entityManager.close();
        return response;
    }

    public void delete(int id)
    {
        if(id!=0)
        {
            EntityManager entityManager = PrestamixEntityManager.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Payment.class, id));
            entityManager.getTransaction().commit();
            entityManager.close();
        }
        else
        {
            throw new ValidationException("Error");
        }
    }
}
