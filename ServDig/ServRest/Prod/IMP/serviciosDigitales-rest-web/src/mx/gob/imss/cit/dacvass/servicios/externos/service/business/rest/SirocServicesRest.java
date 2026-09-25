package mx.gob.imss.cit.dacvass.servicios.externos.service.business.rest;

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

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.ErrorResponseBean;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.Page;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.ConsultaModel;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.ActualizarObraInputSiroc;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.AvisoUbicacionObra;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.AvisoUbicacionObraDetalle;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.ConsultaObraDetalle;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.DetalleRegistroObra;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.ObraGeneralExtPrto;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.ObraSirocInput;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.ObraSirocOutput;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.ObrasSimilaresInputSiroc;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.RegistroObra;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.RegistroObraDetalle;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.SirocOutput;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.ISirocServiciosDigitalesServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.rest.util.ComportamientosComunesUtil;
import mx.gob.imss.cit.dacvass.servicios.rest.util.EjbLocator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Path("v1/siroc")
public class SirocServicesRest {

	private static Logger log = LoggerFactory.getLogger(SirocServicesRest.class);
	
	private ISirocServiciosDigitalesServiceRemote sirocServiciosDigitalesService;
	
	public SirocServicesRest() {
		this.getEjbSirocService();
	}
	
	
	@GET
	@Path("/avisoUbicacionObra/delegacion/{cveIdDelegacion}/subDelegacion/{cveIdSubDelegacion}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<AvisoUbicacionObra> getConsultaAvisosUbicacionObraByDelegSubdeleg(@PathParam("cveIdDelegacion")Long cveIdDelegacion,
			@PathParam("cveIdSubDelegacion")Long cveIdSubDelegacion) throws ServiciosRestException {
		try {
			return sirocServiciosDigitalesService.consultaAvisoRegistroObraByDelegSubDel(cveIdDelegacion, cveIdSubDelegacion);
		}catch(ServiciosRestException ex) {
			log.error("ocurrio un error al consultar el aviso registro de obra por subdelegacion",  ex.getMessage() );
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("Ocurrio un error al inesperado al consultar el aviso registro de obra por subdelegacion" ,  e.getMessage());
			ServiciosRestException ex =  new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un erro inesperado al consutar el aviso de registor de obra por subdelegacion", e.getMessage()), e);
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	
	

	@GET
	@Path("/avisoUbicacionObra/detalle/numAviso/{numeroAvisoUbicacionObra}")
	@Produces({ MediaType.APPLICATION_JSON })
	public AvisoUbicacionObraDetalle getConsultaDetalleAvisosUbicacionObraByNumAviso(
			@PathParam("numeroAvisoUbicacionObra") String numeroAvisoUbicacionObra)
			throws ServiciosRestException {
		try {
			return sirocServiciosDigitalesService.getAvisoRegistroObra(numeroAvisoUbicacionObra);
		}catch(ServiciosRestException ex) {
			log.error("ocurrio un error al consultar el aviso registro de obra por ID" + numeroAvisoUbicacionObra ,  ex.getMessage() );
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("Ocurrio un error al inseperado al consultar el aviso registro de obra por ID" ,  e.getMessage());
			ServiciosRestException ex =  new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un erro inesperado al consutar el aviso de registor de obra por ID ", e.getMessage()), e);
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}

	@GET
	@Path("/registroObra/codigoPostal/{codigoPostal}/colonia/{colonia}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<RegistroObra> getConsultaObraByColoniaCp(@PathParam("codigoPostal")String codigoPostal,
			@PathParam("colonia") String colonia)	throws ServiciosRestException {
		try {
			return sirocServiciosDigitalesService.consultaRegistroObraByCPColonia(codigoPostal, colonia);
		}catch(ServiciosRestException ex) {
			log.error("ocurrio un error al consultar los registro de obra por codigo postal" + codigoPostal ,  ex.getMessage() );
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("Ocurrio un error al inseperado al consultar los registro de obra por codigo postal" ,  e.getMessage());
			ServiciosRestException ex =  new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un erro inesperado  al consultar los registro de obra por codigo postal ", e.getMessage()), e);
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}

	
	@GET
	@Path("/registroObra/detalleRegistro/{numRegistroObra}")
	@Produces({ MediaType.APPLICATION_JSON })
	public DetalleRegistroObra getRegistroObraByNumRegistro(@PathParam("numRegistroObra")String numRegistroObra) throws ServiciosRestException {
		try {
			return sirocServiciosDigitalesService.getRegistroObraByNumRegistro(numRegistroObra);
		}catch(ServiciosRestException ex) {
			log.error("ocurrio un error al consultar el registro de obra",  ex.getMessage() );
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("Ocurrio un error al quere recuperar el EJB" ,  e.getMessage());
			ServiciosRestException ex =  new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un erro inesperado al consutar el registor de obra ", e.getMessage()), e);
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/registroObra/detalleObraPRTO/{numObra}")
	@Produces({ MediaType.APPLICATION_JSON })
	public ObraGeneralExtPrto detalleObraPRTO(
			@PathParam("numObra") String numObra) {
		try {
			return sirocServiciosDigitalesService.detalleObraPRTO(numObra);
		} catch (ServiciosRestException ex) {
			log.error("ocurrio un error al consultar el registro de obra",
					ex.getMessage());
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		} catch (Exception e) {
			log.error("Ocurrio un error al quere recuperar el EJB",
					e.getMessage());
			ServiciosRestException ex = new ServiciosRestException(
					new ErrorResponseBean(
							ErrorResponseBean.codigo500,
							ErrorResponseBean.codigo500Descripcion,
							"ocurrio un erro inesperado al consutar el registor de obra ",
							e.getMessage()), e);
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/registroObra/tipoPatron")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<ConsultaModel>tipoPatron() {
		try {
			return sirocServiciosDigitalesService.tipoPatron();
		} catch (ServiciosRestException ex) {
			log.error("ocurrio un error al consultar el registro de obra",
					ex.getMessage());
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		} catch (Exception e) {
			log.error("Ocurrio un error al quere recuperar el EJB",
					e.getMessage());
			ServiciosRestException ex = new ServiciosRestException(
					new ErrorResponseBean(
							ErrorResponseBean.codigo500,
							ErrorResponseBean.codigo500Descripcion,
							"ocurrio un erro inesperado al consutar el registor de obra ",
							e.getMessage()), e);
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/registroObra/estatusObra")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<ConsultaModel>estatusObra() {
		try {
			return sirocServiciosDigitalesService.estatusObra();
		} catch (ServiciosRestException ex) {
			log.error("ocurrio un error al consultar el estatus de la Obra",
					ex.getMessage());
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		} catch (Exception e) {
			log.error("Ocurrio un error al quere recuperar el EJB del estatus de la obra",
					e.getMessage());
			ServiciosRestException ex = new ServiciosRestException(
					new ErrorResponseBean(
							ErrorResponseBean.codigo500,
							ErrorResponseBean.codigo500Descripcion,
							"ocurrio un erro inesperado al consutar el estatus de la Obra ",
							e.getMessage()), e);
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/registroObra/tiposIncidencia")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<ConsultaModel>rocTipoIncidencia() {
		try {
			return sirocServiciosDigitalesService.rocTipoIncidencia();
		} catch (ServiciosRestException ex) {
			log.error("ocurrio un error al consultar la Tipo Incidencia()",
					ex.getMessage());
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		} catch (Exception e) {
			log.error("Ocurrio un error al quere recuperar el EJB de rocTipoIncidencia",
					e.getMessage());
			ServiciosRestException ex = new ServiciosRestException(
					new ErrorResponseBean(
							ErrorResponseBean.codigo500,
							ErrorResponseBean.codigo500Descripcion,
							"ocurrio un erro inesperado al consutar rocTipoIncidencia ",
							e.getMessage()), e);
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	// Metodo para recuperar la obras
	@POST
	@Path("/registroObra/consultaObra")
	@Produces({ MediaType.APPLICATION_JSON })
	public Page<ConsultaObraDetalle> getConsultaObra(
			ObrasSimilaresInputSiroc<ObraSirocInput> datObra)
			throws ServiciosRestException {
		try {
			return sirocServiciosDigitalesService.getConsultaObra(datObra);
		} catch (Exception e) {
			log.error(
					"Ocurrio un error al inseperado al consultar los registro de obra",
					e.getMessage());
			ServiciosRestException ex = new ServiciosRestException(
					new ErrorResponseBean(
							ErrorResponseBean.codigo500,
							ErrorResponseBean.codigo500Descripcion,
							"ocurrio un erro inesperado  al consultar los registro de obra......",
							e.getMessage()), e);
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
		
	// Consulta para AVISO DE OBRAS Aviso de Ubicación de Obras
	@POST
	@Path("/registroObra/consultaAvisoUbicacionObra")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON })
	public Page<RegistroObraDetalle> consultaUbicacionObra(
			ObrasSimilaresInputSiroc<ObraSirocInput> input)
			throws ServiciosRestException {
		try {
			return sirocServiciosDigitalesService.consultaUbicacionObra(input);
		} catch (Exception e) {
			log.error(
					"Ocurrio un error al inseperado al consultar los registro de obra",
					e.getMessage());
			ServiciosRestException ex = new ServiciosRestException(
					new ErrorResponseBean(
							ErrorResponseBean.codigo500,
							ErrorResponseBean.codigo500Descripcion,
							"ocurrio un erro inesperado  al consultar los registro de obra......",
							e.getMessage()), e);
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@PUT
	@Path("/registroObra/actualizarObra")
	@Produces({ MediaType.APPLICATION_JSON })
	public ObraGeneralExtPrto actualizarObra(ActualizarObraInputSiroc input) {
		try {
			return sirocServiciosDigitalesService.actualizarObra(input);
		} catch (ServiciosRestException ex) {
			log.error("ocurrio un error al consultar el registro de obra",
					ex.getMessage());
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		} catch (Exception e) {
			log.error("Ocurrio un error al quere recuperar el EJB",
					e.getMessage());
			ServiciosRestException ex = new ServiciosRestException(
					new ErrorResponseBean(
							ErrorResponseBean.codigo500,
							ErrorResponseBean.codigo500Descripcion,
							"ocurrio un erro inesperado al consutar el registor de obra ",
							e.getMessage()), e);
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}	
	
	@POST
	@Path("/registroObra/obrasRegistradasPRTO")
	@Produces({ MediaType.APPLICATION_JSON })
	public Page<ObraGeneralExtPrto> obrasRegistradasPRTO(
			ObrasSimilaresInputSiroc<ObraGeneralExtPrto> input) {
		try {
			return sirocServiciosDigitalesService.obrasRegistradasPRTO(input);
		} catch (ServiciosRestException ex) {
			log.error("ocurrio un error al consultar el registro de obra",
					ex.getMessage());
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		} catch (Exception e) {
			log.error("Ocurrio un error al quere recuperar el EJB",
					e.getMessage());
			ServiciosRestException ex = new ServiciosRestException(
					new ErrorResponseBean(
							ErrorResponseBean.codigo500,
							ErrorResponseBean.codigo500Descripcion,
							"ocurrio un erro inesperado al consutar el registor de obra ",
							e.getMessage()), e);
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@POST
	@Path("/registroObra/obrasSimilaresCP")
	@Produces({ MediaType.APPLICATION_JSON })
	public Page<ObraGeneralExtPrto> obrasSimilares(
			ObrasSimilaresInputSiroc<ObraGeneralExtPrto> input) {
		try {
			return sirocServiciosDigitalesService.obrasSimilaresCP(input);
		} catch (ServiciosRestException ex) {
			log.error("ocurrio un error al consultar el registro de obra",
					ex.getMessage());
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		} catch (Exception e) {
			log.error("Ocurrio un error al quere recuperar el EJB",
					e.getMessage());
			ServiciosRestException ex = new ServiciosRestException(
					new ErrorResponseBean(
							ErrorResponseBean.codigo500,
							ErrorResponseBean.codigo500Descripcion,
							"ocurrio un erro inesperado al consutar el registor de obra ",
							e.getMessage()), e);
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@POST
	@Path("/registroObra/obrasSimilaresCPColonia")
	@Produces({ MediaType.APPLICATION_JSON })
	public Page<ObraGeneralExtPrto> obrasSimilaresCpColonia(
			ObrasSimilaresInputSiroc<ObraGeneralExtPrto> input) {
		try {
			return sirocServiciosDigitalesService
					.obrasSimilaresCPColonia(input);
		} catch (ServiciosRestException ex) {
			log.error("ocurrio un error al consultar el registro de obra",
					ex.getMessage());
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		} catch (Exception e) {
			log.error("Ocurrio un error al quere recuperar el EJB",
					e.getMessage());
			ServiciosRestException ex = new ServiciosRestException(
					new ErrorResponseBean(
							ErrorResponseBean.codigo500,
							ErrorResponseBean.codigo500Descripcion,
							"ocurrio un erro inesperado al consutar el registor de obra ",
							e.getMessage()), e);
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	private void getEjbSirocService() {
		try {
			log.debug("llegue a instanciar el EJB");
			sirocServiciosDigitalesService = EjbLocator.getServiciosSirocService();
			System.out.println("sali instanciar el EJB");
			}catch (Exception e) {
				System.out.println("Ocurrio un error al quere recuperar el EJB" +  e.getMessage());
				throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
			}
		}
	
	@GET
	@Path("/registroObra/consultaDetalleObra/{numObra}")
	@Produces({ MediaType.APPLICATION_JSON })
	public SirocOutput rocDetalleObra(@PathParam("numObra") String numObra) {
		try {
			return sirocServiciosDigitalesService.rocDetalleObra(numObra);
		} catch (ServiciosRestException ex) {
			log.error("ocurrio un error al consultar el detalle de la obra rocDetalleObra()",
					ex.getMessage());
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		} catch (Exception e) {
			log.error("Ocurrio un error al quere recuperar el EJB de rocDetalleObra",
					e.getMessage());
			ServiciosRestException ex = new ServiciosRestException(
					new ErrorResponseBean(
							ErrorResponseBean.codigo500,
							ErrorResponseBean.codigo500Descripcion,
							"ocurrio un erro inesperado al consutar rocDetalleObra ",
							e.getMessage()), e);
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	// Consulta las obras hijas relacionadas  por RegistroObra
		@POST
		@Path("/registroObra/getObrasHijasByRegObra")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces({ MediaType.APPLICATION_JSON })
		public List<ObraSirocOutput> getObrasHijasByRegObra(
				List<String> input)
				throws ServiciosRestException {
			try {
				return sirocServiciosDigitalesService.getObrasHijasByRegObra(input);
			} catch (Exception e) {
				log.error(
						"Ocurrio un error al inseperado al consultar las obras hijas",
						e.getMessage());
				ServiciosRestException ex = new ServiciosRestException(
						new ErrorResponseBean(
								ErrorResponseBean.codigo500,
								ErrorResponseBean.codigo500Descripcion,
								"ocurrio un erro inesperado  al consultar los registro con las obras hijas......",
								e.getMessage()), e);
				throw ComportamientosComunesUtil.getWebApplicationException(ex);
			}
		}
	
}
