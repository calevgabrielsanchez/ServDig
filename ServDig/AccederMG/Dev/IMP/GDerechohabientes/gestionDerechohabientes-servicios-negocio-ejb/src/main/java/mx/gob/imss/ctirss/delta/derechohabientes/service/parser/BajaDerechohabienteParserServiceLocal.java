package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.BajaDerechohabienteDto;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteBajaDerechohabiente;
import mx.gob.imss.ctirss.delta.persistence.DitBajaDerechohabiente;

@Local
public interface BajaDerechohabienteParserServiceLocal {
	
	BajaDerechohabienteDto convertirTramiteBajaToBaja(TramiteBajaDerechohabiente tramiteBaja, Long bajaActiva);
	BajaDerechohabienteDto convertirEntityToBajaDto(DitBajaDerechohabiente entity);
	DitBajaDerechohabiente convertirBajadtoToEntity(BajaDerechohabienteDto dto);
	DitBajaDerechohabiente convertirTramiteBajaToEntity(TramiteBajaDerechohabiente tramiteBaja, Long bajaActiva);
}
