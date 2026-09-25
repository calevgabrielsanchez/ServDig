package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.List;

import javax.ejb.Local;
import javax.persistence.TransactionRequiredException;

import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.BajaDerechohabienteDto;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

@Local
public interface BajaDerechohabienteServiceLocal {
	
	Solicitud finalizarSolicitudBaja(Solicitud solicitud) throws SolicitudNoValidaException, SolicitudNoEncontradaException, SolicitudException, ImpactaAlmacenesWSException;
	List<BajaDerechohabienteDto> getBajaDerechohabiente(Long idAsignasionNss, List<Long> idPersonas, List<Long> tiposBaja, Boolean activa);
	Solicitud guardarSolicitudBaja(GrupoFamiliar derechohabiente, TipoTramiteEnum tipo,Usuario usuario,AsignacionNSS nss, OrigenSolicitudEnum origen) throws Exception;
	void actualizarInsertarBaja(BajaDerechohabienteDto bajaDto) throws IllegalArgumentException, TransactionRequiredException;
	List<GrupoFamiliar> quitarIntegrantesConTramiteBaja(List<GrupoFamiliar> grupoFamiliar, List<Long> tiposBaja);
	Solicitud finalizarBajaSinConsultaSolicitud(Solicitud solicitud) throws SolicitudNoValidaException, SolicitudNoEncontradaException, SolicitudException, ImpactaAlmacenesWSException;
	
}
