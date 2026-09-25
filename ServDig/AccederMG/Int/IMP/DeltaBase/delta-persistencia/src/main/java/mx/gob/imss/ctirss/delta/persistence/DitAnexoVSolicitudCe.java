package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

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
 * The persistent class for the DIT_ANEXOV_SOLICITUD_CE database table.
 * 
 */
@Entity
@Table(name = "DIT_ANEXOV_SOLICITUD_CE")
public class DitAnexoVSolicitudCe implements Serializable {

	/** Serial version */
	private static final long serialVersionUID = -8068209607297439952L;

	@Id
	@SequenceGenerator(name = "DIT_ANEXOV_SOLICITUD_CE_GENERATOR", sequenceName = "SEQ_DITANEXOVSOLICITUDCE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_ANEXOV_SOLICITUD_CE_GENERATOR")
	@Column(name = "CVE_ID_ANEXOV_SOLICITUD_CE")
	private Long cveIdAnexoVSolicitudCe;

	@ManyToOne
	@JoinColumn(name = "CVE_ID_SOLICITUD")
	private DitSolicitud ditSolicitud;

	@ManyToOne
	@JoinColumn(name = "ID_AVISO")
	private FdtAviso fdtAviso;

	@ManyToOne
	@JoinColumn(name = "CVE_ID_PATRON_GENERAL")
	private DitPatronGeneral ditPatronGeneral;

	@ManyToOne
	@JoinColumn(name = "CVE_ID_SUBDELEGACION")
	private DicSubdelegacion dicSubdelegacion;

	@ManyToOne
	@JoinColumn(name = "CVE_ID_CLASIFICACION")
	private DitClasificacion ditClasificacion;

	@ManyToOne
	@JoinColumn(name = "CVE_ID_ANALISIS")
	private DitAnalisisCe ditAnalisisCe;

	@ManyToOne
	@JoinColumn(name = "CVE_ID_TIPO_PERSONA")
	private DicTipoPersona dicTipoPersona;

	@Column(name = "IND_FINALIZADO")
	private Integer indFinalizado;

	public Long getCveIdAnexoVSolicitudCe() {
		return cveIdAnexoVSolicitudCe;
	}

	public void setCveIdAnexoVSolicitudCe(Long cveIdAnexoVSolicitudCe) {
		this.cveIdAnexoVSolicitudCe = cveIdAnexoVSolicitudCe;
	}

	public DitSolicitud getDitSolicitud() {
		return ditSolicitud;
	}

	public void setDitSolicitud(DitSolicitud ditSolicitud) {
		this.ditSolicitud = ditSolicitud;
	}

	public FdtAviso getFdtAviso() {
		return fdtAviso;
	}

	public void setFdtAviso(FdtAviso fdtAviso) {
		this.fdtAviso = fdtAviso;
	}

	public DitPatronGeneral getDitPatronGeneral() {
		return ditPatronGeneral;
	}

	public void setDitPatronGeneral(DitPatronGeneral ditPatronGeneral) {
		this.ditPatronGeneral = ditPatronGeneral;
	}

	public DicSubdelegacion getDicSubdelegacion() {
		return dicSubdelegacion;
	}

	public void setDicSubdelegacion(DicSubdelegacion dicSubdelegacion) {
		this.dicSubdelegacion = dicSubdelegacion;
	}

	public DitClasificacion getDitClasificacion() {
		return ditClasificacion;
	}

	public void setDitClasificacion(DitClasificacion ditClasificacion) {
		this.ditClasificacion = ditClasificacion;
	}

	public Integer getIndFinalizado() {
		return indFinalizado;
	}

	public void setIndFinalizado(Integer indFinalizado) {
		this.indFinalizado = indFinalizado;
	}

	public DitAnalisisCe getDitAnalisisCe() {
		return ditAnalisisCe;
	}

	public void setDitAnalisisCe(DitAnalisisCe ditAnalisisCe) {
		this.ditAnalisisCe = ditAnalisisCe;
	}

	public DicTipoPersona getDicTipoPersona() {
		return dicTipoPersona;
	}

	public void setDicTipoPersona(DicTipoPersona dicTipoPersona) {
		this.dicTipoPersona = dicTipoPersona;
	}

}
