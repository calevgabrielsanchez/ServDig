package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the SPT_BENEFICIARIO_SOLICITUD database table.
 * 
 */
@Entity
@Table(name="SPT_BENEFICIARIO_SOLICITUD")
@NamedQuery(name="SptBeneficiarioSolicitud.findAll", query="SELECT s FROM SptBeneficiarioSolicitud s")
public class SptBeneficiarioSolicitud implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id	
	@SequenceGenerator(name = "SEQ_SPTBENEFICIARIOSOLICITUD", sequenceName = "SEQ_SPTBENEFICIARIOSOLICITUD")
	@GeneratedValue(generator = "SEQ_SPTBENEFICIARIOSOLICITUD")
	@Column(name="CVE_ID_BENEFICIARIO_SOLICITUD")
	private long cveIdBeneficiarioSolicitud;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to AptCalculoCuantiasBeneficia
	@OneToMany(mappedBy="sptBeneficiarioSolicitud")
	private List<AptCalculoCuantiasBeneficia> aptCalculoCuantiasBeneficias;

	//bi-directional many-to-one association to AptDetPrevalidBenefEnv
	@OneToMany(mappedBy="sptBeneficiarioSolicitud")
	private List<AptDetPrevalidBenefEnv> aptDetPrevalidBenefEnvs;

	//bi-directional many-to-one association to SptBeneficiarioPension
//	@ManyToOne
//	@JoinColumn(name="CVE_ID_BENEFICIARIO_PENSION")
//	private SptBeneficiarioPension sptBeneficiarioPension;

	@ManyToOne
	@JoinColumn(name="CVE_ID_GRUPO_FAMILIAR_PENSION")
	private SptGrupoFamiliarPension sptGrupoFamiliarPension;
	
	@ManyToOne
	@JoinColumn(name="CVE_ID_ASIGNACION_NSS")
	private DitAsignacionNss ditAsignacionNss;
	
	//bi-directional many-to-one association to SptTramitePension
	@ManyToOne
	@JoinColumn(name="CVE_ID_TRAMITE_PENSION")
	private SptTramitePension sptTramitePension;

	@ManyToOne
	@JoinColumn(name="ID_INCIDENCIA")	
	private SpcIncidencia spcIncidencia;
	
	@Column(name="IND_TITULAR_GRUPO")
	private String indTitularGrupo;
	
	@Column(name="IND_TRAMITE_PROCESO")
	private String indTramiteProceso;
	
	@ManyToOne
	@JoinColumn(name="CVE_ID_PERSONA_INTEGRANTE")	
	private DitPersona ditPersona;
	
	

	
	
	


	//bi-directional many-to-one association to SptBenefPensDetEstud
	@OneToMany(mappedBy="sptBeneficiarioSolicitud")
	private List<SptBenefPensDetEstud> sptBenefPensDetEstuds;

	//bi-directional many-to-one association to SptBenefPensDetImp
	@OneToMany(mappedBy="sptBeneficiarioSolicitud")
	private List<SptBenefPensDetImp> sptBenefPensDetImps;

	//bi-directional many-to-one association to SptBenefPensDetSpe
	@OneToMany(mappedBy="sptBeneficiarioSolicitud")
	private List<SptBenefPensDetSpe> sptBenefPensDetSpes;

	//bi-directional many-to-one association to SptBenefPensDictSt6
	@OneToMany(mappedBy="sptBeneficiarioSolicitud")
	private List<SptBenefPensDictSt6> sptBenefPensDictSt6s;

	//bi-directional many-to-one association to SptDocProbBenefSolicBenef
	//@OneToMany(mappedBy="sptBeneficiarioSolicitud")
	//private List<SptDocProbBenefSolicBenef> sptDocProbBenefSolicBenefs;
	//FIXME no despliega esta relacion
	
	
	@Column(name="ID_COMPONENTE")
	private String idComponente;
	
	
	public SptBeneficiarioSolicitud() {
	}

	public long getCveIdBeneficiarioSolicitud() {
		return this.cveIdBeneficiarioSolicitud;
	}

	public void setCveIdBeneficiarioSolicitud(long cveIdBeneficiarioSolicitud) {
		this.cveIdBeneficiarioSolicitud = cveIdBeneficiarioSolicitud;
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

	public List<AptCalculoCuantiasBeneficia> getAptCalculoCuantiasBeneficias() {
		return this.aptCalculoCuantiasBeneficias;
	}

	public void setAptCalculoCuantiasBeneficias(List<AptCalculoCuantiasBeneficia> aptCalculoCuantiasBeneficias) {
		this.aptCalculoCuantiasBeneficias = aptCalculoCuantiasBeneficias;
	}

	public AptCalculoCuantiasBeneficia addAptCalculoCuantiasBeneficia(AptCalculoCuantiasBeneficia aptCalculoCuantiasBeneficia) {
		getAptCalculoCuantiasBeneficias().add(aptCalculoCuantiasBeneficia);
		aptCalculoCuantiasBeneficia.setSptBeneficiarioSolicitud(this);

		return aptCalculoCuantiasBeneficia;
	}

	public AptCalculoCuantiasBeneficia removeAptCalculoCuantiasBeneficia(AptCalculoCuantiasBeneficia aptCalculoCuantiasBeneficia) {
		getAptCalculoCuantiasBeneficias().remove(aptCalculoCuantiasBeneficia);
		aptCalculoCuantiasBeneficia.setSptBeneficiarioSolicitud(null);

		return aptCalculoCuantiasBeneficia;
	}

	public List<AptDetPrevalidBenefEnv> getAptDetPrevalidBenefEnvs() {
		return this.aptDetPrevalidBenefEnvs;
	}

	public void setAptDetPrevalidBenefEnvs(List<AptDetPrevalidBenefEnv> aptDetPrevalidBenefEnvs) {
		this.aptDetPrevalidBenefEnvs = aptDetPrevalidBenefEnvs;
	}

	public AptDetPrevalidBenefEnv addAptDetPrevalidBenefEnv(AptDetPrevalidBenefEnv aptDetPrevalidBenefEnv) {
		getAptDetPrevalidBenefEnvs().add(aptDetPrevalidBenefEnv);
		aptDetPrevalidBenefEnv.setSptBeneficiarioSolicitud(this);

		return aptDetPrevalidBenefEnv;
	}

	public AptDetPrevalidBenefEnv removeAptDetPrevalidBenefEnv(AptDetPrevalidBenefEnv aptDetPrevalidBenefEnv) {
		getAptDetPrevalidBenefEnvs().remove(aptDetPrevalidBenefEnv);
		aptDetPrevalidBenefEnv.setSptBeneficiarioSolicitud(null);

		return aptDetPrevalidBenefEnv;
	}

//	public SptBeneficiarioPension getSptBeneficiarioPension() {
//		return this.sptBeneficiarioPension;
//	}
//
//	public void setSptBeneficiarioPension(SptBeneficiarioPension sptBeneficiarioPension) {
//		this.sptBeneficiarioPension = sptBeneficiarioPension;
//	}

	public SptTramitePension getSptTramitePension() {
		return this.sptTramitePension;
	}

	public void setSptTramitePension(SptTramitePension sptTramitePension) {
		this.sptTramitePension = sptTramitePension;
	}

	public SptGrupoFamiliarPension getSptGrupoFamiliarPension() {
		return sptGrupoFamiliarPension;
	}

	public void setSptGrupoFamiliarPension(
			SptGrupoFamiliarPension sptGrupoFamiliarPension) {
		this.sptGrupoFamiliarPension = sptGrupoFamiliarPension;
	}

	public DitAsignacionNss getDitAsignacionNss() {
		return ditAsignacionNss;
	}

	public void setDitAsignacionNss(DitAsignacionNss ditAsignacionNss) {
		this.ditAsignacionNss = ditAsignacionNss;
	}

	public SpcIncidencia getSpcIncidencia() {
		return spcIncidencia;
	}

	public void setSpcIncidencia(SpcIncidencia spcIncidencia) {
		this.spcIncidencia = spcIncidencia;
	}

	public String getIndTitularGrupo() {
		return indTitularGrupo;
	}

	public void setIndTitularGrupo(String indTitularGrupo) {
		this.indTitularGrupo = indTitularGrupo;
	}

	public String getIndTramiteProceso() {
		return indTramiteProceso;
	}

	public void setIndTramiteProceso(String indTramiteProceso) {
		this.indTramiteProceso = indTramiteProceso;
	}

	public DitPersona getDitPersona() {
		return ditPersona;
	}

	public void setDitPersona(DitPersona ditPersona) {
		this.ditPersona = ditPersona;
	}

	public List<SptBenefPensDetEstud> getSptBenefPensDetEstuds() {
		return this.sptBenefPensDetEstuds;
	}

	public void setSptBenefPensDetEstuds(List<SptBenefPensDetEstud> sptBenefPensDetEstuds) {
		this.sptBenefPensDetEstuds = sptBenefPensDetEstuds;
	}
	
	public List<SptBenefPensDetImp> getSptBenefPensDetImps() {
		return this.sptBenefPensDetImps;
	}

	public void setSptBenefPensDetImps(List<SptBenefPensDetImp> sptBenefPensDetImps) {
		this.sptBenefPensDetImps = sptBenefPensDetImps;
	}
	
	public List<SptBenefPensDetSpe> getSptBenefPensDetSpes() {
		return this.sptBenefPensDetSpes;
	}

	public void setSptBenefPensDetSpes(List<SptBenefPensDetSpe> sptBenefPensDetSpes) {
		this.sptBenefPensDetSpes = sptBenefPensDetSpes;
	}
	
	public List<SptBenefPensDictSt6> getSptBenefPensDictSt6s() {
		return this.sptBenefPensDictSt6s;
	}

	public void setSptBenefPensDictSt6s(List<SptBenefPensDictSt6> sptBenefPensDictSt6s) {
		this.sptBenefPensDictSt6s = sptBenefPensDictSt6s;
	}

	public String getIdComponente() {
		return idComponente;
	}

	public void setIdComponente(String idComponente) {
		this.idComponente = idComponente;
	}
	
//	public List<SptDocProbBenefSolicBenef> getSptDocProbBenefSolicBenefs() {
//		return this.sptDocProbBenefSolicBenefs;
//	}
//
//	public void setSptDocProbBenefSolicBenefs(List<SptDocProbBenefSolicBenef> sptDocProbBenefSolicBenefs) {
//		this.sptDocProbBenefSolicBenefs = sptDocProbBenefSolicBenefs;
//	}	
	
	
}
