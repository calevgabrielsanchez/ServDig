package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIT_UMF_TURNO database table.
 * 
 */
@Entity
@Table(name="DIT_UMF_TURNO")
public class DitUmfTurno implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@AttributeOverrides( {
		@AttributeOverride(name = "cveIdTurno", column = @Column(name = "CVE_ID_TURNO", nullable = false)),
		@AttributeOverride(name = "cveIdUmf", column = @Column(name = "CVE_ID_UMF", nullable = false)) }
	)
	private DitUmfTurnoPK id;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA", nullable=false)
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NUM_CITA", nullable=false, precision=22)
	private long numCita;

	//bi-directional many-to-one association to DitSolicitud
	@OneToMany(mappedBy="ditUmfTurno")
	private List<DitSolicitud> ditSolicituds;

	//bi-directional many-to-one association to DicTurno
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TURNO", nullable=false, insertable=false, updatable=false)
	private DicTurno dicTurno;

	//bi-directional many-to-one association to DicUmf
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_UMF", nullable=false, insertable=false, updatable=false)
	private DicUmf dicUmf;

    public DitUmfTurno() {
    }

	public DitUmfTurnoPK getId() {
		return this.id;
	}

	public void setId(DitUmfTurnoPK id) {
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

	public long getNumCita() {
		return this.numCita;
	}

	public void setNumCita(long numCita) {
		this.numCita = numCita;
	}

	public List<DitSolicitud> getDitSolicituds() {
		return this.ditSolicituds;
	}

	public void setDitSolicituds(List<DitSolicitud> ditSolicituds) {
		this.ditSolicituds = ditSolicituds;
	}
	
	public DicTurno getDicTurno() {
		return this.dicTurno;
	}

	public void setDicTurno(DicTurno dicTurno) {
		this.dicTurno = dicTurno;
	}
	
	public DicUmf getDicUmf() {
		return this.dicUmf;
	}

	public void setDicUmf(DicUmf dicUmf) {
		this.dicUmf = dicUmf;
	}
	
}