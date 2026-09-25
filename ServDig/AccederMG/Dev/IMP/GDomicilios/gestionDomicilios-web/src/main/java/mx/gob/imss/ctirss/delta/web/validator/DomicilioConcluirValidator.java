/**
 * gestionDomicilios-web23/04/2012
 * mx.gob.imss.ctirss.delta.web.validator23/04/2012
 * DomicilioConcluirValidator.java
 * 23/04/2012
 * 
 */
package mx.gob.imss.ctirss.delta.web.validator;

import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioCamino;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioCarretera;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Vialidad;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoBusquedaVialidadEnum;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Lucio Duran Silva Instituto Mexicano del Seguro Social
 */
public class DomicilioConcluirValidator implements Validator {
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return Domicilio.class.equals(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object arg0, Errors errors) {

		Domicilio domicilio = (Domicilio) arg0;

		/*Vialidad vialidadPrimaria = domicilio.getVialidadPrimaria();
		
		
		// Validaciones de datos requeridos
		if (vialidadPrimaria == null || (vialidadPrimaria.getClave() == null
				&& StringUtils.isBlank(vialidadPrimaria.getNombre()))) {
			errors.rejectValue("vialidadPrimaria.clave", "field.required");
		}*/
		
		//Se verifica que al menos el campo alfanumerico de 
		if (StringUtils.isBlank(domicilio.getNumExteriorAlf())) {
			if(domicilio.getNumExterior1() == null) {
				errors.rejectValue("numExterior1", "field.required");
			}
		} 
		
		Localidad localidad= domicilio.getAsentamiento().getLocalidad();
		
		if(localidad == null || StringUtils.isEmpty(localidad.getClave()) || localidad.getClave()=="-1") {
			errors.rejectValue("asentamiento.localidad.clave", "field.required");
		}
		/* 
		 * Se validan que las vialidades elegidas sean correctas, es decir,
		 * que se hayan elegido a través del autocompletar.
		 */
		//validarVialidad(vialidadPrimaria, errors, "vialidadPrimaria.clave");
		validarVialidadPrimaria(domicilio, errors);
		validarVialidad(domicilio.getVialidadReferenciaPrimaria(), errors, "vialidadReferenciaPrimaria.clave");
		validarVialidad(domicilio.getVialidadReferenciaSecundaria(), errors, "vialidadReferenciaSecundaria.clave");
		validarVialidad(domicilio.getVialidadReferenciaPosterior(), errors, "vialidadReferenciaPosterior.clave");
	}
	
	private void validarVialidadPrimaria(Domicilio domicilio, Errors errors) {
		
		Vialidad vialidad = domicilio.getVialidadPrimaria();
		DomicilioCamino camino =  domicilio.getDomicilioCamino();
		DomicilioCarretera carretera =  domicilio.getDomicilioCarretera();
		
		Integer tipoCalle = domicilio.getTipoBusquedaVialidad();

		if(tipoCalle == null || tipoCalle.equals(TipoBusquedaVialidadEnum.VIALIDAD.getCodigo())) {
			if (vialidad == null || (vialidad.getClave() == null
					&& StringUtils.isBlank(vialidad.getNombre()))) {
				errors.rejectValue("vialidadPrimaria.clave", "field.required");
			} else{
				validarVialidad(vialidad, errors, "vialidadPrimaria.clave");
			}
		} else if (tipoCalle.equals(TipoBusquedaVialidadEnum.VIALIDAD_NO_LOCALIZADA.getCodigo())){
			if(domicilio.getCalle() == null || StringUtils.isBlank(domicilio.getCalle())) {
				errors.rejectValue("vialidadPrimaria.clave", "fiel.all.required");
			}
		} else if (tipoCalle.equals(TipoBusquedaVialidadEnum.CARRETERA.getCodigo())) {
			if(
					(carretera.getAdministracion() == null || carretera.getAdministracion().getClave() == null || carretera.getAdministracion().getClave() == -1) ||
					(carretera.getDerechoTransito() == null || carretera.getDerechoTransito().getClave() == null || carretera.getDerechoTransito().getClave() == -1) ||
					(carretera.getTerminoGeneral() == null || carretera.getTerminoGeneral().getClave() == null || carretera.getTerminoGeneral().getClave() == -1) ||
					StringUtils.isBlank(carretera.getCadenamiento()) || StringUtils.isBlank(carretera.getDestino()) ||
					StringUtils.isBlank(carretera.getOrigen()) || (carretera.getCodigoCarretera() == null)
			) {
				System.out.println("Se validó que no haya carretera");
				errors.rejectValue("vialidadPrimaria.clave", "fiel.all.required");
				return;
			}
		} else if (tipoCalle.equals(TipoBusquedaVialidadEnum.CAMINO.getCodigo())) {
			if(
					(camino.getTerminoGeneral() == null || camino.getTerminoGeneral().getClave() == null || camino.getTerminoGeneral().getClave() == -1) ||
					(camino.getMargen() == null || camino.getMargen().getClave() == null || camino.getMargen().getClave() == -1) ||
					StringUtils.isBlank(camino.getCadenamiento()) || StringUtils.isBlank(camino.getDestino())
					|| StringUtils.isBlank(camino.getOrigen())
			) {
				System.out.println("Se validó que no haya camino");
				errors.rejectValue("vialidadPrimaria.clave", "fiel.all.required");
				return;
			}
		}
		
		
	}
	
	private void validarVialidad(Vialidad vialidad, Errors errors, String campo) {
		
		if (vialidad != null && StringUtils.isNotBlank(vialidad.getNombre()) 
				&& vialidad.getClave() == null
				&& (vialidad.getTipoVialidad() == null 
				|| vialidad.getTipoVialidad().getClave() == null
				|| vialidad.getTipoVialidad().getClave().intValue() == -1)) {
			errors.rejectValue(campo, "vialidad.invalida");
		}
	}
	
	
}
