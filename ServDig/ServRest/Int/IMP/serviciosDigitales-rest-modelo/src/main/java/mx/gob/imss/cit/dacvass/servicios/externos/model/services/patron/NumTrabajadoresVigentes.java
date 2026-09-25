package mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NumTrabajadoresVigentes implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4497010977699546732L;
	private BigDecimal numTrabajadoresPermanentes;
	private BigDecimal numTrabajadoresEventuales;
	private BigDecimal numTrabajadoresConstruccion;
	private BigDecimal numTrabajadoresMexExtranjero;
	private BigDecimal numRefAdicionlesPen;
	private BigDecimal numTotalTrabajadoresVigentes;
	
	public BigDecimal getNumTrabajadoresPermanentes() {
		return numTrabajadoresPermanentes;
	}
	public void setNumTrabajadoresPermanentes(BigDecimal numTrabajadoresPermanentes) {
		this.numTrabajadoresPermanentes = numTrabajadoresPermanentes;
	}
	public BigDecimal getNumTrabajadoresEventuales() {
		return numTrabajadoresEventuales;
	}
	public void setNumTrabajadoresEventuales(BigDecimal numTrabajadoresEventuales) {
		this.numTrabajadoresEventuales = numTrabajadoresEventuales;
	}
	public BigDecimal getNumTrabajadoresConstruccion() {
		return numTrabajadoresConstruccion;
	}
	public void setNumTrabajadoresConstruccion(BigDecimal numTrabajadoresConstruccion) {
		this.numTrabajadoresConstruccion = numTrabajadoresConstruccion;
	}
	public BigDecimal getNumTrabajadoresMexExtranjero() {
		return numTrabajadoresMexExtranjero;
	}
	public void setNumTrabajadoresMexExtranjero(BigDecimal numTrabajadoresMexExtranjero) {
		this.numTrabajadoresMexExtranjero = numTrabajadoresMexExtranjero;
	}
	public BigDecimal getNumTotalTrabajadoresVigentes() {
		return numTotalTrabajadoresVigentes;
	}
	public void setNumTotalTrabajadoresVigentes(BigDecimal numTotalTrabajadoresVigentes) {
		this.numTotalTrabajadoresVigentes = numTotalTrabajadoresVigentes;
	}
	public BigDecimal getNumRefAdicionlesPen() {
		return numRefAdicionlesPen;
	}
	public void setNumRefAdicionlesPen(BigDecimal numRefAdicionlesPen) {
		this.numRefAdicionlesPen = numRefAdicionlesPen;
	}
	

}
