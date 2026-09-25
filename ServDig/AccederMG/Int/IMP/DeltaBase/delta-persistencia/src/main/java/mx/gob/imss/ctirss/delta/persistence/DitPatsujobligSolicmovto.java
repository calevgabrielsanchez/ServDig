package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_PATSUJOBLIG_SOLICMOVTO database table.
 * 
 */
@Entity
@Table(name="DIT_PATSUJOBLIG_SOLICMOVTO")
public class DitPatsujobligSolicmovto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_PATSUJOBLIG_SOLICMOVTO", nullable=false, precision=22)
	private long cveIdPatsujobligSolicmovto;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitMovtoPatSujOblig
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_MOVTO_PAT_SUJ_OBLIG")
	private DitMovtoPatSujOblig ditMovtoPatSujOblig;

    public DitPatsujobligSolicmovto() {
    }

	public long getCveIdPatsujobligSolicmovto() {
		return this.cveIdPatsujobligSolicmovto;
	}

	public void setCveIdPatsujobligSolicmovto(long cveIdPatsujobligSolicmovto) {
		this.cveIdPatsujobligSolicmovto = cveIdPatsujobligSolicmovto;
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

	public DitMovtoPatSujOblig getDitMovtoPatSujOblig() {
		return this.ditMovtoPatSujOblig;
	}

	public void setDitMovtoPatSujOblig(DitMovtoPatSujOblig ditMovtoPatSujOblig) {
		this.ditMovtoPatSujOblig = ditMovtoPatSujOblig;
	}
	
}