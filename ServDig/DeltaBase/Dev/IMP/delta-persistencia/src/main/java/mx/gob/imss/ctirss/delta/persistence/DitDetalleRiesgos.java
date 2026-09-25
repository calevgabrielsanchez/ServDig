package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the DIT_DETALLE_RIESGOS database table.
 * 
 */
@Entity
@Table(name = "DIT_DETALLE_RIESGOS")
public class DitDetalleRiesgos implements Serializable {

	/** Serial version */
	private static final long serialVersionUID = -4816736000696344131L;

	@Id
	@SequenceGenerator(name = "DIT_DETALLE_RIESGOS_GENERATOR", sequenceName = "SEQ_DITDETALLERIESGOS", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_DETALLE_RIESGOS_GENERATOR")
	@Column(name = "CVE_ID_DETALLE_RIESGOS")
	private long cveIdDetalleRiesgos;

	@ManyToOne
	@JoinColumn(name = "CVE_ID_ANALISIS")
	private DitRiesgosAnalisisCe ditRiesgosAnalisisCe;

	@Column(name = "DES_REFERENCIA")
	private String nss;
	
	@Column(name = "DES_TIPO_RIESGO")
	private String tipoRiesgo;

	@Column(name = "DES_CONSECUENCIA_RIESGO")
	private String consecuenciaRiesgo;

	@Column(name = "FECHA_INICIO")
	private Date fechaInicio;

	@Column(name = "FECHA_TERMINO")
	private Date fechaTermino;

	@Column(name = "NUM_DIAS_SUBSIDIADOS")
	private Integer diasSubsidiados;

	@Column(name = "DES_NATURALEZA_LESION")
	private String naturalezaLesion;

	@Column(name = "DES_CAUSA_EXTERNA")
	private String causaExterna;

	@Column(name = "DES_MIEMBRO_LESION")
	private String miembroLesion;

	@Column(name = "DES_OCUPACION_ASEGURADO")
	private String ocupacionAsegurado;

	@Column(name = "DES_COMENTARIOS")
	private String comentarios;

	public long getCveIdDetalleRiesgos() {
		return cveIdDetalleRiesgos;
	}

	public void setCveIdDetalleRiesgos(long cveIdDetalleRiesgos) {
		this.cveIdDetalleRiesgos = cveIdDetalleRiesgos;
	}

	public DitRiesgosAnalisisCe getDitRiesgosAnalisisCe() {
		return ditRiesgosAnalisisCe;
	}

	public void setDitRiesgosAnalisisCe(
			DitRiesgosAnalisisCe ditRiesgosAnalisisCe) {
		this.ditRiesgosAnalisisCe = ditRiesgosAnalisisCe;
	}

	public String getNss() {
		return nss;
	}

	public void setNss(String nss) {
		this.nss = nss;
	}
	
	public String getTipoRiesgo() {
		return tipoRiesgo;
	}

	public void setTipoRiesgo(String tipoRiesgo) {
		this.tipoRiesgo = tipoRiesgo;
	}

	public String getConsecuenciaRiesgo() {
		return consecuenciaRiesgo;
	}

	public void setConsecuenciaRiesgo(String consecuenciaRiesgo) {
		this.consecuenciaRiesgo = consecuenciaRiesgo;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaTermino() {
		return fechaTermino;
	}

	public void setFechaTermino(Date fechaTermino) {
		this.fechaTermino = fechaTermino;
	}

	public Integer getDiasSubsidiados() {
		return diasSubsidiados;
	}

	public void setDiasSubsidiados(Integer diasSubsidiados) {
		this.diasSubsidiados = diasSubsidiados;
	}

	public String getNaturalezaLesion() {
		return naturalezaLesion;
	}

	public void setNaturalezaLesion(String naturalezaLesion) {
		this.naturalezaLesion = naturalezaLesion;
	}

	public String getCausaExterna() {
		return causaExterna;
	}

	public void setCausaExterna(String causaExterna) {
		this.causaExterna = causaExterna;
	}

	public String getMiembroLesion() {
		return miembroLesion;
	}

	public void setMiembroLesion(String miembroLesion) {
		this.miembroLesion = miembroLesion;
	}

	public String getOcupacionAsegurado() {
		return ocupacionAsegurado;
	}

	public void setOcupacionAsegurado(String ocupacionAsegurado) {
		this.ocupacionAsegurado = ocupacionAsegurado;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

}
