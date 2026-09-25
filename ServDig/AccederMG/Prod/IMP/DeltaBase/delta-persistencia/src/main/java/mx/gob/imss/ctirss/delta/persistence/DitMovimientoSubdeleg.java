package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_MOVIMIENTO_SUBDELEG database table.
 * 
 */
@Entity
@Table(name="DIT_MOVIMIENTO_SUBDELEG")
public class DitMovimientoSubdeleg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_MOVIMIENTO_SUBDELEG", nullable=false, precision=22)
	private long cveIdMovimientoSubdeleg;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	//bi-directional many-to-one association to DitMovtoPatSujOblig
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_MOVTO_PAT_SUJ_OBLIG")
	private DitMovtoPatSujOblig ditMovtoPatSujOblig;

	//bi-directional many-to-one association to DicSubdelegacion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_SUBDELEGACION")
	private DicSubdelegacion dicSubdelegacion;

    public DitMovimientoSubdeleg() {
    }

	public long getCveIdMovimientoSubdeleg() {
		return this.cveIdMovimientoSubdeleg;
	}

	public void setCveIdMovimientoSubdeleg(long cveIdMovimientoSubdeleg) {
		this.cveIdMovimientoSubdeleg = cveIdMovimientoSubdeleg;
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

	public DitMovtoPatSujOblig getDitMovtoPatSujOblig() {
		return this.ditMovtoPatSujOblig;
	}

	public void setDitMovtoPatSujOblig(DitMovtoPatSujOblig ditMovtoPatSujOblig) {
		this.ditMovtoPatSujOblig = ditMovtoPatSujOblig;
	}
	
	public DicSubdelegacion getDicSubdelegacion() {
		return this.dicSubdelegacion;
	}

	public void setDicSubdelegacion(DicSubdelegacion dicSubdelegacion) {
		this.dicSubdelegacion = dicSubdelegacion;
	}
	
}