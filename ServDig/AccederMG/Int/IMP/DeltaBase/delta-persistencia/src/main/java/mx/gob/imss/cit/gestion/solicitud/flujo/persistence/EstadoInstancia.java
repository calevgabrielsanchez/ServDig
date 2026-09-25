package mx.gob.imss.cit.gestion.solicitud.flujo.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DIC_EDO_INSTANCIA")
public class EstadoInstancia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5835460303833205434L;

	@Id
	@Column(name = "CVE_ID_EDO_INSTANCIA")
	private long idEstadoInstancia;

	@Column(name = "NOM_EDO_INSTANCIA")
	private String nombre;

	@Column(name = "DES_EDO_INSTANCIA")
	private String descripcion;	
	
	public EstadoInstancia() {
		super();
	}

	public EstadoInstancia(long idEstadoInstancia) {
		super();
		this.idEstadoInstancia = idEstadoInstancia;
	}

	public long getIdEstadoInstancia() {
		return idEstadoInstancia;
	}

	public void setIdEstadoInstancia(long idEstadoInstancia) {
		this.idEstadoInstancia = idEstadoInstancia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
