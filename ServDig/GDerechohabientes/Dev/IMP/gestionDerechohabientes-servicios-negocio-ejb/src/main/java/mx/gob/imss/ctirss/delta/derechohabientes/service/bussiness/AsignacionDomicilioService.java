package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.lang.StringUtils;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.GrupoFamiliarDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.AsignacionDomicilioServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.BajaDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.util.TramiteUtil;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoBajaDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilioEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaDomicilio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;

@Stateless(name = "asignacionDomicilioService", mappedName = "asignacionDomicilioService")
public class AsignacionDomicilioService extends AbstractServiceBusiness implements AsignacionDomicilioServiceRemote, AsignacionDomicilioServiceLocal {

	@EJB(name = "grupoFamiliarDao")
	private GrupoFamiliarDaoLocal grupoFamiliarDaoLocal;
	@EJB(name = "solicitudService")
	private SolicitudServiceLocal solicitudServiceLocal;
	@EJB
	private AsignacionUMFServiceLocal asignacionUMFServiceLocal;
	@EJB
	private CambioClinicaServiceLocal cambioClinicaServiceLocal;
	@EJB
	private CambioMedicoServiceLocal cambioMedicoServiceLocal;
	@EJB
	private UmfServiceLocal umfServiceLocal;
	@EJB
	private BajaDerechohabienteServiceRemote bajaDerechohabienteService; 
	@EJB
	private FinalizaSolicitudServiceLocal finalizaSolicitudServiceLocal;
	
	/*
	 * EJBS de otros proyectos
	 */
	@EJB(mappedName = "domicilioServiceBusiness")
	private DomicilioServiceBusinessRemote domicilioServiceBusinessRemote;
	@EJB(name = "solicitudBusiness", mappedName = "solicitudBusiness")
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@EJB(mappedName = "firmaDigitalBusiness", name = "firmaDigitalBusiness")
	private FirmaDigitalBusinessRemote firmaDigitalBusinessRemote;
	@EJB(name = "documentoProbatorioServiceBusiness", mappedName = "documentoProbatorioServiceBusiness")
	private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusinessRemote;
	
	
	@Override
	public List<GrupoFamiliar> actualizaDomPersonasSinDomicilioEnUmf(
			Long idAsignacionNSS, Long idUmfBusqueda, Domicilio nuevoDomicilio,
			List<Long> idsPersonasExcluir) throws DerechohabientesBusinessException {
		// Otenemos el grupo familiar
		List<GrupoFamiliar> integrantesParaMovimientos = null;
		List<GrupoFamiliar> grupoFamiliar = null;
		try {
			grupoFamiliar = grupoFamiliarDaoLocal.findIntegrantesPorUmfEstadoIntegrantes(
					idAsignacionNSS,idUmfBusqueda, null, idsPersonasExcluir, null, false);
			grupoFamiliar = this.getIntegrantesSinBaja(grupoFamiliar);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error al recuperar grupoFamiliar", e);
			DerechohabientesBusinessException.throwException("error.buscar.grupoFamiliar", e.getCause().getMessage());
		}

		if (grupoFamiliar != null && !grupoFamiliar.isEmpty()) {
			integrantesParaMovimientos = new ArrayList<GrupoFamiliar>();
			integrantesParaMovimientos = this.actualizarDomicilioIntegrantes(grupoFamiliar, nuevoDomicilio);
		}
		
		return integrantesParaMovimientos;
	}

	
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public List<GrupoFamiliar> actualizaDomicilioPadresConcubinas(
			AsignacionNSS nss, Integer patronIMSS, Domicilio nuevoDomicilio) throws DerechohabientesBusinessException {
		List<GrupoFamiliar> integrantesParaMovimientos = null;
		try {
			List<GrupoFamiliar> padresConcubinas = getPadresConcubinasParaCambio(nss, patronIMSS);
			
			integrantesParaMovimientos = this.actualizarDomicilioIntegrantes(padresConcubinas, nuevoDomicilio);
		} catch(DerechohabientesBusinessException e) {
			throw e;
		} catch (Exception e) {
			log.error("Ocurrio un error al actualizar a los padres");
		}
		
		return integrantesParaMovimientos;
	}
	
	private List<GrupoFamiliar> actualizarDomicilioIntegrantes(List<GrupoFamiliar> integrantes, Domicilio domicilio) throws DerechohabientesBusinessException{
		List<GrupoFamiliar> integrantesParaMovimientos = null;
		try {
			if(integrantes != null && !integrantes.isEmpty()) {
				integrantesParaMovimientos = new ArrayList<GrupoFamiliar>();
				for (GrupoFamiliar integrante : integrantes) {
					if(integrante.getCvePersonaDomicilio() == null || integrante.getDomicilio() == null || integrante.getDomicilio().getClave() == null) {
						integrante.setDomicilio(domicilio);
						// y la fecha de su actualizacion
						integrante.setFechaRegistroActualizacion(new Date());
						// y por ultimo actualizamos los cambios
						try {
							GrupoFamiliar aux = grupoFamiliarDaoLocal.updateIntegrante(integrante);
							integrante.setCvePersonaDomicilio(aux.getCvePersonaDomicilio());
							integrantesParaMovimientos.add(integrante);
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
			}
		} catch(DerechohabientesBusinessException e) {
			throw e;
		} catch (Exception e) {
			log.error("Ocurrio un error al actualizar a los padres");
		}
		
		return integrantesParaMovimientos;
	}

	private List<GrupoFamiliar> getIntegrantesSinBaja(List<GrupoFamiliar> entrada) {
		List<Long> tiposBaja = new ArrayList<Long>();
		tiposBaja.add(TipoBajaDerechohabienteEnum.DEFUNCION.getId());
		tiposBaja.add(TipoBajaDerechohabienteEnum.DIVORCIO.getId());
		tiposBaja.add(TipoBajaDerechohabienteEnum.TERMINO_CONCUBINATO.getId());
		tiposBaja.add(TipoBajaDerechohabienteEnum.TERMINO_CONVIVENCIA.getId());
		return bajaDerechohabienteService.quitarIntegrantesConTramiteBaja(entrada, tiposBaja);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Solicitud guardaAsignacionDeDomicilioSimplificado(Long idSolicitud,AsignacionNSS nss,CabezaGrupoFamiliar cabeza,GrupoFamiliar afectado,
			TramiteCorreccionDerechohabiente correccion, List<GrupoFamiliar> padres,
			Map<String, Object> validacionesCambios) throws DerechohabientesBusinessException, ImpactaAlmacenesWSException, Exception {
		
		List<GrupoFamiliar> integrantesMovimientos = new ArrayList<GrupoFamiliar>();
		Map<Long, GrupoFamiliar> integrantesDiferentes = new HashMap<Long, GrupoFamiliar>();
		Solicitud solicitud =new Solicitud(idSolicitud);
		Boolean existeCambioClinica = (Boolean) validacionesCambios.get("existeCambioClinica");
		MedicoEnTurno datosDeAdscripcionAnteriores = afectado.getMedicoEnTurno();
		//se guarda la finalizacion del tramite
		Map<String, Object> resultadoAsignacion= guardaAsignacionDeDomicilioSimpleAsegurado(correccion, afectado);
		//checamos si el guardado del domicilio no es nulo
		if(resultadoAsignacion != null) {
			//si la bandera es nula verificaremos si la trae
			if(existeCambioClinica == null) {
				existeCambioClinica = (Boolean) resultadoAsignacion.get("existeCambioClinica");
			}
			//se obtienen los datos necesarios del map
			correccion = (TramiteCorreccionDerechohabiente) resultadoAsignacion.get("tramite");
			afectado = (GrupoFamiliar) resultadoAsignacion.get("integrante");
			//se agrega al asegurado a los integrantes a ser afectados
			integrantesDiferentes.put(afectado.getDerechohabiente().getIdPersona(), afectado);
			//verificamos si existe cambio de clinica
			if(existeCambioClinica) {
				log.debug("Existe cambio de clinica en el tramite");
				TramiteCorreccionDerechohabiente tramiteCambio = new TramiteCorreccionDerechohabiente();
				tramiteCambio.setIdAsignacionNss(cabeza.getAsignacionNSS());
				tramiteCambio.setIdPersona(afectado.getDerechohabiente().getIdPersona());
				tramiteCambio.setPersona(afectado.getDerechohabiente());
				tramiteCambio.setMedicoEnTurno(correccion.getMedicoEnTurno());
				tramiteCambio.setDomicilio(correccion.getDomicilio());
				tramiteCambio.setIdUmfOrigen(correccion.getIdUmfOrigen());
				//Esto se hace para que el xml de cambio de clinica guarde correctamente los datos de la umf anterior
				afectado.setMedicoEnTurno(datosDeAdscripcionAnteriores);
				this.setPadresConcubinasEnTramite(tramiteCambio, afectado.getAsignacionNSS(),cabeza.getPatronImss(),
						afectado, false,padres);

				Map<String, Object> result = cambioClinicaServiceLocal.agregarTramiteCambioClinicaDependiente(tramiteCambio, afectado.getAsignacionNSS(), cabeza.getPatronImss().equals(1), null,
						idSolicitud, afectado, true, null);
				
				List<GrupoFamiliar> afectadosCambioClinica = (List<GrupoFamiliar>) result.get(TramiteUtil.KEY_AFECTADOS);
				
				if(afectadosCambioClinica != null && !afectadosCambioClinica.isEmpty()) {
					for(GrupoFamiliar afectadoCC : afectadosCambioClinica) {
						integrantesDiferentes.put(afectadoCC.getDerechohabiente().getIdPersona(), afectadoCC);
					}
				}
				
				solicitud = TramiteUtil.getSolicitudFromMap(result);
				solicitud.setSolicitudId(idSolicitud);

			} else if(padres != null && !padres.isEmpty()){
				List<Long> idsPersonasTramite = new ArrayList<Long>();
				List<Fisica> personas = new ArrayList<Fisica>();
				
				idsPersonasTramite.add(afectado.getDerechohabiente().getIdPersona());
				personas.add(afectado.getDerechohabiente());
				
				if(TramiteUtil.isAsegurado(afectado)) {
					//si solo hubo cambio de medico se actualiza tambien a los padres en caso de venir
					for(GrupoFamiliar integrante: padres) {
						
						
						integrante.setDomicilio(correccion.getDomicilio());
						integrante.setMedicoEnTurno(correccion.getMedicoEnTurno());
						integrante.setFechaRegistroActualizacion(new Date());
						// ----------------------------------------------------------------------
						// Al crear el trámite y actualizar el integrante, se toma la fecha 
						// traida de la base; solamente se valida que no sea null
						// ----------------------------------------------------------------------
						integrante.setFechaCambioTurnoMedico(null);
						
						Long cvePersonaDomicilio = this.guardarDom(integrante.getDerechohabiente(), correccion.getDomicilio());
						
						integrante.setCvePersonaDomicilio(cvePersonaDomicilio);
						
						idsPersonasTramite.add(integrante.getDerechohabiente().getIdPersona());
						personas.add(integrante.getDerechohabiente());
						
						grupoFamiliarDaoLocal.updateIntegrante(integrante);
						integrantesDiferentes.put(integrante.getDerechohabiente().getIdPersona(), integrante);
					}
					
					List<GrupoFamiliar> integrantesAsignacionDomicilio = this.actualizaDomPersonasSinDomicilioEnUmf(nss.getIdAsignacionNSS(), 
							correccion.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF(), correccion.getDomicilio(), 
							idsPersonasTramite);
					
					if(integrantesAsignacionDomicilio != null && !integrantesAsignacionDomicilio.isEmpty()) {
						for(GrupoFamiliar integranteSinDomicilio: integrantesAsignacionDomicilio) {
							integrantesDiferentes.put(integranteSinDomicilio.getDerechohabiente().getIdPersona(), integranteSinDomicilio);
							idsPersonasTramite.add(integranteSinDomicilio.getDerechohabiente().getIdPersona());
							personas.add(integranteSinDomicilio.getDerechohabiente());
						}
					}
				}
				
				correccion.setIdAsignacionNss(nss.getIdAsignacionNSS());
				correccion.setNss(nss.getNss());
				correccion.setPersona(null);
				correccion.setIdPersona(null);
				correccion.setCandidatosCambioClinica(idsPersonasTramite);
				correccion.setPersonas(personas);
				
				if(correccion.getTramiteId() != null) {
					solicitudBusinessRemote.actualizarXmlTramite(correccion);
				}
				
				solicitud.setTramites(new ArrayList<Tramite>());
				solicitud.getTramites().add(correccion);
			}
		}
		
		if(!integrantesDiferentes.isEmpty()) {
			log.debug("Numero de integrantes para movimientos: " + integrantesDiferentes.size());
			integrantesMovimientos.addAll(integrantesDiferentes.values());
		}
		
		solicitudBusinessRemote.actualizaAConcluida(solicitud);
		
		if(integrantesMovimientos != null && !integrantesMovimientos.isEmpty()) {
			log.debug("Se enviaran los movimientos al WS de vigencia, existe cambio de clinica ? " + existeCambioClinica);
			
			try {
				finalizaSolicitudServiceLocal.mandaMovimientosWS(integrantesMovimientos, existeCambioClinica);
			} catch(ImpactaAlmacenesWSException e) {
				log.error("Ocurrio un error al impactar en el ws de vigencia para el tramite de asignacion o cambio de clinica", e);
				throw e;
			}
		}
		
		return solicitud;
	}

	/**
	 * Metodo que solo guardar la clinica
	 */
	@Override
	public Map<String, Object> guardaAsignacionDeDomicilioSimpleAsegurado(TramiteCorreccionDerechohabiente correccion
			, GrupoFamiliar afectado) throws DerechohabientesBusinessException, Exception {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		// sacamos el domicilio que actualizaremos o insertaremos
		Domicilio nuevoDomicilio = correccion.getDomicilio();
		Derechohabiente derechohabiente = null;
		Map<String, Object> existenCambios = new HashMap<String, Object>();
		GrupoFamiliar integranteActualizado = null;

		//guardamos el nuevo domicilio
		try {
			nuevoDomicilio = domicilioServiceBusinessRemote.registrarDomicilio(nuevoDomicilio);
			correccion.setDomicilio(nuevoDomicilio);
		} catch (DomicilioNoValidoException e) {
			log.error("No fue posible guardar el nuevo domicilio", e);
			DerechohabientesBusinessException.throwException(e.getSituacion(), e.getSituacion());
		} catch (Exception e) {
			log.error("No fue posible guardar el nuevo domicilio", e);
			DerechohabientesBusinessException.throwException("No fue posible guardar el domicilio","No fue posible guardar el domicilio");
		}
		
		// Validamos que exista la persona dentro del tramite
		Fisica fisica = correccion.getPersona();
		// Validamos si el tramite se aplicara a un derechohabiente
		if (fisica instanceof Derechohabiente) {
			derechohabiente = (Derechohabiente) fisica;
		}
		
		//independientemente de si es derechohabiente o no asociamos el domicilio al derechohabiente
		this.guardarDomicilioPersonaFisica(fisica, nuevoDomicilio);
		
		try {
			// si no es de derechohabientes tan solo actualizamos
			// persona-domicilio
			if (derechohabiente != null) {
				if(afectado == null) {
					log.debug("La persona a quien se le hace el cambio de domicilio no es un derechohabiente");
					//Se obtiene al integrante sin consultar al ws
					integranteActualizado = grupoFamiliarDaoLocal.getIntegranteSinVigencia(derechohabiente.getAsignacionNSS().getIdAsignacionNSS(), derechohabiente.getIdPersona());
				} else {
					integranteActualizado = afectado;
				}
				
				existenCambios = this.cambioClinica(integranteActualizado, correccion);
				integranteActualizado.setDomicilio(nuevoDomicilio);
				integranteActualizado.setMedicoEnTurno(correccion.getMedicoEnTurno());
				integranteActualizado.setFechaRegistroActualizacion(new Date());
				// ----------------------------------------------------------------------
				// Al crear el trámite y actualizar el integrante, se toma la fecha 
				// traida de la base; solamente se valida que no sea null
				// ----------------------------------------------------------------------
				integranteActualizado.setFechaCambioTurnoMedico(null);
				
				Long cvePersonaDomicilio = this.guardarDom(integranteActualizado.getDerechohabiente(), nuevoDomicilio);
				
				integranteActualizado.setCvePersonaDomicilio(cvePersonaDomicilio);
				
				grupoFamiliarDaoLocal.updateIntegrante(integranteActualizado);
			}
		} catch (DerechohabientesBusinessException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			DerechohabientesBusinessException.throwException("Ocurrio un error al asociar a la persona con el domicilio", "Ocurrio un error al asociar a la persona con el domicilio");
		}
		
		result.put("tramite",correccion);
		result.putAll(existenCambios);
		result.put("integrante", integranteActualizado);
		
		return result;
	}
	
	private Map<String, Object> cambioClinica(GrupoFamiliar antiguo, TramiteCorreccionDerechohabiente correccion) {
		Map<String, Object> validacionesClinica = new HashMap<String, Object>();
		Boolean existeCambio = false;
		Boolean existeCambioConsultorio = false;
		
		if(antiguo.getMedicoEnTurno() == null && correccion.getMedicoEnTurno() != null) {
			existeCambio =  true;
		}
		
		MedicoEnTurno morigen = antiguo.getMedicoEnTurno();
		MedicoEnTurno mdestino = correccion.getMedicoEnTurno();
		
		if(morigen != null && mdestino != null) {
			UnidadMedicaFamiliar origen = morigen.getUnidadMedicaFamiliar();
			UnidadMedicaFamiliar destino = mdestino.getUnidadMedicaFamiliar();
			
			if(origen == null && destino != null) {
				existeCambio =  true;
			}
			
			if((origen != null && destino != null) && (!origen.getIdUMF().equals(destino.getIdUMF()))) {
				existeCambio =  true;
			}
			
			if(!existeCambio && (morigen != null && mdestino != null && !morigen.getIdMedicoContultorioTurno().equals(mdestino.getIdMedicoContultorioTurno()))) {
				existeCambioConsultorio = true;
			}
		}
		
		validacionesClinica.put("existeCambioClinica", existeCambio);
		validacionesClinica.put("existeCambioConsultorio", existeCambioConsultorio);
		
		return validacionesClinica;
	}
	
	@Override
	public Solicitud finalizaSolicitudAsignacionDomicilio(
			Solicitud solicitud, GrupoFamiliar registroAsegurado,CabezaGrupoFamiliar cabeza,Boolean consultar,
			Boolean consultarPadresConcubinas, List<GrupoFamiliar> padresConcubinas) throws DerechohabientesBusinessException,
			SolicitudNoValidaException, SolicitudNoEncontradaException,
			SolicitudException {
		
		solicitud = this.finalizarSolicitudDomicilioCommon(solicitud, registroAsegurado, cabeza, consultar, consultarPadresConcubinas, padresConcubinas);
		
		return solicitud;
	}
	
	private Long guardarDomicilioPersonaFisica(Fisica fisica, Domicilio nuevoDomicilio) {
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
	
	private  Solicitud finalizarSolicitudDomicilioCommon(
			Solicitud solicitud, GrupoFamiliar registroAsegurado,CabezaGrupoFamiliar cabeza,Boolean consultar,
			Boolean consultarPadres, List<GrupoFamiliar> padresConcubinas) throws DerechohabientesBusinessException,
			SolicitudNoValidaException, SolicitudNoEncontradaException,
			SolicitudException {

		log.debug("Entramos a finalizar la solicitud de correccion de datos de derechohabiente");
		
		if(consultar) {
			//Guardamos los datos de la firma electronica
			if(solicitud.getFirmaElectronica() != null) {
				firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud, solicitud.getFirmaElectronica());
			}
			
			solicitud = solicitudBusinessRemote.consultar(solicitud);
		}
		
		TramiteCorreccionDerechohabiente correccion = null;
		//Tramite de cambio de clinica que se generara
		TramiteCorreccionDerechohabiente tramiteCambioClinica = null;
		GrupoFamiliar integrante = null;
		Derechohabiente derechohabiente = null;
		Domicilio nuevoDomicilio = null;
		MedicoEnTurno adscripcionAsegurado = null;
		Boolean asignacion = false;

		if (solicitud != null) {
			//Se obtiene un tramite de tipo correccion
			correccion = TramiteUtil.getTramiteCorreccionFromSolicitud(solicitud);

			// si no hay tramite de correccion mandamos una excepcion
			if (correccion == null) {
				throw new SolicitudNoValidaException("No se encontraron tramites validos en la solicitud");
			}
			
			// sacamos el domicilio que actualizaremos o insertaremos
			nuevoDomicilio = correccion.getDomicilio();

			//guardamos el nuevo domicilio
			try {
				nuevoDomicilio = domicilioServiceBusinessRemote.registrarDomicilio(nuevoDomicilio);
				correccion.setDomicilio(nuevoDomicilio);
			} catch (DomicilioNoValidoException e) {
				log.error("No fue posible guardar el nuevo domicilio", e);
				DerechohabientesBusinessException.throwException(e.getSituacion(), e.getSituacion());
			} catch (Exception e) {
				log.error("No fue posible guardar el nuevo domicilio", e);
				DerechohabientesBusinessException.throwException("No fue posible guardar el domicilio","No fue posible guardar el domicilio");
			}
			
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
				
				this.guardarDomicilioPersonaFisica(fisica, nuevoDomicilio);

			} else {// Si es derechohabiente
				log.debug("la persona a quien se le hace el cambio o asignacion es un derechohabiente");
				
				try {
					log.debug("Consultamos al integrante del grupo familiar");
					
					if(cabeza != null) {
						log.debug("la cabeza de grupo familiar es nula, por lo que se tiene que consultar");
						cabeza = grupoFamiliarDaoLocal.getCabezaGrupoFamiliar(derechohabiente.getAsignacionNSS().getIdAsignacionNSS());
					}
					
					//Se obtiene al integrante sin consultar al ws
					integrante = grupoFamiliarDaoLocal.getIntegranteSinVigencia(derechohabiente.getAsignacionNSS().getIdAsignacionNSS(), derechohabiente.getIdPersona());
					//verificamos si encontramos al integrante
					if (integrante != null) {
						//en caso de que el integrante no tenga domicilio o medico, el tramite es de asignacion de domicilio
						if(integrante.getCvePersonaDomicilio() == null || integrante.getMedicoEnTurno() == null) {
							asignacion = true;
						}
						
						log.debug("El integrante ha sido encontrado");
						Long parentesco = integrante.getParentesco().getIdParentesco();
						log.debug("el parentesco del derechohabientes es: "+ parentesco);
						AsignacionNSS nss = integrante.getAsignacionNSS();
						//Checamos si el integrante es un asegurado
						Boolean isAsegurado = parentesco.equals(ParentescoEnum.ASEGURADO.getId()) || parentesco.equals(ParentescoEnum.PENSIONADO.getId());
						// Solo si el que se esta registrando no es el asegurado
						// verificaremos si es necesario crear un tramite de
						// autorizacion para recibir servicios en
						// circunscripcion foranea o de cambio de clinica
						if (!isAsegurado) {
							log.debug("El derechohabiente no es un asegurado");
							
							if(registroAsegurado == null) {
								//consultamos al asegurado
								try {
									registroAsegurado = grupoFamiliarDaoLocal.getIntegranteSinVigencia(nss.getIdAsignacionNSS(), nss.getIdPersona());
								} catch (Exception e) {
									DerechohabientesBusinessException.throwException("No fue posible consultar al integrante asegurado/pensionado del grupo familiar");
								}
								//si no encontramos al asegurado o no tiene umf arrojamos una excepcion
								if (registroAsegurado == null || registroAsegurado.getMedicoEnTurno()==null) {
									DerechohabientesBusinessException.throwException("No fue posible localizar los datos de adscripcion del asegurado/pensionado");
								}
							}
							
							//Obtenemos los datos de adscripcion del asegurado
							adscripcionAsegurado = registroAsegurado.getMedicoEnTurno();
							// Obtenemos la umf del asegurado y la del integrante a asignar umf
							Long idUmfAsegurado = adscripcionAsegurado.getUnidadMedicaFamiliar().getIdUMF();
							Long idUmfIntegrante = correccion.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF();
							Long idUmfAnteriorIntegrante = null;
							//checamos si el integrante trae umf
							if(integrante.getMedicoEnTurno() != null) {
								try{
									idUmfAnteriorIntegrante = integrante.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF();
								}catch( NullPointerException e ){
									log.error("No se pudo obtener la umf anterior del integrante");
								}
							}
							
							//si el integrante no tenia umf el tramite es de asignacion de Umf
							if(idUmfAnteriorIntegrante == null) {
								
								Long cvePersonaDomicilio = this.guardarDomicilioPersonaFisica(integrante.getDerechohabiente(), nuevoDomicilio);
								integrante.setCvePersonaDomicilio(cvePersonaDomicilio);
								integrante.setMedicoEnTurno(correccion.getMedicoEnTurno());
								
								asignacionUMFServiceLocal.guardarTramiteAsignacionUmfDependiente(integrante, solicitud.getSolicitudId(),cabeza.getPatronImss(),false,asignacion);
							} else {
								//Checamos si cambió de umf
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

										log.debug("Se procede a hacer el cambio de clinica del derechohabiente");
										cambioClinicaServiceLocal.agregarTramiteCambioClinicaDependiente(tramiteCambio, nss, cabeza.getPatronImss().equals(1),
												null, solicitud.getSolicitudId(), integrante, asignacion, null);
										
									} /*else { 
										Long cvePersonaDomicilio = this.guardarDomicilioPersonaFisica(integrante.getDerechohabiente(), nuevoDomicilio);
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

									}*/
								} else {
									Long cvePersonaDomicilio = this.guardarDom(integrante.getDerechohabiente(), nuevoDomicilio);
									integrante.setCvePersonaDomicilio(cvePersonaDomicilio);
									TramiteCorreccionDerechohabiente tramiteCambioMedico = new TramiteCorreccionDerechohabiente();
									tramiteCambioMedico.setIdPersona(integrante.getDerechohabiente().getIdPersona());
									tramiteCambioMedico.setPersona(integrante.getDerechohabiente());
									
									// si no es circunscripcion o cambio de clinica
									cambioMedicoServiceLocal.agregarTramiteCambioMedicoDependiente(tramiteCambioMedico, solicitud.getSolicitudId() , nss, null, true);
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
									//todo verificara si es necesario setear a mas de una persona en el tramite
									this.setPadresConcubinasEnTramite(tramiteCambio, nss,cabeza.getPatronImss(),
											integrante, consultarPadres,padresConcubinas);
									
									
									log.debug("Se procede a hacer el cambio de clinica del derechohabiente");
									Map<String, Object> restult = cambioClinicaServiceLocal.agregarTramiteCambioClinicaDependiente(tramiteCambio, nss, cabeza.getPatronImss().equals(1),
											null, solicitud.getSolicitudId(), integrante, asignacion, null);
									
									 Solicitud solicitudCambio = TramiteUtil.getSolicitudFromMap(restult);
									
									if(solicitudCambio != null && solicitudCambio.getTramites() != null && !solicitudCambio.getTramites().isEmpty()) {
										tramiteCambioClinica = TramiteUtil.getTramiteCorreccionPorTipo(solicitudCambio, TipoTramiteEnum.CAMBIO_CLINICA.getCodigo().longValue());
									}
									
								} else {
									
									log.debug("El asegurado no cambio de clinica");
									integrante.setDomicilio(nuevoDomicilio);
									integrante.setMedicoEnTurno(correccion.getMedicoEnTurno());
									integrante.setFechaRegistroActualizacion(new Date());
									// ----------------------------------------------------------------------
									// Al crear el trámite y actualizar el integrante, se toma la fecha 
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
									/*cambioMedicoServiceLocal.agregarTramiteCambioMedicoDependiente(correccion, solicitud.getSolicitudId(), nss,
											null, asignacion);
									//buscaremos a los integrantes que esten en la misma umf para ver si aplicamos cambio de medico
									this.guardarTramiteCambioMedicoDependiente(integrante, solicitud.getSolicitudId(),asignacion);*/
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
								
								asignacionUMFServiceLocal.guardarTramiteAsignacionUmfDependiente(integrante, solicitud.getSolicitudId(), cabeza.getPatronImss(), false, asignacion);
								//this.guardarTramiteAsignacionUmfDependiente(integrante, solicitud.getSolicitudId(),cabeza.getPatronImss(),false,asignacion);
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
				
				String observaciones = null;
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
		
		solicitud.setTramites(new ArrayList<Tramite>());
		solicitud.getTramites().add(correccion);
		if(tramiteCambioClinica != null) {
			solicitud.getTramites().add(tramiteCambioClinica);
		}
//		try {
//			finalizaSolicitudServiceRemote.finalizarSolicitudTramites(solicitud, "", integrante.getAsignacionNSS());
//		} catch (Exception e) {
//			throw new DerechohabientesBusinessException(e.getMessage());
//		}

		return solicitud;
	}
	
	/**
	 * 
	 * @param tramiteCambio
	 * @param nss
	 * @param patronIMSS
	 * @param integrante
	 * @param consultar
	 * @param padres
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void setPadresConcubinasEnTramite(TramiteCorreccionDerechohabiente tramiteCambio,AsignacionNSS nss, Integer patronIMSS,
			GrupoFamiliar integrante, Boolean consultar, List<GrupoFamiliar> padres) throws Exception {
		if(consultar) {
			//buscamos padres y concubinas y los seteamos al tramite
			Map<String, ? extends Object> map = this.getPadresConcubinasParaCambio(nss, patronIMSS, tramiteCambio.getMedicoEnTurno());
			if(map != null) {
				List<Long> ids = (List<Long>) map.get("ids");
				if(ids != null && !ids.isEmpty()) {
					log.debug("El cambio de clinica se hara para mas de una persona");
					//Creamos la lista de las personas que se cambiaran de clinica
					List<Fisica> personas = new ArrayList<Fisica>();
					personas.add(integrante.getDerechohabiente());
					
					List<Derechohabiente> integrantes = (List<Derechohabiente>) map.get("integrantes");
					ids.add(integrante.getDerechohabiente().getIdPersona());
					for(Derechohabiente dere : integrantes) {
						personas.add(dere);
					}
					//Seteamos los ids individuales en null ya que ahora el cambio sera para mas de un integrante
					tramiteCambio.setIdPersona(null);
					tramiteCambio.setPersona(null);
					//ponemos alos padres y consubinas mas el asegurado
					tramiteCambio.setCandidatosCambioClinica(ids);
					tramiteCambio.setPersonas(personas);
				}
			}
		} else {
			if(padres != null && !padres.isEmpty()) {
				List<Fisica> personas = new ArrayList<Fisica>();
				List<Long> idsPersonas = new ArrayList<Long>();
				
				idsPersonas.add(integrante.getDerechohabiente().getIdPersona());
				personas.add(integrante.getDerechohabiente());
				
				for(GrupoFamiliar integ: padres) {
					idsPersonas.add(integ.getDerechohabiente().getIdPersona());
					personas.add(integ.getDerechohabiente());
				}
				
				//Seteamos los ids individuales en null ya que ahora el cambio sera para mas de un integrante
				tramiteCambio.setIdPersona(null);
				tramiteCambio.setPersona(null);
				//ponemos alos padres y consubinas mas el asegurado
				tramiteCambio.setCandidatosCambioClinica(idsPersonas);
				tramiteCambio.setPersonas(personas);
			}
		}
	}
	
	
	@Override
	public List<GrupoFamiliar> getPadresConcubinasParaCambio(AsignacionNSS nss, Integer patronIMSS) throws Exception {
		List<GrupoFamiliar> grupo = cambioClinicaServiceLocal.getPadresConcubinasParaCambio(nss, patronIMSS);
		
		return grupo;
	}

	
	
	@Override
	public Map<String, List<? extends Object>> getPadresConcubinasParaCambio(AsignacionNSS nss, Integer patImss, MedicoEnTurno nuevoMedico) throws Exception{
		
		Map<String, List<? extends Object>> listas = null;
		List<Derechohabiente> integrantes = null;
		List<Long> idIntegrantes = null;
		
		List<Long> parentescos = new ArrayList<Long>();
		parentescos.add(ParentescoEnum.PADRES.getId());
		parentescos.add(ParentescoEnum.CONCUBINARIO.getId());
		
		List<GrupoFamiliar> concubinasPadres = this.getPadresConcubinasParaCambio(nss, patImss);
		
		if (concubinasPadres != null && !concubinasPadres.isEmpty()) {
			listas = new HashMap<String, List<? extends Object>>();
			idIntegrantes = new ArrayList<Long>();
			integrantes = new ArrayList<Derechohabiente>();
			
			log.debug("Se encontraron " + concubinasPadres.size() + " integrantes padres y o concubinas");
			for (GrupoFamiliar integ : concubinasPadres) {
				idIntegrantes.add(integ.getDerechohabiente().getIdPersona());
				integrantes.add(integ.getDerechohabiente());
			}
			
			listas.put("ids", idIntegrantes);
			listas.put("integrantes", integrantes);
		}
		
		return listas;
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


	@Override
	public List<GrupoFamiliar> findPersonasSinDomicilioEnUmf(
			Long idAsignacionNSS, Long idUmf, List<Long> idsPersonasExcluir,
			List<Long> idsEstados) throws Exception {
		List<GrupoFamiliar> grupoFamiliar = null;
		
		try {
			grupoFamiliar = grupoFamiliarDaoLocal.findIntegrantesPorUmfEstadoIntegrantes(
					idAsignacionNSS,idUmf, idsEstados, idsPersonasExcluir, null, false);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error al recuperar grupoFamiliar", e);
			DerechohabientesBusinessException.throwException("error.buscar.grupoFamiliar", e.getCause().getMessage());
		}
		return grupoFamiliar;
	}
	
	
	
	
}
