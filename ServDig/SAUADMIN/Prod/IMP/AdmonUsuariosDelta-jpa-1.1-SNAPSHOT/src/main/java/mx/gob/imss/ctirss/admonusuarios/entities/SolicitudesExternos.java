/**
 * 
 */
package mx.gob.imss.ctirss.admonusuarios.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Alan Garcia
 * Esta clase es la encargada de realizar la persistencia en la base de datos de las solicitudes persistidas en LDAP para usuarios externos.
 */
@Entity
@Table(name="SSO_USEREXTERNOS")
@NamedQueries(
	    {	        
	       
	    }
)
public class SolicitudesExternos implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "DIT_SSOUSEREXTERNOS_CVEIDSSOEXTERNOS_GENERATOR", sequenceName = "SEQ_CVE_SSOUSREXTERNOS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_SSOUSEREXTERNOS_CVEIDSSOEXTERNOS_GENERATOR")	
	@Column(name="CVE_SSOUSREXTERNOS", nullable = false, updatable = false)
	private Long idUsrExternos;
	
	@Column(name="DES_USR_CURP", nullable=false, length=200)
	private String curp;

	@Column(name="NOM_NOMBRE", nullable=false, length=200)
	private String nomNombres;
	
	@Column(name="NOM_PATERNO", nullable=false, length=200)
	private String nomPaterno;
	
	@Column(name="NOM_MATERNO", nullable=false, length=200)
	private String nomMaterno;
	
	@Column(name="REF_CORREO_ELECTRONICO", nullable=false, length=255)
	private String correoElectonico;
	
	@Column(name="CVE_NSS", nullable=false, length=255)
	private String nss;
	
	@Column(name="CVE_SSOESTATUS", nullable=false, length=255)
	private int estatus;
	
	@Column(name="CVE_DELEGACION_IMSS", nullable=false, length=255)
	private String cveDelegacion;
	
	@Column(name="CVE_SUBDELEGACION_IMSS", nullable=false, length=255)
	private String cveSubDelegacion;
	
	@Column(name="CVE_UMF_IMSS", nullable=false, length=255)
	private String cveUmf;
	
	@Column(name="CVE_PERSONA_BDTU", nullable=false, length=255)
	private int cvePersonaBdtu;
	
	@Column(name="DES_PERFIL", nullable=false, length=255)
	private String desPerfil;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA", nullable=false)
	private Date registroAlta;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA", nullable=true)
	private Date registroBaja;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO", nullable=true)
	private Date registroActualizado;

	public Long getIdUsrExternos() {
		return idUsrExternos;
	}

	public void setIdUsrExternos(Long idUsrExternos) {
		this.idUsrExternos = idUsrExternos;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getNomNombres() {
		return nomNombres;
	}

	public void setNomNombres(String nomNombres) {
		this.nomNombres = nomNombres;
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

	public String getCorreoElectonico() {
		return correoElectonico;
	}

	public void setCorreoElectonico(String correoElectonico) {
		this.correoElectonico = correoElectonico;
	}

	public String getNss() {
		return nss;
	}

	public void setNss(String nss) {
		this.nss = nss;
	}

	public int getEstatus() {
		return estatus;
	}

	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}

	public String getCveDelegacion() {
		return cveDelegacion;
	}

	public void setCveDelegacion(String cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}

	public String getCveSubDelegacion() {
		return cveSubDelegacion;
	}

	public void setCveSubDelegacion(String cveSubDelegacion) {
		this.cveSubDelegacion = cveSubDelegacion;
	}

	public String getCveUmf() {
		return cveUmf;
	}

	public void setCveUmf(String cveUmf) {
		this.cveUmf = cveUmf;
	}

	public int getCvePersonaBdtu() {
		return cvePersonaBdtu;
	}

	public void setCvePersonaBdtu(int cvePersonaBdtu) {
		this.cvePersonaBdtu = cvePersonaBdtu;
	}

	public String getDesPerfil() {
		return desPerfil;
	}

	public void setDesPerfil(String desPerfil) {
		this.desPerfil = desPerfil;
	}

	public Date getRegistroAlta() {
		return registroAlta;
	}

	public void setRegistroAlta(Date registroAlta) {
		this.registroAlta = registroAlta;
	}

	public Date getRegistroBaja() {
		return registroBaja;
	}

	public void setRegistroBaja(Date registroBaja) {
		this.registroBaja = registroBaja;
	}

	public Date getRegistroActualizado() {
		return registroActualizado;
	}

	public void setRegistroActualizado(Date registroActualizado) {
		this.registroActualizado = registroActualizado;
	}

}
