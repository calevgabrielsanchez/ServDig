/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Héctor Lara Andrés
 *  @Proyecto: delta
 *  @Archivo:Articulo155.java
 *  @Paquete:mx.gob.imss.ctirss.delta.model.clasificacion
 *  @Fecha:17/08/2012
 */
package mx.gob.imss.ctirss.delta.model.clasificacion;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class Articulo155 extends AbstractModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3055917531928975869L;
	private Long cveIdArticulo155;
	private String desFraccion;
	private String desInciso;
	private Long cveIdSubdelegacion;
	
	public Long getCveIdArticulo155() {
		return cveIdArticulo155;
	}
	public void setCveIdArticulo155(Long cveIdArticulo155) {
		this.cveIdArticulo155 = cveIdArticulo155;
	}
	public String getDesFraccion() {
		return desFraccion;
	}
	public void setDesFraccion(String desFraccion) {
		this.desFraccion = desFraccion;
	}
	public String getDesInciso() {
		return desInciso;
	}
	public void setDesInciso(String desInciso) {
		this.desInciso = desInciso;
	}
	public Long getCveIdSubdelegacion() {
		return cveIdSubdelegacion;
	}
	public void setCveIdSubdelegacion(Long cveIdSubdelegacion) {
		this.cveIdSubdelegacion = cveIdSubdelegacion;
	}
	
	@Override
	public String toString() {
		return "Articulo155 [cveIdArticulo155=" + cveIdArticulo155
				+ ", desFraccion=" + desFraccion + ", desInciso=" + desInciso
				+ ", cveIdSubdelegacion=" + cveIdSubdelegacion + "]";
	}
}
