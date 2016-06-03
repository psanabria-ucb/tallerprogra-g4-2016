package bo.edu.ucbcba.prestamix.group4.model;

import bo.edu.ucbcba.prestamix.group4.model.Pledge;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class PledgeTest {
    private Pledge pledge;
    @Rule
    public final ExpectedException exception =ExpectedException.none();

    @Before
    public void setUp() {pledge=new Pledge();}

    @Test
    public void testSetCod () {
        pledge.setCod("XX-4567");
        assertEquals("XX-4567",pledge.getCod());
    }

    @Test
    public void testSetName () {
        pledge.setName("Samsung Galaxy Tab");
        assertEquals("Samsung Galaxy Tab",pledge.getName());
    }


    @Test
    public void testSetType () {
        pledge.setType("Articulo Digital");
        assertEquals("Articulo Digital", pledge.getType());
    }


    @Test
    public void testSetDescription () {
        pledge.setDescription("Description of the pledge");
        assertEquals("Description of the pledge", pledge.getDescription());
    }


    @Test
    public void testSetLocation () {
        pledge.setLocation("Deposito A");
        assertEquals("Deposito A", pledge.getLocation());
    }




}
