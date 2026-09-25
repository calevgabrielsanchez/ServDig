package mx.gob.imss.ctirss.delta.model.firma;

import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class RespuestaGuardadoArchivosFirma extends AbstractModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4947936199763409122L;
	String tramite;
	Integer etapa;
	String fecha;
	List<Archivo> archivos;
	
	public String getTramite() {
		return tramite;
	}
	
	public void setTramite(String tramite) {
		this.tramite = tramite;
	}
	
	public Integer getEtapa() {
		return etapa;
	}
	
	public void setEtapa(Integer etapa) {
		this.etapa = etapa;
	}
	
	public String getFecha() {
		return fecha;
	}
	
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public List<Archivo> getArchivos() {
		return archivos;
	}
	
	public void setArchivos(List<Archivo> archivos) {
		this.archivos = archivos;
	}
}
