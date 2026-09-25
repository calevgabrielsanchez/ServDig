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
 * Esta es la clase encargada de realizar la persistencia de los movimientos que se registran de un usuario.
 */
@Entity
@Table(name="SSO_USRMOVIMIENTOS")
@NamedQueries(
	    {	        
	       
	    }
)
public class UsuarioMovimientos implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "DIT_SSOUSRMOVIMIENTOS_CVEIDSSOUSRMOVTO_GENERATOR", sequenceName = "SEQ_CVE_SSOUSRMOVTO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_SSOUSRMOVIMIENTOS_CVEIDSSOUSRMOVTO_GENERATOR")	
	@Column(name="CVE_SSOUSRMOVTO", nullable = false, updatable = false)
	private Long idMovimiento;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CVE_SSOSOLICITUD")
	private Solicitudes solicitud;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "CVE_SSOESTATUS")
	private Estatus estatus;
	
	@Column(name="DES_DATOSMOVIENTO", nullable=false, length=200)
	private String datosMovimiento;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CVE_ID_APROBADOR")
	private Aprobadores aprobador;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_FECHAREGISTRO", nullable=false)
	private Date fechaRegistro;

	public Long getIdMovimiento() {
		return idMovimiento;
	}

	public void setIdMovimiento(Long idMovimiento) {
		this.idMovimiento = idMovimiento;
	}

	public Solicitudes getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(Solicitudes solicitud) {
		this.solicitud = solicitud;
	}

	public Estatus getEstatus() {
		return estatus;
	}

	public void setEstatus(Estatus estatus) {
		this.estatus = estatus;
	}

	public String getDatosMovimiento() {
		return datosMovimiento;
	}

	public void setDatosMovimiento(String datosMovimiento) {
		this.datosMovimiento = datosMovimiento;
	}

	public Aprobadores getAprobador() {
		return aprobador;
	}

	public void setAprobador(Aprobadores aprobador) {
		this.aprobador = aprobador;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
}
