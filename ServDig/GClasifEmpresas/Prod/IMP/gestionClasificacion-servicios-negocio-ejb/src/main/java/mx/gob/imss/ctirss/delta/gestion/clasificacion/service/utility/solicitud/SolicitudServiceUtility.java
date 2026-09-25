/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: SolicitudServiceUtility.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.solicitud
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.solicitud;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisEnum;
import mx.gob.imss.ctirss.delta.model.clasificacion.SolicitudConcluida;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RazonCancelacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitudFirmaDigital;
import mx.gob.imss.ctirss.delta.persistence.DivSolicitudConcluida;

@Stateless
public class SolicitudServiceUtility extends AbstractServiceUtility implements SolicitudServiceUtilityLocal  {
	
	@Override
	public Solicitud convertirEntityToModel(DitSolicitud entity) throws Exception {

		Solicitud modelSolicitud = new Solicitud();
			
		if(entity != null){
			
			modelSolicitud.setId(new Long(entity.getCveIdSolicitud()));

			if(entity.getDicEstadoSolicitud() != null){
				EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
				estadoSolicitud.setIdEstadoSolicitud(entity.getDicEstadoSolicitud().getCveIdEstadoSolicitud());
				estadoSolicitud.setDescripcion(entity.getDicEstadoSolicitud().getDesEstadoSolicitud());
				modelSolicitud.setEstadoSolicitud(estadoSolicitud);
			}
			modelSolicitud.setFechaBaja(entity.getFecRegistroBaja());
			modelSolicitud.setNoFolioSolicitud(entity.getRefFolio());
			if(entity.getDicRazonCancelacion() != null){
				RazonCancelacion razonCancelacion = new RazonCancelacion();
				razonCancelacion.setIdRazonCancelacion(entity.getDicRazonCancelacion().getCveIdRazonCancelacion());
				razonCancelacion.setDescripcion(entity.getDicRazonCancelacion().getDesRazonCancelacion());
				modelSolicitud.setRazonCancelacion(razonCancelacion);
			}
			
			/*Se agrega la fecha de surte efecto*/
			modelSolicitud.setFechaCita(entity.getFecRegistroActualizado());
			modelSolicitud.setFechaSolicitud(entity.getFecSolicitud());
			
			TipoSolicitud tipoSolicitud = new TipoSolicitud();
			tipoSolicitud.setIdTipoSolicitud(entity.getDicTipoSolicitud().getCveIdTipoSolicitud());
			modelSolicitud.setTipoSolicitud(tipoSolicitud);
			
			if(entity.getDitSolicitudFirmaDigitals() != null && !entity.getDitSolicitudFirmaDigitals().isEmpty()) {
				DitSolicitudFirmaDigital firma = entity.getDitSolicitudFirmaDigitals().get(0);
				modelSolicitud.setSecuenciaDeNotaria(firma.getNumSecNotaria());
			}
			
		}
				
		return modelSolicitud;
	}
		
	/**
	 * Convierte una lista de objetos Entity DivSolicitudConcluida a una lista de objetos modelo SolicitudConcluida.
	 * @param origen Lista de DivSolicitudConcluida
	 * @return solicitudes Lista de SolicitudConcluida
	 */
	public List<SolicitudConcluida> convertirListOfVsolicitudconcluidasToSolicitudConcluida( List <DivSolicitudConcluida> origen){
		List<SolicitudConcluida> solicitudes = new ArrayList<SolicitudConcluida>();
		SolicitudConcluida model = null;
		for(DivSolicitudConcluida v : origen){
			model = this.convertirVsolicitudconcluidaToSolicitudConcluida(v);
			solicitudes.add(model);
		} 
		return solicitudes;
	}
		
	/**
	 * Convierte un bean entity DivSolicitudConcluida a un bean model SolicitudConcluida
	 * @param v
	 * @return
	 */
	public SolicitudConcluida convertirVsolicitudconcluidaToSolicitudConcluida(DivSolicitudConcluida v){
		
		SolicitudConcluida model = null;
		model = new SolicitudConcluida();
		model.setRegistroPatronal(v.getRegPatronCompleto());
		model.setRazonSocial(v.getRazonSocial());
		model.setNombreComercial(v.getNombreComercial());
		model.setNombre(v.getNombre());
		model.setFechaPresentacion(v.getFecPresentacion());
		
		if(v.getDesCausasAnalisis() != null && v.getDesCausasAnalisis().trim().length() > 0){
			model.setEstatusAnalisis(v.getDesCausasAnalisis());
			
//if(model.getEstatusAnalisis().equals("POR ASIGNAR"))
//	model.setEstatusAnalisis("PENDIENTE DE ANALISIS");
			
		}else{
			model.setEstatusAnalisis(EstatusAnalisisEnum.PENDIENTE_DE_ANALISIS.getDescripcion());
		}
		
		if(v.getCveIdEstatusAnalisis() != null){
			model.setCveIdEstatusAnalisis(v.getCveIdEstatusAnalisis().intValue());
		}
		model.setDelegacion(v.getDesDeleg());
		
		if(v.getCveIdDelegacion() != null){
			model.setIdDelegacion(v.getCveIdDelegacion().intValue());
		}
		model.setSubdelegacion(v.getDesSubdelegacion());
		
		if(v.getCveIdSubdelegacion() != null){
			model.setIdSubdelegacion(v.getCveIdSubdelegacion().intValue());
		}
		
		if(v.getCveIdTipoPersona() != null){ 
			model.setIdTipoPersona(v.getCveIdTipoPersona().toString().equals("1") ? 1 : 2);
		}		

		model.setEstatusMovimiento(v.getDesEstadoTramite());
		
		if(v.getCveIdEstadoTramite() != null){
			model.setIdEstatusMovimiento(v.getCveIdEstadoTramite().intValue());
		}
		
		if(v.getCveIdTipoCausa() != null){
			model.setIdTipoCausa(v.getCveIdTipoCausa().intValue());
		}
		
		if(v.getCveIdAnalisis() != null){
			model.setCveIdAnalisis(v.getCveIdAnalisis().intValue());
		}
		
		if(v.getCveIdSolicitud() != null){
			model.setCveIdSolicitud(v.getCveIdSolicitud().intValue());
		}
		
//		if(v.getCveIdUsuario() != null){
//			model.setCveUsuario(v.getCveIdUsuario().toString());
//		}
		
		model.setCveUsuarioSso((null == v.getCveUsuarioSso()) ? "" : v.getCveUsuarioSso());
		
//		model.setNomUsuarioSistema((null == v.getNomUsuarioSistema()) ? "" : v.getNomUsuarioSistema());
		
		return model;
	}
	
}
