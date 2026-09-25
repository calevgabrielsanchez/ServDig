package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "DIC_DOCTO_ORIG_SOLICITANTE_CDA")
public class DicDoctoOrigSolicitanteCda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CVE_ID_ORIGEN_DOCTO")
	private Long cveIdOrigenDocto;

	@Column(name = "DES_ORIGEN_DOCTO")
	private String desOrigenDocto;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	public Long getCveIdOrigenDocto() {
		return cveIdOrigenDocto;
	}

	public void setCveIdOrigenDocto(Long cveIdOrigenDocto) {
		this.cveIdOrigenDocto = cveIdOrigenDocto;
	}

	public String getDesOrigenDocto() {
		return desOrigenDocto;
	}

	public void setDesOrigenDocto(String desOrigenDocto) {
		this.desOrigenDocto = desOrigenDocto;
	}

	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

}
