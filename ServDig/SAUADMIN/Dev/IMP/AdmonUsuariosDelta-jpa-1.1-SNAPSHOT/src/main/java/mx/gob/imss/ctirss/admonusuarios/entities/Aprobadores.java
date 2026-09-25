/**
 * 
 */
package mx.gob.imss.ctirss.admonusuarios.entities;

import java.io.Serializable;

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
import javax.persistence.CascadeType;

/**
 * @author Alan Rene Garcia Rico
 *
 */
@Entity
@Table(name="SSO_APROBADOR")
@NamedQueries(
	    {		     
	        @NamedQuery(name = "Aprobadores.findById", query = "select a from Aprobadores a where a.idAprobador = :id"),
	        @NamedQuery(name = "Aprobadores.findBySolicitud", query = "select a from Aprobadores a where a.solicitud.idSolicitud = :idSol and a.modulo.idModulo = :idMod"),
	        @NamedQuery(name = "Aprobadores.findByMatricula", query = "select a from Aprobadores a where a.matricula = :idMatricula"),
	        @NamedQuery(name = "Aprobadores.deleteAllByClave", query = "delete  from Aprobadores  where solicitud.idSolicitud = :id"),
	        @NamedQuery(name = "Aprobadores.deleteAllByClavesMod", query = "delete  from Aprobadores  where solicitud.idSolicitud = :idSol and modulo.idModulo = :idMod")
	    }
)
public class Aprobadores implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "DIT_SSOAPROBADOR_CVEIDAPROBADORES_GENERATOR", sequenceName = "SEC_PK_SSO_APROBADOR", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_SSOAPROBADOR_CVEIDAPROBADORES_GENERATOR")	
	@Column(name="CVE_ID_APROBADOR", nullable = false, updatable = false)
	private Long idAprobador;
	
	@Column(name="CVE_MATRICULA", nullable=false, length=20)
	private String matricula;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CVE_SSOSOLICITUD")
	private Solicitudes solicitud;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CVE_ID_MODULO")
	private Modulo modulo;
		
	public Long getIdAprobador() {
		return idAprobador;
	}

	public void setIdAprobador(Long idAprobador) {
		this.idAprobador = idAprobador;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Solicitudes getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(Solicitudes solicitud) {
		this.solicitud = solicitud;
	}

	public Modulo getModulo() {
		return modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}			
}
