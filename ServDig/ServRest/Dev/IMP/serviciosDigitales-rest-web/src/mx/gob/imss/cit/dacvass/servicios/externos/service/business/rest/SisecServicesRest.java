package mx.gob.imss.cit.dacvass.servicios.externos.service.business.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.ErrorResponseBean;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.sisec.ResumenAseguradoTramiteCda;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.sisec.TramitesAbiertosAseguradoSisecResponse;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IPatronServiciosDigitalesServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.ISisecServiciosDigitalesServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.rest.util.ComportamientosComunesUtil;
import mx.gob.imss.cit.dacvass.servicios.rest.util.EjbLocator;
import mx.gob.imss.cit.semanascotizadas.aclaracion.model.DatosRelacionLaboralReconocidaModel;
import mx.gob.imss.cit.semanascotizadas.common.model.DatosHuelga;

@Service
@Path("v1/sisec")
public class SisecServicesRest {
	
	private static Logger log = LoggerFactory.getLogger(SisecServicesRest.class);
	
	private ISisecServiciosDigitalesServiceRemote sisecServiciosDigitalesService;
	
	private IPatronServiciosDigitalesServiceRemote patronServiciosDigitalesService;
	
	public SisecServicesRest() {
		this.getEjbSisecService();
	}
	
	@GET
	@Path("/resumenTramite/cda/{nss}/{refCurp}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<ResumenAseguradoTramiteCda> getResumenAseguradoTramiteCda(@PathParam("nss")String nss,
			@PathParam("refCurp")String refCurp) {
		log.debug("llege a la consulta de resumen de tramites CDA con CURP " + refCurp );
		try {
			return sisecServiciosDigitalesService.getResumenAseguradoTramiteCda(refCurp, nss);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("ocurrio un error inesperado al consultar resumen de tramites CDA con CURP " + refCurp, e);
			ServiciosRestException ex = new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
						"ocurrio un error inesperado resumen de tramites CDA con CURP " + refCurp + e.getMessage() , e.getMessage()),e);
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
		
	}
	
	@GET
	@Path("/validaTramites/nss/{nss}/estadoTramite/{estadoTramite}")
	@Produces({ MediaType.APPLICATION_JSON })
	public TramitesAbiertosAseguradoSisecResponse validaTramiteSisecAseguradoByEstado(@PathParam("nss")String nss,
			@PathParam("estadoTramite")Long estadoTramite) throws ServiciosRestException {
		log.debug("llege a la consulta de validacion  de tramites SISEC con nss" + nss );
		
		try {
			return sisecServiciosDigitalesService.validaTramiteSisecAseguradoByEstado(nss, estadoTramite);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("ocurrio un error inesperado al consultar los tramites abiertos de SISEC" + nss, e);
			ServiciosRestException ex = new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
						"ocurrio un error inesperado al consultar los tramites abiertos de SISEC " +nss+ e.getMessage() , e.getMessage()),e);
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
		
		
		
	}
	
	@GET
	@Path("/patrones/{regPatron}/huelga")
	@Produces({ MediaType.APPLICATION_JSON })
	public DatosHuelga getInfoPatronHuelga(@PathParam("regPatron")String regPatron) {
		log.debug("llege a la consulta de getInfoPatronHuelga " + regPatron);
		try {
			return patronServiciosDigitalesService.getInfoPatronHuelga(regPatron);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("ocurrio un error inesperado al consultar los getInfoPatronHuelga de SISEC " + regPatron, e);
			ServiciosRestException ex = new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error inesperado al consultar los getInfoPatronHuelga de SISEC " + regPatron + e.getMessage() , e.getMessage()),e);
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}

	}
	
	@GET
	@Path("/periodosAprobados/sisec/{nss}/{refCurp}/tipoTramite/{tipoTramite}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<DatosRelacionLaboralReconocidaModel> getPeriodosAprobadosByTipoTramiteSisec(@PathParam("nss")String nss,
			@PathParam("refCurp")String refCurp, @PathParam("tipoTramite")int tipoTramite) {
		log.debug("llege a la consulta de getPeriodosAprobadosByTipoTramiteSisec " + nss + " y tipo tramite "+ tipoTramite);

		try {
			return sisecServiciosDigitalesService.getPeriodosAprobadosByTipoTramiteSisec(nss, refCurp, new Long(tipoTramite));
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("ocurrio un error inesperado al consultar los periodos de SISEC " + refCurp, e);
			ServiciosRestException ex = new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error inesperado al consultar los periodos de SISEC " + refCurp + e.getMessage() , e.getMessage()),e);
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}

	}
	
	
	
	
	
	private void getEjbSisecService() {
		try {
			log.debug("llegue a instanciar el EJB" );
			sisecServiciosDigitalesService = EjbLocator.getServiciosSiseccService();
			patronServiciosDigitalesService =EjbLocator.getPatronDigitalService();
			
			log.debug("sali instanciar el EJB getServiciosSiseccService");
			}catch (Exception e) {
				System.out.println("Ocurrio un error al quere recuperar el EJB" +  e.getMessage());
				throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
			}
		}
	

}
