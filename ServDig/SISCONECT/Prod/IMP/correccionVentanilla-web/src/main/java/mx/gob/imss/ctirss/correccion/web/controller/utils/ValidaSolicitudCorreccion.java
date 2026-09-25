package mx.gob.imss.ctirss.correccion.web.controller.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat;
import mx.gob.imss.ctirss.correccion.prorroga.service.interfaces.ProrrogaService;

@Component
public class ValidaSolicitudCorreccion {
	
	@Autowired
	private ProrrogaService<CrtAnexosolcorrpat> prorrogaServiceBean;

	public  String estadoActualSolicitud(String folioCorreccion){
		CrtAnexosolcorrpat anexo = new CrtAnexosolcorrpat();
		anexo.setNuFolio(folioCorreccion);
		
		CrtAnexosolcorrpat resultado  = prorrogaServiceBean.consultarPorFolio(anexo);
		
		if(resultado!=null && resultado.getEstadoFolioCorr()==1)return "La Solicitud de la Correcion No ha sido aceptada";
		else if(resultado!=null && resultado.getEstadoFolioCorr()==4) return "La Solicitud de la Correcion ya ha sido Presentada";
		else if(resultado==null) return "El folio ingresado no existe";
		
		return "";
	}
}
