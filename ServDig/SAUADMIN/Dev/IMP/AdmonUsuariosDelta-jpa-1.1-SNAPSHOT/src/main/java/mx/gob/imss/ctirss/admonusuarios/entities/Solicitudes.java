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

import mx.gob.imss.ctirss.admonusuarios.abstractModel.AbstractModel;

/**
 * @author Alan Garcia
 * Esta clase es la encargada de realizar la persistencia en la base de datos de las solicitudes creadas.
 */
@Entity
@Table(name="SSO_SOLICITUD")
@NamedQueries(
	    {	        
	        @NamedQuery(name = "Solicitudes.findById", query = "select s from Solicitudes s where s.idSolicitud = :id"),
	        @NamedQuery(name = "Solicitudes.findAllPending", query = "select s from Solicitudes s where s.estatus = 1"),
	        @NamedQuery(name = "Solicitudes.findAllDeleted", query = "select s from Solicitudes s where s.estatus = 4"),
	        @NamedQuery(name = "Solicitudes.findCurp", query = "select s from Solicitudes s where s.curp = :idcurp"),
	        @NamedQuery(name = "Solicitudes.findAll", query = "select s from Solicitudes s")
	    }
)
public class Solicitudes extends AbstractModel  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CVE_SSOSOLICITUD", nullable = true, updatable = false)
	private Long idSolicitud;
	
	@Column(name="NOM_NOMBRE", nullable=false, length=200)
	private String nomNombre;

	@Column(name="NOM_PATERNO", nullable=false, length=200)
	private String nomPaterno;
	
	@Column(name="NOM_MATERNO", nullable=false, length=200)
	private String nomMaterno;
	
	@Column(name="REF_CORREO_ELECTRONICO", nullable=false, length=255)
	private String nomCorreoElectonico;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA", nullable=false)
	private Date registroAlta;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA", nullable=true)
	private Date registroBaja;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO", nullable=true)
	private Date registroActualizado;
	
	@Column(name="CVE_MATRICULA", nullable=false, length=200)
	private String nomMatricula;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CVE_ID_DELEGACION")
	private Delegacion delegacion;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CVE_ID_SUBDELEGACION")
	private Subdelegacion subdelegacion;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "CVE_ID_UMF")
	private UnidadMedicaFamiliar unidadMedicaFamiliar;
	
	@Column(name="CVE_SSOESTATUS")
	private Long estatus;
	
	@Column(name="DES_USR_CURP")
    private String curp;
    
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FEC_USR_NACIMIENTO")
    private Date fechaNacimiento;

	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CVE_SSODEPTO")
	private Departamento departamento;
  
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CVE_SSOPUESTO")
	private Puesto puesto;


	@Column(name="CVE_ID_ENTIDAD")
	private long estado;
		
	@Column(name="DES_TELEFONOOFI")
    private String telefono;
	
	/**
	 * @return the idSolicitud
	 */
	public Long getIdSolicitud() {
		return idSolicitud;
	}
	/**
	 * @param idSolicitud the idSolicitud to set
	 */
	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	/**
	 * @return the nomNombre
	 */
	public String getNomNombre() {
		return nomNombre;
	}
	/**
	 * @param nomNombre the nomNombre to set
	 */
	public void setNomNombre(String nomNombre) {
		this.nomNombre = nomNombre;
	}

	/**
	 * @return the nomPaterno
	 */
	public String getNomPaterno() {
		return nomPaterno;
	}
	/**
	 * @param nomPaterno the nomPaterno to set
	 */
	public void setNomPaterno(String nomPaterno) {
		this.nomPaterno = nomPaterno;
	}

	/**
	 * @return the nomMaterno
	 */
	public String getNomMaterno() {
		return nomMaterno;
	}
	/**
	 * @param nomMaterno the nomMaterno to set
	 */
	public void setNomMaterno(String nomMaterno) {
		this.nomMaterno = nomMaterno;
	}

	/**
	 * @return the nomCorreoElectonico
	 */
	public String getNomCorreoElectonico() {
		return nomCorreoElectonico;
	}
	/**
	 * @param nomCorreoElectonico the nomCorreoElectonico to set
	 */
	public void setNomCorreoElectonico(String nomCorreoElectonico) {
		this.nomCorreoElectonico = nomCorreoElectonico;
	}

	/**
	 * @return the registroAlta
	 */
	public Date getRegistroAlta() {
		return registroAlta;
	}
	/**
	 * @param registroAlta the registroAlta to set
	 */
	public void setRegistroAlta(Date registroAlta) {
		this.registroAlta = registroAlta;
	}

	/**
	 * @return the registroBaja
	 */
	public Date getRegistroBaja() {
		return registroBaja;
	}
	/**
	 * @param registroBaja the registroBaja to set
	 */
	public void setRegistroBaja(Date registroBaja) {
		this.registroBaja = registroBaja;
	}

	/**
	 * @return the registroActualizado
	 */
	public Date getRegistroActualizado() {
		return registroActualizado;
	}
	/**
	 * @param registroActualizado the registroActualizado to set
	 */
	public void setRegistroActualizado(Date registroActualizado) {
		this.registroActualizado = registroActualizado;
	}

	/**
	 * @return the nomMatricula
	 */
	public String getNomMatricula() {
		return nomMatricula;
	}
	/**
	 * @param nomMatricula the nomMatricula to set
	 */
	public void setNomMatricula(String nomMatricula) {
		this.nomMatricula = nomMatricula;
	}

	/**
	 * @return the delegacion
	 */
	public Delegacion getDelegacion() {
		return delegacion;
	}
	/**
	 * @param delegacion the delegacion to set
	 */
	public void setDelegacion(Delegacion delegacion) {
		this.delegacion = delegacion;
	}

	/**
	 * @return the subdelegacion
	 */
	public Subdelegacion getSubdelegacion() {
		return subdelegacion;
	}
	/**
	 * @param subdelegacion the subdelegacion to set
	 */
	public void setSubdelegacion(Subdelegacion subdelegacion) {
		this.subdelegacion = subdelegacion;
	}

	/**
	 * @return the unidadMedicaFamiliar
	 */
	public UnidadMedicaFamiliar getUnidadMedicaFamiliar() {
		return unidadMedicaFamiliar;
	}
	/**
	 * @param unidadMedicaFamiliar the unidadMedicaFamiliar to set
	 */
	public void setUnidadMedicaFamiliar(UnidadMedicaFamiliar unidadMedicaFamiliar) {
		this.unidadMedicaFamiliar = unidadMedicaFamiliar;
	}

	/**
	 * @return the estatus
	 */
	public Long getEstatus() {
		return estatus;
	}
	/**
	 * @param estatus the estatus to set
	 */
	public void setEstatus(Long estatus) {
		this.estatus = estatus;
	}

	public String getCurp() {
	       return curp;
	}

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
	public Departamento getDepartamento() {
		return departamento;
	}
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	public Puesto getPuesto() {
		return puesto;
	}
	public void setPuesto(Puesto puesto) {
		this.puesto = puesto;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public long getEstado() {
		return estado;
	}
	public void setEstado(long estado) {
		this.estado = estado;
	}
	
}


