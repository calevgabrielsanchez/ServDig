package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Date;

/**
 * The persistent class for the DIT_SOLICITUD_NSS_CONFIRMACION database table.
 * 
 */
@Entity
@Table(name="DIT_SOLICITUD_NSS_CONFIRMACION")
public class DitSolicitudNssConfirmacion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
    @SequenceGenerator(name = "SEQ_DITSOLICITUDCONFIRMACION", sequenceName = "SEQ_DITSOLICITUDCONFIRMACION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DITSOLICITUDCONFIRMACION")
	@Column(name="CVE_ID_NSS_CONF", nullable=false, precision=22)
	private long cveIdNssConf;
	
	@Column(name = "REF_CURP", nullable=false)
	private String refCurp;

	@Column(name = "REF_TOKEN", nullable=false)
	private String refCToken;
	
	@Column(name = "REF_CORREO_ELECTRONICO", nullable=false)
	private String refCorreoElectronico;
	
	@Column(name = "CVE_ID_TIPO_SOLICITUD", nullable=false)
	private Long cveIdTipoSolicitud;
	
	@Column(name = "IND_VIGENTE", nullable=false)
	private Integer vigente;

	@Column(name = "FEC_REGISTRO_ALTA", nullable=false)
	private Date fechaAlta;

    public DitSolicitudNssConfirmacion() {
    }


	public String getRefCurp() {
		return refCurp;
	}


	public void setRefCurp(String refCurp) {
		this.refCurp = refCurp;
	}


	public String getRefCToken() {
		return refCToken;
	}


	public void setRefCToken(String refCToken) {
		this.refCToken = refCToken;
	}


	public String getRefCorreoElectronico() {
		return refCorreoElectronico;
	}


	public void setRefCorreoElectronico(String refCorreoElectronico) {
		this.refCorreoElectronico = refCorreoElectronico;
	}


	public long getCveIdNssConf() {
		return cveIdNssConf;
	}


	public void setCveIdNssConf(long cveIdNssConf) {
		this.cveIdNssConf = cveIdNssConf;
	}


	public Long getCveIdTipoSolicitud() {
		return cveIdTipoSolicitud;
	}


	public void setCveIdTipoSolicitud(Long cveIdTipoSolicitud) {
		this.cveIdTipoSolicitud = cveIdTipoSolicitud;
	}

	public void setVigente(Integer vigente) {
		this.vigente = vigente;
	}


	public Integer getVigente() {
		return vigente;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}


	public Date getFechaAlta() {
		return fechaAlta;
	}

	
}
