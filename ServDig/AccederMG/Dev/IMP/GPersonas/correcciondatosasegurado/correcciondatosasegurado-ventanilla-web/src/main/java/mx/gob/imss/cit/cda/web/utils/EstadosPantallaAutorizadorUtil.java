package mx.gob.imss.cit.cda.web.utils;

import java.util.HashMap;
import java.util.Map;

import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;

import org.springframework.stereotype.Component;

@Component
public class EstadosPantallaAutorizadorUtil {

	private static final Map<Integer, String> estadosPantallaAutorizador = new HashMap<Integer, String>();
	
	//Estados de pantalla para el autorizador
	private static final String AUTORIZADOR_EDO_PANTALLA_OPERADA = "0";
	private static final String AUTORIZADOR_EDO_PANTALLA_ATENDIDA = "1";
	private static final String AUTORIZADOR_EDO_PANTALLA_POR_AUTORIZAR = "2";
	private static final String AUTORIZADOR_EDO_PANTALLA_ASIGNADA = "3";
	private static final String AUTORIZADOR_EDO_PANTALLA_RECHAZADA = "4";
	private static final String AUTORIZADOR_EDO_PANTALLA_AUTORIZADA = "5";
	private static final String AUTORIZADOR_EDO_PANTALLA_DEFAULT =  "6";
	
	{
		//Solicitud Estado: Operada
		estadosPantallaAutorizador.put(EstadoTramiteEnum.PROCESADO_SINDO.getCodigo(), AUTORIZADOR_EDO_PANTALLA_OPERADA);
		
		//Solicitud Estado: Atendida
		estadosPantallaAutorizador.put(EstadoTramiteEnum.CERRADO.getCodigo(), AUTORIZADOR_EDO_PANTALLA_ATENDIDA);
		
		//Solicitud Estado: Por autorizar
		estadosPantallaAutorizador.put(EstadoTramiteEnum.EN_ESPERA_AUTORIZACION.getCodigo(), AUTORIZADOR_EDO_PANTALLA_POR_AUTORIZAR);
		
		//Solicitud Estado: Sin Responsable, Asignada o Información  Solicitada o Reasignada
		estadosPantallaAutorizador.put(EstadoTramiteEnum.SIN_RESPONSABLE.getCodigo(), AUTORIZADOR_EDO_PANTALLA_ASIGNADA);
		estadosPantallaAutorizador.put(EstadoTramiteEnum.EN_ESPERA_TRAMITADOR.getCodigo(), AUTORIZADOR_EDO_PANTALLA_ASIGNADA);
		estadosPantallaAutorizador.put(EstadoTramiteEnum.EN_ESPERA_DERECHOHABIENTE.getCodigo(), AUTORIZADOR_EDO_PANTALLA_ASIGNADA);
		
		//Solicitud Estado: Error SINDO  o Rechazada
		estadosPantallaAutorizador.put(EstadoTramiteEnum.ERROR_SINDO.getCodigo(), AUTORIZADOR_EDO_PANTALLA_RECHAZADA);
		estadosPantallaAutorizador.put(EstadoTramiteEnum.RECHAZADO.getCodigo(), AUTORIZADOR_EDO_PANTALLA_RECHAZADA);
		
		//Solicitud Estado: Autorizada o Cancelada o Enviada SINDO
		estadosPantallaAutorizador.put(EstadoTramiteEnum.ANALISIS_COMPLETADO.getCodigo(), AUTORIZADOR_EDO_PANTALLA_AUTORIZADA);
		estadosPantallaAutorizador.put(EstadoTramiteEnum.BAJA_IMPROCEDENCIA.getCodigo(), AUTORIZADOR_EDO_PANTALLA_AUTORIZADA);
		estadosPantallaAutorizador.put(EstadoTramiteEnum.ENVIA_CERTIFICACION_SINDO.getCodigo(), AUTORIZADOR_EDO_PANTALLA_AUTORIZADA);
		
	}
	
	public String obtenerEstadoAutorizador(Integer estado, Integer edoSolicitud) {//pendiente si se retira edoSolicitud
		return  estadosPantallaAutorizador.get(estado) == null ?  
				AUTORIZADOR_EDO_PANTALLA_DEFAULT : estadosPantallaAutorizador.get(estado);
	}
	
}
