package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SPT_BENEF_PENS_DET_SPES database table.
 * 
 */
@Entity
@Table(name="SPT_BENEF_PENS_DET_SPES")
@NamedQuery(name="SptBenefPensDetSpe.findAll", query="SELECT s FROM SptBenefPensDetSpe s")
public class SptBenefPensDetSpe implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SPT_BENEF_PENS_DET_SPES_CVEIDBENEFPENSDETSPES_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SPT_BENEF_PENS_DET_SPES_CVEIDBENEFPENSDETSPES_GENERATOR")
	@Column(name="CVE_ID_BENEF_PENS_DET_SPES")
	private long cveIdBenefPensDetSpes;

	@Column(name="DES_SPES_ESTADO_COMPONENTE")
	private String desSpesEstadoComponente;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="ID_COMPONENTE")
	private String idComponente;

	@Column(name="ID_COMPONENTE_SPES")
	private String idComponenteSpes;

	@Column(name="ID_ESTADO_COMPONENTE")
	private String idEstadoComponente;

	@Column(name="IND_FINIQUITO")
	private String indFiniquito;

	@Column(name="IND_IMPRESION")
	private String indImpresion;

	@Column(name="IND_ORIGEN_SPES")
	private BigDecimal indOrigenSpes;

	@Column(name="IND_TITULAR_GRUPO")
	private String indTitularGrupo;

	//bi-directional many-to-one association to SptBeneficiarioPension
	@ManyToOne
	@JoinColumn(name="CVE_ID_BENEFICIARIO_PENSION")
	private SptBeneficiarioPension sptBeneficiarioPension;

	@ManyToOne
	@JoinColumn(name="CVE_ID_BENEFICIARIO_SOLICITUD")
	private SptBeneficiarioSolicitud sptBeneficiarioSolicitud;
	
	public SptBenefPensDetSpe() {
	}

	public long getCveIdBenefPensDetSpes() {
		return this.cveIdBenefPensDetSpes;
	}

	public void setCveIdBenefPensDetSpes(long cveIdBenefPensDetSpes) {
		this.cveIdBenefPensDetSpes = cveIdBenefPensDetSpes;
	}

	public String getDesSpesEstadoComponente() {
		return this.desSpesEstadoComponente;
	}

	public void setDesSpesEstadoComponente(String desSpesEstadoComponente) {
		this.desSpesEstadoComponente = desSpesEstadoComponente;
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

	public String getIdComponente() {
		return this.idComponente;
	}

	public void setIdComponente(String idComponente) {
		this.idComponente = idComponente;
	}

	public String getIdComponenteSpes() {
		return this.idComponenteSpes;
	}

	public void setIdComponenteSpes(String idComponenteSpes) {
		this.idComponenteSpes = idComponenteSpes;
	}

	public String getIdEstadoComponente() {
		return this.idEstadoComponente;
	}

	public void setIdEstadoComponente(String idEstadoComponente) {
		this.idEstadoComponente = idEstadoComponente;
	}

	public String getIndFiniquito() {
		return this.indFiniquito;
	}

	public void setIndFiniquito(String indFiniquito) {
		this.indFiniquito = indFiniquito;
	}

	public String getIndImpresion() {
		return this.indImpresion;
	}

	public void setIndImpresion(String indImpresion) {
		this.indImpresion = indImpresion;
	}

	public BigDecimal getIndOrigenSpes() {
		return this.indOrigenSpes;
	}

	public void setIndOrigenSpes(BigDecimal indOrigenSpes) {
		this.indOrigenSpes = indOrigenSpes;
	}

	public String getIndTitularGrupo() {
		return this.indTitularGrupo;
	}

	public void setIndTitularGrupo(String indTitularGrupo) {
		this.indTitularGrupo = indTitularGrupo;
	}

	public SptBeneficiarioPension getSptBeneficiarioPension() {
		return this.sptBeneficiarioPension;
	}

	public void setSptBeneficiarioPension(SptBeneficiarioPension sptBeneficiarioPension) {
		this.sptBeneficiarioPension = sptBeneficiarioPension;
	}

	public SptBeneficiarioSolicitud getSptBeneficiarioSolicitud() {
		return sptBeneficiarioSolicitud;
	}

	public void setSptBeneficiarioSolicitud(
			SptBeneficiarioSolicitud sptBeneficiarioSolicitud) {
		this.sptBeneficiarioSolicitud = sptBeneficiarioSolicitud;
	}

	
}