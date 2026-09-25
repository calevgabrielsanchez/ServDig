package mx.gob.imss.ctirss.correccion.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.base.model.AbstractCrtDetBaseCotOmitida;

@Entity
@Table(name="CRT_DETBASECOT_OMITIDA")
public class CrtDetBaseCotOmitida extends AbstractCrtDetBaseCotOmitida{

	

	@Transient
	public String RazonSocial;
	
	@Transient
	public boolean isCedulaIVacia;
	
	@Transient
	public String folioCorreccion;
	@Transient
	public String ejercicio;
	
	
	
	@Transient
	public String getRazonSocial() {
		return RazonSocial;
	}
	
	public void setRazonSocial(String razonSocial) {
		RazonSocial = razonSocial;
	}
	
	@Transient
	public String getFolioCorreccion() {
		return folioCorreccion;
	}
	
	public void setFolioCorreccion(String folioCorreccion) {
		this.folioCorreccion = folioCorreccion;
	}
	
	@Transient
	public String getEjercicio() {
		return ejercicio;
	}
	
	public void setEjercicio(String ejercicio) {
		this.ejercicio = ejercicio;
	}
	
	
	@Transient
	public boolean isCedulaIVacia() {
		return isCedulaIVacia;
	}
	@Transient
	public void setCedulaIVacia(boolean isCedulaIVacia) {
		this.isCedulaIVacia = isCedulaIVacia;
	}





	
}
