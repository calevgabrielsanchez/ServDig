package mx.imss.ctirss.base.model;


import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.imss.ctirss.catalogos.model.DlcMotivodenuncia;
import mx.imss.ctirss.framework.base.model.AbstractModel;
import mx.imss.ctirss.model.DltDenuncia;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DLT_MOTIVODENUNCIA database table.
 * 
 */
@MappedSuperclass
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractDltMotivodenuncia extends AbstractModel {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "cveFoliodenuncia", column = @Column(name = "CVE_FOLIODENUNCIA", nullable = false)),
		@AttributeOverride(name = "cveMotivodenuncia", column = @Column(name = "CVE_MOTIVODENUNCIA", nullable = false))
		})
	private AbstractDltMotivodenunciaPK id;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHAREG")
	private Date fecFechareg;
	
    @Temporal( TemporalType.DATE)
	@Column(name="FEC_LABAL_DEJOLAB")
	private Date fecLabalDejolab;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_LABDEL_INGRESO")
	private Date fecLabdelIngreso;    

	@Column(name="IMP_SALARIO_REAL")
	private BigDecimal impSalarioReal;
	
	@Column(name="IMP_SALARIO_REG")
	private BigDecimal impSalarioReg;

	//bi-directional many-to-one association to DltDenuncia
    @ManyToOne
    @JsonIgnore
	@JoinColumn(name="CVE_FOLIODENUNCIA", referencedColumnName="CVE_FOLIODENUNCIA",nullable = false, insertable = false, updatable = false)
	private DltDenuncia dltDenuncia;
    
    //bi-directional many-to-one association to DlcMotivodenuncia
    @ManyToOne
    @JsonIgnore
	@JoinColumn(name="CVE_MOTIVODENUNCIA", referencedColumnName="CVE_MOTIVODENUNCIA",nullable = false, insertable = false, updatable = false)
	private DlcMotivodenuncia dlcMotivodenuncia;

    public AbstractDltMotivodenuncia(){}
    
	public AbstractDltMotivodenunciaPK getId() {
		return this.id;
	}

	public void setId(AbstractDltMotivodenunciaPK id) {
		this.id = id;
	}
	
	public Date getFecFechareg() {
		return this.fecFechareg;
	}

	public void setFecFechareg(Date fecFechareg) {
		this.fecFechareg = fecFechareg;
	}
	
	public Date getFecLabalDejolab() {
		return this.fecLabalDejolab;
	}

	public void setFecLabalDejolab(Date fecLabalDejolab) {
		this.fecLabalDejolab = fecLabalDejolab;
	}

	public Date getFecLabdelIngreso() {
		return this.fecLabdelIngreso;
	}

	public void setFecLabdelIngreso(Date fecLabdelIngreso) {
		this.fecLabdelIngreso = fecLabdelIngreso;
	}

	public BigDecimal getImpSalarioReal() {
		return this.impSalarioReal;
	}

	public void setImpSalarioReal(BigDecimal impSalarioReal) {
		this.impSalarioReal = impSalarioReal;
	}
	
	public BigDecimal getImpSalarioReg() {
		return this.impSalarioReg;
	}

	public void setImpSalarioReg(BigDecimal impSalarioReg) {
		this.impSalarioReg = impSalarioReg;
	}

	public DltDenuncia getDltDenuncia() {
		return this.dltDenuncia;
	}

	public void setDltDenuncia(DltDenuncia dltDenuncia) {
		this.dltDenuncia = dltDenuncia;
	}
	
	public DlcMotivodenuncia getDlcMotivodenuncia() {
		return this.dlcMotivodenuncia;
	}

	public void setDlcMotivodenuncia(DlcMotivodenuncia dlcMotivodenuncia) {
		this.dlcMotivodenuncia = dlcMotivodenuncia;
	}
}