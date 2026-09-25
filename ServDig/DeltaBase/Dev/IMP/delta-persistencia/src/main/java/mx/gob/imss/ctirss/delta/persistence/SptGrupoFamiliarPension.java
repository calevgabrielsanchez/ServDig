package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the SPT_GRUPO_FAMILIAR_PENSION database table.
 * 
 */
@Entity
@Table(name="SPT_GRUPO_FAMILIAR_PENSION")
@NamedQuery(name="SptGrupoFamiliarPension.findAll", query="SELECT s FROM SptGrupoFamiliarPension s")
public class SptGrupoFamiliarPension implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTGRUPOFAMILIARPENSION", sequenceName = "SEQ_SPTGRUPOFAMILIARPENSION")
	@GeneratedValue(generator = "SEQ_SPTGRUPOFAMILIARPENSION")
	@Column(name="CVE_ID_GRUPO_FAMILIAR_PENSION")
	private long cveIdGrupoFamiliarPension;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INICIO_AJUSTE")
	private Date fecInicioAjuste;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_MODIFICACION")
	private Date fecModificacion;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_MOVIMIENTO")
	private Date fecMovimiento;

	@Column(name="ID_GRUPO_FAMILIAR")
	private String idGrupoFamiliar;

	@Column(name="ID_INCIDENCIA")
	private String idIncidencia;

	@Column(name="ID_TIPO_MOVIMIENTO")
	private String idTipoMovimiento;

	//bi-directional many-to-one association to SptBeneficiarioPension
	@OneToMany(mappedBy="sptGrupoFamiliarPension")
	private List<SptBeneficiarioPension> sptBeneficiarioPensions;

	//bi-directional many-to-one association to DitAsignacionNss
	@ManyToOne
	@JoinColumn(name="CVE_ID_ASIGNACION_NSS")
	private DitAsignacionNss ditAsignacionNss;

	//bi-directional many-to-one association to SptGrupoFamPensNomina
	@OneToMany(mappedBy="sptGrupoFamiliarPension")
	private List<SptGrupoFamPensNomina> sptGrupoFamPensNominas;

	//bi-directional many-to-one association to SptPersRecibePagoPen
	@OneToMany(mappedBy="sptGrupoFamiliarPension")
	private List<SptPersRecibePagoPen> sptPersRecibePagoPens;

	public SptGrupoFamiliarPension() {
	}

	public long getCveIdGrupoFamiliarPension() {
		return this.cveIdGrupoFamiliarPension;
	}

	public void setCveIdGrupoFamiliarPension(long cveIdGrupoFamiliarPension) {
		this.cveIdGrupoFamiliarPension = cveIdGrupoFamiliarPension;
	}

	public Date getFecInicioAjuste() {
		return this.fecInicioAjuste;
	}

	public void setFecInicioAjuste(Date fecInicioAjuste) {
		this.fecInicioAjuste = fecInicioAjuste;
	}

	public Date getFecModificacion() {
		return this.fecModificacion;
	}

	public void setFecModificacion(Date fecModificacion) {
		this.fecModificacion = fecModificacion;
	}

	public Date getFecMovimiento() {
		return this.fecMovimiento;
	}

	public void setFecMovimiento(Date fecMovimiento) {
		this.fecMovimiento = fecMovimiento;
	}

	public String getIdGrupoFamiliar() {
		return this.idGrupoFamiliar;
	}

	public void setIdGrupoFamiliar(String idGrupoFamiliar) {
		this.idGrupoFamiliar = idGrupoFamiliar;
	}

	public String getIdIncidencia() {
		return this.idIncidencia;
	}

	public void setIdIncidencia(String idIncidencia) {
		this.idIncidencia = idIncidencia;
	}

	public String getIdTipoMovimiento() {
		return this.idTipoMovimiento;
	}

	public void setIdTipoMovimiento(String idTipoMovimiento) {
		this.idTipoMovimiento = idTipoMovimiento;
	}

	public List<SptBeneficiarioPension> getSptBeneficiarioPensions() {
		return this.sptBeneficiarioPensions;
	}

	public void setSptBeneficiarioPensions(List<SptBeneficiarioPension> sptBeneficiarioPensions) {
		this.sptBeneficiarioPensions = sptBeneficiarioPensions;
	}

	public SptBeneficiarioPension addSptBeneficiarioPension(SptBeneficiarioPension sptBeneficiarioPension) {
		getSptBeneficiarioPensions().add(sptBeneficiarioPension);
		sptBeneficiarioPension.setSptGrupoFamiliarPension(this);

		return sptBeneficiarioPension;
	}

	public SptBeneficiarioPension removeSptBeneficiarioPension(SptBeneficiarioPension sptBeneficiarioPension) {
		getSptBeneficiarioPensions().remove(sptBeneficiarioPension);
		sptBeneficiarioPension.setSptGrupoFamiliarPension(null);

		return sptBeneficiarioPension;
	}

	public DitAsignacionNss getDitAsignacionNss() {
		return this.ditAsignacionNss;
	}

	public void setDitAsignacionNss(DitAsignacionNss ditAsignacionNss) {
		this.ditAsignacionNss = ditAsignacionNss;
	}

	public List<SptGrupoFamPensNomina> getSptGrupoFamPensNominas() {
		return this.sptGrupoFamPensNominas;
	}

	public void setSptGrupoFamPensNominas(List<SptGrupoFamPensNomina> sptGrupoFamPensNominas) {
		this.sptGrupoFamPensNominas = sptGrupoFamPensNominas;
	}

	public SptGrupoFamPensNomina addSptGrupoFamPensNomina(SptGrupoFamPensNomina sptGrupoFamPensNomina) {
		getSptGrupoFamPensNominas().add(sptGrupoFamPensNomina);
		sptGrupoFamPensNomina.setSptGrupoFamiliarPension(this);

		return sptGrupoFamPensNomina;
	}

	public SptGrupoFamPensNomina removeSptGrupoFamPensNomina(SptGrupoFamPensNomina sptGrupoFamPensNomina) {
		getSptGrupoFamPensNominas().remove(sptGrupoFamPensNomina);
		sptGrupoFamPensNomina.setSptGrupoFamiliarPension(null);

		return sptGrupoFamPensNomina;
	}

	public List<SptPersRecibePagoPen> getSptPersRecibePagoPens() {
		return this.sptPersRecibePagoPens;
	}

	public void setSptPersRecibePagoPens(List<SptPersRecibePagoPen> sptPersRecibePagoPens) {
		this.sptPersRecibePagoPens = sptPersRecibePagoPens;
	}

	public SptPersRecibePagoPen addSptPersRecibePagoPen(SptPersRecibePagoPen sptPersRecibePagoPen) {
		getSptPersRecibePagoPens().add(sptPersRecibePagoPen);
		sptPersRecibePagoPen.setSptGrupoFamiliarPension(this);

		return sptPersRecibePagoPen;
	}

	public SptPersRecibePagoPen removeSptPersRecibePagoPen(SptPersRecibePagoPen sptPersRecibePagoPen) {
		getSptPersRecibePagoPens().remove(sptPersRecibePagoPen);
		sptPersRecibePagoPen.setSptGrupoFamiliarPension(null);

		return sptPersRecibePagoPen;
	}

}