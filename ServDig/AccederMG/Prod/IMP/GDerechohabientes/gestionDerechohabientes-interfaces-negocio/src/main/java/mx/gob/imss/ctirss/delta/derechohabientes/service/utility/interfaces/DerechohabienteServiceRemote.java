package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;


@Remote
public interface DerechohabienteServiceRemote {
	
	GrupoFamiliar detalleDerechohabienteGrupoFamiliar(Long idAsignacionNss, Long idPersona) throws DerechohabientesBusinessException;
	TramiteRegistroDerechohabiente getRegistroDerechohabiente(Long idTramite) throws DerechohabientesBusinessException, Exception;
	Derechohabiente getDerechohabiente(Long idPersona) throws DerechohabientesBusinessException, Exception;
	void actualizaDerechoabiente(Derechohabiente derechohabiente) throws DerechohabientesBusinessException, Exception;
	void updateResultadoCuestionario(TramiteRegistroDerechohabiente registro) throws DerechohabientesBusinessException, Exception; 
	
	/**
	 * Valida si el derechohabiente esta en circunscripci&oacute;n for&aacute;nea
	 * 
	 * @param derechohabiente Derechohabiente
	 * @return true si esta en circunscripci&oacute;n for&aacute;nea
	 */
	public boolean tieneCircunscripcionForeanea(Derechohabiente derechohabiente);
}
