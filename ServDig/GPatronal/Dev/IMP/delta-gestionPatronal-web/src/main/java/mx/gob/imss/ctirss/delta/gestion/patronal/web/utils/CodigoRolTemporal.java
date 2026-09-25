/**
 * delta-gestionPatronal-web30/05/2012
 * mx.gob.imss.ctirss.delta.gestion.patronal.web.utils30/05/2012
 * CodigoRolTemporal.java
 * 30/05/2012
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.patronal.web.utils;

/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Hugo Armando Martínez Chamónica
 *  @Proyecto: delta
 *  @Archivo: CodigoRolTemporal.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.patronal.web.utils
 *  @Fecha: 10:58:43
 */
public enum CodigoRolTemporal {
	TRAMITADOR(1),REPRESENTANTE_LEGAL(8), PATRON_SUJETO_OBLIGADO(2);
	
	private CodigoRolTemporal(Integer valor){
		this.codigo=valor;
	}
	private Integer codigo;
	
	public Integer getCodigo(){
		return codigo;
	}
}
