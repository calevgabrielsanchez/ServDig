/**
 * RectificacionValidator.java
 * mx.gob.imss.ctirss.delta.clasificacion.empresa.web.validation
 * clasificacionEmpresas-web
 */
package mx.gob.imss.ctirss.delta.web.validator;

import mx.gob.imss.ctirss.delta.framework.base.validator.AbstractValidator;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.web.utils.Constantes;
import mx.gob.imss.ctirss.delta.model.clasificacion.ReporteClemBean;
import mx.gob.imss.ctirss.delta.model.clasificacion.TipoDatosClemEnum;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Lucio Duran Silva
 * 13/01/2012
 */
public class DatosClemValidator extends AbstractValidator implements Validator {

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return DatosClemValidator.class.equals(clazz);
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lugarFechaExpedicion", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "motivos", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "idAnalisis", "field.required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "puesto", "field.required");
		
		ReporteClemBean clemBean=(ReporteClemBean)target;
		
		if(!errors.hasErrors()){

			if(clemBean.getCveTipoClem().equals(String.valueOf(TipoDatosClemEnum.DELEGACIONAL.getClave()))){

				if(clemBean.getTitular() == null || clemBean.getTitular().trim().length() == 0){
					log.debug("Error de CLEM_TITULAR_NULL");
					errors.rejectValue("titular", "field.clem.titular", new Object[] {new Integer(Constantes.CLEM_LONGITUD_TITULAR_SUP)},"");
				}else if(clemBean.getTitular().length() > Constantes.CLEM_LONGITUD_TITULAR_SUP ){
					log.debug("Error de CLEM_LONGITUD_TITULAR");
					errors.rejectValue("titular", "field.clem.titular", new Object[] {new Integer(Constantes.CLEM_LONGITUD_TITULAR_SUP)},"");
				}

			}else{ // si la clem es subdelegacional

				//si no viene seleccionado la firma por ausencia, el titular es requerido
				if(clemBean.getFirmaAusencia().equals("0")){
					if(clemBean.getTitular() == null || clemBean.getTitular().trim().length() == 0){
						log.debug("Error de CLEM_TITULAR_NULL");
						errors.rejectValue("titular", "field.titular.required", new Object[] {new Integer(Constantes.CLEM_LONGITUD_TITULAR_SUP)},"");
					}else if(clemBean.getTitular().length() > Constantes.CLEM_LONGITUD_TITULAR_SUP ){
						log.debug("Error de CLEM_LONGITUD_TITULAR");
						errors.rejectValue("titular", "field.clem.titular", new Object[] {new Integer(Constantes.CLEM_LONGITUD_TITULAR_SUP)},"");
					}
					clemBean.setSuplente(null);
				}else{
					if(StringUtils.isBlank(clemBean.getSuplente())){
						log.debug("Error de CLEM_SUPLENTE_NULL");
						errors.rejectValue("titular", "field.suplente.required", new Object[] {new Integer(Constantes.CLEM_LONGITUD_TITULAR_SUP)},"");
					}else if(clemBean.getSuplente().length() > Constantes.CLEM_LONGITUD_TITULAR_SUP ){
						log.debug("Error de CLEM_LONGITUD_SUPLENTE");
						errors.rejectValue("titular", "field.clem.suplente", new Object[] {new Integer(Constantes.CLEM_LONGITUD_TITULAR_SUP)},"");
					}

				}
				
			}
						
			if(clemBean.getLugarFechaExpedicion().length() > Constantes.CLEM_LONGITUD_DESC_LUGARFECHA ){
				log.debug("Error de CLEM_LONGITUD_DESC_LUGARFECHA");
				errors.rejectValue("lugarFechaExpedicion", "field.clem.lugarfecha", new Object[] {new Integer(Constantes.CLEM_LONGITUD_DESC_LUGARFECHA)},"");
			}

			if(clemBean.getMotivos().length() > Constantes.CLEM_LONGITUD_MOTIVOS ){
				log.debug("Error de CLEM_LONGITUD_MOTIVOS");
				errors.rejectValue("motivos", "field.clem.motivos", new Object[] {new Integer(clemBean.getMotivos().length())}, "");
			}

			if(clemBean.getPuesto().length() > Constantes.CLEM_LONGITUD_PUESTO ){
				log.debug("Error de CLEM_LONGITUD_PUESTO");
				errors.rejectValue("puesto", "field.clem.puesto", new Object[] {new Integer(clemBean.getPuesto().length())}, "");
			}

		} else {
			log.debug("SI SE ENCONTRARON ERRORES REQUERIDOS");
		}
		
	}
	
}