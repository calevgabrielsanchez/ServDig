package mx.imss.ctirss.web.validator;



import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mx.imss.ctirss.framework.utils.ConstantesBusiness;
import mx.imss.ctirss.web.bean.DenunciaDTO;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class DenunciaValidator implements Validator {

    @Override    
    public boolean supports(Class<?> clazz) {             
    	return DenunciaDTO.class.equals(clazz); // clase del bean al que da soporte este validador    
    }
    
    @Override    
    public void validate(Object target, Errors errors) {         	    	
    	DenunciaDTO denunciaDTO =  (DenunciaDTO)target;    	
    	String paso = denunciaDTO.getPaso();
    	
    	if(paso.equalsIgnoreCase("1")){
    		errors = validaDatosTrabajador(denunciaDTO,errors);
    	}
    	
    	if(paso.equalsIgnoreCase("2")){
    		errors = validaDatosPatron(denunciaDTO,errors);
    	}
    	
    	if(denunciaDTO.getEnviar() == 1){
    		System.out.println("solicitud de envio de denuncia");
    		errors = this.validaDatosTrabajador(denunciaDTO, errors);
    		if(errors.getAllErrors().size()!= 0){
    			
    		}
    		//valida todos los campos requeridos
    		//validaDatosTrabajador
    		//validaDatosPatrin
    		//validaDatisInfoTrabajo
    		//errores
    		
    	}
    }
    
    public Errors validaDatosTrabajador(DenunciaDTO denunciaDTO, Errors errors){    	
    	if(denunciaDTO.getDltPersonaT().getDesNombre().trim().equalsIgnoreCase("")){
    		errors.reject("error.nombre.trabajador.vacio");
    	}
    	if(denunciaDTO.getDltPersonaT().getDesPaterno().trim().equalsIgnoreCase("")){
    		errors.reject("error.apellidoP.trabajador.vacio");
    	}
    	if(denunciaDTO.getDltPersonaT().getCveCurp().trim().equalsIgnoreCase("")){
    		errors.reject("error.curp.vacio");
    	}
    	if( new Long(denunciaDTO.getDltPersonaT().getDlcTipodocumento().getCveTipodocumento()).toString().equalsIgnoreCase("-1") ){
    		errors.reject("error.identificacion.vacia");
    	}
    	if( denunciaDTO.getDltPersonaT().getNumDocumento().trim().equalsIgnoreCase("")){
    		errors.reject("error.numero.identificacion.vacia");
    	}
    	if(!denunciaDTO.getDltPersonaT().getDesEmail().trim().equalsIgnoreCase("")){
    		String email = denunciaDTO.getDltPersonaT().getDesEmail().trim();
    		Pattern p = Pattern.compile(ConstantesBusiness.EMAIL_PATTERN);
    	    Matcher matcher = p.matcher(email);
    		if(!matcher.matches()){  //true, valido - false, invalido
    			errors.reject("error.email.invalido");
    		}
    		
    	}
    	
    	
    	/*if( denunciaDTO.getMotivosDenuncia() ) {
    	 * String[] mD = request.getParameterValues("md");
    	 * if(mD.length == 0){
    		errors.reject("error.motivos.denuncia.vacia");
    		}else{
    		     try{
					for(int i=0; i < mD.length; i ++){
					   if(mD[i].equalsIgnoreCase("1")){
					       if(denunciaDTO.getValoresMotivoDenuncia().getMd1fechaInicio().trim().equalsIgnorCase("")){
					            errors.reject("error.numero.identificacion.vacia");
					       }
					       if(denunciaDTO.getValoresMotivoDenuncia().getMd1fechaFin().trim().equalsIgnorCase("")){
					            errors.reject("error.numero.identificacion.vacia");
					       }
					       
					   }
					   if(mD[i].equalsIgnoreCase("2")){}
					   if(mD[i].equalsIgnoreCase("3")){}
					   if(mD[i].equalsIgnoreCase("4")){}
					}
    			 }catch(ParseException pe){
        	    	logger.error("Error parseando la fecha" + pe.getMessage());   
        	    }     	
        }
    	}*/
    	
    	if(denunciaDTO.getDltPersonaT().getDlcTipodenunciante().getCveTipodenunciante()== 2){
    		if(denunciaDTO.getDltPersonaB().getDesNombre().trim().equalsIgnoreCase("")){
        		errors.reject("error.nombre.beneficiario.vacio");
        	}
        	if(denunciaDTO.getDltPersonaB().getDesPaterno().trim().equalsIgnoreCase("")){
        		errors.reject("error.apellidoP.beneficiario.vacio");
        	}
        	if(denunciaDTO.getDltPersonaB().getCveCurp().trim().equalsIgnoreCase("")){
        		errors.reject("error.apellidoM.beneficiario.vacio");
        	}
        	if( new Long(denunciaDTO.getDltPersonaB().getDlcTipodocumento().getCveTipodocumento()).toString().equalsIgnoreCase("-1") ){
        		errors.reject("error.identificacion.vacia");
        	}
        	if( denunciaDTO.getDltPersonaB().getNumDocumento().trim().equalsIgnoreCase("")){
        		errors.reject("error.numero.identificacion.vacia");
        	}
    	}
    	if(denunciaDTO.getDltPersonaT().getDlcTipodenunciante().getCveTipodenunciante()== 3){
    		if(denunciaDTO.getDltPersonaRL().getDesNombre().trim().equalsIgnoreCase("")){
        		errors.reject("error.nombre.repLegal.vacio");
        	}
        	if(denunciaDTO.getDltPersonaRL().getDesPaterno().trim().equalsIgnoreCase("")){
        		errors.reject("error.apellidoP.repLegal.vacio");
        	}
        	if(denunciaDTO.getDltPersonaRL().getCveCurp().trim().equalsIgnoreCase("")){
        		errors.reject("error.apellidoM.repLegal.vacio");
        	}
        	if( new Long(denunciaDTO.getDltPersonaRL().getDlcTipodocumento().getCveTipodocumento()).toString().equalsIgnoreCase("-1") ){
        		errors.reject("error.identificacion.vacia");
        	}
        	if( denunciaDTO.getDltPersonaRL().getNumDocumento().trim().equalsIgnoreCase("")){
        		errors.reject("error.numero.identificacion.vacia");
        	}
    		
    	}
    	    	
    	return errors;
    }
    
    public Errors validaDatosPatron(DenunciaDTO denunciaDTO, Errors errors){ 
    	
    	return errors;
    }
    
}
