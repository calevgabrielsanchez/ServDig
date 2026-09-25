package mx.gob.imss.cit.dacvass.servicios.externos.service.business.rest;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.CorreoElectronicoRest;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.SelloDigitalRest;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IServiciosDigitalesUtilServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.rest.util.ComportamientosComunesUtil;
import mx.gob.imss.cit.dacvass.servicios.rest.util.EjbLocator;

@Service
@Path("v1/util")
public class ServiciosDigitalesUtilServicesRest {
	
	private final Logger log = LoggerFactory.getLogger(ServiciosDigitalesUtilServicesRest.class); 
	
	private IServiciosDigitalesUtilServiceRemote serviciosDigitalesUtilService;
	public ServiciosDigitalesUtilServicesRest() {
		this.getEjbUtiloServices();
	}

	
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Path("/correoElectronico/enviar")
	public void enviaCorreoElectronicoService(CorreoElectronicoRest correoRest) {
		//log.debug("llegue el al metodo de correo" + correoRest.toString());
		log.debug("el correo destinatario es " +  correoRest.getCorreoPara()[0] + " y lo envia " + correoRest.getRemitente());
		try {
			serviciosDigitalesUtilService.enviarCorreo(correoRest);
			}catch(ServiciosRestException e) {
				log.error("Ocurrio un error al mandar el correo electronico" ,  e);
				throw ComportamientosComunesUtil.getWebApplicationException(e);
			}catch(Exception e) {
				log.error("error no identificado al mandar el correo" , e);
				throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
			
			}
		
	}
	
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/firmaElectronica/selloDigital")
	public SelloDigitalRest generaSelloDigital(SelloDigitalRest selloRest) {
		log.debug("llegando al servicio del sello digital " + selloRest.toString() );
		try {
			selloRest = serviciosDigitalesUtilService.getSelloDigital(selloRest);
			System.out.println("el valor del sello es : " + selloRest.getSello());
		
			return selloRest;	
			}catch(ServiciosRestException e) {
				log.error("Ocurrio un error al generar el sello" ,  e);
				throw ComportamientosComunesUtil.getWebApplicationException(e);
			}catch(Exception e) {
				log.error("Error no identificado" , e);
				throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
			
			}
		
		
	}
	
	
	@GET
	@Path("/diasInhabiles/{numAnio}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Date> getDiasInhabilesPorAnio(@PathParam("numAnio") Long numAnio) {
		log.debug("llegando al servicio del consulta de dias inhabiles"  );
		try {
			return serviciosDigitalesUtilService.getDiasInhabilesPorAnio(numAnio);
		}catch(ServiciosRestException ex) {
			log.error("erorr de servicios rest" , ex);
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("ALGO NO JALO BIEN" , e);
			throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
		
		}
	
	}
	

	private void getEjbUtiloServices(){
	try {
		serviciosDigitalesUtilService = EjbLocator.getUtilService();
		}catch(Exception e) {
			log.error("Ocurrio un error al quere recuperar el EJB" ,  e);
			ServiciosRestException ex =  new ServiciosRestException(new ErrorResponseBean(ErrorResponseBean.codigo500, ErrorResponseBean.codigo500Descripcion,
					"ocurrio un error al recuperar el EJB de servicios", e.getMessage()), e);
			throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).
				    entity(ex.getErrorBean()).type(MediaType.APPLICATION_JSON).build());
		}
	}
	
	

}
