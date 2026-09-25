package mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Turno implements Serializable {

	private static final long serialVersionUID = -6719103723232685652L;
		protected Long idTurno;
	    protected String descripcion;

	    public Turno() {
	    	
	    }
	    
	    public Turno(Long idTurno) {
	    	this.idTurno = idTurno;
	    }
	    
	    public Turno(String descripcion) {
	    	this.descripcion = descripcion;
	    }
	    
	    public Turno(Long idTurno, String descripcion) {
	    	this.idTurno = idTurno;
	    	this.descripcion = descripcion;
	    }
	    
	    public Long getIdTurno() {
	        return idTurno;
	    }

	    public void setIdTurno(final Long idTurno) {
	        this.idTurno = idTurno;
	    }

	    public String getDescripcion() {
	        return descripcion;
	    }

	    public void setDescripcion(final String descripcion) {
	        this.descripcion = descripcion;
	    }

	
}

	
	
