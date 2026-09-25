package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the SPT_BAJA_SOLICITUD database table.
 * 
 */
@Entity
@Table(name="SPT_BAJA_SOLICITUD")
@NamedQuery(name="SptBajaSolicitud.findAll", query="SELECT s FROM SptBajaSolicitud s")
public class SptBajaSolicitud implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SptBajaSolicitudPK id;

	@Column(name="DES_COMENTARIOS_BAJA")
	private String desComentariosBaja;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to SpcCausaBaja
	@ManyToOne
	@JoinColumn(name="ID_CAUSA_BAJA",insertable=false, updatable=false)
	private SpcCausaBaja spcCausaBaja;

	//bi-directional many-to-one association to SptTramitePension
	@ManyToOne
	@JoinColumn(name="CVE_ID_TRAMITE_PENSION",insertable=false, updatable=false)
	private SptTramitePension sptTramitePension;

	public SptBajaSolicitud() {
	}

	public SptBajaSolicitudPK getId() {
		return this.id;
	}

	public void setId(SptBajaSolicitudPK id) {
		this.id = id;
	}

	public String getDesComentariosBaja() {
		return this.desComentariosBaja;
	}

	public void setDesComentariosBaja(String desComentariosBaja) {
		this.desComentariosBaja = desComentariosBaja;
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

	public SpcCausaBaja getSpcCausaBaja() {
		return this.spcCausaBaja;
	}

	public void setSpcCausaBaja(SpcCausaBaja spcCausaBaja) {
		this.spcCausaBaja = spcCausaBaja;
	}

	public SptTramitePension getSptTramitePension() {
		return this.sptTramitePension;
	}

	public void setSptTramitePension(SptTramitePension sptTramitePension) {
		this.sptTramitePension = sptTramitePension;
	}

}