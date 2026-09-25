package mx.gob.imss.ctirss.delta.model.asegurado.cda;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetalleNss {
	
	private String nss;
	private String correccionesNss;
	
	private List<DetalleCorreccionNss> detalleCoreeccion;
	private Map<String, DetalleCorreccionNss> detalleAsociado;
	
	public String getNss() {
		return nss;
	}
	public void setNss(String nss) {
		this.nss = nss;
	}
	public String getCorreccionesNss() {
		return correccionesNss;
	}
	public void setCorreccionesNss(String correccionesNss) {
		this.correccionesNss = correccionesNss;
	}
	public List<DetalleCorreccionNss> getDetalleCoreeccion() {
		return detalleCoreeccion;
	}
	public void setDetalleCoreeccion(List<DetalleCorreccionNss> detalleCoreeccion) {
		this.detalleCoreeccion = detalleCoreeccion;
	}
	public Map<String, DetalleCorreccionNss> getDetalleAsociado() {
		return detalleAsociado;
	}
	public void setDetalleAsociado(
			Map<String,DetalleCorreccionNss> detalleAsociado) {
		this.detalleAsociado = detalleAsociado;
	}
	
	
	
	
	
	
	

}
