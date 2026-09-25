package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the CFC_DIVISION database table.
 * 
 */
@Entity
@Table(name="CFC_DIVISION")
public class CfcDivision implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_DIVISION", nullable=false, precision=22)
	private long cveDivision;

	@Column(name="CVE_CODIGODIV", length=2)
	private String cveCodigodiv;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHAALTA")
	private Date fecFechaalta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHABAJA")
	private Date fecFechabaja;

	@Column(name="TX_DIVISION", length=200)
	private String txDivision;

	//bi-directional many-to-one association to CfcGrupo
	@OneToMany(mappedBy="cfcDivision")
	private List<CfcGrupo> cfcGrupos;

    public CfcDivision() {
    }

	public long getCveDivision() {
		return this.cveDivision;
	}

	public void setCveDivision(long cveDivision) {
		this.cveDivision = cveDivision;
	}

	public String getCveCodigodiv() {
		return this.cveCodigodiv;
	}

	public void setCveCodigodiv(String cveCodigodiv) {
		this.cveCodigodiv = cveCodigodiv;
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

	public String getTxDivision() {
		return this.txDivision;
	}

	public void setTxDivision(String txDivision) {
		this.txDivision = txDivision;
	}

	public List<CfcGrupo> getCfcGrupos() {
		return this.cfcGrupos;
	}

	public void setCfcGrupos(List<CfcGrupo> cfcGrupos) {
		this.cfcGrupos = cfcGrupos;
	}
	
}