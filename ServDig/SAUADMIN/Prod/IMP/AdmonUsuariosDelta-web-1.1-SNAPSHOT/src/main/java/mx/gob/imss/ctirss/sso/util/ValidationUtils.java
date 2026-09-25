package mx.gob.imss.ctirss.sso.util;

import java.util.regex.Pattern;

public final class ValidationUtils {
	
	// Evita instanciación
    private ValidationUtils() {
        throw new IllegalStateException("Utility class");
    }
    
    //4 letras + 6 números + 1 sexo + 5 letras + 2 caracteres= 18 caracteres
    //Iniciales + Fecha + Sexo + Estado + Consonantes + Homoclave
    private static final Pattern CURP_PATTERN =Pattern.compile("^([A-Z]{4})([0-9]{6})([HM])([A-Z]{5})([0-9A-Z]{2})$");
    
    /**
     * Valida la estructura de una CURP
     *
     * @param curp CURP a validar
     * @return true si es válida, false si no
     */
    public static boolean isValidCurp(String curp) {
        if (curp == null || curp.trim().isEmpty()) {
            return false;
        }
        return CURP_PATTERN.matcher(curp.toUpperCase()).matches();
    }

}
