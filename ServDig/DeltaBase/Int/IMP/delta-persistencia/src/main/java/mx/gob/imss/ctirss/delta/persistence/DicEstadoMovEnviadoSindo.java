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
@Table(name = "DIC_ESTADO_MOV_ENVIADO_SINDO")
public class DicEstadoMovEnviadoSindo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CVE_ID_ESTADO_MOV_ENV_SINDO")
	private Long cveIdEstadoMovEnvSindo;

	@Column(name = "DES_ESTATUS_ENV_SINDO")
	private String desEstatusEnvSindo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	public Long getCveIdEstadoMovEnvSindo() {
		return cveIdEstadoMovEnvSindo;
	}

	public void setCveIdEstadoMovEnvSindo(Long cveIdEstadoMovEnvSindo) {
		this.cveIdEstadoMovEnvSindo = cveIdEstadoMovEnvSindo;
	}

	public String getDesEstatusEnvSindo() {
		return desEstatusEnvSindo;
	}

	public void setDesEstatusEnvSindo(String desEstatusEnvSindo) {
		this.desEstatusEnvSindo = desEstatusEnvSindo;
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
