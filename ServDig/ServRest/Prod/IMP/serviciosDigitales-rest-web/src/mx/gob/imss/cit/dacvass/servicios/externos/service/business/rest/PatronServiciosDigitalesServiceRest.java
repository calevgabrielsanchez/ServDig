package mx.gob.imss.cit.dacvass.servicios.externos.service.business.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import mx.gob.imss.cit.dacvass.servicios.externos.model.consultas.dto.ConsultaPatron;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.ErrorResponseBean;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.clasificacion.ActividadEcononica;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.clasificacion.ActualizacionClasificaionPatronalBdtuSindoDto;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.DatosGeneralesPatron;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.DatosSatDetallePatron;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.DetallePatronClasifMovPatQuery;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.MovimientoRegistroPatronal;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.NumTrabajadoresVigentes;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron.PatronPlataformaResponse;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IPatronServiciosDigitalesServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IServiciosBackEndOriginalServiciosDIigiatlesRemote;
import mx.gob.imss.cit.dacvass.servicios.rest.util.ComportamientosComunesUtil;
import mx.gob.imss.cit.dacvass.servicios.rest.util.EjbLocator;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sindo.MovimientoPatronalType;

@Service
@Path("v1/patrones")
public class PatronServiciosDigitalesServiceRest {
	
	
	private static Logger log = LoggerFactory.getLogger(PatronServiciosDigitalesServiceRest.class);
	
	private IPatronServiciosDigitalesServiceRemote servicioPatron;
	
	private IServiciosBackEndOriginalServiciosDIigiatlesRemote serviciosBackEndOriginalService;
	
	private final Integer tamanioMaximoLista = new Integer(100);
	
	public PatronServiciosDigitalesServiceRest() {
		this.getEjbPatronService();
		//this.getEjbServiciosBackEndOriginalService();
	}
	
	
	
	
	@GET
	@Path("/{numNrp}")
	@Produces({ MediaType.APPLICATION_JSON })
	public SujetoObligado getPatronSujetoObligadoServiciosDigitales (@PathParam("numNrp") String numNrp) {
		try {
			return servicioPatron.consultaDetallePatronSujetoObligadoByRP(numNrp);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/pmc/{numNrp}")
	@Produces({ MediaType.APPLICATION_JSON })
	public SujetoObligado getPatronSujetoObligadoServiciosDigitalesPMC (@PathParam("numNrp") String numNrp) {
		try {
			return servicioPatron.consultaDetallePatronSujetoObligadoByRPPMC(numNrp);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@PUT
	@Path("/envioSINDO/encolaMovimiento/")
	@Consumes({ MediaType.APPLICATION_JSON })
	public void encolaMomvimientoModificacionPatronalSINDO (
			MovimientoPatronalType movimiento) {
		try {
			 log.debug("llegue a la llamada para encolar movimientos" + movimiento.getRfc() );
			 servicioPatron.encolaMomvimientoModificacionPatronalSINDO(movimiento);;
		}catch(ServiciosRestException ex) {
			log.error("ocurrio un error al encolar el movimiento" + ex.getMessage() );
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	
	@PUT
	@Path("/actualizarFuentes/clasificacion/bdtu/sindo")
	@Consumes({ MediaType.APPLICATION_JSON })
	public String actualizarClasidifacionFuentesBdtuSINDO(
			ActualizacionClasificaionPatronalBdtuSindoDto movimientoCalsificacion) {
		try {
			 log.debug("llegue a la llamada para actualizar la clasificación en BDTU" + movimientoCalsificacion.getCveUsuario());
			 return servicioPatron.actualizarClasidifacionFuentesBdtuSINDO(movimientoCalsificacion);
		}catch(ServiciosRestException ex) {
			log.error("ocurrio un error al actualizar la clasificación en BDTU codigo ", ex);
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}


	@PUT
	@Path("/baja/articulo251")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<MovimientoRegistroPatronal> consultaPatronBajaArt251 (List<String> listsRegPatronales) {
		try {
			return servicioPatron.consultaPatronBaja(listsRegPatronales, true);
		}catch(ServiciosRestException ex) {
			log.error("ocurrio un error al consultar el registro consultaPatronBajaArt251",  ex.getMessage() );
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("Ocurrio un error inesperado en consultaPatronBajaArt251" ,  e.getMessage());
			ServiciosRestException ex =  new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un erro inesperado al consutar patrones en baja 251", e.getMessage()), e);
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@PUT
	@Path("/baja")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<MovimientoRegistroPatronal> consultaPatronBaja (List<String> listsRegPatronales) {
		try {
			return servicioPatron.consultaPatronBaja(listsRegPatronales, false);
		}catch(ServiciosRestException ex) {
			log.error("ocurrio un error al consultar el registro consultaPatronBajaArt251",  ex.getMessage() );
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("Ocurrio un error inesperado en consultaPatronBajaArt251" ,  e.getMessage());
			ServiciosRestException ex =  new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un erro inesperado al consutar patrones en baja 251", e.getMessage()), e);
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/{numNrp}/actividadEconomica")
	@Produces({ MediaType.APPLICATION_JSON })
	public ActividadEcononica getActividadEconocimaByRegPatronal(@PathParam("numNrp") String numNrp) {
		try {
			return servicioPatron.getActividadEconocimaByRegPatronal(numNrp);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("Ocurrio un error inesperado en getActividadEconocimaByRegPatronal" ,  e.getMessage());
			ServiciosRestException ex =  new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un erro inesperado al consutar la actividad economica", e.getMessage()), e);
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/{numNrp}/trabajadoresVigentes")
	@Produces({ MediaType.APPLICATION_JSON })
	public NumTrabajadoresVigentes getTrabajadoresVigentesByRegPatronal(@PathParam("numNrp") String numNrp) {
		try {
			return servicioPatron.getTrabajadoresVigentesByRegPatronal(numNrp);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("Ocurrio un error inesperado en getTrabajadoresVigentesByRegPatronal" ,  e.getMessage());
			ServiciosRestException ex =  new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un erro inesperado al consutar los trabajadores vigentes", e.getMessage()), e);
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/{numNrp}/datosGenerales")
	@Produces({ MediaType.APPLICATION_JSON })
	public DatosGeneralesPatron getDatosGeneralesPatron(@PathParam("numNrp") String numNrp) {
		try {
			return servicioPatron.getDatosGeneralesPatron(numNrp);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("Ocurrio un error inesperado en getTrabajadoresVigentesByRegPatronal" ,  e.getMessage());
			ServiciosRestException ex =  new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un erro inesperado al consutar los datos generales del patron", e.getMessage()), e);
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	

	@PUT
	@Path("/list/datosGenerales")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<DatosGeneralesPatron> getDatosGeneralesPatronList(List<String> listsRegPatronales) {
		if(listsRegPatronales== null || listsRegPatronales.isEmpty()) {
			log.error("la lista llego nula o vacia");
			ServiciosRestException ex = new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
					"La lista de registros patronales no pueden ser nulo o vacia", "La lista de registros patronales no pueden ser nulo o vacia"));
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}	
		
		if(listsRegPatronales.size()> tamanioMaximoLista.intValue()) {
			ServiciosRestException ex = new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo400, ErrorResponseBean.codigo400Descripcion,
						"La lista de registros patronales exede el numero maximo de elmentos " + tamanioMaximoLista, 
						"La lista de registros patronales exede el numero maximo de elmentos " + tamanioMaximoLista));
				throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
		List<DatosGeneralesPatron> listDatosPatron = new ArrayList<DatosGeneralesPatron>();
		try {
			for(String nrp :listsRegPatronales) {
				try {
					listDatosPatron.add(servicioPatron.getDatosGeneralesPatron(nrp));
				}catch (ServiciosRestException e) {
					if(!e.getErrorBean().getCode().equals(ErrorResponseBean.codigo404))
						throw e;
				}
			}
			
			return listDatosPatron;
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("Ocurrio un error inesperado en getTrabajadoresVigentesByRegPatronal" ,  e.getMessage());
			ServiciosRestException ex =  new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un erro inesperado al consutar los datos generales del patron", e.getMessage()), e);
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/rfc/{rfc}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<String> getRegistrosPatronalesByRfc(@PathParam("rfc") String rfc) {
		try {
			return servicioPatron.getRegistrosPatronalesByRfc(rfc);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("Ocurrio un error inesperado en getRegistrosPatronalesByRfc" ,  e.getMessage());
			ServiciosRestException ex =  new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un erro inesperado al consutar los RP de un RFC", e.getMessage()), e);
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@POST
	@Path("/datosBasicosSat/listDetallePatron")
	@Produces({ MediaType.APPLICATION_JSON })
	public DatosSatDetallePatron getDatosSatDetallePatron(ConsultaPatron consultaPat) throws ServiciosRestException {
		log.debug("llegue al metodo de getDatosSatDetallePatron" + consultaPat);
		try {
			DatosSatDetallePatron detalle = servicioPatron.getDatosSatDetallePatron(consultaPat);
			//DatosSatDetallePatron detalle =  new DatosSatDetallePatron();
			//List<DetallePatronClasifMovPat> lstResp = new ArrayList<DetallePatronClasifMovPat>();
			return detalle;
			
		//}catch(ServiciosRestException ex) {
		//	throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("Ocurrio un error inesperado en al consultar datos Sat detalle patron" + consultaPat,  e.getMessage());
			ServiciosRestException ex =  new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un erro inesperado al consutar datos Sat detalle patron " + consultaPat , e.getMessage()), e);
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	
	
	
	@GET
	@Path("/esPatronPlataforma/{nrp}")
	@Produces({ MediaType.APPLICATION_JSON })
	public PatronPlataformaResponse validaPatronPlataforma(@PathParam("nrp") String nrp) {
		try {
			
			PatronPlataformaResponse response =servicioPatron.validaPatronPlataforma(nrp);
			log.debug("la respuesta es " + response.getCodigoRespuesta());
			return response;
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("Ocurrio un error inesperado en validaPatronPlataforma" ,  e.getMessage());
			ServiciosRestException ex =  new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un erro inesperado al consutar validaPatronPlataforma", e.getMessage()), e);
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}

	/**  metodo temporal para invocar al backend original
	@PUT
	@Path("/envioSINDO/encolaMovimiento/")
	@Consumes({ MediaType.APPLICATION_JSON })
	public void encolaMomvimientoModificacionPatronalSINDO (
			MovimientoPatronalType movimiento) {
		try {
			 log.debug("llegue a la llamada para encolar movimientos" + movimiento.getRfc() );
			 serviciosBackEndOriginalService.encolaMomvimientoModificacionPatronalSINDO(movimiento);;
		}catch(ServiciosRestException ex) {
			log.error("ocurrio un error al encolar el movimiento" + ex.getMessage() );
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	**/

	/** metodo temporal para invocar al backend original
	@PUT
	@Path("/actualizarFuentes/clasificacion/bdtu/sindo")
	@Consumes({ MediaType.APPLICATION_JSON })
	public String actualizarClasidifacionFuentesBdtuSINDO(
			ActualizacionClasificaionPatronalBdtuSindoDto movimientoCalsificacion) {
		try {
			 log.debug("llegue a la llamada para actualizar la clasificación en BDTU" + movimientoCalsificacion.getCveUsuario());
			 return serviciosBackEndOriginalService.actualizarClasidifacionFuentesBdtuSINDO(movimientoCalsificacion);
		}catch(ServiciosRestException ex) {
			log.error("ocurrio un error al actualizar la clasificación en BDTU codigo ", ex);
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	**/
	
	/**lock
	public void getEjbServiciosBackEndOriginalService() {
		try {
			log.debug("llegue a instanciar el EJB original");
			serviciosBackEndOriginalService = EjbLocator.getServiciosBackEndOriginalService();
			log.debug("sali instanciar el EJB");
			}catch (Exception e) {
				log.error("Ocurrio un error al quere recuperar el EJB de patrones original" +  e.getMessage());
				throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
			}
		}
	**/
	
	
	
	
	public void getEjbPatronService() {
		try {
			System.out.println("llegue a instanciar el EJB");
			servicioPatron = EjbLocator.getPatronDigitalService();
			System.out.println("sali instanciar el EJB");
			}catch (Exception e) {
				System.out.println("Ocurrio un error al quere recuperar el EJB" +  e.getMessage());
				throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
			}
		}
}
