package mx.gob.imss.ctirss.delta.gestion.individuo.service.utility;

import javax.ejb.Stateless;

@Stateless(name = "personaMoralServiceValidate", mappedName = "personaMoralServiceValidate")
public class PersonaMoralServiceValidate implements PersonaMoralServiceValidateLocal {

    private static final String ER_RFC = "^([a-zA-Z\u0026\u00D1\u00F1]{3})\\d{6}([\\w]{3})$";
    private static final int LONGITUD_RFC = 12;
    
    private static final String ERROR_LONGITUD_RFC = "El RFC tiene que ser de " + LONGITUD_RFC + " caracteres";
    private static final String ERROR_FORMATO_RFC= "El RFC no cumple con el formato requerido";
    
    
    @Override
    public String validarRFC(String rfc){

    	if(rfc.length() != LONGITUD_RFC){
    		return ERROR_LONGITUD_RFC;
    	}else if(!rfc.matches(ER_RFC)){
    		return ERROR_FORMATO_RFC;
    	}else{
    		return "";
    	}
    	
    }
    
}
