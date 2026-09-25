package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_RL_FACULTAD database table.
 * 
 */
@Entity
@Table(name="DIT_RL_FACULTAD")
public class DitRlFacultad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_RL_FACULTAD", nullable=false, precision=22)
	private long cveIdRlFacultad;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicFacultad
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_FACULTAD")
	private DicFacultad dicFacultad;

	//bi-directional many-to-one association to DitRepresentanteLegal
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_REPRESENTANTE_LEGAL")
	private DitRepresentanteLegal ditRepresentanteLegal;

    public DitRlFacultad() {
    }

	public long getCveIdRlFacultad() {
		return this.cveIdRlFacultad;
	}

	public void setCveIdRlFacultad(long cveIdRlFacultad) {
		this.cveIdRlFacultad = cveIdRlFacultad;
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

	public DicFacultad getDicFacultad() {
		return this.dicFacultad;
	}

	public void setDicFacultad(DicFacultad dicFacultad) {
		this.dicFacultad = dicFacultad;
	}
	
	public DitRepresentanteLegal getDitRepresentanteLegal() {
		return this.ditRepresentanteLegal;
	}

	public void setDitRepresentanteLegal(DitRepresentanteLegal ditRepresentanteLegal) {
		this.ditRepresentanteLegal = ditRepresentanteLegal;
	}
	
}