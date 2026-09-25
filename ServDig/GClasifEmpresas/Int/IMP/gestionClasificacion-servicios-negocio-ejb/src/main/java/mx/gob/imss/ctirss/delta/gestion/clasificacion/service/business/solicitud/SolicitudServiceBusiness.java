/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: SolicitudServiceBusiness.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.solicitud
 *  @Fecha:04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.solicitud;

import static mx.gob.imss.ctirss.delta.gestion.clasificacion.util.Utiles.isNumerico;

import java.util.ArrayList;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.delta.exception.clasificacion.ClasificacionException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.DictamenDTO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.analisis.AnalisisServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.solicitud.SolicitudServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.ConfiguracionCeBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.model.clasificacion.ConfiguracionCe;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisEnum;
import mx.gob.imss.ctirss.delta.model.clasificacion.FiltrosAnalisisConsulta;
import mx.gob.imss.ctirss.delta.model.clasificacion.SolicitudConcluida;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.enums.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteDictamen;
/**
 * @author Jaime Ramirez N.
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 02/01/2012
 */
@Stateless(name = "solicitudServiceBusiness", mappedName = "solicitudServiceBusiness")
public class SolicitudServiceBusiness extends AbstractServiceBusiness implements SolicitudServiceBusinessRemote {

	@EJB
	private SujetoObligadoServiceBusinessRemote sujetoObligadoService;

	@EJB
	private SolicitudServiceEntityLocal solicitudEntity;
	
	@EJB
	private ConfiguracionCeBusinessRemote configuracionCeBusiness;
	
	@EJB
	private SolicitudBusinessRemote solicitudBusinessRemote;
	
	@EJB
	private DomicilioServiceBusinessRemote domicilioService;
	
	@EJB
	private AnalisisServiceEntityLocal analisisEntity;
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.imss.ctirss.delta.gestion.patron.service.utility.interfaces.SujetoObligadoServiceBusinessRemote
	 * #obtenerDetalleSolicitud(mx.gob.imss.ctirss.delta.gestion.patron.model.SujetoObligado)
	 */
	@Override
	public SujetoObligado obtenerDetalleSolicitud(SujetoObligado model) throws Exception {
		log.debug("INICIO PARA CONSULTAR LA SOLICITUD X CLAVE");
		
		try {
			model = sujetoObligadoService.obtenerDetalleSujetoObligadoActividadEconomica(model);
			log.info("***********" + "\n***********");
			log.info("***********" + "\n***********");
			log.info("***********" + "\n***********");
			log.debug("BUSINESS - La CLASE es:: " + model);
			log.info("***********" + "\n***********");
			log.info("***********" + "\n***********");
			log.info("***********" + "\n***********");
		} catch (Exception e) {
			log.error("ERROR- " + e.getMessage());
		}
		log.debug("FIN PARA CONSULTAR LA SOLICITUD X CLAVE");
		return model;
	}
	
	@Override
	public SujetoObligado obtenerDetalleSolicitudDictamen(SujetoObligado modeloSujetoObligado) throws Exception { 
		

		log.debug("INICIO PARA CONSULTAR LA SOLICITUD PARA OBTENER EL DICTAMEN");
	
		System.out.println("PERSONA FISCAL: "+modeloSujetoObligado.getTipoPersonaFiscal());
		log.debug("REGISTRO PATRONAL A OBTENER LA CLASFICACIÓN: "+modeloSujetoObligado.getNumeroRegistroPatronal());
		System.out.println("MODALIDAD: "+modeloSujetoObligado.getModalidad());
		System.out.println("DIGITO VER: "+modeloSujetoObligado.getDigVerificador());
		
		setRPCompleto(modeloSujetoObligado);
		
		modeloSujetoObligado = sujetoObligadoService.consultarPorRegistroPatronal(
				modeloSujetoObligado.getNumeroRegistroPatronal(), modeloSujetoObligado.getTipoPersonaFiscal());
		
		if(modeloSujetoObligado == null) {
//			Se crea mensaje para enviar a la pantalla principal
			return null;
		}

		log.debug("RELACIONES SUJETO OBLIGADO");
		modeloSujetoObligado = obtenerDetalleRP(modeloSujetoObligado);
		if(modeloSujetoObligado == null) {
//			Se crea mensaje para enviar a la pantalla principal
			return null;
		}
		log.debug("FIN PARA CONSULTAR LA SOLICITUD DEL DICTAMEN");
		return modeloSujetoObligado;
	}

	@Override
	public DatosSalidaPaginador<SolicitudConcluida> consultarSolicitudesConcluidas(
			DatosEntradaPaginador<FiltrosAnalisisConsulta> parametrosPaginador) {
		return solicitudEntity.consultarSolicitudesConcluidas(parametrosPaginador);
	}

	/**
	 * (non-Javadoc)
	 * @throws Exception 
	 * @see mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud.SolicitudServiceBusinessRemote#obtieneConfiguracionCe(cveIdAnalisis, cveIdUsuario, cveIdRol)
	 */
	@Override
	public ConfiguracionCe obtenerConfiguracionCe(final Long cveIdAnalisis, final String cveIdUsuario, final Integer cveIdRol) throws Exception{
		return configuracionCeBusiness.configurarOperaciones(cveIdAnalisis, cveIdUsuario, cveIdRol);
	}
	
	/**
	 * Cambia el Estatus a CANCELADO de los An�lisis encontrados a partir del Registro Patronal
	 * @throws ClasificacionException 
	 * @throws PersistenceException 
	 * 
	 */
	@Override
	public void cancelarAnalisisPorRegistroPatronal(
			final String regPatronal,
			final int estadoCancelacion,
			final mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud) throws PersistenceException, ClasificacionException {
		log.debug("El registro patronal para el analisis es " + regPatronal);
		if(regPatronal!=null && !regPatronal.trim().equals("") &&
				((regPatronal.length()==11 && isNumerico(regPatronal.substring(10)))
						|| (regPatronal.length()==10 && isNumerico(regPatronal.substring(7, 9)))
						|| regPatronal.length()==8)){
			solicitudEntity.cancelarAnalisisPorRegistroPatronal(regPatronal, estadoCancelacion, solicitud);
		}else{
			log.debug("La longitud del RP es inv�lida, debe ser de 8, 10 u 11 caracteres");
		}
	}
	
	@Override
	public void crearAnalisisPorRegistroPatronalDictamen(
			final String regPatronal,
			final mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud) throws PersistenceException, ClasificacionException {
		log.debug("El registro patronal para el analisis es " + regPatronal);
		if(regPatronal!=null && !regPatronal.trim().equals("") &&
				((regPatronal.length()==11 && isNumerico(regPatronal.substring(10)))
						|| (regPatronal.length()==10 && isNumerico(regPatronal.substring(7, 9)))
						|| regPatronal.length()==8)){
			solicitudEntity.crearAnalisisPorRegistroPatronalDictamen(regPatronal,  solicitud);
		}else{
			log.debug("La longitud del RP es inválida, debe ser de 8, 10 u 11 caracteres");
		}
	}
	
	/**
	 * Verifica si la solicitud tiene varias clasificaciones
	 * @param cveIdSolicitud
	 * @return boolean
	 */
	public boolean consultaReintentoRPC(Long cveIdSolicitud){
		return solicitudEntity.consultaReintentoRPC(cveIdSolicitud);
	}

	/**
	 * Obtiene Registro Patronal Completo,
	 * el orden a enviar es: RP, Modalidad y D�gito Verificador
	 */
	@Override
	public String obtenerRegistroPatronalCompleto(String regPatron){
		switch (regPatron.length()) {
		case 8:
			regPatron=solicitudEntity.obtenerRegistroPatronalCompleto(regPatron.substring(0, 8));
			break;
		case 10:
			regPatron=solicitudEntity.obtenerRegistroPatronalCompleto(regPatron.substring(0, 8), regPatron.substring(7, 9));
			break;
		}
		return regPatron;
	}
	@Override
	public Long crearVistaDictamen(SujetoObligado sujeto, DictamenDTO dictamen) {
		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitante(new Usuario());
		solicitud.getSolicitante().setUsuario(dictamen.getUsuario());
		solicitud.setTipoSolicitud(new TipoSolicitud(60L));//Solicitud tipo de registro de obra
		solicitud.setFechaPresentacion(new Date());
		solicitud.setFechaConclusion(new Date());
		solicitud.setOrigenSolicitud(new OrigenSolicitud());
		solicitud.getOrigenSolicitud().setIdTipoSolicitud(OrigenSolicitudEnum.INTERNET.getId());
		solicitud.setEstadoSolicitud(new EstadoSolicitud(EstadoSolicitudEnum.ATENDIDA.getId().intValue()));
		//creamos la lista de tramites, solo va a lleva uno
		solicitud.setTramites(new ArrayList<Tramite>());
		TramiteDictamen tramiteSO = new TramiteDictamen();
		tramiteSO.setFechaEfecto(new Date());
		tramiteSO.setSujetoObligado(sujeto);
		tramiteSO.setIdEjercicioFiscal(dictamen.getIdEjercicio());
		tramiteSO.setIdPatronDictamen(dictamen.getCveIdPatronDictamen());
		tramiteSO.setFechaPresentacion(new Date());
		tramiteSO.setFechaConclusion(new Date());
		tramiteSO.setTipoTramite(new TipoTramite(167));
		tramiteSO.setEstadoTramite(new EstadoTramite());
		tramiteSO.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getValor());
		//anadimos el tramite
		solicitud.getTramites().add(tramiteSO);
		solicitud.setSujetoObligado(sujeto);
		
		//Creamos el tramite y la solicitud
		try {
			solicitud = solicitudBusinessRemote.crear(solicitud);
		} catch (SolicitudNoValidaException e) {
			e.printStackTrace();
			solicitud = null;
		}
		
		log.debug("Viene la clasificacion del patron " + sujeto.getClasificacion());
		solicitud.setSujetoObligado(sujeto);
		try {
			//puede ser aqui
			this.crearAnalisisPorRegistroPatronalDictamen(dictamen.getRegistroPatronal(),  solicitud);
		} catch (PersistenceException e) {
			e.printStackTrace();
		} catch (ClasificacionException e) {
			e.printStackTrace();
		}
		
		return solicitud.getSolicitudId();
	}
	
	///cambio para MM
	@Override
	public void actualizaEstatusInconsistencia(Long cveIdAnalisis) throws Exception{
		AnalisisClasificacionEmpresas analisisClasificacionEmpresas = new AnalisisClasificacionEmpresas();
		analisisClasificacionEmpresas.setCveIdAnalisis(cveIdAnalisis);
		analisisClasificacionEmpresas.setCveIdEstatus(Long.valueOf(EstatusAnalisisEnum.IMPOSIBILIDAD_DE_ANALISIS.getClave()));
		analisisClasificacionEmpresas.setEstatus(EstatusAnalisisEnum.IMPOSIBILIDAD_DE_ANALISIS);	
		analisisEntity.actualizaEstado(analisisClasificacionEmpresas);
	}
	
	@Override
	public Long crearSolicitudDictamen(SujetoObligado sujeto, DictamenDTO dictamen) {
		
		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitante(new Usuario());
		solicitud.getSolicitante().setUsuario(dictamen.getUsuario());
		solicitud.setTipoSolicitud(new TipoSolicitud(60L));//Solicitud tipo de registro de obra
		solicitud.setFechaPresentacion(new Date());
		solicitud.setFechaConclusion(new Date());
		solicitud.setOrigenSolicitud(new OrigenSolicitud());
		solicitud.getOrigenSolicitud().setIdTipoSolicitud(OrigenSolicitudEnum.INTERNET.getId());
		solicitud.setEstadoSolicitud(new EstadoSolicitud(EstadoSolicitudEnum.ATENDIDA.getId().intValue()));
		//creamos la lista de tramites, solo va a lleva uno
		solicitud.setTramites(new ArrayList<Tramite>());
		TramiteDictamen tramiteSO = new TramiteDictamen();
		tramiteSO.setFechaEfecto(new Date());
		tramiteSO.setSujetoObligado(sujeto);
		tramiteSO.setIdEjercicioFiscal(dictamen.getIdEjercicio());
		tramiteSO.setIdPatronDictamen(dictamen.getCveIdPatronDictamen());
		tramiteSO.setFechaPresentacion(new Date());
		tramiteSO.setFechaConclusion(new Date());
		tramiteSO.setTipoTramite(new TipoTramite(167));
		tramiteSO.setEstadoTramite(new EstadoTramite());
		tramiteSO.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getValor());
		//anadimos el tramite
		solicitud.getTramites().add(tramiteSO);
		solicitud.setSujetoObligado(sujeto);
		
		//Creamos el tramite y la solicitud
		try {
			solicitud = solicitudBusinessRemote.crear(solicitud);
		} catch (SolicitudNoValidaException e) {
			e.printStackTrace();
			solicitud = null;
		}
		
		log.debug("Viene la clasificacion del patron " + sujeto.getClasificacion());
		solicitud.setSujetoObligado(sujeto);
		try {
			this.cancelarAnalisisPorRegistroPatronal(dictamen.getRegistroPatronal(), EstatusAnalisisEnum.CANCELADO_POR_NUEVO_TRAMITE_DE_GCE.getClave(), solicitud);
		} catch (PersistenceException e) {
			e.printStackTrace();
		} catch (ClasificacionException e) {
			e.printStackTrace();
		}
		
		return solicitud.getSolicitudId();
	}
	
//	Se arma RP completo del sujeto obligado
	private void setRPCompleto(SujetoObligado modeloSujetoObligado) {
		if(modeloSujetoObligado.getNumeroRegistroPatronal().length() == 8 ) {
			String numeroRPCompleto = modeloSujetoObligado.getNumeroRegistroPatronal();
			
			if(modeloSujetoObligado.getModalidad() != null && modeloSujetoObligado.getModalidad().getNumModalidad() != null) {
				numeroRPCompleto += modeloSujetoObligado.getModalidad().getNumModalidad();
				
				if(modeloSujetoObligado.getDigVerificador() != null) {
					numeroRPCompleto += modeloSujetoObligado.getDigVerificador();
				}
			}
			
			System.out.println("SE COMPLETA EL RP: "+numeroRPCompleto);
			modeloSujetoObligado.setNumeroRegistroPatronal(numeroRPCompleto);
		}
		
	}
	


	private SujetoObligado obtenerDetalleRP(SujetoObligado modeloSujetoObligado) {
		
		try {
			modeloSujetoObligado = sujetoObligadoService.obtenerDetallesRegistroPatronal(modeloSujetoObligado);
		}catch(Exception e) {
			return null;
		}
		
		if(modeloSujetoObligado == null) {
			return null;
		}
		
		
		
//		Se selecciona persona fisica o moral
		Persona persona = modeloSujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)?
				modeloSujetoObligado.getFisica() : modeloSujetoObligado.getMoral();
				
		Domicilio domicilioEncontrado = obtenerDomicilioFiscal(persona);
		
		DomicilioFiscal domicilioFiscal = new DomicilioFiscal();
		if(domicilioEncontrado != null) {
			domicilioFiscal = sujetoObligadoService.convertDomicilioToDomicilioFiscal(domicilioEncontrado);
			System.out.println("DOMICILIO FISCAL::: "+domicilioFiscal);
		}
		modeloSujetoObligado.setDomicilioFiscal(domicilioFiscal);
		CentroTrabajo centroT = new CentroTrabajo();
		Long idDomicilioCT = sujetoObligadoService.consultarClaveDomicilioCentroTrabajo(modeloSujetoObligado.getCveIdSujetoObligado());
		
		if(idDomicilioCT != null) {
			System.out.println("SE ENCONTRÓ EL DOMICILIO: "+idDomicilioCT);
			CentroTrabajo centroTrabajo = new CentroTrabajo();
			centroTrabajo.setClave(idDomicilioCT.intValue());
			
			try {
				Domicilio cTrabajo = domicilioService.consultarDomicilio(centroTrabajo);
				centroT = sujetoObligadoService.convertirDomicilioACentroTrabajo(cTrabajo);
				
			} catch(DomicilioNoLocalizadoException e) {
				System.err.println("NO SE ENCONTRO EL DOMICILIO");
			}
			modeloSujetoObligado.setCntroTrabajo(centroT);
			
		} else {
			System.out.println("NO SE ENCONTRÓ NINGUN CENTRO DE TRABAJO DENTRO DE LA NORMA TECNICA ASOCIADO AL PATRON: "+modeloSujetoObligado.getCveIdSujetoObligado());
			String domicilio = sujetoObligadoService.obtenerDomicilioMigrado(modeloSujetoObligado.getCveIdSujetoObligado());
			centroT =  new CentroTrabajo();
			centroT.setDescripcion(domicilio);
			modeloSujetoObligado.setCntroTrabajo(centroT);
			
		}
		
		return modeloSujetoObligado;
	}

	private Domicilio obtenerDomicilioFiscal(Persona persona){
		Domicilio domicilioFiscal = new Domicilio();
		Long idPersona = persona.getIdPersona();
		TipoPersonaFiscal tipoPersona = null;
		
		if(persona instanceof Fisica) {
			tipoPersona = TipoPersonaFiscal.FISICA;
		} else if(persona instanceof Moral) {
			tipoPersona = TipoPersonaFiscal.MORAL;
		}
		
		Long idDomicilio = sujetoObligadoService.consultarClaveDomicilioFiscal(idPersona, tipoPersona);
		
		System.out.println("DOMICILIO FISCAL ENCONTRANDO: "+idDomicilio);
		try {
			if(idDomicilio != null) {
				domicilioFiscal = new Domicilio();
				Integer claveDom = idDomicilio != null? idDomicilio.intValue() : null;
				domicilioFiscal.setClave(claveDom);
				
				domicilioFiscal = domicilioService.consultarDomicilio(domicilioFiscal);
			
			} else {
				domicilioFiscal = domicilioService.consultarDomicilioFiscalPersona(persona);
				
			}
		} catch(DomicilioNoLocalizadoException e) {
			System.err.println("No existe domicilio fiscal geografico");
		}
	
		
		
		return domicilioFiscal;
	}
}