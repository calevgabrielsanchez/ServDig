package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the FDT_A3_GRUPO_FACTORES database table.
 * 
 */
@Entity
@Table(name="FDT_A3_GRUPO_FACTORES")
public class FdtA3GrupoFactore implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtA3GrupoFactorePK id;

	@Column(name="IM_AGUINALDO", precision=12)
	private BigDecimal imAguinaldo;

	@Column(name="IM_PRIMA_VACACIONAL", precision=5, scale=2)
	private BigDecimal imPrimaVacacional;

	@Column(name="NU_ANIOS_SERVICIO", precision=2)
	private BigDecimal nuAniosServicio;

	@Column(name="NU_DIAS_VACACIONES", precision=2)
	private BigDecimal nuDiasVacaciones;

	@Column(name="PC_FACTOR", precision=6, scale=4)
	private BigDecimal pcFactor;

	//bi-directional many-to-one association to FdtA3Grupo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CV_GRUPO", referencedColumnName="CV_GRUPO", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false, insertable=false, updatable=false)
		})
	private FdtA3Grupo fdtA3Grupo;

	//bi-directional many-to-one association to FdtA3GrupoFactorOtro
	@OneToMany(mappedBy="fdtA3GrupoFactore")
	private List<FdtA3GrupoFactorOtro> fdtA3GrupoFactorOtros;

	//bi-directional many-to-many association to FdtRegistroPeriodo
//    @ManyToMany
//	@JoinTable(
//		name="FDT_REGISTRO_FACTOR"
//		, joinColumns={
//			@JoinColumn(name="CV_GRUPO", referencedColumnName="CV_GRUPO", nullable=false),
//			@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false),
//			@JoinColumn(name="NU_FACTORES", referencedColumnName="NU_FACTORES", nullable=false)
//			}
//		, inverseJoinColumns={
//			@JoinColumn(name="CVE_MODAL", referencedColumnName="CVE_MODAL", nullable=false),
//			@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false),
//			@JoinColumn(name="REG_PATRON", referencedColumnName="REG_PATRON", nullable=false)
//			}
//		)
//	private List<FdtRegistroPeriodo> fdtRegistroPeriodos;

    public FdtA3GrupoFactore() {
    }

	public FdtA3GrupoFactorePK getId() {
		return this.id;
	}

	public void setId(FdtA3GrupoFactorePK id) {
		this.id = id;
	}
	
	public BigDecimal getImAguinaldo() {
		return this.imAguinaldo;
	}

	public void setImAguinaldo(BigDecimal imAguinaldo) {
		this.imAguinaldo = imAguinaldo;
	}

	public BigDecimal getImPrimaVacacional() {
		return this.imPrimaVacacional;
	}

	public void setImPrimaVacacional(BigDecimal imPrimaVacacional) {
		this.imPrimaVacacional = imPrimaVacacional;
	}

	public BigDecimal getNuAniosServicio() {
		return this.nuAniosServicio;
	}

	public void setNuAniosServicio(BigDecimal nuAniosServicio) {
		this.nuAniosServicio = nuAniosServicio;
	}

	public BigDecimal getNuDiasVacaciones() {
		return this.nuDiasVacaciones;
	}

	public void setNuDiasVacaciones(BigDecimal nuDiasVacaciones) {
		this.nuDiasVacaciones = nuDiasVacaciones;
	}

	public BigDecimal getPcFactor() {
		return this.pcFactor;
	}

	public void setPcFactor(BigDecimal pcFactor) {
		this.pcFactor = pcFactor;
	}

	public FdtA3Grupo getFdtA3Grupo() {
		return this.fdtA3Grupo;
	}

	public void setFdtA3Grupo(FdtA3Grupo fdtA3Grupo) {
		this.fdtA3Grupo = fdtA3Grupo;
	}
	
	public List<FdtA3GrupoFactorOtro> getFdtA3GrupoFactorOtros() {
		return this.fdtA3GrupoFactorOtros;
	}

	public void setFdtA3GrupoFactorOtros(List<FdtA3GrupoFactorOtro> fdtA3GrupoFactorOtros) {
		this.fdtA3GrupoFactorOtros = fdtA3GrupoFactorOtros;
	}
	
//	public List<FdtRegistroPeriodo> getFdtRegistroPeriodos() {
//		return this.fdtRegistroPeriodos;
//	}
//
//	public void setFdtRegistroPeriodos(List<FdtRegistroPeriodo> fdtRegistroPeriodos) {
//		this.fdtRegistroPeriodos = fdtRegistroPeriodos;
//	}
	
}