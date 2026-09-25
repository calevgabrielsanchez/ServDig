package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.GrupoFamiliarDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.entity.CorreccionDerechohabienteEntityLocal;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.model.derechohabiente.EstadoDerechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.nss.AsignacionSerieNSS;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;

@Stateless(name = "asignacionUMFService", mappedName = "asignacionUMFService")
public class AsignacionUMFService extends AbstractServiceBusiness implements
		AsignacionUMFServiceLocal {

	@EJB
	GrupoFamiliarDaoLocal grupoFamiliarDaoLocal;
	@EJB
	TramiteServiceLocal tramiteServiceLocal;
	@EJB
	CorreccionDerechohabienteEntityLocal correccionDerechohabienteEntityLocal;
	@EJB
	CambioClinicaServiceLocal cambioClinicaServiceLocal;
	
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
			
			log.debug("Se procede a hacer el cambio de clinica del derechohabiente");
			cambioClinicaServiceLocal.agregarTramiteCambioClinicaDependiente(tramiteCambio, afectado.getAsignacionNSS(), patronImss,
					null, idSolicitud, null, asignacionDomicilio, null);
		}
		
		return personasAfectadas;
	}
}
