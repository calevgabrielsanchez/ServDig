package mx.gob.imss.cit.dacvass.servicios.externos.service.business;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.clasificacion.ActualizacionClasificaionPatronalBdtuSindoDto;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IPatronServiciosDigitalesServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IServiciosBackEndOriginalServiciosDIigiatlesRemote;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sindo.MovimientoPatronalType;

@Stateless(name = "serviciosBackEndOriginalService", mappedName = "serviciosBackEndOriginalService")
public class ServiciosBackEndOriginalServiciosDIigiatlesService extends AbstractServiceBusiness 
									implements IServiciosBackEndOriginalServiciosDIigiatlesRemote{
	private static final Logger log = LoggerFactory
			.getLogger(ServiciosBackEndOriginalServiciosDIigiatlesService.class);

	@EJB(mappedName="patronServiciosDigitalesService")
	IPatronServiciosDigitalesServiceRemote patronServiciosDigitalesService;
	
	/**
	 * Metodo encargado de recuperar la infomración detallada de un patron a traves de su NRP
	 * @param nrp String con el NRP de 10 a 11 posiciónes
	 * @return
	 * @throws ServiciosRestException
	 */

	@Override
	public SujetoObligado consultaDetallePatronSujetoObligadoByRP(String nrp) throws ServiciosRestException {
		log.debug("llegue a la llamada de consulta patron");
		return patronServiciosDigitalesService.consultaDetallePatronSujetoObligadoByRP(nrp);
	}


	/**
	 * Metodo encargado de encolar el movimiento que se va a SINDO por servicios digitales
	 * @param movimiento MovimientoPatronalType
	 * @throws ServiciosRestException
	 */
	@Override
	public void encolaMomvimientoModificacionPatronalSINDO(MovimientoPatronalType movimiento)
			throws ServiciosRestException {
		log.debug("llegue a la llamada del patron en servicio encola movimiento");
		patronServiciosDigitalesService.encolaMomvimientoModificacionPatronalSINDO(movimiento);
	}

	/**
	 * Metodo encargado de guardar el cambio de clasificación en bdtu y generar el movimiento para SINDO
	 * @param movimientoCalsificacion
	 * @throws ServiciosRestException
	 */
	@Override
	public String actualizarClasidifacionFuentesBdtuSINDO(
			ActualizacionClasificaionPatronalBdtuSindoDto movimientoCalsificacion) throws ServiciosRestException{
		log.debug("llegue a la llamada del actualizarClasidifacionFuentesBdtuSINDO");
		return patronServiciosDigitalesService.actualizarClasidifacionFuentesBdtuSINDO(movimientoCalsificacion);
	}

}
