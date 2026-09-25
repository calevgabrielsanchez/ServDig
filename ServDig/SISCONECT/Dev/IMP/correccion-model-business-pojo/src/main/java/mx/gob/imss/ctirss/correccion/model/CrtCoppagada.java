package mx.gob.imss.ctirss.correccion.model;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.ctirss.correccion.catalogos.model.CrcEjercicio;
import mx.gob.imss.ctirss.correccion.framework.annotations.OnSearchLlavePrimaria;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;


/**
 * The persistent class for the CRT_COPPAGADAS database table.
 * 
 */
@Entity
@Table(name="CRT_COPPAGADAS")
@OnSearchLlavePrimaria(atributos="cveCopPagCorr")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrtCoppagada extends AbstractModel {
	
private static final long serialVersionUID = 1L;

	@Transient
	private Collection<Integer> idsAnexoSolCorrPatConcat;
	
	@Id
	@SequenceGenerator(name="SEQ_CRS_CVE_COPPAGCORR_GENERATOR", sequenceName="CRS_CVE_COPPAGCORR")
	@GeneratedValue(generator="SEQ_CRS_CVE_COPPAGCORR_GENERATOR")
	@Column(name="CVE_COPPAGCORR")
	private Integer  cveCopPagCorr;

	@Column(name="CVE_ANEXOSOLCORRPAT", insertable = false, updatable = false)	
	private Integer  cveAnexoSolCorrPat;
	
	//bi-directional many-to-one association to CrtRpEjercicio
    @ManyToOne
	@JoinColumns({
		@JoinColumn(name="CVE_ANEXOSOLCORRPAT", referencedColumnName="CVE_ANEXOSOLCORRPAT"),
		@JoinColumn(name="CVE_EJERCICIO", referencedColumnName="CVE_EJERCICIO")
	})
	private CrcEjercicio crtRpEjercicio;
	
	@Column(name="NU_TRABREGU")
	private Integer  nuTrabRegu;
	
	@Column(name="IMP_COP")
    private BigDecimal impCOP;
	
	@Column(name="IMP_COPACT")
    private BigDecimal impCOPAct;
	
	@Column(name="IMP_COPREC")
    private BigDecimal impCOPRec;
	
	@Column(name="IMP_COPTOT")
    private BigDecimal impCOPTot;
	
	@Column(name="IMP_RCV")
    private BigDecimal impRCV;
	
	@Column(name="IMP_RCVACT")
    private BigDecimal impRCVAct;
	
	@Column(name="IMP_RCVREC")
    private BigDecimal impRCVRec;
	
	@Column(name="IMP_RCVTOT")
    private BigDecimal impRCVTot;
    
	@Column(name="NUM_FOLIOSUA")
    private Integer numFolioSua;
	
	@Column(name="NUM_ORDENINGRESO")
    private String  numOrdenIngreso;
	
	@Column(name="NUM_CREDITO")
    private String  numCredito;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_FECHAPAGO")
    private Date fechaPago;
	
	@Column(name="ID_TIPODOCTO")
    private Integer idTipoDocto;
	
	@Column(name="NUM_PERIODO")
    private Integer numPeriodo;
	
	
	@Column(name="NUM_PERIODO_RCV")
    private Integer numPeriodoRCV;
 
 
	@Temporal( TemporalType.TIMESTAMP)
    @Column(name="FEC_FECHAREG")
    private Date fechaRegistro;
    
    @Column(name="CVE_USUARIO")
    private String cveUsuario;
    
	@Column(name="NUM_TRABREGULA")
    private Integer numTrabRegularizados;
	
	@Column(name="NUM_ALTAS")
    private Integer numTrabAltas;
	
	@Column(name="NUM_BAJAS")
    private Integer numTrabBajas;
	
	@Column(name="NUM_MODIFSALARIO")
    private Integer numTrabModSalario;

	public Integer getCveCopPagCorr() {
		return cveCopPagCorr;
	}

	public void setCveCopPagCorr(Integer cveCopPagCorr) {
		this.cveCopPagCorr = cveCopPagCorr;
	}

	public Integer getCveAnexoSolCorrPat() {
		return cveAnexoSolCorrPat;
	}

	public void setCveAnexoSolCorrPat(Integer cveAnexoSolCorrPat) {
		this.cveAnexoSolCorrPat = cveAnexoSolCorrPat;
	}

	public Integer getNuTrabRegu() {
		return nuTrabRegu;
	}

	public void setNuTrabRegu(Integer nuTrabRegu) {
		this.nuTrabRegu = nuTrabRegu;
	}

	public BigDecimal getImpCOP() {
		return impCOP;
	}

	public void setImpCOP(BigDecimal impCOP) {
		this.impCOP = impCOP;
	}

	public BigDecimal getImpCOPAct() {
		return impCOPAct;
	}

	public void setImpCOPAct(BigDecimal impCOPAct) {
		this.impCOPAct = impCOPAct;
	}

	public BigDecimal getImpCOPRec() {
		return impCOPRec;
	}

	public void setImpCOPRec(BigDecimal impCOPRec) {
		this.impCOPRec = impCOPRec;
	}

	public BigDecimal getImpCOPTot() {
		return impCOPTot;
	}

	public void setImpCOPTot(BigDecimal impCOPTot) {
		this.impCOPTot = impCOPTot;
	}

	public BigDecimal getImpRCV() {
		return impRCV;
	}

	public void setImpRCV(BigDecimal impRCV) {
		this.impRCV = impRCV;
	}

	public BigDecimal getImpRCVAct() {
		return impRCVAct;
	}

	public void setImpRCVAct(BigDecimal impRCVAct) {
		this.impRCVAct = impRCVAct;
	}

	public BigDecimal getImpRCVRec() {
		return impRCVRec;
	}

	public void setImpRCVRec(BigDecimal impRCVRec) {
		this.impRCVRec = impRCVRec;
	}

	public BigDecimal getImpRCVTot() {
		return impRCVTot;
	}

	public void setImpRCVTot(BigDecimal impRCVTot) {
		this.impRCVTot = impRCVTot;
	}

	public Integer getNumFolioSua() {
		return numFolioSua;
	}

	public void setNumFolioSua(Integer numFolioSua) {
		this.numFolioSua = numFolioSua;
	}

	public String getNumOrdenIngreso() {
		return numOrdenIngreso;
	}

	public void setNumOrdenIngreso(String numOrdenIngreso) {
		this.numOrdenIngreso = numOrdenIngreso;
	}

	public String getNumCredito() {
		return numCredito;
	}

	public void setNumCredito(String numCredito) {
		this.numCredito = numCredito;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public Integer getIdTipoDocto() {
		return idTipoDocto;
	}

	public void setIdTipoDocto(Integer idTipoDocto) {
		this.idTipoDocto = idTipoDocto;
	}

	public Integer getNumPeriodo() {
		return numPeriodo;
	}

	public void setNumPeriodo(Integer numPeriodo) {
		this.numPeriodo = numPeriodo;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getCveUsuario() {
		return cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}

	public Integer getNumTrabRegularizados() {
		return numTrabRegularizados;
	}

	public void setNumTrabRegularizados(Integer numTrabRegularizados) {
		this.numTrabRegularizados = numTrabRegularizados;
	}

	public Integer getNumTrabAltas() {
		return numTrabAltas;
	}

	public void setNumTrabAltas(Integer numTrabAltas) {
		this.numTrabAltas = numTrabAltas;
	}

	public Integer getNumTrabBajas() {
		return numTrabBajas;
	}

	public void setNumTrabBajas(Integer numTrabBajas) {
		this.numTrabBajas = numTrabBajas;
	}

	public Integer getNumTrabModSalario() {
		return numTrabModSalario;
	}

	public void setNumTrabModSalario(Integer numTrabModSalario) {
		this.numTrabModSalario = numTrabModSalario;
	}

	public CrcEjercicio getCrtRpEjercicio() {
		return crtRpEjercicio;
	}

	public void setCrtRpEjercicio(CrcEjercicio crtRpEjercicio) {
		this.crtRpEjercicio = crtRpEjercicio;
	}

	public Collection<Integer> getIdsAnexoSolCorrPatConcat() {
		return idsAnexoSolCorrPatConcat;
	}

	public void setIdsAnexoSolCorrPatConcat(Collection<Integer> idsAnexoSolCorrPatConcat) {
		this.idsAnexoSolCorrPatConcat = idsAnexoSolCorrPatConcat;
	}

	public Integer getNumPeriodoRCV() {
		return numPeriodoRCV;
	}

	public void setNumPeriodoRCV(Integer numPeriodoRCV) {
		this.numPeriodoRCV = numPeriodoRCV;
	}

	
}	