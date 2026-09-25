package mx.gob.imss.ctirss.admonusuarios.entidad;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the SSO_USEREXTERNOS database table.
 * 
 */
@Entity
@Table(name="SSO_USEREXTERNOS")
public class SsoUserExternos implements Serializable {
	private static   long serialVersionUID = 1L;
	private long cveSssoUsrExternos;
    private String desUsrCurp;
    private String nomNombre;
    private String nomPaterno;
    private String nomMaterno;
    private String refCorreoElectronico;
    private String cveNss;
    private int cveSsoEstatus;
    private String cveDelegacionImss;
    private String cveSubDelegacionImss;
    private String cveUmfImss;
    private int cvePersonaBdtu;
    private String desPerfil;
    private Date fecRegistroAlta;
    private Date fecRegistroBaja;
    private Date fecRegistroActualizado;
    
    public SsoUserExternos() {
    }

    @Id
    @Column(name="CVE_SSOUSREXTERNOS", unique=true, nullable=false, length=2)
	public long getCveSssoUsrExternos() {
		return cveSssoUsrExternos;
	}

	public void setCveSssoUsrExternos(long cveSssoUsrExternos) {
		this.cveSssoUsrExternos = cveSssoUsrExternos;
	}

	@Column(name="DES_USR_CURP", length=20)
	public String getDesUsrCurp() {
		return desUsrCurp;
	}

	public void setDesUsrCurp(String desUsrCurp) {
		this.desUsrCurp = desUsrCurp;
	}

	@Column(name="NOM_NOMBRE", length=20)
	public String getNomNombre() {
		return nomNombre;
	}

	public void setNomNombre(String nomNombre) {
		this.nomNombre = nomNombre;
	}

	@Column(name="NOM_PATERNO", length=20)
	public String getNomPaterno() {
		return nomPaterno;
	}

	public void setNomPaterno(String nomPaterno) {
		this.nomPaterno = nomPaterno;
	}

	@Column(name="NOM_MATERNO", length=20)
	public String getNomMaterno() {
		return nomMaterno;
	}

	public void setNomMaterno(String nomMaterno) {
		this.nomMaterno = nomMaterno;
	}

	@Column(name="REF_CORREO_ELECTRONICO", length=50)
	public String getRefCorreoElectronico() {
		return refCorreoElectronico;
	}

	public void setRefCorreoElectronico(String refCorreoElectronico) {
		this.refCorreoElectronico = refCorreoElectronico;
	}

	@Column(name="CVE_NSS", length=20)
	public String getCveNss() {
		return cveNss;
	}

	public void setCveNss(String cveNss) {
		this.cveNss = cveNss;
	}

	@Column(name="CVE_SSOESTATUS")
	public int getCveSsoEstatus() {
		return cveSsoEstatus;
	}

	public void setCveSsoEstatus(int cveSsoEstatus) {
		this.cveSsoEstatus = cveSsoEstatus;
	}

	@Column(name="CVE_DELEGACION_IMSS", length=2)
	public String getCveDelegacionImss() {
		return cveDelegacionImss;
	}

	public void setCveDelegacionImss(String cveDelegacionImss) {
		this.cveDelegacionImss = cveDelegacionImss;
	}

	@Column(name="CVE_SUBDELEGACION_IMSS", length=2)
	public String getCveSubDelegacionImss() {
		return cveSubDelegacionImss;
	}

	public void setCveSubDelegacionImss(String cveSubDelegacionImss) {
		this.cveSubDelegacionImss = cveSubDelegacionImss;
	}

	@Column(name="CVE_UMF_IMSS", length=5)
	public String getCveUmfImss() {
		return cveUmfImss;
	}

	public void setCveUmfImss(String cveUmfImss) {
		this.cveUmfImss = cveUmfImss;
	}

	@Column(name="CVE_PERSONA_BDTU")
	public int getCvePersonaBdtu() {
		return cvePersonaBdtu;
	}

	public void setCvePersonaBdtu(int cvePersonaBdtu) {
		this.cvePersonaBdtu = cvePersonaBdtu;
	}

	@Column(name="DES_PERFIL", length=100)
	public String getDesPerfil() {
		return desPerfil;
	}

	public void setDesPerfil(String desPerfil) {
		this.desPerfil = desPerfil;
	}

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA", nullable=false)
	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA", nullable=false)
	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO", nullable=false)
	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}	
}