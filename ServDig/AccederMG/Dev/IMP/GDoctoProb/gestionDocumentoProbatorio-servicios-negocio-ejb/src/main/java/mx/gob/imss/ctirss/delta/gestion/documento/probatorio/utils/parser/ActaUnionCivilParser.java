package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser;


import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.ActaUnionCivil;
import mx.gob.imss.ctirss.delta.persistence.DgCatEstado;
import mx.gob.imss.ctirss.delta.persistence.DicAutoridadEmisora;
import mx.gob.imss.ctirss.delta.persistence.DitActaUnionCivil;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.AutoridadEmisora;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;

public class ActaUnionCivilParser {

	public static DitActaUnionCivil modelToPersist(ActaUnionCivil entrada) {
		DitActaUnionCivil salida = null;
		
		if(entrada != null) {
			salida = new DitActaUnionCivil();
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
	
	public static ActaUnionCivil persistToModel(DitActaUnionCivil entrada) {
		ActaUnionCivil salida = null;
		
		if(entrada != null) {
			salida =new ActaUnionCivil();
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
