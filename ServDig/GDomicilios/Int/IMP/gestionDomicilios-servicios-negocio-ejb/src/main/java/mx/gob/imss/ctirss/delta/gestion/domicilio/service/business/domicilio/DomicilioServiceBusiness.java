/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:DomicilioServiceBusiness.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.domicilio.service.business.domicilio
 *  @Fecha:02/03/2012
 */
package mx.gob.imss.ctirss.delta.gestion.domicilio.service.business.domicilio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.UmfServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.CodigoSinUmfException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.domicilio.AsentamientoNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.AsociarDomicilioException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.LocalidadNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.MunicipioImssNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.SubDelegacionNoLocalizadaException;
import mx.gob.imss.ctirss.delta.exception.domicilio.UmfNoLocalizadaException;
import mx.gob.imss.ctirss.delta.exception.domicilio.VialidadesNoLocalizadasException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.business.DomicilioParserServiceLocal;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.dao.DelegacionDaoLocal;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.entity.domicilio.DomicilioServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.utility.DomicilioServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.MunicipioIMSS;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoVialidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Vialidad;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.digital.modelo.domicilio.Camino;
import mx.gob.imss.digital.modelo.domicilio.Carretera;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

/**
 * @author Lucio Duran Silva 
 * @category ServiceBusiness
 * 
 */

@Stateless(name="domicilioServiceBusiness" ,mappedName="domicilioServiceBusiness" )
public class DomicilioServiceBusiness extends AbstractServiceBusiness implements
		DomicilioServiceBusinessRemote {

	
	
	@EJB
	private DomicilioServiceEntityLocal entity;
	@EJB
	private DelegacionDaoLocal delegacionDaoLocal;
	@EJB( name = "grupoFamiliarService", mappedName = "grupoFamiliarService")
    private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	@EJB
	private DomicilioParserServiceLocal domicilioParserServiceLocal;
    @EJB
    private UmfServiceRemote umfServiceRemote;
	@EJB
    private DomicilioServiceUtilityLocal domicilioServiceUtility;
	

	@Override
	public List<Asentamiento> getAsentamientoPorCodigoPosta(CodigoPostal codigo)
			throws DomicilioNoLocalizadoException {
		
		this.log.debug("getAsentamientoPorCodigoPosta [" + codigo + "]");
		
		//Obtenemos los asentamientos
		List<Asentamiento> asentamientos = this.entity.getAsentamientoPorCodigoPosta(codigo);
		//Se valida que exista al menos un asentamiento asociado al cp.
		if(asentamientos == null || asentamientos.isEmpty()){
			// Se lanza el error en caso de que no existan asentamientos.
			throw new DomicilioNoLocalizadoException();
		}
		return asentamientos;
	}

	@Override
	public List<Asentamiento> getAsentamientoPorMunicipio(Municipio municipio)
			throws DomicilioNoLocalizadoException {
		return entity.getAsentamientoPorMunicipio(municipio);
	}

	@Override
	public List<Localidad> getLocalidadesPorMunicipio(Municipio municipio)
			throws DomicilioNoLocalizadoException {
		return entity.getLocalidadesPorMunicipio(municipio);
	}

	@Override
	public Localidad getLocalidad(Localidad localidad) throws LocalidadNoLocalizadoException {
		return entity.getLocalidad(localidad);
	}

	@Override
	public Asentamiento getAsentamiento(Asentamiento asentamiento)
			throws AsentamientoNoLocalizadoException,
			DomicilioNoLocalizadoException {
		
		CodigoPostal codigo= asentamiento.getCodigoPostal();
		//Obtenemos el asentamiento
		asentamiento =this.entity.getAsentamiento(asentamiento);
		
		if(codigo != null && StringUtils.isNotEmpty(codigo.getCodigoPostal()))
			asentamiento.setCodigoPostal(codigo);
		else
			asentamiento.setCodigoPostal(this.entity.getCodigoPostalDeAsentamiento(asentamiento));
		return asentamiento;
	}

	@Override
	public Domicilio registrarDomicilio(Domicilio domicilio)
			throws DomicilioNoValidoException {
		this.log.debug(" Servicio de Negocio de Registrar Domicilio [" + domicilio + "]");
		/*Validando si contiene los datos minimos requeridos*/
		if(domicilio == null){
			throw new DomicilioNoValidoException();
		}
		domicilio= this.entity.guardarDomicilio(domicilio);
		return domicilio;
	}

	@Override
	public Domicilio consultarDomicilio(Domicilio domicilio)
			throws DomicilioNoLocalizadoException {
		this.log.debug(" Servicio de Negocio de Consultar Domicilio [" + domicilio + "]");
		domicilio = this.entity.consultarDomicilio(domicilio);
		return domicilio;
	}

	@Override
	public List<Vialidad> getVialidades(Localidad localidad)
			throws VialidadesNoLocalizadasException {
		this.log.debug(" Servicio de consulta de vialidades por localidad" + localidad);
		return this.entity.getVialidades(localidad);
	}
	
	
	/**
	 * 
	 * @param localidad
	 * @param tipoVialidad
	 * @return
	 * @throws VialidadesNoLocalizadasException
	 */
	public List<Vialidad> getVialidadesPorTipoVialidad(Localidad localidad, TipoVialidad tipoVialidad)
			throws VialidadesNoLocalizadasException {
		this.log.debug(" Servicio de consulta de vialidades por localidad y por tipo de vialidad " + localidad +"" + tipoVialidad);
		return this.entity.getVialidadesPorTipoVialidad(localidad, tipoVialidad);
	}
	
	/**
	 * 
	 * @param vialidad
	 * @return
	 * @throws VialidadesNoLocalizadasException
	 */
	public Vialidad getVialidad( Vialidad vialidad )throws VialidadesNoLocalizadasException{
		this.log.debug(" Servicio de consulta de Vialidad por Clave de vialidad" + vialidad);
		return this.entity.getVialidad(vialidad);  
	}

	@Override
	public Localidad getLocalidadByVialidad(Vialidad vialidad) throws DomicilioNoValidoException {
		this.log.debug(" Servicio de consulta de Municipio y Localidad por Clave de vialidad" + vialidad);
		return this.entity.getLocalidadByVialidad(vialidad);
	}

	@Override
	public List<EntidadFederativa> findEstadosByDelegacion(Long idDelegacion) {

		List<EntidadFederativa> estados = null;
		estados = delegacionDaoLocal.findEntidaFederativaByDelegacion(idDelegacion);
		return estados;
	}

	@Override
	public List<Municipio> findMunicipiosByDelegacionEstado(Long idDelegacion,
			Long idEstado) {
		List<Municipio> municipios = null;
		municipios = delegacionDaoLocal.findMunicipiosByDelegacionEstado(idDelegacion, idEstado);
		return municipios;
	}
	
	@Override
	public List<Asentamiento> finAsentamientosByDelegacionEstadoMunicipio(
			Long idDelegacion, Long idEstado, Long idMunicipio) {
		List<Asentamiento> asentamientos = null;
		asentamientos = delegacionDaoLocal.findAsentamientoByDelegacionMunicipioEntidad(idDelegacion, idMunicipio, idEstado);
		return asentamientos;
	}

	@Override
	public List<Asentamiento> findAsentamientosByUmf(Long idUmf) {
		List<Asentamiento> asentamientos = null;
		asentamientos = delegacionDaoLocal.findAsentamientosByUmf(idUmf);
		return asentamientos;
	}

	@Override
	public Map<String, Object> findAsentamientosByUmfCp(Long idUmfUsuario, Long idUmfPersona,
			String codigoPostal, Long tipoTramite) throws DomicilioNoLocalizadoException{
		Map<String, Object> result = new HashMap<String, Object>();
		List<Asentamiento> asentamientos = null;
		
		final String KEY_UMF = "idUmf";
		final String KEY_CAMBIO_CLINICA = "cambioClinica";
		final String KEY_ASENTAMIENTOS = "asentamientos";
		
		asentamientos = delegacionDaoLocal.findAsentamientosByUmfCodPos(idUmfPersona, codigoPostal);
		
		if(asentamientos == null || asentamientos.isEmpty()) {
			log.debug("Los asentamientos para la umf de la persona son estan vacios");
			
			if(!idUmfUsuario.equals(idUmfPersona)) {
				log.debug("El id de la umf de la persona y del usuario son distintos");
				asentamientos = delegacionDaoLocal.findAsentamientosByUmfCodPos(idUmfUsuario, codigoPostal);
				
				if(asentamientos == null || asentamientos.isEmpty()) {
					log.debug("Los asentamientos para la umf del usuario son nulos o vacios, se lanza exception");
					lanzarException(tipoTramite);
				}
				
				log.debug("Se ubico al menos un asentamientos que coincide con la umf del usuario, es cambio de clinica");
				result.put(KEY_UMF, idUmfUsuario);
				result.put(KEY_CAMBIO_CLINICA, true);
				result.put(KEY_ASENTAMIENTOS, asentamientos);
			} else {
				log.debug("El id de la umf del asegurado y persona son iguales, se lanza exception");
				lanzarException(tipoTramite);
			}
		} else {
			log.debug("Los asentamientos para el id umf de la persona no son vacios ni nulos");
			result.put(KEY_UMF, idUmfPersona);
			result.put(KEY_CAMBIO_CLINICA, false);
			result.put(KEY_ASENTAMIENTOS, asentamientos);
		}

		return result;
	}
	
	private void lanzarException(Long tipoTramite) throws DomicilioNoLocalizadoException{
		
		if(tipoTramite.equals(TipoTramiteEnum.ASIGNACION_DE_DOMICILIO_PARTICULAR_DH.getCodigo().longValue())) {
			throw new DomicilioNoLocalizadoException("El c\u00F3digo postal no se encuentra dentro de la circunscripci\u00F3n de la UMF que tiene asignada el asegurado/pensionado" +
					" ni en la UMF actual.");
		}else if((tipoTramite.intValue() >= TipoTramiteEnum.REGISTRO_ASEGURADO.getCodigo() &&
				tipoTramite.intValue() <= TipoTramiteEnum.REGISTRO_PADRES.getCodigo()) ||
				tipoTramite.equals(TipoTramiteEnum.MODIFICACION_DE_DERECHOHABIENTE.getCodigo().longValue())) { //se toman en cuenta todos los tramite de registro
			throw new DomicilioNoLocalizadoException("El c\u00F3digo postal no se encuentra dentro de la circunscripci\u00F3n de la UMF.");
		} else {
			throw new DomicilioNoLocalizadoException("El c\u00F3digo postal no se encuentra dentro de la misma circunscripci\u00F3n del asegurado / pensionado.");
		}
		
	}

	@Override
	public List<Asentamiento> getAsentamientosByDelegacionCodigoPostal(
			Long idDelegacion, String codigoPostal)
			throws DomicilioNoLocalizadoException {
		
		List<Asentamiento> asentamientos = null;
		
		List<Long> idDelegaciones = new ArrayList<Long>();
		
		idDelegaciones.add(idDelegacion);
		asentamientos = delegacionDaoLocal.findAsentamientoByDelegacionCp(idDelegaciones, codigoPostal);
		
		if(asentamientos == null || asentamientos.isEmpty())
			throw new DomicilioNoLocalizadoException("El codigo postal no se encuentra dentro de la misma circunscripcion de la Delegacion Actual");
		
		return asentamientos;
	}
	
	

	@Override
	public List<Asentamiento> findAsentamientosByDelegacionInUmfOrigenUmfDestino(
			Long idDelegacion, String cp, Long idUmfOrigen, Long idUmfDestino) throws DomicilioNoLocalizadoException{

		List<Asentamiento> asentamientos = null;
		List<Long> delegacion = Arrays.asList(new Long[]{15L,16L,39L,40L});
		
		List<Long> idDelegaciones = null;
		if(idDelegacion != null && !idDelegacion.equals(0L)) {
			if(delegacion.contains(idDelegacion)) {
				idDelegaciones = delegacion;
			} else {
				idDelegaciones = new ArrayList<Long>();
				idDelegaciones.add(idDelegacion);
			}
		}
		
		asentamientos = delegacionDaoLocal.findAsentamientoByDelegacionCpYUmfsOrigenDestino(idDelegaciones, cp, idUmfOrigen, idUmfDestino);
		
		if(asentamientos == null || asentamientos.isEmpty()) {
			String mensajeError = "";
			
			if(idDelegacion != null && !idDelegacion.equals(0L)){
				mensajeError = "El c\u00F3digo postal no se encuentra dentro de la misma circunscripci\u00F3n de la delegaci\u00F3n actual"
						+ " de adscripci\u00F3n del derechohabiente.";
			} else {
				mensajeError = "El c\u00F3digo postal no se encuentra dentro de la misma circunscripci\u00F3n de la UMF actual";
			}
			throw new DomicilioNoLocalizadoException(mensajeError);
		}
		
		return asentamientos;
	}

	@Override
	public DomicilioFiscal registrarDomicilioFiscal(
			DomicilioFiscal domicilioFiscal) throws DomicilioNoValidoException {
		
		this.log.debug(" Servicio de Negocio de Registrar Domicilio Fiscal [" + domicilioFiscal + "]");
		/*Validando si contiene los datos minimos requeridos*/
		if(domicilioFiscal == null){
			throw new DomicilioNoValidoException();
		}
		domicilioFiscal= this.entity.guardarDomicilioFiscal(domicilioFiscal);
		this.log.debug("al salir del mentodo la clave es desde el servicio [" +domicilioFiscal.getClave()+"]" );
		
		return domicilioFiscal;
	}

	@Override
	public DomicilioFiscal consultarDomicilioFiscal(
			DomicilioFiscal domicilioFiscal)
			throws DomicilioNoLocalizadoException {
		
		this.log.debug(" Servicio de Negocio de Consultar Domicilio Fiscal [" + domicilioFiscal + "]");
	
		domicilioFiscal = this.entity.consultarDomicilioFiscal(domicilioFiscal);
		
		return domicilioFiscal;
	}

	@Override
	public DomicilioFiscal consultarDomicilioFiscalPersona(Persona persona)
			throws DomicilioNoLocalizadoException {

		this.log.debug(" Servicio de Negocio de Consultar Domicilio Fiscal de la persona ["
				+ persona.getIdPersona() + "]");
		
		return this.entity.consultarDomicilioFiscalPersona(persona);
	}

	@Override
	public List<Domicilio> consultarDomiciliosPersonaFisica(Persona persona)
			throws DomicilioNoLocalizadoException {
		
		this.log.debug(" Servicio de Negocio de Consultar Domicilios de la persona ["
				+ persona.getIdPersona() + "]");
		
		return this.entity.consultarDomiciliosPersonaFisica(persona);
	}
	
	@Override
	public List<Domicilio> consultarDomiciliosPersonaMoral(Persona persona)
			throws DomicilioNoLocalizadoException {
		
		this.log.debug(" Servicio de Negocio de Consultar Domicilios de la persona ["
				+ persona.getIdPersona() + "]");
		
		return this.entity.consultarDomiciliosPersonaMoral(persona);
	}

	@Override
	public List<Domicilio> consultarDomiciliosModificablesPersonaFisica(
			Persona persona) throws DomicilioNoLocalizadoException {
		
		this.log.debug(" Servicio de Negocio de Consultar Domicilios modificables de la persona ["
				+ persona.getIdPersona() + "]");
			
		return this.entity.consultarDomiciliosModificablesPersonaFisica(persona);
	}

	@Override
	public void modificarDomicilioFiscal(
			DomicilioFiscal domicilioFiscal) throws DomicilioNoValidoException {
		
		this.entity.modificarDomicilioFiscal(domicilioFiscal);
	}

	@Override
	public Long asociarDomicilioPersona(Domicilio domicilio,
			Long cvePersona) throws DomicilioNoValidoException {
		return this.entity.asociarDomicilioPersona(domicilio, cvePersona);
	}
	
	@Override
	public void asociarDomicilioPersonaMoral(Domicilio domicilio,
			Long cveMoral) throws DomicilioNoValidoException {
		this.entity.asociarDomicilioPersonaMoral(domicilio, cveMoral);
	}
	
	@Override
	public long asociarDomicilioFiscalPersonaFisica(Integer cveDomicilioFiscal,
			Long cveFisica) throws AsociarDomicilioException {
		long cveIdPfdomFiscal = this.entity.asociarDomicilioFiscalPersonaFisica(cveDomicilioFiscal, cveFisica);
		
		return cveIdPfdomFiscal;
	}

	@Override
	public long asociarDomicilioFiscalPersonaMoral(Integer cveDomicilioFiscal,
			Long cveMoral) throws AsociarDomicilioException {
		long cveIdPmdomFiscal = this.entity.asociarDomicilioFiscalPersonaMoral(cveDomicilioFiscal, cveMoral);
		
		return cveIdPmdomFiscal;
	}
	
	@Override
	public void modificarDomicilio(Domicilio domicilio)
			throws TransformacionException {

		this.entity.modificarDomicilio(domicilio);
	}
	
	@Override
	public void desasociarEliminarDomicilioPersona(Long cveDomicilio,
			Long cvePersona) throws AsociarDomicilioException {
		this.entity.desasociarEliminarDomicilioPersona(cveDomicilio, cvePersona);
	}
	
	@Override
	public void desasociarEliminarDomicilioPersonaMoral(Long cveDomicilio,
			Long cvePersonaMoral) throws AsociarDomicilioException {
		this.entity.desasociarEliminarDomicilioPersonaMoral(cveDomicilio, cvePersonaMoral);
	}
	
	@Override
	public List<Domicilio> obtenerDomiciliosPersonaPorTipo(Persona persona,
			List<Long> tiposDomicilio) throws DomicilioNoLocalizadoException {
		
		List<Domicilio> domicilios = null;
		
		if (persona == null) {
			throw new DomicilioNoLocalizadoException(
					"La persona es requerida para realizar la b?squeda de los domicilios");
		} else if (persona.getIdPersona() == null) {
			throw new DomicilioNoLocalizadoException(
					"El id de la persona es requerido para realizar la b?squeda de los domicilios");
		} else if (persona.getTipoPersona() == null || persona.getTipoPersona().getIdTipoPersona() == null) {
			throw new DomicilioNoLocalizadoException(
					"El tipo de la persona es requerido para realizar la b?squeda de los domicilios");
		} else if (tiposDomicilio == null || tiposDomicilio.isEmpty()) {
			throw new DomicilioNoLocalizadoException(
					"Los tipos de domicilios es requerido para realizar la b?squeda de los domicilios");
		} else {
			if (persona.getTipoPersona().getIdTipoPersona().longValue() == TipoPersonaEnum.FISICA.getId()) {
				domicilios = this.entity.consultarDomiciliosPersonaFisicaPorTipo(persona, tiposDomicilio);
			} else {
				domicilios = this.entity.consultarDomiciliosPersonaMoralPorTipo(persona, tiposDomicilio);
			}
		}
		
		return domicilios;
	}
	
	/**
	 * Metodo que recupera un lista municipios imss junto con la subdelegacion y delegacion
	 * @param objMunicipio
	 * @param codigoPostal
	 * @return
	 * @throws MunicipioImssNoLocalizadoException
	 */
	@Override
	public List<MunicipioIMSS> getMunicipioIMSSbyEstadoMunCP(Municipio objMunicipio, String codigoPostal)
			throws MunicipioImssNoLocalizadoException {
		
		if(codigoPostal == null || codigoPostal.isEmpty()){
			throw new IllegalArgumentException();
		}
		return this.entity.getMunicipioIMSSbyEstadoMunCP(objMunicipio, codigoPostal);
	}
	
	
	/**
	 * Metodo que recupera un lista municipios imss junto con la subdelegacion y delegacion
	 * @param objMunicipio
	 * @param codigoPostal
	 * @return
	 * @throws MunicipioImssNoLocalizadoException
	 */
	@Override
	public MunicipioIMSS getMunicipioIMSSbyEstadoMunCP(String cveEnt, String cveMun, String codigoPostal)
			throws MunicipioImssNoLocalizadoException {
		
		if(cveEnt == null || cveMun == null || codigoPostal == null ){
			throw new IllegalArgumentException();
		}
		
		Municipio mun= new Municipio();
		mun.setEntidadFederativa(new  EntidadFederativa() );
		mun.setClave(cveMun);
		mun.getEntidadFederativa().setClave(cveEnt);
		
		return this.entity.getMunicipioIMSSbyEstadoMunCP(mun, codigoPostal).get(0);
	}
	
	/**
	 * Servicio que conuslta una las umf asociadas a un asentamiento en caso de
	 * no encontrar consulta una UMF defaul a nivel delegacion - subdelegacion
	 * en caso de no econtrar registros arroja una excepcion
	 */
	@Override
	public List<UnidadMedicaFamiliar> getUmfByAsentamientoDomicilio(
			Asentamiento asentamiento) throws UmfNoLocalizadaException {

		this.log.debug("Se van a consultar las UMF para el asentamiento "
				+ asentamiento);

		if (asentamiento == null) {
			throw new IllegalArgumentException();
		}

		List<UnidadMedicaFamiliar> lstUmf = null;

		try {
			// Se manda un false para no considerar las UMF se CFE
			lstUmf = this.umfServiceRemote.findUmfsByAsentamiento(asentamiento,
					false);
		} catch (CodigoSinUmfException e) {
			log.error("Error al obtener UMF para el asentamiento "
					+ asentamiento, e);
		} catch (DerechohabientesBusinessException e) {
			log.error("Error al obtener UMF para el asentamiento "
					+ asentamiento, e);
		} catch (Exception e) {
			log.error("Error al obtener UMF para el asentamiento "
					+ asentamiento, e);	
		}

		if (CollectionUtils.isEmpty(lstUmf)) {
			log.warn("No se obtuvieron UMF para el asentamiento "
					+ asentamiento
					+ ", se procede a obtener la UMF por default");

			lstUmf = new ArrayList<UnidadMedicaFamiliar>();
			lstUmf.add(this.entity.getUMFDefaultPorCP((asentamiento
					.getCodigoPostal().getCodigoPostal())));
		}
		
		this.log.debug("Se encontraron " + lstUmf.size()
				+ " UMF para el asentamiento " + asentamiento);

		return lstUmf;

	}
	
	/**
	 * Metodo que recupera una lista de UMF asociadas a un codigo postal a 5
	 * digitos Servicio que conuslta una las umf asociadas a un asentamiento en
	 * caso de no encontrar consulta una UMF defaul a nivel delegacion -
	 * subdelegacion en caso de no econtrar registros arroja una excepcion
	 * 
	 * @param String codigoPostal
	 * @return lista de UMF con los datos de la delegacion y subdelegacion a la
	 *         que pertenece llenos
	 * @throws UmfNoLocalizadaException
	 */
	
	@Override
	public List<UnidadMedicaFamiliar> getUmfByCodigoPostal(String codigoPostal)
			throws UmfNoLocalizadaException {
		
		this.log.debug("Se van a consultar las UMF para el codigo postal " + codigoPostal);

		if (codigoPostal == null) {
			throw new IllegalArgumentException();
		}

		List<UnidadMedicaFamiliar> lstUmf = null;
		
		try {
			// Se manda un 1 para no considerar las UMF se CFE
			lstUmf = this.umfServiceRemote.findUmfByCodigoPostal(codigoPostal, 1);
		} catch (CodigoSinUmfException e) {
			log.error("Error al obtener UMF para el codigo postal "
					+ codigoPostal, e);
		} catch (DerechohabientesBusinessException e) {
			log.error("Error al obtener UMF para el codigo postal "
					+ codigoPostal, e);
		} catch (Exception e) {
			log.error("Error al obtener UMF para el codigo postal "
					+ codigoPostal, e);
		}
		
		if (CollectionUtils.isEmpty(lstUmf)) {
			log.warn("No se obtuvieron UMF para el codigo postal "
					+ codigoPostal
					+ ", se procede a obtener la UMF por default");

			lstUmf = new ArrayList<UnidadMedicaFamiliar>();
			lstUmf.add(this.entity.getUMFDefaultPorCP(codigoPostal));
		}

		return lstUmf;
	}
	
	@Override
	public List<UnidadMedicaFamiliar> getUmfDefaultByCodigoPostal(String codigoPostal) throws UmfNoLocalizadaException{
		this.log.debug("entre al servicio");
		if(codigoPostal == null ){
			throw new IllegalArgumentException();
		}
		try{
			List<UnidadMedicaFamiliar> lstUmf = new ArrayList<UnidadMedicaFamiliar>();
			lstUmf.add(this.entity.getUMFDefaultPorCP(codigoPostal));
			return lstUmf;
		}catch(UmfNoLocalizadaException ex){
			log.error("ocurrio un errro al consultar la clinica defaul con codigo [" +codigoPostal+"]" + ex);
			throw ex;
		}
	}
	
	
	
	
	
	/**
	 * 
	 */
	@Override
	public MunicipioIMSS getMunicipioIMSSPorClave(String clave){
		return entity.getMunicipioIMSSPorClave(clave);
	}
	
	@Override
	public Delegacion obtenerDelegacionPorId(Long idDelegacion) {
				
		return entity.obtenerDelegacionPorId(idDelegacion);
	}
	
	@Override
	public Subdelegacion obtenerSubdelegacionPorId(Long idSubdelegacion) {
		
		return entity.obtenerSubdelegacionPorId(idSubdelegacion);
	}
	
	/**
	 * Servicio de devuelve el catalogo de delegaciones  activas que contiene el IMSS 
	 * @return
	 */
	@Override
	public  List<Delegacion> findDelegacionesActivas() {	
		return entity.findDelegacionesActivas();
	}
	
	/**
	 * Servicio de devuelve el catalogo de subdelegaciones  activas que contiene una delegacion IMSS
	 * @param idDelegacion
	 * @return
	 */
	@Override
	public List<Subdelegacion> findSubDelegacionesActivas(Long idDelegacion) {
		return entity.findSubDelegacionesActivas(idDelegacion);
	}
	
	@Override
	public EntidadFederativa getEstado(String cveEnt) {
		
		return this.entity.getEstado(cveEnt);
	}
	
	@Override
	public List<Vialidad> obtenerVialidadesAutocompletar(Localidad localidad,
			int periodo, String nomVialidad) throws VialidadesNoLocalizadasException {
		
		return this.entity.obtenerVialidadesAutocompletar(localidad, periodo,
				nomVialidad);
	}

	@Override
	public Domicilio obtenerVialidadElegida(Localidad localidad, int periodo,
			Vialidad vialidad) throws VialidadesNoLocalizadasException {
		return this.entity.obtenerVialidadElegida(localidad, periodo, vialidad);
	}
	
	@Override
	public GrupoFamiliar getAsegurado(Long idPersona)throws DerechohabientesBusinessException{
		GrupoFamiliar afectado = null;
		try{
		List<AsignacionNSS> listaNSS = grupoFamiliarServiceRemote.getAsignacionNss( idPersona );
		AsignacionNSS nss = listaNSS.get(0);
		afectado = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarPorIdPersona(nss.getIdAsignacionNSS(), nss.getIdPersona());
		
		return afectado;
		}catch (Exception e){
			throw new DerechohabientesBusinessException(e.getMessage());
		}
	}
	
	/**
	 * Metodo q	ue se usa para complementar los datos de vialidad, ya que para domicilio recortado solo se pide el nombre
	 * una vez que llega a este metodo se buscara la localidad en base al estado y municipio eligien una calle ninguno por default
	 * y poniendola como vialidad primaria
	 * los datos requeridos que se validan son:
	 * cveEstado
	 * codigoPostal
	 * cveMunicipio
	 * CveAsentamiento
	 * calle
	 * numeroExteriorAlf
	 * numeroInteriorAlf es opcional
	 * 
	 * Este metodo recibe un objeto domicilio del modelo digital, lo transforma y retorna un domicilio del paquete que
	 * se usa en todos los proyectos
	 * 
	 * @param domicilio
	 * @return
	 * @throws IllegalArgumentException Cuando falta algun dato para la consulta
	 * @throws DomicilioNoLocalizadoException Cuando no se encuentra la localidad
	 */
	@Override
	public Domicilio complementarLocalidadDomicilioDigRecortado(
			mx.gob.imss.digital.modelo.domicilio.Domicilio domicilio)
			throws DomicilioNoValidoException, DomicilioNoLocalizadoException {
		
		Domicilio domicilioConLocalidad = domicilioParserServiceLocal.convertirDomicilioDigtoDomicilioRecortado(domicilio);
		
		domicilioConLocalidad = this.complementarLocalidadDomicilioRecortado(domicilioConLocalidad);
		
		return domicilioConLocalidad;
	}

	/**
	 * Metodo q	ue se usa para complementar los datos de vialidad, ya que para domicilio recortado solo se pide el nombre
	 * una vez que llega a este metodo se buscara la localidad en base al estado y municipio eligien una calle ninguno por default
	 * y poniendola como vialidad primaria
	 * los datos requeridos que se validan son:
	 * cveEstado
	 * codigoPostal
	 * cveMunicipio
	 * CveAsentamiento
	 * calle
	 * numeroExteriorAlf
	 * numeroInteriorAlf es opcional
	 * 
	 * @param domicilio
	 * @return
	 * @throws IllegalArgumentException Cuando falta algun dato para la consulta
	 * @throws DomicilioNoLocalizadoException Cuando no se encuentra la localidad
	 */
	@Override
	public Domicilio complementarLocalidadDomicilioRecortado(Domicilio domicilio)
			throws DomicilioNoValidoException, DomicilioNoLocalizadoException {
		
		if(domicilio == null) {
			throw new DomicilioNoValidoException("Es necesario el domicilio");
		}
		
		if(StringUtils.isBlank(domicilio.getCalle())) {
			throw new DomicilioNoValidoException("Es necesario contar con la calle del domicilio");
		}
		
		if(domicilio.getAsentamiento() == null || StringUtils.isBlank(domicilio.getAsentamiento().getClave()) ) {
			throw new DomicilioNoValidoException("Es necesario contar con la colonia");
		}
		
		if(domicilio.getAsentamiento().getLocalidad() == null) {
			throw new DomicilioNoValidoException("Es necesario contar con los datos de municipio y estado");
		}
		
		if(StringUtils.isBlank(domicilio.getNumExteriorAlf())) {
			throw new DomicilioNoValidoException("Es necesario contar con el numero exterior");
		}

		if(domicilio.getCodigoPostal() == null || StringUtils.isBlank(domicilio.getCodigoPostal().getCodigoPostal())) {
			throw new DomicilioNoValidoException("Es necesario contar con el codigo postal");
		}
		
		//Solo si no tenemos la vialidad primaria
		if(domicilio.getVialidadPrimaria() == null || domicilio.getVialidadPrimaria().getClave() == null) {
			log.debug("buscaremos la vialidad ninguno");
			Localidad localidad = domicilio.getAsentamiento().getLocalidad();
			Vialidad vialidad = new Vialidad();
			vialidad.setNombre("NINGUNO");
			TipoVialidad tipoVialidad = new TipoVialidad();
			tipoVialidad.setClave(5);
			vialidad.setTipoVialidad(tipoVialidad);
			int periodo = 4;
		
			try {
				Domicilio domCalle = this.obtenerVialidadElegida(localidad, periodo, vialidad);
				log.debug("La calle encontrada es: " + domCalle.getVialidadPrimaria());
				log.debug("La localidad encontrada es: " + domCalle.getLocalidad());
				log.debug("El objeto domicilio es: " + domCalle);
				domicilio.setVialidadPrimaria(domCalle.getVialidadPrimaria());
				if(domicilio.getLocalidad() == null) {
					domicilio.setLocalidad(domCalle.getLocalidad());
				} else {
					domicilio.getLocalidad().setClave(domCalle.getLocalidad().getClave());
					domicilio.getLocalidad().setNombre(domCalle.getLocalidad().getNombre());
				}
				domicilio.getAsentamiento().getLocalidad().setClave(domCalle.getLocalidad().getClave());
				domicilio.getAsentamiento().getLocalidad().setNombre(domCalle.getLocalidad().getNombre());
				
			} catch (VialidadesNoLocalizadasException e) {
				log.debug("no fue posible localizar la vialidad");
				e.printStackTrace();
				throw new DomicilioNoLocalizadoException("No fue posible localizar la localidad con los datos proporcionados");
			}
		} else {
			log.debug("No es necesario buscar la vialidad ninguno ya que se cuenta con una vialidad primaria con clave");
		}
		
		return domicilio;
	}

    @Override
    public void validarDatosDomicilioRecortado(mx.gob.imss.digital.modelo.domicilio.Domicilio domicilio) throws DomicilioNoValidoException {
        List<String> lstErrores = new ArrayList<String>();
        StringBuilder sbErrores = new StringBuilder();
        // Validacion de informacion de Domicilio
        if (domicilio == null) {
            lstErrores.add("Es necesario ingresar la informaci\u00F3n del domicilio particular del asegurado");
        } else {
            // Validacion de Asentamiento
            if(domicilio.getAsentamiento()==null || StringUtils.isBlank(domicilio.getAsentamiento().getClave())){
                lstErrores.add("Es necesario ingresar la informaci\u00F3n del Asentamiento");
            }
            mx.gob.imss.digital.modelo.domicilio.Asentamiento asentamiento = domicilio.getAsentamiento();
            //si no viene la localidad dentro del asentamiento se supone que es domicilio completo
            if (asentamiento.getLocalidad() == null) {
                // Validacion codigo postal
                if (StringUtils.isBlank(domicilio.getCodigoPostal())
                        && (domicilio.getAsentamiento() != null && StringUtils.isBlank(domicilio.getAsentamiento().getCodigoPostal()))) {
                    lstErrores.add("Es necesario ingresar la informaci\u00F3n del C\u00F3digo Postal");
                }
                // Validacion de numero
                if ((domicilio.getNumExterior1() == null || domicilio.getNumExterior1() == 0)
                        && StringUtils.isBlank(domicilio.getNumExteriorAlf())) {
                    lstErrores.add("Es necesario ingresar al menos el n\u00FAmero Exterior (Num\u00E9rico o Alfanum\u00E9rico)");
                }
                // Validacion Vialidad Primaria
                try {
                    if (isCaminoVacio(domicilio.getCamino(), asentamiento)
                            && isCarreteraVacia(domicilio.getCarretera(), asentamiento)
                            && isCalleVacia(domicilio.getCalle(), domicilio.getVialidadPrimaria(), asentamiento)) {
                        lstErrores.add("Es necesario ingresar la Vialidad Primaria o Carretera o Camino");
                    }
                } catch (DomicilioNoValidoException e) {
                    lstErrores.add(e.getMessage());
                }

                // Validacion otras vialidades
                mx.gob.imss.digital.modelo.domicilio.Vialidad vialidadRefPrimaria = domicilio.getVialidadReferenciaPrimaria();
                if (vialidadRefPrimaria != null && vialidadRefPrimaria.getClave() != null && vialidadRefPrimaria.getClave() != 0) {
                    mx.gob.imss.digital.modelo.domicilio.TipoVialidad tipoVialidad = vialidadRefPrimaria.getTipoVialidad();
                    if (tipoVialidad == null || tipoVialidad.getClave() == null || tipoVialidad.getClave() == 0) {
                        lstErrores.add("Es necesario ingresar la informaci\u00F3n del Tipo de Vialidad (Vialidad Referencia Primaria)");
                    }
                }

                mx.gob.imss.digital.modelo.domicilio.Vialidad vialidadRefSecundaria = domicilio.getVialidadReferenciaSecundaria();
                if (vialidadRefSecundaria != null && vialidadRefSecundaria.getClave() != null && vialidadRefSecundaria.getClave() != 0) {
                    mx.gob.imss.digital.modelo.domicilio.TipoVialidad tipoVialidad = vialidadRefSecundaria.getTipoVialidad();
                    if (tipoVialidad == null || tipoVialidad.getClave() == null || tipoVialidad.getClave() == 0) {
                        lstErrores.add("Es necesario ingresar la informaci\u00F3n del Tipo de Vialidad (Vialidad Referencia Secundaria)");
                    }
                }

                mx.gob.imss.digital.modelo.domicilio.Vialidad vialidadRefPosterior = domicilio.getVialidadReferenciaPosterior();
                if (vialidadRefPosterior != null && vialidadRefPosterior.getClave() != null && vialidadRefPosterior.getClave() != 0) {
                    mx.gob.imss.digital.modelo.domicilio.TipoVialidad tipoVialidad = vialidadRefPosterior.getTipoVialidad();
                    if (tipoVialidad == null || tipoVialidad.getClave() == null || tipoVialidad.getClave() == 0) {
                        lstErrores.add("Es necesario ingresar la informaci\u00F3n del Tipo de Vialidad (Vialidad Referencia Posterior)");
                    }
                }
            }
        }

        // Se enumeran los errores detectados durante la validacion
        int numErrores = lstErrores.size();
        if (numErrores > 0) {
            for (int index = 0; index < numErrores; index++) {
                if (index == numErrores - 1) {
                    sbErrores.append(lstErrores.get(index));
                } else {
                    sbErrores.append(lstErrores.get(index)).append(", \n");
                }
            }
        }

        String strErrores = sbErrores.toString();
        if (StringUtils.isNotBlank(strErrores)) {
            throw new DomicilioNoValidoException("Se detectaron los siguientes errores al validar los datos ingresados:\n" + strErrores);
        }
    }

    private boolean isCaminoVacio(Camino camino, mx.gob.imss.digital.modelo.domicilio.Asentamiento asentamiento) throws DomicilioNoValidoException {
        List<String> lstErrores = new ArrayList<String>();
        StringBuilder sbErrores = new StringBuilder();
        boolean caminoVacio = false;
        if (camino == null) {
            caminoVacio = true;
        } else {
            mx.gob.imss.digital.modelo.domicilio.TipoMargen margen = camino.getMargen();
            mx.gob.imss.digital.modelo.domicilio.TipoTerminoGeneral terminoGeneral = camino.getTerminoGeneral();
            if ((margen == null || margen.getClave() == null || margen.getClave() == 0L)
                    && (terminoGeneral == null || terminoGeneral.getClave() == null || terminoGeneral.getClave() == 0L)
                    && StringUtils.isBlank(camino.getCadenamiento()) && StringUtils.isBlank(camino.getOrigen())
                    && StringUtils.isBlank(camino.getDestino())) {
                caminoVacio = true;
            } else {
                if (margen == null || margen.getClave() == null || margen.getClave() == 0L) {
                    lstErrores.add("Es necesario ingresar la informaci\u00F3n del Margen del Camino");
                }
                if (terminoGeneral == null || terminoGeneral.getClave() == null || terminoGeneral.getClave() == 0L) {
                    lstErrores.add("Es necesario ingresar la informaci\u00F3n del Termino General del Camino");
                }
                if (StringUtils.isBlank(camino.getCadenamiento())) {
                    lstErrores.add("Es necesario ingresar el Cadenamiento del Camino");
                }
                if (StringUtils.isBlank(camino.getOrigen())) {
                    lstErrores.add("Es necesario ingresar el Origen del Camino");
                }
                if (StringUtils.isBlank(camino.getDestino())) {
                    lstErrores.add("Es necesario ingresar el Destino del Camino");
                }

                // Validacion de localidad, municipio, entidad federativa
                mx.gob.imss.digital.modelo.domicilio.Localidad localidad = asentamiento.getLocalidad();
                if (localidad == null || StringUtils.isBlank(localidad.getClave())) {
                    lstErrores.add("Es necesario ingresar la informaci\u00F3n de la Localidad");
                } else {
                    mx.gob.imss.digital.modelo.domicilio.Municipio municipio = localidad.getMunicipio();
                    if (municipio == null || StringUtils.isBlank(municipio.getClave())) {
                        lstErrores.add("Es necesario ingresar la informaci\u00F3n del Municipio");
                    } else {
                        mx.gob.imss.digital.modelo.domicilio.EntidadFederativa entidadFederativa = municipio.getEntidadFederativa();
                        if (entidadFederativa == null || StringUtils.isBlank(entidadFederativa.getClave())) {
                            lstErrores.add("Es necesario ingresar la informaci\u00F3n de la Entidad Federativa");
                        }
                    }
                }
            }
        }

        int numErrores = lstErrores.size();
        if (numErrores > 0) {
            for (int index = 0; index < numErrores; index++) {
                if (index == numErrores - 1) {
                    sbErrores.append(lstErrores.get(index));
                } else {
                    sbErrores.append(lstErrores.get(index)).append(", \n");
                }
            }
        }

        String strErrores = sbErrores.toString();
        if (StringUtils.isNotBlank(strErrores)) {
            throw new DomicilioNoValidoException(strErrores);
        }

        return caminoVacio;
    }

    private boolean isCarreteraVacia(Carretera carretera, mx.gob.imss.digital.modelo.domicilio.Asentamiento asentamiento) throws DomicilioNoValidoException {
        List<String> lstErrores = new ArrayList<String>();
        StringBuilder sbErrores = new StringBuilder();
        boolean carreteraVacia = false;

        if (carretera == null) {
            carreteraVacia = true;
        } else {
            mx.gob.imss.digital.modelo.domicilio.TipoAdministracion administracion = carretera.getAdministracion();
            mx.gob.imss.digital.modelo.domicilio.TipoDerechoTransito derechoTransito = carretera.getDerechoTransito();
            mx.gob.imss.digital.modelo.domicilio.TipoTerminoGeneral terminoGeneral = carretera.getTerminoGeneral();

            if ((administracion == null || administracion.getClave() == null || administracion.getClave() == 0L)
                    && (derechoTransito == null || derechoTransito.getClave() == null || derechoTransito.getClave() == 0L)
                    && (terminoGeneral == null || terminoGeneral.getClave() == null || terminoGeneral.getClave() == 0L)
                    && StringUtils.isBlank(carretera.getCadenamiento()) && StringUtils.isBlank(carretera.getOrigen())
                    && StringUtils.isBlank(carretera.getDestino())
                    && (carretera.getCodigoCarretera() == null || carretera.getCodigoCarretera() == 0)) {
                carreteraVacia = true;
            } else {
                if (administracion == null || administracion.getClave() == null || administracion.getClave() == 0L) {
                    lstErrores.add("Es necesario ingresar la informaci\u00F3n de la Administraci\u00F3n de la Carretera");
                }
                if (derechoTransito == null || derechoTransito.getClave() == null || derechoTransito.getClave() == 0L) {
                    lstErrores.add("Es necesario ingresar la informaci\u00F3n del Derecho de Transito de la Carretera");
                }
                if (terminoGeneral == null || terminoGeneral.getClave() == null || terminoGeneral.getClave() == 0L) {
                    lstErrores.add("Es necesario ingresar la informaci\u00F3n del Termino General de la Carretera");
                }
                if (StringUtils.isBlank(carretera.getCadenamiento())) {
                    lstErrores.add("Es necesario ingresar el Cadenamiento de la Carretera");
                }
                if (StringUtils.isBlank(carretera.getOrigen())) {
                    lstErrores.add("Es necesario ingresar el Origen de la Carretera");
                }
                if (StringUtils.isBlank(carretera.getDestino())) {
                    lstErrores.add("Es necesario ingresar el Destino de la Carretera");
                }
                if (carretera.getCodigoCarretera() == null || carretera.getCodigoCarretera() == 0) {
                    lstErrores.add("Es necesario ingresar el C\u00F3digo de la Carretera");
                }

                // Validacion de localidad, municipio, entidad federativa
                mx.gob.imss.digital.modelo.domicilio.Localidad localidad = asentamiento.getLocalidad();
                if (localidad == null || StringUtils.isBlank(localidad.getClave())) {
                    lstErrores.add("Es necesario ingresar la informaci\u00F3n de la Localidad");
                } else {
                    mx.gob.imss.digital.modelo.domicilio.Municipio municipio = localidad.getMunicipio();
                    if (municipio == null || StringUtils.isBlank(municipio.getClave())) {
                        lstErrores.add("Es necesario ingresar la informaci\u00F3n del Municipio");
                    } else {
                        mx.gob.imss.digital.modelo.domicilio.EntidadFederativa entidadFederativa = municipio.getEntidadFederativa();
                        if (entidadFederativa == null || StringUtils.isBlank(entidadFederativa.getClave())) {
                            lstErrores.add("Es necesario ingresar la informaci\u00F3n de la Entidad Federativa");
                        }
                    }
                }
            }
        }

        int numErrores = lstErrores.size();
        if (numErrores > 0) {
            for (int index = 0; index < numErrores; index++) {
                if (index == numErrores - 1) {
                    sbErrores.append(lstErrores.get(index));
                } else {
                    sbErrores.append(lstErrores.get(index)).append(", \n");
                }
            }
        }

        String strErrores = sbErrores.toString();
        if (StringUtils.isNotBlank(strErrores)) {
            throw new DomicilioNoValidoException(strErrores);
        }

        return carreteraVacia;
    }

    private static boolean isCalleVacia(String calle, mx.gob.imss.digital.modelo.domicilio.Vialidad vialidadPrimaria, mx.gob.imss.digital.modelo.domicilio.Asentamiento asentamiento) throws DomicilioNoValidoException {
        List<String> lstErrores = new ArrayList<String>();
        StringBuilder sbErrores = new StringBuilder();
        boolean calleVacia = false;
        if (StringUtils.isBlank(calle)
                && (vialidadPrimaria == null
                || (vialidadPrimaria.getClave() == null && StringUtils.isBlank(vialidadPrimaria.getNombre()))
                || (vialidadPrimaria.getClave() != null && vialidadPrimaria.getClave() == 0 && StringUtils.isBlank(vialidadPrimaria.getNombre())))) {
            calleVacia = true;
        } else if (vialidadPrimaria == null
                || (vialidadPrimaria.getClave() == null && StringUtils.isNotBlank(vialidadPrimaria.getNombre()))
                || (vialidadPrimaria.getClave() != null && vialidadPrimaria.getClave() == 0 && StringUtils.isNotBlank(vialidadPrimaria.getNombre()))) {
            // Validacion de localidad, municipio, entidad federativa
            mx.gob.imss.digital.modelo.domicilio.Localidad localidad = asentamiento.getLocalidad();
            if (localidad == null) {
                lstErrores.add("Es necesario ingresar el Municipio como parametro de la Localidad");
            } else {
                mx.gob.imss.digital.modelo.domicilio.Municipio municipio = localidad.getMunicipio();
                if (municipio == null || StringUtils.isBlank(municipio.getClave())) {
                    lstErrores.add("Es necesario ingresar la informaci\u00F3n del Municipio");
                } else {
                    mx.gob.imss.digital.modelo.domicilio.EntidadFederativa entidadFederativa = municipio.getEntidadFederativa();
                    if (entidadFederativa == null || StringUtils.isBlank(entidadFederativa.getClave())) {
                        lstErrores.add("Es necesario ingresar la informaci\u00F3n de la Entidad Federativa");
                    }
                }
            }
        }

        if (vialidadPrimaria != null) {
            mx.gob.imss.digital.modelo.domicilio.TipoVialidad tipoVialidad = vialidadPrimaria.getTipoVialidad();
            if (tipoVialidad == null || tipoVialidad.getClave() == null || tipoVialidad.getClave() == 0) {
                lstErrores.add("Es necesario ingresar el Tipo de Vialidad (Vialidad Primaria)");
            }
        }

        int numErrores = lstErrores.size();
        if (numErrores > 0) {
            for (int index = 0; index < numErrores; index++) {
                if (index == numErrores - 1) {
                    sbErrores.append(lstErrores.get(index));
                } else {
                    sbErrores.append(lstErrores.get(index)).append(", \n");
                }
            }
        }

        String strErrores = sbErrores.toString();
        if (StringUtils.isNotBlank(strErrores)) {
            throw new DomicilioNoValidoException(strErrores);
        }
        return calleVacia;
    }

    @Override
    public Subdelegacion getSubDelegacionPorCodigoPostal(String codigoPostal) throws SubDelegacionNoLocalizadaException {
        return this.entity.getSubDelegacionPorCP(codigoPostal);
    }
	
	@Override
	public List<Subdelegacion> getSubDelegacionesPorCodigoPostal(String codigoPostal) throws SubDelegacionNoLocalizadaException{
		return this.entity.getSubDelegacionesPorCP(codigoPostal);
	}
	
	@Override
    public List<MunicipioIMSS> getMunicipioImssPorMunicipioIMSS(List<String> municipioIMSS){
		return domicilioServiceUtility.convertirMunicipioIMSS(this.entity.getMunicipioImssPorMunicipioIMSS(municipioIMSS));
	}
    
	@Override
	public List<Domicilio> consultarDomiciliosPersonaFisicaAcceder(Persona persona)
			throws DomicilioNoLocalizadoException {
		
		this.log.debug(" Servicio de Negocio de Consultar Domicilios de la persona ["
				+ persona.getIdPersona() + "] como en Acceder");
		
		return this.entity.consultarDomiciliosPersonaFisicaAcceder(persona);
	}
    
}