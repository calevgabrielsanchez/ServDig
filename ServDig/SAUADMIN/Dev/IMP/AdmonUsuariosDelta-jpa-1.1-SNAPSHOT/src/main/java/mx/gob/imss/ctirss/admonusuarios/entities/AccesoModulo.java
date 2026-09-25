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
 * @author Alan Rene Garcia Rico
 * @version 1
 * Clase encargada de almacenar en la base de datos los datos para la tabla SSO_ACCESOMODULOS
 *
 */
@Entity
@Table(name="SSO_ACCESOMODULOS")
@NamedQueries(
	    {	 
	      @NamedQuery(name = "AccesoModulo.findAllByClave", query = "select a from AccesoModulo a where a.solicitud.idSolicitud = :id"),
	      @NamedQuery(name = "AccesoModulo.deleteAllByClave", query = "delete from AccesoModulo  where solicitud.idSolicitud = :id"),
	      @NamedQuery(name = "AccesoModulo.findAllByDeptoMod", query = "select a from AccesoModulo a where a.deptoModulo.cveDeptoModulo = :id"),
	      @NamedQuery(name = "AccesoModulo.deleteAllByClaveDepMod", query = "delete from AccesoModulo  where deptoModulo.cveDeptoModulo = :idDeptoMod and solicitud.idSolicitud = :idSol"),
	      @NamedQuery(name = "AccesoModulo.deleteAllById", query = "delete from AccesoModulo  where cveAccesoModulo = :id")
	    }
)
public class AccesoModulo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "DIT_SSOACCESOMODULO_CVESSOACCESOMODULO_GENERATOR", sequenceName = "SEC_PK_SSO_PERFILESSOL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_SSOACCESOMODULO_CVESSOACCESOMODULO_GENERATOR")
	@Column(name="CVE_SSOACCESOMODULO", nullable = false, updatable = false)
	private Long cveAccesoModulo;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CVE_SSOSOLICITUD")
	private Solicitudes solicitud;

	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CVE_SSODEPTOMODULO")
	private DeptoModulo deptoModulo;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "CVE_SSOESTATUS")
	private Estatus estatus;
	/*
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CVE_ID_APROBADOR")
	private Aprobadores aprobador; */
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_FECHAREG", nullable = false, updatable = false)
	private Date registroRegistro;

	public Long getCveAccesoModulo() {
		return cveAccesoModulo;
	}

	public void setCveAccesoModulo(Long cveAccesoModulo) {
		this.cveAccesoModulo = cveAccesoModulo;
	}

	public Solicitudes getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(Solicitudes solicitud) {
		this.solicitud = solicitud;
	}

	public DeptoModulo getDeptoModulo() {
		return deptoModulo;
	}

	public void setDeptoModulo(DeptoModulo deptoModulo) {
		this.deptoModulo = deptoModulo;
	}

	public Estatus getEstatus() {
		return estatus;
	}

	public void setEstatus(Estatus estatus) {
		this.estatus = estatus;
	}
   /*
	public Aprobadores getAprobador() {
		return aprobador;
	}

	public void setAprobador(Aprobadores aprobador) {
		this.aprobador = aprobador;
	}  */

	public Date getRegistroRegistro() {
		return registroRegistro;
	}

	public void setRegistroRegistro(Date registroRegistro) {
		this.registroRegistro = registroRegistro;
	}
	
}
