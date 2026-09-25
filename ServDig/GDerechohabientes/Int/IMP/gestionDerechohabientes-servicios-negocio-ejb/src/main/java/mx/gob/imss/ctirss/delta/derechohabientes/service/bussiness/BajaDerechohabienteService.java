package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TransactionRequiredException;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.CabezaGrupoFamiliarDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.CatalogosDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.GrupoFamiliarDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.entity.BajaDerechohabienteEntityLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.BajaDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.TramiteDocumentosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.util.GrupoFamiliarUtil;
import mx.gob.imss.ctirss.delta.derechohabientes.util.TramiteUtil;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.PersonaSinMedioDeContactoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoBajaDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.BajaDerechohabienteDto;
import mx.gob.imss.ctirss.delta.model.derechohabientes.PropiedadesDocumento;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.SubestadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Facebook;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Twitter;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteBajaDerechohabiente;
import mx.gob.imss.ctirss.delta.model.util.Constants;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

import org.apache.commons.lang.StringUtils;
/**
 * @author Mario Teran Blanco
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 17/04/2012
 */
@Stateless( name = "bajaDerechohabienteService", mappedName = "bajaDerechohabienteService")
public class BajaDerechohabienteService extends AbstractServiceBusiness implements
BajaDerechohabienteServiceRemote, BajaDerechohabienteServiceLocal{

	@EJB(name = "grupoFamiliarDao") 
	private GrupoFamiliarDaoLocal grupoFamiliarDaoLocal;
	@EJB(name = "solicitudService") 
	private SolicitudServiceLocal solicitudServiceLocal;
	@EJB(name = "agendarCitaService") 
	private AgendarCitaServiceLocal agendarCitaServiceLocal;
	@EJB(name = "cabezaGrupoFamiliarDao") 
	private CabezaGrupoFamiliarDaoLocal cabezaGrupoFamiliarDaoLocal;
	@EJB(name = "personaBusiness", mappedName="personaBusiness") 
	private PersonaBusinessRemote personaBusinessRemote;
	@EJB(name = "tramiteService") 
	private TramiteServiceLocal tramiteServiceLocal;
	@EJB(name = "prorrogaService") 
	private ProrrogaServiceLocal prorrogaServiceLocal;
	@EJB(name = "bajaDerechohabienteEntity") 
	private BajaDerechohabienteEntityLocal bajaDerechohabienteEntityLocal;
	@EJB(name = "solicitudBusiness", mappedName = "solicitudBusiness") 
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@EJB(name = "documentoProbatorioServiceBusiness",mappedName = "documentoProbatorioServiceBusiness")
	private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusinessRemote;
	@EJB(mappedName = "firmaDigitalBusiness", name = "firmaDigitalBusiness")
	private FirmaDigitalBusinessRemote firmaDigitalBusinessRemote;
	@EJB(name = "tramiteDocumentosService")
	private TramiteDocumentosServiceRemote tramiteDocumentosServiceLocal;
	@EJB( name = "eMailService")
	private EMailServiceLocal eMailServiceLocal;
	@EJB
	private MediosContactoServiceBusinessRemote mediosContactoServiceBusinessRemote;
	@EJB
	private FinalizaSolicitudServiceLocal finalizaSolicitudService;
	@EJB
	private CatalogosDaoLocal catalogosDaoLocal;
	private GrupoFamiliar asegurado;

	@Override
	public List<BajaDerechohabienteDto> getBajaDerechohabiente(Long idAsignasionNss, List<Long> idPersonas, List<Long> tiposBaja, Boolean activa) {
		return bajaDerechohabienteEntityLocal.getBajaDerechohabiente(idAsignasionNss, idPersonas, tiposBaja, activa);
	}
	
	/**
	 * Metodo para obtener a loa integrantes del grupo familiar candidatos a baja administrativa
	 * @param idAsignacionNss
	 * @param origenSolicitud
	 * @param usuario
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	@Override
	public List<GrupoFamiliar> findGrupoFamiliarBajaAdministrativa( Long idAsignacionNss, Usuario usuario) throws DerechohabientesBusinessException {
		//lista d elos integrantes que se regresaran
		List<GrupoFamiliar> grupoFamiliar = null;
		//Se hace la consulta de los integrantes
		try {
			grupoFamiliar = grupoFamiliarDaoLocal.findGrupoFamiliarByEstadoVigente(idAsignacionNss);
		}catch(Exception e) {
			DerechohabientesBusinessException.throwException(Constants.MENSAJE_ERROR_CONSULTA_VIGENCIA, Constants.MENSAJE_ERROR_CONSULTA_VIGENCIA);
		}
		//se valida si los integrantes son vacios o nulos
		GrupoFamiliarUtil.validarIntegrantesVacios(grupoFamiliar);
		//se quitan las bajas
		grupoFamiliar = this.quitarBajas(grupoFamiliar);
		//una vez que se quitan las bajas se vuelve a validar si la lista esta vacia
		GrupoFamiliarUtil.validarIntegrantesVacios(grupoFamiliar);
		//si el metodo anterios no arrojo exception se filtra a los candidatos
		grupoFamiliar = GrupoFamiliarUtil.filtlarCandidatosPorPerfil(grupoFamiliar, usuario);
		//una vez filtrados se vuelve a validar que la lista no venga vacia
		GrupoFamiliarUtil.validarIntegrantesVacios(grupoFamiliar);
		
		return grupoFamiliar;
	}
	

	/**
	 * Metodo para obtener todos los candidatos de un grupo familiar
	 * a baja por defuncion
	 * @param nss - Nss de la cabeza de grupo familiar
	 * @param perfil - El perfil del usuario que llevara a cabo el registro de la baja
	 * @return Lista con los derechohabientes candidatos
	 * @throws DerechohabienteBussinesException
	 */
	@Override
	public List<GrupoFamiliar> findGrupoFamiliarBajaDefuncion(Long idAsignacionNss, Long origenSolicitud, Usuario usuario) throws DerechohabientesBusinessException{
		//Obtenemos todo el grupo relacionado al nss pasado como parametro
		List<GrupoFamiliar> grupoFamiliar =  null;
		log.debug("Comienza con la baja por defuncion");
		//GrupoFamiliar asegurado = null;
		try {
			grupoFamiliar = grupoFamiliarDaoLocal.findGrupoFamiliarByNss(idAsignacionNss);
			log.debug("pasa a validar las personas que se pueden dar de baja");
		} catch (Exception e) {
			log.error("Ocurrio un error inesperado", e);
			DerechohabientesBusinessException.throwException("No existe ningun integrante activo en el grupo familiar" + e.getCause().getMessage(),ExceptionMessages.SIN_INFORMACION);
		}
		
		
		//se valida que no venga vcia la lista
		GrupoFamiliarUtil.validarIntegrantesVacios(grupoFamiliar);
		log.debug("Se validan integrantes vacios");

		//Se modificac esta seccion para que no haga el esta de vigencia de beneficiarios solo se eliminan las bajas por defuncion
		
		/*GrupoFamiliar integrantes = GrupoFamiliarUtil.getAseguradoPensionado(grupoFamiliar);
		
		if(asegurado.getEstadoDerechohabiente().getIdEstadoDerechohabiente().equals(EstadoDerechohabienteEnum.BAJA.getId())) {
			grupoFamiliar = this.quitarBajasDefuncion(grupoFamiliar);
			grupoFamiliar = this.quitarEstados(grupoFamiliar, EstadoDerechohabienteEnum.VIGENTE);

		} else {
			//quitamos a los miembros del grupo familiar en baja
			grupoFamiliar = this.quitarBajas(grupoFamiliar);
		}*/
		
		grupoFamiliar = this.quitarBajasDefuncion(grupoFamiliar);
		GrupoFamiliarUtil.validarIntegrantesVacios(grupoFamiliar);
		//quitamos a los miembros del grupo familiar en baja
		log.debug("se valida que no este en estado de baja por defuncion");
		GrupoFamiliarUtil.validarIntegrantesVacios(grupoFamiliar);
		
		if(origenSolicitud.equals(OrigenSolicitudEnum.VENTANILLA.getId())){
			grupoFamiliar = GrupoFamiliarUtil.filtrarPorUmf(grupoFamiliar, usuario.getIdUmf());
		}
		else {
			//Si el origen de la solicitud es internet quitamos al asegurado
			List<GrupoFamiliar> grupoAux = new ArrayList<GrupoFamiliar>();
			for(GrupoFamiliar integrante: grupoFamiliar) {
				if(integrante.getParentesco().getIdParentesco() != ParentescoEnum.ASEGURADO.getId() && integrante.getParentesco().getIdParentesco() != ParentescoEnum.PENSIONADO.getId()) {
					grupoAux.add(integrante);
					break;
				}
			}
		}

		//si despues de filtrar al asegurado del grupo familiar la lista esta vacia
		//lanzamos la excepcion para informar que no hay integrantes en el grupo
		GrupoFamiliarUtil.validarIntegrantesVacios(grupoFamiliar);

		return grupoFamiliar;
	}

	/**
	 * Metodo para obtener todos los candidatos de un grupo familiar
	 * a baja por termino de convivencia
	 * @param nss Nss de la cabeza de grupo familiar
	 * @param perfil - El perfil del usuario que llevara a cabo el registro de la baja
	 * @return Lista con los derechohabientes candidatos
	 * @throws DerechohabienteBussinesException
	 */
	@Override
	public List<GrupoFamiliar> findGrupoFamiliarBajaConvivencia(Long idAsignacionNss, Long origenSolicitud,Usuario usuario) throws DerechohabientesBusinessException{

		List<GrupoFamiliar> grupoFamiliar = null;
		try {
			grupoFamiliar = grupoFamiliarDaoLocal.findGrupoFamiliarByParentescoNss(idAsignacionNss, ParentescoEnum.PADRES.getId());
		} catch (Exception e) {
			log.error("Ocurrio un error inesperado", e);
			DerechohabientesBusinessException.throwException(ExceptionMessages.SIN_INFORMACION, e.getMessage());
		}

		//si despues de filtrar al asegurado del grupo familiar la lista esta vacia mandamos excepcion
		GrupoFamiliarUtil.validarIntegrantesVacios(grupoFamiliar);

		//quitamos a los miembros del grupo familiar en baja
		//grupoFamiliar = this.quitarBajas(grupoFamiliar);
		log.debug("*******************Cambios Baja 1 Antes de Quitar bajas solictadas Convivencia");
		grupoFamiliar = quitarBajasSolicitadas(grupoFamiliar, TipoBajaDerechohabienteEnum.TERMINO_CONVIVENCIA.getId().intValue(),false);

		if(origenSolicitud.equals(OrigenSolicitudEnum.VENTANILLA.getId()))
			grupoFamiliar = GrupoFamiliarUtil.filtrarPorUmf(grupoFamiliar, usuario.getIdUmf());
		//si despues de filtrar al asegurado del grupo familiar la lista esta vacia mandamos excepcion
		GrupoFamiliarUtil.validarIntegrantesVacios(grupoFamiliar);


		return grupoFamiliar;
	}

	/**
	 * Metodo para obtener todos los candidatos de un grupo familiar
	 * a baja por termino de concibinato
	 * @param nss Nss de la cabeza de grupo familiar
	 * @param perfil - El perfil del usuario que llevara a cabo el registro de la baja
	 * @return Lista con los derechohabientes candidatos
	 * @throws DerechohabienteBussinesException
	 */
	@Override
	public List<GrupoFamiliar> findGrupoFamiliarBajaConcubinato(Long idAsignacionNss, Long origenSolicitud,Usuario usuario) throws DerechohabientesBusinessException{
		//Buscamos a los candidatos a baja por termino de concubinato
		return this.findGrupoDivorcioConcubinato(idAsignacionNss, ParentescoEnum.CONCUBINARIO.getId(), origenSolicitud, usuario);
	}

	/**
	 * Metodo para obtener todos los candidatos de un grupo familiar
	 * a baja por divorcio
	 * @param perfil - El perfil del usuario que llevara a cabo el registro de la baja
	 * @param nss Nss de la cabeza de grupo familiar
	 * @return Lista con los derechohabientes candidatos
	 * @throws DerechohabienteBussinesException
	 */
	@Override
	public List<GrupoFamiliar> findGrupoFamiliarBajaDivorcio(Long idAsignacionNss, Long origenSolicitud, Usuario usuario) throws DerechohabientesBusinessException{
		//buscamos a los candidatos a baja por divorcio
		return this.findGrupoDivorcioConcubinato(idAsignacionNss, ParentescoEnum.CONYUGE.getId(), origenSolicitud, usuario);
	}
	
	/**
	 * Metodo para obtener todos los candidatos de un grupo familiar
	 * a baja por union civil
	 * @param perfil - El perfil del usuario que llevara a cabo el registro de la baja
	 * @param nss Nss de la cabeza de grupo familiar
	 * @return Lista con los derechohabientes candidatos
	 * @throws DerechohabienteBussinesException
	 */
	
	@Override
	public List<GrupoFamiliar> findGrupoFamiliarBajaUnionCivil(Long idAsignacionNss, Long origenSolicitud, Usuario usuario) throws DerechohabientesBusinessException{
		//buscamos a los candidatos a baja por divorcio
		log.debug("metodo para buscar el tramite por termino de union civil" + usuario);
		return this.findGrupoDivorcioConcubinato(idAsignacionNss, ParentescoEnum.PERSONA_EN_UNION_CIVIL.getId(), origenSolicitud, usuario);
		
	}
	
	/*********************************************************************************************
	 * Metodo para obtener los candidatos para baja por divorcio y termino de concubinato
	 * @param idAsignacionNss
	 * @param idParentesco
	 * @param origenSolicitud
	 * @param usuario
	 * @return
	 * @throws DerechohabientesBusinessException
	 ********************************************************************************************/
	private List<GrupoFamiliar> findGrupoDivorcioConcubinato(Long idAsignacionNss, Long idParentesco, Long origenSolicitud,Usuario usuario) throws DerechohabientesBusinessException{
		List<GrupoFamiliar> candidatosBaja = null;
		//Buscamos a los candidatos a baja de acuerdo al parentesco
		try {
			candidatosBaja = grupoFamiliarDaoLocal.findGrupoFamiliarByParentescoWs(idAsignacionNss, idParentesco);
		} catch (Exception e) {
			log.error("Ocurrio un error inesperado", e);
			DerechohabientesBusinessException.throwException(ExceptionMessages.SIN_INFORMACION, e.getMessage());
		}
		int tipoBaja = idParentesco.intValue();
		if(tipoBaja == 7){
			tipoBaja = 8;
		}
		//si despues de filtrar al asegurado del grupo familiar la lista esta vacia mandamos excepcion
		GrupoFamiliarUtil.validarIntegrantesVacios(candidatosBaja);
		log.debug("*******************Cambios Baja 1 Antes de Quitar bajas solictadas Concubinato");
		candidatosBaja = quitarBajasSolicitadas(candidatosBaja, tipoBaja, true);
		//Si es ventanilla se valida que los candidatos esten en la misma umf que el ususario
		if(origenSolicitud.equals(OrigenSolicitudEnum.VENTANILLA.getId()))
			candidatosBaja = GrupoFamiliarUtil.filtrarPorUmf(candidatosBaja, usuario.getIdUmf());
		//si despues de filtrar al asegurado del grupo familiar la lista esta vacia mandamos excepcion
		GrupoFamiliarUtil.validarIntegrantesVacios(candidatosBaja);
		log.debug("baja para candidato en unión civil--5");
		return candidatosBaja;
	}

	/**
	 * Se crea metodo generico para guardar la baja de derechohabiente
	 * @param idDerechohabiente
	 * @param usuario
	 * @param aseguradoPensionado
	 * @param origen
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Map<String, Object> saveSolicitudBajaDerechohabiente(Long idDerechohabiente, TipoBajaDerechohabienteEnum tipoBaja, AsignacionNSS aseguradoPensionado, OrigenSolicitudEnum origen,Usuario usuario) throws DerechohabientesBusinessException{
		//Resultado
		Map<String, Object> result = new HashMap<String, Object>();
		
		//Objeto solicitud
		Solicitud solicitud = null;
		//Tipo de tramite que guardaremos
		TipoTramiteEnum tipoTramite = null;
		//Obenemos al derechohabiente a dar de baja
		GrupoFamiliar derechohabiente = null;
		try {
			derechohabiente = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(aseguradoPensionado.getIdAsignacionNSS(), idDerechohabiente);
			log.debug("baja para candidato en unión civil--4");
		} catch (Exception e1) {
			//en caso de que ocurra un error al consultar al integrante mandamos una excepcion
			log.error("Error al recuperar al integrante afectado", e1);
			DerechohabientesBusinessException.throwException(e1.getCause().getMessage(),"error.busqueda.integrante");
		}
		
		//En caso de no ocurrir ningun error pero no encontrar al integrante mandamos una excepcion
		if(derechohabiente == null) {
			DerechohabientesBusinessException.throwException("No se encontro al integrante del grupo familiar","exception.RNGD0003");
			log.debug("baja para candidato en unión civil--4");
		}
		
		//ponemos en el map al afectado
		result.put("afectado", derechohabiente);
		log.debug("El parentesco a dar de baja es: " + derechohabiente.getParentesco().getIdParentesco());
		
		//Verificamos el tipo de baja, para poner el tipo de tramite correspondiente
		if(tipoBaja.getId().equals(TipoBajaDerechohabienteEnum.DEFUNCION.getId())) {
			tipoTramite = TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_DEFUNCION;
		} else if(tipoBaja.getId().equals(TipoBajaDerechohabienteEnum.TERMINO_CONVIVENCIA.getId())) {
			tipoTramite = TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_TERMINO_DE_CONVIVENCIA;
			//Verificamos que el parentesco del derechohabiente sea el adecuado para este tipo de baja
			if(!derechohabiente.getParentesco().getIdParentesco().equals(ParentescoEnum.PADRES.getId())) {
				DerechohabientesBusinessException.throwException("El parentesco no concuerda con el tipo de baja", ExceptionMessages.PARENTESCO_INCORRECTO_BAJA);
			}
		} else if(tipoBaja.getId().equals(TipoBajaDerechohabienteEnum.DIVORCIO.getId())) {
			tipoTramite = TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_DIVORCIO;
			//Verificamos que el parentesco del derechohabiente sea el adecuado para este tipo de baja
			if(!derechohabiente.getParentesco().getIdParentesco().equals(ParentescoEnum.CONYUGE.getId())) {
				DerechohabientesBusinessException.throwException("El parentesco no concuerda con el tipo de baja", ExceptionMessages.PARENTESCO_INCORRECTO_BAJA);
			}
		} else if(tipoBaja.getId().equals(TipoBajaDerechohabienteEnum.TERMINO_CONCUBINATO.getId())) {
			tipoTramite = TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_TERMINO_DE_CONCUBINATO;
			//Verificamos que el parentesco del derechohabiente sea el adecuado para este tipo de baja
			if(!derechohabiente.getParentesco().getIdParentesco().equals(ParentescoEnum.CONCUBINARIO.getId())) {
				DerechohabientesBusinessException.throwException("El parentesco no concuerda con el tipo de baja", ExceptionMessages.PARENTESCO_INCORRECTO_BAJA);
			}	
		} else if(tipoBaja.getId().equals(TipoBajaDerechohabienteEnum.TERMINO_DE_UNION_CIVIL.getId())) {
			tipoTramite = TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_TERMINO_DE_UNION_CIVIL;
			log.debug("aquí entra el tramite por termino de union civil------------------");
			//Verificamos que el parentesco del derechohabiente sea el adecuado para este tipo de baja
			if(!derechohabiente.getParentesco().getIdParentesco().equals(ParentescoEnum.PERSONA_EN_UNION_CIVIL.getId())) {
				DerechohabientesBusinessException.throwException("El parentesco no concuerda con el tipo de baja", ExceptionMessages.PARENTESCO_INCORRECTO_BAJA);
			}
		} else if(tipoBaja.getId().equals(TipoBajaDerechohabienteEnum.ADMINISTRATIVA.getId())) {
			tipoTramite = TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_AUTORIDAD_NORMATIVA;
		} 

		//guardamos la solicitud y el tramite correspondiente
		try {
			solicitud = this.guardarSolicitudBaja(derechohabiente, tipoTramite,usuario, aseguradoPensionado, origen);
			result.put("solicitud", solicitud);
		} catch(Exception e) {
			//En caso de ocurrir cualquier error al crear la solicitud mandamos una excepcion
			log.error("Error al guardar la solicitud", e);
			DerechohabientesBusinessException.throwException("Error al guardar la solicitud" + e.getCause().getMessage(), ExceptionMessages.ERROR_GUARDADO_SOLCITUD);
		}
		//Retornamos la solicitud recien creada
		return result;
	}


	/**
	 * Metodo para iniciar la autorizacion de un tramite recibiendo los siguientes parametros
	 * @param idTramite - Long : el id del tramite que se autorizara
	 * @param nss - AsignacionNSS : el nss del grupo familiar
	 * @return Tramite : el tramite encontrado
	 * @throws Exception 
	 */
	@Override
	public Solicitud inicioAutorizacionBaja(Long idSolicitud,
			AsignacionNSS nss) throws DerechohabientesBusinessException{

		Solicitud solicitudBaja = null;
		TramiteBajaDerechohabiente tramiteBaja = null;
		GrupoFamiliar integrante = null;

		//Buscamos el tramite de baja con el id que se nos paso como parametro
		// = tramiteServiceLocal.getBaja(idTramite);

		solicitudBaja = new Solicitud();
		solicitudBaja.setSolicitudId(idSolicitud);
		log.debug("aquí entra el tramite por solicitud de union civil1------------------");

		try {
			solicitudBaja = solicitudBusinessRemote.consultar(solicitudBaja);
		} catch (SolicitudNoEncontradaException e) {
			DerechohabientesBusinessException.throwException("Solicitud no encontrada");
		}

		if(solicitudBaja != null) {
			Tramite tramite = solicitudBaja.getTramites().get(0);

			if(tramite instanceof TramiteBajaDerechohabiente) {
				tramiteBaja = (TramiteBajaDerechohabiente) tramite;
			} else {
				DerechohabientesBusinessException.throwException("Inconsistencia en los datos de la solicitud");
			}
		}
		//Verificamos si el tramite no es null
		if(tramiteBaja != null) {
			//buscamos al integrante que sera dado de baja
			try {
				integrante = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(nss.getIdAsignacionNSS(), tramiteBaja.getPersona().getIdPersona());
			} catch (Exception e1) {
				log.error("Error al recuperar al integrante afectado", e1);
				DerechohabientesBusinessException.throwException(e1.getCause().getMessage(),"error.busqueda.integrante");
			}

			if(integrante == null)
				DerechohabientesBusinessException.throwException("No se encontro al integrante del grupo familiar","exception.RNGD0003");

			//Comparamos que el derechohabiente sea activo de lo contrario mandamos la excepcion para indicar que ese candidato
			//ya no puede ser dado de baja
			if(integrante.getEstadoDerechohabiente().getIdEstadoDerechohabiente() == EstadoDerechohabienteEnum.BAJA.getId()) {
				long tipoBaja = tramiteBaja.getTipoTramite().getIdTipoTramite().intValue();
				if(tipoBaja == TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_TERMINO_DE_CONVIVENCIA.getCodigo().longValue()) {
					throw new DerechohabientesBusinessException("El derechohabiente ya no es candidato",ExceptionMessages.ERROR_CANDIDATO_DEPENDENCIA);
				}
			}
		}

		//Si el tramite de baja se encuentra pendiente de autorizacion indicamos mediante una excepcion que la solicitud ya fue atendida
		if(tramiteBaja.getEstadoTramite().getIdEstadoTramitePersona().longValue() != EstadoTramiteEnum.ESPERA_AUTORIZACION.getId()) {
			throw new DerechohabientesBusinessException("La solicitud ya esta atendida", ExceptionMessages.FOLIO_SOLICITUD_ATENDIDA);
		}

		return solicitudBaja;
	}
	
	/**
	 * Metodo para guardar la solicitud de baja de derechohabiente recibiendo los siguientes parametros
	 * @param derechohabiente El integrante a dar de baja
	 * @param tipo El tipo de tramite, es decir, en este caso el tipo de baja
	 * @param usuario El usuario que solicita la baja
	 * @param nss objeto de tipo asignacionNSS que representa al asegurado cabeza de grupo familiar
	 * @return Solicitud la solicitud guardada
	 * @throws DerechohabientesBusinessException 
	 */
	@Override
	public Solicitud guardarSolicitudBaja(GrupoFamiliar derechohabiente, mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum tipo,Usuario usuario,AsignacionNSS nss, OrigenSolicitudEnum origen) throws Exception{

		//Creamos la solicitud a guardar
		//Creamos la solicitud a guardar
		Solicitud solicitudBaja = new Solicitud();
		//guardamos la solicitud de tipo baja
		//solicitudServiceLocal.guardarSolicitud(derechohabiente, tipo, usuario, nss, TipoSolicitudEnum.BAJA);
		solicitudBaja = tramiteServiceLocal.guardarBaja(derechohabiente, tipo, usuario, nss, new TramiteBajaDerechohabiente(), origen);
		//de haber guardado correctamente retornamos el objeto con su id de solicitud y folio
		log.debug("aquí entra el tramite por solicitud de union civil2------------------");

		return solicitudBaja;

	}

	/**
	 * Metodo para rechazar una solicitud de baja recibiendo los siguientes parametros
	 * @param idSolicitud el id de la solicitud a rechazar
	 * @param idPersona el id de la persona involucrada en el tramite
	 * @param idTipoTramite el id del tipo del tramite
	 * @param idRechazo la razon por la cual se rechaza la solicitud
	 * @return Tramite el tramite que se cancelo de baja
	 */
	@Override
	public Solicitud rechazarSolicitudBaja(Long idSolicitud, Long idPersona,
			Long idTramite, Long idRechazo, String observaciones,Fisica personaUsuario) throws DerechohabientesBusinessException {

		//Tramite tramite = null;
		Solicitud solicitud = null;
		//Guardamos el rechazo de la solicitud
		try {
			solicitud = solicitudServiceLocal.rechazarSolicitud(idSolicitud, idRechazo, observaciones,personaUsuario);
			log.debug("aquí entra el tramite por solicitud de union civil3------------------");

			return solicitud;
		} catch(Exception e) {
			e.printStackTrace();
			throw new DerechohabientesBusinessException("No pudo ser rechazada la solicitud: " + e.getCause().getMessage(),ExceptionMessages.ERROR_GUARDADO_SOLCITUD);
		}

		/*
		//Obtenemos el tramite cancelado
		try {
			tramite = solicitudDaoLocal.getTramite(idTramite, idPersona);
		} catch(Exception e) {
			e.printStackTrace();
			throw new DerechohabientesBusinessException("No fue encontrado el tramite" + e.getCause().getMessage(),ExceptionMessages.SIN_INFORMACION);
		}*/

		//return tramite;
	}

	/**
	 * Metodo para quitar de un grupo familiar a los integrantes dados de baja
	 * @param entrada La lista a filtrar
	 * @return Lis<GrupoFamiliar> lista con los integrantes del grupo familiar en estado vigente y conservacion de derechos
	 */
	private List<GrupoFamiliar> quitarBajas(List<GrupoFamiliar> entrada) {
		List<GrupoFamiliar> salida = new ArrayList<GrupoFamiliar>();

		//Recorremos la lista de los integrantes del grupo familiar
		for(GrupoFamiliar integrante: entrada) {
			//Si su estado es baja o fallecido lo quitamos de la lista de integrante
			if(integrante.getEstadoDerechohabiente().getIdEstadoDerechohabiente() != EstadoDerechohabienteEnum.BAJA.getId() &&
					integrante.getEstadoDerechohabiente().getIdEstadoDerechohabiente() != EstadoDerechohabienteEnum.FALLECIDO.getId())
				salida.add(integrante);
			log.debug("se valida que no este en estado de baja");
		}
		log.debug("se valida que no este en estado de baja1");
		return salida;
	}

	/**
	 * Metodo para quitar los integrantes del grupo familiar dados de baja del mismo tipomque se recibe,
	 * o que la baja sea por defuncion
	 * @param entrada La lista a filtrar
	 * @param tipoBaja tipo de vaja a validar
	 * @param validaBDTU si valida o no si existe el beneficiario en BDTU
	 * @return Lis<GrupoFamiliar> lista con los candidatos a dar de baja del grupo familiar
	 */
	private List<GrupoFamiliar> quitarBajasSolicitadas(List<GrupoFamiliar> entrada, int tipoBaja, boolean validaBDTU) {
		List<GrupoFamiliar> salida = new ArrayList<GrupoFamiliar>();

		//Recorremos la lista de los integrantes del grupo familiar
		for(GrupoFamiliar integrante: entrada) {
			Long idAsignacionNSS = integrante.getAsignacionNSS().getIdAsignacionNSS();
			Long idPersona = integrante.getDerechohabiente().getIdPersona();
			GrupoFamiliar candidatoBDTU = null;
			if(validaBDTU){
				try{
					candidatoBDTU = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(idAsignacionNSS, idPersona);
					integrante = candidatoBDTU;
					log.debug("Si existe el candidato: \n  - IdAsignacionNss: " + candidatoBDTU.getAsignacionNSS().getIdAsignacionNSS() +
							"\n  - IdPersona: " + candidatoBDTU.getDerechohabiente().getIdPersona());
				} catch(DerechohabientesBusinessException e){
					log.debug("El candidato no existe en BDTU");
					e.printStackTrace();
				}
				//Si el beneficiario no se encuentra en bdtu o tiene la fecha de otro grupo familiar no se toma como candidato
				if(candidatoBDTU == null || candidatoBDTU.getIndSimilarCalDifGpoFam() == null){
					log.debug("*******************Cambios Baja 2 El candidato no erxisten en bdtu idAsugnacion" +
							" "+idAsignacionNSS+" id persona "+ idPersona);
					continue;
				}
			}
			int existencia = bajaDerechohabienteEntityLocal.tieneBajaPorSolicitud(idAsignacionNSS, idPersona,tipoBaja);
			if(existencia == 0 &&
					integrante.getEstadoDerechohabiente().getIdEstadoDerechohabiente() != EstadoDerechohabienteEnum.FALLECIDO.getId()){
				salida.add(integrante);
			}else{
				log.debug("*******************Cambios Baja 2 El candidato ya cuenta con baja " +
						" "+idAsignacionNSS+" id persona "+ idPersona+" tipo baja "+tipoBaja);
			}
		}
		log.debug("se valida que no este en estado de baja1");
		return salida;
	}

	@Override
	public  List<GrupoFamiliar> quitarEstados(List<GrupoFamiliar> entrada,EstadoDerechohabienteEnum estado) {
		List<GrupoFamiliar> salida = new ArrayList<GrupoFamiliar>();

		//Recorremos la lista de los integrantes del grupo familiar
		for(GrupoFamiliar integrante: entrada) {
			//Si su estado es baja o fallecido lo quitamos de la lista de integrante
			if(!integrante.getEstadoDerechohabiente().getIdEstadoDerechohabiente().equals(estado.getId()))
				salida.add(integrante);
		}
		return salida;
	}

	/**
	 * Metodo para quitar de un grupo familiar a los integrantes dados de baja
	 * @param entrada La lista a filtrar
	 * @return Lis<GrupoFamiliar> lista con los integrantes del grupo familiar en estado vigente y conservacion de derechos
	 */
	@Override
	public List<GrupoFamiliar> quitarBajasDefuncion(List<GrupoFamiliar> entrada) {
		List<GrupoFamiliar> salida = new ArrayList<GrupoFamiliar>();

		//Recorremos la lista de los integrantes del grupo familiar
		for(GrupoFamiliar integrante: entrada) {
			//Si su estado es baja o fallecido lo quitamos de la lista de integrante
			if(!integrante.getSubEstadoDerechohabiente().getIdSubEstadoDerechohabiente().equals(SubestadoDerechohabienteEnum.FALLECIMIENTO.getId()))
				salida.add(integrante);
		}
		return salida;
	}

	/**
	 * Metodo para finalizar una solicitud de baja de derechohabiente
	 * @param solicitud - Solicitud , el objeto debe contener al menos el id de la solicitud
	 * @param asignacionNSs
	 * @return void
	 * @throws SolicitudNoValidaException
	 * @throws SolicitudNoEncontradaException
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Solicitud finalizarSolicitudBaja(Solicitud solicitud, AsignacionNSS asignacionNSS) throws SolicitudNoValidaException,
	SolicitudNoEncontradaException, SolicitudException, ImpactaAlmacenesWSException {
		
		return this.finalizaSolicitudBajaInterno(solicitud, asignacionNSS, true, null, null, null);
	}

	/**
	 * Metodo para finalizar una solicitud de baja de derechohabiente
	 * @param solicitud - Solicitud , el objeto debe contener al menos el id de la solicitud
	 * @return void
	 * @throws SolicitudNoValidaException
	 * @throws SolicitudNoEncontradaException
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Solicitud finalizarSolicitudBaja(Solicitud solicitud) throws SolicitudNoValidaException, 
	SolicitudNoEncontradaException, SolicitudException, ImpactaAlmacenesWSException {
		
		return finalizaSolicitudBaja(solicitud, null, null, null);
		
	}
	
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	@Override
	public Solicitud finalizarBajaSinConsultaSolicitud(Solicitud solicitud) throws SolicitudNoValidaException, SolicitudNoEncontradaException, SolicitudException, ImpactaAlmacenesWSException {
		
		return finalizaSolicitudBaja(solicitud, false, false, false);
	}
	
	private Solicitud finalizaSolicitudBaja(Solicitud solicitud, Boolean consultarSolicitud, Boolean actualizarXml, Boolean actualizarEstado) throws SolicitudNoValidaException, SolicitudNoEncontradaException, SolicitudException, ImpactaAlmacenesWSException {
		AsignacionNSS nss = null;
		Boolean generarFirmaElectronica = false;
		
		//se verifica si se trae el origen de la solicitud
		if(solicitud != null && solicitud.getOrigenSolicitud() != null) {
			Long idOrigenSolicitud = solicitud.getOrigenSolicitud().getIdTipoSolicitud();
			//en caso de que la solicitud venga de ventanilla o portal ciudadano
			if(!idOrigenSolicitud.equals(OrigenSolicitudEnum.INTERNET.getId())) {
				//indicamos que se generara la firma electronica
				generarFirmaElectronica = true;
				//obtenemos el tramite de baja de la solicitud
				TramiteBajaDerechohabiente baja = TramiteUtil.getTramiteBajaFromSolicitud(solicitud);
				//se obtiene el nss del tramite para generar la cadena origginal
				nss = ((Derechohabiente) baja.getPersona()).getAsignacionNSS();
			}
			
		}
		return this.finalizaSolicitudBajaInterno(solicitud, nss, generarFirmaElectronica, consultarSolicitud, actualizarXml,actualizarEstado);
	}

	private Solicitud finalizaSolicitudBajaInterno(Solicitud solicitud, AsignacionNSS asignacionNSS, Boolean generarFirma, Boolean consultarSolicitud, Boolean actualizarXml, Boolean actualizarEstado) 
			throws SolicitudNoValidaException, SolicitudNoEncontradaException, SolicitudException, ImpactaAlmacenesWSException{
		
		//sino viene la bandera de consultar solicitud significa que si la consultaremos
		consultarSolicitud = consultarSolicitud == null ? true : consultarSolicitud;
		//si no viene la bandera de actualizar xml, significa que si se tiene que actualizar
		actualizarXml = actualizarXml == null ? true :  actualizarXml;
		actualizarEstado = actualizarEstado == null ? true : actualizarEstado;			
				
		if(solicitud.getFirmaElectronica() != null) {
			firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud, solicitud.getFirmaElectronica());
		}
	
		if(consultarSolicitud) {
			//Consultamos la solicitud a finalizar
			solicitud = solicitudBusinessRemote.consultar(solicitud);
		}

		//Si la solicitud no es nula 
		if(solicitud != null) {
			//Verificamos que la solicitud contenga al menos un tramite
			if(solicitud.getTramites().isEmpty()) {
				throw new SolicitudNoValidaException("La solicitud no contiene tramites");
			} else {

				//Verificamos que la solicitud contenga tramites de tipo baja de derechohabiente
				TramiteBajaDerechohabiente tramiteBaja = TramiteUtil.getTramiteBajaFromSolicitud(solicitud);

				//Si no encontramos ningun tramite de baja lanzamos una excepcion
				if(tramiteBaja == null) {
					throw new SolicitudNoValidaException("No existen tramites de baja en la solicitud");
				}

				//Insertamos un registro en dit baja para que se calcule la vigencia del derechohabiente
				BajaDerechohabienteDto baja = bajaDerechohabienteEntityLocal.insertFromTramiteBaja(tramiteBaja);

				//En caso de que exista una fecha de defuncion en el tramite, esta se le pone al integrante de la baja
				if(tramiteBaja.getFechaDefuncion() != null) {
					//Obtenemos a la persona fisica a dar de baja por defuncion
					Fisica fisica = new Fisica();
					//Agregamos el id de la persona a actualiza
					fisica.setIdPersona(tramiteBaja.getPersona().getIdPersona());
					//agregamos la fecha de defuncion
					fisica.setFechaDefuncion(tramiteBaja.getFechaDefuncion());
					//actualizamos a la persona indicandole la fecha de defuncion
					try {
						personaBusinessRemote.actualizarPersona(fisica);
					} catch(Exception e) {
						log.error("No fue posible actualizar la fecha de defuncion en la persona", e);
					}
				}

				//guardamos los documentos probatorios del tramite
				try {
					documentoProbatorioServiceBusinessRemote.guardarDocumentosCapturados(solicitud);
				} catch(DocumentoProbatorioException e) {
					log.error("Ocurrió un error al guardar los documentos",e);
				} catch (TramiteNoEncontradoException e) {
					log.error("No se encontro tramite", e);
				}
				
				
				//Solo si el origen no es internet generamos la firma, ya que para internet
				if(generarFirma) {
					log.debug("Se generara el sello para el registro de derechohabiente");
					try {
						TipoTramite tipoTramite = catalogosDaoLocal.getTipoTramite(tramiteBaja.getTipoTramite().getIdTipoTramite().longValue());
						tramiteBaja.setTipoTramite(tipoTramite);
						tramiteDocumentosServiceLocal.generaFirmaElectronica(asignacionNSS,solicitud, tipoTramite.getDescripcion());
					} catch(Exception e) {
						log.error("ocurrio un error al generar la firma digital relacionada a la solicitud");
					}
				}
				
				
				if(actualizarXml || actualizarEstado) {
					//mandamos a llamar al servicio local para que se establezcan el resultado y la razon del resultado
					try{
						if(actualizarXml) {
							tramiteServiceLocal.actualizaXMLTramite(tramiteBaja);
						}
						
						String observaciones = null;
						if (StringUtils.isNotBlank(tramiteBaja.getObservacion())) {
							observaciones = tramiteBaja.getObservacion().length() > 255 ? tramiteBaja.getObservacion().substring(0,250) : tramiteBaja.getObservacion();
						}
						
						if(actualizarEstado) {
							solicitudServiceLocal.marcarAtendidaSolictud(solicitud.getSolicitudId(), observaciones , null);
						}
					} catch (DerechohabientesBusinessException e) {
						log.error("Ocurrio un error al actulizar la solicitud",e);
						throw new SolicitudException();
					} catch (TramiteNoEncontradoException e) {
						log.error("Ocurrio un error al actulizar la solicitud",e);
						throw new SolicitudException();
					} catch (IllegalArgumentException e) {
						log.error("Ocurrio un error al actulizar la solicitud",e);
						throw new SolicitudException();
					}
				}
				
				//Se manda llamar al WS
				try {
					finalizaSolicitudService.finalizaSolicitudBajaDerechohabiente(solicitud, baja, true);
					log.debug("aquí entra el tramite por solicitud de union civil3------------------");

				} catch(ImpactaAlmacenesWSException e) {
					log.error("Ocurrio un error al impactar el ws de vigencia", e);
					throw e;
				} catch (IllegalArgumentException e) {
					log.error(e);
					throw new SolicitudException(e.getMessage());
				} catch (Exception e) {
					log.error(e);
					throw new SolicitudException(e.getMessage());
				}
				
				
			}
		} else {
			throw new SolicitudNoValidaException("La solicitud no puede ser nula");
		}

		return solicitud;	
	}
	@Override
	public Solicitud finalizarSolicitudBajaNormativa(Solicitud solicitud)
	throws SolicitudNoValidaException, SolicitudNoEncontradaException,
	SolicitudException, Exception {

		Usuario usuario = solicitud.getSolicitante();
		solicitud = this.finalizarSolicitudBaja(solicitud);
		AsignacionNSS nss = null;
		TramiteBajaDerechohabiente tramiteBaja = this.getTramiteBaja(solicitud);
		Derechohabiente der = (Derechohabiente) tramiteBaja.getPersona();
		nss = der.getAsignacionNSS();

		int identificadorReporte = tramiteBaja.getTipoTramite().getIdTipoTramite();
		PropiedadesDocumento propiedades = new PropiedadesDocumento();
		propiedades.setIdTramite(tramiteBaja.getTramiteId());
		byte[] documento = null;
		//La generacion del documento Sav002, se realiza a traves de TramiteDocumentosService
		documento = (byte[])tramiteDocumentosServiceLocal.generaDocumentoConSelloDigital(nss, solicitud, usuario, identificadorReporte, propiedades, null);

		try {
			if(documento != null) {
				//Se buscan los medios de contacto del nss
				this.llenaMediosContacto(der.getAsignacionNSS());
				//Se buscan los medios de contacto del beneficiario a dar d ebaja
				this.llenaMediosContacto(der);
				eMailServiceLocal.enviarCorreoBajaNormativa(der, documento);
			}
		} catch(Exception e) {
			log.error("Ocurrio un error al enviar el mar de baja normativa",e);
		}

		return solicitud;
	}

	private TramiteBajaDerechohabiente getTramiteBaja(Solicitud solicitud) {

		TramiteBajaDerechohabiente tramiteBaja = null;

		for(Tramite tramite: solicitud.getTramites()) {
			if(tramite instanceof TramiteBajaDerechohabiente) {
				tramiteBaja = (TramiteBajaDerechohabiente) tramite;
				break;
			}
		}

		return tramiteBaja;
	}

	private Fisica llenaMediosContacto(Fisica miFisica)
	throws Exception {
		TipoPersona unTipoPersona = new TipoPersona();
		unTipoPersona.setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
		miFisica.setTipoPersona(unTipoPersona);
		List<MedioContacto> mediosContacto = null;
		try {
			mediosContacto = mediosContactoServiceBusinessRemote.consultarMedioDeContactoPersona(miFisica);
		} catch (PersonaSinMedioDeContactoException e) {
			log.error("Sin medios de contacto", e);
			mediosContacto = null;
		}

		if(mediosContacto != null){ 				 
			Iterator<MedioContacto> it =  mediosContacto.iterator();
			while(it.hasNext()){ 
				MedioContacto m = it.next(); 
				if(m instanceof TelefonoFijo){
					TelefonoFijo telefonoFijo = (TelefonoFijo)m;
					miFisica.setTelefonoFijo(telefonoFijo);
				}else if ( m instanceof TelefonoMovil){

					TelefonoMovil telefonoMovil = (TelefonoMovil)m;
					miFisica.setTelefonoMovil(telefonoMovil);

				}else if (m instanceof CorreoElectronico){
					CorreoElectronico correoElectronico = (CorreoElectronico)m;
					miFisica.setCorreoElectronico(correoElectronico);
				}else if (m instanceof Facebook){
					Facebook facebook = (Facebook)m;
					miFisica.setFacebook(facebook);

				}else if ( m instanceof Twitter){
					Twitter twitter = (Twitter)m;
					miFisica.setTwitter(twitter);
				} 
			}
		}			
		return miFisica;
	}
	
	
	/**
	 * Metodo para quitar de un grupo familiar a los integrantes dados de baja
	 * @param entrada La lista a filtrar
	 * @return Lis<GrupoFamiliar> lista con los integrantes del grupo familiar en estado vigente y conservacion de derechos
	 */
	@Override
	public List<GrupoFamiliar> quitarBajasNoAdministrativas(List<GrupoFamiliar> entrada) {
		List<GrupoFamiliar> salida = new ArrayList<GrupoFamiliar>();

		try{
			
		
			if( (entrada != null) && ( entrada.size() > 0 ) ){
				
				List<Long> idPersonas = new ArrayList<Long>();
				
				// --------------------------------------------------------------------------------
				// Obtenemos los idPersona de los integrantes en baja para realizar una sola 
				// consulta a la base de datos
				// --------------------------------------------------------------------------------
				for(GrupoFamiliar integrante: entrada){ 
					
					if(integrante.getEstadoDerechohabiente().getIdEstadoDerechohabiente() == EstadoDerechohabienteEnum.BAJA.getId()){
						idPersonas.add(integrante.getDerechohabiente().getIdPersona());
					}else{
						salida.add(integrante);
					}
				}
				
				
				
				// -------------------------------------------------------
				// Existen integrantes en baja
				// -------------------------------------------------------
				if( idPersonas.size() > 0 ){
			
					List<Long> tiposBaja = new ArrayList<Long>();
					tiposBaja.add(TipoBajaDerechohabienteEnum.SUSPENCION.getId());
					
					// -----------------------------------------------------------------------
					// Obtenemos todas las personas con baja administrativa activa
					// -----------------------------------------------------------------------
					List<BajaDerechohabienteDto> bajas = bajaDerechohabienteEntityLocal.getBajaDerechohabiente(
							entrada.get(0).getAsignacionNSS().getIdAsignacionNSS(), idPersonas, tiposBaja, true);
				
					if( bajas != null ){
						
						for(GrupoFamiliar integrante: entrada) {
							if( bajas.contains(integrante) ){
								salida.add(integrante);
							}
						}
						
					}
					
					
				}
					
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
			
		return salida;
		
	}
	
	@Override
	public List<GrupoFamiliar> quitarIntegrantesConTramiteBaja(List<GrupoFamiliar> grupoFamiliar, List<Long> tiposBaja ) {
		List<GrupoFamiliar> grupo = new ArrayList<GrupoFamiliar>();
		
		if(grupoFamiliar != null && !grupoFamiliar.isEmpty()) {
			List<Long> idPersonas = new ArrayList<Long>();
			
			// --------------------------------------------------------------------------------
			// Obtenemos los idPersona de los integrantes en baja para realizar una sola 
			// consulta a la base de datos
			// --------------------------------------------------------------------------------
			for(GrupoFamiliar integrante: grupoFamiliar){ 
				idPersonas.add(integrante.getDerechohabiente().getIdPersona());
			}
			
			// -------------------------------------------------------
			// Existen integrantes en baja
			// -------------------------------------------------------
			if( idPersonas.size() > 0 ){
				// -----------------------------------------------------------------------
				// Obtenemos todas las personas con baja administrativa activa
				// -----------------------------------------------------------------------
				List<BajaDerechohabienteDto> bajas = bajaDerechohabienteEntityLocal.getBajaDerechohabiente(
						grupoFamiliar.get(0).getAsignacionNSS().getIdAsignacionNSS(), idPersonas, tiposBaja, true);
			
				if( bajas != null ){
					
					for(GrupoFamiliar integrante: grupoFamiliar) {
						if( !bajas.contains(integrante) ){
							grupo.add(integrante);
						}
					}
					
				} else {
					grupo = grupoFamiliar;
				}
				
				
			}
		}
			
		return grupo;
		
	}
	

	@Override
	public void actualizarInsertarBaja(BajaDerechohabienteDto bajaDto) throws IllegalArgumentException, TransactionRequiredException {
		bajaDerechohabienteEntityLocal.saveOrUpdate(bajaDto);
	}
	
	@Override
	public boolean tieneBajaAdministrativaActiva(Long idAsignasionNss, Long idPersona){
		return bajaDerechohabienteEntityLocal.tieneBajaAdministrativaActiva(idAsignasionNss, idPersona);
	}
		
	
}