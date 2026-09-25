package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;


import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.SolicitudNssDto;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.SolicitudesAtendidasDto;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;


/**
 * @author Juan Manuel Marquez Hernandez
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 02/01/2013
 */

@Remote
public interface BitacoraTramiteServiceRemote {
	
	public DatosSalidaPaginador<SolicitudNssDto> listSolicitudesAtendidas(SolicitudesAtendidasDto solicitudesDto) throws DerechohabientesBusinessException, Exception;
	public void insertBitacoraSegTramite(Tramite tramite) throws Exception;
}
