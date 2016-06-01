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
            throw new ValidationException("El campo Código está vacío, complete el formulario para continuar");
        }
        else
        {
            if(cod.length()>20)
            {
                throw new ValidationException("El campo código supera los 20 caracteres, corrija el dato para continuar");
            }
            else
            {
                List<Pledge> response = searchPledgeByExactCode(cod);
                if(response.isEmpty())
                {
                    pledge.setCod(cod);
                }
                else
                {
                    throw new ValidationException("El código de la prenda ya existe, verifique si la prenda ya fue creada");
                }
            }
        }

        if ((name == null) || (name.isEmpty())){
            throw new ValidationException("El campo Nombre de la Prenda está vacío, complete el formulario para continuar");
        }
        else
        {
            if(name.length()>25)
            {
                throw new ValidationException("El campo Nombre de la Prenda supera los 25 caracteres, corrija el dato para continuar");
            }
            else{
                pledge.setName(name);
            }
        }

        if ((type == null) || (type.isEmpty())){
            throw new ValidationException("El campo Tipo está vacío, complete el campo para continuar");
        }
        else
        {
            pledge.setType(type);
        }
        if ((description == null) || (description.isEmpty())) {
            throw new ValidationException("El campo Descripción está vacío, complete el formulario para continuar");
        }
        else
        {
            if(description.length()>90)
            {
                throw  new ValidationException("El campo Descripción debe contener máximo 90 caracteres, corrija el dato para continuar");
            }
            else
            {
                pledge.setDescription(description);
            }
        }

        if ((location == null) || (location.isEmpty()) || (location.equals("null")))
        {
            throw new ValidationException("El campo Ubicación está vacío, escoja una ubicación.(Debe tener al menos un deposito registrado, de no ser asi registre uno para continuar)");
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
        if(cod.equals(" "))
        {
           throw new ValidationException("Seleccione la prenda para continuar");
        }
        else
        {
            EntityManager entityManager = PrestamixEntityManager.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Pledge.class,cod));
            entityManager.getTransaction().commit();
            entityManager.close();
        }

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


    public List<Pledge> searchPledgeByExactCode(String q) {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        TypedQuery<Pledge> query = entityManager.createQuery("select p from Pledge p WHERE lower(p.cod) like :cod", Pledge.class);
        query.setParameter("cod", q.toLowerCase());
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

    public Pledge getPledge(String cod)
    {
        if(cod.equals(" "))
        {
            throw new ValidationException("Seleccione la prenda que desea editar");
        }
        else {

            EntityManager entityManager = PrestamixEntityManager.createEntityManager();
            entityManager.getTransaction().begin();
            Pledge response = entityManager.find(Pledge.class,cod);
            entityManager.getTransaction().commit();
            entityManager.close();
            return response;

        }
    }

    public void update(String cod, String name, String type, String description, String location)
    {
        EntityManager entityManager = PrestamixEntityManager.createEntityManager();
        entityManager.getTransaction().begin();
        Pledge pledge = entityManager.find(Pledge.class, cod);
        if ((name == null) || (name.isEmpty())){
            throw new ValidationException("El campo Nombre de la Prenda está vacío, complete el formulario para continuar");
        }
        else
        {
            if(name.length()>25)
            {
                throw new ValidationException("El campo Nombre de la Prenda debe contener máximo 25 caracteres, corrija el dato para continuar");
            }
            else{
                pledge.setName(name);
            }
        }

        if ((type == null) || (type.isEmpty())){
            throw new ValidationException("El campo Tipo está vacío, complete el formulario para continuar");
        }
        else
        {
            pledge.setType(type);
        }
        if ((description == null) || (description.isEmpty())) {
            throw new ValidationException("El campo Descripción tipo está vacío, complete el formulario para continuar");
        }
        else
        {
            if(description.length()>90)
            {
                throw  new ValidationException("El campo Descripción debe contener máximo 90 caracteres, corrija el dato para continuar");
            }
            else
            {
                pledge.setDescription(description);
            }
        }

        if ((location == null) || (location.isEmpty()) || (location.equals("null")))
        {
            throw new ValidationException("El campo Ubicación está vacío o no tiene depositos registrados, corrija el dato para continuar");
        }
        else
        {
            pledge.setLocation(location);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }


}
