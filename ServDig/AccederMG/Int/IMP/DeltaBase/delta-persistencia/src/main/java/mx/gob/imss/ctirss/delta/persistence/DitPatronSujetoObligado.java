package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the DIT_PATRON_SUJETO_OBLIGADO database table.
 * 
 */
@Entity
@Table(name = "DIT_PATRON_SUJETO_OBLIGADO")
public class DitPatronSujetoObligado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DIT_PATRON_SUJETO_OBLIGADO_GENERATOR", sequenceName = "SEQ_DITPATRONSUJETOOBLIGADO", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_PATRON_SUJETO_OBLIGADO_GENERATOR")
	@Column(name = "CVE_ID_PATRON_SUJETO_OBLIGADO", unique = true, nullable = false, precision = 22)
	private long cveIdPatronSujetoObligado;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	// bi-directional many-to-one association to DitAsegurado
	@OneToMany(mappedBy = "ditPatronSujetoObligado")
	private List<DitAsegurado> ditAsegurados;

	// bi-directional many-to-one association to DitBiene
	@OneToMany(mappedBy = "ditPatronSujetoObligado")
	private List<DitBiene> ditBienes;

	// bi-directional many-to-one association to DitClasificacion
	@OneToMany(mappedBy = "ditPatronSujetoObligado")
	private List<DitClasificacion> ditClasificacions;

	// bi-directional many-to-one association to DitContratoAseguramiento
	@OneToMany(mappedBy = "ditPatronSujetoObligado")
	private List<DitContratoAseguramiento> ditContratoAseguramientos;

	// bi-directional many-to-one association to DitCuotasPatronSujetoOblig
	@OneToMany(mappedBy = "ditPatronSujetoObligado")
	private List<DitCuotasPatronSujetoOblig> ditCuotasPatronSujetoObligs;

	// bi-directional many-to-one association to DitEquipoTransporte
	@OneToMany(mappedBy = "ditPatronSujetoObligado")
	private List<DitEquipoTransporte> ditEquipoTransportes;

	// bi-directional many-to-one association to DitIndicadorPatSujOblig
	@OneToMany(mappedBy = "ditPatronSujetoObligado")
	private List<DitIndicadorPatSujOblig> ditIndicadorPatSujObligs;

	// bi-directional one-to-one association to DitInstitEducativa
	@OneToOne(mappedBy = "ditPatronSujetoObligado", fetch = FetchType.LAZY)
	private DitInstitEducativa ditInstitEducativa;

	// bi-directional many-to-one association to DitMaquinariaEquipo
	@OneToMany(mappedBy = "ditPatronSujetoObligado")
	private List<DitMaquinariaEquipo> ditMaquinariaEquipos;

	// bi-directional many-to-one association to DitMateriaPrimaMaterial
	@OneToMany(mappedBy = "ditPatronSujetoObligado")
	private List<DitMateriaPrimaMaterial> ditMateriaPrimaMaterials;

	// bi-directional many-to-one association to DitMovtosAusentismo
	@OneToMany(mappedBy = "ditPatronSujetoObligado")
	private List<DitMovtosAusentismo> ditMovtosAusentismos;

	// bi-directional many-to-one association to DitMovtoPatSujOblig
	@OneToMany(mappedBy = "ditPatronSujetoObligado")
	private List<DitMovtoPatSujOblig> ditMovtoPatSujObligs;

	// bi-directional many-to-one association to DitPatronCargaArchivo
	@OneToMany(mappedBy = "ditPatronSujetoObligado")
	private List<DitPatronCargaArchivo> ditPatronCargaArchivos;

	// bi-directional many-to-one association to DitPatronGeneral
	@OneToMany(mappedBy = "ditPatronSujetoObligado")
	private List<DitPatronGeneral> ditPatronGenerals;

	// bi-directional many-to-one association to DitTipoContribModalidad
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_TIPO_CONTRIB_MODALIDAD")
	private DitTipoContribModalidad ditTipoContribModalidad;

	// bi-directional many-to-one association to DicModalidad
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_MODALIDAD")
	private DicModalidad dicModalidad;

	// bi-directional many-to-one association to DicTipoPagoModalidad
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_TIPO_PAGO_MODALIDAD")
	private DicTipoPagoModalidad dicTipoPagoModalidad;

	// bi-directional many-to-one association to DitPersonaFisica
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_PERSONA_FISICA")
	private DitPersonaFisica ditPersonaFisica;

	// bi-directional many-to-one association to DitPersonaMoral
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_PERSONA_MORAL")
	private DitPersonaMoral ditPersonaMoral;

	// bi-directional many-to-one association to DicTipoInteresado
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_TIPO_INTERESADO")
	private DicTipoInteresado dicTipoInteresado;

	// bi-directional many-to-one association to DitPatSujObligContacto
	@OneToMany(mappedBy = "ditPatronSujetoObligado")
	private List<DitPatSujObligContacto> ditPatSujObligContactos;

	// bi-directional many-to-one association to DitPatSujObligDomicilio
	@OneToMany(mappedBy = "ditPatronSujetoObligado")
	private List<DitPatSujObligDomicilio> ditPatSujObligDomicilios;

	// bi-directional many-to-one association to DitPersonal
	@OneToMany(mappedBy = "ditPatronSujetoObligado")
	private List<DitPersonal> ditPersonals;

	// bi-directional many-to-one association to DitPresentadorAviso
	@OneToMany(mappedBy = "ditPatronSujetoObligado")
	private List<DitPresentadorAviso> ditPresentadorAvisos;

	// bi-directional many-to-one association to DitProceso
	@OneToMany(mappedBy = "ditPatronSujetoObligado")
	private List<DitProceso> ditProcesos;

	// bi-directional many-to-one association to DitProducto
	@OneToMany(mappedBy = "ditPatronSujetoObligado")
	private List<DitProducto> ditProductos;

	// bi-directional many-to-one association to DitRepresentanteLegal
	@OneToMany(mappedBy = "ditPatronSujetoObligado")
	private List<DitRepresentanteLegal> ditRepresentanteLegals;

	
	// bi-directional many-to-one association to DitUsuarioOrdinario
	@OneToMany(mappedBy = "ditPatronSujetoObligado")
	private List<DitUsuarioOrdinario> ditUsuarioOrdinarios;
	
	//bi-directional many-to-one association to DitCentroTrabajoContacto
	@OneToMany(mappedBy="ditPatronSujetoObligado")
	private List<DitCentroTrabajoContacto> ditCentroTrabajoContactos;

	//bi-directional many-to-one association to DitRepresentanteLegalContac
//	@OneToMany(mappedBy="ditPatronSujetoObligado")
//	private List<DitRepresentanteLegalContac> ditRepresentanteLegalContacs;
	
	@Column(name="DES_AFECTACION", length=255)
	private String desAfectacion;
	
	@Column(name="DES_USOS_BIENES", length=255)
	private String desUsosBienes;
	
	@Column(name="DES_NOMBRE_COMERCIAL", length=255)
	private String nombreComercial;
	
	//bi-directional one-to-one association to DitDelsubPatSujOblig
	@OneToOne(mappedBy="ditPatronSujetoObligado")
	private DitSubdelPatSujOblig ditSubdelPatSujOblig;
	
	
	@OneToOne(mappedBy = "ditPatronSujetoObligado", fetch = FetchType.LAZY)
	private DitMunicipioPatSujOblig ditMunicipioPatSujOblig;
	
	//bi-directional many-to-many association to DitDocumentoProbatorio
    @ManyToMany
	@JoinTable(
		name="DIT_DOCTOS_PAT_SUJ_OBLIG"
		, joinColumns={
			@JoinColumn(name="CVE_ID_PATRON_SUJETO_OBLIGADO")
			}
		, inverseJoinColumns={
			@JoinColumn(name="CVE_ID_DOCUMENTO_PROBATORIO")
			}
		)
	private List<DitDocumentoProbatorio> ditDocumentoProbatorios;

	//bi-directional one-to-one association to DitPatSujObligDomMigr
	@OneToOne(mappedBy="ditPatronSujetoObligado")
	private DitPatSujObligDomMigr ditPatSujObligDomMigr;

	@Column(name = "IND_PATRON_CONFIRMADO")
	private Integer indPatronConfirmado;
	
	@Column(name = "IND_MIGR_DOM")
	private Integer indMigrDom;

	

	//bi-directional many-to-one association to DitLlavePatron
	@OneToMany(mappedBy="ditPatronSujetoObligado" , fetch=FetchType.LAZY)
	private List<DitLlavePatron> ditLlavePatrones;
    
	// bi-directional many-to-one association to DitPatSujObligBeneficio
	@OneToMany(mappedBy = "ditPatronSujetoObligado", fetch = FetchType.LAZY)
	private List<DitPatSujObligBeneficio> ditPatSujObligBeneficios;
	
	public DitPatronSujetoObligado() {
	}

	public long getCveIdPatronSujetoObligado() {
		return this.cveIdPatronSujetoObligado;
	}

	public void setCveIdPatronSujetoObligado(long cveIdPatronSujetoObligado) {
		this.cveIdPatronSujetoObligado = cveIdPatronSujetoObligado;
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

	public List<DitAsegurado> getDitAsegurados() {
		return this.ditAsegurados;
	}

	public void setDitAsegurados(List<DitAsegurado> ditAsegurados) {
		this.ditAsegurados = ditAsegurados;
	}

	public List<DitBiene> getDitBienes() {
		return this.ditBienes;
	}

	public void setDitBienes(List<DitBiene> ditBienes) {
		this.ditBienes = ditBienes;
	}

	public List<DitClasificacion> getDitClasificacions() {
		return this.ditClasificacions;
	}

	public void setDitClasificacions(List<DitClasificacion> ditClasificacions) {
		this.ditClasificacions = ditClasificacions;
	}

	public List<DitContratoAseguramiento> getDitContratoAseguramientos() {
		return this.ditContratoAseguramientos;
	}

	public void setDitContratoAseguramientos(
			List<DitContratoAseguramiento> ditContratoAseguramientos) {
		this.ditContratoAseguramientos = ditContratoAseguramientos;
	}

	public List<DitCuotasPatronSujetoOblig> getDitCuotasPatronSujetoObligs() {
		return this.ditCuotasPatronSujetoObligs;
	}

	public void setDitCuotasPatronSujetoObligs(
			List<DitCuotasPatronSujetoOblig> ditCuotasPatronSujetoObligs) {
		this.ditCuotasPatronSujetoObligs = ditCuotasPatronSujetoObligs;
	}

	public List<DitEquipoTransporte> getDitEquipoTransportes() {
		return this.ditEquipoTransportes;
	}

	public void setDitEquipoTransportes(
			List<DitEquipoTransporte> ditEquipoTransportes) {
		this.ditEquipoTransportes = ditEquipoTransportes;
	}

	public List<DitIndicadorPatSujOblig> getDitIndicadorPatSujObligs() {
		return this.ditIndicadorPatSujObligs;
	}

	public void setDitIndicadorPatSujObligs(
			List<DitIndicadorPatSujOblig> ditIndicadorPatSujObligs) {
		this.ditIndicadorPatSujObligs = ditIndicadorPatSujObligs;
	}

	public DitInstitEducativa getDitInstitEducativa() {
		return this.ditInstitEducativa;
	}

	public void setDitInstitEducativa(DitInstitEducativa ditInstitEducativa) {
		this.ditInstitEducativa = ditInstitEducativa;
	}

	public List<DitMaquinariaEquipo> getDitMaquinariaEquipos() {
		return this.ditMaquinariaEquipos;
	}

	public void setDitMaquinariaEquipos(
			List<DitMaquinariaEquipo> ditMaquinariaEquipos) {
		this.ditMaquinariaEquipos = ditMaquinariaEquipos;
	}

	public List<DitMateriaPrimaMaterial> getDitMateriaPrimaMaterials() {
		return this.ditMateriaPrimaMaterials;
	}

	public void setDitMateriaPrimaMaterials(
			List<DitMateriaPrimaMaterial> ditMateriaPrimaMaterials) {
		this.ditMateriaPrimaMaterials = ditMateriaPrimaMaterials;
	}

	public List<DitMovtosAusentismo> getDitMovtosAusentismos() {
		return this.ditMovtosAusentismos;
	}

	public void setDitMovtosAusentismos(
			List<DitMovtosAusentismo> ditMovtosAusentismos) {
		this.ditMovtosAusentismos = ditMovtosAusentismos;
	}

	public List<DitMovtoPatSujOblig> getDitMovtoPatSujObligs() {
		return this.ditMovtoPatSujObligs;
	}

	public void setDitMovtoPatSujObligs(
			List<DitMovtoPatSujOblig> ditMovtoPatSujObligs) {
		this.ditMovtoPatSujObligs = ditMovtoPatSujObligs;
	}

	public List<DitPatronCargaArchivo> getDitPatronCargaArchivos() {
		return this.ditPatronCargaArchivos;
	}

	public void setDitPatronCargaArchivos(
			List<DitPatronCargaArchivo> ditPatronCargaArchivos) {
		this.ditPatronCargaArchivos = ditPatronCargaArchivos;
	}

	public List<DitPatronGeneral> getDitPatronGenerals() {
		return this.ditPatronGenerals;
	}

	public void setDitPatronGenerals(List<DitPatronGeneral> ditPatronGenerals) {
		this.ditPatronGenerals = ditPatronGenerals;
	}

	public DitTipoContribModalidad getDitTipoContribModalidad() {
		return this.ditTipoContribModalidad;
	}

	public void setDitTipoContribModalidad(
			DitTipoContribModalidad ditTipoContribModalidad) {
		this.ditTipoContribModalidad = ditTipoContribModalidad;
	}

	public DicModalidad getDicModalidad() {
		return this.dicModalidad;
	}

	public void setDicModalidad(DicModalidad dicModalidad) {
		this.dicModalidad = dicModalidad;
	}

	public DicTipoPagoModalidad getDicTipoPagoModalidad() {
		return this.dicTipoPagoModalidad;
	}

	public void setDicTipoPagoModalidad(
			DicTipoPagoModalidad dicTipoPagoModalidad) {
		this.dicTipoPagoModalidad = dicTipoPagoModalidad;
	}

	public DitPersonaFisica getDitPersonaFisica() {
		return this.ditPersonaFisica;
	}

	public void setDitPersonaFisica(DitPersonaFisica ditPersonaFisica) {
		this.ditPersonaFisica = ditPersonaFisica;
	}

	public DitPersonaMoral getDitPersonaMoral() {
		return this.ditPersonaMoral;
	}

	public void setDitPersonaMoral(DitPersonaMoral ditPersonaMoral) {
		this.ditPersonaMoral = ditPersonaMoral;
	}

	public DicTipoInteresado getDicTipoInteresado() {
		return this.dicTipoInteresado;
	}

	public void setDicTipoInteresado(DicTipoInteresado dicTipoInteresado) {
		this.dicTipoInteresado = dicTipoInteresado;
	}

	public List<DitPatSujObligContacto> getDitPatSujObligContactos() {
		return this.ditPatSujObligContactos;
	}

	public void setDitPatSujObligContactos(
			List<DitPatSujObligContacto> ditPatSujObligContactos) {
		this.ditPatSujObligContactos = ditPatSujObligContactos;
	}

	public List<DitPatSujObligDomicilio> getDitPatSujObligDomicilios() {
		return this.ditPatSujObligDomicilios;
	}

	public void setDitPatSujObligDomicilios(
			List<DitPatSujObligDomicilio> ditPatSujObligDomicilios) {
		this.ditPatSujObligDomicilios = ditPatSujObligDomicilios;
	}

	public List<DitPersonal> getDitPersonals() {
		return this.ditPersonals;
	}

	public void setDitPersonals(List<DitPersonal> ditPersonals) {
		this.ditPersonals = ditPersonals;
	}

	public List<DitPresentadorAviso> getDitPresentadorAvisos() {
		return this.ditPresentadorAvisos;
	}

	public void setDitPresentadorAvisos(
			List<DitPresentadorAviso> ditPresentadorAvisos) {
		this.ditPresentadorAvisos = ditPresentadorAvisos;
	}

	public List<DitProceso> getDitProcesos() {
		return this.ditProcesos;
	}

	public void setDitProcesos(List<DitProceso> ditProcesos) {
		this.ditProcesos = ditProcesos;
	}

	public List<DitProducto> getDitProductos() {
		return this.ditProductos;
	}

	public void setDitProductos(List<DitProducto> ditProductos) {
		this.ditProductos = ditProductos;
	}

	public List<DitRepresentanteLegal> getDitRepresentanteLegals() {
		return this.ditRepresentanteLegals;
	}

	public void setDitRepresentanteLegals(
			List<DitRepresentanteLegal> ditRepresentanteLegals) {
		this.ditRepresentanteLegals = ditRepresentanteLegals;
	}

	public List<DitUsuarioOrdinario> getDitUsuarioOrdinarios() {
		return this.ditUsuarioOrdinarios;
	}

	public void setDitUsuarioOrdinarios(
			List<DitUsuarioOrdinario> ditUsuarioOrdinarios) {
		this.ditUsuarioOrdinarios = ditUsuarioOrdinarios;
	}

	public List<DitCentroTrabajoContacto> getDitCentroTrabajoContactos() {
		return ditCentroTrabajoContactos;
	}

	public void setDitCentroTrabajoContactos(
			List<DitCentroTrabajoContacto> ditCentroTrabajoContactos) {
		this.ditCentroTrabajoContactos = ditCentroTrabajoContactos;
	}

	public String getDesAfectacion() {
		return desAfectacion;
	}

	public void setDesAfectacion(String desAfectacion) {
		this.desAfectacion = desAfectacion;
	}

	public String getDesUsosBienes() {
		return desUsosBienes;
	}

	public void setDesUsosBienes(String desUsosBienes) {
		this.desUsosBienes = desUsosBienes;
	}

	public String getNombreComercial() {
		return nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	public DitSubdelPatSujOblig getDitSubdelPatSujOblig() {
		return ditSubdelPatSujOblig;
	}

	public void setDitSubdelPatSujOblig(DitSubdelPatSujOblig ditSubdelPatSujOblig) {
		this.ditSubdelPatSujOblig = ditSubdelPatSujOblig;
	}

	public List<DitDocumentoProbatorio> getDitDocumentoProbatorios() {
		return ditDocumentoProbatorios;
	}

	public void setDitDocumentoProbatorios(
			List<DitDocumentoProbatorio> ditDocumentoProbatorios) {
		this.ditDocumentoProbatorios = ditDocumentoProbatorios;
	}

	public DitPatSujObligDomMigr getDitPatSujObligDomMigr() {
		return ditPatSujObligDomMigr;
	}

	public void setDitPatSujObligDomMigr(DitPatSujObligDomMigr ditPatSujObligDomMigr) {
		this.ditPatSujObligDomMigr = ditPatSujObligDomMigr;
	}

	public DitMunicipioPatSujOblig getDitMunicipioPatSujOblig() {
		return ditMunicipioPatSujOblig;
	}

	public void setDitMunicipioPatSujOblig(
			DitMunicipioPatSujOblig ditMunicipioPatSujOblig) {
		this.ditMunicipioPatSujOblig = ditMunicipioPatSujOblig;
	}

	public Integer getIndPatronConfirmado() {
		return indPatronConfirmado;
	}

	public void setIndPatronConfirmado(Integer indPatronConfirmado) {
		this.indPatronConfirmado = indPatronConfirmado;
	}

	public List<DitLlavePatron> getDitLlavePatrones() {
		return ditLlavePatrones;
	}

	public void setDitLlavePatrones(List<DitLlavePatron> ditLlavePatrones) {
		this.ditLlavePatrones = ditLlavePatrones;
	}
	
	public List<DitPatSujObligBeneficio> getDitPatSujObligBeneficios() {
		return this.ditPatSujObligBeneficios;
	}

	public void setDitPatSujObligBeneficios(
			List<DitPatSujObligBeneficio> ditPatSujObligBeneficios) {
		this.ditPatSujObligBeneficios = ditPatSujObligBeneficios;
	}
	
	public Integer getIndMigrDom() {
		return indMigrDom;
	}

	public void setIndMigrDom(Integer indMigrDom) {
		this.indMigrDom = indMigrDom;
	}

}