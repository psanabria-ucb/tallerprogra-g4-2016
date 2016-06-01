import bo.edu.ucbcba.prestamix.group4.model.Pawn;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class PawnTest {
    private Pawn pawn;
    @Rule
    public final ExpectedException exception =ExpectedException.none();

    @Before
    public void setUp() {pawn=new Pawn();}

    @Test
    public void testSetId() {
        pawn.setId(525);
        assertEquals(525,pawn.getId());
    }

    @Test
    public void testSetNameCustome() {
        pawn.setNameCustomer("Marcelo");
        assertEquals("Marcelo",pawn.getNameCustomer());
    }

    @Test
    public void testSetCodPledge() {
        pawn.setCodPledge("XX-121");
        assertEquals("XX-121", pawn.getCodPledge());
    }

    @Test
    public void testSetAmount () {
        pawn.setAmount(350);
        assertEquals(350,pawn.getAmount());
    }

    @Test
    public void testSetType() {
        pawn.setType("Bs");
        assertEquals("Bs",pawn.getType());
    }

    @Test
    public void testSetDate() {
        pawn.setDate("11/11/2010");
        assertEquals("11/11/2010",pawn.getDate());
    }


    @Test
    public void testSetStatus() {
        pawn.setStatus("Muerto");
        assertEquals("Muerto",pawn.getStatus());
    }













}
