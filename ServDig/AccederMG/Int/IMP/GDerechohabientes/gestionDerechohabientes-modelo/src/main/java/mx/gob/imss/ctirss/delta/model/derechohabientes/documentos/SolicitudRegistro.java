/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.derechohabientes.documentos;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ghdolores
 * 
 */
public class SolicitudRegistro implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4882663494537086193L;
	private String folio;
	private String fecha;
	private String nss;
	private String nombre;
	// Datos de la cita
	private String delegacion;
	private String subdelegacion;
	private String umf;
	private String fechaCita;
	private String documentos;
	private String hora;
	//Para el reportes de rechazo
	private String tipoRechazo;
	private String parentesco;
	
	
	
	public String getTipoRechazo() {
		return tipoRechazo;
	}

	public void setTipoRechazo(String tipoRechazo) {
		this.tipoRechazo = tipoRechazo;
	}

	public String getParentesco() {
		return parentesco;
	}

	public void setParentesco(String parentesco) {
		this.parentesco = parentesco;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getDocumentos() {
		return documentos;
	}

	public void setDocumentos(String documentos) {
		this.documentos = documentos;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}



	public String getNss() {
		return nss;
	}

	public void setNss(String nss) {
		this.nss = nss;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDelegacion() {
		return delegacion;
	}

	public void setDelegacion(String delegacion) {
		this.delegacion = delegacion;
	}

	public String getSubdelegacion() {
		return subdelegacion;
	}

	public void setSubdelegacion(String subdelegacion) {
		this.subdelegacion = subdelegacion;
	}

	public String getUmf() {
		return umf;
	}

	public void setUmf(String umf) {
		this.umf = umf;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getFechaCita() {
		return fechaCita;
	}

	public void setFechaCita(String fechaCita) {
		this.fechaCita = fechaCita;
	}

	
}
