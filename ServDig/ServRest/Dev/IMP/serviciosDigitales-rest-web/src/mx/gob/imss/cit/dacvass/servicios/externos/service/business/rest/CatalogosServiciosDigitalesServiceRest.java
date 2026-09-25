package mx.gob.imss.cit.dacvass.servicios.externos.service.business.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.MunicipioImss;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.MunicipioInegi;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.catComun.CatComunDelegacion;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.catComun.CatComunEntidadFederativa;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.catComun.CatComunUmf;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.EntidadFederativa;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.EstadoCivil;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Pais;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Parentesco;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Sexo;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Turno;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.ICatalogosServiciosDigitalesServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.rest.util.ComportamientosComunesUtil;
import mx.gob.imss.cit.dacvass.servicios.rest.util.EjbLocator;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clase;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.digital.modelo.domicilio.Delegacion;
import mx.gob.imss.digital.modelo.domicilio.Subdelegacion;

@Service
@Path("v1/catalogos")
public class CatalogosServiciosDigitalesServiceRest {
	
	private static final Logger log = LoggerFactory
            .getLogger(CatalogosServiciosDigitalesServiceRest.class);
	public CatalogosServiciosDigitalesServiceRest() {
			this.getEjbCatalogoServices();
	}
	
	private ICatalogosServiciosDigitalesServiceRemote catalogosRemote;
	
	private static final Long ID_PRIMER_NIVEL_ATENCION = 1L;
	
	
	@GET
	@Path("/sexo")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Sexo> getCatalogoSexo() {
		log.debug("llegue el al metodo"  );
		try {
			return catalogosRemote.getCatalogoSexo();
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/sexo/{idSexo}")
	@Produces({MediaType.APPLICATION_JSON})
	public Sexo getCatalogoSexo(@PathParam("idSexo") Long idSexo) {
		try {
			return catalogosRemote.getCatalogoSexo(idSexo);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/parentesco")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Parentesco> getCatalogoParentesco() {
		try {
			return catalogosRemote.getCatalogoParentesco();
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/parentesco/{idParentesco}")
	@Produces({MediaType.APPLICATION_JSON})
	public Parentesco getCatalogoParentesco(@PathParam("idParentesco") Long idParentesco) {
		try {
			return catalogosRemote.getCatalogoParentesco(idParentesco);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	
	@GET
	@Path("/entidadFederativa")
	@Produces({MediaType.APPLICATION_JSON})
	public List<EntidadFederativa> getCatalogoEntidadFederativa() {
		try {
			return catalogosRemote.getCatalogoEntidadFed();
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/entidadFederativa/{idEntidadFederativa}")
	@Produces({MediaType.APPLICATION_JSON})
	public EntidadFederativa getCatalogoParentesco(@PathParam("idEntidadFederativa") String idEntidadFederativa) {
		try {
			return catalogosRemote.getCatalogoEntidadFed(idEntidadFederativa);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/estadoCivil")
	@Produces({MediaType.APPLICATION_JSON})
	public List<EstadoCivil> getCatalogoEstadoCivil() {
		try {
			return catalogosRemote.getCatalogoEstadoCivil();
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/estadoCivil/{idEstadoCivil}")
	@Produces({MediaType.APPLICATION_JSON})
	public EstadoCivil getCatalogoEstadoCivil(@PathParam("idEstadoCivil") Long idEstadoCivil) {
		try {
			return catalogosRemote.getCatalogoEstadoCivil(idEstadoCivil);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/pais")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Pais> getCatalogoPais() {
		try {
			return catalogosRemote.getCatalogoPais();
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/pais/{idPais}")
	@Produces({MediaType.APPLICATION_JSON})
	public Pais getCatalogoPais(@PathParam("idPais") Long idPais) {
		try {
			return catalogosRemote.getCatalogoPais(idPais);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/turno")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Turno> getCatalogoTurno() {
		try {
			return catalogosRemote.getCatalogoTurno();
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/turno/{idTurno}")
	@Produces({MediaType.APPLICATION_JSON})
	public Turno getCatalogoTurno(@PathParam("idTurno") Long idTurno) {
		try {
			return catalogosRemote.getCatalogoTurnoByID(idTurno);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	
	@GET
	@Path("/delegacion")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Delegacion> getCatalogoDelegacion() {
		try {
			return catalogosRemote.getCatalogoDelegacion();
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("delegacion/{idDelegacion}/subDelegacion")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Subdelegacion> getCatalogoSubDelegacionByIdDelegacion(@PathParam("idDelegacion") Long idDelegacion) {
		try {
			return catalogosRemote.getCatalogoSubDelegacionByIdDelegacion(idDelegacion);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/delegacion/{idDelegacion}")
	@Produces({MediaType.APPLICATION_JSON})
	public Delegacion getCatalogoDelegacionById(@PathParam("idDelegacion") Long idDelegacion) {
		try {
			return catalogosRemote.getCatalogoDelegacionById(idDelegacion);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/subDelegacion/{idSubDelegacion}")
	@Produces({MediaType.APPLICATION_JSON})
	public Subdelegacion getCatalogoSubDelegacionById(@PathParam("idSubDelegacion") Long idSubDelegacion) {
		try {
			return catalogosRemote.getCatalogoSubDelegacionById(idSubDelegacion);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/subDelegacion/cp/{cp}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Subdelegacion> getSubDelegacionesPorCodigoPostal(
			@PathParam("cp") String cp) {
		try {
			return catalogosRemote.getSubDelegacionesPorCodigoPostal(cp);
		} catch (ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/umf/{idUmf}")
	@Produces({MediaType.APPLICATION_JSON})
	public UnidadMedicaFamiliar getCatalogoUmfById(@PathParam("idUmf") Long idUmf) {
		try {
			return catalogosRemote.getCatalogoUMfById(idUmf);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/subDelegacion/{idSubDelegacion}/umf")
	@Produces({MediaType.APPLICATION_JSON})
	public List<UnidadMedicaFamiliar> getCatalogoUmfBySubDelegacionId(@PathParam("idSubDelegacion") Long idSubDelegacion) {
		try {
			return catalogosRemote.getCatalogoUMfBySubdelegacionNivelAtencion(idSubDelegacion, ID_PRIMER_NIVEL_ATENCION);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/clasificacion/clase/")
	@Produces({MediaType.APPLICATION_JSON})
	public List <Clase> getCatalogoClase() {
		try {
			return catalogosRemote.getCatalogoClase();
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/clasificacion/clase/{idClase}")
	@Produces({MediaType.APPLICATION_JSON})
	public Clase getCatalogoClaseByIdClase(@PathParam("idClase") Long idClase) {
		try {
			return catalogosRemote.getCatalogoClaseByCveClase(idClase);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/clasificacion/clase/{idClase}/fraccion")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Fraccion> getCatalogoFraccionByIdClase(@PathParam("idClase") Long idClase) {
		try {
			return catalogosRemote.getCatalogoFraccionByIdClase(idClase);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/clasificacion/fraccion/{idFraccion}")
	@Produces({MediaType.APPLICATION_JSON})
	public Fraccion getCatalogoFraccionByIdFraccion(@PathParam("idFraccion") Long idFraccion) {
		try {
			return catalogosRemote.getCatalogoFraccionByCveFraccion(idFraccion);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/modalidad/")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Modalidad> getCatalogoModalidadB() {
		try {
			return catalogosRemote.getCatalogoModalidad();
		}catch(ServiciosRestException ex) {
			System.out.println("ocurrio un error al consultar el catalogo modalidad" + ex.getMessage());
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/modalidad/{idModalidad}")
	@Produces({MediaType.APPLICATION_JSON})
	public Modalidad getCatalogoModalidadByIdModadlidad(@PathParam("idModalidad") String idModalidad) {
		try {
			return catalogosRemote.getCatalogoModalidadByCveModalidad(idModalidad);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/tipoPersonaFiscal/")
	@Produces({MediaType.APPLICATION_JSON})
	public List<TipoPersona> getCatalogoTipoPersonaFiscal() {
		try {
			return catalogosRemote.getCatalogoTipoPersona();
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/tipoPersonaFiscal/{idTipoPersona}")
	@Produces({MediaType.APPLICATION_JSON})
	public TipoPersona getCatalogoTipoPersonaFiscalByTipoPersona(@PathParam("idTipoPersona") Long idTipoPersona) {
		try {
			return catalogosRemote.getCatalogoTipoPersonaByIdTipoPersona(idTipoPersona);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}

	
	@GET
	@Path("/clasificacion/division")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Division> getCatalogoDivision() {
		try {
			return catalogosRemote.getCatalogoDivision();
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/clasificacion/division/{idDivision}/grupo")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Grupo> getCatalogoGrupoByIdDivision(@PathParam("idDivision") Long idDivision) {
		try {
			return catalogosRemote.getCatalogoGrupoByIdDivision(idDivision);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/clasificacion/division/grupo/{idGrupo}/fraccion")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Fraccion> getCatalogoFraccionByIdGrupo(@PathParam("idGrupo") Long idGrupo) {
		try {
			return catalogosRemote.getCatalogoFraccionByIdGrupo(idGrupo);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	
	@GET
	@Path("/clasificacion/division/grupo/{idGrupo}/fraccion/claseActiva")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Fraccion> getCatalogoFraccionConClaseActivaByIdGrupo(@PathParam("idGrupo") Long idGrupo) {
		log.debug("entre al metodo getCatalogoFraccionConClaseActivaByIdGrupo ");
		System.out.println("entre al metodo getCatalogoFraccionConClaseActivaByIdGrupo ");
		try {
			return catalogosRemote.getCatalogoFraccionConClaseActivaByIdGrupo(idGrupo);
		}catch(ServiciosRestException ex) {
			ex.printStackTrace();
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/subDelegacion/{idSubDelegacion}/umf/nivelAtencion/{idNivelAtencion}")
	@Produces({MediaType.APPLICATION_JSON})
	public List<UnidadMedicaFamiliar> getCatalogoUmfBySubDelegacionIdNivelAtencion(@PathParam("idSubDelegacion") Long idSubDelegacion,
			@PathParam("idNivelAtencion") Long idNivelAtencion) {
		try {
			return catalogosRemote.getCatalogoUMfBySubdelegacionNivelAtencion(idSubDelegacion, idNivelAtencion);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/municipioInegi/{cveEntidadFed}")
	@Produces({MediaType.APPLICATION_JSON})
	public List<MunicipioInegi> getCatalogoMunicipioInegi
			(@PathParam("cveEntidadFed") String cveEntidadFed) {
		try {
			return catalogosRemote.getCatalogoMunicipioInegi(cveEntidadFed);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/municipioInegi/{cveEntidadFed}/{cveMunicipioInegi}")
	@Produces({MediaType.APPLICATION_JSON})
	public MunicipioInegi getMunicipioInegi
			(@PathParam("cveEntidadFed") String cveEntidadFed, @PathParam("cveMunicipioInegi") String cveMunicipioInegi) {
		try {
			return catalogosRemote.getMunicipioInegi(cveEntidadFed, cveMunicipioInegi );
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/externos/catComun/umf/cvePresupuestal/{cvePresupuestal}")
	@Produces({MediaType.APPLICATION_JSON})
	public CatComunUmf getCatComunUmf
			(@PathParam("cvePresupuestal")String cvePresupuestal) {
		try {
			return catalogosRemote.getCatComunUmf(cvePresupuestal);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/externos/catComun/umf/delegacion/{cveDelegacion}")
	@Produces({MediaType.APPLICATION_JSON})
	public List<CatComunUmf> getCatComunUmfList
			(@PathParam("cveDelegacion") String cveDelegacion, 
					@QueryParam("cveNivelAtencion") Long cveNivelAtencion, 
					@QueryParam("cveTipoUmf") Long cveTipoUmf) {
		try {
			return catalogosRemote.getCatComunUmfList(cveNivelAtencion, cveTipoUmf, cveDelegacion);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/externos/catComun/delegacion/{cveDelegacion}")
	@Produces({MediaType.APPLICATION_JSON})
	public CatComunDelegacion getCatComunDelegacion
			(@PathParam("cveDelegacion") String cveDelegacion){
		try {
			return catalogosRemote.getCatComunDelegacion(cveDelegacion);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/externos/catComun/delegacion")
	@Produces({MediaType.APPLICATION_JSON})
	public List<CatComunDelegacion> getCatComunDelegacionList(){
		try {
			return catalogosRemote.getCatComunDelegacionList();
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/externos/catComun/entidadFederativa/{cveEntidadFederativa}")
	@Produces({MediaType.APPLICATION_JSON})
	public CatComunEntidadFederativa getCatComunEntidadFederativa
			(@PathParam("cveEntidadFederativa") String cveEntidadFederativa){
		try {
			return catalogosRemote.getCatComunEntidadFederativa(cveEntidadFederativa);
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/externos/catComun/entidadFederativa")
	@Produces({MediaType.APPLICATION_JSON})
	public List<CatComunEntidadFederativa> getCatComunEntidadFederativaList(){
		try {
			return catalogosRemote.getCatComunEntidadFederativaList();
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	
	@GET
	@Path("/municipioImss/{cveMunicipioImss}")
	@Produces({MediaType.APPLICATION_JSON})
	public MunicipioImss getMunicipioImss(@PathParam("cveMunicipioImss") String cveMunicipioImss) {
		log.debug("entre al metodo getMunicipioImss " + cveMunicipioImss);
		try {
			return catalogosRemote.getMunicipioImss(cveMunicipioImss);	
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	@GET
	@Path("/municipioImss/cveDelegacionSubDelegacion/{cveDelegacionImss}/{cveSubDelegacionImss}")
	@Produces({MediaType.APPLICATION_JSON})
	public List <MunicipioImss> getMunicipioImssByDelegacionSubdelegacion(@PathParam("cveDelegacionImss") 
			Long cveDelegacionImss, @PathParam("cveSubDelegacionImss") Long cveSubDelegacionImss) {
		log.debug("entre al metodo getMunicipioImss  by del y subdel " + cveDelegacionImss + "subdel " + cveSubDelegacionImss);
		try {
			return catalogosRemote.getMunicipioImssByDelegacionSubdelegacion(cveDelegacionImss,cveSubDelegacionImss );	
		}catch(ServiciosRestException ex) {
			throw ComportamientosComunesUtil.getWebApplicationException(ex);
		}
	}
	
	public void getEjbCatalogoServices() {
		try {
			log.debug("llegue a instanciar el EJB de catalogo");
			catalogosRemote = EjbLocator.getCatalogoService();
			}catch (Exception e) {
				System.out.println("Ocurrio un error al quere recuperar el EJB" +  e.getMessage());
				throw new WebApplicationException(e.getCause(), Response.Status.INTERNAL_SERVER_ERROR);
			}
		}

	
	
	
}
