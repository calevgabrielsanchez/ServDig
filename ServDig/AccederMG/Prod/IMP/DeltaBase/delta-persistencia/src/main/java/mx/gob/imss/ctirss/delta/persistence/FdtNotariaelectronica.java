package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the FDT_NOTARIAELECTRONICA database table.
 * 
 */
@Entity
@Table(name="FDT_NOTARIAELECTRONICA")
public class FdtNotariaelectronica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_NOTARIA", nullable=false, precision=63)
	private double cveNotaria;

	@Column(name="CVE_CURPCP", length=18)
	private String cveCurpcp;

	@Column(name="CVE_PK_AVISO", precision=22)
	private BigDecimal cvePkAviso;

	@Column(name="CVE_PK_REGISTROTABLA", precision=22)
	private BigDecimal cvePkRegistrotabla;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FIRMADOCTO")
	private Date fecFirmadocto;

	@Column(name="IND_PERSONAFIRMA", precision=22)
	private BigDecimal indPersonafirma;

    @Lob()
	@Column(name="OBJ_DOCTOFIRMADO")
	private byte[] objDoctofirmado;

    @Lob()
	@Column(name="TX_CADENAORIGINAL")
	private byte[] txCadenaoriginal;

    @Lob()
	@Column(name="TX_SELLO")
	private byte[] txSello;

	//bi-directional many-to-one association to FdcTpoproceso
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TPOPROCESO")
	private FdcTpoproceso fdcTpoproceso;

    public FdtNotariaelectronica() {
    }

	public double getCveNotaria() {
		return this.cveNotaria;
	}

	public void setCveNotaria(double cveNotaria) {
		this.cveNotaria = cveNotaria;
	}

	public String getCveCurpcp() {
		return this.cveCurpcp;
	}

	public void setCveCurpcp(String cveCurpcp) {
		this.cveCurpcp = cveCurpcp;
	}

	public BigDecimal getCvePkAviso() {
		return this.cvePkAviso;
	}

	public void setCvePkAviso(BigDecimal cvePkAviso) {
		this.cvePkAviso = cvePkAviso;
	}

	public BigDecimal getCvePkRegistrotabla() {
		return this.cvePkRegistrotabla;
	}

	public void setCvePkRegistrotabla(BigDecimal cvePkRegistrotabla) {
		this.cvePkRegistrotabla = cvePkRegistrotabla;
	}

	public Date getFecFirmadocto() {
		return this.fecFirmadocto;
	}

	public void setFecFirmadocto(Date fecFirmadocto) {
		this.fecFirmadocto = fecFirmadocto;
	}

	public BigDecimal getIndPersonafirma() {
		return this.indPersonafirma;
	}

	public void setIndPersonafirma(BigDecimal indPersonafirma) {
		this.indPersonafirma = indPersonafirma;
	}

	public byte[] getObjDoctofirmado() {
		return this.objDoctofirmado;
	}

	public void setObjDoctofirmado(byte[] objDoctofirmado) {
		this.objDoctofirmado = objDoctofirmado != null ? objDoctofirmado.clone() : null;
	}

	public byte[] getTxCadenaoriginal() {
		return this.txCadenaoriginal;
	}

	public void setTxCadenaoriginal(byte[] txCadenaoriginal) {
		this.txCadenaoriginal = txCadenaoriginal != null ? txCadenaoriginal.clone() : null;
	}

	public byte[] getTxSello() {
		return this.txSello;
	}

	public void setTxSello(byte[] txSello) {
		this.txSello = txSello != null ? txSello.clone() : null;
	}

	public FdcTpoproceso getFdcTpoproceso() {
		return this.fdcTpoproceso;
	}

	public void setFdcTpoproceso(FdcTpoproceso fdcTpoproceso) {
		this.fdcTpoproceso = fdcTpoproceso;
	}
	
}