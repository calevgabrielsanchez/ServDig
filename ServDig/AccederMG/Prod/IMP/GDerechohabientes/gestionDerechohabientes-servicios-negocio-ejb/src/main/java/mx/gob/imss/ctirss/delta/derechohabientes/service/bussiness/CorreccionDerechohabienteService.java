package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TransactionRequiredException;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.CatalogosDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.DescripcionesDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.GrupoFamiliarDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.MedicoEnTurnoDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.RegistroDerechohabientesDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.entity.AcuerdoDerechohabienteEntityLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.entity.CircunscripcionEntityLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.entity.CorreccionDerechohabienteEntityLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.AgregadoMedicoServiceLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.BajaDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CorreccionDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.util.DateUtils;
import mx.gob.imss.ctirss.delta.derechohabientes.util.DeltaUtils;
import mx.gob.imss.ctirss.delta.derechohabientes.util.TramiteUtil;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.DocumentoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.CalificacionesPersonaBusinessServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudTramiteBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.EstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoBajaDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.BajaDerechohabienteDto;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.RequisitosDTO;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilioEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.RazonResultadoEnum;
import mx.gob.imss.ctirss.delta.model.enums.SexoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.AfectarDatosPersonaWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.CalificacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaDomicilio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteBajaDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCircunscripcionForanea;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.delta.model.util.Constants;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.AfectarDatosPersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

import org.apache.commons.lang.StringUtils;

/**
 * @author Mario Teran Blanco
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 17/04/2012
 */
@Stateless(name = "correccionDerechohabienteService", mappedName = "correccionDerechohabienteService")
public class CorreccionDerechohabienteService extends AbstractServiceBusiness
		implements CorreccionDerechohabienteServiceRemote,
		CorreccionDerechohabienteServiceLocal {

	@EJB(name = "grupoFamiliarDao")
	private GrupoFamiliarDaoLocal grupoFamiliarDaoLocal;
	@EJB(name = "solicitudService")
	private SolicitudServiceLocal solicitudServiceLocal;
	@EJB(name = "personaBusiness", mappedName = "personaBusiness")
	private PersonaBusinessRemote personaBusinessRemote;
	@EJB(name = "afectarDatosPersonaBusiness", mappedName = "afectarDatosPersonaBusiness")
	private AfectarDatosPersonaBusinessRemote afectarDatosPersonaBusinessRemote;
	@EJB(mappedName = "domicilioServiceBusiness")
	private DomicilioServiceBusinessRemote domicilioServiceBusinessRemote;
	@EJB(name = "tramiteService")
	private TramiteServiceLocal tramiteServiceLocal;
	@EJB(name = "descripcionesDao")
	private DescripcionesDaoLocal descripcionesDaoLocal;
	@EJB(name = "solicitudBusiness", mappedName = "solicitudBusiness")
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@EJB(name = "solicitudTramiteBusiness", mappedName = "solicitudTramiteBusiness")
	private SolicitudTramiteBusinessRemote solicitudTramiteBusinessRemote;
	@EJB(name = "calificacionesPersonaBusinessService", mappedName = "calificacionesPersonaBusinessService")
	private CalificacionesPersonaBusinessServiceRemote calificacionesPersonaBusinessService;
	@EJB
	private RegistroDerechohabientesDaoLocal registroDerechohabientesDaoLocal;
	@EJB
	private CircunscripcionEntityLocal circunscripcionEntityLocal;
	@EJB
	private CorreccionDerechohabienteEntityLocal correccionDerechohabienteEntityLocal;
	@EJB(name = "documentoProbatorioServiceBusiness", mappedName = "documentoProbatorioServiceBusiness")
	private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusinessRemote;
	@EJB(mappedName = "firmaDigitalBusiness", name = "firmaDigitalBusiness")
	private FirmaDigitalBusinessRemote firmaDigitalBusinessRemote;
	@EJB
	private LocalizarPersonaFisicaEnRENAPOServiceBusinessRemote localizarPersonaFisicaEnRENAPOServiceBusiness;
	@EJB
	private DomicilioServiceBusinessRemote domicilioServiceBusiness;
	@EJB
	private FinalizaSolicitudServiceLocal finalizaSolicitudServiceLocal;
	@EJB
	private UmfServiceLocal umfServiceLocal;
	@EJB
	private GrupoFamiliarServiceLocal grupoFamiliarServiceLocal;
	@EJB
	private TramiteDocumentosServiceLocal tramiteDocumentoService;
	@EJB
	private CatalogosDaoLocal catalogosDAO;
	@EJB
	private RequisitosMinimosServiceLocal requisitosMinimosServiceLocal;
	@EJB
	private BajaDerechohabienteServiceRemote bajaDerechohabienteService; 
	@EJB
	private MedicoEnTurnoDaoLocal medicoEnTurnoDAO;
	@EJB
	private AsignacionDomicilioServiceLocal asignacionDomicilioServiceLocal;
	@EJB
	private ConsumidorServiciosMediosContactoLocal consumidorServiciosMediosContactoLocal;
	@EJB
	private AgregadoMedicoServiceLocal agregadoMedicoServiceLocal;
	
	@Override
	public Solicitud finalizarSolicitudDomicilioClinicaCircunscripcion(
			Solicitud solicitud) throws DerechohabientesBusinessException,
			SolicitudNoValidaException, SolicitudNoEncontradaException,
			SolicitudException {

		log.debug("Entramos a finalizar la solicitud de correccion de datos de derechohabiente");
		//Guardamos los datos de la firma electronica
		if(solicitud.getFirmaElectronica() != null) {
			firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud, solicitud.getFirmaElectronica());
		}
		
		solicitud = solicitudBusinessRemote.consultar(solicitud);
		
		TramiteCorreccionDerechohabiente correccion = null;
		GrupoFamiliar integrante = null;
		Derechohabiente derechohabiente = null;
		Domicilio nuevoDomicilio = null;
		MedicoEnTurno adscripcionAsegurado = null;
		CabezaGrupoFamiliar cabeza = null;
		Boolean asignacion = false;

		if (solicitud != null) {
			// Se valida que exista un tramite valido
			for (Tramite tramite : solicitud.getTramites()) {
				if (tramite instanceof TramiteCorreccionDerechohabiente) {
					correccion = (TramiteCorreccionDerechohabiente) tramite;
					break;
				}
			}

			// si no hay tramite de correccion mandamos una excepcion
			if (correccion == null) {
				throw new SolicitudNoValidaException(
						"No se encontraron tramites validos en la solicitud");
			}
			// sacamos el domicilio que actualizaremos o insertaremos
			nuevoDomicilio = correccion.getDomicilio();
			nuevoDomicilio = this.guardarDomicilioNuevo(nuevoDomicilio);
			correccion.setDomicilio(nuevoDomicilio);
			
			// Validamos que exista la persona dentro del tramite
			Fisica fisica = correccion.getPersona();
			// Validamos si el tramite se aplicara a un derechohabiente
			if (fisica instanceof Derechohabiente) {
				derechohabiente = (Derechohabiente) fisica;
			}
			// si no es de derechohabientes tan solo actualizamos
			// persona-domicilio
			if (derechohabiente == null) {
				log.debug("La persona a quien se le hace el cambio de domicilio no es un derechohabiente");
				
				this.guardarDom(fisica, nuevoDomicilio);

			} else {// Si es derechohabiente
				log.debug("la persona a quien se le hace el cambio o asignacion es un derechohabiente");
				try {
					log.debug("Consultamos al integrante del grupo familiar");
					cabeza = grupoFamiliarDaoLocal.getCabezaGrupoFamiliar(derechohabiente.getAsignacionNSS().getIdAsignacionNSS());
					integrante = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(derechohabiente.getAsignacionNSS().getIdAsignacionNSS(),
									derechohabiente.getIdPersona());
					
					if (integrante != null) {
						if(integrante.getCvePersonaDomicilio() == null || integrante.getMedicoEnTurno() == null) {
							asignacion = true;
						}
						
						log.debug("El integrante ha sido encontrado");
						Long parentesco = integrante.getParentesco().getIdParentesco();
						log.debug("el parentesco del derechohabientes es: "+ parentesco);
						AsignacionNSS nss = integrante.getAsignacionNSS();
						
						Boolean isAsegurado = parentesco.equals(ParentescoEnum.ASEGURADO.getId()) || parentesco.equals(ParentescoEnum.PENSIONADO.getId());
						// Solo si el que se esta registrando no es el asegurado
						// verificaremos si es necesario crear un tramite de
						// autorizacion para recibir servicios en
						// circunscripcion foranea o de cambio de clinica
						if (!isAsegurado) {
							log.debug("El derechohabiente no es un asegurado");

							GrupoFamiliar registroAsegurado = null;

							try {
								registroAsegurado = grupoFamiliarServiceLocal.getCabezaGrupoFamiliar(nss.getIdAsignacionNSS());
							} catch (Exception e) {
								DerechohabientesBusinessException.throwException("No fue posible consultar al integrante asegurado/pensionado del grupo familiar");
							}


							if (registroAsegurado == null || registroAsegurado.getMedicoEnTurno()==null) {
								DerechohabientesBusinessException.throwException("No fue posible localizar los datos de adscripcion del asegurado/pensionado");
							}
							//Obtenemos los datos de adscripcion del asegurado
							adscripcionAsegurado = registroAsegurado.getMedicoEnTurno();
							
							
							// Obtenemos la umf del asegurado y del integrante a
							// registrar
							Long idUmfAsegurado = adscripcionAsegurado.getUnidadMedicaFamiliar().getIdUMF();
							Long idUmfIntegrante = correccion.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF();
							Long idUmfAnteriorIntegrante = null;
							
							if(integrante.getMedicoEnTurno() != null) {
								try{
									idUmfAnteriorIntegrante = integrante.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF();
								}catch( NullPointerException e ){
									// ----------------------------------------
									// En caso de que no tenga UMF
									// ----------------------------------------
								}
							}
							
							//si el integrante no tenia umf el tramite es de asignacion de Umf
							if(idUmfAnteriorIntegrante == null) {
								
								Long cvePersonaDomicilio = this.guardarDom(integrante.getDerechohabiente(), nuevoDomicilio);
								integrante.setCvePersonaDomicilio(cvePersonaDomicilio);
								integrante.setMedicoEnTurno(correccion.getMedicoEnTurno());
								
								this.guardarTramiteAsignacionUmfDependiente(integrante, solicitud.getSolicitudId(),cabeza.getPatronImss(),false,asignacion);
							} else {
								//Checamos si cambi� de umf
								if(!idUmfAnteriorIntegrante.equals(idUmfIntegrante)) {
									// Verificamos si las umfs estan dentro de la
									// misma circunscripcion
									Boolean mismaCircunscripcion = umfServiceLocal.mismaCircunscripcion(idUmfAsegurado,idUmfIntegrante);
									// Si las umfs estan dentro de la misma
									// circunscripcion crearemos un tramite de
									// cambio de clinica
									if (mismaCircunscripcion) {
										
										// Tramiteque hara el cambio de clinica
										TramiteCorreccionDerechohabiente tramiteCambio = new TramiteCorreccionDerechohabiente();
										tramiteCambio.setIdPersona(integrante.getDerechohabiente().getIdPersona());
										tramiteCambio.setPersona(integrante.getDerechohabiente());
										tramiteCambio.setIdAsignacionNss(nss.getIdAsignacionNSS());
										tramiteCambio.setMedicoEnTurno(correccion.getMedicoEnTurno());
										tramiteCambio.setDomicilio(correccion.getDomicilio());

										this.guardarTramiteCambioClinicaDependiente(tramiteCambio, nss, nss,cabeza.getPatronImss().equals(1), null,
														solicitud.getSolicitudId(),integrante,asignacion);

									} else { 
										Long cvePersonaDomicilio = this.guardarDom(integrante.getDerechohabiente(), nuevoDomicilio);
										integrante.setCvePersonaDomicilio(cvePersonaDomicilio);
										
										// De lo contrario si no estan dentro de la misma delegacion crearemos un tramite de autorizacion
										// para recibir servicios en circunscripcionforanea
										TramiteCircunscripcionForanea circunscripcion = new TramiteCircunscripcionForanea();
										circunscripcion.setMedicoEnTurnoOrigen(registroAsegurado.getMedicoEnTurno());
										circunscripcion.setMedicoEnTurnoDestino(correccion.getMedicoEnTurno());
										circunscripcion.setDomicilioOrigen(registroAsegurado.getDomicilio());
										circunscripcion.setDomicilioDestino(correccion.getDomicilio());
										circunscripcion.setPersona(integrante.getDerechohabiente());

										Usuario usuario = new Usuario();
										nss = integrante.getAsignacionNSS();
										usuario.setUsuario(integrante.getAsignacionNSS().getCurp());
										usuario.setFisica(integrante.getAsignacionNSS());

										this.guardarTramiteCircunscripcionDependiente(circunscripcion, usuario, nss, solicitud.getSolicitudId(),integrante);

									}
								} else {
									Long cvePersonaDomicilio = this.guardarDom(integrante.getDerechohabiente(), nuevoDomicilio);
									integrante.setCvePersonaDomicilio(cvePersonaDomicilio);
									// si no es circunscripcion o cambio de clinica
									this.guardarTramiteCambioMedicoDependiente(integrante, solicitud.getSolicitudId(),asignacion);
								}
							}
							
						} else {
							log.debug("El integrante es un asegurado");
							Domicilio domicilioAsegurado = integrante.getDomicilio();
							adscripcionAsegurado = integrante.getMedicoEnTurno();
							//Se setea cero en caso de que no venga umf 
							Long idUmfAsegurado = adscripcionAsegurado != null ? adscripcionAsegurado.getUnidadMedicaFamiliar().getIdUMF() : 0L;
							Long idUmfIntegrante = correccion.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF();

							log.debug("El asegurado tiene umf? " + adscripcionAsegurado != null);
							log.debug("El id de la umf anteior es: " + idUmfAsegurado + ", y el de la nueva es: " + idUmfIntegrante);
							log.debug("El asegurado tiene domicilio? " + domicilioAsegurado == null);
							
							//Verificamos si los datos de adscripcion son nulos
							//En caso de no serlos checaremos la circunscripcion de la nueva umf con respecto a la anterior
							if(adscripcionAsegurado != null) {
								if (!idUmfAsegurado.equals(idUmfIntegrante)) {
									log.debug("la umf del asegurado es diferente a la que se eligio");
									
									// Tramite que hara el cambio de clinica
									TramiteCorreccionDerechohabiente tramiteCambio = new TramiteCorreccionDerechohabiente();
									tramiteCambio.setIdAsignacionNss(nss.getIdAsignacionNSS());
									tramiteCambio.setIdPersona(integrante.getDerechohabiente().getIdPersona());
									tramiteCambio.setPersona(integrante.getDerechohabiente());
									tramiteCambio.setMedicoEnTurno(correccion.getMedicoEnTurno());
									tramiteCambio.setDomicilio(correccion.getDomicilio());
									tramiteCambio.setIdUmfOrigen(idUmfAsegurado);
									
									//El metodo verificara si es necesario setear a mas de una persona en el tramite
									tramiteCambio = this.setPadresConcubinasEnTramite(tramiteCambio, nss, cabeza.getPatronImss(), nuevoDomicilio, correccion.getMedicoEnTurno(), correccion.getFechaCambioMedico(), integrante);
									
									log.debug("Se procede a hacer el cambio de clinica del derechohabiente");
									this.guardarTramiteCambioClinicaDependiente(tramiteCambio, nss, nss,cabeza.getPatronImss().equals(1), null,
													solicitud.getSolicitudId(),
													integrante, asignacion);
									
								} else {
									
									log.debug("El asegurado no cambio de clinica");
									
									integrante.setDomicilio(nuevoDomicilio);
									integrante.setMedicoEnTurno(correccion.getMedicoEnTurno());
									integrante.setFechaRegistroActualizacion(new Date());
									// ----------------------------------------------------------------------
									// Al crear el tr�mite y actualizar el integrante, se toma la fecha 
									// traida de la base; solamente se valida que no sea null
									// ----------------------------------------------------------------------
									if( correccion.getFechaCambioMedico() != null && !asignacion){
										integrante.setFechaCambioTurnoMedico(correccion.getFechaCambioMedico());
									} else {
										integrante.setFechaCambioTurnoMedico(null);
									}
									
									Long cvePersonaDomicilio = this.guardarDom(integrante.getDerechohabiente(), nuevoDomicilio);
									
									integrante.setCvePersonaDomicilio(cvePersonaDomicilio);
									//Actualizamos al asegurado
									grupoFamiliarDaoLocal.updateIntegrante(integrante);
									//buscaremos a los integrantes que esten en la misma umf para ver si aplicamos cambio de medico
									this.guardarTramiteCambioMedicoDependiente(integrante, solicitud.getSolicitudId(),asignacion);
								}
							} else {
								//Se procede a crear el tramite de asignacion de consultorio medico y turno
								integrante.setDomicilio(correccion.getDomicilio());
								integrante.setMedicoEnTurno(correccion.getMedicoEnTurno());
								
								if(!asignacion) {
									integrante.setFechaCambioTurnoMedico(correccion.getFechaCambioMedico() != null ? correccion.getFechaCambioMedico() : new Date());
								} else {
									integrante.setFechaCambioTurnoMedico(null);
								}
								
								this.guardarTramiteAsignacionUmfDependiente(integrante, solicitud.getSolicitudId(),cabeza.getPatronImss(),false,asignacion);
							}
							
						}
					}
						
					
					
				} catch (Exception e) {
					e.printStackTrace();
					throw new DerechohabientesBusinessException(e.getMessage());
				}
			}
			
			
			// mandamos a llamar al servicio local para que se establezcan el
			// resultado y la razon del resultado
			try {
				String observaciones = "";
				if (StringUtils.isNotBlank(correccion.getObservacion())) {
					observaciones = correccion.getObservacion().length() > 255 ? correccion.getObservacion().substring(0,250) : correccion.getObservacion();
				}
				solicitudServiceLocal.marcarAtendidaSolictud(
						solicitud.getSolicitudId(),
						observaciones, null);
			} catch (DerechohabientesBusinessException e) {
				log.error("Ocurrio un error al actulizar la solicitud");
				throw new SolicitudException();
			}
			
			//guardamos los documentos probatorios del tramite
			try {
				documentoProbatorioServiceBusinessRemote.guardarDocumentosCapturados(solicitud);
			} catch(DocumentoProbatorioException e) {
				log.error("Ocurrio un error al guardar los documentos",e);
			} catch (TramiteNoEncontradoException e) {
				log.error("No se encontro tramite", e);
			}
			
		} else {
			throw new SolicitudNoEncontradaException("");
		}
		
//		try {
//			finalizaSolicitudServiceRemote.finalizarSolicitudTramites(solicitud, "", integrante.getAsignacionNSS());
//		} catch (Exception e) {
//			throw new DerechohabientesBusinessException(e.getMessage());
//		}

		return solicitud;
	}
	
	private Long guardarDom(Fisica fisica, Domicilio nuevoDomicilio) {
		log.debug("Se guardara la relacion persona domicilio");
		//guardamos el domicilio primero ya que se requiere en la circunscipcion
		PersonaDomicilio personaDomicilio = new PersonaDomicilio();
		personaDomicilio.setPersona(fisica);
		personaDomicilio.setTipoDomicilio(new TipoDomicilio());
		personaDomicilio.getTipoDomicilio().setClave(TipoDomicilioEnum.PARTICULAR.getCodigo().intValue());

		try {
			personaDomicilio.setDomicilio(nuevoDomicilio);
			personaDomicilio = grupoFamiliarDaoLocal.savePersonaDomicilio(personaDomicilio);
			log.debug("El id de la relacion es: " + personaDomicilio.getCvePersonaDomicilio());
			return personaDomicilio.getCvePersonaDomicilio();
		} catch (DomicilioNoValidoException e) {
			log.error("No fue posible guardar el nuevo domicilio", e);
		} catch (Exception e) {
			log.error("No fue posible guardar o actualizar la relacion persona domicilio",e);
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private TramiteCorreccionDerechohabiente setPadresConcubinasEnTramite(TramiteCorreccionDerechohabiente tramiteCambio,AsignacionNSS nss, Integer patronIMSS, Domicilio nuevoDomicilio, MedicoEnTurno medicoNuevo, Date FechaCambio, GrupoFamiliar integrante) throws Exception {
		//buscamos padres y concubinas y los seteamos al tramite
		Map<String, ? extends Object> map = this.actualizarPadresConcubinas(nss, patronIMSS, nuevoDomicilio, medicoNuevo, FechaCambio);
		List<Long> ids = new ArrayList<Long>();
		ids.add(integrante.getDerechohabiente().getIdPersona());
		List<Fisica> personas = new ArrayList<Fisica>();
		personas.add(integrante.getDerechohabiente());
		
		if(map != null) {
			List<Long> idsAux = (List<Long>) map.get("ids");
			if(idsAux != null && !idsAux.isEmpty()) {
				ids.addAll(idsAux);
				log.debug("El cambio de clinica se hara para mas de una persona");
				//Creamos la lista de las personas que se cambiaran de clinica
				List<Derechohabiente> integrantes = (List<Derechohabiente>) map.get("integrantes");
				for(Derechohabiente dere : integrantes) {
					personas.add(dere);
				}
				
			}
		}
		
		//Seteamos los ids individuales en null ya que ahora el cambio sera para mas de un integrante
		tramiteCambio.setIdPersona(null);
		tramiteCambio.setPersona(null);
		//ponemos alos padres y consubinas mas el asegurado
		tramiteCambio.setCandidatosCambioClinica(ids);
		tramiteCambio.setPersonas(personas);
		
		return tramiteCambio;
	}

	private Map<String, List<? extends Object>> actualizarPadresConcubinas(AsignacionNSS nss, Integer patImss, 
			Domicilio nuevoDomicilio, MedicoEnTurno nuevoMedico, Date fechaCambioTurno) throws Exception{
		Boolean patronImss = patImss == null ? false : patImss.equals(1);
		
		Map<String, List<? extends Object>> listas = null;
		List<Derechohabiente> integrantes = null;
		List<Long> idIntegrantes = null;
		
		List<Long> parentescos = new ArrayList<Long>();
		parentescos.add(ParentescoEnum.PADRES.getId());
		parentescos.add(ParentescoEnum.CONCUBINARIO.getId());
		
		List<GrupoFamiliar> concubinasPadres = grupoFamiliarDaoLocal.findGrupoFamiliarPorParentescos(nss.getIdAsignacionNSS(), parentescos, true);
		
		if (concubinasPadres != null && !concubinasPadres.isEmpty()) {
			listas = new HashMap<String, List<? extends Object>>();
			idIntegrantes = new ArrayList<Long>();
			integrantes = new ArrayList<Derechohabiente>();
			
			log.debug("Se encontraron " + concubinasPadres.size() + " integrantes padres y o concubinas");
			for (GrupoFamiliar integ : concubinasPadres) {
				EstadoDerechohabiente estado = integ.getEstadoDerechohabiente();
				Parentesco parentescoI = integ.getParentesco();
				
				log.debug("Se verificar si es posible hacer el cambio de clinica para el siguiente integrante:\n" +
						"- idPersona: " + integ.getDerechohabiente().getIdPersona() + "\n" +
						"- parentesco: " + parentescoI.getDescripcion() + "\n" +
						"- estadoDerechohabiente: " + estado.getDescripcion());
				
				if(estado != null && estado.getIdEstadoDerechohabiente().equals(EstadoDerechohabienteEnum.VIGENTE.getId())) {
					
					if((parentescoI.getIdParentesco().equals(ParentescoEnum.PADRES.getId()) && !patronImss)
							|| parentescoI.getIdParentesco().equals(ParentescoEnum.CONCUBINARIO.getId())) {
						idIntegrantes.add(integ.getDerechohabiente().getIdPersona());
						integrantes.add(integ.getDerechohabiente());
					}
				}
			}
			
			listas.put("ids", idIntegrantes);
			listas.put("integrantes", integrantes);
		}
		
		return listas;
	}

	@Override
	public List<Tramite> findTramitesAbiertos(AsignacionNSS nss,
			List<Long> integrantes) throws DerechohabientesBusinessException {

		List<Tramite> tramites = null;
		try {
			tramites = solicitudTramiteBusinessRemote.getTramitesAbierto(
					integrantes, nss.getIdPersona(),Arrays.asList(4l));
		} catch (Exception e) {
			log.error("no fue posible recuperar los tramites abiertos", e);
		}

		return tramites;
	}

	/**
	 * Metodo para obtener a los candidatos para correccion
	 * 
	 * @param nss
	 *            El nss del grupo Familiar
	 * @param perfil
	 *            del usurio actual
	 */
	@Override
	public List<GrupoFamiliar> findGrupoFamiliarCorreccion(AsignacionNSS nss,
			Usuario usuario) throws DerechohabientesBusinessException {
		List<GrupoFamiliar> candidatos = null;
		List<GrupoFamiliar> recienNacidosBaja = null;
		GrupoFamiliar pensionado = null;
		// Obtenemos los miembros del grupo Familiar del perfil seleccionado
		try {
			
			// Obtenemos a los miembros del grupo familiar del asegurado
			candidatos = grupoFamiliarDaoLocal.findGrupoFamiliarByNss(nss.getIdAsignacionNSS());
			// Solo mandamos a los candidatos que tengan la misma umf que el
			// tramitador
			candidatos = this.filtrarPorUmf(candidatos, usuario.getIdUmf());
		} catch (Exception e) {
			log.error("No se encontro informacion", e);
			DerechohabientesBusinessException.throwException(ExceptionMessages.SIN_INFORMACION, e.getCause().getMessage());
		}

		if (candidatos != null) {
			//obtenemos a los integrantes recien nacidos en baja
			recienNacidosBaja = this.getRecienNacidosBaja(candidatos);
			pensionado = this.getPensionado(candidatos);
			
			candidatos = this.quitarFallecidos(candidatos);
			candidatos = this.quitarBajasNoAdministrativas(candidatos);
			
			//si la lista de recien nacidos no esta vacia
			if(!recienNacidosBaja.isEmpty()) {
				//si la lista de candidatos no es nula, anadimos a los recien naciddos
				if(candidatos != null) {
					candidatos.addAll(recienNacidosBaja);
				} else {
					// si los candidatos son nulos , retornamos solo a los recien nacidos
					candidatos = recienNacidosBaja;
				}
			}
			
			if(candidatos != null) {
				if(pensionado != null) {
					List<Long> idsPersonas = new ArrayList<Long>();
					for(GrupoFamiliar inte: candidatos) {
						idsPersonas.add(inte.getDerechohabiente().getIdPersona());
					}
					
					if(!idsPersonas.contains(pensionado.getDerechohabiente().getIdPersona())) {
						candidatos.add(pensionado);
					}
				}
				
			} else {
				if(pensionado != null) {
					candidatos = new ArrayList<GrupoFamiliar>();
					candidatos.add(pensionado);
				}
			}
		}else {
			// Si es nula mandamos una excepcion indicando que no hay informacion
			// para mostrar
			DerechohabientesBusinessException.throwException(ExceptionMessages.SIN_INFORMACION,"No se encontraron candidatos");
		}

		// Si la lista no es nula pero esta vacia despues de los filtros
		// mandamos un exception indicando que no hay informacion para mostrar
		if (candidatos.size() == 0) {
			DerechohabientesBusinessException.throwException(ExceptionMessages.SIN_INFORMACION,"No se encontraron candidatos");
		}
		
		//se quitan duplicados
		Map<Long, GrupoFamiliar> mapCandidatos = new HashMap<Long, GrupoFamiliar>();
		for(GrupoFamiliar candidato : candidatos) {
			mapCandidatos.put(candidato.getDerechohabiente().getIdPersona(), candidato);
		}
		
		candidatos = new ArrayList<GrupoFamiliar>(mapCandidatos.values());

		return candidatos;
	}
	
	private GrupoFamiliar getPensionado(List<GrupoFamiliar> grupoFamiliar) {
		GrupoFamiliar pensionadoF = null;
		
		if(grupoFamiliar != null && !grupoFamiliar.isEmpty()) {
			for(GrupoFamiliar integrante: grupoFamiliar) {
				if(integrante.getParentesco().getIdParentesco().equals(ParentescoEnum.PENSIONADO.getId())){
					pensionadoF = integrante;
				}
			}
		}
		return pensionadoF;
	}

	private List<GrupoFamiliar> getRecienNacidosBaja(List<GrupoFamiliar> grupoFamiliar) {
		List<GrupoFamiliar> recienNacidos = new ArrayList<GrupoFamiliar>();
		
		if(grupoFamiliar != null && !grupoFamiliar.isEmpty()) {
			for(GrupoFamiliar integrante: grupoFamiliar) {
				if(integrante.getEstadoDerechohabiente().getIdEstadoDerechohabiente().equals(EstadoDerechohabienteEnum.BAJA.getId())
						&& (integrante.getIndRecienNacido() != null && integrante.getIndRecienNacido().intValue() == 1)){
					recienNacidos.add(integrante);
				}
			}
		}
		return recienNacidos;
	}
	/**
	 * @author jorge.garciaca Metodo para listar los integrantes activos del
	 *         grupo familiar para el registro de correccion de datos del
	 *         derechohabiente
	 */
	@Override
	public List<GrupoFamiliar> findGrupoFamiliarCorreccionDatos(Long nss,
			Long origenSolicitud, Usuario usuario)
			throws DerechohabientesBusinessException {
		List<GrupoFamiliar> grupoFamiliar = null;
		// List<GrupoFamiliar> grupoHijos = null;
		// List<GrupoFamiliar> grupoConyuges = null;

		try {

			// log.debug(">>> findGrupoFamiliarCorreccionDatos working ... \n\tnss: "
			// +nss + "\n\torigenSolicitud: " + origenSolicitud +
			// "\n\tusuario: " + usuario);

			/*
			 * if (usuario != null && usuario.getPerfilUsuario() != null &&
			 * usuario.getPerfilUsuario().getIdPerfilUsuario() != null){
			 * 
			 * log.debug(">>> Si se cuentan con los datos del usuario");
			 * 
			 * grupoFamiliar = new ArrayList<GrupoFamiliar>(); AsignacionNSS
			 * asignacionNSS = new AsignacionNSS();
			 * asignacionNSS.setIdAsignacionNSS(nss);
			 * 
			 * grupoFamiliar = this.findGrupoFamiliarCorreccion(asignacionNSS,
			 * usuario);
			 * 
			 * } else {
			 */

			// }

			/**
			 * por ahora solo aplican coyuge e hijos para la modificacion de
			 * datos
			 */

			log.debug(">>> Consultando los integrantes del grupo familiar...");

			grupoFamiliar = new ArrayList<GrupoFamiliar>();
			grupoFamiliar.addAll(grupoFamiliarDaoLocal
					.findGrupoFamiliarParentescoEstado(nss,
							ParentescoEnum.HIJOS.getId(),
							EstadoDerechohabienteEnum.VIGENTE.getId()));
			grupoFamiliar.addAll(grupoFamiliarDaoLocal
					.findGrupoFamiliarParentescoEstado(nss,
							ParentescoEnum.CONYUGE.getId(),
							EstadoDerechohabienteEnum.VIGENTE.getId()));

		} catch (Exception e) {
			log.error("Ocurrio un error inesperado", e);
			DerechohabientesBusinessException
					.throwException("Ocurrio un error inesperado: "
							+ e.getCause().getMessage(),
							ExceptionMessages.GRUPO_FAMILIAR_CONSULTA_ERROR);
		}

		if (grupoFamiliar == null) {
			log.error("Error debido a grupoFamiliar: " + grupoFamiliar);
			DerechohabientesBusinessException.throwException(
					"No existe ningun integrante activo en el grupo familiar",
					ExceptionMessages.SIN_INFORMACION);
		}

		if (grupoFamiliar.isEmpty()) {
			log.error("Error debido a grupoFamiliar.isEmpty(): "
					+ grupoFamiliar.isEmpty());
			DerechohabientesBusinessException.throwException(
					"No existe ningun integrante activo en el grupo familiar",
					ExceptionMessages.SIN_INFORMACION);
		}

		return grupoFamiliar;
	}

	@Override
	/**
	 * Metodo para lozalizar a los candidatos a cambio de medico
	 */
	public List<GrupoFamiliar> findGrupoFamiliarCambioMedico(AsignacionNSS nss,
			Usuario usuario) throws DerechohabientesBusinessException {

		List<GrupoFamiliar> salida = new ArrayList<GrupoFamiliar>();

		// buscamos al asegurado ya que es el unico al que se le puede hacer al
		// cambio de medico
		try {
			salida = grupoFamiliarDaoLocal.findGrupoFamiliarByNss(nss
					.getIdAsignacionNSS());

		} catch (Exception e1) {
			log.error("Error al recuperar al integrante afectado", e1);
			DerechohabientesBusinessException.throwException(
					"error.busqueda.integrante", e1.getCause().getMessage());
		}

		// Verificamos que se haya encontrado de lo contrario mandamos una
		// excepcion
		if (salida == null)
			DerechohabientesBusinessException.throwException(
					ExceptionMessages.SIN_INFORMACION,
					"No se encontraron candidatos");

		salida = this.quitarBajas(salida);
		salida = this.getCanditadosCambioMedico(salida);
		salida = this.filtrarPorUmf(salida, usuario.getIdUmf());

		if (salida.isEmpty())
			DerechohabientesBusinessException.throwException(
					ExceptionMessages.SIN_INFORMACION,
					"No se encontraron candidatos");

		return salida;
	}

	@Override
	public List<GrupoFamiliar> findGrupoFamiliarAsignacionMedico(
			AsignacionNSS nss, Usuario usuario)
			throws DerechohabientesBusinessException {
		List<GrupoFamiliar> grupoFamiliar = null;
		GrupoFamiliar asegurado = null;

		try {
			grupoFamiliar = grupoFamiliarDaoLocal
					.findGrupoFamiliarSinMedico(nss.getNssStr());
		} catch (Exception e) {
			log.error("No se encontro informacion", e);
			DerechohabientesBusinessException.throwException(
					ExceptionMessages.SIN_INFORMACION, e.getCause()
							.getMessage());
		}

		// Checamos que la lista sea diferente de nula
		if (grupoFamiliar != null) {
			// Quitamos los candidatos dados de baja de la lista
			grupoFamiliar = this.quitarBajas(grupoFamiliar);
		}
		// Si es nula mandamos una excepcion indicando que no hay informacion
		// para mostrar
		else {
			throw new DerechohabientesBusinessException(
					ExceptionMessages.SIN_INFORMACION,
					"No se encontraron candidatos");
		}

		if (grupoFamiliar.size() != 0) {
			// Quitamos los candidatos dados de baja de la lista
			asegurado = this.getAsegurado(grupoFamiliar);

			if (asegurado != null) {
				List<UnidadMedicaFamiliar> umfs = null;
				try {
					umfs = domicilioServiceBusinessRemote
							.getUmfByCodigoPostal(asegurado.getDomicilio()
									.getCodigoPostal().getCodigoPostal());
					// umfs =
					// umfCodigoPostalDaoLocal.getUmfByCodigoPostal(asegurado.getDomicilio().getCodigoPostal().getCodigoPostal());
				} catch (Exception e) {
					throw new DerechohabientesBusinessException(
							ExceptionMessages.SIN_INFORMACION,
							"No se encontraron umfs disponibles para el codigo postal");
				}

				if (umfs == null || umfs.size() == 0)
					throw new DerechohabientesBusinessException(
							ExceptionMessages.SIN_INFORMACION,
							"No se encontraron umfs disponibles para el codigo postal");

				// Checamos que la umf del tramitador este dentro de la lista de
				// umfs que correponden al codigo postal
				if (!this.umfTramitador(umfs, usuario.getIdUmf()))
					throw new DerechohabientesBusinessException(
							ExceptionMessages.SIN_INFORMACION,
							"No se encontraron umfs disponibles para el codigo postal");

				grupoFamiliar = new ArrayList<GrupoFamiliar>();
				grupoFamiliar.add(asegurado);
			} else {
				throw new DerechohabientesBusinessException(
						ExceptionMessages.SIN_INFORMACION,
						"No se encontraron candidatos");
			}
		}
		// Si es nula mandamos una excepcion indicando que no hay informacion
		// para mostrar
		else {
			throw new DerechohabientesBusinessException(
					ExceptionMessages.SIN_INFORMACION,
					"No se encontraron candidatos");
		}

		return grupoFamiliar;
	}

	

	/**
	 * Metodo para obtener a los candidatos para suspension de circunscripcion
	 * 
	 * @param nss
	 *            El nss del grupo Familiar
	 * @param activa
	 *            - Boolean indicador que representa si la circunscripcion esta
	 *            abierta(true) o cerrada(false)
	 * @return List<GrupoFamiliar> lista con los integrantes
	 * @throws DerechohabientesBusinessException
	 */
	@Override
	public List<GrupoFamiliar> findGrupoFamiliarSuspencionCircunscripcion(
			AsignacionNSS nss, Boolean activa, Usuario usuario)
			throws DerechohabientesBusinessException {
		List<GrupoFamiliar> candidatos = null;
		GrupoFamiliar cabeza = null;
		try {
			// Buscamos al segurado
			cabeza = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(
					nss.getIdAsignacionNSS(), nss.getIdPersona());
			// Verificamos que el usuario este en la misma umf del asegurado de
			// ser asi buscamos a las personas con una circunscrpicion
			// activa sin importar cual fue la umf donde se inicio la
			// circunscripcion foranea
			if (cabeza.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF()
					.equals(usuario.getIdUmf())) {
				// Buscamos a los candidatos integrantes del grupo familiar del
				// asegurado
				candidatos = grupoFamiliarDaoLocal
						.findGrupoFamiliarCircunscripcion(nss,
								EstadoDerechohabienteEnum.VIGENTE, false,
								activa);
			} else {
				// Buscamos a los candidatos integrantes del grupo familiar del
				// asegurado
				candidatos = grupoFamiliarDaoLocal
						.findGrupoFamiliarCircunscripcion(nss,
								EstadoDerechohabienteEnum.VIGENTE, false,
								activa);
				List<GrupoFamiliar> candidatosEnUmf = new ArrayList<GrupoFamiliar>();
				for (GrupoFamiliar candidato : candidatos) {
					if (candidato.getMedicoEnTurno().getUnidadMedicaFamiliar()
							.getIdUMF().equals(usuario.getIdUmf()))
						candidatosEnUmf.add(candidato);
				}

				candidatos = candidatosEnUmf;
			}
			// quitamos a los candidatos que no pertenecen a la misma umf que el
			// tramitador
			// candidatos = this.filtrarPorUmf(candidatos, usuario.getIdUmf());
		} catch (Exception e) {
			log.error("No se pudieron recuperar candidatos", e);
			throw new DerechohabientesBusinessException(
					ExceptionMessages.SIN_INFORMACION, e.getCause()
							.getMessage());
		}

		if (candidatos != null) {
			// Quitamos los candidatos dados de baja de la lista
			candidatos = this.quitarAsegurado(candidatos);
		}
		// Si es nula mandamos una excepcion indicando que no hay informacion
		// para mostrar
		else {
			throw new DerechohabientesBusinessException(
					ExceptionMessages.SIN_INFORMACION,
					"No se encontraron candidatos");
		}

		// Si la lista no es nula pero esta vacia despues de los filtros
		// mandamos un exception indicando que no hay informacion para mostrar
		if (candidatos.isEmpty()) {
			throw new DerechohabientesBusinessException(
					ExceptionMessages.SIN_INFORMACION,
					"No se encontraron candidatos");
		}

		return candidatos;
	}

	@Override
	public List<GrupoFamiliar> findGrupoFamiliarActualizacionDomicilio(
			Long nss, Long origenSolicitud, Usuario usuario)
			throws DerechohabientesBusinessException {
		List<GrupoFamiliar> grupoFamiliar = null;

		try {
			log.debug(">>> Consultando los integrantes del grupo familiar...");

			grupoFamiliar = new ArrayList<GrupoFamiliar>();
			grupoFamiliar.addAll(grupoFamiliarDaoLocal
					.findGrupoFamiliarParentescoEstado(nss,
							ParentescoEnum.HIJOS.getId(),
							EstadoDerechohabienteEnum.VIGENTE.getId()));
			grupoFamiliar.addAll(grupoFamiliarDaoLocal.findGrupoFamiliarParentescoEstado(nss,
							ParentescoEnum.CONYUGE.getId(),
							EstadoDerechohabienteEnum.VIGENTE.getId()));
			grupoFamiliar.addAll(grupoFamiliarDaoLocal.findGrupoFamiliarParentescoEstado(nss,
							ParentescoEnum.ASEGURADO.getId(),
							EstadoDerechohabienteEnum.VIGENTE.getId()));

		} catch (Exception e) {
			log.error("Ocurrio un error inesperado", e);
			DerechohabientesBusinessException
					.throwException("Ocurrio un error inesperado: "
							+ e.getCause().getMessage(),
							ExceptionMessages.GRUPO_FAMILIAR_CONSULTA_ERROR);
		}

		if (grupoFamiliar == null) {
			log.error("Error debido a grupoFamiliar: " + grupoFamiliar);
			DerechohabientesBusinessException.throwException(
					"No existe ningun integrante activo en el grupo familiar",
					ExceptionMessages.SIN_INFORMACION);
		}

		if (grupoFamiliar.isEmpty()) {
			log.error("Error debido a grupoFamiliar.isEmpty(): "
					+ grupoFamiliar.isEmpty());
			DerechohabientesBusinessException.throwException(
					"No existe ningun integrante activo en el grupo familiar",
					ExceptionMessages.SIN_INFORMACION);
		}

		return grupoFamiliar;
	}

	@Override
	public List<GrupoFamiliar> findGrupoFamiliarCambioClinica(Long nss,
			Long origenSolicitud, Usuario usuario)
			throws DerechohabientesBusinessException {
		List<GrupoFamiliar> grupoFamiliar = null;
		try {
			log.debug(">>> Consultando los integrantes del grupo familiar...");

			grupoFamiliar = new ArrayList<GrupoFamiliar>();
			grupoFamiliar.addAll(grupoFamiliarDaoLocal
					.findGrupoFamiliarParentescoEstado(nss,
							ParentescoEnum.HIJOS.getId(),
							EstadoDerechohabienteEnum.VIGENTE.getId()));
			grupoFamiliar.addAll(grupoFamiliarDaoLocal
					.findGrupoFamiliarParentescoEstado(nss,
							ParentescoEnum.CONYUGE.getId(),
							EstadoDerechohabienteEnum.VIGENTE.getId()));

		} catch (Exception e) {
			log.error("Ocurrio un error inesperado", e);
			DerechohabientesBusinessException
					.throwException("Ocurrio un error inesperado: "
							+ e.getCause().getMessage(),
							ExceptionMessages.GRUPO_FAMILIAR_CONSULTA_ERROR);
		}

		if (grupoFamiliar == null) {
			log.error("Error debido a grupoFamiliar: " + grupoFamiliar);
			DerechohabientesBusinessException.throwException(
					"No existe ningun integrante activo en el grupo familiar",
					ExceptionMessages.SIN_INFORMACION);
		}

		if (grupoFamiliar.isEmpty()) {
			log.error("Error debido a grupoFamiliar.isEmpty(): "
					+ grupoFamiliar.isEmpty());
			DerechohabientesBusinessException.throwException(
					"No existe ningun integrante activo en el grupo familiar",
					ExceptionMessages.SIN_INFORMACION);
		}

		return grupoFamiliar;
	}

	/**
	 * Metodo para guardar el tramite de correccion de derechohabiente
	 * recibiendo los siguientes parametros:
	 * 
	 * @param idDerechohabiente
	 *            Long que indica el id del derechohabiente a modificar
	 * @param usuario
	 *            Usuario el usuario que solicita la corrrecion
	 * @param nss
	 *            AsignacionNSS el objeto que contiene los datos del asegurado
	 * @param correccion
	 *            CorreccionDatoDerechohabiente el objeto que trae toda la
	 *            informacion que sera corregida
	 * @return solicitud Solicitud la solicitud creada
	 * @throws DerechohabientesBusinessException
	 */
	@Override
	public Solicitud saveCorreccionDatosDerechohabiente(Long idDerechohabiente, GrupoFamiliar integrante, 
			Usuario usuario, AsignacionNSS nss,
			TramiteCorreccionDerechohabiente correccion,
			OrigenSolicitudEnum origen, Boolean esAsignacionDomicilio)
			throws DerechohabientesBusinessException {

		//Cechamos si no viene el integrante o si el id de la persona es diferente se consulta
		if(integrante == null || !integrante.getDerechohabiente().getIdPersona().equals(idDerechohabiente)) {
			try {
				integrante = grupoFamiliarDaoLocal.getIntegranteSinVigencia(nss.getIdAsignacionNSS(), idDerechohabiente);
			} catch (Exception e1) {
				log.error("Error al recuperar al integrante afectado", e1);
				DerechohabientesBusinessException.throwException("error.busqueda.integrante", e1.getCause().getMessage());
			}
		}

		if (integrante == null) {
			DerechohabientesBusinessException.throwException("No se encontro al integrante del grupo familiar","No se encontro al integrante del grupo familiar");
		}
		
		// Creamos nuestro objeto solicitud que obtendra el resultado de la
		// operacion
		Solicitud solicitud = null;
		correccion = this.cambiarAMayusculas(correccion);
		try {
			// Si esAsignacionDomicilio != null $$ true el tipod e tramite debe
			// ser ASIGNACION_DE_DOMICILIO_PARTICULAR_DH
			if (esAsignacionDomicilio != null && esAsignacionDomicilio) {
				solicitud = tramiteServiceLocal.guardarCorreccion(integrante,TipoTramiteEnum.ASIGNACION_DE_DOMICILIO_PARTICULAR_DH,usuario, nss, correccion, origen);
			} else {
				solicitud = tramiteServiceLocal.guardarCorreccion(integrante,TipoTramiteEnum.MODIFICACION_DE_DERECHOHABIENTE,usuario, nss, correccion, origen);
			}

		} catch (Exception e) {
			// Si ocurrio algun error mandamos una excepcion indicando que no se
			// pudo guardar la solicitud
			log.error("No fue posible guardar la solicitud", e);
			throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_GUARDADO_SOLCITUD, e.getCause().getMessage());
		}

		// retornamos la solicitud
		return solicitud;
	}

	/**
	 * Metodo para guardar el cambio de consultorio o turno deun derechohabiente
	 * recibiendo lossiguientes parametros
	 * 
	 * @param idDerechohabiente
	 *            Long que indica el id del derechohabiente a modificar
	 * @param usuario
	 *            Usuario el usuario que solicita la corrrecion
	 * @param nss
	 *            AsignacionNSS el objeto que contiene los datos del asegurado
	 * @param correccion
	 *            CorreccionDatoDerechohabiente el objeto que trae toda la
	 *            informacion que sera corregida
	 * @return solicitud Solicitud la solicitud creada
	 * @throws DerechohabientesBusinessException
	 */
	@Override
	public Solicitud saveCorreccionMedicoDerechohabiente(
			Long idDerechohabiente, Usuario usuario, AsignacionNSS nss,
			TramiteCorreccionDerechohabiente correccion,
			OrigenSolicitudEnum origen)
			throws DerechohabientesBusinessException {

		// Obtenemos al integrante del grupo familiar
		GrupoFamiliar integrante = null;

		try {
			integrante = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(nss.getIdAsignacionNSS(), idDerechohabiente);
		} catch (Exception e1) {
			log.error("Error al recuperar al integrante afectado", e1);
			DerechohabientesBusinessException.throwException("error.busqueda.integrante", e1.getCause().getMessage());
		}

		if (integrante == null){
			DerechohabientesBusinessException.throwException("error.busqueda.integrante","No se encontro al integrante del grupo familiar");
		}
		
		// Creamos nuestro objeto solicitud que obtendra el resultado de la
		// operacion
		Solicitud solicitud = null;
		solicitud = this.guardarSolicitudCorreccion(integrante, usuario, nss,correccion, TipoTramiteEnum.CAMBIO_CONSULTORIO_TURNO, origen);

		// retornamos la solicitud
		return solicitud;
	}

	/**
	 * Metodo para guardar el cambio de clinica de un derechohabiente recibiendo
	 * los siguientes parametros
	 * 
	 * @param idDerechohabiente
	 *            Long que indica el id del derechohabiente a modificar
	 * @param usuario
	 *            Usuario el usuario que solicita la corrrecion
	 * @param nss
	 *            AsignacionNSS el objeto que contiene los datos del asegurado
	 * @param correccion
	 *            CorreccionDatoDerechohabiente el objeto que trae toda la
	 *            informacion que sera corregida
	 * @return solicitud Solicitud la solicitud creada
	 * @throws DerechohabientesBusinessException
	 */
	@Override
	public Solicitud saveCorreccionUmfDerechohabiente(Long idDerechohabiente,
			Usuario usuario, AsignacionNSS nss,
			TramiteCorreccionDerechohabiente correccion,
			OrigenSolicitudEnum origen)
			throws DerechohabientesBusinessException {

		// Obtenemos al integrante del grupo familiar
		GrupoFamiliar integrante = null;

		if (idDerechohabiente.equals(nss.getIdPersona())) {
			correccion.setCandidatosCambioClinica(new ArrayList<Long>());
			correccion.getCandidatosCambioClinica().add(idDerechohabiente);
			return this.saveCorreccionUmfDerechohabiente(correccion, usuario,
					nss, false, origen);
		}

		try {
			integrante = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(
					nss.getIdAsignacionNSS(), idDerechohabiente);
		} catch (Exception e1) {
			log.error("Error al recuperar al integrante afectado", e1);
			DerechohabientesBusinessException.throwException(
					"error.busqueda.integrante", e1.getCause().getMessage());
		}

		if (integrante == null)
			DerechohabientesBusinessException.throwException(
					"error.busqueda.integrante",
					"No se encontro al integrante del grupo familiar");

		// Creamos nuestro objeto solicitud que obtendra el resultado de la
		// operacion
		Solicitud solicitud = null;
		// Guardamos la solicitud de correccion
		solicitud = this.guardarSolicitudCorreccion(integrante, usuario, nss,
				correccion, TipoTramiteEnum.CAMBIO_CLINICA, origen);

		// retornamos la solicitud
		return solicitud;
	}

	/**
	 * Metodo para guardar el cambio de clinica de un derechohabiente recibiendo
	 * los siguientes parametros
	 * 
	 * @param idDerechohabiente
	 *            Long que indica el id del derechohabiente a modificar
	 * @param usuario
	 *            Usuario el usuario que solicita la corrrecion
	 * @param nss
	 *            AsignacionNSS el objeto que contiene los datos del asegurado
	 * @param correccion
	 *            CorreccionDatoDerechohabiente el objeto que trae toda la
	 *            informacion que sera corregida
	 * @return solicitud Solicitud la solicitud creada
	 * @throws DerechohabientesBusinessException
	 */
	@Override
	public Solicitud saveCorreccionUmfDerechohabiente(
			TramiteCorreccionDerechohabiente correccion, Usuario usuario,
			AsignacionNSS nss, Boolean patronImss, OrigenSolicitudEnum origen)
			throws DerechohabientesBusinessException {

		// Obtenemos al integrante del grupo familiar
		List<GrupoFamiliar> integrantes = null;
		List<GrupoFamiliar> candidatos = null;
		boolean asegurado = false;

		// buscamos si el asegurado esta seleccionado
		for (Long idPersona : correccion.getCandidatosCambioClinica()) {
			if (idPersona.equals(nss.getIdPersona())) {
				asegurado = true;
				break;
			}
		}

		// Obtenemos a los miembros del grupo familiar del asegurado
		if (asegurado) {
			try {
				// candidatos =
				// grupoFamiliarDaoLocal.findGrupoFamiliarPorEstado(nss.getIdAsignacionNSS(),
				// EstadoDerechohabienteEnum.BAJA.getId());
				candidatos = grupoFamiliarDaoLocal.findGrupoFamiliarByNss(nss
						.getIdAsignacionNSS());
				// quitamos a los beneficiarios dados de baja
				candidatos = this.quitarBajas(candidatos);
				// dejamos solo a los padres y concubina(rio)
				candidatos = this.candidatosCorreccionAsegurado(candidatos,
						patronImss);
			} catch (Exception e) {
				e.printStackTrace();
			}

			for (GrupoFamiliar integrante : candidatos) {
				correccion.getCandidatosCambioClinica().add(
						integrante.getDerechohabiente().getIdPersona());
			}
		}

		try {
			integrantes = new ArrayList<GrupoFamiliar>();
			for (Long idPersona : correccion.getCandidatosCambioClinica()) {
				GrupoFamiliar uno = null;
				uno = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(
						nss.getIdAsignacionNSS(), idPersona);
				integrantes.add(uno);
			}
		} catch (Exception e1) {
			log.error("Error al recuperar al integrante afectado", e1);
			DerechohabientesBusinessException.throwException(
					"error.busqueda.integrante", e1.getCause().getMessage());
		}

		if (integrantes == null)
			DerechohabientesBusinessException.throwException(
					"error.busqueda.integrante",
					"No se encontro al integrante del grupo familiar");

		if (!integrantes.isEmpty()) {

			correccion.setPersonas(new ArrayList<Fisica>());

			for (GrupoFamiliar integrante : integrantes) {
				correccion.getPersonas().add(integrante.getDerechohabiente());
			}
		}

		// Creamos nuestro objeto solicitud que obtendra el resultado de la
		// operacion
		Solicitud solicitud = null;
		// Guardamos la solicitud de correccion
		solicitud = this.guardarSolicitudCorreccion(integrantes, usuario, nss,
				correccion, TipoTramiteEnum.CAMBIO_CLINICA, origen);

		// retornamos la solicitud
		return solicitud;
	}

	/**
	 * Metodo para guardar la autorizacion para recibir los servicios en
	 * circunscripcion foranea de un derechohabiente recibiendo los siguientes
	 * parametros
	 * 
	 * @param idDerechohabiente
	 *            Long que indica el id del derechohabiente a modificar
	 * @param usuario
	 *            Usuario el usuario que solicita la corrrecion
	 * @param nss
	 *            AsignacionNSS el objeto que contiene los datos del asegurado
	 * @param correccion
	 *            CorreccionDatoDerechohabiente el objeto que trae toda la
	 *            informacion que sera corregida
	 * @return solicitud Solicitud la solicitud creada
	 * @throws DerechohabientesBusinessException
	 */
	@Override
	public Solicitud saveCircunscripcionAutorizacionDerechohabiente(
			Long idDerechohabiente, Usuario usuario, AsignacionNSS nss,
			TramiteCorreccionDerechohabiente correccion,
			OrigenSolicitudEnum origen)
			throws DerechohabientesBusinessException {

		// Obtenemos al integrante del grupo familiar
		GrupoFamiliar integrante = null;
		try {
			integrante = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(
					nss.getIdAsignacionNSS(), idDerechohabiente);
		} catch (Exception e1) {
			log.error("Error al recuperar al integrante afectado", e1);
			DerechohabientesBusinessException.throwException(
					"error.busqueda.integrante", e1.getCause().getMessage());
		}

		if (integrante == null)
			DerechohabientesBusinessException.throwException(
					"exception.RNGD0003",
					"No se encontro al integrante del grupo familiar");

		// parseamos lo necesario a la entidad de circunscripcion
		TramiteCircunscripcionForanea circunscripcion = new TramiteCircunscripcionForanea();
		// El domicilio de origen sera el que se tiene guardado en grupo
		// familiar
		circunscripcion.setDomicilioOrigen(integrante.getDomicilio());
		// establecemos el medico en turno origen que es el que tienen el
		// derechohabiente
		circunscripcion.setMedicoEnTurnoOrigen(integrante.getMedicoEnTurno());
		// Colocamos el medico en turno que sea el que trae la correccion
		circunscripcion.setMedicoEnTurnoDestino(medicoEnTurnoDAO.getMedicoEnTurnoById(correccion.getMedicoEnTurno().getIdMedicoContultorioTurno()));
		// colocamos el id de la persona
		circunscripcion.setPersona(new Fisica());
		circunscripcion.getPersona().setIdPersona(correccion.getIdPersona());
		// colocmos el tipo del tramite
		circunscripcion.setTipoTramite(new TipoTramite());
		circunscripcion.getTipoTramite().setIdTipoTramite(
				TipoTramiteEnum.AUTORIZACION_SERVICIOS_CIRCUNSCRIPCION_FORANEA
						.getCodigo());
		circunscripcion.setObservacion(correccion.getObservacion());
		
		
		
		// guardamos en base de datos el nuevo domicilio y lo asignamos como
		// domicilio destino
		Domicilio domicilioDestino = this.guardarDomicilioNuevo(correccion.getDomicilio());
		circunscripcion.setDomicilioDestino(domicilioDestino);
		
		// y guardamos la circunscripcion establecemos la fecha de incicio
		circunscripcion.setFecInicioCircunscripcion(new Date());
		// establecemos el indicador de la circunscripcion
		circunscripcion.setIndCircunscripcionActiva(0);

		// Creamos nuestro objeto solicitud que obtendra el resultado de la operacion
		Solicitud solicitud = null;
		try {
			solicitud = tramiteServiceLocal
					.guardarCircunscripcion(
							integrante,
							TipoTramiteEnum.AUTORIZACION_SERVICIOS_CIRCUNSCRIPCION_FORANEA,
							usuario, nss, circunscripcion, origen);
			Tramite tramite = this.getTramiteCircunscripcion(solicitud);
			circunscripcion.setTramiteId(tramite.getTramiteId());
		} catch (Exception e) {
			// Si ocurrio algun error mandamos una excepcion indicando que no se
			// pudo guardar la solicitud
			log.error("Error al guardar la solicitud", e);
			throw new DerechohabientesBusinessException(
					ExceptionMessages.ERROR_GUARDADO_SOLCITUD, e.getCause()
							.getMessage());
		}

		try {
			circunscripcionEntityLocal.saveCircunscripcionForanea(circunscripcion);
			
			
			// ------------------------------------------------------------------------------
			// FIRMA ELECTRONICA
			//
			// Se necesita para firmar los documentos resultantes
			// Si no existe la firma la crea; necesita el id de solicitud
			// ------------------------------------------------------------------------------
				log.debug(" ========== CIRCUNSCRIPCION AUTORIZACION FIRMA =====================");
			
				// ----------------------------------------------------------------------------
				// La firma necesita una descripci�n de tr�mite
				// ----------------------------------------------------------------------------
				TipoTramite tipoTramiteModel = catalogosDAO.getTipoTramite(circunscripcion.getTipoTramite().getIdTipoTramite().longValue());
				tramiteDocumentoService.generaFirmaElectronica(integrante.getAsignacionNSS(), solicitud, tipoTramiteModel.getDescripcion());
		
		} catch (DocumentoException e) {
			
			// -------------------------------------------------
			// Lanzada al generar la firma electr�nica
			// -------------------------------------------------
			e.printStackTrace();
			DerechohabientesBusinessException dbe = new DerechohabientesBusinessException(ExceptionMessages.FIRMA_ELECTRONICA.ERROR.getMensaje() ,e.getSituacion());
			dbe.setCodigo(ExceptionMessages.FIRMA_ELECTRONICA.ERROR.getCodigo());
			
			//throw dbe;
			
		} catch (Exception e) {
			// Si ocurrio algun error mandamos una excepcion indicando que no se
			// pudo guardar la solicitud
			log.error("Error al guardar el tramite", e);
			throw new DerechohabientesBusinessException(
					ExceptionMessages.ERROR_GUARDADO_SOLCITUD, e.getCause()
							.getMessage());
		}

		return solicitud;
	}

	@Override
	public TramiteCircunscripcionForanea saveValidacionAutorizacionDerechohabiente(
			Long idSolicitud, Usuario usuario, AsignacionNSS nss)
			throws DerechohabientesBusinessException {

		TramiteCircunscripcionForanea circunscripcion = guardarValCircunscripcionInterno(
				idSolicitud, usuario, nss, null, null);
		// marcamos como atendida la solicitud
		String observaciones = null;
		if (StringUtils.isNotBlank(circunscripcion.getObservacion())) {
			observaciones = circunscripcion.getObservacion().length() > 255 ? circunscripcion.getObservacion().substring(0,250) : circunscripcion.getObservacion();
		}
		solicitudServiceLocal.marcarAtendidaSolictud(idSolicitud,
				observaciones, usuario.getFisica());
		// retornamos la solicitud

		return circunscripcion;
	}

	@Override
	public TramiteCircunscripcionForanea saveValidacionSuspensionDerechohabiente(
			TramiteCircunscripcionForanea suspension, Usuario usuario,
			AsignacionNSS nss) throws DerechohabientesBusinessException {

		// Obtenemos el tramite de circunscripcion
		TramiteCircunscripcionForanea circunscripcion = null;
		GrupoFamiliar cabeza = null;
		try {
			circunscripcion = circunscripcionEntityLocal
					.getCircunscripcionForanea(suspension.getTramiteId());
			cabeza = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(
					nss.getIdAsignacionNSS(), nss.getIdPersona());
		} catch (Exception e1) {
			log.error("Error al recuperar el tramite de circunscipcion", e1);
			DerechohabientesBusinessException.throwException(
					"error.busqueda.tramite", e1.getCause().getMessage());
		}

		if (circunscripcion == null)
			DerechohabientesBusinessException.throwException(
					"error.busqueda.tramite",
					"No se encontro el tramite de circunscripcion");

		GrupoFamiliar integrante = null;
		try {
			integrante = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(nss
					.getIdAsignacionNSS(), circunscripcion.getPersona()
					.getIdPersona());
		} catch (Exception e1) {
			log.error("Error al recuperar al integrante afectado", e1);
			DerechohabientesBusinessException.throwException(
					"error.busqueda.integrante", e1.getCause().getMessage());
		}

		if (integrante == null)
			DerechohabientesBusinessException.throwException(
					"exception.RNGD0003",
					"No se encontro al integrante del grupo familiar");

		// Creamos nuestro objeto solicitud que obtendra el resultado de la
		// operacion
		Solicitud solicitudSuspencion = null;

		// Guardamos la solicitud de suspension de servicios en circunscripcion
		// foranea
		try {
			solicitudSuspencion = solicitudBusinessRemote
					.consultarPorIdTramite(circunscripcion
							.getTramiteSuspension().getTramiteId());
			// solicitudDaoLocal.getSolicitudById(circunscripcion.getTramiteSuspension().getSolicitud().getSolicitudId);
		} catch (Exception e1) {
			log.error("No pudo localizar la solicitud", e1);
			throw new DerechohabientesBusinessException(
					"error.busqueda.solicitud", e1.getCause().getMessage());
		}

		if (solicitudSuspencion == null)
			throw new DerechohabientesBusinessException(
					"error.busqueda.solicitud", "No se encontr� la solicitud");

		// Obtenemos el domicilio anterior para volver a colocarselo al
		// derechohabiente
		integrante.setDomicilio(cabeza.getDomicilio());
		// Colocamos la fecha de actualizacion del derechohabiente
		integrante.setFechaRegistroActualizacion(new Date());
		integrante.setMedicoEnTurno(cabeza.getMedicoEnTurno());
		integrante
				.setFechaCambioTurnoMedico(cabeza.getFechaCambioTurnoMedico());
		// Actulizamos en base de datos la informacion
		try {
			grupoFamiliarDaoLocal.updateIntegrante(integrante);
		} catch (Exception e) {
			// Si ocurrio algun error mandamos una excepcion indicando que no se
			// pudo guardar la solicitud
			log.error("No pudo actualizar el integrante", e);
			throw new DerechohabientesBusinessException(
					"error.actualizar.integrante", e.getCause().getMessage());
		}

		// Establecemos las fechas de fin de cirunscripcion
		circunscripcion.setFecFinCircunscripcion(new Date());
		// Y el indicador lo ponemos en 0 indicando que la autorizacion se acabo
		circunscripcion.setIndCircunscripcionActiva(0);
		// Le agregamo el tramite de suspension
		circunscripcion.setTramiteSuspension(new Tramite());
		// le colocamos el id del tramite de supension
		circunscripcion.getTramiteSuspension().setTramiteId(
				suspension.getTramiteSuspension().getTramiteId());
		// actualiza la suspension de la circunscripcion
		try {
			circunscripcionEntityLocal
					.updateCircunscripcionForanea(circunscripcion);
		} catch (Exception e1) {
			log.error("Error al actualizar tramite", e1);
			DerechohabientesBusinessException.throwException(
					"error.actualizar.tramite", e1.getCause().getMessage());
		}

		// marcamos como atendida l solicitud de suspension
		solicitudServiceLocal.marcarAtendidaSolictud(
				solicitudSuspencion.getSolicitudId(),
				circunscripcion.getObservacion(), usuario.getFisica());

		// retornamos la solicitud
		return circunscripcion;

	}

	
	/**
	 * Metodo para guardar la suspension para recibir los servicios en
	 * circunscripcion foranea de un derechohabiente recibiendo los siguientes
	 * parametros
	 * 
	 * @param idCircunscripcion
	 *            El id de la circunscripcion a suspender
	 * @param usuario
	 *            Usuario el usuario que solicita la corrrecion
	 * @param nss
	 *            AsignacionNSS el objeto que contiene los datos del asegurado
	 * @param correccion
	 *            CorreccionDatoDerechohabiente el objeto que trae toda la
	 *            informacion que sera corregida
	 * @return CircunscripcionForanea
	 * @throws DerechohabientesBusinessException
	 */
	@Override
	public Solicitud saveCircunscripcionSuspensionDerechohabiente(
			Long idCircunscripcion, String observaciones, Usuario usuario,
			AsignacionNSS nss) throws DerechohabientesBusinessException {
		
		// Obtenemos el tramite de circunscripcion
		TramiteCircunscripcionForanea circunscripcion = null;
		try {
			circunscripcion = circunscripcionEntityLocal.getCircunscripcionForanea(idCircunscripcion);
		} catch (Exception e1) {
			log.error("Error al recuperar el tramite de circunscipcion", e1);
			DerechohabientesBusinessException.throwException(
					"error.busqueda.tramite", e1.getCause().getMessage());
		}

		if (circunscripcion == null)
			DerechohabientesBusinessException.throwException(
					"error.busqueda.tramite",
					"No se encontro el tramite de circunscripcion");

		GrupoFamiliar integrante = null;
		try {
			integrante = grupoFamiliarDaoLocal.getIntegranteSinVigencia(nss
					.getIdAsignacionNSS(), circunscripcion.getPersona()
					.getIdPersona());
		} catch (Exception e1) {
			log.error("Error al recuperar al integrante afectado", e1);
			DerechohabientesBusinessException.throwException(
					"error.busqueda.integrante", e1.getCause().getMessage());
		}

		if (integrante == null)
			DerechohabientesBusinessException.throwException(
					"exception.RNGD0003",
					"No se encontro al integrante del grupo familiar");

		// Creamos nuestro objeto solicitud que obtendra el resultado de la
		// operacion
		Solicitud solicitudSuspencion = null;
		try {
			circunscripcion.setTramiteId(null);
			// Guardamos la solicitud de suspension de servicios en
			// circunscripcion foranea
			solicitudSuspencion = solicitudServiceLocal
					.guardarSolicitud(
							integrante,
							mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum.SUSPENSION_SERVICIOS_CIRCUNSCRIPCION_FORANEA,
							usuario, nss,
							TipoSolicitudEnum.CORRECCION_DATOS_DERECHOHABIENTE,
							observaciones,
							circunscripcion.getDomicilioOrigen(),
							circunscripcion);

			// circunscripcion.setTramiteId(idCircunscripcion);
			long idTramiteSuspension = solicitudSuspencion.getTramites().get(0)
					.getTramiteId();
			circunscripcion.setTramiteId(idCircunscripcion);
			circunscripcion.setTramiteSuspension(new Tramite());
			circunscripcion.getTramiteSuspension().setTramiteId(
					idTramiteSuspension);
		} catch (Exception e) {
			// Si ocurrio algun error mandamos una excepcion indicando que no se
			// pudo guardar la solicitud
			log.error("Error al guardar la solicitud", e);
			throw new DerechohabientesBusinessException(
					ExceptionMessages.ERROR_GUARDADO_SOLCITUD, e.getCause()
							.getMessage());
		}

		try {
			circunscripcionEntityLocal
					.updateCircunscripcionForanea(circunscripcion);
		} catch (Exception e1) {
			log.error("Error al actualizar tramite", e1);
			DerechohabientesBusinessException.throwException(
					"error.actualizar.tramite", e1.getCause().getMessage());
		}

		
		// ------------------------------------------------------------------------------
		// FIRMA ELECTRONICA
		//
		// Se necesita para firmar los documentos resultantes
		// Si no existe la firma la crea; necesita el id de solicitud
		// ------------------------------------------------------------------------------
		try{
			log.debug(" ========== CIRCUNSCRIPCION AUTORIZACION FIRMA =====================");
		
			// ----------------------------------------------------------------------------
			// La firma necesita una descripci�n de tr�mite
			// ----------------------------------------------------------------------------
			TipoTramite tipoTramiteModel = catalogosDAO.getTipoTramite(circunscripcion.getTipoTramite().getIdTipoTramite().longValue());
			tramiteDocumentoService.generaFirmaElectronica(integrante.getAsignacionNSS(), solicitudSuspencion, tipoTramiteModel.getDescripcion());
	
		} catch (DocumentoException e) {
			
			// -------------------------------------------------
			// Lanzada al generar la firma electr�nica
			// -------------------------------------------------
			e.printStackTrace();
			DerechohabientesBusinessException dbe = new DerechohabientesBusinessException(ExceptionMessages.FIRMA_ELECTRONICA.ERROR.getMensaje() ,e.getSituacion());
			dbe.setCodigo(ExceptionMessages.FIRMA_ELECTRONICA.ERROR.getCodigo());
			
			//throw dbe;
			
		} 
		
		
		
		solicitudSuspencion.setTramites(new ArrayList<Tramite>());
		solicitudSuspencion.getTramites().add(circunscripcion);

		return solicitudSuspencion;
	}

	@Override
	public TramiteCircunscripcionForanea saveValidarCircunscripcionSuspension(
			Long idCircunscripcion, Usuario usuario, AsignacionNSS nss)
			throws DerechohabientesBusinessException {
		// Obtenemos el tramite de circunscripcion
		TramiteCircunscripcionForanea circunscripcion = null;
		try {
			circunscripcion = circunscripcionEntityLocal
					.getCircunscripcionForanea(idCircunscripcion);
		} catch (Exception e1) {
			log.error("Error al recuperar el tramite de circunscipcion", e1);
			DerechohabientesBusinessException.throwException(
					"error.busqueda.tramite", e1.getCause().getMessage());
		}

		if (circunscripcion == null)
			DerechohabientesBusinessException.throwException(
					"error.busqueda.tramite",
					"No se encontro el tramite de circunscripcion");

		GrupoFamiliar integrante = null;
		try {
			integrante = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(nss
					.getIdAsignacionNSS(), circunscripcion.getPersona()
					.getIdPersona());
		} catch (Exception e1) {
			log.error("Error al recuperar al integrante afectado", e1);
			DerechohabientesBusinessException.throwException(
					"error.busqueda.integrante", e1.getCause().getMessage());
		}

		if (integrante == null)
			DerechohabientesBusinessException.throwException(
					"exception.RNGD0003",
					"No se encontro al integrante del grupo familiar");

		// Creamos nuestro objeto solicitud que obtendra el resultado de la
		// operacion
		Solicitud solicitudSuspencion = null;

		// Guardamos la solicitud de suspension de servicios en circunscripcion
		// foranea
		try {
			solicitudSuspencion = solicitudServiceLocal
					.guardarSolicitud(
							integrante,
							mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum.SUSPENSION_SERVICIOS_CIRCUNSCRIPCION_FORANEA,
							usuario, nss,
							TipoSolicitudEnum.CORRECCION_DATOS_DERECHOHABIENTE,
							null, circunscripcion.getDomicilioOrigen(), null);
		} catch (Exception e1) {
			log.error("No pudo actualizar el integrante", e1);
			throw new DerechohabientesBusinessException(
					"error.busqueda.solicitud", e1.getCause().getMessage());
		}

		if (solicitudSuspencion == null)
			throw new DerechohabientesBusinessException(
					"error.busqueda.solicitud", "No se encontr� la solicitud");

		// Obtenemos el domicilio anterior para volver a colocarselo al
		// derechohabiente
		integrante.setDomicilio(circunscripcion.getDomicilioOrigen());
		// Colocamos la fecha de actualizacion del derechohabiente
		integrante.setFechaRegistroActualizacion(new Date());
		// Le ponemos el nuevo medico en turno
		integrante.setMedicoEnTurno(circunscripcion.getMedicoEnTurnoOrigen());
		// Actulizamos en base de datos la informacion
		try {
			grupoFamiliarDaoLocal.updateIntegrante(integrante);
		} catch (Exception e) {
			// Si ocurrio algun error mandamos una excepcion indicando que no se
			// pudo guardar la solicitud
			log.error("No se pudo actualizar el integrante", e);
			throw new DerechohabientesBusinessException(
					"error.actualizar.integrante", e.getCause().getMessage());
		}

		// Establecemos las fechas de fin de cirunscripcion
		circunscripcion.setFecFinCircunscripcion(new Date());
		// Y el indicador lo ponemos en 0 indicando que la autorizacion se acabo
		circunscripcion.setIndCircunscripcionActiva(0);
		// Le agregamo el tramite de suspension
		circunscripcion.setTramiteSuspension(new Tramite());
		// le colocamos el id del tramite de supension
		circunscripcion.getTramiteSuspension().setTramiteId(
				solicitudSuspencion.getTramites().get(0).getTramiteId());
		// Guardamos la actualizacion de la circunscripcion ya con su tramite de
		// suspension
		try {
			circunscripcionEntityLocal
					.updateCircunscripcionForanea(circunscripcion);
		} catch (Exception e1) {
			log.error("Error al actualizar tramite", e1);
			DerechohabientesBusinessException.throwException(
					"error.actualizar.tramite", e1.getCause().getMessage());
		}

		// marcamos como atendida l solicitud de suspension
		String observaciones = null;
		if (StringUtils.isNotBlank(circunscripcion.getObservacion())) {
			observaciones = circunscripcion.getObservacion().length() > 255 ? circunscripcion.getObservacion().substring(0,250) : circunscripcion.getObservacion();
		}
		solicitudServiceLocal.marcarAtendidaSolictud(
				solicitudSuspencion.getSolicitudId(),
				observaciones, usuario.getFisica());

		// retornamos la solicitud
		return circunscripcion;
	}

	/**
	 * Metodo para iniciar la validacion del tramite de correccion de
	 * informacion recibiendo los siguientes parametros:
	 * 
	 * @param idTramite
	 *            el id de la correccion
	 * @return correccion objeto de tipo correccionDatoDerechohabiente
	 *         encontrado
	 * @throws DerechohabientesBusinessException
	 */
	@Override
	public Solicitud inicioValidacionCorreccion(Long idSolicitud,
			Long idAsignacionNss) throws DerechohabientesBusinessException {
		// Obtenemos el tramite de correccion de datos mediante el id del
		// tramite
		TramiteCorreccionDerechohabiente correccion = null;
		Solicitud solicitudCorreccion = new Solicitud(idSolicitud);

		try {
			solicitudCorreccion = solicitudBusinessRemote
					.consultar(solicitudCorreccion);
		} catch (SolicitudNoEncontradaException e) {
			log.error("No se encontro la solicitud", e);
			DerechohabientesBusinessException.throwException(
					"error.busqueda.tramite", "No se encontro el tr�mite");
		}

		if (solicitudCorreccion != null) {

			correccion = this.getTramiteCorreccion(solicitudCorreccion,null);

			if (correccion == null) {
				DerechohabientesBusinessException.throwException(
						"error.busqueda.tramite", "No se encontro el tr�mite");

			}
		}

		// retornamos el tramite encontrado
		return solicitudCorreccion;
	}

	/**
	 * Metodo para obtener la circunscripcion de una persona de acuerdo a
	 */
	@Override
	public TramiteCircunscripcionForanea getCircunscripcionForanea(
			Long idPersona, AsignacionNSS nss, Boolean activa)
			throws DerechohabientesBusinessException {

		TramiteCircunscripcionForanea circunscripcion = null;

		try {
			circunscripcion = circunscripcionEntityLocal
					.getCircunscripcionForanea(idPersona, nss, activa);
		} catch (Exception e1) {
			log.error("Error al recuperar el tramite de circunscipcion", e1);
			DerechohabientesBusinessException.throwException(
					"error.busqueda.tramite", e1.getCause().getMessage());
		}

		if (circunscripcion == null)
			DerechohabientesBusinessException.throwException(
					"error.busqueda.tramite",
					"No se encontro el tramite de circunscripci�n");

		return circunscripcion;
	}

	@Override
	public Solicitud getCircunscripcionTramite(Long idSolicitud)
			throws DerechohabientesBusinessException {
		TramiteCircunscripcionForanea circunscripcion = null;
		Solicitud solicitudSuspension = new Solicitud(idSolicitud);

		try {
			solicitudSuspension = solicitudBusinessRemote
					.consultar(solicitudSuspension);
			circunscripcion = this
					.getTramiteCircunscripcion(solicitudSuspension);
			circunscripcion = circunscripcionEntityLocal
					.getSuspencionCircunscripcionForane(circunscripcion
							.getTramiteId());
		} catch (Exception e1) {
			log.error("Error al recuperar el tramite de circunscipcion", e1);
			DerechohabientesBusinessException.throwException(
					"error.busqueda.tramite", e1.getCause().getMessage());
		}

		if (circunscripcion == null)
			DerechohabientesBusinessException.throwException(
					"error.busqueda.tramite",
					"No se encontro el tramite de circunscripci�n");

		solicitudSuspension.setTramites(new ArrayList<Tramite>());
		solicitudSuspension.getTramites().add(circunscripcion);

		return solicitudSuspension;
	}

	/**
	 * @author Mario Teran Blanco Metodo para guardar una solicitud de tipo de
	 *         correccion de datos de derechohabiente pasandole los siguientes
	 *         parametros
	 * @param derechohabiente
	 *            El integrante afectado por el tramite
	 * @param usuario
	 *            El usuario que solicita el tramite
	 * @param nss
	 *            objeto de tipo asignacionNSS que representa al asegurado
	 *            cabeza de grupo familiar
	 * @param correccion
	 *            objeto de tipo CorreccionDatoDerechohabiente
	 * @return Solicitud la solicitud guardada
	 * @throws DerechohabientesBusinessException
	 */
	private Solicitud guardarSolicitudCorreccion(GrupoFamiliar derechohabiente,
			Usuario usuario, AsignacionNSS nss,
			TramiteCorreccionDerechohabiente correccion,
			TipoTramiteEnum tipoCorreccion, OrigenSolicitudEnum origen)
			throws DerechohabientesBusinessException {

		// Creamos la solicitud a guardar
		Solicitud solicitud = new Solicitud();
		correccion.setIdAsignacionNss(nss.getIdAsignacionNSS());
		correccion.setNss(nss.getNssStr());
		// Guardamos nuestra solicitud y nuestro tramite
		try {
			// solicitud =
			// solicitudServiceLocal.guardarSolicitud(derechohabiente,tipoCorreccion,
			// usuario, nss,TipoSolicitudEnum.CORRECCION);
			log.debug(">>> guardando la solicitud de correcci\u00F3n");
			solicitud = tramiteServiceLocal.guardarCorreccion(derechohabiente,tipoCorreccion, usuario, nss, correccion, origen);
		} catch (Exception e1) {
			log.error("No fue posible guardar la solicitud", e1);
			DerechohabientesBusinessException.throwException(
					ExceptionMessages.ERROR_GUARDADO_SOLCITUD, e1.getMessage());
		}

		// retornamosla solicitud creada
		return solicitud;

	}

	/**
	 * @author Mario Teran Blanco Metodo para guardar una solicitud de tipo de
	 *         correccion de datos de derechohabiente pasandole los siguientes
	 *         parametros
	 * @param derechohabiente
	 *            El integrante afectado por el tramite
	 * @param usuario
	 *            El usuario que solicita el tramite
	 * @param nss
	 *            objeto de tipo asignacionNSS que representa al asegurado
	 *            cabeza de grupo familiar
	 * @param correccion
	 *            objeto de tipo CorreccionDatoDerechohabiente
	 * @return Solicitud la solicitud guardada
	 * @throws DerechohabientesBusinessException
	 */
	private Solicitud guardarSolicitudCorreccion(
			List<GrupoFamiliar> derechohabiente, Usuario usuario,
			AsignacionNSS nss, TramiteCorreccionDerechohabiente correccion,
			TipoTramiteEnum tipoCorreccion, OrigenSolicitudEnum origen)
			throws DerechohabientesBusinessException {

		// Creamos la solicitud a guardar
		Solicitud solicitud = new Solicitud();

		// Guardamos nuestra solicitud y nuestro tramite
		try {
			solicitud = tramiteServiceLocal.guardarCorreccion(derechohabiente,
					tipoCorreccion, usuario, nss, correccion, origen);
		} catch (Exception e1) {
			log.error("No fue posible guardar la solicitud", e1);
			DerechohabientesBusinessException.throwException(
					ExceptionMessages.ERROR_GUARDADO_SOLCITUD, e1.getMessage());
		}

		// retornamosla solicitud creada
		return solicitud;

	}

	/**
	 * Metodo para guardar los datos personales contenidos en un objeto de tipo
	 * correccionDatoDerechohabiente
	 * 
	 * @param correccion
	 * @throws DerechohabientesBusinessException
	 */
	private void actualizarDatosPersonales(
			TramiteCorreccionDerechohabiente correccion)
			throws DerechohabientesBusinessException {
		// parseamos los datos que vienen en correccion a un objeto de tipo
		// fisica
		Fisica fisica = this.convertirCorreccionToFisica(correccion);
		try {
			// Guardamos los datos con el servicio de persona
			personaBusinessRemote.actualizarPersona(fisica);

			// Se actualizan las calificaciones en caso de contar con ellas
			if (correccion.getCalificacion() != null
					&& correccion.getCalificacion().getIdCalificacion() != null) {
				Long idCalificacion = correccion.getCalificacion()
						.getIdCalificacion();

				try {
					if (idCalificacion.equals(CalificacionEnum.VALIDADO_IMSS
							.getCodigo().longValue())) {
						calificacionesPersonaBusinessService
								.calificarIMSS(fisica);
					} else if (idCalificacion
							.equals(CalificacionEnum.VALIDADO_RENAPO
									.getCodigo().longValue())) {
						calificacionesPersonaBusinessService
								.calificarRENAPO(fisica);
					}
				} catch (PersonaSinCalificacionesException e) {
					log.error("Error cal calificar", e);
				}

			}
		} catch (PersonaNoEncontradaException e) {
			log.error("Error al actualizar a la persona", e);
			DerechohabientesBusinessException.throwException(
					"error.actualizar.persona", e.getCause().getMessage());
		}
	}

	private void setNuevosDatosPersonales(GrupoFamiliar grupo, TramiteCorreccionDerechohabiente correccion) {
		grupo.getDerechohabiente().setIdPersona(correccion.getIdPersona());
		grupo.getDerechohabiente().setNombre(correccion.getNombre());
		grupo.getDerechohabiente().setPrimerApellido(correccion.getPrimerApellido());
		grupo.getDerechohabiente().setSegundoApellido(correccion.getSegundoApellido());
		grupo.getDerechohabiente().setCurp(correccion.getCurpCap());
		grupo.getDerechohabiente().setFechaNacimiento(correccion.getFechaNacimiento());
		grupo.getDerechohabiente().setEstadoCivil(correccion.getEstadoCivil());
		grupo.getDerechohabiente().setSexo(correccion.getSexo());
		grupo.getDerechohabiente().setLugarNacimiento(correccion.getLugarNacimiento());
	}
	/**
	 * Metodo para parsear los valores de un objeto de tipo
	 * correccionDatoDerechohabiente a un tipo fisica
	 * 
	 * @param correccion
	 * @return Fisica
	 */
	private Fisica convertirCorreccionToFisica(
			TramiteCorreccionDerechohabiente correccion) {
		Fisica fisica = new Fisica();
		fisica.setIdPersona(correccion.getIdPersona());
		fisica.setNombre(correccion.getNombre());
		fisica.setPrimerApellido(correccion.getPrimerApellido());
		fisica.setSegundoApellido(correccion.getSegundoApellido());
		fisica.setCurp(correccion.getCurpCap());
		fisica.setFechaNacimiento(correccion.getFechaNacimiento());
		fisica.setEstadoCivil(correccion.getEstadoCivil());
		fisica.setSexo(correccion.getSexo());
		fisica.setLugarNacimiento(correccion.getLugarNacimiento());

		return fisica;

	}

	/**
	 * Metodo para quitar de un grupo familiar a los integrantes dados de baja
	 * 
	 * @param entrada
	 *            La lista a filtrar
	 * @return Lis<GrupoFamiliar> lista con los integrantes del grupo familiar
	 *         en estado vigente y conservacion de derechos
	 */
	private List<GrupoFamiliar> quitarBajas(List<GrupoFamiliar> entrada) {
		List<GrupoFamiliar> salida = new ArrayList<GrupoFamiliar>();

		// Recorremos la lista de los integrantes del grupo familiar
		for (GrupoFamiliar integrante : entrada) {
			// Si su estado es baja o fallecido lo quitamos de la lista de
			// integrante
			if (integrante.getEstadoDerechohabiente()
					.getIdEstadoDerechohabiente() != EstadoDerechohabienteEnum.BAJA
					.getId()
					&& integrante.getEstadoDerechohabiente()
							.getIdEstadoDerechohabiente() != EstadoDerechohabienteEnum.FALLECIDO
							.getId())
				salida.add(integrante);
		}
		return salida;
	}
	
	

	/**
	 * Metodo para solo dejar a los padres y concubinas para cambiar domicilio
	 * 
	 * @param entrada
	 * @return
	 */
	private List<GrupoFamiliar> candidatosCorreccionAsegurado(
			List<GrupoFamiliar> entrada, Boolean patronImss) {
		List<GrupoFamiliar> salida = new ArrayList<GrupoFamiliar>();

		// Recorremos la lista de los integrantes del grupo familiar
		for (GrupoFamiliar integrante : entrada) {
			// Si su estado es baja o fallecido lo quitamos de la lista de
			// integrante
			if (patronImss != null) {
				if (integrante.getParentesco().getIdParentesco() == ParentescoEnum.PADRES
						.getId() && !patronImss)
					salida.add(integrante);
			} else {
				if (integrante.getParentesco().getIdParentesco() == ParentescoEnum.PADRES
						.getId())
					salida.add(integrante);
			}
			if (integrante.getParentesco().getIdParentesco() == ParentescoEnum.CONCUBINARIO
					.getId())
				salida.add(integrante);
		}

		return salida;
	}

	/**
	 * Metodo para quitar el asegurado del grupo familiar
	 * 
	 * @param entrada
	 * @return
	 */
	private List<GrupoFamiliar> quitarAsegurado(List<GrupoFamiliar> entrada) {
		List<GrupoFamiliar> salida = new ArrayList<GrupoFamiliar>();
		// Recorremos la lista de los integrantes del grupo familiar
		for (GrupoFamiliar integrante : entrada) {
			// Si su estado es baja o fallecido lo quitamos de la lista de
			// integrante
			if (integrante.getParentesco().getIdParentesco() != ParentescoEnum.ASEGURADO
					.getId()
					&& integrante.getParentesco().getIdParentesco() != ParentescoEnum.PENSIONADO
							.getId())
				salida.add(integrante);
		}

		return salida;
	}

	
	
	/**
	 * Metodo para guardar la validacion del tramite de correccion de datos de
	 * derechohabiente recibiendo los siguientes parametros:
	 * 
	 * @param correccion
	 *            de tipo CorreccionDatosDerechohabiente que contiene la
	 *            informacion a guardar
	 * @return CorreccionDatoDerechohabiente objeto que contendra la informacion
	 *         ya guardada
	 * @throws DerechohabientesBusinessException
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public TramiteCorreccionDerechohabiente guardarValidacionCorreccionDatos(
			Solicitud solicitudCorreccion, GrupoFamiliar afectado,AsignacionNSS asignacionNSS, CabezaGrupoFamiliar cabeza, Fisica personaUsuario,
			List<GrupoFamiliar> padresConcubina, List<GrupoFamiliar> integrantesSinDomicilioenUMF) throws DerechohabientesBusinessException,ImpactaAlmacenesWSException {
		
		boolean tieneIndicadorRN = false;
		boolean tieneNombreRN  = false;
		boolean quitarMarcaRN = false;
		boolean existeCambioDeDomicilio = false;
		boolean aseguradoAfectado = TramiteUtil.isAsegurado(afectado);
		String nombreCorreccion = null;
		
		TramiteCorreccionDerechohabiente correccion = this.getTramiteCorreccion(solicitudCorreccion,null);
		List<GrupoFamiliar> integrantesParaMovimientos = new ArrayList<GrupoFamiliar>();
		//si no hay tramite de correccion en la solicitud mandamos un error
		if (correccion == null) {
			throw new DerechohabientesBusinessException("No se encontro el tramite en la solicitud","No se encontro el tramite en la solicitud");
		}
		//obtenemos el nombre que viene en el tramite de correccion
		nombreCorreccion = correccion.getNombre();
		//si el afectado viene nulo o si no corresponde con la persona en la correccion lo consultamos
		if(afectado == null || !afectado.getDerechohabiente().getIdPersona().equals(correccion.getIdPersona())) {
			try {
				afectado = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(correccion.getIdAsignacionNss(), correccion.getIdPersona());
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Error al recuperar al integrante afectado", e);
				DerechohabientesBusinessException.throwException("error.busqueda.integrante", e.getCause().getMessage());
			}
		}
	
		//si el afectado no se iubico mandamos una excepcion
		if (afectado == null) {
			DerechohabientesBusinessException.throwException("No se encontro al integrante del grupo familiar","No se encontro al integrante del grupo familiar");
		}
		
		//verificamos si el afectado tiene el indicador de recien nacido
		tieneIndicadorRN = afectado.getIndRecienNacido() != null && afectado.getIndRecienNacido().intValue() == 1;
		//verificamos si tiene nombre de recien nacido ya sea RN o RECIEN NACIDO
		tieneNombreRN = nombreCorreccion.equals("RECIEN NACIDO") || nombreCorreccion.trim().equals("RN");
		//si existe un cambio en el nombre, siginifica qie ya no es un recien nacido o que nunca lo fue
		if(nombreCorreccion != null) {
			//quitaremos la marca de recien nacido cuando el nombre haya cambiado o cuando se tenga el indicador de 
			//recien nacido pero no se llame ni recien nacido ni rn, este ultimo caso no se deberia presentar
			if(!nombreCorreccion.equals(afectado.getDerechohabiente().getNombre()) || (tieneIndicadorRN && !tieneNombreRN)) {
				quitarMarcaRN = true;
			}
		}
		//domicilio anterior del asegurado
		Domicilio domicilioAnterior = afectado.getDomicilio();
		
		correccion.setNumCalidad(this.obtenerNumeroCalidad(correccion));
		correccion.getDomicilio().getAsentamiento().setCodigoPostal(correccion.getDomicilio().getCodigoPostal());
		correccion.setDomicilio(this.quitarNulosDomicilio(correccion.getDomicilio()));
		
		// ------------------------------------------------------------------------------
		// El asegurado puede no contar con fecha de nacimiento y no puede capturarla
		// mediante ICA. En caso de traerla se pueden calcular los agregados.
		// ------------------------------------------------------------------------------
		if( correccion.getFechaNacimiento() != null ){
			
			afectado.setAgregadoMedico(this.obtenerAgregadoMedico(correccion, cabeza));
			afectado.setAgregadoAfiliacion(this.obtenerAgregadoAfiliacion(correccion));
		}
		//Se verifica si se tiene que crear el nuevo domicilio, en caso de que se tenga guardar quiere decir que se modifico
		if (correccion.getDomicilio().getClave() == null) {
			existeCambioDeDomicilio = true;
			// Si el domicilio cambio registramos el nuevo domicilio
			Domicilio domicilioNuevo = this.guardarDomicilioNuevo(correccion.getDomicilio());
			// y se lo agregamos a la correccion
			correccion.setDomicilio(domicilioNuevo);
		}
		//Ponemos el parentesco del afectado
		afectado.setParentesco(correccion.getParentesco());
		// Colocamos el nuevo domicilio al afectado
		afectado.setDomicilio(correccion.getDomicilio());
		// Colocamos la fecha de actualizacion del derechohabiente
		// enel grupo familiar
		afectado.setFechaRegistroActualizacion(new Date());
		//seteamos los nuevos datos personales
		this.setNuevosDatosPersonales(afectado, correccion);
		//Actualizamos los datos personales
		this.actualizarDatosPersonales(correccion);
		//Si cambio el nombre ponemos el identificador de recien nacido en nulo
		if(quitarMarcaRN) {
			afectado.setIndRecienNacido(null);
		}

		// y guardamos los nuevos datos del domicilio del derechohabiente
		try {
			GrupoFamiliar aux = grupoFamiliarDaoLocal.updateIntegrante(afectado);
			afectado.setCvePersonaDomicilio(aux.getCvePersonaDomicilio());
			//se actualiza la persona del tramite
			correccion.setPersona(afectado.getDerechohabiente());
			integrantesParaMovimientos.add(afectado);
		} catch (Exception e) {
			e.printStackTrace();
			// Si ocurrio algun error mandamos una excepcion
			// indicando que no se
			// pudo guardar la solicitud
			log.error("No se pudo actualizar el integrante", e);
			throw new DerechohabientesBusinessException("error.actualizar.integrante", e.getCause().getMessage());
		}
		
		// Checamos si el parentesco es asegurado o pensionado para tambien en
		// caso de haber cambiado de domicilio cabiar a los padres
		if (aseguradoAfectado && existeCambioDeDomicilio) {
			log.debug("Voy a actualizar domicilio a los padres y concubinas");
			List<GrupoFamiliar> integrantesActualizar = new ArrayList<GrupoFamiliar>();
			//Si la lista de padres y concubinar no esta vacia se anade
			if(padresConcubina != null && !padresConcubina.isEmpty()) {
				log.debug("existen padre que se cambiaran de domicilio junto con el asegurado");
				integrantesActualizar.addAll(padresConcubina);
			}
			//Si la lista de personas sin domicilio en la misma umf no est� vacia se anade
			if(integrantesSinDomicilioenUMF != null && !integrantesSinDomicilioenUMF.isEmpty()) {
				log.debug("Existen personas sin domicilio a los que se les asignara uno");
				integrantesActualizar.addAll(integrantesSinDomicilioenUMF);
			}
			log.debug("Empiexo a actualizar a los padres, concubina y personas sin domicilio");
			for(GrupoFamiliar integranteActualizar: integrantesActualizar) {
				integranteActualizar.setDomicilio(correccion.getDomicilio());
				// y la fecha de su actualizacion
				integranteActualizar.setFechaRegistroActualizacion(new Date());
				// y por ultimo actualizamos los cambios
				try {
					GrupoFamiliar aux = grupoFamiliarDaoLocal.updateIntegrante(integranteActualizar);
					integranteActualizar.setCvePersonaDomicilio(aux.getCvePersonaDomicilio());
					integrantesParaMovimientos.add(integranteActualizar);
				} catch (Exception e) {
					e.printStackTrace();
					// Si ocurrio algun error mandamos una excepcion
					// indicando que no se
					// pudo guardar la solicitud
					log.error("No se pudo actualizar el integrante", e);
					throw new DerechohabientesBusinessException("error.actualizar.integrante", e.getCause().getMessage());
				}
			}
		}
		//marcamos el tramite como cerrado
		correccion.setEstadoTramite(new EstadoTramite());
		correccion.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());
		//ponemos en la correccion el domicilio anterior
		if(domicilioAnterior != null) {
			correccion.setDomicilio(domicilioAnterior);
		}
		//para la correccion de datos no se debe guardar el medico
		correccion.setMedicoEnTurno(null);
		try {
			// guardamos en dit correccion derechohabiente
			correccionDerechohabienteEntityLocal.saveCorreccionDerechohabiente(correccion);
		} catch (Exception e) {
			e.printStackTrace();
			// Si ocurrio algun error mandamos una excepcion indicando que no se pudo guardar la solicitud
			log.error("No fue posible guardar la solicitud", e);
			throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_GUARDADO_SOLCITUD, e.getCause().getMessage());
		}

		// -----------------------------------------------------------------------------------
		// Si esta en baja administrativa y cambio de parentesco, debemos:
		//
		//	1: 	Desactivar la baja en dit_baja_derechohabiente asignando null al
		//		campo ind_baja_activa
		//	2:	Asignar null en el campo fec_registro_baja en dit_persona
		//  3:	Invocar el WSActualizaFuentesBDTU y cambiar el estado del 
		//		derechohabiente a VIGENTE
		// -----------------------------------------------------------------------------------
		try{
			boolean enBaja = correccion.getIdEstadoDerechohabiente().equals( EstadoDerechohabienteEnum.BAJA.getId());
			boolean cambioParentesco = !correccion.getParentesco().getIdParentesco().equals(correccion.getParentescoActual().getIdParentesco());
			if( enBaja && cambioParentesco ){

				List<Long> idPersonas = new ArrayList<Long>();
				idPersonas.add(correccion.getIdPersona());

				List<Long> tiposBaja = new ArrayList<Long>();
				tiposBaja.add(TipoBajaDerechohabienteEnum.ADMINISTRATIVA.getId());

				List<BajaDerechohabienteDto> bajas = bajaDerechohabienteService.getBajaDerechohabiente(correccion.getIdAsignacionNss(), idPersonas, tiposBaja, true);

				// ------------------------------------------------------------------------------
				// El derechohabiente tiene una BAJA ADMINISTRATIVA activa
				//
				// Nota: 	Puede tener otras bajas administrativas o de otro tipo que no
				// 			esten activas.
				// ------------------------------------------------------------------------------
				if( bajas != null ){

					BajaDerechohabienteDto bajaDto = bajas.get(0);
					bajaDto.setIndBajaActiva(null);
					bajaDerechohabienteService.actualizarInsertarBaja(bajaDto);

					try {
						Fisica fisica = new Fisica(correccion.getIdPersona());
						fisica.setFechaDefuncion(null);
						AfectarDatosPersonaWrapper datosPersona = new AfectarDatosPersonaWrapper(fisica); 
						datosPersona.setModificarFechaDefuncion(true);
						afectarDatosPersonaBusinessRemote.afectarDatosPersonaFisica(datosPersona);
					} catch (PersonaNoEncontradaException e) {
						e.printStackTrace();
					}


					// -----------------------------------------
					// En el WebService se env�a 0
					// Localmente se asigna a null
					// -----------------------------------------
					bajaDto.setIndBajaActiva(0L);
					TramiteBajaDerechohabiente tramite = new TramiteBajaDerechohabiente(bajaDto.getCveIdTramite());
					tramite.setPersona(afectado.getDerechohabiente());
					Solicitud solicitudBaja = new Solicitud(tramite);

					finalizaSolicitudServiceLocal.finalizaSolicitudBajaDerechohabiente(solicitudBaja, bajaDto, false);
				}

			}
		}catch(IllegalArgumentException e){
			e.printStackTrace();
			log.error("Error al actualizar el registro de la baja", e);
			DerechohabientesBusinessException.throwException(
					"error.actualizar.registroBaja", e.getCause().getMessage());
		}catch(TransactionRequiredException e){
			e.printStackTrace();
			log.error("Error al actualizar el registro de la baja", e);
			DerechohabientesBusinessException.throwException(
					"error.actualizar.registroBaja", e.getCause().getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error al actualizar el registro de la baja", e);
			DerechohabientesBusinessException.throwException(
					"error.actualizar.registroBaja", e.getCause().getMessage());
		}

		log.debug("llego2");

		//mandamos los moviemitnos al WS de vigencia
		try {
			finalizaSolicitudServiceLocal.mandaMovimientosWS(integrantesParaMovimientos, true);
		} catch (IllegalArgumentException e) {
			log.error("Error en los argumentos del ws",e);
			DerechohabientesBusinessException.throwException("Datos incompletos para movimiento de vigencia", e.getMessage());
		} catch (ImpactaAlmacenesWSException e) {
			log.error("Ocurrio un error al finalizar la modificacion del derechohabiente", e);
			throw e;
		} catch (DerechohabientesBusinessException e) {
			log.error("Ocurrio un error al finalizar la solicitu", e);
			throw e;
		} catch (Exception e) {
			log.error("Error al finalzar correccion", e);
			DerechohabientesBusinessException.throwException("Datos incompletos para movimiento de vigencia", e.getMessage());
		}

		// por ultimo marcamos como atendida la solicitud
		try {
			
			solicitudCorreccion.setObservacion(correccion.getObservacion());
			tramiteServiceLocal.actualizarSolicitudAConcluida(solicitudCorreccion);
			tramiteServiceLocal.actualizaXMLTramite(correccion);
			// ------------------------------------------------------------------------------
			// FIRMA ELECTRONICA
			//
			// Se necesita para firmar los documentos resultantes
			// Si no existe la firma la crea; necesita el id de solicitud
			// ------------------------------------------------------------------------------
			log.debug(" ========== CORRECCION FIRMA =====================");
			tramiteDocumentoService.generaFirmaElectronica(afectado.getAsignacionNSS(), solicitudCorreccion, correccion.getTipoTramite().getDescripcion());

		} catch(DocumentoException e) {

			// -------------------------------------------------
			// Lanzada al generar la firma electr�nica
			// -------------------------------------------------
			e.printStackTrace();
			DerechohabientesBusinessException dbe = new DerechohabientesBusinessException(ExceptionMessages.FIRMA_ELECTRONICA.ERROR.getMensaje() ,e.getSituacion());
			dbe.setCodigo(ExceptionMessages.FIRMA_ELECTRONICA.ERROR.getCodigo());

			//throw dbe;
		} catch(Exception e) {
			e.printStackTrace();
		}

		log.debug("llego3");
	
		return correccion;
	}

	/**
	 * Metodo para guardar la validacion del tramite de cambio umf recibiendo
	 * los siguientes parametros
	 * 
	 * @param correccion
	 *            de tipo CorreccionDatosDerechohabiente que contiene la
	 *            informacion a guardar
	 * @return CorreccionDatoDerechohabiente objeto que contendra la informacion
	 *         ya guardada
	 * @throws DerechohabientesBusinessException
	 */
	@Override
	public Solicitud guardarValidacionCambioUmf(Solicitud correccion,
			Fisica usuarioPersona, Boolean patronImss, AsignacionNSS nss)
			throws DerechohabientesBusinessException {

		correccion = this.guardarFinalizadoTramiteCambioClinica(correccion,
				usuarioPersona, patronImss, nss, null, null,false);

		try {
			solicitudBusinessRemote.actualizarSolicitudAEstatusConcluida(correccion.getSolicitudId());
			TipoTramite tTramite = catalogosDAO.getTipoTramite(new Long(TipoTramiteEnum.CAMBIO_CLINICA.getCodigo()));
			tramiteDocumentoService.generaFirmaElectronica(nss, correccion, tTramite.getDescripcion());
//			Manda al WS EFM*
//			finalizaSolicitudServiceRemote.finalizarSolicitudTramites(correccion, "EIVI");
			
		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
		} catch (TramiteNoEncontradoException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new DerechohabientesBusinessException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new DerechohabientesBusinessException(e.getMessage());
		}

		return correccion;
	}

	/**
	 * Metodo para guardar la validacion del tramite de cambio de medico
	 * recibiendo los siguientes parametros:
	 * 
	 * @param correccion
	 *            de tipo CorreccionDatosDerechohabiente que contiene la
	 *            informacion a guardar
	 * @return CorreccionDatoDerechohabiente objeto que contendra la informacion
	 *         ya guardada
	 * @throws DerechohabientesBusinessException
	 */
	@Override
	public Solicitud guardarValidacionCambioMedico(Solicitud correccion,
			Fisica usuarioPersona, AsignacionNSS nss)
			throws DerechohabientesBusinessException {

		this.guardarValCambioMedicoInterno(correccion, usuarioPersona, true,nss);
					
		try {

			try {
				solicitudBusinessRemote.actualizarSolicitudAEstatusConcluida(correccion.getSolicitudId());
			} catch (TramiteNoEncontradoException e) {
				e.printStackTrace();
			}
		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
		}

		return correccion;
	}

	@Override
	public TramiteCorreccionDerechohabiente getTramitePendienteByAutorizar(
			Long idTramite) throws DerechohabientesBusinessException {
		TramiteCorreccionDerechohabiente tramite = null;
		try {
			tramite = solicitudServiceLocal
					.detalleCorreccionDerechohabiente(idTramite);
		} catch (Exception e1) {
			log.error("Error al recuperar el tramite de circunscipcion", e1);
			DerechohabientesBusinessException.throwException(
					"error.busqueda.tramite", e1.getCause().getMessage());
		}

		if (tramite.getEstadoTramite().getIdEstadoTramitePersona()
				.equals(EstadoTramiteEnum.EN_ESPERA_AUTORIZACION.getCodigo())) {
			throw new DerechohabientesBusinessException(Constants.SINDATOS);
		}
		return tramite;
	}

	@Override
	public Solicitud saveSolicitudAceptada(Long idSolicitud, AsignacionNSS nss,
			Fisica personaUsuario) throws DerechohabientesBusinessException {

		Solicitud solicitudCorreccion = new Solicitud(idSolicitud);
		TramiteCorreccionDerechohabiente correccion = null;
		CabezaGrupoFamiliar cabeza = null;

		try {
			solicitudCorreccion = solicitudBusinessRemote
					.consultar(solicitudCorreccion);
		} catch (SolicitudNoEncontradaException e3) {
			DerechohabientesBusinessException.throwException(
					ExceptionMessages.ERROR_CONSULTA_SOLICITUD,
					e3.getSituacion());
		}

		correccion = this.getTramiteCorreccion(solicitudCorreccion, null);

		if (correccion == null)
			DerechohabientesBusinessException.throwException(
					"error.busqueda.tramite",
					"No existe tramite de correccion en la solicitud");

		// Obtenemos al itegrante afectado por el tramite
		GrupoFamiliar afectado = null;
		try {
			cabeza = grupoFamiliarDaoLocal.getCabezaGrupoFamiliar(nss
					.getIdAsignacionNSS());
			afectado = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(
					nss.getIdAsignacionNSS(), correccion.getIdPersona());
		} catch (Exception e1) {
			log.error("Error al recuperar al integrante afectado", e1);
			DerechohabientesBusinessException.throwException(
					"error.busqueda.integrante", e1.getCause().getMessage());
		}

		if (afectado == null)
			DerechohabientesBusinessException.throwException(
					"exception.RNGD0003",
					"No se encontro al integrante del grupo familiar");

		correccion.getDomicilio().getAsentamiento()
				.setCodigoPostal(correccion.getDomicilio().getCodigoPostal());
		correccion.setDomicilio(this.quitarNulosDomicilio(correccion
				.getDomicilio()));

		// Verificamos si cambio el domicilio
		if (correccion.getDomicilio().getClave() == null) {
			// Si el domicilio cambio registramos el nuevo domicilio
			Domicilio domicilioNuevo = this.guardarDomicilioNuevo(correccion.getDomicilio());
			// y se lo agregamos a la correccion
			correccion.setDomicilio(domicilioNuevo);
		} else {
			correccion.setDomicilio(afectado.getDomicilio());
		}

		Long parentescoAfectado = afectado.getParentesco().getIdParentesco();
		// Colocamos el parentesco
		afectado.setParentesco(correccion.getParentesco());
		// Colocamos el nuevo domicilio al afectado
		afectado.setDomicilio(correccion.getDomicilio());
		// Colocamos la fecha de actualizacion del derechohabiente enel grupo
		// familiar
		afectado.setFechaRegistroActualizacion(new Date());

		if (!parentescoAfectado.equals(correccion.getParentesco()
				.getIdParentesco())) {
			// colocamos la nueva calidad del parentesco nuevo
			afectado.setCalidad(this.getCalidad(correccion.getParentesco()
					.getIdParentesco(), nss.getIdAsignacionNSS()));
		}

		EstadoDerechohabiente edoDer = new EstadoDerechohabiente();
		Long idEstado = this.validaEstadoDerechohabientes(correccion
				.getFechaNacimiento(), correccion.getSexo().getIdSexo()
				.longValue(), correccion.getParentesco().getIdParentesco(),
				cabeza.getPatronImss() == 1 ? true : false) ? EstadoDerechohabienteEnum.VIGENTE
				.getId() : EstadoDerechohabienteEnum.BAJA.getId();
		edoDer.setIdEstadoDerechohabiente(idEstado);
		afectado.setEstadoDerechohabiente(edoDer);

		// y guardamos los nuevos datos del integrante del grupo familiar
		try {
			grupoFamiliarDaoLocal.updateIntegrante(afectado);
		} catch (Exception e) {
			// Si ocurrio algun error mandamos una excepcion indicando que no se
			// pudo guardar la solicitud
			log.error("No se pudo actualizar el integrante", e);
			throw new DerechohabientesBusinessException(
					"error.actualizar.integrante", e.getCause().getMessage());
		}

		// Actualizamos los datos personales
		this.actualizarDatosPersonales(correccion);

		// guardamos los medios de contacto
		try {
			consumidorServiciosMediosContactoLocal.procesarMediosContactoCorreccion(correccion);
		} catch (Exception e1) {
			log.error("No se pudo actualizar los medios de contacto", e1);
		}
		// guardamos el tramite de correccion de derechohabiente
		try {
			correccionDerechohabienteEntityLocal
					.saveCorreccionDerechohabiente(correccion);
		} catch (Exception e) {
			// Si ocurrio algun error mandamos una excepcion indicando que no se
			// pudo guardar la solicitud
			log.error("No fue posible guardar la solicitud", e);
			throw new DerechohabientesBusinessException(
					ExceptionMessages.ERROR_GUARDADO_SOLCITUD, e.getCause()
							.getMessage());
		}

		if (afectado.getParentesco().getIdParentesco()
				.equals(ParentescoEnum.CONCUBINARIO.getId())
				|| afectado.getParentesco().getIdParentesco()
						.equals(ParentescoEnum.CONYUGE.getId())) {
			List<Long> parentescos = new ArrayList<Long>();
			parentescos.add(ParentescoEnum.CONCUBINARIO.getId());
			parentescos.add(ParentescoEnum.CONYUGE.getId());

			try {
				List<GrupoFamiliar> similares = grupoFamiliarDaoLocal
						.findIntegrantesDuplicados(afectado
								.getDerechohabiente().getIdPersona(),
								parentescos, EstadoDerechohabienteEnum.BAJA
										.getId());
				if (!similares.isEmpty()) {
					for (GrupoFamiliar similar : similares) {
						List<Long> afectada = new ArrayList<Long>();
						afectada.add(similar.getDerechohabiente()
								.getIdPersona());

						List<Long> tipoTramite = new ArrayList<Long>();
						tipoTramite
								.add(TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_TERMINO_DE_CONCUBINATO
										.getCodigo().longValue());
						tipoTramite
								.add(TipoTramiteEnum.BAJA_DE_DERECHOHABIENTE_POR_DIVORCIO
										.getCodigo().longValue());

						List<Long> estadoTramite = new ArrayList<Long>();
						estadoTramite.add(EstadoTramiteEnum.CERRADO.getCodigo()
								.longValue());
						List<Solicitud> solicitudes = solicitudTramiteBusinessRemote
								.getSolicitudesPersona(afectada, tipoTramite,
										estadoTramite, 1L,
										RazonResultadoEnum.NORMAL.getId(),
										similar.getAsignacionNSS()
												.getIdPersona(), true, 1, true);
						if (solicitudes == null || solicitudes.isEmpty()) {
							similar.setIndSimilarCalDifGpoFam(new Date());
							grupoFamiliarDaoLocal.updateIntegrante(similar);
						}
					}
				}
			} catch (Exception e) {
				log.error(
						"Error al buscar el mismo parentesco en otros grupos familiares",
						e);
			}
		}

		try {
			solicitudBusinessRemote
					.actualizarSolicitudAEstatusConcluida(solicitudCorreccion);
		} catch (SolicitudNoEncontradaException e) {
			DerechohabientesBusinessException.throwException(
					ExceptionMessages.ERROR_ACTUALIZA_SOLICITUD,
					e.getSituacion());
		} catch (TramiteNoEncontradoException e) {
			DerechohabientesBusinessException.throwException(
					ExceptionMessages.ERROR_ACTUALIZA_SOLICITUD,
					e.getSituacion());
		}

		solicitudCorreccion.setTramites(new ArrayList<Tramite>());
		solicitudCorreccion.getTramites().add(correccion);

		return solicitudCorreccion;
	}

	@Override
	public Solicitud saveAsignacionMedico(Long idDerechohabiente,
			Usuario usuario, AsignacionNSS nss,
			TramiteCorreccionDerechohabiente correccion,
			OrigenSolicitudEnum origen)
			throws DerechohabientesBusinessException {

		// Obtenemos al integrante del grupo familiar
		GrupoFamiliar integrante = null;
		try {
			integrante = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(
					nss.getIdAsignacionNSS(), idDerechohabiente);
		} catch (Exception e1) {
			log.error("Error al recuperar al integrante afectado", e1);
			DerechohabientesBusinessException.throwException(
					"error.busqueda.integrante", e1.getCause().getMessage());
		}

		if (integrante == null)
			DerechohabientesBusinessException.throwException(
					"error.busqueda.integrante",
					"No se encontro al integrante del grupo familiar");

		// Creamos nuestro objeto solicitud que obtendra el resultado de la
		// operacion
		Solicitud solicitud = this.guardarSolicitudCorreccion(integrante,
				usuario, nss, correccion,
				TipoTramiteEnum.ASIGNACION_CONSULTORIO_TURNO_MEDICO, origen);

		// retornamos la solicitud
		return solicitud;
	}

	@Override
	public Solicitud saveValidacionAsignacionMedico(
			Solicitud solicitudCorreccion, AsignacionNSS nss,
			Fisica personaUsuario) throws DerechohabientesBusinessException {

		// Obtenemos al integrante del grupo familiar
		GrupoFamiliar integrante = null;
		List<GrupoFamiliar> grupoFamiliar = null;

		TramiteCorreccionDerechohabiente correccion = this
				.getTramiteCorreccion(solicitudCorreccion, null);

		if (correccion == null) {
			DerechohabientesBusinessException
					.throwException("Inconsistencias en los datos de la solicitud");
		}

		try {
			integrante = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(
					nss.getIdAsignacionNSS(), correccion.getIdPersona());
		} catch (Exception e1) {
			log.error("Error al recuperar al integrante afectado", e1);
			DerechohabientesBusinessException.throwException(
					"error.busqueda.integrante", e1.getCause().getMessage());
		}

		if (integrante == null)
			DerechohabientesBusinessException.throwException(
					"error.busqueda.integrante",
					"No se encontro al integrante del grupo familiar");

		try {
			grupoFamiliar = grupoFamiliarDaoLocal
					.findGrupoFamiliarSinMedico(nss.getNssStr());
		} catch (Exception e1) {
			log.error(
					"Error al recuperar a los integrantes si medico asignacio",
					e1);
		}

		for (GrupoFamiliar afectado : grupoFamiliar) {

			afectado.setMedicoEnTurno(correccion.getMedicoEnTurno());
			try {
				grupoFamiliarDaoLocal.updateIntegrante(afectado);
				if (!afectado.getDerechohabiente().getIdPersona()
						.equals(integrante.getDerechohabiente().getIdPersona())) {
					/*
					 * Tramite tramite = new Tramite();
					 * tramite.setTramiteId(correccion.getTramiteId());
					 * tramite.setPersona(new Fisica());
					 * tramite.getPersona().setIdPersona
					 * (afectado.getDerechohabiente().getIdPersona());
					 * tramitePersonaFisicaDaoLocal
					 * .saveTramitePersonaFisica(tramite);
					 */
					solicitudBusinessRemote.agregarPersonaATramite(correccion
							.getTramiteId(), afectado.getDerechohabiente()
							.getIdPersona());
				}
			} catch (Exception e) {
				// Si ocurrio algun error mandamos una excepcion indicando que
				// no se
				// pudo guardar la solicitud
				log.error("No se pudo actualizar el integrante", e);
				throw new DerechohabientesBusinessException(
						"error.actualizar.integrante", e.getCause()
								.getMessage());
			}
		}

		correccion.setDomicilio(integrante.getDomicilio());
		try {
			correccionDerechohabienteEntityLocal
					.saveCorreccionDerechohabiente(correccion);
		} catch (Exception e) {
			// Si ocurrio algun error mandamos una excepcion indicando que no se
			// pudo guardar la solicitud
			log.error("No fue posible guardar la solicitud", e);
			throw new DerechohabientesBusinessException(
					ExceptionMessages.ERROR_GUARDADO_SOLCITUD, e.getCause()
							.getMessage());
		}

		try {
			solicitudBusinessRemote
					.actualizarSolicitudAEstatusConcluida(solicitudCorreccion);
		} catch (SolicitudNoEncontradaException e) {
			log.error("No se encontro la solicitud", e);
			throw new DerechohabientesBusinessException(
					ExceptionMessages.ERROR_GUARDADO_SOLCITUD, e.getCause()
							.getMessage());
		} catch (TramiteNoEncontradoException e) {
			log.error("No se encontro el tramite", e);
			throw new DerechohabientesBusinessException(
					ExceptionMessages.ERROR_GUARDADO_SOLCITUD, e.getCause()
							.getMessage());
		} catch (Exception e) {
			log.error("No fue posible finalizar la solicitud", e);
			throw new DerechohabientesBusinessException(
					ExceptionMessages.ERROR_GUARDADO_SOLCITUD, e.getCause()
							.getMessage());
		}

		return solicitudCorreccion;
	}

	@Override
	public boolean validarDatosDerechohabiente(
			TramiteCorreccionDerechohabiente correccion, AsignacionNSS nss)
			throws DerechohabientesBusinessException {

		Long idParentesco = correccion.getParentesco().getIdParentesco();
		GrupoFamiliar derechohabienteActualizar = null;

		try {
			derechohabienteActualizar = grupoFamiliarDaoLocal
					.getIntegranteGrupoFamiliar(nss.getIdAsignacionNSS(),
							correccion.getPersona().getIdPersona());
		} catch (Exception e1) {
			log.error("Error al recuperar al integrante afectado", e1);
			DerechohabientesBusinessException.throwException(e1.getCause()
					.getMessage(), "error.busqueda.integrante");
		}

		if (derechohabienteActualizar == null)
			DerechohabientesBusinessException.throwException(
					"No se encontro al integrante del grupo familiar",
					"error.busqueda.integrante");

		Integer sexoActualizar = correccion.getSexo().getIdSexo();
		List<GrupoFamiliar> integrantes = null;
		try {
			integrantes = grupoFamiliarDaoLocal.findGrupoFamiliarByParentesco(
					nss.getIdAsignacionNSS(), idParentesco);
		} catch (Exception e) {
			log.error("Error al recuperar grupoFamiliar", e);
			DerechohabientesBusinessException.throwException(e.getCause()
					.getMessage(), "error.buscar.grupoFamiliar");
		}

		integrantes = this.quitarBajas(integrantes);
		if (integrantes == null || integrantes.size() == 0) {
			return true;
		}

		List<GrupoFamiliar> esposa = null;
		List<GrupoFamiliar> concubina = null;

		if (!idParentesco.equals(derechohabienteActualizar.getParentesco()
				.getIdParentesco())) {

			if (idParentesco == ParentescoEnum.PADRES.getId()) {
				for (GrupoFamiliar grupoFamiliar : integrantes) {
					if (idParentesco.equals(grupoFamiliar.getParentesco()
							.getIdParentesco())
							&& sexoActualizar
									.equals(grupoFamiliar.getDerechohabiente()
											.getSexo().getIdSexo())) {
						DerechohabientesBusinessException.throwException(
								"Los padres son del mismo sexo", "error.padre");
					}
				}

			} else if (idParentesco == ParentescoEnum.CONCUBINARIO.getId()) {

				try {
					esposa = grupoFamiliarDaoLocal
							.findGrupoFamiliarParentescoEstado(
									nss.getIdAsignacionNSS(),
									ParentescoEnum.CONYUGE.getId(),
									EstadoDerechohabienteEnum.VIGENTE.getId());
				} catch (Exception e) {
					log.error(
							"Error al buscar a la concubina en otro grupo familiar",
							e);
				}

				if (sexoActualizar.equals(nss.getSexo().getIdSexo())) {
					DerechohabientesBusinessException.throwException(
							"El sexo de la pareja no puede ser el mismo",
							"error.pareja");
				} else if (esposa.size() > 0) {
					DerechohabientesBusinessException.throwException(
							"Conyuge repetido", "error.conyuge.repetido");
				} else if (integrantes.size() > 0) {
					DerechohabientesBusinessException.throwException(
							"Concubina repetida", "error.concubina.repetido");
				}

			} else if (idParentesco == ParentescoEnum.CONYUGE.getId()) {

				try {
					concubina = grupoFamiliarDaoLocal
							.findGrupoFamiliarParentescoEstado(
									nss.getIdAsignacionNSS(),
									ParentescoEnum.CONYUGE.getId(),
									EstadoDerechohabienteEnum.VIGENTE.getId());
				} catch (Exception e) {
					log.error(
							"Error al buscar a la esposa en otro grupo familiar",
							e);
				}

				if (sexoActualizar.equals(nss.getSexo().getIdSexo())) {
					DerechohabientesBusinessException.throwException(
							"El sexo de la pareja no puede ser el mismo",
							"error.pareja");
				} else if (concubina.size() > 0) {
					DerechohabientesBusinessException.throwException(
							"Concubina repetida", "error.concubina.repetido");
				} else if (integrantes.size() > 0) {
					DerechohabientesBusinessException.throwException(
							"Conyuge repetido", "error.conyuge.repetido");
				}
			}
		}
		return true;
	}

	@Override
	public RequisitosDTO requisitosCorreccion(GrupoFamiliar integrante,AsignacionNSS asignacionNss, CabezaGrupoFamiliar cabeza, Boolean consultarMod, List<Long> idsModalidades) 
			throws DerechohabientesBusinessException, Exception {
		RequisitosDTO requisitos = null;
		Map<String,Object> requisitosMap = null;
		
		try {
			requisitosMap = requisitosMinimosServiceLocal.requisitosMinimosCorreccion(integrante, asignacionNss, cabeza, consultarMod, idsModalidades);
			
			if(requisitosMap != null) {
				boolean correcto = (Boolean) requisitosMap.get("correcto");
				String mensaje = (String) requisitosMap.get("mensaje");
				requisitos = new RequisitosDTO(correcto ? 1 : 0, mensaje);
			}
		}catch(DerechohabientesBusinessException e) {
			log.error("Ocurrio un error de negocio", e);
			requisitos = new RequisitosDTO(0, e.getSituacion());
		}catch(Exception e) {
			log.error("Ocurrio un error inesperado",e);
			requisitos = new RequisitosDTO(0, "Ocurri&oacute; un error inesperado al validar los requisitos");
		}
		
		return requisitos;
	}

	private Domicilio quitarNulosDomicilio(Domicilio domicilio) {

		if (domicilio.getVialidadReferenciaPosterior() != null) {
			if (domicilio.getVialidadReferenciaPosterior().getClave() != null) {
				if (domicilio.getVialidadReferenciaPosterior().getClave() <= 0) {
					domicilio.setVialidadReferenciaPosterior(null);
				}
			}
		}
		return domicilio;
	}

	private GrupoFamiliar getAsegurado(List<GrupoFamiliar> grupoFamiliar) {
		GrupoFamiliar asegurado = null;

		for (GrupoFamiliar integrante : grupoFamiliar) {
			long parentesco = integrante.getParentesco().getIdParentesco();
			if (parentesco == ParentescoEnum.ASEGURADO.getId()
					|| parentesco == ParentescoEnum.PENSIONADO.getId()) {
				asegurado = integrante;
				break;
			}
		}
		return asegurado;
	}

	private boolean umfTramitador(List<UnidadMedicaFamiliar> umfs, Long idUmf) {

		for (UnidadMedicaFamiliar umf : umfs) {
			if (umf.getIdUMF().longValue() == idUmf.longValue())
				return true;
		}

		return false;
	}

	

	/**
	 * MEtodo para obtener al integrante de un grupo familiar con la mayor
	 * calidad donde la mayor es la calidad 1
	 * 
	 * @param integrantes
	 * @return GrupoFamiliar - Integrante con la mayor calidad
	 */
	private GrupoFamiliar getMayorCalidad(List<GrupoFamiliar> integrantes) {
		GrupoFamiliar mayor = null;
		if (!integrantes.isEmpty()) {
			mayor = integrantes.get(0);
			for (GrupoFamiliar integrante : integrantes) {
				if (integrante.getCalidad().intValue() < mayor.getCalidad()
						.intValue()) {
					mayor = integrante;
				}
			}
		}
		return mayor;
	}

	

	private List<GrupoFamiliar> getCanditadosCambioMedico(
			List<GrupoFamiliar> entrada) {
		List<GrupoFamiliar> salida = new ArrayList<GrupoFamiliar>();

		GrupoFamiliar integrante = this.getIntegrantePorParentesco(entrada,
				ParentescoEnum.ASEGURADO.getId());
		if (integrante != null) {
			salida.add(integrante);
		} else {
			integrante = this.getIntegrantePorParentesco(entrada,
					ParentescoEnum.PENSIONADO.getId());
			if (integrante != null)
				salida.add(integrante);
		}

		integrante = this.getIntegrantePorParentesco(entrada,
				ParentescoEnum.CONYUGE.getId());
		if (integrante != null)
			salida.add(integrante);
		return salida;
	}

	private GrupoFamiliar getIntegrantePorParentesco(
			List<GrupoFamiliar> entrada, Long idParentesco) {
		GrupoFamiliar integrante = null;

		for (GrupoFamiliar der : entrada) {
			if (der.getParentesco().getIdParentesco().equals(idParentesco))
				return der;
		}

		return integrante;
	}

	private Date buscarFechaCambioMedico(List<GrupoFamiliar> entrada) {

		for (GrupoFamiliar integrante : entrada) {
			if (integrante.getFechaCambioTurnoMedico() != null)
				return integrante.getFechaCambioTurnoMedico();
		}
		return null;
	}

	@Override
	public List<Long> guardarTramiteAsignacionUmfDependiente(GrupoFamiliar afectado, Long idSolicitud, Integer patronIMSS, Boolean registro, Boolean asignacionDomicilio)  throws DerechohabientesBusinessException, Exception{

		//Se obtiene el paretesco de las persona afectada
		Long idParentesco = afectado.getParentesco().getIdParentesco();
		//verificamos si la persona es un asegurado pensionado
		Boolean isAsegurado = idParentesco.equals(ParentescoEnum.ASEGURADO.getId()) || idParentesco.equals(ParentescoEnum.PENSIONADO.getId());
		//verificamos si es patron IMSS
		Boolean patronImss = patronIMSS == null ? false : patronIMSS.equals(1);
		//ids de las persona a las que se les asignara domicilio
		List<Long> idPersonasAsignacion = new ArrayList<Long>();
		//persona a las que se les asignara el domicilio
		List<Fisica> personasAsignacion = new ArrayList<Fisica>();
		//integrantes a las que se les asignara domicilio
		List<GrupoFamiliar> grupoAsignacionUMF = new ArrayList<GrupoFamiliar>();
		//lista de integrantes que cambiaran de clinica
		List<GrupoFamiliar> grupoCambioClinica = new ArrayList<GrupoFamiliar>();
		//ids de las personas que se cambiaran de clinica
		List<Long> idPersonasCambioClinica = null;
		//personas que se cambiaran de clinica
		List<Fisica> personasCambioClinica = null;
		//id de las persona que se afectaran por el tramite de asignacion o cambio de clinica
		List<Long> personasAfectadas = new ArrayList<Long>();
		//si no es registro agregamos al integrante afectado
		if(!registro) {
			log.debug("el tramite no es de registro por lo que se agregara al integrante actual a la lista de persona a asignar");
			idPersonasAsignacion.add(afectado.getDerechohabiente().getIdPersona());
			personasAsignacion.add(afectado.getDerechohabiente());
			personasAfectadas.add(afectado.getDerechohabiente().getIdPersona());
			grupoAsignacionUMF.add(afectado);
		}
		
		Fisica fisica = afectado.getDerechohabiente();
		//Se llena el tramite
		TramiteCorreccionDerechohabiente tramite = new TramiteCorreccionDerechohabiente();
		tramite.setEstadoTramite(new EstadoTramite());
		tramite.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());
		tramite.setDatosAsegurado(afectado.getDerechohabiente().getAsignacionNSS());
		tramite.setIdAsignacionNss(afectado.getDerechohabiente().getAsignacionNSS().getIdAsignacionNSS());
		tramite.setNombre(fisica.getNombre());
		tramite.setPrimerApellido(fisica.getPrimerApellido());
		tramite.setSegundoApellido(fisica.getSegundoApellido());
		tramite.setSexo(fisica.getSexo());
		tramite.setFechaNacimiento(fisica.getFechaNacimiento());
		tramite.setFechaNacimientoStr(fisica.getFechaNacimientoFormateada());
		tramite.setLugarNacimiento(fisica.getLugarNacimiento());
		tramite.setDomicilio(afectado.getDomicilio());
		tramite.setMedicoEnTurno(afectado.getMedicoEnTurno());
		tramite.setResultado(true);
		tramite.setTipoTramite(new TipoTramite());
		tramite.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.ASIGNACION_CONSULTORIO_TURNO_MEDICO.getCodigo());
		
		if(isAsegurado) {
			log.debug("correccionService - El integrante es asegurado se valida si tiene padres o concubina que actualizar");
			//lista de los parentescosd a afectar si es un asegurado
			List<Long> parentescos = new ArrayList<Long>();
			parentescos.add(ParentescoEnum.PADRES.getId());
			parentescos.add(ParentescoEnum.CONCUBINARIO.getId());
			//Se realiza la busqueda de las persona
			List<GrupoFamiliar> concubinasPadres = grupoFamiliarDaoLocal.findGrupoFamiliarPorParentescos(afectado.getAsignacionNSS().getIdAsignacionNSS(), parentescos, true);
			//si la lista no viene vacia o nula
			if (concubinasPadres != null && !concubinasPadres.isEmpty()) {
				log.debug("se encontraron " + concubinasPadres.size() + " en el grupo familiar");
				//inicializamos las varibales de listas de cambio de clinica
				idPersonasCambioClinica = new ArrayList<Long>();
				personasCambioClinica = new ArrayList<Fisica>();
				
				log.debug("Se encontraron " + concubinasPadres.size() + " integrantes padres y o concubinas");
				for (GrupoFamiliar integ : concubinasPadres) {
					EstadoDerechohabiente estado = integ.getEstadoDerechohabiente();
					Parentesco parentescoI = integ.getParentesco();
					
					log.debug("Se verificar si es posible hacer el cambio de clinica para el siguiente integrante:\n" +
							"- idPersona: " + integ.getDerechohabiente().getIdPersona() + "\n" +
							"- parentesco: " + parentescoI.getDescripcion() + "\n" +
							"- estadoDerechohabiente: " + estado.getDescripcion());
					
					if(estado != null && estado.getIdEstadoDerechohabiente().equals(EstadoDerechohabienteEnum.VIGENTE.getId())) {
						
						if((parentescoI.getIdParentesco().equals(ParentescoEnum.PADRES.getId()) && !patronImss)
								|| parentescoI.getIdParentesco().equals(ParentescoEnum.CONCUBINARIO.getId())) {
							
							//si el medico es nulo se agrega a la persona a la lista de personas a asignarles domicilio
							if(integ.getMedicoEnTurno() == null) {
								idPersonasAsignacion.add(integ.getDerechohabiente().getIdPersona());
								personasAsignacion.add(integ.getDerechohabiente());
								integ.setMedicoEnTurno(tramite.getMedicoEnTurno());
								integ.setDomicilio(integ.getDomicilio());
								integ.setFechaRegistroActualizacion(new Date());
								if(asignacionDomicilio) {
									integ.setFechaCambioTurnoMedico(null);
								} else {
									integ.setFechaCambioTurnoMedico(afectado.getFechaCambioTurnoMedico());
								}
								grupoAsignacionUMF.add(integ);
							} else {
								//de lo contrario si la umf no corresponder se le hace un cambio de clinica
								if(!integ.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF().equals(tramite.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF())) {
									idPersonasCambioClinica.add(integ.getDerechohabiente().getIdPersona());
									personasCambioClinica.add(integ.getDerechohabiente());
									grupoCambioClinica.add(integ);
								}
							}
						}
					}
				}
				
			}
		}
		
		tramite.setCandidatosCambioClinica(idPersonasAsignacion);
		tramite.setPersonas(personasAsignacion);
		
		if(!idPersonasAsignacion.isEmpty()) {
			tramite = (TramiteCorreccionDerechohabiente) tramiteServiceLocal.saveTramiteCorreccionDependiente(tramite, idSolicitud);
			//se guarda en dit_correccion_derechohabiente
			correccionDerechohabienteEntityLocal.saveCorreccionDerechohabiente(tramite);
			
			for(GrupoFamiliar asignado: grupoAsignacionUMF) {
				grupoFamiliarDaoLocal.updateIntegrante(asignado);
			}
		}
		
		if(isAsegurado && idPersonasCambioClinica != null && !idPersonasCambioClinica.isEmpty()) {
			log.debug("se hara el cambio de clinica para: " + idPersonasCambioClinica.size()+ " personas en el grupo familiar");
			// Tramiteque hara el cambio de clinica
			TramiteCorreccionDerechohabiente tramiteCambio = new TramiteCorreccionDerechohabiente();
			if(idPersonasCambioClinica.size() == 1) {
				tramiteCambio.setPersona(personasCambioClinica.get(0));
				tramiteCambio.setIdPersona(idPersonasCambioClinica.get(0));
			}else{
				tramiteCambio.setCandidatosCambioClinica(idPersonasCambioClinica);
				tramiteCambio.setPersonas(personasCambioClinica);
			}
			tramiteCambio.setIdAsignacionNss(afectado.getAsignacionNSS().getIdAsignacionNSS());
			tramiteCambio.setMedicoEnTurno(tramite.getMedicoEnTurno());
			tramiteCambio.setDomicilio(tramite.getDomicilio());
			tramiteCambio.setIdUmfOrigen(grupoCambioClinica.get(0).getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF());
			if(!asignacionDomicilio) {
				tramiteCambio.setFechaCambioMedico(afectado.getFechaCambioTurnoMedico());
			} else {
				tramiteCambio.setFechaCambioMedico(null);
			}
			tramiteCambio.setResultado(true);

			this.guardarTramiteCambioClinicaDependiente(tramiteCambio, afectado.getAsignacionNSS(), afectado.getAsignacionNSS(),patronImss, afectado.getFechaCambioTurnoMedico(),
							idSolicitud,null, asignacionDomicilio);
		}
		
		return personasAfectadas;
	}
	
	@Override
	public TramiteCorreccionDerechohabiente guardarTramiteCorreccionDatosDerechohabienteDependiente(
			GrupoFamiliar grupo, Fisica fisica, Long idSolicitud)
			throws DerechohabientesBusinessException {
		TramiteCorreccionDerechohabiente tramite = new TramiteCorreccionDerechohabiente();
		tramite.setPersona(grupo.getDerechohabiente());
		tramite.setIdPersona(grupo.getDerechohabiente().getIdPersona());
		tramite.setDatosAsegurado(grupo.getDerechohabiente().getAsignacionNSS());
		tramite.setIdAsignacionNss(grupo.getDerechohabiente()
				.getAsignacionNSS().getIdAsignacionNSS());
		tramite.setNombre(fisica.getNombre());
		tramite.setPrimerApellido(fisica.getPrimerApellido());
		tramite.setSegundoApellido(fisica.getSegundoApellido());
		tramite.setSexo(fisica.getSexo());
		tramite.setFechaNacimiento(fisica.getFechaNacimiento());
		tramite.setFechaNacimientoStr(fisica.getFechaNacimientoFormateada());
		tramite.setLugarNacimiento(fisica.getLugarNacimiento());

		tramite.setTipoTramite(new TipoTramite());
		tramite.getTipoTramite().setIdTipoTramite(
				TipoTramiteEnum.MODIFICACION_DE_DERECHOHABIENTE.getCodigo());

		tramite = (TramiteCorreccionDerechohabiente) tramiteServiceLocal
				.saveTramiteCorreccionDependiente(tramite, idSolicitud);

		grupo.getDerechohabiente().setNombre(fisica.getNombre());
		grupo.getDerechohabiente()
				.setPrimerApellido(fisica.getPrimerApellido());
		grupo.getDerechohabiente().setSegundoApellido(
				fisica.getSegundoApellido());
		grupo.getDerechohabiente().setSexo(fisica.getSexo());
		grupo.getDerechohabiente().setLugarNacimiento(
				fisica.getLugarNacimiento());
		grupo.getDerechohabiente().setFechaNacimiento(
				fisica.getFechaNacimiento());

		try {
			finalizaSolicitudServiceLocal
					.finalizaCorreccionDatosDerechohabienteInternet(grupo);
		} catch (Exception e) {
			log.error(
					"Ocurrio un error al mandar a afectar la correccion en el ws",
					e);
		}
		return tramite;
	}
	
	@Override
	/**
	 * Metodo para guardar un tramite dependiente de cambio de clinica
	 * @author mario.teran
	 * @param correccion
	 */
	public List<Long> guardarTramiteCambioClinicaDependiente(
			TramiteCorreccionDerechohabiente correccion, Fisica usuario,
			AsignacionNSS nss, Boolean patronImss, Date fechaCambioMTC,
			Long idSolicitud, GrupoFamiliar afectado, Boolean asignacionDomicilio)
			throws DerechohabientesBusinessException {

		List<Long> personasCambiadas = null;
		Solicitud solicitudCorr = new Solicitud(idSolicitud);
		solicitudCorr.setTramites(new ArrayList<Tramite>());

		if (correccion.getCandidatosCambioClinica() == null && correccion.getPersona() == null && correccion.getIdPersona() != null) {
			correccion.setPersona(new Fisica());
			correccion.getPersona().setIdPersona(correccion.getIdPersona());
		}
		correccion.setTipoTramite(new TipoTramite());
		correccion.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.CAMBIO_CLINICA.getCodigo());
		correccion = (TramiteCorreccionDerechohabiente) tramiteServiceLocal.saveTramiteCorreccionDependiente(correccion, idSolicitud);

		solicitudCorr.getTramites().add(correccion);

		solicitudCorr = this.guardarFinalizadoTramiteCambioClinica(solicitudCorr, usuario, patronImss, nss, fechaCambioMTC,afectado,asignacionDomicilio);
		if (correccion.getPersonas() != null)
			personasCambiadas = new ArrayList<Long>();

		for (Fisica personaC : correccion.getPersonas())
			personasCambiadas.add(personaC.getIdPersona());

		return personasCambiadas;
	}

	@Override
	public void guardarTramiteCambioMedicoDependiente(
			TramiteCorreccionDerechohabiente correccion, Fisica usuario,
			Long idSolicitud, AsignacionNSS nss)
			throws DerechohabientesBusinessException {
		Solicitud solicitudCorr = new Solicitud(idSolicitud);
		solicitudCorr.setTramites(new ArrayList<Tramite>());

		correccion.setTipoTramite(new TipoTramite());
		correccion.getTipoTramite().setIdTipoTramite(
				TipoTramiteEnum.CAMBIO_CONSULTORIO_TURNO.getCodigo());
		correccion = (TramiteCorreccionDerechohabiente) tramiteServiceLocal.saveTramiteCorreccionDependiente(correccion, idSolicitud);

		solicitudCorr.getTramites().add(correccion);

		this.guardarValCambioMedicoInterno(solicitudCorr, usuario, false, nss);
	}

	@Override
	public List<Long> guardarTramiteCircunscripcionDependiente(
			TramiteCircunscripcionForanea circunscripcion, Usuario usuario,
			AsignacionNSS nss, Long idSolicitud, GrupoFamiliar afectado)
			throws DerechohabientesBusinessException {

		log.debug("El id de la solicitud a la que se le agregara un tramite es: "
				+ idSolicitud);
		List<Long> personasAfectadas = new ArrayList<Long>();
		circunscripcion.setTipoTramite(new TipoTramite());
		circunscripcion.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.AUTORIZACION_SERVICIOS_CIRCUNSCRIPCION_FORANEA.getCodigo());
		circunscripcion = (TramiteCircunscripcionForanea) tramiteServiceLocal.saveTramiteCorreccionDependiente(circunscripcion, idSolicitud);
		circunscripcion.setFecInicioCircunscripcion(new Date());
		circunscripcion.setIndCircunscripcionActiva(1);
		try {
			circunscripcionEntityLocal.saveCircunscripcionForanea(circunscripcion);
		} catch (Exception e) {
			// Si ocurrio algun error mandamos una excepcion indicando que no se
			// pudo guardar la solicitud
			log.error("Error al guardar el tramite", e);
			throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_GUARDADO_SOLCITUD, e.getCause().getMessage());
		}

		log.debug("Este es el id del nuevo tramite de circunscripcion: " + circunscripcion.getTramiteId());
		// Solicitud solCo =
		// this.obtenerSolicitud(circunscripcion.getTramiteId());

		circunscripcion = this.guardarValCircunscripcionInterno(idSolicitud,usuario, nss, circunscripcion, afectado);
		if (circunscripcion.getPersonas() != null) {
			for (Fisica afectada : circunscripcion.getPersonas())
				personasAfectadas.add(afectada.getIdPersona());
		}

		try {
			
			solicitudServiceLocal.marcarAtendidaSolictud(idSolicitud, "",usuario.getFisica());
		} catch (Exception e) {
			log.error("Error al actualizar la solicitud", e);
		}
		return personasAfectadas;

	}

	/**
	 * Metodo que guarda un tramite de cambio de medico consultorio a partir de
	 * un registro de derechohabiente recibe el integrante que se esta
	 * registrando
	 */
	@Override
	public List<Long> guardarTramiteCambioMedicoDependiente(
			GrupoFamiliar nuevosDatos, Long idSolicitud, Boolean asignacionDomicilio)
			throws DerechohabientesBusinessException {

		List<GrupoFamiliar> grupo = null;
		// obtenemos a todos los integrantes que se encuentren registrados en la
		// umf que se va a registrar
		try {
			grupo = grupoFamiliarDaoLocal.findIntegrantesEnUmf(nuevosDatos
					.getAsignacionNSS().getIdAsignacionNSS(), nuevosDatos
					.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF(),
					nuevosDatos.getDerechohabiente().getIdPersona());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// verificamos que haya registrado al menos un integrante del grupo en
		// esa umf
		if (grupo != null && !grupo.isEmpty()) {

			Boolean parentescoPermitido = false;
			// Verificamos si hubo cambio de medico en la misma umf
			Boolean cambioMedico = !nuevosDatos.getMedicoEnTurno().getIdMedicoContultorioTurno().equals(grupo.get(0).getMedicoEnTurno()
							.getIdMedicoContultorioTurno());
			// verificamos si el parentesco es asegurado o conyuge, ya que son
			// los unicos parentescos que tienen permitidos el cambio
			parentescoPermitido = nuevosDatos.getParentesco().getIdParentesco().equals(ParentescoEnum.CONYUGE.getId())
					|| nuevosDatos.getParentesco().getIdParentesco().equals(ParentescoEnum.ASEGURADO.getId())
					|| nuevosDatos.getParentesco().getIdParentesco().equals(ParentescoEnum.PENSIONADO.getId());
			// fecha del dia del tramite
			Date fechaHoy = new Date();
			// si el parentesco es permitido
			if (parentescoPermitido) {
				// si hay cambio de medico
				if (cambioMedico) {
					// Creamos el tramite de cambio de medico consultorio y
					// turno
					TramiteCorreccionDerechohabiente correccion = new TramiteCorreccionDerechohabiente();
					correccion.setMedicoEnTurno(nuevosDatos.getMedicoEnTurno());
					// Seteamos el tipo de tramite que se creara
					correccion.setTipoTramite(new TipoTramite());
					correccion.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.CAMBIO_CONSULTORIO_TURNO.getCodigo());
					correccion.setResultado(true);
					correccion.setRazonResultado(new RazonResultado());
					correccion.getRazonResultado().setIdRazonResultado(RazonResultadoEnum.NORMAL.getId());
					correccion.setEstadoTramite(new EstadoTramite());
					correccion.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());
					correccion.setFechaPresentacion(new Date());
					
					if(nuevosDatos.getDomicilio() != null) {
						correccion.setDomicilio(nuevosDatos.getDomicilio());
					}
				
					// agregamos a las personas relacionadas al tramite
					correccion.setPersonas(new ArrayList<Fisica>());
					for (GrupoFamiliar integrante : grupo) {
						try {
							if(!asignacionDomicilio) {
								// seteamos la fecha de cambio de medico
								integrante.setFechaCambioTurnoMedico(nuevosDatos.getFechaCambioTurnoMedico());
							} else {
								integrante.setFechaCambioTurnoMedico(null);
							}
							// seteamos los nuevos datos de adscripcion
							integrante.setMedicoEnTurno(nuevosDatos.getMedicoEnTurno());
							// Seteamos la fecha de actualizacion del integrante
							integrante.setFechaRegistroActualizacion(fechaHoy);
							
							if(nuevosDatos.getDomicilio() != null && integrante.getDomicilio() == null) {
								integrante.setDomicilio(nuevosDatos.getDomicilio());
							}
							// agregamos a la persona al tramite
							correccion.getPersonas().add(integrante.getDerechohabiente());
							
							// actualizamos al integrante en bd
							grupoFamiliarDaoLocal.updateIntegrante(integrante);
						} catch (Exception e) {
							e.printStackTrace();
							DerechohabientesBusinessException
									.throwException("Error al crear tramite de cambio de medico");
						}
					}

					// agregamos tambien a la persona que ocasiono el cambio de
					// medico, consultorio y turno
					correccion.getPersonas().add(nuevosDatos.getDerechohabiente());
					correccion.setFechaConclusion(new Date());
					// guardamos el tramite de cambio de clinica en BDTU
					correccion = (TramiteCorreccionDerechohabiente) tramiteServiceLocal.saveTramiteCorreccionDependiente(correccion,idSolicitud);
				} else {
					// De lo contrario si no se modifico el medico solo
					// actualizaremos la fecha de cambio de medico
					for (GrupoFamiliar integrante : grupo) {
						try {
							if(!asignacionDomicilio) {
								// seteamos la fecha de cambio de medico
								integrante.setFechaCambioTurnoMedico(nuevosDatos.getFechaCambioTurnoMedico());
							} else {
								integrante.setFechaCambioTurnoMedico(null);
							}
							
							if(nuevosDatos.getDomicilio() != null && integrante.getDomicilio() == null) {
								integrante.setDomicilio(nuevosDatos.getDomicilio());
							}
							// seteamos la fecha de actualizacion al dia de hoy
							integrante.setFechaRegistroActualizacion(fechaHoy);
							// actualizamos al integrante
							grupoFamiliarDaoLocal.updateIntegrante(integrante);
						} catch (Exception e) {
							e.printStackTrace();
							DerechohabientesBusinessException.throwException("Error al crear tramite de cambio de medico");
						}
					}
				}
			}
		}
		return null;
	}

	private Solicitud obtenerSolicitud(Long idTramite) {
		Solicitud solCo = null;

		try {
			solCo = solicitudBusinessRemote.consultarPorIdTramite(idTramite);
		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
		}

		return solCo;
	}

	private Solicitud guardarFinalizadoTramiteCambioClinica(
			Solicitud solicitudCorreccion, Fisica usuarioPersona,
			Boolean patronImss, AsignacionNSS nss, Date fechaCM,
			GrupoFamiliar integranteAfectado, Boolean asignacionDomicilio)
			throws DerechohabientesBusinessException {
		// Obtenemos al integrante del grupo familiar
		GrupoFamiliar afectado = null;
		List<GrupoFamiliar> afectados = null;
		Date fechaCambioMedico = null;
		List<GrupoFamiliar> integrantesEnUmf = new ArrayList<GrupoFamiliar>();
		Boolean cambioMedicoConyuge = false;
		Boolean parentescoPermitido = false;
		Long parentesco = null;
		Long idPersonaExclusion = integranteAfectado != null ? integranteAfectado.getDerechohabiente().getIdPersona() : null;

		TramiteCorreccionDerechohabiente correccion = this.getTramiteCorreccion(solicitudCorreccion, TipoTramiteEnum.CAMBIO_CLINICA.getCodigo().longValue());

		if (correccion == null) {
			DerechohabientesBusinessException.throwException("Inconsistencia en los datos");
		}

		correccion.setNss(nss.getNssStr());
		correccion.setIdAsignacionNss(nss.getIdAsignacionNSS());
		correccion.setPersonas(new ArrayList<Fisica>());
		Domicilio domicilioAnterior = null;
		MedicoEnTurno medicoAnterior = null;
		try {
			// Si la lista de candidatos es nula el tramite se aplico a una sola
			// persona y se obtiene el derechohabiente
			if (correccion.getCandidatosCambioClinica() == null) {
				

				if (integranteAfectado != null) {
					afectado = integranteAfectado;
				} else {
					afectado = grupoFamiliarDaoLocal.getIntegranteSinVigencia(nss.getIdAsignacionNSS(),correccion.getIdPersona());
				}
				
				correccion.setPersona(afectado.getDerechohabiente());

				parentesco = afectado.getParentesco().getIdParentesco();
				parentescoPermitido = parentesco.equals(ParentescoEnum.ASEGURADO.getId()) || parentesco.equals(ParentescoEnum.PENSIONADO.getId())
						|| parentesco.equals(ParentescoEnum.CONYUGE.getId());
				correccion.getPersonas().add(afectado.getDerechohabiente());
			}
			// De lo contrario se buscan a todas las personas afectadas por el
			// tramite
			else {
				afectados = new ArrayList<GrupoFamiliar>();

				for (Long idPersona : correccion.getCandidatosCambioClinica()) {
					//TODO mandar a llamar unicamente BDTU
					afectado = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(nss.getIdAsignacionNSS(), idPersona);
					afectados.add(afectado);
					correccion.getPersonas().add((afectado.getDerechohabiente()));
				}

				afectado = this.getMayorCalidad(afectados);
				correccion.setPersona(afectado.getDerechohabiente());
				parentesco = afectado.getParentesco().getIdParentesco();
				parentescoPermitido = parentesco.equals(ParentescoEnum.ASEGURADO.getId()) || parentesco.equals(ParentescoEnum.PENSIONADO.getId())
						|| parentesco.equals(ParentescoEnum.CONYUGE.getId());
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error al recuperar al integrante afectado", e);
			DerechohabientesBusinessException.throwException("error.busqueda.integrante", e.getCause().getMessage());
		}

		//TODO sacar metodo de calculo de fecha
		if (fechaCM == null && !asignacionDomicilio) {
			log.debug("Se calcula la fecha");
			try {
				List<Long> estados = new ArrayList<Long>();
				estados.add(EstadoDerechohabienteEnum.VIGENTE.getId());
				estados.add(EstadoDerechohabienteEnum.CONSERVACION_DERECHOS.getId());
				estados.add(EstadoDerechohabienteEnum.CON_DERECHO.getId());
				
				integrantesEnUmf = grupoFamiliarDaoLocal.findIntegrantesPorUmfEstado(nss.getIdAsignacionNSS(),
								correccion.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF(),estados, idPersonaExclusion, null);

				// Verificamos si el o la conyuge cambiaron de medico
				if (!integrantesEnUmf.isEmpty())
					cambioMedicoConyuge = !correccion.getMedicoEnTurno().getIdMedicoContultorioTurno().equals(integrantesEnUmf.get(0).getMedicoEnTurno()
									.getIdMedicoContultorioTurno());

				if (parentescoPermitido) {

					if (!integrantesEnUmf.isEmpty()) {
						// Verificamos si existe alguna fecha en alguno de los
						// integrantes del grupo familiar
						Date fechaEnUmf = this.buscarFechaCambioMedico(integrantesEnUmf);
						// Obtenemos los dias que han pasado desde esa fecha en
						// caso de que todos hayan sido null regresara null
						Long dias = TramiteUtil.diasEntreFechayHoy(fechaEnUmf);
						// Si los dias son diferente de null
						if (dias != null) {
							// Checamos si han pasado mas de 365 dias
							if (dias > 365) {
								// Como la esposa es la que se cambia ponemos la
								// fecha de cambio al dia de hoy
								fechaCambioMedico = new Date();
							} else { // de lo contrario si no han pasado mas de
										// 365 dias
								// Checamos que la fecha no sea nula del ultimo
								// cambio de medico y en caso
								// de ser asi ponemos la fecha de hoy a la
								// esposa o esposo
								fechaCambioMedico = fechaEnUmf == null ? new Date() : fechaEnUmf;
							}
						} else {
							fechaCambioMedico = new Date();
						}
					} else {
						fechaCambioMedico = new Date();
					}

				} else {
					if (!integrantesEnUmf.isEmpty()) {
						fechaCambioMedico = this.buscarFechaCambioMedico(integrantesEnUmf);
						fechaCambioMedico = fechaCambioMedico == null ? new Date() : fechaCambioMedico;
					} else {
						fechaCambioMedico = new Date();
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				log.error("Error al recuperar al integrante afectado", e1);
				DerechohabientesBusinessException.throwException("error.busqueda.integrante", e1.getCause().getMessage());
			}
		} else {
			fechaCambioMedico = fechaCM;
			log.debug("la fecha ya esta definida y es " + fechaCambioMedico);
		}

		// Verificamos que si haya candidato o candidatos
		if (correccion.getCandidatosCambioClinica() == null && afectado == null)
			DerechohabientesBusinessException.throwException("exception.RNGD0003","No se encontro al integrante del grupo familiar");
		if (correccion.getCandidatosCambioClinica() != null && afectados == null)
			DerechohabientesBusinessException.throwException("exception.RNGD0003","No se encontro al integrante del grupo familiar");

		domicilioAnterior = afectado.getDomicilio();
		medicoAnterior = afectado.getMedicoEnTurno();
		// Checamos si el parentesco es asegurado o pensionado para tambien en
		// caso de haber cambiado de domicilio cabiar a los padres
		if (parentesco.equals(ParentescoEnum.ASEGURADO.getId())	|| parentesco.equals(ParentescoEnum.PENSIONADO.getId())) {

			Domicilio domicilioNuevo = null;
			// Comparamos si hubo cambio de domicilio
			if (correccion.getDomicilio().getClave() == null) {
				// Si el domicilio cambio registramos el nuevo domicilio
				domicilioNuevo = this.guardarDomicilioNuevo(correccion.getDomicilio());
				// y se lo agregamos a la correccion
				correccion.setDomicilio(domicilioNuevo);
			} 

			List<GrupoFamiliar> candidatos = null;
			List<Long> idPersonas = new ArrayList<Long>();
			List<Tramite> tramitesAbiertos = new ArrayList<Tramite>();
			try {
				List<Long> estados = new ArrayList<Long>();
				estados.add(EstadoDerechohabienteEnum.BAJA.getId());
				// candidatos =
				// grupoFamiliarDaoLocal.findGrupoFamiliarPorEstado(nss.getIdAsignacionNSS(),
				// EstadoDerechohabienteEnum.BAJA.getId());
				candidatos = grupoFamiliarDaoLocal.findIntegrantesPorUmfEstado(nss.getIdAsignacionNSS(), correccion.getIdUmfOrigen(),
						estados, idPersonaExclusion, null);
			} catch (Exception e) {
				log.error(
						"No fue posibles recuperar a las personas en baja que estan en la misma clinica que el asegurado",
						e);
			}

			if (!candidatos.isEmpty()) {
				for (GrupoFamiliar integranteEnBaja : candidatos) {
					idPersonas.add(integranteEnBaja.getDerechohabiente().getIdPersona());
				}

				//TODO buscar y cancelar
				try {
					tramitesAbiertos = solicitudTramiteBusinessRemote.getTramitesAbierto(idPersonas, nss.getIdPersona());
				} catch (Exception e1) {
					log.error(
							"Error al buscar los tramites abiertos de las personas en baja",
							e1);
				}

				if (tramitesAbiertos != null) {
					for (Tramite tramiteACerrar : tramitesAbiertos) {
						Solicitud solCo = this.obtenerSolicitud(tramiteACerrar
								.getTramiteId());

						solicitudServiceLocal.cambiarEdoSolicitud(
										solCo.getSolicitudId(),EstadoSolicitudEnum.CANCELADA,EstadoTramiteEnum.CERRADO,false,
										mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultadoEnum.SOLICITUD_CANCELADA,"Solicitud Cancelada por cambio de clinica del asegurado",
										usuarioPersona);
					}
				}
			}

			if (correccion.getCandidatosCambioClinica() == null) {

				// guardamos el domicilio anterior

				// Colocamos el nuevo domicilio al afectado
				afectado.setDomicilio(correccion.getDomicilio());
				// Colocamos la fecha de actualizacion del derechohabiente enel
				// grupo familiar
				afectado.setFechaRegistroActualizacion(new Date());
				afectado.setMedicoEnTurno(correccion.getMedicoEnTurno());
				if(asignacionDomicilio){
					afectado.setFechaCambioTurnoMedico(null);
				} else {
					afectado.setFechaCambioTurnoMedico(fechaCambioMedico);
				}
				// y guardamos los nuevos datos del domicilio del
				// derechohabiente
				try {
					grupoFamiliarDaoLocal.updateIntegrante(afectado);

				} catch (Exception e) {
					// Si ocurrio algun error mandamos una excepcion indicando
					// que no se
					// pudo guardar la solicitud
					log.error("No se pudo actualizar el integrante", e);
					throw new DerechohabientesBusinessException(
							"error.actualizar.integrante", e.getCause()
									.getMessage());
				}

				// para cada persona en baja creamos un tramite de cambio de
				// clinica
				if (candidatos != null) {
					// Para cada padre y concubina
					for (GrupoFamiliar integrante : candidatos) {
						TramiteCorreccionDerechohabiente correccionA = new TramiteCorreccionDerechohabiente();
						correccionA = this.copiarCorreccion(correccion);
						correccionA.setTramiteId(correccion.getTramiteId());
						correccionA.setIdPersona(integrante.getDerechohabiente().getIdPersona());
						correccionA.setPersona(new Fisica());
						correccionA.getPersona().setIdPersona(integrante.getDerechohabiente().getIdPersona());
						correccionA.setCandidatosCambioClinica(null);
						correccionA.setPersonas(null);
						this.guardarTramiteCambioClinicaDependiente(correccionA, usuarioPersona, nss, patronImss,
								fechaCambioMedico,solicitudCorreccion.getSolicitudId(), null,asignacionDomicilio);
					}
				}
			} else {

				// Para cada padre y concubina
				for (GrupoFamiliar integrante : afectados) {
					// le colocamos el nuevo domicilio
					integrante.setDomicilio(correccion.getDomicilio());
					// y la fecha de su actualizacion
					integrante.setFechaRegistroActualizacion(new Date());
					// Establecemos la nueva umf
					integrante.setMedicoEnTurno(correccion.getMedicoEnTurno());
					// Ponemos la fecha de actualizacion del medico si es que ya
					// hay un integrante de lo contrario ponemos la de hoy
					if(asignacionDomicilio) {
						integrante.setFechaCambioTurnoMedico(null);
					} else {
						integrante.setFechaCambioTurnoMedico(fechaCambioMedico);
					}
					
					try {
						grupoFamiliarDaoLocal.updateIntegrante(integrante);
					} catch (Exception e) {
						// Si ocurrio algun error mandamos una excepcion
						// indicando que no se
						// pudo guardar la solicitud
						log.error("No se pudo actualizar el integrante", e);
						throw new DerechohabientesBusinessException("error.actualizar.integrante", e.getCause().getMessage());
					}
				}

				if (candidatos != null) {
					// Para cada padre y concubina
					for (GrupoFamiliar integrante : candidatos) {
						TramiteCorreccionDerechohabiente correccionA = new TramiteCorreccionDerechohabiente();
						correccionA = this.copiarCorreccion(correccion);
						correccionA.setTramiteId(correccion.getTramiteId());
						correccionA.setIdPersona(integrante.getDerechohabiente().getIdPersona());
						correccionA.setPersona(new Fisica());
						correccionA.getPersona().setIdPersona(integrante.getDerechohabiente().getIdPersona());
						correccionA.setCandidatosCambioClinica(null);
						correccionA.setPersonas(null);
						this.guardarTramiteCambioClinicaDependiente(
								correccionA, usuarioPersona, nss, patronImss,
								fechaCambioMedico,
								solicitudCorreccion.getSolicitudId(), null,asignacionDomicilio);
					}
				}

			}
		}// Si el afectado no es el asegurado actualizamos unicamente el
			// domicilio en caso de ser necesario ya que los datos peronales
			// en este momento ya se actualizaron
		else {
			Domicilio domicilioNuevo = null;
			// Comparamos si hubo cambio de domicilio
			if (correccion.getDomicilio().getClave() == null) {
				// Si el domicilio cambio registramos el nuevo domicilio
				domicilioNuevo = this.guardarDomicilioNuevo(correccion.getDomicilio());
				// y se lo agregamos a la correccion
				correccion.setDomicilio(domicilioNuevo);
			}

			if (correccion.getCandidatosCambioClinica() == null) {
				// Colocamos el nuevo domicilio al afectado
				afectado.setDomicilio(correccion.getDomicilio());
				// Colocamos la fecha de actualizacion del derechohabiente enel
				// grupo familiar
				afectado.setFechaRegistroActualizacion(new Date());
				afectado.setMedicoEnTurno(correccion.getMedicoEnTurno());
				if(asignacionDomicilio) {
					afectado.setFechaCambioTurnoMedico(null);
				}else {
					// Ponemos la fecha de actualizacion del medico si es que ya hay
					// un integrante de lo contrario ponemos la de hoy
					afectado.setFechaCambioTurnoMedico(fechaCambioMedico);
				}
				// y guardamos los nuevos datos del domicilio del
				// derechohabiente
				try {
					grupoFamiliarDaoLocal.updateIntegrante(afectado);
				} catch (Exception e) {
					// Si ocurrio algun error mandamos una excepcion indicando
					// que no se
					// pudo guardar la solicitud
					log.error("No se pudo actualizar el integrante", e);
					throw new DerechohabientesBusinessException(
							"error.actualizar.integrante", e.getCause()
									.getMessage());
				}
			} else {
				for (GrupoFamiliar integrante : afectados) {
					// Colocamos el nuevo domicilio al afectado
					integrante.setDomicilio(correccion.getDomicilio());
					// Colocamos la fecha de actualizacion del derechohabiente
					// enel grupo familiar
					integrante.setFechaRegistroActualizacion(new Date());
					integrante.setMedicoEnTurno(correccion.getMedicoEnTurno());
					// Ponemos la fecha de actualizacion del medico si es que ya
					// hay un integrante de lo contrario ponemos la de hoy
					if(asignacionDomicilio) {
						integrante.setFechaCambioTurnoMedico(null);
					} else {
						integrante.setFechaCambioTurnoMedico(fechaCambioMedico);
					}
					// y guardamos los nuevos datos del domicilio del
					// derechohabiente
					try {
						grupoFamiliarDaoLocal.updateIntegrante(integrante);
					} catch (Exception e) {
						// Si ocurrio algun error mandamos una excepcion
						// indicando que no se
						// pudo guardar la solicitud
						log.error("No se pudo actualizar el integrante", e);
						throw new DerechohabientesBusinessException(
								"error.actualizar.integrante", e.getCause()
										.getMessage());
					}
				}
			}
		}

		// Vserificamos si la
		if (!integrantesEnUmf.isEmpty() && parentescoPermitido && cambioMedicoConyuge) {
			// TipoTramiteEnum tipoTramite =
			// parentesco.equals(ParentescoEnum.CONYUGE.getId()) ?
			// TipoTramiteEnum.CAMBIO_MEDICO_CAMBIO_CLINICA_CONYUGE :
			// TipoTramiteEnum.CAMBIO_MEDICO_CAMBIO_CLINICA_ASEGURADO;
			for (GrupoFamiliar derechohabien : integrantesEnUmf) {
				correccion.getPersonas()
						.add(derechohabien.getDerechohabiente());
				TramiteCorreccionDerechohabiente correccionA = new TramiteCorreccionDerechohabiente();
				correccionA = this.copiarCorreccion(correccion);
				correccionA.setIdPersona(derechohabien.getDerechohabiente().getIdPersona());
				correccionA.setPersona(new Fisica());
				correccionA.getPersona().setIdPersona(derechohabien.getDerechohabiente().getIdPersona());
				correccionA.setCandidatosCambioClinica(null);
				correccionA.setPersonas(null);
				this.guardarTramiteCambioMedicoDependiente(correccionA,
						usuarioPersona, solicitudCorreccion.getSolicitudId(),
						nss);
			}
		}
		
		int index = 0;
		for(Tramite tramite: solicitudCorreccion.getTramites()) {
			if(tramite.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.CAMBIO_CLINICA.getCodigo())) {
				break;
			}
			index++;
		}
		correccion.setEstadoTramite(new EstadoTramite());
		correccion.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.INICIADO.getCodigo());
		solicitudCorreccion.getTramites().set(index, correccion);
		
		try {
			solicitudBusinessRemote.actualizarTramites(solicitudCorreccion);
			// cambiamos el domicilio que habia en correccion
			correccion.setDomicilio(domicilioAnterior);
			correccion.setMedicoEnTurno(medicoAnterior);
			correccionDerechohabienteEntityLocal.saveCorreccionDerechohabiente(correccion);
		} catch (Exception e) {
			// Si ocurrio algun error mandamos una excepcion indicando que no se
			// pudo guardar la solicitud
			e.printStackTrace();
			log.error("No fue posible guardar la solicitud", e);
			throw new DerechohabientesBusinessException(
					ExceptionMessages.ERROR_GUARDADO_SOLCITUD, e.getCause()
							.getMessage());
		}

		return solicitudCorreccion;
	}

	/**
	 * Metodo para guardar la validacion del tramite de cambio de medico
	 * recibiendo los siguientes parametros:
	 * 
	 * @param correccion
	 *            de tipo CorreccionDatosDerechohabiente que contiene la
	 *            informacion a guardar
	 * @return CorreccionDatoDerechohabiente objeto que contendra la informacion
	 *         ya guardada
	 * @throws DerechohabientesBusinessException
	 */
	private TramiteCorreccionDerechohabiente guardarValCambioMedicoInterno(
			Solicitud solicitudCorreccion, Fisica usuarioPersona,
			Boolean checarCambios, AsignacionNSS nss)
			throws DerechohabientesBusinessException {

		// Obtenemos al integrante del grupo familiar
		GrupoFamiliar afectado = null;
		List<GrupoFamiliar> grupoFamiliar = null;
		List<Long> idsPersonasCambiadas = new ArrayList<Long>();
		List<Fisica> personasCambiadas = new ArrayList<Fisica>();

		TramiteCorreccionDerechohabiente correccion = null;

		correccion = this.getTramiteCorreccion(solicitudCorreccion,null);

		if (correccion == null) {
			DerechohabientesBusinessException.throwException("No se encontro ningun tramite de correccion en la solicitud");
		}

		try {
			afectado = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(nss.getIdAsignacionNSS(), correccion.getIdPersona());
		} catch (Exception e1) {
			log.error("Error al recuperar al integrante afectado", e1);
			DerechohabientesBusinessException.throwException("error.busqueda.integrante", e1.getCause().getMessage());
		}

		if (afectado == null){
			DerechohabientesBusinessException.throwException("exception.RNGD0003","No se encontro al integrante del grupo familiar");
		}

		Long parentesco = afectado.getParentesco().getIdParentesco();
		log.debug("El parentesco del afectado es: " + parentesco);

		Boolean cambioBeneficiarios = (parentesco.equals(ParentescoEnum.ASEGURADO.getId()) || parentesco.equals(ParentescoEnum.PENSIONADO.getId()) || parentesco
				.equals(ParentescoEnum.CONYUGE.getId())) && checarCambios;

		log.debug("Se modficaran otros beneficiarios: " + cambioBeneficiarios);
		MedicoEnTurno medicoAnterior = afectado.getMedicoEnTurno();
		if (cambioBeneficiarios) {

			afectado.setMedicoEnTurno(correccion.getMedicoEnTurno());
			afectado.setFechaRegistroActualizacion(new Date());
			afectado.setFechaCambioTurnoMedico(new Date());
			
			try {
				grupoFamiliar = grupoFamiliarDaoLocal.findGrupoFamiliarByNss(nss.getIdAsignacionNSS());
				grupoFamiliarDaoLocal.updateIntegrante(afectado);
			} catch (Exception e) {
				// Si ocurrio algun error mandamos una excepcion indicando que
				// no se
				// pudo guardar la solicitud
				log.error("No se pudo actualizar el integrante", e);
				throw new DerechohabientesBusinessException("error.actualizar.integrante", e.getCause().getMessage());
			}

			// Filtramos a todos los derechohabientes del grupo familiar que se
			// encuentren en la misma umf
			grupoFamiliar = this.filtrarPorUmf(grupoFamiliar, afectado.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF());
			grupoFamiliar = this.quitarIntegrante(grupoFamiliar, afectado);
			
			if(!grupoFamiliar.isEmpty()) {
				idsPersonasCambiadas.add(afectado.getDerechohabiente().getIdPersona());
				personasCambiadas.add(afectado.getDerechohabiente());
			}

			// Para cada integrante que se encuentre en la misma umf que el
			// asegurado le cambiamos el medico
			for (GrupoFamiliar integrante : grupoFamiliar) {
				idsPersonasCambiadas.add(integrante.getDerechohabiente().getIdPersona());
				personasCambiadas.add(integrante.getDerechohabiente());
				
				TramiteCorreccionDerechohabiente correccionA = new TramiteCorreccionDerechohabiente();
				correccionA = this.copiarCorreccion(correccion);
				correccionA.setIdPersona(integrante.getDerechohabiente().getIdPersona());
				correccionA.setPersona(integrante.getDerechohabiente());
				correccionA.setCandidatosCambioClinica(null);
				correccionA.setTramiteId(correccion.getTramiteId());
				
				this.guardarTramiteCambioMedicoDependiente(correccionA,usuarioPersona, solicitudCorreccion.getSolicitudId(),nss);
			}
		} else {
			afectado.setMedicoEnTurno(correccion.getMedicoEnTurno());
			afectado.setFechaRegistroActualizacion(new Date());
			afectado.setFechaCambioTurnoMedico(new Date());
			
			try {
				grupoFamiliarDaoLocal.updateIntegrante(afectado);
			} catch (Exception e) {
				e.printStackTrace();
				// Si ocurrio algun error mandamos una excepcion indicando que
				// no se
				// pudo guardar la solicitud
				log.error("No se pudo actualizar el integrante", e);
				throw new DerechohabientesBusinessException("error.actualizar.integrante", e.getCause().getMessage());
			}
		}

		//Se guardan en la correccion el medico y domicilio anterior
		correccion.setMedicoEnTurno(medicoAnterior);
		correccion.setDomicilio(afectado.getDomicilio());
		
		
		try {
			correccionDerechohabienteEntityLocal.saveCorreccionDerechohabiente(correccion);
			
			// solo si se cambio a mas de una persona actualizamos el xml de la solicitud
			if(idsPersonasCambiadas.size() > 0) {
				//Actualizamos el XML en la BDTU
				solicitudCorreccion = solicitudBusinessRemote.consultar(solicitudCorreccion);
				correccion = this.getTramiteCorreccion(solicitudCorreccion, null);
			
				correccion.setPersona(null);
				correccion.setIdPersona(null);
				correccion.setCandidatosCambioClinica(idsPersonasCambiadas);
				correccion.setPersonas(personasCambiadas);
				
				solicitudCorreccion.getTramites().set(0, correccion);
				
				solicitudBusinessRemote.actualizarTramites(solicitudCorreccion);
				
			}
			// ------------------------------------------------------------------------------
			// FIRMA ELECTRONICA
			//
			// Se necesita para firmar los documentos resultantes
			// Si no existe la firma la crea; necesita el id de solicitud
			// ------------------------------------------------------------------------------
				log.debug(" ========== CAMBIO DE TURNO, MEDICO Y CONSULTORIO FIRMA =====================");
			
				// ----------------------------------------------------------------------------
				// La firma necesita una descripci�n de tr�mite
				// ----------------------------------------------------------------------------
				TipoTramite tipoTramiteModel = catalogosDAO.getTipoTramite(correccion.getTipoTramite().getIdTipoTramite().longValue());
				tramiteDocumentoService.generaFirmaElectronica(afectado.getAsignacionNSS(), solicitudCorreccion, tipoTramiteModel.getDescripcion());
		
		}catch(DocumentoException e){
			e.printStackTrace();
			// -----------------------------------------------------
			// Lanzada por la firma electr�nica
			// -----------------------------------------------------
			log.error("No fue posible generar la firma electr�nica", e);
			DerechohabientesBusinessException dbe = new DerechohabientesBusinessException(ExceptionMessages.FIRMA_ELECTRONICA.ERROR.getMensaje(),e.getCause().getMessage());
			dbe.setCodigo(ExceptionMessages.FIRMA_ELECTRONICA.ERROR.getCodigo());
			
			//throw dbe;
			
			
		} catch (Exception e) {
			e.printStackTrace();
			// Si ocurrio algun error mandamos una excepcion indicando que no se
			// pudo guardar la solicitud
			log.error("No fue posible guardar la solicitud", e);
			throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_GUARDADO_SOLCITUD, e.getCause().getMessage());
		}

		return correccion;
	}

	private TramiteCircunscripcionForanea guardarValCircunscripcionInterno(
			Long idSolicitud, Usuario usuario, AsignacionNSS nss,
			TramiteCircunscripcionForanea circunscripcion_int,
			GrupoFamiliar afectado) throws DerechohabientesBusinessException {

		// Obtenemos al integrante del grupo familiar
		GrupoFamiliar integrante = null;
		// Creamos nuestro objeto solicitud que obtendra el resultado de la
		// operacion
		Solicitud solicitud = new Solicitud(idSolicitud);
		Tramite tramite = null;
		TramiteCircunscripcionForanea circunscripcion = null;
		Date fechaCambioMedico = null;
		List<GrupoFamiliar> integrantesEnUmf = new ArrayList<GrupoFamiliar>();
		Boolean cambioMedicoConyuge = false;

		log.debug("Se esta mandando la siguiente circunscripcion: "
				+ circunscripcion_int + ", para el siguiente integrante: "
				+ afectado);
		if (circunscripcion_int == null) {

			// obtenemos la solicitud
			try {
				solicitud = solicitudBusinessRemote.consultar(solicitud);
			} catch (Exception e1) {
				log.error("No fue posible consultar la solicitud", e1);
				throw new DerechohabientesBusinessException(
						"error.busqueda.solicitud", e1.getCause().getMessage());
			}

			if (solicitud == null)
				throw new DerechohabientesBusinessException(
						"error.busqueda.solicitud",
						"No se encontr� la solicitud");

			// buscamos el tramite de circunscripcion
			tramite = this.getTramiteCircunscripcion(solicitud);

			// si no encontramos el tramite, mandamos una excepcion
			if (tramite == null)
				throw new DerechohabientesBusinessException(
						"error.busqueda.solicitud",
						"No se encontr� la solicitud");

			// Obtenemos al integrante del grupo familiar al que se le aplicara
			// la circunscripcion
			try {
				log.debug("Se buscara al integrante con la siguiente informacion Nss: "
						+ nss.getNssStr()
						+ ", id persona: "
						+ tramite.getPersona().getIdPersona());
				integrante = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(
						nss.getIdAsignacionNSS(), tramite.getPersona()
								.getIdPersona());
			} catch (Exception e1) {
				log.error("Error al recuperar al integrante afectado", e1);
				DerechohabientesBusinessException
						.throwException("error.busqueda.integrante", e1
								.getCause().getMessage());
			}

			// Si no encontramos al integrante mandamos una excepcion
			if (integrante == null)
				DerechohabientesBusinessException.throwException(
						"exception.RNGD0003",
						"No se encontro al integrante del grupo familiar");

			// Buscamos datos de la circunscripcion
			try {
				circunscripcion = circunscripcionEntityLocal
						.getCircunscripcionForanea(tramite.getTramiteId());
			} catch (Exception e1) {
				log.error("Error al recuperar el tramite de circunscipcion", e1);
				DerechohabientesBusinessException.throwException(
						"error.busqueda.tramite", e1.getCause().getMessage());
			}

			// Verificamos que en la tabla de circunscripcion haya datos
			// insertados, de lo contrario lanzamos un excepcion
			if (circunscripcion == null)
				DerechohabientesBusinessException.throwException(
						"error.busqueda.tramite",
						"No se encontro el tramite de circunscripcion");

			// Establecemos la fecha de inicio de circunscripcion al dia de hoy
			// y la ponemos activa
			circunscripcion.setFecInicioCircunscripcion(new Date());
			circunscripcion.setIndCircunscripcionActiva(1);

			// Guardamos la actualizacion de la circunscripcion ya con su
			// tramite de suspension
			try {
				circunscripcionEntityLocal
						.updateCircunscripcionForanea(circunscripcion);
			} catch (Exception e1) {
				log.error("Error al actualizar tramite", e1);
				DerechohabientesBusinessException.throwException(
						"error.actualizar.tramite", e1.getCause().getMessage());
			}
		} else {
			circunscripcion = circunscripcion_int;
			// Obtenemos al integrante del grupo familiar al que se le aplicara
			// la circunscripcion
			log.debug("Ya se paso al integrante no es necesario consultarlo, idPersona: "
					+ circunscripcion.getPersona().getIdPersona()
					+ " y el nss: " + nss.getNssStr());

			if (afectado != null) {
				integrante = afectado;
			} else {
				try {
					integrante = grupoFamiliarDaoLocal
							.getIntegranteGrupoFamiliar(nss
									.getIdAsignacionNSS(), circunscripcion
									.getPersona().getIdPersona());
				} catch (Exception e1) {
					log.error("Error al recuperar al integrante afectado", e1);
					DerechohabientesBusinessException.throwException(
							"error.busqueda.integrante", e1.getCause()
									.getMessage());
				}
			}

			if (integrante == null)
				DerechohabientesBusinessException.throwException(
						"exception.RNGD0003",
						"No se encontro al integrante del grupo familiar");

		}
		circunscripcion.setPersonas(new ArrayList<Fisica>());
		circunscripcion.getPersonas().add(integrante.getDerechohabiente());

		Long parentesco = integrante.getParentesco().getIdParentesco();
		Boolean parentescoPermitido = parentesco
				.equals(ParentescoEnum.ASEGURADO.getId())
				|| parentesco.equals(ParentescoEnum.PENSIONADO.getId())
				|| parentesco.equals(ParentescoEnum.CONYUGE.getId());

		Long idPersonaAExcluir = integrante.getDerechohabiente().getIdPersona() != null ? integrante
				.getDerechohabiente().getIdPersona() : null;

		try {
			List<Long> estados = new ArrayList<Long>();
			estados.add(EstadoDerechohabienteEnum.VIGENTE.getId());
			estados.add(EstadoDerechohabienteEnum.CONSERVACION_DERECHOS.getId());
			estados.add(EstadoDerechohabienteEnum.CON_DERECHO.getId());
			integrantesEnUmf = grupoFamiliarDaoLocal
					.findIntegrantesPorUmfEstado(nss.getIdAsignacionNSS(),
							circunscripcion.getMedicoEnTurnoDestino()
									.getUnidadMedicaFamiliar().getIdUMF(),
							estados, idPersonaAExcluir, null);
		} catch (Exception e) {
			log.error(e);
		}

		if (parentescoPermitido) {

			if (!integrantesEnUmf.isEmpty()) {
				// Verificamos si existe alguna fecha en alguno de los
				// integrantes del grupo familiar
				Date fechaEnUmf = this
						.buscarFechaCambioMedico(integrantesEnUmf);
				// Obtenemos los dias que han pasado desde esa fecha en caso de
				// que todos hayan sido null regresara null
				Long dias = TramiteUtil.diasEntreFechayHoy(fechaEnUmf);
				// Verificamos si el o la conyuge cambiaron de medico
				cambioMedicoConyuge = !circunscripcion
						.getMedicoEnTurnoDestino()
						.getIdMedicoContultorioTurno()
						.equals(integrantesEnUmf.get(0).getMedicoEnTurno()
								.getIdMedicoContultorioTurno());
				// Si los dias son diferente de null
				if (dias != null) {
					// Checamos si han pasado mas de 365 dias
					if (dias > 365) {
						// Como la esposa es la que se cambia ponemos la fecha
						// de cambio al dia de hoy
						fechaCambioMedico = new Date();
					} else { // de lo contrario si no han pasado mas de 365 dias
						// Checamos que la fecha no sea nula del ultimo cambio
						// de medico y en caso
						// de ser asi ponemos la fecha de hoy a la esposa o
						// esposo
						fechaCambioMedico = fechaEnUmf == null ? new Date()
								: fechaEnUmf;
					}
				} else {
					fechaCambioMedico = new Date();
				}
			} else {
				fechaCambioMedico = new Date();
			}

		} else {
			if (!integrantesEnUmf.isEmpty()) {
				fechaCambioMedico = this
						.buscarFechaCambioMedico(integrantesEnUmf);
				fechaCambioMedico = fechaCambioMedico == null ? new Date()
						: fechaCambioMedico;
			} else {
				fechaCambioMedico = new Date();
			}
		}
		// ahora guardamos los nuevos datos de domicilio y umf donde
		// corresponden
		integrante.setDomicilio(circunscripcion.getDomicilioDestino());
		integrante.setMedicoEnTurno(circunscripcion.getMedicoEnTurnoDestino());
		// actualizamos la fecha del cambio de medico
		integrante.setFechaCambioTurnoMedico(fechaCambioMedico);
		try {
			grupoFamiliarDaoLocal.updateIntegrante(integrante);
		} catch (Exception e) {
			// Si ocurrio algun error mandamos una excepcion indicando que no se
			// pudo guardar la solicitud
			log.error("No pudo actualizar el integrante", e);
			throw new DerechohabientesBusinessException(
					"error.actualizar.integrante", e.getCause().getMessage());
		}

		// Vserificamos si hubo cambio de medico
		if (!integrantesEnUmf.isEmpty() && parentescoPermitido
				&& cambioMedicoConyuge) {
			for (GrupoFamiliar derechohabien : integrantesEnUmf) {
				try {
					TramiteCorreccionDerechohabiente correccion = new TramiteCorreccionDerechohabiente();
					correccion.setMedicoEnTurno(circunscripcion
							.getMedicoEnTurnoDestino());
					correccion.setIdPersona(derechohabien.getDerechohabiente()
							.getIdPersona());
					correccion.setPersona(new Fisica());
					correccion.getPersona().setIdPersona(
							derechohabien.getDerechohabiente().getIdPersona());
					correccion.setParentesco(derechohabien.getParentesco());
					correccion.setNss(nss.getNssStr());
					circunscripcion.getPersonas().add(
							derechohabien.getDerechohabiente());
					this.guardarTramiteCambioMedicoDependiente(correccion,
							usuario.getFisica(), solicitud.getSolicitudId(),
							nss);
				} catch (Exception e) {
					// Si ocurrio algun error mandamos una excepcion indicando
					// que no se
					// pudo guardar la solicitud
					log.error("No pudo actualizar el integrante", e);
					throw new DerechohabientesBusinessException(
							"error.actualizar.integrante", e.getCause()
									.getMessage());
				}
			}
		}

		return circunscripcion;
	}

	private TramiteCorreccionDerechohabiente copiarCorreccion(
			TramiteCorreccionDerechohabiente entrada) {
		TramiteCorreccionDerechohabiente salida = new TramiteCorreccionDerechohabiente();

		salida.setCalidad(entrada.getCalidad());
		salida.setCandidatosCambioClinica(entrada.getCandidatosCambioClinica());
		salida.setCorreoElectronico(entrada.getCorreoElectronico());
		salida.setCurpCap(entrada.getCurpCap());
		salida.setDetalleTramiteXml(entrada.getDetalleTramiteXml());
		salida.setDomicilio(entrada.getDomicilio());
		salida.setEnUmfDestino(entrada.getEnUmfDestino());
		salida.setEstadoCivil(entrada.getEstadoCivil());
		salida.setFacebook(entrada.getFacebook());
		salida.setFechaNacimiento(entrada.getFechaNacimiento());
		salida.setFechaNacimientoStr(entrada.getFechaNacimientoStr());
		salida.setIdPersona(entrada.getIdPersona());
		salida.setIdUmfOrigen(entrada.getIdUmfOrigen());
		salida.setLugarNacimiento(entrada.getLugarNacimiento());
		salida.setMedicoEnTurno(entrada.getMedicoEnTurno());
		salida.setNombre(entrada.getNombre());
		salida.setNss(entrada.getNss());
		salida.setParentesco(entrada.getParentesco());
		salida.setPersona(entrada.getPersona());
		salida.setPersonas(entrada.getPersonas());
		salida.setPrimerApellido(entrada.getPrimerApellido());
		salida.setSegundoApellido(entrada.getSegundoApellido());
		salida.setSexo(entrada.getSexo());
		salida.setTelefonoFijo(entrada.getTelefonoFijo());
		salida.setTelefonoMovil(entrada.getTelefonoMovil());
		salida.setTipoTramite(entrada.getTipoTramite());
		salida.setTwitter(entrada.getTwitter());
		salida.setPersona(entrada.getPersona());
		return salida;
	}

	private List<GrupoFamiliar> quitarIntegrante(List<GrupoFamiliar> entrada,
			GrupoFamiliar integrante) {
		List<GrupoFamiliar> salida = new ArrayList<GrupoFamiliar>();

		for (GrupoFamiliar inte : entrada) {
			if (!inte.getDerechohabiente().getIdPersona()
					.equals(integrante.getDerechohabiente().getIdPersona()))
				salida.add(inte);
		}

		return salida;
	}

	private BigDecimal getCalidad(Long idParentesco, Long idAsignacionNss) {

		Parentesco parentesco = null;

		try {
			parentesco = descripcionesDaoLocal.getParentesco(idParentesco);
			List<GrupoFamiliar> mismoParentesco = grupoFamiliarDaoLocal
					.findGrupoFamiliarByParentesco(idAsignacionNss,
							idParentesco);
			if (mismoParentesco.isEmpty()) {
				return parentesco.getCalidadMinima();
			} else {
				BigDecimal mayor = mismoParentesco.get(0).getCalidad();
				for (GrupoFamiliar integrante : mismoParentesco) {
					if (integrante.getCalidad().intValue() > mayor.intValue())
						mayor = integrante.getCalidad();
				}

				mayor = new BigDecimal(mayor.intValue() + 1);

				if (mayor.intValue() > parentesco.getCalidadMaxima().intValue()) {
					return parentesco.getCalidadMinima();
				} else {
					return mayor;
				}
			}
		} catch (DerechohabientesBusinessException e) {
			log.error("No se encontro el parentesco", e);
		} catch (Exception e) {
			log.error(
					"Ocurrio un error al obtener a los integrantes dle grupo con un parentesco",
					e);
		}

		return null;
	}

	private TramiteCorreccionDerechohabiente cambiarAMayusculas(
			TramiteCorreccionDerechohabiente correccion) {

		correccion.setNombre(correccion.getNombre() != null ? correccion
				.getNombre().toUpperCase() : "");
		correccion
				.setPrimerApellido(correccion.getPrimerApellido() != null ? correccion
						.getPrimerApellido().toUpperCase() : "");
		correccion
				.setSegundoApellido(correccion.getSegundoApellido() != null ? correccion
						.getSegundoApellido().toUpperCase() : "");
		correccion
				.setObservacion(correccion.getObservacion() != null ? correccion
						.getObservacion().toUpperCase() : "");

		return correccion;
	}

	@Override
	public boolean validaEstadoDerechohabientes(Date fechaNacimiento,
			Long idSexo, Long idParentesco, boolean patronIMSS) {

		long edad = DateUtils.getEdadRedondeadaEnAnios(fechaNacimiento);
		boolean respuesta = false;

		if (idParentesco.equals(ParentescoEnum.HIJOS.getId())) {
			if (patronIMSS && idSexo.equals(SexoEnum.MUJER.getId())) {
				if (edad <= 25L) {
					respuesta = true;
				} else {
					respuesta = false;
				}
			} else {
				if (edad > 16) {
					respuesta = false;
				} else {
					respuesta = true;
				}

			}
		} else {
			respuesta = true;
		}
		return respuesta;
	}

	private TramiteCorreccionDerechohabiente getTramiteCorreccion(
			Solicitud solicitud, Long idTipoTramite) {
		TramiteCorreccionDerechohabiente correccion = null;

		for (Tramite tramite : solicitud.getTramites()) {
			if (tramite instanceof TramiteCorreccionDerechohabiente) {
				if(idTipoTramite != null) {
					if(tramite.getTipoTramite().getIdTipoTramite().equals(idTipoTramite.intValue())) {
						correccion = (TramiteCorreccionDerechohabiente) tramite;
						break;
					}
				} else {
					correccion = (TramiteCorreccionDerechohabiente) tramite;
					break;
				}
			}
		}
		return correccion;
	}

	private TramiteCircunscripcionForanea getTramiteCircunscripcion(
			Solicitud solicitud) {
		TramiteCircunscripcionForanea circunscripcion = null;

		for (Tramite tramite : solicitud.getTramites()) {
			if (tramite instanceof TramiteCircunscripcionForanea) {
				circunscripcion = (TramiteCircunscripcionForanea) tramite;
			}
		}

		return circunscripcion;
	}

	@Override
	public Solicitud saveCorreccionDerechohabienteWeb(Long idDerechohabiente,
			Usuario usuario, AsignacionNSS nss,
			TramiteCorreccionDerechohabiente correccion,
			OrigenSolicitudEnum origen)
			throws DerechohabientesBusinessException {
		// Obenemos al derechohabiente a dar de baja
		GrupoFamiliar derechohabiente = null;
		try {
			derechohabiente = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(
					nss.getIdAsignacionNSS(), idDerechohabiente);
		} catch (Exception e1) {
			log.error("Error al recuperar al integrante afectado", e1);
			DerechohabientesBusinessException.throwException(e1.getCause()
					.getMessage(), "error.busqueda.integrante");
		}

		if (derechohabiente == null)
			DerechohabientesBusinessException.throwException(
					"No se encontro al integrante del grupo familiar",
					"exception.RNGD0003");

		Solicitud solicitud = null;

		// guardamos la solicitud y el tramite correspondiente
		try {
			solicitud = this.guardarSolicitudCorreccion(derechohabiente,
					usuario, nss, correccion,
					TipoTramiteEnum.MODIFICACION_DE_DERECHOHABIENTE, origen);
		} catch (Exception e) {
			log.error("Error al guardar la solicitud", e);
			DerechohabientesBusinessException
					.throwException("Error al guardar la solicitud"
							+ e.getCause().getMessage(),
							ExceptionMessages.ERROR_GUARDADO_SOLCITUD);
		}

		return solicitud;
	}

	@Override
	public Solicitud saveCorreccionDatosDerechohabiente(Long idDerechohabiente,
			Usuario usuario, AsignacionNSS nss, OrigenSolicitudEnum origen)
			throws DerechohabientesBusinessException {

		// Obenemos al derechohabiente a actualizar
		GrupoFamiliar derechohabiente = null;
		try {
			derechohabiente = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(
					nss.getIdAsignacionNSS(), idDerechohabiente);
		} catch (Exception e1) {
			log.error("Error al recuperar al integrante afectado", e1);
			DerechohabientesBusinessException.throwException(e1.getCause()
					.getMessage(), "error.busqueda.integrante");
		}

		if (derechohabiente == null)
			DerechohabientesBusinessException.throwException(
					"No se encontro al integrante del grupo familiar",
					"exception.RNGD0003");

		Solicitud solicitud = null;

		// guardamos la solicitud y el tramite correspondiente
		try {
			solicitud = this
					.guardarSolicitudCorreccion(
							derechohabiente,
							usuario,
							nss,
							new TramiteCorreccionDerechohabiente(),
							mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum.MODIFICACION_DE_DERECHOHABIENTE,
							origen);
		} catch (Exception e) {
			log.error("Error al guardar la solicitud", e);
			DerechohabientesBusinessException
					.throwException("Error al guardar la solicitud"
							+ e.getCause().getMessage(),
							ExceptionMessages.ERROR_GUARDADO_SOLCITUD);
		}

		return solicitud;
	}

	@Override
	public Solicitud finalizarSolicitudCorreccionDatos(Solicitud solicitud)
			throws SolicitudNoValidaException, SolicitudNoEncontradaException,
			SolicitudException, DerechohabientesBusinessException {

		if (solicitud.getFirmaElectronica() != null) {
			firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud,
					solicitud.getFirmaElectronica());
		}

		// Consultamos la solicitud a finalizar
		solicitud = solicitudBusinessRemote.consultar(solicitud);

		// Si la solicitud no es nula
		if (solicitud != null) {
			// Verificamos que la solicitud contenga al menos un tramite
			if (solicitud.getTramites().isEmpty()) {
				throw new SolicitudNoValidaException(
						"La solicitud no contiene tr\u00E1mites.");
			} else {

				TramiteCorreccionDerechohabiente tramiteCorreccionDerechohabiente = null;
				// Verificamos que la solicitud contenga tramites de tipo
				// correccion de datos de derechohabiente
				for (Tramite tramite : solicitud.getTramites()) {
					if (tramite instanceof TramiteCorreccionDerechohabiente) {
						tramiteCorreccionDerechohabiente = (TramiteCorreccionDerechohabiente) tramite;
						break;
					}
				}

				// Si no encontramos ningun tramite de correccion lanzamos una
				// excepcion
				if (tramiteCorreccionDerechohabiente == null) {
					throw new SolicitudNoValidaException(
							"No existen tr\u00E1mites de correcci\u00F3n de datos en la solicitud.");
				}

				// Insertamos un registro en dit baja para que se calcule la
				// vigencia del derechohabiente (NO APLICA PARA CORRECCION DE
				// DATOS)
				// bajaDerechohabienteEntityLocal.insertFromTramiteBaja(tramiteBaja);

				// En caso de que exista una fecha de defuncion en el tramite,
				// esta se le pone al integrante de la baja
				/*
				 * if(tramiteBaja.getFechaDefuncion() != null) { //Obtenemos a
				 * la persona fisica a dar de baja por defuncion Fisica fisica =
				 * new Fisica(); //Agregamos el id de la persona a actualiza
				 * fisica.setIdPersona(tramiteBaja.getPersona().getIdPersona());
				 * //agregamos la fecha de defuncion
				 * fisica.setFechaDefuncion(tramiteBaja.getFechaDefuncion());
				 * //actualizamos a la persona indicandole la fecha de defuncion
				 * try { personaBusinessRemote.actualizarPersona(fisica); }
				 * catch(Exception e) { log.error(
				 * "No fue posible actualizar la fecha de defuncion en la persona"
				 * , e); } }
				 */

				try {
					this.correccionDerechohabienteEntityLocal
							.saveCorreccionDerechohabiente(tramiteCorreccionDerechohabiente);
				} catch (Exception e) {
					DerechohabientesBusinessException
							.throwException("No fue posible guardar el registro de derechohabiente");
				}

				try {
					solicitudBusinessRemote.actualizarTramites(solicitud);
				} catch (Exception e) {
					e.printStackTrace();
				}

				// guardamos los documentos probatorios del tramite
				try {
					documentoProbatorioServiceBusinessRemote
							.guardarDocumentosCapturados(solicitud);
				} catch (DocumentoProbatorioException e) {
					log.error("Ocurrio un error al guardar los documentos", e);
				} catch (TramiteNoEncontradoException e) {
					log.error("No se encontro tramite", e);
				}

				// mandamos a llamar al servicio local para que se establezcan
				// el resultado y la razon del resultado
				try {
					
					String observaciones = null;
					if (StringUtils.isNotBlank(tramiteCorreccionDerechohabiente.getObservacion())) {
						observaciones = tramiteCorreccionDerechohabiente.getObservacion().length() > 255 ? tramiteCorreccionDerechohabiente.getObservacion().substring(0,250) : tramiteCorreccionDerechohabiente.getObservacion();
					}
					solicitudServiceLocal
							.marcarAtendidaSolictud(solicitud.getSolicitudId(),
									observaciones, null);
				} catch (DerechohabientesBusinessException e) {
					log.error("Ocurrio un error al actulizar la solicitud");
					throw new SolicitudException();
				}
			}
		} else {
			throw new SolicitudNoValidaException(
					"La solicitud no puede ser nula");
		}

		return solicitud;
	}

	@Override
	public Fisica localizarPersonaFisicaEnRENAPOxCURP(String curp)
			throws CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException {
		return this.localizarPersonaFisicaEnRENAPOServiceBusiness
				.localizarPersonaFisicaEnRENAPOxCURP(curp);
	}

	@Override
	public void saveCorreccionDatosDerechohabiente(
			TramiteCorreccionDerechohabiente tramiteCorreccionDerechohabiente)
			throws DerechohabientesBusinessException, Exception {

		/*
		 * if(tramiteCorreccionDerechohabiente.getTramiteId() != null){
		 * if(!registroDerechohabienteDaoLocal
		 * .existeRegistroDerechohabiente(miRegistroDerechohabiente
		 * .getTramiteId())){
		 * registroDerechohabienteDaoLocal.saveRegistroDerechohabiente
		 * (miRegistroDerechohabiente); }else{
		 * registroDerechohabienteDaoLocal.actualizaRegistroDerechohabiente
		 * (miRegistroDerechohabiente); } }
		 */
	}

	@Override
	public Solicitud guardarSolicitudCorreccionDerechohabiente(
			GrupoFamiliar derechohabiente, Usuario usuario, AsignacionNSS nss,
			TramiteCorreccionDerechohabiente correccion,
			TipoTramiteEnum tipoCorreccion, OrigenSolicitudEnum origen)
			throws DerechohabientesBusinessException {
		return this.guardarSolicitudCorreccion(derechohabiente, usuario, nss,
				correccion, tipoCorreccion, origen);
	}

	/*
	 * Seccion para la finalizacion de las solicitudes de los tramites:
	 * ASIGNACION_DE_DOMICILIO_PARTICULAR_DH (101)
	 * ACTUALIZACION_DOMICILIO_PARTICULAR(6) CAMBIO_CLINICA(36)
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.imss.ctirss.gestionpersonas.servicios.business.
	 * SolicitudPersonaBusinessRemote
	 * #finalizarSolicitudAsignacionDomicilio(mx.gob
	 * .imss.ctirss.delta.model.gestion.solicitud.Solicitud)
	 */
	@Override
	public Solicitud finalizarSolicitudAsignacionDomicilio(Solicitud solicitud)
			throws SolicitudNoEncontradaException,
			DomicilioNoLocalizadoException {

		if (solicitud != null && solicitud.getSolicitudId() != null) {
			FirmaElectronica firma = solicitud.getFirmaElectronica();
			// solicitud = this.solicitudBusiness.consultar(solicitud);
			solicitud = solicitudBusinessRemote.consultar(solicitud);
			solicitud.setFirmaElectronica(firma);
			TramiteCorreccionDerechohabiente tramite = (TramiteCorreccionDerechohabiente) solicitud
					.getTramites().get(0);

			// Se obtiene la lista de domicilios ya existen de la persona, para
			// que el primer domicilio
			// sea usado como domicilio del grupo familiar
			Persona persona = new Persona();
			persona.setIdPersona(tramite.getIdPersona());
			List<Domicilio> domicilios = domicilioServiceBusiness
					.consultarDomiciliosPersonaFisica(persona);
			Domicilio domicilio = domicilios.get(0);

			// Actualizar al grupo familiar
			try {
				List<AsignacionNSS> listaNSS = grupoFamiliarDaoLocal
						.getAsignacionNss(tramite.getIdPersona());
				AsignacionNSS nss = listaNSS.get(0);
				GrupoFamiliar afectado = grupoFamiliarDaoLocal
						.getIntegranteGrupoFamiliar(nss.getIdAsignacionNSS(),
								nss.getIdPersona());
				afectado.setCvePersonaDomicilio(domicilio.getCveIdPersonafDom());
				afectado.setFechaRegistroActualizacion(new Date());
				grupoFamiliarDaoLocal.updateIntegrante(afectado);

				// Finalizar el tramite
				if (solicitud.getFirmaElectronica() != null) {
					firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(
							solicitud, solicitud.getFirmaElectronica());
					solicitudBusinessRemote
							.actualizarSolicitudAEstatusConcluida(solicitud);
				}
			} catch (DerechohabientesBusinessException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return solicitud;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.imss.ctirss.gestionpersonas.servicios.business.
	 * SolicitudPersonaBusinessRemote
	 * #finalizarSolicitudActualizacionDomicilio(mx
	 * .gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud,
	 * mx.gob.imss.ctirss.delta.model.gestion.individuo.MDMDatosEntrada)
	 */
	@Override
	public Solicitud finalizarSolicitudActualizacionDomicilio(
			Solicitud solicitud) throws SolicitudNoEncontradaException,
			DomicilioNoLocalizadoException {

		try {
			// Consultar la informacion de la solicitud, la cual ya debe tener
			// la informacion de los datos de modificacion
			// del domicilio
			FirmaElectronica firma = solicitud.getFirmaElectronica();
			// solicitud = this.solicitudBusiness.consultar(solicitud);
			solicitud = solicitudBusinessRemote.consultar(solicitud);
			solicitud.setFirmaElectronica(firma);
			TramiteCorreccionDerechohabiente tramite = (TramiteCorreccionDerechohabiente) solicitud
					.getTramites().get(0);
			domicilioServiceBusiness.modificarDomicilio(tramite.getDomicilio());

			// afectado.setCvePersonaDomicilio( domicilio.getCveIdPersonafDom()
			// );
			// afectado.setFechaRegistroActualizacion( new Date() );
			// grupoFamiliarDaoLocal.updateIntegrante( afectado );

			// Finalizar el tramite
			if (solicitud.getFirmaElectronica() != null) {
				documentoProbatorioServiceBusinessRemote
						.guardarDocumentosCapturados(solicitud);
				firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(
						solicitud, solicitud.getFirmaElectronica());
				solicitudBusinessRemote
						.actualizarSolicitudAEstatusConcluida(solicitud);
			}

		} catch (TramiteNoEncontradoException e) {
			e.printStackTrace();
		} catch (TransformacionException e) {
			e.printStackTrace();
		} catch (DocumentoProbatorioException e) {
			e.printStackTrace();
		}
		// catch (DerechohabientesBusinessException e) {
		// e.printStackTrace();
		// }
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return solicitud;
	}

	@Override
	public Solicitud finalizarSolicitudCambioClinica(Solicitud solicitud)
			throws SolicitudNoEncontradaException,
			DomicilioNoLocalizadoException {

		try {
			// Consultar la informacion de la solicitud, la cual ya debe tener
			// la informacion de los datos de modificacion
			// del domicilio
			FirmaElectronica firma = solicitud.getFirmaElectronica();
			solicitud = solicitudBusinessRemote.consultar(solicitud);
			solicitud.setFirmaElectronica(firma);
			TramiteCorreccionDerechohabiente tramite = (TramiteCorreccionDerechohabiente) solicitud
					.getTramites().get(0);
			domicilioServiceBusiness.modificarDomicilio(tramite.getDomicilio());

			// Finalizar el tramite
			if (solicitud.getFirmaElectronica() != null) {
				documentoProbatorioServiceBusinessRemote
						.guardarDocumentosCapturados(solicitud);
				firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(
						solicitud, solicitud.getFirmaElectronica());
				solicitudBusinessRemote
						.actualizarSolicitudAEstatusConcluida(solicitud);
			}

		} catch (TramiteNoEncontradoException e) {
			e.printStackTrace();
		} catch (TransformacionException e) {
			e.printStackTrace();
		} catch (DocumentoProbatorioException e) {
			e.printStackTrace();
		}

		return solicitud;
	}

	/*
	 * Fin seccion para la finalizacion de las solicitudes de los tramites:
	 * ASIGNACION_DE_DOMICILIO_PARTICULAR_DH (101)
	 * ACTUALIZACION_DOMICILIO_PARTICULAR(6) CAMBIO_CLINICA(36)
	 */

	@Override
	public void updateIntegrante(GrupoFamiliar integrante)
			throws DerechohabientesBusinessException, Exception {
		if (integrante != null) {
			grupoFamiliarDaoLocal.updateIntegrante(integrante);
		} else {
			throw new DerechohabientesBusinessException(
					"El objeto a modificar no puede se null");
		}
	}
	
	/**
	 * Remueve los integrantes que esten en baja y no sea administrativa
	 * 
	 * @param entrada La lista de integrantes a filtrar
	 * @return List<GrupoFamiliar> lista con los integrantes del grupo familiar
	 *         en estado vigente, conservacion de derechos y con baja administrativa
	 */
	private List<GrupoFamiliar> quitarBajasNoAdministrativas(List<GrupoFamiliar> entrada) {
		return bajaDerechohabienteService.quitarBajasNoAdministrativas(entrada);
	}
	
	
	
	/**
	 * Remueve los integrantes fallecidos
	 * 
	 * 
	 * @param entrada La lista de integrantes a filtrar
	 * @return List<GrupoFamiliar> lista con los integrantes del grupo familiar no fallecidos
	 *         
	 */
	private List<GrupoFamiliar> quitarFallecidos(List<GrupoFamiliar> entrada) {
		
		List<GrupoFamiliar> salida = new ArrayList<GrupoFamiliar>();

		//Recorremos la lista de los integrantes del grupo familiar
		for(GrupoFamiliar integrante: entrada) {
			//Si su estado es baja o fallecido lo quitamos de la lista de integrante
			if(!integrante.getEstadoDerechohabiente().getIdEstadoDerechohabiente().equals(EstadoDerechohabienteEnum.FALLECIDO)){
				salida.add(integrante);
			} else {
				if(integrante.getParentesco().getIdParentesco().equals(ParentescoEnum.PENSIONADO.getId())) {
					salida.add(integrante);
				}
			}
		}
		return salida;
	}
	

	/**
	 * 
	 */
	public FirmaElectronica generaFirmaElectronica(AsignacionNSS asignacionNSS,Solicitud solicitud, String nombreTramite) throws DocumentoException{
		return tramiteDocumentoService.generaFirmaElectronica(asignacionNSS, solicitud, nombreTramite);
	}
	
	/**
	 * Obtiene el valor del agreagado m&eacute;dico en base a los nuevos datos del tr&aacute;mite
	 * 
	 * @param correccion TramiteCorreccionDerechohabiente nuevos datos capturados
	 * @return String valor de agregado m&eacute;dico
	 * @throws DerechohabientesBusinessException
	 */
	private String  obtenerAgregadoMedico(TramiteCorreccionDerechohabiente correccion, CabezaGrupoFamiliar cabeza) throws DerechohabientesBusinessException{
		
		Parentesco catParentesco = null;
		
		if(cabeza == null) {
			try {
				cabeza = grupoFamiliarServiceLocal.cabezaGrupoFamiliar(correccion.getIdAsignacionNss());
			} catch (Exception e) {
				log.debug("Error al consultar la cabeza de grupo familiar");
				DerechohabientesBusinessException.throwException("No fue posible consultar la cabeza del grupo familiar");
			}
		}
		
		try {
			catParentesco = catalogosDAO.getCatalogoParentesco(correccion.getParentesco().getIdParentesco());
		} catch(Exception e) {
			DerechohabientesBusinessException.throwException("Ocurrio un error al generar la calidad del derechohabiente");
		}
		
		if(correccion.getFechaNacimiento() == null && correccion.getAnioRegistroNac() == null){
			DerechohabientesBusinessException.throwException("No se pudo concluir el registro, es forzoso la fecha o a�o de nacimiento.");
		}
		
		log.debug("El sexo que viene es " + correccion.getSexo());

		
		Fisica persona = new Fisica();
		persona.setFechaNacimiento(correccion.getFechaNacimiento());
		persona.setSexo(correccion.getSexo());
		persona.setAnioRegistroNac(correccion.getAnioRegistroNac());
	
		String agregado = agregadoMedicoServiceLocal.getAgregadoMedico(cabeza, catParentesco.getCalidadMaxima().intValue(), persona, null);
		
		return agregado;
	}
	
	
	/**
	 * Obtiene el valor de agregado afiliaci&oacute;n 
	 * 
	 * @param correccion TramiteCorreccionDerechohabiente nuevos datos capturados
	 * @return String valir de agregado de afiliaci&oacute;n
	 * @throws DerechohabientesBusinessException
	 */
	private String obtenerAgregadoAfiliacion(TramiteCorreccionDerechohabiente correccion) throws DerechohabientesBusinessException{
		
		return DeltaUtils.getAgregadoIdentidad(correccion.getNumCalidad().intValue(),
				correccion.getSexo().getIdSexo(), correccion.getFechaNacimiento(), correccion.getAnioRegistroNac());
	}
	
	
	/**
	 * Obtiene el numero de calidad para el parentesco en la correci&oacute;n
	 * 
	 * @param correccion
	 * @return Long
	 * @throws DerechohabientesBusinessException
	 */
	private Long obtenerNumeroCalidad(TramiteCorreccionDerechohabiente correccion) throws DerechohabientesBusinessException{
		
		
		Parentesco catParentesco = null;

		try {
			catParentesco = catalogosDAO.getCatalogoParentesco(correccion.getParentesco().getIdParentesco());
		} catch(Exception e) {
			DerechohabientesBusinessException.throwException("Ocurrio un error al generar la calidad del derechohabiente");
		}
		
		Long numCalidad = catParentesco.getCalidadMinima().longValue();
		Long idParentesco = catParentesco.getIdParentesco();
		Integer idSexo	= correccion.getSexo().getIdSexo();
		Long calidadMaxima = catParentesco.getCalidadMaxima().longValue();
		
		Boolean isAsegurado = idParentesco.equals(ParentescoEnum.ASEGURADO.getId()) || idParentesco.equals(ParentescoEnum.PENSIONADO.getId());
		
		if(!isAsegurado){
			if(idParentesco == ParentescoEnum.PADRES.getId()){
				if(idSexo.longValue() == SexoEnum.MUJER.getId()){
					numCalidad = calidadMaxima;
				}
			} else{
				
				Long calidadMasAlta = grupoFamiliarDaoLocal.getCalidadMasAltaRegistradaPorParentesco(correccion.getIdAsignacionNss(), idParentesco);
				
				if(calidadMasAlta != null) {
					
					numCalidad = calidadMasAlta.longValue() + 1L;
					
					if(numCalidad.longValue() > calidadMaxima.longValue()) {
						numCalidad = calidadMaxima;
					}
				} 
			}
		} else  {
			numCalidad = 1L;
		}
		
		
		return numCalidad;
		
	}

	private Domicilio guardarDomicilioNuevo(Domicilio domicilio) throws DerechohabientesBusinessException{
		
		Domicilio domicilioNuevo = null;
		try {
			// Si el domicilio cambio registramos el nuevo domicilio
			domicilioNuevo = domicilioServiceBusinessRemote.registrarDomicilio(domicilio);
		} catch (DomicilioNoValidoException e) {
			e.printStackTrace();
			log.error("Error al guardar el domicilio", e);
			throw new DerechohabientesBusinessException("error.guardar.Domicilio", e.getCause().getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			DerechohabientesBusinessException.throwException("error.guardar.Domicilio", e.getCause().getMessage());
		}
		
		return domicilioNuevo;
	}
	
	private List<GrupoFamiliar> filtrarPorUmf(List<GrupoFamiliar> integrantes,
			Long idUmf) {
		List<GrupoFamiliar> salida = new ArrayList<GrupoFamiliar>();

		for (GrupoFamiliar integrante : integrantes) {
			if (integrante.getMedicoEnTurno() != null) {
				if (integrante.getMedicoEnTurno().getUnidadMedicaFamiliar() != null) {
					if (integrante.getMedicoEnTurno().getUnidadMedicaFamiliar()
							.getIdUMF().longValue() == idUmf.longValue()) {
						salida.add(integrante);
					}
				}
			}
		}

		return salida;
	}
}