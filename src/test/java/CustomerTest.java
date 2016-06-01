import bo.edu.ucbcba.prestamix.group4.exceptions.ValidationException;
import bo.edu.ucbcba.prestamix.group4.model.Customer;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
    private Customer customer;
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        customer= new Customer();
    }

    @Test
    public void testSetCi() {
        customer.setCi(9411020);
        assertEquals(9411020, customer.getCi());
    }

    @Test
    public void testSetFirstName()
    {
        customer.setFirtsName("Sega");
        assertEquals("Sega", customer.getFirtsName());

    }

}
