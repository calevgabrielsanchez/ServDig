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
@Table(name = "DIC_TRAMITE_MODALIDAD")
public class DicTramiteModalidad implements Serializable{

	private static final long serialVersionUID = -7353793037609521388L;

	@Id
	@Column( name = "CVE_ID_TRAMITE_MODALIDAD")
	private Long cveIdTramiteModalidad;
	
	@ManyToOne
	@JoinColumn(name = "CVE_ID_MODALIDAD")
	private DicModalidad dicModalidad;
	
	@ManyToOne
	@JoinColumn(name = "CVE_ID_TIPO_TRAMITE")
	private DicTipoTramite dicTipoTramite;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	public Long getCveIdTramiteModalidad() {
		return cveIdTramiteModalidad;
	}

	public void setCveIdTramiteModalidad(Long cveIdTramiteModalidad) {
		this.cveIdTramiteModalidad = cveIdTramiteModalidad;
	}

	public DicModalidad getDicModalidad() {
		return dicModalidad;
	}

	public void setDicModalidad(DicModalidad dicModalidad) {
		this.dicModalidad = dicModalidad;
	}

	public DicTipoTramite getDicTipoTramite() {
		return dicTipoTramite;
	}

	public void setDicTipoTramite(DicTipoTramite dicTipoTramite) {
		this.dicTipoTramite = dicTipoTramite;
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
