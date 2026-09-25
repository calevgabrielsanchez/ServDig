package mx.gob.imss.cit.cda.web.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import mx.gob.imss.cit.cda.web.vo.DatosHistoriaLaboralVO;
import mx.gob.imss.cit.cda.web.vo.NSSVO;
import mx.gob.imss.ctirss.delta.framework.base.validator.AbstractValidator;

@Component
public class DatosHistoriaLaboralValidator extends AbstractValidator implements
		Validator {

	private static final Logger log = LoggerFactory.getLogger(DatosHistoriaLaboralValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return DatosHistoriaLaboralVO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
            DatosHistoriaLaboralVO datosHistoriaLaboralVO = (DatosHistoriaLaboralVO) target;

            log.debug("##### Entrando Validator #####");
            if (datosHistoriaLaboralVO.getDocumentoProbatorioList()== null || datosHistoriaLaboralVO.getDocumentoProbatorioList().isEmpty()) {
                errors.rejectValue("documentoProbatorioList", "field.documentoProbatorio.listaVacia");
            }
	}
        
        public void validarNssList(Object target, Errors errors){
            DatosHistoriaLaboralVO datosHistoriaLaboralVO = (DatosHistoriaLaboralVO) target;
            log.debug("##### Entrando Validator Documentos Probatorios #####");
            if (datosHistoriaLaboralVO == null || (datosHistoriaLaboralVO.getNSSList() == null || datosHistoriaLaboralVO.getNSSList().isEmpty())) {
                errors.rejectValue("NSSList", "field.NSS.listaVacia");
            }else{
                for(NSSVO nss:datosHistoriaLaboralVO.getNSSList()){
                    if (nss.getDocumentoProbatorioList() == null || nss.getDocumentoProbatorioList().isEmpty()) {
                        log.debug("Error en el NSS {}",nss);
                        errors.rejectValue("documentoProbatorioList", "field.documentoProbatorio.listaVacia");
                        break;
                    }
                }
            }
        }


}

