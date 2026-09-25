package mx.gob.imss.ctirss.delta.gestion.individuo.service.utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

import org.apache.commons.lang.StringUtils;

@Stateless(name = "personaFisicaServiceValidate", mappedName = "personaFisicaServiceValidate")
public class PersonaFisicaServiceValidate implements PersonaFisicaServiceValidateLocal, PersonaFisicaServiceValidateRemote {

	//private static final String ER_CURP = "^([a-zA-Z]{4})\\d{6}([a-zA-Z]{6}[a-zA-Z0-9]{2})$";
	private static final String ER_CURP = "^([a-zA-Z]{4})\\d{6}([hmxHMX]{1}[a-zA-Z]{2}" +
			"[b-df-hj-np-tv-zB-DF-HJ-NP-TV-Z]{3}[a-zA-Z0-9]{1}[0-9]{1})$";
	
	//var reg = /[A-Z]{4}\d{6}[HM][A-Z]{2}[B-DF-HJ-NP-TV-Z]{3}[A-Z0-9][0-9]/;
    private static final int LONGITUD_CURP = 18;
    private static final String ER_RFC = "^([a-zA-Z\u00D1\u00F1]{4})\\d{6}([a-zA-Z\\w]{3})$";
    private static final int LONGITUD_RFC = 13;
    private static final String FORMATO_FECHA = "dd/MM/yyyy";
    private static final String ER_FECHA = "^(0[1-9]|[12][0-9]|3[01])[//](0[1-9]|1[012])[//](19|20)[0-9][0-9]$";

    private static final String ERROR_LONGITUD_CURP = "La CURP tiene que ser de " + LONGITUD_CURP + " caracteres";
    private static final String ERROR_FORMATO_CURP = "La CURP no cumple con el formato requerido";
    
    private static final String ERROR_LONGITUD_RFC = "El RFC tiene que ser de " + LONGITUD_RFC + " caracteres";
    private static final String ERROR_FORMATO_RFC= "El RFC no cumple con el formato requerido";
    
    private static final String ERROR_VACIO_NOMBRE = "El NOMBRE no puede ser vac\u00edo";
    
    private static final String ERROR_VACIO_PRIMER_APELLIDO = "El PRIMER APELLIDO no puede ser vac\u00edo";
    
    private static final String ERROR_VACIO_SEXO = "El SEXO no puede ser vac\u00edo";
    
    private static final String ERROR_VACIO_FECHA_NACIMIENTO = "La FECHA DE NACIMIENTO no puede ser vac\u00eda";
    private static final String ERROR_FORMATO_FECHA_NACIMIENTO = "La FECHA DE NACIMIENTO no cumple con el formato requerido";
    
    private static final String ERROR_VACIO_LUGAR_NACIMIENTO = "El LUGAR DE NACIMIENTO no puede ser vac\u00edo";
   
    
    @Override
    public String validarCURP(String curp){
    	
    	if(curp.length() != LONGITUD_CURP){
    		return ERROR_LONGITUD_CURP;
    	}else if(!curp.matches(ER_CURP)){
    		return ERROR_FORMATO_CURP;
    	}else{
    		return "";
    	}
    	
    }
    
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
    
    @Override
    public List<String> validarDatosBasicos(Fisica fisica){

    	List<String> errores = new ArrayList<String>();
    	
    	// Se valida el nombre
        if(StringUtils.isBlank(fisica.getNombre())){
        	errores.add(ERROR_VACIO_NOMBRE);
        }/*else if(!fisica.getNombre().matches(ER_CADENA_ALFABETICA)){
        	errores.add(ERROR_FORMATO_NOMBRE);
        }else{
        	errores.add("");
        }*/
        
        // Se valida el primer apellido
        if(StringUtils.isBlank(fisica.getPrimerApellido())){
        	errores.add(ERROR_VACIO_PRIMER_APELLIDO);
        }/*else if(!fisica.getPrimerApellido().matches(ER_CADENA_ALFABETICA)){
        	errores.add(ERROR_FORMATO_PRIMER_APELLIDO);
        }else{
        	errores.add("");
        }*/
        
        // Se valida el sexo
        if(fisica.getSexo() == null || fisica.getSexo().getIdSexo() == -1){
        	errores.add(ERROR_VACIO_SEXO);
        }/*else{
        	errores.add("");
        }*/
        
        // Se valida la fecha de nacimiento
    	if(fisica.getFechaNacimiento() == null){
    		errores.add(ERROR_VACIO_FECHA_NACIMIENTO);
    	}else if(!new SimpleDateFormat(FORMATO_FECHA).format(fisica.getFechaNacimiento()).matches(ER_FECHA)){
    		errores.add(ERROR_FORMATO_FECHA_NACIMIENTO);
    	}/*else{
        	errores.add("");
        }*/
    		
    	// Se valida el lugar de nacimiento
        if(fisica.getLugarNacimiento() == null
        		|| fisica.getLugarNacimiento().getIdRenapo() == null
        		|| fisica.getLugarNacimiento().getIdRenapo().equals(-1L)){
        	errores.add(ERROR_VACIO_LUGAR_NACIMIENTO);
        }/*else{
        	errores.add("");
        }*/
        
        return errores;

    }


}
