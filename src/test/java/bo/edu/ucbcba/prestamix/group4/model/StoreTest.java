package bo.edu.ucbcba.prestamix.group4.model;

import bo.edu.ucbcba.prestamix.group4.model.Store;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class StoreTest {
    private Store store;
    @Rule
    public final ExpectedException exception =ExpectedException.none();

    @Before
    public void setUp() { store = new Store();   }

    @Test
    public void testSetId () {
        store.setId(133);
        assertEquals(133,store.getId());
    }

    @Test
    public void testSetName() {
        store.setName("Deposito A");
        assertEquals("Deposito A", store.getName());
    }

    @Test
    public void testSetDescription () {
        store.setDescription("Descripcion de este deposito");
        assertEquals("Descripcion de este deposito",store.getDescription());
    }

    @Test
    public void testSetStatus() {
        store.setStatus("Optimo");
        assertEquals("Optimo",store .getStatus());
    }

}
