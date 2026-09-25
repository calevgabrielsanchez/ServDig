package mx.gob.imss.cit.cda.web.utils;

import java.util.HashMap;
import java.util.Map;

import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;

import org.springframework.stereotype.Component;

@Component
public class EstadosPantallaUtil {

	private static final Map<Integer, String> estadosPantallaResponsable = new HashMap<Integer, String>();
	
	private static final String RESPONSABLE_EDO_PANTALLA_ATIENDE =  "0";
	private static final String RESPONSABLE_EDO_PANTALLA_AUTORIZA = "1";
	private static final String RESPONSABLE_EDO_PANTALLA_FINALIZA = "2";
	private static final String RESPONSABLE_EDO_PANTALLA_DEFAULT =  "3";

	
	{
		//Solicitud Estado: Asignada o Información Solicitada o Reasignada o Error SINDO o Rechazada
		estadosPantallaResponsable.put(EstadoTramiteEnum.EN_ESPERA_TRAMITADOR.getCodigo(), RESPONSABLE_EDO_PANTALLA_ATIENDE);
		estadosPantallaResponsable.put(EstadoTramiteEnum.EN_ESPERA_DERECHOHABIENTE.getCodigo(), RESPONSABLE_EDO_PANTALLA_ATIENDE);
		estadosPantallaResponsable.put(EstadoTramiteEnum.ERROR_SINDO.getCodigo(), RESPONSABLE_EDO_PANTALLA_ATIENDE);
		estadosPantallaResponsable.put(EstadoTramiteEnum.RECHAZADO.getCodigo(), RESPONSABLE_EDO_PANTALLA_ATIENDE);
		
		//Solicitud Estado: Por autorizar o Autorizada o Cancelada o Enviada SINDO
		estadosPantallaResponsable.put(EstadoTramiteEnum.EN_ESPERA_AUTORIZACION.getCodigo(), RESPONSABLE_EDO_PANTALLA_AUTORIZA);
		estadosPantallaResponsable.put(EstadoTramiteEnum.ANALISIS_COMPLETADO.getCodigo(), RESPONSABLE_EDO_PANTALLA_AUTORIZA);
		estadosPantallaResponsable.put(EstadoTramiteEnum.BAJA_IMPROCEDENCIA.getCodigo(), RESPONSABLE_EDO_PANTALLA_AUTORIZA);
		estadosPantallaResponsable.put(EstadoTramiteEnum.ENVIA_CERTIFICACION_SINDO.getCodigo(), RESPONSABLE_EDO_PANTALLA_AUTORIZA);
		
		//Solicitud Estado: Operada o Atendida
		estadosPantallaResponsable.put(EstadoTramiteEnum.CERRADO.getCodigo(), RESPONSABLE_EDO_PANTALLA_FINALIZA);
		estadosPantallaResponsable.put(EstadoTramiteEnum.PROCESADO_SINDO.getCodigo(), RESPONSABLE_EDO_PANTALLA_FINALIZA);
		
	}
	
	public String obtenerEstadoResponsable(Integer estado, Integer edoSolicitud, String usuario) {//Revisar si se retira edoSolicitud y usuario
		String estadoResponsable = RESPONSABLE_EDO_PANTALLA_DEFAULT;
		
		if (estadosPantallaResponsable.get(estado) != null){
			estadoResponsable = estadosPantallaResponsable.get(estado);	
		}
	
		return estadoResponsable;
	}
	
}
