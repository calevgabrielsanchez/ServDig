package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the DIT_HIST_ESTADO_PERSONA database table.
 * 
 */
@Entity
@Table(name="DIT_HIST_ESTADO_PERSONA")
public class DitHistEstadoPersona implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DitHistEstadoPersonaPK id;

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

	//bi-directional many-to-one association to DitPersona
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PERSONA", nullable=false, insertable=false, updatable=false)
	private DitPersona ditPersona;

    public DitHistEstadoPersona() {
    }

	public DitHistEstadoPersonaPK getId() {
		return this.id;
	}

	public void setId(DitHistEstadoPersonaPK id) {
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
	
	public DitPersona getDitPersona() {
		return this.ditPersona;
	}

	public void setDitPersona(DitPersona ditPersona) {
		this.ditPersona = ditPersona;
	}
	
}