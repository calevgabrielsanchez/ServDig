package mx.gob.imss.ctirss.delta.derechohabientes.web.validator;

import java.util.Calendar;
import java.util.regex.Pattern;

import mx.gob.imss.ctirss.delta.framework.base.validator.AbstractValidator;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RegistroDerechohabientesValidator extends AbstractValidator implements Validator {

	@Override
	public boolean supports(Class<?> object) {
		
		return TramiteRegistroDerechohabiente.class.equals(object);
	}

	@Override
	public void validate(Object object, Errors errors) {
		
		TramiteRegistroDerechohabiente tramite = (TramiteRegistroDerechohabiente) object;
		
		log.debug("La razon del registro es: " + tramite.getRazonRegistro());
		if(tramite.getRazonRegistro() == null || tramite.getRazonRegistro().getIdRazonRegistro() == null || tramite.getRazonRegistro().getIdRazonRegistro().equals(-1L)) {
			log.debug("No se encontro la razon de registro");
			errors.rejectValue("razonRegistro.idRazonRegistro", "field.required");
		}
		
		if(tramite.getParentesco() == null || tramite.getParentesco().getIdParentesco() == null || tramite.getParentesco().getIdParentesco().equals(-1L)) {
			errors.rejectValue("parentesco.idParentesco", "field.required");
		}
		
		//if(idPaso.equals(PasoRegistroEnum.CAPTURA_DATOS_PERSONALES.getId())) {
		validaDatosPersonales(tramite, errors);
		//} else if(idPaso.equals(PasoRegistroEnum.CAPTURA_DOMICILIO.getId())) {
		//TODO ya no se valida domicilio ya que se usa el componente de domicilio recortado y el mismo lo valida
		//validaDomicilio(tramite, errors);
		/*} else {
			validaDatosAdscripcion(tramite, errors);
		}*/
	}
	
	private void validaDatosPersonales(TramiteRegistroDerechohabiente tramite, Errors errors) {

		Fisica fisica = tramite.getFisica();
		Parentesco par = tramite.getParentesco();
		
		if(fisica != null) {
			if(StringUtils.isBlank(fisica.getNombre())) {
				errors.rejectValue("fisica.nombre", "field.required");
			}
			
			if(!StringUtils.isBlank(fisica.getPrimerApellido())) {
				if(fisica.getPrimerApellido().length() < 1) {
					  errors.rejectValue("fisica.primerApellido", "field.min.length", new Object[] {new Integer(1)}, "");
				}
			}
			/*if(StringUtils.isBlank(fisica.getPrimerApellido())) {
				errors.rejectValue("fisica.primerApellido", "field.required");
			} else {
				if(fisica.getPrimerApellido().length() < 2) {
					  errors.rejectValue("fisica.primerApellido", "field.min.length", new Object[] {new Integer(LENGTH_APELLIDOS)}, "");
				}
			}*/
			
			if(!StringUtils.isBlank(fisica.getSegundoApellido())) {
				if(fisica.getSegundoApellido().length() < 1) {
					  errors.rejectValue("fisica.segundoApellido", "field.min.length", new Object[] {new Integer(1)}, "");
				}
			}
			
			if(fisica.getSexo() == null || fisica.getSexo().getIdSexo().equals(-1)) {
				errors.rejectValue("fisica.sexo.idSexo", "field.required");
			}
			
			if(fisica.getLugarNacimiento() == null || fisica.getLugarNacimiento().getClave().equals("-1")) {
				errors.rejectValue("fisica.lugarNacimiento.clave", "field.required");
			}
			
			if(fisica.getEstadoCivil() == null || fisica.getEstadoCivil().getIdEstadoCivil() == -1) {
				errors.rejectValue("fisica.estadoCivil.idEstadoCivil", "field.required");
			}
			

			if(fisica.getFechaNacimiento() == null) {
				errors.rejectValue("fisica.fechaNacimiento", "field.required");
			} else {
				if(tramite.getConFechaNacimiento().intValue() == 0) {
					if(par != null && par.getIdParentesco() != null && (par.getIdParentesco().equals(5L) || par.getIdParentesco().equals(6L))) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(fisica.getFechaNacimiento());
						
						int mes = fisica.getMesRegistroNac();
						int anio = 1900 + fisica.getAnioRegistroNac();
						Integer mesFecha = cal.get(Calendar.MONTH)+1;
						Integer anioFecha = cal.get(Calendar.YEAR);
						
						log.debug("el mes es: " + mesFecha + " el de sindo es: " + mes);
						log.debug("el anio es: " + anioFecha + " el de sindo es: " + anio);
						if((mes != mesFecha) || anio != anioFecha) {
							errors.rejectValue("fisica.fechaNacimiento","fecha.error.aniomes");
						}
						
					}
				}
			}
			
			if(fisica.getCorreoElectronico() != null) {
				if(!StringUtils.isBlank(fisica.getCorreoElectronico().getCorreo())) {
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
	
	public void validaDatosAdscripcion(TramiteRegistroDerechohabiente tramite, Errors errors) {
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
			
			if(StringUtils.isBlank(tramite.getObservacion())) {
				errors.rejectValue("observacion", "field.required");
			}
		} else {
			errors.rejectValue("medicoEnTurno.unidadMedicaFamiliar.idUMF", "field.required");
			errors.rejectValue("medicoEnTurno.turno.idTurno", "field.required");
			errors.rejectValue("medicoEnTurno.consultorio.idConsultorio", "field.required");
			
			if(StringUtils.isBlank(tramite.getObservacion())) {
				errors.rejectValue("observacion", "field.required");
			}
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