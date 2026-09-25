package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;


/**
 * The persistent class for the DIV_REPORTE_ANALISIS_GCE database table.
 * 
 */
@Entity
@Table(name="DIV_REPORTE_ANALISIS_GCE")
public class DivReporteAnalisis implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name="DES_CLASE_D")
	private String desClaseD;

	@Column(name="DES_CLASE_R")
	private String desClaseR;
	
	@Column(name="CVE_ID_CLASE_D")
	private String cveIdClaseD;

	@Column(name="CVE_ID_CLASE_R")
	private String cveIdClaseR;

	@Column(name="CVE_CIZ")
	private BigInteger cveCiz;
	
	@Id
	@Column(name="CVE_ID_ANALISIS",unique=false)
	private BigInteger cveIdAnalisis;

	@Column(name="CVE_ID_SOLICITUD")
	private BigInteger cveIdSolicitud;

	@Column(name="CVE_ID_DELEGACION")
	private BigInteger cveIdDelegacion;

	@Column(name="CVE_ID_ESTATUS_ANALISIS")
	private BigInteger cveIdEstatusAnalisis;

	@Column(name="CVE_ID_SUBDELEGACION")
	private BigInteger cveIdSubdelegacion;

	@Column(name="CVE_ID_TIPO_PERSONA")
	private BigInteger cveIdTipoPersona;

	@Column(name="DES_CAUSAS_ANALISIS")
	private String desCausasAnalisis;

	@Column(name="DES_DELEG")
	private String desDeleg;

	@Column(name="DES_SUBDELEGACION")
	private String desSubdelegacion;

	@Column(name="DES_TIPO_PERSONA")
	private String desTipoPersona;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_ANALISIS")
	private Date fecAnalisis;

//	@Column(name="FEC_AUTORIZACION")
//	private Timestamp fecAutorizacion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_PRESENTACION")
	private Date fecPresentacion;

	@Column(name="DES_FRACCION_D")
	private String desFraccionD;

	@Column(name="DES_FRACCION_R")
	private String desFraccionR;

	@Column(name="NUM_IND_ARP")
	private BigInteger numIndArp;

	@Column(name="IND_MOD_AUT")
	private BigInteger indModAut;

	@Column(name="IND_PRESTA_SERVICIO_PERSONAL")
	private BigInteger indPrestaServicioPersonal;

	@Column(name="IND_REG_PAT_CLASE")
	private BigInteger indRegPatClase;

//	@Column(name="NOM_LOC")
//	private String nomLoc;

	@Column(name="DES_NOMBRE_COMERCIAL")
	private String nombreComercial;
	
	@Column(name="DES_RAZON_SOCIAL")
	private String razonSocial;
	
	@Column(name="NOM_NOMBRE")
	private String nombre;

	@Id
	@Column(name="NUM_FOLIO_RESOLUCION")
	private String numFolioResolucion;

	@Column(name="NUM_PRIMA_D")
	private String numPrimaD;

	@Column(name="NUM_PRIMA_R")
	private String numPrimaR;

	@Column(name="REG_PATRON")
	private String regPatron;
	
//	@Column(name="REG_PATRON_PADRE")
//	private String regPatronPadre;
	
	@Column(name="CVE_ID_TIPO_TRAMITE")
	private BigDecimal cveIdTipoTramite;
	
	@Column(name="DES_TIPO_TRAMITE")
	private String desTipoTramite;
	
	@Column(name="DIG_VER")
	private String digVer;
	
	@Column(name="NUM_MODALIDAD")
	private String numModalidad;
	
	@Column(name="CVE_ID_GRUPO_ANALISIS_CE")
	private BigInteger cveIdGrupoAnalisisCe;
	
	@Column(name="DES_COMENTARIO")
	private String desComentario;
	
	@Column(name="REG_PATRON_COMPLETO")
	private String regPatronCompleto;

	@Column(name="CONT_MOD_CLEM")
	private String contModClem;
	
    public DivReporteAnalisis() {
    }

	public String getDesClaseD() {
		return this.desClaseD;
	}

	public void setDesClaseD(String desClaseD) {
		this.desClaseD = desClaseD;
	}

	public String getDesClaseR() {
		return this.desClaseR;
	}

	public void setDesClaseR(String desClaseR) {
		this.desClaseR = desClaseR;
	}

	public BigInteger getCveCiz() {
		return this.cveCiz;
	}

	public void setCveCiz(BigInteger cveCiz) {
		this.cveCiz = cveCiz;
	}

	public BigInteger getCveIdAnalisis() {
		return this.cveIdAnalisis;
	}

	public void setCveIdAnalisis(BigInteger cveIdAnalisis) {
		this.cveIdAnalisis = cveIdAnalisis;
	}

	public BigInteger getCveIdDelegacion() {
		return this.cveIdDelegacion;
	}

	public void setCveIdDelegacion(BigInteger cveIdDelegacion) {
		this.cveIdDelegacion = cveIdDelegacion;
	}

	public BigInteger getCveIdEstatusAnalisis() {
		return this.cveIdEstatusAnalisis;
	}

	public void setCveIdEstatusAnalisis(BigInteger cveIdEstatusAnalisis) {
		this.cveIdEstatusAnalisis = cveIdEstatusAnalisis;
	}

	public BigInteger getCveIdSubdelegacion() {
		return this.cveIdSubdelegacion;
	}

	public void setCveIdSubdelegacion(BigInteger cveIdSubdelegacion) {
		this.cveIdSubdelegacion = cveIdSubdelegacion;
	}

	public BigInteger getCveIdTipoPersona() {
		return this.cveIdTipoPersona;
	}

	public void setCveIdTipoPersona(BigInteger cveIdTipoPersona) {
		this.cveIdTipoPersona = cveIdTipoPersona;
	}

	public String getDesCausasAnalisis() {
		return this.desCausasAnalisis;
	}

	public void setDesCausasAnalisis(String desCausasAnalisis) {
		this.desCausasAnalisis = desCausasAnalisis;
	}

	public String getDesDeleg() {
		return this.desDeleg;
	}

	public void setDesDeleg(String desDeleg) {
		this.desDeleg = desDeleg;
	}

	public String getDesSubdelegacion() {
		return this.desSubdelegacion;
	}

	public void setDesSubdelegacion(String desSubdelegacion) {
		this.desSubdelegacion = desSubdelegacion;
	}

	public String getDesTipoPersona() {
		return this.desTipoPersona;
	}

	public void setDesTipoPersona(String desTipoPersona) {
		this.desTipoPersona = desTipoPersona;
	}

	public Date getFecAnalisis() {
		return this.fecAnalisis;
	}

	public void setFecAnalisis(Date fecAnalisis) {
		this.fecAnalisis = fecAnalisis;
	}

//	public Timestamp getFecAutorizacion() {
//		return this.fecAutorizacion;
//	}
//
//	public void setFecAutorizacion(Timestamp fecAutorizacion) {
//		this.fecAutorizacion = fecAutorizacion;
//	}

	public Date getFecPresentacion() {
		return this.fecPresentacion;
	}

	public void setFecPresentacion(Date fecPresentacion) {
		this.fecPresentacion = fecPresentacion;
	}

	public String getDesFraccionD() {
		return this.desFraccionD;
	}

	public void setDesFraccionD(String desFraccionD) {
		this.desFraccionD = desFraccionD;
	}

	public String getDesFraccionR() {
		return this.desFraccionR;
	}

	public void setDesFraccionR(String desFraccionR) {
		this.desFraccionR = desFraccionR;
	}

	public BigInteger getNumIndArp() {
		return this.numIndArp;
	}

	public void setNumIndArp(BigInteger numIndArp) {
		this.numIndArp = numIndArp;
	}

	public BigInteger getIndModAut() {
		return this.indModAut;
	}

	public void setIndModAut(BigInteger indModAut) {
		this.indModAut = indModAut;
	}

	public BigInteger getIndPrestaServicioPersonal() {
		return this.indPrestaServicioPersonal;
	}

	public void setIndPrestaServicioPersonal(BigInteger indPrestaServicioPersonal) {
		this.indPrestaServicioPersonal = indPrestaServicioPersonal;
	}

	public BigInteger getIndRegPatClase() {
		return this.indRegPatClase;
	}

	public void setIndRegPatClase(BigInteger indRegPatClase) {
		this.indRegPatClase = indRegPatClase;
	}

//	public String getNomLoc() {
//		return this.nomLoc;
//	}
//
//	public void setNomLoc(String nomLoc) {
//		this.nomLoc = nomLoc;
//	}

	public String getNombreComercial() {
		return this.nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	public String getNumFolioResolucion() {
		return this.numFolioResolucion;
	}

	public void setNumFolioResolucion(String numFolioResolucion) {
		this.numFolioResolucion = numFolioResolucion;
	}

	public String getNumPrimaD() {
		return this.numPrimaD;
	}

	public void setNumPrimaD(String numPrimaD) {
		this.numPrimaD = numPrimaD;
	}

	public String getNumPrimaR() {
		return this.numPrimaR;
	}

	public void setNumPrimaR(String numPrimaR) {
		this.numPrimaR = numPrimaR;
	}

	public String getRegPatron() {
		return this.regPatron;
	}

	public void setRegPatron(String regPatron) {
		this.regPatron = regPatron;
	}

	public BigInteger getCveIdSolicitud() {
		return cveIdSolicitud;
	}

	public void setCveIdSolicitud(BigInteger cveIdSolicitud) {
		this.cveIdSolicitud = cveIdSolicitud;
	}
	
	public BigDecimal getCveIdTipoTramite() {
		return this.cveIdTipoTramite;
	}

	public void setCveIdTipoTramite(BigDecimal cveIdTipoTramite) {
		this.cveIdTipoTramite = cveIdTipoTramite;
	}

	public String getCveIdClaseD() {
		return cveIdClaseD;
	}

	public void setCveIdClaseD(String cveIdClaseD) {
		this.cveIdClaseD = cveIdClaseD;
	}

	public String getCveIdClaseR() {
		return cveIdClaseR;
	}

	public void setCveIdClaseR(String cveIdClaseR) {
		this.cveIdClaseR = cveIdClaseR;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDesTipoTramite() {
		return desTipoTramite;
	}

	public void setDesTipoTramite(String desTipoTramite) {
		this.desTipoTramite = desTipoTramite;
	}

	public String getDigVer() {
		return digVer;
	}

	public void setDigVer(String digVer) {
		this.digVer = digVer;
	}

//	public String getRegPatronPadre() {
//		return regPatronPadre;
//	}
//
//	public void setRegPatronPadre(String regPatronPadre) {
//		this.regPatronPadre = regPatronPadre;
//	}

	public BigInteger getCveIdGrupoAnalisisCe() {
		return cveIdGrupoAnalisisCe;
	}

	public void setCveIdGrupoAnalisisCe(BigInteger cveIdGrupoAnalisisCe) {
		this.cveIdGrupoAnalisisCe = cveIdGrupoAnalisisCe;
	}

	public String getDesComentario() {
		return desComentario;
	}

	public void setDesComentario(String desComentario) {
		this.desComentario = desComentario;
	}

	public String getRegPatronCompleto() {
		return regPatronCompleto;
	}

	public void setRegPatronCompleto(String regPatronCompleto) {
		this.regPatronCompleto = regPatronCompleto;
	}

	public String getNumModalidad() {
		return numModalidad;
	}

	public void setNumModalidad(String numModalidad) {
		this.numModalidad = numModalidad;
	}
	
    public String getContModClem() {
		return contModClem;
	}

	public void setContModClem(String contModClem) {
		this.contModClem = contModClem;
	}

	public boolean equals(Object o) {
        if (o == null) { return false; }
        if (o == this) { return true; }
        if (this.getClass() != o.getClass()) { return false; }
        DivReporteAnalisis other = (DivReporteAnalisis)o;
        return new EqualsBuilder()
                .append(this.cveIdAnalisis, other.cveIdAnalisis)
                .append(this.numFolioResolucion, other.numFolioResolucion)
                .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(7, 3)
                .append(cveIdAnalisis)
                .append(numFolioResolucion)
                .hashCode();
    }

}
