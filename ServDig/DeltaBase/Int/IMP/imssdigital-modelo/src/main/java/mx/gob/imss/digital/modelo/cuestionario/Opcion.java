package mx.gob.imss.digital.modelo.cuestionario;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import mx.gob.imss.digital.modelo.interfaces.MensajeError;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "opcion", namespace = "http://mx.gob.imss.digital.modelo.cuestionario")
@XmlRootElement(name = "opcion", namespace = "http://mx.gob.imss.digital.modelo.cuestionario")
public class Opcion implements Serializable, MensajeError {
	
    /**
     * Serial version UID
     */
	private static final long serialVersionUID = 1L;

	private int clave;	
	private String descripcion;
	private int valor;
	private boolean habilitarDependencia;
	private String errorFormGeneral;

	public int getClave() {
		return clave;
	}

	public void setClave(int clave) {
		this.clave = clave;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public boolean isHabilitarDependencia() {
		return habilitarDependencia;
	}

	public void setHabilitarDependencia(boolean habilitarDependencia) {
		this.habilitarDependencia = habilitarDependencia;
	}

	@Override
	public String getErrorFormGeneral() {
		return errorFormGeneral;
	}

	@Override
	public void setErrorFormGeneral(String errorFormGeneral) {
		this.errorFormGeneral = errorFormGeneral;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.clave;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Opcion other = (Opcion) obj;
        if (this.clave != other.clave)
            return false;
        return true;
    }
}
