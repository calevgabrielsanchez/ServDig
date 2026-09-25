package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "DIC_TIPO_PRESTACION")
public class DicTipoPrestacion implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6281146334854190877L;

	@Id
	@Column(name="CVE_ID_TIPO_PRESTACION" , nullable = false)
	private Long cveIdTipoPrestacion;
	
	@Column(name ="DESC_TIPO_PRESTACION", nullable = false)
	private String descTipoPrestacion;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	public Long getCveIdTipoPrestacion() {
		return cveIdTipoPrestacion;
	}

	public void setCveIdTipoPrestacion(Long cveIdTipoPrestacion) {
		this.cveIdTipoPrestacion = cveIdTipoPrestacion;
	}

	public String getDescTipoPrestacion() {
		return descTipoPrestacion;
	}

	public void setDescTipoPrestacion(String descTipoPrestacion) {
		this.descTipoPrestacion = descTipoPrestacion;
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