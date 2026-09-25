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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="DIT_PER_PORTAL_CIUDADANO")
public class DitPerPortalCiudadano implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @SequenceGenerator(name = "DIT_PERPORTACIUDADANO_GENERATOR", sequenceName = "SEQ_DITPERPORTALCIUDADANO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_PERPORTACIUDADANO_GENERATOR")
	@Column(name = "CVE_ID_PER_PORTALCIU", nullable = false)
	private Long cveIdPerPortal;
	
	@Column(name = "CVE_ID_PERSONA") 
	private Long cve_id_persona;
	
	@Column(name = "CVE_ID_SOLICITUD")
	private Long cveIdSolicitud;
	
	@Column(name = "NUM_TELEFONO")
	private String numTelefono;
	
	@Column(name = "DESC_CORREOELECTRONICO")
	private String descCorreoElectronico;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ALTA") 
	private Date fecRegistroAlta; 
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_BAJA")  
	private Date fecRegistroBaja;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ACTUALIZADO") 
	private Date fecRegistroActualizado;
	
	public Long getCveIdPerPortal() {
		return cveIdPerPortal;
	}
	public void setCveIdPerPortal(Long cveIdPerPortal) {
		this.cveIdPerPortal = cveIdPerPortal;
	}
	public Long getCve_id_persona() {
		return cve_id_persona;
	}
	public void setCve_id_persona(Long cve_id_persona) {
		this.cve_id_persona = cve_id_persona;
	}
	public Long getCveIdSolicitud() {
		return cveIdSolicitud;
	}
	public void setCveIdSolicitud(Long cveIdSolicitud) {
		this.cveIdSolicitud = cveIdSolicitud;
	}
	public String getNumTelefono() {
		return numTelefono;
	}
	public void setNumTelefono(String numTelefono) {
		this.numTelefono = numTelefono;
	}
	public String getDescCorreoElectronico() {
		return descCorreoElectronico;
	}
	public void setDescCorreoElectronico(String descCorreoElectronico) {
		this.descCorreoElectronico = descCorreoElectronico;
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
