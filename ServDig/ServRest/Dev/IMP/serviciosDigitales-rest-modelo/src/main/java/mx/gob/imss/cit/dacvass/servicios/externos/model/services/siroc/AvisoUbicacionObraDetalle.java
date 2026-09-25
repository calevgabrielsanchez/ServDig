package mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AvisoUbicacionObraDetalle extends AvisoUbicacionObra implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8669177117355405596L;
	
	private BigDecimal numeroExterior2;
	private BigDecimal numeroInterior;
	private String numeroInteriorAlfa;
	private String descripcionUbicacion;
	
	
	
	
	
	
	public BigDecimal getNumeroExterior2() {
		return numeroExterior2;
	}
	public void setNumeroExterior2(BigDecimal numeroExterior2) {
		this.numeroExterior2 = numeroExterior2;
	}
	public BigDecimal getNumeroInterior() {
		return numeroInterior;
	}
	public void setNumeroInterior(BigDecimal numeroInterior) {
		this.numeroInterior = numeroInterior;
	}
	public String getNumeroInteriorAlfa() {
		return numeroInteriorAlfa;
	}
	public void setNumeroInteriorAlfa(String numeroInteriorAlfa) {
		this.numeroInteriorAlfa = numeroInteriorAlfa;
	}
	
	public String getDescripcionUbicacion() {
		return descripcionUbicacion;
	}
	public void setDescripcionUbicacion(String descripcionUbicacion) {
		this.descripcionUbicacion = descripcionUbicacion;
	}
	
	
	

}
