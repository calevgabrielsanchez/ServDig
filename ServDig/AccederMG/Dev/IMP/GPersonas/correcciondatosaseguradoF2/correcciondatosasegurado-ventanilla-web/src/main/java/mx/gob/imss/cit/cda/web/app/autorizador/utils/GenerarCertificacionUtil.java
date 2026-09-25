package mx.gob.imss.cit.cda.web.app.autorizador.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import mx.gob.imss.ctirss.delta.model.asegurado.cda.MotivoAclaracion;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.TipoRegularizacionNSS;
import mx.gob.imss.ctirss.delta.model.enums.TipoNSSAclaracionEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoNSSCorreccionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.nss.CorreccionNSS;
import mx.gob.imss.ctirss.delta.model.gestion.nss.MovAclaracionNss;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.PeriodoMovimientoAfiliatorio;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

import mx.gob.imss.cit.cda.core.events.CreateEvent;
import mx.gob.imss.cit.cda.web.app.responsable.model.SeguimientoSolicitud;
import mx.gob.imss.cit.cda.web.bandeja.vo.TareaTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;

import org.springframework.beans.factory.annotation.Value;

@Component
public class GenerarCertificacionUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GenerarCertificacionUtil.class);

	@Value("${contenido.asegurado.tramite}")
	private String CORREO_ASEGURADO_TRAMITE;
	
	@Value("${encabezado}")
	private String ENCABEZADO_CORREOS;
	
	@Value("${pie.pagina}")
	private String PIE_PAGINA;
  
  private void generarParametrosReporte_step1(TramiteCorreccionCurp tramite, Map<String, Object> parametros,
		  Set<List<PeriodoMovimientoAfiliatorio>> cuentas){
    
    CorreoElectronico correo = null;
				TelefonoFijo telefono = null;	
				
				if(tramite.getPersona().getMediosContacto() != null && !tramite.getPersona().getMediosContacto().isEmpty()){
					for(MedioContacto med : tramite.getPersona().getMediosContacto()){
						if(med instanceof CorreoElectronico){
							 correo = (CorreoElectronico)med;	
						}
						
						if(med instanceof TelefonoFijo){
							 telefono = (TelefonoFijo)med;	
						}
					}
				}
		
		LOGGER.debug("-------CDA------parametro lista NSS:{} ", tramite.getListaNSS());
		List<String> nssList = new ArrayList<String>();
		List<String> nssInv = new ArrayList<String>();
		LOGGER.debug("-------CDA------ListaNSSCorreccion:{} ", tramite.getListaNssCorreccion().toString());
		for(CorreccionNSS nssInvolucrado :tramite.getListaNssCorreccion())
		{
			if(nssInvolucrado.getListaAclaraciones()!= null && !nssInvolucrado.getListaAclaraciones().isEmpty())
			{
				for(MovAclaracionNss movAclacion : nssInvolucrado.getListaAclaraciones() )
				{
					nssList.add(nssInvolucrado.getNss());
					nssInv.add(Long.toString(movAclacion.getIdTipoNssAclaracion()== null ? 7L :
							TipoNSSAclaracionEnum.fromId(movAclacion.getIdTipoNssAclaracion()).getClave()));
				}
			}
		}
		
		
		parametros.put("TELEFONO_FIJO", telefono != null && telefono.getNumero() != null  ? telefono.getNumero() :"");
		parametros.put("MAIL", correo != null ? correo.getCorreo() :"");		
		parametros.put("LISTA_TIPO_NSS",nssList);
		parametros.put("LISTA_TIPO_MOV",nssInv);
		parametros.put("SEXO", tramite.getPersona().getSexo().getDescripcion()/*.getIdSexo().toString()*/);
                // Para obtener los participantes la tarea debe estar en estado completada
//		parametros.put("RESPONSABLE", participantes.get(ParticipantesEnum.RESPONSABLE.getDescripcion()));
//		parametros.put("AUTORIZADOR", participantes.get(ParticipantesEnum.AUTORIZADOR.getDescripcion()));
//		
    generarParametrosReporte_step2(parametros, cuentas);
  
  }
  
  private void generarParametrosReporte_step2(Map<String, Object> parametros,
          Set<List<PeriodoMovimientoAfiliatorio>> cuentas){
  
    
    	LOGGER.debug("Va a crear lista de datos laborales {} " , cuentas.toString());
		Iterator<List<PeriodoMovimientoAfiliatorio>> perIterator = cuentas.iterator();
		if(perIterator.hasNext()){
			parametros.put("LISTA_DATOS_LABORALES", crearMovimientosAfiliatorios(perIterator.next()));
			LOGGER.debug("-------CDA------ cuenta{}", parametros.get("LISTA_DATOS_LABORALES"));
		}
		
                LOGGER.debug("Lista movimiento final");  
		if(perIterator.hasNext()){
			parametros.put("LISTA_MOVIMIENTO_FINAL", perIterator.next());
			LOGGER.debug("-------CDA------ cuenta{}", parametros.get("LISTA_MOVIMIENTO_FINAL"));
		}
  
  
  }
	
	public Map<String, Object> generarParametrosReporte(Solicitud solicitud, TramiteCorreccionCurp tramite,
			String nssCertificador, Set<List<PeriodoMovimientoAfiliatorio>> cuentas){
		LOGGER.debug("Certificacion id Solicitud {}", (solicitud.getSolicitudId()).toString() );
            LOGGER.debug("Entra a generar parametros");

		Locale locMEX = new Locale("es", "MX");
		SimpleDateFormat sdf = new SimpleDateFormat("dd ' / ' MM ' / 'yyyy", locMEX);
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		tramite.setPersona(tramite.getPersonaRENAPO());

		parametros.put("CADENA_ORIGINAL", solicitud.getFirmaElectronica().getCadenaOriginal());
		parametros.put("SELLO_DIGITAL", solicitud.getFirmaElectronica().getRecibo());
		parametros.put("SECUENCIA_NOTARIAL", solicitud.getFirmaElectronica().getSecuenciaNotaria());
		parametros.put("NUMERO_SERIE", solicitud.getFirmaElectronica().getSerialCertificado());
		parametros.put("FECHA_FORMATO_DOF", "");
		parametros.put("FOLIO", solicitud.getNoFolioSolicitud());
		parametros.put("FECHA_EXPEDICION", sdf.format(solicitud.getFechaConclusion() != null ? solicitud.getFechaConclusion() : new Date()));
		parametros.put("NSS", nssCertificador);
		parametros.put("CURP", tramite.getPersona().getCurp());
		parametros.put("NOMBRE", tramite.getPersona().getNombre());
		parametros.put("PRIMER_APELLIDO", tramite.getPersona().getPrimerApellido());
		parametros.put("SEGUNDO_APELLIDO", tramite.getPersona().getSegundoApellido());
		parametros.put("FECHA_NACIMIENTO", tramite.getPersona().getFechaNacimientoFormateada());
		parametros.put("LUGAR_NACIMIENTO", tramite.getPersona().getLugarNacimiento().getNombre());

		this.generarParametrosReporte_step1(tramite, parametros, cuentas);
		
		List <String> listMotivosAclaracion = new ArrayList<String>();
		for(MotivoAclaracion motivoAclaracion : tramite.getMotivosAclaracion()){
			
				listMotivosAclaracion.add(motivoAclaracion.getDescripcionMotivo());
				
		}
		parametros.put("MOTIVO_ACLARACION",listMotivosAclaracion.toString().toUpperCase());
		
		parametros.put("ORIGEN",solicitud.getOrigenSolicitud().getIdTipoSolicitud().toString());
		
                LOGGER.debug("Imprime parametros {} ", parametros.toString());  
		
		return parametros;		
	}
	
	private List<PeriodoMovimientoAfiliatorio> crearMovimientosAfiliatorios(List<PeriodoMovimientoAfiliatorio>periodos){
		List<PeriodoMovimientoAfiliatorio> periodoMovimientos = new ArrayList<PeriodoMovimientoAfiliatorio>();
		if(periodos!=null && !periodos.isEmpty()){
			periodoMovimientos.addAll(periodos);
		}else{
			PeriodoMovimientoAfiliatorio periodoMovimientoAfiliatorio = new PeriodoMovimientoAfiliatorio();
			periodoMovimientoAfiliatorio.setFechaFinalMovimiento(null);
			periodoMovimientoAfiliatorio.setFechaInicioMovimiento(null);
			periodoMovimientoAfiliatorio.setNrp(null);
			periodoMovimientos.add(periodoMovimientoAfiliatorio);
		}
		
		
		return periodoMovimientos;
	}
	
        @Deprecated
	public List<TipoRegularizacionNSS> obtenerClaveNSSReporte(TramiteCorreccionCurp tramiteCorreccionCurp, String nss){
		List<TipoRegularizacionNSS> claveNSS = new ArrayList<TipoRegularizacionNSS>();
		
		LOGGER.debug("valor de topo NSS: {}",tramiteCorreccionCurp.getCertificacionNSS().getTipoRegularizacionNSS());
		for (TipoRegularizacionNSS idNSS: tramiteCorreccionCurp.getCertificacionNSS().getTipoRegularizacionNSS()){
			TipoRegularizacionNSS tipoRegNss = new TipoRegularizacionNSS();
			if (idNSS.getIdTipoRegularizacionNSS().equals(TipoNSSAclaracionEnum.CORRECCION_DE_DATOS_ESTADISTICOS.getId())) {
				tipoRegNss.setIdTipoRegularizacionNSS(TipoNSSAclaracionEnum.CORRECCION_DE_DATOS_ESTADISTICOS.getClave());
			} else {
				if (idNSS.getIdTipoRegularizacionNSS().equals(TipoNSSAclaracionEnum.CORRECCION_DE_NOMBRE.getId())) {
					tipoRegNss.setIdTipoRegularizacionNSS(TipoNSSAclaracionEnum.CORRECCION_DE_NOMBRE.getClave());
				}
			}
			tipoRegNss.setDescripcionRegularizacionNSS(nss);
			claveNSS.add(tipoRegNss);
			
		}
		LOGGER.debug("LA CLAVE ES: {}",claveNSS);
		return claveNSS;
	}
	
	public String contenidoCorreoAtendida(Solicitud solicitud, String link, TramiteCorreccionCurp tramite) {

		SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");

		return ENCABEZADO_CORREOS + MessageFormat
				.format(CORREO_ASEGURADO_TRAMITE, fecha.format(new Date()), solicitud.getNoFolioSolicitud(), tramite.getPersonaRENAPO().getNombre(),
						tramite.getPersonaRENAPO().getPrimerApellido(), tramite.getPersonaRENAPO().getSegundoApellido(),
						solicitud.getSubdelegacion() != null ? solicitud.getSubdelegacion().getDescripcion() : "", link,
						tramite.getPersonaRENAPO().getCurp()) + PIE_PAGINA;
		}
        
        
         public Map<String, String> armarTareasTramites(CreateEvent<SeguimientoSolicitud> requestCreateEvent){
		Map<String, String> tareasTramitesMap = new HashMap<String, String>();
                
		for (TareaTramite tareaTramite : requestCreateEvent.getData().getTareasTramites()) {
                    tareasTramitesMap.put(tareaTramite.getIdTramite(), tareaTramite.getIdTarea()); 
		}
                
		return tareasTramitesMap;
	}         

                 
         public TramiteCorreccionCurp obtenerTramitePrincipal(Solicitud solicitud) {
             TramiteCorreccionCurp tramitePrincipal = new TramiteCorreccionCurp();
             for(Tramite t : solicitud.getTramites()) {
                    TramiteCorreccionCurp tcda = (TramiteCorreccionCurp) t;
                    if(tcda.getPersonaRENAPO() != null){
                        tramitePrincipal = tcda;
                        break;
                    }
                }
             return tramitePrincipal;
         }
}
