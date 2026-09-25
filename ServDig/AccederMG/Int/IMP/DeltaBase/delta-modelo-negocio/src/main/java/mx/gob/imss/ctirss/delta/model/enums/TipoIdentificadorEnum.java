/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 301012
 * @author ICCSRG
 *
 */
public enum TipoIdentificadorEnum {
	
	CURP(1L), RFC(2L);
	
	
	private long codigo;
		
	private final static Map<String, TipoIdentificadorEnum> hashNames = new HashMap<String, TipoIdentificadorEnum>();
	private final static Map<Long,TipoIdentificadorEnum> hashCodes = new HashMap<Long,TipoIdentificadorEnum>();
	
	static{ 
		for(TipoIdentificadorEnum tipoIdentificador : TipoIdentificadorEnum.values()){
			hashNames.put(tipoIdentificador.name(), tipoIdentificador);
			hashCodes.put(tipoIdentificador.getCodigo(), tipoIdentificador);
		}
	}
	
	private TipoIdentificadorEnum(long valor){
		this.codigo=valor;
	}
	
	public long getCodigo() {
		return codigo;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	
	public static TipoIdentificadorEnum obtenerEnumByName(String name){
		return hashNames.get(name);
	}
	
	public static TipoIdentificadorEnum obternerEnumById(Long codigo){
		return hashCodes.get(codigo);
	}
}
