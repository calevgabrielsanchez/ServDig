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
 * The persistent class for the DIC_TIPO_OPERACION_CE database table.
 * 
 */
@Entity
@Table(name = "DIC_TIPO_OPERACION_CE")
public class DicTipoOperacionCe implements Serializable {

	/** Serial version */
	private static final long serialVersionUID = 7013188936544229716L;

	@Id
	@SequenceGenerator(name = "DIC_TIPO_OPERACION_CE_GENERATOR", sequenceName = "SEQ_DICTIPOOPERACIONCE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIC_TIPO_OPERACION_CE_GENERATOR")
	@Column(name = "CVE_ID_TIPO_OPERACION_CE")
	private long cveIdTipoOperacionCe;

	@Column(name = "DES_TIPO_OPERACION_CE")
	private String desTipoOperacionCe;

	@Column(name = "DES_NOMBRE_ATRIBUTO")
	private String nombreAtributo;

	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	public long getCveIdTipoOperacionCe() {
		return cveIdTipoOperacionCe;
	}

	public void setCveIdTipoOperacionCe(long cveIdTipoOperacionCe) {
		this.cveIdTipoOperacionCe = cveIdTipoOperacionCe;
	}

	public String getDesTipoOperacionCe() {
		return desTipoOperacionCe;
	}

	public void setDesTipoOperacionCe(String desTipoOperacionCe) {
		this.desTipoOperacionCe = desTipoOperacionCe;
	}

	public String getNombreAtributo() {
		return nombreAtributo;
	}

	public void setNombreAtributo(String nombreAtributo) {
		this.nombreAtributo = nombreAtributo;
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
