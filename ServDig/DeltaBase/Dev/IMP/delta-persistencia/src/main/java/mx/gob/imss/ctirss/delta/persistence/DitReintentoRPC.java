package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_REINTENTO_RPC database table.
 * 
 */
@Entity
@Table(name="DIT_REINTENTO_RPC")
public class DitReintentoRPC implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ID_SOLICITUD")
	private long cveIdSolicitud;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="IND_REINTENTO_RPC")
	private Integer indReintentoRPC;

	@Column(name="IND_RPC_INVALIDO")
	private Integer indRpcInvalido;

	//bi-directional one-to-one association to DitSolicitud
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_SOLICITUD")
	private DitSolicitud ditSolicitud;

    public DitReintentoRPC() {
    }

	public long getCveIdSolicitud() {
		return this.cveIdSolicitud;
	}

	public void setCveIdSolicitud(long cveIdSolicitud) {
		this.cveIdSolicitud = cveIdSolicitud;
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

	public Integer getIndReintentoRpc() {
		return this.indReintentoRPC;
	}

	public void setIndReintentoRpc(Integer indReintentoRpc) {
		this.indReintentoRPC = indReintentoRpc;
	}

	public Integer getIndRpcInvalido() {
		return this.indRpcInvalido;
	}

	public void setIndRpcInvalido(Integer indRpcInvalido) {
		this.indRpcInvalido = indRpcInvalido;
	}

	public DitSolicitud getDitSolicitud() {
		return this.ditSolicitud;
	}

	public void setDitSolicitud(DitSolicitud ditSolicitud) {
		this.ditSolicitud = ditSolicitud;
	}
	
}