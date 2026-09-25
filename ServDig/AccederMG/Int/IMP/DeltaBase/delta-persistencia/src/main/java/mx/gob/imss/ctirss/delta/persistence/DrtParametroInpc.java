package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DRT_PARAMETRO_INPC database table.
 * 
 */
@Entity
@Table(name="DRT_PARAMETRO_INPC")
public class DrtParametroInpc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_PARAMETRO_INPC", nullable=false, precision=22)
	private long cveIdParametroInpc;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_APLICACION")
	private Date fecAplicacion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FACREC_DOF")
	private Date fecFacrecDof;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_INPC_REC")
	private Date fecInpcRec;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_VIGENCIA")
	private Date fecVigencia;

	@Column(name="NUM_CONCEPTO", length=1)
	private String numConcepto;

	@Column(name="NUM_PERIODO", precision=22)
	private BigDecimal numPeriodo;

	@Column(name="POR_FACTOR_ACUM_ACT", precision=9, scale=3)
	private BigDecimal porFactorAcumAct;

	@Column(name="POR_FACTOR_ACUM_REC", precision=6, scale=3)
	private BigDecimal porFactorAcumRec;

	@Column(name="POR_INPC", precision=6, scale=3)
	private BigDecimal porInpc;

	@Column(name="POR_RECARGOS", precision=6, scale=3)
	private BigDecimal porRecargos;

    public DrtParametroInpc() {
    }

	public long getCveIdParametroInpc() {
		return this.cveIdParametroInpc;
	}

	public void setCveIdParametroInpc(long cveIdParametroInpc) {
		this.cveIdParametroInpc = cveIdParametroInpc;
	}

	public Date getFecAplicacion() {
		return this.fecAplicacion;
	}

	public void setFecAplicacion(Date fecAplicacion) {
		this.fecAplicacion = fecAplicacion;
	}

	public Date getFecFacrecDof() {
		return this.fecFacrecDof;
	}

	public void setFecFacrecDof(Date fecFacrecDof) {
		this.fecFacrecDof = fecFacrecDof;
	}

	public Date getFecInpcRec() {
		return this.fecInpcRec;
	}

	public void setFecInpcRec(Date fecInpcRec) {
		this.fecInpcRec = fecInpcRec;
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

	public Date getFecVigencia() {
		return this.fecVigencia;
	}

	public void setFecVigencia(Date fecVigencia) {
		this.fecVigencia = fecVigencia;
	}

	public String getNumConcepto() {
		return this.numConcepto;
	}

	public void setNumConcepto(String numConcepto) {
		this.numConcepto = numConcepto;
	}

	public BigDecimal getNumPeriodo() {
		return this.numPeriodo;
	}

	public void setNumPeriodo(BigDecimal numPeriodo) {
		this.numPeriodo = numPeriodo;
	}

	public BigDecimal getPorFactorAcumAct() {
		return this.porFactorAcumAct;
	}

	public void setPorFactorAcumAct(BigDecimal porFactorAcumAct) {
		this.porFactorAcumAct = porFactorAcumAct;
	}

	public BigDecimal getPorFactorAcumRec() {
		return this.porFactorAcumRec;
	}

	public void setPorFactorAcumRec(BigDecimal porFactorAcumRec) {
		this.porFactorAcumRec = porFactorAcumRec;
	}

	public BigDecimal getPorInpc() {
		return this.porInpc;
	}

	public void setPorInpc(BigDecimal porInpc) {
		this.porInpc = porInpc;
	}

	public BigDecimal getPorRecargos() {
		return this.porRecargos;
	}

	public void setPorRecargos(BigDecimal porRecargos) {
		this.porRecargos = porRecargos;
	}

}