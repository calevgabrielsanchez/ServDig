/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: AnalisisServiceBusiness.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.analisis
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.analisis;

import static mx.gob.imss.ctirss.delta.model.clasificacion.TipoCausaAnalisisEnum.RECTIFICACION_DE_LA_CLASIFICACION_INICIAL;
import static mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum.RECTIFICACION_POR_ERROR_EN_EL_ANALISIS;
import static mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum.CERRADO;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import org.jfree.util.Log;

import mx.gob.imss.ctirss.delta.exception.clasificacion.AnalisisNoEncontradoException;
import mx.gob.imss.ctirss.delta.exception.clasificacion.ClasificacionException;
import mx.gob.imss.ctirss.delta.exception.clasificacion.DatosClemException;
import mx.gob.imss.ctirss.delta.exception.clasificacion.EstatusMovimientoException;
import mx.gob.imss.ctirss.delta.exception.clasificacion.PatronNoEncontradoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ClasificacionDTO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.analisis.AnalisisServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.bitacora.BitacoraServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clasificacion.ClasifPropDictServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.AnalisisServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.TipoCausaAnalisisServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clasificacion.ClasificacionPropuestaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clasificacion.ClasificacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clasificacion.FraccionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clem.DatosClemServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.tramite.TramiteServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.bitacora.BitacoraServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.sujetoobligado.SujetoObligadoServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.util.Constantes;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.util.Utiles;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.clasificacion.actividad.economica.ActividadEcServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.clasificacion.AdjuntosClasificacion;
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.model.clasificacion.Comentario;
import mx.gob.imss.ctirss.delta.model.clasificacion.DatosClem;
import mx.gob.imss.ctirss.delta.model.clasificacion.ElementoBitacora;
import mx.gob.imss.ctirss.delta.model.clasificacion.ElementoBitacoraOmision;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisEnum;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisModel;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;

@Stateless(name = "analisisMovimientoBusiness", mappedName = "analisisMovimientoBusiness")
public class AnalisisServiceBusiness extends AbstractServiceBusiness implements
		AnalisisServiceBusinessRemote {

	@EJB
	private AnalisisServiceEntityLocal analisisEntity;
	
	@EJB
	private BitacoraServiceEntityLocal bitacoraEntity;
		
	@EJB
	private ClasificacionPropuestaServiceBusinessRemote clasificacionPropuestaBusiness;
	
	@EJB
	private SolicitudServiceBusinessRemote solicitudPatronalBusiness;
	
	@EJB 
	private BitacoraServiceUtilityLocal bitacoraUtility;
		
	@EJB
	ClasificacionServiceBusinessRemote clasificacionServiceBusiness;
	
	@EJB
	FraccionServiceBusinessRemote fraccionServiceBusiness;
	
	@EJB
	DatosClemServiceBusinessRemote datosClemBusiness;
	
	@EJB
	mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud.SolicitudServiceBusinessRemote solicitudServiceBusiness;
	
//	@EJB
//	private GCESujetoObligadoServiceBusinessRemote gceSujetoObligadoServiceBusiness;
	
	@EJB
	private TipoCausaAnalisisServiceBusinessRemote tipoCausaServiceBusiness;
	
	@EJB
	private TramiteServiceBusinessRemote tramiteBusiness;
	
	private EstatusAnalisisModel estatusAnalisisModel;
	
	@EJB
	private ActividadEcServiceRemote clasificacionActividadEconomicaBusiness;
	
	@EJB
	private SolicitudServiceBusinessRemote solicitudPatronalService;
	
	@EJB
	private SujetoObligadoServiceUtilityLocal sujetoObligadoUtility;

	@EJB
	private ClasifPropDictServiceEntityLocal clasifPropDictEntity;
	
	

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.AnalisisServiceBusinessRemote#ratificarPendienteDictamen(mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ClasificacionDTO)
	 */
	@Override
	public AnalisisClasificacionEmpresas ratificarPendienteDictamen(ClasificacionDTO dto)
			throws AnalisisNoEncontradoException, ClasificacionException, EstatusMovimientoException {
		
		AnalisisClasificacionEmpresas modelo = Utiles.armaModeloAnalisis(dto);
		modelo.setCveIdEstatus(Long.valueOf(EstatusAnalisisEnum.RATIFICADO_PENDIENTE_AUTORIZACION.getClave()));
		modelo.setEstatus(EstatusAnalisisEnum.RATIFICADO_PENDIENTE_AUTORIZACION);
		EstatusAnalisisModel estatusAnalisisModel = new EstatusAnalisisModel();
		
		try {
//			Borra la clasificacion propuesta ya que los registros ratificados
//			no denben de tener esta información.
			clasificacionPropuestaBusiness.borrarClasificacionPropuesta(modelo.getCveIdAnalisis().longValue());
			
//			Valida si el analisis ha sido modificado por otro usuario.
			this.validaEstatusMovimiento(new Long(dto.getCveIdAnalisis()).longValue(), 
					"" +  EstatusAnalisisEnum.PENDIENTE_DE_ANALISIS.getClave() +
					", " + EstatusAnalisisEnum.RATIFICADO_RECHAZADO.getClave() +
					", " + EstatusAnalisisEnum.RECTIFICADO_RECHAZADO.getClave() +
					", " + EstatusAnalisisEnum.RATIFICADO_AUTORIZADO_Y_RECHAZADO.getClave() +
					", " + EstatusAnalisisEnum.RECTIFICADO_AUTORIZADO_Y_RECHAZADO.getClave());
			
			estatusAnalisisModel = bitacoraUtility.armaBitacora(modelo, dto.getComentarios());
			modelo = analisisEntity.actualizaEstado(modelo);
			bitacoraEntity.guardaBitacora(estatusAnalisisModel);
			
//			Borra clasicacion propuesta ya que los registros ratificados
//			no deben tener esta informacion
			clasificacionPropuestaBusiness.borrarClasificacionPropuesta(modelo.getCveIdAnalisis().longValue());
			
		} catch (ClasificacionException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ClasificacionException(e.getMessage(), null);
		}
		return modelo;
	}

	@Override
	public AnalisisClasificacionEmpresas ratificarPendiente(ClasificacionDTO dto)
			throws AnalisisNoEncontradoException, ClasificacionException,
			EstatusMovimientoException {
		
        AnalisisClasificacionEmpresas modelo = Utiles.armaModeloAnalisis(dto);
		modelo.setCveIdEstatus(Long.valueOf(EstatusAnalisisEnum.RATIFICADO_PENDIENTE_AUTORIZACION.getClave()));
		modelo.setEstatus(EstatusAnalisisEnum.RATIFICADO_PENDIENTE_AUTORIZACION);
		EstatusAnalisisModel estatusAnalisisModel = new EstatusAnalisisModel();
		
		try {
			
//se omite validacion por cambio en la vista donde no se mostraran rps en estatus de baja			
			//Valida si el registro patronal no se encuentra dado de baja; en caso contrario,
			//no permite continuar con la operación
//			final Solicitud solicitud = new Solicitud();
//			solicitud.setSolicitudId(Long.valueOf(dto.getCveIdSolicitud()));
//			gceSujetoObligadoServiceBusiness.validarEstadoRegistroPatronal(solicitud);

		 	// borra clasificacion propuesta ya que los registros ratificados 
		 	// no deben tener esta informacion
	 		clasificacionPropuestaBusiness.borrarClasificacionPropuesta(modelo.getCveIdAnalisis().longValue());

			// Valida si el análisis ha sido modificado por otro usuario.
			this.validaEstatusMovimiento(
				new Long(dto.getCveIdAnalisis()).longValue(), 
				"" + EstatusAnalisisEnum.PENDIENTE_DE_ANALISIS.getClave() + 
				", " + EstatusAnalisisEnum.RATIFICADO_RECHAZADO.getClave() + 
				", " + EstatusAnalisisEnum.RECTIFICADO_RECHAZADO.getClave() + 
				", " + EstatusAnalisisEnum.RATIFICADO_AUTORIZADO_Y_RECHAZADO.getClave() +
				", " + EstatusAnalisisEnum.RECTIFICADO_AUTORIZADO_Y_RECHAZADO.getClave());

			estatusAnalisisModel = bitacoraUtility.armaBitacora(modelo, dto.getComentarios());
			modelo = analisisEntity.actualizaEstado(modelo); 
			bitacoraEntity.guardaBitacora(estatusAnalisisModel);

		 	// borra clasificacion propuesta ya que los registros ratificados 
		 	// no deben tener esta informacion
	 		clasificacionPropuestaBusiness.borrarClasificacionPropuesta(
	 			modelo.getCveIdAnalisis().longValue());
	 		
		} catch (ClasificacionException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ClasificacionException(e.getMessage(), null);
		}	 	
 		
 		return modelo; 	
	}
	
	@Override
	public AnalisisClasificacionEmpresas autorizarRatificacion(
			ClasificacionDTO dto) throws AnalisisNoEncontradoException,
			EstatusMovimientoException, PersistenceException, ClasificacionException {

		AnalisisClasificacionEmpresas modelo = Utiles.armaModeloAnalisis(dto);
		modelo.setCveIdEstatus(Long.valueOf(EstatusAnalisisEnum.RATIFICADO_AUTORIZADO.getClave()));
		modelo.setEstatus(EstatusAnalisisEnum.RATIFICADO_AUTORIZADO);

		EstatusAnalisisModel estatusAnalisisModel = new EstatusAnalisisModel();
		try {

//se omite validacion por cambio en la vista donde no se mostraran rps en estatus de baja			
			//Valida si el registro patronal no se encuentra dado de baja; en caso contrario,
			//no permite continuar con la operación
//			final Solicitud solicitud = new Solicitud();
//			solicitud.setSolicitudId(Long.valueOf(dto.getCveIdSolicitud()));
//			gceSujetoObligadoServiceBusiness.validarEstadoRegistroPatronal(solicitud);

			// Valida si el análisis ha sido modificado por otro usuario.
			this.validaEstatusMovimiento(new Long(dto.getCveIdAnalisis()).longValue(), 
				"" + EstatusAnalisisEnum.RATIFICADO_PENDIENTE_AUTORIZACION.getClave());

			estatusAnalisisModel = bitacoraUtility.armaBitacora(modelo, "");
			modelo = analisisEntity.actualizaEstado(modelo);
			bitacoraEntity.guardaBitacora(estatusAnalisisModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
				
	 	return modelo;
	}
	
	
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.AnalisisServiceBusinessRemote#autorizarRatificacionDictamen(mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ClasificacionDTO)
	 */
	@Override
	public AnalisisClasificacionEmpresas autorizarRatificacionDictamen(ClasificacionDTO dto)
			throws AnalisisNoEncontradoException, EstatusMovimientoException, PersistenceException,
			ClasificacionException {
		AnalisisClasificacionEmpresas modelo = Utiles.armaModeloAnalisis(dto);
		modelo.setCveIdEstatus(Long.valueOf(EstatusAnalisisEnum.RATIFICADO_AUTORIZADO.getClave()));
		modelo.setEstatus(EstatusAnalisisEnum.RATIFICADO_AUTORIZADO);
		
		EstatusAnalisisModel estatusAnalisisModel = new EstatusAnalisisModel();
		
		try {
			
//			Valida so el analisis ja sido modificado por otro usuario
			this.validaEstatusMovimiento(new Long(dto.getCveIdAnalisis()).longValue(),
					"" + EstatusAnalisisEnum.RATIFICADO_PENDIENTE_AUTORIZACION.getClave());
			estatusAnalisisModel = bitacoraUtility.armaBitacora(modelo, "");
			modelo = analisisEntity.actualizaEstado(modelo);
			bitacoraEntity.guardaBitacora(estatusAnalisisModel);
		} catch (Exception e) {
			
		}
		return modelo;
	}
	
	

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.AnalisisServiceBusinessRemote#rechazarRatificacionDictamenPendAut(mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ClasificacionDTO)
	 */
	@Override
	public AnalisisClasificacionEmpresas rechazarRatificacionDictamenPendAut(ClasificacionDTO dto)
			throws AnalisisNoEncontradoException, NumberFormatException, EstatusMovimientoException,
			ClasificacionException {
		this.validaEstatusMovimiento(new Long (dto.getCveIdAnalisis()).longValue(), "" + EstatusAnalisisEnum.RATIFICADO_PENDIENTE_AUTORIZACION.getClave());
		this.log.debug("Guardando el rechazo de ratificacion del analisis ["+dto.getCveIdAnalisis()+"]");
		AnalisisClasificacionEmpresas analisis = getAnalisisModel(dto.getCveIdAnalisis(), dto.getUsuario().getCveIdUsuario().toString(), EstatusAnalisisEnum.RATIFICADO_RECHAZADO);
		analisis.setCveIdDelegacion(new Long(dto.getCveIdDelegacion()));
		analisis.setCveIdSubdelegacion(new Long(dto.getCveIdSubdelegacion()));
		
		if(!dto.getCveIdFraccionAct().isEmpty()) {
			analisis.setClasificacionActual(Utiles.armaClasificacion(new Long(dto.getCveIdFraccionAct()).longValue(), new BigDecimal(dto.getPrimaSRTAct())));
		}
		
		if(!dto.getCveIdFraccionAnt().isEmpty()) {
			analisis.setClasificacionAnterior(Utiles.armaClasificacion(new Long(dto.getCveIdFraccionAnt()).longValue(), new BigDecimal(dto.getPrimaSRTAnt())));
		}
		
		log.debug("Actualizando el estatus");
		analisis = this.rechazarAnalisisSolicitud(analisis, dto.getComentarios());
		return analisis;
	}

	@Override
	public AnalisisClasificacionEmpresas rechazarRatificacionPendAut(
			ClasificacionDTO dto) throws AnalisisNoEncontradoException,
			NumberFormatException, EstatusMovimientoException,
			ClasificacionException {

//se omite validacion por cambio en la vista donde no se mostraran rps en estatus de baja
		//Valida si el registro patronal no se encuentra dado de baja; en caso contrario,
		//no permite continuar con la operación
//		final Solicitud solicitud = new Solicitud();
//		solicitud.setSolicitudId(Long.valueOf(dto.getCveIdSolicitud()));
//		gceSujetoObligadoServiceBusiness.validarEstadoRegistroPatronal(solicitud);

    	// Valida si el análisis ha sido modificado por otro usuario.
		this.validaEstatusMovimiento(
			new Long(dto.getCveIdAnalisis()).longValue(), 
			"" + EstatusAnalisisEnum.RATIFICADO_PENDIENTE_AUTORIZACION.getClave());

		//2.Actualizando el estatus
		this.log.debug("Guardando el rechazo de ratificacion [" + dto.getCveIdAnalisis() +"]" );
    	AnalisisClasificacionEmpresas analisis = 
    		getAnalisisModel(dto.getCveIdAnalisis(), dto.getUsuario().getCveIdUsuario().toString(), EstatusAnalisisEnum.RATIFICADO_RECHAZADO);
    	analisis.setCveIdDelegacion(new Long(dto.getCveIdDelegacion()));
    	analisis.setCveIdSubdelegacion(new Long(dto.getCveIdSubdelegacion()));
    	
    	if(!dto.getCveIdFraccionAct().isEmpty()){
	 		analisis.setClasificacionActual(
	 			Utiles.armaClasificacion(new Long(dto.getCveIdFraccionAct()).longValue(), 
	 				new BigDecimal(dto.getPrimaSRTAct())));
	 	}
	 	
	 	if(!dto.getCveIdFraccionAnt().isEmpty()){
	 		analisis.setClasificacionAnterior(
	 			Utiles.armaClasificacion(new Long(dto.getCveIdFraccionAnt()).longValue(), 
	 				new BigDecimal(dto.getPrimaSRTAnt())));
	 	}
    	
		log.debug("Actualizando el estatus");
		analisis = this.rechazarAnalisisSolicitud(analisis, dto.getComentarios());	
		
	 	return analisis;
	}

	@Override
	public AnalisisClasificacionEmpresas autorizarRectificarSolicitud(
			AnalisisClasificacionEmpresas model)
			throws AnalisisNoEncontradoException, EstatusMovimientoException {

		EstatusAnalisisModel estatusAnalisisModel = new EstatusAnalisisModel();
		AnalisisClasificacionEmpresas retVal = null;
		model.setCveIdEstatus(Long.valueOf(EstatusAnalisisEnum.RECTIFICADO_AUTORIZADO.getClave()));
		model.setEstatus(EstatusAnalisisEnum.RECTIFICADO_AUTORIZADO);

		try {
			retVal= analisisEntity.actualizaEstado(model);
			estatusAnalisisModel = bitacoraUtility.armaBitacora(model, null);
			bitacoraEntity.guardaBitacora(estatusAnalisisModel);
		} catch (AnalisisNoEncontradoException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		
		return retVal;
	}

	@Override
	public AnalisisClasificacionEmpresas rechazarRectificacionPendAut(
			ClasificacionDTO dto) throws NumberFormatException,
			ClasificacionException, EstatusMovimientoException,
			AnalisisNoEncontradoException {
	
		this.log.debug("Guardando el rechazo de rectificacion [" + dto.getCveIdAnalisis() +"]" );
		  
//se omite validacion por cambio en la vista donde no se mostraran rps en estatus de baja		
		//Valida si el registro patronal no se encuentra dado de baja; en caso contrario,
		//no permite continuar con la operación
//		final Solicitud solicitud = new Solicitud();
//		solicitud.setSolicitudId(Long.valueOf(dto.getCveIdSolicitud()));
//		gceSujetoObligadoServiceBusiness.validarEstadoRegistroPatronal(solicitud);

    	//2.Actualizando el estatus
		AnalisisClasificacionEmpresas analisis = 
			getAnalisisModel(dto.getCveIdAnalisis(), dto.getUsuario().getCveIdUsuario().toString(), EstatusAnalisisEnum.RECTIFICADO_RECHAZADO);
		analisis.setCveIdDelegacion(new Long(dto.getCveIdDelegacion()));
    	analisis.setCveIdSubdelegacion(new Long(dto.getCveIdSubdelegacion()));
    	
		try{
			regresaClasificacionOriginal(dto.getCveIdAnalisis(), dto.getRegPatronal(), dto.getTipoPersona());
		}catch(Exception e){
			e.printStackTrace();
		}
		clasificacionPropuestaBusiness.borrarClasificacionPropuesta(new Long(dto.getCveIdAnalisis()).longValue());

		if (estatusAnalisisModel != null) {
			if (estatusAnalisisModel.getFraccionActual() != null) {
				analisis.setClasificacionActual(Utiles.armaClasificacion(
						estatusAnalisisModel.getFraccionActual().getId(),
						new BigDecimal(dto.getPrimaSRTAct())));
			}
			
			if (estatusAnalisisModel.getFraccionAnterior() != null) {
				analisis.setClasificacionAnterior(Utiles.armaClasificacion(
						estatusAnalisisModel.getFraccionAnterior().getId(),
						new BigDecimal(dto.getPrimaSRTAnt())));
			}
		}else{
	    	if(!dto.getCveIdFraccionAct().isEmpty()){
	    		analisis.setClasificacionActual(
		 			Utiles.armaClasificacion(new Long(dto.getCveIdFraccionAct()).longValue(), 
		 				new BigDecimal(dto.getPrimaSRTAct())));
		 	}
		 	
		 	if(!dto.getCveIdFraccionAnt().isEmpty()){
		 		analisis.setClasificacionAnterior(
		 			Utiles.armaClasificacion(new Long(dto.getCveIdFraccionAnt()).longValue(), 
		 				new BigDecimal(dto.getPrimaSRTAnt())));
		 	}
		}
    	
		// Valida si el análisis ha sido modificado por otro usuario.
		this.validaEstatusMovimiento(
			new Long(dto.getCveIdAnalisis()).longValue(), 
			"" + EstatusAnalisisEnum.RECTIFICADO_PENDIENTE_AUTORIZACION.getClave());

		analisis = this.rechazarAnalisisSolicitud(analisis, dto.getComentarios());
		
		return analisis;
	}
	
	@Override
	public AnalisisClasificacionEmpresas rechazarRectificacionDictamenPendAut(
			ClasificacionDTO dto) throws NumberFormatException,
			ClasificacionException, EstatusMovimientoException,
			AnalisisNoEncontradoException {
	
		this.log.debug("Guardando el rechazo de rectificacion [" + dto.getCveIdAnalisis() +"]" );
		  
//se omite validacion por cambio en la vista donde no se mostraran rps en estatus de baja		
		//Valida si el registro patronal no se encuentra dado de baja; en caso contrario,
		//no permite continuar con la operación
//		final Solicitud solicitud = new Solicitud();
//		solicitud.setSolicitudId(Long.valueOf(dto.getCveIdSolicitud()));
//		gceSujetoObligadoServiceBusiness.validarEstadoRegistroPatronal(solicitud);

    	//2.Actualizando el estatus
		AnalisisClasificacionEmpresas analisis = 
			getAnalisisModel(dto.getCveIdAnalisis(), dto.getUsuario().getCveIdUsuario().toString(), EstatusAnalisisEnum.RECTIFICADO_RECHAZADO);
		analisis.setCveIdDelegacion(new Long(dto.getCveIdDelegacion()));
    	analisis.setCveIdSubdelegacion(new Long(dto.getCveIdSubdelegacion()));
    	
		try{
			regresaClasificacionOriginal(dto.getCveIdAnalisis(), dto.getRegPatronal(), dto.getTipoPersona());
		}catch(Exception e){
			e.printStackTrace();
		}
		clasificacionPropuestaBusiness.borrarClasificacionPropuesta(new Long(dto.getCveIdAnalisis()).longValue());

		if (estatusAnalisisModel != null) {
			if (estatusAnalisisModel.getFraccionActual() != null) {
				analisis.setClasificacionActual(Utiles.armaClasificacion(
						estatusAnalisisModel.getFraccionActual().getId(),
						estatusAnalisisModel.getFraccionActual().getPrimaSRT()));
			}
			
			if (estatusAnalisisModel.getFraccionAnterior() != null) {
				analisis.setClasificacionAnterior(Utiles.armaClasificacion(
						estatusAnalisisModel.getFraccionAnterior().getId(),
						estatusAnalisisModel.getFraccionAnterior().getPrimaSRT()));
			}
		}else{
	    	if(!dto.getCveIdFraccionAct().isEmpty()){
	    		analisis.setClasificacionActual(
		 			Utiles.armaClasificacion(new Long(dto.getCveIdFraccionAct()).longValue(), 
		 				new BigDecimal(dto.getPrimaSRTAct())));
		 	}
		 	
		 	if(!dto.getCveIdFraccionAnt().isEmpty()){
		 		analisis.setClasificacionAnterior(
		 			Utiles.armaClasificacion(new Long(dto.getCveIdFraccionAnt()).longValue(), 
		 				new BigDecimal(dto.getPrimaSRTAnt())));
		 	}
		}
    	
		// Valida si el análisis ha sido modificado por otro usuario.
		this.validaEstatusMovimiento(
			new Long(dto.getCveIdAnalisis()).longValue(), 
			"" + EstatusAnalisisEnum.RECTIFICADO_PENDIENTE_AUTORIZACION.getClave());

		analisis = this.rechazarAnalisisSolicitud(analisis, dto.getComentarios());
		
		return analisis;
	}

	@Override
	public AnalisisClasificacionEmpresas rechazarAutorizacion(
			ClasificacionDTO dto, Long cveIdPatronDictamen, boolean esDictamen) throws DatosClemException,
			AnalisisNoEncontradoException, NumberFormatException,
			ClasificacionException, EstatusMovimientoException {
		
		SujetoObligado sujetoObligado = new SujetoObligado();
		boolean bRatificacion = (Integer.parseInt(dto.getIdEstatus()) == EstatusAnalisisEnum.RATIFICADO_AUTORIZADO.getClave());
		
//se omite validacion por cambio en la vista donde no se mostraran rps en estatus de baja		
		//Valida si el registro patronal no se encuentra dado de baja; en caso contrario,
		//no permite continuar con la operación
//		final Solicitud solicitud = new Solicitud();
//		solicitud.setSolicitudId(Long.valueOf(dto.getCveIdSolicitud()));
//		gceSujetoObligadoServiceBusiness.validarEstadoRegistroPatronal(solicitud);
		
		EstatusAnalisisEnum estatusAnalisis = null;
		if(bRatificacion){
			this.log.debug("Guardando el rechazo de ratificacion [" + dto.getCveIdAnalisis() +"]" );
			estatusAnalisis = EstatusAnalisisEnum.RATIFICADO_AUTORIZADO;
		}else{
			this.log.debug("Guardando el rechazo de rectificacion [" + dto.getCveIdAnalisis() +"]" );
			estatusAnalisis = EstatusAnalisisEnum.RECTIFICADO_AUTORIZADO;
		}		
		
		AnalisisClasificacionEmpresas analisis = 
			getAnalisisModel(dto.getCveIdAnalisis(), dto.getUsuario().getCveIdUsuario().toString(), estatusAnalisis);
    	analisis.setCveIdDelegacion(new Long(dto.getCveIdDelegacion()));
    	analisis.setCveIdSubdelegacion(new Long(dto.getCveIdSubdelegacion()));
    	
    	Comentario comentario = new Comentario();
    	comentario.setDescripcion(dto.getComentarios());
    	List<Comentario> listaComentarios = new ArrayList<Comentario>();
    	listaComentarios.add(comentario);
    	analisis.setComentarios(listaComentarios);
    			
		if(estatusAnalisis == EstatusAnalisisEnum.RECTIFICADO_AUTORIZADO){
			try{
				sujetoObligado.setNumeroRegistroPatronal(dto.getRegPatronal());
				sujetoObligado.setTipoPersonaFiscal(dto.getTipoPersona().equals("2") ? TipoPersonaFiscal.MORAL : TipoPersonaFiscal.FISICA);
				sujetoObligado = solicitudServiceBusiness.obtenerDetalleSolicitud(sujetoObligado);
				
				if(esDictamen) {
					sujetoObligado = solicitudServiceBusiness.obtenerDetalleSolicitudDictamen(sujetoObligado);
					
				} else {
					sujetoObligado = solicitudServiceBusiness.obtenerDetalleSolicitud(sujetoObligado);
				}
				
				
				Clasificacion clasificacion=sujetoObligado.getClasificacion();
				clasificacion.setFraccion(fraccionServiceBusiness.obtenerFotoClasificacionActual(Long.parseLong(dto.getCveIdAnalisis())));
				clasificacion.setSujetoObligado(sujetoObligado);
				
				AnalisisClasificacionEmpresas ace=new AnalisisClasificacionEmpresas();
				ace.setCveIdAnalisis(Long.parseLong(dto.getCveIdAnalisis()));
				
				/**
				 * Esta funcionalidad pertenecía a la Autorización de Ratificación.
				 * Por peticón del Usuario se pidió hacerla en la Modificación de Autorización
				 */
				String strTipoCausa="";
				if(dto.getTTramite().trim().equals("0")){
					strTipoCausa=String.valueOf(RECTIFICACION_DE_LA_CLASIFICACION_INICIAL.getClave());
				}else{
					strTipoCausa=String.valueOf(tipoCausaServiceBusiness.consultaTipoCausa(
							tramiteBusiness.consultaTramitePorSolicitud(
									Long.parseLong(dto.getCveIdSolicitud()))
									.get(0).getTipoTramite().getIdTipoTramite().longValue(), 1).getCveIdTipoCausa());
				}
				/**
				 * Si el Trámite es Inscripción Inicial(0) entonces se setea valor fijo de Causa RECTIFICACION_DE_LA_CLASIFICACION_INICIAL
				 */
				log.debug("La causa será enviada a SINDO" +strTipoCausa);
				analisis.setTipoCausaAnalisis(strTipoCausa);
				tipoCausaServiceBusiness.registraCausa(analisis);
				analisisEntity.actualizaIndCausa(analisis, true);
				
				if(cveIdPatronDictamen == null){
					clasificacionActividadEconomicaBusiness.actualizarClasificacion(clasificacion,Integer.parseInt(strTipoCausa), 6);
				}
				
				clasificacion.setSujetoObligado(null);
				sujetoObligado.setClasificacion(clasificacion);
				//Genera Nuevo Trámite a una Solicitud existente
				
				TramiteSujetoObligado tramiteSujOblig=solicitudPatronalService.construirTramite(
						RECTIFICACION_POR_ERROR_EN_EL_ANALISIS, CERRADO, sujetoObligado);
				tramiteSujOblig.setFechaPresentacion(new Date());
				tramiteSujOblig.setFechaEfecto(new Date());
				
				log.debug("Genera Trámite para la Solicitud actual");
				solicitudPatronalService.agregarTramiteASolicitud(Long.parseLong(dto.getCveIdSolicitud()), tramiteSujOblig);
				
				//Envía a SINDO por cambio de Tipo Causa;
				//String folio = buildNumeroFolio(dto.getCveNumSubdelegacion());
				if(cveIdPatronDictamen == null){
					String folio = this.buildNumeroFolio(sujetoObligado.getSubdelegacion().getClave());
					log.debug("Registra en SINDO");
					clasificacionActividadEconomicaBusiness.ejecutarProcesoSincronizacionSINDO(folio,
							sujetoObligado,
	                        tramiteSujOblig.getFechaEfecto(), Long.parseLong(strTipoCausa), Integer.valueOf(1),
	                        Integer.valueOf(6), Integer.valueOf(6));
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			DatosClem datosClem = new DatosClem();
			datosClem.setCveAnalisis(new BigDecimal(dto.getCveIdAnalisis()));
			datosClem = datosClemBusiness.consultaClem(datosClem);
			if(datosClem != null){
				datosClemBusiness.elimina(new Long(datosClem.getCveIdClem().longValue()));
			}			
		}
		
		clasificacionPropuestaBusiness.borrarClasificacionPropuesta(new Long(dto.getCveIdAnalisis()).longValue());
		
		//borramos el registro de Dictamen
		if(estatusAnalisis == EstatusAnalisisEnum.RECTIFICADO_AUTORIZADO && cveIdPatronDictamen != null){
			log.debug("Se elimina la clasificacion propuesta del Dictamen");
			clasifPropDictEntity.elimina(sujetoObligado.getCveIdSujetoObligado());
		}

    	if (estatusAnalisisModel != null) {
			if (estatusAnalisisModel.getFraccionActual() != null) {
				analisis.setClasificacionActual(Utiles.armaClasificacion(
						estatusAnalisisModel.getFraccionActual().getId(),
						new BigDecimal( dto.getPrimaSRTAct()) ));
			}
			
			if (estatusAnalisisModel.getFraccionAnterior() != null) {
				analisis.setClasificacionAnterior(Utiles.armaClasificacion(
						estatusAnalisisModel.getFraccionAnterior().getId(),
						new BigDecimal( dto.getPrimaSRTAnt())));
			}
		}else{
	    	if(!dto.getCveIdFraccionAct().isEmpty()){
		 		analisis.setClasificacionActual(
		 			Utiles.armaClasificacion(new Long(dto.getCveIdFraccionAct()).longValue(), 
		 					new BigDecimal( dto.getPrimaSRTAct())));
		 	}
		 	
		 	if(!dto.getCveIdFraccionAnt().isEmpty()){
		 		analisis.setClasificacionAnterior(
		 			Utiles.armaClasificacion(new Long(dto.getCveIdFraccionAnt()).longValue(), 
		 					new BigDecimal( dto.getPrimaSRTAnt())));
		 	}
		}
    	
		// Valida si el análisis ha sido modificado por otro usuario.
		this.validaEstatusMovimiento(
			new Long(dto.getCveIdAnalisis()).longValue(), 
			"" + EstatusAnalisisEnum.RATIFICADO_AUTORIZADO.getClave() + 
			", " + EstatusAnalisisEnum.RECTIFICADO_AUTORIZADO.getClave());

		analisis = this.rechazarAutorizacion(analisis);
		
		return analisis;
	}	
	
	@Override
	public void validaEstatusMovimiento(long cveIdAnalisis, String cveIdEstatus) 
			throws EstatusMovimientoException {
		if( !analisisEntity.validaEstatusMovimiento(cveIdAnalisis, cveIdEstatus) ){
			log.error("Error. Inconsistencia de estatus. No se puede realizar el movimiento.");
			throw new EstatusMovimientoException();
		}
	}
	
	@Override
	public SujetoObligado consultarSujetoObligadoPorAnalisis(
			AnalisisClasificacionEmpresas model, int tipoPersona) throws PatronNoEncontradoException {
		SujetoObligado response = null;
		
		try {
			response = analisisEntity.consultarSujetoObligado(model, tipoPersona);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error : consultarSujetoObligadoPorAnalisis():"+e.getMessage());
			throw new PatronNoEncontradoException();
		}
		return response;
	}

	@Override
	public AnalisisClasificacionEmpresas consultarAnalisisPorId(AnalisisClasificacionEmpresas model) 
			throws AnalisisNoEncontradoException {
		AnalisisClasificacionEmpresas response = null;
		try {
			response = analisisEntity.consultaPorIdAnalisis(model.getCveIdAnalisis().longValue());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error : consultarAnalisisPorId():"+e.getMessage());
			throw new AnalisisNoEncontradoException();
		}
		return response;
	}

	
		
	@Override
	public AnalisisClasificacionEmpresas obtenerDetalleAnalisis(BigDecimal idSolicitud) 
			throws AnalisisNoEncontradoException {
		
		AnalisisClasificacionEmpresas response = new AnalisisClasificacionEmpresas();
		AnalisisClasificacionEmpresas response2 = new AnalisisClasificacionEmpresas();
		Solicitud solicitud = new Solicitud();
		
		try{			
			response = analisisEntity.consultaDetalleAnalisis(idSolicitud);
			
			if(response != null){
				response2 = clasificacionPropuestaBusiness.obtenerClaseDeClasificacionPropuesta(response.getCveIdAnalisis().toString());
				
				log.info("response2 es nulo");
				if(response2 != null){
					response.setClasificacionPropuesta(response2.getClasificacionPropuesta());
				}
				
				//obtenemos el historial del analisis para la seccion de comentarios del detalle de la solicitud
				List<ElementoBitacora> elementoBitacora = bitacoraEntity.buscaComentariosDetalle(response.getCveIdAnalisis(), "");
				response.setComentariosDetalle(elementoBitacora);

				List<ElementoBitacoraOmision> elementoOmision = bitacoraEntity.buscaComentariosOmisiones(response.getCveIdAnalisis());
				response.setHistOmisiones(elementoOmision);

				List<ElementoBitacoraOmision> omisionActual = bitacoraEntity.buscaOmisionActual(response.getCveIdAnalisis());
				response.setOmisionActual(omisionActual);
				
			}else{
							
				return null;
				
			}
			
			solicitud = solicitudPatronalBusiness.consultarSolicitudPorId(idSolicitud.longValue());
			
			log.info("validando si la solicitud es nula");
			if(solicitud != null){				
				TramiteSujetoObligado tramite = sujetoObligadoUtility.obtenerTramiteSujetoObligado(solicitud.getTramites(),
								solicitud.getTipoSolicitud().getIdTipoSolicitud());
				
				response.setClasificacionAnterior(tramite.getSujetoObligado().getClasificacion());
				response.setFechaPresentacion(tramite.getFechaPresentacion());
				response.setFechaEfecto(tramite.getFechaEfecto());
				response.setTramite(tramite);
				
				//obtenemos la secuencia de notaria para los applets
				mx.gob.imss.ctirss.delta.model.gestion.patronal.Solicitud sol = new mx.gob.imss.ctirss.delta.model.gestion.patronal.Solicitud();
				sol.setSecuenciaDeNotaria(solicitud.getSecuenciaDeNotaria());
				response.setSolicitud(sol);
			}
						
			// codigo para cambiar la descripcion del estatus a rp's anteriores al cambio del flujo de la clasificacion
			// esto para que el historial no se vea afectado
			ElementoBitacora elemBit = null;
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

			for (Iterator<ElementoBitacora> iterator = response.getComentariosDetalle().iterator(); iterator.hasNext();) {
				elemBit = iterator.next();
				if(response.getFechaPresentacion().getTime() < sdf.parse(Constantes.FECHA_CAMBIO_FLUJO).getTime() &&
						elemBit.getIdAccionrealizada() == EstatusAnalisisEnum.PENDIENTE_DE_ANALISIS.getClave()){
					elemBit.setAcccionRealizada("POR ASIGNAR");
				}
			}
		}catch (AnalisisNoEncontradoException exc) {
			throw exc;			
		}catch (Exception exc) {
			log.error("Error : obtenerDetalleAnalisis():"+exc.getMessage());
			exc.printStackTrace();
			throw new AnalisisNoEncontradoException( "Se origin\u00F3 un problema al obtener el detalle del analisis: "+idSolicitud, 202);
		}
		return response;
	}
	
	@Override
	public List<EstatusAnalisisModel> consultaEstatusAnalisisPorGrupoAnalisis(Long cveIdGrupo) throws Exception{
		return analisisEntity.consultaEstatusAnalisisPorGrupoAnalisis(cveIdGrupo);
	}

	private AnalisisClasificacionEmpresas rechazarAnalisisSolicitud(
			AnalisisClasificacionEmpresas model, String comentarios) 
			throws AnalisisNoEncontradoException, ClasificacionException {
		EstatusAnalisisModel estatusAnalisisModel = new EstatusAnalisisModel();

		try {
			estatusAnalisisModel = bitacoraUtility.armaBitacora(model, comentarios);
			analisisEntity.actualizaEstado(model);
			bitacoraEntity.guardaBitacora(estatusAnalisisModel);
		} catch (AnalisisNoEncontradoException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		} catch (ClasificacionException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new ClasificacionException(e.getMessage(), null);
		}

		return model;
	}
	
	private AnalisisClasificacionEmpresas rechazarAutorizacion(AnalisisClasificacionEmpresas model) 
			throws AnalisisNoEncontradoException, ClasificacionException {
		EstatusAnalisisModel estatusAnalisisModel = new EstatusAnalisisModel();
		int cveEstatus= model.getEstatus().getClave();

		try {
			this.consultarAnalisisPorId(model);
			if(cveEstatus == EstatusAnalisisEnum.RECTIFICADO_AUTORIZADO.getClave() ){
				model.setCveIdEstatus(Long.valueOf(EstatusAnalisisEnum.RECTIFICADO_AUTORIZADO_Y_RECHAZADO.getClave()));
				model.setEstatus(EstatusAnalisisEnum.RECTIFICADO_AUTORIZADO_Y_RECHAZADO);
				model.setIndModAut(calculaIndModificacion(model));
			}else if(cveEstatus == EstatusAnalisisEnum.RATIFICADO_AUTORIZADO.getClave()){
				model.setCveIdEstatus(Long.valueOf(EstatusAnalisisEnum.RATIFICADO_AUTORIZADO_Y_RECHAZADO.getClave()));
				model.setEstatus(EstatusAnalisisEnum.RATIFICADO_AUTORIZADO_Y_RECHAZADO);				
				model.setIndModAut(2L);
			}			
			estatusAnalisisModel = bitacoraUtility.armaBitacora(model, model.getComentarios().get(0).getDescripcion());
			analisisEntity.actualizaEstado(model);
			bitacoraEntity.guardaBitacora(estatusAnalisisModel);
		}catch (DatosClemException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new AnalisisNoEncontradoException(e.getMessage(), e.getCodigo());
		} catch (AnalisisNoEncontradoException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		} catch (ClasificacionException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}

		return model;
	}
	
	private Long calculaIndModificacion(AnalisisClasificacionEmpresas model){
    	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date auxProcessTime = new Date();
		Date auxCurrentTime = new Date();
		Date auxAnalisisTime = model.getFechaAutorizacion();
		String processTime = null; 
		Long response = 0L;
		try{
			 auxProcessTime = df.parse(df.format(auxProcessTime));
			 if(auxAnalisisTime.after(auxProcessTime)){
				processTime = df.format(auxProcessTime);
				processTime += " "+Constantes.HORA_INICIAL_PROCESO_BATCH+":"+Constantes.MINUTO_INICIAL_PROCESO_BATCH;
				df = new SimpleDateFormat("dd/MM/yyyy hh:mm");
				auxCurrentTime = df.parse(df.format(auxCurrentTime));
				auxProcessTime = df.parse(processTime);
				if(auxCurrentTime.before(auxProcessTime)){
					response= 1L;//No se envío a Sindo
				}else{
					response= 2L;//Ya se envío a Sindo
				}				 
			 }else{
				 //La transaccion ya se envio a sindo
				 response = 2L;
			 }
			
		}catch (Exception e) {
			log.error("====Error al calcular el inDice de modificacion:"+e.getMessage());				
		}
		
		log.error("#####InDice de autorizacion obtenido:"+ response);
		return response;
	}
	
	@Override
    public String buildNumeroFolio(String claveSubdel) {
		Calendar calendar = Calendar.getInstance();
		StringBuilder juliano = new StringBuilder();
		juliano.append(calendar.get(Calendar.DAY_OF_YEAR));
		String folio = claveSubdel
				+ (juliano.length() == 1 ? "00" + juliano : juliano.length() == 2 ? "0" + juliano : juliano);
		log.debug(String.format("Folio con dia juliano: %s", folio));
		folio = claveSubdel + "411";
		log.debug(String.format("Folio corregido con 411: %s", folio));
		return folio;
    }
	

	private void regresaClasificacionOriginal(String cveIdAnalisis, 
			String regPatronal, String tipoPersona) throws Exception{

		// Para obtener las fracción anterior y fracción actual originales. 
		estatusAnalisisModel = clasificacionServiceBusiness.buscaClasificacionInicial(new Long(cveIdAnalisis));
	 	
		// Para obtener la fracción actual a actualizar.
		SujetoObligado sujetoObligado = new SujetoObligado();
		sujetoObligado.setNumeroRegistroPatronal(regPatronal);
		sujetoObligado.setTipoPersonaFiscal(tipoPersona.equals("2") ? TipoPersonaFiscal.MORAL : TipoPersonaFiscal.FISICA);
		sujetoObligado = solicitudServiceBusiness.obtenerDetalleSolicitud(sujetoObligado);
		
		Fraccion fraccionAct = new Fraccion();
		if(estatusAnalisisModel.getFraccionActual() != null){
			fraccionAct.setId(estatusAnalisisModel.getFraccionActual().getId());
			fraccionAct.setClase(estatusAnalisisModel.getFraccionActual().getClase());
			clasificacionServiceBusiness.actualizaFraccion(sujetoObligado.getClasificacion(), fraccionAct);
		}else{
			fraccionAct.setId(null);
			clasificacionServiceBusiness.elimina(sujetoObligado.getClasificacion().getId());
		}

	}
	
	private AnalisisClasificacionEmpresas getAnalisisModel(String cveIdAnalisis, String login, EstatusAnalisisEnum status){
		AnalisisClasificacionEmpresas modelo = new AnalisisClasificacionEmpresas();
		modelo.setCveIdAnalisis(new Long(cveIdAnalisis));
		//Se asigna la fecha
		modelo.setFechaAutorizacion(new Date());
		//Se recupera el usuario logeado
		modelo.setClaveUsuarioAsignado(login);
		modelo.setCveIdEstatus(Long.valueOf(status.getClave()));
		modelo.setEstatus(status);
		return modelo;
	}
	
	@Override
	public List<AdjuntosClasificacion> consultarArchivoAdjunto(String idSol) throws Exception {
		try {
			log.debug(":::: Consultando documentos adjuntos para  la solicitud: " + idSol);
			return clasificacionActividadEconomicaBusiness.consultarArchivoAdjunto(idSol);			
		} catch (Exception e) {
			log.error("::: Error al consultar archivos adjuntos - folio: " + idSol);
			e.printStackTrace();
			throw e;
		}	
	}
	
	@Override
	public AnalisisClasificacionEmpresas obtenerIdAnalisis(BigDecimal idSolicitud) 
			throws AnalisisNoEncontradoException {
		
		AnalisisClasificacionEmpresas response = new AnalisisClasificacionEmpresas();
		
		try{			
			response = analisisEntity.consultaDetalleAnalisis(idSolicitud);
						
		}catch (AnalisisNoEncontradoException exc) {
			throw exc;			
		}catch (Exception exc) {
			log.error("Error : obtenerIDAnalisis():"+exc.getMessage());
			exc.printStackTrace();
			throw new AnalisisNoEncontradoException( "Se origin\u00F3 un problema al obtener el id del analisis: "+idSolicitud, 202);
		}
		return response;
	}
	
	
	@Override
	public AnalisisClasificacionEmpresas cancelaAnalisisPorBajaNRP(AnalisisClasificacionEmpresas analisis)
			throws AnalisisNoEncontradoException, ClasificacionException, EstatusMovimientoException {
		
		analisis.setCveIdEstatus(Long.valueOf(EstatusAnalisisEnum.CANCELADO_POR_BAJA_PATRONAL.getClave()));
		analisis.setEstatus(EstatusAnalisisEnum.CANCELADO_POR_BAJA_PATRONAL);
		EstatusAnalisisModel estatusAnalisisModel = new EstatusAnalisisModel();
		try {
			analisis.setTipoCausaAnalisis(null);
			AnalisisClasificacionEmpresas analisisAct = analisisEntity.actualizaEstado(analisis);
			estatusAnalisisModel = bitacoraUtility.armaBitacora(analisis, EstatusAnalisisEnum.CANCELADO_POR_BAJA_PATRONAL.getDescripcion());
			bitacoraEntity.guardaBitacora(estatusAnalisisModel);
		} catch (ClasificacionException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ClasificacionException(e.getMessage(), null);
		}
		return analisis;
	}
	
}


