package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the DIC_FRACCION database table.
 * 
 */
@Entity
@Table(name="DIC_FRACCION_CLASE")
public class DicFraccionClase implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_FRACCION_CLASE", nullable=false, precision=22)
	private long cveIdFraccionClase;
	
	//bi-directional many-to-one association to DicFraccion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_FRACCION")
	private DicFraccion dicFraccion;

	//bi-directional many-to-one association to DicClase
    @ManyToOne
	@JoinColumn(name="CVE_ID_CLASE")
	private DicClase dicClase;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_INICIO")
	private Date fecInicio;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FIN")
	private Date fecFin;

	public long getCveIdFraccionClase() {
		return cveIdFraccionClase;
	}

	public void setCveIdFraccionClase(long cveIdFraccionClase) {
		this.cveIdFraccionClase = cveIdFraccionClase;
	}

	public DicFraccion getDicFraccion() {
		return dicFraccion;
	}

	public void setDicFraccion(DicFraccion dicFraccion) {
		this.dicFraccion = dicFraccion;
	}

	public DicClase getDicClase() {
		return dicClase;
	}

	public void setDicClase(DicClase dicClase) {
		this.dicClase = dicClase;
	}

	public Date getFecInicio() {
		return fecInicio;
	}

	public void setFecInicio(Date fecInicio) {
		this.fecInicio = fecInicio;
	}

	public Date getFecFin() {
		return fecFin;
	}

	public void setFecFin(Date fecFin) {
		this.fecFin = fecFin;
	}

}
