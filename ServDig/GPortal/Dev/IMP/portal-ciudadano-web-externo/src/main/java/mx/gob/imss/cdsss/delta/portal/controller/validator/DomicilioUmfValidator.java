package mx.gob.imss.cdsss.delta.portal.controller.validator;

import java.util.regex.Pattern;

import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.UmfDomicilioDTO;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class DomicilioUmfValidator implements Validator {
	
	private static final String REGEX_CURP_FISICA = "^([a-zA-Z]{4})\\d{6}([a-zA-Z]{6}[a-zA-Z0-9]{2})$";
	private static final int LENGTH_CURP = 18;

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void validatePersona(Object object, Errors errors) {
		Fisica pf = (Fisica) object;
		
		    	
				//Validacion de CURP
		        if(StringUtils.isNotBlank(pf.getCurp())){
		            if(pf.getCurp().length() != LENGTH_CURP){
		                errors.rejectValue("curp", "field.min.length", new Object[] {new Integer(LENGTH_CURP)}, "");
		            }else{
		                if(!pf.getCurp().matches(REGEX_CURP_FISICA)){
		                    errors.rejectValue("curp", "field.wrong.format");
		                }
		            }
		        }else{
		        	errors.rejectValue("curp", "field.required");
		        }
	}
	
	public void validateMedios(Object object, Errors errors) {
		Persona pf = (Persona) object;
		
		if(pf.getCorreoElectronico() != null) {
			validaMail(errors, pf.getCorreoElectronico().getCorreo(), "correoElectronico.correo");
		}
		
	}

	public void validateCodigoPostal(Object object , Errors errors) {
		UmfDomicilioDTO umfDomicilio = (UmfDomicilioDTO) object;
		
		Domicilio domicilio = umfDomicilio.getDomicilio();
		
		if(domicilio != null && domicilio.getCodigoPostal() != null && domicilio.getCodigoPostal().getCodigoPostal() != null &&
				!domicilio.getCodigoPostal().getCodigoPostal().isEmpty()) {
			String codigo = domicilio.getCodigoPostal().getCodigoPostal();
			
			if(codigo.length() < 5){
				errors.rejectValue("domicilio.codigoPostal.codigoPostal", "", "El campo de c\u00F3digo postal es obligatorio, y se compone de 5 d\u00EDgitos." );
			}
			
			try {
				Integer.valueOf(codigo);
			} catch (Exception e) {
				errors.rejectValue("domicilio.codigoPostal.codigoPostal", "" , "Debe ser n\u00FAmerico." );
			}
		} else {
			errors.rejectValue("domicilio.codigoPostal.codigoPostal","", "El campo de c\u00F3digo postal es obligatorio, y se compone de 5 d\u00EDgitos."  );
		}
	}
	
	@Override
	public void validate(Object object, Errors errors) {
		
		UmfDomicilioDTO umfDomicilio = (UmfDomicilioDTO) object;
		//Validamos los datos del domicilio
		this.validateCodigoPostal(object, errors);
		//Validamos datos de adscripcion
		this.validaDatosAdscripcion(umfDomicilio.getMedicoEnTurno(), errors);
	}
	
	public void validateVersionDomicilio(Object object, Errors errors) {
		
		UmfDomicilioDTO umfDomicilio = (UmfDomicilioDTO) object;
		//Validamos los datos del domicilio
		this.validarDomicilio(umfDomicilio.getDomicilio(), errors);
		//Validamos datos de adscripcion
		this.validaDatosAdscripcion(umfDomicilio.getMedicoEnTurno(), errors);
	}
	
	private void validaMail(Errors errors, String correo, String campo) {
		if(correo!=null && correo.length()>0){
			correo=correo.toLowerCase();
			if(!Pattern.compile("[a-z]+[a-z0-9\\._-]*@[a-z0-9]+(\\.[a-z]{2,3}){1,2}").matcher(correo).matches()){
				errors.rejectValue(campo, "field.invalid", "field.invalid");
			}
		}
	}
	
	private void validarDomicilio(Domicilio domicilio, Errors errors) {
		if(domicilio != null) {
			
			if(domicilio.getCodigoPostal() == null || StringUtils.isBlank(domicilio.getCodigoPostal().getCodigoPostal())) {
				errors.rejectValue("domicilio.codigoPostal.codigoPostal"," ","El campo de c\u00F3digo postal es obligatorio, y se compone de 5 d\u00EDgitos.");
			}
			
			if(StringUtils.isBlank(domicilio.getCalle())) {
				errors.rejectValue("domicilio.calle", "field.required");
			}
			
			if(StringUtils.isBlank(domicilio.getNumExteriorAlf())) {
				errors.rejectValue("domicilio.numExteriorAlf", "field.required");
			}
			
			if(domicilio.getAsentamiento() == null || domicilio.getAsentamiento().getClave().equals("-1")) {
				errors.rejectValue("domicilio.asentamiento.clave", "field.required");
			}
			
			
		
		} else {
			errors.rejectValue("domicilio.vialidadPrimaria.nombre", "field.required");
			errors.rejectValue("domicilio.numExterior1", "field.required");
			errors.rejectValue("domicilio.asentamiento.clave", "field.required");
			errors.rejectValue("domicilio.codigoPostal.codigoPostal", "field.required");
		}
	}
	
	private void validaDatosAdscripcion(MedicoEnTurno medicoEnTurno, Errors errors) {
		Long idInvalido = -1L;
		
		if(medicoEnTurno != null) {
			
			if(medicoEnTurno.getUnidadMedicaFamiliar() == null || medicoEnTurno.getUnidadMedicaFamiliar().getIdUMF() == null
					|| medicoEnTurno.getUnidadMedicaFamiliar().getIdUMF().equals(idInvalido)) {
				errors.rejectValue("medicoEnTurno.unidadMedicaFamiliar.idUMF", "","Para poder continuar es requerido que seleccione la cl\u00EDnica y/o el turno");
			}
			
			if(medicoEnTurno.getTurno() == null || medicoEnTurno.getTurno().getIdTurno() == null ||
					medicoEnTurno.getTurno().getIdTurno().equals(idInvalido)) {
				errors.rejectValue("medicoEnTurno.turno.idTurno", "","Selecciona el turno de tu preferencia");
			}
			
			if(medicoEnTurno.getConsultorio() == null || medicoEnTurno.getConsultorio().getIdConsultorio() == null
					|| medicoEnTurno.getConsultorio().getIdConsultorio().equals(idInvalido)) {
				errors.rejectValue("medicoEnTurno.consultorio.idConsultorio", "field.required");
			}
			
			
		} else {
			//se ponen en tipo oracion los textos
			errors.rejectValue("medicoEnTurno.unidadMedicaFamiliar.idUMF", "","Para poder continuar es requerido que seleccione la cl\u00EDnica y/o el turno");
			errors.rejectValue("medicoEnTurno.turno.idTurno", "","Selecciona el turno de tu preferencia");
			errors.rejectValue("medicoEnTurno.consultorio.idConsultorio", "field.required");
		}
		
	}

}
