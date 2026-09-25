package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Articulo82;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Articulo83;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Articulo84;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;


@Remote
public interface DerechohabienteServiceRemote {
	
	GrupoFamiliar detalleDerechohabienteGrupoFamiliar(Long idAsignacionNss, Long idPersona) throws DerechohabientesBusinessException;
	TramiteRegistroDerechohabiente getRegistroDerechohabiente(Long idTramite) throws DerechohabientesBusinessException, Exception;
	Derechohabiente getDerechohabiente(Long idPersona) throws DerechohabientesBusinessException, Exception;
	List <Articulo82> getArticulo82 () throws DerechohabientesBusinessException, Exception;
	List <Articulo83> getArticulo83 (Integer tiempo) throws DerechohabientesBusinessException, Exception;
	List <Articulo84> getArticulo84 () throws DerechohabientesBusinessException, Exception;
	
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
