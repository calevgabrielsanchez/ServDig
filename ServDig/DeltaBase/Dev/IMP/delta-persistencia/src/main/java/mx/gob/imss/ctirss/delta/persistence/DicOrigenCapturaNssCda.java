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
@Table(name="DIC_ORIGEN_CAPTURA_NSS_CDA")
public class DicOrigenCapturaNssCda implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CVE_ID_ORIGEN_CAPTURA_NSS_CDA")
	private Long cveIdOrigenCapturaNssCda;
	
	@Column(name="DES_ORIGEN_CAPTURA_NSS", length=50)
	private String desOrigenCapturaNssCda;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;
	 
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	public Long getCveIdOrigenCapturaNssCda() {
		return cveIdOrigenCapturaNssCda;
	}

	public void setCveIdOrigenCapturaNssCda(Long cveIdOrigenCapturaNssCda) {
		this.cveIdOrigenCapturaNssCda = cveIdOrigenCapturaNssCda;
	}

	public String getDesOrigenCapturaNssCda() {
		return desOrigenCapturaNssCda;
	}

	public void setDesOrigenCapturaNssCda(String desOrigenCapturaNssCda) {
		this.desOrigenCapturaNssCda = desOrigenCapturaNssCda;
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

	@Override
	public String toString() {
		return "DicOrigenCapturaNssCda [cveIdOrigenCapturaNssCda=" + cveIdOrigenCapturaNssCda + ", desOrigenCapturaNssCda="
				+ desOrigenCapturaNssCda + ", fecRegistroAlta=" + fecRegistroAlta + ", fecRegistroBaja="
				+ fecRegistroBaja + ", fecRegistroActualizado=" + fecRegistroActualizado + "]";
	}
	 

}
