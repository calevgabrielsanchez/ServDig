/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:TitularSuplente.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.model.persona
 *  @Fecha:18/06/2012
 */
package mx.gob.imss.ctirss.delta.model.clasificacion;

import java.io.Serializable;
import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class AdjuntosClasificacion extends AbstractModel implements Serializable{
	private static final long serialVersionUID = -172501883897538177L;
	
	private String refFolio;
	private String rutaArchivo;
	private String nombreArchivo;
	private Long cveIdTipoTramite;

	private Date fecRegistroAlta;
	private Date fecRegistroBaja;
	private Date fecRegistroActualizado;
	
	@Override
	public String toString() {
		return "AdjuntosClasificacion [refFolio=" + refFolio + ", rutaArchivo="
				+ rutaArchivo + ", nombreArchivo=" + nombreArchivo
				+ ", cveIdTipoTramite=" + cveIdTipoTramite
				+ ", fecRegistroAlta=" + fecRegistroAlta + ", fecRegistroBaja="
				+ fecRegistroBaja + ", fecRegistroActualizado="
				+ fecRegistroActualizado + "]";
	}
	
	public String getRefFolio() {
		return refFolio;
	}
	public void setRefFolio(String refFolio) {
		this.refFolio = refFolio;
	}
	public String getRutaArchivo() {
		return rutaArchivo;
	}
	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	public Long getCveIdTipoTramite() {
		return cveIdTipoTramite;
	}
	public void setCveIdTipoTramite(Long cveIdTipoTramite) {
		this.cveIdTipoTramite = cveIdTipoTramite;
	}
	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}
	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}
	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}
	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}
	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}
	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

}
