package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the DIC_TIPO_DOCTO_ANALISIS_CE database table.
 * 
 */
@Entity
@Table(name = "DIC_TIPO_DOCTO_ANALISIS_CE")
public class DicTipoDoctoAnalisisCe implements Serializable {

	/** Serial version */
	private static final long serialVersionUID = 5364489926298821951L;

	@Id
	@Column(name = "CVE_TIPO_DOCTO_ANALISIS_CE")
	private long cveTipoDoctoAnalisisCe;

	@Column(name = "DES_TIPO_DOCTO_ANALISIS_CE")
	private String desTipoDoctoAnalisisCe;

	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	public long getCveTipoDoctoAnalisisCe() {
		return cveTipoDoctoAnalisisCe;
	}

	public void setCveTipoDoctoAnalisisCe(long cveTipoDoctoAnalisisCe) {
		this.cveTipoDoctoAnalisisCe = cveTipoDoctoAnalisisCe;
	}

	public String getDesTipoDoctoAnalisisCe() {
		return desTipoDoctoAnalisisCe;
	}

	public void setDesTipoDoctoAnalisisCe(String desTipoDoctoAnalisisCe) {
		this.desTipoDoctoAnalisisCe = desTipoDoctoAnalisisCe;
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
