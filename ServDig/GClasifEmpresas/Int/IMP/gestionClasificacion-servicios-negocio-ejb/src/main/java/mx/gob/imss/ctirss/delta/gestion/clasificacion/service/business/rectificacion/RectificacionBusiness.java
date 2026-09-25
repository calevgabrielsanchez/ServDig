/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: H�ctor Lara Andr�s
 *  @Proyecto: delta
 *  @Archivo: RectificacionBusiness.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.rectificacion
 *  @Fecha:17/08/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.rectificacion;

import java.math.BigDecimal;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jfree.util.Log;

import mx.gob.imss.ctirss.delta.exception.clasificacion.AnalisisNoEncontradoException;
import mx.gob.imss.ctirss.delta.exception.clasificacion.EstatusMovimientoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ClasificacionPropuestaDTO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.DictamenDTO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.analisis.AnalisisServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.bitacora.BitacoraServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clasificacion.ClasificacionPropuestaServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.AnalisisServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.rectificacion.RectificacionBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.bitacora.BitacoraServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.sujetoobligado.SujetoObligadoServiceUtilityLocal;
/*import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;*/
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisEnum;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisModel;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
/*import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;*/

@Stateless(name = "rectificacionServiceBusiness", mappedName = "rectificacionServiceBusiness")
public class RectificacionBusiness extends AbstractServiceBusiness implements RectificacionBusinessRemote{
	
	@EJB
	private ClasificacionPropuestaServiceEntityLocal clasificacionPropuestaEntity;
	
	@EJB
	private AnalisisServiceEntityLocal analisisEntity;
	
	@EJB
	private SolicitudServiceBusinessRemote solicitudPatronalBusiness;
	
	@EJB
	private BitacoraServiceEntityLocal bitacoraServiceEntity;
	
	@EJB
	private BitacoraServiceUtilityLocal bitacoraUtility;
	
	@EJB
	private mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud.SolicitudServiceBusinessRemote solicitudServiceBusinessRemote;
	
	@EJB
	private SujetoObligadoServiceUtilityLocal sujetoObligadoUtility;

	@EJB
	private AnalisisServiceBusinessRemote analisisBusiness;

	/*@Override
	public AnalisisClasificacionEmpresas consultarClasificacionAnteriorPorIdSolicitud(Long idSolicitud) throws Exception {
		Solicitud solicitud=new Solicitud();
		AnalisisClasificacionEmpresas response=new AnalisisClasificacionEmpresas();
		solicitud=solicitudPatronalBusiness.consultarSolicitudPorId(idSolicitud);
		
		if(solicitud!=null){

			TramiteSujetoObligado tramite = sujetoObligadoUtility.obtenerTramiteSujetoObligado(solicitud.getTramites(), 
					solicitud.getTipoSolicitud().getIdTipoSolicitud());
			
			response.setClasificacionAnterior(tramite.getSujetoObligado().getClasificacion());
		}
		
		return response;
	}*/

	@Override
	public AnalisisClasificacionEmpresas rechazoPorReglaRPC(AnalisisClasificacionEmpresas analisisClasificacionEmpresas)throws Exception{
		return analisisEntity.actualizaEstado(analisisClasificacionEmpresas);
	}

	@Override
	public Integer guardaRectificacion(ClasificacionPropuestaDTO dto, Boolean esDictamen)throws Exception {
		AnalisisClasificacionEmpresas analisisClasificacionEmpresas=new AnalisisClasificacionEmpresas();
		SujetoObligado sujetoObligado=new SujetoObligado();
		Clasificacion clasificacionAnterior=new Clasificacion();
		Clasificacion clasificacionActual=new Clasificacion();
		Clasificacion clasificacion=new Clasificacion();
		Fraccion fraccion=new Fraccion();
		Grupo grupo=new Grupo();
		Division division=new Division();

		EstatusAnalisisModel estatusAnalisisModel = new EstatusAnalisisModel();
		Integer status=0;
		log.info("ClasificacionPropuestaDTO::");
		log.info(dto);
		try{
			division.setId(Long.parseLong(dto.getCveIdDivision()));
			grupo.setId(Long.parseLong(dto.getCveIdGrupo()));
			grupo.setDivision(division);
			fraccion.setId(Long.parseLong(dto.getCveIdFraccionPro()));
			fraccion.setPrimaSRT(BigDecimal.valueOf(Double.parseDouble(dto.getPrimaSRTPro())));
			fraccion.setGrupo(grupo);
			
			analisisClasificacionEmpresas.setCveIdAnalisis(Long.valueOf(Long.parseLong(dto.getCveIdAnalisis().trim())));
			analisisClasificacionEmpresas.setActividadDetectada(dto.getActividadDetectada());
			clasificacion.setFraccion(fraccion);
			analisisClasificacionEmpresas.setClasificacionPropuesta(clasificacion);
			analisisClasificacionEmpresas.setIndModAut(new Long(dto.getIndModAut()));
			analisisClasificacionEmpresas.setClaveUsuarioAsignado(dto.getCveUsuarioAsignado());
			analisisClasificacionEmpresas.setCveIdDelegacion(new Long(dto.getCveIdDelegacion()));
			analisisClasificacionEmpresas.setCveIdSubdelegacion(new Long(dto.getCveIdSubdelegacion()));
			analisisClasificacionEmpresas.setCveIdAnalisis(new Long(dto.getCveIdAnalisis()));
			if(dto.getPrimaSugerida() != null && dto.getPrimaSugerida().length()>0){
				analisisClasificacionEmpresas.setPrimaSugerida(new BigDecimal(dto.getPrimaSugerida()));
			}
			
			if(dto.getRegPatronal().length()!=11 && !esDictamen){
				dto.setRegPatronal(solicitudServiceBusinessRemote.obtenerRegistroPatronalCompleto(dto.getRegPatronal()));
			}
			
			if(dto.getRegPatronal().length()<10 && esDictamen){
				dto.setRegPatronal(solicitudServiceBusinessRemote.obtenerRegistroPatronalCompleto(dto.getRegPatronal()));
			}
							
			/**//**//**//**//**//**//**//**//**//**/
			/* 		ACTUALIZA CLASIFICACIONES 	  */
			/**//**//**//**//**//**//**//**//**//**/
			sujetoObligado.setNumeroRegistroPatronal(dto.getRegPatronal());
			//sujetoObligado.setTipoPersonaFiscal(dto.getTipoPersona().toString().trim().equals("2")?MORAL:FISICA);
			sujetoObligado.setTipoPersonaFiscal(dto.getTipoPersona().toString().trim().equals("2") ? TipoPersonaFiscal.MORAL : TipoPersonaFiscal.FISICA);
			
			
			if(esDictamen) {
				sujetoObligado = solicitudServiceBusinessRemote.obtenerDetalleSolicitudDictamen(sujetoObligado);
			} else {
				sujetoObligado = solicitudServiceBusinessRemote.obtenerDetalleSolicitud(sujetoObligado);
			}
			
			/* Pasa Clasificacion Actual como Anterior */
			clasificacionActual.setId(sujetoObligado.getClasificacion().getId());
			Fraccion fraccionAct = new Fraccion();
			fraccionAct.setId(Long.parseLong(dto.getCveIdFraccionAct()));
			fraccionAct.setPrimaSRT(BigDecimal.valueOf(Double.parseDouble(dto.getPrimaSRTAct())));
			clasificacionActual.setFraccion(fraccionAct);
			
			Fraccion fraccionAnt = new Fraccion();
			analisisClasificacionEmpresas.setClasificacionActual(clasificacionActual);
			
			if(dto.getCveIdFraccionAnt()!=null && !dto.getCveIdFraccionAnt().trim().equals("")){
				fraccionAnt.setId(Long.parseLong(dto.getCveIdFraccionAnt()));
				fraccionAnt.setPrimaSRT(BigDecimal.valueOf(Double.parseDouble(dto.getPrimaSRTAnt())));
				clasificacionAnterior.setFraccion(fraccionAnt);
				analisisClasificacionEmpresas.setClasificacionAnterior(clasificacionAnterior);
			}
			
			/*****					Crea Clasificaci�n Propuesta					*****/
			analisisClasificacionEmpresas.setCveIdEstatus(Long.valueOf(EstatusAnalisisEnum.RECTIFICADO_PENDIENTE_AUTORIZACION.getClave()));
			analisisClasificacionEmpresas.setEstatus(EstatusAnalisisEnum.RECTIFICADO_PENDIENTE_AUTORIZACION);				

			if(clasificacionPropuestaEntity.consultaPorIdAnalisis(analisisClasificacionEmpresas.getCveIdAnalisis().longValue())!=null){
				clasificacionPropuestaEntity.actualiza(analisisClasificacionEmpresas);
				status=1;
			}else{
				clasificacionPropuestaEntity.agrega(analisisClasificacionEmpresas);
				status=1;
			}

			// Valida si el analisis ha sido modificado por otro usuario.
			analisisBusiness.validaEstatusMovimiento(
					new Long(dto.getCveIdAnalisis()).longValue(), 
					"" + EstatusAnalisisEnum.PENDIENTE_DE_ANALISIS.getClave() + 
					", " + EstatusAnalisisEnum.RATIFICADO_RECHAZADO.getClave() + 
					", " + EstatusAnalisisEnum.RECTIFICADO_RECHAZADO.getClave() + 
					", " + EstatusAnalisisEnum.RATIFICADO_AUTORIZADO_Y_RECHAZADO.getClave() +
					", " + EstatusAnalisisEnum.RECTIFICADO_AUTORIZADO_Y_RECHAZADO.getClave());

			//ACTUALIZA STATUS
			analisisEntity.actualizaEstado(analisisClasificacionEmpresas);
			status=2;
			estatusAnalisisModel = bitacoraUtility.armaBitacora(analisisClasificacionEmpresas, dto.getComentarios());
			bitacoraServiceEntity.guardaBitacora(estatusAnalisisModel);
			
			status=1;

		}catch(AnalisisNoEncontradoException e){
			status=0;
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		}catch(EstatusMovimientoException e){
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		}

		return status;
	}

	@Override
	public Integer guardaOmision(String omision, String idAnalisis, String justificacion, String pago, String fechaSurteEfecto)  throws Exception {
		Integer status=0;
		try{
			bitacoraServiceEntity.guardarBitacoraOmisiones(omision, idAnalisis, justificacion, pago, fechaSurteEfecto) ;
			Log.debug("Si se guardo en bd");
			status =1;	
		}
		catch(Exception e){
			log.error(e.getMessage());
			throw e;
		}
			
		return status;
	}
	
	@Override
	public void actualizaEstatusRegularizar(String cveIdAnalisis) throws AnalisisNoEncontradoException{
		AnalisisClasificacionEmpresas analisisClasificacionEmpresas = new AnalisisClasificacionEmpresas();
		analisisClasificacionEmpresas.setCveIdAnalisis(Long.valueOf(Long.parseLong(cveIdAnalisis)));
		analisisClasificacionEmpresas.setCveIdEstatus(Long.valueOf(EstatusAnalisisEnum.POR_REGULARIZAR.getClave()));
		analisisClasificacionEmpresas.setEstatus(EstatusAnalisisEnum.POR_REGULARIZAR);	
		analisisEntity.actualizaEstado(analisisClasificacionEmpresas);
	}
	
}