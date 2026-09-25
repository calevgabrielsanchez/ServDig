package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the APT_COMPONENTES_IMPRESION database table.
 * 
 */
@Entity
@Table(name="APT_COMPONENTES_IMPRESION")
@NamedQuery(name="AptComponentesImpresion.findAll", query="SELECT a FROM AptComponentesImpresion a")
public class AptComponentesImpresion implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AptComponentesImpresionPK id;

	@Column(name="DESC_PARENTESCO")
	private String descParentesco;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_NACIMIENTO")
	private Date fecNacimiento;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_VENCIMIENTO")
	private Date fecVencimiento;

	@Column(name="IMP_MENSUAL")
	private BigDecimal impMensual;

	@Column(name="NOM_COMPLETO")
	private String nomCompleto;

	@Column(name="POR_BENEFICIARIO")
	private BigDecimal porBeneficiario;

	//bi-directional many-to-one association to AptDatosImpresion
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="ID_NSS", referencedColumnName="ID_NSS", insertable=false, updatable=false),
		@JoinColumn(name="ID_SOLICITUD", referencedColumnName="ID_SOLICITUD", insertable=false, updatable=false)
		})
	private AptDatosImpresion aptDatosImpresion;

	public AptComponentesImpresion() {
	}

	public AptComponentesImpresionPK getId() {
		return this.id;
	}

	public void setId(AptComponentesImpresionPK id) {
		this.id = id;
	}

	public String getDescParentesco() {
		return this.descParentesco;
	}

	public void setDescParentesco(String descParentesco) {
		this.descParentesco = descParentesco;
	}

	public Date getFecNacimiento() {
		return this.fecNacimiento;
	}

	public void setFecNacimiento(Date fecNacimiento) {
		this.fecNacimiento = fecNacimiento;
	}

	public Date getFecVencimiento() {
		return this.fecVencimiento;
	}

	public void setFecVencimiento(Date fecVencimiento) {
		this.fecVencimiento = fecVencimiento;
	}

	public BigDecimal getImpMensual() {
		return this.impMensual;
	}

	public void setImpMensual(BigDecimal impMensual) {
		this.impMensual = impMensual;
	}

	public String getNomCompleto() {
		return this.nomCompleto;
	}

	public void setNomCompleto(String nomCompleto) {
		this.nomCompleto = nomCompleto;
	}

	public BigDecimal getPorBeneficiario() {
		return this.porBeneficiario;
	}

	public void setPorBeneficiario(BigDecimal porBeneficiario) {
		this.porBeneficiario = porBeneficiario;
	}

	public AptDatosImpresion getAptDatosImpresion() {
		return this.aptDatosImpresion;
	}

	public void setAptDatosImpresion(AptDatosImpresion aptDatosImpresion) {
		this.aptDatosImpresion = aptDatosImpresion;
	}

}