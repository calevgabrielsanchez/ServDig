package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "DIC_PRESTACION_MOD_DERECHOHAB")
public class DicPrestacionModDerechohab implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8843579695364611603L;

	@Id
	@Column (name = "CVE_ID_PRES_MOD_DER")
	private Long cveIdPresModDer;
	
	@ManyToOne
	@JoinColumn(name = "CVE_ID_MODALIDAD")
	private DicModalidad dicModalidad;
	
	@ManyToOne
	@JoinColumn(name = "CVE_ID_TIPO_PRESTACION")
	private DicTipoPrestacion dicTipoPrestacion;
	
	@ManyToOne
	@JoinColumn(name = "CVE_ID_PRESTACION_DERECHOHAB")
	private DicPrestacionDerechohab dicPrestacionDerechohab;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	public Long getCveIdPresModDer() {
		return cveIdPresModDer;
	}

	public void setCveIdPresModDer(Long cveIdPresModDer) {
		this.cveIdPresModDer = cveIdPresModDer;
	}

	public DicModalidad getDicModalidad() {
		return dicModalidad;
	}

	public void setDicModalidad(DicModalidad dicModalidad) {
		this.dicModalidad = dicModalidad;
	}

	public DicTipoPrestacion getDicTipoPrestacion() {
		return dicTipoPrestacion;
	}

	public void setDicTipoPrestacion(DicTipoPrestacion dicTipoPrestacion) {
		this.dicTipoPrestacion = dicTipoPrestacion;
	}

	public DicPrestacionDerechohab getDicPrestacionDerechohab() {
		return dicPrestacionDerechohab;
	}

	public void setDicPrestacionDerechohab(
			DicPrestacionDerechohab dicPrestacionDerechohab) {
		this.dicPrestacionDerechohab = dicPrestacionDerechohab;
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