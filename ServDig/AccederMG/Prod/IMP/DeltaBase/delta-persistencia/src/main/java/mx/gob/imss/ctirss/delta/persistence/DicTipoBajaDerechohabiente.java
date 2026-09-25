package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "DIC_TIPO_BAJA_DERECHOHABIENTE")
public class DicTipoBajaDerechohabiente implements Serializable {

	private static final long serialVersionUID = -4271930603231741880L;

	@Id
	@Column(name = "CVE_ID_TIPO_BAJA_DER")
	private Long cveIdTipoBajaDer;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizaco;

	@Column(name = "DES_TIPO_BAJA_DERECHOHABIENTE")
	private String desTipoBajaDer;

	@OneToMany(mappedBy = "dicTipoBajaDerechohabiente")
	private List<DitBajaDerechohabiente> ditBajaDerechohabiente;

	public Long getCveIdTipoBajaDer() {
		return cveIdTipoBajaDer;
	}

	public void setCveIdTipoBajaDer(Long cveIdTipoBajaDer) {
		this.cveIdTipoBajaDer = cveIdTipoBajaDer;
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

	public Date getFecRegistroActualizaco() {
		return fecRegistroActualizaco;
	}

	public void setFecRegistroActualizaco(Date fecRegistroActualizaco) {
		this.fecRegistroActualizaco = fecRegistroActualizaco;
	}

	public String getDesTipoBajaDer() {
		return desTipoBajaDer;
	}

	public void setDesTipoBajaDer(String desTipoBajaDer) {
		this.desTipoBajaDer = desTipoBajaDer;
	}

	public List<DitBajaDerechohabiente> getDitBajaDerechohabiente() {
		return ditBajaDerechohabiente;
	}

	public void setDitBajaDerechohabiente(
			List<DitBajaDerechohabiente> ditBajaDerechohabiente) {
		this.ditBajaDerechohabiente = ditBajaDerechohabiente;
	}
}
