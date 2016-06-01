package bo.edu.ucbcba.prestamix.group4.controller;

import bo.edu.ucbcba.prestamix.group4.dao.PrestamixEntityManager;
import bo.edu.ucbcba.prestamix.group4.exceptions.ValidationException;
import bo.edu.ucbcba.prestamix.group4.model.Customer;
import bo.edu.ucbcba.prestamix.group4.model.Pawn;
import bo.edu.ucbcba.prestamix.group4.model.Pledge;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PawnController
{
    public void create(String nameCustomer, String codPledge,
                       String amount, String type, String date, String status)
    {
        Pawn pawn = new Pawn();

        if (isDateValid(date))
        {
            Date d = new Date(date);
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            String convert = dateFormat.format(d);
            pawn.setDate(convert);
        }
        else {
            throw new ValidationException("La fecha debe estar escrita en el formato Mes/Día/Año, corrija el dato para continuar");
        }

        pawn.setNameCustomer(nameCustomer);
        pawn.setCodPledge(codPledge);
        if (amount.matches("[0-9]+"))
            if(amount.length()>6)
                throw new ValidationException("El monto no puede ser mayor a 6 digitos, corrija el dato para continuar");
            else
                pawn.setAmount(Integer.parseInt(amount));
        else
            throw new ValidationException("Monto no es un número, corrija el dato para continuar");
        pawn.setType(type);

        pawn.setStatus(status);


        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(pawn);
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

    public List<Pawn> show()
    {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        TypedQuery<Pawn> query = entityManager.createQuery("select p from Pawn p", Pawn.class);
        List<Pawn> response = query.getResultList();
        entityManager.close();
        return response;
    }

    public List<Pawn> searchPawnsByCustomer(String q)
    {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        TypedQuery<Pawn> query = entityManager.createQuery("select p from Pawn p WHERE lower(p.nameCustomer) like :nameCustomer", Pawn.class);
        query.setParameter("nameCustomer", "%" + q.toLowerCase() + "%");
        List<Pawn> response = query.getResultList();
        entityManager.close();
        return response;
    }

    public List<Pawn> searchPawnsByPledge(String q)
    {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        TypedQuery<Pawn> query = entityManager.createQuery("select p from Pawn p WHERE lower(p.codPledge) like :codPledge", Pawn.class);
        query.setParameter("codPledge", "%" + q.toLowerCase() + "%");
        List<Pawn> response = query.getResultList();
        entityManager.close();
        return response;
    }



    public List<Pawn> searchPawnsByAmount(String q)
    {
        int a;
        if (q.matches("[0-9]+") && (q.length()<7)) {
            if (q.isEmpty()) {
                a = 0;
            } else {
                a = Integer.parseInt(q);
            }
            EntityManager entityManager = PrestamixEntityManager.createEntityManager();
            TypedQuery<Pawn> query = entityManager.createQuery("select p from Pawn p WHERE p.amount =  :a", Pawn.class);
            query.setParameter("a", a);
            List<Pawn> response = query.getResultList();
            entityManager.close();
            return response;
        }
        else {
            throw new ValidationException("El campo debe ser un número no mayor a 6 dígitos para buscar por ese criterio, corrija el dato para continuar");
        }
    }


    public List<Pawn> searchPawnsByType(String q)
    {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        TypedQuery<Pawn> query = entityManager.createQuery("select p from Pawn p WHERE lower(p.type) like :type", Pawn.class);
        query.setParameter("type", "%" + q.toLowerCase() + "%");
        List<Pawn> response = query.getResultList();
        entityManager.close();
        return response;
    }

    public List<Pawn> searchPawnsByStatus(String q)
    {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        TypedQuery<Pawn> query = entityManager.createQuery("select p from Pawn p WHERE lower(p.status) like :status", Pawn.class);
        query.setParameter("status", "%" + q.toLowerCase() + "%");
        List<Pawn> response = query.getResultList();
        entityManager.close();
        return response;
    }

    public List<Pawn> searchPawnByDate(String q)
    {


            EntityManager entityManager = PrestamixEntityManager.createEntityManager();
            TypedQuery<Pawn> query = entityManager.createQuery("select p from Pawn p WHERE lower(p.date) like :date", Pawn.class);
            query.setParameter("date", "%" + q.toLowerCase() + "%");
            List<Pawn> response = query.getResultList();
            entityManager.close();
            return response;
    }




    public void delete(int id)
    {
        if(id!=0)
        {
            EntityManager entityManager = PrestamixEntityManager.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Pawn.class, id));
            entityManager.getTransaction().commit();
            entityManager.close();
        }
        else
        {
            throw new ValidationException("Seleccione el empeño que desea eliminar");
        }
    }

    public Pawn getPawn(int id)
    {
        if(id!=0)
        {
            EntityManager entityManager = PrestamixEntityManager.createEntityManager();
            entityManager.getTransaction().begin();
            Pawn response = entityManager.find(Pawn.class,id);
            entityManager.getTransaction().commit();
            entityManager.close();
            return response;
        }
        else {
        throw new ValidationException("Seleccione el empeño que desea editar");
        }
    }

    public void update(int id, String nameCustomer, String codPledge,
                       String amount, String type, String date, String status)
    {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        entityManager.getTransaction().begin();
        Pawn pawn = entityManager.find(Pawn.class, id);
        if (isDateValid(date))
        {
            Date d = new Date(date);
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            String convert = dateFormat.format(d);
            pawn.setDate(convert);
        }
        else {
            throw new ValidationException("La fecha debe estar escrita en el formato Mes/Día/Año, corrija el dato para continuar");
        }

        if (amount.matches("[0-9]+"))
            if(amount.length()>6)
                throw new ValidationException("El monto no puede ser mayor a 6 digitos, corrija el dato para continuar");
            else
                pawn.setAmount(Integer.parseInt(amount));
        else
            throw new ValidationException("Monto no es un número, corrija el dato para continuar");
        pawn.setType(type);
        pawn.setStatus(status);

        entityManager.getTransaction().commit();
        entityManager.close();


    }


}
