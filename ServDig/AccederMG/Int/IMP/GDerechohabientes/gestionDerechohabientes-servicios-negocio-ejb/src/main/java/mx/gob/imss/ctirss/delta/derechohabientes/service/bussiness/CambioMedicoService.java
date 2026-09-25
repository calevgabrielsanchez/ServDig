package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.CatalogosDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.GrupoFamiliarDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.MedicoEnTurnoDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.entity.CorreccionDerechohabienteEntityLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CambioMedicoServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.util.TramiteUtil;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.DocumentoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;

@Stateless(name = "cambioMedicoService", mappedName = "cambioMedicoService")
public class CambioMedicoService extends AbstractServiceBusiness implements CambioMedicoServiceLocal, CambioMedicoServiceRemote {

	@EJB
	private GrupoFamiliarDaoLocal grupoFamiliarDaoLocal;
	@EJB
	private TramiteServiceLocal tramiteServiceLocal;
	@EJB
	private CorreccionDerechohabienteEntityLocal correccionDerechohabienteEntityLocal;
	@EJB
	private TramiteDocumentosServiceLocal tramiteDocumentosServiceLocal;
	@EJB
	private CatalogosDaoLocal catalogosDaoLocal;
	@EJB
	private FinalizaSolicitudServiceLocal finalizaSolicitudServiceLocal;
	@EJB
	private MedicoEnTurnoDaoLocal medicoEnTurnoDaoLocal;

	
	@Override
	public Map<String, Object> crearTramiteCambioMedicoDependiente(Long idSolicitud,
			List<GrupoFamiliar> integrantesEnUmfDestino, AsignacionNSS nss,
			Boolean asignacionDomicilio, Boolean parentescoPersmitido,
			Boolean existeCambioMedico, TramiteCorreccionDerechohabiente origenCambioMedico) throws DerechohabientesBusinessException {
		
		Map<String, Object> result = null;
		//validamos si habia integrantes en la umf destino y si es necesario hacer un cambio de medico
		if (integrantesEnUmfDestino != null && !integrantesEnUmfDestino.isEmpty() && parentescoPersmitido && existeCambioMedico) {
			
			List<Long> idsPersonasCambioMedico = new ArrayList<Long>();
			List<Fisica> personasCambioMedico = new ArrayList<Fisica>();
			
			TramiteCorreccionDerechohabiente tramiteCambioMedico = TramiteUtil.copiarCorreccion(origenCambioMedico);
			//quitamos los datos de las personas anteriores
			tramiteCambioMedico.setTramiteId(null);
			tramiteCambioMedico.setIdPersona(null);
			tramiteCambioMedico.setPersona(null);
			tramiteCambioMedico.setCandidatosCambioClinica(null);
			tramiteCambioMedico.setPersonas(null);
			
			for (GrupoFamiliar integranteCambio : integrantesEnUmfDestino) {
				idsPersonasCambioMedico.add(integranteCambio.getDerechohabiente().getIdPersona());
				personasCambioMedico.add(integranteCambio.getDerechohabiente());
			}
			
			tramiteCambioMedico.setCandidatosCambioClinica(idsPersonasCambioMedico);
			tramiteCambioMedico.setPersonas(personasCambioMedico);
			
			result = this.agregarTramiteCambioMedicoDependiente(tramiteCambioMedico, idSolicitud, nss,
					integrantesEnUmfDestino, asignacionDomicilio);
			
		}
		
		return result;
	}

	@Override
	/**
	 * Metodo para verificar cuanto tiempo tiene que se hizo el ultimo tramite de cambio de medico
	 * @param Long idPersona el id de la persona al que se le cambiara el medico
	 * @param AsignacionNSS nss el nss de lacabeza de grupo familiar
	 * @return Boolean - bandera que indica si es posible o no el cambio de medico
	 */
	public Boolean isCambioMedicoPosible(Long idPersona, AsignacionNSS nss) {

		GrupoFamiliar integrante = null;

		try {
			integrante = grupoFamiliarDaoLocal.getIntegranteSinVigencia(nss.getIdAsignacionNSS(), idPersona);
		} catch (DerechohabientesBusinessException e1) {
			log.error("No se pudo recuperar la fecha de cambio de medico", e1);
		} catch (Exception e1) {
			log.error("No se pudo recuperar la fecha de cambio de medico", e1);
		}

		if (integrante == null)
			return true;

		if (integrante.getFechaCambioTurnoMedico() == null)
			return true;

		Long diasTranscrurridos = TramiteUtil.diasEntreFechayHoy(integrante.getFechaCambioTurnoMedico());
		if (diasTranscrurridos > 365)
			return true;
		else
			return false;

	}
	
	/**
	 * Metodo para finalizar la solicitud de cambio de medico, afectadndo a todos los integrantes del grupo
	 * familiar que se tegan que afectar
	 * @param Solicitud
	 * @param AsignacionNSS
	 * @return Solicitud - La solicitud con la informacion del tramite actualizada
	 * @throws DerechohabientesBusinessException
	 */
	@Override
	public Solicitud finalizaSolicitudCambioMedico(Solicitud solicitud, AsignacionNSS nss) throws DerechohabientesBusinessException, ImpactaAlmacenesWSException{
		
		Map<String, Object> result = null;
		List<GrupoFamiliar> afectados = null;
		
		TramiteCorreccionDerechohabiente correccion = TramiteUtil.getTramiteCorreccionFromSolicitud(solicitud);
		//ponemos la fecha de cambio de medico al dia de hot
		correccion.setFechaCambioMedico(new Date());
		
		//guardamos todos los cambios necesarios
		result = this.guardaCambioMedico(correccion, false, nss, null);
		//obtenemos el tramite del mapa
		correccion = TramiteUtil.getTramiteCorreccionFromMap(result);
		//obtenemos a los afectados
		afectados = TramiteUtil.getAfectadosFromMap(result);
		correccion.setIdAsignacionNss(nss.getIdAsignacionNSS());
		//se genrera la firma digital
		try {
			TipoTramite tipoTramiteModel = catalogosDaoLocal.getTipoTramite(correccion.getTipoTramite().getIdTipoTramite().longValue());
			tramiteDocumentosServiceLocal.generaFirmaElectronica(nss, solicitud, tipoTramiteModel.getDescripcion());
		} catch (DocumentoException e) {
			e.printStackTrace();
		}

		//retornamos el tramite como quedo
		solicitud.setTramites(new ArrayList<Tramite>());
		solicitud.getTramites().add(correccion);
		
		try {
			//insertamos en ditCorreccion
			correccionDerechohabienteEntityLocal.saveCorreccionDerechohabiente(correccion);
		} catch (Exception e) {
			// Si ocurrio algun error mandamos una excepcion indicando que no se
			// pudo guardar la solicitud
			e.printStackTrace();
			log.error("No fue posible guardar la solicitud", e);
			/*throw new DerechohabientesBusinessException(
					ExceptionMessages.ERROR_GUARDADO_SOLCITUD, e.getCause()
							.getMessage());*/
		}
		
		//se actualiza el XML del tramite
		try {
			tramiteServiceLocal.actualizaXMLTramite(correccion);
		} catch (TramiteNoEncontradoException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		//se actualiza la solicitud a estado concluida
		try {
			tramiteServiceLocal.actualizarSolicitudAConcluida(solicitud);
		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
		}
		
		try {
			finalizaSolicitudServiceLocal.mandaMovimientosWS(afectados, false);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (ImpactaAlmacenesWSException e) {
			log.error("Ocurrio un error al impactar en el ws", e);
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return solicitud;
	}

	/**
	 * Metodo qur crea una solicitud der cambio de medico consultorio o turno, con los datos del tramite que son enviados
	 * @param GrupoFamiliar - El integrante que sera afectado
	 * @param Usuario - el usuario que esta realizando la solicitud
	 * @param AsignacionNSS - El nss para el cual se esta realizando el tramite
	 * @paran TramiteCorreccionDerechohabiente - La informacion relacionada al tramite
	 * @param OrigenSolcitudEnum - El origen de la solicitud ya sea internet o ventanilla
	 * @return Solicitud - La solicitud creada
	 * @throws DerechohabientesBusinessException
	 */
	@Override
	public Solicitud crearSolicitudCambioMedico(
			GrupoFamiliar integranteAfectado, Usuario usuario,
			AsignacionNSS nss, TramiteCorreccionDerechohabiente correccion,
			OrigenSolicitudEnum origen)
			throws DerechohabientesBusinessException {
		
		Solicitud solicitud = null;
		
		correccion.setIdAsignacionNss(nss.getIdAsignacionNSS());
		correccion.setIdPersona(integranteAfectado.getDerechohabiente().getIdPersona());
		correccion.setPersona(integranteAfectado.getDerechohabiente());
		
		// Guardamos la solicitud de correccion
		solicitud = tramiteServiceLocal.guardarSolicitudConInfoTramite(TipoTramiteEnum.CAMBIO_CONSULTORIO_TURNO, usuario, nss, 
				TipoSolicitudEnum.CORRECCION_DATOS_DERECHOHABIENTE, correccion, origen);
		// retornamos la solicitud
		return solicitud;
	}

	/**
	 * Metodo que obtiene a los candidatos a cambio de medico, consultorio o turno
	 * @param AsignacionNSS - El nss del cual se buscaran los candidatos
	 * @param Usuario - El usuario que esta realizando la solicitud
	 * @return List<GrupoFamiliar> - lista de integrantes candidatos a cambio de medico consultorio y turno
	 * @throws DerechohabientesBusinessException
	 */
	@Override
	public List<GrupoFamiliar> findGrupoFamiliarCambioMedico(AsignacionNSS nss,
			Usuario usuario) throws DerechohabientesBusinessException {
		
		List<GrupoFamiliar> candidatos = null;
		List<Long> idsEstados = new ArrayList<Long>();
		List<Long> idsParentescos = new ArrayList<Long>();
		//Solo se buscara a las personas con estos estados
		idsEstados.add(EstadoDerechohabienteEnum.VIGENTE.getId());
		idsEstados.add(EstadoDerechohabienteEnum.CON_DERECHO.getId());
		idsEstados.add(EstadoDerechohabienteEnum.CONSERVACION_DERECHOS.getId());
		idsEstados.add(EstadoDerechohabienteEnum.VIGENTE_POR_PRORRGA.getId());
		//solo se buscara a la cabeza e grupo familiar o a la o a el conyuge ya que son los unicos que pueden solicitar el tramite
		idsParentescos.add(ParentescoEnum.PENSIONADO.getId());
		idsParentescos.add(ParentescoEnum.ASEGURADO.getId());
		idsParentescos.add(ParentescoEnum.CONYUGE.getId());
		idsParentescos.add(ParentescoEnum.PADRES.getId());
		idsParentescos.add(ParentescoEnum.HIJOS.getId());
		
		
		try {
			//se busca a los candidatos que se encuentrwen en la umf del ususario
			candidatos = grupoFamiliarDaoLocal.findIntegrantesPorUmfEstadoIntegrantes(nss.getIdAsignacionNSS(), 
					usuario.getIdUmf(), idsEstados, null, idsParentescos,null);
		} catch (Exception e) {
			DerechohabientesBusinessException.throwException("Ocurri&oacute; un error al consultar la vigencia de los candidatos", 
					"Ocurri&oacute; un error al consultar la vigencia de los candidatos");
		}
		//Si no encontramos a nadie en la UMF retornamos una excepcion
		if(candidatos == null || candidatos.isEmpty()) {
			DerechohabientesBusinessException.throwException(
					ExceptionMessages.SIN_INFORMACION,
					"No se encontraron candidatos para el tr&aacute;mite");
		}
		
		return candidatos;
	}

	/**
	 * Metodo que agrega un tramite de cambio de medico a una solicitud
	 * @param TramiteCorreccionDerechohabiente - La informacion del tramite
	 * @param Long - id de la solicitud a la cual se asociara el tramite de cambio de medico
	 * @param AsignacionNSS - El nss en el cual estan el o los integramtes para el cambio
	 * @param List<GrupoFamiliar> - Listado con las personas a las que se les hara el cambio de medico, en caso de ser nulo se tomara
	 * la informacion que viene el el tramite, si no viene nulo solo se actualizara a los enviados en este parametro
	 * @param Boolean - Indicador para saber si el tramite proviene de un tramite de asignacion de domicilio
	 * @return TramiteCorreccionDerechohabiente 
	 */
	@Override
	public Map<String, Object> agregarTramiteCambioMedicoDependiente(
			TramiteCorreccionDerechohabiente correccion,
			Long idSolicitud, AsignacionNSS nss, List<GrupoFamiliar> afectados, Boolean asignacionDomicilio)
			throws DerechohabientesBusinessException {
		
		Map<String, Object> result = null;
		correccion.setTipoTramite(new TipoTramite());
		correccion.getTipoTramite().setIdTipoTramite(TipoTramiteEnum.CAMBIO_CONSULTORIO_TURNO.getCodigo());
		result = this.guardaCambioMedico(correccion,asignacionDomicilio, nss, afectados);
		
		correccion = TramiteUtil.getTramiteCorreccionFromMap(result);
		
		if(idSolicitud != null) {
			correccion = (TramiteCorreccionDerechohabiente) tramiteServiceLocal.saveTramiteCorreccionDependiente(correccion, idSolicitud);

			try {
				//insertamos en ditCorreccion
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
		} 
		return result;
	}
	
	/**
	 * Metodo para guardar la validacion del tramite de cambio de medico
	 * recibiendo los siguientes parametros:
	 * @param TramiteCorreccionDerechohabientes - tramite que contiene la  informacion a guardar
	 * @param Boolean - indicador para saber si el tramite se genero a partir de una sasignacion de domicilio
	 * @param AsignacionNSS - El nss del grupo familiar debe contener el idAsignacionNSS, el nss, y el nssStr
	 * @param List<GrupoFamiliar> - Los integrantes a los que se les hara el tramite, si vienen nulos o vacios se tomaran del tramite
	 * @return TramiteCorreccionDerechohabiente -  objeto que contendra la informacion ya guardada
	 * @throws DerechohabientesBusinessException
	 */
	private Map<String, Object> guardaCambioMedico(
			TramiteCorreccionDerechohabiente correccion,
			Boolean asignacionDomicilio, AsignacionNSS nss, List<GrupoFamiliar> afectados)
			throws DerechohabientesBusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		//Integrante de mayor calidad o el unico
		GrupoFamiliar afectado = null;
		List<GrupoFamiliar> grupoAux = new ArrayList<GrupoFamiliar>();
		//Se obtienen las personas que vengan del tramite
		List<Long> idsPersonasCambiadas = correccion.getCandidatosCambioClinica();
		List<Fisica> personasCambiadas = correccion.getPersonas();
		MedicoEnTurno destino = medicoEnTurnoDaoLocal.getMedicoEnTurnoById(correccion.getMedicoEnTurno().getIdMedicoContultorioTurno());
		correccion.setMedicoEnTurno(destino);
		
		//si la lista de afectados viene vacia o nula
		if(afectados == null || afectados.isEmpty()) {
			//creamos el array
			afectados = new ArrayList<GrupoFamiliar>();
			//verificamos si los ids de las personas vienn nulos o vacios, en caso no de ser asi
			if(idsPersonasCambiadas != null && ! idsPersonasCambiadas.isEmpty()) {
				//verficiamos si el tamaños d elos ids es el mismo que el de las personas
				if(personasCambiadas == null || personasCambiadas.size() != idsPersonasCambiadas.size() ) {
					//si los tamaños son diferentes tomaremos a las personas que vengan en los ids
					personasCambiadas = new ArrayList<Fisica>();
					//recorresmos los ids de las personas
					for(Long idIntegrante: idsPersonasCambiadas) {
						//añadimos a las personas y a los afectados
						GrupoFamiliar integrante = null;
						try {
							integrante = grupoFamiliarDaoLocal.getIntegranteSinVigencia(nss.getIdAsignacionNSS(), idIntegrante);
							if(integrante != null) {
								afectados.add(integrante);
								personasCambiadas.add(integrante.getDerechohabiente());
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}

					afectado = TramiteUtil.getIntegranteConMayorCalidad(afectados);
				} else {
					//si los tamaños corresponden
					idsPersonasCambiadas = new ArrayList<Long>();
					personasCambiadas = new ArrayList<Fisica>();
					//tan solo buscamos a los integrantes
					try {
						afectado = grupoFamiliarDaoLocal.getIntegranteSinVigencia(nss.getIdAsignacionNSS(), correccion.getIdPersona());
						afectados.add(afectado);
						idsPersonasCambiadas.add(afectado.getDerechohabiente().getIdPersona());
						personasCambiadas.add(afectado.getDerechohabiente());
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					afectado = TramiteUtil.getIntegranteConMayorCalidad(afectados);
				}

				if (afectado == null){
					DerechohabientesBusinessException.throwException("No se encontro al integrante del grupo familiar","No se encontro al integrante del grupo familiar");
				}
			} else {
				//si no vienen los ids
				idsPersonasCambiadas = new ArrayList<Long>();
				personasCambiadas = new ArrayList<Fisica>();
		
				try {
					 //buscamos a todas las personas en la umf y se excluye a la persona que esta relacionada al tramite
					 afectados = grupoFamiliarDaoLocal.findIntegrantesEnUmf(nss.getIdAsignacionNSS(), 
							 correccion.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF(), correccion.getIdPersona());
					 //si la lista regresa nulo 
					 if(afectados == null) {
						 afectados = new ArrayList<GrupoFamiliar>();
					 }
					 //se consulta y se agrega a la persoan que venia en el tramite
					 GrupoFamiliar integranteTramite =grupoFamiliarDaoLocal.getIntegranteSinVigencia(nss.getIdAsignacionNSS(), correccion.getIdPersona());
					 afectados.add(integranteTramite);
					 
					 //agregaremos los ids y las personas al xml
					 if(afectados != null && afectados.size() > 1) {
						 correccion.setPersona(null);
						 correccion.setIdPersona(null);
						 for(GrupoFamiliar integrante: afectados ) {
							 idsPersonasCambiadas.add(integrante.getDerechohabiente().getIdPersona());
							 personasCambiadas.add(integrante.getDerechohabiente());
						 }
						 
						 correccion.setCandidatosCambioClinica(idsPersonasCambiadas);
						 correccion.setPersonas(personasCambiadas);
					 }
					 //seobtiene al integrante con mayor calidad
					 afectado = TramiteUtil.getIntegranteConMayorCalidad(afectados);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			afectado = TramiteUtil.getIntegranteConMayorCalidad(afectados);
		}
		

		//si la lista de personas a afectar viene distinta de nula y no esta vacia nos
		//actualizamos directamente a los afectados y termina
		if(afectados != null && !afectados.isEmpty()) {
			//en este momento el array de afectados ya contiene a todos, asegurado, bajas , padres, concubina
			//y se procede a actualizar a todos
			for (GrupoFamiliar integrante : afectados) {
				//soli si viene desde un tramite se asignacion domicilio, cambiaremos e doimicilio de la persona
				if(asignacionDomicilio) {
					if(integrante.getEstadoDerechohabiente() == null || 
							!integrante.getEstadoDerechohabiente().getIdEstadoDerechohabiente().equals(EstadoDerechohabienteEnum.BAJA.getId())) {
						integrante.setDomicilio(correccion.getDomicilio());
					}
				}
				integrante.setFechaRegistroActualizacion(new Date());
				integrante.setMedicoEnTurno(correccion.getMedicoEnTurno());
				if(asignacionDomicilio) {
					integrante.setFechaCambioTurnoMedico(null);
				} else {
					integrante.setFechaCambioTurnoMedico(correccion.getFechaCambioMedico());
				}
				
				grupoAux.add(integrante);
				// y guardamos los nuevos datos del domicilio del derechohabiente
				try {
					grupoFamiliarDaoLocal.updateIntegrante(integrante);
				} catch (Exception e) {
					log.error("No se pudo actualizar el integrante", e);
					throw new DerechohabientesBusinessException("error.actualizar.integrante", e.getCause().getMessage());
				}
			}
		}
		
		result.put(TramiteUtil.KEY_TRAMITE, correccion);
		result.put(TramiteUtil.KEY_AFECTADOS, grupoAux);

		return result;
	
	}
}
