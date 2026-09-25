package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.BajaDerechohabienteDto;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

@Local
public interface FinalizaSolicitudServiceLocal {
	
	void mandaMovimientoWS(GrupoFamiliar grupoFamiliar) throws 
	IllegalArgumentException, ImpactaAlmacenesWSException, Exception;
	
	void mandaMovimientosWS(List<GrupoFamiliar> candidatosCambio, Boolean cambioClinica) throws 
	IllegalArgumentException, ImpactaAlmacenesWSException, Exception;
	
	void finalizaSolicitudRegistroWeb(Solicitud solicitud, GrupoFamiliar grupoFamiliar) 
	throws IllegalArgumentException, ImpactaAlmacenesWSException, Exception;
	
	void finalizaSolicitudBajaDerechohabiente(Solicitud solicitud, BajaDerechohabienteDto baja, Boolean isNuevaBaja) throws 
	IllegalArgumentException, ImpactaAlmacenesWSException, Exception;
	 
	void finalizaCorreccionDatosDerechohabienteInternet(GrupoFamiliar grupoFamiliar) throws
	IllegalArgumentException, ImpactaAlmacenesWSException, Exception;
	
	void finalizaActualizacionDatosDerechohabienteAsincrono(GrupoFamiliar grupoFamiliar) throws 
	IllegalArgumentException, ImpactaAlmacenesWSException, Exception;

}