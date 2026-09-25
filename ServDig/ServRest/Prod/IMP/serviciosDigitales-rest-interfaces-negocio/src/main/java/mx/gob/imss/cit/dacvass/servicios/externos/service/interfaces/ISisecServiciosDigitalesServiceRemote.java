package mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.sisec.ResumenAseguradoTramiteCda;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.sisec.TramitesAbiertosAseguradoSisecResponse;
import mx.gob.imss.cit.semanascotizadas.aclaracion.model.DatosRelacionLaboralReconocidaModel;
import mx.gob.imss.cit.semanascotizadas.common.model.DatosHuelga;

@Remote
public interface ISisecServiciosDigitalesServiceRemote {
	
	/**
	 * Metodo que consulta la información de los tramites de CDA asociados a un asegurado
	 * @param refCurp
	 * @param nss
	 * @return
	 * @throws ServiciosRestException
	 */
	List<ResumenAseguradoTramiteCda> getResumenAseguradoTramiteCda(String refCurp, String nss)throws  ServiciosRestException;
	
	/**Metodo que recupera las movimientos reconocidos de semanas cotizadas por tipo de tramite
	 * tipoTrmite
	 * 1=aclarcion
	 * 2=portalibilidad IMSS - ISTEE
	 * 3=captura preventiba
	 * @param nss
	 * @param tipoTramite
	 * @return ResumenAseguradoTramiteCda
	 * @throws ServiciosRestException
	 */
	List<DatosRelacionLaboralReconocidaModel> getPeriodosAprobadosByTipoTramiteSisec(String nss, String refCurp, Long tipoTramite) throws  ServiciosRestException;
	
	
	
	/**
	 * Metodo que consulta si tiene tramites abiertos en SISEC estado 1
	 * @param nss
	 * @return
	 * @throws ServiciosRestException
	 */
	TramitesAbiertosAseguradoSisecResponse validaTramiteSisecAseguradoByEstado(String nss, Long estadoTramite) throws ServiciosRestException;

}
