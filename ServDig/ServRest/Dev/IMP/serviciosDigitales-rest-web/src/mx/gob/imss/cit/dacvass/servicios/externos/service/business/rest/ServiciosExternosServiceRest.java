package mx.gob.imss.cit.dacvass.servicios.externos.service.business.rest;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.renapo.RespuestaWSRenapo;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.sat.RespuestaWSSat;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siap.ConsultaUsuarioDirectorioActivo;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siap.EmpleadoSiapRest;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siap.UsuarioDirectorioActivo;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IConsultaServiciosExternosServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.rest.util.ComportamientosComunesUtil;
import mx.gob.imss.cit.dacvass.servicios.rest.util.EjbLocator;

@Service
@Path("v1/externos")
public class ServiciosExternosServiceRest {
	
	private static Logger log = LoggerFactory.getLogger(ServiciosExternosServiceRest.class);
	@Autowired
	private IConsultaServiciosExternosServiceRemote consultaServiciosExternosService;
	
	public ServiciosExternosServiceRest() {
		this.getEjbRemote();
	}

	 public void getEjbRemote(){
			try {
				log.info("llegue a instanciar el EJB del seviciso externos");
				consultaServiciosExternosService = EjbLocator.getServiciosExternosService();
				log.info("sali instanciar el EJB del servicios externos");
				}catch (Exception e) {
					log.error("Ocurrio un error al quere recuperar el EJB IConsultaServiciosExternosServiceRemote" , e);
					throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
				}
	}
	
	 @GET
		@Path("/siap/matricula/{numMatricula}")
		@Produces({ MediaType.APPLICATION_JSON })
		public EmpleadoSiapRest getEmpleadoSiapByMatricula(@PathParam("numMatricula")Long numMatricula) {
			log.debug("llegue al metodo de getEmplaadoSiapByMatricula");
			try {
				return  consultaServiciosExternosService.getEmpleadoSiapByMatricula(numMatricula);
			}catch(ServiciosRestException ex) {
				log.error("ocorrio un error en la consulta de servicios de getEmpleadoSiapByMatricula " + numMatricula ,ex );
				throw ComportamientosComunesUtil.getWebApplicationException(ex);
			}catch(Exception e) {
				log.error("ocurrio un error no cachatdo getEmpleadoSiapByMatricula" + numMatricula,e );
				throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
			}

		}
	 
		@GET
		@Path("/sat/rfc/{rfc}")
		@Produces({ MediaType.APPLICATION_JSON })
		public RespuestaWSSat getDatosSatByRfc(@PathParam("rfc")String rfc) {
			log.debug("llegue al metodo de getDatosSatByRfc");
			try {
				return  consultaServiciosExternosService.getDatosSatByRfc(rfc);
			}catch(ServiciosRestException ex) {
				log.error("ocorrio un error en la consulta de servicios de getDatosSatByRfc " +rfc ,ex );
				throw ComportamientosComunesUtil.getWebApplicationException(ex);
			}catch(Exception e) {
				log.error("ocorrio un error no cachatdo en getDatosSatByRfc " +rfc ,e );
				throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
			}

		}
		
		@GET
		@Path("/renapo/curp/{curp}")
		@Produces({ MediaType.APPLICATION_JSON })
		public RespuestaWSRenapo getDatosPersonaRenapoByCurp(@PathParam("curp")String curp) {
			log.debug("llegue al metodo de getDatosPersonaRenapoByCurp");
			try {
				return  consultaServiciosExternosService.getDatosPersonaRenapoByCurp(curp);
			}catch(ServiciosRestException ex) {
				log.error("ocorrio un error en la consulta de servicios de getDatosPersonaRenapoByCurp " + curp ,ex );
				throw ComportamientosComunesUtil.getWebApplicationException(ex);
			}catch(Exception e) {
				log.error("ocorrio un error no cachatdo getDatosPersonaRenapoByCurp" + curp,e );
				throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
			}

		}
		
		@POST
		@Path("/directorioActivo/usuario")
		@Produces({ MediaType.APPLICATION_JSON })
		public UsuarioDirectorioActivo getDatosUsuarioDirectorioActivo(ConsultaUsuarioDirectorioActivo consulta) {
			log.debug("llegue al metodo de getDatosUsuarioDirectorioActivo" + consulta);
			try {
				return  consultaServiciosExternosService.getDatosUsuarioDirectorioActivo(consulta);
			}catch(ServiciosRestException ex) {
				log.error("ocorrio un error en la consulta de servicios de getDatosUsuarioDirectorioActivo " + consulta ,ex );
				throw ComportamientosComunesUtil.getWebApplicationException(ex);
			}catch(Exception e) {
				log.error("ocorrio un error no cachatdo getDatosUsuarioDirectorioActivo" + consulta,e );
				throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
			}

		}
		
		@POST
		@Path("/directorioActivo/usuario/autentica")
		@Produces({ MediaType.APPLICATION_JSON })
		public UsuarioDirectorioActivo autenticaUsuarioDirectorioActivo(ConsultaUsuarioDirectorioActivo consulta) {
			log.debug("llegue al metodo de autenticaUsuarioDirectorioActivo" + consulta);
			try {
				return  consultaServiciosExternosService.autenticaUsuarioDirectorioActivo(consulta);
			}catch(ServiciosRestException ex) {
				log.error("ocorrio un error en la consulta de servicios de autenticaUsuarioDirectorioActivo " + consulta ,ex );
				throw ComportamientosComunesUtil.getWebApplicationException(ex);
			}catch(Exception e) {
				log.error("ocorrio un error no cachatdo autenticaUsuarioDirectorioActivo" + consulta,e );
				throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
			}

		}
	
}
