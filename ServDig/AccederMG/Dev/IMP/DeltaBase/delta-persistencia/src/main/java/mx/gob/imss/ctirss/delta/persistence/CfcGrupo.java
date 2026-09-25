package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the CFC_GRUPO database table.
 * 
 */
@Entity
@Table(name="CFC_GRUPO")
public class CfcGrupo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_GRUPO", nullable=false, precision=22)
	private long cveGrupo;

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

	@Column(name="TX_GRUPO", length=200)
	private String txGrupo;

	//bi-directional many-to-one association to CfcActeconomica
	@OneToMany(mappedBy="cfcGrupo")
	private List<CfcActeconomica> cfcActeconomicas;

	//bi-directional many-to-one association to CfcDivision
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_DIVISION")
	private CfcDivision cfcDivision;

    public CfcGrupo() {
    }

	public long getCveGrupo() {
		return this.cveGrupo;
	}

	public void setCveGrupo(long cveGrupo) {
		this.cveGrupo = cveGrupo;
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

	public String getTxGrupo() {
		return this.txGrupo;
	}

	public void setTxGrupo(String txGrupo) {
		this.txGrupo = txGrupo;
	}

	public List<CfcActeconomica> getCfcActeconomicas() {
		return this.cfcActeconomicas;
	}

	public void setCfcActeconomicas(List<CfcActeconomica> cfcActeconomicas) {
		this.cfcActeconomicas = cfcActeconomicas;
	}
	
	public CfcDivision getCfcDivision() {
		return this.cfcDivision;
	}

	public void setCfcDivision(CfcDivision cfcDivision) {
		this.cfcDivision = cfcDivision;
	}
	
}