package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteReactivacionDerechohab;
import mx.gob.imss.ctirss.delta.persistence.DitReactivacionDerechohab;

@Stateless(name = "reactivacionParser", mappedName = "reactivacionParser")
public class ReactivacionParser implements ReactivacionParserLocal {

	@Override
	public DitReactivacionDerechohab tramiteReactivacionToDitReactivacion(
			TramiteReactivacionDerechohab tramite) {
		DitReactivacionDerechohab ditReactivacion = null;
		
		if(tramite != null) {
			ditReactivacion = new DitReactivacionDerechohab();
			ditReactivacion.setCveIdBaja(tramite.getIdBaja());
			ditReactivacion.setCveIdTramite(tramite.getTramiteId());
			ditReactivacion.setFecRegistroAlta(tramite.getFechaTramite());
			ditReactivacion.setFecRegistroActualizado(tramite.getFechaRegistroActualizacion());
			ditReactivacion.setFundamentoLegal(tramite.getFundamentoLegal());
			ditReactivacion.setMatricula(tramite.getMatricula());
			ditReactivacion.setMotivo(tramite.getMotivo());
		}
		
		return ditReactivacion;
	}

}
