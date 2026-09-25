/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Héctor Lara Andrés
 *  @Proyecto: delta
 *  @Archivo: RectificacionBusiness.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.rectificacion
 *  @Fecha:17/08/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.desechar;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.clasificacion.EstatusMovimientoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ClasificacionPropuestaDTO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.analisis.AnalisisServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.bitacora.BitacoraServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.AnalisisServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.TipoCausaAnalisisServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clasificacion.ClasificacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.desechar.DesecharBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.bitacora.BitacoraServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.clasificacion.actividad.economica.ActividadEcServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisEnum;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisModel;
import mx.gob.imss.ctirss.delta.model.clasificacion.TipoCausaAnalisis;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clase;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

@Stateless(name = "desecharServiceBusiness", mappedName = "desecharServiceBusiness")
public class DesecharBusiness extends AbstractServiceBusiness implements DesecharBusinessRemote{

	@EJB
	private BitacoraServiceEntityLocal bitacoraServiceEntity;
	
	@EJB
	private BitacoraServiceUtilityLocal bitacoraUtility;	
	
	@EJB
	private AnalisisServiceEntityLocal analisisEntity;
	
	@EJB
	private mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud.SolicitudServiceBusinessRemote solicitudServiceBusiness;
	
	@EJB
	private ClasificacionServiceBusinessRemote clasificacionServiceBusiness;

	@EJB
	private TipoCausaAnalisisServiceBusinessRemote tipoCausaAnalisisServiceBusiness;

	@EJB
	private ActividadEcServiceRemote clasificacionActividadEconomicaBusiness;
	
	@EJB
	private AnalisisServiceBusinessRemote analisisBusiness;
	
	@EJB
	private ActividadEcServiceRemote actividadEcService;
	
	@EJB
	private SolicitudServiceBusinessRemote solicitudPatronalBusiness;

	
	@Override
	public Integer desecharSolicitud(ClasificacionPropuestaDTO dto, String idTipoTramite, String fechaSurteEfecto)throws EstatusMovimientoException {
		
		//Para desechar la solicitud se ocupa el objeto ClasificacionPropuestaDTO que en realidad es la fracción
		//que el operador selecciona con el clasificador, que de acuerdo a la regla de negocio deberia ser
		//la fraccion inmediata anterior al tramite actual

		SujetoObligado sujetoObligado = new SujetoObligado();
		Integer status=0;
		log.info("ClasificacionPropuestaDTO::");
		log.info(dto);
		
		try{			
			//Para la bitacora insertamos para el comentario el estatus para desechar el tramite			
			dto.setComentarios("TRAMITE DESECHADO");

			if(dto.getRegPatronal().length() != 11){
				log.debug("::: Obteniendo registro patronal completo");
				dto.setRegPatronal(solicitudServiceBusiness.obtenerRegistroPatronalCompleto(dto.getRegPatronal()));
			}											

//HASTA AHORITA DEL sujetoObligado SOLO OCUPAMOS LA CLASIFICACION				
			//Obtiene sujeto obligado
			sujetoObligado.setNumeroRegistroPatronal(dto.getRegPatronal());
			sujetoObligado.setTipoPersonaFiscal(dto.getTipoPersona().toString().trim().equals("2") ? TipoPersonaFiscal.MORAL : TipoPersonaFiscal.FISICA);
			sujetoObligado = solicitudServiceBusiness.obtenerDetalleSolicitud(sujetoObligado);	
			if(sujetoObligado.getSubdelegacion() == null) {
				log.debug("::: Error al obtener la delegación del SO, IdSolicitud: " + dto.getCveIdSolicitud() + ", analisis: " + dto.getCveIdAnalisis());
				throw new EstatusMovimientoException("No se pudo obtener la subdelegación: ");
			}
			
			//Obtiene objeto VO necesario para las actualizaciones
			AnalisisClasificacionEmpresas analisisClasificacionEmpresas = obtieneClasificacionEmpresas(dto, sujetoObligado, idTipoTramite);
				
			//Actualizamos estatus en MAC a desechado
			this.desechaAnalisisMAC(analisisClasificacionEmpresas, dto.getComentarios());			
			
			//Actualizamos en la tabla dit_clasificacion el valor de la fraccion y prima			
			this.actualizaClasificacionPatron(sujetoObligado.getClasificacion(),
					analisisClasificacionEmpresas.getClasificacionPropuesta().getFraccion());			

			//Actualiza el historico de causa
			this.registraHistCausa(analisisClasificacionEmpresas);

			// SE CANCELA METODO YA QUE ESTA FUNCIONALIDAD YA NO APLICA
			//Actualiza a estado desechado la solicitud y tramite			
//			this.desechaSolicitudyTramite(dto.getCveIdSolicitud());
						
			//Envia movimiento 06 a SINDO		
			//Pasamos al SO la clasificacion seleccionada por el operador
			Clasificacion clasif = clasificacionServiceBusiness
					.obtenerDetalleFraccionPorId(analisisClasificacionEmpresas.getClasificacionPropuesta());
			clasif.getFraccion()
				.setPrimaSRT(analisisClasificacionEmpresas.getClasificacionPropuesta().getFraccion().getPrimaSRT());			
			sujetoObligado.getClasificacion().setFraccion(clasif.getFraccion());
			this.enviaMovimientoSINDO(sujetoObligado, analisisClasificacionEmpresas.getTipoCausaAnalisis(), fechaSurteEfecto);
			
			// Agrega mensaje en buzon de clasificacion para avisar al patron que su tramite fue desechado
			Solicitud solicitud = new Solicitud();
			solicitud = solicitudPatronalBusiness.consultarSolicitudPorId(new Long(dto.getCveIdSolicitud()));
			log.debug("::: Se insertara mensaje en buzon de clasificacion, folio: " + solicitud.getNoFolioSolicitud());
			String msj = "Su solicitud con folio " + solicitud.getNoFolioSolicitud() + " ha sido desechada";
			actividadEcService.insertaMensajeBuzon(sujetoObligado.getNumeroRegistroPatronal(), idTipoTramite,
					solicitud.getNoFolioSolicitud(), msj);
						
			status=1;			

		}catch(Exception e){
			status = 0;	
			log.error("::: Error al desechar la solicitud: " + dto.getCveIdSolicitud() + ", analisis: " + dto.getCveIdAnalisis());
			e.printStackTrace();
			log.error(e.getMessage());
			throw new EstatusMovimientoException("Ocurrio un error al desechar la solicitud: " + e.getMessage());
		}
			
		return status;
	}	
	

	@Override
	public void enviaMovimientoSINDO(SujetoObligado sujetoObligado, String tipoCausaAnalisis, String fechaSurteEfecto) {
		log.debug("::: Enviando movimiento 06");
		String folio = analisisBusiness.buildNumeroFolio(sujetoObligado.getSubdelegacion().getClave());            
		clasificacionActividadEconomicaBusiness.ejecutarProcesoSincronizacionSINDO(
				folio,
				sujetoObligado, calcularFechaSurteEfecto(fechaSurteEfecto),
				new Long(tipoCausaAnalisis), Integer.valueOf(1), Integer.valueOf(6), Integer.valueOf(6));
	}
	

// SE CANCELA METODO YA QUE ESTA FUNCIONALIDAD YA NO APLICA
//	
//	@Override
//	public void desechaSolicitudyTramite(String cveIdSolicitud) throws Exception {		
//		//Actualiza solicitud y tramite a estado desechado
//		try {			
//			log.debug("::: Actualizando la solicitud y tramite a estado desechado");
//			clasificacionServiceBusiness.actualizaSolicitudyTramite(cveIdSolicitud,
////AQUI ACTUALIZAR DE ACUERDO A LOS VALORES DE ENUM PARA EL ESTADO DESECHAR	
//new Long(2), new Long(2));			
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		}					
//	}
	
	@Override
	public void registraHistCausa(AnalisisClasificacionEmpresas analisisClasificacionEmpresas) throws Exception {		
		log.debug("::: Se registra el historico de causa");
		tipoCausaAnalisisServiceBusiness.registraCausa(analisisClasificacionEmpresas);
	}	
		

	@Override
	public void actualizaClasificacionPatron(Clasificacion clasificacionSO, Fraccion fraccionNueva) throws Exception {		
		//Actualiza clasificacion del patron
		try {			
			log.debug("::: Actualizando la clasificacion en desechar tramite");
			clasificacionServiceBusiness.actualizaFraccionyPrima(clasificacionSO,
					fraccionNueva);			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}					
	}

	@Override
	public void desechaAnalisisMAC(AnalisisClasificacionEmpresas analisisClasificacionEmpresas, String comentarios) throws Exception {		
		//Actualiza analisis de MAC
		try {
			log.debug("::: Actualizando analisis para desechar, IdAnalisis: " + analisisClasificacionEmpresas.getCveIdAnalisis());
			analisisEntity.actualizaAnalisisDesechar(analisisClasificacionEmpresas, true);
			EstatusAnalisisModel estatusAnalisisModel = bitacoraUtility.armaBitacora(analisisClasificacionEmpresas, comentarios);
			bitacoraServiceEntity.guardaBitacora(estatusAnalisisModel);			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}			
		
	}	
	
	private Date calcularFechaSurteEfecto(String fechaSurteEfecto) {
        Calendar cal = Calendar.getInstance();
        int day = Integer.parseInt(fechaSurteEfecto.replaceAll("\\d{0}\\D(\\d{0}).*", "$1").replaceAll("^0", ""));
        int month = Integer.parseInt(fechaSurteEfecto.replaceAll("\\d{2}\\D(\\d{2}).*", "$1").replaceAll("^0", ""));
        int year = Integer.parseInt(fechaSurteEfecto.replaceAll("(?:\\d{2}\\D){2}(\\d{4})", "$1"));
        cal.set(Calendar.DATE, day);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.YEAR, year);
        return cal.getTime();
    }	
	
	private AnalisisClasificacionEmpresas obtieneClasificacionEmpresas(ClasificacionPropuestaDTO dto, SujetoObligado sujetoObligado, String idTipoTramite) {
		AnalisisClasificacionEmpresas analisisClasificacionEmpresas = new AnalisisClasificacionEmpresas();

		log.debug("::: Creando objeto de clasificacion IdAnalisis: " + dto.getCveIdAnalisis());
		
		Clasificacion clasificacionAnterior=new Clasificacion();
		Clasificacion clasificacionActual=new Clasificacion();
		Clasificacion clasificacion=new Clasificacion();
		Fraccion fraccion=new Fraccion();
		Grupo grupo=new Grupo();
		Division division=new Division();
		Clase clase = new Clase();

		division.setId(Long.parseLong(dto.getCveIdDivision()));
		grupo.setId(Long.parseLong(dto.getCveIdGrupo()));
		grupo.setDivision(division);
		clase.setClave(new Long(dto.getClase()));
		fraccion.setId(Long.parseLong(dto.getCveIdFraccionPro()));
		fraccion.setPrimaSRT(BigDecimal.valueOf(Double.parseDouble(dto.getPrimaSRTPro())));
		fraccion.setGrupo(grupo);
		fraccion.setClase(clase);
		clasificacion.setFraccion(fraccion);
		
		analisisClasificacionEmpresas.setCveIdAnalisis(Long.valueOf(Long.parseLong(dto.getCveIdAnalisis().trim())));
		analisisClasificacionEmpresas.setActividadDetectada(dto.getActividadDetectada());
		analisisClasificacionEmpresas.setClasificacionPropuesta(clasificacion);
		analisisClasificacionEmpresas.setClaveUsuarioAsignado(dto.getCveUsuarioAsignado());
		analisisClasificacionEmpresas.setCveIdDelegacion(new Long(dto.getCveIdDelegacion()));
		analisisClasificacionEmpresas.setCveIdSubdelegacion(new Long(dto.getCveIdSubdelegacion()));
		analisisClasificacionEmpresas.setCveIdAnalisis(new Long(dto.getCveIdAnalisis()));
				
		//Tipo de causa para desechar, se busca de acuerdo al tipó de tramite
		TipoCausaAnalisis tc = tipoCausaAnalisisServiceBusiness.consultaTipoCausa(new Long(idTipoTramite), 3);
		log.debug("::: La causa que se enviara a SINDO para el tramite es " + tc.getCveIdTipoCausa() + "-" + tc.getDesCausa());
		analisisClasificacionEmpresas.setTipoCausaAnalisis(""+tc.getCveIdTipoCausa());
		dto.setComentarios("TRAMITE DESECHADO, CAUSA " + tc.getDesCausa());
		
		//Estatus de clasificacion desechado 			
		analisisClasificacionEmpresas.setCveIdEstatus(Long.valueOf(EstatusAnalisisEnum.SOLICITUD_DESECHADA.getClave()));
		analisisClasificacionEmpresas.setEstatus(EstatusAnalisisEnum.SOLICITUD_DESECHADA);				
					
		/* Pasa Clasificación Actual como Anterior */
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
		
		return analisisClasificacionEmpresas;
	}	
	
	
}