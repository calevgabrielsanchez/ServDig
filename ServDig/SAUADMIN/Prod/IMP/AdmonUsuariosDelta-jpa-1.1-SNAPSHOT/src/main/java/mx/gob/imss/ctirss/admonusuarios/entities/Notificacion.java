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
 * @author cesarAgustin
 *
 */
@Entity
@Table(name="SSO_NOTIFICACION")
@NamedQueries(
	    {
	    	@NamedQuery(name = "Notificacion.deleteAllByClave", query = "delete  from Notificacion  where solicitud.idSolicitud = :id")
	    }
)
public class Notificacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CVE_ID_SSO_NOTIFICACION", nullable = true, updatable = false)
	private Long idNotificacion = null;
	

	@Column(name="CVE_TIPO_SSO_NOTIFICACION")
	private Long tipoNotificcion;
	
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CVE_SSOSOLICITUD", nullable=false)	
	private Solicitudes solicitud;  
	
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_FECHA_NOTIFICACION")
	private Date fechaNotificacion;


	public Long getIdNotificacion() {
		return idNotificacion;
	}


	public void setIdNotificacion(Long idNotificacion) {
		this.idNotificacion = idNotificacion;
	}


	public Long getTipoNotificcion() {
		return tipoNotificcion;
	}


	public void setTipoNotificcion(Long tipoNotificcion) {
		this.tipoNotificcion = tipoNotificcion;
	}

   
	public Solicitudes getSolicitud() {
		return solicitud;
	}


	public void setSolicitud(Solicitudes solicitud) {
		this.solicitud = solicitud;
	}   


	public Date getFechaNotificacion() {
		return fechaNotificacion;
	}


	public void setFechaNotificacion(Date fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}


	
}
