package mx.gob.imss.ctirss.delta.portal.derechohabiente.web.controller.validator;

import java.util.regex.Pattern;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.PasoRegistroEnum;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RegistroDerechohabientesValidator implements Validator {

	private static final int LENGTH_APELLIDOS = 2;
	@Override
	public boolean supports(Class<?> object) {
		
		return TramiteRegistroDerechohabiente.class.equals(object);
	}

	@Override
	public void validate(Object object, Errors errors) {
		
		TramiteRegistroDerechohabiente tramite = (TramiteRegistroDerechohabiente) object;
		Long idPaso = tramite.getPaso();
		
		if(idPaso.equals(PasoRegistroEnum.CAPTURA_DATOS_PERSONALES.getId())) {
			validaDatosPersonales(tramite, errors);
		} else if(idPaso.equals(PasoRegistroEnum.CAPTURA_DOMICILIO.getId())) {
			validaDomicilio(tramite, errors);
		} else {
			validaDatosAdscripcion(tramite, errors);
		}
	}
	
	private void validaDatosPersonales(TramiteRegistroDerechohabiente tramite, Errors errors) {

		Fisica fisica = tramite.getFisica();
		
		if(fisica != null) {
			
			//si no trae nombre quiere recir que no trae curp
			if(StringUtils.isBlank(fisica.getNombre())) {
				errors.rejectValue("fisica.nombre", "field.required");
				errors.rejectValue("fisica.curp", "field.required");
			}
			
			if(StringUtils.isBlank(fisica.getPrimerApellido())) {
				errors.rejectValue("fisica.primerApellido", "field.required");
			} else {
				if(fisica.getPrimerApellido().length() < 2) {
					  errors.rejectValue("fisica.primerApellido", "field.min.length", new Object[] {new Integer(LENGTH_APELLIDOS)}, "");
				}
			}
			
			if(!StringUtils.isBlank(fisica.getSegundoApellido())) {
				if(fisica.getSegundoApellido().length() < 2) {
					  errors.rejectValue("fisica.segundoApellido", "field.min.length", new Object[] {new Integer(LENGTH_APELLIDOS)}, "");
				}
			}
			
			if(fisica.getSexo() == null || fisica.getSexo().getIdSexo().equals(-1)) {
				errors.rejectValue("fisica.sexo.idSexo", "field.required");
			}
			
			if(fisica.getLugarNacimiento() == null || fisica.getLugarNacimiento().getClave().equals("-1")) {
				errors.rejectValue("fisica.lugarNacimiento.clave", "field.required");
			}
			
			if(tramite.getParentesco() == null || tramite.getParentesco().getIdParentesco() == -1) {
				errors.rejectValue("parentesco.idParentesco", "field.required");
			}
			
			if(fisica.getEstadoCivil() == null || fisica.getEstadoCivil().getIdEstadoCivil() == -1) {
				errors.rejectValue("fisica.estadoCivil.idEstadoCivil", "field.required");
			}
			
			if(fisica.getFechaNacimiento() == null) {
				errors.rejectValue("fisica.fechaNacimiento", "field.required");
			}
			
			if(fisica.getCorreoElectronico() == null) {
				errors.rejectValue("fisica.correoElectronico.correo", "field.required");
			} else {
				if(StringUtils.isBlank(fisica.getCorreoElectronico().getCorreo())) {
					errors.rejectValue("fisica.correoElectronico.correo", "field.required");
				} else {
					validaMail(errors, fisica.getCorreoElectronico().getCorreo(), "fisica.correoElectronico.correo");
				}
			}
		}
	}
	
	private void validaDomicilio(TramiteRegistroDerechohabiente tramite, Errors errors) {
		
		Domicilio domicilio = tramite.getDomicilio();
		
		if(domicilio != null) {
			if(domicilio.getVialidadPrimaria() == null || StringUtils.isBlank(domicilio.getVialidadPrimaria().getNombre())) {
				errors.rejectValue("domicilio.vialidadPrimaria.nombre", "field.required");
			}
			
			if(domicilio.getNumExterior1() == null && StringUtils.isBlank(domicilio.getNumExteriorAlf())) {
				errors.rejectValue("domicilio.numExterior1", "field.required");
				errors.rejectValue("domicilio.numExteriorAlf", "field.required");
			}
			
			if(domicilio.getAsentamiento() == null || StringUtils.isBlank(domicilio.getAsentamiento().getNombre())) {
				errors.rejectValue("domicilio.asentamiento.nombre", "field.required");
			}
			
			
		
		} else {
			errors.rejectValue("domicilio.vialidadPrimaria.nombre", "field.required");
			errors.rejectValue("domicilio.numExterior1", "field.required");
			errors.rejectValue("domicilio.asentamiento.nombre", "field.required");
			errors.rejectValue("domicilio.asentamiento.localidad.nombre", "field.required");
			errors.rejectValue("domicilio.asentamiento.localidad.municipio.nombre", "field.required");
			errors.rejectValue("domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre", "field.required");
			errors.rejectValue("domicilio.codigoPostal.codigoPostal", "field.required");
		}
		
	}
	
	private void validaDatosAdscripcion(TramiteRegistroDerechohabiente tramite, Errors errors) {
		MedicoEnTurno medicoEnTurno = tramite.getMedicoEnTurno();
		Long idInvalido = -1L;
		
		if(medicoEnTurno != null) {
			
			if(medicoEnTurno.getUnidadMedicaFamiliar() == null || medicoEnTurno.getUnidadMedicaFamiliar().getIdUMF() == null
					|| medicoEnTurno.getUnidadMedicaFamiliar().getIdUMF().equals(idInvalido)) {
				errors.rejectValue("medicoEnTurno.unidadMedicaFamiliar.idUMF", "field.required");
			}
			
			if(medicoEnTurno.getTurno() == null || medicoEnTurno.getTurno().getIdTurno() == null ||
					medicoEnTurno.getTurno().getIdTurno().equals(idInvalido)) {
				errors.rejectValue("medicoEnTurno.turno.idTurno", "field.required");
			}
			
			if(medicoEnTurno.getConsultorio() == null || medicoEnTurno.getConsultorio().getIdConsultorio() == null
					|| medicoEnTurno.getConsultorio().getIdConsultorio().equals(idInvalido)) {
				errors.rejectValue("medicoEnTurno.consultorio.idConsultorio", "field.required");
			}
		} else {
			errors.rejectValue("medicoEnTurno.unidadMedicaFamiliar.idUMF", "field.required");
			errors.rejectValue("medicoEnTurno.turno.idTurno", "field.required");
			errors.rejectValue("medicoEnTurno.consultorio.idConsultorio", "field.required");
		}
		
	}
	
	private void validaMail(Errors errors, String correo, String campo) {
		if(correo!=null && correo.length()>0){
			correo=correo.toLowerCase();
			if(!Pattern.compile("[a-z]+[a-z0-9\\._-]*@[a-z0-9]+(\\.[a-z]{2,3}){1,2}").matcher(correo).matches()){
				errors.rejectValue(campo, "field.invalid", "field.invalid");
			}
		}
	}

}