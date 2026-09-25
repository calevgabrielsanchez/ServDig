package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

@Remote
public interface BajaDerechoabientePensionesRemote {

	List<Long> findBajasActivasBeneficiario(Long idAsignacionNSS, Long idIntegrante) throws DerechohabientesBusinessException;
	GrupoFamiliar validarBajaDerechohabientes(Long idAsignacionNSS, Long idIntegrante, Long tipoBaja) throws DerechohabientesBusinessException;
	Solicitud finalizarSolicitudBaja(Long idAsignacionNSS, Long idIntegrante, Long tipoBaja, String usuario,String observaciones, Date fechaDefuncion) throws DerechohabientesBusinessException;
}
