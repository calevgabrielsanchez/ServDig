package mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the ADC_LADA_NACIONAL database table.
 * 
 */
@Embeddable
@Table(name="ADC_LADA_NACIONAL")
@NamedQuery(name="AdcLadaNacional.findAll", query="SELECT a FROM AdcLadaNacional a")
public class AdcLadaNacional implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CVE_LADA")
	private BigDecimal cveLada;

	@Column(name="NOM_ESTADO")
	private String nomEstado;

	@Column(name="NOM_LOCALIDAD")
	private String nomLocalidad;

	@Column(name="NOM_MUNICIPIO")
	private String nomMunicipio;

	public AdcLadaNacional() {
	}

	public BigDecimal getCveLada() {
		return this.cveLada;
	}

	public void setCveLada(BigDecimal cveLada) {
		this.cveLada = cveLada;
	}

	public String getNomEstado() {
		return this.nomEstado;
	}

	public void setNomEstado(String nomEstado) {
		this.nomEstado = nomEstado;
	}

	public String getNomLocalidad() {
		return this.nomLocalidad;
	}

	public void setNomLocalidad(String nomLocalidad) {
		this.nomLocalidad = nomLocalidad;
	}

	public String getNomMunicipio() {
		return this.nomMunicipio;
	}

	public void setNomMunicipio(String nomMunicipio) {
		this.nomMunicipio = nomMunicipio;
	}

}