package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="DIC_TIPO_PRORROGA")
public class DicTipoProrroga implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3544604963311903246L;


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TIPO_PRORROGA", nullable=false, precision=22)
	private Long cveIdTipoProrroga;
	
	
	@Column(name="DES_TIPO_PRORROGA", nullable=false, length=255)
	private String desTipoProrroga;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA", nullable=false)
	private Date fecRegistroAlta;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.TIMESTAMP)    
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	public Long getCveIdTipoProrroga() {
		return cveIdTipoProrroga;
	}

	public void setCveIdTipoProrroga(Long cveIdTipoProrroga) {
		this.cveIdTipoProrroga = cveIdTipoProrroga;
	}

	public String getDesTipoProrroga() {
		return desTipoProrroga;
	}

	public void setDesTipoProrroga(String desTipoProrroga) {
		this.desTipoProrroga = desTipoProrroga;
	}

	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

}