package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.GrupoFamiliarDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.entity.BajaDerechohabienteEntityLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.entity.ReactivacionEntityLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.TramitesAdministrativosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.util.GrupoFamiliarUtil;
import mx.gob.imss.ctirss.delta.derechohabientes.util.TramiteUtil;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ImpactaAlmacenesWSException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.TipoBajaDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.BajaDerechohabienteDto;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.SubestadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteBajaDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteReactivacionDerechohab;

@Stateless(name = "tramitesAdministrativosService", mappedName = "tramitesAdministrativosService")
public class TramitesAdministrativosService extends AbstractServiceBusiness
		implements TramitesAdministrativosServiceRemote {

	@EJB
	private GrupoFamiliarDaoLocal grupoFamiliarDaoLocal;
	@EJB
	private BajaDerechohabienteServiceLocal bajaDerechohabienteServiceLocal;
	@EJB
	private TramiteServiceLocal tramiteServiceLocal;
	@EJB
	private TramiteDocumentosServiceLocal tramiteDocumentosServiceLocal;
	@EJB
	private BajaDerechohabienteEntityLocal bajaDerechohabienteEntityLocal;
	@EJB
	private SolicitudServiceLocal solicitudServiceLocal;
	@EJB
	private FinalizaSolicitudServiceLocal finalizaSolicitudServiceLocal;
	@EJB
	private ReactivacionEntityLocal reactivacionEntityLocal;
	@EJB
	private FirmaDigitalBusinessRemote firmaDigitalBusinessRemote;
	
	/**
	 * Metodo para encontrar a los candidatos a suspencion administrativa
	 * @param nss
	 * @return List<GrupoFamiliar> lista con los integrantes candidatos al tramite
	 */
	@Override
	public List<GrupoFamiliar> findCandidatosSuspencionAdministrativa(AsignacionNSS nss) throws DerechohabientesBusinessException{
		//Creamos una lista auxiliar para que quitemos al segurado de la lista
		List<GrupoFamiliar> candidatosBaja = null;
		//Buscaremos a los candidatos que esten vigentes dentro del grupo familiar
		List<Long> estadosBusqueda = new ArrayList<Long>();
		estadosBusqueda.add(EstadoDerechohabienteEnum.VIGENTE.getId());
		estadosBusqueda.add(EstadoDerechohabienteEnum.VIGENTE_POR_PRORRGA.getId());
		//buscamos a los integrantes en baja
		try {
			candidatosBaja = grupoFamiliarDaoLocal.findGrupoFamiliarByEstado(nss.getIdAsignacionNSS(), estadosBusqueda);
		
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		//validamos que la lista no este vacia o sea nula
		//GrupoFamiliarUtil.validarIntegrantesVacios(candidatosBaja);
		return GrupoFamiliarUtil.filtraAseguradoPensionado(candidatosBaja);
		
		
	}
	
	

	@Override
	public List<GrupoFamiliar> findCandidatosBajaAdministrativa(
			AsignacionNSS nss) throws DerechohabientesBusinessException {
		List<GrupoFamiliar> candidatosBaja = null;
		
		//consultamos a todos los integrantes del grupo familiar
		candidatosBaja = grupoFamiliarDaoLocal.findDatosBasicosIntegrantesGrupoByIdAsignacionNss(nss.getIdAsignacionNSS(), true, false, false, null, null);
		
		//validamos que la lista no este vacia o sea nula
		GrupoFamiliarUtil.validarIntegrantesVacios(candidatosBaja);
		
		return candidatosBaja;
	}


	
	@Override
	public List<GrupoFamiliar> findCandidatosReactivacionAdministrativa(
			AsignacionNSS nss) throws DerechohabientesBusinessException {
		//lista de los candidatos a suspencion administrativa
		List<GrupoFamiliar> integrantesBaja = null;
		//se crea lista para integrantes activos para validar que no se puedan repetir parentescos
		List<GrupoFamiliar> integrantesActivos = null;
		//Creamos una lista auxiliar para que quitemos al segurado de la lista
		List<GrupoFamiliar> candidatosReactivacion = null;
		List<GrupoFamiliar> padresConyuges = new ArrayList<GrupoFamiliar>();
		//Buscaremos a los candidatos que esten vigentes dentro del grupo familiar
		List<Long> estadosBusqueda = new ArrayList<Long>();
		List<Long> tiposBaja = new ArrayList<Long>();
		tiposBaja.add(TipoBajaDerechohabienteEnum.TERMINO_CONVIVENCIA.getId());
		
		List<Long> tiposBajaConcubinaConyuge = new ArrayList<Long>();
		tiposBajaConcubinaConyuge.add(TipoBajaDerechohabienteEnum.DIVORCIO.getId());
		tiposBajaConcubinaConyuge.add(TipoBajaDerechohabienteEnum.TERMINO_CONCUBINATO.getId());
		
		estadosBusqueda.add(EstadoDerechohabienteEnum.BAJA.getId());
		boolean isTotalConyuges = false;
		boolean isTotalPadres = false;
		int countPadres = 0;
		//buscamos a los integrantes en baja
		try {
			integrantesBaja = grupoFamiliarDaoLocal.findGrupoFamiliarByEstado(nss.getIdAsignacionNSS(), estadosBusqueda);
			estadosBusqueda.clear();
			estadosBusqueda.add(EstadoDerechohabienteEnum.VIGENTE.getId());
			integrantesActivos = grupoFamiliarDaoLocal.findGrupoFamiliarByEstado(nss.getIdAsignacionNSS(), estadosBusqueda);
			
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		//validamos que la lista no este vacia o sea nula
		GrupoFamiliarUtil.validarIntegrantesVacios(integrantesBaja);
		
		//se generan banderas para saber que parentescos ya estan ocupados
		if(integrantesActivos != null && !integrantesActivos.isEmpty()){
			for(GrupoFamiliar integranteVigente: integrantesActivos){
				long idParentesco = integranteVigente.getParentesco().getIdParentesco().intValue();
				if(idParentesco == ParentescoEnum.PADRES.getId()){
					countPadres=countPadres+1;
					if(countPadres >= 2){
						isTotalPadres= true;
					}
				}else if(idParentesco == ParentescoEnum.CONCUBINA.getId() || 
						idParentesco == ParentescoEnum.CONYUGE.getId()){
						isTotalConyuges = true;
				}
			}
		}
		//inicializamos la lista de candidatos
		candidatosReactivacion = new ArrayList<GrupoFamiliar>();
		for(GrupoFamiliar integrante: integrantesBaja) {
			//obtenemos el parentesco del integrate
			Long idParentesco = integrante.getParentesco().getIdParentesco();
			//se agregan todos los hijos con suspension
			if(idParentesco.longValue() == ParentescoEnum.HIJOS.getId() && 
					integrante.getSubEstadoDerechohabiente().getIdSubEstadoDerechohabiente().equals(SubestadoDerechohabienteEnum.SUSPENCION_ADMINISTRATIVA.getId())) {
				candidatosReactivacion.add(integrante);
			//se valida que los padres no esten en su totalidad para el grupo familiar sin importar el tipo de baja
			} else if(idParentesco.equals(ParentescoEnum.PADRES.getId()) && !isTotalPadres) {
				if(integrante.getSubEstadoDerechohabiente().getIdSubEstadoDerechohabiente().equals(SubestadoDerechohabienteEnum.SUSPENCION_ADMINISTRATIVA.getId())
						&& !isTotalPadres){
					candidatosReactivacion.add(integrante);
					countPadres=countPadres+1;
					if(countPadres >= 2){
						isTotalPadres= true;
					}
				} else if(!isTotalPadres) {
					List<Long> idsPersonas = new ArrayList<Long>();
					idsPersonas.add(integrante.getDerechohabiente().getIdPersona());
					List<BajaDerechohabienteDto> bajas = bajaDerechohabienteServiceLocal.getBajaDerechohabiente(nss.getIdAsignacionNSS(), idsPersonas, tiposBaja, true);
					if(bajas != null && !bajas.isEmpty()) {
						candidatosReactivacion.add(integrante);
						countPadres=countPadres+1;
						if(countPadres >= 2){
							isTotalPadres= true;
						}
					}
				}
			}else if((idParentesco.equals(ParentescoEnum.CONCUBINARIO.getId()) || idParentesco.equals(ParentescoEnum.CONCUBINA.getId()) ||
					idParentesco.equals(ParentescoEnum.CONYUGE.getId())) && !isTotalConyuges){
				    if(integrante.getSubEstadoDerechohabiente().getIdSubEstadoDerechohabiente().equals(SubestadoDerechohabienteEnum.SUSPENCION_ADMINISTRATIVA.getId())){
						candidatosReactivacion.add(integrante);
						//isTotalConyuges = true;
					}else if(!isTotalConyuges){
						List<Long> idsPersonas = new ArrayList<Long>();
						idsPersonas.add(integrante.getDerechohabiente().getIdPersona());
						List<BajaDerechohabienteDto> bajas = bajaDerechohabienteServiceLocal.getBajaDerechohabiente(nss.getIdAsignacionNSS(), idsPersonas, tiposBajaConcubinaConyuge, true);
						if(bajas != null && !bajas.isEmpty()) {
							candidatosReactivacion.add(integrante);
							//isTotalConyuges = true;
						}
					}
			}
		}
		
		//retornamos a los candidatos a suspencion administrativa
		return candidatosReactivacion;
	}



	@Override
	public Solicitud crearSolicitudBajaSuspencion(GrupoFamiliar integrante,
			AsignacionNSS asegurado, Usuario usuario,
			OrigenSolicitudEnum origenSolicitud, TipoTramiteEnum tipoTramite) throws Exception {
		
		Solicitud solicitudCreada = null;
		TipoSolicitudEnum tipoSolicitud = null;
		TramiteBajaDerechohabiente baja = new TramiteBajaDerechohabiente();
		
		if(tipoTramite.getCodigo().equals(TipoTramiteEnum.BAJA_ADMINISTRATIVA.getCodigo())) {
			tipoSolicitud = TipoSolicitudEnum.BAJA_ADMINISTRATIVA;
		} else {
			tipoSolicitud = TipoSolicitudEnum.SUSPENCION_ADMINISTRATIVA;
		}
		
		solicitudCreada = tramiteServiceLocal.guardarSolicitud(integrante, tipoTramite, usuario, asegurado, tipoSolicitud, baja, origenSolicitud);
		
		return solicitudCreada;
	}
	
	
	
	@Override
	public Solicitud crearSolicitudReactivacion(GrupoFamiliar integrante,
			AsignacionNSS asegurado, Usuario usuario) throws Exception {

		Solicitud solicitudCreada = null;
		TipoSolicitudEnum tipoSolicitud = TipoSolicitudEnum.REACTIVACION_ADMINISTRATIVA;
		TramiteReactivacionDerechohab tramite = new TramiteReactivacionDerechohab();
		solicitudCreada = tramiteServiceLocal.guardarSolicitud(integrante, TipoTramiteEnum.REACTIVACION_ADMINISTRATIVA, usuario, asegurado, tipoSolicitud, tramite, OrigenSolicitudEnum.VENTANILLA);
		
		return solicitudCreada;
	}



	@Override
	public Solicitud finalizarTramiteBajaSuspencionAdministrativa(Solicitud solicitud, AsignacionNSS asignacionNSS) 
			throws SolicitudNoValidaException, SolicitudException, ImpactaAlmacenesWSException{
		
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
				
				Long idAsignacionNSS = asignacionNSS.getIdAsignacionNSS();
				Long idPersona = tramiteBaja.getPersona().getIdPersona();
				TipoTramite tipoTramite = tramiteBaja.getTipoTramite();
				log.debug("finalizare la solicitud " + solicitud.getNoFolioSolicitud()  + " que tiene un tramite de " + tipoTramite.getDescripcion());
				//Insertamos un registro en dit baja para que se calcule la vigencia del derechohabiente
				BajaDerechohabienteDto baja = bajaDerechohabienteEntityLocal.insertFromTramiteBaja(tramiteBaja);
				//generamos la fiana electronica para la solicitud
				try {
					tramiteDocumentosServiceLocal.generaFirmaElectronica(asignacionNSS,solicitud, tipoTramite.getDescripcion());
				} catch(Exception e) {
					log.error("ocurrio un error al generar la firma digital relacionada a la solicitud");
				}
				
				
				if(tipoTramite.getIdTipoTramite().equals(TipoTramiteEnum.BAJA_ADMINISTRATIVA.getCodigo())) {
					try {
						grupoFamiliarDaoLocal.actualizarFechaBaja(asignacionNSS.getIdAsignacionNSS(), tramiteBaja.getPersona().getIdPersona(), new Date());
					} catch (Exception e) {
						e.printStackTrace();
						throw new SolicitudNoValidaException("No se pudo actualizar la fecha de baja");
					}
				}

				//Se manda llamar al WS
				try {
					finalizaSolicitudServiceLocal.finalizaSolicitudBajaDerechohabiente(solicitud, baja, true);
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
				
				//si el tipo de tramite no es baja administrativa checamos el estado en el que queda el integrante
				//ya que cuando se hace la baja administrativa ningun ws regresa a la persona
				if(!tipoTramite.getIdTipoTramite().equals(TipoTramiteEnum.BAJA_ADMINISTRATIVA.getCodigo())) {
					//ponemos en el xml el estado del derechohabiente
					tramiteBaja.setEstadoDerechohabiente(grupoFamiliarDaoLocal.getEstadoIntegrante(idAsignacionNSS, idPersona));
				}
				
				//mandamos a llamar al servicio local para que se establezcan el resultado y la razon del resultado
				try{
					tramiteServiceLocal.actualizaXMLTramite(tramiteBaja);
					solicitudServiceLocal.marcarAtendidaSolictud(solicitud.getSolicitudId(), tramiteBaja.getObservaciones(), null);
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
		} else {
			throw new SolicitudNoValidaException("La solicitud no puede ser nula");
		}

		return solicitud;	
	}

	@Override
	public Solicitud finalizarReactivacionAdministrativa(Solicitud solicitud,
			AsignacionNSS asignacionNSS) throws SolicitudNoValidaException,
			SolicitudException, ImpactaAlmacenesWSException {
		//Si la solicitud no es nula 
		if(solicitud != null) {
			
			//Verificamos que la solicitud contenga al menos un tramite
			if(solicitud.getTramites().isEmpty()) {
				throw new SolicitudNoValidaException("La solicitud no contiene tramites");
			} else {

				//Verificamos que la solicitud contenga tramites de tipo baja de derechohabiente
				TramiteReactivacionDerechohab tramiteReactivacion = TramiteUtil.getTramiteReactivacionFromSolicitud(solicitud);
		
				//Si no encontramos ningun tramite de reactivacion lanzamos una excepcion
				if(tramiteReactivacion == null) {
					throw new SolicitudNoValidaException("No existen tramites de reactivacion en la solicitud");
				}
				
				TipoTramite tipoTramite = tramiteReactivacion.getTipoTramite();
				Long idAsignacionNSS = asignacionNSS.getIdAsignacionNSS();
				Long idPersona = tramiteReactivacion.getPersona().getIdPersona();
				List<Long> idsPersonas = new ArrayList<Long>();
				idsPersonas.add(idPersona);
				List<BajaDerechohabienteDto> bajas = bajaDerechohabienteEntityLocal.getBajaDerechohabiente(idAsignacionNSS, idsPersonas, null, true);
				
				if(bajas != null && !bajas.isEmpty()) {
					tramiteReactivacion.setIdBaja(bajas.get(0).getCveIdBaja());
					
					//Actualizamos la baja
					BajaDerechohabienteDto bajaDto = bajas.get(0);
					bajaDto.setIndBajaActiva(null);
					bajaDto.setFecRegistroActualizaco(new Date());
					bajaDerechohabienteServiceLocal.actualizarInsertarBaja(bajaDto);

					// -----------------------------------------
					// En el WebService se envía 0
					// Localmente se asigna a null
					// -----------------------------------------
					bajaDto.setIndBajaActiva(0L);
					TramiteBajaDerechohabiente tramite = new TramiteBajaDerechohabiente(bajaDto.getCveIdTramite());
					tramite.setPersona(tramiteReactivacion.getPersona());
					Solicitud solicitudBaja = new Solicitud(tramite);

					try {
						finalizaSolicitudServiceLocal.finalizaSolicitudBajaDerechohabiente(solicitudBaja, bajaDto, false);
					} catch (ImpactaAlmacenesWSException e) {
						log.error("Ocurrio uun error al finalizar la modificacion del derechohabiente", e);
						throw e;
					} catch (IllegalArgumentException e1) {
						e1.printStackTrace();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				
				log.debug("finalizare la solicitud " + solicitud.getNoFolioSolicitud()  + " que tiene un tramite de " + tipoTramite.getDescripcion());
				//Insertamos un registro en dit baja para que se calcule la vigencia del derechohabiente
				tramiteReactivacion = reactivacionEntityLocal.insertFromTramiteReactivacion(tramiteReactivacion);
				//generamos la fiana electronica para la solicitud
				try {
					tramiteDocumentosServiceLocal.generaFirmaElectronica(asignacionNSS,solicitud, tipoTramite.getDescripcion());
				} catch(Exception e) {
					log.error("ocurrio un error al generar la firma digital relacionada a la solicitud");
				}
				
				//seteamos el estado del derechohabiente
				tramiteReactivacion.setEstadoDerechohabiente(grupoFamiliarDaoLocal.getEstadoIntegrante(idAsignacionNSS, idPersona));
				
				//mandamos a llamar al servicio local para que se establezcan el resultado y la razon del resultado
				try{
					tramiteServiceLocal.actualizaXMLTramite(tramiteReactivacion);
					solicitudServiceLocal.marcarAtendidaSolictud(solicitud.getSolicitudId(), tramiteReactivacion.getObservacion(), null);
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
		} else {
			throw new SolicitudNoValidaException("La solicitud no puede ser nula");
		}

		return solicitud;	
	}
	
}