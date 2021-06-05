package pruebas;

import modelo.MiembroOfercompas;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PruebasMiembroOfercompas {


    @Test
    public void email_valido(){
        assertTrue(MiembroOfercompas.validarEmail("rendon.luisgerardo@gmail.com"));
    }

    @Test
    public void email_invalido_sindominio(){
        assertFalse(MiembroOfercompas.validarEmail("rendon.luisgerardo@.com"));
    }

    @Test
    public  void email_invalido_desordenado(){
        assertFalse(MiembroOfercompas.validarEmail(".com.rendon@gmail"));
    }
    @Test
    public  void email_invalido_con_espacios(){
        assertFalse(MiembroOfercompas.validarEmail("rendon. luisgerardo@gmail.com"));
    }
}
