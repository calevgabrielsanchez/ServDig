package mx.gob.imss.cit.dacvass.servicios.externos.service.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import mx.gob.imss.cit.dacvass.servicios.externos.service.entity.catalogo.ICatComunServiceEntityLocal;
import mx.gob.imss.cit.dacvass.servicios.externos.service.entity.catalogo.ICatalogoServiceEntityLocal;
import mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces.ICatalogosServiciosDigitalesServiceRemote;
import mx.gob.imss.cit.dacvass.servicios.externos.service.util.ValidacionesComunesUtil;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CatalogosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.UmfServiceRemote;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clasificacion.FraccionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clase;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.digital.modelo.domicilio.Delegacion;
import mx.gob.imss.digital.modelo.domicilio.Subdelegacion;


@Stateless(name = "catalogosServiciosDigitalesService", mappedName = "catalogosServiciosDigitalesService")
public class CatalogosServiciosDigitalesService  extends AbstractServiceBusiness implements ICatalogosServiciosDigitalesServiceRemote{

	private static final Logger log = LoggerFactory
			.getLogger(CatalogosServiciosDigitalesService.class);

	@EJB(mappedName = "catalogosService")
	private CatalogosServiceRemote catalogosService;

	@EJB(mappedName="domicilioServiceBusiness")
	private DomicilioServiceBusinessRemote domicilioService;

	@EJB(mappedName="umfService")
	private UmfServiceRemote umfService;

	@EJB(mappedName="sujetoObligadoServiceBusiness")
	private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness;

	@EJB(mappedName="fraccionServiceBusiness")
	private FraccionServiceBusinessRemote fraccionServiceBusiness;

	@EJB
	private ICatalogoServiceEntityLocal catalogoServiceEntity;

	@EJB
	private ICatComunServiceEntityLocal catComunServiceEntity;




	@Override
	public Sexo getCatalogoSexo(Long idCatalogo) throws ServiciosRestException{
		ValidacionesComunesUtil.validaIdCatalogo(idCatalogo);
		try {
			//return ParserCatalogosServiciosToRest.parserSexoRest( catalogosService.getCatalogoSexo(idCatalogo));
			return catalogoServiceEntity.getCatalogoSexo(idCatalogo);
		}catch(ServiciosRestException ex) {
			throw ex;
		}catch(Exception ex) {
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}


	}
	@Override
	public List<Sexo> getCatalogoSexo()  throws ServiciosRestException{
		try {
			//return ParserCatalogosServiciosToRest.parserSexoListRest(catalogosService.getCatalogoSexo());
			return catalogoServiceEntity.getCatalogoSexo();
		}catch(ServiciosRestException ex) {
			throw ex;
		}catch(Exception ex) {
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}
	}

	@Override
	public Parentesco getCatalogoParentesco(Long idCatalogo)  throws ServiciosRestException{
		ValidacionesComunesUtil.validaIdCatalogo(idCatalogo);
		try {
			//return ParserCatalogosServiciosToRest.parserParentescoRest(catalogosService.getCatalogoParentesco(idCatalogo));
			return catalogoServiceEntity.getCatalogoParentesco(idCatalogo);
		}catch(ServiciosRestException ex) {
			throw ex;
		}catch(Exception ex) {
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}
	}

	@Override
	public List<Parentesco> getCatalogoParentesco()  throws ServiciosRestException{
		try {
			//return ParserCatalogosServiciosToRest.parserParentescoListRest(catalogosService.getCatalogoParentesco());
			return catalogoServiceEntity.getCatalogoParentesco();
		}catch(ServiciosRestException ex) {
			throw ex;
		}catch(Exception ex) {
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}
	}

	@Override
	public EntidadFederativa getCatalogoEntidadFed(String idCatalogo)  throws ServiciosRestException{
		ValidacionesComunesUtil.validaIdCatalogo(idCatalogo);
		try {
			//return ParserCatalogosServiciosToRest.parserEntidadFederativaRest(catalogosService.getCatalogoEntidadFed(idCatalogo));
			return catalogoServiceEntity.getCatalogoEntidadFed(idCatalogo);
		}catch(ServiciosRestException ex) {
			throw ex;
		}catch(Exception ex) {
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}
	}

	@Override
	public List<EntidadFederativa> getCatalogoEntidadFed() throws ServiciosRestException{
		try {
			//return ParserCatalogosServiciosToRest.parserEntidadFederativaListRest(catalogosService.getCatalogoEntidadFed());
			return catalogoServiceEntity.getCatalogoEntidadFed();
		}catch(ServiciosRestException ex) {
			throw ex;
		}catch(Exception ex) {
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}
	}

	@Override
	public EstadoCivil getCatalogoEstadoCivil(Long idCatalogo)  throws ServiciosRestException{
		ValidacionesComunesUtil.validaIdCatalogo(idCatalogo);
		try {
			//return ParserCatalogosServiciosToRest.parserEdoCivilRest(catalogosService.getCatalogoEstadoCivil(idCatalogo));
			return catalogoServiceEntity.getCatalogoEstadoCivil(idCatalogo);
		}catch(ServiciosRestException ex) {
			throw ex;
		}catch(Exception ex) {
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}
	}

	@Override
	public List<EstadoCivil> getCatalogoEstadoCivil()  throws ServiciosRestException{
		try {
			//return ParserCatalogosServiciosToRest.parserEdoCivilListRest(catalogosService.getCatalogoEstadoCivil());
			return catalogoServiceEntity.getCatalogoEstadoCivil();
		}catch(ServiciosRestException ex) {
			throw ex;
		}catch(Exception ex) {
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}
	}

	@Override
	public Pais getCatalogoPais(Long idCatalogo) throws ServiciosRestException{
		ValidacionesComunesUtil.validaIdCatalogo(idCatalogo);
		try {
			//return ParserCatalogosServiciosToRest.parserPaisRest(catalogosService.getPaisById(idCatalogo));
			return catalogoServiceEntity.getCatalogoPais(idCatalogo);
		}catch(ServiciosRestException ex) {
			throw ex;
		}catch(Exception ex) {
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}
	}
	@Override
	public List<Pais> getCatalogoPais() throws ServiciosRestException{
		try {
			//return ParserCatalogosServiciosToRest.parserPaisListRest(catalogosService.getCatalogoPais());
			return catalogoServiceEntity.getCatalogoPais();
		}catch(ServiciosRestException ex) {
			throw ex;
		}catch(Exception ex) {
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}
	}

	@Override
	public Turno getCatalogoTurnoByID(Long idCatalogo)throws ServiciosRestException{
		ValidacionesComunesUtil.validaIdCatalogo(idCatalogo);
		try {
			//return ParserCatalogosServiciosToRest.parserTurnoRest(catalogosService.getTurnoByID(idCatalogo));
			return catalogoServiceEntity.getCatalogoTurnoByID(idCatalogo);
		}catch(ServiciosRestException ex) {
			throw ex;
		}catch(Exception ex) {
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}

	}

	@Override
	public List<Turno> getCatalogoTurno() throws ServiciosRestException{
		try {
			//return ParserCatalogosServiciosToRest.parserTurnoListRest(catalogosService.getTurnos());
			return catalogoServiceEntity.getCatalogoTurno();
		}catch(ServiciosRestException ex) {
			throw ex;
		}catch(Exception ex) {
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}
	}


	@Override
	public Delegacion getCatalogoDelegacionById(Long idCatalogo) throws ServiciosRestException{
		ValidacionesComunesUtil.validaIdCatalogo(idCatalogo);
		try {
			//return ParserCatalogosServiciosToRest.parserDelegacionRest(domicilioService.obtenerDelegacionPorId(idCatalogo));
			return catalogoServiceEntity.getCatalogoDelegacionById(idCatalogo);
		}catch(ServiciosRestException ex) {
			throw ex;
		}catch(Exception ex) {
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}

	}



	@Override
	public List<Delegacion> getCatalogoDelegacion() throws ServiciosRestException{
		try {
			//return ParserCatalogosServiciosToRest.parserDelegacionListRest(domicilioService.findDelegacionesActivas());
			return catalogoServiceEntity.getCatalogoDelegacion();
		}catch(ServiciosRestException ex) {
			throw ex;
		}catch(Exception ex) {
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}
	}
	@Override 
	public Subdelegacion getCatalogoSubDelegacionById(Long idCatalogo) throws ServiciosRestException{
		ValidacionesComunesUtil.validaIdCatalogo(idCatalogo);
		try {
			//return ParserCatalogosServiciosToRest.parserSubDelegacionRest(domicilioService.obtenerSubdelegacionPorId(idCatalogo));
			return catalogoServiceEntity.getCatalogoSubDelegacionById(idCatalogo);
		}catch(ServiciosRestException ex) {
			throw ex;
		}catch(Exception ex) {
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}

	}

	@Override
	public List<Subdelegacion> getCatalogoSubDelegacionByIdDelegacion(Long idCatalogo) throws ServiciosRestException{
		ValidacionesComunesUtil.validaIdCatalogo(idCatalogo);
		try {
			//return ParserCatalogosServiciosToRest.parserSubDelegacionListRest(domicilioService.findSubDelegacionesActivas(idCatalogo));
			return catalogoServiceEntity.getCatalogoSubDelegacionByIdDelegacion(idCatalogo);
		}catch(ServiciosRestException ex) {
			throw ex;
		}catch(Exception ex) {
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}
	}



	@Override
	public List<UnidadMedicaFamiliar> getCatalogoUMfBySubdelegacionNivelAtencion(Long idCatalogo, Long idNivelAtencion) throws ServiciosRestException{
		ValidacionesComunesUtil.validaIdCatalogo(idCatalogo);
		try {
			//return  umfService.findUnidadesBySubdelegacionNivelAtencion(idCatalogo, idNivelAtencion );
			return catalogoServiceEntity.getCatalogoUMfBySubdelegacionNivelAtencion(idCatalogo, idNivelAtencion );
		}catch(ServiciosRestException ex) {
			throw ex;
		}catch(Exception ex) {
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}
	}

	@Override
	public UnidadMedicaFamiliar getCatalogoUMfById(Long idCatalogo) throws ServiciosRestException{
		ValidacionesComunesUtil.validaIdCatalogo(idCatalogo);
		try {
			//return  umfService.getUnidadMedicaFamiliarById(idCatalogo);
			return catalogoServiceEntity.getCatalogoUMfById(idCatalogo);
		}catch(ServiciosRestException ex) {
			throw ex;
		}catch(Exception ex) {
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}
	}


	@Override
	public Modalidad getCatalogoModalidadByCveModalidad(String idCatalogo) throws ServiciosRestException{
		ValidacionesComunesUtil.validaIdCatalogo(idCatalogo);
		try {
			///Modalidad modalidad =  sujetoObligadoServiceBusiness.getModalidad(idCatalogo);
			return catalogoServiceEntity.getCatalogoModalidadByCveModalidad(idCatalogo);
		}catch(ServiciosRestException ex) {
			throw ex;
		}catch(Exception ex) {
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}
	}

	@Override
	public List<Modalidad> getCatalogoModalidad() throws ServiciosRestException{
		try {

			//return  sujetoObligadoServiceBusiness.getModalidadades();
			return catalogoServiceEntity.getCatalogoModalidad();
		}catch(ServiciosRestException ex) {
			throw ex;
		}catch(Exception ex) {

			log.error("ocurrio un error al consultar el catalogo modalidad" + ex.getMessage());
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}
	}




	@Override
	public List<Clase> getCatalogoClase() throws ServiciosRestException {
		try {
			//List<Clase> lstClase =   fraccionServiceBusiness.consultaCatalogoClase();
			return catalogoServiceEntity.getCatalogoClase();
		}catch(ServiciosRestException ex) {
			throw ex;	
		}catch(Exception ex) {
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}
	}
	@Override
	public Clase getCatalogoClaseByCveClase(Long idCatalogo) throws ServiciosRestException {
		ValidacionesComunesUtil.validaIdCatalogo(idCatalogo);
		try {
			//Clase clase =   fraccionServiceBusiness.consultaCatalogoClaseById(idCatalogo);
			return catalogoServiceEntity.getCatalogoClaseByCveClase(idCatalogo);
		}catch(ServiciosRestException ex) {
			throw ex;	
		}catch(Exception ex) {
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}


	}
	@Override
	public List<Fraccion> getCatalogoFraccionByIdClase(Long idCatalogo) throws ServiciosRestException {
		ValidacionesComunesUtil.validaIdCatalogo(idCatalogo);
		try {
			//List<Fraccion> lstFraccion =   fraccionServiceBusiness.consultaCatalogoFraccionByIdClase(idCatalogo);
			return catalogoServiceEntity.getCatalogoFraccionByIdClase(idCatalogo);
		}catch(ServiciosRestException ex) {
			throw ex;	
		}catch(Exception ex) {
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}
	}
	@Override
	public Fraccion getCatalogoFraccionByCveFraccion(Long idCatalogo) throws ServiciosRestException {
		ValidacionesComunesUtil.validaIdCatalogo(idCatalogo);
		try {
			//Fraccion fraccion =  fraccionServiceBusiness.consultaPorId(idCatalogo);
			return catalogoServiceEntity.getCatalogoFraccionByCveFraccion(idCatalogo);
		}catch(ServiciosRestException ex) {
			throw ex;	
		}catch(Exception ex) {
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}

	}

	@Override
	public List<TipoPersona> getCatalogoTipoPersona() throws ServiciosRestException {
		try {
			return catalogoServiceEntity.getCatalogoTipoPersona();
		}catch(ServiciosRestException ex) {
			throw ex;	
		}catch(Exception ex) {
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}

	}

	@Override
	public TipoPersona getCatalogoTipoPersonaByIdTipoPersona(Long idCatalogo) throws ServiciosRestException {
		ValidacionesComunesUtil.validaIdCatalogo(idCatalogo);
		try {

			return catalogoServiceEntity.getCatalogoTipoPersonaByIdTipoPersona(idCatalogo);
		}catch(ServiciosRestException ex) {
			throw ex;	
		}catch(Exception ex) {
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}	

	}

	@Override
	public List<Division> getCatalogoDivision() throws ServiciosRestException {
		try {
			//List<Division> lstDivision =   fraccionServiceBusiness.consultaCatalogoDivision();
			return catalogoServiceEntity.getCatalogoDivision();
		}catch(ServiciosRestException ex) {
			throw ex;	
		}catch(Exception ex) {
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}
	}

	@Override
	public List<Grupo> getCatalogoGrupoByIdDivision(Long idCatalogo) throws ServiciosRestException {
		ValidacionesComunesUtil.validaIdCatalogo(idCatalogo);
		try {
			//List<Grupo> lstGrupo =   fraccionServiceBusiness.consultaCatalogoGrupoByIdDivision(idCatalogo);
			return catalogoServiceEntity.getCatalogoGrupoByIdDivision(idCatalogo);
		}catch(ServiciosRestException ex) {
			throw ex;	
		}catch(Exception ex) {
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}
	}

	@Override
	public List<Fraccion> getCatalogoFraccionByIdGrupo(Long idCatalogo) throws ServiciosRestException {
		log.debug("llegue al serivio de getCatalogoFraccionByIdGrupo con el valor " + idCatalogo );
		ValidacionesComunesUtil.validaIdCatalogo(idCatalogo);
		try {

			//List<Fraccion> lstFraccion =   fraccionServiceBusiness.consultaCatalogoFraccionByIdGrupo(idCatalogo);
			return catalogoServiceEntity.getCatalogoFraccionByIdGrupo(idCatalogo);
		}catch(ServiciosRestException ex) {
			throw ex;	
		}catch(Exception ex) {
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}
	}


	@Override
	public List<Fraccion> getCatalogoFraccionConClaseActivaByIdGrupo(Long idCatalogo) throws ServiciosRestException {
		log.debug("llegue al serivio de getCatalogoFraccionByIdGrupo con el valor " + idCatalogo );
		ValidacionesComunesUtil.validaIdCatalogo(idCatalogo);
		try {
			//List<Fraccion> lstFraccion =   fraccionServiceBusiness.consultaCatalogoFraccionConClaseActivasByIdGrupo(idCatalogo);
			return catalogoServiceEntity.getCatalogoFraccionConClaseActivaByIdGrupo(idCatalogo);
		}catch(ServiciosRestException ex) {
			log.error("error cachado de serviciso rest " , ex);
			throw ex;	
		}catch(Exception ex) {
			log.error("error no cachado de servicios " , ex);
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}
	}

	@Override
	public List<Subdelegacion> getSubDelegacionesPorCodigoPostal(String cp)
			throws ServiciosRestException {
		log.debug(
				"llegue al serivio de getSubDelegacionesPorCodigoPostal con el valor {}",
				cp);
		ValidacionesComunesUtil.validaIdCatalogo(cp);
		try {
			return catalogoServiceEntity.getSubDelegacionesPorCodigoPostal(cp);
		} catch (ServiciosRestException ex) {
			log.error("error cachado de serviciso rest ", ex);
			throw ex;
		} catch (Exception ex) {
			log.error("error no cachado de servicios ", ex);
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}
	}
	@Override
	public List<MunicipioInegi> getCatalogoMunicipioInegi(String cveEntidadFed) throws ServiciosRestException {
		log.debug("llegue al servicio de getCatalogoMunicipioInegi con el valor {}", cveEntidadFed);
		ValidacionesComunesUtil.validaIdCatalogo(cveEntidadFed);
		try {
			return catalogoServiceEntity.getCatalogoMunicipioInegi(cveEntidadFed);
		} catch (Exception ex) {
			log.error("error no cachado de servicios getCatalogoMunicipioInegi ", ex);
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}
	}
	@Override
	public MunicipioInegi getMunicipioInegi(String cveEntidadFed, String cveMunicipioInegi)
			throws ServiciosRestException {
		log.debug("llegue al servicio de getMunicipioInegi con el valor {}", cveEntidadFed );
		ValidacionesComunesUtil.validaIdCatalogo(cveEntidadFed);
		ValidacionesComunesUtil.validaIdCatalogo(cveMunicipioInegi);
		try {
			return catalogoServiceEntity.getMunicipioInegi(cveEntidadFed, cveMunicipioInegi);
		} catch (Exception ex) {
			log.error("error no cachado de servicios ", ex);
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}
	}

	
	@Override
	public List<CatComunUmf> getCatComunUmfList(Long cveNivelAtencion, Long cveTipoUmf,
			String cveDelegacion) throws ServiciosRestException {
		log.debug("llegue al servicio de getCatComunUmfList co cveEntidadFederativa{}", cveDelegacion );
		ValidacionesComunesUtil.validaIdCatalogo(cveDelegacion);
		try {
			return catComunServiceEntity.getCatComunUmfList(cveNivelAtencion, cveTipoUmf, cveDelegacion);
		}catch(Exception ex) {
			log.error("ocurrio un error al consulaar el catalogo getCatComunUmfList", ex);
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}
	}
	
	@Override
	public CatComunUmf getCatComunUmf(String cvePresupuestal) throws ServiciosRestException {
		log.debug("llegue al servicio de getCatComunUmf con cvePresupuesta {}", cvePresupuestal );
		ValidacionesComunesUtil.validaIdCatalogo(cvePresupuestal);
		try {
			return catComunServiceEntity.getCatComunUmf(cvePresupuestal);
		}catch(Exception ex) {
			log.error("ocurrio un error al consulaar el catalogo getCatComunUmf", ex);
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}

	}
	
	@Override
	public CatComunDelegacion getCatComunDelegacion(String cveDelegacion) throws ServiciosRestException {
		log.debug("llegue al servicio de getCatComunDelegacion con cveDelegacion {}", cveDelegacion );
		ValidacionesComunesUtil.validaIdCatalogo(cveDelegacion);
		try {
			return catComunServiceEntity.getCatComunDelegacion(cveDelegacion);
		}catch(Exception ex) {
			log.error("ocurrio un error al consulaar el catalogo getCatComunDelegacion", ex);
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}
	}
	@Override
	public List<CatComunDelegacion> getCatComunDelegacionList() throws ServiciosRestException {
		log.debug("llegue al servicio de getCatComunDelegacionList {}" );
		try {
			return catComunServiceEntity.getCatComunDelegacionList();
		}catch(Exception ex) {
			log.error("ocurrio un error al consulaar el catalogo getCatComunDelegacionList", ex);
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}
	}
	@Override
	public CatComunEntidadFederativa getCatComunEntidadFederativa(String cveEntidadFederativa)
			throws ServiciosRestException {
		log.debug("llegue al servicio de getCatComunEntidadFederativa con cveEntidadFederativa {}", cveEntidadFederativa );
		ValidacionesComunesUtil.validaIdCatalogo(cveEntidadFederativa);
		try {
			return catComunServiceEntity.getCatComunEntidadFederativa(cveEntidadFederativa);
		}catch(Exception ex) {
			log.error("ocurrio un error al consulaar el catalogo getCatComunEntidadFederativa", ex);
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}
	}
	@Override
	public List<CatComunEntidadFederativa> getCatComunEntidadFederativaList() throws ServiciosRestException {
		log.debug("llegue al servicio de getCatComunEntidadFederativaList" );
		
		try {
			return catComunServiceEntity.getCatComunEntidadFederativaList();
		}catch(Exception ex) {
			log.error("ocurrio un error al consulaar el catalogo getCatComunEntidadFederativaList", ex);
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}
	}
	
	@Override
	public MunicipioImss getMunicipioImss(String cveMunicipioImss)throws ServiciosRestException{
		log.debug("llegue al servicio de getMunicipioImss con cveMunicipioImss {}", cveMunicipioImss );
		ValidacionesComunesUtil.validaIdCatalogo(cveMunicipioImss);
		cveMunicipioImss =cveMunicipioImss.toUpperCase();
		try {
			return catalogoServiceEntity.getMunicipioImss(cveMunicipioImss);
		}catch(Exception ex) {
			log.error("ocurrio un error al consulaar el catalogo getMunicipioImss", ex);
			throw ValidacionesComunesUtil.getExcepcionConsultaCatalogo(ex);
		}
	}


}
