package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.model.derechohabiente.EstadoAcuerdoDh;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAcuerdoDh;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoAcuerdoDH;
import mx.gob.imss.ctirss.delta.persistence.DitAcuerdoDH;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionNss;
import mx.gob.imss.ctirss.delta.persistence.DitGrupoFamiliar;
import mx.gob.imss.ctirss.delta.persistence.DitGrupoFamiliarPK;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;

@Stateless(name = "acuerdoDhParserService", mappedName = "acuerdoDhParserService")
public class AcuerdoParserService implements AcuerdoParserServiceLocal {

	@Override
	public TramiteAcuerdoDh convertEntityToModel(DitAcuerdoDH ditAcuerdo) {
		TramiteAcuerdoDh tramiteAcuerdo = null;
		
		if(ditAcuerdo != null) {
			DitTramite ditTramite = ditAcuerdo.getDitTramite();
			
			tramiteAcuerdo = new TramiteAcuerdoDh();
			tramiteAcuerdo.setTramiteId(ditTramite.getCveIdTramite());
			tramiteAcuerdo.setEstadoAcuerdoDh(convertEstadoEntityToModel(ditAcuerdo.getDicEstadoAcuerdoDH()));
			tramiteAcuerdo.setFechaAcuerdo(ditAcuerdo.getFecInicioAcuerdo());
			tramiteAcuerdo.setFechaFinAcuerdo(ditAcuerdo.getFecFinAcuerdo());
			tramiteAcuerdo.setFechaTramite(ditAcuerdo.getFecRegistroAlta());
			tramiteAcuerdo.setNumeroAcuerdo(ditAcuerdo.getRefNumAcuerdoDH());
			
		}
		
		return tramiteAcuerdo;
	}

	@Override
	public DitAcuerdoDH converModelToEntity(TramiteAcuerdoDh tramite) {
		DitAcuerdoDH ditAcuerdoDH = new DitAcuerdoDH();
		Long idPersona = tramite.getPersona().getIdPersona();
		Long idAsignacionNSS = tramite.getIdAsignacionNSS();
			
		DitGrupoFamiliar ditGrupoFamiliar = new DitGrupoFamiliar();
		ditGrupoFamiliar.setDitPersona(new DitPersona(idPersona));
		ditGrupoFamiliar.setDitAsignacionNss(new DitAsignacionNss(idAsignacionNSS));
		ditGrupoFamiliar.setId(new DitGrupoFamiliarPK(idAsignacionNSS,idPersona));
		
		ditAcuerdoDH.setDitGrupoFamiliar(ditGrupoFamiliar);
		ditAcuerdoDH.setFecInicioAcuerdo(tramite.getFechaAcuerdo());
		ditAcuerdoDH.setRefNumAcuerdoDH(tramite.getNumeroAcuerdo());
		ditAcuerdoDH.setDitTramite(new DitTramite(tramite.getTramiteId()));
		ditAcuerdoDH.setFecRegistroAlta(tramite.getFechaTramite());
		ditAcuerdoDH.setRefObservacion(tramite.getObservacion());
		
		if(tramite.getEstadoAcuerdoDh() != null) {
			ditAcuerdoDH.setDicEstadoAcuerdoDH(new DicEstadoAcuerdoDH(tramite.getEstadoAcuerdoDh().getId()));
		}
		
		return ditAcuerdoDH;
	}
	
	private EstadoAcuerdoDh convertEstadoEntityToModel(DicEstadoAcuerdoDH dicEstadoAcuerdoDh) {
		
		EstadoAcuerdoDh estadoAcuerdo = null;
		if(dicEstadoAcuerdoDh != null) {
			estadoAcuerdo = new EstadoAcuerdoDh(dicEstadoAcuerdoDh.getCveIdEstadoAcuerdoDH(), dicEstadoAcuerdoDh.getDesEstadoAuerdo());
		}
		
		return estadoAcuerdo;
	}

}
