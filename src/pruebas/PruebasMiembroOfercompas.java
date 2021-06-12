package pruebas;

import modelo.MiembroOfercompas;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PruebasMiembroOfercompas {

/*
    @Test
    public void emailValido(){
        assertTrue(MiembroOfercompas.validarEmail("rendon.luisgerardo@gmail.com"));
    }

 */

    @Test
    public void emailInvalidoSinDominio(){
        assertFalse(MiembroOfercompas.validarEmail("rendon.luisgerardo@.com"));
    }

    @Test
    public  void emailInvalidoDesordenado(){
        assertFalse(MiembroOfercompas.validarEmail(".com.rendon@gmail"));
    }
    @Test
    public  void emailInvalidoConEspacios(){
        assertFalse(MiembroOfercompas.validarEmail("rendon. luisgerardo@gmail.com"));
    }
}
