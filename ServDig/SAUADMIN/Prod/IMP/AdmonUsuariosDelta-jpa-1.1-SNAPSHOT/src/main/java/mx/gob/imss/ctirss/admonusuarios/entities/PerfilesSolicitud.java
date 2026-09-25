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
 * 
 * @author Alan Rene Garcia Rico
 * @version 1.0
 * Es la clase encargada de almacenar en la base de datos en la tabla SSO_PERFILESSOL
 *
 */
@Entity
@Table(name="SSO_PERFILESSOL")
@NamedQueries(
    {
    	 @NamedQuery(name = "PerfilesSolicitud.findAllByClave", query = "select p from PerfilesSolicitud p where p.solicitud.idSolicitud = :id"),
    	 @NamedQuery(name = "PerfilesSolicitud.deleteAllByClave", query = "delete  from PerfilesSolicitud  where solicitud.idSolicitud = :id"),
    	 @NamedQuery(name = "PerfilesSolicitud.deleteAllByClavePerfil", query = "delete  from PerfilesSolicitud  where solicitud.idSolicitud = :idSol and puesto.cvePuesto = :idPuesto")
    }
)
public class PerfilesSolicitud implements Serializable {
    private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "CVE_SSOPERFILESSOL", nullable = true, updatable = false)
    private Long idPerfilesSolicitud;

	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CVE_SSOSOLICITUD")
	private Solicitudes solicitud;

	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CVE_SSOPUESTO")
	private Puesto puesto;

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_FECHAREGISTRO", nullable=false, length=200)
	private Date fechaRegistro;
	
	@Column(name="DES_DEFAULT")
	private String defaultRol;
	
	public Long getIdPerfilesSolicitud() {
		return idPerfilesSolicitud;
	}

	public void setIdPerfilesSolicitud(Long idPerfilesSolicitud) {
		this.idPerfilesSolicitud = idPerfilesSolicitud;
	}

	public Solicitudes getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(Solicitudes solicitud) {
		this.solicitud = solicitud;
	}

	public Puesto getPuesto() {
		return puesto;
	}

	public void setPuesto(Puesto puesto) {
		this.puesto = puesto;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getDefaultRol() {
		return defaultRol;
	}

	public void setDefaultRol(String defaultRol) {
		this.defaultRol = defaultRol;
	}	
}
