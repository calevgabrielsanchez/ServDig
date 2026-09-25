package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the DIT_RIESGOS_ANALISIS_CE database table.
 * 
 */
@Entity
@Table(name = "DIT_RIESGOS_ANALISIS_CE")
public class DitRiesgosAnalisisCe implements Serializable {

	/** Serial version */
	private static final long serialVersionUID = -1790363001637018348L;

	@Id
	@Column(name = "CVE_ID_ANALISIS")
	private long cveIdAnalisis;

	@Column(name = "NUM_ACCIDENTES")
	private Integer numAccidentes;

	@Column(name = "NUM_ENFERMEDADES")
	private Integer numEnfermedades;

	@Column(name = "NUM_TOT_DIAS_SUBSIDIADOS")
	private Integer totDiasSubsidiados;

	@Column(name = "NUM_TOT_DEFUNCIONES")
	private Integer totDefunciones;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "ditRiesgosAnalisisCe")
	private List<DitDetalleRiesgos> ditDetalleRiesgos;

	public long getCveIdAnalisis() {
		return cveIdAnalisis;
	}

	public void setCveIdAnalisis(long cveIdAnalisis) {
		this.cveIdAnalisis = cveIdAnalisis;
	}

	public Integer getNumAccidentes() {
		return numAccidentes;
	}

	public void setNumAccidentes(Integer numAccidentes) {
		this.numAccidentes = numAccidentes;
	}

	public Integer getNumEnfermedades() {
		return numEnfermedades;
	}

	public void setNumEnfermedades(Integer numEnfermedades) {
		this.numEnfermedades = numEnfermedades;
	}

	public Integer getTotDiasSubsidiados() {
		return totDiasSubsidiados;
	}

	public void setTotDiasSubsidiados(Integer totDiasSubsidiados) {
		this.totDiasSubsidiados = totDiasSubsidiados;
	}

	public Integer getTotDefunciones() {
		return totDefunciones;
	}

	public void setTotDefunciones(Integer totDefunciones) {
		this.totDefunciones = totDefunciones;
	}

	/**
	 * @return the ditDetalleRiesgos
	 */
	public List<DitDetalleRiesgos> getDitDetalleRiesgos() {
		return ditDetalleRiesgos;
	}

	/**
	 * @param ditDetalleRiesgos
	 *            the ditDetalleRiesgos to set
	 */
	public void setDitDetalleRiesgos(List<DitDetalleRiesgos> ditDetalleRiesgos) {
		this.ditDetalleRiesgos = ditDetalleRiesgos;
	}

}
