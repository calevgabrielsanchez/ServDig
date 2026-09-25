package mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.clasificacion.ActualizacionClasificaionPatronalBdtuSindoDto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sindo.MovimientoPatronalType;

@Remote
public interface IServiciosBackEndOriginalServiciosDIigiatlesRemote {

	/**
	 * Metodo temporal encargado de encolar el movimiento que se va a SINDO por servicios digitales
	 * @param movimiento MovimientoPatronalType
	 * @throws ServiciosRestException
	 */
	void encolaMomvimientoModificacionPatronalSINDO(MovimientoPatronalType movimiento) throws ServiciosRestException;
	
	/**
	 * Metodo temporal encargado de guardar el cambio de clasificación en bdtu y generar el movimiento para SINDO
	 * @param movimientoCalsificacion
	 * @throws ServiciosRestException
	 */
	String actualizarClasidifacionFuentesBdtuSINDO(
			ActualizacionClasificaionPatronalBdtuSindoDto movimientoCalsificacion) throws ServiciosRestException;
	
	/**
	 * Metodo encargado de recuperar la infomración detallada de un patron a traves de su NRP
	 * @param nrp String con el NRP de 10 a 11 posiciónes
	 * @return
	 * @throws ServiciosRestException
	 */
	SujetoObligado consultaDetallePatronSujetoObligadoByRP(String nrp) throws ServiciosRestException;
	
}
