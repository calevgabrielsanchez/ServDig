package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser;


import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.ActaTerminoUnionCivil;
import mx.gob.imss.ctirss.delta.persistence.DgCatEstado;
import mx.gob.imss.ctirss.delta.persistence.DicAutoridadEmisora;
import mx.gob.imss.ctirss.delta.persistence.DitActaTerminoUnionCivil;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.AutoridadEmisora;

import org.apache.log4j.Logger;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;

public class ActaTerminoUnionCivilParser extends AbstractServiceBusiness {

	private static final Logger logger = Logger.getLogger(ActaTerminoUnionCivilParser.class);
	
	public static DitActaTerminoUnionCivil modelToPersist(ActaTerminoUnionCivil entrada) {
		DitActaTerminoUnionCivil salida = null;
		
		logger.debug("------- LOS DATOS DE ENTRADA EN TÉRMINO DE UNIÓN CIVIL FUERON" + entrada);		
		if(entrada != null) {
			salida = new DitActaTerminoUnionCivil();
			salida.setLugarEmision(entrada.getLugarEmision());
			salida.setFecEmision(entrada.getFechaEmision());
			DicAutoridadEmisora aut = new DicAutoridadEmisora ();
			aut.setCveIdAutoridadEmisora(new Long (entrada.getAutoridadEmisora().getCveIdAutoridadEmisora()));
			salida.setDicAutoridadEmisora(aut);
			DgCatEstado entidad = new DgCatEstado();
			entidad.setCveEnt(entrada.getEntidadFederativa().getClave());
			salida.setDgCatEstado(entidad);
			salida.setNoReferencia(entrada.getNoReferencia());
		}
		
		return salida;
	}
	
	public static ActaTerminoUnionCivil persistToModel(DitActaTerminoUnionCivil entrada) {
		ActaTerminoUnionCivil salida = null;
		
		if(entrada != null) {
			salida =new ActaTerminoUnionCivil();
			salida.setLugarEmision(entrada.getLugarEmision());
			salida.setFechaEmision(entrada.getFecEmision());
			AutoridadEmisora autoridad = new AutoridadEmisora();
			autoridad.setCveIdAutoridadEmisora(Long.toString(entrada.getDicAutoridadEmisora().getCveIdAutoridadEmisora()));
			salida.setAutoridadEmisora(autoridad);
			EntidadFederativa entidad = new EntidadFederativa();
			entidad.setClave(entrada.getDgCatEstado().getCveEnt());
			salida.setEntidadFederativa(entidad);
			salida.setIdDocumentoProbatorio(entrada.getDitDocumentoProbatorio()!=null ? 
					entrada.getDitDocumentoProbatorio().getCveIdDocumentoProbatorio().intValue():null);
			
		}
		
		return salida;
	}
}
