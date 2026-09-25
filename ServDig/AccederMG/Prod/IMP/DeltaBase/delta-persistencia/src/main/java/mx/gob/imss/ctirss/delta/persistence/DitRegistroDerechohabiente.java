package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DIT_REGISTRO_DERECHOHABIENTE database table.
 * 
 */

@Entity
@Table(name="DIT_REGISTRO_DERECHOHABIENTE")
public class DitRegistroDerechohabiente implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	@SequenceGenerator(name = "SEQ_DITREGISTRODERECHOHABIENTE", sequenceName = "SEQ_DITREGISTRODERECHOHABIENTE")
    @GeneratedValue(generator = "SEQ_DITREGISTRODERECHOHABIENTE")
	@Column(name="CVE_ID_REG_DERECHOHABIENTE")
	private Long cveIdRegDerechohabiente;
	
	@Column(name="DOMICILIO_ID_ASEGURADO")
	private Long domicilioIdAsegurado;

	@Column(name="DOMICILIO_ID_BENEFICIARIO")
	private Long domicilioIdBeneficiario;
	
	@Column(name="CVE_ID_UMF")
	private Long cveIdUmf;

	@Column(name="CVE_ID_DELEGACION")
	private Long cveIdDelegacion;

	@Column(name="CVE_ID_SUBDELEGACION")
	private Long cveIdSubdelegacion;

	@Column(name="CVE_ESTADO_ASEGURADO")
	private Long cveEstadoAsegurado;

	@Column(name="CVE_ESTADO_BENEFICIARIO")
	private Long cveEstadoBeneficiario;

	@Column(name="IND_CONYUGE_MISMO_SEXO")
	private String indConyugeMismoSexo;

	@Column(name="IND_CONCUBINARIO_MISMO_SEXO")
	private String indConcubinarioMismoSexo;
	
	@Column(name="IND_UNION_CIVIL_MISMO_SEXO")
	private String indPersonaUnionCivilMismoSexo;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA", nullable=false)
	private Date fecRegistroAlta;

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NUM_EVALUACION_CUESTIONARIO", precision=22)
	private BigDecimal numEvaluacionCuestionario;

	//bi-directional many-to-one association to DicCalidadParentesco
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_CALIDAD_PARENTESCO" , updatable=false)
	private DicCalidadParentesco dicCalidadParentesco;

	//bi-directional many-to-one association to DicRazonRegistro
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="CVE_RAZON_REGISTRO", updatable=false)
	private DicRazonRegistro dicRazonRegistro;
 


	//bi-directional many-to-one association to DitUsuario
    @ManyToOne
	@JoinColumn(name="CVE_ID_USUARIO" , insertable=false ,updatable=false)
	private DitUsuario ditUsuario;

    //bi-directional many-to-one association to DitDocumentoProbatorio
    @OneToOne(fetch = FetchType.LAZY)
  	@JoinColumn(name="CVE_ID_TRAMITE")
  	private DitTramite ditTramite;	
	
  	
  	public Long getCveIdRegDerechohabiente() {
		return cveIdRegDerechohabiente;
	}

	public void setCveIdRegDerechohabiente(Long cveIdRegDerechohabiente) {
		this.cveIdRegDerechohabiente = cveIdRegDerechohabiente;
	}
  	
  	
	public DitTramite getDitTramite() {
		return ditTramite;
	}

	public void setDitTramite(DitTramite ditTramite) {
		this.ditTramite = ditTramite;
	}

	
	
    public DitRegistroDerechohabiente() {
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

	public BigDecimal getNumEvaluacionCuestionario() {
		return this.numEvaluacionCuestionario;
	}

	public void setNumEvaluacionCuestionario(BigDecimal numEvaluacionCuestionario) {
		this.numEvaluacionCuestionario = numEvaluacionCuestionario;
	}

	public DicCalidadParentesco getDicCalidadParentesco() {
		return this.dicCalidadParentesco;
	}

	public void setDicCalidadParentesco(DicCalidadParentesco dicCalidadParentesco) {
		this.dicCalidadParentesco = dicCalidadParentesco;
	}
	
	public DicRazonRegistro getDicRazonRegistro() {
		return this.dicRazonRegistro;
	}

	public void setDicRazonRegistro(DicRazonRegistro dicRazonRegistro) {
		this.dicRazonRegistro = dicRazonRegistro;
	}

	public DitUsuario getDitUsuario() {
		return this.ditUsuario;
	}

	public void setDitUsuario(DitUsuario ditUsuario) {
		this.ditUsuario = ditUsuario;
	}

	public Long getDomicilioIdAsegurado() {
		return this.domicilioIdAsegurado;
	}

	public void setDomicilioIdAsegurado(Long domicilioIdAsegurado) {
		this.domicilioIdAsegurado = domicilioIdAsegurado;
	}
	
	public Long getDomicilioIdBeneficiario() {
		return this.domicilioIdBeneficiario;
	}

	public void setDomicilioIdBeneficiario(Long domicilioIdBeneficiario) {
		this.domicilioIdBeneficiario = domicilioIdBeneficiario;
	}
	
	public Long getCveIdUmf() {
		return this.cveIdUmf;
	}

	public void setCveIdUmf(Long cveIdUmf) {
		this.cveIdUmf = cveIdUmf;
	}
	
	public Long getCveIdDelegacion() {
		return this.cveIdDelegacion;
	}

	public void setCveIdDelegacion(Long cveIdDelegacion) {
		this.cveIdDelegacion = cveIdDelegacion;
	}
	
	public Long getCveIdSubdelegacion() {
		return this.cveIdSubdelegacion;
	}

	public void setCveIdSubdelegacion(Long cveIdSubdelegacion) {
		this.cveIdSubdelegacion = cveIdSubdelegacion;
	}
	
	public Long getCveEstadoAsegurado() {
		return this.cveEstadoAsegurado;
	}

	public void setCveEstadoAsegurado(Long cveEstadoAsegurado) {
		this.cveEstadoAsegurado = cveEstadoAsegurado;
	}
	
	public Long getCveEstadoBeneficiario() {
		return this.cveEstadoBeneficiario;
	}

	public void setCveEstadoBeneficiario(Long cveEstadoBeneficiario) {
		this.cveEstadoBeneficiario = cveEstadoBeneficiario;
	}
	
	public String getIndConyugeMismoSexo() {
		return this.indConyugeMismoSexo;
	}

	public void setIndConyugeMismoSexo(String indConyugeMismoSexo) {
		this.indConyugeMismoSexo = indConyugeMismoSexo;
	}
	
	public String getIndConcubinarioMismoSexo() {
		return this.indConcubinarioMismoSexo;
	}

	public void setIndConcubinarioMismoSexo(String indConcubinarioMismoSexo) {
		this.indConcubinarioMismoSexo = indConcubinarioMismoSexo;
	}

	public String getIndPersonaUnionCivilMismoSexo() {
		return indPersonaUnionCivilMismoSexo;
	}

	public void setIndPersonaUnionCivilMismoSexo(String indPersonaUnionCivilMismoSexo) {
		this.indPersonaUnionCivilMismoSexo = indPersonaUnionCivilMismoSexo;
	}

}