package mx.gob.imss.cit.cda.web.validator;

import mx.gob.imss.cit.cda.web.constants.TiposAclaracionEnum;
import mx.gob.imss.cit.cda.web.vo.DomicilioAclaracionVO;
import mx.gob.imss.cit.cda.web.vo.DomicilioCorto;
import mx.gob.imss.cit.cda.web.vo.MotivoAclaracionVO;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class DomicilioValidator extends AbstractValidator {
	private static final Logger logger = LoggerFactory
			.getLogger(DomicilioValidator.class);
	private static final Long SUBDELEGACION_SIN_CAPTURAR = -1L;

	@Override
	public void validate(Object object, Errors errors) {
		logger.debug("---CDA--- ##Validator Domicilio Aclaracion#####");
		DomicilioCorto domicilio = (DomicilioCorto) object;
		this.validarDomicilio(domicilio, errors);
		this.validarAclaracion(domicilio.getMotivoAclaracionVO(), errors);
	}

	private void validarDomicilio(DomicilioCorto domicilio, Errors errors) {		
		logger.debug("---CDA--- Subdelegacion id {}",domicilio.getSubdelegacion().getId());
		if(domicilio.getSubdelegacion()== null || domicilio.getSubdelegacion().getId() == null || domicilio.getSubdelegacion().getId().equals(SUBDELEGACION_SIN_CAPTURAR)){
			errors.rejectValue("subdelegacion.id", "field.required");
		}
	}

	
	private void validarAclaracion(MotivoAclaracionVO motivoAclaracionVO,Errors errors) {
		if(motivoAclaracionVO.getMotivosAclaracionAfore() == null 
				&& motivoAclaracionVO.getMotivosAclaracionIMSS() == null
				&& motivoAclaracionVO.getMotivosAclaracionInfonavit() == null
				&& StringUtils.isBlank(motivoAclaracionVO.getOtro())){
			errors.rejectValue("motivoAclaracionVO.otro", "field.motivo.aclaracion.listaVacia");
		}else if(motivoAclaracionVO.getMotivosAclaracionInfonavit()!= null 
				&& motivoAclaracionVO.getMotivosAclaracionInfonavit().contains(TiposAclaracionEnum.DESCUENTO_INDEBIDO_CREDITO.getClave())
				&& StringUtils.isBlank(motivoAclaracionVO.getCreditoDescontado())){
			errors.rejectValue("motivoAclaracionVO.creditoDescontado", "field.motivo.aclaracion.infonavit.numero.credito");			
		}
		if(!motivoAclaracionVO.getOtro().isEmpty()
				&& StringUtils.isBlank(motivoAclaracionVO.getEspecificacion())){
		errors.rejectValue("motivoAclaracionVO.especificacion", "field.motivo.aclaracion.otro.especificacion");			
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return DomicilioAclaracionVO.class.equals(clazz);
	}

}
