package mx.gob.imss.cit.dacvass.servicios.externos.service.util;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.ErrorResponseBean;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

@Resource
public class ParserPatronServiciosToRest {

	private static final Logger LOG = LoggerFactory
            .getLogger(ParserCatalogosServiciosToRest.class);
	
	public static void toNullInfoPatronServiciosRestFolioPAC(SujetoObligado sujeto) throws ServiciosRestException {
		
		try {
			ParserPatronServiciosToRest.toNullInfoDetalleClasiificacion(sujeto);
		}catch(Exception e) {
			LOG.error("ocuriro un error al parsera la patron en copy properties del bean", e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error al parserar la informcion del patron", e.getMessage()));
		}
	}
	
	private static void toNullInfoDetalleClasiificacion(SujetoObligado sujeto) throws ServiciosRestException {
		
		try {
			sujeto.setProceso(null);
			sujeto.setProductos(null);
			sujeto.setPersonal(null);
			sujeto.setMateriaPrimaMateriales(null);
			sujeto.setEscrituraConstitutiva(null);
			sujeto.setEquiposTransporte(null);
			sujeto.setEquipos(null);
			
		}catch(Exception e) {
			LOG.error("ocuriro un error al parsera la patron en copy properties del bean", e);
			throw new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"Ocurrio un error al parserar la informcion del patron", e.getMessage()));
		}
			
		
	}
	
}
