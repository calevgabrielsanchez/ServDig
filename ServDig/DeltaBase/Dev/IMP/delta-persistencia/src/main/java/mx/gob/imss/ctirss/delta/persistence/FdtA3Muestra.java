package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the FDT_A3_MUESTRA database table.
 * 
 */
@Entity
@Table(name="FDT_A3_MUESTRA")
public class FdtA3Muestra implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtA3MuestraPK id;

	@Column(name="AP_MATERNO_ASEGURADO", length=30)
	private String apMaternoAsegurado;

	@Column(name="AP_PATERNO_ASEGURADO", length=30)
	private String apPaternoAsegurado;

	@Column(name="CV_GRUPO", precision=22)
	private BigDecimal cvGrupo;

	@Column(name="IM_COTIZO_EYM_RT", precision=12, scale=2)
	private BigDecimal imCotizoEymRt;

	@Column(name="IM_COTIZO_INV_VIDA", precision=12, scale=2)
	private BigDecimal imCotizoInvVida;

	@Column(name="IM_CUOTA_DIARIA", precision=12, scale=2)
	private BigDecimal imCuotaDiaria;

	@Column(name="IM_DEBIOCOTIZAR_EYM_RT", precision=12, scale=2)
	private BigDecimal imDebiocotizarEymRt;

	@Column(name="IM_DEBIOCOTIZAR_INV_VIDA", precision=12, scale=2)
	private BigDecimal imDebiocotizarInvVida;

	@Column(name="NOMBRE_ASEGURADO", length=30)
	private String nombreAsegurado;

	@Column(name="NU_ANTIGUEDAD_ANIOS", precision=2)
	private BigDecimal nuAntiguedadAnios;

	@Column(name="NU_DIAS_SALARIO", precision=2)
	private BigDecimal nuDiasSalario;

	@Column(name="NU_FACTOR_INTEGRACION", precision=6, scale=4)
	private BigDecimal nuFactorIntegracion;

	@Column(name="NU_MES", nullable=false, precision=2)
	private BigDecimal nuMes;

	@Column(name="NU_SEGURIDAD_SOCIAL", length=11)
	private String nuSeguridadSocial;

	//bi-directional many-to-one association to FdtRegistroPeriodo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON", nullable=false, insertable=false, updatable=false)
		})
	private FdtRegistroPeriodo fdtRegistroPeriodo;

	//bi-directional many-to-one association to FdtA3MuestraOtroConcepto
	@OneToMany(mappedBy="fdtA3Muestra")
	private List<FdtA3MuestraOtroConcepto> fdtA3MuestraOtroConceptos;

    public FdtA3Muestra() {
    }

	public FdtA3MuestraPK getId() {
		return this.id;
	}

	public void setId(FdtA3MuestraPK id) {
		this.id = id;
	}
	
	public String getApMaternoAsegurado() {
		return this.apMaternoAsegurado;
	}

	public void setApMaternoAsegurado(String apMaternoAsegurado) {
		this.apMaternoAsegurado = apMaternoAsegurado;
	}

	public String getApPaternoAsegurado() {
		return this.apPaternoAsegurado;
	}

	public void setApPaternoAsegurado(String apPaternoAsegurado) {
		this.apPaternoAsegurado = apPaternoAsegurado;
	}

	public BigDecimal getCvGrupo() {
		return this.cvGrupo;
	}

	public void setCvGrupo(BigDecimal cvGrupo) {
		this.cvGrupo = cvGrupo;
	}

	public BigDecimal getImCotizoEymRt() {
		return this.imCotizoEymRt;
	}

	public void setImCotizoEymRt(BigDecimal imCotizoEymRt) {
		this.imCotizoEymRt = imCotizoEymRt;
	}

	public BigDecimal getImCotizoInvVida() {
		return this.imCotizoInvVida;
	}

	public void setImCotizoInvVida(BigDecimal imCotizoInvVida) {
		this.imCotizoInvVida = imCotizoInvVida;
	}

	public BigDecimal getImCuotaDiaria() {
		return this.imCuotaDiaria;
	}

	public void setImCuotaDiaria(BigDecimal imCuotaDiaria) {
		this.imCuotaDiaria = imCuotaDiaria;
	}

	public BigDecimal getImDebiocotizarEymRt() {
		return this.imDebiocotizarEymRt;
	}

	public void setImDebiocotizarEymRt(BigDecimal imDebiocotizarEymRt) {
		this.imDebiocotizarEymRt = imDebiocotizarEymRt;
	}

	public BigDecimal getImDebiocotizarInvVida() {
		return this.imDebiocotizarInvVida;
	}

	public void setImDebiocotizarInvVida(BigDecimal imDebiocotizarInvVida) {
		this.imDebiocotizarInvVida = imDebiocotizarInvVida;
	}

	public String getNombreAsegurado() {
		return this.nombreAsegurado;
	}

	public void setNombreAsegurado(String nombreAsegurado) {
		this.nombreAsegurado = nombreAsegurado;
	}

	public BigDecimal getNuAntiguedadAnios() {
		return this.nuAntiguedadAnios;
	}

	public void setNuAntiguedadAnios(BigDecimal nuAntiguedadAnios) {
		this.nuAntiguedadAnios = nuAntiguedadAnios;
	}

	public BigDecimal getNuDiasSalario() {
		return this.nuDiasSalario;
	}

	public void setNuDiasSalario(BigDecimal nuDiasSalario) {
		this.nuDiasSalario = nuDiasSalario;
	}

	public BigDecimal getNuFactorIntegracion() {
		return this.nuFactorIntegracion;
	}

	public void setNuFactorIntegracion(BigDecimal nuFactorIntegracion) {
		this.nuFactorIntegracion = nuFactorIntegracion;
	}

	public BigDecimal getNuMes() {
		return this.nuMes;
	}

	public void setNuMes(BigDecimal nuMes) {
		this.nuMes = nuMes;
	}

	public String getNuSeguridadSocial() {
		return this.nuSeguridadSocial;
	}

	public void setNuSeguridadSocial(String nuSeguridadSocial) {
		this.nuSeguridadSocial = nuSeguridadSocial;
	}

	public FdtRegistroPeriodo getFdtRegistroPeriodo() {
		return this.fdtRegistroPeriodo;
	}

	public void setFdtRegistroPeriodo(FdtRegistroPeriodo fdtRegistroPeriodo) {
		this.fdtRegistroPeriodo = fdtRegistroPeriodo;
	}
	
	public List<FdtA3MuestraOtroConcepto> getFdtA3MuestraOtroConceptos() {
		return this.fdtA3MuestraOtroConceptos;
	}

	public void setFdtA3MuestraOtroConceptos(List<FdtA3MuestraOtroConcepto> fdtA3MuestraOtroConceptos) {
		this.fdtA3MuestraOtroConceptos = fdtA3MuestraOtroConceptos;
	}
	
}