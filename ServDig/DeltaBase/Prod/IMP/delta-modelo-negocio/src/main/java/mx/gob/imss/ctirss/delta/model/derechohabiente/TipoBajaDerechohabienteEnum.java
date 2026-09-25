package mx.gob.imss.ctirss.delta.model.derechohabiente;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public enum TipoBajaDerechohabienteEnum implements Serializable{

	DEFUNCION(1),
	TERMINO_CONVIVENCIA(2),
	DIVORCIO(3),
	TERMINO_CONCUBINATO(4),
	ADMINISTRATIVA(5),
	ADMIN(6),
	SUSPENCION(7),
	TERMINO_DE_UNION_CIVIL(8);
	
	private Long id;
	private final static Map<Long,TipoBajaDerechohabienteEnum> hashCodes = new HashMap<Long,TipoBajaDerechohabienteEnum>();
	
	static{ 
		for(TipoBajaDerechohabienteEnum baja : TipoBajaDerechohabienteEnum.values()){
			hashCodes.put(baja.getId(), baja);
		}
	}
	
	private TipoBajaDerechohabienteEnum(long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	public static TipoBajaDerechohabienteEnum obternerEnumById(Long codigo){
		return hashCodes.get(codigo);
	}
	
}
