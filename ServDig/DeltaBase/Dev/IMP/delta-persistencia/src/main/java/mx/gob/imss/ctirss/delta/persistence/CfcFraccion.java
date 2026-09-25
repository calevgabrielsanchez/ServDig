package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the CFC_FRACCION database table.
 * 
 */
@Entity
@Table(name="CFC_FRACCION")
public class CfcFraccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_FRACCION", nullable=false, precision=22)
	private long cveFraccion;

	@Column(name="CVE_CLASE", length=2)
	private String cveClase;

	@Column(name="CVE_CODIGOACT", length=3)
	private String cveCodigoact;

	@Column(name="CVE_CODIGODIV", length=2)
	private String cveCodigodiv;

	@Column(name="CVE_CODIGOGRU", length=2)
	private String cveCodigogru;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHAALTA")
	private Date fecFechaalta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHABAJA")
	private Date fecFechabaja;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHAFIN")
	private Date fecFechafin;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHAINI")
	private Date fecFechaini;

	//bi-directional many-to-one association to CfcActeconomica
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ACTECONOMICA")
	private CfcActeconomica cfcActeconomica;

    public CfcFraccion() {
    }

	public long getCveFraccion() {
		return this.cveFraccion;
	}

	public void setCveFraccion(long cveFraccion) {
		this.cveFraccion = cveFraccion;
	}

	public String getCveClase() {
		return this.cveClase;
	}

	public void setCveClase(String cveClase) {
		this.cveClase = cveClase;
	}

	public String getCveCodigoact() {
		return this.cveCodigoact;
	}

	public void setCveCodigoact(String cveCodigoact) {
		this.cveCodigoact = cveCodigoact;
	}

	public String getCveCodigodiv() {
		return this.cveCodigodiv;
	}

	public void setCveCodigodiv(String cveCodigodiv) {
		this.cveCodigodiv = cveCodigodiv;
	}

	public String getCveCodigogru() {
		return this.cveCodigogru;
	}

	public void setCveCodigogru(String cveCodigogru) {
		this.cveCodigogru = cveCodigogru;
	}

	public Date getFecFechaalta() {
		return this.fecFechaalta;
	}

	public void setFecFechaalta(Date fecFechaalta) {
		this.fecFechaalta = fecFechaalta;
	}

	public Date getFecFechabaja() {
		return this.fecFechabaja;
	}

	public void setFecFechabaja(Date fecFechabaja) {
		this.fecFechabaja = fecFechabaja;
	}

	public Date getFecFechafin() {
		return this.fecFechafin;
	}

	public void setFecFechafin(Date fecFechafin) {
		this.fecFechafin = fecFechafin;
	}

	public Date getFecFechaini() {
		return this.fecFechaini;
	}

	public void setFecFechaini(Date fecFechaini) {
		this.fecFechaini = fecFechaini;
	}

	public CfcActeconomica getCfcActeconomica() {
		return this.cfcActeconomica;
	}

	public void setCfcActeconomica(CfcActeconomica cfcActeconomica) {
		this.cfcActeconomica = cfcActeconomica;
	}
	
}