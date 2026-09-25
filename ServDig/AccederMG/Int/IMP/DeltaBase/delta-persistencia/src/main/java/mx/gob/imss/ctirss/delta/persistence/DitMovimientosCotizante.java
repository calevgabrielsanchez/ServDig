package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DIT_MOVIMIENTOS_COTIZANTE database table.
 * 
 */
@Entity
@Table(name="DIT_MOVIMIENTOS_COTIZANTE")
public class DitMovimientosCotizante implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DitMovimientosCotizantePK id;

	@Column(name="CVE_ID_ARTICULO_33", precision=22)
	private BigDecimal cveIdArticulo33;

	@Column(name="CVE_ID_MARCA", precision=22)
	private BigDecimal cveIdMarca;

	@Column(name="CVE_ID_MARCA_EXTEMPO", precision=22)
	private BigDecimal cveIdMarcaExtempo;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FIN")
	private Date fecFin;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_INICIO")
	private Date fecInicio;

	@Column(name="IMP_ACT", precision=11, scale=2)
	private BigDecimal impAct;

	@Column(name="IMP_ACT_MUL", precision=11, scale=2)
	private BigDecimal impActMul;

	@Column(name="IMP_ACT_O", precision=11, scale=2)
	private BigDecimal impActO;

	@Column(name="IMP_ACT_P", precision=11, scale=2)
	private BigDecimal impActP;

	@Column(name="IMP_ACV", precision=11, scale=2)
	private BigDecimal impAcv;

	@Column(name="IMP_APOR_ADIC", precision=11, scale=2)
	private BigDecimal impAporAdic;

	@Column(name="IMP_APOR_COMPLEM", precision=11, scale=2)
	private BigDecimal impAporComplem;

	@Column(name="IMP_APORT_INFONAVIT", precision=11, scale=2)
	private BigDecimal impAportInfonavit;

	@Column(name="IMP_CYV", precision=11, scale=2)
	private BigDecimal impCyv;

	@Column(name="IMP_CYV_O", precision=11, scale=2)
	private BigDecimal impCyvO;

	@Column(name="IMP_CYV_P", precision=11, scale=2)
	private BigDecimal impCyvP;

	@Column(name="IMP_EYM_ADI", precision=11, scale=2)
	private BigDecimal impEymAdi;

	@Column(name="IMP_EYM_ADI_O", precision=11, scale=2)
	private BigDecimal impEymAdiO;

	@Column(name="IMP_EYM_ADI_P", precision=11, scale=2)
	private BigDecimal impEymAdiP;

	@Column(name="IMP_EYM_DIN", precision=11, scale=2)
	private BigDecimal impEymDin;

	@Column(name="IMP_EYM_DIN_O", precision=11, scale=2)
	private BigDecimal impEymDinO;

	@Column(name="IMP_EYM_DIN_P", precision=11, scale=2)
	private BigDecimal impEymDinP;

	@Column(name="IMP_EYM_FIJA", precision=11, scale=2)
	private BigDecimal impEymFija;

	@Column(name="IMP_EYM_PEN", precision=11, scale=2)
	private BigDecimal impEymPen;

	@Column(name="IMP_EYM_PEN_O", precision=11, scale=2)
	private BigDecimal impEymPenO;

	@Column(name="IMP_EYM_PEN_P", precision=11, scale=2)
	private BigDecimal impEymPenP;

	@Column(name="IMP_GUAR", precision=11, scale=2)
	private BigDecimal impGuar;

	@Column(name="IMP_IV", precision=11, scale=2)
	private BigDecimal impIv;

	@Column(name="IMP_IV_O", precision=11, scale=2)
	private BigDecimal impIvO;

	@Column(name="IMP_IV_P", precision=11, scale=2)
	private BigDecimal impIvP;

	@Column(name="IMP_MUL", precision=11, scale=2)
	private BigDecimal impMul;

	@Column(name="IMP_REC", precision=11, scale=2)
	private BigDecimal impRec;

	@Column(name="IMP_REC_O", precision=11, scale=2)
	private BigDecimal impRecO;

	@Column(name="IMP_REC_P", precision=11, scale=2)
	private BigDecimal impRecP;

	@Column(name="IMP_RET", precision=11, scale=2)
	private BigDecimal impRet;

	@Column(name="IMP_RT", precision=11, scale=2)
	private BigDecimal impRt;

	@Column(name="IMP_SALARIO", precision=11, scale=2)
	private BigDecimal impSalario;

	@Column(name="IMP_SALARIO_ART33", precision=22)
	private BigDecimal impSalarioArt33;

	@Column(name="IMP_TOT", precision=11, scale=2)
	private BigDecimal impTot;

	@Column(name="NUM_DIAS_AUSENTISMO", precision=22)
	private BigDecimal numDiasAusentismo;

	@Column(name="NUM_DIAS_COP", precision=22)
	private BigDecimal numDiasCop;

	@Column(name="NUM_DIAS_COTIZACION", precision=22)
	private BigDecimal numDiasCotizacion;

	@Column(name="NUM_DIAS_INCAPACIDAD", precision=22)
	private BigDecimal numDiasIncapacidad;

	@Column(name="NUM_DIAS_RCV", precision=22)
	private BigDecimal numDiasRcv;

	@Column(name="NUM_FOLIO_INCAPACIDAD", precision=22)
	private BigDecimal numFolioIncapacidad;

	@Column(name="TIPO_MOVIMIENTO", precision=22)
	private BigDecimal tipoMovimiento;

	@Column(name="TIPO_ORIGEN", precision=22)
	private BigDecimal tipoOrigen;

	//bi-directional many-to-one association to DitCuotasCotizante
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_ID_ASEGURADO", referencedColumnName="CVE_ID_ASEGURADO", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="CVE_ID_PATRON_SUJETO_OBLIGADO", referencedColumnName="CVE_ID_PATRON_SUJETO_OBLIGADO", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="NUM_CRED", referencedColumnName="NUM_CRED", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="NUM_PER", referencedColumnName="NUM_PER", nullable=false, insertable=false, updatable=false)
		})
	private DitCuotasCotizante ditCuotasCotizante;

    public DitMovimientosCotizante() {
    }

	public DitMovimientosCotizantePK getId() {
		return this.id;
	}

	public void setId(DitMovimientosCotizantePK id) {
		this.id = id;
	}
	
	public BigDecimal getCveIdArticulo33() {
		return this.cveIdArticulo33;
	}

	public void setCveIdArticulo33(BigDecimal cveIdArticulo33) {
		this.cveIdArticulo33 = cveIdArticulo33;
	}

	public BigDecimal getCveIdMarca() {
		return this.cveIdMarca;
	}

	public void setCveIdMarca(BigDecimal cveIdMarca) {
		this.cveIdMarca = cveIdMarca;
	}

	public BigDecimal getCveIdMarcaExtempo() {
		return this.cveIdMarcaExtempo;
	}

	public void setCveIdMarcaExtempo(BigDecimal cveIdMarcaExtempo) {
		this.cveIdMarcaExtempo = cveIdMarcaExtempo;
	}

	public Date getFecFin() {
		return this.fecFin;
	}

	public void setFecFin(Date fecFin) {
		this.fecFin = fecFin;
	}

	public Date getFecInicio() {
		return this.fecInicio;
	}

	public void setFecInicio(Date fecInicio) {
		this.fecInicio = fecInicio;
	}

	public BigDecimal getImpAct() {
		return this.impAct;
	}

	public void setImpAct(BigDecimal impAct) {
		this.impAct = impAct;
	}

	public BigDecimal getImpActMul() {
		return this.impActMul;
	}

	public void setImpActMul(BigDecimal impActMul) {
		this.impActMul = impActMul;
	}

	public BigDecimal getImpActO() {
		return this.impActO;
	}

	public void setImpActO(BigDecimal impActO) {
		this.impActO = impActO;
	}

	public BigDecimal getImpActP() {
		return this.impActP;
	}

	public void setImpActP(BigDecimal impActP) {
		this.impActP = impActP;
	}

	public BigDecimal getImpAcv() {
		return this.impAcv;
	}

	public void setImpAcv(BigDecimal impAcv) {
		this.impAcv = impAcv;
	}

	public BigDecimal getImpAporAdic() {
		return this.impAporAdic;
	}

	public void setImpAporAdic(BigDecimal impAporAdic) {
		this.impAporAdic = impAporAdic;
	}

	public BigDecimal getImpAporComplem() {
		return this.impAporComplem;
	}

	public void setImpAporComplem(BigDecimal impAporComplem) {
		this.impAporComplem = impAporComplem;
	}

	public BigDecimal getImpAportInfonavit() {
		return this.impAportInfonavit;
	}

	public void setImpAportInfonavit(BigDecimal impAportInfonavit) {
		this.impAportInfonavit = impAportInfonavit;
	}

	public BigDecimal getImpCyv() {
		return this.impCyv;
	}

	public void setImpCyv(BigDecimal impCyv) {
		this.impCyv = impCyv;
	}

	public BigDecimal getImpCyvO() {
		return this.impCyvO;
	}

	public void setImpCyvO(BigDecimal impCyvO) {
		this.impCyvO = impCyvO;
	}

	public BigDecimal getImpCyvP() {
		return this.impCyvP;
	}

	public void setImpCyvP(BigDecimal impCyvP) {
		this.impCyvP = impCyvP;
	}

	public BigDecimal getImpEymAdi() {
		return this.impEymAdi;
	}

	public void setImpEymAdi(BigDecimal impEymAdi) {
		this.impEymAdi = impEymAdi;
	}

	public BigDecimal getImpEymAdiO() {
		return this.impEymAdiO;
	}

	public void setImpEymAdiO(BigDecimal impEymAdiO) {
		this.impEymAdiO = impEymAdiO;
	}

	public BigDecimal getImpEymAdiP() {
		return this.impEymAdiP;
	}

	public void setImpEymAdiP(BigDecimal impEymAdiP) {
		this.impEymAdiP = impEymAdiP;
	}

	public BigDecimal getImpEymDin() {
		return this.impEymDin;
	}

	public void setImpEymDin(BigDecimal impEymDin) {
		this.impEymDin = impEymDin;
	}

	public BigDecimal getImpEymDinO() {
		return this.impEymDinO;
	}

	public void setImpEymDinO(BigDecimal impEymDinO) {
		this.impEymDinO = impEymDinO;
	}

	public BigDecimal getImpEymDinP() {
		return this.impEymDinP;
	}

	public void setImpEymDinP(BigDecimal impEymDinP) {
		this.impEymDinP = impEymDinP;
	}

	public BigDecimal getImpEymFija() {
		return this.impEymFija;
	}

	public void setImpEymFija(BigDecimal impEymFija) {
		this.impEymFija = impEymFija;
	}

	public BigDecimal getImpEymPen() {
		return this.impEymPen;
	}

	public void setImpEymPen(BigDecimal impEymPen) {
		this.impEymPen = impEymPen;
	}

	public BigDecimal getImpEymPenO() {
		return this.impEymPenO;
	}

	public void setImpEymPenO(BigDecimal impEymPenO) {
		this.impEymPenO = impEymPenO;
	}

	public BigDecimal getImpEymPenP() {
		return this.impEymPenP;
	}

	public void setImpEymPenP(BigDecimal impEymPenP) {
		this.impEymPenP = impEymPenP;
	}

	public BigDecimal getImpGuar() {
		return this.impGuar;
	}

	public void setImpGuar(BigDecimal impGuar) {
		this.impGuar = impGuar;
	}

	public BigDecimal getImpIv() {
		return this.impIv;
	}

	public void setImpIv(BigDecimal impIv) {
		this.impIv = impIv;
	}

	public BigDecimal getImpIvO() {
		return this.impIvO;
	}

	public void setImpIvO(BigDecimal impIvO) {
		this.impIvO = impIvO;
	}

	public BigDecimal getImpIvP() {
		return this.impIvP;
	}

	public void setImpIvP(BigDecimal impIvP) {
		this.impIvP = impIvP;
	}

	public BigDecimal getImpMul() {
		return this.impMul;
	}

	public void setImpMul(BigDecimal impMul) {
		this.impMul = impMul;
	}

	public BigDecimal getImpRec() {
		return this.impRec;
	}

	public void setImpRec(BigDecimal impRec) {
		this.impRec = impRec;
	}

	public BigDecimal getImpRecO() {
		return this.impRecO;
	}

	public void setImpRecO(BigDecimal impRecO) {
		this.impRecO = impRecO;
	}

	public BigDecimal getImpRecP() {
		return this.impRecP;
	}

	public void setImpRecP(BigDecimal impRecP) {
		this.impRecP = impRecP;
	}

	public BigDecimal getImpRet() {
		return this.impRet;
	}

	public void setImpRet(BigDecimal impRet) {
		this.impRet = impRet;
	}

	public BigDecimal getImpRt() {
		return this.impRt;
	}

	public void setImpRt(BigDecimal impRt) {
		this.impRt = impRt;
	}

	public BigDecimal getImpSalario() {
		return this.impSalario;
	}

	public void setImpSalario(BigDecimal impSalario) {
		this.impSalario = impSalario;
	}

	public BigDecimal getImpSalarioArt33() {
		return this.impSalarioArt33;
	}

	public void setImpSalarioArt33(BigDecimal impSalarioArt33) {
		this.impSalarioArt33 = impSalarioArt33;
	}

	public BigDecimal getImpTot() {
		return this.impTot;
	}

	public void setImpTot(BigDecimal impTot) {
		this.impTot = impTot;
	}

	public BigDecimal getNumDiasAusentismo() {
		return this.numDiasAusentismo;
	}

	public void setNumDiasAusentismo(BigDecimal numDiasAusentismo) {
		this.numDiasAusentismo = numDiasAusentismo;
	}

	public BigDecimal getNumDiasCop() {
		return this.numDiasCop;
	}

	public void setNumDiasCop(BigDecimal numDiasCop) {
		this.numDiasCop = numDiasCop;
	}

	public BigDecimal getNumDiasCotizacion() {
		return this.numDiasCotizacion;
	}

	public void setNumDiasCotizacion(BigDecimal numDiasCotizacion) {
		this.numDiasCotizacion = numDiasCotizacion;
	}

	public BigDecimal getNumDiasIncapacidad() {
		return this.numDiasIncapacidad;
	}

	public void setNumDiasIncapacidad(BigDecimal numDiasIncapacidad) {
		this.numDiasIncapacidad = numDiasIncapacidad;
	}

	public BigDecimal getNumDiasRcv() {
		return this.numDiasRcv;
	}

	public void setNumDiasRcv(BigDecimal numDiasRcv) {
		this.numDiasRcv = numDiasRcv;
	}

	public BigDecimal getNumFolioIncapacidad() {
		return this.numFolioIncapacidad;
	}

	public void setNumFolioIncapacidad(BigDecimal numFolioIncapacidad) {
		this.numFolioIncapacidad = numFolioIncapacidad;
	}

	public BigDecimal getTipoMovimiento() {
		return this.tipoMovimiento;
	}

	public void setTipoMovimiento(BigDecimal tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public BigDecimal getTipoOrigen() {
		return this.tipoOrigen;
	}

	public void setTipoOrigen(BigDecimal tipoOrigen) {
		this.tipoOrigen = tipoOrigen;
	}

	public DitCuotasCotizante getDitCuotasCotizante() {
		return this.ditCuotasCotizante;
	}

	public void setDitCuotasCotizante(DitCuotasCotizante ditCuotasCotizante) {
		this.ditCuotasCotizante = ditCuotasCotizante;
	}
	
}