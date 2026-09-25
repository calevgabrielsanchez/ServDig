package mx.gob.imss.cit.dacvass.servicios.externos.service.business.rest;

import java.util.List;

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

import mx.gob.imss.cit.dacvass.servicios.externos.model.consultas.dto.ConsultaPersonaGfHistLab;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.ConstantesComunesServiciosRest;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.DerechohabienteDTO;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.DerechohabienteSinolave;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.DerechohabienteSinolaveCovid;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.DatosBasicosPersonaGfHistLab;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.DatosBasicosPersonaGrupoFamiliar;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.PersonaGruposFamilares;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IAseguradoServiciosDigitalesServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.IConsultaPersonaServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.rest.util.ComportamientosComunesUtil;
import mx.gob.imss.cit.dacvass.servicios.rest.util.EjbLocator;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;

@Service
@Path("v1/personas/grupoFamiliar")
public class GrupoFamiliarServiceRest {

	private static Logger log = LoggerFactory.getLogger(ConsultaInfoPersonaServiceRest.class);
	//@EJB(name = "personaBusiness", mappedName = "personaBusiness") 
	@Autowired
	private IConsultaPersonaServiceRemote consultaInfoPersonaRemote;
	@Autowired
	private IAseguradoServiciosDigitalesServiceRemote serviciosAseguradoRemote;

	public GrupoFamiliarServiceRest() {
		this.getEjbRemote();
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

	@GET
	@Path("/nss/{nss}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<DerechohabienteDTO> getGrupoFamilarServiciosDigitalesBYNss(
			@PathParam("nss")String nss) {

		log.debug("llegue al metodo del grupo familiar" + nss);

		try {
			return serviciosAseguradoRemote.getGrupoFamiliarByNSS(nss);
		}catch(ServiciosRestException ex) {
			log.error("ocorrio un erro en la consulta de servicios de grupo familiar " ,ex );

			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("ocorrio un error no cachatdo de grupo familiar " ,e );

			throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);

		}

	}

	@GET
	@Path("/asegurado/{idAsingancionNSS}/beneficiario/{idPersona}")
	@Produces({ MediaType.APPLICATION_JSON })
	public DerechohabienteDTO getIntegranteGrupoFamilarServiciosDigitales(
			@PathParam("idAsingancionNSS")Long idAsingancionNSS, @PathParam("idPersona")Long idPersona) {

		log.debug("llegue al metodo de beneficiario");
		if(idAsingancionNSS.intValue() <= 0L || idPersona.intValue() <= 0 )
			throw new NotFoundException("Los par�metros de entrada  no son validos, por favor verifica.");

		try {
			return consultaInfoPersonaRemote.getIntegranteGrupoFamiliar(idAsingancionNSS, idPersona);
		}catch(ServiciosRestException ex) {
			log.error("ocorrio un erro en la consulta de servicios de derechohabiente" ,ex );

			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("ocorrio un error no cachatdo de derechohabiente" ,e );

			throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);

		}

	}


	@GET
	@Path("/asegurado/sinConsultarVigencia/{nss}")
	@Produces({ MediaType.APPLICATION_JSON })
	public DerechohabienteDTO getAseguradoGrupoFamilarServiciosDigitales(
			@PathParam("nss")String nss) {
		log.debug("llegue al metodo de aseguradoSinVigencia" + nss);

		try {
			return serviciosAseguradoRemote.getAseguradoGrupoFamiliarByNSSSinVigencia(nss, 
					ConstantesComunesServiciosRest.VALIDA_BAJA_CONSULTA_ASEGURADO );
		}catch(ServiciosRestException ex) {
			log.error("ocorrio un erro en la consulta de servicios de derechohabiente" ,ex );
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("ocorrio un error no cachatdo de derechohabiente" ,e );
			throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
		}

	}



	@GET
	@Path("/asegurado/sinolave/{nss}")
	@Produces({ MediaType.APPLICATION_JSON })
	public DerechohabienteSinolave getAseguradoVigenteSinolave(
			@PathParam("nss")String nss) {
		log.debug("llegue al metodo de getAseguradoVigenteSinolave");
		if(StringUtils.isEmpty(nss) || nss.length() < 10 )
			throw new NotFoundException("La estructura del NSS no es valida, por favor verificar.");
		nss = nss.length() ==10? nss + ComportamientosComunesUtil.generaDigitoVerificador(nss): nss;

		try {
			return serviciosAseguradoRemote.getAseguradoVigenteSinolave(nss);
		}catch(ServiciosRestException ex) {
			log.error("ocorrio un erro en la consulta de servicios de getAseguradoVigenteSinolave" ,ex );
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("ocorrio un error no cachatdo de derechohabiente getAseguradoVigenteSinolave" ,e );
			throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
		}

	}


	@GET
	@Path("/asegurado/sinConsultarVigencia/{nss}/validaFechaBaja/{validaFechaBaja}")
	@Produces({ MediaType.APPLICATION_JSON })
	public DerechohabienteDTO getAseguradoGrupoFamilarServiciosDigitalesIndFechaBaja(
			@PathParam("nss")String nss,  
			@PathParam("validaFechaBaja")boolean validaFechaBaja){
		log.debug("llegue al metodo de aseguradoSinVigencia con fecha e indicador de fecha de baja" + validaFechaBaja );

		try {
			return serviciosAseguradoRemote.getAseguradoGrupoFamiliarByNSSSinVigencia(nss,validaFechaBaja);
		}catch(ServiciosRestException ex) {
			log.error("ocorrio un erro en la consulta de servicios de asegurado " ,ex );
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("ocorrio un error no cachatdo de consulta de asegruado e info derechohabiente" ,e );
			throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
		}

	}


	@GET
	@Path("/asegurado/sinolave/covid/{nss}/{curp}")
	@Produces({ MediaType.APPLICATION_JSON })
	public DerechohabienteSinolaveCovid getAseguradoVigenteSinolaveCovid(
			@PathParam("nss")String nss, @PathParam("curp")String curp) {
		log.debug("llegue al metodo de getAseguradoVigenteSinolaveCovid");

		try {
			return serviciosAseguradoRemote.getAseguradoVigenteSinolaveCovid(nss, curp);
		}catch(ServiciosRestException ex) {
			log.error("ocorrio un erro en la consulta de servicios de getAseguradoVigenteSinolaveCovid" ,ex );
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("ocorrio un error no cachatdo de derechohabiente getAseguradoVigenteSinolaveCovid" ,e );
			throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
		}

	}

	@GET
	@Path("/{curp}")
	@Produces({ MediaType.APPLICATION_JSON })
	public PersonaGruposFamilares getPersonaGruposFamilares( @PathParam("curp")String curp) {
		log.debug("llegue al metodo de getPersonaGruposFamilares");

		try {
			return consultaInfoPersonaRemote.getPersonaGruposFamilares( curp);
		}catch(ServiciosRestException ex) {
			log.error("ocorrio un erro en la consulta de servicios de getAseguradoVigenteSinolaveCovid" ,ex );
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("ocorrio un error no cachatdo de derechohabiente getAseguradoVigenteSinolaveCovid" ,e );
			throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
		}

	}

	@GET
	@Path("/nss/{nss}/integrante/curp/{curp}")
	@Produces({ MediaType.APPLICATION_JSON })
	public GrupoFamiliar getIntegranteGrupoFamiliarByCurp(
			@PathParam("nss")String nss, @PathParam("curp")String curp) {
		log.debug("llegue al metodo de getIntegranteGrupoFamiliarByCurp");

		try {
			return  serviciosAseguradoRemote.getIntegranteGrupoFamiliarByCurp(nss, curp);
		}catch(ServiciosRestException ex) {
			log.error("ocorrio un error en la consulta de servicios de getIntegranteGrupoFamiliarByCurp" ,ex );
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("ocorrio un error no cachatdo de derechohabiente getIntegranteGrupoFamiliarByCurp" ,e );
			throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
		}

	}


	@GET
	@Path("/datosBasicosPersonaGruposFamiliares/curp/{curp}/vigencia/{vigencia}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<DatosBasicosPersonaGrupoFamiliar> getDatosBasicosPersonaGruposFamiliares(
			@PathParam("curp")String curp, @PathParam("vigencia") boolean conVigencia) {
		log.debug("llegue al metodo de getDatosBasicosPersonaGruposFamiliares");
		try {
			return  consultaInfoPersonaRemote.getDatosBasicosPersonaGruposFamiliares(curp, conVigencia);
		}catch(ServiciosRestException ex) {
			log.error("ocorrio un error en la consulta de servicios de getDatosBasicosPersonaGruposFamiliares" ,ex );
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("ocorrio un error no cachatdo de derechohabiente getDatosBasicosPersonaGruposFamiliares" ,e );
			throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
		}

	}

	@GET
	@Path("/datosBasicosPersonaGruposFamiliares/idee/{idee}/vigencia/{vigencia}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<DatosBasicosPersonaGrupoFamiliar> getDatosBasicosPersonaGruposFamiliaresByIdee(
			@PathParam("idee")String idee, @PathParam("vigencia") boolean conVigencia) {
		log.debug("llegue al metodo de getDatosBasicosPersonaGruposFamiliares");
		try {
			return  consultaInfoPersonaRemote.getDatosBasicosPersonaGruposFamiliaresByIdee(idee, conVigencia);
		}catch(ServiciosRestException ex) {
			log.error("ocorrio un error en la consulta de servicios de getDatosBasicosPersonaGruposFamiliares" ,ex );
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("ocorrio un error no cachatdo de derechohabiente getDatosBasicosPersonaGruposFamiliares" ,e );
			throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
		}

	}
	
	@POST
	@Path("/datosBasicosPersonaAseguradoGFHistLab")
	@Produces({ MediaType.APPLICATION_JSON })
	public DatosBasicosPersonaGfHistLab getDatosBasicosPersonaAseguradoGFHistLab(ConsultaPersonaGfHistLab personaBusqueda) {

		log.debug("llegue al metodo de getDatosBasicosPersonaAseguradoGFHistLab" + personaBusqueda);
		try {
			return  consultaInfoPersonaRemote.getDatosBasicosPersonaAseguradoGFHistLab(personaBusqueda);
		}catch(ServiciosRestException ex) {
			log.error("ocorrio un error en la consulta de servicios de getDatosBasicosPersonaAseguradoGFHistLab" ,ex );
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}catch(Exception e) {
			log.error("ocorrio un error no cachatdo de getDatosBasicosPersonaAseguradoGFHistLab" ,e );
			throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
		}
	}	
}
