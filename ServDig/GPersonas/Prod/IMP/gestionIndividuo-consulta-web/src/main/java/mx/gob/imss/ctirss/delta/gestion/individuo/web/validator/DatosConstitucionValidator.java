package mx.gob.imss.ctirss.delta.gestion.individuo.web.validator;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.ctirss.delta.framework.base.web.ObjectError;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EscrituraConstitutiva;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RegistroSindicato;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class DatosConstitucionValidator implements Validator{

	
	protected static final String MENSAJE_ERROR_BUSQUEDAS = "La persona <PERSONA> no fue localizada en IMSS ni en <ENTIDAD_EXTERNA>. Favor de intentar nuevamente con los criterios de busqueda de datos basicos";
    protected static final String KEY_CODE_ERROR_FIELDS = "erroresCaptura";
	protected static final String KEY_CODE_ERROR_BUSINESS = "erroresNegocio";

	@Override
	public boolean supports(Class<?> arg0) {
		return Moral.class.equals(arg0);
	}

	
	public Map<String, Object> validate(Object moral, Errors errors,HttpServletResponse response,MessageSource messageSource) {
		List<ObjectError > erroresList = new ArrayList<ObjectError>();
		Moral persona=(Moral)moral;
		Map<String, Object> result=new HashMap<String, Object>();
		/*TODO se inive la seccion de escritura y sindicato
		if(persona.getEscrituraConstitutiva()!=null){  
			EscrituraConstitutiva x=persona.getEscrituraConstitutiva();
			
				if(StringUtils.isBlank(x.getNumEscritura())){
					    ObjectError e = new ObjectError();
		                e.setCampo("numEscritura");
		                e.setMensaje(messageSource.getMessage("field.required", null,Locale.getDefault()));
		                erroresList.add(e);
					
				
					
				}
				if(StringUtils.isBlank(x.getNumNotaria())){
					 ObjectError e = new ObjectError();
		                e.setCampo("numNotaria");
		                e.setMensaje(messageSource.getMessage("field.required", null,Locale.getDefault()));
		                erroresList.add(e);
					
				}
				if(StringUtils.isBlank(x.getLugarExpedicion().getEntidadFederativa().getClave())|| 
						x.getLugarExpedicion().getEntidadFederativa().getClave().equals("-1")){
					ObjectError e = new ObjectError();
	                e.setCampo("idEstado");
	                e.setMensaje(messageSource.getMessage("field.required", null,Locale.getDefault()));
	                erroresList.add(e);
					
				}
				if(StringUtils.isBlank(x.getLugarExpedicion().getClave())
						|| x.getLugarExpedicion().getClave().equals("-1")
						){
					ObjectError e = new ObjectError();
	                e.setCampo("idMunicipio");
	                e.setMensaje(messageSource.getMessage("field.required", null,Locale.getDefault()));
	                erroresList.add(e);
					
				}
				if(x.getFechaExpedicion()==null){
					ObjectError e = new ObjectError();
	                e.setCampo("fecExpedicion");
	                e.setMensaje(messageSource.getMessage("field.required", null,Locale.getDefault()));
	                erroresList.add(e);
				
				}else if(x.getFechaExpedicion()!=null){
					Calendar fechaActual = Calendar.getInstance();
					fechaActual.set(Calendar.HOUR_OF_DAY, 0);
					fechaActual.set(Calendar.MINUTE, 0);
					fechaActual.set(Calendar.SECOND, 0);
					fechaActual.set(Calendar.MILLISECOND, 0);
					Date date = fechaActual.getTime();
					//fechaActual.add(Calendar.DAY_OF_YEAR, -364);
					
					
					if (x.getFechaExpedicion().compareTo(date) > 0 ) {
						ObjectError e = new ObjectError();
		                e.setCampo("fecExpedicion");
		                e.setMensaje("La fecha no deberá de ser mayor a la fecha en que se realiza el tramite.");
		                erroresList.add(e);
					}
					
				}
				
				
				if(StringUtils.isBlank(x.getFolioMercantil())){
					boolean folioReq=true;
					
//					if(StringUtils.isBlank(x.getSeccion())){
//						ObjectError e = new ObjectError();
//		                e.setCampo("seccion");
//		                e.setMensaje(messageSource.getMessage("field.required", null,Locale.getDefault()));
//		                erroresList.add(e);
//					}else{
//						folioReq=false;
//					}
//					
//					if(StringUtils.isBlank(x.getPartida())){
//						ObjectError e = new ObjectError();
//		                e.setCampo("partida");
//		                e.setMensaje(messageSource.getMessage("field.required", null,Locale.getDefault()));
//		                erroresList.add(e);
//					}else{
//						folioReq=false;
//					}
//					if(StringUtils.isBlank(x.getVolumen())){
//						ObjectError e = new ObjectError();
//		                e.setCampo("volumen");
//		                e.setMensaje(messageSource.getMessage("field.required", null,Locale.getDefault()));
//		                erroresList.add(e);
//					}else{
//						folioReq=false;
//					}
//					if(StringUtils.isBlank(x.getFoja())){
//						ObjectError e = new ObjectError();
//		                e.setCampo("foja");
//		                e.setMensaje(messageSource.getMessage("field.required", null,Locale.getDefault()));
//		                erroresList.add(e);
//					}else{
//						folioReq=false;
//					}
//					if (folioReq){
//						ObjectError e = new ObjectError();
//		                e.setCampo("folioMercantil");
//		                e.setMensaje(messageSource.getMessage("field.required", null,Locale.getDefault()));
//		                erroresList.add(e);
//						
//					}
			
				}
				
		}else if(persona.getRegistroSindicato()!=null){
			RegistroSindicato sindicato=persona.getRegistroSindicato();
			
			if(StringUtils.isBlank(sindicato.getNumReferenciadocRegistro())){
			//if(sindicato.getNumReferenciadocRegistro()==null || (sindicato.getNumReferenciadocRegistro()!=null&&sindicato.getNumReferenciadocRegistro()==0) ){
				ObjectError e = new ObjectError();
                e.setCampo("numReferencia");
                e.setMensaje(messageSource.getMessage("field.required", null,Locale.getDefault()));
                erroresList.add(e);
			}
			if(sindicato.getFechaRegistro()==null ){
				ObjectError e = new ObjectError();
                e.setCampo("fecRegistro");
                e.setMensaje(messageSource.getMessage("field.required", null,Locale.getDefault()));
                erroresList.add(e);
			}else if(sindicato.getFechaRegistro()!=null){
				
				Calendar fechaActual = Calendar.getInstance();
				fechaActual.set(Calendar.HOUR_OF_DAY, 0);
				fechaActual.set(Calendar.MINUTE, 0);
				fechaActual.set(Calendar.SECOND, 0);
				fechaActual.set(Calendar.MILLISECOND, 0);
				Date date = fechaActual.getTime();
				
				if (sindicato.getFechaRegistro().compareTo(date) > 0 ) {
					ObjectError e = new ObjectError();
	                e.setCampo("fecRegistro");
	                e.setMensaje("La fecha no deberá de ser mayor a la fecha en que se realiza el tramite.");
	                erroresList.add(e);
				}
			}
			if(StringUtils.isBlank(sindicato.getAutoridadLaboral()) ){
				ObjectError e = new ObjectError();
                e.setCampo("autLaboral");
                e.setMensaje(messageSource.getMessage("field.required", null,Locale.getDefault()));
                erroresList.add(e);
			}
		}
		
		if(!erroresList.isEmpty()){
			result.put("mensaje", "La información proporcionada presenta errores, favor de verificarla.");
			result.put(KEY_CODE_ERROR_FIELDS, erroresList);
			response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED );
		}
		*/
		return result;
	}
 
	
	


	@Override
	public void validate(Object arg0, Errors arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
