package mx.gob.imss.ctirss.delta.model.gestion.medio.contacto;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Hugo Martinez
 *
 */
public enum TipoContactoEnum {
	CORREO_ELECTRONICO(1),TELEFONO_FIJO(2),TELEFONO_MOVIL(3), FACEBOOK(4), TWITTER(5);
	
	private Integer codigo;
	
	private final static Map<String, TipoContactoEnum> hashNames = new HashMap<String, TipoContactoEnum>();
	private final static Map<Integer,TipoContactoEnum> hashCodes = new HashMap<Integer,TipoContactoEnum>();
	
	static{ 
		for(TipoContactoEnum propietario : TipoContactoEnum.values()){
			hashNames.put(propietario.name(), propietario);
			hashCodes.put(propietario.getCodigo(), propietario);
		}
	}
	
	private TipoContactoEnum(Integer codigo){
		this.codigo=codigo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	
	public static TipoContactoEnum obtenerEnumByName(String name){
		return hashNames.get(name);
	}
	
	public static TipoContactoEnum obternerEnumById(Integer codigo){
		return hashCodes.get(codigo);
	}

	
}
