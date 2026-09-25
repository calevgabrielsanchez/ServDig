package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the DIC_CLASE database table.
 * 
 */
@Entity
@Table(name="DIC_CLASE")
@OnSearchLlavePrimaria(atributos="cveIdClase")
@ComponentComboCampoDescripcion(atributo="desClase")
public class DicClase implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_CLASE")
	private long cveIdClase;

	@Column(name="DES_CLASE")
	private String desClase;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NUM_PRIMA_MEDIA")
	private BigDecimal numPrimaMedia;

	//bi-directional many-to-one association to DicClasePrima
	@OneToMany(mappedBy="dicClase")
	private Set<DicClasePrima> dicClasePrimas;

	//bi-directional many-to-one association to DicFraccion
//	@OneToMany(mappedBy="dicClase")
//	private Set<DicFraccion> dicFraccions;

    public DicClase() {
    }

	public long getCveIdClase() {
		return this.cveIdClase;
	}

	public void setCveIdClase(long cveIdClase) {
		this.cveIdClase = cveIdClase;
	}

	public String getDesClase() {
		return this.desClase;
	}

	public void setDesClase(String desClase) {
		this.desClase = desClase;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return this.fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public BigDecimal getNumPrimaMedia() {
		return this.numPrimaMedia;
	}

	public void setNumPrimaMedia(BigDecimal numPrimaMedia) {
		this.numPrimaMedia = numPrimaMedia;
	}

	public Set<DicClasePrima> getDicClasePrimas() {
		return this.dicClasePrimas;
	}

	public void setDicClasePrimas(Set<DicClasePrima> dicClasePrimas) {
		this.dicClasePrimas = dicClasePrimas;
	}
	
//	public Set<DicFraccion> getDicFraccions() {
//		return this.dicFraccions;
//	}
//
//	public void setDicFraccions(Set<DicFraccion> dicFraccions) {
//		this.dicFraccions = dicFraccions;
//	}
	
}