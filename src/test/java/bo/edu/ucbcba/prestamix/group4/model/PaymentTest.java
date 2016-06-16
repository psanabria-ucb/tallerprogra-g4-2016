package bo.edu.ucbcba.prestamix.group4.model;

import org.junit.*;

import org.junit.rules.ExpectedException;
import static org.junit.Assert.assertEquals;

public class PaymentTest
{
    private Payment payment;
    @Rule
    public final ExpectedException exception =ExpectedException.none();

    @Before
    public void setUp() {payment=new Payment();}

    @Test
    public void testSetNumberPay() {
        payment.setNumberPay(10);
        assertEquals(10,payment.getNumberPay());
    }

    @Test
    public void testSetInterest()
    {
        payment.setInterest(10);
        assertEquals(10,payment.getInterest());
    }

    @Test
    public void testSetAmount()
    {
        payment.setAmount(5000);
        assertEquals(5000,payment.getAmount());
    }

    @Test
    public void testSetDate()
    {
        payment.setDate("10/10/2016");
        assertEquals("10/10/2016",payment.getDate());
    }


}
