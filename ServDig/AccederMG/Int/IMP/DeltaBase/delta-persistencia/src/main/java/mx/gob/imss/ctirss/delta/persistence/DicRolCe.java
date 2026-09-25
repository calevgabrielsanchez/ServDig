package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the DIC_ROL_CE database table.
 * 
 */
@Entity
@Table(name = "DIC_ROL_CE")
public class DicRolCe implements Serializable {

	/** Serial version */
	private static final long serialVersionUID = -4648432996397056552L;

	@Id
	@SequenceGenerator(name = "DIC_ROL_CE_GENERATOR", sequenceName = "SEQ_DICROLCE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIC_ROL_CE_GENERATOR")
	@Column(name = "CVE_ID_ROL_CE")
	private long cveIdRolCe;

	@Column(name = "DES_ROL_CE")
	private String desRolCe;

	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	public long getCveIdRolCe() {
		return cveIdRolCe;
	}

	public void setCveIdRolCe(long cveIdRolCe) {
		this.cveIdRolCe = cveIdRolCe;
	}

	public String getDesRolCe() {
		return desRolCe;
	}

	public void setDesRolCe(String desRolCe) {
		this.desRolCe = desRolCe;
	}

	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
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

}
