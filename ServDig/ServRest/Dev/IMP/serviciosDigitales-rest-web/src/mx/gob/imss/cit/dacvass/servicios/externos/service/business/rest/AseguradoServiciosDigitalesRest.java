package mx.gob.imss.cit.dacvass.servicios.externos.service.business.rest;

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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.jersey.api.NotFoundException;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.AseguradoVigentePermisoCovid;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Persona;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.asegurado.AseguradoCuentaIndividual;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.asegurado.AseguradoPensionado;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.asegurado.DatosGeneralesAsegurado;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.asegurado.DatosGeneralesAseguradoMarcaAfiliatoria;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IAseguradoServiciosDigitalesServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.rest.util.ComportamientosComunesUtil;
import mx.gob.imss.cit.dacvass.servicios.rest.util.EjbLocator;
import mx.gob.imss.digital.modelo.domicilio.Domicilio;

@Service
@Path("v1/personas/asegurado")
public class AseguradoServiciosDigitalesRest {
	
	
	
	
	private static Logger log = LoggerFactory.getLogger(AseguradoServiciosDigitalesRest.class);
	
	@Autowired
	private IAseguradoServiciosDigitalesServiceRemote serviciosAseguradoRemote;
	
	public AseguradoServiciosDigitalesRest() {
		this.getEjbRemote();
	}

	 public void getEjbRemote(){
			try {
				log.info("llegue a instanciar el EJB del asegurado");
				serviciosAseguradoRemote = EjbLocator.getServiciosAsegurdoService();
				log.info("sali instanciar el EJB del asegurado");
				}catch (Exception e) {
					log.error("Ocurrio un error al quere recuperar el EJB consultaInfoPersonaRemote" , e);
					throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
				}
	}
	 

		@GET
		@Path("/{nss}/tipoPension")
		@Produces({ MediaType.APPLICATION_JSON })
		public AseguradoPensionado getAseguradoPensionado(@PathParam("nss")String nss){
			log.debug("llegue al metodo de getAseguradoPensionado " + nss );
			try {
				return serviciosAseguradoRemote.getAseguradoPensionado(nss);
			}catch(ServiciosRestException ex) {
				log.error("ocorrio un erro en la consulta de servicios de asegurado " ,ex );
				throw ComportamientosComunesUtil.getWebApplicationException(ex);
			}catch(Exception e) {
				log.error("ocorrio un error no cachado en la consulta del asegruiod y datos de pension nss"+ nss ,e );
				throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
			}
		
		}
		
		@GET
		@Path("/{nss}")
		@Produces({ MediaType.APPLICATION_JSON })
		public Persona getPersonaServiciosDigitalesBYNss(
			 	@PathParam("nss")String nss) {
			try {
				return serviciosAseguradoRemote.getPersonaServiciosDigitalesByNSS(nss);
			}catch(ServiciosRestException ex) {
				System.out.println("erorr de servicios rest" + ex.getMessage());
				throw ComportamientosComunesUtil.getWebApplicationException(ex);
			}catch(Exception e) {
				System.out.println("ALGO NO JALO BIEN" + e.getMessage());
				throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
			
			}
		
		}
		
		@GET
		@Path("/{nss}/pasoAlcambioAlpendienteConfirmar")
		@Produces({ MediaType.APPLICATION_JSON })
		public String isAseguradoPasoAlCambioAlPendiente(@PathParam("nss")String nss) {
			log.debug("llegue al metodo de validaAseguradoPasoAlCambioAlPendiente");
			try {
				return serviciosAseguradoRemote.isAseguradoPasoAlCambioAlPendiente(nss);
			}catch(ServiciosRestException ex) {
				log.error("ocorrio un erro en la consulta de servicios de validaAseguradoPasoAlCambioAlPendiente" ,ex );
				throw ComportamientosComunesUtil.getWebApplicationException(ex);
			}catch(Exception e) {
				log.error("ocorrio un error no cachado de validaAseguradoPasoAlCambioAlPendiente" ,e );
				throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
			}
		
		}
		


		@GET
		@Path("/{nss}/fechaRegistroBaja")
		@Produces({ MediaType.APPLICATION_JSON })
		public String getFechaBajaDitAsignacionNss(@PathParam("nss")String nss) {
			log.debug("llegue al metodo de getFechaBajaDitAsignacionNss");
			try {
				return serviciosAseguradoRemote.getFechaBajaDitAsignacionNss(nss);
			}catch(ServiciosRestException ex) {
				log.error("ocorrio un erro en la consulta de servicios de getFechaBajaDitAsignacionNss" ,ex );

				throw ComportamientosComunesUtil.getWebApplicationException(ex);
			}catch(Exception e) {
				log.error("ocorrio un error no cachatdo de getFechaBajaDitAsignacionNss" ,e );

				throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
			}
		
		}
		
		

		@GET
		@Path("/{nss}/relacionLaboral/nrp/{nrp}")
		@Produces({ MediaType.APPLICATION_JSON })
		public Boolean validaRelacionLaboralAseguradoPatron(
														@PathParam("nss")String nss, @PathParam("nrp")String nrp) {
			log.debug("llegue al metodo de validaRelacionLaboralAseguradoPatron " +  nss + " NRP" + nrp); 
			
			if(StringUtils.isEmpty(nrp) || nrp.length() < 10 )
				throw new NotFoundException("La estructura del Registro Patronal no es valida, por favor verificar.");
			
			try {
				return serviciosAseguradoRemote.validaRelacionLaboralAseguradoPatron(nss, nrp);
			}catch(ServiciosRestException ex) {
				log.error("ocorrio un erro en la consulta de relacion laboral" ,ex );
				throw ComportamientosComunesUtil.getWebApplicationException(ex);
			}catch(Exception e) {
				log.error("ocorrio un error no cachatdo de derechohabiente" ,e );
		
				throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
			}
		
		}
		

		@GET
		@Path("/{nss}/domicilio")
		@Produces({ MediaType.APPLICATION_JSON })
		public Domicilio getDomicilioAseguradoServiciosDigitalesBYNss(
			 	@PathParam("nss")String nss) {
			
			log.debug("llegue al metodo getDomicilioAseguradoServiciosDigitalesBYNss");
		
			try {
				return serviciosAseguradoRemote.consultaDomicilioAseguradoGrupoFamiliar(nss);
			}catch(ServiciosRestException ex) {
				log.error("ocorrio un erro en la consulta de servicios " ,ex );
				System.out.println("erorr de servicios rest" + ex.getMessage());
				throw ComportamientosComunesUtil.getWebApplicationException(ex);
			}catch(Exception e) {
				log.error("ocorrio un error no cachatdo " ,e );
				System.out.println("ALGO NO JALO BIEN" + e.getMessage());
				throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
			
			}
		
		}
		
		@GET
		@Path("/{nss}/historiaLaboral")
		@Produces({ MediaType.APPLICATION_JSON })
		public List<AseguradoCuentaIndividual> getMovimientosCuentaIndividual(@PathParam("nss")String nss) {
			
			log.debug("llegue al metodo getMovimientosCuentaIndividual nss " +  nss);
		
			try {
				return serviciosAseguradoRemote.consultarMovimientosCuentaIndividual(nss);
			}catch(ServiciosRestException ex) {
				log.error("ocorrio un erro en la consulta de servicios " ,ex );
				throw ComportamientosComunesUtil.getWebApplicationException(ex);
			}catch(Exception e) {
				log.error("ocorrio un error no cachatdo " ,e );
				throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
			
			}
		
		}
		
		@POST
		@Path("/datosGenerales/list")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces({ MediaType.APPLICATION_JSON })
		public List<DatosGeneralesAsegurado> getListDatosGeneralesAseguradoByNss(List<String> lstNss){
			log.debug("llegue al metodo getDatosGeneralesAseguradoByNss" + lstNss);
			try {
				return serviciosAseguradoRemote.getDatosGeneralesAseguradoByNss(lstNss);
			}catch(ServiciosRestException ex) {
				log.error("ocorrio un error en la consulta de servicios de lista de asegurados " ,ex );
				throw ComportamientosComunesUtil.getWebApplicationException(ex);
			}catch(Exception e) {
				log.error("ocorrio un error no cachatdo  de lista de asegurados" ,e );
				throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
			}
	
			
		}
		
		@GET
		@Path("/datosGenerales/{nss}")
		@Produces({ MediaType.APPLICATION_JSON })
		public DatosGeneralesAsegurado getDatosGeneralesAseguradoByNss(@PathParam("nss") String nss) {
			log.debug("llegue al metodo getDatosGeneralesAseguradoByNss" + nss);
			try {
				return serviciosAseguradoRemote.getDatosGeneralesAseguradoByNss(nss);
			}catch(ServiciosRestException ex) {
				log.error("ocorrio un error en la consulta de servicios " ,ex );
				throw ComportamientosComunesUtil.getWebApplicationException(ex);
			}catch(Exception e) {
				log.error("ocorrio un error no cachatdo " ,e );
				throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
			
			}
			
		}
		
		@GET
		@Path("/canase/datosGenerales/{nss}")
		@Produces({ MediaType.APPLICATION_JSON })
		public DatosGeneralesAsegurado getCanseDatosGeneralesAseguradoByNss(@PathParam("nss") String nss) {
			log.debug("llegue al metodo getDatosGeneralesAseguradoByNss" + nss);
			try {
				return serviciosAseguradoRemote.getCanaseDatosGeneralesAseguradoByNss(nss);
			}catch(ServiciosRestException ex) {
				log.error("ocorrio un error en la consulta de servicios " ,ex );
				throw ComportamientosComunesUtil.getWebApplicationException(ex);
			}catch(Exception e) {
				log.error("ocorrio un error no cachatdo " ,e );
				throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
			
			}
			
		}
		
		@GET
		@Path("/{nss}/marcaAfiliatoria")
		@Produces({ MediaType.APPLICATION_JSON })
		public DatosGeneralesAseguradoMarcaAfiliatoria getAseguradoMarcaAfiliatoria(@PathParam("nss")String nss){
			log.debug("llegue al metodo de getAseguradoPensionado " + nss );
			try {
				return serviciosAseguradoRemote.getMarcaAfiliatoria(nss);
			}catch(ServiciosRestException ex) {
				log.error("ocorrio un erro en la consulta de servicios de asegurado " ,ex );
				throw ComportamientosComunesUtil.getWebApplicationException(ex);
			}catch(Exception e) {
				log.error("ocorrio un error no cachado en la consulta del asegruiod y datos de pension nss"+ nss ,e );
				throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
			}
		
		}
		
		@GET
		@Path("/vigente/permisoCovid/{nss}/{curp}")
		@Produces({ MediaType.APPLICATION_JSON })
		public AseguradoVigentePermisoCovid getAseguradoVigentePermisoCovid(
				@PathParam("nss")String nss, @PathParam("curp")String curp) {
			log.debug("llegue al metodo de getAseguradoVigenteSinolaveCovid");

			try {
				return serviciosAseguradoRemote.getAseguradoVigentePermisoCovid(nss, curp);
			}catch(ServiciosRestException ex) {
				log.error("ocorrio un erro en la consulta de servicios de getAseguradoVigenteSinolaveCovid" ,ex );
				throw ComportamientosComunesUtil.getWebApplicationException(ex);
			}catch(Exception e) {
				log.error("ocorrio un error no cachatdo de derechohabiente getAseguradoVigenteSinolaveCovid" ,e );
				throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
			}

		}
		
		
}
