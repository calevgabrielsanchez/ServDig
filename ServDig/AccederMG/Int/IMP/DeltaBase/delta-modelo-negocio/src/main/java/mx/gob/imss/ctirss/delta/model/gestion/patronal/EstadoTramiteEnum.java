/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.gestion.patronal;

/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Hugo Armando Mart-nez Cham-nica
 *  @Proyecto: delta
 *  @Archivo: EstadoTramiteEnum.java
 *  @Paquete: mx.gob.imss.ctirss.delta.model.gestion.patronal
 *  @Fecha: 11:11:58
 */
public enum EstadoTramiteEnum {
	INICIADO(1), CERRADO(2), EN_ESPERA_AUTORIZACION(3), EN_ESPERA_TRAMITADOR(4), EN_ESPERA_DERECHOHABIENTE(5);
	
	private EstadoTramiteEnum(Integer valor){
		this.codigo=valor;
	}
	private Integer codigo;
	
	public Integer getValor(){
		return codigo;
	}
	
	
}
