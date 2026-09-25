package mx.gob.imss.ctirss.sso.util;

import org.junit.Assert;
import org.junit.Test;

public class ValidationUtilsTest {
	
	@Test
	public void pruebaSimple() {
	    Assert.assertTrue(true);
	}

    //Caso válido estándar
    @Test
    public void testCurpValida() {
    	//GUGS001027HNLTNXA2    
        String curp = "LETV011027MCHMRLA9";
        Assert.assertTrue(ValidationUtils.isValidCurp(curp));
    }
    
    //Minúsculas (debe convertir internamente)
    @Test
    public void testCurpEnMinusculas() {
        String curp = "vaia801022 mvzzzl01".replace(" ", "");
        Assert.assertTrue(ValidationUtils.isValidCurp(curp));
    }
    
    //Longitud menor
    @Test
    public void testCurpLongitudMenor() {
        String curp = "VAIA801022MVZZZL";
        Assert.assertFalse(ValidationUtils.isValidCurp(curp));
    }
    
    //Longitud mayor
    @Test
    public void testCurpLongitudMayor() {
        String curp = "GOGM810103HNLNJG06999";
        Assert.assertFalse(ValidationUtils.isValidCurp(curp));
    }
    
    //Sexo inválido. En la CURP, la posición 11 (carácter 11) representa el sexo:([HM])
    @Test
    public void testCurpSexoInvalido() {
        String curp = "GOGM810103XNLNJG06";
        Assert.assertFalse(ValidationUtils.isValidCurp(curp));
    }
    
    // Caracteres inválidos. Los primeros 4 caracteres deben ser LETRAS mayúsculas solamente
    @Test
    public void testCurpCaracteresInvalidos() {
        String curp = "GO6M810103XNLNJG06";
        Assert.assertFalse(ValidationUtils.isValidCurp(curp));
    }
    
    //Null
    @Test
    public void testCurpNull() {
        Assert.assertFalse(ValidationUtils.isValidCurp(null));
    }

    //Vacía
    @Test
    public void testCurpVacia() {
        Assert.assertFalse(ValidationUtils.isValidCurp(""));
    }

    // Espacios
    @Test
    public void testCurpConEspacios() {
        String curp = " VAIA801022MVZZZL01 ";
        Assert.assertFalse(ValidationUtils.isValidCurp(curp));
    }

}
