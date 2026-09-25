package mx.gob.imss.cit.cda.web.utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.gestion.solicitud.flujo.model.InicioTramite;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.ParticipantesEnum;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoNegocioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

public class WorkFlowDataUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(WorkFlowDataUtil.class);
	private static final String ORIGEN_SOLICITUD_INTERNET = "INTERNET";
	private static final String SUBDELEGACION_SIN_RESPONSABLE = "SIN_RESPONSABLE";

	/*
	 * 
	 * Selecciona al autorizador y responsable que esten disponibles
	 */
	public static InicioTramite iniciarTramite(Solicitud solicitud, List<Fisica> listAutorizador, TramiteCorreccionCurp tcda,
			List<Fisica> listResponsable, String ultimoResponsable) {

		InicioTramite inicioTramite = new InicioTramite();
		DeltaUtils deltaUtils = new DeltaUtils();
		
		Fisica autorizador = elegirPersona(listAutorizador);
		Fisica responsable = elegirPersonaOrdenada(listResponsable, ultimoResponsable);
		LOGGER.info("Curp autorizador", autorizador.getCurp());
                LOGGER.info("Curp responsable", responsable.getCurp());
		inicioTramite.setFolio(solicitud.getNoFolioSolicitud());
		inicioTramite.setIdTramite(tcda.getTramiteId().intValue());				
		inicioTramite.setEstatus(responsable.getCurp().equals(SUBDELEGACION_SIN_RESPONSABLE)?EstadoTramiteEnum.SIN_RESPONSABLE.getDescripcion():EstadoNegocioEnum.obtenerDescripcionNegocio(EstadoTramiteEnum.EN_ESPERA_TRAMITADOR.getCodigo()));
		inicioTramite.setFechaSolicitud(deltaUtils.convertirDateToStringCompleto(new Date()));
		inicioTramite.setFechaActualizacion(deltaUtils.convertirDateToStringCompleto(new Date()));
		Map<String, String> participantes = new HashMap<String, String>();
		participantes.put(ParticipantesEnum.AUTORIZADOR.getDescripcion(), autorizador.getCurp());
		participantes.put(ParticipantesEnum.RESPONSABLE.getDescripcion(), responsable.getCurp().equals(SUBDELEGACION_SIN_RESPONSABLE)?"":responsable.getCurp());
		LOGGER.info("participantes", participantes.toString());
		inicioTramite.setParticipantes(participantes);
                LOGGER.info("participantes", participantes.get(ParticipantesEnum.RESPONSABLE.getDescripcion()));
		
		Map<String, Object> mapJson = new HashMap<String, Object>();
		
		mapJson.put("nssInvolucrados", (tcda.getListaNSS().get(0)));
		mapJson.put("origen", ORIGEN_SOLICITUD_INTERNET);
		mapJson.put("subdelegacion", solicitud.getSubdelegacion().getId().toString());
		mapJson.put(ParticipantesEnum.RESPONSABLE.getDescripcion(), responsable.getCurp());
		mapJson.put("cveEstado", responsable.getCurp().equals(SUBDELEGACION_SIN_RESPONSABLE)?EstadoTramiteEnum.SIN_RESPONSABLE.getCodigo():EstadoTramiteEnum.EN_ESPERA_TRAMITADOR.getCodigo());
		inicioTramite.setData(generarJsonDataWf(mapJson));
		
		return inicioTramite;
		
	}
	
	/*
	 * 
	 * Convertir java a json
	 */
	public static String generarJsonDataWf(Map<String, Object> data){
		String jsonParams ="";
		
		ObjectMapper mapper = new ObjectMapper();		
		try {
			jsonParams = mapper.writeValueAsString(data);
		} catch (JsonGenerationException e) {
			LOGGER.error("Error al parsear el Json del workflow", e);
		} catch (JsonMappingException e) {
			LOGGER.error("Error al parsear el Json del workflow", e);
		} catch (IOException e) {
			LOGGER.error("Error al parsear el Json del workflow", e);
		}
		return jsonParams;
	}
	
	
	public static Map<String, Object> generarJavaDataWf(String json){
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();		
		try {
			map = mapper.readValue(json, new TypeReference<Map<String, Object>>(){});

		} catch (JsonParseException e) {
			LOGGER.error("Error al generar el objeto Java del workflow", e);
		} catch (JsonMappingException e) {
			LOGGER.error("Error al generar el objeto Java del workflow", e);
		} catch (IOException e) {
			LOGGER.error("Error al generar el objeto Java del workflow", e);
		}
		
		return map;
	}

	public static Fisica elegirPersona(List<Fisica> persona) {
		return persona.get(randomPersona(0, persona.size()));
	}
	
	public static int randomPersona(int min, int max) {	     
	    return min + (int)(Math.random() * ((max - min)));	    
	}	
	
	public static Solicitud generarSolicitudResponsable(Map <String,String> participantes, Long idSolicitud){
		
		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitudId(idSolicitud);
		Usuario usuario = new Usuario();
		usuario.setCveIdUsuario(participantes.get(ParticipantesEnum.RESPONSABLE.getDescripcion()));
		usuario.setUsuario(participantes.get(ParticipantesEnum.RESPONSABLE.getDescripcion()));
		solicitud.setSolicitante(usuario);
		return solicitud;	
	}
	
	public static Fisica elegirPersonaOrdenada(List<Fisica> persona, String curpUltimoResp) {		
		TreeSet<Fisica> fisicas = new TreeSet<Fisica>(new Comparator<Fisica>() {
			@Override
			public int compare(Fisica o1, Fisica o2) {
				return o1.getCurp().compareTo(o2.getCurp());
			}
		});		
		fisicas.addAll(persona);
		Fisica fisicaUltimo = new Fisica();
		if(StringUtils.isBlank(curpUltimoResp)){
			curpUltimoResp = "A";
		}
		fisicaUltimo.setCurp(curpUltimoResp);
		fisicas.add(fisicaUltimo);
		
		List<Fisica> fisicaOrdena = new ArrayList<Fisica>(fisicas);		
		return fisicaOrdena.get(bynarySearchCurp(fisicas, fisicaUltimo) == fisicas.size() -1 ? 0 :bynarySearchCurp(fisicas, fisicaUltimo)+1);

	}
	
	private static int bynarySearchCurp(TreeSet<Fisica>fisicas, Fisica fisicaKey){
		List<Fisica> fisicaOrdena = new ArrayList<Fisica>(fisicas);
		return Collections.binarySearch(fisicaOrdena, fisicaKey, new Comparator<Fisica>() {

			@Override
			public int compare(Fisica o1, Fisica o2) {
				return o1.getCurp().compareTo(o2.getCurp());
			}
		});
	}
	
	
}
