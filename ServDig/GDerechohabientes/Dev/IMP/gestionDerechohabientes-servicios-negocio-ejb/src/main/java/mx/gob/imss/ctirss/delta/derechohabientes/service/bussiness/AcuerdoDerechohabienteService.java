package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.GrupoFamiliarDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.entity.AcuerdoDerechohabienteEntityLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.AcuerdoDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.util.TramiteUtil;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.EstadoAcuerdoDh;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.enums.EstadoAcuerdoDhEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.SubestadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAcuerdoDh;

@Stateless(name = "acuerdoDerechohabienteService", mappedName = "acuerdoDerechohabienteService")
public class AcuerdoDerechohabienteService extends AbstractServiceBusiness implements AcuerdoDerechohabienteServiceRemote {

	@EJB
	private GrupoFamiliarDaoLocal grupoFamiliarDaoLocal;
	@EJB
	private TramiteServiceLocal tramiteServiceLocal;
	@EJB
	private SolicitudServiceLocal solicitudServiceLocal;
	@EJB
	private AcuerdoDerechohabienteEntityLocal acuerdoDerechohabienteEntityLocal;
	
	@Override
	public List<GrupoFamiliar> findCandidatosAcuerdo(Long idAsignacionNSS) throws DerechohabientesBusinessException{
		List<GrupoFamiliar> beneficiarios = null;
		List<GrupoFamiliar> candidatos = null;
		try {
			beneficiarios = grupoFamiliarDaoLocal.findGrupoFamiliarByParentesco(idAsignacionNSS, ParentescoEnum.PADRES.getId());
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DerechohabientesBusinessException("Ocurrio un error al obtener los candidatos al tramite.");
		}
		
		//verificaramos a los candidatos
		if(beneficiarios != null && !beneficiarios.isEmpty()) {
			candidatos = new ArrayList<GrupoFamiliar>();
			for(GrupoFamiliar candidato: beneficiarios) {
				Long idPersona = candidato.getDerechohabiente().getIdPersona();
				Long idEstadoDerechohabiente = candidato.getEstadoDerechohabiente().getIdEstadoDerechohabiente();
				Long idSubEstado = candidato.getSubEstadoDerechohabiente() != null ? candidato.getSubEstadoDerechohabiente().getIdSubEstadoDerechohabiente() : null;
				
				if(idEstadoDerechohabiente.equals(EstadoDerechohabienteEnum.BAJA.getId()) && idSubEstado != null && idSubEstado.equals(SubestadoDerechohabienteEnum.FALLECIMIENTO.getId())) {
					// si está dato de baja por fallecimiento nos saltamos a la persona
					continue;
				} else if(acuerdoDerechohabienteEntityLocal.getAcuerdoDerechohabiente(idAsignacionNSS, idPersona, 1L) == null) {
					//si no esta en baja por fallecimiento y no tiene acuerdo activo anadimos al beneficiario al grupo
					candidatos.add(candidato);
				}
			}
		}
		
		return candidatos;
	}

	@Override
	public Solicitud crearSolicitudAcuerdo(GrupoFamiliar integrante,TramiteAcuerdoDh tramite, Usuario usuario, OrigenSolicitudEnum origenSolicitud) throws DerechohabientesBusinessException {
		
		Solicitud solicitudCreada = null;
		AsignacionNSS asegurado = integrante.getAsignacionNSS();
		TipoSolicitudEnum tipoSolicitud = TipoSolicitudEnum.REGISTRO_ACUERDO_DERECHOHABIENTES;
		TipoTramiteEnum tipoTramite = TipoTramiteEnum.REGISTRO_ACUERDO_DERECHOHAB;
		
		
		solicitudCreada = tramiteServiceLocal.guardarSolicitud(integrante, tipoTramite, usuario, asegurado, tipoSolicitud, tramite, origenSolicitud);
		
		return solicitudCreada;
	}

	@Override
	public Solicitud finalizarSolicitudAcuerdo(Solicitud solicitud,
			AsignacionNSS asignacionNSS) throws SolicitudNoValidaException,
			SolicitudException {
		
		if(solicitud != null && !solicitud.getTramites().isEmpty()) {
			
			TramiteAcuerdoDh tramiteAcuerdo = TramiteUtil.getTramiteAcuerdoFromSolicitud(solicitud);
			
			if(tramiteAcuerdo == null) {
				throw new SolicitudNoValidaException("La solicitud no contiene tramites de acuerdo");
			}
			
			try {
				tramiteAcuerdo.setIdAsignacionNSS(asignacionNSS.getIdAsignacionNSS());
				tramiteAcuerdo.setFechaTramite(new Date());
				tramiteAcuerdo.setEstadoAcuerdoDh(new EstadoAcuerdoDh(EstadoAcuerdoDhEnum.ACTIVO.getId()));
				
				tramiteAcuerdo = acuerdoDerechohabienteEntityLocal.saveAcuerdoDerechohabiente(tramiteAcuerdo);
			} catch(Exception e) {
				e.printStackTrace();
				throw new SolicitudException("Ocurrio un error al finalizar la solicitud de acuerdo");
			}
			
			try{
				tramiteServiceLocal.actualizaXMLTramite(tramiteAcuerdo);
				solicitudServiceLocal.marcarAtendidaSolictud(solicitud.getSolicitudId(), tramiteAcuerdo.getObservacion(), null);
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
		} else {
			throw new SolicitudNoValidaException("La solicitud no puede ser nula y debe contener tramites");
		}
		
		return solicitud;
	}

}