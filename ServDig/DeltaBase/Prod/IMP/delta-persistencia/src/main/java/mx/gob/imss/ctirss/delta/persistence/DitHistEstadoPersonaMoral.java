package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.delta.framework.base.entity.AbstractEntity;


/**
 * The persistent class for the DIT_HIST_ESTADO_PERSONA_MORAL database table.
 * 
 */
@Entity
@Table(name="DIT_HIST_ESTADO_PERSONA_MORAL")
public class DitHistEstadoPersonaMoral extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DitHistEstadoPersonaMoralPK id;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicEstadoPersona
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ESTADO_PERSONA", nullable=false, insertable=false, updatable=false)
	private DicEstadoPersona dicEstadoPersona;

	//bi-directional many-to-one association to DitPersonaMoral
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PERSONA_MORAL", nullable=false, insertable=false, updatable=false)
	private DitPersonaMoral ditPersonaMoral;

    public DitHistEstadoPersonaMoral() {
    }

	public DitHistEstadoPersonaMoralPK getId() {
		return this.id;
	}

	public void setId(DitHistEstadoPersonaMoralPK id) {
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

	public DicEstadoPersona getDicEstadoPersona() {
		return this.dicEstadoPersona;
	}

	public void setDicEstadoPersona(DicEstadoPersona dicEstadoPersona) {
		this.dicEstadoPersona = dicEstadoPersona;
	}
	
	public DitPersonaMoral getDitPersonaMoral() {
		return this.ditPersonaMoral;
	}

	public void setDitPersonaMoral(DitPersonaMoral ditPersonaMoral) {
		this.ditPersonaMoral = ditPersonaMoral;
	}
	
}