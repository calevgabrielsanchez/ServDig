package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_SOLICITUD_DOCUMENTO database table.
 * 
 */
@Entity
@Table(name="DIT_SOLICITUD_DOCUMENTO")
public class DitSolicitudDocumento implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CVE_ID_SOLICITUD")
	private long cveIdSolicitud;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

    @Lob()
	@Column(name="REF_ACUSE_RECIBO")
	private byte[] refAcuseRecibo;

    @Lob()
	@Column(name="REF_COMPROBANTE_TRAMITE")
	private byte[] refComprobanteTramite;

	//bi-directional many-to-one association to DitSolicitud
    @OneToOne
	@JoinColumn(name="CVE_ID_SOLICITUD", insertable=false, updatable=false)
	private DitSolicitud ditSolicitud;
    
    public long getCveIdSolicitud() {
		return cveIdSolicitud;
	}

	public void setCveIdSolicitud(long cveIdSolicitud) {
		this.cveIdSolicitud = cveIdSolicitud;
	}

	public DitSolicitudDocumento() {
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

	public byte[] getRefAcuseRecibo() {
		return this.refAcuseRecibo;
	}

	public void setRefAcuseRecibo(byte[] refAcuseRecibo) {
		this.refAcuseRecibo = refAcuseRecibo != null ? refAcuseRecibo.clone() : null;
	}

	public byte[] getRefComprobanteTramite() {
		return this.refComprobanteTramite;
	}

	public void setRefComprobanteTramite(byte[] refComprobanteTramite) {
		this.refComprobanteTramite = refComprobanteTramite != null ? refComprobanteTramite.clone() : null;
	}

	public DitSolicitud getDitSolicitud() {
		return this.ditSolicitud;
	}

	public void setDitSolicitud(DitSolicitud ditSolicitud) {
		this.ditSolicitud = ditSolicitud;
	}
	
}