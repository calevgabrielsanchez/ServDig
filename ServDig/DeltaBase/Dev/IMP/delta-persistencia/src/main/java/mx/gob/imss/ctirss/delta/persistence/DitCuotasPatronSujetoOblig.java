package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIT_CUOTAS_PATRON_SUJETO_OBLIG database table.
 * 
 */
@Entity
@Table(name="DIT_CUOTAS_PATRON_SUJETO_OBLIG")
public class DitCuotasPatronSujetoOblig implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DitCuotasPatronSujetoObligPK id;

	@Column(name="CVE_ID_DEL_CTL", precision=22)
	private BigDecimal cveIdDelCtl;

	@Column(name="CVE_ID_SUB_CTL", precision=22)
	private BigDecimal cveIdSubCtl;

	@Column(name="CVE_ID_USU", precision=22)
	private BigDecimal cveIdUsu;

	@Column(name="DEL_EMI", precision=22)
	private BigDecimal delEmi;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_CAP")
	private Date fecCap;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_MOV")
	private Date fecMov;

	@Column(name="HORA_CAP", precision=22)
	private BigDecimal horaCap;

	@Column(name="IMP_ACT", precision=11, scale=2)
	private BigDecimal impAct;

	@Column(name="IMP_ACT_MUL", precision=11, scale=2)
	private BigDecimal impActMul;

	@Column(name="IMP_ACT_O", precision=11, scale=2)
	private BigDecimal impActO;

	@Column(name="IMP_ACT_P", precision=11, scale=2)
	private BigDecimal impActP;

	@Column(name="IMP_APOR_ADIC", precision=11, scale=2)
	private BigDecimal impAporAdic;

	@Column(name="IMP_APOR_COMPLEM", precision=11, scale=2)
	private BigDecimal impAporComplem;

	@Column(name="IMP_APOR_GOBIERNO_FEDERAL", precision=11, scale=2)
	private BigDecimal impAporGobiernoFederal;

	@Column(name="IMP_APOR_INCOBRABILIDAD", precision=11, scale=2)
	private BigDecimal impAporIncobrabilidad;

	@Column(name="IMP_CYV", precision=11, scale=2)
	private BigDecimal impCyv;

	@Column(name="IMP_CYV_O", precision=11, scale=2)
	private BigDecimal impCyvO;

	@Column(name="IMP_CYV_P", precision=11, scale=2)
	private BigDecimal impCyvP;

	@Column(name="IMP_EYM_ADI", precision=22)
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

	@Column(name="IMP_GASTOS_EJE", precision=11, scale=2)
	private BigDecimal impGastosEje;

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

	@Column(name="IMP_TOT", precision=11, scale=2)
	private BigDecimal impTot;

	@Column(name="SUB_EMI", precision=22)
	private BigDecimal subEmi;

	//bi-directional many-to-one association to DitCuotasCotizante
	@OneToMany(mappedBy="ditCuotasPatronSujetoOblig")
	private List<DitCuotasCotizante> ditCuotasCotizantes;

	//bi-directional many-to-one association to DitPatronSujetoObligado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PATRON_SUJETO_OBLIGADO", nullable=false, insertable=false, updatable=false)
	private DitPatronSujetoObligado ditPatronSujetoObligado;

	//bi-directional many-to-one association to DitUsuario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_USUARIO")
	private DitUsuario ditUsuario;

	//bi-directional many-to-one association to DicClasePrima
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_CLASE_PRIMA")
	private DicClasePrima dicClasePrima;

	//bi-directional many-to-one association to DitMarcasPatronSujetoOblig
	@OneToMany(mappedBy="ditCuotasPatronSujetoOblig")
	private List<DitMarcasPatronSujetoOblig> ditMarcasPatronSujetoObligs;

	//bi-directional many-to-one association to DitMovtosCartera
	@OneToMany(mappedBy="ditCuotasPatronSujetoOblig")
	private List<DitMovtosCartera> ditMovtosCarteras;

    public DitCuotasPatronSujetoOblig() {
    }

	public DitCuotasPatronSujetoObligPK getId() {
		return this.id;
	}

	public void setId(DitCuotasPatronSujetoObligPK id) {
		this.id = id;
	}
	
	public BigDecimal getCveIdDelCtl() {
		return this.cveIdDelCtl;
	}

	public void setCveIdDelCtl(BigDecimal cveIdDelCtl) {
		this.cveIdDelCtl = cveIdDelCtl;
	}

	public BigDecimal getCveIdSubCtl() {
		return this.cveIdSubCtl;
	}

	public void setCveIdSubCtl(BigDecimal cveIdSubCtl) {
		this.cveIdSubCtl = cveIdSubCtl;
	}

	public BigDecimal getCveIdUsu() {
		return this.cveIdUsu;
	}

	public void setCveIdUsu(BigDecimal cveIdUsu) {
		this.cveIdUsu = cveIdUsu;
	}

	public BigDecimal getDelEmi() {
		return this.delEmi;
	}

	public void setDelEmi(BigDecimal delEmi) {
		this.delEmi = delEmi;
	}

	public Date getFecCap() {
		return this.fecCap;
	}

	public void setFecCap(Date fecCap) {
		this.fecCap = fecCap;
	}

	public Date getFecMov() {
		return this.fecMov;
	}

	public void setFecMov(Date fecMov) {
		this.fecMov = fecMov;
	}

	public BigDecimal getHoraCap() {
		return this.horaCap;
	}

	public void setHoraCap(BigDecimal horaCap) {
		this.horaCap = horaCap;
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

	public BigDecimal getImpAporGobiernoFederal() {
		return this.impAporGobiernoFederal;
	}

	public void setImpAporGobiernoFederal(BigDecimal impAporGobiernoFederal) {
		this.impAporGobiernoFederal = impAporGobiernoFederal;
	}

	public BigDecimal getImpAporIncobrabilidad() {
		return this.impAporIncobrabilidad;
	}

	public void setImpAporIncobrabilidad(BigDecimal impAporIncobrabilidad) {
		this.impAporIncobrabilidad = impAporIncobrabilidad;
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

	public BigDecimal getImpGastosEje() {
		return this.impGastosEje;
	}

	public void setImpGastosEje(BigDecimal impGastosEje) {
		this.impGastosEje = impGastosEje;
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

	public BigDecimal getImpTot() {
		return this.impTot;
	}

	public void setImpTot(BigDecimal impTot) {
		this.impTot = impTot;
	}

	public BigDecimal getSubEmi() {
		return this.subEmi;
	}

	public void setSubEmi(BigDecimal subEmi) {
		this.subEmi = subEmi;
	}

	public List<DitCuotasCotizante> getDitCuotasCotizantes() {
		return this.ditCuotasCotizantes;
	}

	public void setDitCuotasCotizantes(List<DitCuotasCotizante> ditCuotasCotizantes) {
		this.ditCuotasCotizantes = ditCuotasCotizantes;
	}
	
	public DitPatronSujetoObligado getDitPatronSujetoObligado() {
		return this.ditPatronSujetoObligado;
	}

	public void setDitPatronSujetoObligado(DitPatronSujetoObligado ditPatronSujetoObligado) {
		this.ditPatronSujetoObligado = ditPatronSujetoObligado;
	}
	
	public DitUsuario getDitUsuario() {
		return this.ditUsuario;
	}

	public void setDitUsuario(DitUsuario ditUsuario) {
		this.ditUsuario = ditUsuario;
	}
	
	public DicClasePrima getDicClasePrima() {
		return this.dicClasePrima;
	}

	public void setDicClasePrima(DicClasePrima dicClasePrima) {
		this.dicClasePrima = dicClasePrima;
	}
	
	public List<DitMarcasPatronSujetoOblig> getDitMarcasPatronSujetoObligs() {
		return this.ditMarcasPatronSujetoObligs;
	}

	public void setDitMarcasPatronSujetoObligs(List<DitMarcasPatronSujetoOblig> ditMarcasPatronSujetoObligs) {
		this.ditMarcasPatronSujetoObligs = ditMarcasPatronSujetoObligs;
	}
	
	public List<DitMovtosCartera> getDitMovtosCarteras() {
		return this.ditMovtosCarteras;
	}

	public void setDitMovtosCarteras(List<DitMovtosCartera> ditMovtosCarteras) {
		this.ditMovtosCarteras = ditMovtosCarteras;
	}
	
}