package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CFT_ERROR_CARGA database table.
 * 
 */
@Entity
@Table(name="CFT_ERROR_CARGA")
public class CftErrorCarga implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ERROR_CARGA", nullable=false, precision=22)
	private long cveErrorCarga;

	@Column(name="CVE_DELEG_ORIG", precision=2)
	private BigDecimal cveDelegOrig;

	@Column(name="CVE_TPOFISCALIZA", precision=22)
	private BigDecimal cveTpofiscaliza;

	@Column(name="DESC_MENSAJE_ERROR", length=200)
	private String descMensajeError;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_CARGA")
	private Date fecCarga;

	@Column(name="NOM_ARCHIVO", length=100)
	private String nomArchivo;

	@Column(name="NUM_EXPEDIENTE", length=25)
	private String numExpediente;

	@Column(name="REG_PATRONAL_INS", length=11)
	private String regPatronalIns;

	@Column(name="SDELEG_ORIG", precision=2)
	private BigDecimal sdelegOrig;

    public CftErrorCarga() {
    }

	public long getCveErrorCarga() {
		return this.cveErrorCarga;
	}

	public void setCveErrorCarga(long cveErrorCarga) {
		this.cveErrorCarga = cveErrorCarga;
	}

	public BigDecimal getCveDelegOrig() {
		return this.cveDelegOrig;
	}

	public void setCveDelegOrig(BigDecimal cveDelegOrig) {
		this.cveDelegOrig = cveDelegOrig;
	}

	public BigDecimal getCveTpofiscaliza() {
		return this.cveTpofiscaliza;
	}

	public void setCveTpofiscaliza(BigDecimal cveTpofiscaliza) {
		this.cveTpofiscaliza = cveTpofiscaliza;
	}

	public String getDescMensajeError() {
		return this.descMensajeError;
	}

	public void setDescMensajeError(String descMensajeError) {
		this.descMensajeError = descMensajeError;
	}

	public Date getFecCarga() {
		return this.fecCarga;
	}

	public void setFecCarga(Date fecCarga) {
		this.fecCarga = fecCarga;
	}

	public String getNomArchivo() {
		return this.nomArchivo;
	}

	public void setNomArchivo(String nomArchivo) {
		this.nomArchivo = nomArchivo;
	}

	public String getNumExpediente() {
		return this.numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getRegPatronalIns() {
		return this.regPatronalIns;
	}

	public void setRegPatronalIns(String regPatronalIns) {
		this.regPatronalIns = regPatronalIns;
	}

	public BigDecimal getSdelegOrig() {
		return this.sdelegOrig;
	}

	public void setSdelegOrig(BigDecimal sdelegOrig) {
		this.sdelegOrig = sdelegOrig;
	}

}