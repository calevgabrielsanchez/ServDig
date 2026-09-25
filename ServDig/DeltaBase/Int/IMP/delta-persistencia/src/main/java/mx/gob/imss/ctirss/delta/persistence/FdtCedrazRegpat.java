package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the FDT_CEDRAZ_REGPAT database table.
 * 
 */
@Entity
@Table(name="FDT_CEDRAZ_REGPAT")
public class FdtCedrazRegpat implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtCedrazRegpatPK id;

	@Column(name="BASE_COT_CV", precision=12, scale=2)
	private BigDecimal baseCotCv;

	@Column(name="BASE_COT_EM", precision=12, scale=2)
	private BigDecimal baseCotEm;

	@Column(name="EXCEDENTE_CV", precision=12, scale=2)
	private BigDecimal excedenteCv;

	@Column(name="EXCEDENTE_EM", precision=12, scale=2)
	private BigDecimal excedenteEm;

	@Column(name="VAR_ANT_DIC_CV", precision=12, scale=2)
	private BigDecimal varAntDicCv;

	@Column(name="VAR_ANT_DIC_EM", precision=12, scale=2)
	private BigDecimal varAntDicEm;

	@Column(name="VAR_DIC_CV", precision=12, scale=2)
	private BigDecimal varDicCv;

	@Column(name="VAR_DIC_EM", precision=12, scale=2)
	private BigDecimal varDicEm;

	//bi-directional many-to-one association to FdtCedrazPercepcione
	@OneToMany(mappedBy="fdtCedrazRegpat")
	private List<FdtCedrazPercepcione> fdtCedrazPercepciones;

	//bi-directional many-to-one association to FdtCedrazDictamen
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="ID_CEDULA", referencedColumnName="ID_CEDULA", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false, insertable=false, updatable=false)
		})
	private FdtCedrazDictamen fdtCedrazDictamen;

    public FdtCedrazRegpat() {
    }

	public FdtCedrazRegpatPK getId() {
		return this.id;
	}

	public void setId(FdtCedrazRegpatPK id) {
		this.id = id;
	}
	
	public BigDecimal getBaseCotCv() {
		return this.baseCotCv;
	}

	public void setBaseCotCv(BigDecimal baseCotCv) {
		this.baseCotCv = baseCotCv;
	}

	public BigDecimal getBaseCotEm() {
		return this.baseCotEm;
	}

	public void setBaseCotEm(BigDecimal baseCotEm) {
		this.baseCotEm = baseCotEm;
	}

	public BigDecimal getExcedenteCv() {
		return this.excedenteCv;
	}

	public void setExcedenteCv(BigDecimal excedenteCv) {
		this.excedenteCv = excedenteCv;
	}

	public BigDecimal getExcedenteEm() {
		return this.excedenteEm;
	}

	public void setExcedenteEm(BigDecimal excedenteEm) {
		this.excedenteEm = excedenteEm;
	}

	public BigDecimal getVarAntDicCv() {
		return this.varAntDicCv;
	}

	public void setVarAntDicCv(BigDecimal varAntDicCv) {
		this.varAntDicCv = varAntDicCv;
	}

	public BigDecimal getVarAntDicEm() {
		return this.varAntDicEm;
	}

	public void setVarAntDicEm(BigDecimal varAntDicEm) {
		this.varAntDicEm = varAntDicEm;
	}

	public BigDecimal getVarDicCv() {
		return this.varDicCv;
	}

	public void setVarDicCv(BigDecimal varDicCv) {
		this.varDicCv = varDicCv;
	}

	public BigDecimal getVarDicEm() {
		return this.varDicEm;
	}

	public void setVarDicEm(BigDecimal varDicEm) {
		this.varDicEm = varDicEm;
	}

	public List<FdtCedrazPercepcione> getFdtCedrazPercepciones() {
		return this.fdtCedrazPercepciones;
	}

	public void setFdtCedrazPercepciones(List<FdtCedrazPercepcione> fdtCedrazPercepciones) {
		this.fdtCedrazPercepciones = fdtCedrazPercepciones;
	}
	
	public FdtCedrazDictamen getFdtCedrazDictamen() {
		return this.fdtCedrazDictamen;
	}

	public void setFdtCedrazDictamen(FdtCedrazDictamen fdtCedrazDictamen) {
		this.fdtCedrazDictamen = fdtCedrazDictamen;
	}
	
}