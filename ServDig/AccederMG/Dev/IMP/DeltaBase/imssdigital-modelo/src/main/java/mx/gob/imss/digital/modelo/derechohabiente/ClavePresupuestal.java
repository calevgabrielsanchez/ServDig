package mx.gob.imss.digital.modelo.derechohabiente;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "clavePresupuestal", namespace = "mx.gob.imss.digital.modelo.derechohabiente")
@XmlRootElement(name = "clavePresupuestal", namespace = "mx.gob.imss.digital.modelo.derechohabiente")
public class ClavePresupuestal implements Serializable {
	private static final long serialVersionUID = 1L;
	private long idClavePresupuestal;
	private String descripcion;
	private String clavePresupuestalRecortada;
	private String clavePresupuestal;

	public long getIdClavePresupuestal() {
		return idClavePresupuestal;
	}

	public void setIdClavePresupuestal(long idClavePresupuestal) {
		this.idClavePresupuestal = idClavePresupuestal;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getClavePresupuestalRecortada() {
		return clavePresupuestalRecortada;
	}

	public void setClavePresupuestalRecortada(String clavePresupuestalRecortada) {
		this.clavePresupuestalRecortada = clavePresupuestalRecortada;
	}

	public String getClavePresupuestal() {
		return clavePresupuestal;
	}

	public void setClavePresupuestal(String clavePresupuestal) {
		this.clavePresupuestal = clavePresupuestal;
	}

}
