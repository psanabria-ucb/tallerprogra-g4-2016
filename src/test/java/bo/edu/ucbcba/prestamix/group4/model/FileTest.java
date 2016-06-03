package bo.edu.ucbcba.prestamix.group4.model;

import bo.edu.ucbcba.prestamix.group4.model.File;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;


public class FileTest {
    private File file;
    @Rule
    public final ExpectedException exception =ExpectedException.none();

    @Before
    public void setUp() {file=new File();}

    @Test
    public void testSetId() {
        file.setId(525);
        assertEquals(525,file.getId());
    }

    @Test
    public void testSetNameCustome() {
        file.setNameCustomer("Marcelo");
        assertEquals("Marcelo",file.getNameCustomer());
    }

    @Test
    public void testSetCodPledge() {
        file.setCodPledge("XX-121");
        assertEquals("XX-121", file.getCodPledge());
    }

    @Test
    public void testSetAmount () {
        file.setAmount(350);
        assertEquals(350,file.getAmount());
    }

    @Test
    public void testSetType() {
        file.setType("Bs");
        assertEquals("Bs",file.getType());
    }

    @Test
    public void testSetDate() {
        file.setDate("11/11/2010");
        assertEquals("11/11/2010",file.getDate());
    }


    @Test
    public void testSetStatus() {
        file.setStatus("Muerto");
        assertEquals("Muerto",file.getStatus());
    }


}
