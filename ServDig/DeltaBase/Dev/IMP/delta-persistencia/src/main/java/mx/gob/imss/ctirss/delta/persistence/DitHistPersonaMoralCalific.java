package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.framework.base.entity.AbstractEntity;

import java.util.Date;


/**
 * The persistent class for the DIT_HIST_PERSONA_MORAL_CALIFIC database table.
 * 
 */
@Entity
@Table(name="DIT_HIST_PERSONA_MORAL_CALIFIC")
public class DitHistPersonaMoralCalific extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DitHistPersonaMoralCalificPK id;

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

	//bi-directional many-to-one association to DitPersonaMoral
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PERSONA_MORAL", nullable=false, insertable=false, updatable=false)
	private DitPersonaMoral ditPersonaMoral;

    public DitHistPersonaMoralCalific() {
    }

	public DitHistPersonaMoralCalificPK getId() {
		return this.id;
	}

	public void setId(DitHistPersonaMoralCalificPK id) {
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
	
	public DitPersonaMoral getDitPersonaMoral() {
		return this.ditPersonaMoral;
	}

	public void setDitPersonaMoral(DitPersonaMoral ditPersonaMoral) {
		this.ditPersonaMoral = ditPersonaMoral;
	}
	
}