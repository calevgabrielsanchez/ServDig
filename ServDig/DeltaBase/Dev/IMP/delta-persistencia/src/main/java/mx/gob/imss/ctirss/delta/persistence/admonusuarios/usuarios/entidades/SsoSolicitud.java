package mx.gob.imss.ctirss.delta.persistence.admonusuarios.entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Temporal;
import java.util.Date;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SSO_SOLICITUD")
public class SsoSolicitud  implements Serializable{

    private static final long serialVersionUID = 1L;
	private Long cveSsosolicitud;
	private String cveIdEntidad;
	private String cveMatricula;
	private String desUsrCurp;
	private Date fecRegistroActualizado;
	private Date fecRegistroAlta;
	private Date fecRegistroBaja;
	private Date fecUsrNacimiento;
	private String nomMaterno;
	private String nomNombre;
	private String nomPaterno;
	private String refCorreoElectronico;
	private String desTelefonoOfi;
	
	private String nss; 
	private String puesto;
	private String departamento;
	private String cveDelegacion;
	private String cveSubdelegacion;
	private long estatus;
	private String cveUmf;

    public SsoSolicitud() {
    }

    public SsoSolicitud(Long clave) {
    	cveSsosolicitud = clave;
    }	

	@Id
	@Column(name="CVE_SSOSOLICITUD", unique=true, nullable=true)
	public Long getCveSsosolicitud() {
		return this.cveSsosolicitud;
	}

	public void setCveSsosolicitud(Long cveSsosolicitud) {
		this.cveSsosolicitud = cveSsosolicitud;
	}


	@Column(name="CVE_ID_ENTIDAD", length=2)
	public String getCveIdEntidad() {
		return this.cveIdEntidad;
	}

	public void setCveIdEntidad(String cveIdEntidad) {
		this.cveIdEntidad = cveIdEntidad;
	}


	@Column(name="CVE_MATRICULA", nullable=false, length=20)
	public String getCveMatricula() {
		return this.cveMatricula;
	}

	public void setCveMatricula(String cveMatricula) {
		this.cveMatricula = cveMatricula;
	}


	@Column(name="DES_USR_CURP", length=20)
	public String getDesUsrCurp() {
		return this.desUsrCurp;
	}

	public void setDesUsrCurp(String desUsrCurp) {
		this.desUsrCurp = desUsrCurp;
	}


    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}


    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA", nullable=false)
	public Date getFecRegistroAlta() {
		return this.fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}


    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	public Date getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}


    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_USR_NACIMIENTO")
	public Date getFecUsrNacimiento() {
		return this.fecUsrNacimiento;
	}

	public void setFecUsrNacimiento(Date fecUsrNacimiento) {
		this.fecUsrNacimiento = fecUsrNacimiento;
	}


	@Column(name="NOM_MATERNO", nullable=false, length=20)
	public String getNomMaterno() {
		return this.nomMaterno;
	}

	public void setNomMaterno(String nomMaterno) {
		this.nomMaterno = nomMaterno;
	}


	@Column(name="NOM_NOMBRE", nullable=false, length=20)
	public String getNomNombre() {
		return this.nomNombre;
	}

	public void setNomNombre(String nomNombre) {
		this.nomNombre = nomNombre;
	}


	@Column(name="NOM_PATERNO", nullable=false, length=20)
	public String getNomPaterno() {
		return this.nomPaterno;
	}

	public void setNomPaterno(String nomPaterno) {
		this.nomPaterno = nomPaterno;
	}


	@Column(name="REF_CORREO_ELECTRONICO", nullable=false, length=50)
	public String getRefCorreoElectronico() {
		return this.refCorreoElectronico;
	}

	public void setRefCorreoElectronico(String refCorreoElectronico) {
		this.refCorreoElectronico = refCorreoElectronico;
	}

	@Column(name="DES_TELEFONOOFI", nullable=false, length=25)
	public String getDesTelefonoOfi() {
		return desTelefonoOfi;
	}


	public void setDesTelefonoOfi(String desTelefonoOfi) {
		this.desTelefonoOfi = desTelefonoOfi;
	}


	@Column(name="DES_SIAP_NSS", nullable=true, length=11)
	public String getNss() {
		return nss;
	}


	public void setNss(String nss) {
		this.nss = nss;
	}

	@Column(name="DES_SIAP_PUESTO", nullable=true, length=200)
	public String getPuesto() {
		return puesto;
	}


	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	@Column(name="DES_SIAP_DEPTO", nullable=true, length=200)
	public String getDepartamento() {
		return departamento;
	}


	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	@Column(name="DES_SIAP_CVEDEL", nullable=true, length=2)
	public String getCveDelegacion() {
		return cveDelegacion;
	}


	public void setCveDelegacion(String cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}


	@Column(name="DES_SIAP_CVESUBDEL", nullable=true, length=2)
	public String getCveSubdelegacion() {
		return cveSubdelegacion;
	}


	public void setCveSubdelegacion(String cveSubdelegacion) {
		this.cveSubdelegacion = cveSubdelegacion;
	}


	@Column(name="DES_SIAP_ESTATUS", nullable=true, length=1)
	public long getEstatus() {
		return estatus;
	}


	public void setEstatus(long estatus) {
		this.estatus = estatus;
	}

	@Column(name="DES_SIAP_CVEUMF", nullable=true, length=2)
	public String getCveUmf() {
		return cveUmf;
	}


	public void setCveUmf(String cveUmf) {
		this.cveUmf = cveUmf;
	}
}
