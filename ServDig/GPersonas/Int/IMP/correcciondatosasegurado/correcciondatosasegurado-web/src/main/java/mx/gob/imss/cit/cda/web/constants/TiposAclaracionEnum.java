package mx.gob.imss.cit.cda.web.constants;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mx.gob.imss.cit.cda.web.constants.TiposAclaracionEnum.Dependencia;
import mx.gob.imss.ctirss.delta.model.enums.MotivoAclaracionEnum;

public enum TiposAclaracionEnum {

	COBRO_INCAPACIDAD(Dependencia.IMSS.nombre, "COBRO DE INCAPACIDAD","1",
			MotivoAclaracionEnum.COBRO_INCAPACIDAD), 
	PENSION(Dependencia.IMSS.nombre, "PENSI\u00D3N","2", 
			MotivoAclaracionEnum.PENSION), 
	RETIRO_DESEMPLEO(Dependencia.IMSS.nombre, "RETIRO POR DESEMPLEO","3", 
			MotivoAclaracionEnum.RETIRO_DESEMPLEO),
	REGISTRO_BENEFICIARIOS(Dependencia.IMSS.nombre, "REGISTRO DE BENEFICIARIOS","4", 
			MotivoAclaracionEnum.REGISTRO_BENEFICIARIOS),
	ADSCRIPCION_UMF(Dependencia.IMSS.nombre, "ADSCRIPCI\u00D3N A UMF","5", 
			MotivoAclaracionEnum.ADSCRIPCION_UMF),
	CAMBIO_UMF(Dependencia.IMSS.nombre, "CAMBIO DE UMF","6", 
			MotivoAclaracionEnum.CAMBIO_UMF),
	GASTOS_MATRIMONIO(Dependencia.IMSS.nombre, "GASTOS DE MATRIMONIO","7", 
			MotivoAclaracionEnum.GASTOS_MATRIMONIO),
	GASTOS_FUNERAL(Dependencia.IMSS.nombre, "GASTOS DE FUNERAL","8", 
			MotivoAclaracionEnum.GASTOS_FUNERAL),

	OBTENER_CREDITO(Dependencia.INFONAVIT.nombre, "OBTENER CR\u00C9DITO","1", 
			MotivoAclaracionEnum.OBTENER_CREDITO), 
	CONCLUSION_CREDITO(Dependencia.INFONAVIT.nombre, "CONCLUSI\u00D3N DE CR\u00C9DITO","2", 
			MotivoAclaracionEnum.CONCLUSION_CREDITO),
	PRORROGA_REESTRUCTURA_CREDITO(Dependencia.INFONAVIT.nombre, "PR\u00D3RROGA O REESTRUCTURA DE CR\u00C9DITO","3", 
			MotivoAclaracionEnum.PRORROGA_REESTRUCTURA_CREDITO),
	DESCUENTO_INDEBIDO_CREDITO(Dependencia.INFONAVIT.nombre, "DESCUENTO INDEBIDO DE CR\u00C9DITO","4", 
			MotivoAclaracionEnum.DESCUENTO_INDEBIDO_CREDITO),

	REGISTRO_AFORE(Dependencia.AFORE.nombre, "REGISTRO EN AFORE","1",
			MotivoAclaracionEnum.REGISTRO_AFORE), 
	ACLARACION_SALDO_SUPUESTA(Dependencia.AFORE.nombre, "ACLARACI\u00D3N DE SALDO SUBCUENTA VIVIENDA","2",
			MotivoAclaracionEnum.ACLARACION_SALDO_SUPUESTA_VIVIENDA),
	OTRO(Dependencia.OTRO.nombre, "", "1", MotivoAclaracionEnum.OTRO);

	private String dependencia;
	private String mensaje;
	private String clave;
	private MotivoAclaracionEnum motivoAclaracion;

	private TiposAclaracionEnum() {
	}

	private TiposAclaracionEnum(String dependencia, String mensaje,String clave, MotivoAclaracionEnum motivoAclaracion) {
		this.dependencia = dependencia;
		this.mensaje = mensaje;
		this.clave = clave;
		this.motivoAclaracion = motivoAclaracion;
	}

	public static Map<String, String> obtenerMotivosPorDependencia(Dependencia dependencia) {
		
		Map<String, String> motivos = new LinkedHashMap<String, String>();

		for (TiposAclaracionEnum tiposAclaracionEnum : TiposAclaracionEnum
				.values()) {
			if (tiposAclaracionEnum.dependencia.equalsIgnoreCase(dependencia.nombre)) {
				motivos.put(tiposAclaracionEnum.clave,tiposAclaracionEnum.mensaje);
			}
		}
		return motivos;
	}
	
	
public static List<String> obtenerMensajesPorClaves(Dependencia dependencia,List<String>claves) {
		
		List<String> mensajes = new ArrayList<String>();

		for (TiposAclaracionEnum tiposAclaracionEnum : TiposAclaracionEnum.values()) {
			if (tiposAclaracionEnum.dependencia.equalsIgnoreCase(dependencia.nombre) && claves.contains(tiposAclaracionEnum.clave)) {
				mensajes.add(tiposAclaracionEnum.mensaje);
			}
		}
		return mensajes;
	}

	public static List<TiposAclaracionEnum> obtenerAclaracionesPorClaves(Dependencia dependencia, List<String> claves) {
		List<TiposAclaracionEnum> aclaraciones = new ArrayList<TiposAclaracionEnum>();
		for (TiposAclaracionEnum tiposAclaracionEnum : TiposAclaracionEnum.values()) {
			if (tiposAclaracionEnum.dependencia.equalsIgnoreCase(dependencia.nombre)
					&& claves.contains(tiposAclaracionEnum.clave)) {
				aclaraciones.add(tiposAclaracionEnum);
			}
		}
		return aclaraciones;
	}
	
	public static TiposAclaracionEnum obtenerTipoAclaracionEnumPorClaveMotivoAclaracion(long clave){
		for (TiposAclaracionEnum tiposAclaracionEnum : TiposAclaracionEnum.values()) {
			if (MotivoAclaracionEnum.fromId(clave).equals(tiposAclaracionEnum.getMotivoAclaracion())) {
				return tiposAclaracionEnum;
			}
		}
		return null;
	}

	public String getDependencia() {
		return dependencia;
	}

	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public MotivoAclaracionEnum getMotivoAclaracion() {
		return motivoAclaracion;
	}

	public void setMotivoAclaracion(MotivoAclaracionEnum motivoAclaracion) {
		this.motivoAclaracion = motivoAclaracion;
	}



	public enum Dependencia {
		IMSS("IMSS"), AFORE("Afore"), INFONAVIT("Infonavit"), OTRO("Otro");
		private String nombre;

		private Dependencia(String nombre) {
			this.nombre = nombre;
		}

	}

}
