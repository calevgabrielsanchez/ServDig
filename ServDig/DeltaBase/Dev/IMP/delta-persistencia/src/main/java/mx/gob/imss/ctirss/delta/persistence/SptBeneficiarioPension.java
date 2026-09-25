package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the SPT_BENEFICIARIO_PENSION database table.
 * 
 */
@Entity
@Table(name="SPT_BENEFICIARIO_PENSION")
@NamedQuery(name="SptBeneficiarioPension.findAll", query="SELECT s FROM SptBeneficiarioPension s")
public class SptBeneficiarioPension implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTBENEFICIARIOPENSION", sequenceName = "SEQ_SPTBENEFICIARIOPENSION")
	@GeneratedValue(generator = "SEQ_SPTBENEFICIARIOPENSION")
	@Column(name="CVE_ID_BENEFICIARIO_PENSION")
	private long cveIdBeneficiarioPension;

	@Column(name="CVE_ID_ASIGNACION_NSS")
	private BigDecimal cveIdAsignacionNss;

	@Column(name="CVE_ID_PERSONA")
	private BigDecimal cveIdPersona;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_MOVIMIENTO")
	private Date fecMovimiento;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="ID_INCIDENCIA")
	private String idIncidencia;

	//bi-directional many-to-one association to SptBeneficiariopensionPensio
	@OneToMany(mappedBy="sptBeneficiarioPension")
	private List<SptBeneficiariopensionPensio> sptBeneficiariopensionPensios;

	//bi-directional many-to-one association to SptGrupoFamiliarPension
	@ManyToOne
	@JoinColumn(name="CVE_ID_GRUPO_FAMILIAR_PENSION")
	private SptGrupoFamiliarPension sptGrupoFamiliarPension;

	//bi-directional many-to-one association to SptBeneficiarioSolicitud
	//@OneToMany(mappedBy="sptBeneficiarioPension")
	//private List<SptBeneficiarioSolicitud> sptBeneficiarioSolicituds;

	//bi-directional many-to-one association to SptBenefPensDetEstud
	@OneToMany(mappedBy="sptBeneficiarioPension")
	private List<SptBenefPensDetEstud> sptBenefPensDetEstuds;

	//bi-directional many-to-one association to SptBenefPensDetImp
	@OneToMany(mappedBy="sptBeneficiarioPension")
	private List<SptBenefPensDetImp> sptBenefPensDetImps;

	//bi-directional many-to-one association to SptBenefPensDetSpe
	@OneToMany(mappedBy="sptBeneficiarioPension")
	private List<SptBenefPensDetSpe> sptBenefPensDetSpes;

	//bi-directional many-to-one association to SptBenefPensDictSt6
	//@OneToMany(mappedBy="sptBeneficiarioPension")
	//private List<SptBenefPensDictSt6> sptBenefPensDictSt6s;

	//bi-directional many-to-one association to SptDocProbBenefSolicBenef
	@OneToMany(mappedBy="sptBeneficiarioPension")
	private List<SptDocProbBenefSolicBenef> sptDocProbBenefSolicBenefs;

	public SptBeneficiarioPension() {
	}

	public long getCveIdBeneficiarioPension() {
		return this.cveIdBeneficiarioPension;
	}

	public void setCveIdBeneficiarioPension(long cveIdBeneficiarioPension) {
		this.cveIdBeneficiarioPension = cveIdBeneficiarioPension;
	}

	public BigDecimal getCveIdAsignacionNss() {
		return this.cveIdAsignacionNss;
	}

	public void setCveIdAsignacionNss(BigDecimal cveIdAsignacionNss) {
		this.cveIdAsignacionNss = cveIdAsignacionNss;
	}

	public BigDecimal getCveIdPersona() {
		return this.cveIdPersona;
	}

	public void setCveIdPersona(BigDecimal cveIdPersona) {
		this.cveIdPersona = cveIdPersona;
	}

	public Date getFecMovimiento() {
		return this.fecMovimiento;
	}

	public void setFecMovimiento(Date fecMovimiento) {
		this.fecMovimiento = fecMovimiento;
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

	public String getIdIncidencia() {
		return this.idIncidencia;
	}

	public void setIdIncidencia(String idIncidencia) {
		this.idIncidencia = idIncidencia;
	}

	public List<SptBeneficiariopensionPensio> getSptBeneficiariopensionPensios() {
		return this.sptBeneficiariopensionPensios;
	}

	public void setSptBeneficiariopensionPensios(List<SptBeneficiariopensionPensio> sptBeneficiariopensionPensios) {
		this.sptBeneficiariopensionPensios = sptBeneficiariopensionPensios;
	}

	public SptBeneficiariopensionPensio addSptBeneficiariopensionPensio(SptBeneficiariopensionPensio sptBeneficiariopensionPensio) {
		getSptBeneficiariopensionPensios().add(sptBeneficiariopensionPensio);
		sptBeneficiariopensionPensio.setSptBeneficiarioPension(this);

		return sptBeneficiariopensionPensio;
	}

	public SptBeneficiariopensionPensio removeSptBeneficiariopensionPensio(SptBeneficiariopensionPensio sptBeneficiariopensionPensio) {
		getSptBeneficiariopensionPensios().remove(sptBeneficiariopensionPensio);
		sptBeneficiariopensionPensio.setSptBeneficiarioPension(null);

		return sptBeneficiariopensionPensio;
	}

	public SptGrupoFamiliarPension getSptGrupoFamiliarPension() {
		return this.sptGrupoFamiliarPension;
	}

	public void setSptGrupoFamiliarPension(SptGrupoFamiliarPension sptGrupoFamiliarPension) {
		this.sptGrupoFamiliarPension = sptGrupoFamiliarPension;
	}

//	public List<SptBeneficiarioSolicitud> getSptBeneficiarioSolicituds() {
//		return this.sptBeneficiarioSolicituds;
//	}
//
//	public void setSptBeneficiarioSolicituds(List<SptBeneficiarioSolicitud> sptBeneficiarioSolicituds) {
//		this.sptBeneficiarioSolicituds = sptBeneficiarioSolicituds;
//	}

//	public SptBeneficiarioSolicitud addSptBeneficiarioSolicitud(SptBeneficiarioSolicitud sptBeneficiarioSolicitud) {
//		getSptBeneficiarioSolicituds().add(sptBeneficiarioSolicitud);
//		//sptBeneficiarioSolicitud.setSptBeneficiarioPension(this);
//
//		return sptBeneficiarioSolicitud;
//	}
//
//	public SptBeneficiarioSolicitud removeSptBeneficiarioSolicitud(SptBeneficiarioSolicitud sptBeneficiarioSolicitud) {
//		getSptBeneficiarioSolicituds().remove(sptBeneficiarioSolicitud);
//		//sptBeneficiarioSolicitud.setSptBeneficiarioPension(null);
//
//		return sptBeneficiarioSolicitud;
//	}

	public List<SptBenefPensDetEstud> getSptBenefPensDetEstuds() {
		return this.sptBenefPensDetEstuds;
	}

	public void setSptBenefPensDetEstuds(List<SptBenefPensDetEstud> sptBenefPensDetEstuds) {
		this.sptBenefPensDetEstuds = sptBenefPensDetEstuds;
	}

	public SptBenefPensDetEstud addSptBenefPensDetEstud(SptBenefPensDetEstud sptBenefPensDetEstud) {
		getSptBenefPensDetEstuds().add(sptBenefPensDetEstud);
		sptBenefPensDetEstud.setSptBeneficiarioPension(this);

		return sptBenefPensDetEstud;
	}

	public SptBenefPensDetEstud removeSptBenefPensDetEstud(SptBenefPensDetEstud sptBenefPensDetEstud) {
		getSptBenefPensDetEstuds().remove(sptBenefPensDetEstud);
		sptBenefPensDetEstud.setSptBeneficiarioPension(null);

		return sptBenefPensDetEstud;
	}

	public List<SptBenefPensDetImp> getSptBenefPensDetImps() {
		return this.sptBenefPensDetImps;
	}

	public void setSptBenefPensDetImps(List<SptBenefPensDetImp> sptBenefPensDetImps) {
		this.sptBenefPensDetImps = sptBenefPensDetImps;
	}

	public SptBenefPensDetImp addSptBenefPensDetImp(SptBenefPensDetImp sptBenefPensDetImp) {
		getSptBenefPensDetImps().add(sptBenefPensDetImp);
		sptBenefPensDetImp.setSptBeneficiarioPension(this);

		return sptBenefPensDetImp;
	}

	public SptBenefPensDetImp removeSptBenefPensDetImp(SptBenefPensDetImp sptBenefPensDetImp) {
		getSptBenefPensDetImps().remove(sptBenefPensDetImp);
		sptBenefPensDetImp.setSptBeneficiarioPension(null);

		return sptBenefPensDetImp;
	}

	public List<SptBenefPensDetSpe> getSptBenefPensDetSpes() {
		return this.sptBenefPensDetSpes;
	}

	public void setSptBenefPensDetSpes(List<SptBenefPensDetSpe> sptBenefPensDetSpes) {
		this.sptBenefPensDetSpes = sptBenefPensDetSpes;
	}

	public SptBenefPensDetSpe addSptBenefPensDetSpe(SptBenefPensDetSpe sptBenefPensDetSpe) {
		getSptBenefPensDetSpes().add(sptBenefPensDetSpe);
		sptBenefPensDetSpe.setSptBeneficiarioPension(this);

		return sptBenefPensDetSpe;
	}

	public SptBenefPensDetSpe removeSptBenefPensDetSpe(SptBenefPensDetSpe sptBenefPensDetSpe) {
		getSptBenefPensDetSpes().remove(sptBenefPensDetSpe);
		sptBenefPensDetSpe.setSptBeneficiarioPension(null);

		return sptBenefPensDetSpe;
	}

//	public List<SptBenefPensDictSt6> getSptBenefPensDictSt6s() {
//		return this.sptBenefPensDictSt6s;
//	}
//
//	public void setSptBenefPensDictSt6s(List<SptBenefPensDictSt6> sptBenefPensDictSt6s) {
//		this.sptBenefPensDictSt6s = sptBenefPensDictSt6s;
//	}
//
//	public SptBenefPensDictSt6 addSptBenefPensDictSt6(SptBenefPensDictSt6 sptBenefPensDictSt6) {
//		getSptBenefPensDictSt6s().add(sptBenefPensDictSt6);
//		sptBenefPensDictSt6.setSptBeneficiarioPension(this);
//
//		return sptBenefPensDictSt6;
//	}
//
//	public SptBenefPensDictSt6 removeSptBenefPensDictSt6(SptBenefPensDictSt6 sptBenefPensDictSt6) {
//		getSptBenefPensDictSt6s().remove(sptBenefPensDictSt6);
//		sptBenefPensDictSt6.setSptBeneficiarioPension(null);
//
//		return sptBenefPensDictSt6;
//	}

	public List<SptDocProbBenefSolicBenef> getSptDocProbBenefSolicBenefs() {
		return this.sptDocProbBenefSolicBenefs;
	}

	public void setSptDocProbBenefSolicBenefs(List<SptDocProbBenefSolicBenef> sptDocProbBenefSolicBenefs) {
		this.sptDocProbBenefSolicBenefs = sptDocProbBenefSolicBenefs;
	}

	public SptDocProbBenefSolicBenef addSptDocProbBenefSolicBenef(SptDocProbBenefSolicBenef sptDocProbBenefSolicBenef) {
		getSptDocProbBenefSolicBenefs().add(sptDocProbBenefSolicBenef);
		sptDocProbBenefSolicBenef.setSptBeneficiarioPension(this);

		return sptDocProbBenefSolicBenef;
	}

	public SptDocProbBenefSolicBenef removeSptDocProbBenefSolicBenef(SptDocProbBenefSolicBenef sptDocProbBenefSolicBenef) {
		getSptDocProbBenefSolicBenefs().remove(sptDocProbBenefSolicBenef);
		sptDocProbBenefSolicBenef.setSptBeneficiarioPension(null);

		return sptDocProbBenefSolicBenef;
	}

}