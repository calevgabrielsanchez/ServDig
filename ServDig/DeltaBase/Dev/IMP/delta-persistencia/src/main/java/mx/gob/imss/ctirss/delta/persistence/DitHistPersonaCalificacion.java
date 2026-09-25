package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_HIST_PERSONA_CALIFICACION database table.
 * 
 */
@Entity
@Table(name="DIT_HIST_PERSONA_CALIFICACION")
public class DitHistPersonaCalificacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DitHistPersonaCalificacionPK id;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicPersonaCalificacion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_CALIFICACION", nullable=false, insertable=false, updatable=false)
	private DicPersonaCalificacion dicPersonaCalificacion;

	//bi-directional many-to-one association to DitPersona
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PERSONA", nullable=false, insertable=false, updatable=false)
	private DitPersona ditPersona;

	//bi-directional many-to-one association to DitPersonaView
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CVE_ID_PERSONA", insertable=false, updatable=false)
	private DitPersonaView ditPersonaView;	

    public DitHistPersonaCalificacion() {
    }

	public DitHistPersonaCalificacionPK getId() {
		return this.id;
	}

	public void setId(DitHistPersonaCalificacionPK id) {
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

	public DicPersonaCalificacion getDicPersonaCalificacion() {
		return this.dicPersonaCalificacion;
	}

	public void setDicPersonaCalificacion(DicPersonaCalificacion dicPersonaCalificacion) {
		this.dicPersonaCalificacion = dicPersonaCalificacion;
	}
	
	public DitPersona getDitPersona() {
		return this.ditPersona;
	}

	public void setDitPersona(DitPersona ditPersona) {
		this.ditPersona = ditPersona;
	}

	public DitPersonaView getDitPersonaView() {
		return ditPersonaView;
	}

	public void setDitPersonaView(DitPersonaView ditPersonaView) {
		this.ditPersonaView = ditPersonaView;
	}
	
}