package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the CFC_ACTECONOMICA database table.
 * 
 */
@Entity
@Table(name="CFC_ACTECONOMICA")
public class CfcActeconomica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ACTECONOMICA", nullable=false, precision=22)
	private long cveActeconomica;

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

	@Column(name="TX_ACTIVIDAD", length=200)
	private String txActividad;

	//bi-directional many-to-one association to CfcGrupo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_GRUPO")
	private CfcGrupo cfcGrupo;

	//bi-directional many-to-one association to CfcFraccion
	@OneToMany(mappedBy="cfcActeconomica")
	private List<CfcFraccion> cfcFraccions;

	//bi-directional many-to-one association to FdtAnexo5
	@OneToMany(mappedBy="cfcActeconomica")
	private List<FdtAnexo5> fdtAnexo5s;

    public CfcActeconomica() {
    }

	public long getCveActeconomica() {
		return this.cveActeconomica;
	}

	public void setCveActeconomica(long cveActeconomica) {
		this.cveActeconomica = cveActeconomica;
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

	public String getTxActividad() {
		return this.txActividad;
	}

	public void setTxActividad(String txActividad) {
		this.txActividad = txActividad;
	}

	public CfcGrupo getCfcGrupo() {
		return this.cfcGrupo;
	}

	public void setCfcGrupo(CfcGrupo cfcGrupo) {
		this.cfcGrupo = cfcGrupo;
	}
	
	public List<CfcFraccion> getCfcFraccions() {
		return this.cfcFraccions;
	}

	public void setCfcFraccions(List<CfcFraccion> cfcFraccions) {
		this.cfcFraccions = cfcFraccions;
	}
	
	public List<FdtAnexo5> getFdtAnexo5s() {
		return this.fdtAnexo5s;
	}

	public void setFdtAnexo5s(List<FdtAnexo5> fdtAnexo5s) {
		this.fdtAnexo5s = fdtAnexo5s;
	}
	
}