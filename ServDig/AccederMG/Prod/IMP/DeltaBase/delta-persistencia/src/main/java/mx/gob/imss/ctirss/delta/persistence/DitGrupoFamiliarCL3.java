package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@NamedQueries({ 
	
	@NamedQuery(name = "findGrupoFamiliarNssParentescoCL3", 
			query = "select g from DitGrupoFamiliarCL3 g where g.ditAsignacionNss.numNss=:nss and g.dicCalidadParentesco.cveIdCalidadParentesco=:idParentesco"),
	@NamedQuery(name = "findGrupoFamiliarByNssCL3", 
			query = "select g from DitGrupoFamiliarCL3 g where g.ditAsignacionNss.numNss=:nss"),
	@NamedQuery(name = "findGrupoFamiliarByIdAsignacionNssCL3", 
			query = "select g from DitGrupoFamiliarCL3 g where g.ditAsignacionNss.cveIdAsignacionNss=:idAsignacionNss"),
	@NamedQuery(name = "findGrupoFamiliarByParentescoCL3", 
			query = "select g from DitGrupoFamiliarCL3 g where g.ditAsignacionNss.cveIdAsignacionNss=:idAsignacionNss and g.dicCalidadParentesco.cveIdCalidadParentesco=:idParentesco"),
	//TODO en esta consulta se quitan referencias a estado
	@NamedQuery(name="findGrupoFamiliarBajaCL3",
			query="select g from DitGrupoFamiliarCL3 g where g.ditAsignacionNss.cveIdAsignacionNss=:idAsignacionNss " +
			"and g.dicCalidadParentesco.cveIdCalidadParentesco=:idParentesco"),
	//TODO en esta consulta se quitan referencias a estado
	@NamedQuery(name="getIntegranGrupoFamiliarByEstadoCL3",
			query="select g from DitGrupoFamiliarCL3 g " +
			"where g.ditPersona.cveIdPersona=:idPersona"),
	//TODO en esta consulta se quitan referencias a estado
	@NamedQuery(name="findGrupoFamiliarPorEstadoDhCL3",
			query="select g from DitGrupoFamiliarCL3 g " +
			"where g.ditAsignacionNss.cveIdAsignacionNss=:idAsignacionNss"),
	@NamedQuery(name="getIntegranteGrupoFamiliarByNssCL3",
			query="select g from DitGrupoFamiliarCL3 g " +
			"where g.ditAsignacionNss.numNss=:nss  and g.ditPersona.cveIdPersona=:idPersona"),
	//TODO en esta nsmedquery se quitaron las referencia aestado y subestado
	@NamedQuery(name="findGrupoFamiliarEstadoSubestadoCL3",
			query="select g from DitGrupoFamiliarCL3 g " +
			"where g.dicCalidadParentesco.cveIdCalidadParentesco=:idParentesco and " +
			" g.ditAsignacionNss.cveIdAsignacionNss=:idAsignacionNss"),
	//TODO esta consulta se quita porque solo se consultaba el estado y ya no hay relacion
	/*@NamedQuery(name="getGrupoFamiliarEstadoDerechohabienteCL3",
			query="select g.dicEstadoDerechohabiente from DitGrupoFamiliarCL3 g " +
			"where g.ditPersona.cveIdPersona=:idPersona and g.ditAsignacionNss.cveIdAsignacionNss=:idAsignacionNss"),*/
	//TODO en esta consulta se quitan referencias a estado
	@NamedQuery(name="getGrupoFamiliarByAsignacionNssCL3",
			query="select g from DitGrupoFamiliarCL3 g " +
			"where g.ditPersona.cveIdPersona=:idPersona and g.ditAsignacionNss.cveIdAsignacionNss=:idAsignacionNss")
											
})
@Entity
@Table(name="DIT_GRUPO_FAMILIAR_CL3")
public class DitGrupoFamiliarCL3 implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private DitGrupoFamiliarCL3PK id;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA", nullable=false)
	private Date fecRegistroAlta;

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_CAMBIO_TURNO_CONSULTORIO")
	private Date fecCambioTurnoConsultorio;
	
	@Column(name="NUM_CALIDAD", precision=22)
	private long numCalidad;
	
	@Column(name="FEC_BAJA_SIMCAL_DIF_GPO_FAM")
	private Date ind_SimilarCalDifGpoFam;

	//bi-directional many-to-one association to DicCalidadParentesco
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_CALIDAD_PARENTESCO", nullable=false)
	private DicCalidadParentesco dicCalidadParentesco;
	
	//bi-directional many-to-one association to DitAsignacionNss
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_ASIGNACION_NSS", nullable=false, insertable=false, updatable=false)
	private DitAsignacionNssCL3 ditAsignacionNss;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_PERSONAF_DOM")
	private DitPersonafDom ditPersonafDom;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PERSONA_INTEGRANTE", nullable=false, insertable=false, updatable=false)
	private DitPersona ditPersona;
	
	@ManyToOne
	@JoinColumn(name="CVE_ID_UMF_CONS_TURNO_MED")
	private DitUmfConsTurnoMedico ditUmfConsTurnoMedico;
	
	@Column(name="REF_AGREGADO_AFILIACION", length=8)
	private String refAgregadoAfiliacion;

	@Column(name="REF_AGREGADO_MEDICO", length=255)
	private String refAgregadoMedico;

	@Column(name="IND_RECIEN_NACIDO")
	private Integer indRecienNacido;

	public DitGrupoFamiliarCL3PK getId() {
		return id;
	}

	public void setId(DitGrupoFamiliarCL3PK id) {
		this.id = id;
	}

	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public Date getFecCambioTurnoConsultorio() {
		return fecCambioTurnoConsultorio;
	}

	public void setFecCambioTurnoConsultorio(Date fecCambioTurnoConsultorio) {
		this.fecCambioTurnoConsultorio = fecCambioTurnoConsultorio;
	}

	public long getNumCalidad() {
		return numCalidad;
	}

	public void setNumCalidad(long numCalidad) {
		this.numCalidad = numCalidad;
	}

	public Date getInd_SimilarCalDifGpoFam() {
		return ind_SimilarCalDifGpoFam;
	}

	public void setInd_SimilarCalDifGpoFam(Date ind_SimilarCalDifGpoFam) {
		this.ind_SimilarCalDifGpoFam = ind_SimilarCalDifGpoFam;
	}

	public DicCalidadParentesco getDicCalidadParentesco() {
		return dicCalidadParentesco;
	}

	public void setDicCalidadParentesco(DicCalidadParentesco dicCalidadParentesco) {
		this.dicCalidadParentesco = dicCalidadParentesco;
	}

	public DitAsignacionNssCL3 getDitAsignacionNss() {
		return ditAsignacionNss;
	}

	public void setDitAsignacionNss(DitAsignacionNssCL3 ditAsignacionNss) {
		this.ditAsignacionNss = ditAsignacionNss;
	}

	public DitPersonafDom getDitPersonafDom() {
		return ditPersonafDom;
	}

	public void setDitPersonafDom(DitPersonafDom ditPersonafDom) {
		this.ditPersonafDom = ditPersonafDom;
	}

	public DitPersona getDitPersona() {
		return ditPersona;
	}

	public void setDitPersona(DitPersona ditPersona) {
		this.ditPersona = ditPersona;
	}

	public DitUmfConsTurnoMedico getDitUmfConsTurnoMedico() {
		return ditUmfConsTurnoMedico;
	}

	public void setDitUmfConsTurnoMedico(DitUmfConsTurnoMedico ditUmfConsTurnoMedico) {
		this.ditUmfConsTurnoMedico = ditUmfConsTurnoMedico;
	}

	public String getRefAgregadoAfiliacion() {
		return refAgregadoAfiliacion;
	}

	public void setRefAgregadoAfiliacion(String refAgregadoAfiliacion) {
		this.refAgregadoAfiliacion = refAgregadoAfiliacion;
	}

	public String getRefAgregadoMedico() {
		return refAgregadoMedico;
	}

	public void setRefAgregadoMedico(String refAgregadoMedico) {
		this.refAgregadoMedico = refAgregadoMedico;
	}

	public Integer getIndRecienNacido() {
		return indRecienNacido;
	}

	public void setIndRecienNacido(Integer indRecienNacido) {
		this.indRecienNacido = indRecienNacido;
	}
	
	
	
}
