/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.ctirss.delta.model.clasificacion;

import java.util.Arrays;

/**
 *
 * @author JGuerra
 */
public enum TipoRegistroEnum {
    
	TODOS("-1","TODOS") ,
	ARP("0","ARP") ,
	RPC("1","RPC"),
	PSP("2","PSP");
	
	private final String clave;
	private final String descripcion;
	
	
	
	private TipoRegistroEnum(String clave, String descripcion) {
		this.clave = clave;
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getClave() {
		return clave;
	}

	/**
	 * Obtiene un enum en base a su clave.
	 * @param clave
	 * @return
	 */
	public static TipoRegistroEnum getEnumByClave(String clave){
		TipoRegistroEnum enumEncontrado = null;
		for (TipoRegistroEnum tp: Arrays.asList(TipoRegistroEnum.class.getEnumConstants())){
			if (tp.getClave().compareTo(clave)==0) {
				enumEncontrado = tp;
			}
		}
		return enumEncontrado;
	}
	    
}
