package mx.gob.imss.ctirss.delta.derechohabientes.service.entity;

import java.util.List;

import javax.ejb.Local;
import javax.persistence.TransactionRequiredException;

import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.BajaDerechohabienteDto;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteBajaDerechohabiente;
import mx.gob.imss.ctirss.delta.persistence.DitBajaDerechohabiente;

@Local
public interface BajaDerechohabienteEntityLocal {

	List<BajaDerechohabienteDto> getBajaDerechohabiente(Long idAsignasionNss, List<Long> idPersonas, List<Long> tiposBaja, Boolean activa);
	BajaDerechohabienteDto getBajaDerechohabiente(Long cveIdBaja) throws Exception;
	BajaDerechohabienteDto insertFromTramiteBaja(TramiteBajaDerechohabiente tramiteBaja);
	void insert(DitBajaDerechohabiente entity)throws Exception;
	void saveOrUpdate( BajaDerechohabienteDto bajaDto ) throws IllegalArgumentException, TransactionRequiredException;
	boolean tieneBajaAdministrativaActiva(Long idAsignasionNss, Long idPersona);
	int tieneBajaPorSolicitud(Long idAsignasionNss, Long idPersona, int tipoBaja);
}
