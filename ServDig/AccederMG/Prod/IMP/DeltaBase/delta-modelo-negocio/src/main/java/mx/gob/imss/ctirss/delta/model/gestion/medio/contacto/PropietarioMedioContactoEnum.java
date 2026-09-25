package mx.gob.imss.ctirss.delta.model.gestion.medio.contacto;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Hugo Martinez
 *
 */
public enum PropietarioMedioContactoEnum {
	PERSONA_FISICA(1), PERSONA_MORAL(2), DERECHOHABIENTE(3), SOCIO(4), 
	REPRESENTANTE_LEGAL(5), CENTRO_TRABAJO(6);
	
	private Integer codigo;
	private final static Map<String, PropietarioMedioContactoEnum> hashNames = new HashMap<String, PropietarioMedioContactoEnum>();
	private final static Map<Integer,PropietarioMedioContactoEnum> hashCodes = new HashMap<Integer,PropietarioMedioContactoEnum>();
	
	static{ 
		for(PropietarioMedioContactoEnum propietario : PropietarioMedioContactoEnum.values()){
			hashNames.put(propietario.name(), propietario);
			hashCodes.put(propietario.getCodigo(), propietario);
		}
	}
	
	private PropietarioMedioContactoEnum(Integer codigo){
		this.codigo=codigo;
		
	}
	
	
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public static PropietarioMedioContactoEnum obtenerEnumByName(String name){
		return hashNames.get(name);
	}
	
	public static PropietarioMedioContactoEnum obternerEnumById(Integer codigo){
		return hashCodes.get(codigo);
	}

	
	
}
