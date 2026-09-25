package mx.gob.imss.cit.gestion.solicitud.flujo.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DIC_EDO_TAREA")
public class EstadoTarea implements Serializable {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4060123094526886638L;

	@Id
	@Column(name = "CVE_ID_EDO_TAREA")
	private long idEstadoTarea;

	@Column(name = "NOM_EDO_TAREA")
	private String nombre;

	@Column(name = "DES_EDO_TAREA")
	private String descripcion;	
	
	public EstadoTarea() {
		super();
	}

	public EstadoTarea(long idEstadoTarea) {
		super();
		this.idEstadoTarea = idEstadoTarea;
	}

	public long getIdEstadoTarea() {
		return idEstadoTarea;
	}

	public void setIdEstadoTarea(long idEstadoTarea) {
		this.idEstadoTarea = idEstadoTarea;
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
