package mx.gob.imss.ctirss.correccion.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@Entity
@Table(name="SSO_USUARIOS")
public class SsoUsuarios extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 178004187990690551L;

	@Id
	@Column(name = "CVE_SSOSOLICITUD")
	private Long cveSsoSolicitud;
	
	@Column(name = "NOM_NOMBRE")
	private String nomNombre;
	
	@Column(name = "NOM_PATERNO")
	private String nomPaterno;
	
	@Column(name = "NOM_MATERNO")
	private String nomMaterno;
	
	@Column(name = "REF_CORREO_ELECTRONICO")
	private String refCorreoElectronico;
	
	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;
	
	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
	
	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;
	
	@Column(name = "CVE_MATRICULA")
	private String cveMatricula;
	
	@Column(name = "CVE_SSOESTATUS")
	private Long cveSsoEstatus;
	
	@Column(name = "CVE_ID_DELEGACION")
	private Long cveIdDelegacion;
	
	@Column(name = "CVE_ID_SUBDELEGACION")
	private Long cveIdSubDelegacion;
	
	@Column(name = "CVE_ID_UMF")
	private Long cveIdUmf;
	
	@Column(name = "FEC_USR_NACIMIENTO")
	private Date fecUsrNacimiento;
	
	@Column(name = "CVE_ID_ENTIDAD")
	private String cveIdEntidad;
	
	@Column(name = "DES_USR_CURP")
	private String desUsrCurp;
	
	@Column(name = "CVE_SSODEPTO")
	private Long cveSsoDepto;
	
	@Column(name = "CVE_SSOPUESTO")
	private Long cveSsoPuesto;
	
	@Column(name = "CVE_USERAPROBADOR")
	private Long cveUserAprobador;
	
	@Column(name = "DES_TELEFONOOFI")
	private String desTelefonoOfi;
	
	@Column(name = "DES_SIAP_NSS")
	private String desSiapNss;
	
	@Column(name = "DES_SIAP_PUESTO")
	private String desSiapPuesto;
	
	@Column(name = "DES_SIAP_DEPTO")
	private String desSiapDepto;
	
	@Column(name = "DES_SIAP_CVEDEL")
	private String desSiapCveDel;
	
	@Column(name = "DES_SIAP_CVESUBDEL")
	private String desSiapCveSubDel;
	
	@Column(name = "DES_SIAP_ESTATUS")
	private Long desSiapEstatus;
	
	@Column(name = "DES_SIAP_CVEUMF")
	private String desSiapCveUmf;
	
	@Transient
	private String total;
	
	@Transient
	private String nombreCompleto;

	public Long getCveSsoSolicitud() {
		return cveSsoSolicitud;
	}

	public void setCveSsoSolicitud(Long cveSsoSolicitud) {
		this.cveSsoSolicitud = cveSsoSolicitud;
	}

	public String getNomNombre() {
		return nomNombre;
	}

	public void setNomNombre(String nomNombre) {
		this.nomNombre = nomNombre;
	}

	public String getNomPaterno() {
		return nomPaterno;
	}

	public void setNomPaterno(String nomPaterno) {
		this.nomPaterno = nomPaterno;
	}

	public String getNomMaterno() {
		return nomMaterno;
	}

	public void setNomMaterno(String nomMaterno) {
		this.nomMaterno = nomMaterno;
	}

	public String getRefCorreoElectronico() {
		return refCorreoElectronico;
	}

	public void setRefCorreoElectronico(String refCorreoElectronico) {
		this.refCorreoElectronico = refCorreoElectronico;
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

	public String getCveMatricula() {
		return cveMatricula;
	}

	public void setCveMatricula(String cveMatricula) {
		this.cveMatricula = cveMatricula;
	}

	public Long getCveSsoEstatus() {
		return cveSsoEstatus;
	}

	public void setCveSsoEstatus(Long cveSsoEstatus) {
		this.cveSsoEstatus = cveSsoEstatus;
	}

	public Long getCveIdDelegacion() {
		return cveIdDelegacion;
	}

	public void setCveIdDelegacion(Long cveIdDelegacion) {
		this.cveIdDelegacion = cveIdDelegacion;
	}

	public Long getCveIdSubDelegacion() {
		return cveIdSubDelegacion;
	}

	public void setCveIdSubDelegacion(Long cveIdSubDelegacion) {
		this.cveIdSubDelegacion = cveIdSubDelegacion;
	}

	public Long getCveIdUmf() {
		return cveIdUmf;
	}

	public void setCveIdUmf(Long cveIdUmf) {
		this.cveIdUmf = cveIdUmf;
	}

	public Date getFecUsrNacimiento() {
		return fecUsrNacimiento;
	}

	public void setFecUsrNacimiento(Date fecUsrNacimiento) {
		this.fecUsrNacimiento = fecUsrNacimiento;
	}

	public String getCveIdEntidad() {
		return cveIdEntidad;
	}

	public void setCveIdEntidad(String cveIdEntidad) {
		this.cveIdEntidad = cveIdEntidad;
	}

	public String getDesUsrCurp() {
		return desUsrCurp;
	}

	public void setDesUsrCurp(String desUsrCurp) {
		this.desUsrCurp = desUsrCurp;
	}

	public Long getCveSsoDepto() {
		return cveSsoDepto;
	}

	public void setCveSsoDepto(Long cveSsoDepto) {
		this.cveSsoDepto = cveSsoDepto;
	}

	public Long getCveSsoPuesto() {
		return cveSsoPuesto;
	}

	public void setCveSsoPuesto(Long cveSsoPuesto) {
		this.cveSsoPuesto = cveSsoPuesto;
	}

	public Long getCveUserAprobador() {
		return cveUserAprobador;
	}

	public void setCveUserAprobador(Long cveUserAprobador) {
		this.cveUserAprobador = cveUserAprobador;
	}

	public String getDesTelefonoOfi() {
		return desTelefonoOfi;
	}

	public void setDesTelefonoOfi(String desTelefonoOfi) {
		this.desTelefonoOfi = desTelefonoOfi;
	}

	public String getDesSiapNss() {
		return desSiapNss;
	}

	public void setDesSiapNss(String desSiapNss) {
		this.desSiapNss = desSiapNss;
	}

	public String getDesSiapPuesto() {
		return desSiapPuesto;
	}

	public void setDesSiapPuesto(String desSiapPuesto) {
		this.desSiapPuesto = desSiapPuesto;
	}

	public String getDesSiapDepto() {
		return desSiapDepto;
	}

	public void setDesSiapDepto(String desSiapDepto) {
		this.desSiapDepto = desSiapDepto;
	}

	public String getDesSiapCveDel() {
		return desSiapCveDel;
	}

	public void setDesSiapCveDel(String desSiapCveDel) {
		this.desSiapCveDel = desSiapCveDel;
	}

	public String getDesSiapCveSubDel() {
		return desSiapCveSubDel;
	}

	public void setDesSiapCveSubDel(String desSiapCveSubDel) {
		this.desSiapCveSubDel = desSiapCveSubDel;
	}

	public Long getDesSiapEstatus() {
		return desSiapEstatus;
	}

	public void setDesSiapEstatus(Long desSiapEstatus) {
		this.desSiapEstatus = desSiapEstatus;
	}

	public String getDesSiapCveUmf() {
		return desSiapCveUmf;
	}

	public void setDesSiapCveUmf(String desSiapCveUmf) {
		this.desSiapCveUmf = desSiapCveUmf;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
	
	/**
	 * @return the nombreCompleto
	 */
	public String getNombreCompleto() {
		final StringBuffer cad = new StringBuffer();
		if (this.getNomNombre() != null) {
			cad.append(this.getNomNombre());
		}
		if (this.getNomPaterno() != null) {
			cad.append(" ");
			cad.append(this.getNomPaterno());
		}
		if (this.getNomMaterno() != null) {
			cad.append(" ");
			cad.append(this.getNomMaterno());
		}
		return cad.toString();
	}
	
	/**
	 * @param nombreCompleto the nombreCompleto to set
	 */
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

}
