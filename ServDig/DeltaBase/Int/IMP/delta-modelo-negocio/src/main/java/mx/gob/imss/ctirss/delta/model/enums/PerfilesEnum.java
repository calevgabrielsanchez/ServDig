package mx.gob.imss.ctirss.delta.model.enums;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum PerfilesEnum implements Serializable{
	
	TRAMITADOR(new Long(1),"TRAMITADOR"),
	PATRON(new Long(2), "PATRON"),
	ASEGURADO(new Long(3), "ASEGURADO"),
	AUTORIZADOR(new Long(4), "AUTORIZADOR"),
	PENSIONADO(new Long(5), "PENSIONADO"),
	CONYUGE(new Long(6), "CONYUGE"),
	DESCENDIENTE(new Long(7), "DESCENDIENTE"),
	JEFE_DEPTO_AFIL_VIGENCIA (new Long(8), "JEFE DE DEPARTAMENTO AFILIACION VIGENCIA"),
	JEFE_OFICINA_VIGENCIA (new Long(9), "JEFE DE OFICINA DE VIGENCIA DE DERECHOS"),
	ADMINISTRACION_PRIMA_SEGURO_RIESGOS_TRABAJO (new Long(10), "TITULAR DE LA DIVISION DE ADMINISTRACION DE LA PRIMA DEL SEGURO DE RIESGOS DE TRABAJO"),
	NORMATIVO_CE (new Long(11), "NORMATIVO CE"),
	TITULAR_DE_LA_SUBJEFATURA_DE_DIVISION_DE_PRESTACIONES_EN_ESPECIE(new Long(12), "TITULAR DE LA SUBJEFATURA DE DIVISION DE PRESTACIONES EN ESPECIE"),
	JEFE_DE_DEPARTAMENTO_DE_SUPERVISION_DE_AFILIACION_Y_VIGENCIA(new Long(13), "JEFE DE DEPARTAMENTO DE SUPERVISION DE AFILIACION VIGENCIA"),
	JEFE_DE_DEPARTAMENTO_VIGENCIA(new Long(14), "JEFE DE DEPARTAMENTO VIGENCIA"),
	CONSULTA_VIGENCIA(new Long(15), "CONSULTA DE VIGENCIA");
	
	private final static Map<String, PerfilesEnum> hashNames = new HashMap<String, PerfilesEnum>();
	
	static{ 
		for(PerfilesEnum perfil : PerfilesEnum.values()){
			hashNames.put(perfil.name(), perfil);
			
		}
	}
	
	private Long id;
	private String desc;
	
	PerfilesEnum (Long id, String desc) {
		this.id=id;
		this.desc = desc;
	}
	
	public Long getId(){
		return this.id;
	}
	
	public String getDesc(){
		return this.desc;
	}
	
	public static PerfilesEnum obtenerEnumByName(String name){
		return hashNames.get(name);
	}
}
