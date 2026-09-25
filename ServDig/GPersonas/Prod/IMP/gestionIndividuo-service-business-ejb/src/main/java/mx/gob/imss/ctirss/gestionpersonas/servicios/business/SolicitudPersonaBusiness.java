/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:SolicitudPersonaBusiness.java
 *  @Paquete:mx.gob.imss.ctirss.gestionpersonas.servicios.business
 *  @Fecha:22/02/2012
 */
package mx.gob.imss.ctirss.gestionpersonas.servicios.business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CorreccionDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.individuo.AfectacionDatosPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ComparacionSinDiferenciasException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.RegistroPersonaFisicaException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.exception.usuario.ActualizaUsuarioEsquemaSeguridadException;
import mx.gob.imss.ctirss.delta.exception.usuario.EsquemaSegurdiadException;
import mx.gob.imss.ctirss.delta.exception.usuario.UsuarioNoRegistradoEnEsquemaDeSeguridadException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.CalificacionesPersonaBusinessServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.entity.UsuarioPortalEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.individuo.util.Utilerias;
import mx.gob.imss.ctirss.delta.gestion.individuo.util.WebserviceTools;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.ModuloEnum;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoPerInteresadaSol;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.Pais;
import mx.gob.imss.ctirss.delta.model.enums.CambioComparacionEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoIdentificadorEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaInteresadaSolEnum;
import mx.gob.imss.ctirss.delta.model.firma.RespuestaFirmadoSimple;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.AfectarDatosPersonaWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ClavesRenapo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.EstadoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.EstadoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fiel;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosConsulta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Identificador;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.MDMDatosEntrada;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaEstado;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoIdentificador;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.CalificacionPersona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FiltroSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modulo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.AcuseVentanilla;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.PersonaInteresadaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultadoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCambioInformacionPersona;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteMoral;
import mx.gob.imss.ctirss.gestionpersonas.servicios.entity.SolicitudPersonaEntityLocal;
import mx.gob.imss.ctirss.gestionpersonas.servicios.utility.AfectarDatosPersonaUtilityLocal;

/**
 * @author Joaquin Ponte, Cesar Garcia
 * 
 */
@Stateless(name = "solicitudPersonaBusiness", mappedName = "solicitudPersonaBusiness")
public class SolicitudPersonaBusiness extends AbstractServiceBusiness implements SolicitudPersonaBusinessRemote {

	@EJB
	private transient SolicitudPersonaEntityLocal solicitudPersonaEntity;

	@EJB
	private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;

	@EJB(name = "personaMoralBusiness")
	private transient PersonaMoralBusinessRemote personaMoralBusiness;

	@EJB
	private SolicitudBusinessRemote solicitudBusiness;

	@EJB
	private FirmaDigitalBusinessRemote firmaDigitalBusinessRemote;

	@EJB
	private AfectarDatosPersonaBusinessRemote afectarDatosPersonaBusiness;

	@EJB
	private AfectarDatosPersonaUtilityLocal afectarDatosPersonaUtility;

	@EJB
	private PersonaBusinessLocal personaBusiness; 

	@EJB
	private ComponentesExternosBusinessLocal componentesExternosBusiness;

	@EJB
	private CalificacionesPersonaBusinessServiceRemote calificacionesPersonaBusinessService;

	@EJB
	private DomicilioServiceBusinessRemote domicilioServiceBusiness;

	@EJB( name = "grupoFamiliarService", mappedName = "grupoFamiliarService")
	private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;

	@EJB(name = "correccionDerechohabienteService", mappedName = "correccionDerechohabienteService")
	private CorreccionDerechohabienteServiceRemote correccionDerechohabienteServiceRemote;

	@EJB(name = "documentoProbatorioServiceBusiness",mappedName = "documentoProbatorioServiceBusiness")
	DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusinessRemote;

	@EJB
	UsuarioPortalEntityLocal usuarioPortalEntityLocal;



	@Override
	public Solicitud alta(Solicitud solicitud) {

		if (solicitud != null) {

			solicitud.setIdEstadoSolicitud(EstadoSolicitud.REGISTRADA);
			solicitud.setDesEstadoSolicitud(EstadoSolicitud.ESTADO_1_REGISTRADA);

			// INICIALIZAMOS CADA TRAMITE A SU ESTADO INICIAL (REGISTRADO)
			final List<Tramite> listaTramites = solicitud.getTramite();
			for (Tramite tramite : listaTramites) {

				tramite.setIdEstadoTramite(EstadoTramite.REGISTRADO);
				tramite.setDesEstadoTramite(EstadoTramite.ESTADO_1_REGISTRADO);

				// VERIFICA SI ES UNA PERSONA FISICA
				if (tramite.getPersonaFisica() != null) {

					// VERIFICAMOS SI EL ACTA DE NACIMIENTO FUE UTILIZADA DE MANERA EFECTIVA EN EL TRAMITE
					if (tramite.getPersonaFisica().getActaNacimiento() != null 
							&& tramite.getPersonaFisica().getActaNacimiento().getMunicipio() != null
							&& tramite.getPersonaFisica().getActaNacimiento().getMunicipio().getEntidadFederativa() != null
							&& tramite.getPersonaFisica().getActaNacimiento().getMunicipio().getEntidadFederativa().getClave() != null
							&& tramite.getPersonaFisica().getActaNacimiento().getMunicipio().getEntidadFederativa().getClave().equals("-1")) {
						tramite.getPersonaFisica().setActaNacimiento(null);
					}

					// VERIFICAMOS SI SETTEAMOS EL PAIS EN BASE A LA ENTIDAD FEDERATIVA
					if (tramite.getPersonaFisica().getPais() == null
							// PARA SETEAR EL PAIS NECESITAMOS LA ENTIDAD FEDERATIVA
							&& tramite.getPersonaFisica().getLugarNacimiento().getClave() != null) {

						final Pais pais = new Pais();
						if (0 < Integer.parseInt(tramite.getPersonaFisica().getLugarNacimiento().getClave()) && Integer.parseInt(tramite.getPersonaFisica().getLugarNacimiento().getClave()) < ClavesRenapo.FROM_DESC_TO_CVE_ENT_FED_NAC_MAP.get("NE")) {
							pais.setIdPais(1); // MEXICANA
						} else if (
								tramite.getPersonaFisica().getLugarNacimiento().getClave().equals(ClavesRenapo.FROM_DESC_TO_CVE_ENT_FED_NAC_MAP.get("NE").toString()) || 
								tramite.getPersonaFisica().getLugarNacimiento().getClave().equals(ClavesRenapo.FROM_DESC_TO_CVE_ENT_FED_NAC_MAP.get("SE").toString())) {
							pais.setIdPais(2); // EXTRANJERA
						} else {
							pais.setIdPais(null);
						}
						tramite.getPersonaFisica().setPais(pais);
					} // END VERIFICAMOS SI SETTEAMOS EL PAIS EN BASE A LA ENTIDAD FEDERATIVA

					// ESTADO
					final EstadoPersona estadoPersona = new EstadoPersona();
					estadoPersona.setIdEstadoPersona(Utilerias.convertir(EstadoPersonaEnum.VALIDADO.getCodigo())); // ESTADO VALIDADO
					final PersonaEstado personaEstado = new PersonaEstado();
					personaEstado.setEstadoPersona(estadoPersona);
					if (tramite.getPersonaFisica().getPersonaEstados() == null) {
						tramite.getPersonaFisica().setPersonaEstados(new LinkedList<PersonaEstado>());
					}
					tramite.getPersonaFisica().getPersonaEstados().add(personaEstado);
				}// END VERIFICA SI ES UNA PERSONA FISICA

				// VERIFICA SI ES UNA PERSONA MORAL
				if (tramite.getPersonaMoral() != null) {

					if (Utilerias.isNotBlank(tramite.getPersonaMoral().getPersonaCalificaciones().get(0).getCalificacion().getIdCalificacion())) {

						// ESTADO
						final EstadoPersona estadoPersona = new EstadoPersona();
						estadoPersona.setIdEstadoPersona(Utilerias.convertir(EstadoPersonaEnum.VALIDADO.getCodigo())); // ESTADO VALIDADO
						final PersonaEstado personaEstado = new PersonaEstado();
						personaEstado.setEstadoPersona(estadoPersona);
						if (tramite.getPersonaMoral().getPersonaEstados() == null) {
							tramite.getPersonaMoral().setPersonaEstados(new LinkedList<PersonaEstado>());
						}
						tramite.getPersonaMoral().getPersonaEstados().add(personaEstado);
					} else {
						log.error("\n\n*************************************\n\n");
						log.error(" Estas lineas no deberian aparecer en el servidor, se ha generado una condici茂驴艙n que el sistema no deberia presentar, por favor reportarlo");
						log.error(" Error en: SolicitudPersonaBusiness.alta");
						log.error("\n\n*************************************\n\n");
					}
				}
			}

			solicitud = solicitudPersonaEntity.alta(solicitud);

		}
		return solicitud;
	}

	public Solicitud getSolicitud(final Long idSolicitud) throws SolicitudNoEncontradaException {
		return solicitudPersonaEntity.getSolicitud(idSolicitud);
	}

	public Solicitud modificar(final Solicitud solicitud) {
		return solicitudPersonaEntity.modificar(solicitud);
	}

	public void cancelaSolicitudes() {
		/*
		 * log.trace("este es el proceso de cancelacion de solicitudes")
		 * ;
		 * List<Solicitud> listaSolicitud =
		 * solicitudPersonaEntity.getSolicitudesVencidas();
		 * if (listaSolicitud != null){
		 * for (Solicitud solicitudSeleccionada : listaSolicitud){
		 * log.trace("Se cancelara la solicitud: " +
		 * solicitudSeleccionada.getIdSolicitud());
		 * Solicitud solicitud = null;
		 * try{
		 * solicitud =
		 * (Solicitud)WebserviceTools.getXml(solicitudSeleccionada.getXml(),
		 * Solicitud.class);
		 * }
		 * catch(Exception e){
		 * log.trace(e.getMessage());
		 * log.trace("La solicitud " +
		 * solicitudSeleccionada.getIdSolicitud() +
		 * " tiene un XML mal formado");
		 * continue;
		 * }
		 * //RECORRE TODAS LOS TRAMITES DE LA SOLICITUD
		 * List<Tramite> listaTramite = solicitud.getTramite();
		 * if (listaTramite != null){
		 * for (Tramite tramite : listaTramite){
		 * log.trace("Solicitud a cancelar. id: " +
		 * solicitud.getIdSolicitud() + "   Tramite a procesar: " +
		 * tramite.getIdTramite());
		 * //VERIFICA QUE EL TRAMITE ESTE EN ESTATUS REGISTRADA
		 * if (tramite.getIdEstadoTramite() == EstadoTramite.REGISTRADO){
		 * //CAMBIA EL ESTADO DEL TRAMITE
		 * tramite.setIdEstadoTramite(EstadoTramite.CERRADO);
		 * tramite.setIndResultado(0L);
		 * tramite.setIdRazonResultado(RazonRechazoTramite.SOLICITUD_CANCELADA_CVE
		 * );
		 * tramite.setDesRazonResultado(RazonRechazoTramite.SOLICITUD_CANCELADA);
		 * }
		 * }
		 * }//RECORRE TODAS LOS TRAMITES DE LA SOLICITUD
		 * //ACTUALIZAMOS EL REGISTRO DE LA SOLICITUD
		 * solicitud.setIdEstadoSolicitud(EstadoSolicitud.CANCELADA);
		 * solicitudPersonaEntity.modificar(solicitud);
		 * }
		 * }
		 */
	}

	@Override
	public void procesarSolicitudesRegistradas() throws SolicitudException{
		log.trace("Hilo de procesarSolicitudesRegistradas...");
		final List<Solicitud> listaSolicitud = solicitudPersonaEntity.getSolicitudesRegistradas();
		if (listaSolicitud != null) {
			for (Solicitud solicitudSeleccionada : listaSolicitud) {
				if (procesarTramiteSolicitud(solicitudSeleccionada) == null) {
					continue;
				}

			}
		}
	}

	@Override
	public Solicitud procesarSolicitudNueva(Solicitud solicitud) throws SolicitudException{

		System.out.println("\n\n\nSOLUCION TEMPORAL. BORRAR HASTA QUE FUNCIONE\n\n\n");
		try{
			System.out.println("SOLICITUD RECIBIDA PARA PROCESAR EN BD.");
			System.out.println(WebserviceTools.getStringXml(solicitud));
		}
		catch(Exception e){
			e.printStackTrace();
		}

		final Solicitud solicitudNva = alta(solicitud);
		procesarTramiteSolicitud(solicitudNva);
		return solicitud;
	}

	public Solicitud procesarTramiteSolicitud(final Solicitud solicitud) throws SolicitudException{
		log.trace("SolicitudPeronaBusiness. procesarTramiteSolicitud. Inicio. " + new Date());
		Solicitud solicitudModificada = null;
		try {
			//OBTENEMOS LOS OBJETOS DE LA SOLICITUD EN BASE AL CAMPO XML.
			log.trace("SolicitudPeronaBusiness. procesarTramiteSolicitud. Solicitud a procesar id: " + solicitud.getIdSolicitud());
			boolean bSolicitudConTramitesNoValidados = false; // NOPMD

			final List<Tramite> listaTramite = solicitud.getTramite();
			if (listaTramite != null) {
				for (Tramite tramite : listaTramite) {
					log.trace("SolicitudPersonaBusiness. procesarTramiteSolicitud. Solicitud registrada. id: " + solicitud.getIdSolicitud() + "   Tramite a procesar: " + tramite.getIdTramite());

					//SOLO PROCESA TRAMITES CON ESTADO REGISTRADO
					if (EstadoTramite.REGISTRADO.equals(tramite.getIdEstadoTramite())) {
						if (tramite.getTipoTramite() != null) {

							//VERIFICA QUE SE TRATE DE UN TRAMITE DE REGISTRO DE PERSONAS
							if (tramite.getTipoTramite().getIdTipoTramite().intValue() == mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.TipoTramite.REGISTRO_PERSONA.intValue()) {

								log.trace("SolicitudPersonaBusiness. procesarTramiteSolicitud. Solicitud registrada. id: " + solicitud.getIdSolicitud() + "   Tramite a procesar: " + tramite.getIdTramite() + " TipoTramite: " + tramite.getTipoTramite().getIdTipoTramite());
								boolean bCambiaEstadoTramite = false;

								//VERIFICA SI ES UNA PERSONA FISICA
								if (tramite.getPersonaFisica() != null) {

									// VERIFICAMOS QUE LA PERSONA ESTE CALIFICADA POR EL
									// IMSS, RENAPO Y SAT PARA PODERLO DAR DE ALTA
									final int iCalificacion = Utilerias.convertir(tramite.getPersonaFisica().getPersonaCalificaciones().get(0).getCalificacion().getIdCalificacion());
									if (iCalificacion == CalificacionPersona.VALIDADO_RENAPO || iCalificacion == CalificacionPersona.VALIDADO_SAT || iCalificacion == CalificacionPersona.VALIDADO_IMSS) {

										// 191807 311012 se setean los identificadoresen caso de haberse localizado a la persona en alguna entidad externa
										agregarIdentificadores(tramite.getPersonaFisica());

										// GUARDA LA PERSONA EN BASE DE DATOS
										//										final Fisica personaFisicaResultado = personaBusiness.altaPersonaFisica(tramite.getPersonaFisica());
										Fisica personaFisicaResultado = personaFisicaServiceBusiness.registrar(tramite.getPersonaFisica());

										log.trace("SolicitudPersonaBusiness. procesarTramiteSolicitud. Solicitud registrada. Persona enviada.\n\n" + tramite.getPersonaFisica());
										log.trace("SolicitudPersonaBusiness. procesarTramiteSolicitud. Solicitud registrada. Persona recibida despues de darla de alta en BD.\n\n" + personaFisicaResultado);
										tramite.getPersonaFisica().setLugarNacimiento(personaFisicaResultado.getLugarNacimiento());
										tramite.getPersonaFisica().setIdPersona(personaFisicaResultado.getIdPersona());
										//170812 por peticion de lucio
										//                                        tramite.setPersonaFisica(personaFisicaResultado);

										bCambiaEstadoTramite = true;
									} else if (iCalificacion == CalificacionPersona.NO_VALIDADO) {
										bSolicitudConTramitesNoValidados = true; // NOPMD
									}
								} else {
									//PERSONA MORAL
									// VERIFICAMOS QUE LA PERSONA ESTE CALIFICADA POR EL
									// IMSS, RENAPO Y SAT PARA PODERLO DAR DE ALTA
									final int iCalificacion = Utilerias.convertir(tramite.getPersonaMoral().getPersonaCalificaciones().get(0).getCalificacion().getIdCalificacion());
									if (CalificacionPersona.VALIDADO_RENAPO.equals(iCalificacion) || CalificacionPersona.VALIDADO_SAT.equals(iCalificacion) || CalificacionPersona.VALIDADO_IMSS.equals(iCalificacion)) {

										// GUARDA LA PERSONA EN BASE DE DATOS
										final Moral personaMoralResultado = personaMoralBusiness.altaPersonaMoral(tramite.getPersonaMoral());
										tramite.getPersonaMoral().setIdPersona(personaMoralResultado.getIdPersona());

										bCambiaEstadoTramite = true;
									} else if (iCalificacion == CalificacionPersona.NO_VALIDADO) {
										bSolicitudConTramitesNoValidados = true; // NOPMD
									}
								}//VERIFICA SI ES UNA PERSONA FISICA

								// DE ACUERDO AL PROCESAMIENTO DEL TRAMITE VERIFICA SI ES
								// NECESARIO CAMBIAR EL ESTADO DEL TRAMITE
								if (bCambiaEstadoTramite) {
									// CAMBIA EL ESTADO DEL TRAMITE
									tramite.setIdEstadoTramite(EstadoTramite.CERRADO);
									tramite.setDesEstadoTramite(EstadoTramite.ESTADO_3_CERRADO);
									tramite.setIndResultado(1L);
									tramite.setIdRazonResultado(1L);
									tramite.setDesRazonResultado("Normal");
								}
							}
						}

					}// VERIFICA QUE EL TRAMITE ESTE EN ESTATUS REGISTRADO
				}
			} // RECORRE TODAS LOS TRAMITES DE LA SOLICITUD

			// ESTABLECEMOS EL ESTADO DE LA SOLICITUD
			// VERIFICAMOS SI LA SOLICITUD TIENE TRAMITES CON PERSONAS CON
			// CALIFICACION NO VALIDADAS
			if (bSolicitudConTramitesNoValidados) {
				solicitud.setIdEstadoSolicitud(Long.valueOf(EstadoSolicitud.EN_PROCESO));
				solicitud.setDesEstadoSolicitud(EstadoSolicitud.ESTADO_2_EN_PROCESO);
			} else {
				solicitud.setIdEstadoSolicitud(Long.valueOf(EstadoSolicitud.ATENDIDA));
				solicitud.setDesEstadoSolicitud(EstadoSolicitud.ESTADO_3_ATENDIDA);
			}

			// GENERAMOS EL XML DE LA SOLICITUD Y LO ALMACENAMOS
			solicitudModificada = solicitudPersonaEntity.modificar(solicitud);

		} catch (Exception e) {
			this.log.error(e.getMessage(), e);
			throw new SolicitudException(e.getMessage());
		}

		log.trace("SolicitudPersonaBusiness. procesarTramiteSolicitud. Final. " + new Date());
		return solicitudModificada;
	}

	@Override
	public Solicitud procesarSolicitudExistente(final Solicitud solicitud) throws SolicitudException {
		return procesarTramiteSolicitud(modificar(solicitud));
	}

	/**
	 * 191807 301012
	 * Se setean los identificadoresen caso de haberse localizado a la persona en alguna entidad externa
	 */
	public void agregarIdentificadores(Fisica personaFisicaRenapo){

		List<Identificador> identificadores = new ArrayList<Identificador>();

		// Primero se valida que se tenga la CURP
		if(personaFisicaRenapo.getCurp() != null && !personaFisicaRenapo.getCurp().trim().equals("")){

			Identificador identificador = new Identificador();
			Fisica fisicaId = new Fisica();
			fisicaId.setIdPersona(personaFisicaRenapo.getIdPersona());

			TipoIdentificador tipoIdentificador = new TipoIdentificador();
			tipoIdentificador.setIdTipoIdentificador(TipoIdentificadorEnum.CURP.getCodigo());
			tipoIdentificador.setDesIdentificador(TipoIdentificadorEnum.obternerEnumById(tipoIdentificador.getIdTipoIdentificador()).name());

			identificador.setTipoIdentificador(tipoIdentificador);

			identificador.setPersona(fisicaId);
			identificador.setIdentificadora(personaFisicaRenapo.getCurp());
			identificador.setVigente(1);

			identificadores.add(identificador);

		}

		// Luego se valida que se tenga la RFC
		if(personaFisicaRenapo.getRfc() != null && !personaFisicaRenapo.getRfc().trim().equals("")){

			Identificador identificador = new Identificador();
			Fisica fisicaId = new Fisica();
			fisicaId.setIdPersona(personaFisicaRenapo.getIdPersona());

			TipoIdentificador tipoIdentificador = new TipoIdentificador();
			tipoIdentificador.setIdTipoIdentificador(TipoIdentificadorEnum.RFC.getCodigo());
			tipoIdentificador.setDesIdentificador(TipoIdentificadorEnum.obternerEnumById(tipoIdentificador.getIdTipoIdentificador()).name());

			identificador.setTipoIdentificador(tipoIdentificador);

			identificador.setPersona(fisicaId);
			identificador.setIdentificadora(personaFisicaRenapo.getRfc());
			identificador.setVigente(1);

			identificadores.add(identificador);

		}

		personaFisicaRenapo.setIdentificadores(identificadores);

	}

	@Override
	public mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud obtenerSolicitudRegistrada(
			Long idPersona, Long idTipoPersona,
			TipoSolicitudEnum tipoSolicitud, TipoTramiteEnum tipoTramite)
					throws SolicitudException {

		return obtenerSolicitudPersonaCommon(idPersona, idTipoPersona,
				tipoSolicitud, tipoTramite, EstadoSolicitudEnum.REGISTRADA);
	}

	@Override
	public mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud obtenerSolicitudEnProceso(
			Long idPersona, Long idTipoPersona,
			TipoSolicitudEnum tipoSolicitud, TipoTramiteEnum tipoTramite)
					throws SolicitudException {

		return obtenerSolicitudPersonaCommon(idPersona, idTipoPersona,
				tipoSolicitud, tipoTramite, EstadoSolicitudEnum.PENDIENTE_AUTORIZACION);
	}

	/**
	 * Servicio que crea tanto la solicitud como el tr醡ite relacionado a la
	 * actualizaci髇 de datos de una persona y tambi閚 realiza la afectaci髇 en
	 * la base de datos.
	 * 
	 * @param datosRespuesta
	 * @param Usuario con la clave del usuario que realiza el tramite
	 * @param idPersonaInteresadaSolicitud -  el id de la persona interesara, para el caso del portal persona o cualquier otro diferente
	 * al de asegurado y derechohabiente, sera el mismo que el de la persona afectada, para el caso del portal del asegurado o derechohabiente
	 * sera el id de la persona que corresponde al asegurado o al pensionado, mientras que el id de la persona del tramite sera el beneficiario
	 * y en su caso el asegurado o pensionado. El atributo puede venir nulo y no se guardara la relacion entre DitSolicitud y DitPersonaInteresadaSol
	 * @return
	 * @throws SolicitudNoValidaException
	 * @throws AfectacionDatosPersonaException
	 * @throws PersonaNoEncontradaException
	 * @throws RegistroPersonaFisicaException
	 * @throws ComparacionSinDiferenciasException
	 */
	@Override
	public mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud crearTramiteActualizacionDatosPersona(
			ICADatosRespuesta datosRespuesta, Usuario usuario, Long idPersonaInteresadaSolicitud, OrigenSolicitudEnum origenSolicitud)
					throws SolicitudNoValidaException, AfectacionDatosPersonaException,
					PersonaNoEncontradaException, RegistroPersonaFisicaException, ComparacionSinDiferenciasException {

		/*
		 * Se valida si existieron cambios, si es as铆 se crea la solicitud con
		 * el tramite de actualizaci贸n de datos.
		 */
		mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud;
		if (existenDiferencias(datosRespuesta.getCambios())) {

			this.log.debug("Se va a crear la solicitud y el tramite relacionado a la actualizacion de datos de la persona");

			/*
			 * Se agrega el documento probatorio acta de nacimiento de la
			 * persona IMSS a la lista de documentos, para que no se pierda
			 * el objeto al momento de recuperar la informaci贸n
			 */
			if (datosRespuesta.getPersonaFisicaIMSS() != null && 
					datosRespuesta.getPersonaFisicaIMSS().getActaNacimiento() != null) {
				datosRespuesta.getPersonaFisicaIMSS().getDocumentosProbatorios()
				.add(datosRespuesta.getPersonaFisicaIMSS().getActaNacimiento());
			}

			boolean isFisica = true;

			/*
			 * Se crea el sujeto obligado y tramite asociado a la solicitud
			 * dependiendo del tipo de persona
			 */
			if (datosRespuesta.getPersonaMoralIMSS() != null) {
				isFisica = false;
			}

			mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite tramite = getTramiteAsociadoByTipoPersona(
					isFisica, datosRespuesta);

			SujetoObligado sujetoObligado = new SujetoObligado();
			if (isFisica) {
				Fisica fisica = new Fisica();
				fisica.setIdPersona(datosRespuesta.getPersonaFisicaIMSS().getIdPersona());
				fisica.setCveFisica(datosRespuesta.getPersonaFisicaIMSS().getCveFisica());

				sujetoObligado.setFisica(fisica);
			} else {
				Moral moral = new Moral();
				moral.setIdPersona(datosRespuesta.getPersonaMoralIMSS().getIdPersona());
				moral.setCveMoral(datosRespuesta.getPersonaMoralIMSS().getCveMoral());

				sujetoObligado.setMoral(moral);
			}

			tramite.setAcuseVentanilla(new AcuseVentanilla());
			if (usuario != null) {
				tramite.getAcuseVentanilla().setUsuarioVentanilla(usuario.getUsuario());
				UsuarioFuncionario usuarioFuncionario = usuario.getUsuarioFuncionario();
				if (usuarioFuncionario != null
						&& usuarioFuncionario.getSubdelegacion() != null) {
					tramite.getAcuseVentanilla().setIdSubdelegacion(
							usuarioFuncionario.getSubdelegacion().getId());
				}
			}

			PersonaInteresadaSolicitud personaInteresadaSol;
			if (idPersonaInteresadaSolicitud != null) {
				personaInteresadaSol = new PersonaInteresadaSolicitud();

				Fisica fisica = new Fisica();
				fisica.setIdPersona(idPersonaInteresadaSolicitud);
				personaInteresadaSol.setPersona(fisica);

				TipoPerInteresadaSol tipoPersona = new TipoPerInteresadaSol();
				tipoPersona.setCveTipoInteresadaSol(TipoPersonaInteresadaSolEnum.ASEGURADO_PENSIONADO.getId());
				personaInteresadaSol.setTipoPersonaInteresadaSol(tipoPersona);
			} else {
				personaInteresadaSol = null;
			}

			// Se crea la solicitud y el tramite
			solicitud = solicitudBusiness.crearSolicitudInicialPorEnum(EstadoSolicitudEnum.REGISTRADA,
					TipoSolicitudEnum.ACTUALIZACION_DATOS_GENERALES, origenSolicitud, usuario);
			Date fechaActual = new Date();
			solicitud.setFechaPresentacion(fechaActual);
			solicitud.setPersonaInteresadaSolicitud(personaInteresadaSol);
			solicitud.setSujetoObligado(sujetoObligado);

			List<mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite> tramites =
					new ArrayList<mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite>();
			tramites.add(tramite);
			solicitud.setTramites(tramites);

			solicitud = solicitudBusiness.crear(solicitud);

			log.debug("La solicitud [ id=" + solicitud.getSolicitudId()
			+ " | folio=" + solicitud.getNoFolioSolicitud()
			+ "] fue creada exitosamente.");
		} else {
			log.warn("No exitieron cambios en la comparaci贸n, por lo tanto, no se cre贸 la solicitud");
			solicitud = null;
			throw new ComparacionSinDiferenciasException();
		}

		return solicitud;
	}

	@Override
	public boolean existenDiferencias(
			Map<String, CambioComparacionEnum> diferencias) {
		return afectarDatosPersonaUtility.existenDiferencias(diferencias);
	}

	@Override
	public mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite getTramiteAsociadoByTipoPersona(
			boolean isFisica, ICADatosRespuesta datosRespuesta) {
		mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite tramite;

		if (isFisica) {
			tramite = new TramiteFisica();
			((TramiteFisica) tramite).setDatosICA(datosRespuesta);
			((TramiteFisica) tramite).setFisica(datosRespuesta
					.getPersonaFisicaIMSS());
		} else {
			tramite = new TramiteMoral();
			((TramiteMoral) tramite).setDatosICA(datosRespuesta);
			((TramiteMoral) tramite).setMoral(datosRespuesta
					.getPersonaMoralIMSS());
		}

		mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite tramiteInicializado;
		try {
			tramiteInicializado = solicitudBusiness.inicializarTramitePorEnum(tramite,
					TipoTramiteEnum.ACTUALIZACION_DATOS_GENERALES, EstadoTramiteEnum.INICIADO);
		} catch (SolicitudNoValidaException e) {
			tramiteInicializado = null;
		}

		return tramiteInicializado;
	}

	@Override
	public mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud cancelarSolicitud(Long idSolicitud)
			throws SolicitudNoEncontradaException, TramiteNoEncontradoException {

		mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud = new mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud();
		solicitud.setSolicitudId(idSolicitud);

		mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud estadoSolicitud = new mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud();
		estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.CANCELADA
				.getCodigo());
		solicitud.setEstadoSolicitud(estadoSolicitud);

		solicitud = this.solicitudBusiness.actualizarEstados(solicitud);

		return solicitud;
	}

	@Override
	public Map<String, Object> retomarSolicitudActualizacionDatos(
			Long idSolicitud) throws SolicitudNoEncontradaException,
	SolicitudNoValidaException {

		Map<String, Object> resultado = new HashMap<String, Object>();

		mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud = new mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud();
		solicitud.setSolicitudId(idSolicitud);

		solicitud = this.solicitudBusiness.consultar(solicitud);

		// Se checa que la solicitud encontrada tenga informaci贸n de ICA
		for (mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite tramite : solicitud
				.getTramites()) {
			if (tramite instanceof TramiteFisica) {
				TramiteFisica tramiteFisica = (TramiteFisica) tramite;
				if (tramiteFisica.getDatosICA() != null) {

					ICADatosRespuesta datosICA = tramiteFisica.getDatosICA(); 

					/*
					 * Se recorre la lista de documentos probatorios para buscar
					 * si existe acta de nacimiento y se agrega al atributo
					 * correspondiente, se hace esto porque el atributo
					 * Nacimiento del objeto Persona tiene la anotaci贸n
					 * 
					 * @XMLTransient y por lo tanto, no se serializa
					 */
					for(DocumentoProbatorio documento : datosICA.getPersonaFisicaIMSS().getDocumentosProbatorios()) {
						if(documento instanceof Nacimiento) {
							datosICA.getPersonaFisicaIMSS().setActaNacimiento((Nacimiento) documento);
						}
					}

					for(DocumentoProbatorio documento : datosICA.getPersonaFisicaEE().getDocumentosProbatorios()) {
						if(documento instanceof Nacimiento) {
							datosICA.getPersonaFisicaEE().setActaNacimiento((Nacimiento) documento);
						}
					}

					resultado.put("solicitud", solicitud);
					resultado.put("datosICA", datosICA);
					break;
				}
			} else if (tramite instanceof TramiteMoral) {
				TramiteMoral tramiteMoral = (TramiteMoral) tramite;
				if (tramiteMoral.getDatosICA() != null) {
					resultado.put("solicitud", solicitud);
					resultado.put("datosICA", tramiteMoral.getDatosICA());
					break;
				}
			}
		}

		// Si no se encotraron datos ICA, se lanza una excepcion
		if (resultado.isEmpty()) {
			throw new SolicitudNoValidaException(
					"La solicitud a retomar no cuenta con informaci贸n de actualizaci贸n de datos");
		}

		return resultado;
	}

	@Override
	public void guardarSolicitudActualizacionDatos(Long idSolicitud,
			ICADatosRespuesta datosRespuesta)
					throws SolicitudNoEncontradaException, TramiteNoEncontradoException {

		mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud = new mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud();
		solicitud.setSolicitudId(idSolicitud);

		// Se busca la solicitud
		solicitud = this.solicitudBusiness.consultar(solicitud);

		/* Se recorren los tramites de la solicitud, hasta encontrar
		 * el tramite relacionado al ICA, una vez encontrado
		 * se settea el objeto de ICA
		 */
		for (mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite tramite : solicitud
				.getTramites()) {
			if (tramite instanceof TramiteFisica) {
				TramiteFisica tramiteFisica = (TramiteFisica) tramite;
				if (tramiteFisica.getDatosICA() != null) {
					tramiteFisica.setDatosICA(datosRespuesta);
					break;
				}  
			} else if (tramite instanceof TramiteMoral) {
				TramiteMoral tramiteMoral = (TramiteMoral) tramite;
				if (tramiteMoral.getDatosICA() != null) {
					tramiteMoral.setDatosICA(datosRespuesta);
					break;
				}
			}
		}

		// Se actualiza el tramite de la solicitud
		this.solicitudBusiness.actualizarTramites(solicitud);
	}

	@Override
	public void finalizarSolicitudActualizacionDatos(Long idSolicitud)
			throws SolicitudNoEncontradaException, SolicitudNoValidaException,
			AfectacionDatosPersonaException, PersonaNoEncontradaException, TramiteNoEncontradoException {

		mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud = new mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud();
		solicitud.setSolicitudId(idSolicitud);

		Boolean tramiteDerechohabientes = false;
		Long idPersonaInteresadaSol = null;

		// Se busca la solicitud en la base de datos
		solicitud = this.solicitudBusiness.consultar(solicitud);

		//Verificamos si la solicitud contiene el atributo de persona interesada
		if(solicitud.getPersonaInteresadaSolicitud() != null) {
			idPersonaInteresadaSol = solicitud.getPersonaInteresadaSolicitud().getPersona().getIdPersona();
		}

		/*
		 * Se recorren los tramites de la solicitud, hasta encontrar el tramite
		 * relacionado al ICA, una vez encontrado se settea el objeto de ICA
		 */
		ICADatosRespuesta datosRespuesta = null;
		Long idTramite = null;
		for (mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite tramite : solicitud
				.getTramites()) {
			if (tramite instanceof TramiteFisica) {
				TramiteFisica tramiteFisica = (TramiteFisica) tramite;
				if (tramiteFisica.getDatosICA() != null) {

					datosRespuesta = tramiteFisica.getDatosICA();
					// Se integran los cambios entre las entidades comparadas
					datosRespuesta = this.personaFisicaServiceBusiness.integrarCambios(datosRespuesta);

					/* 
					 * Debido a que el servicio de integraci贸n de los cambios, no
					 * devuelve los documentos probatorios en la lista, s贸lo los trae
					 * en cada uno de los atributos, es necesario pasarlos a la lista
					 */
					Fisica fisica = datosRespuesta.getPersonaFisicaIMSS();

					List<GrupoFamiliar> grupos = componentesExternosBusiness.obtenerGruposPorIdPersonaYPersonaInteresada(fisica.getIdPersona(),
							idPersonaInteresadaSol);

					if(grupos != null && !grupos.isEmpty()) {
						GrupoFamiliar grupoEncontrado = grupos.get(0);
						if(!grupoEncontrado.getEstadoDerechohabiente().getIdEstadoDerechohabiente().equals(EstadoDerechohabienteEnum.BAJA.getId())){
							Fisica personaEE = datosRespuesta.getPersonaFisicaEE();
							log.debug("El grupo familiar encontrado es: " + grupoEncontrado);
							componentesExternosBusiness.saveTramiteCorreccionDatosDerechohabiente(grupoEncontrado, personaEE, idSolicitud);
						}
					}

					if(fisica.getDocumentosProbatorios() == null) {
						fisica.setDocumentosProbatorios(new ArrayList<DocumentoProbatorio>());
					}

					if (fisica.getActaNacimiento() != null) {
						fisica.getDocumentosProbatorios().add(fisica.getActaNacimiento());
					} else if (fisica.getDocumentoMigratorio() != null) {
						fisica.getDocumentosProbatorios().add(fisica.getDocumentoMigratorio());
					} else if (fisica.getCartaNaturalizacion() != null) {
						fisica.getDocumentosProbatorios().add(fisica.getCartaNaturalizacion());
					} else if (fisica.getNumeroUnicoExtranjero() != null) {
						fisica.getDocumentosProbatorios().add(fisica.getNumeroUnicoExtranjero());
					} else if (fisica.getCertificadoNacionalidadMexicana() != null) {
						fisica.getDocumentosProbatorios().add(fisica.getCertificadoNacionalidadMexicana());
					} else if (fisica.getOficioSolicitanteRefugiado() != null) {
						fisica.getDocumentosProbatorios().add(fisica.getOficioSolicitanteRefugiado());
					} else if (fisica.getFormaMigratoriaTurista() != null) {
						fisica.getDocumentosProbatorios().add(fisica.getFormaMigratoriaTurista());
					}


					idTramite = tramiteFisica.getTramiteId();
					break;
				}
			} else if (tramite instanceof TramiteMoral) {
				TramiteMoral tramiteMoral = (TramiteMoral) tramite;
				if (tramiteMoral.getDatosICA() != null) {
					datosRespuesta = tramiteMoral.getDatosICA();
					// Se integran los cambios entre las entidades comparadas
					datosRespuesta = this.personaMoralBusiness.integrarCambios(datosRespuesta);
					idTramite = tramiteMoral.getTramiteId();
					break;
				}
			}
		}

		// Se afectan los cambios en la base de datos
		if (datosRespuesta != null) {

			TramiteCambioInformacionPersona tramiteCambioInformacionPersona = new TramiteCambioInformacionPersona();
			tramiteCambioInformacionPersona.setDatosICA(datosRespuesta);
			tramiteCambioInformacionPersona.setTramiteId(idTramite);

			Modulo moduloOrigen = new Modulo();
			moduloOrigen.setIdModulo(ModuloEnum.PERSONAS.getCodigo()
					.longValue());

			this.afectarDatosPersonaBusiness.afectarDatos(
					tramiteCambioInformacionPersona, moduloOrigen);

			/*TODO se comenta el uso de la marca de acreditado a peticion del usuario JMLL 02/01/2018
			Persona persona = datosRespuesta.getPersonaFisicaIMSS()!=null ? datosRespuesta.getPersonaFisicaIMSS() 
					: datosRespuesta.getPersonaMoralIMSS();
			this.personaBusiness.reportarAcreditacion(persona);
			 **/
		} else {
			throw new SolicitudNoValidaException(
					"La solicitud de actualizaci贸n de datos que se quiere finalizar no cuenta con datos qu茅 afectar");
		}

		/* 
		 * Se cambia el estado de la solicitud a CONCLUIDA
		 * y publica la finalizacion en el COMET
		 */
		this.solicitudBusiness.actualizarSolicitudAEstatusConcluida(solicitud.getSolicitudId());
	}


	@Override
	public mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud crearTramiteModificacionDatosPersona(
			MDMDatosEntrada datosEntrada, Usuario usuario) throws SolicitudNoValidaException {
		return this.crearTramiteModificacionDatosPersona(datosEntrada, usuario, null);
	}


	@Override
	public mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud crearTramiteModificacionDatosPersona(
			MDMDatosEntrada datosEntrada, Usuario usuario, Long idPersonaInteresadaSolicitud) throws SolicitudNoValidaException {
		/*
		 * Se crea el sujeto obligado y tramite asociado a la solicitud
		 * dependiendo del tipo de persona
		 */
		boolean isFisica;
		if (datosEntrada.getPersonaMoral() != null) {
			isFisica = false;
		} else {
			isFisica = true;
		}

		SujetoObligado sujetoObligado = new SujetoObligado();

		mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite tramite;
		TipoTramiteEnum tipoTramiteEnum = null;
		if (isFisica) {
			Fisica fisica = new Fisica();
			fisica.setIdPersona(datosEntrada.getPersonaFisica().getIdPersona());
			fisica.setCveFisica(datosEntrada.getPersonaFisica().getCveFisica());

			sujetoObligado.setFisica(fisica);

			tramite = new TramiteFisica();
			((TramiteFisica) tramite).setDatosMDM(datosEntrada);
			((TramiteFisica) tramite).setFisica(fisica);

			/*
			 * De acuerdo a la bandera que se traiga en los datos de entrada es
			 * el tipo de tramite que se usa
			 */
			if (datosEntrada.getIndCapturaMediosContactoParticular()) {
				tipoTramiteEnum = TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO;
			} else if (datosEntrada.getIndCapturaDomicilioParticular()) {
				tipoTramiteEnum = TipoTramiteEnum.ACTUALIZACION_DOMICILIO_PARTICULAR;
			} else if (datosEntrada.getIndAsignacionDomicilio()) {
				tipoTramiteEnum = TipoTramiteEnum.ASIGNACION_DE_DOMICILIO_PARTICULAR_DH;
			} else if (datosEntrada.getIndActualizacionDomicilioDerechohabiente()) {
				tipoTramiteEnum = TipoTramiteEnum.ACTUALIZACION_DOMICILIO_PARTICULAR;
			} else if (datosEntrada.getIndCambioClinica()) {
				tipoTramiteEnum = TipoTramiteEnum.CAMBIO_CLINICA;
			}
		} else {
			Moral moral = new Moral();
			moral.setIdPersona(datosEntrada.getPersonaMoral().getIdPersona());
			moral.setCveMoral(datosEntrada.getPersonaMoral().getCveMoral());

			sujetoObligado.setMoral(moral);

			tramite = new TramiteMoral();
			((TramiteMoral) tramite).setDatosMDM(datosEntrada);
			((TramiteMoral) tramite).setMoral(moral);

			/*
			 * De acuerdo a la bandera que se traiga en los datos de entrada es
			 * el tipo de tramite que se usa
			 */
			if (datosEntrada.getIndCapturaMediosContactoParticular()) {
				tipoTramiteEnum = TipoTramiteEnum.ACTUALIZACION_DATOS_CONTACTO;
			} else if (datosEntrada.getIndCapturaDomicilioParticular()) {
				tipoTramiteEnum = TipoTramiteEnum.ACTUALIZACION_DOMICILIO_PARTICULAR;
			} else if (datosEntrada.getIndAsignacionDomicilio()) {
				tipoTramiteEnum = TipoTramiteEnum.ASIGNACION_DE_DOMICILIO_PARTICULAR_DH;
			} else if (datosEntrada.getIndCambioClinica()) {
				tipoTramiteEnum = TipoTramiteEnum.CAMBIO_CLINICA;
			}
		}

		// Datos generales de la solicitud
		OrigenSolicitudEnum origenSolicitudInicial;
		if (datosEntrada.getOrigen() != null
				&& OrigenSolicitudEnum.getById(datosEntrada.getOrigen()) != null) {
			origenSolicitudInicial = OrigenSolicitudEnum.getById(datosEntrada.getOrigen());
		} else {
			origenSolicitudInicial = null;
		}

		TipoSolicitudEnum tipoSolicitudInicial;
		Long idTipoSolicitud = datosEntrada.getIdTipoSolicitud();
		if (idTipoSolicitud != null) {
			tipoSolicitudInicial = TipoSolicitudEnum.obtenerEnumById(idTipoSolicitud.intValue());
		} else {
			tipoSolicitudInicial = TipoSolicitudEnum.ACTUALIZACION_DATOS_GENERALES;
		}

		PersonaInteresadaSolicitud personaInteresadaSol;
		if (idPersonaInteresadaSolicitud != null) {
			personaInteresadaSol = new PersonaInteresadaSolicitud();

			Fisica fisica = new Fisica();
			fisica.setIdPersona(idPersonaInteresadaSolicitud);
			personaInteresadaSol.setPersona(fisica);

			TipoPerInteresadaSol tipoPersona = new TipoPerInteresadaSol();
			tipoPersona.setCveTipoInteresadaSol(TipoPersonaInteresadaSolEnum.ASEGURADO_PENSIONADO.getId());
			personaInteresadaSol.setTipoPersonaInteresadaSol(tipoPersona);
		} else {
			personaInteresadaSol = null;
		}

		mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud = solicitudBusiness.crearSolicitudInicialPorEnum(
				EstadoSolicitudEnum.REGISTRADA, tipoSolicitudInicial, origenSolicitudInicial, usuario);
		Date fechaActual = new Date();
		solicitud.setFechaPresentacion(fechaActual);
		solicitud.setSujetoObligado(sujetoObligado);
		solicitud.setPersonaInteresadaSolicitud(personaInteresadaSol);

		solicitud = solicitudBusiness.asociarTramiteSolicitudPorEnum(solicitud, tramite, tipoTramiteEnum,
				EstadoTramiteEnum.INICIADO);

		// Se crea la solicitud y el tramite
		solicitud = this.solicitudBusiness.crear(solicitud);

		this.log.debug("La solicitud [ id=" + solicitud.getSolicitudId()
		+ " | folio=" + solicitud.getNoFolioSolicitud()
		+ "] fue creada exitosamente.");

		return solicitud;
	}

	@Override
	public Map<String, Object> retomarSolicitudModificacionDatosPersona(
			Long idSolicitud) throws SolicitudNoEncontradaException,
	SolicitudNoValidaException {

		Map<String, Object> resultado = new HashMap<String, Object>();

		mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud = new mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud();
		solicitud.setSolicitudId(idSolicitud);

		solicitud = this.solicitudBusiness.consultar(solicitud);

		/*
		 * Se checa que la solicitud encontrada tenga informaci髇 de
		 * Modificaci髇 Manual
		 */
		for (mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite tramite : solicitud
				.getTramites()) {
			if (tramite instanceof TramiteFisica) {
				TramiteFisica tramiteFisica = (TramiteFisica) tramite;
				if (tramiteFisica.getDatosMDM() != null) {

					MDMDatosEntrada datosModif = tramiteFisica.getDatosMDM(); 

					resultado.put("solicitud", solicitud);
					resultado.put("datosModif", datosModif);
					break;
				}
			} else if (tramite instanceof TramiteMoral) {
				TramiteMoral tramiteMoral = (TramiteMoral) tramite;
				if (tramiteMoral.getDatosMDM() != null) {

					MDMDatosEntrada datosModif = tramiteMoral.getDatosMDM(); 

					resultado.put("solicitud", solicitud);
					resultado.put("datosModif", datosModif);
				}
			}
		}

		// Si no se encotraron datos ICA, se lanza una excepcion
		if (resultado.isEmpty()) {
			throw new SolicitudNoValidaException(
					"La solicitud a retomar no cuenta con informaci髇 de actualizaci髇 de datos");
		}

		return resultado;
	}

	@Override
	public void guardarSolicitudModificacionDatosPersona(
			mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud,
			MDMDatosEntrada datosModif)
					throws SolicitudNoEncontradaException, TramiteNoEncontradoException {
		/* Se recorren los tramites de la solicitud, hasta encontrar
		 * el tramite relacionado a la modificaci贸n, una vez encontrado
		 * se settea el objeto de la modificaci贸n
		 */
		for (mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite tramite : solicitud
				.getTramites()) {
			if (tramite instanceof TramiteFisica) {
				TramiteFisica tramiteFisica = (TramiteFisica) tramite;
				if (tramiteFisica.getDatosMDM() != null) {
					tramiteFisica.setDatosMDM(datosModif);
					break;
				}  
			} else if (tramite instanceof TramiteMoral) {
				TramiteMoral tramiteMoral = (TramiteMoral) tramite;
				if (tramiteMoral.getDatosMDM() != null) {
					tramiteMoral.setDatosMDM(datosModif);
					break;
				}
			}
		}

		// Se actualiza el tramite de la solicitud
		this.solicitudBusiness.actualizarTramites(solicitud);
	} 

	@Override
	public void finalizarSolicitudModificacionDatosPersona(
			mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud,
			MDMDatosEntrada datosModif,
			FirmaElectronica firmaElectronica) throws SolicitudNoEncontradaException, TramiteNoEncontradoException, SolicitudException {
		guardarSolicitudModificacionDatosPersona(solicitud, datosModif);
		solicitudBusiness.enviarSolicitudAProceso(solicitud, firmaElectronica);
	}

	@Override
	public void finalizarSolicitudModificacionDatosPersona(Long idSolicitud)
			throws SolicitudNoEncontradaException,
			AfectacionDatosPersonaException, PersonaNoEncontradaException,
			SolicitudNoValidaException, TramiteNoEncontradoException,
			ErrorComparacionDatosRENAPOException,
			PersonaFisicaNoEncontradaException {

		Long idTramite = null;
		MDMDatosEntrada datosModif = null;
		mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud = new mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud();
		solicitud.setSolicitudId(idSolicitud);

		// Se busca la solicitud en la base de datos
		solicitud = this.solicitudBusiness.consultar(solicitud);

		/*
		 * Se recorren los tramites de la solicitud, hasta encontrar el tramite
		 * relacionado a la modificaci贸n, una vez encontrado se settea el objeto
		 * de la modificaci贸n
		 */
		for (mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite tramite : solicitud
				.getTramites()) {
			if (tramite instanceof TramiteFisica) {
				TramiteFisica tramiteFisica = (TramiteFisica) tramite;
				if (tramiteFisica.getDatosMDM() != null) {
					datosModif = tramiteFisica.getDatosMDM(); 
					idTramite = tramiteFisica.getTramiteId();
					break;
				}
			} else if (tramite instanceof TramiteMoral) {
				TramiteMoral tramiteMoral = (TramiteMoral) tramite;
				if (tramiteMoral.getDatosMDM() != null) {
					datosModif = tramiteMoral.getDatosMDM();
					idTramite = tramiteMoral.getTramiteId();
					break;
				}
			}
		}

		// Se afectan los cambios en la base de datos
		if (datosModif != null) {

			// Se actualiza el tramite de la solicitud
			this.solicitudBusiness.actualizarTramites(solicitud);

			TramiteCambioInformacionPersona tramiteCambioInformacionPersona = new TramiteCambioInformacionPersona();
			tramiteCambioInformacionPersona.setDatosModifManual(datosModif);
			tramiteCambioInformacionPersona.setTramiteId(idTramite);

			Modulo moduloOrigen = new Modulo();
			moduloOrigen.setIdModulo(ModuloEnum.PERSONAS.getCodigo()
					.longValue());

			this.afectarDatosPersonaBusiness.afectarDatos(
					tramiteCambioInformacionPersona, moduloOrigen);

			/* 
			 * Se cambia el estado de la solicitud a CONCLUIDA
			 * y publica la finalizacion en el COMET
			 */
			this.solicitudBusiness.actualizarSolicitudAEstatusConcluida(solicitud.getSolicitudId());

		} else {
			throw new SolicitudNoValidaException(
					"La solicitud de actualizaci贸n de datos que se quiere finalizar no cuenta con datos qu茅 afectar");
		}
	}

	@Override
	public mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud generaSolicitudUsuarioSSO(mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud) 
			throws SolicitudNoValidaException, IllegalArgumentException{
		if(solicitud == null){
			throw new IllegalArgumentException("La solicitud no puede ser nula");
		}
		return this.solicitudBusiness.crear(solicitud);

	}


	private mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud obtenerSolicitudPersonaCommon(
			Long idPersona, Long idTipoPersona,
			TipoSolicitudEnum tipoSolicitud, TipoTramiteEnum tipoTramite,
			EstadoSolicitudEnum estadoSolicitud) throws SolicitudException {

		TipoPersonaEnum tipoPersona = null;

		if (idTipoPersona.equals(TipoPersona.TIPO_PERSONA_FISICA)) {
			tipoPersona = TipoPersonaEnum.FISICA;
		} else {
			tipoPersona = TipoPersonaEnum.MORAL;
		}

		mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud = this.solicitudBusiness
				.obtenerSolicitudDePersonaPorTipoSolicitudTramiteyEstado(idPersona, tipoPersona, tipoSolicitud, tipoTramite, estadoSolicitud);

		return solicitud;

	}

	@Override
	public Fisica creaCuentaUsuararioSSO(mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud, FirmaElectronica firmaElectronica) 
			throws DomicilioNoValidoException, SolicitudNoEncontradaException, TramiteNoEncontradoException, RegistroPersonaFisicaException,
			UsuarioNoRegistradoEnEsquemaDeSeguridadException, PersonaNoEncontradaException{
		TramiteFisica tramiteFisica  = null;
		this.log.debug("entre a generar confirmar la solicitud con datos ");
		mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite tramite = solicitud.getTramites().get(0);

		tramiteFisica = (TramiteFisica) tramite;
		Fisica objFisica =tramiteFisica.getFisica();
		Date fechaRegistro = new Date();
		boolean usuarioGuardadoEsquemaSeguridad=false;

		List<mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite> lstTramiteReg = new ArrayList();
		//se parsea el tramite a fisica y se valida si no tiene id de persona
		//si no existe la persona se manda a salvar
		boolean personaNueva = false;
		try{
			
			// Se realiza proceso de Identificacion de Cambios Automaticos para realizar las modificaciones
			log.debug("Aplicando modificaciones de ICA");
			ICADatosConsulta icaDatosConsulta = new ICADatosConsulta();
			icaDatosConsulta.setPersonaFisica(objFisica);
			icaDatosConsulta.setIndicadorConsultaRENAPO(Boolean.TRUE);
			icaDatosConsulta.setIndicadorConsultaSAT(Boolean.TRUE);
			icaDatosConsulta.setIndicadorMostrarPantalla(Boolean.FALSE);

			try {
				ICADatosRespuesta icaDatosRespuesta = personaFisicaServiceBusiness.identificarCambios(icaDatosConsulta);
				ICADatosRespuesta icaDatosRespuestaFinal = personaFisicaServiceBusiness.integrarCambios(icaDatosRespuesta);
				TramiteCambioInformacionPersona tramiteIca = new TramiteCambioInformacionPersona();
				tramiteIca.setDatosICA(icaDatosRespuestaFinal);

				AfectarDatosPersonaWrapper datosPersona = afectarDatosPersonaUtility.crearWrapperDesdeICA(tramiteIca);
				if (personaNueva) {
					datosPersona.setModificarDocumentoProbatorio(false);
				}
				afectarDatosPersonaBusiness.afectarDatosPersonaFisica(datosPersona);
			}  catch (ComparacionSinDiferenciasException e) {
				log.info("No se aplica modificacion en ICA");
			}

			log.debug("modificaciones de ICA aplicadas");
			Usuario usuario = new Usuario();
			usuario.setFisica(objFisica);
			usuario.setPassword(firmaElectronica.getSerialCertificado());

			// se realiza la actualizacion de tramite solicitud y generacion de tramite persona fisica
			mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite objEstadoTramite = 
					new mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite ();
			objEstadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());
			tramiteFisica.setEstadoTramite(objEstadoTramite);
			tramiteFisica.setFechaConclusion(fechaRegistro);
			tramiteFisica.setFechaTramite(fechaRegistro);
			RazonResultado objRazonReslt = new RazonResultado();
			objRazonReslt.setIdRazonResultado(RazonResultadoEnum.NORMAL.getCodigo().longValue());
			tramiteFisica.setRazonResultado(objRazonReslt);
			tramiteFisica.setResultado(true);
			tramiteFisica.setFisica(objFisica);

			//se setea el tipo de tramite
			TipoTramite tipoTramite = new TipoTramite();
			tipoTramite.setIdTipoTramite(TipoTramiteEnum.ALTA_USUARIOS_SSO.getCodigo());
			tramite.setTipoTramite(tipoTramite);
			lstTramiteReg.add(tramiteFisica);

			//se llenan la info de la solicitud para darla por cerrada
			solicitud.setTramites(null);
			solicitud.setTramites(lstTramiteReg);

			mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud estadoSolicitud = new mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud();
			estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.ATENDIDA.getCodigo());
			solicitud.setEstadoSolicitud(estadoSolicitud);
			solicitud.setFechaActualizacion(fechaRegistro);
			solicitud.setFechaConclusion(fechaRegistro);
			//se setea el usuario a la solicitud
			usuario.setUsuario(objFisica.getCurp());
			solicitud.setSolicitante(usuario);		
			//se actualiza el estado del tramite y la solicitud para que se den por atendidas
			mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitudCreada = this.solicitudBusiness.crear(solicitud);
			this.log.debug("Solicitud creada ...." + solicitudCreada);


			// Se agregan los datos de la firma digital a la solicitud
			solicitud.setSolicitudId(solicitudCreada.getSolicitudId());

			if (firmaElectronica != null) {
				firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud, firmaElectronica);
			}

			//this.solicitudBusiness.actualizarSolicitudAEstatusConcluida(solicitudCreada);
			Long idTramiteUsuario = null;

			if(firmaElectronica != null) {
				solicitudCreada.setNumeroSerieCertificado(firmaElectronica.getSerialCertificado());
				solicitudCreada.setSecuenciaDeNotaria(firmaElectronica.getReciboNotarial());
				solicitudCreada.setCadenaOriginal(firmaElectronica.getCadenaOriginal());

				//Se almacena la informaci髇 del certificado de la persona registrada
				Fiel fiel = new Fiel();
				fiel.setClaveSerial(firmaElectronica.getSerialCertificado());
				fiel.setFechaValidaInicio(firmaElectronica.getIniciaVigenciaCertificado());
				fiel.setFechaValidaFin(firmaElectronica.getFinVigenciaCertificado());
				TipoPersona tipoPersona = new TipoPersona();
				tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
				objFisica.setTipoPersona(tipoPersona);
				objFisica.setFiel(fiel);
				personaBusiness.registrarDatosCertificadoPersona(objFisica);

				if (firmaElectronica.getRecibo().length() > 1500) {
					solicitud.setSelloDigital(firmaElectronica.getRecibo().substring(0, 1500)); //Falta cambiar algo
				} else {
					solicitud.setSelloDigital(firmaElectronica.getRecibo());
				}
			}
			for(mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite tramiteA: solicitudCreada.getTramites()) {
				if(tramiteA.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ALTA_USUARIOS_SSO.getCodigo())) {
					idTramiteUsuario = tramiteA.getTipoTramite().getIdTipoTramite().longValue();
					break;
				}
			}
			try {
				this.solicitudBusiness.obtenerDocumentoResultante(solicitudCreada,idTramiteUsuario,DocumentoPorTipoEnum.CARTA_TERMINOS_FIEL.getId().intValue());
			}catch (Exception e) {
				log.error("error al generar la carta de terminos de registros de usuario", e);
			}
			//se realiza la llamada para crear la cuenta en el SSO
			try {
				componentesExternosBusiness.crearUsuarioEsquemaSeguridad(usuario, solicitud.getSolicitudId());
				usuarioGuardadoEsquemaSeguridad = true;
			}catch (Exception e) {
				log.error("ocurrio un error al dar de alta el usuario en el openAM" , e);
				usuarioGuardadoEsquemaSeguridad = false;
				throw new Exception("ocurrio un error al dar de alta el usuario en el openAM " + e.getMessage());
			}

		}catch(Exception ex){
			this.log.error("ocurrio un error al dar de alta al usuario ", ex);
			if(usuarioGuardadoEsquemaSeguridad){
				try{
					componentesExternosBusiness.eliminaUsuarioEsquemaSeguridadByCURP(objFisica.getCurp());
				}catch(Exception e){
					log.error("ocurrio un error al dar de baja al usuario en el openAM" , e);
					//throw new UsuarioNoRegistradoEnEsquemaDeSeguridadException(); 
				}
			}
			throw new UsuarioNoRegistradoEnEsquemaDeSeguridadException(ex.getMessage());
		}
		return objFisica;
	}

	@Override
	public void actualizaUsuarioEsquemaSeguridad(Usuario objUsuario,  FirmaElectronica firmaElectronica ) throws ActualizaUsuarioEsquemaSeguridadException, EsquemaSegurdiadException{

		//iniciazion de variables para crear el tramite, y la solicitud
		Date fechaRegistro = new Date();

		TramiteFisica tramiteFisica = new TramiteFisica();
		Fisica usuarioFisica = objUsuario.getFisica();

		List<mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite> lstTramiteReg = new ArrayList();

		mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite objEstadoTramite = 
				new mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite ();

		mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud = 
				new mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud();


		objEstadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());
		tramiteFisica.setEstadoTramite(objEstadoTramite);
		tramiteFisica.setFechaConclusion(fechaRegistro);
		tramiteFisica.setFechaTramite(fechaRegistro);
		RazonResultado objRazonReslt = new RazonResultado();
		objRazonReslt.setIdRazonResultado(RazonResultadoEnum.NORMAL.getCodigo().longValue());
		tramiteFisica.setRazonResultado(objRazonReslt);
		tramiteFisica.setResultado(true);
		tramiteFisica.setFisica(usuarioFisica);
		//se setea el tipo de tramite
		TipoTramite tipoTramite = new TipoTramite();
		tipoTramite.setIdTipoTramite(TipoTramiteEnum.ACTUALIZACION_CUENTA_USUARIO.getCodigo());
		tramiteFisica.setTipoTramite(tipoTramite);

		lstTramiteReg.add(tramiteFisica);
		//se llenan la info de la solicitu para darla por cerrada
		solicitud.setTramites(lstTramiteReg);
		solicitud.setTipoSolicitud(new TipoSolicitud());
		solicitud.getTipoSolicitud().setIdTipoSolicitud(TipoSolicitudEnum.REGISTRO_USUARIOS_SSO.getValor().longValue());
		solicitud.setSolicitante(objUsuario);

		mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud estadoSolicitud = new mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud();
		estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.ATENDIDA.getCodigo());
		solicitud.setEstadoSolicitud(estadoSolicitud);
		solicitud.setFechaActualizacion(fechaRegistro);
		solicitud.setFechaConclusion(fechaRegistro);

		//se setea el usuario para la solicitud
		objUsuario.setUsuario(objUsuario.getFisica().getCurp());
		solicitud.setSolicitante(objUsuario);
		//se maneja aparte el guardado de la solicitud debido a que si aqui algo truena tengo que seguir con el flujo
		try {
			//se actualiza el estado del tramite y la solicitud para que se den por atendidas
			mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitudCreada = this.solicitudBusiness.crear(solicitud);
			this.log.debug("Solicitud creada ...." + solicitudCreada);
			solicitud.setSolicitudId(solicitudCreada.getSolicitudId());

			if (firmaElectronica != null) {
				firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud, firmaElectronica);
				//Se almacena la informaci髇 del certificado de la persona registrada
				Fiel fiel = new Fiel();
				fiel.setClaveSerial(firmaElectronica.getSerialCertificado());
				fiel.setFechaValidaInicio(firmaElectronica.getIniciaVigenciaCertificado());
				fiel.setFechaValidaFin(firmaElectronica.getFinVigenciaCertificado());
				TipoPersona tipoPersona = new TipoPersona();
				tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
				usuarioFisica.setTipoPersona(tipoPersona);
				usuarioFisica.setFiel(fiel);
				personaBusiness.registrarDatosCertificadoPersona(usuarioFisica);

			}

			this.solicitudBusiness.actualizarSolicitudAEstatusConcluida(solicitud);
		}catch(Exception ex){
			this.log.error("ocurrio un error al generar la solicitud de actualizacion de usuario ", ex);
			throw new ActualizaUsuarioEsquemaSeguridadException( ex.getMessage());
		}
		//se genera un bloque especifico para la actualizacion del registro en esquema de seguridad para arrojar excepcion si algo mas pasa
		try{
			componentesExternosBusiness.actualizaUsuarioEsquemaSeguridad(objUsuario);
		}catch(Exception e){
			this.log.error("erro al tratar de actualizar al usuario en el esquema de seguridad" ,e);
			throw new ActualizaUsuarioEsquemaSeguridadException( e.getMessage());
		}


	}

	/*
	 * Seccion para la finalizacion de las solicitudes de los tramites:
	 * ASIGNACION_DE_DOMICILIO_PARTICULAR_DH (101)
	 * ACTUALIZACION_DOMICILIO_PARTICULAR(6)
	 * CAMBIO_CLINICA(36)
	 */

	/* 
	 * (non-Javadoc)
	 * @see mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote#finalizarSolicitudAsignacionDomicilio(mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud)
	 */
	@Override
	public mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud 
	finalizarSolicitudAsignacionDomicilio(mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud) 
			throws SolicitudNoEncontradaException, DomicilioNoLocalizadoException {

		if(solicitud != null && solicitud.getSolicitudId() != null){
			FirmaElectronica firma = solicitud.getFirmaElectronica();
			solicitud = this.solicitudBusiness.consultar(solicitud);
			solicitud.setFirmaElectronica(firma);
			TramiteFisica tramite = (TramiteFisica) solicitud.getTramites().get(0);


			//Se obtiene la lista de domicilios ya existen de la persona, para que el primer domicilio
			//sea usado como domicilio del grupo familiar
			List<Domicilio>domicilios = domicilioServiceBusiness.consultarDomiciliosPersonaFisica( tramite.getDatosMDM().getPersonaFisica() );
			Domicilio domicilio = domicilios.get(0);

			//Actualizar al grupo familiar
			try {
				List<AsignacionNSS> listaNSS = grupoFamiliarServiceRemote.getAsignacionNss( tramite.getDatosMDM().getPersonaFisica().getIdPersona() );
				AsignacionNSS nss = listaNSS.get(0);
				GrupoFamiliar afectado = grupoFamiliarServiceRemote.getIntegranteGrupoFamiliarPorIdPersona(nss.getIdAsignacionNSS(), nss.getIdPersona());
				afectado.setCvePersonaDomicilio( domicilio.getCveIdPersonafDom() );
				afectado.setFechaRegistroActualizacion( new Date() );
				correccionDerechohabienteServiceRemote.updateIntegrante( afectado );


				//Finalizar el tramite
				if(solicitud.getFirmaElectronica() != null) {
					firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud, solicitud.getFirmaElectronica());
					solicitudBusiness.actualizarSolicitudAEstatusConcluida(solicitud);
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
	 * @see mx.gob.imss.ctirss.gestionpersonas.servicios.business.SolicitudPersonaBusinessRemote#finalizarSolicitudActualizacionDomicilio(mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud, mx.gob.imss.ctirss.delta.model.gestion.individuo.MDMDatosEntrada)
	 */
	@Override
	public mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud finalizarSolicitudActualizacionDomicilio(
			mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud, MDMDatosEntrada mdmDatosEntrada)
					throws SolicitudNoEncontradaException,
					DomicilioNoLocalizadoException {

		try {
			//Consultar la informacion de la solicitud, la cual ya debe tener la informacion de los datos de modificacion
			//del domicilio
			FirmaElectronica firma = solicitud.getFirmaElectronica();
			solicitud = this.solicitudBusiness.consultar(solicitud);
			solicitud.setFirmaElectronica(firma);

			if ( solicitud.getTramites().get(0) instanceof TramiteFisica ){
				domicilioServiceBusiness.modificarDomicilio( mdmDatosEntrada.getPersonaFisica().getDomicilios().get(0) );
			}
			else if( solicitud.getTramites().get(0) instanceof TramiteMoral ){
				domicilioServiceBusiness.modificarDomicilio( mdmDatosEntrada.getPersonaFisica().getDomicilios().get(0) );
			}

			//Finalizar el tramite
			if(solicitud.getFirmaElectronica() != null) {
				documentoProbatorioServiceBusinessRemote.guardarDocumentosCapturados(solicitud);
				firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud, solicitud.getFirmaElectronica());
				solicitudBusiness.actualizarSolicitudAEstatusConcluida(solicitud);
			}

		} 
		catch (TramiteNoEncontradoException e) {
			e.printStackTrace();
		}
		catch (TransformacionException e) {
			e.printStackTrace();
		} catch (DocumentoProbatorioException e) {
			e.printStackTrace();
		}

		return solicitud;
	}

	@Override
	public mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud 
	finalizarSolicitudCambioClinica(mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud)
			throws SolicitudNoEncontradaException, DomicilioNoLocalizadoException{
		try {
			//Consultar la informacion de la solicitud, la cual ya debe tener la informacion de los datos de modificacion
			//del domicilio
			FirmaElectronica firma = solicitud.getFirmaElectronica();
			solicitud = this.solicitudBusiness.consultar(solicitud);
			solicitud.setFirmaElectronica(firma);

			//Finalizar el tramite
			if(solicitud.getFirmaElectronica() != null) {
				documentoProbatorioServiceBusinessRemote.guardarDocumentosCapturados(solicitud);
				firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud, solicitud.getFirmaElectronica());
				solicitudBusiness.actualizarSolicitudAEstatusConcluida(solicitud);
			}

		} 
		catch (TramiteNoEncontradoException e) {
			e.printStackTrace();
		}
		catch (DocumentoProbatorioException e) {
			e.printStackTrace();
		}

		return solicitud;
	}

	/*
	 * Fin seccion para la finalizacion de las solicitudes de los tramites:
	 * ASIGNACION_DE_DOMICILIO_PARTICULAR_DH (101)
	 * ACTUALIZACION_DOMICILIO_PARTICULAR(6)
	 * CAMBIO_CLINICA(36)
	 */

	@Override
	public mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud actualizarTramites(
			mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud) throws SolicitudNoEncontradaException, TramiteNoEncontradoException {

		return solicitudBusiness.actualizarTramites(solicitud);
	}

	@Override
	public boolean existeTramiteRegistroUsuario(String curp) {
		boolean existeTramiteRegistro = false;
		boolean mostrarSolicInternet = true;

		FiltroSolicitud filtro = new FiltroSolicitud();
		filtro.setCurp(curp);
		filtro.setTramiteId(TipoTramiteEnum.ALTA_USUARIOS_SSO.getCodigo().longValue());
		filtro.setIdEstadoSolicitud(EstadoTramiteEnum.CERRADO.getCodigo().longValue());
		filtro.setIdOrigenSolicitud(-1L);

		DatosSalidaPaginador<mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud> output = solicitudBusiness
				.listarSolicitudesPorFiltro(null, filtro, mostrarSolicInternet);

		if (output.getiTotalRecords() > 0) {
			try {
				boolean existeLdap = componentesExternosBusiness.existeUsuarioEsquemaSeguridadByCURP(curp);

				if (existeLdap) {
					existeTramiteRegistro = true;
				} else {
					log.error("Existe un tramite de Registro de Usuario concluido, sin embargo no existe en el esquema de seguridad");
				}
			} catch (EsquemaSegurdiadException e) {
				log.error("Error al obtener el usuario, se considera inexistente como Usuario en IMSS Digital");
			} catch (Exception e) {
				log.error(e);
			}
		}

		return existeTramiteRegistro;
	}

	@Override
	public Fisica creaCuentaUsuararioSSOPass(mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud, Usuario usuario) 
			throws DomicilioNoValidoException, SolicitudNoEncontradaException, TramiteNoEncontradoException, RegistroPersonaFisicaException,
			UsuarioNoRegistradoEnEsquemaDeSeguridadException, PersonaNoEncontradaException{
		TramiteFisica tramiteFisica  = null;
		this.log.debug("entre a generar confirmar la solicitud con datos ");
		mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite tramite = solicitud.getTramites().get(0);

		tramiteFisica = (TramiteFisica) tramite;
		Fisica objFisica =tramiteFisica.getFisica();
		objFisica.setDocumentosProbatorios(null);
		Date fechaRegistro = new Date();
		boolean usuarioGuardadoEsquemaSeguridad=false;

		List<mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite> lstTramiteReg = new ArrayList();

		try{

			AfectarDatosPersonaWrapper personaWrapper = new AfectarDatosPersonaWrapper();
			personaWrapper.setFisica(objFisica);
			personaWrapper.setModificarCURP(true);
			personaWrapper.setModificarFechaNacimiento(true);
			calificacionesPersonaBusinessService.calificarRENAPO(objFisica);

			//se actualiza el rfc y la fecha de nacimiento de la persona o los datos que se recuperaran de la cupr.
			this.personaBusiness.afectarDatosPersona(personaWrapper);

			//se realiza la llamada para crear la cuenta en el SSO
			//se setea el usuario a la solicitud
			usuario.setUsuario(objFisica.getCurp());
			componentesExternosBusiness.crearUsuarioEsquemaSeguridad(objFisica, usuario);
			usuarioGuardadoEsquemaSeguridad = true;

			// se realiza la actualizacion de tramite solicitud y generacion de tramite persona fisica
			mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite objEstadoTramite = 
					new mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite ();
			objEstadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());
			tramiteFisica.setEstadoTramite(objEstadoTramite);
			tramiteFisica.setFechaConclusion(fechaRegistro);
			tramiteFisica.setFechaTramite(fechaRegistro);
			RazonResultado objRazonReslt = new RazonResultado();
			objRazonReslt.setIdRazonResultado(RazonResultadoEnum.NORMAL.getCodigo().longValue());
			tramiteFisica.setRazonResultado(objRazonReslt);
			tramiteFisica.setResultado(true);
			tramiteFisica.setFisica(objFisica);

			//se setea el tipo de tramite
			TipoTramite tipoTramite = new TipoTramite();
			tipoTramite.setIdTipoTramite(TipoTramiteEnum.ALTA_USUARIOS_SSO.getCodigo());
			tramite.setTipoTramite(tipoTramite);
			lstTramiteReg.add(tramiteFisica);

			//se llenan la info de la solicitud para darla por cerrada
			solicitud.setTramites(null);
			solicitud.setTramites(lstTramiteReg);

			mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud estadoSolicitud = new mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud();
			estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.ATENDIDA.getCodigo());
			solicitud.setEstadoSolicitud(estadoSolicitud);
			solicitud.setFechaActualizacion(fechaRegistro);
			solicitud.setFechaConclusion(fechaRegistro);

			solicitud.setSolicitante(usuario);		
			//se actualiza el estado del tramite y la solicitud para que se den por atendidas
			mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitudCreada = this.solicitudBusiness.crear(solicitud);
			this.log.debug("Solicitud creada ...." + solicitudCreada);


			// Se agregan los datos de la firma digital a la solicitud
			solicitud.setSolicitudId(solicitudCreada.getSolicitudId());

			//guardamos los datos de la cuenta
			usuarioPortalEntityLocal.insertarDatosCuenta(usuario,solicitudCreada.getSolicitudId());
			String cadenaOriginal = this.generaCadenaOriginal(objFisica,solicitud, "REGISTRO DE USUARIO");

			RespuestaFirmadoSimple firmadoSimple = firmaDigitalBusinessRemote.getSelloDigital(cadenaOriginal, null, null);

			this.log.debug("Tramite: " + firmadoSimple.getTramite());

			FirmaElectronica firmaElectronica = firmaDigitalBusinessRemote.convertirRespuestaFirmadoSimple(cadenaOriginal, firmadoSimple);
			firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud, firmaElectronica);

			this.solicitudBusiness.actualizarSolicitudAEstatusConcluida(solicitudCreada);
			Long idTramiteUsuario = null;

			for(mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite tramiteA: solicitudCreada.getTramites()) {
				if(tramiteA.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ALTA_USUARIOS_SSO.getCodigo())) {
					idTramiteUsuario = tramiteA.getTipoTramite().getIdTipoTramite().longValue();
					break;
				}
			}

			this.solicitudBusiness.obtenerDocumentoResultante(solicitudCreada,idTramiteUsuario,DocumentoPorTipoEnum.CARTA_TERMINOS_FIEL.getId().intValue());
		}catch(Exception ex){
			this.log.error("ocurrio un error al dar de alta al usuario ", ex);
			if(usuarioGuardadoEsquemaSeguridad){
				try{
					componentesExternosBusiness.eliminaUsuarioEsquemaSeguridadByCURP(objFisica.getCurp());
				}catch(Exception e){
					throw new UsuarioNoRegistradoEnEsquemaDeSeguridadException(); 
				}

			}
			throw new UsuarioNoRegistradoEnEsquemaDeSeguridadException();
		}
		return objFisica;
	}

	private String generaCadenaOriginal(Fisica fisica, mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud, String nombreTramite) {
		Locale locMEX = new Locale("es", "MX");

		String nombre = fisica.getNombre() != null ? fisica.getNombre().trim()
				: "";
		String apellidoP = fisica.getPrimerApellido() != null ? fisica
				.getPrimerApellido().trim() : "";
		String apellidoM = fisica.getSegundoApellido() != null ? fisica
				.getSegundoApellido().trim() : "";

		String nombreCompleto = nombre + " " + apellidoP + " " + apellidoM;

		SimpleDateFormat sdf = new SimpleDateFormat(
				"dd 'de' MMMM yyyy, HH:mm:ss", locMEX);

		StringBuilder cadenaOriginal = new StringBuilder(
				"||Invocante:portalimssdigital");
		cadenaOriginal.append("|Tipo de tr醡ite:" + nombreTramite);
		cadenaOriginal.append("|Fecha:" + sdf.format(new Date()));
		cadenaOriginal.append("|Folio:" + solicitud.getNoFolioSolicitud());
		if(!StringUtils.isBlank(fisica.getRfc())) {
			cadenaOriginal.append("|RFC:"+ (fisica.getRfc() != null ? fisica.getRfc() : ""));
		}

		cadenaOriginal.append("|Nombre o Raz髇 Social:" + nombreCompleto);
		cadenaOriginal.append("|Curp:"
				+ (fisica.getCurp() != null ? fisica.getCurp() : ""));
		cadenaOriginal.append("|N鷐ero de Seguridad Social:" + fisica.getNss()
		+ "||");



		return cadenaOriginal.toString();
	}

	@Override
	public List<String> consultarNombresPatrones(List<String> registroPatronal) {
		return solicitudPersonaEntity.consultarNombresPatrones(registroPatronal);
	}
	@Override
	public List<String> consultarNombrePatronPorRPYModalidad(String registroPatronal, String modalidad) {
		return solicitudPersonaEntity.consultarNombrePatronPorRPYModalidad(registroPatronal, modalidad);
	}

	@Override
	public void insertarDatosCuenta(Usuario usuario, Long idSolicitud) {
		// TODO Auto-generated method stub
		usuarioPortalEntityLocal.insertarDatosCuenta(usuario,idSolicitud);
	}

	@Override
	public mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica creaActualizaPersonaFisicaRegistroUsuario(
			mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud solicitud) throws Exception {
		
		mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite tramite = solicitud.getTramites().get(0);

		TramiteFisica tramiteFisicaSol = (TramiteFisica) tramite;
		Fisica objFisica =tramiteFisicaSol.getFisica();
		
		log.debug("llegue al proceso de alta o actualizacon de persona" + objFisica);
		Date fechaRegistro = new Date();
		mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica tramiteAlta = null;
		if(objFisica.getIdPersona() == null){
			log.debug("La persona es nueva, se realiza su registro");

			objFisica =	this.personaFisicaServiceBusiness.registrar(objFisica);
			this.log.debug(" ... id  dela persona objFisica .. " + objFisica.getIdPersona());
			tramiteAlta = new TramiteFisica();
			tramiteAlta.setFisica(objFisica);

			mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite objEstadoTramite = 
					new mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite ();

			objEstadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());
			tramiteAlta.setEstadoTramite(objEstadoTramite);
			tramiteAlta.setFechaConclusion(fechaRegistro);
			tramiteAlta.setFechaTramite(fechaRegistro);
			RazonResultado objRazonReslt = new RazonResultado();
			objRazonReslt.setIdRazonResultado(RazonResultadoEnum.NORMAL.getCodigo().longValue());
			tramiteAlta.setRazonResultado(objRazonReslt);
			tramiteAlta.setResultado(true);
			tramiteAlta.setFisica(objFisica);

			tramiteAlta.setTramiteId(null);

			TipoTramite tipoTramite = new TipoTramite();
			tipoTramite.setIdTipoTramite(TipoTramiteEnum.REGISTRO_DE_PERSONA.getCodigo());
			tramiteAlta.setTipoTramite(tipoTramite);

		}else{
			AfectarDatosPersonaWrapper personaWrapper = new AfectarDatosPersonaWrapper();
			personaWrapper.setFisica(objFisica);
			personaWrapper.setModificarCURP(true);
			personaWrapper.setModificarRFC(true);
			personaWrapper.setModificarFechaNacimiento(true);
			//se califica a la persona existente
			this.log.debug("entre con las siguientes calificaciones " + objFisica.getPersonaCalificaciones().size());
			calificacionesPersonaBusinessService.calificarRENAPOySAT(objFisica);
			//se valida si no existe la persona fisica se inserte
			if(objFisica.getCveFisica() == null || objFisica.getCveFisica() == -1){
				this.personaFisicaServiceBusiness.guardarPersonaFisica(objFisica);
			}else{
				this.personaFisicaServiceBusiness.afectarDatosPersonaFisica(personaWrapper);
			}
			//se actualiza el rfc y la fecha de nacimiento de la persona o los datos que se recuperaran de la cupr.
			this.personaBusiness.afectarDatosPersona(personaWrapper);
		}
		return tramiteAlta; 
	}

}