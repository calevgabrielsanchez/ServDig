package mx.gob.imss.cit.dacvass.servicios.externos.service.business.rest;

import java.util.List;

import javax.ws.rs.GET;
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

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.ConstantesComunesServiciosRest;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.DerechohabienteDTO;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.DerechohabienteSinolave;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.DerechohabienteSinolaveCovid;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.PersonaFisicaMoral;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.PersonaSua;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.DatosBasicosPersonaGrupoFamiliar;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.DatosPersona;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Persona;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.PersonaGruposFamilares;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.renapo.RespuestaWSRenapo;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.sat.RespuestaWSSat;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IAseguradoServiciosDigitalesServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IConsultaPersonaServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.rest.util.ComportamientosComunesUtil;
import mx.gob.imss.cit.dacvass.servicios.rest.util.EjbLocator;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaAutorizada;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;


@Service
@Path("v1/personas")
public class ConsultaInfoPersonaServiceRest  {

	private static Logger log = LoggerFactory.getLogger(ConsultaInfoPersonaServiceRest.class);
	//@EJB(name = "personaBusiness", mappedName = "personaBusiness") 
	@Autowired
	private IConsultaPersonaServiceRemote consultaInfoPersonaRemote;
	@Autowired
	private IAseguradoServiciosDigitalesServiceRemote serviciosAseguradoRemote;

	public ConsultaInfoPersonaServiceRest() {
		this.getEjbRemote();
	}

	@GET
	@Path("{curpID}")
	@Produces({ MediaType.APPLICATION_JSON })
	public DatosPersona getInfoPersonaServiciosDigitales(
			@PathParam("curpID")String curp) {
		DatosPersona objPersonaBdtu = null;
		try {
			objPersonaBdtu = consultaInfoPersonaRemote.getInfoPersonaServiciosDigitales(curp);
			
			if(objPersonaBdtu.getDatosAsegurado() != null && objPersonaBdtu.getDatosAsegurado().getDatosHistoriaLaboral() != null) {
				log.debug("la hiostoria laboral es  " + objPersonaBdtu.getDatosAsegurado().getDatosHistoriaLaboral().get(0).getNumeroRegistroPatronal());
			}

		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("ocurrio un error al consultar a la persona", e);
			throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
		}
		//	return this.getDatosPersonaMoc();
		return objPersonaBdtu;
	}




	/**
	 * Metodo que recupera la persona de renapo la compara datos basicos contra la persona existente en bdtu en caso de existir 
	 * 
	 * @param curp
	 * @return
	 */
	@GET
	@Path("/personaRenapoServiciosDigaltes/{curpID}/{nss}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Persona getPersonaRenapoServiciosDigitales(
			@PathParam("curpID")String curp, @PathParam("nss")String nss) {
		log.debug("entrando al servicio " + curp );
		Persona objPersonaBdtu = null;
		try {
			objPersonaBdtu = consultaInfoPersonaRemote.getPersonaServiciosDigitalesNSSRenapoCurp(curp, nss);
			System.out.println("pase la consulta" );
			return objPersonaBdtu;
		}catch(ServiciosRestException ex) {
			log.error("erorr de servicios rest" + ex.getMessage());
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("ALGO NO JALO BIEN" + e.getMessage());
			throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);

		}

	}


	@GET
	@Path("/fisica/moral/rfc/{rfc}")
	@Produces({ MediaType.APPLICATION_JSON })
	public PersonaFisicaMoral getPersonaFisicaMoralByRfc(@PathParam("rfc")String rfc) {
		log.debug("llegue al metodo de getPersonaFisicaMoralByRfc");

		try {
			return  consultaInfoPersonaRemote.getPersonaFisicaMoralByRfc(rfc);
		}catch(ServiciosRestException ex) {
			log.error("ocorrio un error en la consulta de servicios de getPersonaFisicaMoralByRfc" ,ex );
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("ocorrio un error no cachatdo de derechohabiente getPersonaFisicaMoralByRfc" ,e );
			throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
		}

	}

	@GET
	@Path("/representanteLegal/{cveIdPersona}/{cveIdTipoPersona}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<RepresentanteLegal> getRepresentanteLegal(@PathParam("cveIdPersona")Long cveIdPersona,
			@PathParam("cveIdTipoPersona")Long cveIdTipoPersona) {
		log.debug("llegue al metodo de getRepresentanteLegal" );

		try {
			return  consultaInfoPersonaRemote.getRepresentanteLegal(cveIdPersona,cveIdTipoPersona);
		}catch(ServiciosRestException ex) {
			log.error("ocorrio un error en la consulta de servicios de getRepresentanteLegal" ,ex );
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("ocorrio un error no cachatdo de derechohabiente getRepresentanteLegal" ,e );
			throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
		}

	}
	

	@GET
	@Path("/fisica/moral/representados/{cveIdPersona}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona> getPersonaRepresentada(@PathParam("cveIdPersona")Long cveIdPersona) {
		log.debug("llegue al metodo de getPersonaRepresentada");

		try {
			return  consultaInfoPersonaRemote.getPersonaRepresentada(cveIdPersona);
		}catch(ServiciosRestException ex) {
			log.error("ocorrio un error en la consulta de servicios de getPersonaRepresentada" ,ex );
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("ocorrio un error no cachatdo de derechohabiente getPersonaRepresentada" ,e );
			throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GET
	@Path("/fisica/moral/personaAutorizada/{cveIdPersona}/{cveIdTipoPersona}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<PersonaAutorizada>  getPersonaAutorizada(
			@PathParam("cveIdPersona")Long cveIdPersona, @PathParam("cveIdTipoPersona")Long cveIdTipoPersona) {
		log.debug("llegue al metodo de getPersonaAutorizada");

		try {
			return  consultaInfoPersonaRemote.getPersonaAutorizada(cveIdPersona, cveIdTipoPersona);
		}catch(ServiciosRestException ex) {
			log.error("ocorrio un error en la consulta de servicios de getPersonaAutorizada" ,ex );
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("ocorrio un error no cachatdo de derechohabiente getPersonaAutorizada" ,e );
			throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GET
	@Path("/fisica/moral/representados/rfc/{rfc}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<String> getRfcPersonaRepresentadaByRfc(@PathParam("rfc")String rfc) {
		log.debug("llegue al metodo de getRfcPersonasRepresentadasByRfc");

		try {
			return  consultaInfoPersonaRemote.getRfcPersonaRepresentadaByRfc(rfc);
		}catch(ServiciosRestException ex) {
			log.error("ocurrio un error en la consulta del servicios getRfcPersonasRepresentadasByRfc" ,ex );
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("ocurrio un error no cachado en el servicios getRfcPersonasRepresentadasByRfc" ,e );
			throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
		}

	}

	
	@GET
	@Path("/datosGenerales/sua/{curp}/validaRegistroFiel/{indConsultaRegistroProtalFiel}/consultaCorreo/{indConsultaCorreoElectronico}")
	@Produces({ MediaType.APPLICATION_JSON })
	public  PersonaSua getPersonaSuaRenapoComplementoSD(@PathParam("curp")String curp, 
			@PathParam("indConsultaRegistroProtalFiel")boolean indConsultaRegistroProtalFiel, 
			@PathParam("indConsultaCorreoElectronico") boolean indConsultaCorreoElectronico) 
					throws ServiciosRestException{
		log.debug("llegue al metodo de getPersonaSuaRenapoComplementoSD con curp" + curp);
		try {
			return consultaInfoPersonaRemote.getPersonaSuaRenapoComplementoSD(curp, indConsultaRegistroProtalFiel, indConsultaCorreoElectronico);
		}catch(ServiciosRestException ex) {
			log.error("ocorrio un erro en la consulta de servicios de asegurado " ,ex );
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("ocorrio un error no cachatdo de consulta de asegruado e info derechohabiente" ,e );
			throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
		}

	}

	
		
	public void getEjbRemote(){
		try {
			System.out.println("llegue a instanciar el EJB de consulta persona");
			consultaInfoPersonaRemote = EjbLocator.getConsltaInfoPersonaRemote();
			serviciosAseguradoRemote = EjbLocator.getServiciosAsegurdoService();
			System.out.println("sali instanciar el EJB de consulta persona");
		}catch (Exception e) {
			System.out.println("Ocurrio un error al quere recuperar el EJB consultaInfoPersonaRemote" +  e.getMessage());
			throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

}
