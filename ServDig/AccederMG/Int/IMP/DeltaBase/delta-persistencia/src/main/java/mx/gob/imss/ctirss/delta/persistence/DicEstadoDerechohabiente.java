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



/**
 * The persistent class for the DIC_ESTADO_DERECHOHABIENTE database table.
 * 
 */
@Entity
@Table(name="DIC_ESTADO_DERECHOHABIENTE")
public class DicEstadoDerechohabiente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ESTADO_DERECHOHABIENTE", nullable=false, precision=22)
	private long cveEstadoDerechohabiente;

	@Column(name="DES_ESTADO_DERECHOHABIENTE", nullable=false, length=255)
	private String desEstadoDerechohabiente;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
		
    public DicEstadoDerechohabiente() {
    }

	public long getCveEstadoDerechohabiente() {
		return this.cveEstadoDerechohabiente;
	}

	public void setCveEstadoDerechohabiente(long cveEstadoDerechohabiente) {
		this.cveEstadoDerechohabiente = cveEstadoDerechohabiente;
	}

	public String getDesEstadoDerechohabiente() {
		return this.desEstadoDerechohabiente;
	}

	public void setDesEstadoDerechohabiente(String desEstadoDerechohabiente) {
		this.desEstadoDerechohabiente = desEstadoDerechohabiente;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return this.fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}
	
}