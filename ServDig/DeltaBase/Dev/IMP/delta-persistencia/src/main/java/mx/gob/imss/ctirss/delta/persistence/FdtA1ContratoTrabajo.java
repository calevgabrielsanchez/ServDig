package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the FDT_A1_CONTRATO_TRABAJO database table.
 * 
 */
@Entity
@Table(name="FDT_A1_CONTRATO_TRABAJO")
public class FdtA1ContratoTrabajo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FdtA1ContratoTrabajoPK id;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_PERIODO_A")
	private Date fhPeriodoA;

    @Temporal( TemporalType.DATE)
	@Column(name="FH_PERIODO_DE")
	private Date fhPeriodoDe;

	@Column(name="IN_TIPO_CONTRATO", nullable=false, length=1)
	private String inTipoContrato;

	@Column(name="IN_TP_CONTRATACION", nullable=false, length=1)
	private String inTpContratacion;

	@Column(name="TX_ESPECIFICAR", length=50)
	private String txEspecificar;

	@Column(name="TX_SINDICATO", length=100)
	private String txSindicato;

	//bi-directional many-to-one association to FdtPeriodo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_DICTAMEN", nullable=false, insertable=false, updatable=false)
	private FdtPeriodo fdtPeriodo;

	//bi-directional many-to-many association to FdtA3Grupo
//    @ManyToMany
//	@JoinTable(
//		name="FDT_CONTRATO_GRUPO"
//		, joinColumns={
//			@JoinColumn(name="CV_CONTRATO", referencedColumnName="CV_CONTRATO", nullable=false),
//			@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false)
//			}
//		, inverseJoinColumns={
//			@JoinColumn(name="CV_GRUPO", referencedColumnName="CV_GRUPO", nullable=false),
//			@JoinColumn(name="ID_DICTAMEN", referencedColumnName="ID_DICTAMEN", nullable=false)
//			}
//		)
//	private List<FdtA3Grupo> fdtA3Grupos;

    public FdtA1ContratoTrabajo() {
    }

	public FdtA1ContratoTrabajoPK getId() {
		return this.id;
	}

	public void setId(FdtA1ContratoTrabajoPK id) {
		this.id = id;
	}
	
	public Date getFhPeriodoA() {
		return this.fhPeriodoA;
	}

	public void setFhPeriodoA(Date fhPeriodoA) {
		this.fhPeriodoA = fhPeriodoA;
	}

	public Date getFhPeriodoDe() {
		return this.fhPeriodoDe;
	}

	public void setFhPeriodoDe(Date fhPeriodoDe) {
		this.fhPeriodoDe = fhPeriodoDe;
	}

	public String getInTipoContrato() {
		return this.inTipoContrato;
	}

	public void setInTipoContrato(String inTipoContrato) {
		this.inTipoContrato = inTipoContrato;
	}

	public String getInTpContratacion() {
		return this.inTpContratacion;
	}

	public void setInTpContratacion(String inTpContratacion) {
		this.inTpContratacion = inTpContratacion;
	}

	public String getTxEspecificar() {
		return this.txEspecificar;
	}

	public void setTxEspecificar(String txEspecificar) {
		this.txEspecificar = txEspecificar;
	}

	public String getTxSindicato() {
		return this.txSindicato;
	}

	public void setTxSindicato(String txSindicato) {
		this.txSindicato = txSindicato;
	}

	public FdtPeriodo getFdtPeriodo() {
		return this.fdtPeriodo;
	}

	public void setFdtPeriodo(FdtPeriodo fdtPeriodo) {
		this.fdtPeriodo = fdtPeriodo;
	}
	
//	public List<FdtA3Grupo> getFdtA3Grupos() {
//		return this.fdtA3Grupos;
//	}
//
//	public void setFdtA3Grupos(List<FdtA3Grupo> fdtA3Grupos) {
//		this.fdtA3Grupos = fdtA3Grupos;
//	}
	
}