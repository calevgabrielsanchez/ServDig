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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.MunicipioInegi;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IDomicilioServiciosDigitalesServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.rest.util.ComportamientosComunesUtil;
import mx.gob.imss.cit.dacvass.servicios.rest.util.EjbLocator;
import mx.gob.imss.digital.modelo.domicilio.Domicilio;



@Service
@Path("v1/domicilios")
public class DomicilioServiciosDigitalesServiceRest {
	
	private static final Logger log = LoggerFactory
            .getLogger(DomicilioServiciosDigitalesServiceRest.class); 
	
		private IDomicilioServiciosDigitalesServiceRemote servicioDomicio;
		
		public DomicilioServiciosDigitalesServiceRest() {
			this.getEjbDomicilioServices();
		}
		
		
		@GET
		@Path("/{cveDomicilio}")
		@Produces({ MediaType.APPLICATION_JSON })
		public Domicilio getDomicilioServiciosDigitales (@PathParam("cveDomicilio") Long cveDomicilio) {
			
			try {
				
				return servicioDomicio.consultaDomicilio(cveDomicilio);
				
			}catch(ServiciosRestException ex) {
				throw ComportamientosComunesUtil.getWebApplicationException(ex);
			}
		}
		
		@POST
		@Path("/crear")
		@Consumes({ MediaType.APPLICATION_JSON })
		@Produces({ MediaType.APPLICATION_JSON })
		public Domicilio guardaDomicilioServiciosDigitales ( Domicilio domicilio) {
			
			try {
				return servicioDomicio.registraDomicilio(domicilio);
			}catch(ServiciosRestException ex) {
				log.error("ocurrio un errro al querer guardar el domicio recortado", ex);
				throw ComportamientosComunesUtil.getWebApplicationException(ex);
			}
		}
		
		@PUT
		@Path("/actualizar")
		@Consumes({ MediaType.APPLICATION_JSON })
		@Produces({ MediaType.APPLICATION_JSON })
		public void  actualizaDomicilioServiciosDigitales ( Domicilio domicilio) {
			log.debug("llegue al metodo par aactulaizar el domiclio");
			try {
				servicioDomicio.actualizaDomicilio(domicilio);
			}catch(ServiciosRestException ex) {
				log.error("ocurrio un errro al querer actualizar  el domicio recortado", ex);
				throw ComportamientosComunesUtil.getWebApplicationException(ex);
			}
		}
		
		
		@GET
		@Path("/asentamientos/codigoPostal/{codigoPostal}")
		@Produces({ MediaType.APPLICATION_JSON })
		public List<mx.gob.imss.digital.modelo.domicilio.Asentamiento> getAsentamientoPorCodigoPosta (@PathParam("codigoPostal") String codigoPostal) {
			
			log.debug("llege la metodo para consultar el asentamiento");
			try {
				return servicioDomicio.getAsentamientoPorCodigoPostal(codigoPostal);
			}catch(ServiciosRestException ex) {
				log.error("ocurrio en error al consultar el asentamiento", ex);
				throw ComportamientosComunesUtil.getWebApplicationException(ex);
			}
		}
		
		@POST
		@Path("/crear/normaInegi")
		@Consumes({ MediaType.APPLICATION_JSON })
		@Produces({ MediaType.APPLICATION_JSON })
		public mx.gob.imss.ctirss.delta.model.domicilio.Domicilio registraDomicilioDeltaInegi(
								mx.gob.imss.ctirss.delta.model.domicilio.Domicilio domicilioDelta) {
			log.debug("entre al metodo para guardar el domiclio inegi");
			try {
				return servicioDomicio.registraDomicilioDeltaInegi(domicilioDelta);
			}catch(ServiciosRestException ex) {
				log.error("ocurrio un errro al querer guardar el domicio inegi", ex);
				throw ComportamientosComunesUtil.getWebApplicationException(ex);
			}
		}
		
		@GET
		@Path("/normaInegi/{cveDomicilio}")
		@Produces({ MediaType.APPLICATION_JSON })
		public mx.gob.imss.ctirss.delta.model.domicilio.Domicilio getDomicilioNormaInegi(@PathParam("cveDomicilio") Long cveDomicilio) {
			try {
				
				return servicioDomicio.consultaDomicilioNormaInegi(cveDomicilio);
				
			}catch(ServiciosRestException ex) {
				throw ComportamientosComunesUtil.getWebApplicationException(ex);
			}
		}
		
		@POST
		@Path("/localidades/municipio")
		@Consumes({ MediaType.APPLICATION_JSON })
		@Produces({ MediaType.APPLICATION_JSON })
		public List<mx.gob.imss.digital.modelo.domicilio.Localidad> getLocalidadPorMunicipio (MunicipioInegi municipio) {
			
			log.debug("llege la metodo para consultar la locaidad  getLocalidadPorMunicipio");
			try {
				return servicioDomicio.getLocalidadPorMunicipio(municipio);
			}catch(ServiciosRestException ex) {
				log.error("ocurrio en error al consultar la localidad", ex);
				throw ComportamientosComunesUtil.getWebApplicationException(ex);
			}
		}
		
		
		@GET
		@Path("/localidades/codigoPostal/{codigoPostal}")
		@Produces({ MediaType.APPLICATION_JSON })
		public List<mx.gob.imss.digital.modelo.domicilio.Localidad> getLocalidadPorCodigoPostal (@PathParam("codigoPostal") String codigoPostal) {
			
			log.debug("llege la metodo para consultar la localidad por CP");
			try {
				return servicioDomicio.getLocalidadPorCodigoPostal(codigoPostal);
			}catch(ServiciosRestException ex) {
				log.error("ocurrio en error al consultar la localiad por cp ", ex);
				throw ComportamientosComunesUtil.getWebApplicationException(ex);
			}
		}
		
		
		
		public void getEjbDomicilioServices() {
			try {
				System.out.println("llegue a instanciar el EJB");
				servicioDomicio = EjbLocator.getDomicilioDigitalService();
				System.out.println("sali instanciar el EJB");
				}catch (Exception e) {
					System.out.println("Ocurrio un error al quere recuperar el EJB" +  e.getMessage());
					throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
				}
			}

}
