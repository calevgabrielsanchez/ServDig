package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.Where;

import java.util.Date;


/**
 * The persistent class for the DIT_GRUPO_FAMILIAR database table.
 * 
 */
@NamedQueries({ 
	
	@NamedQuery(name = "findGrupoFamiliarNssParentesco", 
			query = "select g from DitGrupoFamiliar g where g.ditAsignacionNss.numNss=:nss and g.dicCalidadParentesco.cveIdCalidadParentesco=:idParentesco"),
	@NamedQuery(name = "findGrupoFamiliarByNss", 
			query = "select g from DitGrupoFamiliar g where g.ditAsignacionNss.numNss=:nss"),
	@NamedQuery(name = "findGrupoFamiliarByIdAsignacionNss", 
			query = "select g from DitGrupoFamiliar g where g.ditAsignacionNss.cveIdAsignacionNss=:idAsignacionNss"),
	@NamedQuery(name = "findGrupoFamiliarByParentesco", 
			query = "select g from DitGrupoFamiliar g where g.ditAsignacionNss.cveIdAsignacionNss=:idAsignacionNss and g.dicCalidadParentesco.cveIdCalidadParentesco=:idParentesco"),
	//TODO en esta consulta se quitan referencias a estado
	@NamedQuery(name="findGrupoFamiliarBaja",
			query="select g from DitGrupoFamiliar g where g.ditAsignacionNss.cveIdAsignacionNss=:idAsignacionNss " +
			"and g.dicCalidadParentesco.cveIdCalidadParentesco=:idParentesco"),
	//TODO en esta consulta se quitan referencias a estado
	@NamedQuery(name="getIntegranGrupoFamiliarByEstado",
			query="select g from DitGrupoFamiliar g " +
			"where g.ditPersona.cveIdPersona=:idPersona"),
	//TODO en esta consulta se quitan referencias a estado
	@NamedQuery(name="findGrupoFamiliarPorEstadoDh",
			query="select g from DitGrupoFamiliar g " +
			"where g.ditAsignacionNss.cveIdAsignacionNss=:idAsignacionNss"),
	@NamedQuery(name="getIntegranteGrupoFamiliarByNss",
			query="select g from DitGrupoFamiliar g " +
			"where g.ditAsignacionNss.numNss=:nss  and g.ditPersona.cveIdPersona=:idPersona"),
	//TODO en esta nsmedquery se quitaron las referencia aestado y subestado
	@NamedQuery(name="findGrupoFamiliarEstadoSubestado",
			query="select g from DitGrupoFamiliar g " +
			"where g.dicCalidadParentesco.cveIdCalidadParentesco=:idParentesco and " +
			" g.ditAsignacionNss.cveIdAsignacionNss=:idAsignacionNss"),
	//TODO esta consulta se quita porque solo se consultaba el estado y ya no hay relacion
	/*@NamedQuery(name="getGrupoFamiliarEstadoDerechohabiente",
			query="select g.dicEstadoDerechohabiente from DitGrupoFamiliar g " +
			"where g.ditPersona.cveIdPersona=:idPersona and g.ditAsignacionNss.cveIdAsignacionNss=:idAsignacionNss"),*/
	//TODO en esta consulta se quitan referencias a estado
	@NamedQuery(name="getGrupoFamiliarByAsignacionNss",
			query="select g from DitGrupoFamiliar g " +
			"where g.ditPersona.cveIdPersona=:idPersona and g.ditAsignacionNss.cveIdAsignacionNss=:idAsignacionNss")
											
})
@Entity
@Table(name="DIT_GRUPO_FAMILIAR")
@Where(clause = "FEC_REGISTRO_BAJA is null")
public class DitGrupoFamiliar implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DitGrupoFamiliarPK id;

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
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="CVE_ID_CALIDAD_PARENTESCO", nullable=false)
	private DicCalidadParentesco dicCalidadParentesco;
	
	//bi-directional many-to-one association to DitAsignacionNss
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="CVE_ID_ASIGNACION_NSS", nullable=false, insertable=false, updatable=false)
	private DitAsignacionNss ditAsignacionNss;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_PERSONAF_DOM")
	private DitPersonafDom ditPersonafDom;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="CVE_ID_PERSONA_INTEGRANTE", nullable=false, insertable=false, updatable=false)
	private DitPersona ditPersona;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="CVE_ID_UMF_CONS_TURNO_MED")
	private DitUmfConsTurnoMedico ditUmfConsTurnoMedico;
	
	@Column(name="REF_AGREGADO_AFILIACION", length=8)
	private String refAgregadoAfiliacion;

	@Column(name="REF_AGREGADO_MEDICO", length=255)
	private String refAgregadoMedico;

	@Column(name="IND_RECIEN_NACIDO")
	private Integer indRecienNacido;
	
    public DitGrupoFamiliar() {
    }

	public DitGrupoFamiliarPK getId() {
		return this.id;
	}

	public void setId(DitGrupoFamiliarPK id) {
		this.id = id;
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

	public long getNumCalidad() {
		return this.numCalidad;
	}

	public void setNumCalidad(long numCalidad) {
		this.numCalidad = numCalidad;
	}

	public DicCalidadParentesco getDicCalidadParentesco() {
		return this.dicCalidadParentesco;
	}

	public void setDicCalidadParentesco(DicCalidadParentesco dicCalidadParentesco) {
		this.dicCalidadParentesco = dicCalidadParentesco;
	}
	
	public DitAsignacionNss getDitAsignacionNss() {
		return this.ditAsignacionNss;
	}

	public void setDitAsignacionNss(DitAsignacionNss ditAsignacionNss) {
		this.ditAsignacionNss = ditAsignacionNss;
	}
	
	public DitPersonafDom getDitPersonafDom() {
		return ditPersonafDom;
	}

	public void setDitPersonafDom(DitPersonafDom ditPersonafDom) {
		this.ditPersonafDom = ditPersonafDom;
	}

	public DitUmfConsTurnoMedico getDitUmfConsTurnoMedico() {
		return this.ditUmfConsTurnoMedico;
	}

	public void setDitUmfConsTurnoMedico(DitUmfConsTurnoMedico ditUmfConsTurnoMedico) {
		this.ditUmfConsTurnoMedico = ditUmfConsTurnoMedico;
	}

	public Date getFecCambioTurnoConsultorio() {
		return fecCambioTurnoConsultorio;
	}

	public void setFecCambioTurnoConsultorio(Date fecCambioTurnoConsultorio) {
		this.fecCambioTurnoConsultorio = fecCambioTurnoConsultorio;
	}

	public Date getInd_SimilarCalDifGpoFam() {
		return ind_SimilarCalDifGpoFam;
	}

	public void setInd_SimilarCalDifGpoFam(Date ind_SimilarCalDifGpoFam) {
		this.ind_SimilarCalDifGpoFam = ind_SimilarCalDifGpoFam;
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

	public DitPersona getDitPersona() {
		return ditPersona;
	}

	public void setDitPersona(DitPersona ditPersona) {
		this.ditPersona = ditPersona;
	}

	public Integer getIndRecienNacido() {
		return indRecienNacido;
	}

	public void setIndRecienNacido(Integer indRecienNacido) {
		this.indRecienNacido = indRecienNacido;
	}
	
	

}